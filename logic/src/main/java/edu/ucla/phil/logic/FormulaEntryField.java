package edu.ucla.phil.logic;

import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.border.EmptyBorder;

class FormulaEntryField extends FormulaTextPane {
   Frame ownerFrame;
   MessageDialog ownerDialog;

   FormulaEntryField(Frame frame) {
      this("", -1, false, frame);
   }

   FormulaEntryField(String s, Frame frame) {
      this(s, -1, false, frame);
   }

   FormulaEntryField(boolean flag) {
      this("", -1, flag, null);
   }

   FormulaEntryField(String s, int i, Frame frame) {
      this(s, i, false, frame);
   }

   FormulaEntryField(String s, int i, boolean flag, Frame frame) {
      super(s, i, flag);
      this.setBorder(new EmptyBorder(0, 0, 0, 0));
      this.ownerFrame = frame;
      this.ownerDialog = null;
   }

   @Override
   public String getName() {
      String s = super.getName();
      return s == null ? "" : s;
   }

   void setOwnerFrame(Frame frame) {
      this.ownerFrame = frame;
   }

   void setOwnerDialog(MessageDialog messagedialog) {
      this.ownerDialog = messagedialog;
   }

   @Override
   void showKeypad() {
      Rectangle rectangle = LogicProgram.boundsIn(this, null);
      SymbolKeypadDialog symbolkeypaddialog;
      if (this.ownerDialog == null) {
         symbolkeypaddialog = new SymbolKeypadDialog(this.ownerFrame, this.getName(), false, this);
      } else {
         symbolkeypaddialog = new SymbolKeypadDialog(this.ownerDialog, this.getName(), false, this);
      }

      symbolkeypaddialog.ownerDialog = this.ownerDialog;
      String[] astring = new String[]{"\\l->", "\\l~", "\\l&", "\\l|", "\\l<->", "\\l@", "\\l!", "=", "\\l<>", "\\l%"};
      String[] astring1 = LogicProgram.splitChars(LogicProgram.sentenceLetters);
      String[] astring2 = new String[]{"(", ")", ".", "\\l.:"};
      String[] astring3 = LogicProgram.splitChars(LogicProgram.operationLetters);
      String[] astring4 = LogicProgram.splitChars(LogicProgram.predicateLetters);
      String[] astring5 = LogicProgram.splitChars("xyzuvw");
      String[] astring6 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
      String[][] astring7 = new String[][]{astring, astring1, astring2, astring3, astring4, astring5, astring6};
      KeypadGrid keypadgrid;
      symbolkeypaddialog.add(keypadgrid = new KeypadGrid(symbolkeypaddialog, astring7), "West");
      String[] astring8 = new String[]{
         "Ctrl+Shift+C", "Ctrl+Shift+N", "Ctrl+Shift+A", "Ctrl+Shift+O", "Ctrl+Shift+B", "Ctrl+Shift+U", "Ctrl+Shift+E", null, "Ctrl+Shift+I", "Ctrl+Shift+D"
      };
      String[] astring9 = new String[]{null, null, null, "Ctrl+Shift+T"};
      String[][] astring10 = new String[][]{astring8, null, astring9};
      keypadgrid.setToolTips(astring10);
      keypadgrid.setEnabled(true);
      keypadgrid.requestFocus();
      astring = new String[]{"space", "backspace", "copy", "paste"};
      String[][] astring11 = new String[][]{astring};
      symbolkeypaddialog.add(keypadgrid = new KeypadGrid(symbolkeypaddialog, astring11, true, false), "West");
      astring2 = new String[]{null, null, "Ctrl+C", "Ctrl+V"};
      String[][] astring12 = new String[][]{astring2};
      keypadgrid.setToolTips(astring12);
      keypadgrid.setEnabled(true);
      keypadgrid.requestFocus();
      symbolkeypaddialog.invalidate();
      symbolkeypaddialog.setEnabled(true);
      symbolkeypaddialog.requestFocus();
      symbolkeypaddialog.setAnchor(new Point(rectangle.x, rectangle.y + rectangle.height));
      symbolkeypaddialog.setResizable(false);
      symbolkeypaddialog.showLater();
   }

   void showCaretOnDialogFocus(MessageDialog messagedialog) {
      if (messagedialog != null) {
         messagedialog.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent windowevent) {
               FormulaEntryField.this.getCaret().setVisible(true);
            }

            @Override
            public void windowLostFocus(WindowEvent windowevent) {
            }
         });
      }
   }
}
