package edu.ucla.phil.logic;

import java.util.Hashtable;

class Submission implements ResponseHandler {
   String ipAddress = SocketLineClient.getLocalIpString(".");
   int courseUid;
   int userUid;
   String passwordHash;
   ServerSession session;
   String evaluation;
   String problemMd5;
   String work;
   String problemName;
   String module;
   int helpCount;
   long duration;
   String timestamp;
   String submissionUid;
   String[] succeededNames;
   String[] failedNames;

   Submission(int i, int j, ServerSession serversession, String s) {
      this.courseUid = i;
      this.userUid = j;
      this.passwordHash = s;
      this.session = serversession;
      this.reset();
   }

   void reset() {
      this.evaluation = null;
      this.problemMd5 = null;
      this.work = null;
      this.problemName = null;
      this.module = null;
      this.helpCount = 0;
      this.duration = 0L;
      this.timestamp = null;
      this.submissionUid = null;
   }

   boolean isComplete() {
      return this.passwordHash != null
         && this.session != null
         && this.evaluation != null
         && this.problemMd5 != null
         && this.work != null
         && this.problemName != null
         && this.module != null;
   }

   String getLogRecord() {
      return this.submissionUid + ":" + this.ipAddress + ":" + this.timestamp;
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
