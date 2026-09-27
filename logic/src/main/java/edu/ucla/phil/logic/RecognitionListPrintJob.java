package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class RecognitionListPrintJob extends PrintPage {
   RecognitionListPrintJob(int[] aint) {
      super(LPRecognition.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPRecognition.getStatements(this.problemIndices, dimension);
   }

   static void print(int[] aint) {
      if (aint != null) {
         new RecognitionListPrintJob(aint).schedule();
      }
   }
}
