package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Vector;

class RuleTable extends Hashtable {
   TheoremTable theorems;
   Vector ruleNames;
   Hashtable theoremRuleCache;
   Hashtable headings;
   RuleProperties properties;

   RuleTable(TheoremTable theoremtable) {
      this.theorems = theoremtable;
      this.ruleNames = new Vector();
      this.theoremRuleCache = new Hashtable();
      this.headings = new Hashtable();
      this.properties = new RuleProperties();
   }

   void addRuleLine(String s, Vector vector) {
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
      if (this.getRule(s1) != null) {
         System.out.println("redefinition of rule " + s1);
      } else {
         Object object;
         if (s2.indexOf(".:") == -1) {
            object = new Rule(s1, s2, this, this.theorems);
         } else {
            object = new SchematicRule(s1, s2);
         }

         String s3 = ((Rule)object).getError();
         if (s3 != null) {
            System.out.println("error in rule " + s1 + ": " + s3);
         } else {
            this.properties.registerConverses((Rule)object);
            if (vector != null) {
               this.headings.put(s1, vector);
            }

            this.addRule((Rule)object);
         }
      }
   }

   static RuleTable read(Reader reader, TheoremTable theoremtable) {
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
                  ruletable.addRuleLine(s, vector);
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

   void addRule(Rule rule) {
      this.put(rule.name.toUpperCase(), rule);
      this.ruleNames.addElement(rule.name);
   }

   Rule getRule(String s) {
      return (Rule)this.get(s.toUpperCase());
   }

   Theorem getTheorem(Integer integer) {
      return this.theorems == null ? null : this.theorems.getTheorem(integer);
   }

   Rule findRule(String s) {
      Integer integer = Rule.parseTheoremNumber(s);
      if (integer != null) {
         return this.getTheorem(integer);
      } else {
         integer = Rule.parseTheoremRuleNumber(s);
         if (integer != null) {
            synchronized (this.theoremRuleCache) {
               Rule rule = (Rule)this.theoremRuleCache.get(integer);
               if (rule == null) {
                  rule = Rule.fromTheorem(this.getTheorem(integer));
                  if (rule == null) {
                     return null;
                  }

                  this.theoremRuleCache.put(integer, rule);
               }

               return rule.findComponent(s);
            }
         } else {
            return this.getRule(s);
         }
      }
   }

   void removeRule(Rule rule) {
      this.remove(rule.name.toUpperCase());
      this.ruleNames.removeElement(rule.name);
   }
}
