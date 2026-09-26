package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_UF extends C_CA {
   C_UF(int[] aint) {
      super(LPRecognition.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPRecognition.getStatements(this.f249, dimension);
   }

   static void m1358(int[] aint) {
      if (aint != null) {
         new C_UF(aint).m1923();
      }
   }
}
