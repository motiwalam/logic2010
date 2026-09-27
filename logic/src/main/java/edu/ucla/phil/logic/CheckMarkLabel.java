package edu.ucla.phil.logic;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

class CheckMarkLabel extends JLabel {
   CheckMarkLabel(boolean flag) {
      this.setOpaque(false);
      this.setText("");
      this.setBorder(new EmptyBorder(1, 3, 1, 3));
      this.setChecked(flag);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
   }

   public void setChecked(boolean flag) {
      if (flag) {
         this.setText("✓");
      } else {
         this.setText("　");
      }
   }
}
