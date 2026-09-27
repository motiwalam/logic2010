package edu.ucla.phil.logic;

import java.util.Hashtable;

class InvalidityProblemEntry extends ProblemEntry {
   static Hashtable workProblemNames = null;

   InvalidityProblemEntry(String s, boolean flag) {
      super(s, flag, workProblemNames);
   }

   @Override
   int computeState(String s) {
      return LPInvalidation.getProblemState(s);
   }
}
