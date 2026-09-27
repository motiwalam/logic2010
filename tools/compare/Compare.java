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
        dataflow = !Arrays.asList(args).contains("--no-dataflow");
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
            String key = k;
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
            if (px.equals(py) && dataflow) {
                // same ingredients; now check they are wired together the same way
                long t0 = System.nanoTime();
                Map<String, Integer> dx = Dataflow.sinks(x, a.name), dy = Dataflow.sinks(y, b.name);
                long t1 = System.nanoTime();
                Dataflow.dropMatched(dx, dy);
                long t2 = System.nanoTime();
                if (System.getenv("COMPARE_PROFILE") != null && t2 - t0 > 1_000_000_000L)
                    System.err.printf("slow: %s.%s analysis=%dms match=%dms sinks=%d/%d%n", a.name, k, (t1 - t0) / 1000000, (t2 - t1) / 1000000, dx.size(), dy.size());
                if (!dx.equals(dy)) {
                    px = dx; py = dy;
                    k = k + " [dataflow]";
                }
            }
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
    static boolean dataflow;
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
                if ((op == Opcodes.GETSTATIC || op == Opcodes.GETFIELD) && next(in) != null && next(in).getOpcode() == Opcodes.POP) continue;
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
            } else if (op >= Opcodes.I2L && op <= Opcodes.I2S || op >= Opcodes.LCMP && op <= Opcodes.DCMPG) {
                key = "convert/compare " + Printer(op);
            } else if (op == Opcodes.INSTANCEOF || op == Opcodes.ARRAYLENGTH) {
                key = Printer(op);
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

    /**
     * Symbolic dataflow fingerprint. Runs ASM's Analyzer with an interpreter whose values
     * are depth-limited expression strings. Loads/stores are transparent, so the result is
     * independent of local-variable allocation, and each value records which parameters,
     * fields, call results and constants it was computed from. For every "sink" (call,
     * field/array store, return, throw, branch, switch) the operand expressions are
     * recorded. This catches wrong-variable/merged-variable decompiler bugs that the
     * ingredient-count fingerprint cannot see.
     */
    static class Dataflow {
        static final int DEPTH = 4, MAX_ALTS = 6;

        static final class V implements org.objectweb.asm.tree.analysis.Value {
            final int size;
            final TreeSet<String> e;   // alternative expressions at join points; "*" = too many
            V(int size, Collection<String> e) { this.size = size; this.e = new TreeSet<>(e); }
            V(int size, String e) { this(size, List.of(e)); }
            public int getSize() { return size; }
            @Override public boolean equals(Object o) { return o instanceof V v && v.size == size && v.e.equals(e); }
            @Override public int hashCode() { return e.hashCode() * 31 + size; }
            String str() { return e.size() == 1 ? e.first() : "{" + String.join("|", e) + "}"; }
        }

        static String cut(String s) {
            // limit nesting depth of an expression string
            StringBuilder b = new StringBuilder();
            int d = 0, skip = -1;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(' || c == '{') { d++; if (d > DEPTH && skip < 0) { skip = d; b.append("(…"); } }
                if (skip < 0) b.append(c);
                if (c == ')' || c == '}') { if (d == skip) { skip = -1; b.append(')'); } d--; }
            }
            return b.toString();
        }

        static V v(int size, String op, V... args) {
            StringBuilder b = new StringBuilder(op).append('(');
            for (int i = 0; i < args.length; i++) { if (i > 0) b.append(','); b.append(args[i] == null ? "?" : args[i].str()); }
            return new V(size, cut(b.append(')').toString()));
        }

        static int sz(Type t) { return t == null ? 1 : t.getSize(); }

        static final Set<Integer> COMMUTATIVE = Set.of(Opcodes.IADD, Opcodes.LADD, Opcodes.FADD, Opcodes.DADD,
                Opcodes.IMUL, Opcodes.LMUL, Opcodes.FMUL, Opcodes.DMUL, Opcodes.IAND, Opcodes.LAND,
                Opcodes.IOR, Opcodes.LOR, Opcodes.IXOR, Opcodes.LXOR);

        static final Set<Integer> ASSOCIATIVE = Set.of(Opcodes.IADD, Opcodes.LADD, Opcodes.IMUL, Opcodes.LMUL,
                Opcodes.IAND, Opcodes.LAND, Opcodes.IOR, Opcodes.LOR, Opcodes.IXOR, Opcodes.LXOR);

        /** Split "a,b(c,d),e" at top-level commas. */
        static List<String> splitTop(String s) {
            List<String> r = new ArrayList<>();
            int d = 0, start = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(' || c == '{') d++;
                else if (c == ')' || c == '}') d--;
                else if (c == ',' && d == 0) { r.add(s.substring(start, i)); start = i + 1; }
            }
            r.add(s.substring(start));
            return r;
        }

        static String opName(int op) { return org.objectweb.asm.util.Printer.OPCODES[op].toLowerCase(); }

        static class Interp extends org.objectweb.asm.tree.analysis.Interpreter<V> {
            Interp() { super(Opcodes.ASM9); }
            @Override public V newValue(Type t) {
                if (t == Type.VOID_TYPE) return null;
                return new V(sz(t), "?");
            }
            String outerParam;   // descriptor of an inner-class constructor's outer-instance parameter
            @Override public V newParameterValue(boolean isInstanceMethod, int local, Type t) {
                if (isInstanceMethod && local == 1 && t.getDescriptor().equals(outerParam))
                    return new V(1, "captured " + outerParam + "(this)");
                return new V(sz(t), (isInstanceMethod && local == 0) ? "this" : "p" + local);
            }
            @Override public V newExceptionValue(TryCatchBlockNode tcb, org.objectweb.asm.tree.analysis.Frame<V> f, Type t) {
                return new V(1, "exc");
            }
            @Override public V newOperation(AbstractInsnNode in) {
                int op = in.getOpcode();
                switch (op) {
                    case Opcodes.ACONST_NULL: return new V(1, "null");
                    case Opcodes.LCONST_0: case Opcodes.LCONST_1: return new V(2, "" + (op - Opcodes.LCONST_0));
                    case Opcodes.DCONST_0: case Opcodes.DCONST_1: return new V(2, "" + (op - Opcodes.DCONST_0));
                    case Opcodes.BIPUSH: case Opcodes.SIPUSH: return new V(1, "" + ((IntInsnNode) in).operand);
                    case Opcodes.LDC: {
                        Object c = ((LdcInsnNode) in).cst;
                        return new V(c instanceof Long || c instanceof Double ? 2 : 1, constString(c).replace('(', '[').replace(')', ']'));
                    }
                    case Opcodes.GETSTATIC: {
                        FieldInsnNode f = (FieldInsnNode) in;
                        return new V(sz(Type.getType(f.desc)), "gs " + declaringClass(f.owner, f.name, f.desc, false) + "." + f.name);
                    }
                    case Opcodes.NEW: return new V(1, "new " + ((TypeInsnNode) in).desc);
                    default:
                        if (op >= Opcodes.ICONST_M1 && op <= Opcodes.ICONST_5) return new V(1, "" + (op - Opcodes.ICONST_0));
                        if (op >= Opcodes.FCONST_0 && op <= Opcodes.FCONST_2) return new V(1, "" + (op - Opcodes.FCONST_0));
                        return new V(1, opName(op));
                }
            }
            @Override public V copyOperation(AbstractInsnNode in, V v) { return v; }
            @Override public V unaryOperation(AbstractInsnNode in, V v) {
                int op = in.getOpcode();
                switch (op) {
                    case Opcodes.IINC: return v(1, "iadd", v, new V(1, "" + ((IincInsnNode) in).incr));
                    case Opcodes.GETFIELD: {
                        FieldInsnNode f = (FieldInsnNode) in;
                        if (f.name.startsWith("this$") || f.name.startsWith("val$") || syntheticMember(f.owner, f.name, f.desc, false))
                            return v(sz(Type.getType(f.desc)), "captured " + f.desc, v);   // compiler-named outer/captured fields
                        return v(sz(Type.getType(f.desc)), "gf " + declaringClass(f.owner, f.name, f.desc, false) + "." + f.name, v);
                    }
                    case Opcodes.CHECKCAST: return v;
                    case Opcodes.INSTANCEOF: return v(1, "instanceof " + ((TypeInsnNode) in).desc, v);
                    case Opcodes.NEWARRAY: case Opcodes.ANEWARRAY: return v(1, "newarray", v);
                    case Opcodes.I2L: case Opcodes.F2L: case Opcodes.D2L: case Opcodes.I2D: case Opcodes.L2D: case Opcodes.F2D:
                        return v(2, opName(op), v);
                    case Opcodes.I2B: case Opcodes.I2C: case Opcodes.I2S:
                        // narrowing is meaningful; widening int->int (i2s of a short) is a no-op but rare
                        return v(1, opName(op), v);
                    case Opcodes.LNEG: case Opcodes.DNEG: return v(2, opName(op), v);
                    default:
                        if (op >= Opcodes.IFEQ && op <= Opcodes.IFLE || op == Opcodes.IFNULL || op == Opcodes.IFNONNULL
                                || op == Opcodes.TABLESWITCH || op == Opcodes.LOOKUPSWITCH || op == Opcodes.PUTSTATIC
                                || op == Opcodes.ATHROW || op == Opcodes.MONITORENTER || op == Opcodes.MONITOREXIT
                                || op >= Opcodes.IRETURN && op <= Opcodes.ARETURN) return null;
                        return v(1, opName(op), v);
                }
            }
            @Override public V binaryOperation(AbstractInsnNode in, V a, V b) {
                int op = in.getOpcode();
                if (op == Opcodes.PUTFIELD || op >= Opcodes.IF_ICMPEQ && op <= Opcodes.IF_ACMPNE) return null;
                int size = (op == Opcodes.LALOAD || op == Opcodes.DALOAD) ? 2
                        : (op >= Opcodes.IADD && op <= Opcodes.LXOR && "LD".indexOf(opName(op).toUpperCase().charAt(0)) >= 0 && op != Opcodes.LCMP) ? 2 : 1;
                if (op == Opcodes.LSHL || op == Opcodes.LSHR || op == Opcodes.LUSHR) size = 2;
                if (op >= Opcodes.LCMP && op <= Opcodes.DCMPG) size = 1;
                if (ASSOCIATIVE.contains(op)) {
                    // integer + * & | ^ are associative and commutative (wraparound arithmetic):
                    // flatten nested chains and sort operands, so (a+b)+c == a+(c+b)
                    List<String> terms = new ArrayList<>();
                    for (V x : new V[]{a, b}) {
                        String t = x == null ? "?" : x.str();
                        if (t.startsWith(opName(op) + "(") && t.endsWith(")")) terms.addAll(splitTop(t.substring(opName(op).length() + 1, t.length() - 1)));
                        else terms.add(t);
                    }
                    Collections.sort(terms);
                    return new V(size, cut(opName(op) + "(" + String.join(",", terms) + ")"));
                }
                if (COMMUTATIVE.contains(op) && a.str().compareTo(b.str()) > 0) { V t = a; a = b; b = t; }
                return v(size, opName(op), a, b);
            }
            @Override public V ternaryOperation(AbstractInsnNode in, V a, V b, V c) { return null; }
            @Override public V naryOperation(AbstractInsnNode in, List<? extends V> args) {
                if (in instanceof MethodInsnNode mi) {
                    Type rt = Type.getReturnType(mi.desc);
                    return v(sz(rt), "call " + callName(mi), args.toArray(new V[0]));
                }
                if (in instanceof MultiANewArrayInsnNode) return v(1, "newarray", args.toArray(new V[0]));
                return new V(1, "indy");
            }
            @Override public void returnOperation(AbstractInsnNode in, V v, V expected) { }
            @Override public V merge(V a, V b) {
                if (a.equals(b)) return a;
                if (a.e.contains("*")) return a;
                TreeSet<String> u = new TreeSet<>(a.e);
                u.addAll(b.e);
                u.remove("?");
                if (u.isEmpty()) u.add("?");
                if (u.size() > MAX_ALTS) return new V(Math.min(a.size, b.size), "*");
                V r = new V(Math.min(a.size, b.size), u);
                return r.equals(a) ? a : r;
            }
        }

        static String callName(MethodInsnNode mi) {
            if (mi.name.startsWith("access$")) return "accessor";
            if (syntheticMember(mi.owner, mi.name, mi.desc, true)) return "accessor";
            return declaringClass(mi.owner, mi.name, mi.desc, true) + "." + mi.name;
        }

        /** One sink string per combination of the operands' alternative values. */
        static List<String> expand(String op, V... args) {
            List<String> acc = new ArrayList<>(List.of(""));
            for (int i = 0; i < args.length; i++) {
                Collection<String> alts = args[i] == null ? List.of("?") : args[i].e;
                List<String> next = new ArrayList<>();
                for (String prefix : acc) for (String a : alts) next.add(prefix + (i > 0 ? "," : "") + a);
                acc = next.size() > 64 ? List.of(prefix(acc) + "*") : next;
            }
            List<String> r = new ArrayList<>();
            for (String a : acc) r.add(cut(op + "(" + a + ")"));
            return r;
        }

        /**
         * Remove sinks that have a counterpart on the other side once "*" (an imprecise value,
         * e.g. a loop variable whose alternatives were widened) is treated as a wildcard.
         */
        static void dropMatched(Map<String, Integer> a, Map<String, Integer> b) {
            Set<String> allA = new HashSet<>(a.keySet()), allB = new HashSet<>(b.keySet());
            a.keySet().removeIf(x -> allB.contains(x) || allB.stream().anyMatch(y -> matches(x, y)));
            b.keySet().removeIf(y -> allA.contains(y) || allA.stream().anyMatch(x -> matches(x, y)));
        }

        static boolean matches(String x, String y) {
            return (y.indexOf('*') >= 0 || y.indexOf('…') >= 0 || y.indexOf('?') >= 0) && wild(y).matcher(x).matches()
                || (x.indexOf('*') >= 0 || x.indexOf('…') >= 0 || x.indexOf('?') >= 0) && wild(x).matcher(y).matches();
        }

        static final Map<String, java.util.regex.Pattern> WILD = new HashMap<>();
        static java.util.regex.Pattern wild(String s) {
            return WILD.computeIfAbsent(s, k -> {
                StringBuilder r = new StringBuilder();
                for (String part : k.split("(?<=[*…?])|(?=[*…?])")) {
                    if (part.equals("*") || part.equals("…") || part.equals("?")) r.append(".*");
                    else r.append(java.util.regex.Pattern.quote(part));
                }
                return java.util.regex.Pattern.compile(r.toString());
            });
        }

        static String prefix(List<String> l) { return l.isEmpty() ? "" : l.get(0); }

        /** Set of sink descriptions for one method (presence, not counts: decompilers duplicate/merge code). */
        static Map<String, Integer> sinks(MethodNode m, String owner) {
            Map<String, Integer> out = new TreeMap<>();
            if (m.instructions.size() == 0) return out;
            org.objectweb.asm.tree.analysis.Frame<V>[] frames;
            try {
                Interp interp = new Interp();
                int dollar = owner.lastIndexOf('$');
                if (m.name.equals("<init>") && dollar > 0 && m.desc.startsWith("(L" + owner.substring(0, dollar) + ";"))
                    interp.outerParam = "L" + owner.substring(0, dollar) + ";";
                frames = new org.objectweb.asm.tree.analysis.Analyzer<>(interp).analyze(owner, m);
            } catch (org.objectweb.asm.tree.analysis.AnalyzerException e) {
                out.put("analysis failed: " + e.getMessage(), 1);
                return out;
            }
            AbstractInsnNode[] insns = m.instructions.toArray();
            for (int i = 0; i < insns.length; i++) {
                org.objectweb.asm.tree.analysis.Frame<V> f = frames[i];
                AbstractInsnNode in = insns[i];
                int op = in.getOpcode();
                if (f == null || op < 0) continue;
                String sink = null;
                int top = f.getStackSize();
                if (in instanceof MethodInsnNode mi) {
                    int n = Type.getArgumentTypes(mi.desc).length + (op == Opcodes.INVOKESTATIC ? 0 : 1);
                    if (mi.owner.equals("java/lang/StringBuilder") || mi.owner.equals("java/lang/StringBuffer")) continue;
                    V[] a = new V[n];
                    for (int j = 0; j < n; j++) a[j] = f.getStack(top - n + j);
                    // a static call through an instance expression leaves nothing on the stack; fine
                    for (String x : expand("call " + callName(mi), a)) out.put(x, 1);
                } else if (op == Opcodes.PUTFIELD) {
                    FieldInsnNode fi = (FieldInsnNode) in;
                    if (syntheticMember(fi.owner, fi.name, fi.desc, false) || fi.name.startsWith("this$") || fi.name.startsWith("val$")) continue;
                    for (String x : expand("pf " + declaringClass(fi.owner, fi.name, fi.desc, false) + "." + fi.name, f.getStack(top - 2), f.getStack(top - 1))) out.put(x, 1);
                } else if (op == Opcodes.PUTSTATIC) {
                    FieldInsnNode fi = (FieldInsnNode) in;
                    for (String x : expand("ps " + declaringClass(fi.owner, fi.name, fi.desc, false) + "." + fi.name, f.getStack(top - 1))) out.put(x, 1);
                } else if (op >= Opcodes.IASTORE && op <= Opcodes.SASTORE) {
                    for (String x : expand("astore", f.getStack(top - 3), f.getStack(top - 2), f.getStack(top - 1))) out.put(x, 1);
                } else if (op >= Opcodes.IRETURN && op <= Opcodes.ARETURN) {
                    for (String x : expand("ret", f.getStack(top - 1))) out.put(x, 1);
                } else if (op == Opcodes.ATHROW) {
                    for (String x : expand("throw", f.getStack(top - 1))) out.put(x, 1);
                } else if (op >= Opcodes.IFEQ && op <= Opcodes.IFLE || op == Opcodes.IFNULL || op == Opcodes.IFNONNULL) {
                    for (String x : expand("cond", f.getStack(top - 1))) out.put(x, 1);
                } else if (op >= Opcodes.IF_ICMPEQ && op <= Opcodes.IF_ACMPNE) {
                    V x = f.getStack(top - 2), y = f.getStack(top - 1);
                    if (x.str().compareTo(y.str()) > 0) { V t = x; x = y; y = t; }
                    for (String z : expand("cond", x, y)) out.put(z, 1);
                } else if (op == Opcodes.TABLESWITCH || op == Opcodes.LOOKUPSWITCH) {
                    for (String x : expand("switch", f.getStack(top - 1))) out.put(x, 1);
                }
                if (sink != null) out.put(sink, 1);
            }
            return out;
        }
    }
}
