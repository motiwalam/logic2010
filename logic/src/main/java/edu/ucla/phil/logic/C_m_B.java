package edu.ucla.phil.logic;

import java.util.Vector;

class C_m_B {
   C_RF f1276;
   C_RF f1277;
   C_MB f1278;
   C_i_[] f1279;

   C_m_B(C_RF c_rf, C_RF c_rf1, C_MB c_mb, Vector vector) {
      this.f1276 = c_rf;
      this.f1277 = c_rf1;
      this.f1278 = c_mb;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof C_m_B)) {
         return false;
      } else {
         C_m_B c_m_b1 = (C_m_B)object;
         return this.f1276.m1235(c_m_b1.f1276) && (this.f1277 == null ? c_m_b1.f1277 == null : this.f1277.m1235(c_m_b1.f1277));
      }
   }

   @Override
   public int hashCode() {
      return this.toString().hashCode();
   }

   @Override
   public String toString() {
      return this.f1276 + ":" + this.f1277;
   }
}
