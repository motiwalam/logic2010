package edu.ucla.phil.logic;

import java.util.Vector;

class Rule {
   String f820;
   Vector f821;
   Theorem f822;
   String f823 = null;

   Rule(String s) {
      this(s, new Vector());
   }

   Rule(String s, Vector vector) {
      this.f820 = s;
      this.f821 = vector;
      this.f822 = null;
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

         Rule rule1 = ruletable.m2205(s2);
         if (rule1 == null) {
            Integer integer = m1366(s2);
            if (integer == null) {
               this.f823 = "could not find rule " + s2;
            } else {
               this.f823 = "could not find theorem number " + integer;
            }
         } else {
            this.f821.addElement(rule1);
         }
      }
   }

   static Integer m1366(String s) {
      s = s.toUpperCase();
      int i = s.length();
      if (i >= 2 && s.substring(0, 1).equals("T") && "123456789".indexOf(s.substring(1, 2)) != -1) {
         try {
            return new Integer(s.substring(1));
         } catch (NumberFormatException numberformatexception) {
            return null;
         }
      } else {
         return null;
      }
   }

   static Integer m1367(String s) {
      s = s.toUpperCase();
      int i = 3;
      int j = s.length();
      if (j >= 3 && s.substring(0, 2).equals("RT") && "123456789".indexOf(s.charAt(2)) != -1) {
         while (i < j && "0123456789".indexOf(s.charAt(i)) != -1) {
            i++;
         }

         try {
            return new Integer(s.substring(2, i));
         } catch (NumberFormatException numberformatexception) {
            return null;
         }
      } else {
         return null;
      }
   }

   Rule m1368(String s) {
      if (this.f820.equals(s)) {
         return this;
      } else {
         int i = this.f821 == null ? 0 : this.f821.size();

         for (int j = 0; j < i; j++) {
            Rule rule1 = this.m1372(j).m1368(s);
            if (rule1 != null) {
               return rule1;
            }
         }

         return null;
      }
   }

   String m1369() {
      return this.f820;
   }

   void m1370(Rule rule1) {
      if (this.f821 != null && rule1 != null) {
         this.f821.addElement(rule1);
      }
   }

   int m1371() {
      return this.f821 == null ? 0 : this.f821.size();
   }

   Rule m1372(int i) {
      return this.f821 == null ? null : (Rule)this.f821.elementAt(i);
   }

   boolean m1193(C_w_E c_w_e, String s, boolean flag) {
      if (this.f822 != null && this.f822.m1193(c_w_e, s, flag)) {
         return true;
      } else if (c_w_e.hasProperty(this, s)) {
         return true;
      } else {
         int i = this.f821 == null ? 0 : this.f821.size();
         if (i == 0) {
            return false;
         } else {
            for (int j = 0; j < i; j++) {
               if (((Rule)this.f821.elementAt(j)).m1193(c_w_e, s, flag) == flag) {
                  return flag;
               }
            }

            return !flag;
         }
      }
   }

   boolean m952(C_w_E c_w_e) {
      int i = this.f821 == null ? 0 : this.f821.size();

      for (int j = 0; j < i; j++) {
         if (!((Rule)this.f821.elementAt(j)).m952(c_w_e)) {
            return false;
         }
      }

      return true;
   }

   boolean m953(C_w_E c_w_e) {
      int i = this.f821 == null ? 0 : this.f821.size();

      for (int j = 0; j < i; j++) {
         if (((Rule)this.f821.elementAt(j)).m953(c_w_e)) {
            return true;
         }
      }

      return false;
   }

   void m955(Vector vector, C_w_E c_w_e, String s) {
      if (c_w_e == null || !c_w_e.hasProperty(this, s)) {
         int i = this.m1371();

         for (int j = 0; j < i; j++) {
            this.m1372(j).m955(vector, c_w_e, s);
         }
      }
   }

   SchematicRule[] m1373(C_w_E c_w_e, String s) {
      Vector vector = new Vector();
      this.m955(vector, c_w_e, s);
      SchematicRule[] aschematicrule = new SchematicRule[vector.size()];
      vector.copyInto(aschematicrule);
      return aschematicrule;
   }

   SchematicRule[] m1374() {
      return this.m1373(null, null);
   }

   boolean m1375(Rule rule1) {
      if (this.f820.equals(rule1.f820)) {
         return true;
      } else {
         int i = this.f821 == null ? 0 : this.f821.size();

         for (int j = 0; j < i; j++) {
            Rule rule2 = (Rule)this.f821.elementAt(j);
            if (rule2 != null && rule2.m1375(rule1)) {
               return true;
            }
         }

         return false;
      }
   }

   boolean m1376(Rule rule1, Vector vector) {
      int i = vector == null ? 0 : vector.size();

      for (int j = 0; j < i; j++) {
         Rule rule2 = (Rule)vector.elementAt(j);
         if (rule1.m1375(rule2) && rule2.m1375(this)) {
            return true;
         }
      }

      return false;
   }

   static Rule m1377(Theorem theorem) {
      if (theorem == null) {
         return null;
      } else {
         String s = "RT" + theorem.f700;
         Expression expression2 = theorem.conclusion;
         Vector vector;
         if (expression2.symbol.equals("<->")) {
            Expression expression = expression2.getChild(0);
            Expression expression1 = expression2.getChild(1);
            vector = new Vector();
            vector.addElement(m1378(new SchematicRule(s + "L", new Expression[]{expression}, expression1), theorem));
            Expression[] aexpression = m1379(expression);
            if (aexpression.length > 1) {
               vector.addElement(m1378(new SchematicRule(s + "LF", aexpression, expression1), theorem));
            }

            vector.addElement(m1378(new SchematicRule(s + "R", new Expression[]{expression1}, expression), theorem));
            aexpression = m1379(expression1);
            if (aexpression.length > 1) {
               vector.addElement(m1378(new SchematicRule(s + "RF", aexpression, expression), theorem));
            }
         } else {
            if (!expression2.symbol.equals("->")) {
               return m1378(new SchematicRule(s, null, expression2), theorem);
            }

            Expression expression3 = expression2.getChild(0);
            Expression expression4 = expression2.getChild(1);
            Expression[] aexpression1 = m1379(expression3);
            if (aexpression1.length <= 1) {
               return m1378(new SchematicRule(s, aexpression1, expression4), theorem);
            }

            vector = new Vector();
            vector.addElement(m1378(new SchematicRule(s + "L", new Expression[]{expression3}, expression4), theorem));
            vector.addElement(m1378(new SchematicRule(s + "LF", aexpression1, expression4), theorem));
         }

         return m1378(new Rule(s, vector), theorem);
      }
   }

   static Rule m1378(Rule rule, Theorem theorem) {
      rule.f822 = theorem;
      return rule;
   }

   static Expression[] m1379(Expression expression) {
      Vector vector = new Vector();
      m1380(expression, vector);
      Expression[] aexpression = new Expression[vector.size()];
      vector.copyInto(aexpression);
      return aexpression;
   }

   static void m1380(Expression expression, Vector vector) {
      if (expression.symbol.equals("&")) {
         m1380(expression.getChild(0), vector);
         m1380(expression.getChild(1), vector);
      } else {
         vector.addElement(expression);
      }
   }

   String m1381() {
      return this.f823;
   }

   @Override
   public String toString() {
      return this.m958(".", ".:");
   }

   String m958(String s, String s2) {
      String s1 = "";
      int i = this.m1371();
      boolean flag = false;

      for (int j = 0; j < i; j++) {
         s1 = s1 + (flag ? s : "") + this.m1372(j).f820;
         flag = true;
      }

      return s1;
   }
}
