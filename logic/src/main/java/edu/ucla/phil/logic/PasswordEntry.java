package edu.ucla.phil.logic;

import javax.swing.JPasswordField;

class PasswordEntry {
   String password;
   String newPassword;
   boolean retried;

   PasswordEntry(String s) {
      this(s, null);
   }

   PasswordEntry(String s, String s1) {
      this.password = s;
      this.newPassword = s1;
      this.retried = false;
   }

   PasswordEntry(JPasswordField jpasswordfield) {
      this(jpasswordfield, null);
   }

   PasswordEntry(JPasswordField jpasswordfield, JPasswordField jpasswordfield1) {
      this(hashPassword(jpasswordfield), hashPassword(jpasswordfield1));
   }

   static String hashPassword(JPasswordField jpasswordfield) {
      return jpasswordfield == null ? null : Scrambler.md5Base64(new String(jpasswordfield.getPassword()));
   }
}
