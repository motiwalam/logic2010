package edu.ucla.phil.logic;

class C_FD implements LogicConstants {
   C_HF f311;
   SchemeInstantiation f312;
   C_DD[] f313;
   C_DD f314;

   C_FD(C_HF c_hf, DerivationLineChecker derivationlinechecker) {
      this.f311 = (C_HF)c_hf.clone();
      this.f312 = this.f311.f393.m1893(null);
      int i = this.f311.m692();
      this.f313 = new C_DD[i];

      for (int j = 0; j < i; j++) {
         this.f313[j] = this.m541(this.f311.m694(j, derivationlinechecker));
      }

      this.f314 = this.m541(this.f311.m696(derivationlinechecker));
   }

   C_DD m541(Expression expression) {
      C_DD c_dd = new C_DD(expression, true, -1);
      c_dd.m460(this.f312.f1189);
      c_dd.m461(this.f311.f394);
      return c_dd;
   }

   C_e_B m542() {
      return this.m543(true);
   }

   C_e_B m543(boolean flag) {
      C_e_B c_e_b = new C_e_B();
      int i = this.f311.m692();

      for (int j = 0; j < i; j++) {
         if (j != 0) {
            c_e_b.m1761(".");
         }

         c_e_b.m1760(this.f313[j].m463());
      }

      C_e_B c_e_b1 = this.f314.m463();
      if (flag && !c_e_b.m1765(c_e_b1)) {
         return c_e_b1;
      } else {
         c_e_b.m1761(".:");
         c_e_b.m1760(c_e_b1);
         return c_e_b;
      }
   }
}
