package edu.ucla.phil.logic;

import java.util.Vector;

class C_JA implements C_i_D {
   Vector f429;
   C_d_C f430;
   C_CD f431;

   C_JA(C_d_C c_d_c) {
      this.m719(c_d_c);
   }

   void m719(C_d_C c_d_c) {
      this.f429 = new Vector();
      this.f430 = c_d_c;
      this.f431 = null;
   }

   Vector m720() {
      return this.f429;
   }

   C_CD m721() {
      return this.f431;
   }

   @Override
   public void m68(C_d_C c_d_c, C_d_C c_d_c1, Vector vector, Vector vector1) {
      if (this.f431 == null && c_d_c == this.f430) {
         this.f431 = new C_CD(c_d_c, c_d_c1, vector, vector1);
      }
   }

   @Override
   public void m69(C_d_C c_d_c, C_d_C c_d_c1, Vector vector, Vector vector1) {
      if (this.f431 == null && c_d_c == this.f430) {
         this.f431 = new C_CD(c_d_c, c_d_c1, vector, vector1);
      }

      this.f429.addElement(new C_f_A(c_d_c, c_d_c1, vector, vector1));
   }
}
