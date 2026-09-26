package edu.ucla.phil.logic;

import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import javax.swing.SwingUtilities;

class C_x_E extends StyledTextPane implements C_v_D, KeyListener, FocusListener, MouseListener {
   C_x_C f1444 = null;
   boolean f1445 = true;

   C_x_E(String s) {
      super(s);
      this.addKeyListener(this);
      this.addMouseListener(this);
      this.addFocusListener(this);
      this.setFocusTraversalKeys(0, Collections.EMPTY_SET);
      this.setFocusTraversalKeys(1, Collections.EMPTY_SET);
   }

   @Override
   public void keyReleased(KeyEvent keyevent) {
   }

   @Override
   public void keyPressed(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      int i = keyevent.getKeyCode();
      int j = keyevent.getModifiers();
      if ((i == 61 || i == 187) && (j & 3) == 3) {
         C_d_C c_d_c3 = this.m2178(9, (j & 8) != 0);
         if (c_d_c3 != null) {
            c_d_c3.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if ((i == 47 || i == 191) && (j & 3) == 3) {
         if (this.f1444.f1437.f1044.hintsDisabled) {
            Toolkit.getDefaultToolkit().beep();
         } else {
            this.f1444.f1437.m1695();
         }

         keyevent.consume();
      } else if (i == 50 && (j & 3) == 3) {
         C_d_C c_d_c = this.m2178(11, (j & 8) != 0);
         if (c_d_c != null) {
            c_d_c.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if (i == 9) {
         keyevent.consume();
      } else if (i == 10) {
         keyevent.consume();
      } else if (i == 39 && (j & 8) != 0) {
         C_d_C c_d_c6 = this.f1444.f1437;
         C_d_C c_d_c8 = c_d_c6.m1688();
         int l = c_d_c8 == null ? -1 : c_d_c8.m1687(c_d_c6);
         if (l != -1 && l + 1 < c_d_c8.f1050.length) {
            c_d_c8.m1686(l + 1).f1045.f1438.requestFocus();
         } else {
            Toolkit.getDefaultToolkit().beep();
         }

         keyevent.consume();
      } else if (i == 37 && (j & 8) != 0) {
         C_d_C c_d_c5 = this.f1444.f1437;
         C_d_C c_d_c7 = c_d_c5.m1688();
         System.out.println(c_d_c7.m1687(c_d_c5));
         int k = c_d_c7 == null ? -1 : c_d_c7.m1687(c_d_c5);
         if (k - 1 < 0) {
            Toolkit.getDefaultToolkit().beep();
         } else {
            c_d_c7.m1686(k - 1).f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if (i == 38 && (j & 8) != 0) {
         C_d_C c_d_c4 = this.f1444.f1437;
         C_d_C c_d_c2 = c_d_c4.m1688();
         if (c_d_c2 == null) {
            Toolkit.getDefaultToolkit().beep();
         } else {
            c_d_c2.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if (i == 40 && (j & 8) != 0) {
         C_d_C c_d_c1 = this.f1444.f1437;
         if (c_d_c1.f1050.length == 0) {
            Toolkit.getDefaultToolkit().beep();
         } else {
            c_d_c1.m1686(0).f1045.f1438.requestFocus();
         }

         keyevent.consume();
      }
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      int i = keyevent.getKeyCode();
      int j = keyevent.getModifiers();
      if (c0 == 1 && (j & 3) == 3) {
         C_d_C c_d_c9 = this.m2178(3, (j & 8) != 0);
         if (c_d_c9 != null) {
            c_d_c9.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 2 && (j & 3) == 3) {
         C_d_C c_d_c8 = this.m2178(5, (j & 8) != 0);
         if (c_d_c8 != null) {
            c_d_c8.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 3 && (j & 3) == 3) {
         C_d_C c_d_c7 = this.m2178(2, (j & 8) != 0);
         if (c_d_c7 != null) {
            c_d_c7.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 4 && (j & 3) == 3) {
         C_d_C c_d_c6 = this.m2178(8, (j & 8) != 0);
         if (c_d_c6 != null) {
            c_d_c6.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 5 && (j & 3) == 3) {
         C_d_C c_d_c5 = this.m2178(7, (j & 8) != 0);
         if (c_d_c5 != null) {
            c_d_c5.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if ((c0 == '\n' || c0 == '\r') && (j & 3) == 3) {
         C_d_C c_d_c4 = this.m2178(10, (j & 8) != 0);
         if (c_d_c4 != null) {
            c_d_c4.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 14 && (j & 3) == 3) {
         C_d_C c_d_c3 = this.m2178(1, (j & 8) != 0);
         if (c_d_c3 != null) {
            c_d_c3.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 15 && (j & 3) == 3) {
         C_d_C c_d_c2 = this.m2178(4, (j & 8) != 0);
         if (c_d_c2 != null) {
            c_d_c2.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 20 && (j & 3) == 3) {
         C_d_C c_d_c1 = this.m2178(0, (j & 8) != 0);
         if (c_d_c1 != null) {
            c_d_c1.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 21 && (j & 3) == 3) {
         C_d_C c_d_c = this.m2178(6, (j & 8) != 0);
         if (c_d_c != null) {
            c_d_c.f1045.f1438.requestFocus();
         }

         keyevent.consume();
      } else if ((c0 == '\n' || c0 == '\r') && (j & 3) == 2) {
         if (this.isEditable()) {
            int k = this.getSelectionStart();
            int l = this.getSelectionEnd();
            if (k == l) {
               k = this.getCaretPosition();
            } else {
               this.m1795("", k, l);
            }

            this.m1796("\n", k);
         }

         keyevent.consume();
      } else if (c0 == '\n') {
         this.f1444.m2173((j & 8) != 0);
         keyevent.consume();
      } else if (c0 == 1) {
         this.selectAll();
         keyevent.consume();
      }

      this.m1798(this.getCaretPosition());
      this.f1444.validate();
   }

   C_d_C m2178(int i, boolean flag) {
      C_d_C c_d_c = this.f1444.f1437.m1688();
      int j = c_d_c == null ? -1 : c_d_c.m1687(this.f1444.f1437);
      int k = c_d_c == null ? 2 : c_d_c.f1050[j];
      int l = connOutTypes[i];
      if (k != 2 && l != 2 && l != k) {
         Toolkit.getDefaultToolkit().beep();
         return null;
      } else {
         if (flag) {
            EditableTextPane.m2024(this.getText());
         }

         return this.f1444.f1437.m1690(i, true);
      }
   }

   @Override
   public void mouseReleased(MouseEvent mouseevent) {
   }

   @Override
   public void mouseClicked(MouseEvent mouseevent) {
   }

   @Override
   public void mouseEntered(MouseEvent mouseevent) {
   }

   @Override
   public void mouseExited(MouseEvent mouseevent) {
   }

   @Override
   public void mousePressed(MouseEvent mouseevent) {
      this.f1445 = false;
      if (SwingUtilities.isRightMouseButton(mouseevent)) {
         this.f1444.m2174((mouseevent.getModifiers() & 2) != 0, mouseevent.getX());
         mouseevent.consume();
      }
   }

   @Override
   public void focusGained(FocusEvent focusevent) {
      this.f1444.m2170(focusevent);
   }

   @Override
   public void focusLost(FocusEvent focusevent) {
      this.f1445 = true;
      this.f1444.m2171(focusevent);
   }
}
