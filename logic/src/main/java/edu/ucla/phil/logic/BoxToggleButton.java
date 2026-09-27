package edu.ucla.phil.logic;

import java.awt.event.ActionEvent;

class BoxToggleButton extends ExpandToggleButton {
   BoxToggleButton(CollapsibleNode collapsiblenode) {
      super(collapsiblenode);
   }

   @Override
   protected void fireActionPerformed(ActionEvent actionevent) {
      super.fireActionPerformed(actionevent);
      DerivationBox derivationbox = (DerivationBox)this.getTarget();
      derivationbox.ensureFocusVisible();
      derivationbox.module.setWidths(false);
   }
}
