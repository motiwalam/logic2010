package edu.ucla.phil.logic;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

class KeypadGrid extends JPanel implements LogicConstants, ModuleComponentMarker, KeyListener {
   KeypadTarget target;
   int width;
   int height;
   static String[] SYMBOLS = LogicProgram.symbols;

   KeypadGrid(KeypadTarget keypadtarget, String[][] astring, boolean flag, boolean flag1) {
      this.target = keypadtarget;
      this.height = astring.length;
      this.width = 0;

      for (int i = 0; i < this.height; i++) {
         if (astring[i] != null && astring[i].length > this.width) {
            this.width = astring[i].length;
         }
      }

      this.setLayout(new FlexGridLayout(this.width, this.height, flag, flag1));
      this.setFocusable(true);
      this.addKeyListener(this);

      for (int k = 0; k < this.height; k++) {
         String[] astring1 = astring[k];
         if (astring1 != null) {
            for (int j = 0; j < astring1.length; j++) {
               if (astring1[j] != null) {
                  this.add(new KeypadKey(this, astring1[j]), new Point(j, k));
               }
            }
         }
      }
   }

   KeypadGrid(KeypadTarget keypadtarget, String[][] astring) {
      this(keypadtarget, astring, false, false);
   }

   void setToolTips(String[][] astring) {
      FlexGridLayout flexgridlayout = (FlexGridLayout)this.getLayout();

      for (int i = 0; i < astring.length; i++) {
         String[] astring1 = astring[i];
         if (astring1 != null) {
            for (int j = 0; j < astring1.length; j++) {
               String s = astring1[j];
               if (s != null) {
                  KeypadKey keypadkey = (KeypadKey)flexgridlayout.getCellComponent(new Point(j, i));
                  if (keypadkey != null) {
                     keypadkey.setHelpText(s);
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
            LogicProgram.forwardKeyEvent(this, keyevent);
         } else if (i == 37 && (j & 8) == 0) {
            moveCaretLeft(this.target);
            keyevent.consume();
         } else if (i == 39 && (j & 8) == 0) {
            moveCaretRight(this.target);
            keyevent.consume();
         } else if (i == 36) {
            moveCaretHome(this.target);
            keyevent.consume();
         } else if (i == 35) {
            moveCaretEnd(this.target);
            keyevent.consume();
         } else if (i == 8) {
            deleteBackward(this.target);
            keyevent.consume();
         } else if (i == 127) {
            if ((j & 8) == 0) {
               deleteForward(this.target);
            } else {
               this.target.translateKey("delete line");
            }

            keyevent.consume();
         } else if (i == 38 && (j & 8) == 0) {
            this.target.translateKey("up");
            keyevent.consume();
         } else if (i == 40 && (j & 8) == 0) {
            this.target.translateKey("down");
            keyevent.consume();
         } else if (i == 10) {
            this.target.translateKey("enter");
            keyevent.consume();
         } else if (i == 9) {
            this.target.translateKey("tab");
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
            selectAllText(this.target);
            keyevent.consume();
         } else if (c0 == 24 && (i & 1) != 0) {
            this.target.translateKey("Box/Unbox");
            keyevent.consume();
         } else {
            String s;
            if ((s = FormulaTextPane.shortcutSymbol(c0, i)) != null) {
               this.insertText(s);
               keyevent.consume();
            } else if (!Character.isISOControl(c0)) {
               this.insertText(String.valueOf(c0));
               keyevent.consume();
            } else {
               LogicProgram.forwardKeyEvent(this, keyevent);
            }
         }
      }
   }

   @Override
   public void keyReleased(KeyEvent keyevent) {
   }

   static void moveCaretHome(KeypadTarget keypadtarget) {
      keypadtarget.selectInTarget(0, 0);
      keypadtarget.setTargetCaret(0);
   }

   static void moveCaretEnd(KeypadTarget keypadtarget) {
      int i = keypadtarget.getTargetText().length();
      keypadtarget.selectInTarget(i, i);
      keypadtarget.setTargetCaret(i);
   }

   static void moveCaretRight(KeypadTarget keypadtarget) {
      int i = keypadtarget.getTargetSelectionStart();
      int j = keypadtarget.getTargetSelectionEnd();
      int k = keypadtarget.getTargetCaret();
      int l = keypadtarget.getTargetText().length();
      if (i != j) {
         k = j;
      } else if (k < l) {
         k++;
      }

      keypadtarget.selectInTarget(k, k);
      keypadtarget.setTargetCaret(k);
   }

   static void moveCaretLeft(KeypadTarget keypadtarget) {
      int i = keypadtarget.getTargetSelectionStart();
      int j = keypadtarget.getTargetSelectionEnd();
      int k = keypadtarget.getTargetCaret();
      if (i != j) {
         k = i;
      } else if (k > 0) {
         k--;
      }

      keypadtarget.selectInTarget(k, k);
      keypadtarget.setTargetCaret(k);
   }

   static void selectAllText(KeypadTarget keypadtarget) {
      int i = keypadtarget.getTargetText().length();
      keypadtarget.selectInTarget(0, i);
      keypadtarget.setTargetCaret(i);
   }

   void insertText(String s) {
      insertText(this.target, s);
   }

   static void insertText(KeypadTarget keypadtarget, String s) {
      if (s != null && !keypadtarget.isTargetReadOnly()) {
         int i = keypadtarget.getTargetSelectionStart();
         int j = keypadtarget.getTargetSelectionEnd() - i;
         if (j > 0) {
            keypadtarget.deleteFromTarget(i, j);
         } else {
            i = keypadtarget.getTargetCaret();
         }

         keypadtarget.insertIntoTarget(s, i);
         int k;
         keypadtarget.setTargetCaret(k = i + s.length());
         keypadtarget.selectInTarget(k, k);
         keypadtarget.invalidate();
      }
   }

   static void deleteBackward(KeypadTarget keypadtarget) {
      if (!keypadtarget.isTargetReadOnly()) {
         int i = keypadtarget.getTargetSelectionStart();
         int j = keypadtarget.getTargetSelectionEnd() - i;
         if (j == 0 && (i = keypadtarget.getTargetCaret() - 1) >= 0) {
            j = 1;
         }

         if (j > 0) {
            keypadtarget.deleteFromTarget(i, j);
            keypadtarget.setTargetCaret(i);
            keypadtarget.selectInTarget(i, i);
            keypadtarget.invalidate();
         }
      }
   }

   static void deleteForward(KeypadTarget keypadtarget) {
      if (!keypadtarget.isTargetReadOnly()) {
         int i = keypadtarget.getTargetSelectionStart();
         int j = keypadtarget.getTargetSelectionEnd() - i;
         int k = keypadtarget.getTargetText().length();
         if (j == 0 && (i = keypadtarget.getTargetCaret()) < k) {
            j = 1;
         }

         if (j > 0) {
            keypadtarget.deleteFromTarget(i, j);
            keypadtarget.setTargetCaret(i);
            keypadtarget.selectInTarget(i, i);
            keypadtarget.invalidate();
         }
      }
   }
}
