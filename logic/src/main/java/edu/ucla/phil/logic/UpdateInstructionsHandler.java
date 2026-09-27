package edu.ucla.phil.logic;

class UpdateInstructionsHandler extends DialogHandler {
   int result = 1;
   String instructionsUrl;

   UpdateInstructionsHandler(String s, String s1) {
      super(s);
      this.instructionsUrl = s1;
   }

   @Override
   boolean handleChoice(MessageDialog messagedialog) {
      String s = this.getSelectedAction(messagedialog);
      if (s == null) {
         return true;
      } else if (s.equalsIgnoreCase("backup")) {
         ServerConnection.backupWork(LogicProgram.backupName, null);
         return false;
      } else if (s.equalsIgnoreCase("update")) {
         DesktopLauncher.browse(this.instructionsUrl.replaceAll(" ", "%20"));
         return false;
      } else if (s.equalsIgnoreCase("quit")) {
         this.result = 3;
         return true;
      } else {
         return true;
      }
   }
}
