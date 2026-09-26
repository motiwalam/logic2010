package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class C_RC extends C_TA implements ActionListener, C_LC {
   int f733;
   C_ZE f734;
   C_g_C f735;
   C_k_E f736;

   C_RC(C_k_E c_k_e) {
      this.f736 = c_k_e;
      this.setLayout(new FlowLayout(0));
      this.add(this.f734 = new C_ZE("Please click OK when you are finished."));
      this.add(this.f735 = new C_g_C("OK"));
      this.f735.addActionListener(this);
      this.m1203(1);
   }

   void m1203(int i) {
      this.f733 = i;
      if (i == 0) {
         this.f736.f1204.setText("Please separate sentence letters with a period.");
      } else if (i == 1) {
         this.f736.f1204.setText("Please fill in the proper truth values.");
      }
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (actionevent.getSource() == this.f735) {
         if (this.f733 == 0) {
            C_c_B c_c_b = this.f736.f1203.m1957();
            if (this.f736.f1203.f1290 = c_c_b.f427 == null) {
               this.f736.f1203.m1951();
            } else if (!this.f736.f1200.checkMessagesDisabled) {
               C_UA.m1329(C_FE.m411(c_c_b.f427), c_c_b.f428, null, null);
            }
         } else if (this.f733 == 1) {
            C_c_B c_c_b1 = this.f736.f1203.m1956();
            if (this.f736.f1219 = c_c_b1.f427 == null) {
               this.f736.m1911();
            } else if (!this.f736.f1200.checkMessagesDisabled) {
               C_UA.m1329(C_FE.m411(c_c_b1.f427), c_c_b1.f428, null, null);
            }
         }
      }
   }
}
