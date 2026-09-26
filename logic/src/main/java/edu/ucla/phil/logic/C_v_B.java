package edu.ucla.phil.logic;

import java.awt.print.Printable;

class C_v_B extends C_l_B {
   C_XD f1420;

   C_v_B(C_XD c_xd, C_c_C c_c_c) {
      super(c_c_c);
      this.f1420 = c_xd;
   }

   @Override
   Printable m413() {
      return C_JE.m727(this.f1420, 20);
   }

   static void m2130(C_XD c_xd, C_c_C c_c_c) {
      if (c_xd != null) {
         new C_v_B(c_xd, c_c_c).m1923();
      }
   }
}
