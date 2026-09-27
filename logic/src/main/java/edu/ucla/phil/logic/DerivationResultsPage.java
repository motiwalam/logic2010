package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class DerivationResultsPage extends PrintPage {
   DerivationResultsPage(int[] aint) {
      super(LPDerivation.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPDerivation.getResults(this.problemIndices, dimension);
   }

   static void printResults(int[] aint) {
      if (aint != null) {
         new DerivationResultsPage(aint).schedule();
      }
   }
}
