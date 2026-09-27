package edu.ucla.phil.logic;

class LexicalOrder implements OrderPredicate {
   boolean ignoreCase;

   public LexicalOrder() {
      this(false);
   }

   public LexicalOrder(boolean flag) {
      this.ignoreCase = flag;
   }

   @Override
   public boolean inOrder(Object object, Object object1) {
      if (object instanceof String && object1 instanceof String) {
         if (this.ignoreCase) {
            object = ((String)object).toUpperCase();
            object1 = ((String)object1).toUpperCase();
         }

         return ((String)object1).compareTo((String)object) >= 0;
      } else {
         throw new IllegalArgumentException("LexicalOrder expected two strings.");
      }
   }
}
