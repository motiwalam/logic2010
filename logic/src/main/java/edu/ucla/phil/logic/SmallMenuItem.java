package edu.ucla.phil.logic;

import javax.swing.JMenuItem;

class SmallMenuItem extends JMenuItem {
   boolean marked = false;

   SmallMenuItem(String s) {
      super(LogicProgram.expandEscapes(s));
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize * 3 / 4));
   }

   void setHoverText(String s) {
      this.setToolTipText(s);
   }
}
