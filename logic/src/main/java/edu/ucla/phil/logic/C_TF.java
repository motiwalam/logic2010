package edu.ucla.phil.logic;

class C_TF implements C_v_D {
   int[] f780;
   String[] f781;

   C_TF() {
      this.f780 = null;
      this.f781 = null;
   }

   C_TF(C_d_C c_d_c) {
      this.m1308(c_d_c);
   }

   void m1308(C_d_C c_d_c) {
      int i = c_d_c.f1050.length;
      this.f780 = new int[i];
      this.f781 = new String[i];

      for (int j = 0; j < i; j++) {
         C_d_C c_d_c1 = c_d_c.m1686(j);
         this.f780[j] = c_d_c1.f1049;
         this.f781[j] = c_d_c1.m1699(false);
      }
   }

   void m1309(C_d_C c_d_c) {
      int i = c_d_c.f1050.length;
      int[] aint = c_d_c.f1050;
      if (this.f780.length < i) {
         i = this.f780.length;
      }

      for (int j = 0; j < i; j++) {
         if (aint[j] == 2 || this.f780[j] == 2 || this.f780[j] == aint[j]) {
            c_d_c.m1686(j).m1704(new C_XD(this.f781[j]), false);
         }
      }
   }
}
