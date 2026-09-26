package edu.ucla.phil.logic;

import java.util.Hashtable;

class MessageRef {
   String f427;
   Hashtable f428;

   MessageRef(String s, Hashtable hashtable) {
      this.f427 = s;
      this.f428 = hashtable;
   }

   String m716() {
      return this.f427;
   }

   Hashtable m717() {
      return this.f428;
   }

   MessageRef m718(String s, String s1) {
      if (this.f428 == null) {
         this.f428 = new Hashtable();
      }

      Message.putParam(this.f428, s, s1);
      return this;
   }
}
