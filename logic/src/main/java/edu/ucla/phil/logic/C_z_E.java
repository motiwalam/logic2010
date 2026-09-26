package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.KeyEvent;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

class C_z_E extends SizedPanel implements C_LC {
   ButtonGroup f1494 = new ButtonGroup();
   Border f1495;

   C_z_E() {
      this.setLayout(new C_m_A());
      this.setBorder(new EmptyBorder(4, 0, 0, 0));
   }

   @Override
   public Component add(Component component) {
      this.m2232(component);
      return super.add(component);
   }

   @Override
   public Component add(Component component, int i) {
      this.m2232(component);
      return super.add(component, i);
   }

   @Override
   public void add(Component component, Object object) {
      this.m2232(component);
      super.add(component, object);
   }

   @Override
   public void add(Component component, Object object, int i) {
      this.m2232(component);
      super.add(component, object, i);
   }

   @Override
   public Component add(String s, Component component) {
      this.m2232(component);
      return super.add(s, component);
   }

   private void m2232(Component component) {
      if (component instanceof AbstractButton) {
         this.f1494.add((AbstractButton)component);
      } else if (component instanceof C_HC) {
         this.f1494.add(((C_HC)component).f382);
      }
   }

   @Override
   public void remove(Component component) {
      this.m2233(component);
      super.remove(component);
   }

   @Override
   public void remove(int i) {
      Component component = this.getComponent(i);
      this.m2233(component);
      super.remove(i);
   }

   private void m2233(Component component) {
      if (component instanceof AbstractButton) {
         this.f1494.remove((AbstractButton)component);
      } else if (component instanceof C_HC) {
         this.f1494.remove(((C_HC)component).f382);
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
