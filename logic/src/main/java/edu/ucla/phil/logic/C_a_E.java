package edu.ucla.phil.logic;

import java.awt.Dialog;
import java.awt.Frame;

class C_a_E extends KeypadDialog implements C_LC {
   MessageDialog f978 = null;

   C_a_E(Frame frame, String s, boolean flag, EditableTextPane editabletextpane) {
      super(frame, s, flag, editabletextpane);
   }

   C_a_E(Dialog dialog, String s, boolean flag, EditableTextPane editabletextpane) {
      super(dialog, s, flag, editabletextpane);
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
