package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_c_ extends C_CA {
   C_c_(int[] aint) {
      super(LPRecognition.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPRecognition.getResults(this.f249, dimension);
   }

   static void m1665(int[] aint) {
      if (aint != null) {
         new C_c_(aint).m1923();
      }
   }
}
