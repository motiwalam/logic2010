package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;

public class C_TA extends JPanel implements C_AF {
   Color normalForeground = null;
   Color normalBackground = null;
   boolean selected = false;

   C_TA() {
      this(true);
   }

   C_TA(boolean flag) {
      if (flag) {
         this.setBackground(null);
         this.setForeground(null);
      }
   }

   @Override
   public void prerender(boolean flag, boolean flag1) {
      if (this.selected != flag) {
         this.selected = flag;
         if (flag) {
            this.normalForeground = this.getForeground();
            this.normalBackground = this.getBackground();
         } else {
            this.setCellForeground(this.normalForeground);
            this.setCellBackground(this.normalBackground);
         }
      }
   }

   @Override
   public void setCellForeground(Color color) {
      this.setForeground(color);
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         Component component = this.getComponent(j);
         if (component instanceof C_AF) {
            ((C_AF)component).setCellForeground(color);
         } else {
            component.setForeground(color);
         }
      }
   }

   @Override
   public void setCellBackground(Color color) {
      this.setBackground(color);
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         Component component = this.getComponent(j);
         if (component instanceof C_AF) {
            ((C_AF)component).setCellBackground(color);
         } else {
            component.setBackground(color);
         }
      }
   }
}
