package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class RuleProperties extends Hashtable implements RulePropertySource {
   @Override
   public boolean hasProperty(Rule rule, String s) {
      if (s.equals("notConditional")) {
         if (!(rule instanceof SchematicRule)) {
            return false;
         } else {
            SchematicRule schematicrule2 = (SchematicRule)rule;
            if (schematicrule2.conclusion == null) {
               return true;
            } else {
               int j = schematicrule2.premises.length;
               return j == 0 ? getEquivalenceOrConditional(schematicrule2.conclusion) == null : j != 1;
            }
         }
      } else if (s.equals("notConditionalBC")) {
         if (!(rule instanceof SchematicRule)) {
            return false;
         } else {
            SchematicRule schematicrule1 = (SchematicRule)rule;
            if (schematicrule1.conclusion == null) {
               return true;
            } else {
               int i = schematicrule1.premises.length;
               if (i == 0) {
                  return getConditionalEquivalence(schematicrule1.conclusion) == null;
               } else {
                  return i == 1 ? !schematicrule1.conclusion.symbol.equals("<->") : true;
               }
            }
         }
      } else if (s.equals("biconditional")) {
         if (!(rule instanceof SchematicRule)) {
            return false;
         } else {
            SchematicRule schematicrule = (SchematicRule)rule;
            return schematicrule.premises != null && schematicrule.premises.length != 0 ? false : getEquivalence(schematicrule.conclusion, false) != null;
         }
      } else if (s.equals("hasConverse")) {
         return !(rule instanceof SchematicRule) ? false : this.getConverses((SchematicRule)rule) != null;
      } else {
         throw new IllegalArgumentException("unknown property: " + s);
      }
   }

   static Expression getEquivalenceOrConditional(Expression expression) {
      return getEquivalence(expression, true);
   }

   static Expression getEquivalence(Expression expression, boolean flag) {
      if (expression == null) {
         return null;
      } else if (expression.symbol.equals("->")) {
         return flag ? expression : null;
      } else {
         boolean flag1;
         for (flag1 = false; !expression.symbol.equals("<->"); expression = expression.getChild(1)) {
            if (!expression.symbol.equals("@")) {
               return null;
            }

            flag1 = true;
         }

         return flag1 ? expression.copy() : expression;
      }
   }

   static Expression getEquivalenceSide(Expression expression, int i) {
      Expression expression1 = getEquivalence(expression, true);
      return expression1 == null ? null : expression1.getChild(i);
   }

   static Expression getEquivalenceSide(Expression expression, boolean flag, int i) {
      Expression expression1 = getEquivalence(expression, flag);
      return expression1 == null ? null : expression1.getChild(i);
   }

   static Expression getConditionalEquivalence(Expression expression) {
      if (expression == null) {
         return null;
      } else {
         boolean flag;
         for (flag = false; !expression.symbol.equals("->"); expression = expression.getChild(1)) {
            if (expression.symbol.equals("<->")) {
               if (!expression.getChild(0).symbol.equals("<->") && !expression.getChild(1).symbol.equals("<->")) {
                  return null;
               }

               return flag ? expression.copy() : expression;
            }

            if (!expression.symbol.equals("@")) {
               return null;
            }

            flag = true;
         }

         if (!expression.getChild(1).symbol.equals("<->")) {
            return null;
         } else {
            return flag ? expression.copy() : expression;
         }
      }
   }

   static Expression getConditionalEquivalencePart(Expression expression, int i, int j) {
      Expression expression1 = getConditionalEquivalence(expression);
      return expression1 == null ? null : expression1.getChild(i).getChild(j);
   }

   @Override
   public boolean hasProperty(Integer integer, String s) {
      Theorem theorem = LogicProgram.getTheorem(integer);
      return theorem == null ? true : this.hasProperty(theorem, s);
   }

   Vector getConverses(SchematicRule schematicrule) {
      if (schematicrule.sourceTheorem != null && getEquivalence(schematicrule.sourceTheorem.conclusion, false) != null) {
         Vector vector1 = new Vector();
         vector1.addElement(schematicrule.sourceTheorem.name);
         return vector1;
      } else if (this.hasProperty(schematicrule, "biconditional")) {
         Vector vector = new Vector();
         vector.addElement(schematicrule.name);
         return vector;
      } else {
         return (Vector)this.get(schematicrule.name);
      }
   }

   void registerConverses(Rule rule) {
      SchematicRule[] aschematicrule = rule.getForms(this, "notConditional");
      int i = aschematicrule.length;

      for (int j = 0; j < i; j++) {
         SchematicRule schematicrule = aschematicrule[j];
         if (!this.hasProperty(schematicrule, "biconditional")) {
            Expression expression = getFromSide(schematicrule, false);
            Expression expression1 = getToSide(schematicrule, false);

            for (int k = 0; k < i; k++) {
               SchematicRule schematicrule1 = aschematicrule[k];
               boolean flag = this.hasProperty(schematicrule1, "biconditional");
               if (k >= j || flag) {
                  Expression expression2 = getFromSide(schematicrule1, false);
                  Expression expression3 = getToSide(schematicrule1, false);
                  if (this.isConversePair(expression2, expression3, expression, expression1)
                     || flag && this.isConversePair(expression3, expression2, expression, expression1)) {
                     this.addConverse(schematicrule, schematicrule1);
                  }
               }
            }
         }
      }
   }

   boolean isConversePair(Expression expression, Expression expression1, Expression expression2, Expression expression3) {
      SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
      BinderMap bindermap = new BinderMap();
      BoundVariableMap boundvariablemap = new BoundVariableMap();
      if (!expression.match(expression3, schemeinstantiation, bindermap)) {
         return false;
      } else if (!expression1.match(expression2, schemeinstantiation, bindermap)) {
         return false;
      } else if (!boundvariablemap.matchBinders(expression, expression3, bindermap)) {
         return false;
      } else {
         return !boundvariablemap.matchBinders(expression1, expression2, bindermap) ? false : schemeinstantiation.hasNoDeferredMatches();
      }
   }

   void addConverse(SchematicRule schematicrule, SchematicRule schematicrule1) {
      Vector vector;
      if ((vector = (Vector)this.get(schematicrule.name)) == null) {
         this.put(schematicrule.name, vector = new Vector());
      }

      if (!vector.contains(schematicrule1.name)) {
      }

      vector.addElement(schematicrule1.name);
      if (!this.hasProperty(schematicrule1, "biconditional")) {
         if ((vector = (Vector)this.get(schematicrule1.name)) == null) {
            this.put(schematicrule1.name, vector = new Vector());
         }

         if (!vector.contains(schematicrule.name)) {
            vector.addElement(schematicrule.name);
         }
      }
   }

   Vector getRulesWithConverse(RuleTable ruletable) {
      Vector vector = new Vector();
      Vector vector1 = ruletable.ruleNames;
      int i = vector1.size();

      for (int j = 0; j < i; j++) {
         String s = (String)vector1.elementAt(j);
         Rule rule = ruletable.getRule(s);
         if (rule.testProperty(this, "hasConverse", true)) {
            vector.addElement(s);
         }
      }

      return vector;
   }

   IntervalSet getTheoremsWithConverse(TheoremTable theoremtable) {
      IntervalSet intervalset = new IntervalSet();
      Enumeration enumeration = theoremtable.theoremNumbers.elements();

      while (enumeration.hasMoreElements()) {
         Integer integer = (Integer)enumeration.nextElement();
         Theorem theorem = theoremtable.getTheorem(integer);
         if (theorem.testProperty(this, "hasConverse", true)) {
            intervalset.union(IntervalSet.singleton(theorem.number));
         }
      }

      return intervalset;
   }

   static Expression getFromSide(SchematicRule schematicrule, boolean flag) {
      return schematicrule.premises.length == 0 ? getEquivalenceSide(schematicrule.conclusion, flag ? 1 : 0) : schematicrule.premises[0];
   }

   static Expression getConditionalFromSide(SchematicRule schematicrule, boolean flag, boolean flag1) {
      return schematicrule.premises.length == 0
         ? getConditionalEquivalencePart(schematicrule.conclusion, flag ? 0 : 1, flag1 ? 1 : 0)
         : schematicrule.conclusion.getChild(flag1 ? 1 : 0);
   }

   static Expression getToSide(SchematicRule schematicrule, boolean flag) {
      return schematicrule.premises.length == 0 ? getEquivalenceSide(schematicrule.conclusion, flag ? 0 : 1) : schematicrule.conclusion;
   }

   static Expression getConditionalToSide(SchematicRule schematicrule, boolean flag, boolean flag1) {
      return schematicrule.premises.length == 0
         ? getConditionalEquivalencePart(schematicrule.conclusion, flag ? 0 : 1, flag1 ? 0 : 1)
         : schematicrule.conclusion.getChild(flag1 ? 0 : 1);
   }

   @Override
   public Vector getProofs(SchematicRule schematicrule) {
      return null;
   }

   @Override
   public Vector getProofs(Integer integer) {
      return null;
   }

   @Override
   public String excludedProof() {
      return null;
   }

   @Override
   public boolean checkProof(String s) {
      return true;
   }
}
