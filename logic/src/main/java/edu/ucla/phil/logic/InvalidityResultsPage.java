package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class InvalidityResultsPage extends PrintPage {
   InvalidityResultsPage(int[] aint) {
      super(LPInvalidation.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPInvalidation.getResults(this.problemIndices, dimension);
   }

   static void printResults(int[] aint) {
      if (aint != null) {
         new InvalidityResultsPage(aint).schedule();
      }
   }
}
