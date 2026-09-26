package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;

class C_ZF implements LayoutManager {
   LPDerivation f914 = null;

   C_ZF(LPDerivation lpderivation) {
      this.f914 = lpderivation;
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
         if (component instanceof C_t_E) {
            C_t_E c_t_e = (C_t_E)component;
            if (!c_t_e.f1378.m28()) {
               c_t_e.setVisible(false);
            } else {
               c_t_e.setSize(k, c_t_e.getPreferredSize().height);
               if (container1 == null) {
                  if ((container1 = LogicProgram.m1034(container, c_t_e.f1378)) == null) {
                     continue;
                  }

                  j = LogicProgram.m1035(container, container1).y;
               }

               Rectangle rectangle = LogicProgram.m1035(c_t_e.f1378, container1);
               c_t_e.setLocation(0, rectangle.y - j);
               c_t_e.setVisible(true);
            }
         }
      }
   }
}
