package edu.ucla.phil.logic;

public abstract class Justification implements DerivationConstants, Cloneable {
   String label;

   Justification(String s) {
      this.label = s;
   }

   @Override
   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException clonenotsupportedexception) {
         throw new RuntimeException("CloneNotSupportedException");
      }
   }

   abstract boolean reapply(DerivationLineChecker derivationlinechecker);

   abstract String encode();

   static Justification decode(String s, LPDerivation lpderivation) {
      int i = s.indexOf(":");
      if (i == -1) {
         return null;
      } else {
         int j = Integer.parseInt(s.substring(0, i));
         if (j == 1) {
            return RuleApplication.decode(s);
         } else if (j == 2) {
            return PremiseJustification.decode(s);
         } else if (j == 3) {
            return IndirectAssumptionJustification.decode(s);
         } else {
            return j == 4 ? InterchangeJustification.decodeInterchange(s, lpderivation) : null;
         }
      }
   }
}
