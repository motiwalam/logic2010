package edu.ucla.phil.logic;

class C_l_F extends C_LF {
   C_G f1263;

   C_l_F(LPDerivation lpderivation, int i) {
      super("Line " + i);
      C_0B c_0b = lpderivation.problem.m32(i);
      if (c_0b == null) {
         throw new IllegalArgumentException("cannot find line " + i);
      } else {
         this.f1263 = c_0b instanceof C_G ? (C_G)c_0b : ((C__)c_0b).f917;
         this.f527 = this.f1263.f332;
      }
   }

   C_l_F(C_G c_g) {
      super("Line " + c_g.m30());
      this.f1263 = c_g;
      this.f527 = c_g.f332;
   }
}
