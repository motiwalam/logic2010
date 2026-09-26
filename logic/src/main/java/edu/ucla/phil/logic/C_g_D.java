package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

class C_g_D extends C_TA {
   C_p_A f1130;
   C_p_A f1131;
   C_p_A f1132;
   C_l_A f1133;

   C_g_D(Color[] acolor) {
      super(false);
      this.setLayout(new GridBagLayout());
      GridBagConstraints gridbagconstraints = new GridBagConstraints();
      gridbagconstraints.gridx = 0;
      gridbagconstraints.gridy = 0;
      gridbagconstraints.anchor = 23;
      this.add(this.f1130 = new C_p_A("Problem: ", false), gridbagconstraints);
      this.f1130.m2020(true);
      this.f1130.setFocusable(false);
      gridbagconstraints.gridx = 1;
      gridbagconstraints.anchor = 23;
      gridbagconstraints.weightx = 1.0;
      gridbagconstraints.fill = 1;
      this.add(this.f1131 = new C_p_A(""), gridbagconstraints);
      this.f1131.setEditable(false);
      this.f1131.setFont(LogicProgram.m1029(LogicProgram.f539));
      this.f1131.m1787(true);
      this.f1131.m1789(true);
      gridbagconstraints.gridx = 2;
      gridbagconstraints.fill = 0;
      gridbagconstraints.anchor = 24;
      gridbagconstraints.weightx = 0.0;
      this.add(this.f1132 = new C_p_A("", false), gridbagconstraints);
      this.f1132.m2020(true);
      this.f1132.setFocusable(false);
      gridbagconstraints.gridx = 0;
      gridbagconstraints.gridy = 1;
      gridbagconstraints.gridwidth = 3;
      gridbagconstraints.fill = 1;
      gridbagconstraints.weightx = 1.0;
      this.add(this.f1133 = new C_l_A(true), gridbagconstraints);
      int i = this.f1131.getHeight();
      this.f1130.setSize(this.f1130.getWidth(), i);
      this.f1133.m1919(acolor);
      this.m1829();
   }

   void m1821(String s) {
      if (s == null) {
         s = "Problem";
      }

      this.f1130.setText(s.trim() + ": ");
      this.validate();
   }

   String m1822() {
      return this.f1130.getName();
   }

   void m1823(String s) {
      if (s == null) {
         s = "";
      }

      this.f1131.setText(s.trim());
      this.validate();
   }

   String m1824() {
      return this.f1131.getText();
   }

   void m1825(String s) {
      if (s == null) {
         s = "";
      }

      this.f1132.setText(s.trim());
      this.validate();
   }

   String m1826() {
      return this.f1132.getText();
   }

   void m1827(String s) {
      this.f1133.m1916(s);
      this.validate();
   }

   String m1828() {
      return this.f1133.m1917();
   }

   @Override
   public void setFont(Font font) {
      super.setFont(font);
      if (this.f1130 != null) {
         this.f1130.setFont(font);
      }

      if (this.f1131 != null) {
         this.f1131.setFont(font);
      }

      if (this.f1132 != null) {
         this.f1132.setFont(font);
      }

      if (this.f1133 != null) {
         this.f1133.setFont(font);
      }

      this.validate();
   }

   void m1829() {
      this.m1821(null);
      this.m1823(null);
      this.m1825(null);
      this.m1827(null);
   }
}
