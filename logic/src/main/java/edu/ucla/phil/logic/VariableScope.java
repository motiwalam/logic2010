package edu.ucla.phil.logic;

import java.util.Hashtable;

class VariableScope extends Hashtable {
   private VariableScope shadowed = null;

   public VariableScope() {
   }

   public VariableScope(int i) {
      super(i);
   }

   public VariableScope(int i, float f) {
      super(i, f);
   }

   public void push(Object object, Object object1) {
      Object object2 = this.put(object, object1);
      if (object2 != null) {
         if (this.shadowed == null) {
            this.shadowed = new VariableScope();
         }

         this.shadowed.push(object, object2);
      }
   }

   public Object pop(Object object) {
      Object object1 = this.remove(object);
      if (object1 != null && this.shadowed != null) {
         Object object2 = this.shadowed.pop(object);
         if (object2 != null) {
            this.put(object, object2);
         }
      }

      return object1;
   }

   public Object getShadowed(Object object, int i) {
      if (i == 0) {
         return this.get(object);
      } else {
         return this.shadowed == null ? null : this.shadowed.getShadowed(object, i - 1);
      }
   }
}
