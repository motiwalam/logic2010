package edu.ucla.phil.logic;

class BiconditionalAssumptionJustification extends Justification {
   static final int TYPE = 5;
   boolean rightSide;

   BiconditionalAssumptionJustification(boolean flag) {
      super("ASS BD");
      this.rightSide = flag;
   }

   @Override
   boolean reapply(DerivationLineChecker derivationlinechecker) {
      if (!derivationlinechecker.ruleName.equals(this.label)) {
         return false;
      } else {
         derivationlinechecker.getClass();
         if ((derivationlinechecker.matchLine || derivationlinechecker.finalStep) && derivationlinechecker.argumentCount != 0) {
            return false;
         } else if (derivationlinechecker.result == null && derivationlinechecker.line.getIndexInBox() == 1) {
            Expression expression = derivationlinechecker.line.box.getFormula();
            if (expression == null) {
               return false;
            } else if (!expression.getSymbol().equals("<->")) {
               return false;
            } else {
               derivationlinechecker.line.box.assumptionType = 3;
               derivationlinechecker.result = expression.getChild(derivationlinechecker.line.box.assumedSide = this.rightSide ? 1 : 0);
               if (derivationlinechecker.matchLine && !derivationlinechecker.checkResultMatchesLine(true)) {
                  return false;
               } else {
                  derivationlinechecker.popStack(0);
                  return true;
               }
            }
         } else {
            return false;
         }
      }
   }

   @Override
   String encode() {
      return "5:" + (this.rightSide ? "R" : "L");
   }

   static BiconditionalAssumptionJustification decode(String s) {
      int i;
      if ((i = s.indexOf(":")) == -1) {
         return null;
      } else if (!s.substring(0, i).equals(Integer.toString(5))) {
         return null;
      } else {
         s = s.substring(i + 1);
         boolean flag = s.equals("R");
         return new BiconditionalAssumptionJustification(flag);
      }
   }
}
