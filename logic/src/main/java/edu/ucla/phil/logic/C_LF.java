package edu.ucla.phil.logic;

import java.util.Vector;

class C_LF extends C_VB {
   C_RF[] f526 = new C_RF[0];
   C_RF f527 = null;

   C_LF(String s) {
      super(s, null);
   }

   C_LF(String s, C_RF[] ac_rf, C_RF c_rf) {
      this(s);
      if (ac_rf != null) {
         this.f526 = ac_rf;
      }

      this.f527 = c_rf;
   }

   C_LF(String s, String s1) {
      this(s);
      this.m948(s1);
   }

   void m948(String s) {
      C_VC c_vc = new C_VC(s);
      this.f526 = c_vc.f827;
      this.f527 = c_vc.f828;
      String s1 = c_vc.m1384();
      if (s1 != null) {
         this.f823 = "parse error: " + s1;
      } else {
         int i = c_vc.m1385();
         if (i != 0) {
            this.f823 = C_VC.f832[i];
         }
      }
   }

   C_RF[] m949() {
      return this.f526;
   }

   C_RF m950() {
      return this.f527;
   }

   C_LF m951() {
      int i = this.f526.length;
      C_RF[] ac_rf = new C_RF[i];

      for (int j = 0; j < i; j++) {
         ac_rf[j] = this.f526[j].m1237();
      }

      return new C_LF(this.f820, ac_rf, this.f527.m1237());
   }

   @Override
   boolean m952(C_w_E c_w_e) {
      Vector vector = this.m954(c_w_e);
      if (vector != null && !this.m1193(c_w_e, "weakAss", false)) {
         String s = c_w_e.excludedProof();
         int i = vector.size();

         for (int j = 0; j < i; j++) {
            String s1 = (String)vector.elementAt(j);
            if ((s == null || !s.equals(s1)) && c_w_e.checkProof(s1)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @Override
   boolean m953(C_w_E c_w_e) {
      return this.m952(c_w_e);
   }

   Vector m954(C_w_E c_w_e) {
      return this.f822 != null ? this.f822.m954(c_w_e) : c_w_e.getProofs(this);
   }

   @Override
   void m955(Vector vector, C_w_E c_w_e, String s) {
      if (c_w_e == null || !c_w_e.hasProperty(this, s)) {
         vector.addElement(this);
      }
   }

   int m956(C_LF[] ac_lf) {
      int i = ac_lf == null ? 0 : ac_lf.length;

      for (int j = 0; j < i; j++) {
         if (this.f820.equals(ac_lf[j].f820)) {
            return j;
         }
      }

      return -1;
   }

   String m957(int[] aint) {
      String s = "";
      boolean flag = false;
      int i = Math.min(this.f526.length, aint.length);

      for (int j = 0; j < i; j++) {
         s = s + (flag ? "." : "") + this.f526[aint[j]];
         flag = true;
      }

      return s + ".:" + this.f527;
   }

   @Override
   String m958(String s, String s1) {
      String s2 = "";
      boolean flag = false;

      for (int i = 0; i < this.f526.length; i++) {
         s2 = s2 + (flag ? s : "") + this.f526[i];
         flag = true;
      }

      return s2 + s1 + this.f527;
   }
}
