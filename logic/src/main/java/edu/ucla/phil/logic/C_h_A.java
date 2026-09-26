package edu.ucla.phil.logic;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Rectangle;

class C_h_A extends FlowLayout {
   double f1155 = 0.5;
   double f1156 = 0.0;
   C_j_F f1157 = null;

   C_h_A() {
      this.setHgap(9);
      this.setVgap(9);
   }

   @Override
   public void layoutContainer(Container container) {
      synchronized (container) {
         container.setSize(this.preferredLayoutSize(container));
         Rectangle rectangle = LogicProgram.m1038(container);
         C_VF c_vf = container instanceof C_VF ? ((C_VF)container).f842 : null;
         int i = container.getComponentCount();
         if (i != 0) {
            Dimension[] adimension = new Dimension[i];
            adimension[0] = container.getComponent(0).getPreferredSize();
            Rectangle rectangle1 = new Rectangle(
               rectangle.x + (int)((rectangle.width - adimension[0].width) * this.f1155), rectangle.y, adimension[0].width, adimension[0].height
            );
            Dimension dimension = new Dimension(0, 0);

            for (int j = 1; j < i; j++) {
               adimension[j] = container.getComponent(j).getPreferredSize();
               dimension.width = dimension.width + adimension[j].width;
               if (dimension.height < adimension[j].height) {
                  dimension.height = adimension[j].height;
               }
            }

            if (dimension.height > 0) {
               dimension.width = dimension.width + this.getHgap() * (i - 2);
               dimension.height = dimension.height + this.getVgap();
            }

            rectangle1.y = rectangle1.y + (int)((rectangle.height - rectangle1.height - dimension.height) * this.f1156);
            if (c_vf != null) {
               rectangle1.x = c_vf.f845.getBounds().x + c_vf.f845.m386() - ((C_VF)container).f845.m386();
            }

            container.getComponent(0).setBounds(rectangle1);
            rectangle1.x = rectangle.x + (int)((rectangle.width - dimension.width) * this.f1155);
            rectangle1.y = rectangle1.y + rectangle1.height + this.getVgap();

            for (int k = 1; k < i; k++) {
               rectangle1.width = adimension[k].width;
               rectangle1.height = adimension[k].height;
               if (c_vf != null) {
                  rectangle1.x = c_vf.getComponent(k).getBounds().x;
               }

               container.getComponent(k).setBounds(rectangle1);
               rectangle1.x = rectangle1.x + rectangle1.width + this.getHgap();
            }
         }
      }
   }

   public boolean m1837() {
      return false;
   }

   @Override
   public Dimension minimumLayoutSize(Container container) {
      return this.preferredLayoutSize(container);
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      C_VF c_vf = container instanceof C_VF ? ((C_VF)container).f842 : null;
      int i = container.getComponentCount();
      Insets insets = container.getInsets();
      if (i == 0) {
         return new Dimension(insets.left + insets.right, insets.top + insets.bottom);
      } else {
         Dimension dimension = container.getComponent(0).getPreferredSize();
         Dimension dimension1 = new Dimension(0, 0);

         for (int j = 1; j < i; j++) {
            Dimension dimension2 = container.getComponent(j).getPreferredSize();
            dimension1.width = dimension1.width + dimension2.width;
            if (dimension1.height < dimension2.height) {
               dimension1.height = dimension2.height;
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
         if (c_vf != null) {
            dimension.width = c_vf.getPreferredSize().width;
         }

         return dimension;
      }
   }

   void m1838(C_j_F c_j_f) {
      this.f1157 = c_j_f;
   }
}
