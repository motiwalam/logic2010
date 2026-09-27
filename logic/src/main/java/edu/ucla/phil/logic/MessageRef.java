package edu.ucla.phil.logic;

import java.util.Hashtable;

class MessageRef {
   String id;
   Hashtable params;

   MessageRef(String s, Hashtable hashtable) {
      this.id = s;
      this.params = hashtable;
   }

   String getId() {
      return this.id;
   }

   Hashtable getParams() {
      return this.params;
   }

   MessageRef putParam(String s, String s1) {
      if (this.params == null) {
         this.params = new Hashtable();
      }

      Message.putParam(this.params, s, s1);
      return this;
   }
}
