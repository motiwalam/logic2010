package edu.ucla.phil.logic;

class UpdatePromptHandler extends DialogHandler {
   int result = 1;
   UpdateInfo updateInfo;

   UpdatePromptHandler(String s, UpdateInfo updateinfo) {
      super(s);
      this.updateInfo = updateinfo;
   }

   @Override
   int getDefaultIndex() {
      return 0;
   }

   @Override
   boolean handleChoice(MessageDialog messagedialog) {
      String s = this.getSelectedAction(messagedialog);
      if (s == null) {
         return true;
      } else {
         if (s.equalsIgnoreCase("backup")) {
            if (ServerConnection.backupWork(LogicProgram.backupName, null)) {
               ServerConnection.backedUpForUpdate = true;
               this.result = ServerConnection.performUpdate(this.updateInfo);
            } else {
               this.result = 5;
            }
         } else if (s.equalsIgnoreCase("update")) {
            this.result = ServerConnection.performUpdate(this.updateInfo);
         }

         return true;
      }
   }
}
