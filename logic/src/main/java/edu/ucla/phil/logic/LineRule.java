package edu.ucla.phil.logic;

class LineRule extends SchematicRule {
   DerivationLine line;

   LineRule(LPDerivation lpderivation, int i) {
      super("Line " + i);
      DerivationNode derivationnode = lpderivation.problem.findLine(i);
      if (derivationnode == null) {
         throw new IllegalArgumentException("cannot find line " + i);
      } else {
         this.line = derivationnode instanceof DerivationLine ? (DerivationLine)derivationnode : ((DerivationBox)derivationnode).showLine;
         this.conclusion = this.line.formula;
      }
   }

   LineRule(DerivationLine derivationline) {
      super("Line " + derivationline.getLineNumber());
      this.line = derivationline;
      this.conclusion = derivationline.formula;
   }
}
