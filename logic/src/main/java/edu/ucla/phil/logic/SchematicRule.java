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
      this.m948(s1);
   }

   void m948(String s) {
      ArgumentParser argumentparser = new ArgumentParser(s);
      this.premises = argumentparser.f827;
      this.conclusion = argumentparser.f828;
      String s1 = argumentparser.m1384();
      if (s1 != null) {
         this.f823 = "parse error: " + s1;
      } else {
         int i = argumentparser.m1385();
         if (i != 0) {
            this.f823 = ArgumentParser.f832[i];
         }
      }
   }

   Expression[] m949() {
      return this.premises;
   }

   Expression m950() {
      return this.conclusion;
   }

   SchematicRule m951() {
      int i = this.premises.length;
      Expression[] aexpression = new Expression[i];

      for (int j = 0; j < i; j++) {
         aexpression[j] = this.premises[j].copy();
      }

      return new SchematicRule(this.f820, aexpression, this.conclusion.copy());
   }

   @Override
   boolean m952(C_w_E c_w_e) {
      Vector vector = this.m954(c_w_e);
      if (vector != null && !this.m1193(c_w_e, "weakAss", false)) {
         String s = c_w_e.excludedProof();
         int i = vector.size();

         for (int j = 0; j < i; j++) {
            String s1 = (String)vector.elementAt(j);
            if ((s == null || !s.equals(s1)) && c_w_e.checkProof(s1)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @Override
   boolean m953(C_w_E c_w_e) {
      return this.m952(c_w_e);
   }

   Vector m954(C_w_E c_w_e) {
      return this.f822 != null ? this.f822.m954(c_w_e) : c_w_e.getProofs(this);
   }

   @Override
   void m955(Vector vector, C_w_E c_w_e, String s) {
      if (c_w_e == null || !c_w_e.hasProperty(this, s)) {
         vector.addElement(this);
      }
   }

   int m956(SchematicRule[] aschematicrule) {
      int i = aschematicrule == null ? 0 : aschematicrule.length;

      for (int j = 0; j < i; j++) {
         if (this.f820.equals(aschematicrule[j].f820)) {
            return j;
         }
      }

      return -1;
   }

   String m957(int[] aint) {
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
   String m958(String s, String s1) {
      String s2 = "";
      boolean flag = false;

      for (int i = 0; i < this.premises.length; i++) {
         s2 = s2 + (flag ? s : "") + this.premises[i];
         flag = true;
      }

      return s2 + s1 + this.conclusion;
   }
}
