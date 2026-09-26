package edu.ucla.phil.logic;

class C_PF extends SchematicRule {
   SchematicRule f696;
   SchemeInstantiation f697;

   C_PF(SchematicRule schematicrule, SchemeInstantiation schemeinstantiation) {
      super("Instance of " + schematicrule.f820);
      this.f696 = schematicrule;
      this.f697 = schemeinstantiation;
      if (schematicrule != null && schematicrule.conclusion != null) {
         this.conclusion = schematicrule.conclusion.instantiate(schemeinstantiation);
         this.conclusion = this.conclusion.universalClosure();
      }
   }

   String m1182() {
      return this.f696.f820 + "," + this.f697.m1896();
   }

   static C_PF m1183(String s) {
      int i = s.indexOf(44);
      Rule rule = LPDerivation.getRule(i == -1 ? s : s.substring(0, i));
      SchemeInstantiation schemeinstantiation = i == -1 ? null : SchemeInstantiation.m1897(s.substring(i + 1));
      return !(rule instanceof SchematicRule) ? null : new C_PF((SchematicRule)rule, schemeinstantiation);
   }
}
