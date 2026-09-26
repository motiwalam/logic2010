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
   static String[] f1452 = LogicProgram.symbols;

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
      return this.m2183() > 0 ? ((EditableTextPane)this.f1448.elementAt(0)).getHeight() : 1;
   }

   @Override
   public int getScrollableBlockIncrement(Rectangle rectangle, int i, int j) {
      return this.m2183() > 0 ? ((EditableTextPane)this.f1448.elementAt(0)).getHeight() : 1;
   }

   void m2180() {
      this.removeAll();
      this.f1448 = new Vector();
      this.f1449 = new Vector();
      this.f1451.gridy = 0;
   }

   void m2181(TaggedRecord taggedrecord) {
      if (taggedrecord == null) {
         this.m2180();
      } else {
         this.m2182(taggedrecord.valueAt(taggedrecord.indexOfTag('=')));
      }
   }

   void m2182(String s) {
      this.m2180();
      if (s != null) {
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\:");
         DelimitedTokenizer delimitedtokenizer1 = new DelimitedTokenizer("\\.");
         delimitedtokenizer.m1132(s);
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
            String s1 = delimitedtokenizer.m1135();
            if (s1 == null) {
               break;
            }

            delimitedtokenizer1.m1132(delimitedtokenizer.m1133());
            String s2 = delimitedtokenizer1.m1135();
            if (s2 == null) {
               break;
            }

            delimitedtokenizer.m1132(delimitedtokenizer1.m1133());
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
      DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\:");
      DelimitedTokenizer delimitedtokenizer1 = new DelimitedTokenizer("\\.");

      for (int j = 0; j < i; j++) {
         EditableTextPane editabletextpane = (EditableTextPane)this.f1448.elementAt(j);
         EditableTextPane editabletextpane1 = (EditableTextPane)this.f1449.elementAt(j);
         if (editabletextpane != null && editabletextpane1 != null) {
            String s1 = LogicProgram.m995(editabletextpane.getText().trim(), f1452, maggie);
            String s2 = editabletextpane1.getText().trim();
            if (s1.length() != 0 && s2.length() != 0) {
               if (s.length() != 0) {
                  s = s + ".";
               }

               s = s + delimitedtokenizer.m1137(s1) + ":" + delimitedtokenizer1.m1137(s2);
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

   int m2186(EditableTextPane editabletextpane) {
      return this.f1448.indexOf(editabletextpane);
   }

   int m2187(EditableTextPane editabletextpane) {
      return this.f1449.indexOf(editabletextpane);
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
