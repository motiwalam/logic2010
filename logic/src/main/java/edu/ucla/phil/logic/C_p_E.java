package edu.ucla.phil.logic;

class C_p_E extends C_HD {
   static final int f1336 = 3;
   boolean f1337;

   C_p_E(boolean flag) {
      super("ASS ID");
      this.f1337 = flag;
   }

   @Override
   boolean m600(C_a_ c_a_) {
      if (!c_a_.f939.equals(this.f384)) {
         return false;
      } else {
         c_a_.getClass();
         if ((c_a_.f944 || c_a_.f945) && c_a_.f938 != 0) {
            return false;
         } else if (c_a_.f942 == null && c_a_.f935.m16() == 1) {
            C_RF c_rf = c_a_.f935.f317.m44();
            if (c_rf == null) {
               return false;
            } else if (!this.f1337 && !c_rf.m1214().equals("~")) {
               return false;
            } else {
               c_a_.f935.f317.f920 = 1;
               c_a_.f942 = (C_RF)(this.f1337 ? c_rf.m1256() : c_rf.m1217(0));
               if (c_a_.f944 && !c_a_.m1609(true)) {
                  return false;
               } else {
                  c_a_.m1631(0);
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
