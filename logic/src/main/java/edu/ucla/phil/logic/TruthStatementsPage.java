package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class TruthStatementsPage extends PrintPage {
   TruthStatementsPage(int[] aint) {
      super(LPTruthAnalysis.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPTruthAnalysis.getStatements(this.problemIndices, dimension);
   }

   static void printStatements(int[] aint) {
      if (aint != null) {
         new TruthStatementsPage(aint).schedule();
      }
   }
}
