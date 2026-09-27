package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;

public class EventQueueFlusher extends EventQueue {
   static void flushEvents() {
      if (EventQueue.isDispatchThread()) {
         EventQueue eventqueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
         EventQueueFlusher eventqueueflusher = new EventQueueFlusher();
         eventqueue.push(eventqueueflusher);

         while (eventqueueflusher.peekEvent() != null) {
            Object object = null;

            try {
               object = eventqueueflusher.getNextEvent();
            } catch (InterruptedException interruptedexception) {
               break;
            }

            if (object == null) {
               break;
            }

            eventqueueflusher.dispatchEvent((AWTEvent)object);
         }

         eventqueueflusher.pop();
      }
   }
}
