package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;
import javax.swing.Box;

class DerivationProblemsPage extends PrintPage {
   DerivationProblemsPage(int[] aint) {
      super(LPDerivation.printQueue, aint);
   }

   @Override
   Vector getPrintComponents(Dimension dimension) {
      return LPDerivation.getPrintProblems(this.problemIndices, dimension);
   }

   static void printProblems(int[] aint) {
      if (aint != null) {
         DerivationProblemsPage derivationproblemspage = new DerivationProblemsPage(aint);
         derivationproblemspage.setSeparator(Box.createVerticalStrut(LogicProgram.fontSize * 3));
         derivationproblemspage.schedule();
      }
   }
}
