package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_e_F extends C_CA {
   C_e_F(int[] aint) {
      super(LPInvalidation.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPInvalidation.getResults(this.f249, dimension);
   }

   static void m1800(int[] aint) {
      if (aint != null) {
         new C_e_F(aint).m1923();
      }
   }
}
