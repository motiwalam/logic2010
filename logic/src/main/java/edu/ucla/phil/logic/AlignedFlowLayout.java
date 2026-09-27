package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

class AlignedFlowLayout extends FlowLayout {
   static final int TOP = 0;
   static final int BOTTOM = 2;
   int verticalAlignment;

   AlignedFlowLayout() {
      this.verticalAlignment = 0;
   }

   AlignedFlowLayout(int i, int j) {
      super(i);
      this.verticalAlignment = j;
   }

   AlignedFlowLayout(int i, int j, int k, int l) {
      super(i, k, l);
      this.verticalAlignment = j;
   }

   @Override
   public Dimension minimumLayoutSize(Container container) {
      return this.preferredLayoutSize(container);
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      AlignedFlowLayout.FlowRowMetrics alignedflowlayout$flowrowmetrics = new AlignedFlowLayout.FlowRowMetrics(container);
      Insets insets = container.getInsets();
      return new Dimension(
         alignedflowlayout$flowrowmetrics.totalWidth + insets.left + insets.right, alignedflowlayout$flowrowmetrics.totalHeight + insets.top + insets.bottom
      );
   }

   @Override
   public void layoutContainer(Container container) {
      int i = this.getAlignment();
      int j = this.getHgap();
      int k = this.getVgap();
      AlignedFlowLayout.FlowRowMetrics alignedflowlayout$flowrowmetrics = new AlignedFlowLayout.FlowRowMetrics(container);
      Insets insets = container.getInsets();
      Dimension dimension = container.getSize();
      int l = 0;
      int i1 = 0;
      if (i == 0) {
         l = insets.left;
      } else if (i == 1) {
         l = (dimension.width - alignedflowlayout$flowrowmetrics.totalWidth) / 2;
      } else if (i == 2) {
         l = dimension.width - alignedflowlayout$flowrowmetrics.totalWidth - insets.right;
      }

      if (this.verticalAlignment == 0) {
         i1 = insets.top + k;
      } else if (this.verticalAlignment == 1) {
         i1 = dimension.height / 2;
      } else if (this.verticalAlignment == 2) {
         i1 = dimension.height - insets.bottom - k;
      }

      for (int j1 = 0; j1 < alignedflowlayout$flowrowmetrics.componentCount; j1++) {
         Dimension dimension1 = alignedflowlayout$flowrowmetrics.sizes[j1];
         if (dimension1 != null) {
            int l1 = l + j;
            l = l1 + dimension1.width;
            int k1 = 0;
            if (this.verticalAlignment == 0) {
               k1 = i1;
            } else if (this.verticalAlignment == 1) {
               k1 = i1 - dimension1.height / 2;
            } else if (this.verticalAlignment == 2) {
               k1 = i1 - dimension1.height;
            }

            container.getComponent(j1).setBounds(l1, k1, dimension1.width, dimension1.height);
         }
      }
   }

   class FlowRowMetrics {
      int maxWidth = 0;
      int maxHeight = 0;
      int totalWidth = AlignedFlowLayout.this.getHgap();
      int totalHeight = 2 * AlignedFlowLayout.this.getVgap();
      int componentCount;
      int visibleCount;
      Dimension[] sizes;

      FlowRowMetrics(Container container) {
         this.componentCount = container.getComponentCount();
         this.visibleCount = 0;
         this.sizes = new Dimension[this.componentCount];

         for (int i = 0; i < this.componentCount; i++) {
            Component component = container.getComponent(i);
            if (component.isVisible()) {
               this.visibleCount++;
               this.sizes[i] = component.getPreferredSize();
               this.totalWidth = this.totalWidth + AlignedFlowLayout.this.getHgap() + this.sizes[i].width;
               if (this.maxWidth < this.sizes[i].width) {
                  this.maxWidth = this.sizes[i].width;
               }

               if (this.maxHeight < this.sizes[i].height) {
                  this.maxHeight = this.sizes[i].height;
               }
            } else {
               this.sizes[i] = null;
            }
         }

         this.totalHeight = this.totalHeight + this.maxHeight;
      }
   }
}
