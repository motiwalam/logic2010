package edu.ucla.phil.logic;

import java.util.Vector;

class ErrorMarker implements NodeMatchListener, SymbolizationConstants {
   boolean quantifierUnrestricted = false;

   @Override
   public void nodeMatched(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
   }

   @Override
   public void nodeMismatched(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
      SymbolizationConnectivePanel symbolizationconnectivepanel = symbolizationnode.getConnectivePanel();
      if (symbolizationconnectivepanel != null) {
         symbolizationconnectivepanel.showError(symbolizationnode, symbolizationnode1, vector, vector1);
      }

      SymbolizationNode symbolizationnode2 = symbolizationnode.getParentNode();
      SymbolizationNode symbolizationnode3 = symbolizationnode1.getParentNode();
      if (symbolizationnode2 != null
         && symbolizationnode3 != null
         && symbolizationnode2.connective == symbolizationnode3.connective
         && symbolizationnode1.connective != symbolizationnode.connective
         && (
            symbolizationnode3.connective == 6 && symbolizationnode1.connective == 2
               || symbolizationnode3.connective == 7 && symbolizationnode1.connective == 3
         )) {
         this.quantifierUnrestricted = true;
      }
   }
}
