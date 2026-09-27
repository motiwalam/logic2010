package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class InvalidityStatementsPage extends PrintPage {
   InvalidityStatementsPage(int[] aint) {
      super(LPInvalidation.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPInvalidation.getStatements(this.problemIndices, dimension);
   }

   static void printStatements(int[] aint) {
      if (aint != null) {
         new InvalidityStatementsPage(aint).schedule();
      }
   }
}
