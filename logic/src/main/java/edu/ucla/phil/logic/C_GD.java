package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;

class C_GD extends JToggleButton implements C_n_A {
   String f357;
   C_k_E f358;
   Point f359;
   C_VF f360;
   C_VF f361;
   boolean f362;

   C_GD(String s, C_k_E c_k_e, Point point) {
      super("");
      this.f358 = c_k_e;
      this.f359 = point;
      this.setFont(LogicProgram.m1029(LogicProgram.f539));
      this.f362 = false;
      this.f360 = new C_VF(this, false);
      this.f361 = new C_VF(this, true);
      this.f360.m1402(this.f361);
      this.f361.m1402(this.f360);
      this.f360.m1403(this.f361);
      this.setBorder(new BevelBorder(0));
      if (c_k_e.f1218 != null) {
         C_DD c_dd;
         if (point.x < c_k_e.f1216) {
            c_dd = new C_DD(c_k_e.f1218.f827[point.x]);
         } else {
            c_dd = new C_DD(c_k_e.f1218.f828);
         }

         this.f360.m1397(c_dd);
      }

      this.m632(s, true);
   }

   void m632(String s, boolean flag) {
      String s1 = m638(s);
      boolean flag1 = m641(s);
      int i = flag ? this.f358.f1214.m678(this.f360.m1415()) : -1;
      if (i != -1) {
         String s2 = this.m637().substring(i, i + 1);
         if (s1.equals("?")) {
            s1 = s2;
            flag1 = false;
         } else {
            flag1 = !s1.equals(s2);
         }
      }

      this.f360.m1400(m639(s), true);
      this.f360.m1411(true);
      this.setText(s1);
      this.m635(flag1);
      this.f357 = this.f360.m1407() + (flag1 ? "-" : "+") + s1;
   }

   String m633() {
      return this.f360.m1407() + (this.f360.f848 ? "+" : "-") + this.f360.f845.f241.m1657();
   }

   void m634(String s) {
      this.setText(m638(s));
      this.m635(m641(s));
      this.f357 = s;
   }

   void m635(boolean flag) {
      Color[] acolor = this.f358.f1200.colors;
      if ((this.f362 = flag) && !this.f358.f1200.tableErrorsDisabled) {
         if (this.f358.f1200.forPrint) {
            this.setFont(this.f358.f1200.errorFont);
         }

         this.setForeground(acolor[4]);
         this.setBackground(acolor[3]);
      } else {
         if (this.f358.f1200.forPrint) {
            this.setFont(this.f358.f1200.font);
         }

         this.setForeground(acolor[0]);
         this.setBackground(acolor[1]);
      }

      this.invalidate();
   }

   String m636() {
      return this.f360.m1407() + (this.f362 ? "-" : "+") + this.getText();
   }

   String m637() {
      return C_XF.m1523(this.f359.y, this.f358.f1217);
   }

   static String m638(String s) {
      int i = m640(s);
      return i == -1 ? "?" : s.substring(i + 1);
   }

   static String m639(String s) {
      int i = m640(s);
      return i == -1 ? s : s.substring(0, i);
   }

   static int m640(String s) {
      if (s == null) {
         return -1;
      } else {
         int i = s.indexOf(43);
         return i == -1 ? s.indexOf(45) : i;
      }
   }

   static boolean m641(String s) {
      return s.indexOf(45) != -1;
   }

   @Override
   protected void fireActionPerformed(ActionEvent actionevent) {
      Point point = this.f358.f1201.f891;
      this.setSelected(point == null || !point.equals(this.f359));
      super.fireActionPerformed(actionevent);
   }

   @Override
   public void setSelected(boolean flag) {
      Point point = this.f358.f1201.f891;
      if (flag) {
         if (point != null && !point.equals(this.f359)) {
            this.f358.f1201.f890[point.y][point.x].setSelected(false);
         }

         this.f358.f1201.f891 = this.f359;
         this.f358.f1206.setVisible(true);
         this.f358.f1202.m235(this.f360, this.f361);
      } else if (point != null && point.equals(this.f359)) {
         this.m642();
         this.f358.f1201.f891 = null;
         this.f358.f1202.m235(null, null);
         this.f358.f1206.setVisible(false);
      }

      super.setSelected(flag);
   }

   void m642() {
      String s = this.m633();
      if (!m641(s) && s.charAt(0) == '?') {
      }

      this.m634(s);
   }

   @Override
   public void paintBorder(Graphics graphics) {
      super.paintBorder(graphics);
      if (this.isSelected()) {
         Rectangle rectangle = this.getBounds();
         graphics.drawRect(0, 0, rectangle.width - 1, rectangle.height - 1);
      }
   }
}
