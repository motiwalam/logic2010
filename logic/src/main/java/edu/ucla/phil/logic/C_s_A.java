package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;

class C_s_A extends SizedPanel implements ActionListener {
   C_IE f1354;
   C_SA f1355;
   SizedPanel f1356;
   C__D f1357;
   C_ZE f1358;
   C_u_ f1359;
   FlowLayout f1360;

   C_s_A(C_IE c_ie, C_SA c_sa) {
      this.f1354 = c_ie;
      this.f1355 = c_sa;
      this.setLayout(new BoxLayout(this, 0));
      this.add(this.f1356 = new SizedPanel());
      this.f1356.add(this.f1357 = new C__D(c_ie.f422), "North");
      this.f1357.addActionListener(this);
      this.f1357.m1583("Click to set the value of " + c_ie.f422);
      this.add(this.f1358 = new C_ZE(c_ie.m711(c_sa.f745.size)));
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      Object object = actionevent.getSource();
      if (object instanceof C__D) {
         int i = this.f1355.f745.size;
         if (i == 0) {
            MessageDialog.showMessage("Empty Universe", "The Universe needs to have\nat least one element.", null, null);
         } else if (C_CE.m444(this.f1354, i)) {
            this.f1358.setText(this.f1354.m711(this.f1355.f745.size));
         }
      }
   }
}
