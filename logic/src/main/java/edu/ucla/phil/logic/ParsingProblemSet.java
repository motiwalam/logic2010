package edu.ucla.phil.logic;

import javax.swing.SwingUtilities;

class ParsingProblemSet extends ProblemSet {
   ParsingProblemSet() {
      this.skipArgumentCheck = true;
   }

   @Override
   void restateProblems(LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
      LPParsing.restating = true;
      ProblemSet.ProblemRestateTask problemset$problemrestatetask = new ProblemSet.ProblemRestateTask(
         this.size(), new TaggedRecord(), new LPParsing(false), logicmodule$modulestartuptask
      );
      SwingUtilities.invokeLater(problemset$problemrestatetask);
   }

   @Override
   ProblemEntry createEntry(String s, boolean flag) {
      return new ParsingProblemEntry(s, flag);
   }

   @Override
   boolean hasWork(TaggedRecord taggedrecord) {
      return LPParsing.hasWork(taggedrecord);
   }

   @Override
   String getWork(TaggedRecord taggedrecord) {
      return LPParsing.getWork(taggedrecord);
   }

   @Override
   String removeWork(TaggedRecord taggedrecord) {
      return LPParsing.removeWork(taggedrecord);
   }

   @Override
   String getProblemStatement(TaggedRecord taggedrecord) {
      return LPParsing.getProblemStatement(taggedrecord);
   }

   @Override
   int getModuleIndex() {
      return 2;
   }
}
