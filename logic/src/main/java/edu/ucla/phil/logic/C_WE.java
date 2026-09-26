package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Vector;

public class C_WE extends C_X {
   private C_i_ f862;
   private C_y_A f863;

   public C_WE(String s) {
      super(s);
   }

   @Override
   void m1212() {
      this.f738 = 5;
   }

   public void m1455(C_i_ c_i_) {
      this.f740.addElement(c_i_);
      this.f741++;
   }

   public void m1456(C_y_A c_y_a) {
      this.f740.addElement(c_y_a);
      this.f741++;
   }

   C_i_ m1457() {
      return (C_i_)this.m1217(0);
   }

   C_y_A m1458() {
      return (C_y_A)this.m1217(1);
   }

   @Override
   C_RF m1222(int[] aint, int i, int j, Vector vector) {
      if (i == j) {
         return this;
      } else {
         if (vector != null) {
            vector.addElement(this);
         }

         C_RF c_rf = this.m1217(aint[i]);
         return c_rf == null ? null : c_rf.m1222(aint, i + 1, j, vector);
      }
   }

   @Override
   boolean m1236(C_RF c_rf, C_MB c_mb) {
      if (c_rf == null) {
         return false;
      } else if (this.f738 == c_rf.f738 && this.f739.equals(c_rf.f739) && this.f741 == c_rf.f741) {
         if (c_mb != null) {
            c_mb.m1093(this, c_rf);
         }

         for (int i = 0; i < this.f741; i++) {
            if (!this.m1217(i).m1236(c_rf.m1217(i), c_mb)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   C_RF m1240(C_RF c_rf, C_j_D c_j_d, C_MB c_mb, Vector vector) {
      C_WE c_we1 = new C_WE(this.f739);
      c_mb.m1094(c_rf, this, vector, c_we1);

      for (int i = 0; i < this.f741; i++) {
         c_we1.m1215(this.m1217(i).m1240(c_rf, c_j_d, c_mb, vector));
      }

      return c_we1;
   }

   @Override
   C_RF m1247(Vector vector, int i, C_j_D c_j_d) {
      if (!this.m1217(1).m1252(this)) {
         return this.m1217(1).m1247(vector, i, c_j_d);
      } else {
         ((C_i_)this.m1217(0)).f739 = C_i_A.m1853(i);
         super.m1247(vector, i + 1, c_j_d);
         Enumeration enumeration = c_j_d.keys();
         C_i_A c_i_a = null;
         C_RF c_rf = this.m1217(1).m1237();

         while (enumeration.hasMoreElements()) {
            C_i_A c_i_a1 = (C_i_A)enumeration.nextElement();
            if (c_i_a1 instanceof C_W) {
               C_GF c_gf = c_j_d.m1878(c_i_a1);
               if (C_HA.m681(c_rf, c_gf.f368.m1217(1).m1237())) {
                  c_i_a = c_i_a1;
                  break;
               }
            }
         }

         if (c_i_a == null) {
            c_i_a = C_W.m1423(i, vector);
            if (!c_j_d.m1881(c_i_a.m1175(), this)) {
               throw new RuntimeException("could not terminate a descriptive");
            }
         }

         return c_i_a.m1175();
      }
   }

   @Override
   C_RF m1251(int i, String s, boolean flag) {
      if (flag) {
         super.m1251(i, s, true).m1257();
      }

      return this;
   }

   @Override
   Vector m1244(Vector vector) {
      vector.addElement(this);
      return super.m1244(vector);
   }

   @Override
   void m1258(C_JD c_jd) {
      c_jd.m723(this.m1217(0).f739, this);
      super.m1258(c_jd);
      c_jd.m724(this.m1217(0).f739);
   }

   @Override
   void m1260(C_JD c_jd, Vector vector) {
      c_jd.m723(this.m1217(0).f739, this);
      super.m1260(c_jd, vector);
      c_jd.m724(this.m1217(0).f739);
   }

   @Override
   boolean m1268(C_RF c_rf, C_RF c_rf1, C_j_D c_j_d, C_MB c_mb, Vector vector) {
      if (c_rf1 != null) {
         if (this.f738 != c_rf1.f738 || !this.f739.equals(c_rf1.f739) || this.f741 != c_rf1.f741) {
            return false;
         }

         c_mb.m1094(c_rf, this, vector, c_rf1);
      }

      for (int i = 0; i < this.f741; i++) {
         if (!this.m1217(i).m1268(c_rf, c_rf1 == null ? null : c_rf1.m1217(i), c_j_d, c_mb, vector)) {
            return false;
         }
      }

      return true;
   }

   String m1459(C_RF c_rf, int i) {
      return c_rf instanceof C_q_F && c_rf.f741 > 1 ? "(" + c_rf.m1207(i) + ")" : c_rf.m1207(i);
   }

   @Override
   String m1207(int i) {
      return this.f739 + this.m1217(0) + this.m1459(this.m1217(1), i);
   }

   @Override
   String m1209(int i) {
      return this.f739 + this.m1217(0) + ((C_y_A)this.m1217(1)).m1209(i);
   }

   @Override
   void m1211(C_DD c_dd) {
      super.m1211(c_dd);
      c_dd.f283 = (c_dd.m458(1).f282 = (c_dd.m458(0).f282 = this.f739.length()) + c_dd.m458(0).f283) + c_dd.m458(1).f283;
   }
}
