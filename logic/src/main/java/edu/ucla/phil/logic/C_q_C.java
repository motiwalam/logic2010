package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.util.Vector;

class C_q_C extends C_CA {
   C_q_C(int[] aint) {
      super(LPInvalidation.printQueue, aint);
   }

   @Override
   Vector m70(Dimension dimension) {
      return LPInvalidation.getPrintProblems(this.f249, dimension);
   }

   static void m2038(int[] aint) {
      if (aint != null) {
         new C_q_C(aint).m1923();
      }
   }
}
