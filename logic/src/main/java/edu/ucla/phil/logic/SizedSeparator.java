package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JSeparator;

class SizedSeparator extends JSeparator {
   int width;
   int thickness;
   boolean horizontal;

   SizedSeparator(boolean flag) {
      this(0, 0, flag, null);
   }

   SizedSeparator(int i, int j, boolean flag, Color color) {
      this.width = i;
      this.thickness = j;
      this.horizontal = flag;
      this.setForeground(color);
   }

   void setLength(int i) {
      this.width = i;
      this.invalidate();
   }

   void setThickness(int i) {
      this.thickness = this.width < i ? this.width : i;
      this.invalidate();
   }

   @Override
   public Dimension getPreferredSize() {
      return this.horizontal ? new Dimension(this.width, 0) : new Dimension(0, this.width);
   }

   public Dimension adjustSize(Dimension dimension) {
      return dimension;
   }

   @Override
   public void paint(Graphics graphics) {
      Rectangle rectangle = this.getBounds();
      if (this.thickness != 0) {
         if (this.horizontal) {
            graphics.fillRect((rectangle.width - this.thickness) / 2, 0, this.thickness, rectangle.height);
         } else {
            graphics.fillRect(0, (rectangle.height - this.thickness) / 2, rectangle.width, this.thickness);
         }
      }
   }
}
