package edu.ucla.phil.logic;

import java.util.Vector;

class C_m_B {
   Expression f1276;
   Expression f1277;
   C_MB f1278;
   SimpleTerm[] f1279;

   C_m_B(Expression expression, Expression expression1, C_MB c_mb, Vector vector) {
      this.f1276 = expression;
      this.f1277 = expression1;
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
