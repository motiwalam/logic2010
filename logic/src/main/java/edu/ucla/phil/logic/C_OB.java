package edu.ucla.phil.logic;

import javax.swing.JMenuItem;

class C_OB extends JMenuItem {
   boolean f653 = false;

   C_OB(String s) {
      super(LogicProgram.m1004(s));
      this.setFont(LogicProgram.m1029(LogicProgram.f539 * 3 / 4));
   }

   void m1141(String s) {
      this.setToolTipText(s);
   }
}
