package edu.ucla.phil.logic;

class BackupEntry {
   String key;
   String date;
   int backupId;

   BackupEntry(String s, String s1, int i) {
      this.key = s;
      this.date = s1;
      this.backupId = i;
   }

   boolean isValid() {
      return this.key != null && this.date != null;
   }

   void clear() {
      this.key = null;
      this.date = null;
   }
}
