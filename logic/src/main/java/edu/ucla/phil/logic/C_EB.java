package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.Box;

class C_EB extends C_TA implements C_LC {
   C_ZE f291;
   C_AD f292;
   LPParsing f293;
   int f294;

   C_EB(LPParsing lpparsing) {
      this.f293 = lpparsing;
      this.f294 = 0;
      this.setLayout(new C_m_A());
      this.add(this.f291 = new C_ZE(" "));
      this.add(Box.createRigidArea(new Dimension(4, 4)));
      this.add(this.f292 = new C_AD(this));
      this.f292.f144 = this;
      this.m494();
   }

   void m494() {
      if (this.f293.noDescent) {
         int[] aint = this.f292.f142.f1195;
         boolean flag = aint == null || aint.length == 0;
         this.f291.setText(this.f293.checkNow ? (this.m495() ? "Correct" : (flag ? "Incomplete" : "Incorrect")) : " ");
      } else {
         this.f291.setText(this.f293.checkNow ? (this.m495() ? "Complete" : "Incomplete") : " ");
      }

      this.f292.validate();
   }

   boolean m495() {
      return this.f293.noDescent ? this.f292.f142.f1198 : this.f294 == 0;
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      if (awtevent.getID() == 400) {
         LogicProgram.m1086(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
