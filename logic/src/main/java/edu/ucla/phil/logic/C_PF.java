package edu.ucla.phil.logic;

class C_PF extends C_LF {
   C_LF f696;
   C_j_D f697;

   C_PF(C_LF c_lf, C_j_D c_j_d) {
      super("Instance of " + c_lf.f820);
      this.f696 = c_lf;
      this.f697 = c_j_d;
      if (c_lf != null && c_lf.f527 != null) {
         this.f527 = c_lf.f527.m1238(c_j_d);
         this.f527 = this.f527.m1275();
      }
   }

   String m1182() {
      return this.f696.f820 + "," + this.f697.m1896();
   }

   static C_PF m1183(String s) {
      int i = s.indexOf(44);
      C_VB c_vb = LPDerivation.getRule(i == -1 ? s : s.substring(0, i));
      C_j_D c_j_d = i == -1 ? null : C_j_D.m1897(s.substring(i + 1));
      return !(c_vb instanceof C_LF) ? null : new C_PF((C_LF)c_vb, c_j_d);
   }
}
