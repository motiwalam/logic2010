package edu.ucla.phil.logic;

import javax.swing.SwingUtilities;

class InvalidityProblemSet extends ProblemSet {
   @Override
   void restateProblems(LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
      LPInvalidation.restating = true;
      ProblemSet.ProblemRestateTask problemset$problemrestatetask = new ProblemSet.ProblemRestateTask(
         this.size(), new TaggedRecord(), new LPInvalidation(false), logicmodule$modulestartuptask
      );
      SwingUtilities.invokeLater(problemset$problemrestatetask);
   }

   @Override
   ProblemEntry createEntry(String s, boolean flag) {
      return new InvalidityProblemEntry(s, flag);
   }

   @Override
   boolean hasWork(TaggedRecord taggedrecord) {
      return LPInvalidation.hasWork(taggedrecord);
   }

   @Override
   String getWork(TaggedRecord taggedrecord) {
      return LPInvalidation.getWork(taggedrecord);
   }

   @Override
   String removeWork(TaggedRecord taggedrecord) {
      return LPInvalidation.removeWork(taggedrecord);
   }

   @Override
   String getProblemStatement(TaggedRecord taggedrecord) {
      return LPInvalidation.getProblemStatement(taggedrecord);
   }

   @Override
   int getModuleIndex() {
      return 1;
   }
}
