package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;

abstract class C_f_F {
   String f1119;
   int f1120;
   boolean f1121;
   boolean f1122;
   static final int f1123 = 0;
   static final int f1124 = 1;
   static final int f1125 = 2;
   static final int f1126 = 3;
   static final int f1127 = 4;
   static final String[] f1128 = new String[]{"N", "I", "C", "I", "U"};

   abstract int m513(String s);

   C_f_F(String s, boolean flag, Hashtable hashtable) {
      this.f1119 = s;
      this.f1120 = flag ? 4 : this.m513(s);
      this.f1121 = false;
      this.f1122 = m1817(s, hashtable);
   }

   static Hashtable m1813(Reader reader, Hashtable hashtable) {
      Hashtable hashtable1 = hashtable == null ? new Hashtable() : hashtable;
      C_XB c_xb;
      if (reader instanceof C_XB) {
         c_xb = (C_XB)reader;
      } else {
         c_xb = new C_XB(reader, LogicProgram.f537);
      }

      try {
         String s;
         while ((s = c_xb.readLine()) != null) {
            String s1;
            if (!C_XD.m1511(s) && (s1 = C_XD.m1493(s)) != null) {
               hashtable1.put(s1.trim().toUpperCase(), Boolean.TRUE);
            }
         }

         c_xb.close();
      } catch (IOException ioexception) {
      }

      return hashtable1;
   }

   static Hashtable m1814(C_e_D c_e_d, Hashtable hashtable) {
      Hashtable hashtable1 = new Hashtable();
      int i = c_e_d.size();

      for (int j = 0; j < i; j++) {
         C_f_F c_f_f = (C_f_F)c_e_d.get(j);
         if (c_f_f != null && c_f_f.f1119 != null) {
            String s = C_XD.m1493(c_f_f.f1119).trim().toUpperCase();
            if (hashtable == null || hashtable.get(s) == null) {
               hashtable1.put(s, Boolean.TRUE);
            }

            c_f_f.f1122 = m1817(c_f_f.f1119, hashtable1);
         }
      }

      return hashtable1;
   }

   static void m1815(C_e_D c_e_d, Hashtable hashtable) {
      int i = c_e_d.size();

      for (int j = 0; j < i; j++) {
         C_f_F c_f_f = (C_f_F)c_e_d.get(j);
         if (c_f_f != null && c_f_f.f1119 != null) {
            c_f_f.f1122 = m1817(c_f_f.f1119, hashtable);
         }
      }
   }

   static Hashtable m1816(String s, C_e_D c_e_d) {
      Hashtable hashtable = null;
      C_XB c_xb = LogicProgram.m1067(s, false, true);
      if (c_xb != null) {
         hashtable = m1813(c_xb, hashtable);
      }

      c_xb = LogicProgram.m1067(s, true, true);
      if (c_xb != null) {
         hashtable = m1813(c_xb, hashtable);
      }

      return m1814(c_e_d, hashtable);
   }

   static boolean m1817(String s, Hashtable hashtable) {
      String s1 = C_XD.m1493(s);
      if (s1 == null) {
         return false;
      } else {
         s1 = s1.trim().toUpperCase();
         if (s1.startsWith("DEMO")) {
            return false;
         } else {
            return hashtable == null ? false : hashtable.get(s1) != null;
         }
      }
   }
}
