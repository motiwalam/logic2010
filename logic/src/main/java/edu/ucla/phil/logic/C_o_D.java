package edu.ucla.phil.logic;

import java.util.Vector;

class C_o_D {
   C_RF f1325;
   C_RF f1326;
   C_i_[] f1327;

   C_o_D(C_RF c_rf, C_RF c_rf1, Vector vector) {
      this.f1325 = c_rf;
      this.f1326 = c_rf1;
      this.f1327 = m2007(vector);
   }

   static C_i_[] m2007(Vector vector) {
      int i = vector == null ? 0 : vector.size();
      C_i_[] ac_i_ = i == 0 ? null : new C_i_[i];
      if (i != 0) {
         vector.copyInto(ac_i_ = new C_i_[i]);
      }

      return ac_i_;
   }

   static Vector m2008(C_i_[] ac_i_) {
      Vector vector = new Vector();
      int i = ac_i_ == null ? 0 : ac_i_.length;

      for (int j = 0; j < i; j++) {
         vector.addElement(ac_i_[j]);
      }

      return vector;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof C_o_D)) {
         return false;
      } else {
         C_o_D c_o_d1 = (C_o_D)object;
         if (this.f1325 == c_o_d1.f1325 && this.f1326 == c_o_d1.f1326) {
            C_i_[] ac_i_ = c_o_d1.f1327;
            int i = this.f1327 == null ? 0 : this.f1327.length;
            int j = ac_i_ == null ? 0 : ac_i_.length;
            if (i != j) {
               return false;
            } else {
               for (int k = 0; k < i; k++) {
                  if (this.f1327[k] != ac_i_[k]) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      int i = (this.f1325 == null ? 0 : this.f1325.hashCode()) + this.f1326.hashCode();
      int j = this.f1327 == null ? 0 : this.f1327.length;

      for (int k = 0; k < j; k++) {
         i += this.f1327[k].hashCode();
      }

      return i;
   }
}
