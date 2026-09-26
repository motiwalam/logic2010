package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;

class C_g_ extends C_DC {
   int f1129;
   int vgap;

   C_g_(int i, int j) {
      super(i, j);
      this.f1129 = i;
      this.vgap = j;
   }

   public void m1818(Container container, Rectangle rectangle2) {
      super.layoutContainer(container);
      int i = container.getComponentCount();

      for (int j = 0; j < i; j++) {
         Component component = container.getComponent(j);
         if (component instanceof C_ZD) {
            C_JE c_je = (C_JE)((C_ZD)component).m1546();
            Rectangle rectangle = component.getBounds();
            Rectangle rectangle1 = LogicProgram.m1035(c_je.f436.f1371, container);
            rectangle.y = rectangle1.y + (rectangle1.height - rectangle.height) / 2;
            component.setBounds(rectangle);
         }
      }
   }
}
