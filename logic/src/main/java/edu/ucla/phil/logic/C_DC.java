package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

class C_DC extends FlowLayout {
   int f275;
   int f276;

   C_DC(int i, int j) {
      this.f275 = i;
      this.f276 = j;
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      int i = container.getComponentCount();
      int j = 0;
      int k = 0;
      boolean flag = container instanceof C_v_A && ((C_v_A)container).m2126() != null;
      int l = 0;

      for (int i1 = 0; i1 < i; i1++) {
         Component component = container.getComponent(i1);
         if (!(component instanceof C_ZD)) {
            Dimension dimension = component.getPreferredSize();
            if (dimension.height < this.f275) {
               dimension.height = this.f275;
            }

            j += dimension.height + l;
            l = this.f276;
            if (i1 == 0 && flag) {
               if (k < dimension.width) {
                  k = dimension.width;
               }

               if (!((C_v_A)container).m2124()) {
                  break;
               }
            } else if (k < dimension.width + this.f275) {
               k = dimension.width + this.f275;
            }
         }
      }

      return new Dimension(k, j);
   }

   @Override
   public Dimension minimumLayoutSize(Container container) {
      return this.preferredLayoutSize(container);
   }

   @Override
   public void layoutContainer(Container container) {
      Insets insets = container.getInsets();
      int i = container.getComponentCount();
      boolean flag = container instanceof C_v_A && ((C_v_A)container).m2126() != null;
      int j = insets.top;

      for (int k = 0; k < i; k++) {
         Component component = container.getComponent(k);
         if (!(component instanceof C_ZD)) {
            Dimension dimension = component.getPreferredSize();
            if (k == 0 && flag) {
               component.setBounds(insets.left, j, dimension.width, dimension.height);
               if (!((C_v_A)container).m2124()) {
                  break;
               }
            } else {
               Component component1 = component instanceof C_v_A ? ((C_v_A)component).m2122() : null;
               component.setBounds(insets.left + this.f275, j, dimension.width, dimension.height);
               if (component1 != null) {
                  component1.setBounds(insets.left, j, this.f275, this.f275);
               }
            }

            j += dimension.height + this.f276;
         }
      }
   }
}
