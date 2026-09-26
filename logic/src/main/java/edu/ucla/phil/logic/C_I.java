package edu.ucla.phil.logic;

class C_I extends C_D implements C_v_D {
   C_I(String s) {
      super(s);
   }

   @Override
   boolean m451(C_UA c_ua) {
      String s = this.m450(c_ua);
      LPSymbolizer lpsymbolizer = (LPSymbolizer)this.m449("symbolizer");
      C_IF c_if = (C_IF)this.m449("edit");
      if (s == null) {
         return false;
      } else if (s.equalsIgnoreCase("ok")) {
         return true;
      } else if (s.equalsIgnoreCase("clear")) {
         c_if.setText("");
         return false;
      } else {
         return s.equalsIgnoreCase("cancel");
      }
   }
}
