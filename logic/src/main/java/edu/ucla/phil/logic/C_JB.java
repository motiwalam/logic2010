package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class C_JB extends JPanel implements ComponentListener, C_LC {
   JScrollPane f432;
   C_JE f433;

   C_JB(JScrollPane jscrollpane, C_JE c_je) {
      this.setLayout(new FlowLayout(0, 0, 0));
      this.setFont(LogicProgram.m1029(c_je.f438 * 7 / 10));
      this.f432 = jscrollpane;
      this.f433 = c_je;
   }

   @Override
   public void componentHidden(ComponentEvent componentevent) {
   }

   @Override
   public void componentMoved(ComponentEvent componentevent) {
   }

   @Override
   public void componentResized(ComponentEvent componentevent) {
      this.m722();
   }

   @Override
   public void componentShown(ComponentEvent componentevent) {
      this.m722();
   }

   void m722() {
      if (this.f433 != null && this.f432 != null) {
         this.f433.m728(LogicProgram.m1038(this.f432).width - 16 - this.f433.f438 / 4);
         this.invalidate();
      }
   }
}
