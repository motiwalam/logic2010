package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_f_C extends ProblemEntry {
   static Hashtable f1111 = null;

   C_f_C(String s, boolean flag) {
      super(s, flag, f1111);
   }

   @Override
   int m513(String s) {
      return LPTruthAnalysis.getProblemState(s);
   }
}
