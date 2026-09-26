package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.plaf.metal.MetalCheckBoxIcon;

class C_g_F extends MetalCheckBoxIcon {
   public C_g_F() {
   }

   @Override
   protected int getControlSize() {
      return m1833();
   }

   static int m1833() {
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
