package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Rectangle;

class C_t_C extends FlowLayout {
   double f1375 = 0.5;
   double f1376 = 0.0;

   C_t_C(int i) {
      this.setVgap(i);
   }

   @Override
   public void layoutContainer(Container container) {
      Rectangle rectangle = LogicProgram.m1038(container);
      int i = container.getComponentCount();
      if (i >= 2) {
         Component component = container.getComponent(0);
         Component component1 = container.getComponent(1);
         Dimension dimension = component.getPreferredSize();
         Dimension dimension1 = component1.getPreferredSize();
         int j = container instanceof C_BC ? ((C_BC)container).m385() : dimension.width / 2;
         int k = dimension1.width / 2;
         if (j > k) {
            k = j - k;
            j = 0;
         } else {
            j = k - j;
            k = 0;
         }

         int l = rectangle.x + (int)((rectangle.width - Math.max(j + dimension.width, k + dimension1.width)) * this.f1375);
         int i1 = rectangle.y + (int)((rectangle.height - dimension.height - this.getVgap() - dimension1.height) * this.f1376);
         component.setBounds(j + l, i1, dimension.width, dimension.height);
         component1.setBounds(k + l, i1 + dimension.height + this.getVgap(), dimension1.width, dimension1.height);
      }
   }

   public boolean m2086() {
      return false;
   }

   @Override
   public Dimension minimumLayoutSize(Container container) {
      return this.preferredLayoutSize(container);
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      int i = container.getComponentCount();
      Insets insets = container.getInsets();
      if (i < 2) {
         return new Dimension(insets.left + insets.right, insets.top + insets.bottom);
      } else {
         Dimension dimension = container.getComponent(0).getPreferredSize();
         int j = container instanceof C_BC ? ((C_BC)container).m385() : dimension.width / 2;
         Dimension dimension1 = container.getComponent(1).getPreferredSize();
         int k = j - dimension1.width / 2;
         int l = k + dimension1.width;
         dimension.width = Math.max(dimension.width, l) - Math.min(0, k);
         dimension.height = dimension.height + this.getVgap() + dimension1.height;
         dimension.width = dimension.width + insets.left + insets.right;
         dimension.height = dimension.height + insets.top + insets.bottom;
         return dimension;
      }
   }
}
