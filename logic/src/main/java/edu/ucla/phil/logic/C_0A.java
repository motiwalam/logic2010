package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_0A implements C_v_ {
   String f1 = C_GE.m644(".");
   int f2;
   int f3;
   String f4;
   C_0C f5;
   String f6;
   String f7;
   String f8;
   String f9;
   String f10;
   int f11;
   long f12;
   String f13;
   String f14;
   String[] f15;
   String[] f16;

   C_0A(int i, int j, C_0C c_0c, String s) {
      this.f2 = i;
      this.f3 = j;
      this.f4 = s;
      this.f5 = c_0c;
      this.m1();
   }

   void m1() {
      this.f6 = null;
      this.f7 = null;
      this.f8 = null;
      this.f9 = null;
      this.f10 = null;
      this.f11 = 0;
      this.f12 = 0L;
      this.f13 = null;
      this.f14 = null;
   }

   boolean m2() {
      return this.f4 != null && this.f5 != null && this.f6 != null && this.f7 != null && this.f8 != null && this.f9 != null && this.f10 != null;
   }

   String m3() {
      return this.f14 + ":" + this.f1 + ":" + this.f13;
   }

   @Override
   public void m4(String s, Hashtable hashtable) {
      this.f5.m4(s, hashtable);
   }

   @Override
   public C_c_B m5() {
      return this.f5.m5();
   }
}
