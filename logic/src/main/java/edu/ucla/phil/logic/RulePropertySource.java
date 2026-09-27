package edu.ucla.phil.logic;

import java.util.Vector;

interface RulePropertySource {
   boolean hasProperty(Rule rule, String s);

   boolean hasProperty(Integer integer, String s);

   Vector getProofs(SchematicRule schematicrule);

   Vector getProofs(Integer integer);

   String excludedProof();

   boolean checkProof(String s);
}
