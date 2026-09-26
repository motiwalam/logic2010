package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;
import javax.swing.Box;

class C_TC extends C_CA {
   C_TC(int[] aint) {
      super(LPSymbolizer.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPSymbolizer.getPrintProblems(this.f249, dimension);
   }

   static void m1307(int[] aint) {
      if (aint != null) {
         C_TC c_tc = new C_TC(aint);
         c_tc.m414(Box.createVerticalStrut(LogicProgram.f539 * 3));
         c_tc.m1923();
      }
   }
}
