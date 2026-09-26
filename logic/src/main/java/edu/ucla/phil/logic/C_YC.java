package edu.ucla.phil.logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class C_YC extends EditableTextPane implements KeyListener {
   LPRecognition f904;

   C_YC(LPRecognition lprecognition) {
      this.f904 = lprecognition;
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      int i = keyevent.getKeyCode();
      int j = keyevent.getModifiers();
      if (c0 == '\n') {
         if (this.f904.checkDisabled) {
            MessageDialog.showMessage("Feature Disabled", "Checking is disabled for this problem.", null, null);
         } else {
            this.f904.checkProblem();
         }

         this.f904.requestFocus();
      }
   }
}
