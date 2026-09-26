package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_l_D extends ProblemEntry {
   static Hashtable f1254 = null;

   C_l_D(String s, boolean flag) {
      super(s, flag, f1254);
   }

   @Override
   int m513(String s) {
      return LPParsing.getProblemState(s);
   }
}
