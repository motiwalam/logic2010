package edu.ucla.phil.logic;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

class C_g_C extends JButton {
   C_g_C(String s) {
      super(s);
      this.setBackground(null);
      this.setForeground(null);
      this.setFont(LogicProgram.m1030(LogicProgram.f539, 1));
      this.setBorder(new BevelBorder(0));
      this.setMaximumSize(new Dimension(2147483647, 0));
   }

   public Dimension m1820(Dimension dimension) {
      return dimension;
   }
}
