package edu.ucla.phil.logic;

class C_ZA extends C_VD {
   C_G f908;

   C_ZA(LPDerivation lpderivation, int i) {
      super("Line " + i);
      C_0B c_0b = lpderivation.problem.m32(i);
      if (c_0b == null) {
         throw new IllegalArgumentException("cannot find line " + i);
      } else {
         this.f908 = c_0b instanceof C_G ? (C_G)c_0b : ((C__)c_0b).f917;
         this.f834 = this.f908.f332;
      }
   }

   C_ZA(C_G c_g) {
      super("Line " + c_g.m30());
      this.f908 = c_g;
      this.f834 = c_g.f332;
   }
}
