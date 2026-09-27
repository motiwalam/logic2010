package edu.ucla.phil.logic;

class LineFormula extends LabeledExpression {
   DerivationLine line;

   LineFormula(LPDerivation lpderivation, int i) {
      super("Line " + i);
      DerivationNode derivationnode = lpderivation.problem.findLine(i);
      if (derivationnode == null) {
         throw new IllegalArgumentException("cannot find line " + i);
      } else {
         this.line = derivationnode instanceof DerivationLine ? (DerivationLine)derivationnode : ((DerivationBox)derivationnode).showLine;
         this.expression = this.line.formula;
      }
   }

   LineFormula(DerivationLine derivationline) {
      super("Line " + derivationline.getLineNumber());
      this.line = derivationline;
      this.expression = derivationline.formula;
   }
}
