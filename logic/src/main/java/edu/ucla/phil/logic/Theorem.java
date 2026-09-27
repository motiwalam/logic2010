package edu.ucla.phil.logic;

import java.util.Vector;

class Theorem extends SchematicRule {
   Integer number;

   Theorem(Integer integer, String s) {
      super("T" + integer, ".:" + s);
      this.number = integer;
   }

   Theorem(Integer integer, Expression expression) {
      super("T" + integer, null, expression);
      this.number = integer;
   }

   Integer getNumber() {
      return this.number;
   }

   Expression getFormula() {
      return this.conclusion;
   }

   @Override
   boolean testProperty(RulePropertySource rulepropertysource, String s, boolean flag) {
      return rulepropertysource.hasProperty(this.number, s);
   }

   @Override
   Vector getProofProblems(RulePropertySource rulepropertysource) {
      return rulepropertysource.getProofs(this.number);
   }

   @Override
   public String toString() {
      return this.format("", "");
   }

   @Override
   String format(String s1, String s) {
      return s + this.conclusion.toString();
   }
}
