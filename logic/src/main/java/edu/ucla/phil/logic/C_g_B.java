package edu.ucla.phil.logic;

import java.awt.event.ActionEvent;

class C_g_B extends C_ZD {
   C_g_B(C_v_A c_v_a) {
      super(c_v_a);
   }

   @Override
   protected void fireActionPerformed(ActionEvent actionevent) {
      super.fireActionPerformed(actionevent);
      DerivationBox derivationbox = (DerivationBox)this.m1546();
      derivationbox.m1564();
      derivationbox.f915.setWidths(false);
   }
}
