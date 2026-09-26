package edu.ucla.phil.logic;

class C_p_F extends C_D {
   int f1338 = 1;
   String f1339;

   C_p_F(String s, String s1) {
      super(s);
      this.f1339 = s1;
   }

   @Override
   boolean m451(C_UA c_ua) {
      String s = this.m450(c_ua);
      if (s == null) {
         return true;
      } else if (s.equalsIgnoreCase("backup")) {
         C_KC.m896(LogicProgram.f567, null);
         return false;
      } else if (s.equalsIgnoreCase("update")) {
         C_Q.m1184(this.f1339.replaceAll(" ", "%20"));
         return false;
      } else if (s.equalsIgnoreCase("quit")) {
         this.f1338 = 3;
         return true;
      } else {
         return true;
      }
   }
}
