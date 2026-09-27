package edu.ucla.phil.logic;

import java.util.Vector;

class BinderKey {
   Expression context;
   Expression binder;
   SimpleTerm[] contextTerms;

   BinderKey(Expression expression, Expression expression1, Vector vector) {
      this.context = expression;
      this.binder = expression1;
      this.contextTerms = toArray(vector);
   }

   static SimpleTerm[] toArray(Vector vector) {
      int i = vector == null ? 0 : vector.size();
      SimpleTerm[] asimpleterm = i == 0 ? null : new SimpleTerm[i];
      if (i != 0) {
         vector.copyInto(asimpleterm = new SimpleTerm[i]);
      }

      return asimpleterm;
   }

   static Vector toVector(SimpleTerm[] asimpleterm) {
      Vector vector = new Vector();
      int i = asimpleterm == null ? 0 : asimpleterm.length;

      for (int j = 0; j < i; j++) {
         vector.addElement(asimpleterm[j]);
      }

      return vector;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof BinderKey)) {
         return false;
      } else {
         BinderKey binderkey1 = (BinderKey)object;
         if (this.context == binderkey1.context && this.binder == binderkey1.binder) {
            SimpleTerm[] asimpleterm = binderkey1.contextTerms;
            int i = this.contextTerms == null ? 0 : this.contextTerms.length;
            int j = asimpleterm == null ? 0 : asimpleterm.length;
            if (i != j) {
               return false;
            } else {
               for (int k = 0; k < i; k++) {
                  if (this.contextTerms[k] != asimpleterm[k]) {
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
      int i = (this.context == null ? 0 : this.context.hashCode()) + this.binder.hashCode();
      int j = this.contextTerms == null ? 0 : this.contextTerms.length;

      for (int k = 0; k < j; k++) {
         i += this.contextTerms[k].hashCode();
      }

      return i;
   }
}
