package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;
import javax.swing.SwingUtilities;

class SymbolizationProblemSet extends ProblemSet {
   Hashtable answerGroups;
   boolean useUserKey;

   SymbolizationProblemSet(boolean flag) {
      this.useUserKey = flag;
      this.skipArgumentCheck = true;
      this.answerGroups = null;
   }

   @Override
   void restateProblems(LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
      LPSymbolizer.restating = true;
      ProblemSet.ProblemRestateTask problemset$problemrestatetask = new ProblemSet.ProblemRestateTask(
         this.size(), new TaggedRecord(), new SymbolizationNode(null), this, logicmodule$modulestartuptask
      );
      SwingUtilities.invokeLater(problemset$problemrestatetask);
   }

   @Override
   synchronized int addProblem(String s, Vector vector, boolean flag) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      int i = this.addProblem(taggedrecord, vector, flag);
      if (i != -1 && this.answerGroups != null) {
         String s1 = taggedrecord.valueAt(taggedrecord.indexOfTag('g'));
         if (s1 != null) {
            String s3;
            Vector vector1 = (Vector)this.answerGroups.get(s3 = s1.trim());
            boolean flag1 = vector1 == null;
            String s2 = taggedrecord.getName();
            if (flag1) {
               vector1 = new Vector();
            }

            vector1.addElement(new AnswerSet(s2, SymbolizationNode.lookupAnswers(taggedrecord, this.useUserKey)));
            if (flag1) {
               this.answerGroups.put(s3, vector1);
            }
         }
      }

      return i;
   }

   @Override
   synchronized void removeProblem(int i) {
      TaggedRecord taggedrecord = new TaggedRecord(this.getRecordAt(i));
      if (taggedrecord.getOriginalName() == null && LPSymbolizer.getExerciseTitle(taggedrecord.getName()) == null) {
         String s = taggedrecord.valueAt(taggedrecord.indexOfTag('@'));
         if (s != null) {
            DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\.");
            delimitedtokenizer.setInput(s);

            while (true) {
               String s1 = delimitedtokenizer.nextToken();
               if (s1 == null) {
                  break;
               }

               if (!s1.equals("")) {
                  LPSymbolizer.userKey.remove(s1);
               }
            }
         }
      }

      super.removeProblem(i);
   }

   @Override
   ProblemEntry createEntry(String s, boolean flag) {
      return new SymbolizationEntry(s, this, flag);
   }

   @Override
   boolean hasWork(TaggedRecord taggedrecord) {
      return LPSymbolizer.hasWork(taggedrecord);
   }

   @Override
   String getWork(TaggedRecord taggedrecord) {
      return LPSymbolizer.getWork(taggedrecord);
   }

   @Override
   String removeWork(TaggedRecord taggedrecord) {
      return LPSymbolizer.removeWork(taggedrecord);
   }

   @Override
   String getProblemStatement(TaggedRecord taggedrecord) {
      return LPSymbolizer.getProblemStatement(taggedrecord);
   }

   @Override
   int getModuleIndex() {
      return 4;
   }
}
