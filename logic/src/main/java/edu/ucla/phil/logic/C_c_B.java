package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_c_B extends C_J implements C_v_ {
   C_c_B(String s) {
      this(s, null);
   }

   C_c_B(String s, Hashtable hashtable) {
      super(s, hashtable);
   }

   @Override
   public void m4(String s, Hashtable hashtable) {
      this.f427 = s;
      this.f428 = hashtable;
   }

   @Override
   public C_c_B m5() {
      return this;
   }
}
