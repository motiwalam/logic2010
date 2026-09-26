package edu.ucla.phil.logic;

import java.util.Enumeration;

class C_CF implements Enumeration {
   String f266;
   int f267;
   int f268;
   int f269;

   C_CF(String s) {
      this.f266 = s;
      this.f267 = this.f266 == null ? 0 : this.f266.length();
      this.f268 = 0;
      this.f269 = -1;
   }

   @Override
   public boolean hasMoreElements() {
      return this.f267 != 0;
   }

   @Override
   public Object nextElement() {
      if (this.f267 == 0) {
         return null;
      } else {
         String s;
         for (s = ""; this.f268 >= this.f267; this.f269++) {
            this.f268 = this.f268 - this.f267;
         }

         s = s + this.f266.charAt(this.f268);
         this.f268++;
         if (this.f269 >= 0) {
            s = s + this.f269;
         }

         return s;
      }
   }
}
