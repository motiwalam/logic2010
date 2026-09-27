package edu.ucla.phil.logic;

import java.util.Hashtable;

class UpdateInfo implements ResponseHandler {
   static final int METHOD_LOADER = 0;
   static final int METHOD_DIRECT = 1;
   static final int METHOD_MANUAL = 2;
   ServerUrl downloadUrl;
   String downloadText;
   ServerUrl loaderUrl;
   String loaderVersion;
   UserInfo user;
   boolean localUpdate;
   boolean backupRequired = false;
   int method = 0;
   ErrorRef error = null;

   @Override
   public void setError(String s, Hashtable hashtable) {
      if (this.error == null) {
         this.error = new ErrorRef(null);
      }

      this.error.setError(s, hashtable);
   }

   @Override
   public ErrorRef getError() {
      return this.error;
   }
}
