package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_x_ extends C_CA {
   C_x_(int[] aint) {
      super(LPTruthAnalysis.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPTruthAnalysis.getPrintProblems(this.f249, dimension);
   }

   static void m2161(int[] aint) {
      if (aint != null) {
         new C_x_(aint).m1923();
      }
   }
}
