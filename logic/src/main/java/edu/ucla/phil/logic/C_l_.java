package edu.ucla.phil.logic;

class C_l_ extends Justification {
   static final int f1223 = 2;
   int f1224;

   C_l_(int i) {
      super("PR" + (i + 1));
      this.f1224 = i;
   }

   @Override
   boolean m600(DerivationLineChecker derivationlinechecker) {
      if (!derivationlinechecker.f939.equals(this.f384) && !derivationlinechecker.f939.equals("PR")) {
         return false;
      } else {
         derivationlinechecker.getClass();
         if ((derivationlinechecker.f944 || derivationlinechecker.f945) && derivationlinechecker.f938 != 0) {
            return false;
         } else {
            Expression[] aexpression = derivationlinechecker.f935.f317.f915.premises;
            if (this.f1224 >= aexpression.length) {
               return false;
            } else {
               derivationlinechecker.f942 = aexpression[this.f1224];
               if (derivationlinechecker.f944 && !derivationlinechecker.m1609(true)) {
                  return false;
               } else {
                  derivationlinechecker.m1631(0);
                  return true;
               }
            }
         }
      }
   }

   @Override
   String m609() {
      return "2:" + this.f1224;
   }

   static C_l_ m1915(String s) {
      int i;
      if ((i = s.indexOf(":")) == -1) {
         return null;
      } else if (!s.substring(0, i).equals(Integer.toString(2))) {
         return null;
      } else {
         s = s.substring(i + 1);
         int j = Integer.parseInt(s);
         return j < 0 ? null : new C_l_(j);
      }
   }
}
