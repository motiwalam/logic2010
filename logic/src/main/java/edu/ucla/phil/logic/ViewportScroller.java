package edu.ucla.phil.logic;

import java.awt.Point;
import javax.swing.JViewport;

class ViewportScroller implements Runnable {
   Point position;
   JViewport viewport;

   public ViewportScroller(Point point, JViewport jviewport) {
      this.position = point;
      this.viewport = jviewport;
   }

   @Override
   public void run() {
      this.viewport.setViewPosition(this.position);
   }
}
