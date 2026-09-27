package edu.ucla.phil.logic;

import java.util.Vector;

class SchematicRule extends Rule {
   Expression[] premises = new Expression[0];
   Expression conclusion = null;

   SchematicRule(String s) {
      super(s, null);
   }

   SchematicRule(String s, Expression[] aexpression, Expression expression) {
      this(s);
      if (aexpression != null) {
         this.premises = aexpression;
      }

      this.conclusion = expression;
   }

   SchematicRule(String s, String s1) {
      this(s);
      this.parseForm(s1);
   }

   void parseForm(String s) {
      ArgumentParser argumentparser = new ArgumentParser(s);
      this.premises = argumentparser.premises;
      this.conclusion = argumentparser.conclusion;
      String s1 = argumentparser.getUnparsedText();
      if (s1 != null) {
         this.error = "parse error: " + s1;
      } else {
         int i = argumentparser.getErrorCode();
         if (i != 0) {
            this.error = ArgumentParser.ERROR_MESSAGES[i];
         }
      }
   }

   Expression[] getPremises() {
      return this.premises;
   }

   Expression getConclusion() {
      return this.conclusion;
   }

   SchematicRule copyRule() {
      int i = this.premises.length;
      Expression[] aexpression = new Expression[i];

      for (int j = 0; j < i; j++) {
         aexpression[j] = this.premises[j].copy();
      }

      return new SchematicRule(this.name, aexpression, this.conclusion.copy());
   }

   @Override
   boolean isProven(RulePropertySource rulepropertysource) {
      Vector vector = this.getProofProblems(rulepropertysource);
      if (vector != null && !this.testProperty(rulepropertysource, "weakAss", false)) {
         String s = rulepropertysource.excludedProof();
         int i = vector.size();

         for (int j = 0; j < i; j++) {
            String s1 = (String)vector.elementAt(j);
            if ((s == null || !s.equals(s1)) && rulepropertysource.checkProof(s1)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @Override
   boolean isAnyFormProven(RulePropertySource rulepropertysource) {
      return this.isProven(rulepropertysource);
   }

   Vector getProofProblems(RulePropertySource rulepropertysource) {
      return this.sourceTheorem != null ? this.sourceTheorem.getProofProblems(rulepropertysource) : rulepropertysource.getProofs(this);
   }

   @Override
   void collectForms(Vector vector, RulePropertySource rulepropertysource, String s) {
      if (rulepropertysource == null || !rulepropertysource.hasProperty(this, s)) {
         vector.addElement(this);
      }
   }

   int indexByName(SchematicRule[] aschematicrule) {
      int i = aschematicrule == null ? 0 : aschematicrule.length;

      for (int j = 0; j < i; j++) {
         if (this.name.equals(aschematicrule[j].name)) {
            return j;
         }
      }

      return -1;
   }

   String formatInOrder(int[] aint) {
      String s = "";
      boolean flag = false;
      int i = Math.min(this.premises.length, aint.length);

      for (int j = 0; j < i; j++) {
         s = s + (flag ? "." : "") + this.premises[aint[j]];
         flag = true;
      }

      return s + ".:" + this.conclusion;
   }

   @Override
   String format(String s, String s1) {
      String s2 = "";
      boolean flag = false;

      for (int i = 0; i < this.premises.length; i++) {
         s2 = s2 + (flag ? s : "") + this.premises[i];
         flag = true;
      }

      return s2 + s1 + this.conclusion;
   }
}
