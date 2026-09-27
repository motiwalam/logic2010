package edu.ucla.phil.logic;

import java.util.Hashtable;

class ParsingProblemEntry extends ProblemEntry {
   static Hashtable newProblemNames = null;

   ParsingProblemEntry(String s, boolean flag) {
      super(s, flag, newProblemNames);
   }

   @Override
   int computeState(String s) {
      return LPParsing.getProblemState(s);
   }
}
