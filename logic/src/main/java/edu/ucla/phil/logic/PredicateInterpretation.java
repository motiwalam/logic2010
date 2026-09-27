package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Vector;

class PredicateInterpretation extends SymbolInterpretation {
   Vector extension;

   PredicateInterpretation(String s, int i) {
      super(s, i);
      this.clearValues();
   }

   PredicateInterpretation() {
      this.clearValues();
   }

   @Override
   void clearValues() {
      this.extension = null;
   }

   boolean parseExtension(String s) {
      DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\{");
      delimitedtokenizer.setInput(s);

      while (delimitedtokenizer.getRemaining() != null) {
         String s1 = delimitedtokenizer.nextToken();
         if (!s1.trim().equals("")) {
            return false;
         }

         while (delimitedtokenizer.getDelimiter() == '{') {
            ExpressionPath expressionpath = ExpressionPath.fromArray(ExpressionPath.parse("{" + delimitedtokenizer.nextToken()));
            if (expressionpath == null || expressionpath.depth != this.arity) {
               return false;
            }

            if (this.extension == null) {
               this.extension = new Vector();
            }

            if (!this.extension.contains(expressionpath)) {
               this.extension.addElement(expressionpath);
            }
         }
      }

      return true;
   }

   @Override
   void restrictToUniverse(int i) {
      if (this.extension != null) {
         Vector vector = new Vector();
         Enumeration enumeration = this.extension.elements();

         while (enumeration.hasMoreElements()) {
            ExpressionPath expressionpath = (ExpressionPath)enumeration.nextElement();
            int[] aint = expressionpath.toArray();
            int k = aint.length;
            int j = 0;

            while (j < k && aint[j] < i) {
               j++;
            }

            if (j >= k) {
               vector.addElement(expressionpath);
            }
         }

         this.extension = vector;
      }
   }

   @Override
   String encode() {
      return super.encode() + this.encodeExtension();
   }

   String encodeExtension() {
      String object = "";
      int i = this.extension == null ? 0 : this.extension.size();

      for (int j = 0; j < i; j++) {
         object = object + this.extension.elementAt(j);
      }

      return (String)object;
   }

   @Override
   String describeValues(int i) {
      if (i == 0) {
         return "";
      } else if (this.arity == 0) {
         return this.extension != null && !this.extension.isEmpty() ? "True" : "False";
      } else {
         String s = "{";
         int k = this.extension == null ? 0 : this.extension.size();
         if (this.arity == 1) {
            for (int j = 0; j < k; j++) {
               s = s + (j == 0 ? "" : ", ") + ((ExpressionPath)this.extension.elementAt(j)).toArray()[0];
            }
         } else {
            for (int i1 = 0; i1 < k; i1++) {
               String s1 = s + (i1 == 0 ? "" : ", ") + "(";
               int[] aint = ((ExpressionPath)this.extension.elementAt(i1)).toArray();

               for (int l = 0; l < this.arity; l++) {
                  s1 = s1 + (l == 0 ? "" : ",") + aint[l];
               }

               s = s1 + ")";
            }
         }

         return s + "}";
      }
   }

   @Override
   Object getValue(int[] aint) {
      if (aint == null ? this.arity == 0 : aint.length == this.arity) {
         if (this.extension == null || this.extension.isEmpty()) {
            return Boolean.FALSE;
         } else {
            return this.arity == 0 ? Boolean.TRUE : new Boolean(this.extension.contains(ExpressionPath.fromArray(aint)));
         }
      } else {
         throw new IllegalArgumentException();
      }
   }
}
