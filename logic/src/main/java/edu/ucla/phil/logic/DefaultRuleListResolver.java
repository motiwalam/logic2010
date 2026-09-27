package edu.ucla.phil.logic;

import java.util.Vector;

class DefaultRuleListResolver implements RuleLister {
   @Override
   public void listRules(String s, Vector vector, IntervalSet intervalset, boolean flag) {
      LogicProgram.listRules(s, vector, intervalset, flag);
   }
}
