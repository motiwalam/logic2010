package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;

class C_IA extends C_h_A {
   C_IA() {
      this.setHgap(0);
      this.setVgap(0);
   }

   @Override
   public void layoutContainer(Container container) {
      synchronized (container) {
         Dimension dimension = this.preferredLayoutSize(container);
         container.setSize(dimension);
         Rectangle rectangle = LogicProgram.m1038(container);
         int i = container.getComponentCount();
         if (i != 0) {
            int j = rectangle.x + (int)((rectangle.width - dimension.width) * this.f1155);
            int k = rectangle.y + (int)((rectangle.height - dimension.height) * this.f1156);
            int l = this.getHgap();

            for (int i1 = 0; i1 < i; i1++) {
               Component component = container.getComponent(i >= 3 && i1 <= 1 ? 1 - i1 : i1);
               Dimension dimension1 = component.getPreferredSize();
               component.setBounds(j, k, dimension1.width, dimension1.height);
               j += dimension1.width + l;
            }
         }
      }
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      int i = 0;
      int j = 0;
      Insets insets = container.getInsets();
      int k = container.getComponentCount();

      for (int l = 0; l < k; l++) {
         Dimension dimension = container.getComponent(l).getPreferredSize();
         if (dimension.height > j) {
            j = dimension.height;
         }

         i += dimension.width;
      }

      if (k > 0) {
         i += this.getHgap() * (k - 1);
      }

      i += insets.left + insets.right;
      j += insets.top + insets.bottom;
      return new Dimension(i, j);
   }
}
