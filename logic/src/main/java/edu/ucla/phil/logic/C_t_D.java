package edu.ucla.phil.logic;

import java.util.Vector;

public class C_t_D extends C_y_A {
   public C_t_D(String s) {
      super(s);
   }

   @Override
   void m1212() {
      this.f738 = 7;
   }

   public void m2087(C_X c_x) {
      this.f740.addElement(c_x);
      this.f741++;
   }

   public void m2088(C_X c_x) {
      this.f740.addElement(c_x);
      this.f741++;
   }

   @Override
   C_RF m1240(C_RF c_rf, C_j_D c_j_d, C_MB c_mb, Vector vector) {
      C_t_D c_t_d1 = new C_t_D(this.f739);

      for (int i = 0; i < this.f741; i++) {
         c_t_d1.m1215(this.m1217(i).m1240(c_rf, c_j_d, c_mb, vector));
      }

      return c_t_d1;
   }

   @Override
   String m1207(int i) {
      return this.m1217(0).m1207(i) + this.f739 + this.m1217(1).m1207(i);
   }

   @Override
   String m1209(int i) {
      return this.m1217(0).m1209(i) + this.f739 + this.m1217(1).m1209(i);
   }

   @Override
   void m1211(C_DD c_dd) {
      super.m1211(c_dd);
      c_dd.f283 = (c_dd.m458(1).f282 = (c_dd.m458(0).f282 = 0) + c_dd.m458(0).f283 + this.f739.length()) + c_dd.m458(1).f283;
   }
}
