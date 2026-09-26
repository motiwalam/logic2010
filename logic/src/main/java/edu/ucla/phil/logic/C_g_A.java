package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_g_A extends C_CA {
   C_g_A(int[] aint) {
      super(LPDerivation.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPDerivation.getStatements(this.f249, dimension);
   }

   static void m1819(int[] aint) {
      if (aint != null) {
         new C_g_A(aint).m1923();
      }
   }
}
