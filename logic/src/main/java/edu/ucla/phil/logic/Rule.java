package edu.ucla.phil.logic;

import java.util.Vector;

class Rule {
   String name;
   Vector components;
   Theorem sourceTheorem;
   String error = null;

   Rule(String s) {
      this(s, new Vector());
   }

   Rule(String s, Vector vector) {
      this.name = s;
      this.components = vector;
      this.sourceTheorem = null;
   }

   Rule(String s, String s1, RuleTable ruletable, TheoremTable theoremtable) {
      this(s);

      while (!s1.equals("")) {
         int i = s1.indexOf(".");
         String s2;
         if (i == -1) {
            s2 = s1.trim();
            s1 = "";
         } else {
            s2 = s1.substring(0, i).trim();
            s1 = s1.substring(i + 1);
         }

         Rule rule1 = ruletable.findRule(s2);
         if (rule1 == null) {
            Integer integer = parseTheoremNumber(s2);
            if (integer == null) {
               this.error = "could not find rule " + s2;
            } else {
               this.error = "could not find theorem number " + integer;
            }
         } else {
            this.components.addElement(rule1);
         }
      }
   }

   static Integer parseTheoremNumber(String s) {
      String s1 = s.toUpperCase();
      int i = s1.length();
      if (i >= 2 && s1.substring(0, 1).equals("T") && "123456789".indexOf(s1.substring(1, 2)) != -1) {
         try {
            return new Integer(s1.substring(1));
         } catch (NumberFormatException numberformatexception) {
            return null;
         }
      } else {
         return null;
      }
   }

   static Integer parseTheoremRuleNumber(String s) {
      String s1 = s.toUpperCase();
      int i = 3;
      int j = s1.length();
      if (j >= 3 && s1.substring(0, 2).equals("RT") && "123456789".indexOf(s1.charAt(2)) != -1) {
         while (i < j && "0123456789".indexOf(s1.charAt(i)) != -1) {
            i++;
         }

         try {
            return new Integer(s1.substring(2, i));
         } catch (NumberFormatException numberformatexception) {
            return null;
         }
      } else {
         return null;
      }
   }

   Rule findComponent(String s) {
      if (this.name.equals(s)) {
         return this;
      } else {
         int i = this.components == null ? 0 : this.components.size();

         for (int j = 0; j < i; j++) {
            Rule rule1 = this.getComponent(j).findComponent(s);
            if (rule1 != null) {
               return rule1;
            }
         }

         return null;
      }
   }

   String getRuleName() {
      return this.name;
   }

   void addComponent(Rule rule1) {
      if (this.components != null && rule1 != null) {
         this.components.addElement(rule1);
      }
   }

   int getComponentCount() {
      return this.components == null ? 0 : this.components.size();
   }

   Rule getComponent(int i) {
      return this.components == null ? null : (Rule)this.components.elementAt(i);
   }

   boolean testProperty(RulePropertySource rulepropertysource, String s, boolean flag) {
      if (this.sourceTheorem != null && this.sourceTheorem.testProperty(rulepropertysource, s, flag)) {
         return true;
      } else if (rulepropertysource.hasProperty(this, s)) {
         return true;
      } else {
         int i = this.components == null ? 0 : this.components.size();
         if (i == 0) {
            return false;
         } else {
            for (int j = 0; j < i; j++) {
               if (((Rule)this.components.elementAt(j)).testProperty(rulepropertysource, s, flag) == flag) {
                  return flag;
               }
            }

            return !flag;
         }
      }
   }

   boolean isProven(RulePropertySource rulepropertysource) {
      int i = this.components == null ? 0 : this.components.size();

      for (int j = 0; j < i; j++) {
         if (!((Rule)this.components.elementAt(j)).isProven(rulepropertysource)) {
            return false;
         }
      }

      return true;
   }

   boolean isAnyFormProven(RulePropertySource rulepropertysource) {
      int i = this.components == null ? 0 : this.components.size();

      for (int j = 0; j < i; j++) {
         if (((Rule)this.components.elementAt(j)).isAnyFormProven(rulepropertysource)) {
            return true;
         }
      }

      return false;
   }

   void collectForms(Vector vector, RulePropertySource rulepropertysource, String s) {
      if (rulepropertysource == null || !rulepropertysource.hasProperty(this, s)) {
         int i = this.getComponentCount();

         for (int j = 0; j < i; j++) {
            this.getComponent(j).collectForms(vector, rulepropertysource, s);
         }
      }
   }

   SchematicRule[] getForms(RulePropertySource rulepropertysource, String s) {
      Vector vector = new Vector();
      this.collectForms(vector, rulepropertysource, s);
      SchematicRule[] aschematicrule = new SchematicRule[vector.size()];
      vector.copyInto(aschematicrule);
      return aschematicrule;
   }

   SchematicRule[] getAllForms() {
      return this.getForms(null, null);
   }

   boolean includes(Rule rule1) {
      if (this.name.equals(rule1.name)) {
         return true;
      } else {
         int i = this.components == null ? 0 : this.components.size();

         for (int j = 0; j < i; j++) {
            Rule rule2 = (Rule)this.components.elementAt(j);
            if (rule2 != null && rule2.includes(rule1)) {
               return true;
            }
         }

         return false;
      }
   }

   boolean isMutuallyIncludedWithAny(Rule rule1, Vector vector) {
      int i = vector == null ? 0 : vector.size();

      for (int j = 0; j < i; j++) {
         Rule rule2 = (Rule)vector.elementAt(j);
         if (rule1.includes(rule2) && rule2.includes(this)) {
            return true;
         }
      }

      return false;
   }

   static Rule fromTheorem(Theorem theorem) {
      if (theorem == null) {
         return null;
      } else {
         String s = "RT" + theorem.number;
         Expression expression2 = theorem.conclusion;
         Vector vector;
         if (expression2.symbol.equals("<->")) {
            Expression expression = expression2.getChild(0);
            Expression expression1 = expression2.getChild(1);
            vector = new Vector();
            vector.addElement(attachTheorem(new SchematicRule(s + "L", new Expression[]{expression}, expression1), theorem));
            Expression[] aexpression = splitConjuncts(expression);
            if (aexpression.length > 1) {
               vector.addElement(attachTheorem(new SchematicRule(s + "LF", aexpression, expression1), theorem));
            }

            vector.addElement(attachTheorem(new SchematicRule(s + "R", new Expression[]{expression1}, expression), theorem));
            aexpression = splitConjuncts(expression1);
            if (aexpression.length > 1) {
               vector.addElement(attachTheorem(new SchematicRule(s + "RF", aexpression, expression), theorem));
            }
         } else {
            if (!expression2.symbol.equals("->")) {
               return attachTheorem(new SchematicRule(s, null, expression2), theorem);
            }

            Expression expression3 = expression2.getChild(0);
            Expression expression4 = expression2.getChild(1);
            Expression[] aexpression1 = splitConjuncts(expression3);
            if (aexpression1.length <= 1) {
               return attachTheorem(new SchematicRule(s, aexpression1, expression4), theorem);
            }

            vector = new Vector();
            vector.addElement(attachTheorem(new SchematicRule(s + "L", new Expression[]{expression3}, expression4), theorem));
            vector.addElement(attachTheorem(new SchematicRule(s + "LF", aexpression1, expression4), theorem));
         }

         return attachTheorem(new Rule(s, vector), theorem);
      }
   }

   static Rule attachTheorem(Rule rule, Theorem theorem) {
      rule.sourceTheorem = theorem;
      return rule;
   }

   static Expression[] splitConjuncts(Expression expression) {
      Vector vector = new Vector();
      collectConjuncts(expression, vector);
      Expression[] aexpression = new Expression[vector.size()];
      vector.copyInto(aexpression);
      return aexpression;
   }

   static void collectConjuncts(Expression expression, Vector vector) {
      if (expression.symbol.equals("&")) {
         collectConjuncts(expression.getChild(0), vector);
         collectConjuncts(expression.getChild(1), vector);
      } else {
         vector.addElement(expression);
      }
   }

   String getError() {
      return this.error;
   }

   @Override
   public String toString() {
      return this.format(".", ".:");
   }

   String format(String s, String s2) {
      String s1 = "";
      int i = this.getComponentCount();
      boolean flag = false;

      for (int j = 0; j < i; j++) {
         s1 = s1 + (flag ? s : "") + this.getComponent(j).name;
         flag = true;
      }

      return s1;
   }
}
