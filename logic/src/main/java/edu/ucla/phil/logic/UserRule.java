package edu.ucla.phil.logic;

import java.util.Vector;

class UserRule extends SchematicRule {
   String problemName;

   UserRule(String s) {
      this(s, s);
   }

   UserRule(String s, String s1) {
      super(s1);
      this.loadFromProblem(s);
   }

   Vector getSourceProblems() {
      if (this.problemName == null) {
         return null;
      } else {
         Vector vector = new Vector();
         vector.addElement(this.problemName);
         return vector;
      }
   }

   void loadFromProblem(String s) {
      TaggedRecord taggedrecord = new TaggedRecord(LPDerivation.problems.getRecord(s));
      this.problemName = taggedrecord.getName();
      this.parseForm(LPDerivation.getProblemStatement(taggedrecord));
   }
}
