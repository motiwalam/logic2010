package edu.ucla.phil.logic;

class C_s_B extends C_NC implements C_TE {
   boolean f1361 = false;

   C_s_B() {
      this.setLineWrap(true);
      this.setWrapStyleWord(true);
      this.setEditable(false);
      this.setFont(LogicProgram.m1029(LogicProgram.f539));
   }

   C_s_B(String s) {
      super(s);
      this.setLineWrap(true);
      this.setWrapStyleWord(true);
      this.setEditable(false);
      this.setFont(LogicProgram.m1029(LogicProgram.f539));
   }

   @Override
   public void setFocusable(boolean flag) {
      this.f1361 = flag;
      super.setFocusable(flag);
   }

   @Override
   public boolean m943() {
      return this.f1361;
   }
}
