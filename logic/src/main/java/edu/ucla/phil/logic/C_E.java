package edu.ucla.phil.logic;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

class C_E extends JLabel {
   C_E(boolean flag) {
      this.setOpaque(false);
      this.setText("");
      this.setBorder(new EmptyBorder(1, 3, 1, 3));
      this.m492(flag);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
   }

   public void m492(boolean flag) {
      if (flag) {
         this.setText("✓");
      } else {
         this.setText("　");
      }
   }
}
