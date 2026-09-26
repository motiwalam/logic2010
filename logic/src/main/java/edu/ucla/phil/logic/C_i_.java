package edu.ucla.phil.logic;

import java.util.Vector;

public class C_i_ extends C_X {
   private C_RF f1172;

   public C_i_(String s) {
      super(s);
   }

   @Override
   void m1212() {
      this.f738 = 3;
   }

   public void m1848(C_RF c_rf) {
      this.f1172 = c_rf;
   }

   boolean m1849() {
      return this.f1172 != null;
   }

   C_RF m1850() {
      return this.f1172;
   }

   @Override
   boolean m1236(C_RF c_rf, C_MB c_mb) {
      if (!(c_rf instanceof C_i_)) {
         return false;
      } else {
         C_RF c_rf1 = ((C_i_)c_rf).f1172;
         if (this.f1172 != null) {
            if (c_rf1 == null) {
               return false;
            }

            boolean flag = this.m1261();
            boolean flag1 = ((C_i_)c_rf).m1261();
            if (flag != flag1) {
               return false;
            }

            if (flag) {
               return this.f1172.m1219(this.f739) == c_rf1.m1219(c_rf.f739);
            }

            if (c_mb != null) {
               return c_mb.m1091(this.f1172) == c_rf1;
            }
         } else if (c_rf1 != null) {
            return false;
         }

         return this.f739.equals(c_rf.f739);
      }
   }

   @Override
   C_RF m1240(C_RF c_rf, C_j_D c_j_d, C_MB c_mb, Vector vector) {
      C_RF c_rf1 = this.f1172 == null ? null : c_mb.m1092(c_rf, this.f1172, vector);
      if (c_j_d != null && c_rf == null && c_rf1 == null) {
         C_PB c_pb = new C_PB(this);
         C_GF c_gf = c_j_d.m1878(c_pb);
         if (c_gf != null) {
            return c_gf.f368.m1240(this, c_j_d, c_mb, vector);
         }

         if (this.f1172 != null) {
            c_j_d.m1886(c_pb);
         }
      }

      if (this.m1261() && c_rf != null) {
         vector.addElement(this);
         C_RF c_rf2 = c_rf.m1217(this.f1172.m1219(this.f739)).m1240(null, c_j_d, c_mb, vector);
         vector.removeElementAt(vector.size() - 1);
         return c_rf2;
      } else {
         C_i_ c_i_1 = new C_i_(this.f739);
         c_i_1.f1172 = c_rf1;
         return c_i_1;
      }
   }

   @Override
   void m1230(String s, C_e_ c_e_, Vector vector) {
      if (s != null) {
         if (this.m1262() && s.equals(this.f739)) {
            vector.addElement(c_e_.clone());
         }
      }
   }

   @Override
   int m1242(Vector vector, int i) {
      if (this.f1172 != null) {
         C_RF c_rf = this.f1172.m1217(0);
         if (this == c_rf && i < vector.size()) {
            String s = (String)vector.elementAt(i);
            if (s != null && !s.equals("")) {
               this.f739 = s;
            }

            i++;
         } else {
            this.f739 = c_rf.f739;
         }
      }

      return i;
   }

   @Override
   void m1258(C_JD c_jd) {
      this.f1172 = (C_RF)c_jd.get(this.f739);
   }

   @Override
   void m1260(C_JD c_jd, Vector vector) {
      C_RF c_rf = (C_RF)c_jd.get(this.f739);
      if (this.f1172 != c_rf) {
         vector.addElement(new C_RF[]{c_rf, this});
      }
   }

   @Override
   void m1264(C_RF c_rf) {
      if (this.f1172 == null && c_rf.m1219(this.f739) != -1) {
         this.f1172 = c_rf;
      }
   }

   @Override
   boolean m1265(C_RF c_rf) {
      return this.f739.equals(LogicProgram.m1015(this.f739)) && (c_rf == null || c_rf.m1219(this.f739) == -1);
   }

   @Override
   boolean m1261() {
      return this.f1172 != null && this.f1172.m1263();
   }

   @Override
   boolean m1262() {
      return this.f1172 != null && !this.f1172.m1263();
   }

   static boolean m1851(String s) {
      return m1852(s, false);
   }

   static boolean m1852(String s, boolean flag) {
      C_RF c_rf;
      try {
         c_rf = LogicProgram.m1008(s, true, flag);
      } catch (C_k_B c_k_b) {
         return false;
      }

      return c_rf instanceof C_i_;
   }

   @Override
   void m1253(Vector vector, Vector vector1) {
      if (vector != null && !vector.contains(this.f739)) {
         vector.addElement(this.f739);
      }

      if (!this.m1262() && vector1 != null && !vector1.contains(this.f739)) {
         vector1.addElement(this.f739);
      }
   }

   @Override
   void m1277(Vector vector) {
      if (!this.m1262() && !vector.contains(this.f739)) {
         vector.addElement(this.f739);
      }
   }

   @Override
   boolean m1268(C_RF c_rf, C_RF c_rf1, C_j_D c_j_d, C_MB c_mb, Vector vector) {
      if (this.f1172 == null) {
         if (c_rf1 != null) {
            return c_rf == null ? c_j_d.m1881(this, c_rf1) : super.m1268(c_rf, c_rf1, c_j_d, c_mb, vector);
         } else {
            return c_rf != null || c_j_d.m1885(this) && c_j_d.m1887(this, null, c_mb, vector);
         }
      } else if (this.m1261()) {
         vector.addElement(this);
         boolean flag = c_rf.m1217(this.f1172.m1219(this.f739)).m1268(null, c_rf1, c_j_d, c_mb, vector);
         vector.removeElementAt(vector.size() - 1);
         return flag;
      } else {
         return c_rf1 == null || c_rf1 instanceof C_i_ && ((C_i_)c_rf1).m1850() == c_mb.m1092(c_rf, this.f1172, vector);
      }
   }

   @Override
   C_RF m1247(Vector vector, int i, C_j_D c_j_d) {
      if (this.m1262()) {
         this.f739 = this.f1172.m1217(0).f739;
      }

      return this;
   }

   @Override
   boolean m1252(C_RF c_rf) {
      return this.f1172 == c_rf;
   }

   @Override
   void m1246(Vector vector) {
      C_i_A c_i_a = this.m1272();
      if (c_i_a != null && vector.indexOf(c_i_a) == -1) {
         vector.addElement(c_i_a);
      }
   }

   @Override
   C_i_A m1272() {
      return this.m1262() ? null : new C_PB(this);
   }

   @Override
   boolean m1273(C_j_D c_j_d) {
      return this.m1262() ? true : c_j_d.m1885(this);
   }

   @Override
   boolean m1274(C_j_D c_j_d) {
      return this.m1262() || c_j_d.m1878(this.m1272()) != null;
   }

   @Override
   boolean m1210() {
      return true;
   }

   @Override
   String m1207(int i) {
      return this.f739;
   }

   @Override
   String m1209(int i) {
      return this.f739;
   }

   @Override
   void m1211(C_DD c_dd) {
      super.m1211(c_dd);
      c_dd.f283 = this.f739.length();
   }
}
