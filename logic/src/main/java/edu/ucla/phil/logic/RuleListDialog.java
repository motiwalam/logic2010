package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Frame;

class RuleListDialog extends MessageDialog {
   RuleListDialog(Frame frame, String s, Component component, String[] astring) {
      super(frame, s, component, astring);
      this.setDefaultCloseOperation(2);
   }

   @Override
   public void dispose() {
      if (LPDerivation.ruleQuery == this) {
         LPDerivation.ruleQuery = null;
      }

      super.dispose();
   }
}
