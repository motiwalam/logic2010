package edu.ucla.phil.logic;

class C_SE extends C_D {
   int f772 = 1;
   C_j_B f773;

   C_SE(String s, C_j_B c_j_b) {
      super(s);
      this.f773 = c_j_b;
   }

   @Override
   int m446() {
      return 0;
   }

   @Override
   boolean m451(C_UA c_ua) {
      String s = this.m450(c_ua);
      if (s == null) {
         return true;
      } else {
         if (s.equalsIgnoreCase("backup")) {
            if (C_KC.m896(LogicProgram.f567, null)) {
               C_KC.f486 = true;
               this.f772 = C_KC.m872(this.f773);
            } else {
               this.f772 = 5;
            }
         } else if (s.equalsIgnoreCase("update")) {
            this.f772 = C_KC.m872(this.f773);
         }

         return true;
      }
   }
}
