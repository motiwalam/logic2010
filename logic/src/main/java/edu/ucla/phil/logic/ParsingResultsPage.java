package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class ParsingResultsPage extends PrintPage {
   ParsingResultsPage(int[] aint) {
      super(LPParsing.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPParsing.getResults(this.problemIndices, dimension);
   }

   static void printResults(int[] aint) {
      if (aint != null) {
         new ParsingResultsPage(aint).schedule();
      }
   }
}
