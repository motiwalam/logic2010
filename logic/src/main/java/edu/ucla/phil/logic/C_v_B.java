package edu.ucla.phil.logic;

import java.awt.print.Printable;

class C_v_B extends C_l_B {
   TaggedRecord f1420;

   C_v_B(TaggedRecord taggedrecord, C_c_C c_c_c) {
      super(c_c_c);
      this.f1420 = taggedrecord;
   }

   @Override
   Printable m413() {
      return C_JE.m727(this.f1420, 20);
   }

   static void m2130(TaggedRecord taggedrecord, C_c_C c_c_c) {
      if (taggedrecord != null) {
         new C_v_B(taggedrecord, c_c_c).m1923();
      }
   }
}
