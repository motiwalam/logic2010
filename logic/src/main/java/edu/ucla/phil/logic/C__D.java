package edu.ucla.phil.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Action;
import javax.swing.JButton;

class C__D extends JButton implements ActionListener, MouseListener {
   Action f929 = null;
   Action f930 = null;

   C__D(String s) {
      this.setForeground(null);
      this.setBackground(null);
      this.addActionListener(this);
      this.addMouseListener(this);
      this.setText(LogicProgram.m1004(s));
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize, 1));
   }

   C__D(String s, Action action) {
      this(s);
      this.f929 = action;
   }

   C__D(String s, Action action, Action action1) {
      this(s, action);
      this.f930 = action1;
   }

   @Override
   public void mouseEntered(MouseEvent mouseevent) {
   }

   @Override
   public void mouseExited(MouseEvent mouseevent) {
   }

   @Override
   public void mousePressed(MouseEvent mouseevent) {
      if (this.f930 != null && (mouseevent.getModifiers() & 4) == 4) {
         ActionEvent actionevent = new ActionEvent(this, 1001, this.getActionCommand(), mouseevent.getWhen(), mouseevent.getModifiers());
         this.f930.actionPerformed(actionevent);
      }
   }

   @Override
   public void mouseReleased(MouseEvent mouseevent) {
   }

   @Override
   public void mouseClicked(MouseEvent mouseevent) {
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (this.f929 != null) {
         this.f929.actionPerformed(actionevent);
      }
   }

   public void m1583(String s) {
      if (s == null) {
         this.setToolTipText(null);
      } else {
         s = s.replace("\n", "<br>");
         this.setToolTipText("<html>" + s + "</html>");
      }
   }

   public void m1584(Action action) {
      this.f929 = action;
      this.f930 = null;
   }

   public void m1585(Action action, Action action1) {
      this.f929 = action;
      this.f930 = action1;
   }
}
