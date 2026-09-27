package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class StatementListPrinter extends PrintPage {
   StatementListPrinter(int[] aint) {
      super(LPSymbolizer.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPSymbolizer.getStatements(this.problemIndices, dimension);
   }

   static void printSelected(int[] aint) {
      if (aint != null) {
         new StatementListPrinter(aint).schedule();
      }
   }
}
