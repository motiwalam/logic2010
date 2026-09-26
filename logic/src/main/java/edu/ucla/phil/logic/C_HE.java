package edu.ucla.phil.logic;

import java.io.File;
import java.util.Vector;

class C_HE extends C_o_ {
   C_p_ f385;
   Vector f386;
   String f387;
   File f388;
   C_N f389;

   C_HE(C_p_ c_p_, Vector vector, String s, C_r_A c_r_a, File file1, C_N c_n) {
      super(c_r_a, null);
      this.f385 = c_p_;
      this.f386 = vector;
      this.f387 = s;
      this.f388 = file1;
      this.f389 = c_n;
   }

   @Override
   Object m687() {
      return C_KC.m811(this.f385, this.f386, this.f387, this.f1317, this.f388, this.f389);
   }
}
