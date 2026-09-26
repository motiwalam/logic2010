package edu.ucla.phil.logic;

public abstract class Justification implements DerivationConstants, Cloneable {
   String f384;

   Justification(String s) {
      this.f384 = s;
   }

   @Override
   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException clonenotsupportedexception) {
         throw new RuntimeException("CloneNotSupportedException");
      }
   }

   abstract boolean m600(DerivationLineChecker derivationlinechecker);

   abstract String m609();

   static Justification m686(String s, LPDerivation lpderivation) {
      int i = s.indexOf(":");
      if (i == -1) {
         return null;
      } else {
         int j = Integer.parseInt(s.substring(0, i));
         if (j == 1) {
            return C_HF.m698(s);
         } else if (j == 2) {
            return C_l_.m1915(s);
         } else if (j == 3) {
            return C_p_E.m2027(s);
         } else {
            return j == 4 ? C_GA.m610(s, lpderivation) : null;
         }
      }
   }
}
