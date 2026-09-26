package edu.ucla.phil.logic;

import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

class C_ZD extends BasicArrowButton implements SwingConstants {
   private C_v_A f909;

   C_ZD(C_v_A c_v_a, boolean flag) {
      super(flag ? 5 : 3);
      this.f909 = c_v_a;
   }

   C_ZD(C_v_A c_v_a) {
      this(c_v_a, true);
   }

   boolean m1545() {
      return this.getDirection() != 3;
   }

   C_v_A m1546() {
      return this.f909;
   }

   void m1547(boolean flag) {
      if (flag != this.m1545()) {
         this.setDirection(flag ? 5 : 3);
         this.revalidate();
      }
   }

   @Override
   protected void fireActionPerformed(ActionEvent actionevent) {
      this.m1547(!this.m1545());
      super.fireActionPerformed(actionevent);
   }
}
