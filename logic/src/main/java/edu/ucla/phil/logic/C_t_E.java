package edu.ucla.phil.logic;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

class C_t_E extends C_ZE implements LogicConstants {
   boolean f1377;
   DerivationLine f1378;
   private static int f1379 = 0;
   int f1380 = ++f1379;

   C_t_E(String s) {
      super(s, 0, 1);
      this.f1377 = false;
      this.f1378 = null;
   }

   C_t_E(DerivationLine derivationline) {
      super("", 4);
      this.f1377 = false;
      this.f1378 = derivationline;
   }

   C_t_E m2089() {
      C_t_E c_t_e1 = new C_t_E(this.getText());
      c_t_e1.f1377 = this.f1377;
      return c_t_e1;
   }

   void m2090(boolean flag) {
      this.f1377 = flag;
      this.invalidate();
   }

   void m2091(Graphics graphics) {
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
      if (this.f1377) {
         this.m2091(graphics);
      }
   }
}
