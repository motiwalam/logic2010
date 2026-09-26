package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_Z extends C_CA {
   C_Z(int[] aint) {
      super(LPDerivation.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPDerivation.getResults(this.f249, dimension);
   }

   static void m1544(int[] aint) {
      if (aint != null) {
         new C_Z(aint).m1923();
      }
   }
}
