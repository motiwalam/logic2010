package edu.ucla.phil.logic;

import javax.swing.SwingUtilities;

class C_j_A extends ProblemSet {
   @Override
   void m1099(LogicModule.C__A logicmodule$c__a) {
      LPRecognition.restating = true;
      ProblemSet.C__A problemset$c__a = new ProblemSet.C__A(this.size(), new TaggedRecord(), new LPRecognition(false), logicmodule$c__a);
      SwingUtilities.invokeLater(problemset$c__a);
   }

   @Override
   ProblemEntry m1102(String s, boolean flag) {
      return new C_ED(s, flag);
   }

   @Override
   boolean m1103(TaggedRecord taggedrecord) {
      return LPRecognition.hasWork(taggedrecord);
   }

   @Override
   String m1104(TaggedRecord taggedrecord) {
      return LPRecognition.getWork(taggedrecord);
   }

   @Override
   String m1105(TaggedRecord taggedrecord) {
      return LPRecognition.removeWork(taggedrecord);
   }

   @Override
   String m1106(TaggedRecord taggedrecord) {
      return LPRecognition.getProblemStatement(taggedrecord);
   }

   @Override
   int m1107() {
      return 3;
   }
}
