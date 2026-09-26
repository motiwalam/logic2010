package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import javax.swing.AbstractButton;
import javax.swing.border.BevelBorder;

class C_BB extends BevelBorder {
   C_BB(int i) {
      super(i);
   }

   C_BB(int i, Color color, Color color1) {
      super(i, color, color1);
   }

   @Override
   public Insets getBorderInsets(Component component) {
      return this.getBorderInsets(component, new Insets(0, 0, 0, 0));
   }

   @Override
   public Insets getBorderInsets(Component component, Insets insets) {
      Insets insets1 = null;
      if (component instanceof AbstractButton) {
         insets1 = ((AbstractButton)component).getMargin();
      }

      insets.top = (insets1 == null ? 0 : insets1.top) + 2;
      insets.left = (insets1 == null ? 0 : insets1.left) + 2;
      insets.bottom = (insets1 == null ? 0 : insets1.bottom) + 2;
      insets.right = (insets1 == null ? 0 : insets1.right) + 2;
      return insets;
   }
}
