package edu.ucla.phil.logic;

import java.awt.BorderLayout;

class C_B extends CellPanel implements C_LC {
   C_VF f145;
   C_VF f146;
   C_CC f147;
   C_k_E f148;

   C_B(C_k_E c_k_e) {
      this.f148 = c_k_e;
      this.setLayout(new BorderLayout());
      this.f145 = null;
      this.f146 = null;
   }

   void m235(C_VF c_vf, C_VF c_vf1) {
      this.removeAll();
      if ((this.f145 = c_vf) != null) {
         this.add(c_vf, "North");
      }

      if (c_vf != null && c_vf1 != null) {
         this.add(this.f147 = new C_CC(20, 2, false, LogicConstants.bruinGold), "Center");
      }

      if ((this.f146 = c_vf1) != null) {
         this.add(c_vf1, "South");
      }

      if (c_vf != null || c_vf1 != null) {
         this.validate();
      }
   }
}
