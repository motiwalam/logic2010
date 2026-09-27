package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.JPanel;

class ExpressionLevelsPanel extends JPanel implements ModuleComponentMarker {
   Vector levelRows;

   ExpressionLevelsPanel(Expression expression) {
      this.setLayout(new VerticalStackLayout(1, 1));
      this.setFont(LogicProgram.getFont(12, 0));
      this.levelRows = new Vector();
      this.addExpression(expression, 0);
      this.enableEvents(8L);
   }

   void addExpression(Expression expression, int i) {
      if (i >= this.levelRows.size()) {
         JPanel jpanel;
         this.levelRows.addElement(jpanel = new JPanel());
         jpanel.setLayout(new FlowLayout());
         this.add(jpanel);
      }

      JPanel jpanel1 = (JPanel)this.levelRows.elementAt(i);
      jpanel1.add(new ExpressionButton(expression));
      int j = expression.getChildCount();

      for (int k = 0; k < j; k++) {
         this.addExpression(expression.getChild(k), i + 1);
      }
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      int i = awtevent.getID();
      if (i == 400) {
         LogicProgram.forwardKeyEvent(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
