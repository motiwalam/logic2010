package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

class C_u_ extends FlowLayout {
   int f1381;
   int[] f1382;
   Component f1383;

   C_u_(int i) {
      this(null, i, null);
   }

   C_u_(int i, int[] aint) {
      this(null, i, aint);
   }

   C_u_(Component component, int i) {
      this(component, i, null);
   }

   C_u_(Component component, int i, int[] aint) {
      this.f1383 = component;
      this.f1381 = i;
      this.f1382 = new int[i];
      if (aint != null) {
         this.m2094(aint);
      }
   }

   int m2092(int i) {
      return this.f1382[i];
   }

   void m2093(int i, int j) {
      this.f1382[i] = j;
   }

   void m2094(int[] aint) {
      for (int i = 0; i < this.f1381; i++) {
         this.f1382[i] = aint[i];
      }
   }

   int m2095(Container container) {
      int i = 0;

      for (int j = 0; j < this.f1381; j++) {
         i += this.f1382[j];
      }

      if (this.f1383 != null) {
         i += LogicProgram.m1035(this.f1383, container).x;
      }

      return i;
   }

   @Override
   public void layoutContainer(Container container) {
      int i = 0;
      byte b0 = 0;
      C_u_.C__A c_u_$c__a = new C_u_.C__A(container);

      for (int j = 0; j < c_u_$c__a.f1385; j++) {
         Component component = container.getComponent(j);
         int k = this.f1382[j];
         if (j == 0 && this.f1383 != null) {
            k += LogicProgram.m1035(this.f1383, container).x;
         }

         component.setBounds(i, b0, k, c_u_$c__a.f1384);
         i += k;
      }
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      C_u_.C__A c_u_$c__a = new C_u_.C__A(container);
      return new Dimension(this.m2095(container), c_u_$c__a.f1384);
   }

   class C__A {
      int f1384 = 0;
      int f1385;
      Dimension[] f1386;

      C__A(Container container) {
         this.f1385 = container.getComponentCount();
         if (C_u_.this.f1381 < this.f1385) {
            this.f1385 = C_u_.this.f1381;
         }

         this.f1386 = new Dimension[this.f1385];

         for (int i = 0; i < this.f1385; i++) {
            Component component = container.getComponent(i);
            this.f1386[i] = component.getPreferredSize();
            if (this.f1384 < this.f1386[i].height) {
               this.f1384 = this.f1386[i].height;
            }
         }
      }
   }
}
