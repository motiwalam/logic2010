package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.Box;

class C_d_ extends SizedPanel implements C_F {
   C_b_A f1033;
   C_ZE f1034;
   C_k_E f1035;

   C_d_(C_k_E c_k_e) {
      this.f1035 = c_k_e;
      CellPanel cellpanel = new CellPanel();
      cellpanel.setLayout(new BorderLayout());
      this.add(cellpanel, "West");
      cellpanel.add(this.f1034 = new C_ZE(""), "West");
      cellpanel.add(Box.createRigidArea(new Dimension(10, 2)));
      cellpanel.add(this.f1033 = new C_b_A(new String[]{"yes", "no"}, "?"), "East");
      this.f1033.setBackground(c_k_e.f1200.colors[1]);
      this.f1033.setForeground(c_k_e.f1200.colors[0]);
      this.f1033.setBorder(new C_BB(0, Color.gray, Color.black));
      this.f1033.setMargin(new Insets(2, 2, 2, 2));
      this.f1033.m1659(this);
   }

   void m1673(String s) {
      this.f1034.setText(s);
      this.validate();
   }

   @Override
   public void m514(C_b_A c_b_a, int j, int i) {
      if (c_b_a == this.f1033) {
         this.f1035.f1215 = i;
      }
   }
}
