package edu.ucla.phil.logic;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

class LineLabel extends LogicLabel implements LogicConstants {
   boolean canceled;
   DerivationLine line;
   private static int instanceCount = 0;
   int serial = ++instanceCount;

   LineLabel(String s) {
      super(s, 0, 1);
      this.canceled = false;
      this.line = null;
   }

   LineLabel(DerivationLine derivationline) {
      super("", 4);
      this.canceled = false;
      this.line = derivationline;
   }

   LineLabel copyLabel() {
      LineLabel linelabel1 = new LineLabel(this.getText());
      linelabel1.canceled = this.canceled;
      return linelabel1;
   }

   void setCanceled(boolean flag) {
      this.canceled = flag;
      this.invalidate();
   }

   void drawStrikeout(Graphics graphics) {
      Rectangle rectangle = this.getBounds();
      FontMetrics fontmetrics = this.getFontMetrics(this.getFont());
      int i = fontmetrics.getAscent();
      int j = fontmetrics.getDescent();
      int k = Math.max(fontmetrics.getLeading(), 1);
      int l = 0;
      int i1 = rectangle.x;
      int j1 = rectangle.y + k + (i + j) * 7 / 16;
      String s = this.getText();
      int k1 = s.length();

      while (l < k1) {
         int l1 = s.indexOf(32, l);
         int i2 = fontmetrics.stringWidth(l1 < 0 ? s.substring(l) : s.substring(l, l1));
         if (i2 > 0) {
            graphics.fillRect(i1, j1, i2, (i + j) / 8);
         }

         if (l1 < 0) {
            break;
         }

         i1 += i2 + fontmetrics.stringWidth(s.substring(l1, l = l1 + 1));
      }
   }

   @Override
   public void paintChildren(Graphics graphics) {
      super.paintChildren(graphics);
      if (this.canceled) {
         this.drawStrikeout(graphics);
      }
   }
}
