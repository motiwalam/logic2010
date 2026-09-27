package edu.ucla.phil.logic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Verifies a data conversion (tools/convert-data.py): for every legacy file in LEGACY_DIR,
 * reads the converted file in NEW_DIR through the program's own DataFiles reader and
 * checks that the program sees the same data.
 *
 * Compared: heading lines, and every record's (tag, value) sequence as parsed by the
 * program's TaggedRecord. Not compared: '#' comments (ignored by the program). Allowed
 * differences, all invisible to the program: message values are trimmed (Message trims
 * every field it reads); consecutive option records of the same section are merged
 * (every option reader loops over all fields of a record); the tips outline is one record
 * (it is read with TaggedRecord(reader), which concatenates all lines); rule/theorem
 * bodies are trimmed (the formula and rule-list parsers ignore blanks); lines without any
 * tagged field are dropped (no reader uses a record without fields).
 *
 * Usage (from the reverse-engineering tree, with a build of the main tree):
 *   javac -cp MAIN/build/logic.jar -d /tmp/check tools/dataformat/CheckDataConversion.java
 *   java -cp MAIN/build/logic.jar:/tmp/check edu.ucla.phil.logic.CheckDataConversion LEGACY_DIR NEW_DIR
 */
public class CheckDataConversion {
   static final String KEY = "the Logic Program is protected by international copyright law";
   static final String[][] KEYS = {   // legacy file -> link key (selects the record schema)
      {"wraith.txt", "options"}, {"imp.txt", "tips"}, {"spectre.txt", "messages"},
      {"zombie.txt", "derMessages"}, {"tomb.txt", "invMessages"}, {"shade.txt", "parMessages"},
      {"demon.txt", "symMessages"}, {"crypt.txt", "truMessages"}, {"troll.txt", "recMessages"},
      {"ghoul.txt", "derwork.txt"}, {"werewolf.txt", "invwork.txt"}, {"vampire.txt", "parwork.txt"},
      {"devil.txt", "symwork.txt"}, {"mummy.txt", "symAnswers"}, {"warlock.txt", "truwork.txt"},
      {"goblin.txt", "recwork.txt"}, {"banshee.txt", "rules"}, {"fiend.txt", "theorems"},
      {"spirit.txt", null}, {"ghost.txt", null}
   };
   static int failures = 0, checked = 0;

   public static void main(String[] args) throws IOException {
      LogicProgram.scrambleKey = KEY;
      walk(new File(args[0]), new File(args[1]));
      System.out.println(failures == 0 ? "OK: " + checked + " files convert without loss"
            : failures + " problem(s) in " + checked + " files");
      System.exit(failures == 0 ? 0 : 1);
   }

   static void walk(File legacyDir, File newDir) throws IOException {
      File[] files = legacyDir.listFiles();
      if (files == null) return;
      for (File f : files) {
         if (f.isDirectory()) {
            if (!f.getName().equals("work")) walk(f, new File(newDir, f.getName()));
            continue;
         }
         for (String[] k : KEYS) {
            if (k[0].equals(f.getName())) check(f, newDir, k[1]);
         }
      }
   }

   static void check(File legacy, File newDir, String key) throws IOException {
      String newName = null;
      for (String[] a : DataFiles.FILE_ALIASES) if (a[0].equals(legacy.getName())) newName = a[1];
      File converted = new File(newDir, newName);
      checked++;
      if (!converted.exists()) { fail(legacy, "missing " + converted); return; }
      List<String> oldLines = new ArrayList<>(), newLines = new ArrayList<>();
      ScrambledReader r = new ScrambledReader(new FileReader(legacy), KEY);
      for (String l; (l = r.readLine()) != null; ) oldLines.add(l);
      r.close();
      r = DataFiles.open(converted, key, true);
      for (String l; (l = r.readLine()) != null; ) newLines.add(l);
      r.close();
      String kind = legacy.getName();
      List<String> a, b;
      if (kind.equals("spirit.txt") || kind.equals("ghost.txt")) {
         a = conf(oldLines, true); b = conf(newLines, false);
      } else if (kind.equals("banshee.txt") || kind.equals("fiend.txt")) {
         a = list(oldLines); b = list(newLines);
      } else {
         String schema = DataFiles.schemaForKey(key);
         a = records(oldLines, schema); b = records(newLines, schema);
      }
      if (!a.equals(b)) {
         int i = 0;
         while (i < a.size() && i < b.size() && a.get(i).equals(b.get(i))) i++;
         fail(legacy, "differs at item " + i + ":\n    legacy:    " + (i < a.size() ? a.get(i) : "<end>")
               + "\n    converted: " + (i < b.size() ? b.get(i) : "<end>"));
      }
   }

   static List<String> records(List<String> lines, String schema) {
      List<String> out = new ArrayList<>();
      TaggedRecord all = new TaggedRecord();
      String lastSection = null;
      for (String l : lines) {
         if (l.startsWith("#-")) { out.add("heading " + l.substring(2)); lastSection = null; continue; }
         if (TaggedRecord.isBlankOrComment(l)) continue;
         TaggedRecord t = new TaggedRecord(l);
         if (t.getFieldCount() == 0) continue;   // no fields: ignored by every reader (the converter drops it)
         if (schema.equals("tips")) {
            for (int i = 0; i < t.getFieldCount(); i++) all.tags += t.tagAt(i);
            for (int i = 0; i < t.getFieldCount(); i++) all.values.addElement(t.valueAt(i));
            continue;
         }
         StringBuilder sb = new StringBuilder();
         int start = 0;
         if (schema.equals("options")) {
            String section = t.getName() == null ? null : t.getName().trim().toLowerCase();
            if (section != null && section.equals(lastSection)) {
               start = 1;                       // continues the previous record
               sb.append(out.remove(out.size() - 1));
            }
            lastSection = section;
         }
         for (int i = start; i < t.getFieldCount(); i++) {
            String v = t.valueAt(i);
            if (schema.equals("messages")) v = v.trim();
            sb.append(t.tagAt(i)).append('=').append(v).append('\u0001');
         }
         out.add(sb.toString());
      }
      if (schema.equals("tips")) {
         StringBuilder sb = new StringBuilder();
         for (int i = 0; i < all.getFieldCount(); i++) sb.append(all.tagAt(i)).append('=').append(all.valueAt(i)).append('\u0001');
         out.add(sb.toString());
      }
      return out;
   }

   static List<String> list(List<String> lines) {
      List<String> out = new ArrayList<>();
      for (String l : lines) {
         if (l.startsWith("#-")) { out.add("heading " + l.substring(2)); continue; }
         if (TaggedRecord.isBlankOrComment(l)) continue;
         String s = l.trim();
         int i = 0;
         while (i < s.length() && !Character.isWhitespace(s.charAt(i))) i++;
         out.add(s.substring(0, i) + " := " + s.substring(i).trim());
      }
      return out;
   }

   /** key -> value, with legacy file names in values mapped to their readable names. */
   static List<String> conf(List<String> lines, boolean legacy) {
      List<String> out = new ArrayList<>();
      for (String l : lines) {
         int c = l.indexOf(':');
         if (TaggedRecord.isBlankOrComment(l) || c == -1) continue;
         String k = l.substring(0, c).trim(), v = l.substring(c + 1).trim();
         if (legacy) {
            for (String[] a : DataFiles.FILE_ALIASES) {
               if (v.equals(a[0]) || v.endsWith("/" + a[0])) v = v.substring(0, v.length() - a[0].length()) + a[1];
            }
         }
         out.add(k.toUpperCase() + " = " + v);
      }
      return out;
   }

   static void fail(File f, String msg) {
      failures++;
      System.out.println("FAIL " + f + ": " + msg);
   }
}
