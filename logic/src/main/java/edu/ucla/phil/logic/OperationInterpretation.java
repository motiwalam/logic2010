package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class OperationInterpretation extends SymbolInterpretation {
   Hashtable valueTable;
   Integer defaultValue;

   OperationInterpretation(String s, int i) {
      super(s, i);
      this.clearValues();
   }

   OperationInterpretation() {
      this.clearValues();
   }

   @Override
   void clearValues() {
      this.valueTable = null;
      this.defaultValue = null;
   }

   boolean parseValueTable(String s) {
      DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\{;");
      delimitedtokenizer.setInput(s);

      while (delimitedtokenizer.getRemaining() != null) {
         String s1 = delimitedtokenizer.nextToken();
         Integer integer = LogicProgram.parseInteger(s1.trim());
         if (integer == null) {
            return false;
         }

         if (delimitedtokenizer.getDelimiter() == '{' || this.defaultValue != null) {
            while (delimitedtokenizer.getDelimiter() == '{') {
               ExpressionPath expressionpath = ExpressionPath.fromArray(ExpressionPath.parse("{" + delimitedtokenizer.nextToken()));
               if (expressionpath == null || expressionpath.depth != this.arity) {
                  return false;
               }

               if (this.valueTable == null) {
                  this.valueTable = new Hashtable();
               }

               this.valueTable.put(expressionpath, integer);
            }
         } else {
            this.defaultValue = integer;
         }
      }

      return true;
   }

   @Override
   void restrictToUniverse(int i) {
      if (this.defaultValue != null && this.defaultValue >= i) {
         this.defaultValue = null;
      }

      if (this.valueTable != null) {
         Hashtable hashtable = new Hashtable();
         Enumeration enumeration = this.valueTable.keys();

         while (enumeration.hasMoreElements()) {
            ExpressionPath expressionpath = (ExpressionPath)enumeration.nextElement();
            Integer integer = (Integer)this.valueTable.get(expressionpath);
            if (integer < i) {
               int[] aint = expressionpath.toArray();
               int k = aint.length;
               int j = 0;

               while (j < k && aint[j] < i) {
                  j++;
               }

               if (j >= k) {
                  hashtable.put(expressionpath, integer);
               }
            }
         }

         this.valueTable = hashtable;
      }
   }

   @Override
   String encode() {
      return super.encode() + this.encodeValueTable();
   }

   String encodeValueTable() {
      String object = "";
      boolean flag = false;
      if (this.valueTable != null) {
         Hashtable hashtable = groupByValue(this.valueTable);
         Enumeration enumeration = hashtable.keys();

         while (enumeration.hasMoreElements()) {
            Object object1 = enumeration.nextElement();
            Vector vector = (Vector)hashtable.get(object1);
            object = object + (flag ? ";" : "") + object1;
            flag = true;
            int i = vector == null ? 0 : vector.size();

            for (int j = 0; j < i; j++) {
               object = object + vector.elementAt(j);
            }
         }
      }

      if (this.defaultValue != null) {
         object = object + (flag ? ";" : "") + this.defaultValue;
      }

      return (String)object;
   }

   static Hashtable groupByValue(Hashtable hashtable) {
      Hashtable hashtable1 = new Hashtable();
      Enumeration enumeration = hashtable.keys();

      while (enumeration.hasMoreElements()) {
         Object object = enumeration.nextElement();
         Object object1 = hashtable.get(object);
         Vector vector = (Vector)hashtable1.get(object1);
         if (vector == null) {
            hashtable1.put(object1, vector = new Vector());
         }

         vector.addElement(object);
      }

      return hashtable1;
   }

   @Override
   String describeValues(int i) {
      if (i == 0) {
         return "";
      } else if (this.arity == 0) {
         return this.defaultValue == null ? "0" : this.defaultValue.toString();
      } else {
         boolean flag = false;
         String s = "";
         int[] aint = new int[this.arity];

         for (int j = 0; j < this.arity; j++) {
            aint[j] = 0;
         }

         int k;
         do {
            String s1 = s + (flag ? "; " : "") + this.name + "(";
            flag = true;

            for (int l = 0; l < this.arity; l++) {
               s1 = s1 + (l == 0 ? "" : ",") + aint[l];
            }

            s = s1 + ")=" + this.getValue(aint);

            for (k = 0; k < this.arity && ++aint[k] == i; k++) {
               aint[k] = 0;
            }
         } while (k != this.arity);

         return s;
      }
   }

   @Override
   Object getValue(int[] aint) {
      if (aint == null ? this.arity == 0 : aint.length == this.arity) {
         if (this.arity != 0 && this.valueTable != null) {
            Integer integer = (Integer)this.valueTable.get(ExpressionPath.fromArray(aint));
            return integer == null ? (this.defaultValue == null ? new Integer(0) : this.defaultValue) : integer;
         } else {
            return this.defaultValue == null ? new Integer(0) : this.defaultValue;
         }
      } else {
         throw new IllegalArgumentException();
      }
   }
}
