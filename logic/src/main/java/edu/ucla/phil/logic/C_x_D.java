package edu.ucla.phil.logic;

import java.util.Vector;

public class C_x_D extends C_y_A {
   public C_x_D(String s) {
      super(s);
   }

   @Override
   void m1212() {
      this.f738 = 6;
   }

   public void m2176(C_X c_x) {
      this.f740.addElement(c_x);
      this.f741++;
   }

   public void m2177(C_X c_x) {
      this.f740.addElement(c_x);
      this.f741++;
   }

   @Override
   C_RF m1247(Vector vector, int i, C_j_D c_j_d) {
      super.m1247(vector, i, c_j_d);
      C_RF c_rf = this.m1217(0);
      C_RF c_rf1 = this.m1217(1);
      if (c_rf.m1206().compareTo(c_rf1.m1206()) > 0) {
         this.f740.setElementAt(c_rf, 1);
         this.f740.setElementAt(c_rf1, 0);
      }

      return this;
   }

   @Override
   C_RF m1240(C_RF c_rf, C_j_D c_j_d, C_MB c_mb, Vector vector) {
      C_x_D c_x_d1 = new C_x_D(this.f739);

      for (int i = 0; i < this.f741; i++) {
         c_x_d1.m1215(this.m1217(i).m1240(c_rf, c_j_d, c_mb, vector));
      }

      return c_x_d1;
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
