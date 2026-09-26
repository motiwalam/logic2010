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

class KeypadDialog extends BaseDialog implements C_r_B, Runnable, WindowListener, C_LC, WindowFocusListener {
   EditableTextPane f776;
   boolean f777;
   Point f778;

   public KeypadDialog(Frame frame, String s, boolean flag, EditableTextPane editabletextpane) {
      super(frame, s, flag);
      this.m1300(editabletextpane);
   }

   public KeypadDialog(Dialog dialog, String s, boolean flag, EditableTextPane editabletextpane) {
      super(dialog, s, flag);
      this.m1300(editabletextpane);
   }

   public void m1300(EditableTextPane editabletextpane) {
      this.f776 = editabletextpane;
      this.f777 = false;
      this.setLayout(new C_m_A());
      this.addWindowListener(this);
      this.addWindowFocusListener(this);
      JRootPane jrootpane = this.getRootPane();
      jrootpane.getInputMap(1).put(KeyStroke.getKeyStroke(27, 0), "CloseOnEsc");
      jrootpane.getActionMap().put("CloseOnEsc", new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            KeypadDialog.this.dispose();
            KeypadDialog.this.m1303();
         }
      });
      this.setFocusTraversalKeys(0, Collections.EMPTY_SET);
      this.setFocusTraversalKeys(1, Collections.EMPTY_SET);
   }

   void m1301() {
      this.pack();
      SwingUtilities.invokeLater(this);
   }

   @Override
   public void run() {
      this.setVisible(true);
      this.toFront();
   }

   void m1302(Point point) {
      this.f778 = point;
      this.setLocation(point);
   }

   @Override
   public void windowOpened(WindowEvent windowevent) {
      Rectangle rectangle = this.getBounds();
      if (rectangle.y != this.f778.y || rectangle.x != this.f778.x) {
         this.setLocation(this.f778);
      }
   }

   @Override
   public void windowClosing(WindowEvent windowevent) {
      this.dispose();
      this.f776.requestFocus();
   }

   @Override
   public void windowClosed(WindowEvent windowevent) {
      this.f776.requestFocus();
   }

   @Override
   public void windowActivated(WindowEvent windowevent) {
   }

   @Override
   public void windowDeactivated(WindowEvent windowevent) {
      Window window = windowevent.getOppositeWindow();
      Frame frame = LogicProgram.m1044(this.f776);
      if (window != frame) {
         this.dispose();
      }

      this.f776.requestFocus();
   }

   @Override
   public void windowIconified(WindowEvent windowevent) {
      this.dispose();
      this.f776.requestFocus();
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
      if (!this.f777) {
         this.f777 = true;
         super.dispose();
      }
   }

   public void m1303() {
      this.f776.requestFocus();
   }

   @Override
   public String m375(String s) {
      if (s == null) {
         return null;
      } else {
         if (s.equals("\b")) {
            C_JF.m740(this);
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
            clipboard1.setContents(new StringSelection(this.m1304().substring(this.m376(), this.m377())), null);
            s = null;
         }

         return s;
      }
   }

   @Override
   public String m1304() {
      return this.f776.getText();
   }

   @Override
   public int m376() {
      return this.f776.getSelectionStart();
   }

   @Override
   public int m377() {
      return this.f776.getSelectionEnd();
   }

   @Override
   public int m378() {
      return this.f776.getCaretPosition();
   }

   @Override
   public void m379(int i, int j) {
      this.f776.select(i, j);
   }

   @Override
   public void m380(int i) {
      this.f776.setCaretPosition(i);
   }

   @Override
   public void m381(String s, int i) {
      this.f776.m1796(s, i);
   }

   @Override
   public void m1305(int i, int j) {
      this.f776.m1795(null, i, i + j);
   }

   @Override
   public void invalidate() {
      this.f776.invalidate();
      super.invalidate();
   }

   @Override
   public boolean m1306() {
      return !this.f776.isEditable();
   }
}
