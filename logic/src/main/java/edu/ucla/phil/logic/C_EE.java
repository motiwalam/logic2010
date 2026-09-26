package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_EE extends C_f_F {
   static Hashtable f297 = null;

   C_EE(String s, boolean flag) {
      super(s, flag, f297);
   }

   @Override
   int m513(String s) {
      return LPDerivation.getProblemState(s);
   }
}
