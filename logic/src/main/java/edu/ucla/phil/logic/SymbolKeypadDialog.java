package edu.ucla.phil.logic;

import java.awt.Dialog;
import java.awt.Frame;

class SymbolKeypadDialog extends KeypadDialog implements ModuleComponentMarker {
   MessageDialog ownerDialog = null;

   SymbolKeypadDialog(Frame frame, String s, boolean flag, EditableTextPane editabletextpane) {
      super(frame, s, flag, editabletextpane);
   }

   SymbolKeypadDialog(Dialog dialog, String s, boolean flag, EditableTextPane editabletextpane) {
      super(dialog, s, flag, editabletextpane);
   }

   @Override
   public String translateKey(String s) {
      if (s == null) {
         return null;
      } else {
         if (s.equals("backspace")) {
            s = "\b";
         } else if (s.equals("space")) {
            s = " ";
         }

         return super.translateKey(s);
      }
   }
}
