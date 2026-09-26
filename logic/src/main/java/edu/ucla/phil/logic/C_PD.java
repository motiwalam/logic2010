package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_PD extends C_CA {
   C_PD(int[] aint) {
      super(LPSymbolizer.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPSymbolizer.getAnswers(this.f249, dimension);
   }

   static void m1179(int[] aint) {
      if (aint != null) {
         new C_PD(aint).m1923();
      }
   }
}
