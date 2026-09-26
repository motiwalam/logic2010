package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

class C_ZE extends JLabel implements C_TE, C_LC, CellRenderable {
   boolean f910 = false;
   Color f911 = null;
   Color f912 = null;
   boolean f913 = false;

   C_ZE() {
      super("", 0);
      this.setBackground(null);
      this.setForeground(null);
      this.enableEvents(8L);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.setBorder(new EmptyBorder(1, 1, 0, 1));
      this.setOpaque(true);
   }

   C_ZE(String s) {
      super(s, 0);
      this.setBackground(null);
      this.setForeground(null);
      this.enableEvents(8L);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.setBorder(new EmptyBorder(1, 1, 0, 1));
      this.setOpaque(true);
   }

   C_ZE(String s, int i) {
      super(s, i);
      this.setBackground(null);
      this.setForeground(null);
      this.enableEvents(8L);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.setBorder(new EmptyBorder(1, 1, 0, 1));
      this.setOpaque(true);
   }

   C_ZE(String s, int i, int j) {
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
      this.f910 = flag;
      super.setFocusable(flag);
   }

   @Override
   public boolean m943() {
      return this.f910;
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      if (awtevent.getID() == 400) {
         LogicProgram.m1086(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }

   @Override
   public void prerender(boolean flag, boolean flag1) {
      if (this.f913 != flag) {
         this.f913 = flag;
         if (flag) {
            this.f911 = this.getForeground();
            this.f912 = this.getBackground();
         } else {
            this.setForeground(this.f911);
            this.setBackground(this.f912);
         }
      }
   }

   @Override
   public void setCellForeground(Color color) {
      this.setForeground(this.f911);
   }

   @Override
   public void setCellBackground(Color color) {
      this.setBackground(color);
   }
}
