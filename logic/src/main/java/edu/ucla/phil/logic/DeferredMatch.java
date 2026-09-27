package edu.ucla.phil.logic;

import java.util.Vector;

class DeferredMatch {
   Expression pattern;
   Expression instance;
   BinderMap binderMap;
   SimpleTerm[] contextTerms;

   DeferredMatch(Expression expression, Expression expression1, BinderMap bindermap, Vector vector) {
      this.pattern = expression;
      this.instance = expression1;
      this.binderMap = bindermap;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof DeferredMatch)) {
         return false;
      } else {
         DeferredMatch deferredmatch1 = (DeferredMatch)object;
         return this.pattern.isIdentical(deferredmatch1.pattern)
            && (this.instance == null ? deferredmatch1.instance == null : this.instance.isIdentical(deferredmatch1.instance));
      }
   }

   @Override
   public int hashCode() {
      return this.toString().hashCode();
   }

   @Override
   public String toString() {
      return this.pattern + ":" + this.instance;
   }
}
