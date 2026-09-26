package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;

class C_m_A extends FlowLayout implements LayoutManager2 {
   static final int LEFT = 0;
   static final int f1268 = 1;
   static final int RIGHT = 2;
   static final int f1269 = 3;
   static final int f1270 = 0;
   static final int f1271 = 4;
   static final int f1272 = 8;
   static final int f1273 = 12;
   static final int f1274 = 16;
   private int f1275;

   C_m_A(int i, int j) {
      this.f1275 = i;
      this.setVgap(j);
   }

   C_m_A(int i) {
      this(i, 0);
   }

   C_m_A() {
      this(0, 0);
   }

   @Override
   public void layoutContainer(Container container) {
      Rectangle rectangle = LogicProgram.m1038(container);
      new Dimension(rectangle.width, rectangle.height);
      Dimension dimension = new Dimension(this.m1938(container, false));
      Insets insets = container.getInsets();
      int i = container.getComponentCount();
      int j = insets.top;
      switch (this.f1275 & 12) {
         case 4:
            j = insets.top + (rectangle.height - dimension.height) / 2;
            break;
         case 8:
            j = insets.top + (rectangle.height - dimension.height);
      }

      int k = rectangle.width;

      for (int l = 0; l < i; l++) {
         Component component = container.getComponent(l);
         if (component.isVisible()) {
            Dimension dimension1 = component.getPreferredSize();
            switch (this.f1275 & 3) {
               case 0:
                  component.setBounds(insets.left, j, dimension1.width, dimension1.height);
                  break;
               case 1:
                  component.setBounds(insets.left + (k - dimension1.width) / 2, j, dimension1.width, dimension1.height);
                  break;
               case 2:
                  component.setBounds(insets.left + (k - dimension1.width), j, dimension1.width, dimension1.height);
                  break;
               case 3:
                  component.setBounds(insets.left, j, k, dimension1.height);
            }

            j += dimension1.height + this.getVgap();
         }
      }
   }

   @Override
   public Dimension minimumLayoutSize(Container container) {
      return this.m1938(container, true);
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      return this.m1938(container, false);
   }

   @Override
   public Dimension maximumLayoutSize(Container container) {
      return this.m1938(container, false);
   }

   Dimension m1938(Container container, boolean flag) {
      Insets insets = container.getInsets();
      int i = container.getComponentCount();
      int j = 0;
      int k = 0;
      int l = 0;

      for (int i1 = 0; i1 < i; i1++) {
         Component component = container.getComponent(i1);
         if (component.isVisible()) {
            j++;
            Dimension dimension = flag ? component.getMinimumSize() : component.getPreferredSize();
            if (dimension.width > l) {
               l = dimension.width;
            }

            k += dimension.height;
         }
      }

      if (j > 0) {
         k += this.getVgap() * (j - 1);
      }

      k += insets.top + insets.bottom;
      l += insets.left + insets.right;
      return new Dimension(l, k);
   }

   @Override
   public void addLayoutComponent(Component component, Object object) {
   }

   @Override
   public float getLayoutAlignmentX(Container container) {
      return 0.0F;
   }

   @Override
   public float getLayoutAlignmentY(Container container) {
      return 0.0F;
   }

   @Override
   public void invalidateLayout(Container container) {
   }
}
