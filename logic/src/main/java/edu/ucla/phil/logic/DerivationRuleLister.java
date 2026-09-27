package edu.ucla.phil.logic;

import java.util.Vector;

class DerivationRuleLister implements RuleLister {
   @Override
   public void listRules(String s, Vector vector, IntervalSet intervalset, boolean flag) {
      LPDerivation.listRules(s, vector, intervalset, flag);
   }
}
