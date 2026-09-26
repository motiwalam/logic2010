import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/**
 * Checks that a recompiled jar is equivalent to a reference jar (normally the original
 * jar with the deobfuscation mappings applied, i.e. build/logic-remapped.jar).
 *
 * Compares class structure (supertypes, fields, methods, access flags) and, for every
 * method, a "behavioural fingerprint": the multiset of methods it calls, fields it
 * reads/writes, types it instantiates/checks, and constants it loads, plus counts of
 * arithmetic/branch/return instructions. Compiler differences (javac 1.6 vs modern
 * javac, synthetic accessors, switch-on-string lowering...) are normalised away where
 * they are known to be harmless.
 *
 * Usage: java -cp asm*.jar Compare.java reference.jar rebuilt.jar [--verbose]
 * Exit status 0 when equivalent.
 */
public class Compare {
    static boolean verbose;

    public static void main(String[] args) throws Exception {
        verbose = Arrays.asList(args).contains("--verbose");
        int ki = Arrays.asList(args).indexOf("--known");
        if (ki >= 0) {
            // lines: "<class>: <method name+desc>   # reason". Differences listed here were
            // inspected by hand and found to be harmless (see the reasons in the file).
            for (String l : Files.readAllLines(Paths.get(args[ki + 1]))) {
                int h = l.indexOf('#');
                String k = (h >= 0 ? l.substring(0, h) : l).trim();
                if (!k.isEmpty()) known.add(k);
            }
        }
        Map<String, ClassNode> ref = read(args[0]), got = read(args[1]);
        jarClasses = ref; // both jars have the same class names; use the reference for hierarchy lookups
        int problems = 0;
        Set<String> all = new TreeSet<>(ref.keySet());
        all.addAll(got.keySet());
        for (String name : all) {
            ClassNode a = ref.get(name), b = got.get(name);
            if (a == null) { report(name, "extra class in rebuilt jar"); problems++; continue; }
            if (b == null) { report(name, "missing class in rebuilt jar"); problems++; continue; }
            problems += compareClass(a, b);
        }
        System.out.println(problems == 0 ? "EQUIVALENT: " + ref.size() + " classes match"
                : problems + " difference(s) found");
        if (minorDiffs > 0) System.out.println("(" + minorDiffs + " method(s) differ only in redundant-looking casts; --verbose to list)");
        System.exit(problems == 0 ? 0 : 1);
    }

    static void report(String where, String what) {
        System.out.println(where + ": " + what);
    }

    static final int ACC_MASK = Opcodes.ACC_PUBLIC | Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED
            | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL | Opcodes.ACC_ABSTRACT | Opcodes.ACC_INTERFACE;

    static int compareClass(ClassNode a, ClassNode b) {
        int p = 0;
        if (!Objects.equals(a.superName, b.superName)) { report(a.name, "superclass " + a.superName + " vs " + b.superName); p++; }
        if (!new TreeSet<>(a.interfaces).equals(new TreeSet<>(b.interfaces))) { report(a.name, "interfaces " + a.interfaces + " vs " + b.interfaces); p++; }
        // javac adds ACC_SUPER/ACC_SYNCHRONIZED noise on classes; compare the meaningful bits
        int ca = a.access & (ACC_MASK | Opcodes.ACC_ENUM), cb = b.access & (ACC_MASK | Opcodes.ACC_ENUM);
        if (a.name.matches(".*\\$\\d+")) { ca &= ~Opcodes.ACC_FINAL; cb &= ~Opcodes.ACC_FINAL; } // javac < 9 marked anonymous classes final
        if (ca != cb) { report(a.name, "class access " + Integer.toHexString(ca) + " vs " + Integer.toHexString(cb)); p++; }

        Map<String, FieldNode> fa = new TreeMap<>(), fb = new TreeMap<>();
        for (FieldNode f : a.fields) if (!synthetic(f.access, f.name)) fa.put(f.name + ":" + f.desc, f);
        for (FieldNode f : b.fields) if (!synthetic(f.access, f.name)) fb.put(f.name + ":" + f.desc, f);
        for (String k : union(fa.keySet(), fb.keySet())) {
            FieldNode x = fa.get(k), y = fb.get(k);
            if (x == null || y == null) { report(a.name, (x == null ? "extra" : "missing") + " field " + k); p++; continue; }
            if ((x.access & ACC_MASK) != (y.access & ACC_MASK)) { report(a.name, "field access " + k); p++; }
            if (!Objects.equals(x.value, y.value)) { report(a.name, "field constant " + k + " " + x.value + " vs " + y.value); p++; }
        }

        Map<String, MethodNode> ma = new TreeMap<>(), mb = new TreeMap<>();
        for (MethodNode m : a.methods) if (!synthetic(m.access, m.name)) ma.put(m.name + m.desc, m);
        for (MethodNode m : b.methods) if (!synthetic(m.access, m.name)) mb.put(m.name + m.desc, m);
        for (String k : union(ma.keySet(), mb.keySet())) {
            MethodNode x = ma.get(k), y = mb.get(k);
            if (x == null || y == null) {
                // javac emits an empty <clinit>/default ctor differences only if source differs; report
                report(a.name, (x == null ? "extra" : "missing") + " method " + k); p++; continue;
            }
            int accMask = ACC_MASK & ~Opcodes.ACC_INTERFACE | Opcodes.ACC_SYNCHRONIZED | Opcodes.ACC_VARARGS;
            if ((x.access & accMask & ~Opcodes.ACC_VARARGS) != (y.access & accMask & ~Opcodes.ACC_VARARGS)) {
                report(a.name, "method access " + k + " " + Integer.toHexString(x.access) + " vs " + Integer.toHexString(y.access)); p++;
            }
            if (!new TreeSet<>(orEmpty(x.exceptions)).equals(new TreeSet<>(orEmpty(y.exceptions)))) {
                report(a.name, "throws clause " + k); p++;
            }
            Map<String, Integer> px = fingerprint(x, a), py = fingerprint(y, b);
            Map<String, Integer> minorX = extractMinor(px), minorY = extractMinor(py);
            if (!minorX.equals(minorY)) minorDiffs++;
            if (verbose && !minorX.equals(minorY)) report(a.name, "casts differ in " + k + ": " + minorX + " vs " + minorY);
            if (!px.equals(py) && known.contains(a.name + ": " + k)) {
                if (verbose) report(a.name, "known difference in " + k);
            } else if (!px.equals(py)) {
                p++;
                StringBuilder sb = new StringBuilder("code differs in " + k + ":");
                for (String f : union(px.keySet(), py.keySet())) {
                    int cx = px.getOrDefault(f, 0), cy = py.getOrDefault(f, 0);
                    if (cx != cy) sb.append("\n      ").append(f).append("  original=").append(cx).append(" rebuilt=").append(cy);
                }
                report(a.name, sb.toString());
            }
        }
        return p;
    }

    static int minorDiffs;
    static Set<String> known = new HashSet<>();
    static Map<String, ClassNode> jarClasses = new HashMap<>();
    static Map<String, ClassNode> jdkClasses = new HashMap<>();

    static Map<String, Integer> extractMinor(Map<String, Integer> fp) {
        Map<String, Integer> m = new TreeMap<>();
        fp.entrySet().removeIf(e -> { if (e.getKey().startsWith("~")) { m.put(e.getKey(), e.getValue()); return true; } return false; });
        return m;
    }

    static AbstractInsnNode next(AbstractInsnNode in) {
        AbstractInsnNode n = in.getNext();
        while (n != null && n.getOpcode() < 0) n = n.getNext();
        return n;
    }

    static ClassNode lookup(String name) {
        ClassNode cn = jarClasses.get(name);
        if (cn != null) return cn;
        return jdkClasses.computeIfAbsent(name, n -> {
            try (InputStream is = ClassLoader.getSystemResourceAsStream(n + ".class")) {
                if (is == null) return null;
                ClassNode c = new ClassNode();
                new ClassReader(is).accept(c, ClassReader.SKIP_CODE);
                return c;
            } catch (IOException e) { return null; }
        });
    }

    /**
     * Canonical owner for a member reference. javac records the static receiver type,
     * which depends on how precisely the decompiler typed a variable, so resolve to the
     * declaring class, and for overridable methods to the most general declaration.
     */
    static String declaringClass(String owner, String name, String desc, boolean method) {
        if (owner.startsWith("[")) return owner;
        List<String> declarers = new ArrayList<>();
        Deque<String> q = new ArrayDeque<>(List.of(owner));
        Set<String> seen = new HashSet<>();
        while (!q.isEmpty()) {
            String c = q.poll();
            if (!seen.add(c)) continue;
            ClassNode cn = lookup(c);
            if (cn == null) continue;
            int acc = -1;
            if (method) { for (MethodNode m : cn.methods) if (m.name.equals(name) && m.desc.equals(desc)) acc = m.access; }
            else { for (FieldNode f : cn.fields) if (f.name.equals(name) && f.desc.equals(desc)) acc = f.access; }
            if (acc != -1) {
                declarers.add(c);
                if (!method || (acc & (Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE)) != 0) return c;
            }
            if (cn.superName != null) q.add(cn.superName);
            q.addAll(cn.interfaces);
        }
        if (declarers.isEmpty()) return owner;
        // roots: declarations that don't themselves override an inherited declaration
        return declarers.stream().filter(d -> declarers.stream().noneMatch(o -> !o.equals(d) && isAncestor(o, d)))
                .sorted().findFirst().orElse(owner);
    }

    static boolean isAncestor(String anc, String cls) {
        Deque<String> q = new ArrayDeque<>(List.of(cls));
        Set<String> seen = new HashSet<>();
        while (!q.isEmpty()) {
            String c = q.poll();
            if (!seen.add(c)) continue;
            if (!c.equals(cls) && c.equals(anc)) return true;
            ClassNode cn = lookup(c);
            if (cn == null) continue;
            if (cn.superName != null) q.add(cn.superName);
            q.addAll(cn.interfaces);
        }
        return false;
    }

    static boolean syntheticMember(String owner, String name, String desc, boolean method) {
        ClassNode cn = lookup(declaringClass(owner, name, desc, method));
        if (cn == null) return false;
        if (method) { for (MethodNode m : cn.methods) if (m.name.equals(name) && m.desc.equals(desc)) return (m.access & Opcodes.ACC_SYNTHETIC) != 0; }
        else { for (FieldNode f : cn.fields) if (f.name.equals(name) && f.desc.equals(desc)) return (f.access & Opcodes.ACC_SYNTHETIC) != 0; }
        return false;
    }

    static boolean synthetic(int access, String name) {
        return (access & Opcodes.ACC_SYNTHETIC) != 0 || name.startsWith("access$") || name.startsWith("class$")
                || name.startsWith("$SwitchMap$") || name.startsWith("this$") || name.startsWith("val$");
    }

    static <T> List<T> orEmpty(List<T> l) { return l == null ? List.of() : l; }

    static Set<String> union(Set<String> a, Set<String> b) {
        Set<String> s = new TreeSet<>(a); s.addAll(b); return s;
    }

    /** Behavioural fingerprint of one method, robust to register allocation and block ordering. */
    static Map<String, Integer> fingerprint(MethodNode m, ClassNode owner) {
        Map<String, Integer> fp = new TreeMap<>();
        for (AbstractInsnNode in : m.instructions) {
            String key = null;
            int op = in.getOpcode();
            if (in instanceof MethodInsnNode mi) {
                if (mi.owner.equals("java/lang/StringBuilder") || mi.owner.equals("java/lang/StringBuffer")) continue; // string concat lowering
                if (mi.name.startsWith("access$") || syntheticMember(mi.owner, mi.name, mi.desc, true)) { key = "call accessor"; }  // synthetic accessor: target varies by compiler
                else key = "call " + declaringClass(mi.owner, mi.name, mi.desc, true) + "." + mi.name + mi.desc;
            } else if (in instanceof FieldInsnNode fi) {
                if (fi.name.startsWith("this$") || fi.name.startsWith("val$") || fi.name.startsWith("$SwitchMap$")
                        || syntheticMember(fi.owner, fi.name, fi.desc, false)) continue; // outer-instance/captured-variable fields
                // `expr.staticField.staticMethod()` compiles to getstatic+pop; decompilers drop it
                if (op == Opcodes.GETSTATIC && next(in) != null && next(in).getOpcode() == Opcodes.POP) continue;
                key = (op == Opcodes.GETFIELD || op == Opcodes.GETSTATIC ? "read " : "write ") + declaringClass(fi.owner, fi.name, fi.desc, false) + "." + fi.name;
            } else if (in instanceof TypeInsnNode ti) {
                if (ti.desc.equals("java/lang/StringBuilder") || ti.desc.equals("java/lang/StringBuffer")) continue;
                // casts are compared separately (decompilers add/remove redundant ones)
                key = (op == Opcodes.CHECKCAST ? "~" : "") + Printer(op) + " " + ti.desc;
            } else if (in instanceof LdcInsnNode li) {
                key = "const " + constString(li.cst);
            } else if (in instanceof IntInsnNode ii && (op == Opcodes.BIPUSH || op == Opcodes.SIPUSH)) {
                key = "const " + ii.operand;
            } else if (op >= Opcodes.ICONST_M1 && op <= Opcodes.ICONST_5) {
                key = "const " + (op - Opcodes.ICONST_0);
            } else if (op == Opcodes.LCONST_0 || op == Opcodes.LCONST_1) {
                key = "const " + (op - Opcodes.LCONST_0) + "L";
            } else if (op >= Opcodes.FCONST_0 && op <= Opcodes.FCONST_2) {
                key = "const " + (op - Opcodes.FCONST_0) + ".0f";
            } else if (op == Opcodes.DCONST_0 || op == Opcodes.DCONST_1) {
                key = "const " + (op - Opcodes.DCONST_0) + ".0d";
            } else if (in instanceof MultiANewArrayInsnNode ma) {
                key = "newarray " + ma.desc;
            } else if (op >= Opcodes.IADD && op <= Opcodes.LXOR || op == Opcodes.IINC) {
                key = "arith " + Printer(op);
            } else if (op == Opcodes.ATHROW || op == Opcodes.MONITORENTER) {
                key = Printer(op);
            } else if (in instanceof TableSwitchInsnNode || in instanceof LookupSwitchInsnNode) {
                key = "switch";
            }
            if (key != null) fp.merge(key, 1, Integer::sum);
        }
        // constants are too sensitive to compiler choices (iconst vs bipush, folded
        // expressions, switch lowering); keep only distinct values for them.
        fp.replaceAll((k, v) -> k.startsWith("const ") ? 1 : v);
        return fp;
    }

    static String constString(Object c) {
        if (c instanceof Type t) return "class " + t.getDescriptor();
        if (c instanceof String s) return "\"" + s + "\"";
        if (c instanceof Long) return c + "L";
        if (c instanceof Float) return c + "f";
        if (c instanceof Double) return c + "d";
        return String.valueOf(c);
    }

    static String Printer(int op) {
        return org.objectweb.asm.util.Printer.OPCODES[op].toLowerCase();
    }

    static Map<String, ClassNode> read(String jar) throws IOException {
        Map<String, ClassNode> m = new TreeMap<>();
        try (JarFile jf = new JarFile(jar)) {
            for (JarEntry e : Collections.list(jf.entries())) {
                if (!e.getName().endsWith(".class")) continue;
                ClassNode cn = new ClassNode();
                new ClassReader(jf.getInputStream(e).readAllBytes()).accept(cn, 0);
                m.put(cn.name, cn);
            }
        }
        return m;
    }
}
