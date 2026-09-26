package edu.ucla.phil.logic;

class C_c_E extends C_HD {
   static final int f1030 = 5;
   boolean f1031;

   C_c_E(boolean flag) {
      super("ASS BD");
      this.f1031 = flag;
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
            } else if (!c_rf.m1214().equals("<->")) {
               return false;
            } else {
               c_a_.f935.f317.f920 = 3;
               c_a_.f942 = c_rf.m1217(c_a_.f935.f317.f923 = this.f1031 ? 1 : 0);
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
