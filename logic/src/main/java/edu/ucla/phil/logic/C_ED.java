package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_ED extends ProblemEntry {
   static Hashtable f296 = null;

   C_ED(String s, boolean flag) {
      super(s, flag, f296);
   }

   @Override
   int m513(String s) {
      return LPRecognition.getProblemState(s);
   }
}
