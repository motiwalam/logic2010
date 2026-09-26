package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_WF extends C_CA {
   C_WF(int[] aint) {
      super(LPSymbolizer.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPSymbolizer.getStatements(this.f249, dimension);
   }

   static void m1460(int[] aint) {
      if (aint != null) {
         new C_WF(aint).m1923();
      }
   }
}
