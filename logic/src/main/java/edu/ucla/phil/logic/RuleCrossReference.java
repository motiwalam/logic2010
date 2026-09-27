package edu.ucla.phil.logic;

import java.util.Vector;

class RuleCrossReference {
   Vector ruleNames = null;
   IntervalSet theoremNumbers = null;
   ProblemSelector selector = null;

   RuleCrossReference() {
   }

   RuleCrossReference(String s, RuleLister rulelister) {
      this(s, rulelister, false);
   }

   RuleCrossReference(String s, RuleLister object, boolean flag) {
      this();
      int i = s.indexOf(58);
      if (object == null) {
         object = new DefaultRuleListResolver();
      }

      if (i == -1) {
         ((RuleLister)object).listRules(s, this.ruleNames = new Vector(), this.theoremNumbers = new IntervalSet(), flag);
         this.selector = new ProblemSelector().complement();
      } else {
         ((RuleLister)object).listRules(s.substring(0, i), this.ruleNames = new Vector(), this.theoremNumbers = new IntervalSet(), flag);
         this.selector = new ProblemSelector(s.substring(i + 1));
      }
   }

   RuleCrossReference withPrefix(String s) {
      this.selector.addPrefix(s);
      return this;
   }

   void applyTo(String s, Vector vector, IntervalSet intervalset) {
      if (this.selector != null && (s == null ? this.selector.hasFlag('u') : this.selector.contains(s))) {
         LogicProgram.appendAll(this.ruleNames, vector, true);
         intervalset.union(this.theoremNumbers);
      }
   }
}
