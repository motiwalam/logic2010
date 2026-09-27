package edu.ucla.phil.logic;

class PremiseExpression extends LabeledExpression {
   Integer premiseIndex;

   PremiseExpression(LPDerivation lpderivation, int i) {
      super("Premise " + i);
      if (lpderivation.premises != null && i >= 1 && i <= lpderivation.premises.length) {
         this.premiseIndex = new Integer(i - 1);
         this.expression = lpderivation.premises[i - 1];
      } else {
         throw new IllegalArgumentException("no premise " + i);
      }
   }
}
