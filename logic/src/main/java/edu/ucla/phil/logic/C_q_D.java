package edu.ucla.phil.logic;

import java.awt.Insets;
import java.awt.event.ActionEvent;

class C_q_D extends C__D {
   C_JF f1342;

   C_q_D(C_JF c_jf, String s) {
      super(s);
      this.setFont(LogicProgram.m1029(LogicProgram.f539));
      this.setBorder(new C_BB(0));
      this.setMargin(new Insets(1, 1, 1, 1));
      this.setFocusable(false);
      this.f1342 = c_jf;
   }

   @Override
   protected void fireActionPerformed(ActionEvent actionevent) {
      String s = this.f1342.f439.m375(this.getText());
      if (s != null) {
         this.f1342.m738(s);
      }

      super.fireActionPerformed(actionevent);
   }
}
