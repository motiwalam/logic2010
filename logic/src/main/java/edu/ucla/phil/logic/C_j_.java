package edu.ucla.phil.logic;

import java.awt.event.ItemEvent;

class C_j_ extends C_NF {
   C_f_D f1176;

   C_j_(String s, C_f_D c_f_d) {
      super(s);
      this.f1176 = c_f_d;
   }

   @Override
   protected void fireItemStateChanged(ItemEvent itemevent) {
      super.fireItemStateChanged(itemevent);
      boolean flag = itemevent.getStateChange() == 1;
      int i = this.f1176.m1809();
      LPParsing lpparsing = this.f1176.f1112.f1455;
      if (flag && i == 2) {
         this.f1176.f1112.m2190(false);
      }

      if (flag && lpparsing.checkNow && !lpparsing.checkDisabled) {
         C_DD c_dd = new C_DD(this.f1176.f1112.f1459);
         String s = c_dd.m464();
         this.f1176.f1112.f1457.setVisible(i == 0 && s.equals("O") || i == 1 && s.equals("I"));
         this.f1176.m1808(s.equals(C_f_D.m1810(i)) ? "Correct" : "Incorrect");
      }
   }
}
