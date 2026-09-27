package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class RecognitionResultsPrintJob extends PrintPage {
   RecognitionResultsPrintJob(int[] aint) {
      super(LPRecognition.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPRecognition.getResults(this.problemIndices, dimension);
   }

   static void print(int[] aint) {
      if (aint != null) {
         new RecognitionResultsPrintJob(aint).schedule();
      }
   }
}
