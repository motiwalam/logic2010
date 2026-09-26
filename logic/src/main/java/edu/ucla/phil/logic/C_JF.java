package edu.ucla.phil.logic;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

class C_JF extends JPanel implements C_n_A, C_LC, KeyListener {
   C_r_B f439;
   int width;
   int height;
   static String[] f440 = LogicProgram.f596;

   C_JF(C_r_B c_r_b, String[][] astring, boolean flag, boolean flag1) {
      this.f439 = c_r_b;
      this.height = astring.length;
      this.width = 0;

      for (int i = 0; i < this.height; i++) {
         if (astring[i] != null && astring[i].length > this.width) {
            this.width = astring[i].length;
         }
      }

      this.setLayout(new C_QF(this.width, this.height, flag, flag1));
      this.setFocusable(true);
      this.addKeyListener(this);

      for (int k = 0; k < this.height; k++) {
         String[] astring1 = astring[k];
         if (astring1 != null) {
            for (int j = 0; j < astring1.length; j++) {
               if (astring1[j] != null) {
                  this.add(new C_q_D(this, astring1[j]), new Point(j, k));
               }
            }
         }
      }
   }

   C_JF(C_r_B c_r_b, String[][] astring) {
      this(c_r_b, astring, false, false);
   }

   void m732(String[][] astring) {
      C_QF c_qf = (C_QF)this.getLayout();

      for (int i = 0; i < astring.length; i++) {
         String[] astring1 = astring[i];
         if (astring1 != null) {
            for (int j = 0; j < astring1.length; j++) {
               String s = astring1[j];
               if (s != null) {
                  C_q_D c_q_d = (C_q_D)c_qf.m1197(new Point(j, i));
                  if (c_q_d != null) {
                     c_q_d.m1583(s);
                  }
               }
            }
         }
      }
   }

   @Override
   public void keyPressed(KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         int i = keyevent.getKeyCode();
         int j = keyevent.getModifiers();
         if (i == 27) {
            LogicProgram.m1086(this, keyevent);
         } else if (i == 37 && (j & 8) == 0) {
            m736(this.f439);
            keyevent.consume();
         } else if (i == 39 && (j & 8) == 0) {
            m735(this.f439);
            keyevent.consume();
         } else if (i == 36) {
            m733(this.f439);
            keyevent.consume();
         } else if (i == 35) {
            m734(this.f439);
            keyevent.consume();
         } else if (i == 8) {
            m740(this.f439);
            keyevent.consume();
         } else if (i == 127) {
            if ((j & 8) == 0) {
               m741(this.f439);
            } else {
               this.f439.m375("delete line");
            }

            keyevent.consume();
         } else if (i == 38 && (j & 8) == 0) {
            this.f439.m375("up");
            keyevent.consume();
         } else if (i == 40 && (j & 8) == 0) {
            this.f439.m375("down");
            keyevent.consume();
         } else if (i == 10) {
            this.f439.m375("enter");
            keyevent.consume();
         } else if (i == 9) {
            this.f439.m375("tab");
            keyevent.consume();
         }
      }
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         char c0 = keyevent.getKeyChar();
         int i = keyevent.getModifiers();
         if (c0 == 1 && (i & 1) == 0) {
            m737(this.f439);
            keyevent.consume();
         } else if (c0 == 24 && (i & 1) != 0) {
            this.f439.m375("Box/Unbox");
            keyevent.consume();
         } else {
            String s;
            if ((s = C_s_D.m2081(c0, i)) != null) {
               this.m738(s);
               keyevent.consume();
            } else if (!Character.isISOControl(c0)) {
               this.m738(String.valueOf(c0));
               keyevent.consume();
            } else {
               LogicProgram.m1086(this, keyevent);
            }
         }
      }
   }

   @Override
   public void keyReleased(KeyEvent keyevent) {
   }

   static void m733(C_r_B c_r_b) {
      c_r_b.m379(0, 0);
      c_r_b.m380(0);
   }

   static void m734(C_r_B c_r_b) {
      int i = c_r_b.m1304().length();
      c_r_b.m379(i, i);
      c_r_b.m380(i);
   }

   static void m735(C_r_B c_r_b) {
      int i = c_r_b.m376();
      int j = c_r_b.m377();
      int k = c_r_b.m378();
      int l = c_r_b.m1304().length();
      if (i != j) {
         k = j;
      } else if (k < l) {
         k++;
      }

      c_r_b.m379(k, k);
      c_r_b.m380(k);
   }

   static void m736(C_r_B c_r_b) {
      int i = c_r_b.m376();
      int j = c_r_b.m377();
      int k = c_r_b.m378();
      if (i != j) {
         k = i;
      } else if (k > 0) {
         k--;
      }

      c_r_b.m379(k, k);
      c_r_b.m380(k);
   }

   static void m737(C_r_B c_r_b) {
      int i = c_r_b.m1304().length();
      c_r_b.m379(0, i);
      c_r_b.m380(i);
   }

   void m738(String s) {
      m739(this.f439, s);
   }

   static void m739(C_r_B c_r_b, String s) {
      if (s != null && !c_r_b.m1306()) {
         int i = c_r_b.m376();
         int j = c_r_b.m377() - i;
         if (j > 0) {
            c_r_b.m1305(i, j);
         } else {
            i = c_r_b.m378();
         }

         c_r_b.m381(s, i);
         int k;
         c_r_b.m380(k = i + s.length());
         c_r_b.m379(k, k);
         c_r_b.invalidate();
      }
   }

   static void m740(C_r_B c_r_b) {
      if (!c_r_b.m1306()) {
         int i = c_r_b.m376();
         int j = c_r_b.m377() - i;
         if (j == 0 && (i = c_r_b.m378() - 1) >= 0) {
            j = 1;
         }

         if (j > 0) {
            c_r_b.m1305(i, j);
            c_r_b.m380(i);
            c_r_b.m379(i, i);
            c_r_b.invalidate();
         }
      }
   }

   static void m741(C_r_B c_r_b) {
      if (!c_r_b.m1306()) {
         int i = c_r_b.m376();
         int j = c_r_b.m377() - i;
         int k = c_r_b.m1304().length();
         if (j == 0 && (i = c_r_b.m378()) < k) {
            j = 1;
         }

         if (j > 0) {
            c_r_b.m1305(i, j);
            c_r_b.m380(i);
            c_r_b.m379(i, i);
            c_r_b.invalidate();
         }
      }
   }
}
