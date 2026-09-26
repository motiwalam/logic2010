package edu.ucla.phil.logic;

import javax.swing.JLabel;

class C_d_D extends JLabel {
   C_d_D(String s) {
      super(s);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
   }

   C_d_D(String s, int i) {
      super(s);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize, i));
   }
}
