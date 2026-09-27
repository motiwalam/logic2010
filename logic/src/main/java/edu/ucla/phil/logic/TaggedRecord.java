package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Vector;

class TaggedRecord {
   String tags;
   Vector values;
   ScrambledReader reader;
   boolean clearOnRead;
   String rawLine;

   TaggedRecord(Reader readerx, boolean flag) {
      this.open(readerx, flag);
      if (!flag) {
         while (this.readNext()) {
         }
      }
   }

   TaggedRecord(Reader readerx) {
      this(readerx, false);
   }

   TaggedRecord() {
      this.clear();
   }

   TaggedRecord(String s) {
      this();
      this.parse(s);
   }

   boolean readNext() {
      if (this.reader == null) {
         return false;
      } else {
         if (this.clearOnRead) {
            this.tags = "";
            this.values = new Vector();
         }

         String s;
         do {
            try {
               if ((s = this.reader.readLine()) == null) {
                  return false;
               }
            } catch (IOException ioexception) {
               return false;
            }
         } while (isBlankOrComment(s));

         this.parse(s);
         return true;
      }
   }

   void close() {
      try {
         if (this.reader != null) {
            this.reader.close();
         }
      } catch (IOException ioexception) {
      }
   }

   void clear() {
      this.tags = "";
      this.values = new Vector();
      this.reader = null;
      this.rawLine = null;
   }

   void open(Reader readerx, boolean flag) {
      this.clear();
      this.clearOnRead = flag;
      if (readerx instanceof ScrambledReader) {
         this.reader = (ScrambledReader)readerx;
      } else {
         this.reader = new ScrambledReader(readerx, LogicProgram.scrambleKey);
      }
   }

   void parse(String s) {
      this.rawLine = s;
      if (s != null && !s.equals("") && s.charAt(0) != '#') {
         String s1 = "";
         if (s.charAt(0) == '`') {
            s = s.substring(1);
         }

         while (true) {
            int i = s.indexOf(96);
            if (i == -1 || i == s.length() - 1) {
               return;
            }

            char c0 = s.charAt(i + 1);
            String s2 = s1 + s.substring(0, i);
            s = s.substring(i + 2);
            if (c0 == '`') {
               s1 = s2 + c0;
            } else {
               this.tags = this.tags + c0;
               this.values.addElement(s2);
               s1 = "";
            }
         }
      }
   }

   char tagAt(int i) {
      return this.tags.charAt(i);
   }

   int indexOfTag(char c0) {
      return this.tags.indexOf(c0);
   }

   int indexOfTag(char c0, int i) {
      int j = this.tags.substring(i).indexOf(c0);
      return j == -1 ? -1 : j + i;
   }

   int[] indexesOfTag(char c0) {
      int i = 0;
      ExpressionPath expressionpath = new ExpressionPath();

      while (true) {
         i = this.indexOfTag(c0, i);
         if (i == -1) {
            return expressionpath.toArray();
         }

         expressionpath.push(i);
         i++;
      }
   }

   int indexOfAnyTag(String s) {
      return this.indexOfAnyTag(s, 0);
   }

   int indexOfAnyTag(String s, int i) {
      String s1 = this.tags.substring(i);
      int j = -1;
      int k = s.length();

      for (int l = 0; l < k; l++) {
         int i1 = s1.indexOf(s.charAt(l));
         j = minNonNegative(j, i1);
      }

      return j == -1 ? -1 : j + i;
   }

   static int minNonNegative(int i, int j) {
      if (j < 0) {
         return i;
      } else {
         return i >= 0 && j >= i ? i : j;
      }
   }

   int[] indexesOfAnyTag(String s) {
      int i = 0;
      ExpressionPath expressionpath = new ExpressionPath();

      while (true) {
         i = this.indexOfAnyTag(s, i);
         if (i == -1) {
            return expressionpath.toArray();
         }

         expressionpath.push(i);
         i++;
      }
   }

   int getFieldCount() {
      return this.values.size();
   }

   String valueAt(int i) {
      return i == -1 ? null : (String)this.values.elementAt(i);
   }

   String formatFields(String s) {
      String s1 = "";
      int[] aint = this.indexesOfAnyTag(s);
      int i = aint.length;

      for (int j = 0; j < i; j++) {
         s1 = s1 + formatField(this.valueAt(aint[j]), this.tagAt(aint[j]));
      }

      return s1;
   }

   Integer intValueAt(int i) {
      Integer integer = null;
      String s = this.valueAt(i);
      if (s != null) {
         try {
            integer = Integer.valueOf(s);
         } catch (NumberFormatException numberformatexception) {
         }
      }

      return integer;
   }

   Long longValueAt(int i) {
      Long olong = null;
      String s = this.valueAt(i);
      if (s != null) {
         try {
            olong = Long.valueOf(s);
         } catch (NumberFormatException numberformatexception) {
         }
      }

      return olong;
   }

   String getRawLine() {
      return this.rawLine;
   }

   void removeField(int i) {
      this.tags = this.tags.substring(0, i) + this.tags.substring(i + 1);
      this.values.removeElementAt(i);
   }

   void removeFields(int[] aint) {
      int i = aint == null ? 0 : aint.length;

      for (int j = 0; j < i; j++) {
         this.removeField(aint[j]);
      }
   }

   void setValueAt(String s, int i) {
      this.values.setElementAt(s, i);
   }

   void insertField(char c0, String s, int i) {
      this.tags = this.tags.substring(0, i) + c0 + this.tags.substring(i);
      this.values.insertElementAt(s, i);
   }

   void addField(char c0, String s) {
      this.tags = this.tags + c0;
      this.values.addElement(s);
   }

   static String nameOf(String s) {
      return new TaggedRecord(s).getName();
   }

   String getName() {
      return this.valueAt(this.indexOfTag('$'));
   }

   static String withName(String s, String s1) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      taggedrecord.setName(s1);
      return taggedrecord.toString();
   }

   void setName(String s) {
      int i = this.indexOfTag('$');
      if (i == -1) {
         this.insertField('$', s, 0);
      } else {
         this.setValueAt(s, i);
      }
   }

   String getOriginalName() {
      return this.valueAt(this.indexOfTag('o'));
   }

   int getErrorCount() {
      Integer integer = this.intValueAt(this.indexOfTag('e'));
      return integer == null ? 0 : integer;
   }

   long getTimestamp() {
      Long olong = this.longValueAt(this.indexOfTag('t'));
      return olong == null ? 0L : olong;
   }

   static String stripTimestamp(String s) {
      return removeTags(s, "t");
   }

   static String removeTags(String s, String s1) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      taggedrecord.removeFields(taggedrecord.indexesOfAnyTag(s1));
      return taggedrecord.toString();
   }

   static boolean isExample(String s) {
      if (s == null) {
         return false;
      } else {
         TaggedRecord taggedrecord = new TaggedRecord(s);
         Hashtable hashtable = taggedrecord.getKeyValues('%');
         return hashtable != null && hashtable.containsKey("eg");
      }
   }

   static String clearExampleFlag(String s) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      int[] aint = taggedrecord.indexesOfTag('%');

      for (int i = aint.length - 1; i >= 0; i--) {
         String s1 = taggedrecord.valueAt(aint[i]);
         if (s1 != null && s1.equals("eg")) {
            taggedrecord.removeField(aint[i]);
         }
      }

      return taggedrecord.toString();
   }

   static String appendField(String s, char c0, String s1) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      taggedrecord.addField(c0, s1);
      return taggedrecord.toString();
   }

   boolean isHidden() {
      int[] aint = this.indexesOfTag('%');

      for (int i = aint.length - 1; i >= 0; i--) {
         String s = this.valueAt(aint[i]);
         if (s != null && s.equals("hide")) {
            return true;
         }
      }

      return false;
   }

   Hashtable getKeyValues(char c0) {
      int[] aint = this.indexesOfTag(c0);
      int i = aint.length;
      Hashtable hashtable = new Hashtable();

      for (int j = 0; j < i; j++) {
         String s = this.valueAt(aint[j]);
         int k = s.indexOf(58);
         String s1 = k == -1 ? s : s.substring(0, k);
         String s2 = k == -1 ? "" : s.substring(k + 1);
         hashtable.put(s1.toLowerCase(), s2);
      }

      return hashtable;
   }

   private static String escapeBackquotes(String s) {
      String s1 = "";

      int i;
      while ((i = s.indexOf(96)) != -1) {
         s1 = s1 + s.substring(0, i) + "``";
         s = s.substring(i + 1);
      }

      return s1 + s;
   }

   static String formatField(String s, char c0) {
      return s == null ? "" : escapeBackquotes(s) + "`" + c0;
   }

   static String toLine(String s) {
      String s1 = removeChar(s, '\n');
      if (s1 != null && s1.length() != 0) {
         char c0 = s1.charAt(0);
         if (c0 == '#' || c0 == '`') {
            s1 = '`' + s1;
         }
      }

      return s1;
   }

   static String removeChar(String s, char c0) {
      if (s == null) {
         return s;
      } else {
         String s1 = "";

         int i;
         while ((i = s.indexOf(c0)) != -1) {
            s1 = s1 + s.substring(0, i);
            s = s.substring(i + 1);
         }

         return s1 + s;
      }
   }

   static boolean isBlankOrComment(String s) {
      return s.trim().equals("") || s.charAt(0) == '#';
   }

   @Override
   public String toString() {
      String s = "";
      int i = this.tags.length();

      for (int j = 0; j < i; j++) {
         s = s + formatField((String)this.values.elementAt(j), this.tags.charAt(j));
      }

      return toLine(s);
   }
}
