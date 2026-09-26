package edu.ucla.phil.logic;

class C_c_E extends Justification {
   static final int f1030 = 5;
   boolean f1031;

   C_c_E(boolean flag) {
      super("ASS BD");
      this.f1031 = flag;
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
            } else if (!expression.getSymbol().equals("<->")) {
               return false;
            } else {
               derivationlinechecker.f935.f317.f920 = 3;
               derivationlinechecker.f942 = expression.getChild(derivationlinechecker.f935.f317.f923 = this.f1031 ? 1 : 0);
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
      return "5:" + (this.f1031 ? "R" : "L");
   }

   static C_c_E m1672(String s) {
      int i;
      if ((i = s.indexOf(":")) == -1) {
         return null;
      } else if (!s.substring(0, i).equals(Integer.toString(5))) {
         return null;
      } else {
         s = s.substring(i + 1);
         boolean flag = s.equals("R");
         return new C_c_E(flag);
      }
   }
}
