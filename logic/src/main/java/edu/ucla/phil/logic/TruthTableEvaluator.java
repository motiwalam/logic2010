package edu.ucla.phil.logic;

import java.util.Vector;

class TruthTableEvaluator {
   Expression formula;
   ArgumentParser argument;
   Vector sentenceLetters;
   boolean[] rowResults;

   TruthTableEvaluator(Expression expression) {
      this.argument = null;
      this.sentenceLetters = new Vector();
      this.collectSentenceLetters(this.formula = expression);
      this.computeRows();
   }

   TruthTableEvaluator(ArgumentParser argumentparser) {
      this.formula = null;
      this.argument = argumentparser;
      this.sentenceLetters = new Vector();
      int i = argumentparser.premises.length;

      for (int j = 0; j < i; j++) {
         this.collectSentenceLetters(argumentparser.premises[j]);
      }

      this.collectSentenceLetters(argumentparser.conclusion);
      this.computeRows();
   }

   void collectSentenceLetters(Expression expression) {
      if (expression instanceof ConnectiveFormula) {
         int i = expression.getChildCount();

         for (int j = 0; j < i; j++) {
            this.collectSentenceLetters(expression.getChild(j));
         }
      } else if (expression != null && this.indexOfLetter(expression) == -1) {
         this.sentenceLetters.addElement(expression);
      }
   }

   ErrorRef parseLetterOrder(String s) {
      Vector vector = new Vector();

      while (s != null) {
         int i = s.indexOf(46);
         String s1;
         if (i == -1) {
            s1 = s;
            s = null;
         } else {
            s1 = s.substring(0, i);
            s = s.substring(i + 1);
         }

         if ((s1 = s1.trim()).length() != 0) {
            Expression expression;
            try {
               expression = LogicProgram.parseFormula(s1);
            } catch (FormulaParseException formulaparseexception) {
               expression = null;
            }

            if (expression == null) {
               vector.addElement(new ErrorRef("truerr016", Message.params("unparsed", s1)));
            } else {
               vector.addElement(expression);
            }
         }
      }

      return this.setLetterOrder(vector);
   }

   ErrorRef setLetterOrder(Vector vector) {
      int j = vector.size();
      int[] aint = new int[j];

      for (int i = 0; i < j; i++) {
         Object object = vector.elementAt(i);
         if (object instanceof ErrorRef) {
            return (ErrorRef)object;
         }

         aint[i] = this.indexOfLetter((Expression)object);
         if (aint[i] == -1) {
            return new ErrorRef("truerr017", Message.params("sentence", object + ""));
         }

         for (int k = 0; k < i; k++) {
            if (aint[i] == aint[k]) {
               return new ErrorRef("truerr018", Message.params("sentence", object + ""));
            }
         }
      }

      if (this.sentenceLetters.size() != j) {
         return new ErrorRef("truerr015");
      } else {
         Vector vector1 = new Vector();

         for (int l = 0; l < j; l++) {
            vector1.addElement(this.sentenceLetters.elementAt(aint[l]));
         }

         this.sentenceLetters = vector1;
         this.computeRows();
         return new ErrorRef(null);
      }
   }

   void computeRows() {
      int i = this.sentenceLetters.size();
      this.rowResults = new boolean[i == 0 ? 0 : 1 << i];
      if (this.formula != null) {
         this.evaluateAllRows(this.formula, this.rowResults);
      } else if (this.argument != null) {
         int j = this.argument.premises.length;
         this.evaluateAllRows(this.argument.conclusion, this.rowResults);
         boolean[] aboolean = new boolean[this.rowResults.length];

         for (int k = 0; k < j; k++) {
            this.evaluateAllRows(this.argument.premises[k], aboolean);

            for (int l = 0; l < this.rowResults.length; l++) {
               if (!aboolean[l]) {
                  this.rowResults[l] = true;
               }
            }
         }
      }
   }

   void evaluateAllRows(Expression expression, boolean[] aboolean) {
      int i = aboolean.length;

      for (int j = 0; j < i; j++) {
         aboolean[j] = this.evaluate(expression, this.rowAssignment(j));
      }
   }

   boolean evaluate(Expression expression, boolean[] aboolean) {
      if (expression instanceof ConnectiveFormula) {
         String s = expression.getSymbol();
         if (s.equals("~")) {
            return !this.evaluate(expression.getChild(0), aboolean);
         } else if (s.equals("&")) {
            return this.evaluate(expression.getChild(0), aboolean) & this.evaluate(expression.getChild(1), aboolean);
         } else if (s.equals("|")) {
            return this.evaluate(expression.getChild(0), aboolean) | this.evaluate(expression.getChild(1), aboolean);
         } else if (s.equals("->")) {
            return !this.evaluate(expression.getChild(0), aboolean) | this.evaluate(expression.getChild(1), aboolean);
         } else {
            return s.equals("<->") ? this.evaluate(expression.getChild(0), aboolean) == this.evaluate(expression.getChild(1), aboolean) : false;
         }
      } else {
         int i = this.indexOfLetter(expression);
         return i == -1 ? false : aboolean[i];
      }
   }

   int indexOfLetter(Expression expression) {
      if (expression == null) {
         return -1;
      } else {
         int i = this.sentenceLetters.size();

         for (int j = 0; j < i; j++) {
            if (expression.isAlphaEquivalent((Expression)this.sentenceLetters.elementAt(j), new BinderMap())) {
               return j;
            }
         }

         return -1;
      }
   }

   boolean[] rowAssignment(long i) {
      int j = this.sentenceLetters.size();
      boolean[] aboolean = new boolean[j];

      for (int k = 0; k < j; k++) {
         aboolean[k] = (i & 1L) != 0L;
         i >>= 1;
      }

      return aboolean;
   }

   boolean isAllTrue() {
      int i = this.rowResults.length;

      for (int j = 0; j < i; j++) {
         if (!this.rowResults[j]) {
            return false;
         }
      }

      return true;
   }

   static boolean areEquivalent(Expression expression, Expression expression1) {
      if (expression instanceof Formula && expression1 instanceof Formula) {
         ConnectiveFormula connectiveformula = new ConnectiveFormula("<->");
         connectiveformula.addChild(expression);
         connectiveformula.addChild(expression1);
         return new TruthTableEvaluator(connectiveformula).isAllTrue();
      } else {
         return false;
      }
   }
}
