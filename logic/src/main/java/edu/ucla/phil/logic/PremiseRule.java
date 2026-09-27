package edu.ucla.phil.logic;

class PremiseRule extends SchematicRule {
   Integer premiseIndex;

   PremiseRule(LPDerivation lpderivation, int i) {
      super("Premise " + i);
      if (lpderivation.premises != null && i >= 1 && i <= lpderivation.premises.length) {
         this.premiseIndex = new Integer(i - 1);
         this.conclusion = lpderivation.premises[i - 1];
      } else {
         throw new IllegalArgumentException("no premise " + i);
      }
   }
}
