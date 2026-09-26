package edu.ucla.phil.logic;

import java.awt.Event;
import javax.swing.JLabel;

class C_NB extends C_b_A implements C_v_D {
   C_x_C f640;
   JLabel f641;
   C_MA f642;
   boolean f643;

   C_NB(C_x_C c_x_c) {
      this.f640 = c_x_c;
      this.f643 = false;
   }

   public void m1129(Event event) {
      if (!this.f643) {
         if (event == null && !this.f640.f1441) {
            this.f640.m2172(false);
         } else {
            this.f640.f1438.requestFocus();
         }
      }

      this.f640.f1441 = false;
   }

   @Override
   public boolean gotFocus(Event event, Object object) {
      super.gotFocus(event, object);
      return false;
   }

   @Override
   public boolean lostFocus(Event event, Object object) {
      super.lostFocus(event, object);
      return false;
   }
}
