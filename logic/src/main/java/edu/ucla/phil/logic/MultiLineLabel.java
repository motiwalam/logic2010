package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;

class MultiLineLabel extends SizedPanel implements FocusPreference {
   boolean focusableFlag = false;

   MultiLineLabel() {
      this(null, 0);
   }

   MultiLineLabel(String s) {
      this(s, 0);
   }

   MultiLineLabel(String s, int i) {
      this.setLayout(new VerticalStackLayout(i));
      this.setLines(s);
   }

   static MultiLineLabel create(String s) {
      return new MultiLineLabel(LogicProgram.expandEscapes(s));
   }

   static MultiLineLabel create(String s, int i) {
      return new MultiLineLabel(LogicProgram.expandEscapes(s), i);
   }

   @Override
   public Dimension getPreferredSize() {
      return super.getPreferredSize();
   }

   void setLabelFont(Font font) {
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         ((LogicLabel)this.getComponent(j)).setFont(font);
      }
   }

   void setLabelForeground(Color color) {
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         this.getComponent(j).setForeground(color);
      }
   }

   void setLabelBackground(Color color) {
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         this.getComponent(j).setBackground(color);
      }
   }

   void setLabelsEnabled(boolean flag) {
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         this.getComponent(j).setEnabled(flag);
      }
   }

   @Override
   public void setFocusable(boolean flag) {
      this.focusableFlag = flag;
      super.setFocusable(flag);
   }

   void setLines(String s) {
      this.removeAll();
      if (s != null) {
         int i;
         while ((i = s.indexOf(10)) != -1) {
            this.add(new LogicLabel(s.substring(0, i)));
            s = s.substring(i + 1);
         }

         this.add(new LogicLabel(s));
      }
   }

   @Override
   public boolean wantsFocus() {
      return this.focusableFlag;
   }

   @Override
   public boolean keyDown(Event event, int i) {
      return false;
   }
}
