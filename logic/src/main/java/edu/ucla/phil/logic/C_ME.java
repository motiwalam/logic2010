package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.JPanel;

class C_ME extends JPanel implements C_LC {
   Vector f619;

   C_ME(Expression expression) {
      this.setLayout(new C_m_A(1, 1));
      this.setFont(LogicProgram.getFont(12, 0));
      this.f619 = new Vector();
      this.m1108(expression, 0);
      this.enableEvents(8L);
   }

   void m1108(Expression expression, int i) {
      if (i >= this.f619.size()) {
         JPanel jpanel;
         this.f619.addElement(jpanel = new JPanel());
         jpanel.setLayout(new FlowLayout());
         this.add(jpanel);
      }

      JPanel jpanel1 = (JPanel)this.f619.elementAt(i);
      jpanel1.add(new C_PA(expression));
      int j = expression.getChildCount();

      for (int k = 0; k < j; k++) {
         this.m1108(expression.getChild(k), i + 1);
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
