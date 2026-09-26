package edu.ucla.phil.logic;

class C_EF extends ProblemEntry {
   int f298;
   static final int f299 = 0;
   static final int f300 = 1;
   static final int f301 = 2;

   C_EF(String s, ProblemSet problemset) {
      super(s, true, null);
      if (problemset.m1768(s) == null) {
         this.f298 = 2;
      } else if (problemset.m1772(TaggedRecord.m1493(s)) == null) {
         this.f298 = 0;
      } else {
         this.f298 = 1;
      }
   }

   @Override
   int m513(String s) {
      return 0;
   }
}
