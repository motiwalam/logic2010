package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class ParsingStatementsPage extends PrintPage {
   ParsingStatementsPage(int[] aint) {
      super(LPParsing.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPParsing.getStatements(this.problemIndices, dimension);
   }

   static void printStatements(int[] aint) {
      if (aint != null) {
         new ParsingStatementsPage(aint).schedule();
      }
   }
}
