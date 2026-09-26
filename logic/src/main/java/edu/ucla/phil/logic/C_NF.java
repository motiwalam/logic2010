package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.DefaultButtonModel;
import javax.swing.JRadioButton;

class C_NF extends JRadioButton implements C_LC {
   C_NF(String s) {
      super(s, new C_n_B());
      this.setBackground(null);
      this.setForeground(null);
      this.setSize(LogicProgram.f539 - 1, LogicProgram.f539 - 1);
      this.setFont(LogicProgram.m1029(LogicProgram.f539));
   }

   C_NF(Component component) {
      this.setBackground(null);
      this.setForeground(null);
      this.setSize(LogicProgram.f539 - 1, LogicProgram.f539 - 1);
      this.setFont(LogicProgram.m1029(LogicProgram.f539));
      this.add(component);
   }

   @Override
   public void setSelected(boolean flag) {
      DefaultButtonModel defaultbuttonmodel = flag ? null : (DefaultButtonModel)this.model;
      ButtonGroup buttongroup = defaultbuttonmodel == null ? null : defaultbuttonmodel.getGroup();
      if (buttongroup != null) {
         defaultbuttonmodel.setGroup(null);
      }

      super.setSelected(flag);
      if (buttongroup != null) {
         defaultbuttonmodel.setGroup(buttongroup);
      }
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
