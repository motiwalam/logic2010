package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

class C_l_A extends C_LB {
   C_CC f1225;
   C_CC f1226;
   C_LB f1227;
   C_e_E f1228;
   C_u_ f1229;

   C_l_A(boolean flag) {
      if (flag) {
         this.f1229 = null;
         this.setLayout(new BorderLayout());
         this.add(this.f1225 = new C_CC(true), "West");
         this.add(this.f1227 = new C_LB(), "Center");
         this.add(this.f1226 = new C_CC(true), "East");
      } else {
         this.setLayout(this.f1229 = new C_u_(3));
         this.add(this.f1225 = new C_CC(true));
         this.add(this.f1227 = new C_LB());
         this.add(this.f1226 = new C_CC(true));
      }

      this.f1228 = new C_e_E();
      StyledDocument styleddocument = this.f1228.getStyledDocument();
      SimpleAttributeSet simpleattributeset = new SimpleAttributeSet();
      StyleConstants.setAlignment(simpleattributeset, 1);
      StyleConstants.setForeground(simpleattributeset, C_n_A.bruinBlack);
      StyleConstants.setBackground(simpleattributeset, C_n_A.bruinWhite);
      StyleConstants.setFontFamily(simpleattributeset, this.f1228.getFont().getFamily());
      StyleConstants.setFontSize(simpleattributeset, this.f1228.getFont().getSize());
      StyleConstants.setBold(simpleattributeset, this.f1228.getFont().isBold());
      this.f1228.setParagraphAttributes(simpleattributeset, true);
      this.f1228.m1787(true);
      this.f1228.m1789(true);
      this.f1227.add(this.f1228);
      this.f1227.setBackground(C_n_A.bruinWhite);
      this.f1227.setForeground(C_n_A.bruinBlack);
      this.f1228.setVisible(false);
      this.setEnabled(false);
   }

   void m1916(String s) {
      if (s == null) {
         this.f1228.setText("");
         this.f1228.setVisible(false);
      } else {
         this.f1228.setText(LogicProgram.m1004(s.trim()));
         this.f1228.setVisible(true);
      }
   }

   String m1917() {
      return this.f1228.isVisible() ? this.f1228.getText() : null;
   }

   @Override
   public void setFont(Font font) {
      if (this.f1228 == null) {
         super.setFont(font);
      } else {
         this.f1228.setFont(font);
      }
   }

   void m1918(float f) {
      this.f1228.setAlignmentY(f);
   }

   void m1919(Color[] acolor) {
      this.f1227.setForeground(acolor[5]);
      this.f1227.setBackground(acolor[6]);
   }

   void m1920(int i, int j) {
      this.m1921(i, j, 0);
   }

   void m1921(int i, int j, int k) {
      if (this.f1229 == null) {
         this.f1225.m421(i);
         this.f1227.m934(j);
         this.f1226.m421(k);
      } else {
         this.f1229.m2094(new int[]{i, j, k});
      }
   }
}
