package edu.ucla.phil.logic;

import java.util.Hashtable;

class ErrorRef extends MessageRef implements ResponseHandler {
   ErrorRef(String s) {
      this(s, null);
   }

   ErrorRef(String s, Hashtable hashtable) {
      super(s, hashtable);
   }

   @Override
   public void m4(String s, Hashtable hashtable) {
      this.f427 = s;
      this.f428 = hashtable;
   }

   @Override
   public ErrorRef m5() {
      return this;
   }
}
