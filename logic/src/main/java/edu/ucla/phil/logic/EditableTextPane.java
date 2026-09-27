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

class EditableTextPane extends StyledTextPane implements KeyListener, ModuleComponentMarker {
   int minWidth;
   int maxWidth;
   boolean multiLine;
   boolean minimumIsPreferred;
   UndoManager undoManager;

   EditableTextPane(String s, int i, int j, boolean flag) {
      super(s);
      this.setText(s);
      this.minWidth = i < 0 ? 0 : i;
      this.maxWidth = j < 0 ? 2147483647 : j;
      this.multiLine = flag;
      this.minimumIsPreferred = false;
      this.setFocusable(true);
      this.addKeyListener(this);
      this.setWrapLines(true);
      this.setWrapWords(true);
      this.setMargin(new Insets(0, 1, 0, 1));
      this.getDocument().addUndoableEditListener(this.undoManager = new UndoManager());
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

   public boolean isMinimumPreferred() {
      return this.minimumIsPreferred;
   }

   public void setMinimumPreferred(boolean flag) {
      this.minimumIsPreferred = flag;
   }

   @Override
   public Dimension getMinimumSize() {
      return this.minimumIsPreferred ? this.getPreferredSize() : super.getMinimumSize();
   }

   @Override
   public Dimension getMaximumSize() {
      Dimension dimension = super.getMaximumSize();
      if (dimension.width > this.maxWidth) {
         dimension.width = this.maxWidth;
      }

      return dimension;
   }

   @Override
   public Dimension getPreferredSize() {
      Dimension dimension = super.getPreferredSize();
      if (dimension.width < this.minWidth) {
         dimension.width = this.minWidth;
      } else if (dimension.width > this.maxWidth) {
         dimension.width = this.maxWidth;
      }

      return dimension;
   }

   public Dimension getPreferredSizeAtLeast(Dimension dimension) {
      Dimension dimension1 = super.getPreferredSize();
      if (dimension1.width < dimension.width) {
         dimension1.width = dimension.width;
      }

      if (dimension1.width > this.maxWidth) {
         dimension1.width = this.maxWidth;
      }

      return dimension1;
   }

   void setMinWidth(int i) {
      this.minWidth = i;
   }

   void setFixedWidth(int i) {
      this.minWidth = i;
      this.maxWidth = i;
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
            LogicProgram.forwardKeyEvent(this, keyevent);
            keyevent.consume();
         } else if (!this.multiLine && c0 == '\n') {
            LogicProgram.forwardKeyEvent(this, keyevent);
            keyevent.consume();
         }
      }
   }

   @Override
   public void keyReleased(KeyEvent keyevent) {
   }

   static void copyToClipboard(String s) {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents(new StringSelection(s), null);
   }

   static String getClipboardText() {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable transferable = clipboard.getContents(null);

      try {
         return (String)transferable.getTransferData(DataFlavor.stringFlavor);
      } catch (Exception exception) {
         return null;
      }
   }

   public boolean undo() {
      try {
         this.undoManager.undo();
         return true;
      } catch (CannotUndoException cannotundoexception) {
         return false;
      }
   }

   public boolean redo() {
      try {
         this.undoManager.redo();
         return true;
      } catch (CannotRedoException cannotredoexception) {
         return false;
      }
   }
}
