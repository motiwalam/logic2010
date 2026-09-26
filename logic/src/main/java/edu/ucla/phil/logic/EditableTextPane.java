package edu.ucla.phil.logic;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.text.StyledDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

class EditableTextPane extends StyledTextPane implements KeyListener, C_LC {
   int f1329;
   int f1330;
   boolean f1331;
   boolean f1332;
   UndoManager f1333;

   EditableTextPane(String s, int i, int j, boolean flag) {
      super(s);
      this.setText(s);
      this.f1329 = i < 0 ? 0 : i;
      this.f1330 = j < 0 ? 2147483647 : j;
      this.f1331 = flag;
      this.f1332 = false;
      this.setFocusable(true);
      this.addKeyListener(this);
      this.m1787(true);
      this.m1789(true);
      this.setMargin(new Insets(0, 1, 0, 1));
      this.getDocument().addUndoableEditListener(this.f1333 = new UndoManager());
   }

   EditableTextPane(StyledDocument styleddocument, int i, int j, boolean flag) {
      this("", i, j, flag);
      this.setStyledDocument(styleddocument);
   }

   EditableTextPane(String s, int i, int j) {
      this(s, i, j, false);
   }

   EditableTextPane(String s, int i, boolean flag) {
      this(s, i, i, flag);
   }

   EditableTextPane(String s, int i) {
      this(s, i, i, false);
   }

   EditableTextPane(int i, boolean flag) {
      this("", i, i, flag);
   }

   EditableTextPane(int i) {
      this("", i, i, false);
   }

   EditableTextPane(String s, boolean flag) {
      this(s, 0, 2147483647, flag);
   }

   EditableTextPane(String s) {
      this(s, 0, 2147483647, false);
   }

   EditableTextPane(boolean flag) {
      this("", 0, 2147483647, flag);
   }

   EditableTextPane() {
      this("", 0, 2147483647, false);
   }

   @Override
   public void setEditable(boolean flag) {
      super.setEditable(flag);
      if (!flag) {
         this.setCursor(new Cursor(2));
      }
   }

   public boolean m2019() {
      return this.f1332;
   }

   public void m2020(boolean flag) {
      this.f1332 = flag;
   }

   @Override
   public Dimension getMinimumSize() {
      return this.f1332 ? this.getPreferredSize() : super.getMinimumSize();
   }

   @Override
   public Dimension getMaximumSize() {
      Dimension dimension = super.getMaximumSize();
      if (dimension.width > this.f1330) {
         dimension.width = this.f1330;
      }

      return dimension;
   }

   @Override
   public Dimension getPreferredSize() {
      Dimension dimension = super.getPreferredSize();
      if (dimension.width < this.f1329) {
         dimension.width = this.f1329;
      } else if (dimension.width > this.f1330) {
         dimension.width = this.f1330;
      }

      return dimension;
   }

   public Dimension m2021(Dimension dimension) {
      Dimension dimension1 = super.getPreferredSize();
      if (dimension1.width < dimension.width) {
         dimension1.width = dimension.width;
      }

      if (dimension1.width > this.f1330) {
         dimension1.width = this.f1330;
      }

      return dimension1;
   }

   void m2022(int i) {
      this.f1329 = i;
   }

   void m2023(int i) {
      this.f1329 = i;
      this.f1330 = i;
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         char c0 = keyevent.getKeyChar();
         int i = keyevent.getModifiers();
         if (c0 == 1 && (i & 1) == 0) {
            this.selectAll();
            keyevent.consume();
         }
      }
   }

   @Override
   public void keyPressed(KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         char c0 = keyevent.getKeyChar();
         int i = keyevent.getModifiers();
         if (c0 == 1 && (i & 1) == 0) {
            this.selectAll();
            keyevent.consume();
         } else if (c0 == '\t') {
            LogicProgram.m1086(this, keyevent);
            keyevent.consume();
         } else if (!this.f1331 && c0 == '\n') {
            LogicProgram.m1086(this, keyevent);
            keyevent.consume();
         }
      }
   }

   @Override
   public void keyReleased(KeyEvent keyevent) {
   }

   static void m2024(String s) {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents(new StringSelection(s), null);
   }

   static String m2025() {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable transferable = clipboard.getContents(null);

      try {
         return (String)transferable.getTransferData(DataFlavor.stringFlavor);
      } catch (Exception exception) {
         return null;
      }
   }

   public boolean m1844() {
      try {
         this.f1333.undo();
         return true;
      } catch (CannotUndoException cannotundoexception) {
         return false;
      }
   }

   public boolean m2026() {
      try {
         this.f1333.redo();
         return true;
      } catch (CannotRedoException cannotredoexception) {
         return false;
      }
   }
}
