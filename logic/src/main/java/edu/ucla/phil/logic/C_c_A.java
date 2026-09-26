package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_c_A extends C_CA {
   C_c_A(int[] aint) {
      super(LPSymbolizer.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPSymbolizer.getResults(this.f249, dimension);
   }

   static void m1666(int[] aint) {
      if (aint != null) {
         new C_c_A(aint).m1923();
      }
   }
}
