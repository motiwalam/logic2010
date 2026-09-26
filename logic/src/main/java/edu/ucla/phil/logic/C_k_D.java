package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_k_D extends C_CA {
   C_k_D(int[] aint) {
      super(LPInvalidation.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPInvalidation.getStatements(this.f249, dimension);
   }

   static void m1907(int[] aint) {
      if (aint != null) {
         new C_k_D(aint).m1923();
      }
   }
}
