package edu.ucla.phil.logic;

class C_p_D {
   String f1334;
   String f1335;

   C_p_D(String s, String s1) {
      this.f1334 = s;
      this.f1335 = s1;
   }

   C_p_D(String s) {
      int i = s == null ? -1 : s.indexOf(58);
      if (i == -1) {
         this.f1334 = s;
         this.f1335 = null;
      } else {
         this.f1334 = s.substring(0, i);
         this.f1335 = s.substring(i + 1);
      }
   }

   @Override
   public String toString() {
      return this.f1335 == null ? this.f1334 : this.f1334 + ":" + this.f1335;
   }
}
