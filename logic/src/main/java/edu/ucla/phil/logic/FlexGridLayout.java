package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.awt.Rectangle;

class FlexGridLayout extends GridLayout implements LayoutManager2 {
   int rows;
   int cols;
   int nextRow;
   int nextColumn;
   int hAlign;
   int vAlign;
   boolean variableColumnWidths;
   boolean variableRowHeights;
   boolean fillCells;
   Component[][] cells;
   Dimension[][] cellSizes;
   int[] columnWidths;
   int[] rowHeights;
   Dimension maxCellSize;

   FlexGridLayout(int i, int j, int k, int l, boolean flag, boolean flag1, boolean flag2) {
      this.rows = j;
      this.cols = i;
      this.hAlign = Math.min(Math.max(k, 0), 2);
      this.vAlign = Math.min(Math.max(l, 0), 2);
      this.variableColumnWidths = flag;
      this.variableRowHeights = flag1;
      this.fillCells = flag2;
      this.nextRow = 0;
      this.nextColumn = 0;
      this.cells = new Component[j][i];
      this.cellSizes = new Dimension[j][i];
      this.columnWidths = new int[i];
      this.rowHeights = new int[j];
      this.maxCellSize = new Dimension();
   }

   FlexGridLayout(int i, int j, int k, int l, boolean flag, boolean flag1) {
      this(i, j, k, l, flag, flag1, false);
   }

   FlexGridLayout(int i, int j, boolean flag, boolean flag1, boolean flag2) {
      this(i, j, 1, 1, flag, flag1, flag2);
   }

   FlexGridLayout(int i, int j, boolean flag, boolean flag1) {
      this(i, j, flag, flag1, true);
   }

   FlexGridLayout(int i, int j) {
      this(i, j, false, false);
   }

   @Override
   public void layoutContainer(Container container) {
      Rectangle rectangle = LogicProgram.getInteriorBounds(container);
      this.measure(false);
      int i = this.getHgap();
      int j = this.getVgap();
      Insets insets = container.getInsets();
      int k = insets.top + j / 2;
      Dimension dimension = new Dimension(this.maxCellSize);

      for (int l = 0; l < this.rows; l++) {
         int i1 = insets.left + i / 2;
         if (this.variableRowHeights) {
            dimension.height = this.rowHeights[l];
         }

         for (int j1 = 0; j1 < this.cols; j1++) {
            Component component = this.cells[l][j1];
            if (this.variableColumnWidths) {
               dimension.width = this.columnWidths[j1];
            }

            if (component != null && component.isVisible()) {
               Dimension dimension1;
               int k1;
               int l1;
               if (this.fillCells) {
                  dimension1 = dimension;
                  k1 = 0;
                  l1 = 0;
               } else {
                  dimension1 = this.cellSizes[l][j1];
                  k1 = (dimension.width - dimension1.width) * this.hAlign / 2;
                  l1 = (dimension.height - dimension1.height) * this.vAlign / 2;
               }

               component.setBounds(i1 + k1, k + l1, dimension1.width, dimension1.height);
            }

            i1 += dimension.width + i;
         }

         k += dimension.height + j;
      }

      Dimension dimension2 = this.computeSize(container);
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

   void measure(boolean flag) {
      this.maxCellSize.width = 0;
      this.maxCellSize.height = 0;

      for (int j = 0; j < this.cols; j++) {
         this.columnWidths[j] = 0;
      }

      for (int i = 0; i < this.rows; i++) {
         this.rowHeights[i] = 0;

         for (int k = 0; k < this.cols; k++) {
            Component component = this.cells[i][k];
            if (component != null && component.isVisible()) {
               Dimension dimension = flag ? component.getMinimumSize() : component.getPreferredSize();
               this.cellSizes[i][k] = new Dimension(dimension);
               if (dimension.width > this.columnWidths[k]) {
                  this.columnWidths[k] = dimension.width;
               }

               if (dimension.height > this.rowHeights[i]) {
                  this.rowHeights[i] = dimension.height;
               }
            }
         }

         if (this.rowHeights[i] > this.maxCellSize.height) {
            this.maxCellSize.height = this.rowHeights[i];
         }
      }

      for (int l = 0; l < this.cols; l++) {
         if (this.columnWidths[l] > this.maxCellSize.width) {
            this.maxCellSize.width = this.columnWidths[l];
         }
      }
   }

   Dimension computeSize(Container container) {
      Insets insets = container.getInsets();
      int i = insets.left + insets.right + (this.cols == 0 ? 0 : this.getHgap() * (this.cols - 1));
      if (this.variableColumnWidths) {
         for (int j = 0; j < this.cols; j++) {
            i += this.columnWidths[j];
         }
      } else {
         i += this.maxCellSize.width * this.cols;
      }

      int l = insets.top + insets.bottom + (this.rows == 0 ? 0 : this.getVgap() * (this.rows - 1));
      if (this.variableRowHeights) {
         for (int k = 0; k < this.rows; k++) {
            l += this.rowHeights[k];
         }
      } else {
         l += this.maxCellSize.height * this.rows;
      }

      return new Dimension(i, l);
   }

   @Override
   public Dimension minimumLayoutSize(Container container) {
      this.measure(true);
      return this.computeSize(container);
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      this.measure(false);
      return this.computeSize(container);
   }

   @Override
   public Dimension maximumLayoutSize(Container container) {
      this.measure(false);
      return this.computeSize(container);
   }

   public Dimension fitContainer(Container container, Dimension dimension1, Component component, Dimension dimension2, Dimension dimension3) {
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

         this.nextRow = ((Point)object).y;
         this.nextColumn = ((Point)object).x;
      }

      this.cells[this.nextRow][this.nextColumn] = component;
      if (++this.nextColumn >= this.cols) {
         this.nextColumn = 0;
         if (++this.nextRow >= this.rows) {
            this.nextRow = 0;
         }
      }
   }

   Component getCellComponent(Object object) {
      if (!(object instanceof Point)) {
         throw new IllegalArgumentException("Constraint needs to be a java.awt.Point");
      } else {
         int i = ((Point)object).x;
         int j = ((Point)object).y;
         return j >= 0 && j < this.rows && i >= 0 && i < this.cols ? this.cells[j][i] : null;
      }
   }

   Point getCellPosition(Container container, Component component) {
      if (component == null) {
         return null;
      } else {
         for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
               if (this.cells[i][j] == component) {
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
            if (this.cells[i][j] == component) {
               this.cells[i][j] = null;
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
