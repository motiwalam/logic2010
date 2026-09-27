package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

class LogicLabel extends JLabel implements FocusPreference, ModuleComponentMarker, CellRenderable {
   boolean focusRequested = false;
   Color savedForeground = null;
   Color savedBackground = null;
   boolean prerendered = false;

   LogicLabel() {
      super("", 0);
      this.setBackground(null);
      this.setForeground(null);
      this.enableEvents(8L);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.setBorder(new EmptyBorder(1, 1, 0, 1));
      this.setOpaque(true);
   }

   LogicLabel(String s) {
      super(s, 0);
      this.setBackground(null);
      this.setForeground(null);
      this.enableEvents(8L);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.setBorder(new EmptyBorder(1, 1, 0, 1));
      this.setOpaque(true);
   }

   LogicLabel(String s, int i) {
      super(s, i);
      this.setBackground(null);
      this.setForeground(null);
      this.enableEvents(8L);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.setBorder(new EmptyBorder(1, 1, 0, 1));
      this.setOpaque(true);
   }

   LogicLabel(String s, int i, int j) {
      super(s, i);
      this.setBackground(null);
      this.setForeground(null);
      this.setVerticalAlignment(j);
      this.enableEvents(8L);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.setBorder(new EmptyBorder(1, 1, 0, 1));
      this.setOpaque(true);
   }

   @Override
   public Dimension getPreferredSize() {
      return super.getPreferredSize();
   }

   @Override
   public void setFocusable(boolean flag) {
      this.focusRequested = flag;
      super.setFocusable(flag);
   }

   @Override
   public boolean wantsFocus() {
      return this.focusRequested;
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      if (awtevent.getID() == 400) {
         LogicProgram.forwardKeyEvent(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }

   @Override
   public void prerender(boolean flag, boolean flag1) {
      if (this.prerendered != flag) {
         this.prerendered = flag;
         if (flag) {
            this.savedForeground = this.getForeground();
            this.savedBackground = this.getBackground();
         } else {
            this.setForeground(this.savedForeground);
            this.setBackground(this.savedBackground);
         }
      }
   }

   @Override
   public void setCellForeground(Color color) {
      this.setForeground(this.savedForeground);
   }

   @Override
   public void setCellBackground(Color color) {
      this.setBackground(color);
   }
}
