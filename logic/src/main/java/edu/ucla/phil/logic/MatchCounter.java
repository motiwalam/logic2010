package edu.ucla.phil.logic;

import java.util.Vector;

class MatchCounter implements NodeMatchListener {
   int count;

   MatchCounter() {
      this.reset();
   }

   void reset() {
      this.count = 0;
   }

   int getCount() {
      return this.count;
   }

   @Override
   public void nodeMatched(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
      this.count++;
   }

   @Override
   public void nodeMismatched(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
   }
}
