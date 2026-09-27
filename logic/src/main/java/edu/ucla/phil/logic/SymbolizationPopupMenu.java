package edu.ucla.phil.logic;

import java.awt.Component;

class SymbolizationPopupMenu extends SymbolizationNodeMenu implements SymbolizationConstants {
   SymbolizationTextPanel textPanel;

   SymbolizationPopupMenu(SymbolizationTextPanel symbolizationtextpanel) {
      super(symbolizationtextpanel);
      this.textPanel = symbolizationtextpanel;
      this.setFont(symbolizationtextpanel.getFont());
   }

   void showAtPanel() {
      this.showAt(0, 0, this.textPanel);
   }

   void showAt(int i, int j, Component component) {
      this.show(component, i, j);
   }
}
