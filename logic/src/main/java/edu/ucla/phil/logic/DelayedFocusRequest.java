package edu.ucla.phil.logic;

import java.awt.Component;

class DelayedFocusRequest extends Thread {
   Component component;
   long delayMillis;

   DelayedFocusRequest(Component componentx) {
      this(componentx, 0L);
   }

   DelayedFocusRequest(Component componentx, long i) {
      this.component = componentx;
      this.delayMillis = i;
   }

   @Override
   public void run() {
      try {
         if (this.delayMillis != 0L) {
            Thread.sleep(this.delayMillis);
         }
      } catch (InterruptedException interruptedexception) {
      }

      this.component.requestFocus();
   }
}
