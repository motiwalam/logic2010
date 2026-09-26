package edu.ucla.phil.logic;

import javax.swing.SwingUtilities;

class C_ZC extends C_e_D {
   C_ZC() {
      this.f1080 = true;
   }

   @Override
   void m1099(C_U.C__A c_u$c__a) {
      LPParsing.restating = true;
      C_e_D.C__A c_e_d$c__a = new C_e_D.C__A(this.size(), new C_XD(), new LPParsing(false), c_u$c__a);
      SwingUtilities.invokeLater(c_e_d$c__a);
   }

   @Override
   C_f_F m1102(String s, boolean flag) {
      return new C_l_D(s, flag);
   }

   @Override
   boolean m1103(C_XD c_xd) {
      return LPParsing.hasWork(c_xd);
   }

   @Override
   String m1104(C_XD c_xd) {
      return LPParsing.getWork(c_xd);
   }

   @Override
   String m1105(C_XD c_xd) {
      return LPParsing.removeWork(c_xd);
   }

   @Override
   String m1106(C_XD c_xd) {
      return LPParsing.getProblemStatement(c_xd);
   }

   @Override
   int m1107() {
      return 2;
   }
}
