package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_o_E extends C_CA {
   C_o_E(int[] aint) {
      super(LPParsing.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPParsing.getResults(this.f249, dimension);
   }

   static void m2009(int[] aint) {
      if (aint != null) {
         new C_o_E(aint).m1923();
      }
   }
}
