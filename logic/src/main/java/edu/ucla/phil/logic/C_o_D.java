package edu.ucla.phil.logic;

import java.util.Vector;

class C_o_D {
   Expression f1325;
   Expression f1326;
   SimpleTerm[] f1327;

   C_o_D(Expression expression, Expression expression1, Vector vector) {
      this.f1325 = expression;
      this.f1326 = expression1;
      this.f1327 = m2007(vector);
   }

   static SimpleTerm[] m2007(Vector vector) {
      int i = vector == null ? 0 : vector.size();
      SimpleTerm[] asimpleterm = i == 0 ? null : new SimpleTerm[i];
      if (i != 0) {
         vector.copyInto(asimpleterm = new SimpleTerm[i]);
      }

      return asimpleterm;
   }

   static Vector m2008(SimpleTerm[] asimpleterm) {
      Vector vector = new Vector();
      int i = asimpleterm == null ? 0 : asimpleterm.length;

      for (int j = 0; j < i; j++) {
         vector.addElement(asimpleterm[j]);
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
            SimpleTerm[] asimpleterm = c_o_d1.f1327;
            int i = this.f1327 == null ? 0 : this.f1327.length;
            int j = asimpleterm == null ? 0 : asimpleterm.length;
            if (i != j) {
               return false;
            } else {
               for (int k = 0; k < i; k++) {
                  if (this.f1327[k] != asimpleterm[k]) {
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
