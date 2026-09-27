package edu.ucla.phil.logic;

import java.util.Vector;

class HintCollector implements NodeMatchListener {
   Vector errors;
   SymbolizationNode target;
   SymbolizationHint hint;

   HintCollector(SymbolizationNode symbolizationnode) {
      this.reset(symbolizationnode);
   }

   void reset(SymbolizationNode symbolizationnode) {
      this.errors = new Vector();
      this.target = symbolizationnode;
      this.hint = null;
   }

   Vector getErrors() {
      return this.errors;
   }

   SymbolizationHint getHint() {
      return this.hint;
   }

   @Override
   public void nodeMatched(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
      if (this.hint == null && symbolizationnode == this.target) {
         this.hint = new SymbolizationHint(symbolizationnode, symbolizationnode1, vector, vector1);
      }
   }

   @Override
   public void nodeMismatched(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
      if (this.hint == null && symbolizationnode == this.target) {
         this.hint = new SymbolizationHint(symbolizationnode, symbolizationnode1, vector, vector1);
      }

      this.errors.addElement(new SymbolizationErrorButton(symbolizationnode, symbolizationnode1, vector, vector1));
   }
}
