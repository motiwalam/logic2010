package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_j_B implements C_v_ {
   static final int f1177 = 0;
   static final int f1178 = 1;
   static final int f1179 = 2;
   C_p_ f1180;
   String f1181;
   C_p_ f1182;
   String f1183;
   C_OE f1184;
   boolean f1185;
   boolean f1186 = false;
   int f1187 = 0;
   C_c_B f1188 = null;

   @Override
   public void m4(String s, Hashtable hashtable) {
      if (this.f1188 == null) {
         this.f1188 = new C_c_B(null);
      }

      this.f1188.m4(s, hashtable);
   }

   @Override
   public C_c_B m5() {
      return this.f1188;
   }
}
