package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Hashtable;
import java.util.Vector;

class DerivationBox extends C_v_A implements DerivationNode, DerivationConstants {
   LPDerivation f915;
   DerivationBox f916;
   DerivationLine f917;
   DerivationLine f918;
   Rectangle f919;
   int f920;
   Vector f921;
   boolean f922;
   int f923;
   private static final boolean f924 = false;

   DerivationBox(DerivationBox derivationbox1) {
      this.f916 = derivationbox1;
      this.f915 = derivationbox1.f915;
      this.m2125(this.f917 = new DerivationLine(this, true));
      this.f918 = null;
      this.f920 = 0;
      this.f923 = -1;
      this.m1559();
      this.f919 = null;
      this.setLayout(new C_DF(this.f915.indent, 0));
      this.m2123(true);
   }

   DerivationBox(LPDerivation lpderivation) {
      this.f916 = null;
      this.f915 = lpderivation;
      this.m2125(this.f917 = new DerivationLine(this, true));
      this.f918 = null;
      this.f920 = 0;
      this.f923 = -1;
      this.m1559();
      this.f919 = null;
      this.setLayout(new C_DF(lpderivation.indent, 0));
      this.m2123(true);
   }

   DerivationBox(DerivationLine derivationline) {
      this.f916 = derivationline.f317;
      this.f915 = this.f916.f915;
      this.m2125(this.f917 = derivationline);
      this.f918 = null;
      this.f920 = 0;
      this.f923 = -1;
      this.m1559();
      this.f919 = null;
      this.setLayout(new C_DF(this.f915.indent, 0));
      this.m2123(true);
   }

   @Override
   public void m1548(Component object) {
      if (object != null) {
         object = new C_g_B(this);
      }

      super.m1548((Component)object);
   }

   @Override
   public DerivationBox m17() {
      return this.f916;
   }

   @Override
   public void paintBorder(Graphics graphics) {
      super.paintBorder(graphics);
      this.m1549(graphics);
   }

   void m1549(Graphics graphics) {
      Rectangle rectangle = LogicProgram.m1035(this, this.f915.problem);
      this.m1552(graphics, rectangle);
   }

   @Override
   public void setFont(Font font) {
      super.setFont(font);
      int i = this.m1555();

      for (int j = 0; j < i; j++) {
         this.m1560(j).setFont(font);
      }

      this.setLayout(new C_DF(this.f915.indent, 0));
   }

   @Override
   public void m35(Color[] acolor) {
      int i = this.m1555();

      for (int j = 0; j < i; j++) {
         this.m1560(j).m35(acolor);
      }
   }

   @Override
   public void m34() {
      int i = this.m1555();

      for (int j = 0; j < i; j++) {
         this.m1560(j).m34();
      }
   }

   void m1550() {
      this.m2123(true);
      int i = this.m1555();

      for (int j = 0; j < i; j++) {
         DerivationNode derivationnode = this.m1560(j);
         if (derivationnode instanceof DerivationBox) {
            ((DerivationBox)derivationnode).m1550();
         }
      }
   }

   void m1551(boolean flag) {
      this.f917.f322.setForeground(flag ? this.f915.colors[1] : this.f915.colors[0]);
      this.f917.f322.setBackground(flag ? this.f915.colors[0] : this.f915.colors[2]);
   }

   void m1552(Graphics graphics, Rectangle rectangle) {
      if (this.f919 != null) {
         byte b0 = 2;
         byte b1 = 1;
         int i = this.f919.x - rectangle.x - b1;
         int j = this.f919.y - rectangle.y - b1;
         int k = this.f919.width + 1 * b1;
         int l = this.f919.height + 2 * b1;

         for (int i1 = 0; i1 < b0; i1++) {
            graphics.drawLine(i, j, i + k - 1, j);
            graphics.drawLine(i, j, i, j + l);
            graphics.drawLine(i, j + l, i + k - 1, j + l);
            i--;
            j--;
            k++;
            l += 2;
         }
      }
   }

   @Override
   int m1553(int i) {
      int j = 0;
      int k = this.getComponentCount();

      while (true) {
         while (j >= k || !(this.getComponent(j) instanceof C_ZD)) {
            if (j >= k) {
               return k;
            }

            if (i == 0) {
               return j;
            }

            i--;
            j++;
         }

         j++;
      }
   }

   @Override
   int m1554(int i) {
      int j = 0;
      int k = this.getComponentCount();
      if (k > i) {
         k = i;
      }

      for (int l = 0; l < k; l++) {
         if (!(this.getComponent(l) instanceof C_ZD)) {
            j++;
         }
      }

      return j;
   }

   @Override
   int m1555() {
      int i = this.getComponentCount();
      int j = i;

      while (--j >= 0) {
         if (this.getComponent(j) instanceof C_ZD) {
            i--;
         }
      }

      return i;
   }

   @Override
   public int m30() {
      return this.f917.m30();
   }

   @Override
   public DerivationNode m32(int i) {
      if (i <= 0) {
         return null;
      } else {
         int j = 1;
         int l = this.m1555() - 1;
         if (l < j) {
            if (LogicProgram.debug) {
               System.out.println(LPDerivation.trimTitle(this.f915.problemTitle) + ": bad line number (" + i + ")");
            }

            return null;
         } else {
            DerivationNode derivationnode;
            int i1;
            if ((i1 = (derivationnode = this.m1560(j)).m30()) == i) {
               return derivationnode;
            } else if (i < i1) {
               return null;
            } else {
               DerivationNode derivationnode1 = derivationnode;
               if ((i1 = (derivationnode = this.m1560(l)).m30()) == i) {
                  return derivationnode;
               } else if (i > i1) {
                  return derivationnode.m32(i);
               } else {
                  while (l - j > 1) {
                     int k;
                     if ((i1 = (derivationnode = this.m1560(k = (j + l) / 2)).m30()) == i) {
                        return derivationnode;
                     }

                     if (i < i1) {
                        l = k;
                     } else {
                        j = k;
                        derivationnode1 = derivationnode;
                     }
                  }

                  return derivationnode1.m32(i);
               }
            }
         }
      }
   }

   DerivationLine m1556(int i) {
      DerivationLine derivationline = new DerivationLine(this, false);
      this.add(derivationline, i);
      derivationline.m34();
      this.f915.setWidths(false);
      this.f915.problem.m1558();
      return derivationline;
   }

   DerivationBox m1557(int i) {
      DerivationBox derivationbox1 = new DerivationBox(this);
      this.add(derivationbox1, i);
      derivationbox1.m34();
      this.f915.setWidths(false);
      this.f915.problem.m1558();
      return derivationbox1;
   }

   public void m1558() {
      this.m33(0);
      this.m45();
   }

   @Override
   public void m45() {
      int i = this.m1555();

      for (int j = 0; j < i; j++) {
         this.m1560(j).m45();
      }
   }

   @Override
   public int m33(int i) {
      int j = this.m1555();

      for (int k = 0; k < j; k++) {
         i = this.m1560(k).m33(i);
      }

      return i;
   }

   @Override
   public int m36(boolean flag) {
      if (flag && !this.m2124()) {
         return 0;
      } else {
         int i = this.m1555();
         int j = 0;

         for (int k = 1; k < i; k++) {
            int l = this.m1560(k).m36(flag) + 1;
            if (l > j) {
               j = l;
            }
         }

         return j;
      }
   }

   void m1559() {
      this.f921 = null;
      int i = this.m1555();

      for (int j = 1; j < i; j++) {
         DerivationNode derivationnode = this.m1560(j);
         if (derivationnode instanceof DerivationBox) {
            ((DerivationBox)derivationnode).m1559();
         }
      }
   }

   @Override
   public int m37() {
      return this.f916 == null ? 0 : this.f916.m37() + 1;
   }

   @Override
   public int m38(boolean flag) {
      int i = 1;
      if (!flag || this.m2124()) {
         int j = this.m1555();

         for (int k = 1; k < j; k++) {
            i += this.m1560(k).m38(flag);
         }
      }

      return i;
   }

   @Override
   public DerivationLine m18() {
      return this.f917.m18();
   }

   @Override
   public void m19(boolean flag) {
      if (flag) {
         int i = this.m1555();

         while (--i > 0) {
            this.m1560(i).m19(true);
         }
      }

      this.f917.m19(false);
      this.f915.problem.m1558();
   }

   DerivationNode m1560(int i) {
      return (DerivationNode)this.getComponent(this.m1553(i));
   }

   @Override
   public void m6(String s) {
      this.f917.m6(s);
   }

   @Override
   public String m7(boolean flag) {
      return this.f917.m7(flag);
   }

   @Override
   public void m8(String s) {
      this.f917.m8(s);
   }

   @Override
   public String m9(boolean flag) {
      return this.f917.m9(flag);
   }

   @Override
   public void m10(String s, boolean flag) {
      this.f917.m10(s, flag);
   }

   @Override
   public void m11(String s) {
      this.f917.m11(s);
   }

   void m1561(String s, int i) {
      this.f917.m549(s, i);
   }

   @Override
   public void m12(String s, Hashtable hashtable) {
      this.f917.m12(s, hashtable);
   }

   @Override
   public void m13() {
      this.f917.m13();
   }

   void m1562(int i) {
      this.f917.m13();
   }

   @Override
   public C_l_E m14() {
      return this.f917.f323;
   }

   @Override
   public C_l_E m15() {
      return null;
   }

   int m1563(Component component) {
      int i = this.getComponentCount();
      Component[] acomponent = this.getComponents();

      for (int j = 0; j < i; j++) {
         if (acomponent[j] == component) {
            return j;
         }
      }

      return -1;
   }

   @Override
   public int m16() {
      return this.f916 == null ? -1 : this.f916.m1554(this.f916.m1563(this));
   }

   void m1564() {
      C_l_E c_l_e = this.f915.focus;
      if (c_l_e != null && !c_l_e.f1258.m28()) {
         this.m22(false);
      }
   }

   @Override
   public void m22(boolean flag) {
      this.f917.m22(false);
   }

   @Override
   public void requestFocus() {
   }

   @Override
   public DerivationNode m23(boolean flag) {
      return this.f917.m23(flag);
   }

   @Override
   public DerivationNode m24(boolean flag) {
      return this.f917.m24(flag);
   }

   @Override
   public DerivationNode m25() {
      return this;
   }

   @Override
   public boolean m26() {
      return true;
   }

   @Override
   public boolean m27() {
      return false;
   }

   @Override
   public boolean m28() {
      return this.f917.m28();
   }

   @Override
   public void m29() {
      this.f917.m29();
   }

   @Override
   public Rectangle m31() {
      return this.f917.m31();
   }

   @Override
   public void m20() {
      int i = this.m16();
      if (i > 0) {
         DerivationNode derivationnode = this.f916.m1560(i - 1);
         if (derivationnode instanceof DerivationBox) {
            C_l_E c_l_e = this.f915.focus;
            if (c_l_e != null && c_l_e.f1258 == this.f917) {
               this.f916.m1551(false);
            }

            this.f916.remove(this);
            ((DerivationBox)derivationnode).add(this, -1);
            this.f916 = (DerivationBox)derivationnode;
            if (c_l_e != null && c_l_e.f1258 == this.f917) {
               this.f916.m1551(true);
            }
         }
      }
   }

   @Override
   public void m21() {
      if (this.f916 != null) {
         int i = this.m16();
         if (i == this.f916.m1555() - 1) {
            DerivationBox derivationbox1 = this.f916.f916;
            if (derivationbox1 != null) {
               C_l_E c_l_e = this.f915.focus;
               if (c_l_e != null && c_l_e.f1258 == this.f917) {
                  this.f916.m1551(false);
               }

               this.f916.remove(this);
               derivationbox1.add(this, derivationbox1.m1553(this.f916.m16() + 1));
               this.f916 = derivationbox1;
               if (c_l_e != null && c_l_e.f1258 == this.f917) {
                  this.f916.m1551(true);
               }
            }
         }
      }
   }

   @Override
   public void m39(C_AA c_aa) {
      this.f917.m39(c_aa);
   }

   @Override
   public void m40(C_AA c_aa) {
      this.f917.m40(c_aa);
   }

   @Override
   public void m41() {
      this.f917.m41();
   }

   @Override
   public void m42() {
      this.f917.m42();
   }

   @Override
   public boolean m43() {
      boolean flag = true;
      int i = this.m1555();

      for (int j = 0; j < i; j++) {
         flag = this.m1560(j).m43() && flag;
      }

      return flag;
   }

   @Override
   public Expression m44() {
      return this.f917.m44();
   }

   @Override
   public String m47() {
      String s = "";
      int i = this.m1555();

      for (int j = 0; j < i; j++) {
         s = s + this.m1560(j).m47();
      }

      if (this.f918 == null) {
         s = s + "`=";
      }

      return s;
   }

   @Override
   public String m48() {
      String s = "";
      int i = this.m1555();

      for (int j = 0; j < i; j++) {
         s = s + this.m1560(j).m48();
      }

      return s;
   }

   @Override
   public boolean m46() {
      int i = this.f915.setPhase(4);
      if (this.f916 != null) {
         this.f916.f922 = false;
      }

      this.f922 = true;

      try {
         int j = this.m1555();
         this.m13();
         boolean flag;
         if (this.f916 != null) {
            this.f920 = 0;
            this.f923 = -1;
            flag = this.f917.m46();
            if (this.f918 == null) {
               this.m11("dererr055");
               this.f915.complete = false;
               flag = false;
            }
         } else {
            flag = this.f915.conclusion != null;
            if (!flag) {
               if (this.f915.premises.length == 0) {
                  this.m11("dererr052");
               } else {
                  this.m11("dererr053");
               }
            } else {
               boolean flag1 = false;
               int k = 1;

               while (k < j && !(flag1 = this.f915.isConclusion(this.m1560(k).m44()))) {
                  k++;
               }

               if (!flag1) {
                  this.m11("dererr054");
                  flag = false;
               }
            }
         }

         this.m1565();

         for (int l = 1; l < j; l++) {
            flag &= this.m1560(l).m46();
            if (this.f915.aborted()) {
               return false;
            }
         }

         this.m1566();
         return flag;
      } finally {
         this.f915.setPhase(i);
      }
   }

   void m1565() {
      if (this.f916 != null) {
         if (this.f915.varNames == null) {
            this.f915.varNames = new Vector();
         }

         Expression expression = this.f917.m44();
         if (expression != null) {
            expression.m1253(this.f915.varNames, null);
         }
      }
   }

   void m1566() {
      if (this.f916 != null) {
         if (this.f916.f921 == null) {
            this.f916.f921 = new Vector();
         }

         Expression expression = this.f917.m44();
         if (expression != null) {
            expression.m1253(null, this.f916.f921);
         }
      }
   }

   boolean m1567() {
      Expression expression = this.f917.m44();
      if (expression != null && expression.getSymbol().equals("@")) {
         String s = ((SimpleTerm)expression.getChild(0)).getSymbol();

         for (DerivationBox derivationbox1 = this.f916; derivationbox1 != null; derivationbox1 = derivationbox1.f916) {
            if (derivationbox1.f921 != null && derivationbox1.f921.contains(s)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
