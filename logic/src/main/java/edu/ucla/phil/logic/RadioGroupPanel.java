package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.KeyEvent;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

class RadioGroupPanel extends SizedPanel implements ModuleComponentMarker {
   ButtonGroup buttonGroup = new ButtonGroup();
   Border unusedBorder;

   RadioGroupPanel() {
      this.setLayout(new VerticalStackLayout());
      this.setBorder(new EmptyBorder(4, 0, 0, 0));
   }

   @Override
   public Component add(Component component) {
      this.registerButton(component);
      return super.add(component);
   }

   @Override
   public Component add(Component component, int i) {
      this.registerButton(component);
      return super.add(component, i);
   }

   @Override
   public void add(Component component, Object object) {
      this.registerButton(component);
      super.add(component, object);
   }

   @Override
   public void add(Component component, Object object, int i) {
      this.registerButton(component);
      super.add(component, object, i);
   }

   @Override
   public Component add(String s, Component component) {
      this.registerButton(component);
      return super.add(s, component);
   }

   private void registerButton(Component component) {
      if (component instanceof AbstractButton) {
         this.buttonGroup.add((AbstractButton)component);
      } else if (component instanceof RadioOptionRow) {
         this.buttonGroup.add(((RadioOptionRow)component).radioButton);
      }
   }

   @Override
   public void remove(Component component) {
      this.unregisterButton(component);
      super.remove(component);
   }

   @Override
   public void remove(int i) {
      Component component = this.getComponent(i);
      this.unregisterButton(component);
      super.remove(i);
   }

   private void unregisterButton(Component component) {
      if (component instanceof AbstractButton) {
         this.buttonGroup.remove((AbstractButton)component);
      } else if (component instanceof RadioOptionRow) {
         this.buttonGroup.remove(((RadioOptionRow)component).radioButton);
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
