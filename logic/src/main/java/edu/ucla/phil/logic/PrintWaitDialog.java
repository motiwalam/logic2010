package edu.ucla.phil.logic;

import java.awt.Frame;
import javax.swing.JComponent;

class PrintWaitDialog extends MessageDialog implements PrintQueueListener {
   PrintWaitDialog(Frame frame, String s, JComponent jcomponent, String[] astring) {
      super(frame, s, jcomponent, astring);
   }

   @Override
   public void queueChanged(PrintQueue printqueue) {
      if (printqueue.isEmpty()) {
         this.selectedButton = -1;
         this.close();
      }
   }
}
