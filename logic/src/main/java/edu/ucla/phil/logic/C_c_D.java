package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_c_D extends C_CA {
   C_c_D(int[] aint) {
      super(LPTruthAnalysis.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPTruthAnalysis.getStatements(this.f249, dimension);
   }

   static void m1671(int[] aint) {
      if (aint != null) {
         new C_c_D(aint).m1923();
      }
   }
}
