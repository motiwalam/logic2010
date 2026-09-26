package edu.ucla.phil.logic;

class C_r_E extends C_D implements C_v_D {
   C_r_E(String s) {
      super(s);
   }

   @Override
   boolean m451(C_UA c_ua) {
      String s = this.m450(c_ua);
      C_y_B c_y_b = (C_y_B)this.m449("scheme");
      LPSymbolizer lpsymbolizer = (LPSymbolizer)this.m449("symbolizer");
      if (c_y_b == null) {
         return true;
      } else if (s == null) {
         return false;
      } else if (s.equalsIgnoreCase("ok")) {
         return true;
      } else if (s.equalsIgnoreCase("clear")) {
         c_y_b.m2182(":");
         return false;
      } else if (s.equalsIgnoreCase("open")) {
         String s1 = C_WB.m1435(lpsymbolizer, c_y_b);
         if (s1 != null) {
            c_y_b.m2182(s1);
         }

         return false;
      } else if (s.equalsIgnoreCase("cancel")) {
         return true;
      } else if (s.equalsIgnoreCase("help")) {
         C_UA.m1329(C_h_E.m411("symnot008"), null, null, null);
         return false;
      } else {
         return false;
      }
   }
}
