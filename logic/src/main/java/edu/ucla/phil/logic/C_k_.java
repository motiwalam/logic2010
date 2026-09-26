package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class C_k_ extends C_NC implements LogicConstants, C_j_F, MouseListener {
   static String[] f1193 = LogicProgram.symbols;
   C_DD f1194;
   int[] f1195 = null;
   Point f1196 = null;
   char[] f1197 = null;
   boolean f1198 = false;

   C_k_(C_DD c_dd) {
      super(LogicProgram.m995(c_dd.toString(), maggie, f1193));
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize * 7 / 6));
      this.f1194 = c_dd;
      this.setEditable(false);
      this.addMouseListener(this);
   }

   C_k_(String s, boolean flag) {
      super(LogicProgram.m995(s, maggie, f1193));
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize * 7 / 6));
      this.f1194 = flag ? new C_DD(s) : null;
      if (this.f1194 != null && this.f1194.f278 == null) {
         this.f1194 = null;
      }

      this.setEditable(false);
      this.addMouseListener(this);
   }

   C_k_(String s) {
      this(s, false);
   }

   C_n_F m1898() {
      if (this.f1194 == null) {
         return new C_n_F();
      } else {
         C_n_F c_n_f = this.f1194.m469();
         C_DD c_dd = this.f1194.m471(true);
         C_DD.m487(c_dd.f286, c_dd.f287, c_n_f, false);
         int i = this.f1194.m459()[0];

         for (int j = 0; j < c_n_f.f1314; j++) {
            c_n_f.f1316[j] = c_n_f.f1316[j] - i;
         }

         LogicProgram.m996(this.f1194.toString(), maggie, f1193, c_n_f.f1316);
         return c_n_f;
      }
   }

   C_n_F m1899() {
      C_n_F c_n_f = this.m1898();
      int[] aint = c_n_f.f1316;
      int i = c_n_f.f1314;

      for (int j = 0; j < i; j++) {
         aint[j] = this.m1130(aint[j]).x;
      }

      return c_n_f;
   }

   @Override
   public int m386() {
      C_n_F c_n_f = this.m1899();
      return c_n_f.f1314 < 2 ? (this.m1130(0).x + this.m1130(this.getDocument().getLength()).x) / 2 : (c_n_f.f1316[0] + c_n_f.f1316[1]) / 2;
   }

   @Override
   public void mousePressed(MouseEvent mouseevent) {
      int i = mouseevent.getX();
      Container container = this.getParent();
      if (container instanceof C_AD) {
         C_AD c_ad = (C_AD)container;
         if ("N".equals(c_ad.f144.f293.problem.f1456.m1811())) {
            return;
         }

         if (c_ad.f144.f293.noDescent) {
            this.f1198 = this.f1194 != null && this.f1194.m457() != 0 && this.m1899().m1983(i);
            this.m1901(this.f1195 = this.m1900(i));
            this.repaint();
         } else if (!c_ad.f143 && this.f1194 != null && this.f1194.m457() != 0 && this.m1899().m1983(i)) {
            c_ad.m229(!c_ad.f143);
            this.m1903(i, dialogGreen, false);
         } else {
            this.m1903(i, dialogRed, true);
            c_ad.f144.f293.errorCount++;
         }
      }
   }

   @Override
   public void mouseEntered(MouseEvent mouseevent) {
   }

   @Override
   public void mouseExited(MouseEvent mouseevent) {
   }

   @Override
   public void mouseClicked(MouseEvent mouseevent) {
   }

   @Override
   public void mouseReleased(MouseEvent mouseevent) {
   }

   int[] m1900(int i) {
      String s = this.getText();
      int j = 0;
      int k = s.length();
      if (i >= this.m1130(0).x && i < this.m1130(k).x) {
         while (j < k && i >= this.m1130(j + 1).x) {
            j++;
         }

         return s.charAt(j) == ' ' ? null : LogicProgram.m1001(s, j, f1193);
      } else {
         return null;
      }
   }

   boolean m1901(int[] aint) {
      if (aint == null) {
         this.f1197 = null;
         this.f1196 = null;
         return false;
      } else {
         String s = this.getText();
         this.f1197 = new char[aint[1] - aint[0]];
         s.getChars(aint[0], aint[1], this.f1197, 0);
         Graphics graphics = this.getGraphics();
         this.f1196 = this.m1130(aint[0]);
         this.f1196.y = this.f1196.y + graphics.getFontMetrics().getAscent();
         return true;
      }
   }

   void m1902() {
      this.m1901(this.f1195 = null);
      this.f1198 = false;
      this.repaint();
   }

   void m1903(int i, Color color, boolean flag) {
      if (this.m1901(this.f1195 = this.m1900(i))) {
         if (flag) {
            Toolkit.getDefaultToolkit().beep();
         }

         Graphics graphics = this.getGraphics();
         Color color1 = this.getForeground();
         graphics.setColor(color);
         graphics.drawChars(this.f1197, 0, this.f1197.length, this.f1196.x, this.f1196.y);

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException interruptedexception) {
         }

         graphics.setColor(color1);
         graphics.drawChars(this.f1197, 0, this.f1197.length, this.f1196.x, this.f1196.y);
      }
   }

   public void m1904() {
   }

   @Override
   public void paintComponent(Graphics graphics) {
      super.paintComponent(graphics);
      Container container = this.getParent();
      if (container instanceof C_AD) {
         C_AD c_ad = (C_AD)container;
         boolean flag = c_ad.f144.f293.noDescent;
         boolean flag1 = c_ad.f144.f293.checkDisabled;
         if (flag && this.f1197 != null && this.f1196 != null) {
            Color color = graphics.getColor();
            Color color1 = flag ? dialogOrange : (this.f1198 ? dialogGreen : dialogRed);
            graphics.setColor(color1);
            graphics.drawChars(this.f1197, 0, this.f1197.length, this.f1196.x, this.f1196.y);
            graphics.setColor(color);
         }
      }
   }
}
