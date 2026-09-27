package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;

abstract class ProblemEntry {
   String name;
   int state;
   boolean hidden;
   boolean extraProblem;
   static final int STATE_NO_WORK = 0;
   static final int STATE_INCORRECT = 1;
   static final int STATE_CORRECT = 2;
   static final int STATE_INCOMPLETE = 3;
   static final int STATE_UNCHECKED = 4;
   static final String[] STATE_CODES = new String[]{"N", "I", "C", "I", "U"};

   abstract int computeState(String s);

   ProblemEntry(String s, boolean flag, Hashtable hashtable) {
      this.name = s;
      this.state = flag ? 4 : this.computeState(s);
      this.hidden = false;
      this.extraProblem = isListed(s, hashtable);
   }

   static Hashtable readProblemNames(Reader reader, Hashtable hashtable) {
      Hashtable hashtable1 = hashtable == null ? new Hashtable() : hashtable;
      ScrambledReader scrambledreader;
      if (reader instanceof ScrambledReader) {
         scrambledreader = (ScrambledReader)reader;
      } else {
         scrambledreader = new ScrambledReader(reader, LogicProgram.scrambleKey);
      }

      try {
         String s;
         while ((s = scrambledreader.readLine()) != null) {
            String s1;
            if (!TaggedRecord.isBlankOrComment(s) && (s1 = TaggedRecord.nameOf(s)) != null) {
               hashtable1.put(s1.trim().toUpperCase(), Boolean.TRUE);
            }
         }

         scrambledreader.close();
      } catch (IOException ioexception) {
      }

      return hashtable1;
   }

   static Hashtable findExtraProblems(ProblemSet problemset, Hashtable hashtable) {
      Hashtable hashtable1 = new Hashtable();
      int i = problemset.size();

      for (int j = 0; j < i; j++) {
         ProblemEntry problementry = (ProblemEntry)problemset.get(j);
         if (problementry != null && problementry.name != null) {
            String s = TaggedRecord.nameOf(problementry.name).trim().toUpperCase();
            if (hashtable == null || hashtable.get(s) == null) {
               hashtable1.put(s, Boolean.TRUE);
            }

            problementry.extraProblem = isListed(problementry.name, hashtable1);
         }
      }

      return hashtable1;
   }

   static void markExtraProblems(ProblemSet problemset, Hashtable hashtable) {
      int i = problemset.size();

      for (int j = 0; j < i; j++) {
         ProblemEntry problementry = (ProblemEntry)problemset.get(j);
         if (problementry != null && problementry.name != null) {
            problementry.extraProblem = isListed(problementry.name, hashtable);
         }
      }
   }

   static Hashtable findExtraProblems(String s, ProblemSet problemset) {
      Hashtable hashtable = null;
      ScrambledReader scrambledreader = LogicProgram.openProblemFile(s, false, true);
      if (scrambledreader != null) {
         hashtable = readProblemNames(scrambledreader, hashtable);
      }

      scrambledreader = LogicProgram.openProblemFile(s, true, true);
      if (scrambledreader != null) {
         hashtable = readProblemNames(scrambledreader, hashtable);
      }

      return findExtraProblems(problemset, hashtable);
   }

   static boolean isListed(String s, Hashtable hashtable) {
      String s1 = TaggedRecord.nameOf(s);
      if (s1 == null) {
         return false;
      } else {
         String s2 = s1.trim().toUpperCase();
         if (s2.startsWith("DEMO")) {
            return false;
         } else {
            return hashtable == null ? false : hashtable.get(s2) != null;
         }
      }
   }
}
