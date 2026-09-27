package edu.ucla.phil.logic;

import java.util.Hashtable;

public class ProblemDbRecord implements ResponseHandler {
   ServerSession session;
   String problemName;
   String problemText;
   String commonName;
   String comment;
   Integer version;

   String getProblemMd5() {
      return Scrambler.md5Base64(this.problemText.trim());
   }

   String getWebFormProblem() {
      return LogicProgram.translateSymbols(this.problemText, LogicConstants.maggie, LogicProgram.htmlSymbols);
   }

   String getTruncatedComment() {
      return this.comment != null && this.comment.length() > 255 ? this.comment.substring(0, 252) + "..." : this.comment;
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
