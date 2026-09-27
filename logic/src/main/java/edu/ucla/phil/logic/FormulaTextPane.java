package edu.ucla.phil.logic;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class FormulaTextPane extends EditableTextPane implements LogicConstants, MouseListener {
   static final int CTRL_SHIFT_MASK = 3;
   static String[] displaySymbols = LogicProgram.symbols;
   static final String OPEN_BRACKETS = "({[";
   static final String CLOSE_BRACKETS = ")}]";

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
         this.showKeypad();
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

   void insertAtCaret(String s) {
      this.insertAtCaret(s, false);
   }

   void insertAtCaret(String s, boolean flag) {
      if (flag || this.isEditable()) {
         int i = this.getSelectionStart();
         int j = this.getSelectionEnd();
         this.replaceRange(s, i, j);
         this.setCaretPosition(i + s.length());
      }
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         char c0 = keyevent.getKeyChar();
         int i = keyevent.getModifiers();
         String s;
         if ((s = shortcutSymbol(c0, i)) != null) {
            this.insertAtCaret(s);
            keyevent.consume();
         } else if ((i & 3) == 2 && c0 == 5) {
            int[] aint1 = new int[]{this.getSelectionStart(), this.getSelectionEnd()};
            if (aint1[0] >= aint1[1]) {
               aint1[0] = aint1[1] = this.getCaretPosition();
            }

            String s2 = LogicProgram.translateSymbols(this.getText(), displaySymbols, maggie, aint1);
            aint1 = new FormulaParseNode(s2).findNodeContaining(aint1[0], aint1[1], true).getTextRange();
            this.setText(LogicProgram.translateSymbols(s2, maggie, displaySymbols, aint1));
            this.select(aint1[0], aint1[1]);
            keyevent.consume();
         } else if ((i & 3) == 2 && c0 == 2) {
            int[] aint = new int[]{this.getSelectionStart(), this.getSelectionEnd()};
            if (aint[0] >= aint[1]) {
               aint[0] = aint[1] = this.getCaretPosition();
            }

            String s1 = this.getText();
            if (this.selectEnclosingBrackets(this.getText(), aint)) {
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

   boolean selectEnclosingBrackets(String s, int[] aint) {
      int k = s.length();
      int j;
      int i = j = aint[0];

      while ((i = this.findOpenBracket(s, i)) >= 0 && (j = this.findCloseBracket(s, j)) >= 0) {
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

   int findOpenBracket(String s, int i) {
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

   int findCloseBracket(String s, int i) {
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

   static String shortcutSymbol(char c0, int i) {
      if ((i & 3) == 3) {
         if (c0 == 1) {
            return LogicProgram.translateSymbols("&", maggie, displaySymbols);
         }

         if (c0 == 2) {
            return LogicProgram.translateSymbols("<->", maggie, displaySymbols);
         }

         if (c0 == 3) {
            return LogicProgram.translateSymbols("->", maggie, displaySymbols);
         }

         if (c0 == 4) {
            return LogicProgram.translateSymbols("%", maggie, displaySymbols);
         }

         if (c0 == 5) {
            return LogicProgram.translateSymbols("!", maggie, displaySymbols);
         }

         if (c0 == '\t') {
            return LogicProgram.translateSymbols("<>", maggie, displaySymbols);
         }

         if (c0 == 14) {
            return LogicProgram.translateSymbols("~", maggie, displaySymbols);
         }

         if (c0 == 15) {
            return LogicProgram.translateSymbols("|", maggie, displaySymbols);
         }

         if (c0 == 20) {
            return LogicProgram.translateSymbols(".:", maggie, displaySymbols);
         }

         if (c0 == 21) {
            return LogicProgram.translateSymbols("@", maggie, displaySymbols);
         }

         if (c0 == '\r' || c0 == '\n') {
            return LogicProgram.translateSymbols("[m]", maggie, displaySymbols);
         }
      } else if ((i & 8) != 0) {
         int j = "123456789".indexOf(c0);
         if (j == -1) {
            j = "¡™£¢∞§¶•ª".indexOf(c0);
         }

         if (j != -1) {
            return SchematicLetter.placeholder(j);
         }
      } else if (i == 0) {
         int k = "\u0000™\u0000\u0000∞\u0000\u0000•\u0000".indexOf(c0);
         if (k != -1) {
            return SchematicLetter.placeholder(k);
         }
      }

      return null;
   }

   void showKeypad() {
   }
}
