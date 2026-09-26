package edu.ucla.phil.logic;

class C_d_E extends C_LB {
   int f1060;
   C_b_A f1061;

   C_d_E(String s, int i) {
      this.f1060 = i;
      this.add(new C_ZE(s), "West");
      String[] astring = new String[i];

      for (int j = 0; j < i; j++) {
         astring[j] = "" + j;
      }

      this.add(this.f1061 = new C_b_A(astring, 0), "Center");
   }

   int m1744() {
      return this.f1061.m1651();
   }

   void m1745(int i) {
      this.f1061.m1649(i);
   }
}
