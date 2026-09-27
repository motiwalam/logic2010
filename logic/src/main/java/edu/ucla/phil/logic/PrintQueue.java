package edu.ucla.phil.logic;

import java.util.Vector;

class PrintQueue extends Vector {
   Vector listeners = new Vector();
   String title;

   PrintQueue(String s) {
      this.title = s;
   }

   String getTitle() {
      return this.title;
   }

   synchronized void addQueueListener(PrintQueueListener printqueuelistener) {
      this.listeners.addElement(printqueuelistener);
   }

   synchronized void removeQueueListener(PrintQueueListener printqueuelistener) {
      this.listeners.removeElement(printqueuelistener);
   }

   synchronized void fireQueueChanged() {
      int i = this.listeners.size();

      for (int j = 0; j < i; j++) {
         ((PrintQueueListener)this.listeners.elementAt(j)).queueChanged(this);
      }
   }
}
