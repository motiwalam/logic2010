package edu.ucla.phil.logic;

class C_IE {
   String f422;
   int f423;

   C_IE(String s, int i) {
      this.f422 = s;
      this.f423 = i;
   }

   C_IE() {
      this(null, 0);
   }

   void m705() {
   }

   static C_IE m706(String s) {
      int i = s.indexOf("(");
      if (i == -1) {
         return null;
      } else {
         String s1 = s.substring(0, i);
         s = s.substring(i + 1);
         i = s.indexOf(")");
         if (i == -1) {
            return null;
         } else {
            Integer integer = LogicProgram.parseInteger(s.substring(0, i));
            if (integer == null) {
               return null;
            } else {
               int j = integer;
               s = s.substring(i + 1);
               DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\{,");
               delimitedtokenizer.m1132(s);
               if (delimitedtokenizer.m1135().trim().equals("")) {
                  C_a_C c_a_c = new C_a_C();
                  c_a_c.f422 = s1;
                  c_a_c.f423 = j;
                  if (c_a_c.m1640(s)) {
                     return c_a_c;
                  }
               } else {
                  C_g_E c_g_e = new C_g_E();
                  c_g_e.f422 = s1;
                  c_g_e.f423 = j;
                  if (c_g_e.m1830(s)) {
                     return c_g_e;
                  }
               }

               return null;
            }
         }
      }
   }

   void m707(int i) {
   }

   Object m708(int[] aint) {
      return null;
   }

   String m709() {
      return this.m710();
   }

   @Override
   public String toString() {
      return this.m709();
   }

   String m710() {
      return this.f422 + "(" + this.f423 + ")";
   }

   String m711(int i) {
      return "";
   }

   @Override
   public boolean equals(Object object) {
      return !(object instanceof C_IE) ? false : ((C_IE)object).f422.equals(this.f422) && ((C_IE)object).f423 == this.f423;
   }

   @Override
   public int hashCode() {
      return (this.f422 == null ? 0 : this.f422.hashCode()) + this.f423 * 40503;
   }
}
