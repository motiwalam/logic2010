package edu.ucla.phil.logic;

class RuleInstance extends SchematicRule {
   SchematicRule baseRule;
   SchemeInstantiation instantiation;

   RuleInstance(SchematicRule schematicrule, SchemeInstantiation schemeinstantiation) {
      super("Instance of " + schematicrule.name);
      this.baseRule = schematicrule;
      this.instantiation = schemeinstantiation;
      if (schematicrule != null && schematicrule.conclusion != null) {
         this.conclusion = schematicrule.conclusion.instantiate(schemeinstantiation);
         this.conclusion = this.conclusion.universalClosure();
      }
   }

   String encode() {
      return this.baseRule.name + "," + this.instantiation.encode();
   }

   static RuleInstance decode(String s) {
      int i = s.indexOf(44);
      Rule rule = LPDerivation.getRule(i == -1 ? s : s.substring(0, i));
      SchemeInstantiation schemeinstantiation = i == -1 ? null : SchemeInstantiation.decode(s.substring(i + 1));
      return !(rule instanceof SchematicRule) ? null : new RuleInstance((SchematicRule)rule, schemeinstantiation);
   }
}
