package edu.ucla.phil.logic;

class C_b_E extends DialogHandler {
   int f1027 = -1;

   C_b_E(String s) {
      super(s);
   }

   @Override
   boolean m451(MessageDialog messagedialog) {
      String s = messagedialog.f791[messagedialog.f790].getText();
      this.f1027 = LogicProgram.m1051(this.f272, s);
      return true;
   }
}
