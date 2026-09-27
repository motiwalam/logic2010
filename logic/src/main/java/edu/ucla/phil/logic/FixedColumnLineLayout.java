package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

class FixedColumnLineLayout extends FlowLayout {
   int columnCount;
   int[] columnWidths;
   Component indentSource;

   FixedColumnLineLayout(int i) {
      this(null, i, null);
   }

   FixedColumnLineLayout(int i, int[] aint) {
      this(null, i, aint);
   }

   FixedColumnLineLayout(Component component, int i) {
      this(component, i, null);
   }

   FixedColumnLineLayout(Component component, int i, int[] aint) {
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
      int j = container.getComponentCount();

      for (int k = 0; k < j; k++) {
         Component component = container.getComponent(k);
         int l = this.columnWidths[k];
         if (k == 0 && this.indentSource != null) {
            l += LogicProgram.boundsRelativeTo(this.indentSource, container).x;
         }

         component.setSize(new Dimension(l, 10));
         component.doLayout();
      }

      FixedColumnLineLayout.LineRowMetrics fixedcolumnlinelayout$linerowmetrics = new FixedColumnLineLayout.LineRowMetrics(container);

      for (int j1 = 0; j1 < j; j1++) {
         Component component1 = container.getComponent(j1);
         int i1 = this.columnWidths[j1];
         if (j1 == 0 && this.indentSource != null) {
            i1 += LogicProgram.boundsRelativeTo(this.indentSource, container).x;
         }

         component1.setBounds(i, b0, i1, fixedcolumnlinelayout$linerowmetrics.rowHeight);
         i += i1;
      }
   }

   @Override
   public Dimension preferredLayoutSize(Container container) {
      FixedColumnLineLayout.LineRowMetrics fixedcolumnlinelayout$linerowmetrics = new FixedColumnLineLayout.LineRowMetrics(container);
      return new Dimension(this.getTotalWidth(container), fixedcolumnlinelayout$linerowmetrics.rowHeight);
   }

   class LineRowMetrics {
      int rowHeight = 0;
      int count;
      Dimension[] sizes;

      LineRowMetrics(Container container) {
         this.count = container.getComponentCount();
         if (FixedColumnLineLayout.this.columnCount < this.count) {
            this.count = FixedColumnLineLayout.this.columnCount;
         }

         this.sizes = new Dimension[this.count];

         for (int i = 0; i < this.count; i++) {
            Component component = container.getComponent(i);
            this.sizes[i] = component.getPreferredSize();
            if (!(component instanceof MessageDetailsButton) && this.rowHeight < this.sizes[i].height) {
               this.rowHeight = this.sizes[i].height;
            }
         }
      }
   }
}
