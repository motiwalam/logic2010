package edu.ucla.phil.logic;

import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class PasswordFieldValidator implements DocumentListener {
   MessageDialog dialog;
   int buttonIndex;
   JPasswordField passwordField;
   JPasswordField confirmField;
   JPasswordField oldPasswordField;

   PasswordFieldValidator(MessageDialog messagedialog, int i, JPasswordField jpasswordfield) {
      this(messagedialog, i, jpasswordfield, null, null);
   }

   PasswordFieldValidator(MessageDialog messagedialog, int i, JPasswordField jpasswordfield, JPasswordField jpasswordfield1) {
      this(messagedialog, i, jpasswordfield, jpasswordfield1, null);
   }

   PasswordFieldValidator(MessageDialog messagedialog, int i, JPasswordField jpasswordfield, JPasswordField jpasswordfield1, JPasswordField jpasswordfield2) {
      this.dialog = messagedialog;
      this.buttonIndex = i;
      this.attach(this.passwordField = jpasswordfield);
      this.attach(this.confirmField = jpasswordfield1);
      this.attach(this.oldPasswordField = jpasswordfield2);
      this.updateButton();
   }

   void attach(JPasswordField jpasswordfield) {
      if (jpasswordfield != null) {
         jpasswordfield.setEchoChar('*');
         jpasswordfield.getDocument().addDocumentListener(this);
      }
   }

   @Override
   public void changedUpdate(DocumentEvent documentevent) {
      this.updateButton();
   }

   @Override
   public void insertUpdate(DocumentEvent documentevent) {
      this.updateButton();
   }

   @Override
   public void removeUpdate(DocumentEvent documentevent) {
      this.updateButton();
   }

   void updateButton() {
      String s = new String(this.passwordField.getPassword());
      String s1 = this.confirmField == null ? null : new String(this.confirmField.getPassword());
      String s2 = this.oldPasswordField == null ? null : new String(this.oldPasswordField.getPassword());
      if (s.length() <= 0 || this.confirmField != null && !s.equals(s1) || s2 != null && s2.length() <= 0) {
         this.dialog.buttons[this.buttonIndex].setEnabled(false);
         this.dialog.setDefaultButtonIndex(-1);
      } else {
         this.dialog.buttons[this.buttonIndex].setEnabled(true);
         this.dialog.setDefaultButtonIndex(this.buttonIndex);
      }
   }
}
