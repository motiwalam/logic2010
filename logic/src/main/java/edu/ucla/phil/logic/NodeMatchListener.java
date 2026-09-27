package edu.ucla.phil.logic;

import java.util.Vector;

interface NodeMatchListener {
   void nodeMatched(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1);

   void nodeMismatched(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1);
}
