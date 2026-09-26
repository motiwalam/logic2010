package edu.ucla.phil.logic;

class C_0D {
   String f25;
   boolean f26;

   C_0D(String s, boolean flag) {
      this.f25 = s;
      this.f26 = flag;
   }

   int m60(C_0D c_0d1) {
      int i = this.f25.compareTo(c_0d1.f25);
      if (i != 0) {
         return i;
      } else if (this.f26 == c_0d1.f26) {
         return 0;
      } else {
         return this.f26 ? -1 : 1;
      }
   }

   @Override
   public String toString() {
      return (this.f26 ? "" : "~") + "\"" + DelimitedTokenizer.m1139(this.f25, "\\\"") + "\"";
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof C_0D)) {
         return false;
      } else {
         C_0D c_0d1 = (C_0D)object;
         return this.f26 != c_0d1.f26 ? false : this.f25.equals(c_0d1.f25);
      }
   }
}
