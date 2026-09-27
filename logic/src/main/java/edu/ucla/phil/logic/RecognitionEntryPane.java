package edu.ucla.phil.logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class RecognitionEntryPane extends EditableTextPane implements KeyListener {
   LPRecognition module;

   RecognitionEntryPane(LPRecognition lprecognition) {
      this.module = lprecognition;
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      int i = keyevent.getKeyCode();
      int j = keyevent.getModifiers();
      if (c0 == '\n') {
         if (this.module.checkDisabled) {
            MessageDialog.showMessage("Feature Disabled", "Checking is disabled for this problem.", null, null);
         } else {
            this.module.checkProblem();
         }

         this.module.requestFocus();
      }
   }
}
