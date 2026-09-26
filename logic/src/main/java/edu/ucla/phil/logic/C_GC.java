package edu.ucla.phil.logic;

import java.awt.Component;

class C_GC extends Thread {
   Component f355;
   long f356;

   C_GC(Component component) {
      this(component, 0L);
   }

   C_GC(Component component, long i) {
      this.f355 = component;
      this.f356 = i;
   }

   @Override
   public void run() {
      try {
         if (this.f356 != 0L) {
            Thread.sleep(this.f356);
         }
      } catch (InterruptedException interruptedexception) {
      }

      this.f355.requestFocus();
   }
}
