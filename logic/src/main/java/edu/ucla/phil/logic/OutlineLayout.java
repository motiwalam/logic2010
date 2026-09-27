package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;

class OutlineLayout extends IndentedTreeLayout {
   int indent;
   int vgap;

   OutlineLayout(int i, int j) {
      super(i, j);
      this.indent = i;
      this.vgap = j;
   }

   public void layoutWithCenteredToggles(Container container, Rectangle rectangle2) {
      super.layoutContainer(container);
      int i = container.getComponentCount();

      for (int j = 0; j < i; j++) {
         Component component = container.getComponent(j);
         if (component instanceof ExpandToggleButton) {
            OutlineNode outlinenode = (OutlineNode)((ExpandToggleButton)component).getTarget();
            Rectangle rectangle = component.getBounds();
            Rectangle rectangle1 = LogicProgram.boundsRelativeTo(outlinenode.entry.titleLabel, container);
            rectangle.y = rectangle1.y + (rectangle1.height - rectangle.height) / 2;
            component.setBounds(rectangle);
         }
      }
   }
}
