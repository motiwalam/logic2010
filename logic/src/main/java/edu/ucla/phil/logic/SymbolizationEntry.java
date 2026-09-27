package edu.ucla.phil.logic;

import java.util.Hashtable;

class SymbolizationEntry extends ProblemEntry {
   int answerIndex = -1;
   static Hashtable knownNames = null;

   SymbolizationEntry(String s, SymbolizationProblemSet symbolizationproblemset, boolean flag) {
      super(s, true, knownNames);
      if (!flag) {
         LPSymbolizer.getProblemState(s, symbolizationproblemset, this);
      }
   }

   void resetState() {
      this.state = 0;
      this.answerIndex = -1;
   }

   @Override
   int computeState(String s) {
      return 0;
   }
}
