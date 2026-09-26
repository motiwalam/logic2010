package edu.ucla.phil.logic;

import java.util.Enumeration;

class C_SC implements Enumeration {
   int f760;
   int f761;
   C_n_F f762;

   public C_SC(C_n_F c_n_f) {
      this.f762 = c_n_f;
      this.f761 = c_n_f.f1313 ? 1 : 0;
      this.f760 = this.f761 < c_n_f.f1314 ? c_n_f.f1316[this.f761] : 0;
   }

   @Override
   public boolean hasMoreElements() {
      return this.f761 + 1 < this.f762.f1314 && this.f760 < this.f762.f1316[this.f761 + 1];
   }

   @Override
   public Object nextElement() {
      Integer integer = null;
      if (this.hasMoreElements()) {
         integer = new Integer(this.f760);
         if (++this.f760 >= this.f762.f1316[this.f761 + 1]) {
            this.f761 += 2;
            this.f760 = this.f761 < this.f762.f1314 ? this.f762.f1316[this.f761] : 0;
         }
      }

      return integer;
   }
}
