package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Vector;

class C_XD {
   String f874;
   Vector f875;
   C_XB f876;
   boolean f877;
   String f878;

   C_XD(Reader reader, boolean flag) {
      this.m1472(reader, flag);
      if (!flag) {
         while (this.m1469()) {
         }
      }
   }

   C_XD(Reader reader) {
      this(reader, false);
   }

   C_XD() {
      this.m1471();
   }

   C_XD(String s) {
      this();
      this.m1473(s);
   }

   boolean m1469() {
      if (this.f876 == null) {
         return false;
      } else {
         if (this.f877) {
            this.f874 = "";
            this.f875 = new Vector();
         }

         String s;
         do {
            try {
               if ((s = this.f876.readLine()) == null) {
                  return false;
               }
            } catch (IOException ioexception) {
               return false;
            }
         } while (m1511(s));

         this.m1473(s);
         return true;
      }
   }

   void m1470() {
      try {
         if (this.f876 != null) {
            this.f876.close();
         }
      } catch (IOException ioexception) {
      }
   }

   void m1471() {
      this.f874 = "";
      this.f875 = new Vector();
      this.f876 = null;
      this.f878 = null;
   }

   void m1472(Reader reader, boolean flag) {
      this.m1471();
      this.f877 = flag;
      if (reader instanceof C_XB) {
         this.f876 = (C_XB)reader;
      } else {
         this.f876 = new C_XB(reader, LogicProgram.f537);
      }
   }

   void m1473(String s) {
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
               this.f874 = this.f874 + c0;
               this.f875.addElement(s1);
               s1 = "";
            }
         }
      }
   }

   char m1474(int i) {
      return this.f874.charAt(i);
   }

   int m1475(char c0) {
      return this.f874.indexOf(c0);
   }

   int m1476(char c0, int i) {
      int j = this.f874.substring(i).indexOf(c0);
      return j == -1 ? -1 : j + i;
   }

   int[] m1477(char c0) {
      int i = 0;
      C_e_ c_e_ = new C_e_();

      while (true) {
         i = this.m1476(c0, i);
         if (i == -1) {
            return c_e_.m1752();
         }

         c_e_.m1749(i);
         i++;
      }
   }

   int m1478(String s) {
      return this.m1479(s, 0);
   }

   int m1479(String s, int i) {
      String s1 = this.f874.substring(i);
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
      C_e_ c_e_ = new C_e_();

      while (true) {
         i = this.m1479(s, i);
         if (i == -1) {
            return c_e_.m1752();
         }

         c_e_.m1749(i);
         i++;
      }
   }

   int m1482() {
      return this.f875.size();
   }

   String m1483(int i) {
      return i == -1 ? null : (String)this.f875.elementAt(i);
   }

   String m1484(String s) {
      String s1 = "";
      int[] aint = this.m1481(s);
      int i = aint.length;

      for (int j = 0; j < i; j++) {
         s1 = s1 + m1508(this.m1483(aint[j]), this.m1474(aint[j]));
      }

      return s1;
   }

   Integer m1485(int i) {
      Integer integer = null;
      String s = this.m1483(i);
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
      String s = this.m1483(i);
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
      this.f874 = this.f874.substring(0, i) + this.f874.substring(i + 1);
      this.f875.removeElementAt(i);
   }

   void m1489(int[] aint) {
      int i = aint == null ? 0 : aint.length;

      for (int j = 0; j < i; j++) {
         this.m1488(aint[j]);
      }
   }

   void m1490(String s, int i) {
      this.f875.setElementAt(s, i);
   }

   void m1491(char c0, String s, int i) {
      this.f874 = this.f874.substring(0, i) + c0 + this.f874.substring(i);
      this.f875.insertElementAt(s, i);
   }

   void m1492(char c0, String s) {
      this.f874 = this.f874 + c0;
      this.f875.addElement(s);
   }

   static String m1493(String s) {
      return new C_XD(s).m1494();
   }

   String m1494() {
      return this.m1483(this.m1475('$'));
   }

   static String m1495(String s, String s1) {
      C_XD c_xd = new C_XD(s);
      c_xd.m1496(s1);
      return c_xd.toString();
   }

   void m1496(String s) {
      int i = this.m1475('$');
      if (i == -1) {
         this.m1491('$', s, 0);
      } else {
         this.m1490(s, i);
      }
   }

   String m1497() {
      return this.m1483(this.m1475('o'));
   }

   int m1498() {
      Integer integer = this.m1485(this.m1475('e'));
      return integer == null ? 0 : integer;
   }

   long m1499() {
      Long olong = this.m1486(this.m1475('t'));
      return olong == null ? 0L : olong;
   }

   static String m1500(String s) {
      return m1501(s, "t");
   }

   static String m1501(String s, String s1) {
      C_XD c_xd = new C_XD(s);
      c_xd.m1489(c_xd.m1481(s1));
      return c_xd.toString();
   }

   static boolean m1502(String s) {
      if (s == null) {
         return false;
      } else {
         C_XD c_xd = new C_XD(s);
         Hashtable hashtable = c_xd.m1506('%');
         return hashtable != null && hashtable.containsKey("eg");
      }
   }

   static String m1503(String s) {
      C_XD c_xd = new C_XD(s);
      int[] aint = c_xd.m1477('%');

      for (int i = aint.length - 1; i >= 0; i--) {
         String s1 = c_xd.m1483(aint[i]);
         if (s1 != null && s1.equals("eg")) {
            c_xd.m1488(aint[i]);
         }
      }

      return c_xd.toString();
   }

   static String m1504(String s, char c0, String s1) {
      C_XD c_xd = new C_XD(s);
      c_xd.m1492(c0, s1);
      return c_xd.toString();
   }

   boolean m1505() {
      int[] aint = this.m1477('%');

      for (int i = aint.length - 1; i >= 0; i--) {
         String s = this.m1483(aint[i]);
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
         String s = this.m1483(aint[j]);
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

   static boolean m1511(String s) {
      return s.trim().equals("") || s.charAt(0) == '#';
   }

   @Override
   public String toString() {
      String s = "";
      int i = this.f874.length();

      for (int j = 0; j < i; j++) {
         s = s + m1508((String)this.f875.elementAt(j), this.f874.charAt(j));
      }

      return m1509(s);
   }
}
