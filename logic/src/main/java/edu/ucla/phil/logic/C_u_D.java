package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_u_D extends C_f_F {
   static Hashtable f1398 = null;

   C_u_D(String s, boolean flag) {
      super(s, flag, f1398);
   }

   @Override
   int m513(String s) {
      return LPInvalidation.getProblemState(s);
   }
}
