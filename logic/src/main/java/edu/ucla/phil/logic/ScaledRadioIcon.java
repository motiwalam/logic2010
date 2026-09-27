package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JRadioButton;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class ScaledRadioIcon implements Icon, UIResource, Serializable {
   int size = this.getIconWidth();

   @Override
   public void paintIcon(Component component, Graphics graphics, int i, int j) {
      JRadioButton jradiobutton = (JRadioButton)component;
      ButtonModel buttonmodel = jradiobutton.getModel();
      boolean flag = buttonmodel.isSelected();
      Color color = component.getBackground();
      Object object = LogicConstants.bruinBlack;
      ColorUIResource coloruiresource = MetalLookAndFeel.getControlShadow();
      Object object1 = LogicConstants.bruinBlack;
      ColorUIResource coloruiresource1 = MetalLookAndFeel.getControlHighlight();
      ColorUIResource coloruiresource2 = MetalLookAndFeel.getControlHighlight();
      Object object2 = LogicConstants.bruinWhite;
      if (!buttonmodel.isEnabled()) {
         object = coloruiresource;
         object1 = coloruiresource;
      } else if (buttonmodel.isPressed() && buttonmodel.isArmed()) {
         object2 = coloruiresource;
      }

      graphics.translate(i, j);
      graphics.setColor((Color)object2);
      graphics.fillOval(0, 0, this.size, this.size);
      graphics.setColor((Color)object1);
      int l = this.size / 8;

      for (int k = 0; k < l; k++) {
         graphics.drawOval(k, k, this.size - 2 * k, this.size - 2 * k);
      }

      if (flag) {
         graphics.setColor((Color)object);
         graphics.fillOval(this.size / 4, this.size / 4, this.size / 2, this.size / 2);
      }

      graphics.translate(-i, -j);
   }

   @Override
   public int getIconWidth() {
      if (LogicProgram.fontSize < 8) {
         return LogicProgram.fontSize;
      } else {
         return LogicProgram.fontSize < 12 ? 8 : LogicProgram.fontSize * 2 / 3;
      }
   }

   @Override
   public int getIconHeight() {
      return this.getIconWidth();
   }
}
