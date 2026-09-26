package edu.ucla.phil.logic;

import java.util.Hashtable;

class C__C extends C_f_F {
   int f927 = -1;
   static Hashtable f928 = null;

   C__C(String s, C_h_C c_h_c, boolean flag) {
      super(s, true, f928);
      if (!flag) {
         LPSymbolizer.getProblemState(s, c_h_c, this);
      }
   }

   void m1582() {
      this.f1120 = 0;
      this.f927 = -1;
   }

   @Override
   int m513(String s) {
      return 0;
   }
}
