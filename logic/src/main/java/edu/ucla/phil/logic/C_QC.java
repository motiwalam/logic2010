package edu.ucla.phil.logic;

class C_QC extends C_ZE {
   C_QC(String s) {
      super(s);
   }

   C_QC(String s, int i) {
      super(s, i);
   }

   @Override
   public String toString() {
      return this.getText();
   }

   void m1187(String s) {
      this.setToolTipText(s);
   }
}
