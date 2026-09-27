package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class RecognitionProblemsPage extends PrintPage {
   RecognitionProblemsPage(int[] aint) {
      super(LPRecognition.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPRecognition.getPrintProblems(this.problemIndices, dimension);
   }

   static void printProblems(int[] aint) {
      if (aint != null) {
         new RecognitionProblemsPage(aint).schedule();
      }
   }
}
