package edu.ucla.phil.logic;

class SymbolInterpretation {
   String name;
   int arity;

   SymbolInterpretation(String s, int i) {
      this.name = s;
      this.arity = i;
   }

   SymbolInterpretation() {
      this(null, 0);
   }

   void clearValues() {
   }

   static SymbolInterpretation parse(String s) {
      int i = s.indexOf("(");
      if (i == -1) {
         return null;
      } else {
         String s1 = s.substring(0, i);
         String s2 = s.substring(i + 1);
         i = s2.indexOf(")");
         if (i == -1) {
            return null;
         } else {
            Integer integer = LogicProgram.parseInteger(s2.substring(0, i));
            if (integer == null) {
               return null;
            } else {
               int j = integer;
               s = s2.substring(i + 1);
               DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\{,");
               delimitedtokenizer.setInput(s);
               if (delimitedtokenizer.nextToken().trim().equals("")) {
                  PredicateInterpretation predicateinterpretation = new PredicateInterpretation();
                  predicateinterpretation.name = s1;
                  predicateinterpretation.arity = j;
                  if (predicateinterpretation.parseExtension(s)) {
                     return predicateinterpretation;
                  }
               } else {
                  OperationInterpretation operationinterpretation = new OperationInterpretation();
                  operationinterpretation.name = s1;
                  operationinterpretation.arity = j;
                  if (operationinterpretation.parseValueTable(s)) {
                     return operationinterpretation;
                  }
               }

               return null;
            }
         }
      }
   }

   void restrictToUniverse(int i) {
   }

   Object getValue(int[] aint) {
      return null;
   }

   String encode() {
      return this.getSignature();
   }

   @Override
   public String toString() {
      return this.encode();
   }

   String getSignature() {
      return this.name + "(" + this.arity + ")";
   }

   String describeValues(int i) {
      return "";
   }

   @Override
   public boolean equals(Object object) {
      return !(object instanceof SymbolInterpretation)
         ? false
         : ((SymbolInterpretation)object).name.equals(this.name) && ((SymbolInterpretation)object).arity == this.arity;
   }

   @Override
   public int hashCode() {
      return (this.name == null ? 0 : this.name.hashCode()) + this.arity * 40503;
   }
}
