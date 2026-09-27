package edu.ucla.phil.logic;

import java.util.Vector;

class OperationLetter extends SchematicLetter {
   String letter;
   int arity;
   Vector deferredMatches;

   OperationLetter(OperationTerm operationterm) {
      this(operationterm.symbol, operationterm.childCount);
   }

   OperationLetter(String s, int i) {
      this.letter = s;
      this.arity = i;
      this.deferredMatches = null;
   }

   @Override
   public int hashCode() {
      return this.letter.hashCode();
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof OperationLetter)) {
         return false;
      } else {
         OperationLetter operationletter1 = (OperationLetter)object;
         return this.letter.equals(operationletter1.letter) && this.arity == operationletter1.arity;
      }
   }

   @Override
   public String toString() {
      return this.arity == 0 ? this.letter : this.letter + "(" + placeholders(0, this.arity) + ")";
   }

   @Override
   public String getLetter() {
      return this.letter;
   }

   @Override
   public int getArity() {
      return this.arity;
   }

   @Override
   public Expression toExpression() {
      OperationTerm operationterm = new OperationTerm(this.letter);

      for (int i = 0; i < this.arity; i++) {
         operationterm.addChild(new SimpleTerm(placeholder(i)));
      }

      return operationterm;
   }

   @Override
   Vector getDeferredMatches(boolean flag) {
      if (flag && this.deferredMatches == null) {
         this.deferredMatches = new Vector();
      }

      return this.deferredMatches;
   }

   @Override
   public SchematicLetter freshLetter(Vector vector) {
      return freshOperationLetter(this.arity, vector);
   }

   static SchematicLetter freshOperationLetter(int i, Vector vector) {
      LetterGenerator lettergenerator = new LetterGenerator(LogicProgram.reverse(LogicProgram.operationLetters));

      while (lettergenerator.hasMoreElements()) {
         OperationLetter operationletter = new OperationLetter((String)lettergenerator.nextElement(), i);
         if (vector.indexOf(operationletter) == -1) {
            vector.addElement(operationletter);
            return operationletter;
         }
      }

      return null;
   }
}
