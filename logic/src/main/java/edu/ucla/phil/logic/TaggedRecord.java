package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Vector;

class TaggedRecord {
   String tags;
   Vector values;
   ScrambledReader reader;
   boolean f877;
   String f878;

   TaggedRecord(Reader reader, boolean flag) {
      this.m1472(readerx, flag);
      if (!flag) {
         while (this.readNext()) {
         }
      }
   }

   TaggedRecord(Reader reader) {
      this(readerx, false);
   }

   TaggedRecord() {
      this.m1471();
   }

   TaggedRecord(String s) {
      this();
      this.parse(s);
   }

   boolean readNext() {
      if (this.reader == null) {
         return false;
      } else {
         if (this.f877) {
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

   void m1471() {
      this.tags = "";
      this.values = new Vector();
      this.reader = null;
      this.f878 = null;
   }

   void m1472(Reader reader, boolean flag) {
      this.m1471();
      this.f877 = flag;
      if (readerx instanceof ScrambledReader) {
         this.reader = (ScrambledReader)readerx;
      } else {
         this.reader = new ScrambledReader(readerx, LogicProgram.scrambleKey);
      }
   }

   void parse(String s) {
      this.f878 = s;
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
            s1 = s1 + s.substring(0, i);
            s = s.substring(i + 2);
            if (c0 == '`') {
               s1 = s1 + c0;
            } else {
               this.tags = this.tags + c0;
               this.values.addElement(s1);
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

   int m1476(char c0, int i) {
      int j = this.tags.substring(i).indexOf(c0);
      return j == -1 ? -1 : j + i;
   }

   int[] m1477(char c0) {
      int i = 0;
      ExpressionPath expressionpath = new ExpressionPath();

      while (true) {
         i = this.m1476(c0, i);
         if (i == -1) {
            return expressionpath.m1752();
         }

         expressionpath.m1749(i);
         i++;
      }
   }

   int m1478(String s) {
      return this.m1479(s, 0);
   }

   int m1479(String s, int i) {
      String s1 = this.tags.substring(i);
      int j = -1;
      int k = s.length();

      for (int l = 0; l < k; l++) {
         int i1 = s1.indexOf(s.charAt(l));
         j = m1480(j, i1);
      }

      return j == -1 ? -1 : j + i;
   }

   static int m1480(int i, int j) {
      if (j < 0) {
         return i;
      } else {
         return i >= 0 && j >= i ? i : j;
      }
   }

   int[] m1481(String s) {
      int i = 0;
      ExpressionPath expressionpath = new ExpressionPath();

      while (true) {
         i = this.m1479(s, i);
         if (i == -1) {
            return expressionpath.m1752();
         }

         expressionpath.m1749(i);
         i++;
      }
   }

   int m1482() {
      return this.values.size();
   }

   String valueAt(int i) {
      return i == -1 ? null : (String)this.values.elementAt(i);
   }

   String m1484(String s) {
      String s1 = "";
      int[] aint = this.m1481(s);
      int i = aint.length;

      for (int j = 0; j < i; j++) {
         s1 = s1 + m1508(this.valueAt(aint[j]), this.tagAt(aint[j]));
      }

      return s1;
   }

   Integer m1485(int i) {
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

   Long m1486(int i) {
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

   String m1487() {
      return this.f878;
   }

   void m1488(int i) {
      this.tags = this.tags.substring(0, i) + this.tags.substring(i + 1);
      this.values.removeElementAt(i);
   }

   void m1489(int[] aint) {
      int i = aint == null ? 0 : aint.length;

      for (int j = 0; j < i; j++) {
         this.m1488(aint[j]);
      }
   }

   void m1490(String s, int i) {
      this.values.setElementAt(s, i);
   }

   void m1491(char c0, String s, int i) {
      this.tags = this.tags.substring(0, i) + c0 + this.tags.substring(i);
      this.values.insertElementAt(s, i);
   }

   void m1492(char c0, String s) {
      this.tags = this.tags + c0;
      this.values.addElement(s);
   }

   static String m1493(String s) {
      return new TaggedRecord(s).getName();
   }

   String getName() {
      return this.valueAt(this.indexOfTag('$'));
   }

   static String m1495(String s, String s1) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      taggedrecord.m1496(s1);
      return taggedrecord.toString();
   }

   void m1496(String s) {
      int i = this.indexOfTag('$');
      if (i == -1) {
         this.m1491('$', s, 0);
      } else {
         this.m1490(s, i);
      }
   }

   String m1497() {
      return this.valueAt(this.indexOfTag('o'));
   }

   int m1498() {
      Integer integer = this.m1485(this.indexOfTag('e'));
      return integer == null ? 0 : integer;
   }

   long m1499() {
      Long olong = this.m1486(this.indexOfTag('t'));
      return olong == null ? 0L : olong;
   }

   static String m1500(String s) {
      return m1501(s, "t");
   }

   static String m1501(String s, String s1) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      taggedrecord.m1489(taggedrecord.m1481(s1));
      return taggedrecord.toString();
   }

   static boolean m1502(String s) {
      if (s == null) {
         return false;
      } else {
         TaggedRecord taggedrecord = new TaggedRecord(s);
         Hashtable hashtable = taggedrecord.m1506('%');
         return hashtable != null && hashtable.containsKey("eg");
      }
   }

   static String m1503(String s) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      int[] aint = taggedrecord.m1477('%');

      for (int i = aint.length - 1; i >= 0; i--) {
         String s1 = taggedrecord.valueAt(aint[i]);
         if (s1 != null && s1.equals("eg")) {
            taggedrecord.m1488(aint[i]);
         }
      }

      return taggedrecord.toString();
   }

   static String m1504(String s, char c0, String s1) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      taggedrecord.m1492(c0, s1);
      return taggedrecord.toString();
   }

   boolean m1505() {
      int[] aint = this.m1477('%');

      for (int i = aint.length - 1; i >= 0; i--) {
         String s = this.valueAt(aint[i]);
         if (s != null && s.equals("hide")) {
            return true;
         }
      }

      return false;
   }

   Hashtable m1506(char c0) {
      int[] aint = this.m1477(c0);
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

   private static String m1507(String s) {
      String s1 = "";

      int i;
      while ((i = s.indexOf(96)) != -1) {
         s1 = s1 + s.substring(0, i) + "``";
         s = s.substring(i + 1);
      }

      return s1 + s;
   }

   static String m1508(String s, char c0) {
      return s == null ? "" : m1507(s) + "`" + c0;
   }

   static String m1509(String s) {
      s = m1510(s, '\n');
      if (s != null && s.length() != 0) {
         char c0 = s.charAt(0);
         if (c0 == '#' || c0 == '`') {
            s = '`' + s;
         }
      }

      return s;
   }

   static String m1510(String s, char c0) {
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
         s = s + m1508((String)this.values.elementAt(j), this.tags.charAt(j));
      }

      return m1509(s);
   }
}
