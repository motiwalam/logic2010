package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;

public class C_YD extends EventQueue {
   static void m1539() {
      if (EventQueue.isDispatchThread()) {
         EventQueue eventqueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
         C_YD c_yd = new C_YD();
         eventqueue.push(c_yd);

         while (c_yd.peekEvent() != null) {
            Object object = null;

            try {
               object = c_yd.getNextEvent();
            } catch (InterruptedException interruptedexception) {
               break;
            }

            if (object == null) {
               break;
            }

            c_yd.dispatchEvent((AWTEvent)object);
         }

         c_yd.pop();
      }
   }
}
