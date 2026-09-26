package edu.ucla.phil.logic;

class C_i_C implements C_EA {
   boolean f1173;

   public C_i_C() {
      this(false);
   }

   public C_i_C(boolean flag) {
      this.f1173 = flag;
   }

   @Override
   public boolean m493(Object object, Object object1) {
      if (object instanceof String && object1 instanceof String) {
         if (this.f1173) {
            object = ((String)object).toUpperCase();
            object1 = ((String)object1).toUpperCase();
         }

         return ((String)object1).compareTo((String)object) >= 0;
      } else {
         throw new IllegalArgumentException("LexicalOrder expected two strings.");
      }
   }
}
