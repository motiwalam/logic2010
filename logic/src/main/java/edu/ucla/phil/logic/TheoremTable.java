package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Vector;

class TheoremTable extends Hashtable {
   C_n_F f1464 = new C_n_F();
   Hashtable f1465 = new Hashtable();

   void m2197(String s, Vector vector) {
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
      String s1 = theorem.m1381();
      if (s1 != null) {
         System.out.println("error in theorem T" + integer + ": " + s1);
      } else {
         if (vector != null) {
            this.f1465.put(integer, vector);
         }

         this.f1464.m1975(C_n_F.m1970(integer));
         this.put(integer, theorem);
      }
   }

   static TheoremTable m2198(Reader reader) {
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
                  theoremtable.m2197(s, vector);
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

   Theorem m2199(Integer integer) {
      return integer == null ? null : (Theorem)this.get(integer);
   }
}
