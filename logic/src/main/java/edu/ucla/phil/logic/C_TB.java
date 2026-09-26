package edu.ucla.phil.logic;

import java.util.Vector;

class C_TB implements C_RD {
   @Override
   public void m1204(String s, Vector vector, C_n_F c_n_f, boolean flag) {
      LPDerivation.listRules(s, vector, c_n_f, flag);
   }
}
