package edu.ucla.phil.logic;

import java.util.Hashtable;

class ProblemUpload implements ResponseHandler {
   ServerSession session;
   int userUid;
   int courseUid;
   int syntax;
   String problemName;
   String text;
   String webText;
   String type;
   String aux;
   String[] answers;
   String timestamp;
   String problemUid;
   String[] succeededNames;
   String[] failedNames;
   String resultText;

   public ProblemUpload(ServerSession serversession, int i, int j, int k) {
      this.session = serversession;
      this.userUid = i;
      this.courseUid = j;
      this.syntax = k;
      this.reset();
   }

   void reset() {
      this.problemName = null;
      this.text = null;
      this.webText = null;
      this.aux = null;
      this.type = null;
      this.answers = null;
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
