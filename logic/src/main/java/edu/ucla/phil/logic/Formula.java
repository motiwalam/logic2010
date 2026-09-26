package edu.ucla.phil.logic;

import java.util.Vector;

public abstract class Formula extends Expression {
   Formula(String s) {
      super(s);
   }

   @Override
   Expression universalClosure() {
      Object object = this.copy();
      Vector vector = this.getFreeVariables();
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         QuantifiedFormula quantifiedformula = new QuantifiedFormula("@");
         quantifiedformula.addChild(new SimpleTerm((String)vector.elementAt(j)));
         quantifiedformula.addChild((Expression)object);
         object = quantifiedformula;
      }

      ((Expression)object).m1257();
      return (Expression)object;
   }
}
