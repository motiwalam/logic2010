package edu.ucla.phil.logic;

import javax.swing.SwingUtilities;

class C_O extends ProblemSet {
   @Override
   void m1099(LogicModule.C__A logicmodule$c__a) {
      LPTruthAnalysis.restating = true;
      ProblemSet.C__A problemset$c__a = new ProblemSet.C__A(this.size(), new TaggedRecord(), new LPTruthAnalysis(false), logicmodule$c__a);
      SwingUtilities.invokeLater(problemset$c__a);
   }

   @Override
   ProblemEntry m1102(String s, boolean flag) {
      return new C_f_C(s, flag);
   }

   @Override
   boolean m1103(TaggedRecord taggedrecord) {
      return LPTruthAnalysis.hasWork(taggedrecord);
   }

   @Override
   String m1104(TaggedRecord taggedrecord) {
      return LPTruthAnalysis.getWork(taggedrecord);
   }

   @Override
   String m1105(TaggedRecord taggedrecord) {
      return LPTruthAnalysis.removeWork(taggedrecord);
   }

   @Override
   String m1106(TaggedRecord taggedrecord) {
      return LPTruthAnalysis.getProblemStatement(taggedrecord);
   }

   @Override
   int m1107() {
      return 5;
   }
}
