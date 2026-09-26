package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class C_r_D extends Hashtable implements C_w_E {
   @Override
   public boolean hasProperty(C_VB c_vb, String s) {
      if (s.equals("notConditional")) {
         if (!(c_vb instanceof C_LF)) {
            return false;
         } else {
            C_LF c_lf2 = (C_LF)c_vb;
            if (c_lf2.f527 == null) {
               return true;
            } else {
               int j = c_lf2.f526.length;
               return j == 0 ? m2061(c_lf2.f527) == null : j != 1;
            }
         }
      } else if (s.equals("notConditionalBC")) {
         if (!(c_vb instanceof C_LF)) {
            return false;
         } else {
            C_LF c_lf1 = (C_LF)c_vb;
            if (c_lf1.f527 == null) {
               return true;
            } else {
               int i = c_lf1.f526.length;
               if (i == 0) {
                  return m2065(c_lf1.f527) == null;
               } else {
                  return i == 1 ? !c_lf1.f527.f739.equals("<->") : true;
               }
            }
         }
      } else if (s.equals("biconditional")) {
         if (!(c_vb instanceof C_LF)) {
            return false;
         } else {
            C_LF c_lf = (C_LF)c_vb;
            return c_lf.f526 != null && c_lf.f526.length != 0 ? false : m2062(c_lf.f527, false) != null;
         }
      } else if (s.equals("hasConverse")) {
         return !(c_vb instanceof C_LF) ? false : this.m2067((C_LF)c_vb) != null;
      } else {
         throw new IllegalArgumentException("unknown property: " + s);
      }
   }

   static C_RF m2061(C_RF c_rf) {
      return m2062(c_rf, true);
   }

   static C_RF m2062(C_RF c_rf, boolean flag) {
      if (c_rf == null) {
         return null;
      } else if (c_rf.f739.equals("->")) {
         return flag ? c_rf : null;
      } else {
         boolean flag1;
         for (flag1 = false; !c_rf.f739.equals("<->"); c_rf = c_rf.m1217(1)) {
            if (!c_rf.f739.equals("@")) {
               return null;
            }

            flag1 = true;
         }

         return flag1 ? c_rf.m1237() : c_rf;
      }
   }

   static C_RF m2063(C_RF c_rf, int i) {
      C_RF c_rf1 = m2062(c_rf, true);
      return c_rf1 == null ? null : c_rf1.m1217(i);
   }

   static C_RF m2064(C_RF c_rf, boolean flag, int i) {
      C_RF c_rf1 = m2062(c_rf, flag);
      return c_rf1 == null ? null : c_rf1.m1217(i);
   }

   static C_RF m2065(C_RF c_rf) {
      if (c_rf == null) {
         return null;
      } else {
         boolean flag;
         for (flag = false; !c_rf.f739.equals("->"); c_rf = c_rf.m1217(1)) {
            if (c_rf.f739.equals("<->")) {
               if (!c_rf.m1217(0).f739.equals("<->") && !c_rf.m1217(1).f739.equals("<->")) {
                  return null;
               }

               return flag ? c_rf.m1237() : c_rf;
            }

            if (!c_rf.f739.equals("@")) {
               return null;
            }

            flag = true;
         }

         if (!c_rf.m1217(1).f739.equals("<->")) {
            return null;
         } else {
            return flag ? c_rf.m1237() : c_rf;
         }
      }
   }

   static C_RF m2066(C_RF c_rf, int i, int j) {
      C_RF c_rf1 = m2065(c_rf);
      return c_rf1 == null ? null : c_rf1.m1217(i).m1217(j);
   }

   @Override
   public boolean hasProperty(Integer integer, String s) {
      C_QE c_qe = LogicProgram.m1025(integer);
      return c_qe == null ? true : this.hasProperty(c_qe, s);
   }

   Vector m2067(C_LF c_lf) {
      if (c_lf.f822 != null && m2062(c_lf.f822.f527, false) != null) {
         Vector vector1 = new Vector();
         vector1.addElement(c_lf.f822.f820);
         return vector1;
      } else if (this.hasProperty(c_lf, "biconditional")) {
         Vector vector = new Vector();
         vector.addElement(c_lf.f820);
         return vector;
      } else {
         return (Vector)this.get(c_lf.f820);
      }
   }

   void m2068(C_VB c_vb) {
      C_LF[] ac_lf = c_vb.m1373(this, "notConditional");
      int i = ac_lf.length;

      for (int j = 0; j < i; j++) {
         C_LF c_lf = ac_lf[j];
         if (!this.hasProperty(c_lf, "biconditional")) {
            C_RF c_rf = m2073(c_lf, false);
            C_RF c_rf1 = m2075(c_lf, false);

            for (int k = 0; k < i; k++) {
               C_LF c_lf1 = ac_lf[k];
               boolean flag = this.hasProperty(c_lf1, "biconditional");
               if (k >= j || flag) {
                  C_RF c_rf2 = m2073(c_lf1, false);
                  C_RF c_rf3 = m2075(c_lf1, false);
                  if (this.m2069(c_rf2, c_rf3, c_rf, c_rf1) || flag && this.m2069(c_rf3, c_rf2, c_rf, c_rf1)) {
                     this.m2070(c_lf, c_lf1);
                  }
               }
            }
         }
      }
   }

   boolean m2069(C_RF c_rf, C_RF c_rf1, C_RF c_rf2, C_RF c_rf3) {
      C_j_D c_j_d = new C_j_D();
      C_MB c_mb = new C_MB();
      C__B c__b = new C__B();
      if (!c_rf.m1267(c_rf3, c_j_d, c_mb)) {
         return false;
      } else if (!c_rf1.m1267(c_rf2, c_j_d, c_mb)) {
         return false;
      } else if (!c__b.m1572(c_rf, c_rf3, c_mb)) {
         return false;
      } else {
         return !c__b.m1572(c_rf1, c_rf2, c_mb) ? false : c_j_d.m1890();
      }
   }

   void m2070(C_LF c_lf, C_LF c_lf1) {
      Vector vector;
      if ((vector = (Vector)this.get(c_lf.f820)) == null) {
         this.put(c_lf.f820, vector = new Vector());
      }

      if (!vector.contains(c_lf1.f820)) {
      }

      vector.addElement(c_lf1.f820);
      if (!this.hasProperty(c_lf1, "biconditional")) {
         if ((vector = (Vector)this.get(c_lf1.f820)) == null) {
            this.put(c_lf1.f820, vector = new Vector());
         }

         if (!vector.contains(c_lf.f820)) {
            vector.addElement(c_lf.f820);
         }
      }
   }

   Vector m2071(C_z_B c_z_b) {
      Vector vector = new Vector();
      Vector vector1 = c_z_b.f1469;
      int i = vector1.size();

      for (int j = 0; j < i; j++) {
         String s = (String)vector1.elementAt(j);
         C_VB c_vb = c_z_b.m2203(s);
         if (c_vb.m1193(this, "hasConverse", true)) {
            vector.addElement(s);
         }
      }

      return vector;
   }

   C_n_F m2072(C_z_ c_z_) {
      C_n_F c_n_f = new C_n_F();
      Enumeration enumeration = c_z_.f1464.m1985();

      while (enumeration.hasMoreElements()) {
         Integer integer = (Integer)enumeration.nextElement();
         C_QE c_qe = c_z_.m2199(integer);
         if (c_qe.m1193(this, "hasConverse", true)) {
            c_n_f.m1975(C_n_F.m1970(c_qe.f700));
         }
      }

      return c_n_f;
   }

   static C_RF m2073(C_LF c_lf, boolean flag) {
      return c_lf.f526.length == 0 ? m2063(c_lf.f527, flag ? 1 : 0) : c_lf.f526[0];
   }

   static C_RF m2074(C_LF c_lf, boolean flag, boolean flag1) {
      return c_lf.f526.length == 0 ? m2066(c_lf.f527, flag ? 0 : 1, flag1 ? 1 : 0) : c_lf.f527.m1217(flag1 ? 1 : 0);
   }

   static C_RF m2075(C_LF c_lf, boolean flag) {
      return c_lf.f526.length == 0 ? m2063(c_lf.f527, flag ? 0 : 1) : c_lf.f527;
   }

   static C_RF m2076(C_LF c_lf, boolean flag, boolean flag1) {
      return c_lf.f526.length == 0 ? m2066(c_lf.f527, flag ? 0 : 1, flag1 ? 0 : 1) : c_lf.f527.m1217(flag1 ? 0 : 1);
   }

   @Override
   public Vector getProofs(C_LF c_lf) {
      return null;
   }

   @Override
   public Vector getProofs(Integer integer) {
      return null;
   }

   @Override
   public String excludedProof() {
      return null;
   }

   @Override
   public boolean checkProof(String s) {
      return true;
   }
}
