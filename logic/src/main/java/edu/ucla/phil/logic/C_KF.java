package edu.ucla.phil.logic;

class C_KF extends C_VD {
   Integer f500;

   C_KF(LPDerivation lpderivation, int i) {
      super("Premise " + i);
      if (lpderivation.premises != null && i >= 1 && i <= lpderivation.premises.length) {
         this.f500 = new Integer(i - 1);
         this.f834 = lpderivation.premises[i - 1];
      } else {
         throw new IllegalArgumentException("no premise " + i);
      }
   }
}
