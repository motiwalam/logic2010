package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Vector;

class RuleTable extends Hashtable {
   TheoremTable f1468;
   Vector f1469;
   Hashtable f1470;
   Hashtable f1471;
   RuleProperties f1472;

   RuleTable(TheoremTable theoremtable) {
      this.f1468 = theoremtable;
      this.f1469 = new Vector();
      this.f1470 = new Hashtable();
      this.f1471 = new Hashtable();
      this.f1472 = new RuleProperties();
   }

   void m2200(String s, Vector vector) {
      int i = s.length();
      int j = 0;

      while (j < i && Character.isWhitespace(s.charAt(j))) {
         j++;
      }

      int k = j;

      while (k < i && !Character.isWhitespace(s.charAt(k))) {
         k++;
      }

      String s1 = s.substring(j, k);
      String s2 = s.substring(k);
      if (this.m2203(s1) != null) {
         System.out.println("redefinition of rule " + s1);
      } else {
         Object object;
         if (s2.indexOf(".:") == -1) {
            object = new Rule(s1, s2, this, this.f1468);
         } else {
            object = new SchematicRule(s1, s2);
         }

         String s3 = ((Rule)object).m1381();
         if (s3 != null) {
            System.out.println("error in rule " + s1 + ": " + s3);
         } else {
            this.f1472.m2068((Rule)object);
            if (vector != null) {
               this.f1471.put(s1, vector);
            }

            this.m2202((Rule)object);
         }
      }
   }

   static RuleTable m2201(Reader reader, TheoremTable theoremtable) {
      if (reader == null) {
         return null;
      } else {
         ScrambledReader scrambledreader;
         if (reader instanceof ScrambledReader) {
            scrambledreader = (ScrambledReader)reader;
         } else {
            scrambledreader = new ScrambledReader(reader, LogicProgram.scrambleKey);
         }

         RuleTable ruletable = new RuleTable(theoremtable);

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
                  ruletable.m2200(s, vector);
                  vector = null;
               }
            }
         } catch (IOException ioexception1) {
            ruletable = null;
         } finally {
            try {
               scrambledreader.close();
            } catch (IOException ioexception) {
            }
         }

         return ruletable;
      }
   }

   void m2202(Rule rule) {
      this.put(rule.f820.toUpperCase(), rule);
      this.f1469.addElement(rule.f820);
   }

   Rule m2203(String s) {
      return (Rule)this.get(s.toUpperCase());
   }

   Theorem m2204(Integer integer) {
      return this.f1468 == null ? null : this.f1468.m2199(integer);
   }

   Rule m2205(String s) {
      Integer integer = Rule.m1366(s);
      if (integer != null) {
         return this.m2204(integer);
      } else {
         integer = Rule.m1367(s);
         if (integer != null) {
            synchronized (this.f1470) {
               Rule rule = (Rule)this.f1470.get(integer);
               if (rule == null) {
                  rule = Rule.m1377(this.m2204(integer));
                  if (rule == null) {
                     return null;
                  }

                  this.f1470.put(integer, rule);
               }

               return rule.m1368(s);
            }
         } else {
            return this.m2203(s);
         }
      }
   }

   void m2206(Rule rule) {
      this.remove(rule.f820.toUpperCase());
      this.f1469.removeElement(rule.f820);
   }
}
