package edu.ucla.phil.logic;

import java.util.Hashtable;

class TruthProblemEntry extends ProblemEntry {
   static Hashtable workProblemNames = null;

   TruthProblemEntry(String s, boolean flag) {
      super(s, flag, workProblemNames);
   }

   @Override
   int computeState(String s) {
      return LPTruthAnalysis.getProblemState(s);
   }
}
