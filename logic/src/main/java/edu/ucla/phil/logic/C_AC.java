package edu.ucla.phil.logic;

import java.awt.Frame;
import javax.swing.JComponent;

class C_AC extends MessageDialog implements C_TD {
   C_AC(Frame frame, String s, JComponent jcomponent, String[] astring) {
      super(frame, s, jcomponent, astring);
   }

   @Override
   public void m223(C_c_C c_c_c) {
      if (c_c_c.isEmpty()) {
         this.f790 = -1;
         this.m1325();
      }
   }
}
