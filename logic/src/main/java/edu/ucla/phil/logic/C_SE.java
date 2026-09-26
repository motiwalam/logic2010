package edu.ucla.phil.logic;

class C_SE extends DialogHandler {
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
   boolean m451(MessageDialog messagedialog) {
      String s = this.m450(messagedialog);
      if (s == null) {
         return true;
      } else {
         if (s.equalsIgnoreCase("backup")) {
            if (ServerConnection.m896(LogicProgram.f567, null)) {
               ServerConnection.f486 = true;
               this.f772 = ServerConnection.m872(this.f773);
            } else {
               this.f772 = 5;
            }
         } else if (s.equalsIgnoreCase("update")) {
            this.f772 = ServerConnection.m872(this.f773);
         }

         return true;
      }
   }
}
