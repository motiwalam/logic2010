package edu.ucla.phil.logic;

import java.util.Vector;

public class C_q_A extends C_y_A {
   private C_y_A f1340;

   public C_q_A(String s) {
      super(s);
   }

   C_q_A(C_y_A c_y_a) {
      super(null);
      this.f1340 = c_y_a;
   }

   @Override
   void m1212() {
      this.f738 = 0;
   }

   public void m2028(C_X c_x) {
      this.f740.addElement(c_x);
      this.f741++;
   }

   C_y_A m2029() {
      return this.f1340;
   }

   @Override
   C_RF m1240(C_RF c_rf, C_j_D c_j_d, C_MB c_mb, Vector vector) {
      if (c_rf == null && c_j_d != null) {
         C_GF c_gf = c_j_d.m1878(this.m1272());
         if (c_gf != null) {
            return c_gf.f368.m1240(this, c_j_d, c_mb, vector);
         }
      }

      C_q_A c_q_a1 = new C_q_A(this.f739);

      for (int i = 0; i < this.f741; i++) {
         c_q_a1.m1215(this.m1217(i).m1240(c_rf, c_j_d, c_mb, vector));
      }

      return c_q_a1;
   }

   @Override
   boolean m1268(C_RF c_rf, C_RF c_rf1, C_j_D c_j_d, C_MB c_mb, Vector vector) {
      return c_rf != null ? super.m1268(c_rf, c_rf1, c_j_d, c_mb, vector) : this.m1269(c_rf1, c_j_d, c_mb, vector);
   }

   @Override
   C_c_B m1270(C_RF c_rf) {
      return c_rf != null && !(c_rf instanceof C_y_A)
         ? new C_c_B("dererr068", C_H.m667("pattern", "\\l" + this + "\\l", "replacement", "\\l" + c_rf + "\\l"))
         : null;
   }

   @Override
   void m1246(Vector vector) {
      C_i_A c_i_a = this.m1272();
      if (vector.indexOf(c_i_a) == -1) {
         vector.addElement(c_i_a);
      }

      super.m1246(vector);
   }

   @Override
   C_i_A m1272() {
      return new C_w_C(this);
   }

   @Override
   boolean m1273(C_j_D c_j_d) {
      return c_j_d.m1885(this) & super.m1273(c_j_d);
   }

   @Override
   boolean m1274(C_j_D c_j_d) {
      return c_j_d.m1878(this.m1272()) == null ? false : super.m1274(c_j_d);
   }

   @Override
   boolean m1263() {
      return true;
   }

   static boolean m2030(String s) {
      return LogicProgram.f600.indexOf(s.charAt(0)) != -1;
   }

   @Override
   boolean m1210() {
      return !m2030(this.f739);
   }

   @Override
   String m1207(int i) {
      String s = this.f739;
      boolean flag = this.f741 > 1 || this.f741 > 0 && this.m1210();
      if (flag) {
         s = s + "(";
      }

      for (int j = 0; j < this.f741; j++) {
         s = s + this.m1217(j).m1207(i);
      }

      if (flag) {
         s = s + ")";
      }

      return s;
   }

   @Override
   String m1209(int i) {
      String s = this.f739;
      boolean flag = this.f741 > 1 || this.f741 > 0 && this.m1210();
      if (flag) {
         s = s + "(";
      }

      for (int j = 0; j < this.f741; j++) {
         s = s + this.m1217(j).m1209(i);
      }

      if (flag) {
         s = s + ")";
      }

      return s;
   }

   @Override
   void m1211(C_DD c_dd) {
      super.m1211(c_dd);
      c_dd.f283 = this.f739.length();
      int i = c_dd.m457();

      for (int j = 0; j < i; j++) {
         C_DD c_dd1 = c_dd.m458(j);
         c_dd1.f282 = c_dd.f283;
         c_dd.f283 = c_dd.f283 + c_dd1.f283;
      }
   }
}
