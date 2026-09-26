package edu.ucla.phil.logic;

import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JDialog;

class C_u_B extends JDialog {
   Frame f1395;

   C_u_B(Frame frame) {
      super(frame);
      this.f1395 = frame;
   }

   C_u_B(Frame frame, boolean flag) {
      super(frame, flag);
      this.f1395 = frame;
   }

   C_u_B(Frame frame, String s) {
      super(frame, s);
      this.f1395 = frame;
   }

   C_u_B(Frame frame, String s, boolean flag) {
      super(frame, s, flag);
      this.f1395 = frame;
   }

   C_u_B(Dialog dialog) {
      super(dialog);
      this.f1395 = null;
   }

   C_u_B(Dialog dialog, boolean flag) {
      super(dialog, flag);
      this.f1395 = null;
   }

   C_u_B(Dialog dialog, String s) {
      super(dialog, s);
      this.f1395 = null;
   }

   C_u_B(Dialog dialog, String s, boolean flag) {
      super(dialog, s, flag);
      this.f1395 = null;
   }

   Frame m2100() {
      return this.f1395;
   }
}
