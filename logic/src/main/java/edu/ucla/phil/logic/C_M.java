package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

class C_M extends C_LB {
   C_G f608;

   C_M() {
      this(null, null);
   }

   C_M(int i) {
      this(null, new Dimension(i, -1));
   }

   C_M(int i, int j) {
      this(null, new Dimension(i, j));
   }

   C_M(Dimension dimension) {
      this(null, dimension);
   }

   C_M(C_G c_g) {
      this(c_g, null);
   }

   C_M(C_G c_g, Dimension dimension) {
      super(dimension);
      this.f608 = c_g;
   }

   @Override
   public void paintBorder(Graphics graphics) {
      super.paintBorder(graphics);
      this.m1087(graphics);
   }

   void m1087(Graphics graphics) {
      if (this.f608 != null && this.getComponentCount() == 0) {
         C__ c__ = this.f608.f317;
         if (c__ != null) {
            Rectangle rectangle = LogicProgram.m1035(this, c__.f915.problem);
            c__.m1552(graphics, rectangle);
            c__ = c__.f916;
         }
      }
   }
}
