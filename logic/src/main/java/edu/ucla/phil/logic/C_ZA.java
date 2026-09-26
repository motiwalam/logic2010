package edu.ucla.phil.logic;

class C_ZA extends C_VD {
   DerivationLine f908;

   C_ZA(LPDerivation lpderivation, int i) {
      super("Line " + i);
      DerivationNode derivationnode = lpderivation.problem.m32(i);
      if (derivationnode == null) {
         throw new IllegalArgumentException("cannot find line " + i);
      } else {
         this.f908 = derivationnode instanceof DerivationLine ? (DerivationLine)derivationnode : ((DerivationBox)derivationnode).f917;
         this.f834 = this.f908.f332;
      }
   }

   C_ZA(DerivationLine derivationline) {
      super("Line " + derivationline.m30());
      this.f908 = derivationline;
      this.f834 = derivationline.f332;
   }
}
