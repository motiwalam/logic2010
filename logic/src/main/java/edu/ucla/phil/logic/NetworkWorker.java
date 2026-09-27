package edu.ucla.phil.logic;

abstract class NetworkWorker extends Thread {
   NetworkTask task;
   Object result;

   NetworkWorker(NetworkTask networktask, Object object) {
      this.task = networktask;
      this.result = object;
   }

   @Override
   public void run() {
      this.runWork();
      this.task.dismissDialog();
   }

   void runWork() {
      try {
         this.result = this.perform();
      } catch (RuntimeException runtimeexception) {
         runtimeexception.printStackTrace(System.err);
      }
   }

   abstract Object perform();

   Object getResult() {
      return this.result;
   }
}
