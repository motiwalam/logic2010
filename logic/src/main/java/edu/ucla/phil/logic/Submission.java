package edu.ucla.phil.logic;

import java.util.Hashtable;

class Submission implements ResponseHandler {
   String f1 = C_GE.m644(".");
   int f2;
   int f3;
   String f4;
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
   String[] f15;
   String[] f16;

   Submission(int i, int j, ServerSession serversession, String s) {
      this.f2 = i;
      this.f3 = j;
      this.f4 = s;
      this.session = serversession;
      this.m1();
   }

   void m1() {
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

   boolean m2() {
      return this.f4 != null
         && this.session != null
         && this.evaluation != null
         && this.problemMd5 != null
         && this.work != null
         && this.problemName != null
         && this.module != null;
   }

   String m3() {
      return this.submissionUid + ":" + this.f1 + ":" + this.timestamp;
   }

   @Override
   public void m4(String s, Hashtable hashtable) {
      this.session.m4(s, hashtable);
   }

   @Override
   public ErrorRef m5() {
      return this.session.m5();
   }
}
