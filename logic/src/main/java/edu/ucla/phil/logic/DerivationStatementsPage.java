package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class DerivationStatementsPage extends PrintPage {
   DerivationStatementsPage(int[] aint) {
      super(LPDerivation.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPDerivation.getStatements(this.problemIndices, dimension);
   }

   static void printStatements(int[] aint) {
      if (aint != null) {
         new DerivationStatementsPage(aint).schedule();
      }
   }
}
