package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_AE extends C_CA {
   C_AE(int[] aint) {
      super(LPParsing.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPParsing.getStatements(this.f249, dimension);
   }

   static void m234(int[] aint) {
      if (aint != null) {
         new C_AE(aint).m1923();
      }
   }
}
