package edu.ucla.phil.logic;

import java.util.Hashtable;

class RecognitionProblemEntry extends ProblemEntry {
   static Hashtable savedWork = null;

   RecognitionProblemEntry(String s, boolean flag) {
      super(s, flag, savedWork);
   }

   @Override
   int computeState(String s) {
      return LPRecognition.getProblemState(s);
   }
}
