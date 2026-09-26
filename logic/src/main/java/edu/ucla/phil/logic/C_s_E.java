package edu.ucla.phil.logic;

import java.io.File;

class C_s_E extends C_o_ {
   C_p_ f1368;
   File f1369;
   C_i_B f1370;

   C_s_E(C_p_ c_p_, File file1, C_i_B c_i_b, C_r_A c_r_a) {
      super(c_r_a, new Boolean(false));
      this.f1368 = c_p_;
      this.f1369 = file1;
      this.f1370 = c_i_b;
   }

   @Override
   Object m687() {
      return new Boolean(this.f1370.m1857(this.f1368, this.f1369, this.f1317));
   }
}
