package edu.ucla.phil.logic;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Rectangle;

class TreeNodeLayout extends FlowLayout {
   double alignX = 0.5;
   double alignY = 0.0;
   AnchorProvider anchor = null;

   TreeNodeLayout() {
      this.setHgap(9);
      this.setVgap(9);
   }

   @Override
   public void layoutContainer(Container container) {
      synchronized (container) {
         container.setSize(this.preferredLayoutSize(container));
         Rectangle rectangle = LogicProgram.getInteriorBounds(container);
         TruthValueTree truthvaluetree = container instanceof TruthValueTree ? ((TruthValueTree)container).alignmentTree : null;
         int i = container.getComponentCount();
         if (i != 0) {
            Dimension[] adimension = new Dimension[i];
            adimension[0] = container.getComponent(0).getPreferredSize();
            Rectangle rectangle1 = new Rectangle(
               rectangle.x + (int)((rectangle.width - adimension[0].width) * this.alignX), rectangle.y, adimension[0].width, adimension[0].height
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

            rectangle1.y = rectangle1.y + (int)((rectangle.height - rectangle1.height - dimension.height) * this.alignY);
            if (truthvaluetree != null) {
               rectangle1.x = truthvaluetree.label.getBounds().x + truthvaluetree.label.getAnchorX() - ((TruthValueTree)container).label.getAnchorX();
            }

            container.getComponent(0).setBounds(rectangle1);
            rectangle1.x = rectangle.x + (int)((rectangle.width - dimension.width) * this.alignX);
            rectangle1.y = rectangle1.y + rectangle1.height + this.getVgap();

            for (int k = 1; k < i; k++) {
               rectangle1.width = adimension[k].width;
               rectangle1.height = adimension[k].height;
               if (truthvaluetree != null) {
                  rectangle1.x = truthvaluetree.getComponent(k).getBounds().x;
               }

               container.getComponent(k).setBounds(rectangle1);
               rectangle1.x = rectangle1.x + rectangle1.width + this.getHgap();
            }
         }
      }
   }

   public boolean isFixedTreeNodeLayout() {
      return false;
   }

   @Override
   public Dimension minimumLayoutSize(Container container) {
      return this.preferredLayoutSize(container);
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      TruthValueTree truthvaluetree = container instanceof TruthValueTree ? ((TruthValueTree)container).alignmentTree : null;
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
         if (truthvaluetree != null) {
            dimension.width = truthvaluetree.getPreferredSize().width;
         }

         return dimension;
      }
   }

   void setAnchor(AnchorProvider anchorprovider) {
      this.anchor = anchorprovider;
   }
}
