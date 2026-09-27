package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.SwingUtilities;

class DerivationProblemSet extends ProblemSet {
   Hashtable ruleProofIndex = null;

   synchronized void indexRuleProofs(TaggedRecord taggedrecord) {
      String s = taggedrecord.getName();
      String s1 = LPDerivation.getProblemRuleProven(taggedrecord);
      if (s != null && s1 != null) {
         Vector vector = new Vector();
         IntervalSet intervalset = new IntervalSet();
         LPDerivation.listRules(s1, vector, intervalset, false);
         if (this.ruleProofIndex == null) {
            this.ruleProofIndex = new Hashtable();
         }

         Enumeration enumeration = vector.elements();

         while (enumeration.hasMoreElements()) {
            Rule rule = LogicProgram.ruleTable.getRule((String)enumeration.nextElement());
            if (rule != null) {
               SchematicRule[] aschematicrule = rule.getAllForms();
               int i = aschematicrule.length;

               for (int j = 0; j < i; j++) {
                  String s2 = aschematicrule[j].name;
                  Vector vector1 = (Vector)this.ruleProofIndex.get(s2);
                  if (vector1 == null) {
                     vector1 = new Vector();
                     vector1.addElement(s);
                     this.ruleProofIndex.put(s2, vector1);
                  } else if (!vector1.contains(s)) {
                     vector1.addElement(s);
                  }
               }
            }
         }

         enumeration = intervalset.elements();

         while (enumeration.hasMoreElements()) {
            Theorem theorem = LogicProgram.getTheorem((Integer)enumeration.nextElement());
            if (theorem != null) {
               Integer integer = theorem.number;
               Vector vector2 = (Vector)this.ruleProofIndex.get(integer);
               if (vector2 == null) {
                  vector2 = new Vector();
                  vector2.addElement(s);
                  this.ruleProofIndex.put(integer, vector2);
               }

               if (!vector2.contains(s)) {
                  vector2.addElement(s);
               }
            }
         }
      }
   }

   @Override
   synchronized int addProblem(String s, Vector vector, boolean flag) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      int i = this.addProblem(taggedrecord, vector, flag);
      if (i != -1 && this.ruleProofIndex != null) {
         this.indexRuleProofs(taggedrecord);
      }

      return i;
   }

   @Override
   void restateProblems(LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
      LPDerivation.restating = true;
      ProblemSet.ProblemRestateTask problemset$problemrestatetask = new ProblemSet.ProblemRestateTask(
         this.size(), new TaggedRecord(), new LPDerivation(false), logicmodule$modulestartuptask
      );
      SwingUtilities.invokeLater(problemset$problemrestatetask);
   }

   synchronized void rebuildUserRules() {
      int i = this.size();
      LPDerivation.userRules = new RuleTable(null);

      for (int j = 0; j < i; j++) {
         TaggedRecord taggedrecord = new TaggedRecord(this.getRecordAt(j));
         String s = taggedrecord.getName();
         if (s != null && s.toUpperCase().startsWith("UR")) {
            UserRule userrule = new UserRule(s);
            if (userrule.error == null) {
               LPDerivation.userRules.addRule(userrule);
            }
         }
      }
   }

   @Override
   synchronized void removeProblem(int i) {
      TaggedRecord taggedrecord = new TaggedRecord(this.getRecordAt(i));
      String s = taggedrecord.getName();
      if (s != null && s.toUpperCase().equals("UR")) {
         UserRule userrule = (UserRule)LPDerivation.userRules.getRule(s);
         if (userrule != null) {
            LPDerivation.userRules.removeRule(userrule);
         }
      }

      super.removeProblem(i);
   }

   @Override
   String getSearchNote(TaggedRecord taggedrecord) {
      String s = LPDerivation.getProblemRuleProven(taggedrecord);
      if (s != null && !s.trim().equals("")) {
         StringBuffer stringbuffer = new StringBuffer("proves ");
         String[] astring = s.trim().split("\\s*\\.\\s*");

         for (int i = 0; i < astring.length; i++) {
            if (i > 0) {
               stringbuffer.append(", ");
            }

            stringbuffer.append(astring[i]);
            Rule rule = SchematicRule.parseTheoremNumber(astring[i]) == null ? LPDerivation.getRule(astring[i]) : null;
            if (rule != null) {
               SchematicRule[] aschematicrule = rule.getAllForms();
               if (aschematicrule.length > 1 || aschematicrule.length == 1 && !aschematicrule[0].name.equals(astring[i])) {
                  stringbuffer.append(" (");

                  for (int j = 0; j < aschematicrule.length; j++) {
                     stringbuffer.append(j == 0 ? "" : ", ").append(aschematicrule[j].name);
                  }

                  stringbuffer.append(")");
               }
            }
         }

         return stringbuffer.toString();
      } else {
         return null;
      }
   }

   @Override
   ProblemEntry createEntry(String s, boolean flag) {
      return new DerivationProblemEntry(s, flag);
   }

   @Override
   boolean hasWork(TaggedRecord taggedrecord) {
      return LPDerivation.hasWork(taggedrecord);
   }

   @Override
   String getWork(TaggedRecord taggedrecord) {
      return LPDerivation.getWork(taggedrecord);
   }

   @Override
   String removeWork(TaggedRecord taggedrecord) {
      return LPDerivation.removeWork(taggedrecord);
   }

   @Override
   String getProblemStatement(TaggedRecord taggedrecord) {
      return LPDerivation.getProblemStatement(taggedrecord);
   }

   @Override
   int getModuleIndex() {
      return 0;
   }
}
