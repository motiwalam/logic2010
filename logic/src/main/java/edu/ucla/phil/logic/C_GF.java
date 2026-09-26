package edu.ucla.phil.logic;

class C_GF {
   C_RF f367;
   C_RF f368;
   C_c_B f369;

   C_GF(C_RF c_rf, C_RF c_rf1) {
      if ((this.f369 = C_j_D.m1884(c_rf, c_rf1)) == null) {
         this.f367 = c_rf;
         this.f368 = c_rf1.m1237();
         this.f368.m1264(c_rf);
      }
   }

   C_GF(C_c_B c_c_b) {
      this.f369 = c_c_b;
   }

   boolean m656(C_GF c_gf1) {
      if (this.f369 != null || c_gf1.f369 != null) {
         return false;
      } else {
         return this.f367.f739.equals(c_gf1.f367.f739) && this.f367.f741 == c_gf1.f367.f741 ? this.f368.m1236(c_gf1.f368, null) : false;
      }
   }

   @Override
   public String toString() {
      return this.f367 + ":" + this.f368;
   }
}
