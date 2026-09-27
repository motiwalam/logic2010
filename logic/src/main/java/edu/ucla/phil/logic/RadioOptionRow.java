package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

class RadioOptionRow extends SizedPanel implements ModuleComponentMarker {
   LogicRadioButton radioButton;
   Component content;

   RadioOptionRow(Component component) {
      this.setLayout(new FlowLayout());
      this.add(this.radioButton = new LogicRadioButton(""));
      this.add(this.content = component);
   }

   public void setOptionSelected(boolean flag) {
      this.radioButton.setSelected(flag);
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
