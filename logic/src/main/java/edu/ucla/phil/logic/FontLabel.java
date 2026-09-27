package edu.ucla.phil.logic;

import javax.swing.JLabel;

class FontLabel extends JLabel {
   FontLabel(String s) {
      super(s);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
   }

   FontLabel(String s, int i) {
      super(s);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize, i));
   }
}
