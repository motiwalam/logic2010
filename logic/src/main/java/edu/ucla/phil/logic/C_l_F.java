package edu.ucla.phil.logic;

class C_l_F extends SchematicRule {
   DerivationLine f1263;

   C_l_F(LPDerivation lpderivation, int i) {
      super("Line " + i);
      DerivationNode derivationnode = lpderivation.problem.m32(i);
      if (derivationnode == null) {
         throw new IllegalArgumentException("cannot find line " + i);
      } else {
         this.f1263 = derivationnode instanceof DerivationLine ? (DerivationLine)derivationnode : ((DerivationBox)derivationnode).f917;
         this.conclusion = this.f1263.f332;
      }
   }

   C_l_F(DerivationLine derivationline) {
      super("Line " + derivationline.m30());
      this.f1263 = derivationline;
      this.conclusion = derivationline.f332;
   }
}
