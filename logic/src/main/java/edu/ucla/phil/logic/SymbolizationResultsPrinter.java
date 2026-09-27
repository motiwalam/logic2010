package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class SymbolizationResultsPrinter extends PrintPage {
   SymbolizationResultsPrinter(int[] aint) {
      super(LPSymbolizer.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPSymbolizer.getResults(this.problemIndices, dimension);
   }

   static void printSelected(int[] aint) {
      if (aint != null) {
         new SymbolizationResultsPrinter(aint).schedule();
      }
   }
}
