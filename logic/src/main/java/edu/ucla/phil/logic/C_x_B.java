package edu.ucla.phil.logic;

import java.awt.event.ItemEvent;

class C_x_B extends C_NE {
   C_XF f1435;
   int f1436;

   C_x_B(C_XF c_xf, int i) {
      this.f1435 = c_xf;
      this.f1436 = i;
      this.setBackground(c_xf.f883.f1200.colors[1]);
      this.setForeground(c_xf.f883.f1200.colors[0]);
   }

   @Override
   protected void fireItemStateChanged(ItemEvent itemevent) {
      boolean flag = itemevent.getStateChange() == 1;
      int i = this.f1435.f892;
      if (flag) {
         if (i != -1 && i != this.f1436) {
            this.f1435.f889[i].setSelected(false);
         }

         this.f1435.f892 = this.f1436;
      } else if (i != -1 && i == this.f1436) {
         this.f1435.f892 = -1;
      }

      super.fireItemStateChanged(itemevent);
   }
}
