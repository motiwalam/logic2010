package edu.ucla.phil.logic;

import javax.swing.JCheckBox;

class C_NE extends JCheckBox {
   C_NE() {
      super(new C_g_F());
   }

   C_NE(String s) {
      super(s, new C_g_F());
      this.setFont(LogicProgram.m1029(LogicProgram.f539));
   }
}
