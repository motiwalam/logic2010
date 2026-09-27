package edu.ucla.phil.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Action;
import javax.swing.JButton;

class ActionButton extends JButton implements ActionListener, MouseListener {
   Action primaryAction = null;
   Action rightClickAction = null;

   ActionButton(String s) {
      this.setForeground(null);
      this.setBackground(null);
      this.addActionListener(this);
      this.addMouseListener(this);
      this.setText(LogicProgram.expandEscapes(s));
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize, 1));
   }

   ActionButton(String s, Action action) {
      this(s);
      this.primaryAction = action;
   }

   ActionButton(String s, Action action, Action action1) {
      this(s, action);
      this.rightClickAction = action1;
   }

   @Override
   public void mouseEntered(MouseEvent mouseevent) {
   }

   @Override
   public void mouseExited(MouseEvent mouseevent) {
   }

   @Override
   public void mousePressed(MouseEvent mouseevent) {
      if (this.rightClickAction != null && (mouseevent.getModifiers() & 4) == 4) {
         ActionEvent actionevent = new ActionEvent(this, 1001, this.getActionCommand(), mouseevent.getWhen(), mouseevent.getModifiers());
         this.rightClickAction.actionPerformed(actionevent);
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
      if (this.primaryAction != null) {
         this.primaryAction.actionPerformed(actionevent);
      }
   }

   public void setHelpText(String s) {
      if (s == null) {
         this.setToolTipText(null);
      } else {
         s = s.replace("\n", "<br>");
         this.setToolTipText("<html>" + s + "</html>");
      }
   }

   public void setPrimaryAction(Action action) {
      this.primaryAction = action;
      this.rightClickAction = null;
   }

   public void setActions(Action action, Action action1) {
      this.primaryAction = action;
      this.rightClickAction = action1;
   }
}
