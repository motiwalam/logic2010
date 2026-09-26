package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;
import javax.swing.Box;

class C_MC extends C_CA {
   C_MC(int[] aint) {
      super(LPDerivation.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPDerivation.getPrintProblems(this.f249, dimension);
   }

   static void m1096(int[] aint) {
      if (aint != null) {
         C_MC c_mc = new C_MC(aint);
         c_mc.m414(Box.createVerticalStrut(LogicProgram.f539 * 3));
         c_mc.m1923();
      }
   }
}
