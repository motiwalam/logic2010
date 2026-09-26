package edu.ucla.phil.logic;

abstract class C_o_ extends Thread {
   NetworkTask f1317;
   Object f1318;

   C_o_(NetworkTask networktask, Object object) {
      this.f1317 = networktask;
      this.f1318 = object;
   }

   @Override
   public void run() {
      this.m1989();
      this.f1317.m2054();
   }

   void m1989() {
      try {
         this.f1318 = this.m687();
      } catch (RuntimeException runtimeexception) {
         runtimeexception.printStackTrace(System.err);
      }
   }

   abstract Object m687();

   Object m1990() {
      return this.f1318;
   }
}
