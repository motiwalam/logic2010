package edu.ucla.phil.logic;

import javax.swing.SwingUtilities;

class C_PC extends C_e_D {
   @Override
   void m1099(C_U.C__A c_u$c__a) {
      LPInvalidation.restating = true;
      C_e_D.C__A c_e_d$c__a = new C_e_D.C__A(this.size(), new C_XD(), new LPInvalidation(false), c_u$c__a);
      SwingUtilities.invokeLater(c_e_d$c__a);
   }

   @Override
   C_f_F m1102(String s, boolean flag) {
      return new C_u_D(s, flag);
   }

   @Override
   boolean m1103(C_XD c_xd) {
      return LPInvalidation.hasWork(c_xd);
   }

   @Override
   String m1104(C_XD c_xd) {
      return LPInvalidation.getWork(c_xd);
   }

   @Override
   String m1105(C_XD c_xd) {
      return LPInvalidation.removeWork(c_xd);
   }

   @Override
   String m1106(C_XD c_xd) {
      return LPInvalidation.getProblemStatement(c_xd);
   }

   @Override
   int m1107() {
      return 1;
   }
}
