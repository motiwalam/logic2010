package edu.ucla.phil.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Reads the program's data files (problems, answers, rules, theorems, messages, options,
 * tips, links and version info).
 *
 * The data files are plain text in three readable formats (see data/README.md):
 *
 *   *.rec   record files: "field: value" lines; records are separated by blank lines
 *           (or "## heading" lines); "# comment" lines are ignored
 *   *.list  rule/theorem lists: "NAME  BODY" lines, plus headings and comments
 *   *.conf  "key: value" settings (version.conf, links.conf)
 *
 * Internally the program works with "tagged record" lines, where every field is written
 * as value`t with a one-character tag t (see TaggedRecord). This class translates the
 * readable files into that line form, so the rest of the program is unchanged: open()
 * returns a reader that yields exactly the lines the program expects.
 *
 * The older scrambled files (spirit.txt, ghost.txt, ghoul.txt, ...) are still understood:
 * in normal (server) mode the course server can send course files in that format. When
 * both a readable file and its legacy counterpart exist, the newer one is used.
 *
 * The student's work (work/derivation.rec, ...) is saved as record files too (writeWork).
 * Work in the older format (work/derwork.txt, ...) is still read, and replaced by the
 * readable file when it is next saved; server backups carry the older format.
 */
final class DataFiles {
   static final String VERSION_FILE = "version.conf";
   static final String LINKS_FILE = "links.conf";

   /** Legacy file name and its readable replacement, relative to the same directory. */
   static final String[][] FILE_ALIASES = {
      {"spirit.txt", "version.conf"},
      {"ghost.txt", "links.conf"},
      {"wraith.txt", "options.rec"},
      {"imp.txt", "derivation-tips.rec"},
      {"spectre.txt", "messages/general.rec"},
      {"zombie.txt", "messages/derivation.rec"},
      {"tomb.txt", "messages/invalidity.rec"},
      {"shade.txt", "messages/parsing.rec"},
      {"demon.txt", "messages/symbolization.rec"},
      {"crypt.txt", "messages/truth-tables.rec"},
      {"troll.txt", "messages/recognition.rec"},
      {"ghoul.txt", "derivation-problems.rec"},
      {"werewolf.txt", "invalidity-problems.rec"},
      {"vampire.txt", "parsing-problems.rec"},
      {"devil.txt", "symbolization-problems.rec"},
      {"mummy.txt", "symbolization-answers.rec"},
      {"warlock.txt", "truth-table-problems.rec"},
      {"goblin.txt", "recognition-problems.rec"},
      {"banshee.txt", "rules.list"},
      {"fiend.txt", "theorems.list"}
   };

   /**
    * The student's work files in the work directory: the program's internal name (the
    * name the files had in the older format) and the readable file that replaces it.
    */
   static final String[][] WORK_FILES = {
      {"derwork.txt", "derivation.rec", "Derivation work"},
      {"invwork.txt", "invalidity.rec", "Invalidity work"},
      {"parwork.txt", "parsing.rec", "Parsing work"},
      {"recwork.txt", "recognition.rec", "Rule-recognition work"},
      {"symwork.txt", "symbolization.rec", "Symbolization work"},
      {"truwork.txt", "truth-tables.rec", "Truth-table work"},
      {"keywork.txt", "symbolization-answers.rec", "Answer keys for your own symbolization problems"}
   };

   /** Last line of a work file: the digest the program checks the records against. */
   static final String DIGEST_COMMENT = "# digest:";

   /** Readable link names used in links.conf, and the program's internal link keys. */
   static final String[][] LINK_ALIASES = {
      {"derivation-problems", "derwork.txt"},
      {"invalidity-problems", "invwork.txt"},
      {"parsing-problems", "parwork.txt"},
      {"symbolization-problems", "symwork.txt"},
      {"truth-table-problems", "truwork.txt"},
      {"recognition-problems", "recwork.txt"},
      {"symbolization-answers", "symAnswers"},
      {"derivation-messages", "derMessages"},
      {"invalidity-messages", "invMessages"},
      {"parsing-messages", "parMessages"},
      {"symbolization-messages", "symMessages"},
      {"truth-table-messages", "truMessages"},
      {"recognition-messages", "recMessages"},
      {"derivation-tips", "tips"}
   };

   /** Which record schema the data behind each internal link key uses. */
   static final String[][] KEY_SCHEMAS = {
      {"derwork.txt", "derivation-problems"},
      {"invwork.txt", "invalidity-problems"},
      {"parwork.txt", "parsing-problems"},
      {"symwork.txt", "symbolization-problems"},
      {"truwork.txt", "truth-table-problems"},
      {"recwork.txt", "recognition-problems"},
      {"keywork.txt", "symbolization-answers"},
      {"symAnswers", "symbolization-answers"},
      {"messages", "messages"},
      {"derMessages", "messages"},
      {"invMessages", "messages"},
      {"parMessages", "messages"},
      {"symMessages", "messages"},
      {"truMessages", "messages"},
      {"recMessages", "messages"},
      {"options", "options"},
      {"tips", "tips"}
   };

   // Field names of each record schema: "<tag> <name> [<more accepted names>]".
   // The first name is the one the converter writes; every listed name is accepted.
   static final String[] COMMON_PROBLEM_FIELDS = {
      "$ problem", "% options", "! note", "C common-name", "o original-name", "e errors", "t seconds"
   };
   static final Object[][] SCHEMAS = {
      {"derivation-problems", new String[]{
         "- statement show", "+ collapsed-show", "< line", "> reason", "# cancel", "= end-box",
         ": cached-justification", "s command", "m message", "? flag", "p proves"}},
      {"invalidity-problems", new String[]{
         "? argument", "# universe-size", "= interpretation", "& workspace"}},
      {"parsing-problems", new String[]{
         "= formula", "[ notation", "] expansion", "* main-connective-answer"}},
      {"symbolization-problems", new String[]{
         "- english", "+ node", "= scheme", "@ answer-keys", "g answer-group", "h hints"}},
      {"symbolization-answers", new String[]{
         "$ answer-key", "- english", "+ node", "e errors", "t seconds"}},
      {"truth-table-problems", new String[]{
         "= statement", "@ row", "* answer", "# counterexample-row", "& setup"}},
      {"recognition-problems", new String[]{
         "= argument", "* answer", "@ correct-rules", "~ near-miss-rules", "& comment"}},
      {"messages", new String[]{
         "s sort", "n id", "e error", "E error-revised", "i info", "I info-revised", "x text",
         "d description", "p programmer-note", "b buttons"}},
      {"tips", new String[]{
         "$ text", "+ entry", "- expanded-entry", "= end"}},
      {"options", new String[]{
         "$ section", "+ set", "- unset", "? option"}},
      {"options/logic", new String[]{
         "u login", "f font-size", "c backup-count", "b backup-name", "r restore-name",
         "p instance-port", "o option-o"}},
      {"options/derivation", new String[]{
         "d disable", "D disable-all-forms", "m manual", "M manual-all-forms", "a assume-weakly",
         "A assume"}},
      {"options/recognition", new String[]{
         "a activate-rules"}}
   };

   /**
    * The fields that hold formulas, by schema: their quantifiers may be written as words
    * ("forall x", "exists x"; see QuantifierWords). A symbolization node holds a formula
    * part before its first ':' and English after it; only the formula part is converted.
    */
   static final String[][] FORMULA_FIELDS = {
      {"derivation-problems", "-+<"},
      {"invalidity-problems", "?"},
      {"parsing-problems", "="},
      {"recognition-problems", "="},
      {"truth-table-problems", "="},
      {"symbolization-problems", "+"},
      {"symbolization-answers", "+"}
   };

   static final Hashtable schemaTagToName = new Hashtable(); // "schema\u0000tag" -> name
   static final Hashtable schemaNameToTag = new Hashtable(); // "schema\u0000name" -> Character

   static {
      for (int i = 0; i < SCHEMAS.length; i++) {
         String schema = (String)SCHEMAS[i][0];
         if (schema.endsWith("-problems")) {
            defineFields(schema, COMMON_PROBLEM_FIELDS);
         }

         defineFields(schema, (String[])SCHEMAS[i][1]);
      }
   }

   static void defineFields(String schema, String[] fields) {
      for (int i = 0; i < fields.length; i++) {
         String[] parts = fields[i].split(" ");
         Character tag = new Character(parts[0].charAt(0));
         schemaTagToName.put(schema + "\u0000" + tag, parts[1]);

         for (int j = 1; j < parts.length; j++) {
            schemaNameToTag.put(schema + "\u0000" + parts[j], tag);
         }
      }
   }

   static boolean isFormulaField(String schema, char tag) {
      for (int i = 0; i < FORMULA_FIELDS.length; i++) {
         if (FORMULA_FIELDS[i][0].equals(schema)) {
            return FORMULA_FIELDS[i][1].indexOf(tag) != -1;
         }
      }

      return false;
   }

   /** A field's value with its quantifiers as words (toWords) or as the program's @ and ! symbols. */
   static String convertQuantifiers(String schema, char tag, String value, boolean toWords) {
      if (!isFormulaField(schema, tag)) {
         return value;
      } else if (schema.startsWith("symbolization")) {
         int i = value.indexOf(':');
         String s = i == -1 ? value : value.substring(0, i);
         s = toWords ? QuantifierWords.toWords(s) : QuantifierWords.toSymbols(s);
         return i == -1 ? s : s + value.substring(i);
      } else {
         return toWords ? QuantifierWords.toWords(value) : QuantifierWords.toSymbols(value);
      }
   }

   static String schemaForKey(String key) {
      for (int i = 0; i < KEY_SCHEMAS.length; i++) {
         if (KEY_SCHEMAS[i][0].equalsIgnoreCase(key)) {
            return KEY_SCHEMAS[i][1];
         }
      }

      return null;
   }

   /** The program's internal link key for a key found in links.conf. */
   static String internalLinkKey(String key) {
      for (int i = 0; i < LINK_ALIASES.length; i++) {
         if (LINK_ALIASES[i][0].equalsIgnoreCase(key)) {
            return LINK_ALIASES[i][1];
         }
      }

      return key;
   }

   /**
    * The file to read for a path: the path itself or its legacy/readable counterpart,
    * whichever exists (the newer one if both do). Returns the path itself if neither exists.
    */
   static File choose(File file) {
      String path = file.getPath().replace(File.separatorChar, '/');
      File best = file.exists() ? file : null;

      for (int i = 0; i < FILE_ALIASES.length; i++) {
         for (int j = 0; j < 2; j++) {
            String name = FILE_ALIASES[i][j];
            if (path.equals(name) || path.endsWith("/" + name)) {
               File other = new File(path.substring(0, path.length() - name.length()) + FILE_ALIASES[i][1 - j]);
               if (other.exists() && (best == null || other.lastModified() > best.lastModified())) {
                  best = other;
               }
            }
         }
      }

      return best != null ? best : file;
   }

   static boolean isReadableFormat(File file) {
      String name = file.getName();
      return name.endsWith(".rec") || name.endsWith(".list") || name.endsWith(".conf");
   }

   /**
    * Opens a data file for reading as tagged-record lines. key is the internal link key
    * (it selects the record schema), scrambled whether a legacy file is scrambled.
    */
   static ScrambledReader open(File file, String key, boolean scrambled) throws IOException {
      File chosen = choose(file);
      if (!isReadableFormat(chosen)) {
         return new ScrambledReader(new FileReader(chosen), scrambled ? LogicProgram.scrambleKey : null);
      } else {
         Vector lines = readLines(chosen);
         String text;
         if (chosen.getName().endsWith(".rec")) {
            text = recordsToTaggedLines(lines, schemaForKey(key), chosen.getPath());
         } else if (chosen.getName().endsWith(".list")) {
            text = listToLines(lines);
         } else {
            text = confToLines(lines);
         }

         return new ScrambledReader(new StringReader(text), null);
      }
   }

   static Vector readLines(File file) throws IOException {
      Vector lines = new Vector();
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

      try {
         String line;
         while ((line = reader.readLine()) != null) {
            lines.addElement(line);
         }
      } finally {
         reader.close();
      }

      return lines;
   }

   /** "## text" -> "#-text", "# text" -> "#text": headings and comments as the program writes them. */
   static String commentLine(String line) {
      if (line.startsWith("##")) {
         return "#-" + stripOneSpace(line.substring(2));
      } else {
         return "#" + stripOneSpace(line.substring(1));
      }
   }

   static String stripOneSpace(String s) {
      return s.startsWith(" ") ? s.substring(1) : s;
   }

   /** Converts a .rec file to tagged-record lines, one line per record. */
   static String recordsToTaggedLines(Vector lines, String schema, String fileName) {
      StringBuffer out = new StringBuffer();
      StringBuffer record = null;
      String section = null;

      for (int n = 0; n < lines.size(); n++) {
         String line = (String)lines.elementAt(n);
         String trimmed = line.trim();
         if (trimmed.equals("") || line.startsWith("##")) {
            // blank lines and headings end a record
            if (record != null) {
               endRecord(out, record);
               record = null;
            }

            if (line.startsWith("#")) {
               out.append(commentLine(line)).append('\n');
            }
         } else if (line.startsWith(DIGEST_COMMENT)) {
            // the program reads the digest from the last "#" line of a work file
            out.append("# ").append(line.substring(DIGEST_COMMENT.length()).trim()).append('\n');
         } else if (line.startsWith("#")) {
            out.append(commentLine(line)).append('\n');
         } else {
            int colon = trimmed.indexOf(':');
            if (colon <= 0) {
               warn(fileName, n + 1, "expected \"field: value\"");
            } else {
               String name = trimmed.substring(0, colon).trim();
               String value = parseValue(trimmed.substring(colon + 1).trim(), fileName, n + 1);
               if (record == null) {
                  record = new StringBuffer();
                  section = null;
               }

               if (schema != null && schema.equals("options") && name.equals("section")) {
                  section = value.trim().toLowerCase();
               }

               char tag = tagFor(schema, section, name);
               if (tag == 0) {
                  warn(fileName, n + 1, "unknown field \"" + name + "\"");
               } else {
                  record.append(escapeValue(convertQuantifiers(schema, tag, value, false))).append('`').append(tag);
               }
            }
         }
      }

      if (record != null) {
         endRecord(out, record);
      }

      return out.toString();
   }

   static void endRecord(StringBuffer out, StringBuffer record) {
      // A line starting with a backquote loses that character when parsed, and one
      // starting with '#' is a comment: protect both with a leading backquote.
      if (record.length() > 0 && (record.charAt(0) == '`' || record.charAt(0) == '#')) {
         out.append('`');
      }

      out.append(record).append('\n');
   }

   static char tagFor(String schema, String section, String name) {
      if (name.startsWith("tag-")) {
         String t = name.substring(4);
         if (t.equals("space")) {
            return ' ';
         } else if (t.equals("colon")) {
            return ':';
         }

         return t.length() == 1 ? t.charAt(0) : 0;
      } else if (schema == null) {
         return 0;
      } else {
         Character tag = null;
         if (section != null) {
            tag = (Character)schemaNameToTag.get(schema + "/" + section + "\u0000" + name);
         }

         if (tag == null) {
            tag = (Character)schemaNameToTag.get(schema + "\u0000" + name);
         }

         return tag == null ? 0 : tag.charValue();
      }
   }

   /** A value is either raw text, or a JSON-style "quoted string" (used when it has surrounding blanks). */
   static String parseValue(String s, String fileName, int lineNo) {
      if (!s.startsWith("\"")) {
         return s;
      } else if (s.length() < 2 || !s.endsWith("\"")) {
         warn(fileName, lineNo, "unterminated quoted value");
         return s;
      } else {
         StringBuffer out = new StringBuffer();

         for (int i = 1; i < s.length() - 1; i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < s.length() - 1) {
               char e = s.charAt(++i);
               switch (e) {
                  case 'b': out.append('\b'); break;
                  case 'f': out.append('\f'); break;
                  case 'n': out.append('\n'); break;
                  case 'r': out.append('\r'); break;
                  case 't': out.append('\t'); break;
                  case 'u':
                     if (i + 4 <= s.length() - 2) {
                        out.append((char)Integer.parseInt(s.substring(i + 1, i + 5), 16));
                        i += 4;
                     }
                     break;
                  default: out.append(e);
               }
            } else {
               out.append(c);
            }
         }

         return out.toString();
      }
   }

   static String escapeValue(String value) {
      StringBuffer out = new StringBuffer();

      for (int i = 0; i < value.length(); i++) {
         char c = value.charAt(i);
         out.append(c);
         if (c == '`') {
            out.append('`');
         }
      }

      return out.toString();
   }

   /** Rule and theorem lists: "NAME  BODY" lines become "NAME\tBODY". */
   static String listToLines(Vector lines) {
      StringBuffer out = new StringBuffer();

      for (int n = 0; n < lines.size(); n++) {
         String line = (String)lines.elementAt(n);
         if (line.startsWith("#")) {
            out.append(commentLine(line)).append('\n');
         } else if (!line.trim().equals("")) {
            String trimmed = line.trim();
            int i = 0;
            while (i < trimmed.length() && !Character.isWhitespace(trimmed.charAt(i))) {
               i++;
            }

            out.append(trimmed.substring(0, i)).append('\t').append(QuantifierWords.toSymbols(trimmed.substring(i).trim())).append('\n');
         }
      }

      return out.toString();
   }

   /** "key: value" settings; readable link names are translated to the internal keys. */
   static String confToLines(Vector lines) {
      StringBuffer out = new StringBuffer();

      for (int n = 0; n < lines.size(); n++) {
         String line = (String)lines.elementAt(n);
         int colon = line.indexOf(':');
         if (line.startsWith("#") || colon == -1) {
            out.append(line).append('\n');
         } else {
            out.append(internalLinkKey(line.substring(0, colon).trim())).append(':').append(line.substring(colon + 1)).append('\n');
         }
      }

      return out.toString();
   }

   /** The readable name of a work file, given its internal name; null if it is not a work file. */
   static String workFileName(String internalName) {
      for (int i = 0; i < WORK_FILES.length; i++) {
         if (WORK_FILES[i][0].equalsIgnoreCase(internalName)) {
            return WORK_FILES[i][1];
         }
      }

      return null;
   }

   /** Whether dir holds a work file in the older format, which is converted when next saved. */
   static boolean hasLegacyWork(File dir, String internalName) {
      return workFileName(internalName) != null && new File(dir, internalName).isFile();
   }

   /** Whether dir holds the work file, in either format. */
   static boolean hasWork(File dir, String internalName) {
      String name = workFileName(internalName);
      return new File(dir, internalName).isFile() || name != null && new File(dir, name).isFile();
   }

   /**
    * The work file to read: the readable one, or the older-format file if only it exists or
    * it is newer (a backup restored from the server is written in the older format).
    */
   static File chooseWork(File dir, String internalName) {
      File legacy = new File(dir, internalName);
      String name = workFileName(internalName);
      File readable = name == null ? null : new File(dir, name);
      if (readable != null && readable.isFile() && (!legacy.isFile() || readable.lastModified() >= legacy.lastModified())) {
         return readable;
      } else {
         return legacy.isFile() ? legacy : null;
      }
   }

   /** Opens a work file for reading as tagged-record lines; null if there is none. */
   static PlainRecordReader openWork(File dir, String internalName) throws IOException {
      File file = chooseWork(dir, internalName);
      if (file == null) {
         return null;
      } else if (!isReadableFormat(file)) {
         return new PlainRecordReader(new FileReader(file));
      } else {
         return new PlainRecordReader(new StringReader(recordsToTaggedLines(readLines(file), schemaForKey(internalName), file.getPath())));
      }
   }

   /** A work file's contents in the older format (the format of server backups), or null if there is none. */
   static byte[] legacyWorkBytes(File dir, String internalName) throws IOException {
      File file = chooseWork(dir, internalName);
      if (file == null) {
         return null;
      } else if (!isReadableFormat(file)) {
         return Files.readAllBytes(file.toPath());
      } else {
         Vector lines = readLines(file);
         String digest = null;
         StringBuffer out = new StringBuffer();
         String[] records = recordsToTaggedLines(lines, schemaForKey(internalName), file.getPath()).split("\n");

         for (int i = 0; i < lines.size(); i++) {
            String line = (String)lines.elementAt(i);
            if (line.startsWith(DIGEST_COMMENT)) {
               digest = line.substring(DIGEST_COMMENT.length()).trim();
            }
         }

         for (int i = 0; i < records.length; i++) {
            if (!records[i].equals("") && !records[i].startsWith("#")) {
               out.append(records[i]).append(LINE_SEPARATOR);
            }
         }

         if (digest != null) {
            out.append("# ").append(digest).append(LINE_SEPARATOR);
         }

         return out.toString().getBytes();
      }
   }

   static final String LINE_SEPARATOR = System.getProperty("line.separator");

   /**
    * The records to save, each exactly as it will read back from its readable form. The
    * digest is computed over these, so it matches the records the program reads next time.
    * Records without fields are left out (there is nothing to write for them).
    */
   static Vector canonicalRecords(Vector records) {
      return canonicalRecords(records, null);
   }

   static Vector canonicalRecords(Vector records, String schema) {
      Vector out = new Vector();

      for (int i = 0; i < records.size(); i++) {
         TaggedRecord t = new TaggedRecord((String)records.elementAt(i));
         if (t.getFieldCount() != 0) {
            StringBuffer fields = new StringBuffer();

            for (int j = 0; j < t.getFieldCount(); j++) {
               String value = t.valueAt(j);
               if (schema != null) {
                  value = convertQuantifiers(schema, t.tagAt(j), convertQuantifiers(schema, t.tagAt(j), value, true), false);
               }

               fields.append(escapeValue(value)).append('`').append(t.tagAt(j));
            }

            StringBuffer line = new StringBuffer();
            endRecord(line, fields);
            out.addElement(line.substring(0, line.length() - 1));
         }
      }

      return out;
   }

   /**
    * Writes a work file in the readable format: records (from canonicalRecords) as
    * "field: value" blocks, then the digest, if any. Replaces the older-format file.
    */
   static void writeWork(File dir, String internalName, Vector records, String digest) throws IOException {
      String name = workFileName(internalName);
      String schema = schemaForKey(internalName);
      String title = name;
      for (int i = 0; i < WORK_FILES.length; i++) {
         if (WORK_FILES[i][1].equals(name)) {
            title = WORK_FILES[i][2];
         }
      }

      StringBuffer out = new StringBuffer();
      out.append("# ").append(title).append(", saved by the program.\n");
      out.append("# Format: see data/README.md; the fields are those of the course's ").append(schema).append(".rec.\n");
      if (digest != null) {
         out.append("# The digest on the last line must match the records, or the program refuses the file.\n");
      }

      for (int i = 0; i < records.size(); i++) {
         out.append('\n');
         writeRecord(out, new TaggedRecord((String)records.elementAt(i)), schema);
      }

      if (digest != null) {
         out.append('\n').append(DIGEST_COMMENT).append(' ').append(digest).append('\n');
      }

      File file = new File(dir, name);
      File temp = new File(dir, name + ".tmp");
      Writer writer = new OutputStreamWriter(new FileOutputStream(temp), StandardCharsets.UTF_8);
      try {
         writer.write(out.toString());
      } finally {
         writer.close();
      }

      Files.move(temp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
      new File(dir, internalName).delete();
   }

   /**
    * One record as "field: value" lines. In a derivation, the lines of each Show line's box
    * are indented under it (indentation is ignored when the file is read).
    */
   static void writeRecord(StringBuffer out, TaggedRecord t, String schema) {
      boolean derivation = "derivation-problems".equals(schema);
      boolean firstShow = true;
      int depth = 0;
      int lineDepth = 0;

      for (int i = 0; i < t.getFieldCount(); i++) {
         char tag = t.tagAt(i);
         String name = (String)schemaTagToName.get(schema + "\u0000" + tag);
         if (name == null) {
            name = tag == ' ' ? "tag-space" : tag == ':' ? "tag-colon" : "tag-" + tag;
         }

         int indent = 0;
         if (derivation) {
            if (tag == '-' || tag == '+' || tag == '<') {
               lineDepth = depth;
               if (tag != '<') {
                  depth++;
               }
            } else if (tag == '#' || tag == '=') {
               depth = Math.max(depth - 1, 0);
               lineDepth = depth;
            }

            if (tag == '-') {
               name = firstShow ? "statement" : "show";
               firstShow = false;
            }

            // the problem's own fields stay at the left; a line's fields go with the line
            indent = "-+<#=>:sm?".indexOf(tag) != -1 ? lineDepth : 0;
         }

         for (int j = 0; j < indent; j++) {
            out.append("  ");
         }

         out.append(name).append(':').append(formatValue(convertQuantifiers(schema, tag, t.valueAt(i), true))).append('\n');
      }
   }

   /** " value", or " \"quoted\"" when the value's blanks or characters would not survive as raw text. */
   static String formatValue(String v) {
      if (v.equals("")) {
         return "";
      }

      boolean quote = !v.equals(v.trim()) || v.startsWith("\"");
      for (int i = 0; i < v.length() && !quote; i++) {
         quote = v.charAt(i) < ' ';
      }

      if (!quote) {
         return " " + v;
      }

      StringBuffer out = new StringBuffer(" \"");
      for (int i = 0; i < v.length(); i++) {
         char c = v.charAt(i);
         if (c == '"' || c == '\\') {
            out.append('\\').append(c);
         } else if (c == '\n') {
            out.append("\\n");
         } else if (c == '\r') {
            out.append("\\r");
         } else if (c == '\t') {
            out.append("\\t");
         } else if (c < ' ') {
            out.append(String.format("\\u%04x", (int)c));
         } else {
            out.append(c);
         }
      }

      return out.append('"').toString();
   }

   static void warn(String fileName, int lineNo, String message) {
      System.err.println("data file " + fileName + ":" + lineNo + ": " + message);
   }
}
