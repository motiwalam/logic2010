package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;

class C_q_B extends C_LB implements C_TE {
   boolean f1341 = false;

   C_q_B() {
      this(null, 0);
   }

   C_q_B(String s) {
      this(s, 0);
   }

   C_q_B(String s, int i) {
      this.setLayout(new C_m_A(i));
      this.m2037(s);
   }

   static C_q_B m2031(String s) {
      return new C_q_B(LogicProgram.m1004(s));
   }

   static C_q_B m2032(String s, int i) {
      return new C_q_B(LogicProgram.m1004(s), i);
   }

   @Override
   public Dimension getPreferredSize() {
      return super.getPreferredSize();
   }

   void m2033(Font font) {
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         ((C_ZE)this.getComponent(j)).setFont(font);
      }
   }

   void m2034(Color color) {
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         this.getComponent(j).setForeground(color);
      }
   }

   void m2035(Color color) {
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         this.getComponent(j).setBackground(color);
      }
   }

   void m2036(boolean flag) {
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         this.getComponent(j).setEnabled(flag);
      }
   }

   @Override
   public void setFocusable(boolean flag) {
      this.f1341 = flag;
      super.setFocusable(flag);
   }

   void m2037(String s) {
      this.removeAll();
      if (s != null) {
         int i;
         while ((i = s.indexOf(10)) != -1) {
            this.add(new C_ZE(s.substring(0, i)));
            s = s.substring(i + 1);
         }

         this.add(new C_ZE(s));
      }
   }

   @Override
   public boolean m943() {
      return this.f1341;
   }

   @Override
   public boolean keyDown(Event event, int i) {
      return false;
   }
}
