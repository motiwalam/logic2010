package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;

class DerivationOverlayLayout implements LayoutManager {
   LPDerivation module = null;

   DerivationOverlayLayout(LPDerivation lpderivation) {
      this.module = lpderivation;
   }

   @Override
   public void addLayoutComponent(String s, Component component) {
   }

   @Override
   public void removeLayoutComponent(Component component) {
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      return container.getSize();
   }

   @Override
   public Dimension minimumLayoutSize(Container container) {
      return container.getSize();
   }

   @Override
   public void layoutContainer(Container container) {
      int i = container.getComponentCount();
      Container container1 = null;
      int j = 0;
      int k = container.getWidth();

      for (int l = 0; l < i; l++) {
         Component component = container.getComponent(l);
         if (component instanceof LineLabel) {
            LineLabel linelabel = (LineLabel)component;
            if (!linelabel.line.areEnclosingBoxesExpanded()) {
               linelabel.setVisible(false);
            } else {
               linelabel.setSize(k, linelabel.getPreferredSize().height);
               if (container1 == null) {
                  if ((container1 = LogicProgram.commonAncestor(container, linelabel.line)) == null) {
                     continue;
                  }

                  j = LogicProgram.boundsRelativeTo(container, container1).y;
               }

               Rectangle rectangle = LogicProgram.boundsRelativeTo(linelabel.line, container1);
               linelabel.setLocation(0, rectangle.y - j);
               linelabel.setVisible(true);
            }
         }
      }
   }
}
