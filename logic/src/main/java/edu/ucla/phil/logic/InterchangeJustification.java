package edu.ucla.phil.logic;

import java.util.Vector;

class InterchangeJustification extends Justification {
   static final int TYPE = 4;
   ExpressionPath path = null;
   boolean reversed = false;
   boolean pathChosen = false;
   RuleApplication ruleApplication = null;
   DerivationLine equivalenceLine = null;
   Integer equivalencePremise = null;
   SchemeInstantiation instantiation = null;
   BoundVariableMap boundVariables = null;
   DerivationLine conditionLine = null;
   Integer conditionPremise = null;
   RuleInstance conditionInstance = null;
   boolean conditionReversed = false;

   InterchangeJustification() {
      super("IE");
   }

   InterchangeJustification(ExpressionPath expressionpath, boolean flag, RuleApplication ruleapplication) {
      this(expressionpath, flag, ruleapplication, false, null);
   }

   InterchangeJustification(ExpressionPath expressionpath, boolean flag, RuleApplication ruleapplication, boolean flag1, SchematicRule schematicrule) {
      this();
      this.path = expressionpath;
      this.reversed = flag;
      this.ruleApplication = ruleapplication;
      this.setCondition(flag1, schematicrule);
   }

   InterchangeJustification(
      ExpressionPath expressionpath, boolean flag, DerivationLine derivationline, SchemeInstantiation schemeinstantiation, BoundVariableMap boundvariablemap
   ) {
      this(expressionpath, flag, derivationline, schemeinstantiation, boundvariablemap, false, null);
   }

   InterchangeJustification(
      ExpressionPath expressionpath,
      boolean flag,
      DerivationLine derivationline,
      SchemeInstantiation schemeinstantiation,
      BoundVariableMap boundvariablemap,
      boolean flag1,
      SchematicRule schematicrule
   ) {
      this();
      this.path = expressionpath;
      this.reversed = flag;
      this.equivalenceLine = derivationline;
      this.instantiation = schemeinstantiation;
      this.boundVariables = boundvariablemap;
      this.setCondition(flag1, schematicrule);
   }

   InterchangeJustification(
      ExpressionPath expressionpath, boolean flag, Integer integer, SchemeInstantiation schemeinstantiation, BoundVariableMap boundvariablemap
   ) {
      this(expressionpath, flag, integer, schemeinstantiation, boundvariablemap, false, null);
   }

   InterchangeJustification(
      ExpressionPath expressionpath,
      boolean flag,
      Integer integer,
      SchemeInstantiation schemeinstantiation,
      BoundVariableMap boundvariablemap,
      boolean flag1,
      SchematicRule schematicrule
   ) {
      this();
      this.path = expressionpath;
      this.reversed = flag;
      this.equivalencePremise = integer;
      this.instantiation = schemeinstantiation;
      this.boundVariables = boundvariablemap;
      this.setCondition(flag1, schematicrule);
   }

   void setCondition(boolean flag, SchematicRule schematicrule) {
      this.conditionReversed = flag;
      if (schematicrule instanceof LineRule) {
         this.conditionLine = ((LineRule)schematicrule).line;
         this.conditionPremise = null;
         this.conditionInstance = null;
      } else if (schematicrule instanceof PremiseRule) {
         this.conditionLine = null;
         this.conditionPremise = ((PremiseRule)schematicrule).premiseIndex;
         this.conditionInstance = null;
      } else if (schematicrule instanceof RuleInstance) {
         this.conditionLine = null;
         this.conditionPremise = null;
         this.conditionInstance = (RuleInstance)schematicrule;
      }
   }

   @Override
   boolean reapply(DerivationLineChecker derivationlinechecker) {
      boolean flag = this.conditionLine == null && this.conditionPremise == null && this.conditionInstance == null;
      String s = flag ? "IE" : "CIE";
      if (!derivationlinechecker.ruleName.equals(s)) {
         return false;
      } else {
         derivationlinechecker.getClass();
         if (!derivationlinechecker.matchLine && !derivationlinechecker.finalStep
            ? derivationlinechecker.argumentCount >= 1
            : derivationlinechecker.argumentCount == 1) {
            if (derivationlinechecker.getStackFormula(-1).getSubexpression(this.path) == null) {
               return false;
            } else {
               if (this.ruleApplication != null) {
                  if (!isRuleUsable(derivationlinechecker, this.ruleApplication.form, flag)) {
                     return false;
                  }

                  if (!validateInnerRule(this.ruleApplication, derivationlinechecker, this.path, this.getConditionRule(derivationlinechecker))) {
                     return false;
                  }
               } else if (this.equivalenceLine != null) {
                  LPDerivation lpderivation = derivationlinechecker.line.box.module;
                  if (lpderivation.problem.findLine(this.equivalenceLine.getLineNumber()) != this.equivalenceLine.getHeadNode()) {
                     return false;
                  }

                  if (!derivationlinechecker.canUse(this.equivalenceLine)) {
                     return false;
                  }

                  if (!validateEquivalenceLine(this.equivalenceLine, derivationlinechecker, this.path, flag)) {
                     return false;
                  }
               } else {
                  if (this.equivalencePremise == null) {
                     return false;
                  }

                  int i = this.equivalencePremise;
                  LPDerivation lpderivation1 = derivationlinechecker.line.box.module;
                  if (lpderivation1.premises == null || i < 0 || i >= lpderivation1.premises.length) {
                     return false;
                  }

                  if (!validateEquivalencePremise(this.equivalencePremise, derivationlinechecker, this.path, flag)) {
                     return false;
                  }
               }

               if (derivationlinechecker.getCitedNodeOutsideBox(-1) != null) {
                  derivationlinechecker.line.box.strategyConsistent = false;
               }

               Expression expression = this.getSource(derivationlinechecker);
               if (!expression.isIdentical(derivationlinechecker.getStackFormula(-1))) {
                  return false;
               } else {
                  derivationlinechecker.result = this.getResult(derivationlinechecker);
                  if (derivationlinechecker.matchLine && !derivationlinechecker.checkResultMatchesLine(true)) {
                     return false;
                  } else {
                     derivationlinechecker.popStack(1);
                     return true;
                  }
               }
            }
         } else {
            return false;
         }
      }
   }

   static boolean isConditionSubstitutionClean(DerivationLineChecker derivationlinechecker, ExpressionPath expressionpath, SchematicRule schematicrule) {
      return schematicrule == null
         || replaceAt(derivationlinechecker.getStackFormula(-1), schematicrule.conclusion, expressionpath).findMislinkedVariables() == null;
   }

   static boolean validateInnerRule(
      RuleApplication ruleapplication, DerivationLineChecker derivationlinechecker, ExpressionPath expressionpath, SchematicRule schematicrule
   ) {
      SchematicRule schematicrule1 = ruleapplication.getForm();
      String s = schematicrule == null ? "notConditional" : "notConditionalBC";
      if (LogicProgram.ruleTable.properties != null && LogicProgram.ruleTable.properties.hasProperty(schematicrule1, s)) {
         derivationlinechecker.reportError("dererr098", Message.params("inner rule name", schematicrule1.name));
         return false;
      } else if (!isConditionSubstitutionClean(derivationlinechecker, expressionpath, schematicrule)) {
         derivationlinechecker.reportError("dererr099", Message.params("condition name", schematicrule.name));
         return false;
      } else {
         return true;
      }
   }

   static boolean validateEquivalenceLine(
      DerivationLine derivationline, DerivationLineChecker derivationlinechecker, ExpressionPath expressionpath, boolean flag
   ) {
      Expression expression;
      if (flag) {
         expression = RuleProperties.getEquivalence(derivationline.formula, false);
      } else {
         expression = RuleProperties.getConditionalEquivalence(derivationline.formula);
      }

      if (expression == null) {
         derivationlinechecker.reportError("dererr086", Message.params("remote line number", derivationline.getLineNumber() + ""));
         return false;
      } else if (replaceAt(derivationlinechecker.getStackFormula(-1), derivationline.formula, expressionpath).findMislinkedVariables() != null) {
         derivationlinechecker.reportError("dererr087", Message.params("remote line number", derivationline.getLineNumber() + ""));
         return false;
      } else {
         return true;
      }
   }

   static boolean validateEquivalencePremise(Integer integer, DerivationLineChecker derivationlinechecker, ExpressionPath expressionpath, boolean flag) {
      int i = integer;
      LPDerivation lpderivation = derivationlinechecker.line.box.module;
      Expression expression;
      if (flag) {
         expression = RuleProperties.getEquivalence(lpderivation.premises[i], false);
      } else {
         expression = RuleProperties.getConditionalEquivalence(lpderivation.premises[i]);
      }

      if (expression == null) {
         derivationlinechecker.reportError("dererr088", Message.params("premise index", i + 1 + ""));
         return false;
      } else if (replaceAt(derivationlinechecker.getStackFormula(-1), lpderivation.premises[i], expressionpath).findMislinkedVariables() != null) {
         derivationlinechecker.reportError("dererr089", Message.params("premise index", i + 1 + ""));
         return false;
      } else {
         return true;
      }
   }

   static SchemeInstantiation identityInstantiation(Expression expression) {
      SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
      expression.match(null, schemeinstantiation);
      boolean flag = false;
      int i = schemeinstantiation.pendingLetters.size();

      for (int j = 0; j < i; j++) {
         SchematicLetter schematicletter = (SchematicLetter)schemeinstantiation.pendingLetters.elementAt(0);
         Expression expression1 = schematicletter.toExpression();
         schemeinstantiation.putReplacement(schematicletter, new LetterReplacement(expression1, expression1));
      }

      return schemeinstantiation;
   }

   HighlightedText toHighlightedText(DerivationLineChecker derivationlinechecker) {
      return this.toHighlightedText(derivationlinechecker, true);
   }

   HighlightedText toHighlightedText(DerivationLineChecker derivationlinechecker, boolean flag) {
      if (this.ruleApplication == null) {
         HighlightedText highlightedtext = this.createDisplay(this.getSource(derivationlinechecker, true)).toStyledText();
         HighlightedText highlightedtext1 = this.createDisplay(this.getResult(derivationlinechecker, true)).toStyledText();
         if (flag && !highlightedtext.sharesHighlightLayer(highlightedtext1)) {
            return highlightedtext1;
         } else {
            highlightedtext.append(".:");
            highlightedtext.append(highlightedtext1);
            return highlightedtext;
         }
      } else {
         return this.toRuleApplication().createDisplay(null).toHighlightedText(flag);
      }
   }

   FormulaParseNode createDisplay(Expression expression) {
      FormulaParseNode formulaparsenode = new FormulaParseNode(expression, true, -1);
      formulaparsenode.addLetterRanges(this.instantiation.pendingLetters);
      formulaparsenode.addTermRanges(this.boundVariables);
      return formulaparsenode;
   }

   @Override
   String encode() {
      return "4:" + this;
   }

   @Override
   public String toString() {
      String object = "";
      if (this.path != null) {
         object = object + this.path;
      }

      String object1 = object + (this.reversed ? "<" : ">");
      if (this.ruleApplication != null) {
         object1 = object1 + this.ruleApplication;
      } else if (this.equivalenceLine != null) {
         object1 = object1 + this.equivalenceLine.getLineNumber();
         if (this.instantiation != null) {
            object1 = object1 + "," + this.instantiation.encode();
         }

         if (this.boundVariables != null) {
            object1 = object1 + ";" + this.boundVariables.encode();
         }
      } else if (this.equivalencePremise != null) {
         object1 = object1 + "#" + this.equivalencePremise;
         if (this.instantiation != null) {
            object1 = object1 + "," + this.instantiation.encode();
         }

         if (this.boundVariables != null) {
            object1 = object1 + ";" + this.boundVariables.encode();
         }
      }

      if (this.conditionLine != null) {
         object = object1 + (this.conditionReversed ? "?<" : "?>");
         object1 = object + this.conditionLine.getLineNumber();
      } else if (this.conditionPremise != null) {
         object = object1 + (this.conditionReversed ? "?<" : "?>");
         object1 = object + "#" + this.conditionPremise;
      } else if (this.conditionInstance != null) {
         object = object1 + (this.conditionReversed ? "?<" : "?>");
         object1 = object + "#" + this.conditionInstance.encode();
      }

      return (String)object1;
   }

   static InterchangeJustification decodeInterchange(String s, LPDerivation lpderivation) {
      int i = s.indexOf(":");
      if (i == -1) {
         return null;
      } else {
         return !s.substring(0, i).equals(Integer.toString(4)) ? null : decodeBody(s.substring(i + 1), lpderivation);
      }
   }

   static InterchangeJustification decodeBody(String s, LPDerivation lpderivation) {
      int i = TaggedRecord.minNonNegative(s.indexOf("?>"), s.indexOf("?<"));
      String s1 = i == -1 ? null : s.substring(i + 1);
      if (i != -1) {
         s = s.substring(0, i);
      }

      i = TaggedRecord.minNonNegative(s.indexOf(">"), s.indexOf("<"));
      if (i == -1) {
         return null;
      } else {
         InterchangeJustification interchangejustification = new InterchangeJustification();
         interchangejustification.path = i == 0 ? null : ExpressionPath.fromArray(ExpressionPath.parse(s.substring(0, i)));
         interchangejustification.reversed = s.charAt(i) == '<';
         String s2 = s.substring(i + 1);
         if ((interchangejustification.ruleApplication = RuleApplication.decodeBody(s2)) == null
            && (interchangejustification.equivalenceLine = parseLineReference(s2, lpderivation)) == null
            && (interchangejustification.equivalencePremise = parsePremiseReference(s2, lpderivation)) != null) {
         }

         if (interchangejustification.equivalenceLine != null || interchangejustification.equivalencePremise != null) {
            interchangejustification.instantiation = parseInstantiation(s2);
            interchangejustification.boundVariables = parseBoundVariables(s2);
         }

         if (s1 != null) {
            interchangejustification.conditionReversed = s1.charAt(0) == '<';
            s1 = s1.substring(1);
            if ((interchangejustification.conditionLine = parseLineReference(s1, lpderivation)) == null
               && (interchangejustification.conditionPremise = parsePremiseReference(s1, lpderivation)) == null
               && (interchangejustification.conditionInstance = parseConditionInstance(s1)) != null) {
            }
         }

         return interchangejustification;
      }
   }

   static DerivationLine parseLineReference(String s, LPDerivation lpderivation) {
      int i = s.indexOf(",");
      if (i != -1 || (i = s.indexOf(";")) != -1) {
         s = s.substring(0, i);
      }

      Integer integer = LogicProgram.parseInteger(s);
      if (integer == null) {
         return null;
      } else {
         DerivationNode derivationnode = lpderivation.problem.findLine(integer);
         if (derivationnode == null) {
            return null;
         } else {
            return derivationnode instanceof DerivationLine ? (DerivationLine)derivationnode : ((DerivationBox)derivationnode).showLine;
         }
      }
   }

   static Integer parsePremiseReference(String s, LPDerivation lpderivation) {
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

   static SchemeInstantiation parseInstantiation(String s) {
      int i = s.indexOf(",");
      if (i == -1) {
         return null;
      } else {
         String s1 = s.substring(i + 1);
         if ((i = s1.indexOf(";")) != -1) {
            s1 = s1.substring(0, i);
         }

         return SchemeInstantiation.decode(s1);
      }
   }

   static BoundVariableMap parseBoundVariables(String s) {
      int i = s.indexOf(";");
      return i == -1 ? null : BoundVariableMap.decode(s.substring(i + 1));
   }

   static RuleInstance parseConditionInstance(String s) {
      return !s.startsWith("#") ? null : RuleInstance.decode(s.substring(1));
   }

   static Expression replaceAt(Expression expression, Expression expression1, ExpressionPath expressionpath) {
      Expression expression2 = expression1.copy();
      if (expressionpath.depth == 0) {
         return expression2;
      } else {
         expression = expression.copy();
         expression.getSubexpression(expressionpath.indexes, 0, expressionpath.depth - 1, null)
            .children
            .setElementAt(expression2, expressionpath.indexes[expressionpath.depth - 1]);
         return expression;
      }
   }

   Expression getSource(DerivationLineChecker derivationlinechecker) {
      return this.getSource(derivationlinechecker, false);
   }

   Expression getSource(DerivationLineChecker derivationlinechecker, boolean flag) {
      boolean flag1 = this.conditionLine == null && this.conditionPremise == null && this.conditionInstance == null;
      Expression expression1;
      if (this.ruleApplication != null) {
         BinderMap bindermap = new BinderMap();
         Expression expression;
         if (flag1) {
            expression = RuleProperties.getFromSide(this.ruleApplication.form, this.reversed);
         } else {
            expression = RuleProperties.getConditionalFromSide(this.ruleApplication.form, this.conditionReversed, this.reversed);
         }

         if (expression == null) {
            return null;
         }

         expression1 = expression.instantiate(this.ruleApplication.instantiation, bindermap);
         if (!this.ruleApplication.boundVariables.renameBinders(expression, expression1, bindermap, flag ? null : derivationlinechecker)) {
            return null;
         }
      } else {
         if (this.equivalenceLine != null) {
            expression1 = this.equivalenceLine.formula;
         } else if (this.equivalencePremise != null) {
            expression1 = derivationlinechecker.line.box.module.premises[this.equivalencePremise];
         } else {
            expression1 = null;
         }

         Expression expression2;
         if (flag1) {
            expression2 = RuleProperties.getEquivalenceSide(expression1, false, this.reversed ? 1 : 0);
         } else {
            expression2 = RuleProperties.getConditionalEquivalencePart(expression1, this.conditionReversed ? 0 : 1, this.reversed ? 1 : 0);
         }

         if (expression2 == null) {
            return null;
         }

         BinderMap bindermap1 = new BinderMap();
         expression1 = expression2.instantiate(this.instantiation, bindermap1);
         if (this.boundVariables != null) {
            this.boundVariables.renameBinders(expression2, expression1, bindermap1, flag ? null : derivationlinechecker);
         }
      }

      return replaceAt(derivationlinechecker.getStackFormula(-1), expression1, this.path).linkVariables();
   }

   Expression getResult(DerivationLineChecker derivationlinechecker) {
      return this.getResult(derivationlinechecker, false);
   }

   Expression getResult(DerivationLineChecker derivationlinechecker, boolean flag) {
      boolean flag1 = this.conditionLine == null && this.conditionPremise == null && this.conditionInstance == null;
      Expression expression1;
      if (this.ruleApplication != null) {
         BinderMap bindermap = new BinderMap();
         Expression expression;
         if (flag1) {
            expression = RuleProperties.getToSide(this.ruleApplication.form, this.reversed);
         } else {
            expression = RuleProperties.getConditionalToSide(this.ruleApplication.form, this.conditionReversed, this.reversed);
         }

         if (expression == null) {
            return null;
         }

         expression1 = expression.instantiate(this.ruleApplication.instantiation, bindermap);
         if (!this.ruleApplication.boundVariables.renameBinders(expression, expression1, bindermap, flag ? null : derivationlinechecker)) {
            return null;
         }
      } else {
         if (this.equivalenceLine != null) {
            expression1 = this.equivalenceLine.formula;
         } else if (this.equivalencePremise != null) {
            expression1 = derivationlinechecker.line.box.module.premises[this.equivalencePremise];
         } else {
            expression1 = null;
         }

         Expression expression2;
         if (flag1) {
            expression2 = RuleProperties.getEquivalenceSide(expression1, false, this.reversed ? 0 : 1);
         } else {
            expression2 = RuleProperties.getConditionalEquivalencePart(expression1, this.conditionReversed ? 0 : 1, this.reversed ? 0 : 1);
         }

         if (expression2 == null) {
            return null;
         }

         BinderMap bindermap1 = new BinderMap();
         expression1 = expression2.instantiate(this.instantiation, bindermap1);
         if (this.boundVariables != null && !this.boundVariables.renameBinders(expression2, expression1, bindermap1, flag ? null : derivationlinechecker)) {
            return null;
         }
      }

      return replaceAt(derivationlinechecker.getStackFormula(-1), expression1, this.path).linkVariables();
   }

   SchematicRule getConditionRule(DerivationLineChecker derivationlinechecker) {
      if (this.conditionLine != null) {
         return new LineRule(this.conditionLine);
      } else if (this.conditionPremise != null) {
         return new PremiseRule(derivationlinechecker.line.box.module, this.conditionPremise + 1);
      } else {
         return this.conditionInstance != null ? this.conditionInstance : null;
      }
   }

   static Expression alignConditionQuantifiers(SchemeInstantiation schemeinstantiation, Expression expression, Expression expression1) {
      schemeinstantiation = (SchemeInstantiation)schemeinstantiation.clone();
      expression.match(null, schemeinstantiation);
      Vector vector = schemeinstantiation.assignFreshLetters(expression1).pendingLetters;
      Expression expression4 = expression.instantiate(schemeinstantiation);
      int i = countLeadingUniversals(expression4);
      int j = countLeadingUniversals(expression1);
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
            Vector vector2 = expression1.findOccurrences(expression2);
            int i1 = vector2.size();

            for (int j1 = 0; j1 < i1; j1++) {
               Expression expression3 = expression4.getSubexpression((ExpressionPath)vector2.elementAt(j1));
               if (expression3 != null && !mentionsAnyLetter(expression3, vector) && !schemeinstantiation1.addReplacement(expression2, expression3)) {
                  return null;
               }
            }
         }

         return expression1.instantiate(schemeinstantiation1);
      }
   }

   SchematicRule getEquivalenceRule(DerivationLineChecker derivationlinechecker) {
      if (this.ruleApplication != null) {
         return this.ruleApplication.getForm();
      } else if (this.equivalenceLine != null) {
         return new LineRule(this.equivalenceLine);
      } else {
         return this.equivalencePremise != null ? new PremiseRule(derivationlinechecker.line.box.module, this.equivalencePremise + 1) : null;
      }
   }

   RuleApplication toRuleApplication() {
      if (this.ruleApplication == null) {
         return null;
      } else {
         SchematicRule schematicrule = new SchematicRule(this.ruleApplication.form.name);
         boolean flag = this.ruleApplication.form.premises.length == 0;
         Expression expression;
         if (this.conditionLine == null && this.conditionPremise == null && this.conditionInstance == null) {
            if (!flag) {
               return this.ruleApplication;
            }

            expression = RuleProperties.getEquivalenceOrConditional(this.ruleApplication.form.conclusion);
         } else if (flag) {
            expression = RuleProperties.getConditionalEquivalence(this.ruleApplication.form.conclusion);
            if (expression != null) {
               expression = expression.getChild(this.conditionReversed ? 0 : 1);
            }
         } else {
            expression = this.ruleApplication.form.conclusion;
         }

         if (expression == null) {
            return null;
         } else {
            schematicrule.premises = new Expression[]{expression.getChild(this.reversed ? 1 : 0)};
            schematicrule.conclusion = expression.getChild(this.reversed ? 0 : 1);
            return new RuleApplication(schematicrule, new int[]{0}, this.ruleApplication.instantiation, this.ruleApplication.boundVariables);
         }
      }
   }

   static Vector findApplications(DerivationLineChecker derivationlinechecker, ExpressionPath expressionpath, Rule rule, SchematicRule schematicrule) {
      Expression expression = derivationlinechecker.getStackFormula(-1).getSubexpression(expressionpath).copy();
      Expression expression1 = derivationlinechecker.matchLine && !derivationlinechecker.interactive ? derivationlinechecker.lineFormula : null;
      if (expression1 != null) {
         expression1 = expression1.getSubexpression(expressionpath).copy();
      }

      RuleProperties ruleproperties = LogicProgram.ruleTable.properties;
      String s = schematicrule == null ? "notConditional" : "notConditionalBC";
      SchematicRule[] aschematicrule = rule.getForms(ruleproperties, s);
      LPDerivation lpderivation = derivationlinechecker.line.box.module;
      String s1 = derivationlinechecker.interactive ? "manualOrDisabled" : "disabled";
      SchematicRule[] aschematicrule1 = rule.getForms(lpderivation, s1);
      Vector vector = new Vector();
      Vector vector1 = new Vector();

      for (SchematicRule schematicrule1 : aschematicrule) {
         Vector vector2 = vector;
         if (LogicProgram.indexOf(aschematicrule1, schematicrule1) == -1) {
            vector2 = vector1;
         } else if (!isRuleUsable(derivationlinechecker, schematicrule1, schematicrule == null)) {
            vector2 = vector1;
         }

         Expression expression2;
         Expression expression3;
         boolean flag;
         int[] aint;
         Expression expression4;
         if (schematicrule1.premises.length == 0) {
            if (schematicrule == null) {
               expression4 = RuleProperties.getEquivalenceOrConditional(schematicrule1.conclusion);
            } else {
               expression4 = RuleProperties.getConditionalEquivalence(schematicrule1.conclusion);
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
            if (schematicrule1 instanceof LineRule) {
               if (expression4 != null && flag) {
                  DerivationLine derivationline = ((LineRule)schematicrule1).line;
                  SchemeInstantiation schemeinstantiation = identityInstantiation(schematicrule1.conclusion);
                  BoundVariableMap boundvariablemap = matchEquivalence(schemeinstantiation, expression2, expression3, expression, expression1);
                  if (boundvariablemap != null) {
                     vector2.addElement(new InterchangeJustification(expressionpath, false, derivationline, schemeinstantiation, boundvariablemap));
                  }

                  schemeinstantiation = identityInstantiation(schematicrule1.conclusion);
                  boundvariablemap = matchEquivalence(schemeinstantiation, expression3, expression2, expression, expression1);
                  if (boundvariablemap != null) {
                     vector2.addElement(new InterchangeJustification(expressionpath, true, derivationline, schemeinstantiation, boundvariablemap));
                  }
               }
            } else if (schematicrule1 instanceof PremiseRule) {
               if (expression4 != null && flag) {
                  Integer integer = ((PremiseRule)schematicrule1).premiseIndex;
                  SchemeInstantiation schemeinstantiation1 = identityInstantiation(schematicrule1.conclusion);
                  BoundVariableMap boundvariablemap1 = matchEquivalence(schemeinstantiation1, expression2, expression3, expression, expression1);
                  if (boundvariablemap1 != null) {
                     vector2.addElement(new InterchangeJustification(expressionpath, false, integer, schemeinstantiation1, boundvariablemap1));
                  }

                  schemeinstantiation1 = identityInstantiation(schematicrule1.conclusion);
                  boundvariablemap1 = matchEquivalence(schemeinstantiation1, expression3, expression2, expression, expression1);
                  if (boundvariablemap1 != null) {
                     vector2.addElement(new InterchangeJustification(expressionpath, true, integer, schemeinstantiation1, boundvariablemap1));
                  }
               }
            } else {
               SchemeInstantiation schemeinstantiation2 = new SchemeInstantiation();
               BoundVariableMap boundvariablemap2 = matchEquivalence(schemeinstantiation2, expression2, expression3, expression, expression1);
               if (boundvariablemap2 != null) {
                  RuleApplication ruleapplication = new RuleApplication(schematicrule1, aint, schemeinstantiation2, boundvariablemap2);
                  vector2.addElement(new InterchangeJustification(expressionpath, false, ruleapplication));
               }

               if (flag) {
                  schemeinstantiation2 = new SchemeInstantiation();
                  boundvariablemap2 = matchEquivalence(schemeinstantiation2, expression3, expression2, expression, expression1);
                  if (boundvariablemap2 != null) {
                     RuleApplication ruleapplication1 = new RuleApplication(schematicrule1, aint, schemeinstantiation2, boundvariablemap2);
                     vector2.addElement(new InterchangeJustification(expressionpath, true, ruleapplication1));
                  }
               }
            }
         } else if (schematicrule1 instanceof LineRule) {
            if (expression4 != null) {
               DerivationLine derivationline1 = ((LineRule)schematicrule1).line;
               if (expression3.symbol.equals("<->")) {
                  SchemeInstantiation schemeinstantiation3 = identityInstantiation(schematicrule1.conclusion);
                  BoundVariableMap boundvariablemap3 = matchConditionalEquivalence(
                     schemeinstantiation3, expression3.getChild(0), expression3.getChild(1), expression2, expression, expression1, schematicrule.conclusion
                  );
                  if (boundvariablemap3 != null) {
                     vector2.addElement(
                        new InterchangeJustification(expressionpath, false, derivationline1, schemeinstantiation3, boundvariablemap3, false, schematicrule)
                     );
                  }

                  schemeinstantiation3 = identityInstantiation(schematicrule1.conclusion);
                  boundvariablemap3 = matchConditionalEquivalence(
                     schemeinstantiation3, expression3.getChild(1), expression3.getChild(0), expression2, expression, expression1, schematicrule.conclusion
                  );
                  if (boundvariablemap3 != null) {
                     vector2.addElement(
                        new InterchangeJustification(expressionpath, true, derivationline1, schemeinstantiation3, boundvariablemap3, false, schematicrule)
                     );
                  }
               }

               if (flag && expression2.symbol.equals("<->")) {
                  SchemeInstantiation schemeinstantiation4 = identityInstantiation(schematicrule1.conclusion);
                  BoundVariableMap boundvariablemap4 = matchConditionalEquivalence(
                     schemeinstantiation4, expression2.getChild(0), expression2.getChild(1), expression3, expression, expression1, schematicrule.conclusion
                  );
                  if (boundvariablemap4 != null) {
                     vector2.addElement(
                        new InterchangeJustification(expressionpath, false, derivationline1, schemeinstantiation4, boundvariablemap4, true, schematicrule)
                     );
                  }

                  schemeinstantiation4 = identityInstantiation(schematicrule1.conclusion);
                  boundvariablemap4 = matchConditionalEquivalence(
                     schemeinstantiation4, expression2.getChild(1), expression2.getChild(0), expression3, expression, expression1, schematicrule.conclusion
                  );
                  if (boundvariablemap4 != null) {
                     vector2.addElement(
                        new InterchangeJustification(expressionpath, true, derivationline1, schemeinstantiation4, boundvariablemap4, true, schematicrule)
                     );
                  }
               }
            }
         } else if (schematicrule1 instanceof PremiseRule) {
            if (expression4 != null) {
               Integer integer1 = ((PremiseRule)schematicrule1).premiseIndex;
               if (expression3.symbol.equals("<->")) {
                  SchemeInstantiation schemeinstantiation5 = identityInstantiation(schematicrule1.conclusion);
                  BoundVariableMap boundvariablemap5 = matchConditionalEquivalence(
                     schemeinstantiation5, expression3.getChild(0), expression3.getChild(1), expression2, expression, expression1, schematicrule.conclusion
                  );
                  if (boundvariablemap5 != null) {
                     vector2.addElement(
                        new InterchangeJustification(expressionpath, false, integer1, schemeinstantiation5, boundvariablemap5, false, schematicrule)
                     );
                  }

                  schemeinstantiation5 = identityInstantiation(schematicrule1.conclusion);
                  boundvariablemap5 = matchConditionalEquivalence(
                     schemeinstantiation5, expression3.getChild(1), expression3.getChild(0), expression2, expression, expression1, schematicrule.conclusion
                  );
                  if (boundvariablemap5 != null) {
                     vector2.addElement(
                        new InterchangeJustification(expressionpath, true, integer1, schemeinstantiation5, boundvariablemap5, false, schematicrule)
                     );
                  }
               }

               if (flag && expression2.symbol.equals("<->")) {
                  SchemeInstantiation schemeinstantiation6 = identityInstantiation(schematicrule1.conclusion);
                  BoundVariableMap boundvariablemap6 = matchConditionalEquivalence(
                     schemeinstantiation6, expression2.getChild(0), expression2.getChild(1), expression3, expression, expression1, schematicrule.conclusion
                  );
                  if (boundvariablemap6 != null) {
                     vector2.addElement(
                        new InterchangeJustification(expressionpath, false, integer1, schemeinstantiation6, boundvariablemap6, true, schematicrule)
                     );
                  }

                  schemeinstantiation6 = identityInstantiation(schematicrule1.conclusion);
                  boundvariablemap6 = matchConditionalEquivalence(
                     schemeinstantiation6, expression2.getChild(1), expression2.getChild(0), expression3, expression, expression1, schematicrule.conclusion
                  );
                  if (boundvariablemap6 != null) {
                     vector2.addElement(
                        new InterchangeJustification(expressionpath, true, integer1, schemeinstantiation6, boundvariablemap6, true, schematicrule)
                     );
                  }
               }
            }
         } else {
            if (expression3.symbol.equals("<->")) {
               SchemeInstantiation schemeinstantiation7 = new SchemeInstantiation();
               BoundVariableMap boundvariablemap7 = matchConditionalEquivalence(
                  schemeinstantiation7, expression3.getChild(0), expression3.getChild(1), expression2, expression, expression1, schematicrule.conclusion
               );
               if (boundvariablemap7 != null) {
                  RuleApplication ruleapplication2 = new RuleApplication(schematicrule1, aint, schemeinstantiation7, boundvariablemap7);
                  vector2.addElement(new InterchangeJustification(expressionpath, false, ruleapplication2, false, schematicrule));
               }

               schemeinstantiation7 = new SchemeInstantiation();
               boundvariablemap7 = matchConditionalEquivalence(
                  schemeinstantiation7, expression3.getChild(1), expression3.getChild(0), expression2, expression, expression1, schematicrule.conclusion
               );
               if (boundvariablemap7 != null) {
                  RuleApplication ruleapplication3 = new RuleApplication(schematicrule1, aint, schemeinstantiation7, boundvariablemap7);
                  vector2.addElement(new InterchangeJustification(expressionpath, true, ruleapplication3, false, schematicrule));
               }
            }

            if (flag && expression2.symbol.equals("<->")) {
               SchemeInstantiation schemeinstantiation8 = new SchemeInstantiation();
               BoundVariableMap boundvariablemap8 = matchConditionalEquivalence(
                  schemeinstantiation8, expression2.getChild(0), expression2.getChild(1), expression3, expression, expression1, schematicrule.conclusion
               );
               if (boundvariablemap8 != null) {
                  RuleApplication ruleapplication4 = new RuleApplication(schematicrule1, aint, schemeinstantiation8, boundvariablemap8);
                  vector2.addElement(new InterchangeJustification(expressionpath, false, ruleapplication4, true, schematicrule));
               }

               schemeinstantiation8 = new SchemeInstantiation();
               boundvariablemap8 = matchConditionalEquivalence(
                  schemeinstantiation8, expression2.getChild(1), expression2.getChild(0), expression3, expression, expression1, schematicrule.conclusion
               );
               if (boundvariablemap8 != null) {
                  RuleApplication ruleapplication5 = new RuleApplication(schematicrule1, aint, schemeinstantiation8, boundvariablemap8);
                  vector2.addElement(new InterchangeJustification(expressionpath, true, ruleapplication5, true, schematicrule));
               }
            }
         }
      }

      return vector1.isEmpty() && vector.isEmpty() ? null : vector;
   }

   static BoundVariableMap matchEquivalence(
      SchemeInstantiation schemeinstantiation, Expression expression, Expression expression1, Expression expression2, Expression expression3
   ) {
      return matchConditionalEquivalence(schemeinstantiation, expression, expression1, null, expression2, expression3, null);
   }

   static BoundVariableMap matchConditionalEquivalence(
      SchemeInstantiation schemeinstantiation,
      Expression expression,
      Expression expression1,
      Expression expression2,
      Expression expression3,
      Expression expression4,
      Expression expression5
   ) {
      BinderMap bindermap = new BinderMap();
      if (!expression.match(expression3, schemeinstantiation, bindermap)) {
         return null;
      } else if (!expression1.match(expression4, schemeinstantiation, bindermap)) {
         return null;
      } else {
         if (expression5 != null) {
            expression5 = alignConditionQuantifiers(schemeinstantiation, expression2, expression5);
            if (expression5 == null) {
               return null;
            }
         }

         if (expression2 != null && !expression2.match(expression5, schemeinstantiation, bindermap)) {
            return null;
         } else {
            BoundVariableMap boundvariablemap = new BoundVariableMap();
            if (!boundvariablemap.matchBinders(expression, expression3, bindermap)) {
               return null;
            } else if (!boundvariablemap.matchBinders(expression1, expression4, bindermap)) {
               return null;
            } else if (expression2 != null && !boundvariablemap.matchBinders(expression2, expression5, bindermap)) {
               return null;
            } else {
               bindermap = new BinderMap();
               Expression expression6 = expression.instantiate(schemeinstantiation, bindermap);
               Expression expression7 = expression1.instantiate(schemeinstantiation, bindermap);
               Expression expression8 = expression2 == null ? null : expression2.instantiate(schemeinstantiation, bindermap);
               if (!boundvariablemap.renameBinders(expression, expression6, bindermap, null)) {
                  return null;
               } else if (!boundvariablemap.renameBinders(expression1, expression7, bindermap, null)) {
                  return null;
               } else {
                  return expression2 != null && !boundvariablemap.renameBinders(expression2, expression8, bindermap, null) ? null : boundvariablemap;
               }
            }
         }
      }
   }

   static boolean mentionsAnyLetter(Expression expression, Vector vector) {
      int i = vector == null ? 0 : vector.size();

      for (int j = 0; j < i; j++) {
         if (!expression.findLetterOccurrences((SchematicLetter)vector.elementAt(j)).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   static int countLeadingUniversals(Expression expression) {
      Expression expression1 = expression;

      int i;
      for (i = 0; expression1.symbol.equals("@"); expression1 = expression1.getChild(1)) {
         i++;
      }

      return i;
   }

   static boolean isRuleUsable(DerivationLineChecker derivationlinechecker, SchematicRule schematicrule, boolean flag) {
      if (schematicrule instanceof PremiseRule) {
         return true;
      } else if (schematicrule instanceof LineRule) {
         return derivationlinechecker.canUse(((LineRule)schematicrule).line);
      } else {
         LPDerivation lpderivation = derivationlinechecker.line.box.module;
         String s = derivationlinechecker.interactive ? "manualOrDisabled" : "disabled";
         if (lpderivation.hasProperty(schematicrule, s)) {
            return false;
         } else if (!schematicrule.isProven(lpderivation)) {
            lpderivation.proofMissing = true;
            return false;
         } else if (!flag) {
            return true;
         } else {
            RuleProperties ruleproperties = LogicProgram.ruleTable.properties;
            Vector vector = ruleproperties.getConverses(schematicrule);
            int i = vector == null ? 0 : vector.size();

            for (int j = 0; j < i; j++) {
               Rule rule = LPDerivation.getRule((String)vector.elementAt(j));
               if (rule instanceof SchematicRule && !lpderivation.hasProperty(rule, s)) {
                  if (rule.isProven(lpderivation)) {
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
