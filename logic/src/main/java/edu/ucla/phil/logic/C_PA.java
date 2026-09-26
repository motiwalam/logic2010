package edu.ucla.phil.logic;

import javax.swing.JButton;

class C_PA extends JButton implements C_n_A {
   C_RF f670;
   static String[] f671 = LogicProgram.f596;

   C_PA(C_RF c_rf) {
      super(LogicProgram.m995(c_rf.toString(), maggie, f671));
      this.f670 = c_rf;
      this.setFont(LogicProgram.m1030(LogicProgram.f539, 1));
   }
}
