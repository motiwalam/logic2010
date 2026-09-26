package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.awt.Rectangle;

class C_QF extends GridLayout implements LayoutManager2 {
   int rows;
   int cols;
   int f701;
   int f702;
   int f703;
   int f704;
   boolean f705;
   boolean f706;
   boolean f707;
   Component[][] f708;
   Dimension[][] f709;
   int[] f710;
   int[] f711;
   Dimension f712;

   C_QF(int i, int j, int k, int l, boolean flag, boolean flag1, boolean flag2) {
      this.rows = j;
      this.cols = i;
      this.f703 = Math.min(Math.max(k, 0), 2);
      this.f704 = Math.min(Math.max(l, 0), 2);
      this.f705 = flag;
      this.f706 = flag1;
      this.f707 = flag2;
      this.f701 = 0;
      this.f702 = 0;
      this.f708 = new Component[j][i];
      this.f709 = new Dimension[j][i];
      this.f710 = new int[i];
      this.f711 = new int[j];
      this.f712 = new Dimension();
   }

   C_QF(int i, int j, int k, int l, boolean flag, boolean flag1) {
      this(i, j, k, l, flag, flag1, false);
   }

   C_QF(int i, int j, boolean flag, boolean flag1, boolean flag2) {
      this(i, j, 1, 1, flag, flag1, flag2);
   }

   C_QF(int i, int j, boolean flag, boolean flag1) {
      this(i, j, flag, flag1, true);
   }

   C_QF(int i, int j) {
      this(i, j, false, false);
   }

   @Override
   public void layoutContainer(Container container) {
      Rectangle rectangle = LogicProgram.m1038(container);
      this.m1194(false);
      int i = this.getHgap();
      int j = this.getVgap();
      Insets insets = container.getInsets();
      int k = insets.top + j / 2;
      Dimension dimension = new Dimension(this.f712);

      for (int l = 0; l < this.rows; l++) {
         int i1 = insets.left + i / 2;
         if (this.f706) {
            dimension.height = this.f711[l];
         }

         for (int j1 = 0; j1 < this.cols; j1++) {
            Component component = this.f708[l][j1];
            if (this.f705) {
               dimension.width = this.f710[j1];
            }

            if (component != null && component.isVisible()) {
               Dimension dimension1;
               int k1;
               int l1;
               if (this.f707) {
                  dimension1 = dimension;
                  k1 = 0;
                  l1 = 0;
               } else {
                  dimension1 = this.f709[l][j1];
                  k1 = (dimension.width - dimension1.width) * this.f703 / 2;
                  l1 = (dimension.height - dimension1.height) * this.f704 / 2;
               }

               component.setBounds(i1 + k1, k + l1, dimension1.width, dimension1.height);
            }

            i1 += dimension.width + i;
         }

         k += dimension.height + j;
      }

      Dimension dimension2 = this.m1195(container);
      if (rectangle.width >= 0 && rectangle.height >= 0) {
         int i2 = rectangle.width + insets.left + insets.right;
         int j2 = rectangle.height + insets.top + insets.bottom;
         if (dimension2.width > i2) {
            dimension2.width = i2;
         }

         if (dimension2.height > j2) {
            dimension2.height = j2;
         }
      }
   }

   void m1194(boolean flag) {
      this.f712.width = 0;
      this.f712.height = 0;

      for (int j = 0; j < this.cols; j++) {
         this.f710[j] = 0;
      }

      for (int i = 0; i < this.rows; i++) {
         this.f711[i] = 0;

         for (int k = 0; k < this.cols; k++) {
            Component component = this.f708[i][k];
            if (component != null && component.isVisible()) {
               Dimension dimension = flag ? component.getMinimumSize() : component.getPreferredSize();
               this.f709[i][k] = new Dimension(dimension);
               if (dimension.width > this.f710[k]) {
                  this.f710[k] = dimension.width;
               }

               if (dimension.height > this.f711[i]) {
                  this.f711[i] = dimension.height;
               }
            }
         }

         if (this.f711[i] > this.f712.height) {
            this.f712.height = this.f711[i];
         }
      }

      for (int l = 0; l < this.cols; l++) {
         if (this.f710[l] > this.f712.width) {
            this.f712.width = this.f710[l];
         }
      }
   }

   Dimension m1195(Container container) {
      Insets insets = container.getInsets();
      int i = insets.left + insets.right + (this.cols == 0 ? 0 : this.getHgap() * (this.cols - 1));
      if (this.f705) {
         for (int j = 0; j < this.cols; j++) {
            i += this.f710[j];
         }
      } else {
         i += this.f712.width * this.cols;
      }

      int l = insets.top + insets.bottom + (this.rows == 0 ? 0 : this.getVgap() * (this.rows - 1));
      if (this.f706) {
         for (int k = 0; k < this.rows; k++) {
            l += this.f711[k];
         }
      } else {
         l += this.f712.height * this.rows;
      }

      return new Dimension(i, l);
   }

   @Override
   public Dimension minimumLayoutSize(Container container) {
      this.m1194(true);
      return this.m1195(container);
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      this.m1194(false);
      return this.m1195(container);
   }

   @Override
   public Dimension maximumLayoutSize(Container container) {
      this.m1194(false);
      return this.m1195(container);
   }

   public Dimension m1196(Container container, Dimension dimension1, Component component, Dimension dimension2, Dimension dimension3) {
      Dimension dimension = this.preferredLayoutSize(container);
      container.setSize(dimension);
      return dimension;
   }

   @Override
   public void addLayoutComponent(Component component, Object object) {
      if (object != null) {
         if (!(object instanceof Point)) {
            throw new IllegalArgumentException("Bad constraint type");
         }

         this.f701 = ((Point)object).y;
         this.f702 = ((Point)object).x;
      }

      this.f708[this.f701][this.f702] = component;
      if (++this.f702 >= this.cols) {
         this.f702 = 0;
         if (++this.f701 >= this.rows) {
            this.f701 = 0;
         }
      }
   }

   Component m1197(Object object) {
      if (!(object instanceof Point)) {
         throw new IllegalArgumentException("Constraint needs to be a java.awt.Point");
      } else {
         int i = ((Point)object).x;
         int j = ((Point)object).y;
         return j >= 0 && j < this.rows && i >= 0 && i < this.cols ? this.f708[j][i] : null;
      }
   }

   Point m1198(Container container, Component component) {
      if (component == null) {
         return null;
      } else {
         for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
               if (this.f708[i][j] == component) {
                  return new Point(j, i);
               }
            }
         }

         return null;
      }
   }

   @Override
   public void removeLayoutComponent(Component component) {
      for (int i = 0; i < this.rows; i++) {
         for (int j = 0; j < this.cols; j++) {
            if (this.f708[i][j] == component) {
               this.f708[i][j] = null;
            }
         }
      }
   }

   @Override
   public void invalidateLayout(Container container) {
   }

   @Override
   public float getLayoutAlignmentX(Container container) {
      return 0.5F;
   }

   @Override
   public float getLayoutAlignmentY(Container container) {
      return 0.5F;
   }
}
