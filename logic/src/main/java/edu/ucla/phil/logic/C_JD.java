package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_JD extends Hashtable {
   private C_JD f435 = null;

   public C_JD() {
   }

   public C_JD(int i) {
      super(i);
   }

   public C_JD(int i, float f) {
      super(i, f);
   }

   public void m723(Object object, Object object1) {
      Object object2 = this.put(object, object1);
      if (object2 != null) {
         if (this.f435 == null) {
            this.f435 = new C_JD();
         }

         this.f435.m723(object, object2);
      }
   }

   public Object m724(Object object) {
      Object object1 = this.remove(object);
      if (object1 != null && this.f435 != null) {
         Object object2 = this.f435.m724(object);
         if (object2 != null) {
            this.put(object, object2);
         }
      }

      return object1;
   }

   public Object m725(Object object, int i) {
      if (i == 0) {
         return this.get(object);
      } else {
         return this.f435 == null ? null : this.f435.m725(object, i - 1);
      }
   }
}
