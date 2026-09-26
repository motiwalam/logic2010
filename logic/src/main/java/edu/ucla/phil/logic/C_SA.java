package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;

class C_SA extends C_LB implements C_h_B, C_F {
   static String[] f744 = LogicProgram.f596;
   LPInvalidation f745;
   C_LB f746;
   C_LB f747;
   C_LB f748;
   C_LB f749;
   C_LB f750;
   C_LB f751;
   C_IF f752;
   C_b_A f753;
   C_ZE f754;
   C_ZE f755;

   C_SA(LPInvalidation lpinvalidation) {
      this.f745 = lpinvalidation;
      this.add(this.f746 = new C_LB(), "North");
      this.f746.add(this.f747 = new C_LB(), "Center");
      this.f746.add(this.f748 = new C_LB(), "South");
      this.f747.add(this.f751 = new C_LB(), "West");
      this.f751.setLayout(new FlowLayout(0, 0, 0));
      this.f751.setBorder(new EmptyBorder(2, 0, 0, 0));
      this.f751.add(this.f754 = new C_ZE("Size of the Universe: "));
      this.f751
         .add(this.f753 = new C_b_A(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"}, "click to set", -1));
      this.f753.m1659(this);
      this.f748.add(this.f755 = new C_ZE(), "West");
      this.f755.setBorder(new EmptyBorder(0, 0, 2, 0));
      this.add(this.f749 = new C_LB(), "Center");
      this.f749.add(this.f752 = new C_IF(true), "North");
      this.f752.setBackground(bruinWhite);
      this.f752.setForeground(bruinBlack);
      this.f749.add(this.f750 = new C_LB(), "Center");
      this.f750.setLayout(new C_m_A());
      this.f750.setBorder(new EmptyBorder(2, 0, 2, 0));
      this.m1279();
   }

   void m1278() {
      String s = LPInvalidation.trimTitle(this.f745.title);
      this.f745.titlePanel.m1821(s);
      this.f745.titlePanel.m1823(this.f745.unparsed == null ? "" : LogicProgram.m995(this.f745.unparsed, maggie, f744));
      this.f753.m1649(this.f745.size - 1);
      this.f745.titlePanel.m1825("");
      String s1 = "Universe: {";

      for (int i = 0; i < this.f745.size; i++) {
         s1 = s1 + (i == 0 ? "" : ", ") + i;
      }

      s1 = s1 + "}";
      this.f755.setText(s1);
      C_h_D c_h_d = this.f745.getSymbolList();
      this.f745.symbols = c_h_d.m1841();
      this.f750.removeAll();
      this.f750.invalidate();
      int j = c_h_d.f1160.size();

      for (int k = 0; k < j; k++) {
         this.f750.add(new C_s_A((C_a_C)c_h_d.f1160.elementAt(k), this));
      }

      j = c_h_d.f1161.size();

      for (int l = 0; l < j; l++) {
         this.f750.add(new C_s_A((C_g_E)c_h_d.f1161.elementAt(l), this));
      }

      this.m1279();
      this.repaint();
   }

   @Override
   public void addNotify() {
      super.addNotify();
      if (this.f752.f424 == null) {
         this.f752.f424 = this.f745.frame;
      }

      if (this.f745.forPrint) {
         this.m1280(this.m939().width);
      }
   }

   void m1279() {
      if (this.f745.frame != null) {
         int i = LogicProgram.m1038(this.f745.scroller).width - 16;
         this.m934(i);
         this.m1280(i);
      }
   }

   void m1280(int j1) {
      int j = this.f750.getComponentCount();
      int k = 0;

      for (int i = 0; i < j; i++) {
         Component component = this.f750.getComponent(i);
         if (component instanceof C_s_A) {
            int l = ((C_s_A)component).f1356.getPreferredSize().width;
            if (l > k) {
               k = l;
            }
         }
      }

      for (int i1 = 0; i1 < j; i1++) {
         Component component1 = this.f750.getComponent(i1);
         if (component1 instanceof C_s_A) {
            component1.invalidate();
         }
      }

      this.invalidate();
   }

   @Override
   public void m514(C_b_A c_b_a, int i, int j) {
      if (i != j) {
         this.f745.setSize(j + 1);
      }
   }
}
