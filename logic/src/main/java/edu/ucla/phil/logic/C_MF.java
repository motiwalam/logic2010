package edu.ucla.phil.logic;

import java.util.Vector;

class C_MF {
   String f620;
   String f621;
   String f622;
   Integer f623;
   String f624;
   String[] f625;
   String[] f626;
   Integer f627;
   static C_MF[] f628 = null;

   C_MF(String s, String s1, String s2, Integer integer, String s3, Integer integer1, String s4) {
      this.f620 = s == null ? "" : s;
      this.f621 = s1 == null ? "" : s1;
      this.f622 = s2 == null ? "" : s2;
      this.f623 = integer;
      this.f624 = s3;
      this.f627 = integer1;
      this.f625 = m1114(s4, 0);
      this.f626 = m1114(s4, 1);
   }

   static String m1109(String s, C_SF c_sf) {
      return s != null && c_sf != null ? c_sf.m1298(c_sf.m1296(s)) : s;
   }

   static String[] m1110(String[] astring, C_SF c_sf) {
      if (astring == null) {
         return null;
      } else {
         int i = astring.length;
         String[] astring1 = new String[i];

         for (int j = 0; j < i; j++) {
            astring1[j] = m1109(astring[j], c_sf);
         }

         return astring1;
      }
   }

   static String[] m1111(C_MF[] ac_mf) {
      if (ac_mf == null) {
         return null;
      } else {
         int i = ac_mf.length;
         Vector vector = new Vector();

         for (int j = 0; j < i; j++) {
            String s = ac_mf[j].f620;
            if (!m1115(vector, s)) {
               vector.addElement(s);
            }
         }

         String[] astring = new String[vector.size()];
         vector.copyInto(astring);
         return astring;
      }
   }

   static String[] m1112(C_MF[] ac_mf) {
      if (ac_mf == null) {
         return null;
      } else {
         int i = ac_mf.length;
         Vector vector = new Vector();

         for (int j = 0; j < i; j++) {
            String s = ac_mf[j].f621;
            if (!m1115(vector, s)) {
               vector.addElement(s);
            }
         }

         String[] astring = new String[vector.size()];
         vector.copyInto(astring);
         return astring;
      }
   }

   static String[] m1113(C_MF[] ac_mf) {
      if (ac_mf == null) {
         return null;
      } else {
         int i = ac_mf.length;
         Vector vector = new Vector();

         for (int j = 0; j < i; j++) {
            String s = ac_mf[j].f622;
            if (!m1115(vector, s)) {
               vector.addElement(s);
            }
         }

         String[] astring = new String[vector.size()];
         vector.copyInto(astring);
         return astring;
      }
   }

   static String[] m1114(String s, int i) {
      if (s == null) {
         return null;
      } else {
         C_OA c_oa = new C_OA("\\:");
         c_oa.m1132(s);

         while (--i >= 0 && c_oa.m1135() != null) {
         }

         String s2 = c_oa.m1135();
         if (s2 == null) {
            return null;
         } else {
            Vector vector = new Vector();
            c_oa = new C_OA("\\;");
            c_oa.m1132(s2);

            String s1;
            while ((s1 = c_oa.m1135()) != null && (s1 = s1.trim()).length() != 0) {
               vector.add(s1);
            }

            if (vector.size() == 0) {
               return null;
            } else {
               String[] astring = new String[vector.size()];
               vector.copyInto(astring);
               return astring;
            }
         }
      }
   }

   static boolean m1115(Vector vector, String s) {
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         Object object = vector.elementAt(j);
         if (object instanceof String && ((String)object).equalsIgnoreCase(s)) {
            return true;
         }
      }

      return false;
   }

   static C_MF[] m1116(C_OE c_oe) {
      String s = c_oe.m1156();
      String s1 = c_oe.m1157();
      String s2 = c_oe.m1159();
      C_MF[] ac_mf = m1120(s, s1, s2);
      if (ac_mf == null) {
         return null;
      } else {
         if (ac_mf.length == 0) {
            ac_mf = m1119(s, s1);
         }

         if (ac_mf == null) {
            return null;
         } else {
            if (ac_mf.length == 0) {
               ac_mf = m1118(s);
            }

            if (ac_mf == null) {
               return null;
            } else {
               if (c_oe instanceof C_HB && ac_mf.length == 0) {
                  ac_mf = m1117();
               }

               return ac_mf;
            }
         }
      }
   }

   static C_MF[] m1117() {
      if (f628 == null) {
         C_KC.m917();
      }

      return f628;
   }

   static C_MF[] m1118(String s) {
      C_MF[] ac_mf = m1117();
      if (ac_mf != null && s != null) {
         Vector vector = new Vector();
         int i = ac_mf.length;

         for (int j = 0; j < i; j++) {
            if (s.equalsIgnoreCase(ac_mf[j].f620)) {
               vector.addElement(ac_mf[j]);
            }
         }

         C_MF[] ac_mf1 = new C_MF[vector.size()];
         vector.copyInto(ac_mf1);
         return ac_mf1;
      } else {
         return ac_mf;
      }
   }

   static C_MF[] m1119(String s, String s1) {
      C_MF[] ac_mf = m1118(s);
      if (ac_mf != null && s1 != null) {
         Vector vector = new Vector();
         int i = ac_mf.length;

         for (int j = 0; j < i; j++) {
            if (s1.equalsIgnoreCase(ac_mf[j].f621)) {
               vector.addElement(ac_mf[j]);
            }
         }

         C_MF[] ac_mf1 = new C_MF[vector.size()];
         vector.copyInto(ac_mf1);
         return ac_mf1;
      } else {
         return ac_mf;
      }
   }

   static C_MF[] m1120(String s, String s1, String s2) {
      C_MF[] ac_mf = m1119(s, s1);
      if (ac_mf != null && s2 != null) {
         Vector vector = new Vector();
         int i = ac_mf.length;

         for (int j = 0; j < i; j++) {
            if (s2.equalsIgnoreCase(ac_mf[j].f622)) {
               vector.addElement(ac_mf[j]);
            }
         }

         C_MF[] ac_mf1 = new C_MF[vector.size()];
         vector.copyInto(ac_mf1);
         return ac_mf1;
      } else {
         return ac_mf;
      }
   }
}
