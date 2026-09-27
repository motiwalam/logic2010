package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import javax.swing.SwingUtilities;

class DerivationLineEditor extends FormulaTextPane implements DerivationConstants, FocusListener {
   int savedSelectionStart;
   int savedSelectionEnd;
   int savedCaretPosition;
   DerivationLine line;
   boolean ignoreFocusLoss;
   int unusedCounter = 0;
   static boolean UNUSED_FLAG = false;

   DerivationLineEditor(DerivationLine derivationline) {
      this.line = derivationline;
      this.ignoreFocusLoss = false;
      this.savedSelectionStart = this.savedSelectionEnd = this.savedCaretPosition = 0;
      this.setCaretPosition(0);
      this.setForeground(derivationline.box.module.colors[0]);
      this.setBackground(derivationline.box.module.colors[2]);
      this.setWrapLines(true);
      this.setWrapWords(true);
      this.addFocusListener(this);
   }

   @Override
   public void setForeground(Color color) {
      super.setForeground(color);
      this.setTextForeground(this.getForeground());
   }

   @Override
   public void setBackground(Color color) {
      super.setBackground(color);
      this.setTextBackground(this.getBackground());
   }

   boolean handleKeyTyped(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      int i = keyevent.getModifiers();
      if (c0 == 17 && (i & 1) != 0) {
         return true;
      } else if (c0 == 19 && (i & 1) != 0) {
         DerivationNode derivationnode1 = this.line.toggleShow();
         if (derivationnode1 != null) {
            derivationnode1.focusEditor(false);
         }

         return true;
      } else if (c0 == 24 && (i & 1) != 0) {
         this.line.toggleBoxAndCancel();
         if (this.line.box.cancelLine == this.line) {
            this.line.focusEditor(true);
         }

         return true;
      } else if (c0 == '\t' && (i & 2) == 0) {
         this.line.focusEditor(this == this.line.formulaEditor);
         return true;
      } else if (c0 != '\n' && c0 != '\r') {
         if (c0 == 127 && (i & 8) != 0) {
            if ((i & 1) != 0 && this.line == this.line.box.showLine) {
               this.line.deleteFollowingLines();
            }

            if (this.line.box.module.problem.showLine != this.line) {
               DerivationNode derivationnode = this.line.getNextNode(false);
               if (derivationnode == null) {
                  derivationnode = this.line.getPreviousNode(true);
               }

               this.line.deleteNode(false);
               derivationnode.focusEditor(this.line.annotationEditor == this);
            }

            return true;
         } else {
            return false;
         }
      } else {
         if (this == this.line.formulaEditor) {
            DerivationBox derivationbox = this.line.applyShowPrefix();
         }

         DerivationLine derivationline = null;
         boolean flag = this.line.annotationEditor == this;
         int j = i & 11;
         if (this.line.box.module.commandMode) {
            if (this.line.annotationEditor != null && (j == 1 || j == 2)) {
               this.line.clearMessage(1);
               this.line.setFormulaText("");
               this.line.formula = null;
               this.line.syntaxOk = true;
            }

            if (j != 3 && j != 8) {
               this.line.commitEdit(j != 0 || this.line.formulaEditor != null);
            }

            derivationline = j == 1 ? this.line : this.line.insertLineAfter();
         } else {
            derivationline = this.line.insertLineAfter();
         }

         if (derivationline != null) {
            if (derivationline != this.line && this.line.box.showLine == this.line) {
               derivationline.focusEditor(this.line.box.module.commandMode);
            } else {
               derivationline.focusEditor(flag);
            }
         }

         return true;
      }
   }

   boolean handleKeyPressed(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      int i = keyevent.getKeyCode();
      int j = keyevent.getModifiers();
      if (c0 == '\t' && (j & 2) == 0) {
         return true;
      } else if (i == 38) {
         if ((j & 8) == 0) {
            DerivationNode derivationnode1 = this.line.getPreviousNode(true);
            if (derivationnode1 != null) {
               derivationnode1.focusEditor(this.line.annotationEditor == this);
            }
         } else if (this.line.box.showLine == this.line && this.line.box.module.problem.showLine != this.line && this.line.box.getContentCount() > 1) {
            this.line.box.setExpanded(false);
            this.line.box.module.setWidths(true);
         }

         return true;
      } else if (i == 40) {
         if ((j & 8) == 0) {
            DerivationNode derivationnode = this.line.getNextNode(true);
            if (derivationnode != null) {
               derivationnode.focusEditor(this.line.annotationEditor == this);
            }
         } else if (this.line.box.showLine == this.line) {
            this.line.box.setExpanded(true);
            this.line.box.module.setWidths(true);
         }

         return true;
      } else if (i == 39 && (j & 8) != 0) {
         this.line.indentIntoOpenBox();
         return true;
      } else if (i == 37 && (j & 8) != 0) {
         this.line.outdentFollowingLines();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void focusGained(FocusEvent focusevent) {
      if (this.getGraphics() == null) {
         this.line.focusEditor(false);
      } else {
         if (this.line.box.module.focus != this) {
            if (this == this.line.annotationEditor) {
               this.line.refreshReferenceNumbers();
               this.line.clearReferences();
            }

            this.restoreSelection();
            this.line.box.module.focus = this;
            this.line.box.module.lastFocus = this;
            this.scrollToPosition(this.getCaretPosition());
         }

         this.setForeground(this.line.box.module.colors[1]);
         this.setBackground(this.line.box.module.colors[0]);
         DerivationBox derivationbox = this.line.getEnclosingBox();
         if (derivationbox != null) {
            derivationbox.highlightShowLabel(true);
         }

         if (this.line.box.module.requestAid) {
            SwingUtilities.invokeLater(new Runnable() {
               @Override
               public void run() {
                  DerivationLineEditor.this.showKeypad();
               }
            });
            this.line.box.module.requestAid = false;
         }
      }
   }

   @Override
   public void focusLost(FocusEvent focusevent) {
      this.setForeground(this.line.box.module.colors[0]);
      this.setBackground(this.line.box.module.colors[2]);
      DerivationBox derivationbox = this.line.getEnclosingBox();
      if (derivationbox != null) {
         derivationbox.highlightShowLabel(false);
      }

      if (this.line.box.module.focus == this) {
         if (this.ignoreFocusLoss) {
            this.ignoreFocusLoss = false;
         } else if (this == this.line.annotationEditor) {
            this.line.parseReferences();
         } else if (this == this.line.formulaEditor) {
            this.line.parseFormula();
            this.line.checkRedundantShow();
         }

         this.saveSelection(true);
         this.line.box.module.focus = null;
      }
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      int i = awtevent.getID();
      if (i == 400) {
         if (this.handleKeyTyped((KeyEvent)awtevent)) {
            this.line.box.module.invalrepaint();
         } else {
            super.processEvent(awtevent);
            LogicProgram.forwardKeyEvent(this, (KeyEvent)awtevent);
         }
      } else if (i == 401) {
         if (!this.handleKeyPressed((KeyEvent)awtevent)) {
            super.processEvent(awtevent);
            LogicProgram.forwardKeyEvent(this, (KeyEvent)awtevent);
         }
      } else {
         super.processEvent(awtevent);
      }
   }

   void saveSelection(boolean flag) {
      this.savedSelectionStart = this.getSelectionStart();
      this.savedSelectionEnd = this.getSelectionEnd();
      this.savedCaretPosition = this.getCaretPosition();
      if (flag) {
         this.select(0, 0);
      }
   }

   void restoreSelection() {
      this.select(this.savedSelectionStart, this.savedSelectionEnd);
   }

   void adjustSavedSelection(int i, int j, int k) {
      if (this.savedSelectionStart >= i + j) {
         this.savedSelectionStart += k - j;
      } else if (this.savedSelectionStart > i + k) {
         this.savedSelectionStart = i + k;
      }

      if (this.savedSelectionEnd >= i + j) {
         this.savedSelectionEnd += k - j;
      } else if (this.savedSelectionEnd > i + k) {
         this.savedSelectionEnd = i + k;
      }

      if (this.savedCaretPosition >= i + j) {
         this.savedCaretPosition += k - j;
      } else if (this.savedCaretPosition > i + k) {
         this.savedCaretPosition = i + k;
      }
   }

   @Override
   void showKeypad() {
      Rectangle rectangle = LogicProgram.boundsRelativeTo(this, null);
      DerivationKeypad derivationkeypad = new DerivationKeypad(this);
      if (this == this.line.formulaEditor) {
         String[] astring = new String[]{"\\l->", "\\l~", "\\l&", "\\l|", "\\l<->", "\\l@", "\\l!", "\\l=", "\\l<>", "\\l%"};
         String[] astring1 = LogicProgram.splitChars(LogicProgram.sentenceLetters);
         String[] astring2 = LogicProgram.splitChars(LogicProgram.operationLetters);
         String[] astring3 = LogicProgram.splitChars(LogicProgram.predicateLetters);
         String[] astring4 = LogicProgram.splitChars("xyzuvw");
         String[] astring5 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
         String[] astring6 = new String[]{"(", ")", ".", "\\l.:"};
         String[][] astring7 = new String[][]{astring, astring1, astring6, astring2, astring3, astring4, astring5};
         KeypadGrid keypadgrid;
         derivationkeypad.add(keypadgrid = new KeypadGrid(derivationkeypad, astring7));
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
         keypadgrid.setToolTips(astring10);
         astring = new String[]{"space", "up", "Show/Unshow", "enter"};
         astring1 = new String[]{"tab", "down", "backspace", "delete line"};
         astring2 = new String[]{"copy", "paste"};
         String[][] astring16 = new String[][]{astring, astring1, astring2};
         derivationkeypad.add(keypadgrid = new KeypadGrid(derivationkeypad, astring16, true, false));
         astring4 = new String[]{null, null, "Ctrl+Shift+S"};
         astring5 = new String[]{null, null, null, "Alt+Delete"};
         astring6 = new String[]{"Ctrl+C", "Ctrl+V"};
         astring7 = new String[][]{astring4, astring5, astring6};
         keypadgrid.setToolTips(astring7);
      } else if (this == this.line.annotationEditor) {
         Integer integer = this.line.box.module.chapter;
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

         derivationkeypad.add(new KeypadGrid(derivationkeypad, astring21, true, false));
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

         KeypadGrid keypadgrid1;
         derivationkeypad.add(keypadgrid1 = new KeypadGrid(derivationkeypad, astring11, true, false));
         keypadgrid1.setToolTips(astring12);
         astring13 = new String[]{"space", "up", "Box/Unbox", "enter"};
         astring14 = new String[]{"tab", "down", "backspace", "delete line"};
         astring17 = new String[]{"copy", "paste"};
         String[][] astring19 = new String[][]{astring13, astring14, astring17};
         derivationkeypad.add(keypadgrid1 = new KeypadGrid(derivationkeypad, astring19, true, false));
         astring20 = new String[]{null, null, "Ctrl+Shift+X"};
         String[] astring23 = new String[]{null, null, null, "Alt+Delete"};
         astring24 = new String[]{"Ctrl+C", "Ctrl+V"};
         String[][] astring26 = new String[][]{astring20, astring23, astring24};
         keypadgrid1.setToolTips(astring26);
         astring13 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
         String[][] astring15 = new String[][]{astring13};
         derivationkeypad.add(new KeypadGrid(derivationkeypad, astring15));
      }

      derivationkeypad.setAnchor(new Point(rectangle.x, rectangle.y + rectangle.height));
      derivationkeypad.setResizable(false);
      this.ignoreFocusLoss = true;
      derivationkeypad.showLater();
   }
}
