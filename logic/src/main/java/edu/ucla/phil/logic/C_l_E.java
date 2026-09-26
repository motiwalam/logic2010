package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import javax.swing.SwingUtilities;

class C_l_E extends C_s_D implements C_DE, FocusListener {
   int f1255;
   int f1256;
   int f1257;
   C_G f1258;
   boolean f1259;
   int f1260 = 0;
   static boolean f1261 = false;

   C_l_E(C_G c_g) {
      this.f1258 = c_g;
      this.f1259 = false;
      this.f1255 = this.f1256 = this.f1257 = 0;
      this.setCaretPosition(0);
      this.setForeground(c_g.f317.f915.colors[0]);
      this.setBackground(c_g.f317.f915.colors[2]);
      this.m1787(true);
      this.m1789(true);
      this.addFocusListener(this);
   }

   @Override
   public void setForeground(Color color) {
      super.setForeground(color);
      this.m1791(this.getForeground());
   }

   @Override
   public void setBackground(Color color) {
      super.setBackground(color);
      this.m1792(this.getBackground());
   }

   boolean m1928(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      int i = keyevent.getModifiers();
      if (c0 == 17 && (i & 1) != 0) {
         return true;
      } else if (c0 == 19 && (i & 1) != 0) {
         C_0B c_0b1 = this.f1258.m570();
         if (c_0b1 != null) {
            c_0b1.m22(false);
         }

         return true;
      } else if (c0 == 24 && (i & 1) != 0) {
         this.f1258.m573();
         if (this.f1258.f317.f918 == this.f1258) {
            this.f1258.m22(true);
         }

         return true;
      } else if (c0 == '\t' && (i & 2) == 0) {
         this.f1258.m22(this == this.f1258.f323);
         return true;
      } else if (c0 != '\n' && c0 != '\r') {
         if (c0 == 127 && (i & 8) != 0) {
            if ((i & 1) != 0 && this.f1258 == this.f1258.f317.f917) {
               this.f1258.m577();
            }

            if (this.f1258.f317.f915.problem.f917 != this.f1258) {
               C_0B c_0b = this.f1258.m23(false);
               if (c_0b == null) {
                  c_0b = this.f1258.m24(true);
               }

               this.f1258.m19(false);
               c_0b.m22(this.f1258.f324 == this);
            }

            return true;
         } else {
            return false;
         }
      } else {
         if (this == this.f1258.f323) {
            C__ c__ = this.f1258.m564();
         }

         C_G c_g = null;
         boolean flag = this.f1258.f324 == this;
         int j = i & 11;
         if (this.f1258.f317.f915.commandMode) {
            if (this.f1258.f324 != null && (j == 1 || j == 2)) {
               this.f1258.m557(1);
               this.f1258.m6("");
               this.f1258.f332 = null;
               this.f1258.f333 = true;
            }

            if (j != 3 && j != 8) {
               this.f1258.m563(j != 0 || this.f1258.f323 != null);
            }

            c_g = j == 1 ? this.f1258 : this.f1258.m18();
         } else {
            c_g = this.f1258.m18();
         }

         if (c_g != null) {
            if (c_g != this.f1258 && this.f1258.f317.f917 == this.f1258) {
               c_g.m22(this.f1258.f317.f915.commandMode);
            } else {
               c_g.m22(flag);
            }
         }

         return true;
      }
   }

   boolean m1929(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      int i = keyevent.getKeyCode();
      int j = keyevent.getModifiers();
      if (c0 == '\t' && (j & 2) == 0) {
         return true;
      } else if (i == 38) {
         if ((j & 8) == 0) {
            C_0B c_0b1 = this.f1258.m24(true);
            if (c_0b1 != null) {
               c_0b1.m22(this.f1258.f324 == this);
            }
         } else if (this.f1258.f317.f917 == this.f1258 && this.f1258.f317.f915.problem.f917 != this.f1258 && this.f1258.f317.m1555() > 1) {
            this.f1258.f317.m2123(false);
            this.f1258.f317.f915.setWidths(true);
         }

         return true;
      } else if (i == 40) {
         if ((j & 8) == 0) {
            C_0B c_0b = this.f1258.m23(true);
            if (c_0b != null) {
               c_0b.m22(this.f1258.f324 == this);
            }
         } else if (this.f1258.f317.f917 == this.f1258) {
            this.f1258.f317.m2123(true);
            this.f1258.f317.f915.setWidths(true);
         }

         return true;
      } else if (i == 39 && (j & 8) != 0) {
         this.f1258.m566();
         return true;
      } else if (i == 37 && (j & 8) != 0) {
         this.f1258.m567();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void focusGained(FocusEvent focusevent) {
      if (this.getGraphics() == null) {
         this.f1258.m22(false);
      } else {
         if (this.f1258.f317.f915.focus != this) {
            if (this == this.f1258.f324) {
               this.f1258.m45();
               this.f1258.m592();
            }

            this.m1931();
            this.f1258.f317.f915.focus = this;
            this.f1258.f317.f915.lastFocus = this;
            this.m1798(this.getCaretPosition());
         }

         this.setForeground(this.f1258.f317.f915.colors[1]);
         this.setBackground(this.f1258.f317.f915.colors[0]);
         C__ c__ = this.f1258.m17();
         if (c__ != null) {
            c__.m1551(true);
         }

         if (this.f1258.f317.f915.requestAid) {
            SwingUtilities.invokeLater(new Runnable() {
               @Override
               public void run() {
                  C_l_E.this.m714();
               }
            });
            this.f1258.f317.f915.requestAid = false;
         }
      }
   }

   @Override
   public void focusLost(FocusEvent focusevent) {
      this.setForeground(this.f1258.f317.f915.colors[0]);
      this.setBackground(this.f1258.f317.f915.colors[2]);
      C__ c__ = this.f1258.m17();
      if (c__ != null) {
         c__.m1551(false);
      }

      if (this.f1258.f317.f915.focus == this) {
         if (this.f1259) {
            this.f1259 = false;
         } else if (this == this.f1258.f324) {
            this.f1258.m586();
         } else if (this == this.f1258.f323) {
            this.f1258.m594();
            this.f1258.m593();
         }

         this.m1930(true);
         this.f1258.f317.f915.focus = null;
      }
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      int i = awtevent.getID();
      if (i == 400) {
         if (this.m1928((KeyEvent)awtevent)) {
            this.f1258.f317.f915.invalrepaint();
         } else {
            super.processEvent(awtevent);
            LogicProgram.m1086(this, (KeyEvent)awtevent);
         }
      } else if (i == 401) {
         if (!this.m1929((KeyEvent)awtevent)) {
            super.processEvent(awtevent);
            LogicProgram.m1086(this, (KeyEvent)awtevent);
         }
      } else {
         super.processEvent(awtevent);
      }
   }

   void m1930(boolean flag) {
      this.f1255 = this.getSelectionStart();
      this.f1256 = this.getSelectionEnd();
      this.f1257 = this.getCaretPosition();
      if (flag) {
         this.select(0, 0);
      }
   }

   void m1931() {
      this.select(this.f1255, this.f1256);
   }

   void m1932(int i, int j, int k) {
      if (this.f1255 >= i + j) {
         this.f1255 += k - j;
      } else if (this.f1255 > i + k) {
         this.f1255 = i + k;
      }

      if (this.f1256 >= i + j) {
         this.f1256 += k - j;
      } else if (this.f1256 > i + k) {
         this.f1256 = i + k;
      }

      if (this.f1257 >= i + j) {
         this.f1257 += k - j;
      } else if (this.f1257 > i + k) {
         this.f1257 = i + k;
      }
   }

   @Override
   void m714() {
      Rectangle rectangle = LogicProgram.m1035(this, null);
      C_BA c_ba = new C_BA(this);
      if (this == this.f1258.f323) {
         String[] astring = new String[]{"\\l->", "\\l~", "\\l&", "\\l|", "\\l<->", "\\l@", "\\l!", "\\l=", "\\l<>", "\\l%"};
         String[] astring1 = LogicProgram.m1022(LogicProgram.f599);
         String[] astring2 = LogicProgram.m1022(LogicProgram.f601);
         String[] astring3 = LogicProgram.m1022(LogicProgram.f600);
         String[] astring4 = LogicProgram.m1022("xyzuvw");
         String[] astring5 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
         String[] astring6 = new String[]{"(", ")", ".", "\\l.:"};
         String[][] astring7 = new String[][]{astring, astring1, astring6, astring2, astring3, astring4, astring5};
         C_JF c_jf;
         c_ba.add(c_jf = new C_JF(c_ba, astring7));
         String[] astring8 = new String[]{
            "Ctrl+Shift+C",
            "Ctrl+Shift+N",
            "Ctrl+Shift+A",
            "Ctrl+Shift+O",
            "Ctrl+Shift+B",
            "Ctrl+Shift+U",
            "Ctrl+Shift+E",
            null,
            "Ctrl+Shift+I",
            "Ctrl+Shift+D"
         };
         String[] astring9 = new String[]{null, null, null, "Ctrl+Shift+T"};
         String[][] astring10 = new String[][]{astring8, null, astring9};
         c_jf.m732(astring10);
         astring = new String[]{"space", "up", "Show/Unshow", "enter"};
         astring1 = new String[]{"tab", "down", "backspace", "delete line"};
         astring2 = new String[]{"copy", "paste"};
         String[][] astring16 = new String[][]{astring, astring1, astring2};
         c_ba.add(c_jf = new C_JF(c_ba, astring16, true, false));
         astring4 = new String[]{null, null, "Ctrl+Shift+S"};
         astring5 = new String[]{null, null, null, "Alt+Delete"};
         astring6 = new String[]{"Ctrl+C", "Ctrl+V"};
         astring7 = new String[][]{astring4, astring5, astring6};
         c_jf.m732(astring7);
      } else if (this == this.f1258.f324) {
         Integer integer = this.f1258.f317.f915.chapter;
         String[] astring13 = new String[]{"PR", "CD", "ID", "DD", "ASS CD", "ASS ID"};
         String[] astring14 = new String[]{"R", "MP", "MT", "DN"};
         String[] astring17 = new String[]{"S", "BC", "CB", "MTP", "ADD", "ADJ"};
         String[] astring18 = new String[]{"DM", "NC", "NB", "CDJ"};
         String[] astring20 = new String[]{"UI", "EI", "EG", "QN", "UD"};
         String[][] astring21;
         if (integer != null && integer == 1) {
            astring21 = new String[][]{astring13, astring14};
         } else if (integer != null && integer == 2) {
            astring21 = new String[][]{astring13, astring14, astring17, astring18};
         } else {
            astring21 = new String[][]{astring13, astring14, astring17, astring18, astring20};
         }

         c_ba.add(new C_JF(c_ba, astring21, true, false));
         astring13 = new String[]{"Show Conc", "Show Cons", "Show Ant"};
         astring14 = new String[]{"Show Unneg", "Show NegCons"};
         astring17 = new String[]{"Show Conclusion", "Show Consequent", "Show Antecedent"};
         astring18 = new String[]{"Show Unnegation", "Show NegConsequent"};
         astring20 = new String[]{"Show Corr", "Show Conj", "Show Cond"};
         String[] astring22 = new String[]{"Show CorrCond", "Show Conjunct", "Show Conditional"};
         String[] astring24 = new String[]{"Show NegDisj"};
         String[] astring25 = new String[]{"Show NegDisjunct"};
         String[] astring27 = new String[]{"Show Inst"};
         String[] astring28 = new String[]{"Show Instance"};
         String[][] astring11;
         String[][] astring12;
         if (integer != null && integer == 1) {
            astring11 = new String[][]{astring13, astring14};
            astring12 = new String[][]{astring17, astring18};
         } else if (integer != null && integer == 2) {
            astring11 = new String[][]{astring13, astring14, astring20, astring24};
            astring12 = new String[][]{astring17, astring18, astring22, astring25};
         } else {
            astring11 = new String[][]{astring13, astring14, astring20, astring24, astring27};
            astring12 = new String[][]{astring17, astring18, astring22, astring25, astring28};
         }

         C_JF c_jf1;
         c_ba.add(c_jf1 = new C_JF(c_ba, astring11, true, false));
         c_jf1.m732(astring12);
         astring13 = new String[]{"space", "up", "Box/Unbox", "enter"};
         astring14 = new String[]{"tab", "down", "backspace", "delete line"};
         astring17 = new String[]{"copy", "paste"};
         String[][] astring19 = new String[][]{astring13, astring14, astring17};
         c_ba.add(c_jf1 = new C_JF(c_ba, astring19, true, false));
         astring20 = new String[]{null, null, "Ctrl+Shift+X"};
         String[] astring23 = new String[]{null, null, null, "Alt+Delete"};
         astring24 = new String[]{"Ctrl+C", "Ctrl+V"};
         String[][] astring26 = new String[][]{astring20, astring23, astring24};
         c_jf1.m732(astring26);
         astring13 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
         String[][] astring15 = new String[][]{astring13};
         c_ba.add(new C_JF(c_ba, astring15));
      }

      c_ba.m1302(new Point(rectangle.x, rectangle.y + rectangle.height));
      c_ba.setResizable(false);
      this.f1259 = true;
      c_ba.m1301();
   }
}
