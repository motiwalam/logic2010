package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class AnswerPrinter extends PrintPage {
   AnswerPrinter(int[] aint) {
      super(LPSymbolizer.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPSymbolizer.getAnswers(this.problemIndices, dimension);
   }

   static void printSelected(int[] aint) {
      if (aint != null) {
         new AnswerPrinter(aint).schedule();
      }
   }
}
