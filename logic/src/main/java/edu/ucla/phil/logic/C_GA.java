package edu.ucla.phil.logic;

import java.util.Vector;

class C_GA extends Justification {
   static final int f342 = 4;
   ExpressionPath f343 = null;
   boolean f344 = false;
   boolean f345 = false;
   C_HF f346 = null;
   DerivationLine f347 = null;
   Integer f348 = null;
   SchemeInstantiation f349 = null;
   C__B f350 = null;
   DerivationLine f351 = null;
   Integer f352 = null;
   C_PF f353 = null;
   boolean f354 = false;

   C_GA() {
      super("IE");
   }

   C_GA(ExpressionPath expressionpath, boolean flag, C_HF c_hf) {
      this(expressionpath, flag, c_hf, false, null);
   }

   C_GA(ExpressionPath expressionpath, boolean flag, C_HF c_hf, boolean flag1, SchematicRule schematicrule) {
      this();
      this.f343 = expressionpath;
      this.f344 = flag;
      this.f346 = c_hf;
      this.m599(flag1, schematicrule);
   }

   C_GA(ExpressionPath expressionpath, boolean flag, DerivationLine derivationline, SchemeInstantiation schemeinstantiation, C__B c__b) {
      this(expressionpath, flag, derivationline, schemeinstantiation, c__b, false, null);
   }

   C_GA(
      ExpressionPath expressionpath,
      boolean flag,
      DerivationLine derivationline,
      SchemeInstantiation schemeinstantiation,
      C__B c__b,
      boolean flag1,
      SchematicRule schematicrule
   ) {
      this();
      this.f343 = expressionpath;
      this.f344 = flag;
      this.f347 = derivationline;
      this.f349 = schemeinstantiation;
      this.f350 = c__b;
      this.m599(flag1, schematicrule);
   }

   C_GA(ExpressionPath expressionpath, boolean flag, Integer integer, SchemeInstantiation schemeinstantiation, C__B c__b) {
      this(expressionpath, flag, integer, schemeinstantiation, c__b, false, null);
   }

   C_GA(
      ExpressionPath expressionpath,
      boolean flag,
      Integer integer,
      SchemeInstantiation schemeinstantiation,
      C__B c__b,
      boolean flag1,
      SchematicRule schematicrule
   ) {
      this();
      this.f343 = expressionpath;
      this.f344 = flag;
      this.f348 = integer;
      this.f349 = schemeinstantiation;
      this.f350 = c__b;
      this.m599(flag1, schematicrule);
   }

   void m599(boolean flag, SchematicRule schematicrule) {
      this.f354 = flag;
      if (schematicrule instanceof C_l_F) {
         this.f351 = ((C_l_F)schematicrule).f1263;
         this.f352 = null;
         this.f353 = null;
      } else if (schematicrule instanceof C_OF) {
         this.f351 = null;
         this.f352 = ((C_OF)schematicrule).f667;
         this.f353 = null;
      } else if (schematicrule instanceof C_PF) {
         this.f351 = null;
         this.f352 = null;
         this.f353 = (C_PF)schematicrule;
      }
   }

   @Override
   boolean m600(DerivationLineChecker derivationlinechecker) {
      boolean flag = this.f351 == null && this.f352 == null && this.f353 == null;
      String s = flag ? "IE" : "CIE";
      if (!derivationlinechecker.f939.equals(s)) {
         return false;
      } else {
         derivationlinechecker.getClass();
         if (!derivationlinechecker.f944 && !derivationlinechecker.f945 ? derivationlinechecker.f938 >= 1 : derivationlinechecker.f938 == 1) {
            if (derivationlinechecker.m1628(-1).m1220(this.f343) == null) {
               return false;
            } else {
               if (this.f346 != null) {
                  if (!m631(derivationlinechecker, this.f346.f391, flag)) {
                     return false;
                  }

                  if (!m602(this.f346, derivationlinechecker, this.f343, this.m622(derivationlinechecker))) {
                     return false;
                  }
               } else if (this.f347 != null) {
                  LPDerivation lpderivation = derivationlinechecker.f935.f317.f915;
                  if (lpderivation.problem.m32(this.f347.m30()) != this.f347.m25()) {
                     return false;
                  }

                  if (!derivationlinechecker.f935.m565(this.f347)) {
                     return false;
                  }

                  if (!m603(this.f347, derivationlinechecker, this.f343, flag)) {
                     return false;
                  }
               } else {
                  if (this.f348 == null) {
                     return false;
                  }

                  int i = this.f348;
                  LPDerivation lpderivation1 = derivationlinechecker.f935.f317.f915;
                  if (lpderivation1.premises == null || i < 0 || i >= lpderivation1.premises.length) {
                     return false;
                  }

                  if (!m604(this.f348, derivationlinechecker, this.f343, flag)) {
                     return false;
                  }
               }

               if (derivationlinechecker.m1615(-1) != null) {
                  derivationlinechecker.f935.f317.f922 = false;
               }

               Expression expression = this.m618(derivationlinechecker);
               if (!expression.m1235(derivationlinechecker.m1628(-1))) {
                  return false;
               } else {
                  derivationlinechecker.f942 = this.m620(derivationlinechecker);
                  if (derivationlinechecker.f944 && !derivationlinechecker.m1609(true)) {
                     return false;
                  } else {
                     derivationlinechecker.m1631(1);
                     return true;
                  }
               }
            }
         } else {
            return false;
         }
      }
   }

   static boolean m601(DerivationLineChecker derivationlinechecker, ExpressionPath expressionpath, SchematicRule schematicrule) {
      return schematicrule == null || m617(derivationlinechecker.m1628(-1), schematicrule.conclusion, expressionpath).m1259() == null;
   }

   static boolean m602(C_HF c_hf, DerivationLineChecker derivationlinechecker, ExpressionPath expressionpath, SchematicRule schematicrule) {
      SchematicRule schematicrule1 = c_hf.m688();
      String s = schematicrule == null ? "notConditional" : "notConditionalBC";
      if (LogicProgram.f534.f1472 != null && LogicProgram.f534.f1472.hasProperty(schematicrule1, s)) {
         derivationlinechecker.m1622("dererr098", Message.params("inner rule name", schematicrule1.f820));
         return false;
      } else if (!m601(derivationlinechecker, expressionpath, schematicrule)) {
         derivationlinechecker.m1622("dererr099", Message.params("condition name", schematicrule.f820));
         return false;
      } else {
         return true;
      }
   }

   static boolean m603(DerivationLine derivationline, DerivationLineChecker derivationlinechecker, ExpressionPath expressionpath, boolean flag) {
      Expression expression;
      if (flag) {
         expression = RuleProperties.m2062(derivationline.f332, false);
      } else {
         expression = RuleProperties.m2065(derivationline.f332);
      }

      if (expression == null) {
         derivationlinechecker.m1622("dererr086", Message.params("remote line number", derivationline.m30() + ""));
         return false;
      } else if (m617(derivationlinechecker.m1628(-1), derivationline.f332, expressionpath).m1259() != null) {
         derivationlinechecker.m1622("dererr087", Message.params("remote line number", derivationline.m30() + ""));
         return false;
      } else {
         return true;
      }
   }

   static boolean m604(Integer integer, DerivationLineChecker derivationlinechecker, ExpressionPath expressionpath, boolean flag) {
      int i = integer;
      LPDerivation lpderivation = derivationlinechecker.f935.f317.f915;
      Expression expression;
      if (flag) {
         expression = RuleProperties.m2062(lpderivation.premises[i], false);
      } else {
         expression = RuleProperties.m2065(lpderivation.premises[i]);
      }

      if (expression == null) {
         derivationlinechecker.m1622("dererr088", Message.params("premise index", i + 1 + ""));
         return false;
      } else if (m617(derivationlinechecker.m1628(-1), lpderivation.premises[i], expressionpath).m1259() != null) {
         derivationlinechecker.m1622("dererr089", Message.params("premise index", i + 1 + ""));
         return false;
      } else {
         return true;
      }
   }

   static SchemeInstantiation m605(Expression expression) {
      SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
      expression.m1266(null, schemeinstantiation);
      boolean flag = false;
      int i = schemeinstantiation.f1189.size();

      for (int j = 0; j < i; j++) {
         SchematicLetter schematicletter = (SchematicLetter)schemeinstantiation.f1189.elementAt(0);
         Expression expression1 = schematicletter.m1175();
         schemeinstantiation.m1880(schematicletter, new LetterReplacement(expression1, expression1));
      }

      return schemeinstantiation;
   }

   C_e_B m606(DerivationLineChecker derivationlinechecker) {
      return this.m607(derivationlinechecker, true);
   }

   C_e_B m607(DerivationLineChecker derivationlinechecker, boolean flag) {
      if (this.f346 == null) {
         C_e_B c_e_b = this.m608(this.m619(derivationlinechecker, true)).m463();
         C_e_B c_e_b1 = this.m608(this.m621(derivationlinechecker, true)).m463();
         if (flag && !c_e_b.m1765(c_e_b1)) {
            return c_e_b1;
         } else {
            c_e_b.m1761(".:");
            c_e_b.m1760(c_e_b1);
            return c_e_b;
         }
      } else {
         return this.m625().m700(null).m543(flag);
      }
   }

   C_DD m608(Expression expression) {
      C_DD c_dd = new C_DD(expression, true, -1);
      c_dd.m460(this.f349.f1189);
      c_dd.m461(this.f350);
      return c_dd;
   }

   @Override
   String m609() {
      return "4:" + this;
   }

   @Override
   public String toString() {
      Object object = "";
      if (this.f343 != null) {
         object = object + this.f343;
      }

      object = object + (this.f344 ? "<" : ">");
      if (this.f346 != null) {
         object = object + this.f346;
      } else if (this.f347 != null) {
         object = object + this.f347.m30();
         if (this.f349 != null) {
            object = object + "," + this.f349.m1896();
         }

         if (this.f350 != null) {
            object = object + ";" + this.f350.m1580();
         }
      } else if (this.f348 != null) {
         object = object + "#" + this.f348;
         if (this.f349 != null) {
            object = object + "," + this.f349.m1896();
         }

         if (this.f350 != null) {
            object = object + ";" + this.f350.m1580();
         }
      }

      if (this.f351 != null) {
         object = object + (this.f354 ? "?<" : "?>");
         object = object + this.f351.m30();
      } else if (this.f352 != null) {
         object = object + (this.f354 ? "?<" : "?>");
         object = object + "#" + this.f352;
      } else if (this.f353 != null) {
         object = object + (this.f354 ? "?<" : "?>");
         object = object + "#" + this.f353.m1182();
      }

      return (String)object;
   }

   static C_GA m610(String s, LPDerivation lpderivation) {
      int i = s.indexOf(":");
      if (i == -1) {
         return null;
      } else {
         return !s.substring(0, i).equals(Integer.toString(4)) ? null : m611(s.substring(i + 1), lpderivation);
      }
   }

   static C_GA m611(String s, LPDerivation lpderivation) {
      int i = TaggedRecord.m1480(s.indexOf("?>"), s.indexOf("?<"));
      String s1 = i == -1 ? null : s.substring(i + 1);
      if (i != -1) {
         s = s.substring(0, i);
      }

      i = TaggedRecord.m1480(s.indexOf(">"), s.indexOf("<"));
      if (i == -1) {
         return null;
      } else {
         C_GA c_ga = new C_GA();
         c_ga.f343 = i == 0 ? null : ExpressionPath.m1746(ExpressionPath.m1755(s.substring(0, i)));
         c_ga.f344 = s.charAt(i) == '<';
         s = s.substring(i + 1);
         if ((c_ga.f346 = C_HF.m699(s)) == null && (c_ga.f347 = m612(s, lpderivation)) == null && (c_ga.f348 = m613(s, lpderivation)) != null) {
         }

         if (c_ga.f347 != null || c_ga.f348 != null) {
            c_ga.f349 = m614(s);
            c_ga.f350 = m615(s);
         }

         if (s1 != null) {
            c_ga.f354 = s1.charAt(0) == '<';
            s1 = s1.substring(1);
            if ((c_ga.f351 = m612(s1, lpderivation)) == null && (c_ga.f352 = m613(s1, lpderivation)) == null && (c_ga.f353 = m616(s1)) != null) {
            }
         }

         return c_ga;
      }
   }

   static DerivationLine m612(String s, LPDerivation lpderivation) {
      int i = s.indexOf(",");
      if (i != -1 || (i = s.indexOf(";")) != -1) {
         s = s.substring(0, i);
      }

      Integer integer = LogicProgram.parseInteger(s);
      if (integer == null) {
         return null;
      } else {
         DerivationNode derivationnode = lpderivation.problem.m32(integer);
         if (derivationnode == null) {
            return null;
         } else {
            return derivationnode instanceof DerivationLine ? (DerivationLine)derivationnode : ((DerivationBox)derivationnode).f917;
         }
      }
   }

   static Integer m613(String s, LPDerivation lpderivation) {
      if (lpderivation.premises == null) {
         return null;
      } else {
         int i = s.indexOf(",");
         if (i != -1 || (i = s.indexOf(";")) != -1) {
            s = s.substring(0, i);
         }

         if (!s.startsWith("#")) {
            return null;
         } else {
            Integer integer = LogicProgram.parseInteger(s.substring(1));
            if (integer == null) {
               return null;
            } else {
               int j = integer;
               return j >= 0 && j < lpderivation.premises.length ? integer : null;
            }
         }
      }
   }

   static SchemeInstantiation m614(String s) {
      int i = s.indexOf(",");
      if (i == -1) {
         return null;
      } else {
         s = s.substring(i + 1);
         if ((i = s.indexOf(";")) != -1) {
            s = s.substring(0, i);
         }

         return SchemeInstantiation.m1897(s);
      }
   }

   static C__B m615(String s) {
      int i = s.indexOf(";");
      return i == -1 ? null : C__B.m1581(s.substring(i + 1));
   }

   static C_PF m616(String s) {
      return !s.startsWith("#") ? null : C_PF.m1183(s.substring(1));
   }

   static Expression m617(Expression expression, Expression expression1, ExpressionPath expressionpath) {
      expression1 = expression1.copy();
      if (expressionpath.depth == 0) {
         return expression1;
      } else {
         expression = expression.copy();
         expression.m1222(expressionpath.indexes, 0, expressionpath.depth - 1, null)
            .children
            .setElementAt(expression1, expressionpath.indexes[expressionpath.depth - 1]);
         return expression;
      }
   }

   Expression m618(DerivationLineChecker derivationlinechecker) {
      return this.m619(derivationlinechecker, false);
   }

   Expression m619(DerivationLineChecker derivationlinechecker, boolean flag) {
      boolean flag1 = this.f351 == null && this.f352 == null && this.f353 == null;
      Expression expression1;
      if (this.f346 != null) {
         C_MB c_mb = new C_MB();
         Expression expression;
         if (flag1) {
            expression = RuleProperties.m2073(this.f346.f391, this.f344);
         } else {
            expression = RuleProperties.m2074(this.f346.f391, this.f354, this.f344);
         }

         if (expression == null) {
            return null;
         }

         expression1 = expression.m1239(this.f346.f393, c_mb);
         if (!this.f346.f394.m1574(expression, expression1, c_mb, flag ? null : derivationlinechecker)) {
            return null;
         }
      } else {
         if (this.f347 != null) {
            expression1 = this.f347.f332;
         } else if (this.f348 != null) {
            expression1 = derivationlinechecker.f935.f317.f915.premises[this.f348];
         } else {
            expression1 = null;
         }

         Expression expression2;
         if (flag1) {
            expression2 = RuleProperties.m2064(expression1, false, this.f344 ? 1 : 0);
         } else {
            expression2 = RuleProperties.m2066(expression1, this.f354 ? 0 : 1, this.f344 ? 1 : 0);
         }

         if (expression2 == null) {
            return null;
         }

         C_MB c_mb1 = new C_MB();
         expression1 = expression2.m1239(this.f349, c_mb1);
         if (this.f350 != null) {
            this.f350.m1574(expression2, expression1, c_mb1, flag ? null : derivationlinechecker);
         }
      }

      return m617(derivationlinechecker.m1628(-1), expression1, this.f343).m1257();
   }

   Expression m620(DerivationLineChecker derivationlinechecker) {
      return this.m621(derivationlinechecker, false);
   }

   Expression m621(DerivationLineChecker derivationlinechecker, boolean flag) {
      boolean flag1 = this.f351 == null && this.f352 == null && this.f353 == null;
      Expression expression1;
      if (this.f346 != null) {
         C_MB c_mb = new C_MB();
         Expression expression;
         if (flag1) {
            expression = RuleProperties.m2075(this.f346.f391, this.f344);
         } else {
            expression = RuleProperties.m2076(this.f346.f391, this.f354, this.f344);
         }

         if (expression == null) {
            return null;
         }

         expression1 = expression.m1239(this.f346.f393, c_mb);
         if (!this.f346.f394.m1574(expression, expression1, c_mb, flag ? null : derivationlinechecker)) {
            return null;
         }
      } else {
         if (this.f347 != null) {
            expression1 = this.f347.f332;
         } else if (this.f348 != null) {
            expression1 = derivationlinechecker.f935.f317.f915.premises[this.f348];
         } else {
            expression1 = null;
         }

         Expression expression2;
         if (flag1) {
            expression2 = RuleProperties.m2064(expression1, false, this.f344 ? 0 : 1);
         } else {
            expression2 = RuleProperties.m2066(expression1, this.f354 ? 0 : 1, this.f344 ? 0 : 1);
         }

         if (expression2 == null) {
            return null;
         }

         C_MB c_mb1 = new C_MB();
         expression1 = expression2.m1239(this.f349, c_mb1);
         if (this.f350 != null && !this.f350.m1574(expression2, expression1, c_mb1, flag ? null : derivationlinechecker)) {
            return null;
         }
      }

      return m617(derivationlinechecker.m1628(-1), expression1, this.f343).m1257();
   }

   SchematicRule m622(DerivationLineChecker derivationlinechecker) {
      if (this.f351 != null) {
         return new C_l_F(this.f351);
      } else if (this.f352 != null) {
         return new C_OF(derivationlinechecker.f935.f317.f915, this.f352 + 1);
      } else {
         return this.f353 != null ? this.f353 : null;
      }
   }

   static Expression m623(SchemeInstantiation schemeinstantiation, Expression expression, Expression expression1) {
      schemeinstantiation = (SchemeInstantiation)schemeinstantiation.clone();
      expression.m1266(null, schemeinstantiation);
      Vector vector = schemeinstantiation.m1893(expression1).f1189;
      expression = expression.instantiate(schemeinstantiation);
      int i = m630(expression);
      int j = m630(expression1);
      if (j < i) {
         return null;
      } else if (j == i) {
         return expression1;
      } else {
         Vector vector1 = new Vector();

         do {
            vector1.addElement(expression1.getChild(0).copy());
            expression1 = expression1.getChild(1);
         } while (--j > i);

         SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation();
         int k = vector1.size();

         for (int l = 0; l < k; l++) {
            Expression expression2 = (Expression)vector1.elementAt(l);
            Vector vector2 = expression1.m1223(expression2);
            int i1 = vector2.size();

            for (int j1 = 0; j1 < i1; j1++) {
               Expression expression3 = expression.m1220((ExpressionPath)vector2.elementAt(j1));
               if (expression3 != null && !m629(expression3, vector) && !schemeinstantiation1.m1881(expression2, expression3)) {
                  return null;
               }
            }
         }

         return expression1.instantiate(schemeinstantiation1);
      }
   }

   SchematicRule m624(DerivationLineChecker derivationlinechecker) {
      if (this.f346 != null) {
         return this.f346.m688();
      } else if (this.f347 != null) {
         return new C_l_F(this.f347);
      } else {
         return this.f348 != null ? new C_OF(derivationlinechecker.f935.f317.f915, this.f348 + 1) : null;
      }
   }

   C_HF m625() {
      if (this.f346 == null) {
         return null;
      } else {
         SchematicRule schematicrule = new SchematicRule(this.f346.f391.f820);
         boolean flag = this.f346.f391.premises.length == 0;
         Expression expression;
         if (this.f351 == null && this.f352 == null && this.f353 == null) {
            if (!flag) {
               return this.f346;
            }

            expression = RuleProperties.m2061(this.f346.f391.conclusion);
         } else if (flag) {
            expression = RuleProperties.m2065(this.f346.f391.conclusion);
            if (expression != null) {
               expression = expression.getChild(this.f354 ? 0 : 1);
            }
         } else {
            expression = this.f346.f391.conclusion;
         }

         if (expression == null) {
            return null;
         } else {
            schematicrule.premises = new Expression[]{expression.getChild(this.f344 ? 1 : 0)};
            schematicrule.conclusion = expression.getChild(this.f344 ? 0 : 1);
            return new C_HF(schematicrule, new int[]{0}, this.f346.f393, this.f346.f394);
         }
      }
   }

   static Vector m626(DerivationLineChecker derivationlinechecker, ExpressionPath expressionpath, Rule rule, SchematicRule schematicrule) {
      Expression expression = derivationlinechecker.m1628(-1).m1220(expressionpath).copy();
      Expression expression1 = derivationlinechecker.f944 && !derivationlinechecker.f946 ? derivationlinechecker.f941 : null;
      if (expression1 != null) {
         expression1 = expression1.m1220(expressionpath).copy();
      }

      RuleProperties ruleproperties = LogicProgram.f534.f1472;
      String s = schematicrule == null ? "notConditional" : "notConditionalBC";
      SchematicRule[] aschematicrule = rule.m1373(ruleproperties, s);
      LPDerivation lpderivation = derivationlinechecker.f935.f317.f915;
      String s1 = derivationlinechecker.f946 ? "manualOrDisabled" : "disabled";
      SchematicRule[] aschematicrule1 = rule.m1373(lpderivation, s1);
      Vector vector = new Vector();
      Vector vector1 = new Vector();

      for (SchematicRule schematicrule1 : aschematicrule) {
         Vector vector2 = vector;
         if (LogicProgram.m1051(aschematicrule1, schematicrule1) == -1) {
            vector2 = vector1;
         } else if (!m631(derivationlinechecker, schematicrule1, schematicrule == null)) {
            vector2 = vector1;
         }

         Expression expression2;
         Expression expression3;
         boolean flag;
         int[] aint;
         Expression expression4;
         if (schematicrule1.premises.length == 0) {
            if (schematicrule == null) {
               expression4 = RuleProperties.m2061(schematicrule1.conclusion);
            } else {
               expression4 = RuleProperties.m2065(schematicrule1.conclusion);
            }

            expression2 = expression4.getChild(0);
            expression3 = expression4.getChild(1);
            flag = expression4.symbol.equals("<->");
            aint = new int[0];
         } else {
            expression4 = null;
            expression2 = schematicrule1.premises[0];
            expression3 = schematicrule1.conclusion;
            flag = false;
            aint = new int[]{0};
         }

         if (schematicrule == null) {
            if (schematicrule1 instanceof C_l_F) {
               if (expression4 != null && flag) {
                  DerivationLine derivationline = ((C_l_F)schematicrule1).f1263;
                  SchemeInstantiation schemeinstantiation = m605(schematicrule1.conclusion);
                  C__B c__b = m627(schemeinstantiation, expression2, expression3, expression, expression1);
                  if (c__b != null) {
                     vector2.addElement(new C_GA(expressionpath, false, derivationline, schemeinstantiation, c__b));
                  }

                  schemeinstantiation = m605(schematicrule1.conclusion);
                  c__b = m627(schemeinstantiation, expression3, expression2, expression, expression1);
                  if (c__b != null) {
                     vector2.addElement(new C_GA(expressionpath, true, derivationline, schemeinstantiation, c__b));
                  }
               }
            } else if (schematicrule1 instanceof C_OF) {
               if (expression4 != null && flag) {
                  Integer integer = ((C_OF)schematicrule1).f667;
                  SchemeInstantiation schemeinstantiation1 = m605(schematicrule1.conclusion);
                  C__B c__b1 = m627(schemeinstantiation1, expression2, expression3, expression, expression1);
                  if (c__b1 != null) {
                     vector2.addElement(new C_GA(expressionpath, false, integer, schemeinstantiation1, c__b1));
                  }

                  schemeinstantiation1 = m605(schematicrule1.conclusion);
                  c__b1 = m627(schemeinstantiation1, expression3, expression2, expression, expression1);
                  if (c__b1 != null) {
                     vector2.addElement(new C_GA(expressionpath, true, integer, schemeinstantiation1, c__b1));
                  }
               }
            } else {
               SchemeInstantiation schemeinstantiation2 = new SchemeInstantiation();
               C__B c__b2 = m627(schemeinstantiation2, expression2, expression3, expression, expression1);
               if (c__b2 != null) {
                  C_HF c_hf = new C_HF(schematicrule1, aint, schemeinstantiation2, c__b2);
                  vector2.addElement(new C_GA(expressionpath, false, c_hf));
               }

               if (flag) {
                  schemeinstantiation2 = new SchemeInstantiation();
                  c__b2 = m627(schemeinstantiation2, expression3, expression2, expression, expression1);
                  if (c__b2 != null) {
                     C_HF c_hf1 = new C_HF(schematicrule1, aint, schemeinstantiation2, c__b2);
                     vector2.addElement(new C_GA(expressionpath, true, c_hf1));
                  }
               }
            }
         } else if (schematicrule1 instanceof C_l_F) {
            if (expression4 != null) {
               DerivationLine derivationline1 = ((C_l_F)schematicrule1).f1263;
               if (expression3.symbol.equals("<->")) {
                  SchemeInstantiation schemeinstantiation3 = m605(schematicrule1.conclusion);
                  C__B c__b3 = m628(
                     schemeinstantiation3, expression3.getChild(0), expression3.getChild(1), expression2, expression, expression1, schematicrule.conclusion
                  );
                  if (c__b3 != null) {
                     vector2.addElement(new C_GA(expressionpath, false, derivationline1, schemeinstantiation3, c__b3, false, schematicrule));
                  }

                  schemeinstantiation3 = m605(schematicrule1.conclusion);
                  c__b3 = m628(
                     schemeinstantiation3, expression3.getChild(1), expression3.getChild(0), expression2, expression, expression1, schematicrule.conclusion
                  );
                  if (c__b3 != null) {
                     vector2.addElement(new C_GA(expressionpath, true, derivationline1, schemeinstantiation3, c__b3, false, schematicrule));
                  }
               }

               if (flag && expression2.symbol.equals("<->")) {
                  SchemeInstantiation schemeinstantiation4 = m605(schematicrule1.conclusion);
                  C__B c__b4 = m628(
                     schemeinstantiation4, expression2.getChild(0), expression2.getChild(1), expression3, expression, expression1, schematicrule.conclusion
                  );
                  if (c__b4 != null) {
                     vector2.addElement(new C_GA(expressionpath, false, derivationline1, schemeinstantiation4, c__b4, true, schematicrule));
                  }

                  schemeinstantiation4 = m605(schematicrule1.conclusion);
                  c__b4 = m628(
                     schemeinstantiation4, expression2.getChild(1), expression2.getChild(0), expression3, expression, expression1, schematicrule.conclusion
                  );
                  if (c__b4 != null) {
                     vector2.addElement(new C_GA(expressionpath, true, derivationline1, schemeinstantiation4, c__b4, true, schematicrule));
                  }
               }
            }
         } else if (schematicrule1 instanceof C_OF) {
            if (expression4 != null) {
               Integer integer1 = ((C_OF)schematicrule1).f667;
               if (expression3.symbol.equals("<->")) {
                  SchemeInstantiation schemeinstantiation5 = m605(schematicrule1.conclusion);
                  C__B c__b5 = m628(
                     schemeinstantiation5, expression3.getChild(0), expression3.getChild(1), expression2, expression, expression1, schematicrule.conclusion
                  );
                  if (c__b5 != null) {
                     vector2.addElement(new C_GA(expressionpath, false, integer1, schemeinstantiation5, c__b5, false, schematicrule));
                  }

                  schemeinstantiation5 = m605(schematicrule1.conclusion);
                  c__b5 = m628(
                     schemeinstantiation5, expression3.getChild(1), expression3.getChild(0), expression2, expression, expression1, schematicrule.conclusion
                  );
                  if (c__b5 != null) {
                     vector2.addElement(new C_GA(expressionpath, true, integer1, schemeinstantiation5, c__b5, false, schematicrule));
                  }
               }

               if (flag && expression2.symbol.equals("<->")) {
                  SchemeInstantiation schemeinstantiation6 = m605(schematicrule1.conclusion);
                  C__B c__b6 = m628(
                     schemeinstantiation6, expression2.getChild(0), expression2.getChild(1), expression3, expression, expression1, schematicrule.conclusion
                  );
                  if (c__b6 != null) {
                     vector2.addElement(new C_GA(expressionpath, false, integer1, schemeinstantiation6, c__b6, true, schematicrule));
                  }

                  schemeinstantiation6 = m605(schematicrule1.conclusion);
                  c__b6 = m628(
                     schemeinstantiation6, expression2.getChild(1), expression2.getChild(0), expression3, expression, expression1, schematicrule.conclusion
                  );
                  if (c__b6 != null) {
                     vector2.addElement(new C_GA(expressionpath, true, integer1, schemeinstantiation6, c__b6, true, schematicrule));
                  }
               }
            }
         } else {
            if (expression3.symbol.equals("<->")) {
               SchemeInstantiation schemeinstantiation7 = new SchemeInstantiation();
               C__B c__b7 = m628(
                  schemeinstantiation7, expression3.getChild(0), expression3.getChild(1), expression2, expression, expression1, schematicrule.conclusion
               );
               if (c__b7 != null) {
                  C_HF c_hf2 = new C_HF(schematicrule1, aint, schemeinstantiation7, c__b7);
                  vector2.addElement(new C_GA(expressionpath, false, c_hf2, false, schematicrule));
               }

               schemeinstantiation7 = new SchemeInstantiation();
               c__b7 = m628(
                  schemeinstantiation7, expression3.getChild(1), expression3.getChild(0), expression2, expression, expression1, schematicrule.conclusion
               );
               if (c__b7 != null) {
                  C_HF c_hf3 = new C_HF(schematicrule1, aint, schemeinstantiation7, c__b7);
                  vector2.addElement(new C_GA(expressionpath, true, c_hf3, false, schematicrule));
               }
            }

            if (flag && expression2.symbol.equals("<->")) {
               SchemeInstantiation schemeinstantiation8 = new SchemeInstantiation();
               C__B c__b8 = m628(
                  schemeinstantiation8, expression2.getChild(0), expression2.getChild(1), expression3, expression, expression1, schematicrule.conclusion
               );
               if (c__b8 != null) {
                  C_HF c_hf4 = new C_HF(schematicrule1, aint, schemeinstantiation8, c__b8);
                  vector2.addElement(new C_GA(expressionpath, false, c_hf4, true, schematicrule));
               }

               schemeinstantiation8 = new SchemeInstantiation();
               c__b8 = m628(
                  schemeinstantiation8, expression2.getChild(1), expression2.getChild(0), expression3, expression, expression1, schematicrule.conclusion
               );
               if (c__b8 != null) {
                  C_HF c_hf5 = new C_HF(schematicrule1, aint, schemeinstantiation8, c__b8);
                  vector2.addElement(new C_GA(expressionpath, true, c_hf5, true, schematicrule));
               }
            }
         }
      }

      return vector1.isEmpty() && vector.isEmpty() ? null : vector;
   }

   static C__B m627(SchemeInstantiation schemeinstantiation, Expression expression, Expression expression1, Expression expression2, Expression expression3) {
      return m628(schemeinstantiation, expression, expression1, null, expression2, expression3, null);
   }

   static C__B m628(
      SchemeInstantiation schemeinstantiation,
      Expression expression,
      Expression expression1,
      Expression expression2,
      Expression expression3,
      Expression expression4,
      Expression expression5
   ) {
      C_MB c_mb = new C_MB();
      if (!expression.m1267(expression3, schemeinstantiation, c_mb)) {
         return null;
      } else if (!expression1.m1267(expression4, schemeinstantiation, c_mb)) {
         return null;
      } else {
         if (expression5 != null) {
            expression5 = m623(schemeinstantiation, expression2, expression5);
            if (expression5 == null) {
               return null;
            }
         }

         if (expression2 != null && !expression2.m1267(expression5, schemeinstantiation, c_mb)) {
            return null;
         } else {
            C__B c__b = new C__B();
            if (!c__b.m1572(expression, expression3, c_mb)) {
               return null;
            } else if (!c__b.m1572(expression1, expression4, c_mb)) {
               return null;
            } else if (expression2 != null && !c__b.m1572(expression2, expression5, c_mb)) {
               return null;
            } else {
               c_mb = new C_MB();
               Expression expression6 = expression.m1239(schemeinstantiation, c_mb);
               Expression expression7 = expression1.m1239(schemeinstantiation, c_mb);
               Expression expression8 = expression2 == null ? null : expression2.m1239(schemeinstantiation, c_mb);
               if (!c__b.m1574(expression, expression6, c_mb, null)) {
                  return null;
               } else if (!c__b.m1574(expression1, expression7, c_mb, null)) {
                  return null;
               } else {
                  return expression2 != null && !c__b.m1574(expression2, expression8, c_mb, null) ? null : c__b;
               }
            }
         }
      }
   }

   static boolean m629(Expression expression, Vector vector) {
      int i = vector == null ? 0 : vector.size();

      for (int j = 0; j < i; j++) {
         if (!expression.m1225((SchematicLetter)vector.elementAt(j)).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   static int m630(Expression expression) {
      Expression expression1 = expression;

      int i;
      for (i = 0; expression1.symbol.equals("@"); expression1 = expression1.getChild(1)) {
         i++;
      }

      return i;
   }

   static boolean m631(DerivationLineChecker derivationlinechecker, SchematicRule schematicrule, boolean flag) {
      if (schematicrule instanceof C_OF) {
         return true;
      } else if (schematicrule instanceof C_l_F) {
         return derivationlinechecker.f935.m565(((C_l_F)schematicrule).f1263);
      } else {
         LPDerivation lpderivation = derivationlinechecker.f935.f317.f915;
         String s = derivationlinechecker.f946 ? "manualOrDisabled" : "disabled";
         if (lpderivation.hasProperty(schematicrule, s)) {
            return false;
         } else if (!schematicrule.m952(lpderivation)) {
            lpderivation.proofMissing = true;
            return false;
         } else if (!flag) {
            return true;
         } else {
            RuleProperties ruleproperties = LogicProgram.f534.f1472;
            Vector vector = ruleproperties.m2067(schematicrule);
            int i = vector == null ? 0 : vector.size();

            for (int j = 0; j < i; j++) {
               Rule rule = LPDerivation.getRule((String)vector.elementAt(j));
               if (rule instanceof SchematicRule && !lpderivation.hasProperty(rule, s)) {
                  if (rule.m952(lpderivation)) {
                     return true;
                  }

                  lpderivation.proofMissing = true;
               }
            }

            return false;
         }
      }
   }
}
