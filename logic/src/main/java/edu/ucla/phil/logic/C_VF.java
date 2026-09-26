package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.BorderFactory;

class C_VF extends CellPanel implements LogicConstants, C_LC {
   C_VF parent = null;
   C_VF f841 = null;
   C_VF f842 = null;
   C_h_A f843;
   C_DD f844 = null;
   C_BC f845;
   C_GD f846;
   boolean f847 = false;
   boolean f848 = true;
   boolean f849;

   C_VF(C_GD c_gd, boolean flag) {
      this.f849 = flag;
      this.f846 = c_gd;
      this.setLayout(this.f843 = (C_h_A)(flag ? new C_IA() : new C_h_A()));
      this.m1398(this.f845 = new C_BC(this, ""));
      this.add(this.f845);
   }

   synchronized void m1395(String s) {
      this.m1396(s, this);
      this.m1405(this);
   }

   void m1396(String s, C_VF c_vf1) {
      if (c_vf1 != null && this.f841 != null && c_vf1 != this.f841) {
         this.f841.m1396(s, c_vf1);
      }

      this.m1399(new C_DD(s), null);
      if (this.f844 == null) {
         this.m1398(this.f845 = new C_BC(this, s));
         this.add(this.f845);
      }

      this.setBorder(BorderFactory.createEtchedBorder());
   }

   synchronized void m1397(C_DD c_dd) {
      this.m1399(c_dd, this);
      this.m1405(this);
   }

   void m1398(C_j_F c_j_f) {
      this.f843.m1838(c_j_f);
   }

   void m1399(C_DD c_dd, C_VF c_vf1) {
      if (c_vf1 != null && this.f841 != null && c_vf1 != this.f841) {
         this.f841.m1399(c_dd, c_vf1);
      }

      this.m1398(null);
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         this.remove(this.getComponent(0));
      }

      this.validate();
      if (c_dd != null && c_dd.f278 != null) {
         this.f844 = c_dd;
         this.m1398(this.f845 = new C_BC(this, c_dd));
         this.add(this.f845);
         if (c_dd.f278 instanceof ConnectiveFormula) {
            i = c_dd.m457();

            for (int k = 0; k < i; k++) {
               C_VF c_vf2;
               this.m1418(c_vf2 = new C_VF(this.f846, this.f849));
               c_vf2.m1399(c_dd.m458(k), null);
            }
         }
      } else {
         this.f844 = null;
      }
   }

   synchronized String m1400(String s, boolean flag) {
      return this.m1401(s, flag, this);
   }

   String m1401(String s, boolean flag, C_VF c_vf1) {
      if (c_vf1 != null && this.f841 != null && c_vf1 != this.f841) {
         this.f841.m1401(s, flag, c_vf1);
      }

      C_k_E c_k_e = flag ? this.f846.f358 : null;
      Point point = flag ? this.f846.f359 : null;
      char c0 = '?';
      boolean flag1 = false;
      if (c_k_e != null && c_k_e.f1214 != null && point != null) {
         int i = c_k_e.f1214.m678(this.m1415());
         if (i != -1) {
            c0 = C_XF.m1523(point.y, c_k_e.f1217).charAt(i);
            flag1 = true;
         }
      }

      if (s != null && s.length() != 0) {
         if (c0 == '?') {
            c0 = s.charAt(0);
         }

         s = s.substring(1);
      }

      this.f845.f241.m1647(flag1);
      Color[] acolor = this.f846.f358.f1200.colors;
      this.f845.f241.m1644(acolor[0], acolor[flag1 ? 2 : 1]);
      this.f847 = false;
      this.f845.f241.m1649("?TF".indexOf(c0) - 1);
      int j = this.m1420();

      for (int k = 0; k < j; k++) {
         s = this.m1421(k).m1401(s, flag, null);
      }

      return s;
   }

   void m1402(C_VF c_vf1) {
      this.f841 = c_vf1;
      int i = this.m1420();

      for (int j = 0; j < i; j++) {
         this.m1421(j).m1402(c_vf1 == null ? null : c_vf1.m1421(j));
      }
   }

   void m1403(C_VF c_vf1) {
      this.f842 = c_vf1;
      int i = this.m1420();

      for (int j = 0; j < i; j++) {
         this.m1421(j).m1403(c_vf1 == null ? null : c_vf1.m1421(j));
      }
   }

   boolean m1404(C_VF c_vf1) {
      if (this.f842 != c_vf1) {
         return false;
      } else {
         if (c_vf1 != null) {
            int i = this.m1420();

            for (int j = 0; j < i; j++) {
               if (!this.m1421(j).m1404(c_vf1.m1421(j))) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   void m1405(C_VF c_vf1) {
      if (c_vf1 != null && this.f841 != null && c_vf1 != this.f841) {
         this.f841.m1405(c_vf1);
      }

      this.m1402(this.f841);
      this.m1403(this.f842);
   }

   @Override
   public synchronized void layout() {
      if (this.f842 != null) {
         this.f842.layout();
      }

      super.layout();
   }

   boolean m1406() {
      if (!this.f845.f241.m1648() && this.f845.f241.m1651() != -1) {
         return true;
      } else {
         int i = this.m1420();

         for (int j = 0; j < i; j++) {
            if (this.m1421(j).m1406()) {
               return true;
            }
         }

         return false;
      }
   }

   String m1407() {
      return this.m1408("");
   }

   String m1408(String s) {
      s = s + this.m1417();
      int i = this.m1420();

      for (int j = 0; j < i; j++) {
         s = this.m1421(j).m1408(s);
      }

      return s;
   }

   synchronized void m1409(boolean flag) {
      this.m1410(flag, this);
   }

   void m1410(boolean flag, C_VF c_vf1) {
      LPTruthAnalysis lptruthanalysis = this.f846.f358.f1200;
      if (c_vf1 != null && this.f841 != null && c_vf1 != this.f841) {
         this.f841.m1410(flag, c_vf1);
      }

      Color[] acolor = lptruthanalysis.colors;
      if (this.f847 != flag && !this.f845.f241.m1648() && !lptruthanalysis.treeErrorsDisabled) {
         if (this.f847 = flag) {
            if (lptruthanalysis.forPrint) {
               this.f845.f241.setFont(lptruthanalysis.errorFont);
            } else {
               this.f845.f241.m1644(acolor[4], acolor[3]);
            }
         } else if (lptruthanalysis.forPrint) {
            this.f845.f241.setFont(lptruthanalysis.font);
         } else {
            this.f845.f241.m1644(acolor[0], acolor[1]);
         }

         this.validate();
      }
   }

   synchronized boolean m1411(boolean flag) {
      return this.m1412(flag, this);
   }

   boolean m1412(boolean flag, C_VF c_vf1) {
      if (c_vf1 != null && this.f841 != null && c_vf1 != this.f841) {
         this.f841.m1412(flag, c_vf1);
      }

      if (this.f844 == null) {
         return false;
      } else {
         C_k_E c_k_e = flag ? this.f846.f358 : null;
         Point point = flag ? this.f846.f359 : null;
         char c0 = this.m1417();
         this.f848 = true;
         if (c_k_e != null && c_k_e.f1214 != null && point != null) {
            int i = c_k_e.f1214.m678(this.f844.f278);
            String s = C_XF.m1523(point.y, c_k_e.f1217);
            if (i != -1 && s.charAt(i) != c0) {
               this.f848 = false;
            }
         }

         if (this.f848 && c0 != '?') {
            String s1 = this.m1416();
            if (s1.equals("~")) {
               char c2 = this.m1421(0).m1417();
               this.f848 = c2 != '?' && c0 == 'T' == (c2 == 'F');
            } else if (s1.equals("->")) {
               char c3 = this.m1421(0).m1417();
               char c1 = this.m1421(1).m1417();
               this.f848 = c3 != '?' && c1 != '?' && c0 == 'T' == (c3 == 'F' | c1 == 'T');
               if (!this.f848 && !this.f846.f358.f1200.completeAllNodes) {
                  this.f848 = c0 == 'T' && c3 == 'F' | c1 == 'T';
               }
            } else if (s1.equals("<->")) {
               char c4 = this.m1421(0).m1417();
               char c7 = this.m1421(1).m1417();
               this.f848 = c4 != '?' && c7 != '?' && c0 == 'T' == (c4 == 'T' == (c7 == 'T'));
            } else if (s1.equals("&")) {
               char c5 = this.m1421(0).m1417();
               char c8 = this.m1421(1).m1417();
               this.f848 = c5 != '?' && c8 != '?' && c0 == 'F' == (c5 == 'F' | c8 == 'F');
               if (!this.f848 && !this.f846.f358.f1200.completeAllNodes) {
                  this.f848 = c0 == 'F' && c5 == 'F' | c8 == 'F';
               }
            } else if (s1.equals("|")) {
               char c6 = this.m1421(0).m1417();
               char c9 = this.m1421(1).m1417();
               this.f848 = c6 != '?' && c9 != '?' && c0 == 'T' == (c6 == 'T' | c9 == 'T');
               if (!this.f848 && !this.f846.f358.f1200.completeAllNodes) {
                  this.f848 = c0 == 'T' && c6 == 'T' | c9 == 'T';
               }
            }
         }

         this.m1410(!this.f848, null);
         int j = this.m1420();

         for (int k = 0; k < j; k++) {
            if (!this.m1421(k).m1412(flag, null)) {
               this.f848 = false;
            }
         }

         return this.f848;
      }
   }

   synchronized void m1413() {
      if (this.f841 != null) {
         this.f841.m1414(this.f845.f241.m1651(), this);
      }
   }

   void m1414(int i, C_VF c_vf1) {
      if (c_vf1 != null && this.f841 != null && c_vf1 != this.f841) {
         this.f841.m1414(i, c_vf1);
      }

      this.f845.f241.m1649(i);
   }

   Expression m1415() {
      return this.f844 == null ? null : this.f844.f278;
   }

   String m1416() {
      Expression expression = this.m1415();
      return expression == null ? null : expression.getSymbol();
   }

   char m1417() {
      return this.f845.f241.m1657().charAt(0);
   }

   C_VF m1418(C_VF c_vf1) {
      if (c_vf1 != null) {
         this.add(c_vf1.m1419());
         c_vf1.parent = this;
      }

      return c_vf1;
   }

   C_VF m1419() {
      if (this.parent != null) {
         this.parent.remove(this);
         this.parent = null;
      }

      return this;
   }

   int m1420() {
      return this.getComponentCount() - 1;
   }

   C_VF m1421(int i) {
      Component component = this.getComponent(i + 1);
      return component instanceof C_VF ? (C_VF)component : null;
   }

   C_VF m1422() {
      C_VF c_vf1 = this;

      while (c_vf1.parent != null) {
         c_vf1 = c_vf1.parent;
      }

      return c_vf1;
   }

   @Override
   protected void paintComponent(Graphics graphics) {
      super.paintComponent(graphics);
      if (!this.f849) {
         int i = this.getComponentCount();
         if (i != 0 && this.f843.f1157 != null) {
            Rectangle rectangle = this.getComponent(0).getBounds();
            graphics.setColor(this.getForeground());
            int l;
            int j1;
            int i1 = j1 = l = rectangle.x + this.f843.f1157.m386();
            int k = rectangle.y + rectangle.height;
            int j = this.f843.getVgap();

            for (int k1 = 1; k1 < i; k1++) {
               Component component = this.getComponent(k1);
               if (component instanceof C_VF) {
                  component.validate();
               }

               rectangle = component instanceof C_VF ? LogicProgram.m1035(((C_VF)component).f845, this) : component.getBounds();
               int l1 = rectangle.x + rectangle.width / 2;
               if (l1 < i1) {
                  i1 = l1;
               }

               if (l1 > j1) {
                  j1 = l1;
               }

               graphics.drawLine(l1, k + j / 2, l1, k + j);
            }

            if (i > 1) {
               graphics.drawLine(l, k, l, k + j / 2);
               graphics.drawLine(i1, k + j / 2, j1, k + j / 2);
            }
         }
      }
   }
}
