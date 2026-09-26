package edu.ucla.phil.logic;

class C_I extends DialogHandler implements C_v_D {
   C_I(String s) {
      super(s);
   }

   @Override
   boolean m451(MessageDialog messagedialog) {
      String s = this.m450(messagedialog);
      LPSymbolizer lpsymbolizer = (LPSymbolizer)this.m449("symbolizer");
      FormulaEntryField formulaentryfield = (FormulaEntryField)this.m449("edit");
      if (s == null) {
         return false;
      } else if (s.equalsIgnoreCase("ok")) {
         return true;
      } else if (s.equalsIgnoreCase("clear")) {
         formulaentryfield.setText("");
         return false;
      } else {
         return s.equalsIgnoreCase("cancel");
      }
   }
}
