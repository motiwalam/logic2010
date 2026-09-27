package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.plaf.metal.MetalCheckBoxIcon;

class ScaledCheckBoxIcon extends MetalCheckBoxIcon {
   public ScaledCheckBoxIcon() {
   }

   @Override
   protected int getControlSize() {
      return getScaledSize();
   }

   static int getScaledSize() {
      if (LogicProgram.fontSize < 13) {
         return LogicProgram.fontSize;
      } else {
         return LogicProgram.fontSize < 18 ? 13 : LogicProgram.fontSize * 3 / 4;
      }
   }

   @Override
   protected void drawCheck(Component component, Graphics graphics, int i, int j) {
      graphics.setColor(component.getForeground());
      super.drawCheck(component, graphics, i, j);
   }
}
