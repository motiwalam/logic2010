package edu.ucla.phil.logic;

import java.util.Vector;

class C_VB {
   String f820;
   Vector f821;
   C_QE f822;
   String f823 = null;

   C_VB(String s) {
      this(s, new Vector());
   }

   C_VB(String s, Vector vector) {
      this.f820 = s;
      this.f821 = vector;
      this.f822 = null;
   }

   C_VB(String s, String s1, C_z_B c_z_b, C_z_ c_z_) {
      this(s);

      while (!s1.equals("")) {
         int i = s1.indexOf(".");
         String s2;
         if (i == -1) {
            s2 = s1.trim();
            s1 = "";
         } else {
            s2 = s1.substring(0, i).trim();
            s1 = s1.substring(i + 1);
         }

         C_VB c_vb1 = c_z_b.m2205(s2);
         if (c_vb1 == null) {
            Integer integer = m1366(s2);
            if (integer == null) {
               this.f823 = "could not find rule " + s2;
            } else {
               this.f823 = "could not find theorem number " + integer;
            }
         } else {
            this.f821.addElement(c_vb1);
         }
      }
   }

   static Integer m1366(String s) {
      s = s.toUpperCase();
      int i = s.length();
      if (i >= 2 && s.substring(0, 1).equals("T") && "123456789".indexOf(s.substring(1, 2)) != -1) {
         try {
            return new Integer(s.substring(1));
         } catch (NumberFormatException numberformatexception) {
            return null;
         }
      } else {
         return null;
      }
   }

   static Integer m1367(String s) {
      s = s.toUpperCase();
      int i = 3;
      int j = s.length();
      if (j >= 3 && s.substring(0, 2).equals("RT") && "123456789".indexOf(s.charAt(2)) != -1) {
         while (i < j && "0123456789".indexOf(s.charAt(i)) != -1) {
            i++;
         }

         try {
            return new Integer(s.substring(2, i));
         } catch (NumberFormatException numberformatexception) {
            return null;
         }
      } else {
         return null;
      }
   }

   C_VB m1368(String s) {
      if (this.f820.equals(s)) {
         return this;
      } else {
         int i = this.f821 == null ? 0 : this.f821.size();

         for (int j = 0; j < i; j++) {
            C_VB c_vb1 = this.m1372(j).m1368(s);
            if (c_vb1 != null) {
               return c_vb1;
            }
         }

         return null;
      }
   }

   String m1369() {
      return this.f820;
   }

   void m1370(C_VB c_vb1) {
      if (this.f821 != null && c_vb1 != null) {
         this.f821.addElement(c_vb1);
      }
   }

   int m1371() {
      return this.f821 == null ? 0 : this.f821.size();
   }

   C_VB m1372(int i) {
      return this.f821 == null ? null : (C_VB)this.f821.elementAt(i);
   }

   boolean m1193(C_w_E c_w_e, String s, boolean flag) {
      if (this.f822 != null && this.f822.m1193(c_w_e, s, flag)) {
         return true;
      } else if (c_w_e.hasProperty(this, s)) {
         return true;
      } else {
         int i = this.f821 == null ? 0 : this.f821.size();
         if (i == 0) {
            return false;
         } else {
            for (int j = 0; j < i; j++) {
               if (((C_VB)this.f821.elementAt(j)).m1193(c_w_e, s, flag) == flag) {
                  return flag;
               }
            }

            return !flag;
         }
      }
   }

   boolean m952(C_w_E c_w_e) {
      int i = this.f821 == null ? 0 : this.f821.size();

      for (int j = 0; j < i; j++) {
         if (!((C_VB)this.f821.elementAt(j)).m952(c_w_e)) {
            return false;
         }
      }

      return true;
   }

   boolean m953(C_w_E c_w_e) {
      int i = this.f821 == null ? 0 : this.f821.size();

      for (int j = 0; j < i; j++) {
         if (((C_VB)this.f821.elementAt(j)).m953(c_w_e)) {
            return true;
         }
      }

      return false;
   }

   void m955(Vector vector, C_w_E c_w_e, String s) {
      if (c_w_e == null || !c_w_e.hasProperty(this, s)) {
         int i = this.m1371();

         for (int j = 0; j < i; j++) {
            this.m1372(j).m955(vector, c_w_e, s);
         }
      }
   }

   C_LF[] m1373(C_w_E c_w_e, String s) {
      Vector vector = new Vector();
      this.m955(vector, c_w_e, s);
      C_LF[] ac_lf = new C_LF[vector.size()];
      vector.copyInto(ac_lf);
      return ac_lf;
   }

   C_LF[] m1374() {
      return this.m1373(null, null);
   }

   boolean m1375(C_VB c_vb1) {
      if (this.f820.equals(c_vb1.f820)) {
         return true;
      } else {
         int i = this.f821 == null ? 0 : this.f821.size();

         for (int j = 0; j < i; j++) {
            C_VB c_vb2 = (C_VB)this.f821.elementAt(j);
            if (c_vb2 != null && c_vb2.m1375(c_vb1)) {
               return true;
            }
         }

         return false;
      }
   }

   boolean m1376(C_VB c_vb1, Vector vector) {
      int i = vector == null ? 0 : vector.size();

      for (int j = 0; j < i; j++) {
         C_VB c_vb2 = (C_VB)vector.elementAt(j);
         if (c_vb1.m1375(c_vb2) && c_vb2.m1375(this)) {
            return true;
         }
      }

      return false;
   }

   static C_VB m1377(C_QE c_qe) {
      if (c_qe == null) {
         return null;
      } else {
         String s = "RT" + c_qe.f700;
         C_RF c_rf2 = c_qe.f527;
         Vector vector;
         if (c_rf2.f739.equals("<->")) {
            C_RF c_rf = c_rf2.m1217(0);
            C_RF c_rf1 = c_rf2.m1217(1);
            vector = new Vector();
            vector.addElement(m1378(new C_LF(s + "L", new C_RF[]{c_rf}, c_rf1), c_qe));
            C_RF[] ac_rf = m1379(c_rf);
            if (ac_rf.length > 1) {
               vector.addElement(m1378(new C_LF(s + "LF", ac_rf, c_rf1), c_qe));
            }

            vector.addElement(m1378(new C_LF(s + "R", new C_RF[]{c_rf1}, c_rf), c_qe));
            ac_rf = m1379(c_rf1);
            if (ac_rf.length > 1) {
               vector.addElement(m1378(new C_LF(s + "RF", ac_rf, c_rf), c_qe));
            }
         } else {
            if (!c_rf2.f739.equals("->")) {
               return m1378(new C_LF(s, null, c_rf2), c_qe);
            }

            C_RF c_rf3 = c_rf2.m1217(0);
            C_RF c_rf4 = c_rf2.m1217(1);
            C_RF[] ac_rf1 = m1379(c_rf3);
            if (ac_rf1.length <= 1) {
               return m1378(new C_LF(s, ac_rf1, c_rf4), c_qe);
            }

            vector = new Vector();
            vector.addElement(m1378(new C_LF(s + "L", new C_RF[]{c_rf3}, c_rf4), c_qe));
            vector.addElement(m1378(new C_LF(s + "LF", ac_rf1, c_rf4), c_qe));
         }

         return m1378(new C_VB(s, vector), c_qe);
      }
   }

   static C_VB m1378(C_VB c_vb, C_QE c_qe) {
      c_vb.f822 = c_qe;
      return c_vb;
   }

   static C_RF[] m1379(C_RF c_rf) {
      Vector vector = new Vector();
      m1380(c_rf, vector);
      C_RF[] ac_rf = new C_RF[vector.size()];
      vector.copyInto(ac_rf);
      return ac_rf;
   }

   static void m1380(C_RF c_rf, Vector vector) {
      if (c_rf.f739.equals("&")) {
         m1380(c_rf.m1217(0), vector);
         m1380(c_rf.m1217(1), vector);
      } else {
         vector.addElement(c_rf);
      }
   }

   String m1381() {
      return this.f823;
   }

   @Override
   public String toString() {
      return this.m958(".", ".:");
   }

   String m958(String s, String s2) {
      String s1 = "";
      int i = this.m1371();
      boolean flag = false;

      for (int j = 0; j < i; j++) {
         s1 = s1 + (flag ? s : "") + this.m1372(j).f820;
         flag = true;
      }

      return s1;
   }
}
