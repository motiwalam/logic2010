package edu.ucla.phil.logic;

import javax.swing.SwingUtilities;

class TruthProblemSet extends ProblemSet {
   @Override
   void restateProblems(LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
      LPTruthAnalysis.restating = true;
      ProblemSet.ProblemRestateTask problemset$problemrestatetask = new ProblemSet.ProblemRestateTask(
         this.size(), new TaggedRecord(), new LPTruthAnalysis(false), logicmodule$modulestartuptask
      );
      SwingUtilities.invokeLater(problemset$problemrestatetask);
   }

   @Override
   ProblemEntry createEntry(String s, boolean flag) {
      return new TruthProblemEntry(s, flag);
   }

   @Override
   boolean hasWork(TaggedRecord taggedrecord) {
      return LPTruthAnalysis.hasWork(taggedrecord);
   }

   @Override
   String getWork(TaggedRecord taggedrecord) {
      return LPTruthAnalysis.getWork(taggedrecord);
   }

   @Override
   String removeWork(TaggedRecord taggedrecord) {
      return LPTruthAnalysis.removeWork(taggedrecord);
   }

   @Override
   String getProblemStatement(TaggedRecord taggedrecord) {
      return LPTruthAnalysis.getProblemStatement(taggedrecord);
   }

   @Override
   int getModuleIndex() {
      return 5;
   }
}
