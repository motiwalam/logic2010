package edu.ucla.phil.logic;

import java.util.Hashtable;

class BackupRequest implements ResponseHandler {
   String ipAddress = SocketLineClient.getLocalIpString(".");
   int userUid;
   String passwordHash;
   ServerSession session;
   String backupKey;
   String data;
   String unusedText;
   int selectedBackupId;
   BackupEntry[] backups;

   BackupRequest(int i, ServerSession serversession, String s) {
      this.userUid = i;
      this.passwordHash = s;
      this.session = serversession;
      this.reset();
   }

   void reset() {
      this.data = null;
      this.backupKey = null;
      this.unusedText = null;
      this.selectedBackupId = 0;
      this.backups = null;
   }

   boolean isReady() {
      return this.passwordHash != null && this.session != null;
   }

   int countBackupsForKey() {
      return this.countBackups(this.backupKey);
   }

   int countBackups(String s) {
      if (s == null) {
         return 0;
      } else {
         int i = this.backups == null ? 0 : this.backups.length;
         int j = 0;

         for (int k = 0; k < i; k++) {
            BackupEntry backupentry = this.backups[k];
            if (backupentry.isValid() && backupentry.key.equalsIgnoreCase(s)) {
               j++;
            }
         }

         return j;
      }
   }

   boolean forgetSelectedBackup() {
      int i = this.backups == null ? 0 : this.backups.length;

      for (int j = 0; j < i; j++) {
         BackupEntry backupentry = this.backups[j];
         if (backupentry.isValid() && backupentry.backupId == this.selectedBackupId) {
            backupentry.clear();
            return true;
         }
      }

      return false;
   }

   boolean selectNewestBackup() {
      if (this.backupKey == null) {
         return false;
      } else {
         BackupEntry backupentry = null;
         int i = this.backups == null ? 0 : this.backups.length;

         for (int j = 0; j < i; j++) {
            BackupEntry backupentry1 = this.backups[j];
            if (backupentry1.isValid()
               && backupentry1.key.equalsIgnoreCase(this.backupKey)
               && (backupentry == null || backupentry1.date.compareTo(backupentry.date) > 0)) {
               backupentry = backupentry1;
            }
         }

         if (backupentry == null) {
            return false;
         } else {
            this.selectedBackupId = backupentry.backupId;
            return true;
         }
      }
   }

   boolean selectOldestBackup() {
      if (this.backupKey == null) {
         return false;
      } else {
         BackupEntry backupentry = null;
         int i = this.backups == null ? 0 : this.backups.length;

         for (int j = 0; j < i; j++) {
            BackupEntry backupentry1 = this.backups[j];
            if (backupentry1.isValid()
               && backupentry1.key.equalsIgnoreCase(this.backupKey)
               && (backupentry == null || backupentry1.date.compareTo(backupentry.date) < 0)) {
               backupentry = backupentry1;
            }
         }

         if (backupentry == null) {
            return false;
         } else {
            this.selectedBackupId = backupentry.backupId;
            return true;
         }
      }
   }

   boolean pruneBackups(int i, BusyIndicator busyindicator, NetworkTask networktask) {
      boolean flag = true;
      int j = this.countBackupsForKey();
      if (busyindicator != null) {
         busyindicator.setBusy(true);
      }

      while (j > i) {
         flag = false;
         if (!this.selectOldestBackup() || !ServerConnection.deleteBackup(this, busyindicator, networktask) || !this.forgetSelectedBackup()) {
            break;
         }

         flag = true;
         j--;
      }

      if (busyindicator != null) {
         busyindicator.setBusy(false);
      }

      return flag;
   }

   @Override
   public void setError(String s, Hashtable hashtable) {
      this.session.setError(s, hashtable);
   }

   @Override
   public ErrorRef getError() {
      return this.session.getError();
   }
}
