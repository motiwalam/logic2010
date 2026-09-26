package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Vector;

class C_a_C extends C_IE {
   Vector f969;

   C_a_C(String s, int i) {
      super(s, i);
      this.m705();
   }

   C_a_C() {
      this.m705();
   }

   @Override
   void m705() {
      this.f969 = null;
   }

   boolean m1640(String s) {
      DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\{");
      delimitedtokenizer.m1132(s);

      while (delimitedtokenizer.m1133() != null) {
         String s1 = delimitedtokenizer.m1135();
         if (!s1.trim().equals("")) {
            return false;
         }

         while (delimitedtokenizer.m1134() == '{') {
            ExpressionPath expressionpath = ExpressionPath.m1746(ExpressionPath.m1755("{" + delimitedtokenizer.m1135()));
            if (expressionpath == null || expressionpath.depth != this.f423) {
               return false;
            }

            if (this.f969 == null) {
               this.f969 = new Vector();
            }

            if (!this.f969.contains(expressionpath)) {
               this.f969.addElement(expressionpath);
            }
         }
      }

      return true;
   }

   @Override
   void m707(int i) {
      if (this.f969 != null) {
         Vector vector = new Vector();
         Enumeration enumeration = this.f969.elements();

         while (enumeration.hasMoreElements()) {
            ExpressionPath expressionpath = (ExpressionPath)enumeration.nextElement();
            int[] aint = expressionpath.m1752();
            int k = aint.length;
            int j = 0;

            while (j < k && aint[j] < i) {
               j++;
            }

            if (j >= k) {
               vector.addElement(expressionpath);
            }
         }

         this.f969 = vector;
      }
   }

   @Override
   String m709() {
      return super.m709() + this.m1641();
   }

   String m1641() {
      Object object = "";
      int i = this.f969 == null ? 0 : this.f969.size();

      for (int j = 0; j < i; j++) {
         object = object + this.f969.elementAt(j);
      }

      return (String)object;
   }

   @Override
   String m711(int i) {
      if (i == 0) {
         return "";
      } else if (this.f423 == 0) {
         return this.f969 != null && !this.f969.isEmpty() ? "True" : "False";
      } else {
         String s = "{";
         int k = this.f969 == null ? 0 : this.f969.size();
         if (this.f423 == 1) {
            for (int j = 0; j < k; j++) {
               s = s + (j == 0 ? "" : ", ") + ((ExpressionPath)this.f969.elementAt(j)).m1752()[0];
            }
         } else {
            for (int i1 = 0; i1 < k; i1++) {
               s = s + (i1 == 0 ? "" : ", ") + "(";
               int[] aint = ((ExpressionPath)this.f969.elementAt(i1)).m1752();

               for (int l = 0; l < this.f423; l++) {
                  s = s + (l == 0 ? "" : ",") + aint[l];
               }

               s = s + ")";
            }
         }

         return s + "}";
      }
   }

   @Override
   Object m708(int[] aint) {
      if (aint == null ? this.f423 == 0 : aint.length == this.f423) {
         if (this.f969 == null || this.f969.isEmpty()) {
            return Boolean.FALSE;
         } else {
            return this.f423 == 0 ? Boolean.TRUE : new Boolean(this.f969.contains(ExpressionPath.m1746(aint)));
         }
      } else {
         throw new IllegalArgumentException();
      }
   }
}
