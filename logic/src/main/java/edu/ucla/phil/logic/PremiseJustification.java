package edu.ucla.phil.logic;

class PremiseJustification extends Justification {
   static final int TYPE = 2;
   int premiseIndex;

   PremiseJustification(int i) {
      super("PR" + (i + 1));
      this.premiseIndex = i;
   }

   @Override
   boolean reapply(DerivationLineChecker derivationlinechecker) {
      if (!derivationlinechecker.ruleName.equals(this.label) && !derivationlinechecker.ruleName.equals("PR")) {
         return false;
      } else {
         derivationlinechecker.getClass();
         if ((derivationlinechecker.matchLine || derivationlinechecker.finalStep) && derivationlinechecker.argumentCount != 0) {
            return false;
         } else {
            Expression[] aexpression = derivationlinechecker.line.box.module.premises;
            if (this.premiseIndex >= aexpression.length) {
               return false;
            } else {
               derivationlinechecker.result = aexpression[this.premiseIndex];
               if (derivationlinechecker.matchLine && !derivationlinechecker.checkResultMatchesLine(true)) {
                  return false;
               } else {
                  derivationlinechecker.popStack(0);
                  return true;
               }
            }
         }
      }
   }

   @Override
   String encode() {
      return "2:" + this.premiseIndex;
   }

   static PremiseJustification decode(String s) {
      int i;
      if ((i = s.indexOf(":")) == -1) {
         return null;
      } else if (!s.substring(0, i).equals(Integer.toString(2))) {
         return null;
      } else {
         s = s.substring(i + 1);
         int j = Integer.parseInt(s);
         return j < 0 ? null : new PremiseJustification(j);
      }
   }
}
