package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Rectangle;

class C_YF extends FlowLayout {
   double f905 = 0.5;
   double f906 = 0.0;
   C_j_F f907 = null;

   C_YF() {
      this.setHgap(3 * LogicProgram.fontSize / 4);
      this.setVgap(3 * LogicProgram.fontSize / 4);
   }

   @Override
   public void layoutContainer(Container container) {
      container.setSize(this.preferredLayoutSize(container));
      Rectangle rectangle = LogicProgram.m1038(container);
      int i = container.getComponentCount();
      if (i != 0) {
         Dimension[] adimension = new Dimension[i];
         adimension[0] = container.getComponent(0).getPreferredSize();
         Rectangle rectangle1 = new Rectangle(
            rectangle.x + (int)((rectangle.width - adimension[0].width) * this.f905), rectangle.y, adimension[0].width, adimension[0].height
         );
         Dimension dimension = new Dimension(0, 0);

         for (int j = 1; j < i; j++) {
            Component component = container.getComponent(j);
            if (component.isVisible()) {
               adimension[j] = component.getPreferredSize();
               dimension.width = dimension.width + adimension[j].width;
               if (dimension.height < adimension[j].height) {
                  dimension.height = adimension[j].height;
               }
            }
         }

         if (dimension.height > 0) {
            dimension.width = dimension.width + this.getHgap() * (i - 2);
            dimension.height = dimension.height + this.getVgap();
         }

         rectangle1.y = rectangle1.y + (int)((rectangle.height - rectangle1.height - dimension.height) * this.f906);
         container.getComponent(0).setBounds(rectangle1);
         rectangle1.x = rectangle.x + (int)((rectangle.width - dimension.width) * this.f905);
         rectangle1.y = rectangle1.y + rectangle1.height + this.getVgap();

         for (int k = 1; k < i; k++) {
            Component component1 = container.getComponent(k);
            if (component1.isVisible()) {
               rectangle1.width = adimension[k].width;
               rectangle1.height = adimension[k].height;
               component1.setBounds(rectangle1);
               rectangle1.x = rectangle1.x + rectangle1.width + this.getHgap();
            }
         }
      }
   }

   public boolean m1541() {
      return false;
   }

   @Override
   public Dimension minimumLayoutSize(Container container) {
      return this.preferredLayoutSize(container);
   }

   public Dimension m1542(Container container) {
      return this.preferredLayoutSize(container);
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      int i = container.getComponentCount();
      Insets insets = container.getInsets();
      if (i == 0) {
         return new Dimension(insets.left + insets.right, insets.top + insets.bottom);
      } else {
         Dimension dimension = container.getComponent(0).getPreferredSize();
         Dimension dimension1 = new Dimension(0, 0);

         for (int j = 1; j < i; j++) {
            Component component = container.getComponent(j);
            if (component.isVisible()) {
               Dimension dimension2 = component.getPreferredSize();
               dimension1.width = dimension1.width + dimension2.width;
               if (dimension1.height < dimension2.height) {
                  dimension1.height = dimension2.height;
               }
            }
         }

         if (dimension1.height > 0) {
            dimension1.width = dimension1.width + this.getHgap() * (i - 2);
            if (dimension.width < dimension1.width) {
               dimension.width = dimension1.width;
            }

            dimension.height = dimension.height + this.getVgap() + dimension1.height;
         }

         dimension.width = dimension.width + insets.left + insets.right;
         dimension.height = dimension.height + insets.top + insets.bottom;
         return dimension;
      }
   }

   void m1543(C_j_F c_j_f) {
      this.f907 = c_j_f;
   }
}
