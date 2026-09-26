package edu.ucla.phil.logic;

import java.util.Vector;

class C_r_C implements C_i_D, C_v_D {
   boolean f1353 = false;

   @Override
   public void m68(C_d_C c_d_c, C_d_C c_d_c1, Vector vector, Vector vector1) {
   }

   @Override
   public void m69(C_d_C c_d_c, C_d_C c_d_c1, Vector vector, Vector vector1) {
      C_VE c_ve = c_d_c.m1685();
      if (c_ve != null) {
         c_ve.m1393(c_d_c, c_d_c1, vector, vector1);
      }

      C_d_C c_d_c2 = c_d_c.m1688();
      C_d_C c_d_c3 = c_d_c1.m1688();
      if (c_d_c2 != null
         && c_d_c3 != null
         && c_d_c2.f1048 == c_d_c3.f1048
         && c_d_c1.f1048 != c_d_c.f1048
         && (c_d_c3.f1048 == 6 && c_d_c1.f1048 == 2 || c_d_c3.f1048 == 7 && c_d_c1.f1048 == 3)) {
         this.f1353 = true;
      }
   }
}
