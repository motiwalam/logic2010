package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;
import javax.swing.SwingUtilities;

class C_h_C extends ProblemSet {
   Hashtable f1158;
   boolean f1159;

   C_h_C(boolean flag) {
      this.f1159 = flag;
      this.f1080 = true;
      this.f1158 = null;
   }

   @Override
   void m1099(LogicModule.C__A logicmodule$c__a) {
      LPSymbolizer.restating = true;
      ProblemSet.C__A problemset$c__a = new ProblemSet.C__A(this.size(), new TaggedRecord(), new C_d_C(null), this, logicmodule$c__a);
      SwingUtilities.invokeLater(problemset$c__a);
   }

   @Override
   synchronized int m1098(String s, Vector vector, boolean flag) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      int i = this.m1775(taggedrecord, vector, flag);
      if (i != -1 && this.f1158 != null) {
         String s1 = taggedrecord.valueAt(taggedrecord.indexOfTag('g'));
         if (s1 != null) {
            String s3;
            Vector vector1 = (Vector)this.f1158.get(s3 = s1.trim());
            boolean flag1 = vector1 == null;
            String s2 = taggedrecord.getName();
            if (flag1) {
               vector1 = new Vector();
            }

            vector1.addElement(new C_WD(s2, C_d_C.m1707(taggedrecord, this.f1159)));
            if (flag1) {
               this.f1158.put(s3, vector1);
            }
         }
      }

      return i;
   }

   @Override
   synchronized void m1101(int i) {
      TaggedRecord taggedrecord = new TaggedRecord(this.m1778(i));
      if (taggedrecord.m1497() == null && LPSymbolizer.getExerciseTitle(taggedrecord.getName()) == null) {
         String s = taggedrecord.valueAt(taggedrecord.indexOfTag('@'));
         if (s != null) {
            DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\.");
            delimitedtokenizer.m1132(s);

            while (true) {
               String s1 = delimitedtokenizer.m1135();
               if (s1 == null) {
                  break;
               }

               if (!s1.equals("")) {
                  LPSymbolizer.userKey.remove(s1);
               }
            }
         }
      }

      super.m1101(i);
   }

   @Override
   ProblemEntry m1102(String s, boolean flag) {
      return new C__C(s, this, flag);
   }

   @Override
   boolean m1103(TaggedRecord taggedrecord) {
      return LPSymbolizer.hasWork(taggedrecord);
   }

   @Override
   String m1104(TaggedRecord taggedrecord) {
      return LPSymbolizer.getWork(taggedrecord);
   }

   @Override
   String m1105(TaggedRecord taggedrecord) {
      return LPSymbolizer.removeWork(taggedrecord);
   }

   @Override
   String m1106(TaggedRecord taggedrecord) {
      return LPSymbolizer.getProblemStatement(taggedrecord);
   }

   @Override
   int m1107() {
      return 4;
   }
}
