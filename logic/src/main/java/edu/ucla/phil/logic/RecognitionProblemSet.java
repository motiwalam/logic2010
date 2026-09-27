package edu.ucla.phil.logic;

import javax.swing.SwingUtilities;

class RecognitionProblemSet extends ProblemSet {
   @Override
   void restateProblems(LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
      LPRecognition.restating = true;
      ProblemSet.ProblemRestateTask problemset$problemrestatetask = new ProblemSet.ProblemRestateTask(
         this.size(), new TaggedRecord(), new LPRecognition(false), logicmodule$modulestartuptask
      );
      SwingUtilities.invokeLater(problemset$problemrestatetask);
   }

   @Override
   ProblemEntry createEntry(String s, boolean flag) {
      return new RecognitionProblemEntry(s, flag);
   }

   @Override
   boolean hasWork(TaggedRecord taggedrecord) {
      return LPRecognition.hasWork(taggedrecord);
   }

   @Override
   String getWork(TaggedRecord taggedrecord) {
      return LPRecognition.getWork(taggedrecord);
   }

   @Override
   String removeWork(TaggedRecord taggedrecord) {
      return LPRecognition.removeWork(taggedrecord);
   }

   @Override
   String getProblemStatement(TaggedRecord taggedrecord) {
      return LPRecognition.getProblemStatement(taggedrecord);
   }

   @Override
   int getModuleIndex() {
      return 3;
   }
}
