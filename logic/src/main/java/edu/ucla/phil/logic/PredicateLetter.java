package edu.ucla.phil.logic;

import java.util.Vector;

class PredicateLetter extends SchematicLetter {
   String letter;
   int arity;
   Vector deferredMatches;

   PredicateLetter(AtomicFormula atomicformula) {
      this(atomicformula.symbol, atomicformula.childCount);
   }

   PredicateLetter(String s, int i) {
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
      if (!(object instanceof PredicateLetter)) {
         return false;
      } else {
         PredicateLetter predicateletter1 = (PredicateLetter)object;
         return this.letter.equals(predicateletter1.letter) && this.arity == predicateletter1.arity;
      }
   }

   @Override
   public String toString() {
      if (this.arity == 0) {
         return this.letter;
      } else {
         return this.arity == 1 && AtomicFormula.isPredicateLetter(this.letter)
            ? this.letter + placeholder(0)
            : this.letter + "(" + placeholders(0, this.arity) + ")";
      }
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
      AtomicFormula atomicformula = new AtomicFormula(this.letter);

      for (int i = 0; i < this.arity; i++) {
         atomicformula.addChild(new SimpleTerm(placeholder(i)));
      }

      return atomicformula;
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
      return freshPredicateLetter(!AtomicFormula.isPredicateLetter(this.letter), this.arity, vector);
   }

   static SchematicLetter freshPredicateLetter(int i, Vector vector) {
      return freshPredicateLetter(false, i, vector);
   }

   static SchematicLetter freshPredicateLetter(boolean flag, int i, Vector vector) {
      LetterGenerator lettergenerator = new LetterGenerator(
         LogicProgram.reverse(i != 0 && !flag ? LogicProgram.predicateLetters : LogicProgram.sentenceLetters)
      );

      while (lettergenerator.hasMoreElements()) {
         PredicateLetter predicateletter = new PredicateLetter((String)lettergenerator.nextElement(), i);
         if (vector.indexOf(predicateletter) == -1) {
            vector.addElement(predicateletter);
            return predicateletter;
         }
      }

      return null;
   }
}
