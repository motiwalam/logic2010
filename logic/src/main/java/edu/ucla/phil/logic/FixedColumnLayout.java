package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

class FixedColumnLayout extends FlowLayout {
   int columnCount;
   int[] columnWidths;
   Component indentSource;

   FixedColumnLayout(int i) {
      this(null, i, null);
   }

   FixedColumnLayout(int i, int[] aint) {
      this(null, i, aint);
   }

   FixedColumnLayout(Component component, int i) {
      this(component, i, null);
   }

   FixedColumnLayout(Component component, int i, int[] aint) {
      this.indentSource = component;
      this.columnCount = i;
      this.columnWidths = new int[i];
      if (aint != null) {
         this.setColumnWidths(aint);
      }
   }

   int getColumnWidth(int i) {
      return this.columnWidths[i];
   }

   void setColumnWidth(int i, int j) {
      this.columnWidths[i] = j;
   }

   void setColumnWidths(int[] aint) {
      for (int i = 0; i < this.columnCount; i++) {
         this.columnWidths[i] = aint[i];
      }
   }

   int getTotalWidth(Container container) {
      int i = 0;

      for (int j = 0; j < this.columnCount; j++) {
         i += this.columnWidths[j];
      }

      if (this.indentSource != null) {
         i += LogicProgram.boundsRelativeTo(this.indentSource, container).x;
      }

      return i;
   }

   @Override
   public void layoutContainer(Container container) {
      int i = 0;
      byte b0 = 0;
      FixedColumnLayout.ColumnRowMetrics fixedcolumnlayout$columnrowmetrics = new FixedColumnLayout.ColumnRowMetrics(container);

      for (int j = 0; j < fixedcolumnlayout$columnrowmetrics.count; j++) {
         Component component = container.getComponent(j);
         int k = this.columnWidths[j];
         if (j == 0 && this.indentSource != null) {
            k += LogicProgram.boundsRelativeTo(this.indentSource, container).x;
         }

         component.setBounds(i, b0, k, fixedcolumnlayout$columnrowmetrics.rowHeight);
         i += k;
      }
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      FixedColumnLayout.ColumnRowMetrics fixedcolumnlayout$columnrowmetrics = new FixedColumnLayout.ColumnRowMetrics(container);
      return new Dimension(this.getTotalWidth(container), fixedcolumnlayout$columnrowmetrics.rowHeight);
   }

   class ColumnRowMetrics {
      int rowHeight = 0;
      int count;
      Dimension[] sizes;

      ColumnRowMetrics(Container container) {
         this.count = container.getComponentCount();
         if (FixedColumnLayout.this.columnCount < this.count) {
            this.count = FixedColumnLayout.this.columnCount;
         }

         this.sizes = new Dimension[this.count];

         for (int i = 0; i < this.count; i++) {
            Component component = container.getComponent(i);
            this.sizes[i] = component.getPreferredSize();
            if (this.rowHeight < this.sizes[i].height) {
               this.rowHeight = this.sizes[i].height;
            }
         }
      }
   }
}
