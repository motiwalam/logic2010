package edu.ucla.phil.logic;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class FormulaTextPane extends EditableTextPane implements LogicConstants, MouseListener {
   static final int f1364 = 3;
   static String[] f1365 = LogicProgram.symbols;
   static final String f1366 = "({[";
   static final String f1367 = ")}]";

   FormulaTextPane(boolean flag) {
      super(flag);
      this.addMouseListener(this);
   }

   FormulaTextPane() {
      this.addMouseListener(this);
   }

   FormulaTextPane(String s, boolean flag) {
      super(s, flag);
      this.addMouseListener(this);
   }

   FormulaTextPane(String s) {
      super(s);
      this.addMouseListener(this);
   }

   FormulaTextPane(String s, int i) {
      super(s, i);
      this.addMouseListener(this);
   }

   FormulaTextPane(String s, int i, int j) {
      super(s, i, j);
      this.addMouseListener(this);
   }

   FormulaTextPane(String s, int i, boolean flag) {
      super(s, i, flag);
      this.addMouseListener(this);
   }

   @Override
   public void mousePressed(MouseEvent mouseevent) {
      if ((mouseevent.getModifiers() & 4) != 0) {
         this.m714();
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

   void m1842(String s) {
      this.m2077(s, false);
   }

   void m2077(String s, boolean flag) {
      if (flag || this.isEditable()) {
         int i = this.getSelectionStart();
         int j = this.getSelectionEnd();
         this.m1795(s, i, j);
         this.setCaretPosition(i + s.length());
      }
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         char c0 = keyevent.getKeyChar();
         int i = keyevent.getModifiers();
         String s;
         if ((s = m2081(c0, i)) != null) {
            this.m1842(s);
            keyevent.consume();
         } else if ((i & 3) == 2 && c0 == 5) {
            int[] aint1 = new int[]{this.getSelectionStart(), this.getSelectionEnd()};
            if (aint1[0] >= aint1[1]) {
               aint1[0] = aint1[1] = this.getCaretPosition();
            }

            String s2 = LogicProgram.m996(this.getText(), f1365, maggie, aint1);
            aint1 = new C_DD(s2).m481(aint1[0], aint1[1], true).m459();
            this.setText(LogicProgram.m996(s2, maggie, f1365, aint1));
            this.select(aint1[0], aint1[1]);
            keyevent.consume();
         } else if ((i & 3) == 2 && c0 == 2) {
            int[] aint = new int[]{this.getSelectionStart(), this.getSelectionEnd()};
            if (aint[0] >= aint[1]) {
               aint[0] = aint[1] = this.getCaretPosition();
            }

            String s1 = this.getText();
            if (this.m2078(this.getText(), aint)) {
               this.select(aint[0], aint[1]);
            } else {
               Toolkit.getDefaultToolkit().beep();
            }

            keyevent.consume();
         } else {
            super.keyTyped(keyevent);
         }
      }
   }

   boolean m2078(String s, int[] aint) {
      int k = s.length();
      int j;
      int i = j = aint[0];

      while ((i = this.m2079(s, i)) >= 0 && (j = this.m2080(s, j)) >= 0) {
         if ("({[".indexOf(s.charAt(i)) != ")}]".indexOf(s.charAt(j - 1))) {
            return false;
         }

         if (j > aint[1]) {
            aint[0] = i;
            aint[1] = j;
            return true;
         }
      }

      return false;
   }

   int m2079(String s, int i) {
      String s1 = "";
      int j = s.length();
      if (j == 0) {
         return -1;
      } else {
         while (--i >= 0) {
            char c0 = s.charAt(i);
            int k;
            if ((k = ")}]".indexOf(c0)) != -1) {
               s1 = "({[".charAt(k) + s1;
            } else if ("({[".indexOf(c0) != -1) {
               if (s1.length() == 0) {
                  return i;
               }

               if (s1.charAt(0) != c0) {
                  return -1;
               }

               if (s1.charAt(0) == c0) {
                  s1 = s1.substring(1);
               }
            }
         }

         return -1;
      }
   }

   int m2080(String s, int i) {
      String s1 = "";
      int j = s.length();
      if (j == 0) {
         return -1;
      } else {
         while (++i <= j) {
            char c0 = s.charAt(i - 1);
            int k;
            if ((k = "({[".indexOf(c0)) != -1) {
               s1 = ")}]".charAt(k) + s1;
            } else if (")}]".indexOf(c0) != -1) {
               if (s1.length() == 0) {
                  return i;
               }

               if (s1.charAt(0) != c0) {
                  return -1;
               }

               if (s1.charAt(0) == c0) {
                  s1 = s1.substring(1);
               }
            }
         }

         return -1;
      }
   }

   @Override
   public void keyPressed(KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         super.keyPressed(keyevent);
      }
   }

   @Override
   public void keyReleased(KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         super.keyReleased(keyevent);
      }
   }

   static String m2081(char c0, int i) {
      if ((i & 3) == 3) {
         if (c0 == 1) {
            return LogicProgram.m995("&", maggie, f1365);
         }

         if (c0 == 2) {
            return LogicProgram.m995("<->", maggie, f1365);
         }

         if (c0 == 3) {
            return LogicProgram.m995("->", maggie, f1365);
         }

         if (c0 == 4) {
            return LogicProgram.m995("%", maggie, f1365);
         }

         if (c0 == 5) {
            return LogicProgram.m995("!", maggie, f1365);
         }

         if (c0 == '\t') {
            return LogicProgram.m995("<>", maggie, f1365);
         }

         if (c0 == 14) {
            return LogicProgram.m995("~", maggie, f1365);
         }

         if (c0 == 15) {
            return LogicProgram.m995("|", maggie, f1365);
         }

         if (c0 == 20) {
            return LogicProgram.m995(".:", maggie, f1365);
         }

         if (c0 == 21) {
            return LogicProgram.m995("@", maggie, f1365);
         }

         if (c0 == '\r' || c0 == '\n') {
            return LogicProgram.m995("[m]", maggie, f1365);
         }
      } else if ((i & 8) != 0) {
         int j = "123456789".indexOf(c0);
         if (j == -1) {
            j = "¡™£¢∞§¶•ª".indexOf(c0);
         }

         if (j != -1) {
            return SchematicLetter.m1853(j);
         }
      } else if (i == 0) {
         int k = "\u0000™\u0000\u0000∞\u0000\u0000•\u0000".indexOf(c0);
         if (k != -1) {
            return SchematicLetter.m1853(k);
         }
      }

      return null;
   }

   void m714() {
   }
}
