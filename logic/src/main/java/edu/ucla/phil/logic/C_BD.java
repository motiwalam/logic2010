package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_BD extends C_CA {
   C_BD(int[] aint) {
      super(LPRecognition.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPRecognition.getPrintProblems(this.f249, dimension);
   }

   static void m390(int[] aint) {
      if (aint != null) {
         new C_BD(aint).m1923();
      }
   }
}
