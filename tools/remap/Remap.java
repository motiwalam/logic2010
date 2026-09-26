import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.*;

/**
 * Deobfuscation remapper for the yGuard-obfuscated Logic 2010 jar.
 *
 * Every obfuscated identifier gets a stable, globally unique placeholder id
 * (classes: C_xx, fields: fNNN, methods: mNNN). Methods that override each
 * other share one id. A mapping file can then give any id a real name:
 *
 *   class bB MainWindow        # obfuscated class name (relative to base package) -> new name
 *   m123  parseFormula         # method id -> new name
 *   f45   currentUser          # field id  -> new name
 *
 * Usage: java -cp asm*.jar Remap.java in.jar out.jar ids.txt [mapping files...]
 *   ids.txt is written: a listing of every id with owner and descriptor, for reference.
 */
public class Remap {
    static final String BASE = "edu/ucla/phil/logic/";
    static final Pattern OBF_MEMBER = Pattern.compile("[A-Za-z_$]|.*[^\\x00-\\x7f].*");
    static final Pattern OBF_CLASS = Pattern.compile("[A-Za-z_$]{1,3}");

    static Map<String, ClassNode> classes = new TreeMap<>();
    static Map<String, byte[]> resources = new LinkedHashMap<>();
    static Map<String, ClassNode> externalCache = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Path in = Paths.get(args[0]), out = Paths.get(args[1]), idsOut = Paths.get(args[2]);
        List<Path> mappingFiles = new ArrayList<>();
        for (int i = 3; i < args.length; i++) mappingFiles.add(Paths.get(args[i]));

        readJar(in);

        // ---- class names ----
        Map<String, String> classMap = new TreeMap<>();
        for (String name : classes.keySet()) classMap.put(name, defaultClassName(name));

        // ---- method groups (overrides share a name) ----
        UnionFind uf = new UnionFind();
        for (ClassNode cn : classes.values()) {
            Set<String> ancestors = new LinkedHashSet<>();
            collectAncestors(cn.name, ancestors);
            // index declarations by name+desc across the class and all its in-jar ancestors
            Map<String, List<String>> decls = new HashMap<>();
            for (String a : ancestors) {
                ClassNode an = classes.get(a);
                if (an == null) continue;
                for (MethodNode mn : an.methods) {
                    if (mn.name.startsWith("<")) continue;
                    decls.computeIfAbsent(mn.name + mn.desc, k -> new ArrayList<>()).add(a + "." + mn.name + mn.desc);
                }
            }
            for (List<String> l : decls.values())
                for (int i = 1; i < l.size(); i++) uf.union(l.get(0), l.get(i));
        }

        // a group is renamable only if no member overrides something outside the jar
        Set<String> frozenRoots = new HashSet<>();
        for (ClassNode cn : classes.values()) {
            Set<String> ancestors = new LinkedHashSet<>();
            collectAncestors(cn.name, ancestors);
            for (MethodNode mn : cn.methods) {
                String key = cn.name + "." + mn.name + mn.desc;
                if (mn.name.startsWith("<") || !OBF_MEMBER.matcher(mn.name).matches()
                        || overridesExternal(ancestors, mn)) {
                    frozenRoots.add(uf.find(key));
                }
            }
        }

        // assign ids in deterministic order
        Map<String, String> methodIdByRoot = new HashMap<>();
        Map<String, String> fieldIdByKey = new HashMap<>();
        Map<String, List<String>> idDescriptions = new LinkedHashMap<>();
        int mCount = 0, fCount = 0;
        for (ClassNode cn : classes.values()) {
            for (FieldNode fn : cn.fields) {
                if (!OBF_MEMBER.matcher(fn.name).matches()) continue;
                String id = "f" + (++fCount);
                fieldIdByKey.put(cn.name + "." + fn.name + ":" + fn.desc, id);
                idDescriptions.put(id, new ArrayList<>(List.of(cn.name + "." + fn.name + " " + fn.desc)));
            }
            for (MethodNode mn : cn.methods) {
                String key = cn.name + "." + mn.name + mn.desc;
                String root = uf.find(key);
                if (frozenRoots.contains(root)) continue;
                String id = methodIdByRoot.get(root);
                if (id == null) {
                    id = "m" + (++mCount);
                    methodIdByRoot.put(root, id);
                    idDescriptions.put(id, new ArrayList<>());
                }
                idDescriptions.get(id).add(cn.name + "." + mn.name + mn.desc);
            }
        }

        // ---- apply mapping files ----
        Map<String, String> idNames = new HashMap<>();
        for (Path mp : mappingFiles) {
            int lineNo = 0;
            for (String line : Files.readAllLines(mp, StandardCharsets.UTF_8)) {
                lineNo++;
                int hash = line.indexOf('#');
                if (hash >= 0) line = line.substring(0, hash);
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split("\\s+");
                String where = mp + ":" + lineNo;
                if (p[0].equals("class")) {
                    if (p.length != 3) die(where + ": expected 'class OLD NEW'");
                    String old = p[1].startsWith("edu/") ? p[1] : BASE + p[1];
                    if (!classes.containsKey(old)) die(where + ": unknown class " + old);
                    classMap.put(old, qualifyNew(old, p[2]));
                } else {
                    if (p.length != 2) die(where + ": expected 'ID NEWNAME'");
                    if (!idDescriptions.containsKey(p[0])) die(where + ": unknown id " + p[0]);
                    if (!p[1].matches("[A-Za-z_$][A-Za-z0-9_$]*")) die(where + ": bad identifier " + p[1]);
                    if (idNames.put(p[0], p[1]) != null) die(where + ": duplicate entry for " + p[0]);
                }
            }
        }
        // inner classes follow their (possibly renamed) outer class
        for (String name : classes.keySet()) {
            String mapped = classMap.get(name);
            int d = name.lastIndexOf('$');
            if (d > 0 && classes.containsKey(name.substring(0, d))) {
                String outerNew = classMap.get(name.substring(0, d));
                String simpleNew = mapped.substring(mapped.lastIndexOf('/') + 1);
                String innerNew = simpleNew.substring(simpleNew.lastIndexOf('$') + 1);
                classMap.put(name, outerNew + "$" + innerNew);
            }
        }
        // resolve nested inner classes (A$B$C) - repeat until stable
        for (boolean changed = true; changed; ) {
            changed = false;
            for (String name : classes.keySet()) {
                int d = name.lastIndexOf('$');
                if (d > 0 && classes.containsKey(name.substring(0, d))) {
                    String want = classMap.get(name.substring(0, d)) + classMap.get(name).substring(classMap.get(name).lastIndexOf('$'));
                    if (!want.equals(classMap.get(name))) { classMap.put(name, want); changed = true; }
                }
            }
        }
        checkUnique(classMap);

        Map<String, String> memberMap = new HashMap<>(); // "owner.name+desc" or "owner.name:desc" -> new name
        for (Map.Entry<String, String> e : fieldIdByKey.entrySet())
            memberMap.put(e.getKey(), idNames.getOrDefault(e.getValue(), e.getValue()));
        for (ClassNode cn : classes.values())
            for (MethodNode mn : cn.methods) {
                String key = cn.name + "." + mn.name + mn.desc;
                String id = methodIdByRoot.get(uf.find(key));
                if (id != null) memberMap.put(key, idNames.getOrDefault(id, id));
            }

        validate(memberMap);

        // ---- write ids listing ----
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(idsOut, StandardCharsets.UTF_8))) {
            pw.println("# Generated by tools/remap. id -> original owner.name descriptor (original obfuscated names)");
            for (Map.Entry<String, List<String>> e : idDescriptions.entrySet()) {
                String nm = idNames.get(e.getKey());
                for (String d : e.getValue())
                    pw.println(e.getKey() + (nm != null ? "=" + nm : "") + "\t" + d);
            }
            pw.println("# classes");
            for (Map.Entry<String, String> e : classMap.entrySet())
                if (!e.getKey().equals(e.getValue())) pw.println(e.getKey() + "\t" + e.getValue());
        }

        // ---- remap and write ----
        Remapper remapper = new Remapper() {
            @Override public String map(String internalName) {
                return classMap.getOrDefault(internalName, internalName);
            }
            @Override public String mapMethodName(String owner, String name, String desc) {
                String r = resolveMethod(owner, name, desc, memberMap);
                return r != null ? r : name;
            }
            @Override public String mapFieldName(String owner, String name, String desc) {
                String r = resolveField(owner, name, desc, memberMap);
                return r != null ? r : name;
            }
            @Override public String mapInnerClassName(String name, String ownerName, String innerName) {
                String m = map(name);
                if (innerName == null) return null;
                int d = m.lastIndexOf('$');
                return d >= 0 ? m.substring(d + 1) : innerName;
            }
        };
        try (JarOutputStream jos = new JarOutputStream(Files.newOutputStream(out))) {
            for (Map.Entry<String, byte[]> r : resources.entrySet()) {
                jos.putNextEntry(new ZipEntry(r.getKey()));
                byte[] data = r.getValue();
                if (r.getKey().equals("META-INF/MANIFEST.MF")) data = cleanManifest(data);
                jos.write(data);
                jos.closeEntry();
            }
            for (ClassNode cn : classes.values()) {
                ClassWriter cw = new ClassWriter(0);
                cn.accept(new ClassRemapper(cw, remapper));
                jos.putNextEntry(new ZipEntry(classMap.get(cn.name) + ".class"));
                jos.write(cw.toByteArray());
                jos.closeEntry();
            }
        }
        System.err.println("remapped " + classes.size() + " classes; " + fCount + " field ids, " + mCount
                + " method ids; " + idNames.size() + " names from mappings");
    }

    // yGuard writes a per-entry digest section in the manifest; strip it, keep Main-Class.
    static byte[] cleanManifest(byte[] data) {
        String s = new String(data, StandardCharsets.UTF_8);
        int i = s.indexOf("\r\n\r\n");
        if (i < 0) i = s.indexOf("\n\n");
        if (i >= 0) s = s.substring(0, i) + "\n\n";
        return s.getBytes(StandardCharsets.UTF_8);
    }

    static String defaultClassName(String name) {
        if (!name.startsWith(BASE)) return name;
        String rest = name.substring(BASE.length());
        String[] parts = rest.split("/");
        StringBuilder sb = new StringBuilder(BASE);
        for (int i = 0; i < parts.length - 1; i++) sb.append("pkg").append(parts[i]).append('/');
        String simple = parts[parts.length - 1];
        String[] nest = simple.split("\\$", -1);
        // handle names starting with '$' (e.g. "$E", "$E$1")
        List<String> segs = new ArrayList<>();
        for (int i = 0; i < nest.length; i++) {
            if (nest[i].isEmpty() && i + 1 < nest.length) { nest[i + 1] = "$" + nest[i + 1]; continue; }
            segs.add(nest[i]);
        }
        for (int i = 0; i < segs.size(); i++) {
            String s = segs.get(i);
            if (i > 0) sb.append('$');
            if (s.matches("\\d+")) sb.append(s);
            else if (OBF_CLASS.matcher(s).matches()) sb.append(i > 1 ? "C" + i + "_" : "C_").append(s.replace("$", "0").replaceAll("([a-z])", "$1_"));
            else sb.append(s);
        }
        return sb.toString();
    }

    static String qualifyNew(String old, String nw) {
        if (nw.contains("/")) return nw;
        if (nw.contains(".")) return BASE + nw.replace('.', '/');
        // keep the (renamed) package of the old class
        String def = defaultClassName(old);
        return def.substring(0, def.lastIndexOf('/') + 1) + nw;
    }

    static void checkUnique(Map<String, String> classMap) {
        Map<String, String> seen = new HashMap<>();
        for (Map.Entry<String, String> e : classMap.entrySet()) {
            String prev = seen.put(e.getValue().toLowerCase(Locale.ROOT), e.getKey());
            if (prev != null) die("class name collision (case-insensitive): " + prev + " and " + e.getKey() + " -> " + e.getValue());
        }
    }

    /** Refuse mappings that would create accidental overrides or duplicate members. */
    static void validate(Map<String, String> memberMap) {
        for (ClassNode cn : classes.values()) {
            Set<String> fieldNames = new HashSet<>();
            for (FieldNode fn : cn.fields) {
                String nn = memberMap.getOrDefault(cn.name + "." + fn.name + ":" + fn.desc, fn.name);
                if (!fieldNames.add(nn)) die("duplicate field name " + nn + " in " + cn.name);
            }
            Set<String> ancestors = new LinkedHashSet<>();
            collectAncestors(cn.name, ancestors);
            Map<String, String> finalSigs = new HashMap<>(); // newName+argdesc -> origin key
            for (String a : ancestors) {
                ClassNode an = classes.get(a);
                if (an == null) continue;
                for (MethodNode mn : an.methods) {
                    if (mn.name.startsWith("<")) continue;
                    if (a != cn.name && (mn.access & Opcodes.ACC_PRIVATE) != 0) continue;
                    String key = a + "." + mn.name + mn.desc;
                    String nn = memberMap.getOrDefault(key, mn.name);
                    String sig = nn + mn.desc.substring(0, mn.desc.indexOf(')') + 1);
                    String prevOrig = finalSigs.get(sig);
                    String orig = mn.name + mn.desc;
                    if (prevOrig != null && !prevOrig.equals(orig)) {
                        // same new name+args but originally different methods: would (re)bind overrides/overloads
                        if (!(prevOrig.substring(0, prevOrig.indexOf('(')).equals(mn.name) && sameArgs(prevOrig, orig)))
                            die("mapping conflict in hierarchy of " + cn.name + ": " + sig + " from " + prevOrig + " and " + a + "." + orig);
                    }
                    if (prevOrig == null) finalSigs.put(sig, orig);
                }
            }
        }
    }

    static boolean sameArgs(String a, String b) {
        return a.substring(a.indexOf('('), a.indexOf(')')).equals(b.substring(b.indexOf('('), b.indexOf(')')));
    }

    static String resolveMethod(String owner, String name, String desc, Map<String, String> memberMap) {
        Set<String> anc = new LinkedHashSet<>();
        collectAncestors(owner, anc);
        for (String a : anc) {
            String r = memberMap.get(a + "." + name + desc);
            if (r != null) return r;
        }
        return null;
    }

    static String resolveField(String owner, String name, String desc, Map<String, String> memberMap) {
        Set<String> anc = new LinkedHashSet<>();
        collectAncestors(owner, anc);
        for (String a : anc) {
            ClassNode cn = classes.get(a);
            if (cn == null) continue;
            for (FieldNode fn : cn.fields)
                if (fn.name.equals(name) && fn.desc.equals(desc))
                    return memberMap.get(a + "." + name + ":" + desc);
        }
        return null;
    }

    static void collectAncestors(String name, Set<String> out) {
        if (name == null || !out.add(name)) return;
        ClassNode cn = classes.get(name);
        if (cn == null) return;
        collectAncestors(cn.superName, out);
        for (String i : cn.interfaces) collectAncestors(i, out);
    }

    static boolean overridesExternal(Set<String> ancestors, MethodNode mn) {
        if ((mn.access & (Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE)) != 0) return false;
        String args = mn.desc.substring(0, mn.desc.indexOf(')') + 1);
        for (String a : ancestors) {
            if (classes.containsKey(a)) continue;
            for (String ext : externalHierarchy(a)) {
                ClassNode en = external(ext);
                if (en == null) continue;
                for (MethodNode em : en.methods)
                    if (em.name.equals(mn.name) && em.desc.startsWith(args)) return true;
            }
        }
        return false;
    }

    static List<String> externalHierarchy(String name) {
        List<String> l = new ArrayList<>();
        Deque<String> q = new ArrayDeque<>(List.of(name));
        while (!q.isEmpty()) {
            String n = q.pop();
            if (l.contains(n)) continue;
            l.add(n);
            ClassNode en = external(n);
            if (en == null) continue;
            if (en.superName != null) q.add(en.superName);
            q.addAll(en.interfaces);
        }
        return l;
    }

    static ClassNode external(String name) {
        return externalCache.computeIfAbsent(name, n -> {
            try (InputStream is = ClassLoader.getSystemResourceAsStream(n + ".class")) {
                if (is == null) { System.err.println("warning: external class not found: " + n); return null; }
                ClassNode cn = new ClassNode();
                new ClassReader(is).accept(cn, ClassReader.SKIP_CODE);
                return cn;
            } catch (IOException e) { throw new UncheckedIOException(e); }
        });
    }

    static void readJar(Path in) throws IOException {
        try (JarInputStream jis = new JarInputStream(Files.newInputStream(in), false)) {
            // JarInputStream hides the manifest; re-read with JarFile below
        }
        try (JarFile jf = new JarFile(in.toFile(), false)) {
            List<JarEntry> entries = Collections.list(jf.entries());
            for (JarEntry e : entries) {
                if (e.isDirectory()) continue;
                byte[] data = jf.getInputStream(e).readAllBytes();
                if (e.getName().endsWith(".class")) {
                    ClassNode cn = new ClassNode();
                    new ClassReader(data).accept(cn, 0);
                    classes.put(cn.name, cn);
                } else {
                    resources.put(e.getName(), data);
                }
            }
        }
    }

    static void die(String msg) {
        System.err.println("error: " + msg);
        System.exit(1);
    }

    static class UnionFind {
        Map<String, String> parent = new HashMap<>();
        String find(String x) {
            String p = parent.getOrDefault(x, x);
            if (p.equals(x)) return x;
            String r = find(p);
            parent.put(x, r);
            return r;
        }
        void union(String a, String b) {
            String ra = find(a), rb = find(b);
            if (ra.equals(rb)) return;
            // deterministic: smaller string becomes root
            if (ra.compareTo(rb) < 0) parent.put(rb, ra); else parent.put(ra, rb);
        }
    }
}
