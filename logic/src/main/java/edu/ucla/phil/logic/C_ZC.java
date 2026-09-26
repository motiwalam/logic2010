package edu.ucla.phil.logic;

import javax.swing.SwingUtilities;

class C_ZC extends ProblemSet {
   C_ZC() {
      this.f1080 = true;
   }

   @Override
   void m1099(LogicModule.C__A logicmodule$c__a) {
      LPParsing.restating = true;
      ProblemSet.C__A problemset$c__a = new ProblemSet.C__A(this.size(), new TaggedRecord(), new LPParsing(false), logicmodule$c__a);
      SwingUtilities.invokeLater(problemset$c__a);
   }

   @Override
   ProblemEntry m1102(String s, boolean flag) {
      return new C_l_D(s, flag);
   }

   @Override
   boolean m1103(TaggedRecord taggedrecord) {
      return LPParsing.hasWork(taggedrecord);
   }

   @Override
   String m1104(TaggedRecord taggedrecord) {
      return LPParsing.getWork(taggedrecord);
   }

   @Override
   String m1105(TaggedRecord taggedrecord) {
      return LPParsing.removeWork(taggedrecord);
   }

   @Override
   String m1106(TaggedRecord taggedrecord) {
      return LPParsing.getProblemStatement(taggedrecord);
   }

   @Override
   int m1107() {
      return 2;
   }
}
