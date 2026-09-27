package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;

class VerticalStackLayout extends FlowLayout implements LayoutManager2 {
   static final int LEFT = 0;
   static final int H_CENTER = 1;
   static final int RIGHT = 2;
   static final int H_FILL = 3;
   static final int V_TOP = 0;
   static final int V_CENTER = 4;
   static final int V_BOTTOM = 8;
   static final int V_MASK = 12;
   static final int FLAG_16 = 16;
   private int alignment;

   VerticalStackLayout(int i, int j) {
      this.alignment = i;
      this.setVgap(j);
   }

   VerticalStackLayout(int i) {
      this(i, 0);
   }

   VerticalStackLayout() {
      this(0, 0);
   }

   @Override
   public void layoutContainer(Container container) {
      Rectangle rectangle = LogicProgram.getInteriorBounds(container);
      new Dimension(rectangle.width, rectangle.height);
      Dimension dimension = new Dimension(this.computeSize(container, false));
      Insets insets = container.getInsets();
      int i = container.getComponentCount();
      int j = insets.top;
      switch (this.alignment & 12) {
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
            switch (this.alignment & 3) {
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
      return this.computeSize(container, true);
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      return this.computeSize(container, false);
   }

   @Override
   public Dimension maximumLayoutSize(Container container) {
      return this.computeSize(container, false);
   }

   Dimension computeSize(Container container, boolean flag) {
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
