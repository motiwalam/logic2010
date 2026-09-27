package edu.ucla.phil.logic;

class IndirectAssumptionJustification extends Justification {
   static final int TYPE = 3;
   boolean negateShow;

   IndirectAssumptionJustification(boolean flag) {
      super("ASS ID");
      this.negateShow = flag;
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
            } else if (!this.negateShow && !expression.getSymbol().equals("~")) {
               return false;
            } else {
               derivationlinechecker.line.box.assumptionType = 1;
               derivationlinechecker.result = (Expression)(this.negateShow ? expression.negate() : expression.getChild(0));
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
      return "3:" + (this.negateShow ? "+" : "-");
   }

   static IndirectAssumptionJustification decode(String s) {
      int i;
      if ((i = s.indexOf(":")) == -1) {
         return null;
      } else if (!s.substring(0, i).equals(Integer.toString(3))) {
         return null;
      } else {
         s = s.substring(i + 1);
         boolean flag = !s.equals("-");
         return new IndirectAssumptionJustification(flag);
      }
   }
}
