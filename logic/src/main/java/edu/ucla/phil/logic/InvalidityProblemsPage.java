package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class InvalidityProblemsPage extends PrintPage {
   InvalidityProblemsPage(int[] aint) {
      super(LPInvalidation.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPInvalidation.getPrintProblems(this.problemIndices, dimension);
   }

   static void printProblems(int[] aint) {
      if (aint != null) {
         new InvalidityProblemsPage(aint).schedule();
      }
   }
}
