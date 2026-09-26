package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;

abstract class ProblemEntry {
   String name;
   int state;
   boolean f1121;
   boolean f1122;
   static final int f1123 = 0;
   static final int f1124 = 1;
   static final int f1125 = 2;
   static final int f1126 = 3;
   static final int f1127 = 4;
   static final String[] f1128 = new String[]{"N", "I", "C", "I", "U"};

   abstract int m513(String s);

   ProblemEntry(String s, boolean flag, Hashtable hashtable) {
      this.name = s;
      this.state = flag ? 4 : this.m513(s);
      this.f1121 = false;
      this.f1122 = m1817(s, hashtable);
   }

   static Hashtable m1813(Reader reader, Hashtable hashtable) {
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
            if (!TaggedRecord.isBlankOrComment(s) && (s1 = TaggedRecord.m1493(s)) != null) {
               hashtable1.put(s1.trim().toUpperCase(), Boolean.TRUE);
            }
         }

         scrambledreader.close();
      } catch (IOException ioexception) {
      }

      return hashtable1;
   }

   static Hashtable m1814(ProblemSet problemset, Hashtable hashtable) {
      Hashtable hashtable1 = new Hashtable();
      int i = problemset.size();

      for (int j = 0; j < i; j++) {
         ProblemEntry problementry = (ProblemEntry)problemset.get(j);
         if (problementry != null && problementry.name != null) {
            String s = TaggedRecord.m1493(problementry.name).trim().toUpperCase();
            if (hashtable == null || hashtable.get(s) == null) {
               hashtable1.put(s, Boolean.TRUE);
            }

            problementry.f1122 = m1817(problementry.name, hashtable1);
         }
      }

      return hashtable1;
   }

   static void m1815(ProblemSet problemset, Hashtable hashtable) {
      int i = problemset.size();

      for (int j = 0; j < i; j++) {
         ProblemEntry problementry = (ProblemEntry)problemset.get(j);
         if (problementry != null && problementry.name != null) {
            problementry.f1122 = m1817(problementry.name, hashtable);
         }
      }
   }

   static Hashtable m1816(String s, ProblemSet problemset) {
      Hashtable hashtable = null;
      ScrambledReader scrambledreader = LogicProgram.m1067(s, false, true);
      if (scrambledreader != null) {
         hashtable = m1813(scrambledreader, hashtable);
      }

      scrambledreader = LogicProgram.m1067(s, true, true);
      if (scrambledreader != null) {
         hashtable = m1813(scrambledreader, hashtable);
      }

      return m1814(problemset, hashtable);
   }

   static boolean m1817(String s, Hashtable hashtable) {
      String s1 = TaggedRecord.m1493(s);
      if (s1 == null) {
         return false;
      } else {
         s1 = s1.trim().toUpperCase();
         if (s1.startsWith("DEMO")) {
            return false;
         } else {
            return hashtable == null ? false : hashtable.get(s1) != null;
         }
      }
   }
}
