package edu.ucla.phil.logic;

import java.util.Vector;

class TermLetter extends SchematicLetter {
   String letter;
   Vector deferredMatches;

   TermLetter(SimpleTerm simpleterm) {
      this(simpleterm.symbol);
   }

   TermLetter(String s) {
      this.letter = s;
      this.deferredMatches = null;
   }

   @Override
   public int hashCode() {
      return this.letter.hashCode();
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof TermLetter)) {
         return false;
      } else {
         TermLetter termletter1 = (TermLetter)object;
         return this.letter.equals(termletter1.letter);
      }
   }

   @Override
   public String toString() {
      return this.letter;
   }

   @Override
   public String getLetter() {
      return this.letter;
   }

   @Override
   public int getArity() {
      return 0;
   }

   @Override
   public Expression toExpression() {
      return new SimpleTerm(this.letter);
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
      return freshTermLetter(vector);
   }

   static SchematicLetter freshTermLetter(Vector vector) {
      LetterGenerator lettergenerator = new LetterGenerator(LogicProgram.variableLetters);

      while (lettergenerator.hasMoreElements()) {
         TermLetter termletter = new TermLetter((String)lettergenerator.nextElement());
         if (vector.indexOf(termletter) == -1) {
            vector.addElement(termletter);
            return termletter;
         }
      }

      return null;
   }
}
