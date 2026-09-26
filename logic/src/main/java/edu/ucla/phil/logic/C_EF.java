package edu.ucla.phil.logic;

class C_EF extends C_f_F {
   int f298;
   static final int f299 = 0;
   static final int f300 = 1;
   static final int f301 = 2;

   C_EF(String s, C_e_D c_e_d) {
      super(s, true, null);
      if (c_e_d.m1768(s) == null) {
         this.f298 = 2;
      } else if (c_e_d.m1772(C_XD.m1493(s)) == null) {
         this.f298 = 0;
      } else {
         this.f298 = 1;
      }
   }

   @Override
   int m513(String s) {
      return 0;
   }
}
