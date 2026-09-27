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
   public void setError(String s, Hashtable hashtable) {
      this.id = s;
      this.params = hashtable;
   }

   @Override
   public ErrorRef getError() {
      return this;
   }
}
