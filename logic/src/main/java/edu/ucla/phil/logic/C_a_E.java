package edu.ucla.phil.logic;

import java.awt.Dialog;
import java.awt.Frame;

class C_a_E extends C_T implements C_LC {
   C_UA f978 = null;

   C_a_E(Frame frame, String s, boolean flag, C_p_A c_p_a) {
      super(frame, s, flag, c_p_a);
   }

   C_a_E(Dialog dialog, String s, boolean flag, C_p_A c_p_a) {
      super(dialog, s, flag, c_p_a);
   }

   @Override
   public String m375(String s) {
      if (s == null) {
         return null;
      } else {
         if (s.equals("backspace")) {
            s = "\b";
         } else if (s.equals("space")) {
            s = " ";
         }

         return super.m375(s);
      }
   }
}
