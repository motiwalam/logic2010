package edu.ucla.phil.logic;

class UserProblemDialogHandler extends DialogHandler implements SymbolizationConstants {
   UserProblemDialogHandler(String s) {
      super(s);
   }

   @Override
   boolean handleChoice(MessageDialog messagedialog) {
      String s = this.getSelectedAction(messagedialog);
      LPSymbolizer lpsymbolizer = (LPSymbolizer)this.getProperty("symbolizer");
      FormulaEntryField formulaentryfield = (FormulaEntryField)this.getProperty("edit");
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
