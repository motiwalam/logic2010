package edu.ucla.phil.logic;

import java.awt.Point;
import javax.swing.JViewport;

class C_z_A implements Runnable {
   Point f1466;
   JViewport f1467;

   public C_z_A(Point point, JViewport jviewport) {
      this.f1466 = point;
      this.f1467 = jviewport;
   }

   @Override
   public void run() {
      this.f1467.setViewPosition(this.f1466);
   }
}
