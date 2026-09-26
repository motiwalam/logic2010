package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JSeparator;

class C_CC extends JSeparator {
   int width;
   int f256;
   boolean f257;

   C_CC(boolean flag) {
      this(0, 0, flag, null);
   }

   C_CC(int i, int j, boolean flag, Color color) {
      this.width = i;
      this.f256 = j;
      this.f257 = flag;
      this.setForeground(color);
   }

   void m421(int i) {
      this.width = i;
      this.invalidate();
   }

   void m422(int i) {
      this.f256 = this.width < i ? this.width : i;
      this.invalidate();
   }

   @Override
   public Dimension getPreferredSize() {
      return this.f257 ? new Dimension(this.width, 0) : new Dimension(0, this.width);
   }

   public Dimension m423(Dimension dimension) {
      return dimension;
   }

   @Override
   public void paint(Graphics graphics) {
      Rectangle rectangle = this.getBounds();
      if (this.f256 != 0) {
         if (this.f257) {
            graphics.fillRect((rectangle.width - this.f256) / 2, 0, this.f256, rectangle.height);
         } else {
            graphics.fillRect(0, (rectangle.height - this.f256) / 2, rectangle.width, this.f256);
         }
      }
   }
}
