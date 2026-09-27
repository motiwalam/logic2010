package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;
import javax.swing.Box;

class SymbolizationProblemPrinter extends PrintPage {
   SymbolizationProblemPrinter(int[] aint) {
      super(LPSymbolizer.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPSymbolizer.getPrintProblems(this.problemIndices, dimension);
   }

   static void printSelected(int[] aint) {
      if (aint != null) {
         SymbolizationProblemPrinter symbolizationproblemprinter = new SymbolizationProblemPrinter(aint);
         symbolizationproblemprinter.setSeparator(Box.createVerticalStrut(LogicProgram.fontSize * 3));
         symbolizationproblemprinter.schedule();
      }
   }
}
