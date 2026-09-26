package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

class C_HC extends C_LB implements C_LC {
   C_NF f382;
   Component f383;

   C_HC(Component component) {
      this.setLayout(new FlowLayout());
      this.add(this.f382 = new C_NF(""));
      this.add(this.f383 = component);
   }

   public void m685(boolean flag) {
      this.f382.setSelected(flag);
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      if (awtevent.getID() == 400) {
         LogicProgram.m1086(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
