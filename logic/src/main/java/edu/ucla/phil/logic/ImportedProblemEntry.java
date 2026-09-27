package edu.ucla.phil.logic;

class ImportedProblemEntry extends ProblemEntry {
   int importStatus;
   static final int STATUS_NEW = 0;
   static final int STATUS_DUPLICATE = 1;
   static final int STATUS_INVALID = 2;

   ImportedProblemEntry(String s, ProblemSet problemset) {
      super(s, true, null);
      if (problemset.getStatement(s) == null) {
         this.importStatus = 2;
      } else if (problemset.getEntry(TaggedRecord.nameOf(s)) == null) {
         this.importStatus = 0;
      } else {
         this.importStatus = 1;
      }
   }

   @Override
   int computeState(String s) {
      return 0;
   }
}
