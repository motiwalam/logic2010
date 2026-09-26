package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

class C_M extends SizedPanel {
   DerivationLine f608;

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

   C_M(DerivationLine derivationline) {
      this(derivationline, null);
   }

   C_M(DerivationLine derivationline, Dimension dimension) {
      super(dimension);
      this.f608 = derivationline;
   }

   @Override
   public void paintBorder(Graphics graphics) {
      super.paintBorder(graphics);
      this.m1087(graphics);
   }

   void m1087(Graphics graphics) {
      if (this.f608 != null && this.getComponentCount() == 0) {
         DerivationBox derivationbox = this.f608.f317;
         if (derivationbox != null) {
            Rectangle rectangle = LogicProgram.m1035(this, derivationbox.f915.problem);
            derivationbox.m1552(graphics, rectangle);
            derivationbox = derivationbox.f916;
         }
      }
   }
}
