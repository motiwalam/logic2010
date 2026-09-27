package edu.ucla.phil.logic;

import java.util.Hashtable;

class DerivationProblemEntry extends ProblemEntry {
   static Hashtable workProblemNames = null;

   DerivationProblemEntry(String s, boolean flag) {
      super(s, flag, workProblemNames);
   }

   @Override
   int computeState(String s) {
      return LPDerivation.getProblemState(s);
   }
}
