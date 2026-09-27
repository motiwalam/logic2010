package edu.ucla.phil.logic;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Vector;

class SymbolizationConnectivePanel extends SizedPanel implements SymbolizationConstants {
   int connective;
   String label;
   LogicLabel labelComponent;
   SymbolizationErrorButton errorButton;
   SymbolizationNode parent;
   Container enableTarget;
   static String[] displaySymbols = LogicProgram.symbols;

   SymbolizationConnectivePanel(SymbolizationNode symbolizationnode, int i) {
      this(symbolizationnode, i, null, null);
   }

   SymbolizationConnectivePanel(SymbolizationNode symbolizationnode, int i, String s) {
      this(symbolizationnode, i, s, null);
   }

   SymbolizationConnectivePanel(SymbolizationNode symbolizationnode, int i, String s, Container container) {
      this.parent = symbolizationnode;
      this.setLayout(new FlowLayout(1, 0, 0));
      if (i != 11) {
         LogicLabel logiclabel = new LogicLabel(LogicProgram.translateSymbols(connSymbol[this.connective = i], maggie, displaySymbols));
         if (symbolizationnode.symbolizer != null) {
            logiclabel.setForeground(symbolizationnode.symbolizer.colors[0]);
         }

         logiclabel.setFocusable(false);
         this.add(logiclabel);
      }

      this.errorButton = null;
      this.labelComponent = null;
      this.setLabel(s);
      this.enableTarget = (Container)(container == null ? this : container);
      this.enableTarget.setEnabled(false);
   }

   void setLabel(String s) {
      if (this.labelComponent != null) {
         this.remove(this.labelComponent);
         this.labelComponent = null;
         this.invalidate();
      }

      if ((this.label = s) != null) {
         this.labelComponent = new LogicLabel(LogicProgram.translateSymbols(s, maggie, displaySymbols));
         if (this.parent.symbolizer != null) {
            this.labelComponent.setForeground(this.parent.symbolizer.colors[0]);
         }

         this.labelComponent.setFocusable(false);
         this.add(this.labelComponent);
      }
   }

   void showError(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
      if (!symbolizationnode.symbolizer.errorMessagesDisabled) {
         this.add(this.errorButton = new SymbolizationErrorButton(symbolizationnode, symbolizationnode1, vector, vector1));
         this.enableTarget.setEnabled(true);
         this.revalidate();
         if (symbolizationnode1.connective == 11) {
            ExpressionPath expressionpath = new ExpressionPath();
            if (SymbolizationNode.translateExpression(symbolizationnode1.getLabel(), vector1, vector, expressionpath) == null) {
               for (int i = 0; i < expressionpath.depth; i++) {
                  SymbolizationNode symbolizationnode2 = symbolizationnode;
                  SymbolizationNode symbolizationnode3 = symbolizationnode1;
                  int j = vector.size();
                  int k = expressionpath.indexes[i];

                  while (j > k && (symbolizationnode2 = symbolizationnode2.getParentNode()) != null) {
                     symbolizationnode3 = symbolizationnode3.getParentNode();
                     if (symbolizationnode2.isBinder()) {
                        j--;
                     }
                  }

                  if (symbolizationnode2 != null) {
                     SymbolizationConnectivePanel symbolizationconnectivepanel1 = symbolizationnode2.getConnectivePanel();
                     if (symbolizationconnectivepanel1.errorButton == null) {
                        Vector vector2 = (Vector)vector.clone();
                        Vector vector3 = (Vector)vector1.clone();
                        vector2.setSize(j);
                        vector3.setSize(j);
                        symbolizationconnectivepanel1.add(
                           symbolizationconnectivepanel1.errorButton = new SymbolizationErrorButton(symbolizationnode2, symbolizationnode3, vector2, vector3)
                        );
                        symbolizationconnectivepanel1.enableTarget.setEnabled(true);
                     }
                  }
               }
            }
         }
      }

      symbolizationnode.symbolizer.errorCount++;
   }

   void clearError() {
      if (this.errorButton != null) {
         this.enableTarget.setEnabled(false);
         this.remove(this.errorButton);
         this.errorButton = null;
         this.invalidate();
      }
   }
}
