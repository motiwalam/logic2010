package edu.ucla.phil.logic;

import java.util.Vector;

class SymbolCollector {
   Vector predicates = new Vector();
   Vector operations = new Vector();

   void collect(Expression expression) {
      if (expression instanceof AtomicFormula) {
         PredicateInterpretation predicateinterpretation = new PredicateInterpretation(expression.getSymbol(), expression.getChildCount());
         if (!this.predicates.contains(predicateinterpretation)) {
            this.predicates.addElement(predicateinterpretation);
         }
      } else if (expression instanceof OperationTerm) {
         OperationInterpretation operationinterpretation = new OperationInterpretation(expression.getSymbol(), expression.getChildCount());
         if (!this.operations.contains(operationinterpretation)) {
            this.operations.addElement(operationinterpretation);
         }
      }

      int j = expression == null ? 0 : expression.getChildCount();

      for (int i = 0; i < j; i++) {
         this.collect(expression.getChild(i));
      }
   }

   void mergeInterpretations(LPInvalidation lpinvalidation) {
      int i = lpinvalidation.symbols == null ? 0 : lpinvalidation.symbols.size();

      for (int j = 0; j < i; j++) {
         SymbolInterpretation symbolinterpretation = (SymbolInterpretation)lpinvalidation.symbols.elementAt(j);
         if (symbolinterpretation instanceof PredicateInterpretation) {
            int k = this.predicates.indexOf(symbolinterpretation);
            if (k != -1) {
               this.predicates.setElementAt(symbolinterpretation, k);
            }
         } else if (symbolinterpretation instanceof OperationInterpretation) {
            int l = this.operations.indexOf(symbolinterpretation);
            if (l != -1) {
               this.operations.setElementAt(symbolinterpretation, l);
            }
         }
      }
   }

   Vector getAllSymbols() {
      Vector vector = new Vector();
      int j = this.predicates.size();

      for (int i = 0; i < j; i++) {
         vector.addElement(this.predicates.elementAt(i));
      }

      j = this.operations.size();

      for (int k = 0; k < j; k++) {
         vector.addElement(this.operations.elementAt(k));
      }

      return vector;
   }
}
