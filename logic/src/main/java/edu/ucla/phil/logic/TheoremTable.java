package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Vector;

class TheoremTable extends Hashtable {
   IntervalSet theoremNumbers = new IntervalSet();
   Hashtable headings = new Hashtable();

   void addTheoremLine(String s, Vector vector) {
      int i = s.length();
      int j = 0;

      while (j < i && Character.isWhitespace(s.charAt(j))) {
         j++;
      }

      int k = j;

      while (k < i && !Character.isWhitespace(s.charAt(k))) {
         k++;
      }

      Integer integer;
      try {
         integer = Integer.valueOf(s.substring(j, k));
      } catch (NumberFormatException numberformatexception) {
         System.out.println("Bad theorem number format: " + s.substring(j, k));
         return;
      }

      Theorem theorem = new Theorem(integer, s.substring(k));
      String s1 = theorem.getError();
      if (s1 != null) {
         System.out.println("error in theorem T" + integer + ": " + s1);
      } else {
         if (vector != null) {
            this.headings.put(integer, vector);
         }

         this.theoremNumbers.union(IntervalSet.singleton(integer));
         this.put(integer, theorem);
      }
   }

   static TheoremTable read(Reader reader) {
      if (reader == null) {
         return null;
      } else {
         ScrambledReader scrambledreader;
         if (reader instanceof ScrambledReader) {
            scrambledreader = (ScrambledReader)reader;
         } else {
            scrambledreader = new ScrambledReader(reader, LogicProgram.scrambleKey);
         }

         TheoremTable theoremtable = new TheoremTable();

         try {
            Vector vector = null;

            String s;
            while ((s = scrambledreader.readLine()) != null) {
               if (TaggedRecord.isBlankOrComment(s)) {
                  if (s.indexOf("#-") == 0) {
                     if (vector == null) {
                        vector = new Vector();
                     }

                     vector.addElement(s.substring(2));
                  }
               } else {
                  theoremtable.addTheoremLine(s, vector);
                  vector = null;
               }
            }
         } catch (IOException ioexception1) {
            theoremtable = null;
         } finally {
            try {
               scrambledreader.close();
            } catch (IOException ioexception) {
            }
         }

         return theoremtable;
      }
   }

   Theorem getTheorem(Integer integer) {
      return integer == null ? null : (Theorem)this.get(integer);
   }
}
