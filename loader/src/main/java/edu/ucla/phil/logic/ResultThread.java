package edu.ucla.phil.logic;

abstract class ResultThread extends Thread {
   Valve valve;
   Object result;

   ResultThread(Valve valve, Object result) {
      this.valve = valve;
      this.result = result;
   }

   @Override
   public void run() {
      this.safeSpin();
      this.valve.dispose();
   }

   void safeSpin() {
      try {
         this.result = this.spin();
      } catch (RuntimeException var2) {
         var2.printStackTrace(System.err);
      }
   }

   abstract Object spin();

   Object getResult() {
      return this.result;
   }
}
