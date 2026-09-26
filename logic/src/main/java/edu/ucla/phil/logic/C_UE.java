package edu.ucla.phil.logic;

import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class C_UE implements DocumentListener {
   C_UA f811;
   int f812;
   JPasswordField f813;
   JPasswordField f814;
   JPasswordField f815;

   C_UE(C_UA c_ua, int i, JPasswordField jpasswordfield) {
      this(c_ua, i, jpasswordfield, null, null);
   }

   C_UE(C_UA c_ua, int i, JPasswordField jpasswordfield, JPasswordField jpasswordfield1) {
      this(c_ua, i, jpasswordfield, jpasswordfield1, null);
   }

   C_UE(C_UA c_ua, int i, JPasswordField jpasswordfield, JPasswordField jpasswordfield1, JPasswordField jpasswordfield2) {
      this.f811 = c_ua;
      this.f812 = i;
      this.m1356(this.f813 = jpasswordfield);
      this.m1356(this.f814 = jpasswordfield1);
      this.m1356(this.f815 = jpasswordfield2);
      this.m1357();
   }

   void m1356(JPasswordField jpasswordfield) {
      if (jpasswordfield != null) {
         jpasswordfield.setEchoChar('*');
         jpasswordfield.getDocument().addDocumentListener(this);
      }
   }

   @Override
   public void changedUpdate(DocumentEvent documentevent) {
      this.m1357();
   }

   @Override
   public void insertUpdate(DocumentEvent documentevent) {
      this.m1357();
   }

   @Override
   public void removeUpdate(DocumentEvent documentevent) {
      this.m1357();
   }

   void m1357() {
      String s = new String(this.f813.getPassword());
      String s1 = this.f814 == null ? null : new String(this.f814.getPassword());
      String s2 = this.f815 == null ? null : new String(this.f815.getPassword());
      if (s.length() <= 0 || this.f814 != null && !s.equals(s1) || s2 != null && s2.length() <= 0) {
         this.f811.f791[this.f812].setEnabled(false);
         this.f811.m1314(-1);
      } else {
         this.f811.f791[this.f812].setEnabled(true);
         this.f811.m1314(this.f812);
      }
   }
}
