package edu.ucla.phil.logic;

import java.awt.BorderLayout;

class C_f_E extends SizedPanel {
   C_s_B f1118;

   C_f_E(String s) {
      this.setLayout(new BorderLayout());
      this.f1118 = new C_s_B(s);
      this.f1118.setLineWrap(true);
      this.f1118.setWrapStyleWord(true);
      this.f1118.setBackground(LogicProgram.f605[1]);
      this.setBackground(LogicProgram.f605[1]);
      this.add(this.f1118, "North");
   }
}
