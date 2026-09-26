package edu.ucla.phil.logic;

import java.util.Vector;

class C_c_C extends Vector {
   Vector f1028 = new Vector();
   String f1029;

   C_c_C(String s) {
      this.f1029 = s;
   }

   String m1667() {
      return this.f1029;
   }

   synchronized void m1668(C_TD c_td) {
      this.f1028.addElement(c_td);
   }

   synchronized void m1669(C_TD c_td) {
      this.f1028.removeElement(c_td);
   }

   synchronized void m1670() {
      int i = this.f1028.size();

      for (int j = 0; j < i; j++) {
         ((C_TD)this.f1028.elementAt(j)).m223(this);
      }
   }
}
