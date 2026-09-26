package edu.ucla.phil.logic;

class C_ZB extends DialogHandler {
   C_ZB(String s) {
      super(s);
   }

   @Override
   boolean m451(MessageDialog messagedialog) {
      String s = this.m450(messagedialog);
      if (s != null && s.equalsIgnoreCase("abort")) {
         C_l_B.m1925((C_c_C)this.m449("queue"));
      }

      return false;
   }
}
