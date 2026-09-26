package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.JPanel;

class C_ME extends JPanel implements C_LC {
   Vector f619;

   C_ME(C_RF c_rf) {
      this.setLayout(new C_m_A(1, 1));
      this.setFont(LogicProgram.m1030(12, 0));
      this.f619 = new Vector();
      this.m1108(c_rf, 0);
      this.enableEvents(8L);
   }

   void m1108(C_RF c_rf, int i) {
      if (i >= this.f619.size()) {
         JPanel jpanel;
         this.f619.addElement(jpanel = new JPanel());
         jpanel.setLayout(new FlowLayout());
         this.add(jpanel);
      }

      JPanel jpanel1 = (JPanel)this.f619.elementAt(i);
      jpanel1.add(new C_PA(c_rf));
      int j = c_rf.m1216();

      for (int k = 0; k < j; k++) {
         this.m1108(c_rf.m1217(k), i + 1);
      }
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      int i = awtevent.getID();
      if (i == 400) {
         LogicProgram.m1086(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
