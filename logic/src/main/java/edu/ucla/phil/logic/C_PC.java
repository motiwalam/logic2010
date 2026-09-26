package edu.ucla.phil.logic;

import javax.swing.SwingUtilities;

class C_PC extends ProblemSet {
   @Override
   void m1099(LogicModule.C__A logicmodule$c__a) {
      LPInvalidation.restating = true;
      ProblemSet.C__A problemset$c__a = new ProblemSet.C__A(this.size(), new TaggedRecord(), new LPInvalidation(false), logicmodule$c__a);
      SwingUtilities.invokeLater(problemset$c__a);
   }

   @Override
   ProblemEntry m1102(String s, boolean flag) {
      return new C_u_D(s, flag);
   }

   @Override
   boolean m1103(TaggedRecord taggedrecord) {
      return LPInvalidation.hasWork(taggedrecord);
   }

   @Override
   String m1104(TaggedRecord taggedrecord) {
      return LPInvalidation.getWork(taggedrecord);
   }

   @Override
   String m1105(TaggedRecord taggedrecord) {
      return LPInvalidation.removeWork(taggedrecord);
   }

   @Override
   String m1106(TaggedRecord taggedrecord) {
      return LPInvalidation.getProblemStatement(taggedrecord);
   }

   @Override
   int m1107() {
      return 1;
   }
}
