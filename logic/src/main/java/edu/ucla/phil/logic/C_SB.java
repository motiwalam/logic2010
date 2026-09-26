package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JButton;

class C_SB extends JButton implements LogicConstants, ActionListener {
   String f756;
   DerivationLine f757;
   Rectangle f758;
   C_y_ f759;

   C_SB(DerivationLine derivationline) {
      super("?");
      this.f757 = derivationline;
      this.m1283();
      Color[] acolor = derivationline.f317.f915.colors;
      this.setMargin(new Insets(0, 0, 0, 0));
      super.setForeground(acolor[6]);
      super.setBackground(acolor[5]);
      this.addActionListener(this);
      this.setFont(LogicProgram.getFont(derivationline.f317.f915.fontSize - 4));
   }

   @Override
   public void setBackground(Color color) {
   }

   @Override
   public void setForeground(Color color) {
   }

   void m1281(Hashtable hashtable, DerivationLineChecker derivationlinechecker) {
      if (derivationlinechecker == null) {
         this.f756 = C_n_.m1961(this.f757.f337.text, hashtable, this.f757);
      } else {
         this.f756 = C_n_.m1960(this.f757.f337.text, hashtable, derivationlinechecker);
      }

      short short1 = 300;
      short short2 = 200;
      Point point = MessageDialog.m1321(new Dimension(short1, short2));
      Rectangle rectangle = LogicProgram.m1035(this.f757.f325, null);
      Point point1 = new Point(rectangle.x, rectangle.y);
      int i = point1.x - short1 - 10;
      int j = point.y;
      new Rectangle(i, j, short1, short2);
      this.f759 = new C_y_(this.f757, this.f757.f337.buttons);
   }

   void m1282(String s, Object object) {
      if (this.f759 != null) {
         this.f759.m447(s, object);
      }
   }

   void m1283() {
      this.f756 = "";
      this.f758 = null;
      this.f759 = null;
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      String s = this.f757.f337.id;
      this.f757.m22(true);
      MessageDialog.m1332(s, this.f756, this.f758, this.f759);
   }

   @Override
   public Dimension getPreferredSize() {
      Dimension dimension = super.getPreferredSize();
      dimension.width = 100;
      return dimension;
   }
}
