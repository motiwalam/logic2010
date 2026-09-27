package edu.ucla.phil.logic;

import javax.swing.JCheckBox;

class ScaledCheckBox extends JCheckBox {
   ScaledCheckBox() {
      super(new ScaledCheckBoxIcon());
   }

   ScaledCheckBox(String s) {
      super(s, new ScaledCheckBoxIcon());
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
   }
}
