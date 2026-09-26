package edu.ucla.phil.logic;

class C_p_E extends Justification {
   static final int f1336 = 3;
   boolean f1337;

   C_p_E(boolean flag) {
      super("ASS ID");
      this.f1337 = flag;
   }

   @Override
   boolean m600(DerivationLineChecker derivationlinechecker) {
      if (!derivationlinechecker.f939.equals(this.f384)) {
         return false;
      } else {
         derivationlinechecker.getClass();
         if ((derivationlinechecker.f944 || derivationlinechecker.f945) && derivationlinechecker.f938 != 0) {
            return false;
         } else if (derivationlinechecker.f942 == null && derivationlinechecker.f935.m16() == 1) {
            Expression expression = derivationlinechecker.f935.f317.m44();
            if (expression == null) {
               return false;
            } else if (!this.f1337 && !expression.getSymbol().equals("~")) {
               return false;
            } else {
               derivationlinechecker.f935.f317.f920 = 1;
               derivationlinechecker.f942 = (Expression)(this.f1337 ? expression.negate() : expression.getChild(0));
               if (derivationlinechecker.f944 && !derivationlinechecker.m1609(true)) {
                  return false;
               } else {
                  derivationlinechecker.m1631(0);
                  return true;
               }
            }
         } else {
            return false;
         }
      }
   }

   @Override
   String m609() {
      return "3:" + (this.f1337 ? "+" : "-");
   }

   static C_p_E m2027(String s) {
      int i;
      if ((i = s.indexOf(":")) == -1) {
         return null;
      } else if (!s.substring(0, i).equals(Integer.toString(3))) {
         return null;
      } else {
         s = s.substring(i + 1);
         boolean flag = !s.equals("-");
         return new C_p_E(flag);
      }
   }
}
