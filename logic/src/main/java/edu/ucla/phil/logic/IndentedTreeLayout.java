package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

class IndentedTreeLayout extends FlowLayout {
   int indent;
   int gap;

   IndentedTreeLayout(int i, int j) {
      this.indent = i;
      this.gap = j;
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      int i = container.getComponentCount();
      int j = 0;
      int k = 0;
      boolean flag = container instanceof CollapsibleNode && ((CollapsibleNode)container).getHeader() != null;
      int l = 0;

      for (int i1 = 0; i1 < i; i1++) {
         Component component = container.getComponent(i1);
         if (!(component instanceof ExpandToggleButton)) {
            Dimension dimension = component.getPreferredSize();
            if (dimension.height < this.indent) {
               dimension.height = this.indent;
            }

            j += dimension.height + l;
            l = this.gap;
            if (i1 == 0 && flag) {
               if (k < dimension.width) {
                  k = dimension.width;
               }

               if (!((CollapsibleNode)container).isExpanded()) {
                  break;
               }
            } else if (k < dimension.width + this.indent) {
               k = dimension.width + this.indent;
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
      boolean flag = container instanceof CollapsibleNode && ((CollapsibleNode)container).getHeader() != null;
      int j = insets.top;

      for (int k = 0; k < i; k++) {
         Component component = container.getComponent(k);
         if (!(component instanceof ExpandToggleButton)) {
            Dimension dimension = component.getPreferredSize();
            if (k == 0 && flag) {
               component.setBounds(insets.left, j, dimension.width, dimension.height);
               if (!((CollapsibleNode)container).isExpanded()) {
                  break;
               }
            } else {
               Component component1 = component instanceof CollapsibleNode ? ((CollapsibleNode)component).getToggle() : null;
               component.setBounds(insets.left + this.indent, j, dimension.width, dimension.height);
               if (component1 != null) {
                  component1.setBounds(insets.left, j, this.indent, this.indent);
               }
            }

            j += dimension.height + this.gap;
         }
      }
   }
}
