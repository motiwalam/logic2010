package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

class C_u_A extends FlowLayout {
   int f1388;
   int[] f1389;
   Component f1390;

   C_u_A(int i) {
      this(null, i, null);
   }

   C_u_A(int i, int[] aint) {
      this(null, i, aint);
   }

   C_u_A(Component component, int i) {
      this(component, i, null);
   }

   C_u_A(Component component, int i, int[] aint) {
      this.f1390 = component;
      this.f1388 = i;
      this.f1389 = new int[i];
      if (aint != null) {
         this.m2098(aint);
      }
   }

   int m2096(int i) {
      return this.f1389[i];
   }

   void m2097(int i, int j) {
      this.f1389[i] = j;
   }

   void m2098(int[] aint) {
      for (int i = 0; i < this.f1388; i++) {
         this.f1389[i] = aint[i];
      }
   }

   int m2099(Container container) {
      int i = 0;

      for (int j = 0; j < this.f1388; j++) {
         i += this.f1389[j];
      }

      if (this.f1390 != null) {
         i += LogicProgram.m1035(this.f1390, container).x;
      }

      return i;
   }

   @Override
   public void layoutContainer(Container container) {
      int i = 0;
      byte b0 = 0;
      int j = container.getComponentCount();

      for (int k = 0; k < j; k++) {
         Component component = container.getComponent(k);
         int l = this.f1389[k];
         if (k == 0 && this.f1390 != null) {
            l += LogicProgram.m1035(this.f1390, container).x;
         }

         component.setSize(new Dimension(l, 10));
         component.doLayout();
      }

      C_u_A.C__A c_u_a$c__a = new C_u_A.C__A(container);

      for (int j1 = 0; j1 < j; j1++) {
         Component component1 = container.getComponent(j1);
         int i1 = this.f1389[j1];
         if (j1 == 0 && this.f1390 != null) {
            i1 += LogicProgram.m1035(this.f1390, container).x;
         }

         component1.setBounds(i, b0, i1, c_u_a$c__a.f1391);
         i += i1;
      }
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      C_u_A.C__A c_u_a$c__a = new C_u_A.C__A(container);
      return new Dimension(this.m2099(container), c_u_a$c__a.f1391);
   }

   class C__A {
      int f1391 = 0;
      int f1392;
      Dimension[] f1393;

      C__A(Container container) {
         this.f1392 = container.getComponentCount();
         if (C_u_A.this.f1388 < this.f1392) {
            this.f1392 = C_u_A.this.f1388;
         }

         this.f1393 = new Dimension[this.f1392];

         for (int i = 0; i < this.f1392; i++) {
            Component component = container.getComponent(i);
            this.f1393[i] = component.getPreferredSize();
            if (!(component instanceof C_SB) && this.f1391 < this.f1393[i].height) {
               this.f1391 = this.f1393[i].height;
            }
         }
      }
   }
}
