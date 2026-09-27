package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class TruthResultsPage extends PrintPage {
   TruthResultsPage(int[] aint) {
      super(LPTruthAnalysis.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPTruthAnalysis.getResults(this.problemIndices, dimension);
   }

   static void printResults(int[] aint) {
      if (aint != null) {
         new TruthResultsPage(aint).schedule();
      }
   }
}
