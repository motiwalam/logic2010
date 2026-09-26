package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;

class C_x_C extends SizedPanel implements C_v_D {
   C_d_C f1437;
   C_x_E f1438;
   C_NB f1439;
   C__E f1440;
   boolean f1441;
   int f1442;
   static final boolean f1443 = true;

   C_x_C(C_d_C c_d_c, String s, int i) {
      this.f1437 = c_d_c;
      this.f1442 = i;
      this.f1441 = false;
      this.setLayout(new FlowLayout(1, 0, 0));
      if (i == 1) {
         C_ZE c_ze = new C_ZE("(");
         c_ze.setFocusable(false);
         this.add(c_ze);
      }

      this.add(this.f1438 = new C_x_E(m2166(s == null ? "" : s)));
      if (c_d_c.f1044 != null) {
         Color[] acolor = c_d_c.f1044.colors;
         this.f1438.setForeground(acolor[0]);
         this.f1438.setBackground(acolor[2]);
      }

      this.f1438.f1444 = this;
      if (i == -1) {
         C_ZE c_ze1 = new C_ZE(")");
         c_ze1.setFocusable(false);
         this.add(c_ze1);
      }

      this.f1439 = null;
      this.f1440 = new C__E(this);
   }

   void m2164() {
      this.m2165(false);
   }

   void m2165(boolean flag) {
      if (flag) {
         int[] aint = new int[]{this.f1438.getSelectionStart(), this.f1438.getSelectionEnd()};
         this.f1438.setText(m2167(this.f1438.getText(), aint));
         this.f1438.select(aint[0], aint[1]);
      } else {
         this.f1438.setText(m2166(this.f1438.getText()));
      }

      this.f1437.invalidate();
      if (this.f1437.f1044 != null) {
         this.f1437.f1044.updateSymbolization();
      }
   }

   static String m2166(String s) {
      return m2167(s, null);
   }

   static String m2167(String s, int[] aint) {
      if (s == null) {
         s = "";
      }

      char[] achar = s.toCharArray();
      int i = 0;
      int j = 0;
      int k = achar.length;

      while (i < k) {
         while (i < k && m2168(achar[i])) {
            i++;
         }

         while (i < k && !m2168(achar[i])) {
            m2169(aint, j, i);
            achar[j++] = achar[i++];
         }

         if (i < k) {
            m2169(aint, j, i);
            achar[j++] = achar[i++];
         }
      }

      if (j > 0 && m2168(achar[j - 1])) {
         j--;
      }

      m2169(aint, j, k);
      return j == 0 ? "    " : new String(achar, 0, j);
   }

   private static boolean m2168(char c0) {
      return c0 <= ' ' && c0 != '\n';
   }

   private static void m2169(int[] aint, int i, int j) {
      if (aint != null) {
         int k = aint.length;

         while (--k >= 0) {
            if (aint[k] > i && aint[k] <= j) {
               aint[k] = i;
            }
         }
      }
   }

   public void m2170(FocusEvent focusevent) {
      if (this.f1440.isVisible()) {
         this.f1441 = true;
      }

      this.m2172(true);
   }

   public void m2171(FocusEvent focusevent) {
      Object object = focusevent.getSource();
      if (!this.m2175() && !LogicProgram.m1033(this, (Component)object)) {
         this.m2172(false);
      }
   }

   void m2172(boolean flag) {
      if (this.f1437.f1044 != null) {
         Color[] acolor = this.f1437.f1044.colors;
         if (flag) {
            if (this.f1437.f1044.focus == this) {
               return;
            }

            if (this.f1437.f1044.focus != null) {
               this.f1437.f1044.focus.m2172(false);
            }

            this.f1437.f1044.focus = this;
            this.f1437.f1044.lastFocus = this;
            this.f1438.setForeground(acolor[1]);
            this.f1438.setBackground(acolor[0]);
            if (this.f1438.f1445) {
               this.f1438.setCaretPosition(0);
               this.f1438.f1445 = false;
            }

            C_d_C c_d_c = this.f1437.m1688();
            if (c_d_c == null) {
               byte b0 = -1;
            } else {
               c_d_c.m1687(this.f1437);
            }

            this.f1437.invalidate();
         } else {
            if (this.f1437.f1044.focus != this) {
               return;
            }

            this.f1438.setForeground(acolor[0]);
            this.f1438.setBackground(acolor[2]);
            this.f1437.f1044.focus = null;
            this.validate();
            this.m2164();
         }
      }
   }

   void m2173(boolean flag) {
      this.m2174(flag, 0);
   }

   void m2174(boolean flag, int i) {
      if (flag) {
         EditableTextPane.m2024(this.f1438.getText());
      }

      if (!this.f1438.hasFocus()) {
         this.f1438.f1445 = true;
         this.f1438.requestFocus();
      }

      Rectangle rectangle = this.f1438.getBounds(null);
      this.f1440.m1088();
      this.f1440.m1587(i + rectangle.x, rectangle.y + rectangle.height, this);
   }

   boolean m2175() {
      return this.f1440.isVisible();
   }
}
