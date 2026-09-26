package edu.ucla.phil.logic;

import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JDialog;

class BaseDialog extends JDialog {
   Frame f1395;

   BaseDialog(Frame frame) {
      super(frame);
      this.f1395 = frame;
   }

   BaseDialog(Frame frame, boolean flag) {
      super(frame, flag);
      this.f1395 = frame;
   }

   BaseDialog(Frame frame, String s) {
      super(frame, s);
      this.f1395 = frame;
   }

   BaseDialog(Frame frame, String s, boolean flag) {
      super(frame, s, flag);
      this.f1395 = frame;
   }

   BaseDialog(Dialog dialog) {
      super(dialog);
      this.f1395 = null;
   }

   BaseDialog(Dialog dialog, boolean flag) {
      super(dialog, flag);
      this.f1395 = null;
   }

   BaseDialog(Dialog dialog, String s) {
      super(dialog, s);
      this.f1395 = null;
   }

   BaseDialog(Dialog dialog, String s, boolean flag) {
      super(dialog, s, flag);
      this.f1395 = null;
   }

   Frame m2100() {
      return this.f1395;
   }
}
