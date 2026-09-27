package edu.ucla.phil.logic;

class LabeledExpression {
   String label;
   Expression expression;

   LabeledExpression(String s) {
      this(s, null);
   }

   LabeledExpression(String s, Expression expressionx) {
      this.label = s;
      this.expression = expressionx;
   }
}
