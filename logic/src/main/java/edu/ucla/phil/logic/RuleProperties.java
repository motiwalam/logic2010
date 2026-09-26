package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class RuleProperties extends Hashtable implements C_w_E {
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
               return j == 0 ? m2061(schematicrule2.conclusion) == null : j != 1;
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
                  return m2065(schematicrule1.conclusion) == null;
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
            return schematicrule.premises != null && schematicrule.premises.length != 0 ? false : m2062(schematicrule.conclusion, false) != null;
         }
      } else if (s.equals("hasConverse")) {
         return !(rule instanceof SchematicRule) ? false : this.m2067((SchematicRule)rule) != null;
      } else {
         throw new IllegalArgumentException("unknown property: " + s);
      }
   }

   static Expression m2061(Expression expression) {
      return m2062(expression, true);
   }

   static Expression m2062(Expression expression, boolean flag) {
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

   static Expression m2063(Expression expression, int i) {
      Expression expression1 = m2062(expression, true);
      return expression1 == null ? null : expression1.getChild(i);
   }

   static Expression m2064(Expression expression, boolean flag, int i) {
      Expression expression1 = m2062(expression, flag);
      return expression1 == null ? null : expression1.getChild(i);
   }

   static Expression m2065(Expression expression) {
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

   static Expression m2066(Expression expression, int i, int j) {
      Expression expression1 = m2065(expression);
      return expression1 == null ? null : expression1.getChild(i).getChild(j);
   }

   @Override
   public boolean hasProperty(Integer integer, String s) {
      Theorem theorem = LogicProgram.m1025(integer);
      return theorem == null ? true : this.hasProperty(theorem, s);
   }

   Vector m2067(SchematicRule schematicrule) {
      if (schematicrule.f822 != null && m2062(schematicrule.f822.conclusion, false) != null) {
         Vector vector1 = new Vector();
         vector1.addElement(schematicrule.f822.f820);
         return vector1;
      } else if (this.hasProperty(schematicrule, "biconditional")) {
         Vector vector = new Vector();
         vector.addElement(schematicrule.f820);
         return vector;
      } else {
         return (Vector)this.get(schematicrule.f820);
      }
   }

   void m2068(Rule rule) {
      SchematicRule[] aschematicrule = rule.m1373(this, "notConditional");
      int i = aschematicrule.length;

      for (int j = 0; j < i; j++) {
         SchematicRule schematicrule = aschematicrule[j];
         if (!this.hasProperty(schematicrule, "biconditional")) {
            Expression expression = m2073(schematicrule, false);
            Expression expression1 = m2075(schematicrule, false);

            for (int k = 0; k < i; k++) {
               SchematicRule schematicrule1 = aschematicrule[k];
               boolean flag = this.hasProperty(schematicrule1, "biconditional");
               if (k >= j || flag) {
                  Expression expression2 = m2073(schematicrule1, false);
                  Expression expression3 = m2075(schematicrule1, false);
                  if (this.m2069(expression2, expression3, expression, expression1) || flag && this.m2069(expression3, expression2, expression, expression1)) {
                     this.m2070(schematicrule, schematicrule1);
                  }
               }
            }
         }
      }
   }

   boolean m2069(Expression expression, Expression expression1, Expression expression2, Expression expression3) {
      SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
      C_MB c_mb = new C_MB();
      C__B c__b = new C__B();
      if (!expression.m1267(expression3, schemeinstantiation, c_mb)) {
         return false;
      } else if (!expression1.m1267(expression2, schemeinstantiation, c_mb)) {
         return false;
      } else if (!c__b.m1572(expression, expression3, c_mb)) {
         return false;
      } else {
         return !c__b.m1572(expression1, expression2, c_mb) ? false : schemeinstantiation.m1890();
      }
   }

   void m2070(SchematicRule schematicrule, SchematicRule schematicrule1) {
      Vector vector;
      if ((vector = (Vector)this.get(schematicrule.f820)) == null) {
         this.put(schematicrule.f820, vector = new Vector());
      }

      if (!vector.contains(schematicrule1.f820)) {
      }

      vector.addElement(schematicrule1.f820);
      if (!this.hasProperty(schematicrule1, "biconditional")) {
         if ((vector = (Vector)this.get(schematicrule1.f820)) == null) {
            this.put(schematicrule1.f820, vector = new Vector());
         }

         if (!vector.contains(schematicrule.f820)) {
            vector.addElement(schematicrule.f820);
         }
      }
   }

   Vector m2071(RuleTable ruletable) {
      Vector vector = new Vector();
      Vector vector1 = ruletable.f1469;
      int i = vector1.size();

      for (int j = 0; j < i; j++) {
         String s = (String)vector1.elementAt(j);
         Rule rule = ruletable.m2203(s);
         if (rule.m1193(this, "hasConverse", true)) {
            vector.addElement(s);
         }
      }

      return vector;
   }

   C_n_F m2072(TheoremTable theoremtable) {
      C_n_F c_n_f = new C_n_F();
      Enumeration enumeration = theoremtable.f1464.m1985();

      while (enumeration.hasMoreElements()) {
         Integer integer = (Integer)enumeration.nextElement();
         Theorem theorem = theoremtable.m2199(integer);
         if (theorem.m1193(this, "hasConverse", true)) {
            c_n_f.m1975(C_n_F.m1970(theorem.f700));
         }
      }

      return c_n_f;
   }

   static Expression m2073(SchematicRule schematicrule, boolean flag) {
      return schematicrule.premises.length == 0 ? m2063(schematicrule.conclusion, flag ? 1 : 0) : schematicrule.premises[0];
   }

   static Expression m2074(SchematicRule schematicrule, boolean flag, boolean flag1) {
      return schematicrule.premises.length == 0
         ? m2066(schematicrule.conclusion, flag ? 0 : 1, flag1 ? 1 : 0)
         : schematicrule.conclusion.getChild(flag1 ? 1 : 0);
   }

   static Expression m2075(SchematicRule schematicrule, boolean flag) {
      return schematicrule.premises.length == 0 ? m2063(schematicrule.conclusion, flag ? 0 : 1) : schematicrule.conclusion;
   }

   static Expression m2076(SchematicRule schematicrule, boolean flag, boolean flag1) {
      return schematicrule.premises.length == 0
         ? m2066(schematicrule.conclusion, flag ? 0 : 1, flag1 ? 0 : 1)
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
