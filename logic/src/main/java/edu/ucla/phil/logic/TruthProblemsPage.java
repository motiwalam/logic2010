package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class TruthProblemsPage extends PrintPage {
   TruthProblemsPage(int[] aint) {
      super(LPTruthAnalysis.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPTruthAnalysis.getPrintProblems(this.problemIndices, dimension);
   }

   static void printProblems(int[] aint) {
      if (aint != null) {
         new TruthProblemsPage(aint).schedule();
      }
   }
}
