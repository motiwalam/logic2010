package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.Scrollable;

class C_y_B extends JPanel implements C_v_D, Scrollable, MouseListener {
   Vector f1448 = new Vector();
   Vector f1449 = new Vector();
   MouseListener mouseListener = null;
   boolean f1450 = false;
   GridBagConstraints f1451 = new GridBagConstraints();
   static String[] f1452 = LogicProgram.f596;

   C_y_B() {
      this.setLayout(new GridBagLayout());
   }

   C_y_B(boolean flag) {
      this();
      if (this.f1450 = flag) {
         this.mouseListener = this;
      }
   }

   @Override
   public boolean getScrollableTracksViewportWidth() {
      return true;
   }

   @Override
   public boolean getScrollableTracksViewportHeight() {
      return false;
   }

   @Override
   public Dimension getPreferredScrollableViewportSize() {
      return this.getPreferredSize();
   }

   @Override
   public int getScrollableUnitIncrement(Rectangle rectangle, int i, int j) {
      return this.m2183() > 0 ? ((C_p_A)this.f1448.elementAt(0)).getHeight() : 1;
   }

   @Override
   public int getScrollableBlockIncrement(Rectangle rectangle, int i, int j) {
      return this.m2183() > 0 ? ((C_p_A)this.f1448.elementAt(0)).getHeight() : 1;
   }

   void m2180() {
      this.removeAll();
      this.f1448 = new Vector();
      this.f1449 = new Vector();
      this.f1451.gridy = 0;
   }

   void m2181(C_XD c_xd) {
      if (c_xd == null) {
         this.m2180();
      } else {
         this.m2182(c_xd.m1483(c_xd.m1475('=')));
      }
   }

   void m2182(String s) {
      this.m2180();
      if (s != null) {
         C_OA c_oa = new C_OA("\\:");
         C_OA c_oa1 = new C_OA("\\.");
         c_oa.m1132(s);
         this.f1451.weighty = 0.0;
         this.f1451.gridwidth = 1;
         this.f1451.gridx = 0;
         this.f1451.fill = 0;
         this.f1451.insets = new Insets(0, 0, 0, 0);
         this.f1451.weightx = 0.0;
         this.f1451.anchor = 23;
         C_d_D c_d_d = new C_d_D("symbol");
         c_d_d.setForeground(this.getForeground());
         this.add(c_d_d, this.f1451);
         this.f1451.gridx = 1;
         this.f1451.insets = new Insets(0, 5, 0, 5);
         c_d_d = new C_d_D(":");
         c_d_d.setForeground(this.getForeground());
         this.add(c_d_d, this.f1451);
         this.f1451.gridx = 2;
         this.f1451.insets = new Insets(0, 0, 0, 0);
         this.f1451.weightx = 1.0;
         this.f1451.fill = 2;
         c_d_d = new C_d_D("English");
         c_d_d.setForeground(this.getForeground());
         this.add(c_d_d, this.f1451);
         this.f1451.gridy++;

         while (true) {
            String s1 = c_oa.m1135();
            if (s1 == null) {
               break;
            }

            c_oa1.m1132(c_oa.m1133());
            String s2 = c_oa1.m1135();
            if (s2 == null) {
               break;
            }

            c_oa.m1132(c_oa1.m1133());
            this.m2185(s1, s2);
         }

         this.revalidate();
      }
   }

   int m2183() {
      return this.f1448.size();
   }

   String m2184() {
      int i = this.m2183();
      String s = "";
      C_OA c_oa = new C_OA("\\:");
      C_OA c_oa1 = new C_OA("\\.");

      for (int j = 0; j < i; j++) {
         C_p_A c_p_a = (C_p_A)this.f1448.elementAt(j);
         C_p_A c_p_a1 = (C_p_A)this.f1449.elementAt(j);
         if (c_p_a != null && c_p_a1 != null) {
            String s1 = LogicProgram.m995(c_p_a.getText().trim(), f1452, maggie);
            String s2 = c_p_a1.getText().trim();
            if (s1.length() != 0 && s2.length() != 0) {
               if (s.length() != 0) {
                  s = s + ".";
               }

               s = s + c_oa.m1137(s1) + ":" + c_oa1.m1137(s2);
            }
         }
      }

      return s;
   }

   void m2185(String s, String s1) {
      C_JC c_jc = new C_JC(LogicProgram.m995(s.trim(), maggie, f1452), this);
      c_jc.setForeground(this.getForeground());
      c_jc.setEditable(this.f1450);
      c_jc.m2020(true);
      if (this.mouseListener != null) {
         c_jc.addMouseListener(this.mouseListener);
      }

      this.f1451.weighty = 0.0;
      this.f1451.gridwidth = 1;
      this.f1451.gridx = 0;
      this.f1451.fill = 2;
      this.f1451.insets = new Insets(0, 0, 0, 0);
      this.f1451.weightx = 0.0;
      this.f1451.anchor = 23;
      this.add(c_jc, this.f1451);
      this.f1448.addElement(c_jc);
      this.f1451.gridx = 1;
      this.f1451.insets = new Insets(0, 5, 0, 5);
      C_d_D c_d_d = new C_d_D(":");
      c_d_d.setForeground(this.getForeground());
      this.add(c_d_d, this.f1451);
      C_JC c_jc1 = new C_JC(s1.trim(), this);
      c_jc1.setForeground(this.getForeground());
      c_jc1.setEditable(this.f1450);
      c_jc1.m1787(true);
      c_jc1.m1789(true);
      if (this.mouseListener != null) {
         c_jc1.addMouseListener(this.mouseListener);
      }

      this.f1451.gridx = 2;
      this.f1451.insets = new Insets(0, 0, 0, 0);
      this.f1451.weightx = 1.0;
      this.f1451.fill = 2;
      this.add(c_jc1, this.f1451);
      this.f1449.addElement(c_jc1);
      this.f1451.gridy++;
      this.validate();
   }

   int m2186(C_p_A c_p_a) {
      return this.f1448.indexOf(c_p_a);
   }

   int m2187(C_p_A c_p_a) {
      return this.f1449.indexOf(c_p_a);
   }

   @Override
   public void mouseClicked(MouseEvent mouseevent) {
   }

   @Override
   public void mouseEntered(MouseEvent mouseevent) {
   }

   @Override
   public void mouseExited(MouseEvent mouseevent) {
   }

   @Override
   public void mousePressed(MouseEvent mouseevent) {
   }

   @Override
   public void mouseReleased(MouseEvent mouseevent) {
   }
}
