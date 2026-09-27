package edu.ucla.phil.logic;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.util.Collections;
import javax.swing.AbstractAction;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

class KeypadDialog extends BaseDialog implements KeypadTarget, Runnable, WindowListener, ModuleComponentMarker, WindowFocusListener {
   EditableTextPane target;
   boolean disposed;
   Point anchor;

   public KeypadDialog(Frame frame, String s, boolean flag, EditableTextPane editabletextpane) {
      super(frame, s, flag);
      this.init(editabletextpane);
   }

   public KeypadDialog(Dialog dialog, String s, boolean flag, EditableTextPane editabletextpane) {
      super(dialog, s, flag);
      this.init(editabletextpane);
   }

   public void init(EditableTextPane editabletextpane) {
      this.target = editabletextpane;
      this.disposed = false;
      this.setLayout(new VerticalStackLayout());
      this.addWindowListener(this);
      this.addWindowFocusListener(this);
      JRootPane jrootpane = this.getRootPane();
      jrootpane.getInputMap(1).put(KeyStroke.getKeyStroke(27, 0), "CloseOnEsc");
      jrootpane.getActionMap().put("CloseOnEsc", new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            KeypadDialog.this.dispose();
            KeypadDialog.this.refocusTarget();
         }
      });
      this.setFocusTraversalKeys(0, Collections.EMPTY_SET);
      this.setFocusTraversalKeys(1, Collections.EMPTY_SET);
   }

   void showLater() {
      this.pack();
      SwingUtilities.invokeLater(this);
   }

   @Override
   public void run() {
      this.setVisible(true);
      this.toFront();
   }

   void setAnchor(Point point) {
      this.anchor = point;
      this.setLocation(point);
   }

   @Override
   public void windowOpened(WindowEvent windowevent) {
      Rectangle rectangle = this.getBounds();
      if (rectangle.y != this.anchor.y || rectangle.x != this.anchor.x) {
         this.setLocation(this.anchor);
      }
   }

   @Override
   public void windowClosing(WindowEvent windowevent) {
      this.dispose();
      this.target.requestFocus();
   }

   @Override
   public void windowClosed(WindowEvent windowevent) {
      this.target.requestFocus();
   }

   @Override
   public void windowActivated(WindowEvent windowevent) {
   }

   @Override
   public void windowDeactivated(WindowEvent windowevent) {
      Window window = windowevent.getOppositeWindow();
      Frame frame = LogicProgram.findFrame(this.target);
      if (window != frame) {
         this.dispose();
      }

      this.target.requestFocus();
   }

   @Override
   public void windowIconified(WindowEvent windowevent) {
      this.dispose();
      this.target.requestFocus();
   }

   @Override
   public void windowDeiconified(WindowEvent windowevent) {
   }

   @Override
   public void windowGainedFocus(WindowEvent windowevent) {
   }

   @Override
   public void windowLostFocus(WindowEvent windowevent) {
      this.dispose();
   }

   @Override
   public void dispose() {
      if (!this.disposed) {
         this.disposed = true;
         super.dispose();
      }
   }

   public void refocusTarget() {
      this.target.requestFocus();
   }

   @Override
   public String translateKey(String s) {
      if (s == null) {
         return null;
      } else {
         if (s.equals("\b")) {
            KeypadGrid.deleteBackward(this);
            s = null;
         } else if (s.equals("paste")) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            try {
               s = (String)clipboard.getContents(this).getTransferData(DataFlavor.stringFlavor);
            } catch (Exception exception) {
               s = null;
            }
         } else if (s.equals("copy")) {
            Clipboard clipboard1 = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard1.setContents(new StringSelection(this.getTargetText().substring(this.getTargetSelectionStart(), this.getTargetSelectionEnd())), null);
            s = null;
         }

         return s;
      }
   }

   @Override
   public String getTargetText() {
      return this.target.getText();
   }

   @Override
   public int getTargetSelectionStart() {
      return this.target.getSelectionStart();
   }

   @Override
   public int getTargetSelectionEnd() {
      return this.target.getSelectionEnd();
   }

   @Override
   public int getTargetCaret() {
      return this.target.getCaretPosition();
   }

   @Override
   public void selectInTarget(int i, int j) {
      this.target.select(i, j);
   }

   @Override
   public void setTargetCaret(int i) {
      this.target.setCaretPosition(i);
   }

   @Override
   public void insertIntoTarget(String s, int i) {
      this.target.insertText(s, i);
   }

   @Override
   public void deleteFromTarget(int i, int j) {
      this.target.replaceRange(null, i, i + j);
   }

   @Override
   public void invalidate() {
      this.target.invalidate();
      super.invalidate();
   }

   @Override
   public boolean isTargetReadOnly() {
      return !this.target.isEditable();
   }
}
