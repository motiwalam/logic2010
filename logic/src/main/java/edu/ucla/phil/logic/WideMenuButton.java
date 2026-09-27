package edu.ucla.phil.logic;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

class WideMenuButton extends JButton {
   WideMenuButton(String s) {
      super(s);
      this.setBackground(null);
      this.setForeground(null);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize, 1));
      this.setBorder(new BevelBorder(0));
   }

   // Full width, natural height. (The original set a maximum size of (MAX, 0), which old
   // macOS Java ignored but modern BoxLayout honours, collapsing the menu buttons.)
   @Override
   public Dimension getMaximumSize() {
      return new Dimension(Integer.MAX_VALUE, this.getPreferredSize().height);
   }

   public Dimension adjustSize(Dimension dimension) {
      return dimension;
   }
}
