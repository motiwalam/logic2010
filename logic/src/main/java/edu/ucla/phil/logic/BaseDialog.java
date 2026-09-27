package edu.ucla.phil.logic;

import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JDialog;

class BaseDialog extends JDialog {
   Frame ownerFrame;

   BaseDialog(Frame frame) {
      super(frame);
      this.ownerFrame = frame;
   }

   BaseDialog(Frame frame, boolean flag) {
      super(frame, flag);
      this.ownerFrame = frame;
   }

   BaseDialog(Frame frame, String s) {
      super(frame, s);
      this.ownerFrame = frame;
   }

   BaseDialog(Frame frame, String s, boolean flag) {
      super(frame, s, flag);
      this.ownerFrame = frame;
   }

   BaseDialog(Dialog dialog) {
      super(dialog);
      this.ownerFrame = null;
   }

   BaseDialog(Dialog dialog, boolean flag) {
      super(dialog, flag);
      this.ownerFrame = null;
   }

   BaseDialog(Dialog dialog, String s) {
      super(dialog, s);
      this.ownerFrame = null;
   }

   BaseDialog(Dialog dialog, String s, boolean flag) {
      super(dialog, s, flag);
      this.ownerFrame = null;
   }

   Frame getOwnerFrame() {
      return this.ownerFrame;
   }
}
