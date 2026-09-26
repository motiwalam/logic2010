package edu.ucla.phil.logic;

class C_OF extends C_LF {
   Integer f667;

   C_OF(LPDerivation lpderivation, int i) {
      super("Premise " + i);
      if (lpderivation.premises != null && i >= 1 && i <= lpderivation.premises.length) {
         this.f667 = new Integer(i - 1);
         this.f527 = lpderivation.premises[i - 1];
      } else {
         throw new IllegalArgumentException("no premise " + i);
      }
   }
}
