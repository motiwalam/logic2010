package edu.ucla.phil.logic;

class SchemeDialogHandler extends DialogHandler implements SymbolizationConstants {
   SchemeDialogHandler(String s) {
      super(s);
   }

   @Override
   boolean handleChoice(MessageDialog messagedialog) {
      String s = this.getSelectedAction(messagedialog);
      SchemeEditor schemeeditor = (SchemeEditor)this.getProperty("scheme");
      LPSymbolizer lpsymbolizer = (LPSymbolizer)this.getProperty("symbolizer");
      if (schemeeditor == null) {
         return true;
      } else if (s == null) {
         return false;
      } else if (s.equalsIgnoreCase("ok")) {
         return true;
      } else if (s.equalsIgnoreCase("clear")) {
         schemeeditor.setScheme(":");
         return false;
      } else if (s.equalsIgnoreCase("open")) {
         String s1 = SymbolizationDialogs.chooseScheme(lpsymbolizer, schemeeditor);
         if (s1 != null) {
            schemeeditor.setScheme(s1);
         }

         return false;
      } else if (s.equalsIgnoreCase("cancel")) {
         return true;
      } else if (s.equalsIgnoreCase("help")) {
         MessageDialog.showMessage(SymbolizationMessages.get("symnot008"), null, null, null);
         return false;
      } else {
         return false;
      }
   }
}
