package edu.ucla.phil.logic;

import java.awt.Event;
import javax.swing.JLabel;

class SymbolizationMenuButton extends ChoiceButton implements SymbolizationConstants {
   SymbolizationTextPanel textPanel;
   JLabel label;
   SymbolizationNodeMenu menu;
   boolean ignoreDismiss;

   SymbolizationMenuButton(SymbolizationTextPanel symbolizationtextpanel) {
      this.textPanel = symbolizationtextpanel;
      this.ignoreDismiss = false;
   }

   public void handlePopupClosed(Event event) {
      if (!this.ignoreDismiss) {
         if (event == null && !this.textPanel.popupWasOpen) {
            this.textPanel.setActive(false);
         } else {
            this.textPanel.textPane.requestFocus();
         }
      }

      this.textPanel.popupWasOpen = false;
   }

   @Override
   public boolean gotFocus(Event event, Object object) {
      super.gotFocus(event, object);
      return false;
   }

   @Override
   public boolean lostFocus(Event event, Object object) {
      super.lostFocus(event, object);
      return false;
   }
}
