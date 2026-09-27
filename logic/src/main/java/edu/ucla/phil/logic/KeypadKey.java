package edu.ucla.phil.logic;

import java.awt.Insets;
import java.awt.event.ActionEvent;

class KeypadKey extends ActionButton {
   KeypadGrid grid;

   KeypadKey(KeypadGrid keypadgrid, String s) {
      super(s);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.setBorder(new MarginBevelBorder(0));
      this.setMargin(new Insets(1, 1, 1, 1));
      this.setFocusable(false);
      this.grid = keypadgrid;
   }

   @Override
   protected void fireActionPerformed(ActionEvent actionevent) {
      String s = this.grid.target.translateKey(this.getText());
      if (s != null) {
         this.grid.insertText(s);
      }

      super.fireActionPerformed(actionevent);
   }
}
