package edu.ucla.phil.logic;

import javax.swing.JPasswordField;

class C__F {
   String f932;
   String f933;
   boolean f934;

   C__F(String s) {
      this(s, null);
   }

   C__F(String s, String s1) {
      this.f932 = s;
      this.f933 = s1;
      this.f934 = false;
   }

   C__F(JPasswordField jpasswordfield) {
      this(jpasswordfield, null);
   }

   C__F(JPasswordField jpasswordfield, JPasswordField jpasswordfield1) {
      this(m1588(jpasswordfield), m1588(jpasswordfield1));
   }

   static String m1588(JPasswordField jpasswordfield) {
      return jpasswordfield == null ? null : C_z_D.m2225(new String(jpasswordfield.getPassword()));
   }
}
