package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.DefaultButtonModel;
import javax.swing.JRadioButton;

class LogicRadioButton extends JRadioButton implements ModuleComponentMarker {
   LogicRadioButton(String s) {
      super(s, new ScaledRadioIcon());
      this.setBackground(null);
      this.setForeground(null);
      this.setSize(LogicProgram.fontSize - 1, LogicProgram.fontSize - 1);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
   }

   LogicRadioButton(Component component) {
      this.setBackground(null);
      this.setForeground(null);
      this.setSize(LogicProgram.fontSize - 1, LogicProgram.fontSize - 1);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
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
         LogicProgram.forwardKeyEvent(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
