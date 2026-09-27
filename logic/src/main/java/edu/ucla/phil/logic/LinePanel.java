package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

class LinePanel extends SizedPanel {
   DerivationLine line;

   LinePanel() {
      this(null, null);
   }

   LinePanel(int i) {
      this(null, new Dimension(i, -1));
   }

   LinePanel(int i, int j) {
      this(null, new Dimension(i, j));
   }

   LinePanel(Dimension dimension) {
      this(null, dimension);
   }

   LinePanel(DerivationLine derivationline) {
      this(derivationline, null);
   }

   LinePanel(DerivationLine derivationline, Dimension dimension) {
      super(dimension);
      this.line = derivationline;
   }

   @Override
   public void paintBorder(Graphics graphics) {
      super.paintBorder(graphics);
      this.paintBoxBracket(graphics);
   }

   void paintBoxBracket(Graphics graphics) {
      if (this.line != null && this.getComponentCount() == 0) {
         DerivationBox derivationbox = this.line.box;
         if (derivationbox != null) {
            Rectangle rectangle = LogicProgram.boundsRelativeTo(this, derivationbox.module.problem);
            derivationbox.drawBracket(graphics, rectangle);
            derivationbox = derivationbox.parentBox;
         }
      }
   }
}
