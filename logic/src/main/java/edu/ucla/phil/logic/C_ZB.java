package edu.ucla.phil.logic;

class C_ZB extends C_D {
   C_ZB(String s) {
      super(s);
   }

   @Override
   boolean m451(C_UA c_ua) {
      String s = this.m450(c_ua);
      if (s != null && s.equalsIgnoreCase("abort")) {
         C_l_B.m1925((C_c_C)this.m449("queue"));
      }

      return false;
   }
}
