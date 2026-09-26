package edu.ucla.phil.logic;

class C_P {
   int f668;
   int f669;

   C_P(int i, int j) {
      this.f668 = i;
      this.f669 = j;
   }

   int m1172(C_P c_p1) {
      return this.f668 != c_p1.f668 ? this.f668 - c_p1.f668 : this.f669 - c_p1.f669;
   }
}
