package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

class C_b_C extends FlowLayout {
   static final int f1016 = 0;
   static final int f1017 = 2;
   int f1018;

   C_b_C() {
      this.f1018 = 0;
   }

   C_b_C(int i, int j) {
      super(i);
      this.f1018 = j;
   }

   C_b_C(int i, int j, int k, int l) {
      super(i, k, l);
      this.f1018 = j;
   }

   @Override
   public Dimension minimumLayoutSize(Container container) {
      return this.preferredLayoutSize(container);
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      C_b_C.C__A c_b_c$c__a = new C_b_C.C__A(container);
      Insets insets = container.getInsets();
      return new Dimension(c_b_c$c__a.f1021 + insets.left + insets.right, c_b_c$c__a.f1022 + insets.top + insets.bottom);
   }

   @Override
   public void layoutContainer(Container container) {
      int i = this.getAlignment();
      int j = this.getHgap();
      int k = this.getVgap();
      C_b_C.C__A c_b_c$c__a = new C_b_C.C__A(container);
      Insets insets = container.getInsets();
      Dimension dimension = container.getSize();
      int l = 0;
      int i1 = 0;
      if (i == 0) {
         l = insets.left;
      } else if (i == 1) {
         l = (dimension.width - c_b_c$c__a.f1021) / 2;
      } else if (i == 2) {
         l = dimension.width - c_b_c$c__a.f1021 - insets.right;
      }

      if (this.f1018 == 0) {
         i1 = insets.top + k;
      } else if (this.f1018 == 1) {
         i1 = dimension.height / 2;
      } else if (this.f1018 == 2) {
         i1 = dimension.height - insets.bottom - k;
      }

      for (int j1 = 0; j1 < c_b_c$c__a.f1023; j1++) {
         Dimension dimension1 = c_b_c$c__a.f1025[j1];
         if (dimension1 != null) {
            l += j;
            l += dimension1.width;
            int k1 = 0;
            if (this.f1018 == 0) {
               k1 = i1;
            } else if (this.f1018 == 1) {
               k1 = i1 - dimension1.height / 2;
            } else if (this.f1018 == 2) {
               k1 = i1 - dimension1.height;
            }

            container.getComponent(j1).setBounds(l, k1, dimension1.width, dimension1.height);
         }
      }
   }

   class C__A {
      int f1019 = 0;
      int f1020 = 0;
      int f1021 = C_b_C.this.getHgap();
      int f1022 = 2 * C_b_C.this.getVgap();
      int f1023;
      int f1024;
      Dimension[] f1025;

      C__A(Container container) {
         this.f1023 = container.getComponentCount();
         this.f1024 = 0;
         this.f1025 = new Dimension[this.f1023];

         for (int i = 0; i < this.f1023; i++) {
            Component component = container.getComponent(i);
            if (component.isVisible()) {
               this.f1024++;
               this.f1025[i] = component.getPreferredSize();
               this.f1021 = this.f1021 + C_b_C.this.getHgap() + this.f1025[i].width;
               if (this.f1019 < this.f1025[i].width) {
                  this.f1019 = this.f1025[i].width;
               }

               if (this.f1020 < this.f1025[i].height) {
                  this.f1020 = this.f1025[i].height;
               }
            } else {
               this.f1025[i] = null;
            }
         }

         this.f1022 = this.f1022 + this.f1020;
      }
   }
}
