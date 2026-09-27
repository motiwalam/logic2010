package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

class DerivationLineChecker implements MessageParamSource, DerivationConstants {
   DerivationLine line;
   Vector citedNodes;
   Vector stack;
   int argumentCount;
   String ruleName;
   String remaining;
   Expression lineFormula;
   Expression result;
   int stepIndex;
   boolean matchLine;
   boolean finalStep;
   boolean interactive;
   boolean reusedCache;
   ErrorRef cachedError;
   final boolean CONST_TRUE = true;
   final boolean CONST_FALSE = false;
   boolean hasPremiseMatch;
   boolean hasFullMatch;
   Vector premiseMatches;
   Vector fullMatches;
   Vector consumedFormulas;
   Vector presetAnswers;
   Expression assertion;
   Expression target;
   boolean preview;
   ErrorRef previewError;
   SchematicRule[] allForms;
   SchematicRule[] enabledForms;
   SchematicRule[] automaticForms;
   int minPremises;
   int maxPremises;
   Vector clashes;

   DerivationLineChecker(DerivationLine derivationline, boolean flag) {
      this.line = derivationline;
      this.interactive = flag;
      this.reset();
   }

   /**
    * A checker that previews justification text s (the stack view): it records the first
    * error instead of showing it and caches nothing. The caller also sets serialMode so
    * that no dialog opens, and restores the state the checker changes (DerivationStackView).
    */
   DerivationLineChecker(DerivationLine derivationline, String s) {
      this(derivationline, false);
      this.preview = true;
      this.remaining = s;
   }

   void reset() {
      this.citedNodes = new Vector();
      this.stack = new Vector();
      this.argumentCount = 0;
      this.ruleName = null;
      this.remaining = this.line.getAnnotationText(true);
      this.lineFormula = this.line.getFormula();
      this.result = null;
      this.stepIndex = -1;
      this.matchLine = false;
      this.finalStep = false;
      this.reusedCache = false;
      this.cachedError = null;
      this.premiseMatches = null;
      this.hasPremiseMatch = false;
      this.fullMatches = null;
      this.hasFullMatch = false;
      this.consumedFormulas = null;
      this.presetAnswers = null;
      this.assertion = null;
      this.target = null;
      this.allForms = null;
      this.enabledForms = null;
      this.automaticForms = null;
      this.clashes = null;
      this.computePremiseRange();
   }

   void computePremiseRange() {
      this.minPremises = -1;
      this.maxPremises = -1;
      if (this.automaticForms != null) {
         int i = this.automaticForms.length;

         for (int j = 0; j < i; j++) {
            int k = this.automaticForms[j].premises.length;
            if (k <= this.argumentCount && (!this.matchLine && !this.finalStep || k == this.argumentCount)) {
               if (k > this.maxPremises) {
                  this.maxPremises = k;
               }

               if (this.minPremises == -1 || k < this.minPremises) {
                  this.minPremises = k;
               }
            }
         }
      }
   }

   boolean readNextStep() {
      this.ruleName = null;
      this.assertion = null;
      if (this.remaining == null) {
         return true;
      } else {
         boolean flag = false;
         char c0 = '\u0000';
         boolean flag1 = true;
         boolean flag2 = true;
         boolean flag3 = false;
         boolean flag4 = false;
         if (this.result != null) {
            this.citedNodes.addElement(null);
            this.stack.addElement(this.result);
         }

         DerivationBox derivationbox = this.line.box.module.problem;

         while (flag2 && flag1) {
            int k = 0;

            int l;
            for (l = this.remaining.length(); k < l; k++) {
               c0 = this.remaining.charAt(k);
               if (Character.isDigit(c0)) {
                  break;
               }

               if (isNameChar(c0)) {
                  flag1 = false;
                  break;
               }

               if (c0 == '[' || c0 == ']') {
                  this.reportError("dererr112", Message.params("assertion", this.remaining.substring(k)));
                  return false;
               }
            }

            if (k >= l) {
               if (flag3) {
                  this.reportError("dererr001");
                  return false;
               }

               if (flag4) {
                  this.reportError("dererr018");
                  return false;
               }

               if (this.stack.size() != 0) {
                  this.reportError("dererr002");
                  return false;
               }

               return true;
            }

            int i;
            for (i = k++; k < l; k++) {
               c0 = this.remaining.charAt(k);
               if (isNameChar(c0)) {
                  if (flag1) {
                     flag2 = false;
                  }
               } else if (!Character.isDigit(c0)) {
                  break;
               }
            }

            int j = k++;
            if (flag2 && flag1) {
               if (flag3) {
                  this.reportError("dererr001");
                  return false;
               }

               if (flag4) {
                  this.reportError("dererr018");
                  return false;
               }

               DerivationNode derivationnode;
               try {
                  derivationnode = derivationbox.findLine(Integer.parseInt(this.remaining.substring(i, j)));
               } catch (NumberFormatException numberformatexception) {
                  derivationnode = null;
               }

               if (derivationnode == null) {
                  this.reportError("dererr003", Message.params("remote line number", this.remaining.substring(i, j)));
                  return false;
               }

               if (!this.line.canUse(derivationnode)) {
                  return false;
               }

               Expression expression = derivationnode.getFormula();
               if (expression == null) {
                  String s = derivationnode.getFormulaText(true).trim().equals("") ? "dererr004" : "dererr005";
                  this.reportError(s, Message.params("remote line number", this.remaining.substring(i, j)));
                  return false;
               }

               this.citedNodes.addElement(derivationnode);
               this.stack.addElement(expression);
               this.remaining = this.remaining.substring(j);
            } else {
               this.ruleName = this.remaining.substring(i, j);
               this.remaining = this.remaining.substring(j);
               if (flag2) {
                  this.ruleName = this.normalizeRuleName(this.ruleName);
                  if (flag3) {
                     this.ruleName = "ASS " + this.ruleName;
                  } else if (flag4) {
                     this.ruleName = "SHOW " + this.ruleName;
                  } else {
                     if (this.ruleName.equals("ASS")) {
                        flag3 = true;
                        flag1 = true;
                        continue;
                     }

                     if (this.ruleName.equals("SHOW")) {
                        flag4 = true;
                        flag1 = true;
                        continue;
                     }
                  }
               } else {
                  this.reportError("dererr006");
               }

               return flag2 && this.readAssertion();
            }
         }

         return false;
      }
   }

   boolean skipToNextStep() {
      int i = this.remaining.length();

      for (int j = 0; j < i; j++) {
         char c0 = this.remaining.charAt(j);
         if (Character.isDigit(c0) || isNameChar(c0)) {
            this.remaining = this.remaining.substring(j);
            return true;
         }
      }

      return false;
   }

   Object readSourceFormula() {
      Integer integer = this.readLineNumber();
      if (integer != null) {
         DerivationNode derivationnode = this.line.box.module.problem.findLine(integer);
         if (derivationnode == null) {
            return new ErrorRef("dererr003", Message.params("remote line number", integer.toString()));
         } else if (!this.line.canUse(derivationnode)) {
            return new ErrorRef(null);
         } else {
            Expression expression = derivationnode.getFormula();
            if (expression == null) {
               String s = derivationnode.getFormulaText(true).trim().equals("") ? "dererr004" : "dererr005";
               return new ErrorRef(s, Message.params("remote line number", integer.toString()));
            } else {
               return expression;
            }
         }
      } else {
         int i = this.readPremiseNumber();
         if (i != -1) {
            Expression[] aexpression = this.line.box.module.premises;
            if (aexpression == null || aexpression.length == 0) {
               return new ErrorRef("dererr029");
            } else if (i > aexpression.length) {
               return new ErrorRef("dererr030", Message.params("premise index", i + ""));
            } else {
               if (i == 0) {
                  if (aexpression.length > 1) {
                     i = DerivationDialogs.chooseFormula(this.line, aexpression, DerivationMessage.format(DerivationMessage.getText("derdlg002"), null, this))
                        + 1;
                     if (i == 0) {
                        return new ErrorRef(null);
                     }
                  } else {
                     i = 1;
                  }
               }

               return aexpression[i - 1];
            }
         } else {
            return null;
         }
      }
   }

   Integer readLineNumber() {
      int k = 0;
      int l = this.remaining.length();
      boolean flag = false;

      char c0;
      for (c0 = 0; k < l; k++) {
         c0 = this.remaining.charAt(k);
         if (Character.isDigit(c0)) {
            break;
         }

         if (isNameChar(c0)) {
            return null;
         }
      }

      if (k >= l) {
         return null;
      } else {
         int i;
         for (i = k++; k < l; k++) {
            c0 = this.remaining.charAt(k);
            if (!Character.isDigit(c0)) {
               break;
            }
         }

         if (isNameChar(c0)) {
            return null;
         } else {
            int j = k++;
            Integer integer = LogicProgram.parseInteger(this.remaining.substring(i, j));
            if (integer != null) {
               this.remaining = this.remaining.substring(j);
            }

            return integer;
         }
      }
   }

   int readPremiseNumber() {
      int k = 0;
      int l = this.remaining.length();

      for (char c0 = '\u0000'; k < l; k++) {
         c0 = this.remaining.charAt(k);
         if (Character.isDigit(c0)) {
            return -1;
         }

         if (isNameChar(c0)) {
            break;
         }
      }

      if (k >= l) {
         return -1;
      } else {
         int i;
         for (i = k++; k < l; k++) {
            char c1 = this.remaining.charAt(k);
            if (!Character.isDigit(c1) && !isNameChar(c1)) {
               break;
            }
         }

         int j = k++;
         int i1 = parsePremiseNumber(this.remaining.substring(i, j).toUpperCase());
         if (i1 != -1) {
            this.remaining = this.remaining.substring(j);
         }

         return i1;
      }
   }

   static boolean isNameChar(char c0) {
      return !Character.isLetter(c0) && c0 < 256 ? "~!@#$%^&*(){}_+-=<>|/".indexOf(c0) != -1 : true;
   }

   String normalizeRuleName(String s) {
      if (s == null) {
         return null;
      } else {
         int i = s.indexOf("/");
         if (i != -1) {
            if (this.presetAnswers == null) {
               this.presetAnswers = new Vector();
            }

            String s1 = s.substring(i + 1);
            s = s.substring(0, i);

            while ((i = s1.indexOf("/")) != -1) {
               this.presetAnswers.addElement(s1.substring(0, i));
               s1 = s1.substring(i + 1);
            }

            this.presetAnswers.addElement(s1);
         }

         return s.toUpperCase();
      }
   }

   private void cacheJustification(Justification justification) {
      if (this.preview) {
         return;
      }

      this.line.justifications.setElementAt(justification, this.stepIndex);
   }

   private boolean fail() {
      this.line.justifications = null;
      this.line.box.strategyConsistent = false;
      return false;
   }

   static boolean containsWildcard(Expression expression) {
      if (expression.symbol.startsWith("?")) {
         return true;
      } else {
         int i = expression.getChildCount();

         for (int j = 0; j < i; j++) {
            if (containsWildcard(expression.getChild(j))) {
               return true;
            }
         }

         return false;
      }
   }

   static boolean matchesPattern(Expression expression, Expression expression1) {
      if (!expression1.symbol.startsWith("?")) {
         if (!expression.symbol.equals(expression1.symbol)) {
            return false;
         } else {
            int i = expression.getChildCount();
            if (expression1.getChildCount() != i) {
               return false;
            } else {
               for (int j = 0; j < i; j++) {
                  if (!matchesPattern(expression.getChild(j), expression1.getChild(j))) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else if (!(expression instanceof Formula)) {
         return false;
      } else if (expression.findMislinkedVariables() != null) {
         return false;
      } else if (expression1.symbol.equals("?PNX")) {
         return isPrenex(expression);
      } else if (expression1.symbol.equals("?NOV")) {
         return hasOnlyOuterQuantifiers(expression);
      } else if (expression1.symbol.equals("?DNF")) {
         return isNormalForm(expression, true);
      } else {
         return expression1.symbol.equals("?CNF") ? isNormalForm(expression, false) : expression1.symbol.equals("?");
      }
   }

   static boolean isPrenex(Expression expression) {
      return expression instanceof QuantifiedFormula ? isPrenex(expression.getChild(1)) : isQuantifierFree(expression);
   }

   static boolean hasOnlyOuterQuantifiers(Expression expression) {
      if (expression instanceof QuantifiedFormula) {
         return isQuantifierFree(expression.getChild(1));
      } else {
         int i = expression.getChildCount();

         for (int j = 0; j < i; j++) {
            if (!hasOnlyOuterQuantifiers(expression.getChild(j))) {
               return false;
            }
         }

         return true;
      }
   }

   static boolean isQuantifierFree(Expression expression) {
      if (expression instanceof QuantifiedFormula) {
         return false;
      } else {
         int i = expression.getChildCount();

         for (int j = 0; j < i; j++) {
            if (!isQuantifierFree(expression.getChild(j))) {
               return false;
            }
         }

         return true;
      }
   }

   static boolean isNormalForm(Expression expression, boolean flag) {
      if (expression instanceof QuantifiedFormula) {
         return isNormalForm(expression.getChild(1), flag);
      } else {
         String s = expression.symbol;
         if (!s.equals("->") && !s.equals("<->")) {
            int i = expression.getChildCount();

            for (int j = 0; j < i; j++) {
               Expression expression1 = expression.getChild(j);
               String s1 = expression1.symbol;
               if (s.equals("~")) {
                  if (s1.equals("~") || s1.equals("&") || s1.equals("|")) {
                     return false;
                  }
               } else if (flag) {
                  if (s.equals("&") && s1.equals("|")) {
                     return false;
                  }
               } else if (s.equals("|") && s1.equals("&")) {
                  return false;
               }

               if (!isNormalForm(expression1, flag)) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   boolean checkStep(boolean flag) {
      return this.checkStep(flag, false);
   }

   /**
    * Checks one step. A formula in brackets after the rule name ("MP[Q]") is the result the
    * step must have: it selects among the rule's possible results as the line's own formula
    * does on the last step, and the step fails if the rule cannot produce it.
    */
   boolean checkStep(boolean flag, boolean flag1) {
      this.target = this.assertion != null ? this.assertion : (flag ? this.lineFormula : null);
      if (this.assertion != null && flag && this.lineFormula != null && !this.assertion.isIdentical(this.lineFormula)) {
         this.reportError("dererr111");
         return this.fail();
      } else if (!this.applyStep(flag, flag1)) {
         return false;
      } else if (this.assertion != null && !this.assertionHolds()) {
         this.reportError("dererr110");
         return this.fail();
      } else {
         return true;
      }
   }

   /** Whether the step just applied produced the asserted formula. */
   boolean assertionHolds() {
      if (isStackOperation(this.ruleName)) {
         Expression expression = this.getStackFormula(-1);
         return expression != null && expression.isIdentical(this.assertion);
      } else if (this.ruleName.equals("CD") || this.ruleName.equals("ID") || this.ruleName.equals("DD") || this.ruleName.equals("UD") || this.ruleName.equals("BD")) {
         Expression expression = this.line.box.getFormula();
         return expression != null && expression.isIdentical(this.assertion);
      } else {
         return this.result != null && this.result.isIdentical(this.assertion);
      }
   }

   static boolean isStackOperation(String s) {
      return s.equals("DUP") || s.equals("DROP") || s.equals("SWAP");
   }

   /**
    * Reads "[formula]" right after a rule name (blanks may come between). Brackets nest, so a
    * formula may itself contain brackets.
    */
   boolean readAssertion() {
      int i = 0;
      int j = this.remaining.length();

      while (i < j && Character.isWhitespace(this.remaining.charAt(i))) {
         i++;
      }

      if (i < j && this.remaining.charAt(i) == ']') {
         this.reportError("dererr112", Message.params("assertion", this.remaining.substring(i)));
         return false;
      } else if (i >= j || this.remaining.charAt(i) != '[') {
         return true;
      } else {
         int k = i;
         int l = 0;

         for (; k < j; k++) {
            char c0 = this.remaining.charAt(k);
            if (c0 == '[') {
               l++;
            } else if (c0 == ']' && --l == 0) {
               break;
            }
         }

         if (k >= j) {
            this.reportError("dererr112", Message.params("assertion", this.remaining.substring(i)));
            return false;
         } else {
            String s = this.remaining.substring(i + 1, k);

            try {
               this.assertion = LogicProgram.parseFormula(LogicProgram.translateSymbols(s, DerivationLine.SYMBOLS, maggie));
            } catch (FormulaParseException formulaparseexception) {
               this.assertion = null;
            }

            if (this.assertion == null) {
               this.reportError("dererr112", Message.params("assertion", this.remaining.substring(i, k + 1)));
               return false;
            } else {
               this.remaining = this.remaining.substring(k + 1);
               return true;
            }
         }
      }
   }

   private boolean applyStep(boolean flag, boolean flag1) {
      this.argumentCount = this.getStackSize();
      this.matchLine = flag;
      this.finalStep = flag1;
      this.premiseMatches = null;
      this.hasPremiseMatch = false;
      this.fullMatches = null;
      this.hasFullMatch = false;
      this.allForms = null;
      this.enabledForms = null;
      this.automaticForms = null;
      this.computePremiseRange();
      this.stepIndex++;
      if (this.line.justifications == null) {
         this.line.justifications = new Vector();
      }

      int j = this.stepIndex + 1 - this.line.justifications.size();
      if (j > 0 || (flag || flag1) && j < 0) {
         this.line.justifications.setSize(this.stepIndex + 1);
      }

      ErrorRef errorref;
      if ((errorref = this.line.box.module.checkDerivationRule(this.ruleName, this.interactive)) != null) {
         this.reportError(errorref.getId());
         return this.fail();
      } else {
         if (isStackOperation(this.ruleName)) {
            if (flag || flag1) {
               this.reportError("dererr114");
               return this.fail();
            }
         } else if (!this.ruleName.equals("CD")
            && !this.ruleName.equals("ID")
            && !this.ruleName.equals("DD")
            && !this.ruleName.equals("UD")
            && !this.ruleName.equals("BD")) {
            if ((flag || flag1) && this.line.box.cancelLine == this.line) {
               this.reportError("dererr009");
               return this.fail();
            }
         } else {
            if (!flag && !flag1) {
               this.reportError("dererr007");
               return this.fail();
            }

            boolean flag2 = this.line.isLastInBox();
            this.line.readyToCancel = flag2 && this.line.box.cancelLine != this.line;
            if (!flag2) {
               this.reportError("dererr008");
               return this.fail();
            }
         }

         this.line.box.strategyConsistent &= (this.ruleName.equals("IE") || this.ruleName.equals("CIE") || this.ruleName.equals("BD") || this.ruleName.startsWith("ASS "));
         Justification justification = (Justification)this.line.justifications.elementAt(this.stepIndex);
         if (justification != null) {
            Vector vector = (Vector)this.stack.clone();
            Vector vector1 = (Vector)this.citedNodes.clone();
            Expression expression = this.result;
            if (justification.reapply(this)) {
               if (this.assertion == null || this.assertionHolds()) {
                  this.reusedCache = true;
                  return true;
               }

               this.stack = vector;
               this.citedNodes = vector1;
               this.result = expression;
               this.consumedFormulas = null;
               this.cachedError = null;
            }

            if (this.cachedError != null) {
               this.reportError(this.cachedError.id, this.cachedError.params);
               return this.fail();
            }

            this.cacheJustification(null);
         }

         if (isStackOperation(this.ruleName)) {
            return this.applyStackOperation();
         } else if (this.ruleName.equals("CD")) {
            if (this.argumentCount != 1) {
               this.reportError("dererr010", Message.params("n", "1"));
               return this.fail();
            } else {
               Expression expression6 = this.line.box.getFormula();
               Expression expression17 = this.getStackFormula(-1);
               if (expression6 == null) {
                  this.reportError(this.line.box.getFormulaText(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.fail();
               } else if (!expression6.getSymbol().equals("->")) {
                  this.reportError("dererr013");
                  return this.fail();
               } else if (!matchesPattern(expression17, expression6.getChild(1))) {
                  this.reportError("dererr014");
                  return this.fail();
               } else {
                  DerivationNode derivationnode4;
                  if ((derivationnode4 = this.getCitedNodeOutsideBox(-1)) != null) {
                     this.reportError("dererr015", Message.params("remote line number", derivationnode4.getLineNumber() + ""));
                     return this.fail();
                  } else {
                     if (this.line.box.cancelLine == this.line && this.line.box.module.serialMode && !this.preview) {
                        int i2 = this.line.box.assumptionType;
                        if (this.line.box.module.mixedModeDisabled && i2 != 2) {
                           this.reportError("dererr101");
                           return this.fail();
                        }

                        if (i2 != 0 && i2 != 1 && i2 != 2) {
                           this.reportError("dererr101");
                           return this.fail();
                        }

                        if ((errorref = this.line.box.module.checkDerivationRule("CD/" + ASS_STR[i2], this.interactive)) != null) {
                           this.reportError(errorref.id);
                           return this.fail();
                        }

                        this.line.box.showLine.showMessage(i2 == 2 ? "derinf001" : "derinf002", 4);
                     }

                     this.popStack(1);
                     return true;
                  }
               }
            }
         } else if (this.ruleName.equals("ID")) {
            if (this.argumentCount != 2) {
               this.reportError("dererr010", Message.params("n", "2"));
               return this.fail();
            } else {
               Expression expression5 = this.getStackFormula(-2);
               Expression expression16 = this.getStackFormula(-1);
               if (!expression5.isNegationOf(expression16) && !expression16.isNegationOf(expression5)) {
                  this.reportError("dererr017");
                  return this.fail();
               } else {
                  DerivationNode derivationnode3;
                  if ((derivationnode3 = this.getCitedNodeOutsideBox(-2)) == null && (derivationnode3 = this.getCitedNodeOutsideBox(-1)) == null) {
                     if (this.line.box.cancelLine == this.line && this.line.box.module.serialMode && !this.preview) {
                        int l1 = this.line.box.assumptionType;
                        if (this.line.box.module.mixedModeDisabled && l1 != 1) {
                           this.reportError("dererr101");
                           return this.fail();
                        }

                        if (l1 != 0 && l1 != 1 && l1 != 2) {
                           this.reportError("dererr101");
                           return this.fail();
                        }

                        if ((errorref = this.line.box.module.checkDerivationRule("ID/" + ASS_STR[l1], this.interactive)) != null) {
                           this.reportError(errorref.id);
                           return this.fail();
                        }

                        this.line.box.showLine.showMessage(l1 == 1 ? "derinf001" : "derinf002");
                     }

                     this.popStack(2);
                     return true;
                  } else {
                     this.reportError("dererr015", Message.params("remote line number", derivationnode3.getLineNumber() + ""));
                     return this.fail();
                  }
               }
            }
         } else if (this.ruleName.equals("DD")) {
            if (this.argumentCount != 1) {
               this.reportError("dererr010", Message.params("n", "1"));
               return this.fail();
            } else {
               Expression expression4 = this.line.box.getFormula();
               Expression expression15 = this.getStackFormula(-1);
               if (expression4 == null) {
                  this.reportError(this.line.box.getFormulaText(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.fail();
               } else if (!matchesPattern(expression15, expression4)) {
                  this.reportError("dererr019");
                  return this.fail();
               } else {
                  DerivationNode derivationnode2;
                  if ((derivationnode2 = this.getCitedNodeOutsideBox(-1)) != null) {
                     this.reportError("dererr015", Message.params("remote line number", derivationnode2.getLineNumber() + ""));
                     return this.fail();
                  } else {
                     if (this.line.box.cancelLine == this.line && this.line.box.module.serialMode && !this.preview) {
                        int k1 = this.line.box.assumptionType;
                        if (this.line.box.module.mixedModeDisabled && k1 != 0) {
                           this.reportError("dererr101");
                           return this.fail();
                        }

                        if (k1 != 0 && k1 != 1 && k1 != 2) {
                           this.reportError("dererr101");
                           return this.fail();
                        }

                        if ((errorref = this.line.box.module.checkDerivationRule("DD/" + ASS_STR[k1], this.interactive)) != null) {
                           this.reportError(errorref.id);
                           return this.fail();
                        }

                        this.line.box.showLine.showMessage(k1 == 0 ? "derinf001" : "derinf002");
                     }

                     this.popStack(1);
                     return true;
                  }
               }
            }
         } else if (this.ruleName.equals("UD")) {
            if (this.argumentCount != 1) {
               this.reportError("dererr010", Message.params("n", "1"));
               return this.fail();
            } else {
               Expression expression3 = this.line.box.getFormula();
               Expression expression14 = this.getStackFormula(-1);
               if (expression3 == null) {
                  this.reportError(this.line.box.getFormulaText(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.fail();
               } else if (!expression3.getSymbol().equals("@")) {
                  this.reportError("dererr021");
                  return this.fail();
               } else if (!matchesPattern(expression14, expression3.getChild(1))) {
                  this.reportError("dererr022");
                  return this.fail();
               } else {
                  DerivationNode derivationnode1;
                  if ((derivationnode1 = this.getCitedNodeOutsideBox(-1)) != null) {
                     this.reportError("dererr015", Message.params("remote line number", derivationnode1.getLineNumber() + ""));
                     return this.fail();
                  } else {
                     String s = ((SimpleTerm)expression3.getChild(0)).symbol;
                     if (this.isVariableUsedInOuterBoxes(s)) {
                        this.reportError("dererr023", Message.params("variable name", "\\l" + s + "\\l"));
                        return this.fail();
                     } else {
                        this.popStack(1);
                        return true;
                     }
                  }
               }
            }
         } else if (this.ruleName.equals("BD")) {
            if (this.argumentCount != 1) {
               this.reportError("dererr010", Message.params("n", "1"));
               return this.fail();
            } else {
               Expression expression2 = this.line.box.getFormula();
               if (expression2 == null) {
                  this.reportError(this.line.box.getFormulaText(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.fail();
               } else if (!expression2.getSymbol().equals("<->")) {
                  this.reportError("dererr081");
                  return this.fail();
               } else {
                  Expression expression13 = this.getStackFormula(-1);
                  int j1 = this.line.box.assumedSide;
                  if (j1 == -1) {
                     if (!matchesPattern(expression13, expression2.getChild(0)) && !matchesPattern(expression13, expression2.getChild(1))) {
                        this.reportError("dererr102");
                        return this.fail();
                     }
                  } else {
                     Expression expression22 = j1 == 2 ? this.line.box.getNode(1).getFormula() : expression2.getChild(j1);
                     if ((!matchesPattern(expression13, expression2.getChild(0)) || !matchesPattern(expression22, expression2.getChild(1)))
                        && (!matchesPattern(expression22, expression2.getChild(0)) || !matchesPattern(expression13, expression2.getChild(1)))) {
                        this.reportError("dererr090");
                        return this.fail();
                     }
                  }

                  if (this.line.box.module.serialMode && !this.line.box.strategyConsistent && !this.preview) {
                     this.reportError("dererr091");
                     return this.fail();
                  } else {
                     DerivationNode derivationnode;
                     if ((derivationnode = this.getCitedNodeOutsideBox(-1)) != null) {
                        this.reportError("dererr015", Message.params("remote line number", derivationnode.getLineNumber() + ""));
                        return this.fail();
                     } else {
                        if (this.line.box.cancelLine == this.line && this.line.box.module.serialMode && !this.preview) {
                           int i3 = this.line.box.assumptionType;
                           if (i3 != 3) {
                              this.reportError("dererr101");
                              return this.fail();
                           }

                           if ((errorref = this.line.box.module.checkDerivationRule("BD/" + ASS_STR[i3], this.interactive)) != null) {
                              this.reportError(errorref.id);
                              return this.fail();
                           }

                           this.line.box.showLine.showMessage(i3 == 3 ? "derinf001" : "derinf002", 4);
                        }

                        this.popStack(1);
                        return true;
                     }
                  }
               }
            }
         } else if (this.ruleName.equals("ASS CD")) {
            if (this.argumentCount != 0) {
               this.reportError("dererr024", Message.params("n", this.argumentCount + ""));
               return this.fail();
            } else if (this.line.getIndexInBox() != 1) {
               this.reportError("dererr025");
               return this.fail();
            } else if (this.result != null) {
               this.reportError("dererr026");
               return this.fail();
            } else {
               Expression expression1 = this.line.box.getFormula();
               if (expression1 == null) {
                  this.reportError(this.line.box.getFormulaText(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.fail();
               } else if (!expression1.getSymbol().equals("->")) {
                  this.reportError("dererr013");
                  return this.fail();
               } else {
                  this.line.box.assumptionType = 2;
                  this.result = expression1.getChild(0);
                  if (flag && !this.checkResultMatchesLine()) {
                     return this.fail();
                  } else {
                     this.popStack(0);
                     return true;
                  }
               }
            }
         } else if (this.ruleName.equals("ASS ID")) {
            if (this.argumentCount != 0) {
               this.reportError("dererr024", Message.params("n", this.argumentCount + ""));
               return this.fail();
            } else if (this.line.getIndexInBox() != 1) {
               this.reportError("dererr025");
               return this.fail();
            } else if (this.result != null) {
               this.reportError("dererr026");
               return this.fail();
            } else {
               Expression expression = this.line.box.getFormula();
               if (expression == null) {
                  this.reportError(this.line.box.getFormulaText(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.fail();
               } else {
                  if (this.target != null) {
                     if (!expression.isNegationOf(this.target) && !this.target.isNegationOf(expression)) {
                        this.reportError(this.assertion != null ? "dererr110" : "dererr027");
                        return this.fail();
                     }

                     this.result = this.target;
                  } else if (expression.getSymbol().equals("~")) {
                     Expression[] aexpression2 = new Expression[]{expression.getChild(0), expression.negate()};
                     int i1 = DerivationDialogs.chooseFormula(
                        this.line, aexpression2, DerivationMessage.format(DerivationMessage.getText("derdlg001"), null, this)
                     );
                     if (i1 == -1) {
                        if (this.line.box.module.serialMode) {
                           this.line.box.module.complete = false;
                           this.reportError("dererr064");
                        } else {
                           this.reportError("dererr028");
                           this.line.box.module.abort(true);
                        }

                        return this.fail();
                     }

                     this.result = aexpression2[i1];
                     this.cacheJustification(new IndirectAssumptionJustification(i1 == 1));
                  } else {
                     this.result = expression.negate();
                  }

                  this.line.box.assumptionType = 1;
                  if (flag && !this.checkResultMatchesLine()) {
                     return this.fail();
                  } else {
                     this.popStack(0);
                     return true;
                  }
               }
            }
         } else {
            j = LogicProgram.indexOf(BD_ASS, this.ruleName);
            if (j != -1) {
               if (this.argumentCount != 0) {
                  this.reportError("dererr024", Message.params("n", this.argumentCount + ""));
                  return this.fail();
               } else if (this.line.getIndexInBox() != 1) {
                  this.reportError("dererr025");
                  return this.fail();
               } else if (this.result != null) {
                  this.reportError("dererr026");
                  return this.fail();
               } else {
                  Expression expression12 = this.line.box.getFormula();
                  if (expression12 == null) {
                     this.reportError(this.line.box.getFormulaText(true).trim().equals("") ? "dererr011" : "dererr012");
                     return this.fail();
                  } else if (!expression12.getSymbol().equals("<->")) {
                     this.reportError("dererr081");
                     return this.fail();
                  } else {
                     if (this.target != null) {
                        if ((j == 1 || !expression12.getChild(0).isIdentical(this.target))
                           && (j == 0 || !expression12.getChild(1).isIdentical(this.target))) {
                           String[] astring = new String[]{"the left", "the right", "either"};
                           if (this.assertion != null) {
                              this.reportError("dererr110");
                           } else {
                              this.reportError("dererr092", Message.params("side", astring[j]));
                           }

                           return this.fail();
                        }

                        this.result = this.target;
                        // an asserted side is recorded as that side; the line's own formula as before
                        this.line.box.assumedSide = this.assertion == null ? j : (expression12.getChild(0).isIdentical(this.target) && j != 1 ? 0 : 1);
                     } else if (j < 2) {
                        if (containsWildcard(this.result = expression12.getChild(j))) {
                           this.reportError("dererr093");
                           return this.fail();
                        }

                        this.line.box.assumedSide = j;
                     } else {
                        Expression[] aexpression5 = new Expression[]{expression12.getChild(0), expression12.getChild(1)};
                        int l2;
                        if (aexpression5[0].isIdentical(aexpression5[1])) {
                           l2 = 0;
                        } else if (containsWildcard(aexpression5[1]) && !containsWildcard(aexpression5[0])) {
                           l2 = 0;
                        } else if (containsWildcard(aexpression5[0]) && !containsWildcard(aexpression5[1])) {
                           l2 = 1;
                        } else {
                           if (containsWildcard(aexpression5[0]) && containsWildcard(aexpression5[1])) {
                              this.reportError("dererr093");
                              return this.fail();
                           }

                           l2 = DerivationDialogs.chooseFormula(
                              this.line, aexpression5, DerivationMessage.format(DerivationMessage.getText("derdlg001"), null, this)
                           );
                        }

                        if (l2 == -1) {
                           if (this.line.box.module.serialMode) {
                              this.line.box.module.complete = false;
                              this.reportError("dererr064");
                           } else {
                              this.reportError("dererr028");
                              this.line.box.module.abort(true);
                           }

                           return this.fail();
                        }

                        this.result = aexpression5[l2];
                        this.cacheJustification(new BiconditionalAssumptionJustification(l2 == 1));
                        this.line.box.assumedSide = l2;
                     }

                     this.line.box.assumptionType = 3;
                     if (flag && !this.checkResultMatchesLine()) {
                        return this.fail();
                     } else {
                        this.popStack(0);
                        return true;
                     }
                  }
               }
            } else if (this.ruleName.startsWith("SHOW CONC") && "SHOW CONCLUSION".startsWith(this.ruleName)) {
               if (!this.checkShowVariant(true)) {
                  return this.fail();
               } else if (this.line.box.module.conclusion == null) {
                  this.reportError("dererr053");
                  return this.fail();
               } else {
                  this.result = this.line.box.module.conclusion.copy();
                  return this.finishShowVariant();
               }
            } else if (this.ruleName.startsWith("SHOW CONS") && "SHOW CONSEQUENT".startsWith(this.ruleName)) {
               if (!this.checkShowVariant(false)) {
                  return this.fail();
               } else {
                  Expression expression11 = this.line.box.getFormula();
                  if (expression11 != null && expression11.symbol.equals("->")) {
                     this.result = expression11.getChild(1).copy();
                     return this.finishShowVariant();
                  } else {
                     this.reportError("dererr077", Message.params("an", "a", "expected form", "conditional"));
                     return this.fail();
                  }
               }
            } else if (this.ruleName.startsWith("SHOW CORR") && "SHOW CORRCOND".startsWith(this.ruleName)) {
               if (!this.checkShowVariant(false)) {
                  return this.fail();
               } else {
                  Expression expression10 = this.line.box.getFormula();
                  if (expression10 != null && expression10.symbol.equals("|")) {
                     this.result = new ConnectiveFormula("->");
                     this.result.addChild(expression10.getChild(0).negate());
                     this.result.addChild(expression10.getChild(1).copy());
                     return this.finishShowVariant();
                  } else {
                     this.reportError("dererr077", Message.params("an", "a", "expected form", "disjunction"));
                     return this.fail();
                  }
               }
            } else if (this.ruleName.startsWith("SHOW CONJ") && "SHOW CONJUNCT".startsWith(this.ruleName)) {
               if (!this.checkShowVariant(false)) {
                  return this.fail();
               } else {
                  Expression expression9 = this.line.box.getFormula();
                  if (expression9 != null && expression9.symbol.equals("&")) {
                     Expression[] aexpression4 = new Expression[]{expression9.getChild(0).copy(), expression9.getChild(1).copy()};
                     int k2 = DerivationDialogs.chooseFormula(this.line, aexpression4, "Please choose a conjunct:");
                     if (k2 == -1) {
                        return this.fail();
                     } else {
                        this.result = aexpression4[k2];
                        return this.finishShowVariant();
                     }
                  } else {
                     this.reportError("dererr077", Message.params("an", "a", "expected form", "conjunction"));
                     return this.fail();
                  }
               }
            } else if (this.ruleName.startsWith("SHOW COND") && "SHOW CONDITIONAL".startsWith(this.ruleName)) {
               if (!this.checkShowVariant(false)) {
                  return this.fail();
               } else {
                  Expression expression8 = this.line.box.getFormula();
                  if (expression8 != null && expression8.symbol.equals("<->")) {
                     Expression[] aexpression3 = new Expression[]{new ConnectiveFormula("->"), null};
                     aexpression3[0].addChild(expression8.getChild(0).copy());
                     aexpression3[0].addChild(expression8.getChild(1).copy());
                     aexpression3[1] = new ConnectiveFormula("->");
                     aexpression3[1].addChild(expression8.getChild(1).copy());
                     aexpression3[1].addChild(expression8.getChild(0).copy());
                     int j2 = DerivationDialogs.chooseFormula(this.line, aexpression3, "Please choose a conditional:");
                     if (j2 == -1) {
                        return this.fail();
                     } else {
                        this.result = aexpression3[j2];
                        return this.finishShowVariant();
                     }
                  } else {
                     this.reportError("dererr077", Message.params("an", "a", "expected form", "biconditional"));
                     return this.fail();
                  }
               }
            } else if (this.ruleName.startsWith("SHOW INST") && "SHOW INSTANCE".startsWith(this.ruleName)) {
               if (!this.checkShowVariant(false)) {
                  return this.fail();
               } else {
                  Expression expression7 = this.line.box.getFormula();
                  if (expression7 != null && expression7.symbol.equals("@")) {
                     this.result = expression7.getChild(1).copy();
                     return this.finishShowVariant();
                  } else {
                     this.reportError("dererr077", Message.params("an", "a", "expected form", "universal generalization"));
                     return this.fail();
                  }
               }
            } else if (this.ruleName.startsWith("SHOW UNNEG") && "SHOW UNNEGATION".startsWith(this.ruleName)) {
               Object object3 = this.readSourceFormula();
               this.matchLine = !this.skipToNextStep();
               if (!this.checkShowVariant(false)) {
                  return this.fail();
               } else if (object3 == null) {
                  this.reportError("dererr079", Message.params("an", "a", "expected form", "negation"));
                  return this.fail();
               } else if (object3 instanceof ErrorRef) {
                  errorref = (ErrorRef)object3;
                  if (errorref.id != null) {
                     this.reportError(errorref.id, errorref.params);
                  }

                  return this.fail();
               } else {
                  Expression expression21 = (Expression)object3;
                  if (!expression21.symbol.equals("~")) {
                     String s4 = "\\l" + expression21 + "\\l";
                     this.reportError("dererr080", Message.params("remote line", s4, "an", "a", "expected form", "negation"));
                     return this.fail();
                  } else {
                     this.result = expression21.getChild(0).copy();
                     return this.finishShowVariant();
                  }
               }
            } else if (this.ruleName.startsWith("SHOW ANT") && "SHOW ANTECEDENT".startsWith(this.ruleName)) {
               Object object2 = this.readSourceFormula();
               this.matchLine = !this.skipToNextStep();
               if (!this.checkShowVariant(false)) {
                  return this.fail();
               } else if (object2 == null) {
                  this.reportError("dererr079", Message.params("an", "a", "expected form", "(bi)conditional"));
                  return this.fail();
               } else if (object2 instanceof ErrorRef) {
                  errorref = (ErrorRef)object2;
                  if (errorref.id != null) {
                     this.reportError(errorref.id, errorref.params);
                  }

                  return this.fail();
               } else {
                  Expression expression20 = (Expression)object2;
                  if (expression20.symbol.equals("->")) {
                     this.result = expression20.getChild(0).copy();
                  } else {
                     if (!expression20.symbol.equals("<->")) {
                        String s3 = "\\l" + expression20 + "\\l";
                        this.reportError("dererr080", Message.params("remote line", s3, "an", "a", "expected form", "(bi)conditional"));
                        return this.fail();
                     }

                     if ((this.result = DerivationDialogs.chooseSideToShow(this, expression20, false)) == null) {
                        return this.fail();
                     }
                  }

                  return this.finishShowVariant();
               }
            } else if (this.ruleName.startsWith("SHOW NEGCONS") && "SHOW NEGCONSEQUENT".startsWith(this.ruleName)) {
               Object object1 = this.readSourceFormula();
               this.matchLine = !this.skipToNextStep();
               if (!this.checkShowVariant(false)) {
                  return this.fail();
               } else if (object1 == null) {
                  this.reportError("dererr079", Message.params("an", "a", "expected form", "(bi)conditional"));
                  return this.fail();
               } else if (object1 instanceof ErrorRef) {
                  errorref = (ErrorRef)object1;
                  if (errorref.id != null) {
                     this.reportError(errorref.id, errorref.params);
                  }

                  return this.fail();
               } else {
                  Expression expression19 = (Expression)object1;
                  if (expression19.symbol.equals("->")) {
                     this.result = expression19.getChild(1).copy().negate();
                  } else {
                     if (!expression19.symbol.equals("<->")) {
                        String s2 = "\\l" + expression19 + "\\l";
                        this.reportError("dererr080", Message.params("remote line", s2, "an", "a", "expected form", "(bi)conditional"));
                        return this.fail();
                     }

                     if ((this.result = DerivationDialogs.chooseSideToShow(this, expression19, true)) == null) {
                        return this.fail();
                     }
                  }

                  return this.finishShowVariant();
               }
            } else if (this.ruleName.startsWith("SHOW NEGDISJ") && "SHOW NEGDISJUNCT".startsWith(this.ruleName)) {
               Object object = this.readSourceFormula();
               this.matchLine = !this.skipToNextStep();
               if (!this.checkShowVariant(false)) {
                  return this.fail();
               } else if (object == null) {
                  this.reportError("dererr079", Message.params("an", "a", "expected form", "disjunction"));
                  return this.fail();
               } else if (object instanceof ErrorRef) {
                  errorref = (ErrorRef)object;
                  if (errorref.id != null) {
                     this.reportError(errorref.id, errorref.params);
                  }

                  return this.fail();
               } else {
                  Expression expression18 = (Expression)object;
                  if (!expression18.symbol.equals("|")) {
                     String s1 = "\\l" + expression18 + "\\l";
                     this.reportError("dererr080", Message.params("remote line", s1, "an", "a", "expected form", "disjunction"));
                     return this.fail();
                  } else {
                     Expression[] aexpression = new Expression[]{expression18.getChild(0).copy().negate(), expression18.getChild(1).copy().negate()};
                     int k = DerivationDialogs.chooseFormula(this.line, aexpression, "Please choose the negation of a disjunct:");
                     if (k == -1) {
                        return this.fail();
                     } else {
                        this.result = aexpression[k];
                        return this.finishShowVariant();
                     }
                  }
               }
            } else {
               int i;
               if ((i = parsePremiseNumber(this.ruleName)) != -1) {
                  Expression[] aexpression1 = this.line.box.module.premises;
                  int l = aexpression1 == null ? 0 : aexpression1.length;
                  if (l == 0 || !this.line.box.module.problem.showLine.syntaxOk) {
                     this.reportError("dererr029");
                     return this.fail();
                  } else if (i > l) {
                     this.reportError("dererr030", Message.params("premise index", i + ""));
                     return this.fail();
                  } else if ((flag || flag1) && this.argumentCount != 0) {
                     this.reportError("dererr024", Message.params("n", this.argumentCount + ""));
                     return this.fail();
                  } else if (this.target != null) {
                     if (i == 0) {
                        if (!this.line.box.module.isPremise(this.target)) {
                           this.reportError(this.assertion != null ? "dererr110" : "dererr031");
                           return this.fail();
                        }
                     } else if (!this.target.isIdentical(aexpression1[i - 1])) {
                        if (this.assertion != null) {
                           this.reportError("dererr110");
                        } else {
                           this.reportError("dererr032", Message.params("premise index", i + "", "indexed premise", "\\l" + aexpression1[i - 1] + "\\l"));
                        }

                        return this.fail();
                     }

                     this.result = this.target;
                     this.popStack(0);
                     return true;
                  } else {
                     if (i == 0) {
                        if (l > 1) {
                           i = DerivationDialogs.chooseFormula(
                                 this.line, aexpression1, DerivationMessage.format(DerivationMessage.getText("derdlg002"), null, this)
                              )
                              + 1;
                           if (i == 0) {
                              if (this.line.box.module.serialMode) {
                                 this.line.box.module.complete = false;
                                 this.reportError("dererr064");
                              } else {
                                 this.reportError("dererr028");
                                 this.line.box.module.abort(true);
                              }

                              return this.fail();
                           }

                           this.cacheJustification(new PremiseJustification(i - 1));
                        } else {
                           i = 1;
                        }
                     }

                     this.result = aexpression1[i - 1];
                     if (flag && !this.checkResultMatchesLine()) {
                        return this.fail();
                     } else {
                        this.popStack(0);
                        return true;
                     }
                  }
               } else if (this.ruleName.equals("IE")) {
                  if (!flag && !flag1 ? this.argumentCount >= 1 : this.argumentCount == 1) {
                     InterchangeJustification interchangejustification1 = new InterchangeJustification();
                     if (!DerivationDialogs.interchangeFormulaQuery(this, interchangejustification1)) {
                        return this.fail();
                     } else if (!DerivationDialogs.interchangeRuleQuery(this, interchangejustification1)) {
                        return this.fail();
                     } else if (!interchangejustification1.reapply(this)) {
                        if (LogicProgram.debug) {
                           System.out.println("unexpected IE error during applyRule");
                        }

                        Hashtable hashtable1 = new Hashtable();
                        Message.putParam(hashtable1, "inner rule", interchangejustification1.getEquivalenceRule(this).name);
                        Message.putParam(hashtable1, "inner exp", "\\l" + this.getStackFormula(-1).getSubexpression(interchangejustification1.path) + "\\l");
                        this.reportError("dererr085", hashtable1);
                        return this.fail();
                     } else {
                        this.cacheJustification(interchangejustification1);
                        return true;
                     }
                  } else {
                     this.reportError("dererr084", Message.params("n", "1"));
                     return this.fail();
                  }
               } else if (!this.ruleName.equals("CIE")) {
                  SchemeInstantiation schemeinstantiation = this.matchNamedRule();
                  if (schemeinstantiation == null || !this.checkInstantiationRestrictions(schemeinstantiation)) {
                     return this.fail();
                  } else {
                     return flag && !this.checkResultMatchesLine() ? this.fail() : true;
                  }
               } else if (!flag && !flag1 ? this.argumentCount >= 1 : this.argumentCount == 1) {
                  InterchangeJustification interchangejustification = new InterchangeJustification();
                  if (!DerivationDialogs.interchangeFormulaQuery(this, interchangejustification)) {
                     return this.fail();
                  } else if (!DerivationDialogs.cieRuleQuery(this, interchangejustification)) {
                     return this.fail();
                  } else if (!interchangejustification.reapply(this)) {
                     if (LogicProgram.debug) {
                        System.out.println("unexpected CIE error during applyRule");
                     }

                     Hashtable hashtable = new Hashtable();
                     Message.putParam(hashtable, "inner rule", interchangejustification.getEquivalenceRule(this).name);
                     Message.putParam(hashtable, "inner exp", "\\l" + this.getStackFormula(-1).getSubexpression(interchangejustification.path) + "\\l");
                     this.reportError("dererr095", hashtable);
                     return this.fail();
                  } else {
                     this.cacheJustification(interchangejustification);
                     return true;
                  }
               } else {
                  this.reportError("dererr084", Message.params("n", "1"));
                  return this.fail();
               }
            }
         }
      }
   }

   boolean checkResultMatchesLine() {
      return this.checkResultMatchesLine(false);
   }

   boolean checkResultMatchesLine(boolean flag) {
      String s = this.line.getFormulaText(false);
      if (s == null) {
         return true;
      } else {
         if (s.equals("") && this.line.box.module.commandMode && this.interactive) {
            this.line.clearMessage(1);
            this.line.setFormulaText(this.result.toString());
            this.line.parseFormula();
         } else if (this.lineFormula == null || !this.lineFormula.isIdentical(this.result)) {
            if (!flag) {
               boolean flag1 = this.line.box.module.commandMode;
               this.reportError(this.reusedCache ? "dererr064" : (this.hasFullMatch ? (flag1 ? "dererr033" : "dererr103") : "dererr100"));
               this.putMessageObject("sum", this.result);
            }

            return false;
         }

         return true;
      }
   }

   boolean checkShowVariant(boolean flag) {
      if (this.result != null) {
         this.reportError("dererr020");
         return false;
      } else if (this.argumentCount != 0) {
         this.reportError("dererr024", Message.params("n", this.argumentCount + ""));
         return false;
      } else if (!this.matchLine && !this.finalStep) {
         this.reportError("dererr020");
         return false;
      } else if (!this.interactive) {
         this.reportError("dererr074");
         return false;
      } else if (this.line.box.parentBox == null != flag) {
         this.reportError(flag ? "dererr075" : "dererr076");
         return false;
      } else {
         return true;
      }
   }

   boolean finishShowVariant() {
      String s = this.line.getAnnotationText(true);
      this.line.makeShowLine();
      if (this.line.commandLog != null) {
         this.line.commandLog.setText("\"" + s + "\"");
      }

      if (this.interactive && !this.checkResultMatchesLine(true)) {
         return this.fail();
      } else {
         this.popStack(0);
         return true;
      }
   }

   boolean checkInstantiationRestrictions(SchemeInstantiation schemeinstantiation) {
      return this.checkInstantiationRestrictions(schemeinstantiation, false);
   }

   boolean checkInstantiationRestrictions(SchemeInstantiation schemeinstantiation, boolean flag) {
      if (this.ruleName.equals("EI")) {
         SchematicRule schematicrule = (SchematicRule)LogicProgram.getRule("EI");
         Expression expression = schematicrule.getConclusion().getChild(0).instantiate(schemeinstantiation);
         Vector vector = this.line.box.module.varNames;
         if (!(expression instanceof SimpleTerm)) {
            if (!flag) {
               this.reportError("dererr100");
            }

            return false;
         }

         if (vector != null && vector.contains(expression.symbol)) {
            if (!flag) {
               this.reportError("dererr034", Message.params("variable name", "\\l" + expression + "\\l"));
            }

            this.cachedError = new ErrorRef("dererr034", Message.params("variable name", "\\l" + expression + "\\l"));
            return false;
         }
      }

      return true;
   }

   boolean isVariableUsedInOuterBoxes(String s) {
      DerivationBox derivationbox = this.line.box;
      if (derivationbox.showLine == this.line) {
         derivationbox = derivationbox.parentBox;
      }

      if (derivationbox != null) {
         derivationbox = derivationbox.parentBox;
      }

      while (derivationbox != null) {
         if (derivationbox.boxVariables != null && derivationbox.boxVariables.contains(s)) {
            return true;
         }

         derivationbox = derivationbox.parentBox;
      }

      return false;
   }

   DerivationNode getCitedNodeOutsideBox(int i) {
      DerivationNode derivationnode = this.getCitedNode(i);
      if (derivationnode == null) {
         return null;
      } else {
         return derivationnode.getEnclosingBox() == this.line.box ? null : derivationnode;
      }
   }

   SchemeInstantiation matchNamedRule() {
      Integer integer = Theorem.parseTheoremNumber(this.ruleName);
      if (integer == null) {
         Rule rule = LPDerivation.getRule(this.ruleName);
         if (rule == null) {
            this.reportError("dererr035");
            return null;
         } else {
            return this.matchRule(rule);
         }
      } else {
         Theorem theorem = LogicProgram.getTheorem(integer);
         if (theorem == null) {
            this.reportError("dererr036", Message.params("theorem number", integer + ""));
            return null;
         } else if ((this.matchLine || this.finalStep) && this.argumentCount != 0) {
            this.reportError("dererr024", Message.params("n", this.argumentCount + ""));
            return null;
         } else {
            return this.matchRule(theorem);
         }
      }
   }

   SchemeInstantiation matchRule(Rule rule) {
      if (rule == null) {
         return null;
      } else {
         boolean flag = false;
         boolean flag1 = false;
         boolean flag2 = false;
         boolean flag3 = false;
         Vector vector = new Vector();
         this.premiseMatches = new Vector();
         this.hasPremiseMatch = false;
         this.fullMatches = new Vector();
         this.hasFullMatch = false;
         LPDerivation lpderivation = this.line.box.module;
         this.allForms = rule.getAllForms();
         this.enabledForms = rule.getForms(lpderivation, "disabled");
         this.automaticForms = this.interactive ? rule.getForms(lpderivation, "manualOrDisabled") : this.enabledForms;
         this.computePremiseRange();

         for (int i = 0; i < this.allForms.length; i++) {
            SchematicRule schematicrule = this.allForms[i];
            int j = schematicrule.premises.length;
            if (!this.matchLine && !this.finalStep ? j <= this.argumentCount : j == this.argumentCount) {
               flag = true;
               SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
               SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation();
               boolean flag4 = false;
               boolean flag5 = false;
               boolean[] aboolean = new boolean[j];
               schematicrule.conclusion.match(null, schemeinstantiation1);
               if (this.target != null) {
                  flag4 = schematicrule.conclusion.match(this.target, schemeinstantiation);
               } else {
                  flag4 = schemeinstantiation.mergeFrom(schemeinstantiation1);
               }

               PermutationIterator permutationiterator = new PermutationIterator(j);

               while (true) {
                  int[] aint = permutationiterator.current();
                  SchemeInstantiation[] aschemeinstantiation = new SchemeInstantiation[j];
                  int k = 0;

                  for (int l = 0; l < j; l++) {
                     aschemeinstantiation[l] = new SchemeInstantiation();
                     if (schematicrule.premises[aint[l]].match(this.getStackFormula(l - j), aschemeinstantiation[l])) {
                        k++;
                        aboolean[aint[l]] = true;
                     }
                  }

                  label330:
                  if (k == j) {
                     boolean flag8 = false;
                     SchemeInstantiation schemeinstantiation2 = new SchemeInstantiation();

                     for (int i1 = 0; i1 < j; i1++) {
                        if (!schemeinstantiation2.mergeFrom(aschemeinstantiation[i1])) {
                           break label330;
                        }
                     }

                     BoundVariableMap boundvariablemap2 = new BoundVariableMap();
                     if (schemeinstantiation2.hasNoDeferredMatches()) {
                        for (int j1 = 0; j1 < j; j1++) {
                           if (!boundvariablemap2.matches(schematicrule.premises[aint[j1]], this.getStackFormula(j1 - j), schemeinstantiation2)) {
                              break label330;
                           }
                        }
                     } else {
                        flag8 = true;
                     }

                     RuleApplication ruleapplication2 = new RuleApplication(schematicrule, aint, schemeinstantiation2, boundvariablemap2);
                     this.premiseMatches.addElement(ruleapplication2);
                     this.hasPremiseMatch = true;
                     if (schemeinstantiation2.mergeFrom(schemeinstantiation1)
                        && schemeinstantiation2.hasNoDeferredMatches()
                        && boundvariablemap2.coversBinders(schematicrule.conclusion)) {
                        this.fullMatches.addElement(ruleapplication2);
                        this.hasFullMatch = true;
                     }

                     if (!flag4) {
                        if (!flag8) {
                           ruleapplication2.failureKind = 1;
                        }
                     } else if (!schemeinstantiation2.mergeFrom(schemeinstantiation)) {
                        if (!flag8) {
                           ruleapplication2.failureKind = 2;
                        }
                     } else {
                        label394: {
                           if (schemeinstantiation2.hasNoDeferredMatches()) {
                              if (flag8) {
                                 for (int k1 = 0; k1 < j; k1++) {
                                    if (!boundvariablemap2.matches(schematicrule.premises[aint[k1]], this.getStackFormula(k1 - j), schemeinstantiation2)) {
                                       break label394;
                                    }
                                 }
                              }

                              BinderMap bindermap2 = new BinderMap();
                              Expression expression = schematicrule.conclusion.instantiate(schemeinstantiation2, bindermap2);
                              int[][] aint1 = bindermap2.getBinderCorrespondence(schematicrule.conclusion, expression);
                              if (!boundvariablemap2.matchBinders(schematicrule.conclusion, this.target, aint1)) {
                                 ruleapplication2.failureKind = 3;
                                 break label394;
                              }

                              if (!boundvariablemap2.renameBinders(schematicrule.conclusion, expression, aint1, null)) {
                                 ruleapplication2.failureKind = 4;
                                 ruleapplication2.failureDetail = expression.findMislinkedVariables();
                                 break label394;
                              }

                              if (this.target != null && !expression.isIdentical(this.target)) {
                                 ruleapplication2.failureKind = 5;
                                 break label394;
                              }
                           }

                           if (schematicrule.indexByName(this.enabledForms) == -1) {
                              flag1 = true;
                           } else if (this.interactive && schematicrule.indexByName(this.automaticForms) == -1) {
                              flag2 = true;
                           } else if (!schematicrule.isProven(this.line.box.module)) {
                              flag3 = true;
                           } else {
                              vector.addElement(ruleapplication2);
                           }
                        }
                     }
                  }

                  if (!permutationiterator.next()) {
                     break;
                  }
               }
            }
         }

         if (!this.pruneDegenerateMatches(vector)) {
            return null;
         } else if (!flag) {
            if (!this.matchLine && !this.finalStep && this.argumentCount != 0) {
               this.reportError("dererr038", Message.params("n", this.argumentCount + ""));
            } else {
               this.reportError("dererr039", Message.params("n", this.argumentCount + ""));
            }

            return null;
         } else if (vector.isEmpty()) {
            if (flag3) {
               this.line.box.module.proofMissing = true;
            }

            if (flag1) {
               this.reportError("dererr041");
            } else if (flag2) {
               this.reportError("dererr040");
            } else if (flag3) {
               this.reportError("dererr016");
            } else if (this.reusedCache) {
               this.reportError("dererr064");
            } else if (this.assertion != null && this.hasFullMatch) {
               this.reportError("dererr110");
            } else if (this.hasFullMatch && this.fullMatches.size() == 1) {
               if (this.matchLine && this.lineFormula != null) {
                  Expression expression1 = ((RuleApplication)this.fullMatches.elementAt(0)).getConclusion();
                  if (this.line.box.module.commandMode) {
                     this.reportError("dererr033", Message.params("rule form conclusion", "\\l" + expression1 + "\\l"));
                     this.putMessageObject("sum", expression1);
                  } else {
                     this.reportError("dererr103");
                  }
               } else {
                  RuleApplication ruleapplication = (RuleApplication)this.fullMatches.elementAt(0);
                  if (ruleapplication.failureKind == 4) {
                     Expression[] aexpression = (Expression[])((Vector)ruleapplication.failureDetail).elementAt(0);
                     this.reportError(
                        "dererr074",
                        Message.params(
                           "rule form conclusion",
                           "\\l" + ruleapplication.getConclusion() + "\\l",
                           "misbinder",
                           "\\l" + aexpression[0].symbol + "\\l",
                           "misbound",
                           "\\l" + aexpression[1] + "\\l"
                        )
                     );
                  } else {
                     this.reportError("dererr100");
                  }
               }
            } else if (this.hasPremiseMatch) {
               this.reportError("dererr100");
            } else if (this.minPremises == this.maxPremises) {
               this.reportError("dererr042");
            } else {
               this.reportError("dererr063");
            }

            return null;
         } else {
            int l1 = this.assertion != null ? this.chooseAssertedInstance(vector) : -1;
            if (l1 == -1) {
               l1 = DerivationDialogs.chooseRuleInstance(this, vector);
            }

            if (l1 == -1) {
               if (this.line.box.module.serialMode) {
                  this.line.box.module.complete = false;
                  this.reportError("dererr064");
               } else {
                  this.reportError("dererr028");
                  this.line.box.module.abort(true);
               }

               return null;
            } else {
               RuleApplication ruleapplication1 = (RuleApplication)vector.elementAt(l1);
               this.premiseMatches = new Vector();
               this.premiseMatches.addElement(ruleapplication1);
               this.fullMatches = new Vector();
               this.fullMatches.addElement(ruleapplication1);
               SchematicRule schematicrule1 = ruleapplication1.getForm();
               int i2 = schematicrule1.premises.length;
               SchemeInstantiation schemeinstantiation3 = ruleapplication1.getInstantiation();
               if (schemeinstantiation3.hasNoDeferredMatches()) {
                  BinderMap bindermap = new BinderMap();
                  this.result = schematicrule1.conclusion.instantiate(schemeinstantiation3, bindermap);
                  BoundVariableMap boundvariablemap = ruleapplication1.getBoundVariables();
                  boolean flag6 = vector.size() > 1 || !boundvariablemap.coversBinders(schematicrule1.conclusion);
                  if (!boundvariablemap.renameBinders(schematicrule1.conclusion, this.result, bindermap, this)) {
                     return null;
                  } else {
                     if (flag6) {
                        this.cacheJustification(ruleapplication1);
                     }

                     this.popStack(i2);
                     return schemeinstantiation3;
                  }
               } else if (this.line.box.module.frame == null) {
                  return null;
               } else {
                  if (this.ruleName.equals("EG")) {
                     if (!DerivationDialogs.generalizationTermQuery(this, ruleapplication1)) {
                        return null;
                     }
                  } else if (this.ruleName.equals("EI")) {
                     if (!DerivationDialogs.existentialVarQuery(this, ruleapplication1)) {
                        return null;
                     }
                  } else if (this.ruleName.equals("UI")) {
                     if (!DerivationDialogs.universalTermQuery(this, ruleapplication1)) {
                        return null;
                     }
                  } else if (!ruleapplication1.form.name.equalsIgnoreCase("LL1") && !ruleapplication1.form.name.equalsIgnoreCase("LL2")) {
                     if (!ruleapplication1.form.name.equalsIgnoreCase("LL3") && !ruleapplication1.form.name.equalsIgnoreCase("LL4")) {
                        if (this.finalStep && this.lineFormula != null && this.ruleName.equals("EL")) {
                           if (!DerivationDialogs.eulerTermQuery(this, ruleapplication1)) {
                              return null;
                           }
                        } else if (!DerivationDialogs.instanceSchemeQuery(this, ruleapplication1)) {
                           return null;
                        }
                     } else if (!DerivationDialogs.leibniz34TermQuery(this, ruleapplication1)) {
                        return null;
                     }
                  } else if (!DerivationDialogs.leibniz12TermQuery(this, ruleapplication1)) {
                     return null;
                  }

                  BinderMap bindermap1 = new BinderMap();
                  Expression expression2 = schematicrule1.conclusion.instantiate(schemeinstantiation3, bindermap1);
                  int[][] aint2 = bindermap1.getBinderCorrespondence(schematicrule1.conclusion, expression2);
                  int[] aint3 = ruleapplication1.getPremiseOrder();
                  BoundVariableMap boundvariablemap1 = ruleapplication1.getBoundVariables();
                  boolean flag7 = true;

                  for (int j2 = 0; j2 < i2; j2++) {
                     if (!boundvariablemap1.matches(schematicrule1.premises[aint3[j2]], this.getStackFormula(j2 - i2), schemeinstantiation3)) {
                        flag7 = false;
                        break;
                     }
                  }

                  if (flag7) {
                     flag7 = false;
                     if ((this.target == null || boundvariablemap1.matchBinders(schematicrule1.conclusion, this.target, aint2))
                        && boundvariablemap1.renameBinders(schematicrule1.conclusion, expression2, aint2, null)
                        && (this.target == null || expression2.isIdentical(this.target))) {
                        flag7 = true;
                     }
                  }

                  if (!flag7) {
                     SimpleTerm simpleterm = null;
                     if (boundvariablemap1.clashes != null) {
                        simpleterm = (SimpleTerm)((Expression[])boundvariablemap1.clashes.elementAt(0))[1];
                     }

                     this.reportError("dererr043", Message.params("inst term", "\\l" + simpleterm + "\\l"));
                     return null;
                  } else if (this.ruleName.equals("EG") && !DerivationDialogs.dummyVarQuery(this, ruleapplication1, boundvariablemap1)) {
                     return null;
                  } else if (!boundvariablemap1.renameBinders(schematicrule1.conclusion, expression2, aint2, this)) {
                     return null;
                  } else {
                     this.result = expression2;
                     this.cacheJustification(new RuleApplication(schematicrule1, aint3, schemeinstantiation3, boundvariablemap1));
                     this.popStack(i2);
                     return schemeinstantiation3;
                  }
               }
            }
         }
      }
   }

   /**
    * With an asserted result, the applications left all give that result (they differ only
    * in which cited formula fills which premise): take the first one that is fully
    * determined. Returns -1 if none is, so that the program asks as usual.
    */
   int chooseAssertedInstance(Vector vector) {
      for (int i = 0; i < vector.size(); i++) {
         if (((RuleApplication)vector.elementAt(i)).getInstantiation().hasNoDeferredMatches()) {
            return i;
         }
      }

      return -1;
   }

   /**
    * DUP pushes another copy of the top formula, DROP removes it, SWAP exchanges the top
    * two. They rearrange the formulas already on the stack (each keeps the line it was
    * cited from) and produce no result of their own.
    */
   boolean applyStackOperation() {
      int i = this.ruleName.equals("SWAP") ? 2 : 1;
      if (this.argumentCount < i) {
         this.reportError("dererr113", Message.params("n", i + "", "s", i == 1 ? "" : "s"));
         return this.fail();
      } else {
         int j = this.stack.size();
         if (this.ruleName.equals("DUP")) {
            this.stack.addElement(this.stack.elementAt(j - 1));
            this.citedNodes.addElement(this.citedNodes.elementAt(j - 1));
         } else if (this.ruleName.equals("DROP")) {
            this.stack.setSize(j - 1);
            this.citedNodes.setSize(j - 1);
         } else {
            Object object = this.stack.elementAt(j - 1);
            this.stack.setElementAt(this.stack.elementAt(j - 2), j - 1);
            this.stack.setElementAt(object, j - 2);
            object = this.citedNodes.elementAt(j - 1);
            this.citedNodes.setElementAt(this.citedNodes.elementAt(j - 2), j - 1);
            this.citedNodes.setElementAt(object, j - 2);
         }

         this.result = null;
         return true;
      }
   }

   boolean verifyApplication(RuleApplication ruleapplication, SchemeInstantiation schemeinstantiation) {
      SchematicRule schematicrule = ruleapplication.getForm();
      int i = schematicrule.premises.length;
      Expression[] aexpression = new Expression[i];
      int[][][] aint = new int[i][][];

      for (int j = 0; j < i; j++) {
         BinderMap bindermap = new BinderMap();
         aexpression[j] = schematicrule.premises[j].instantiate(schemeinstantiation, bindermap);
         aint[j] = bindermap.getBinderCorrespondence(schematicrule.premises[j], aexpression[j]);
      }

      BinderMap bindermap1 = new BinderMap();
      Expression expression = schematicrule.conclusion.instantiate(schemeinstantiation, bindermap1);
      int[][] aint1 = bindermap1.getBinderCorrespondence(schematicrule.conclusion, expression);
      int[] aint2 = ruleapplication.getPremiseOrder();
      BoundVariableMap boundvariablemap = ruleapplication.getBoundVariables();
      boolean flag = true;

      for (int k = 0; k < i; k++) {
         flag = false;
         if (!boundvariablemap.matchBinders(schematicrule.premises[aint2[k]], this.getStackFormula(k - i), aint[aint2[k]])
            || !boundvariablemap.renameBinders(schematicrule.premises[aint2[k]], aexpression[aint2[k]], aint[aint2[k]], null)
            || !aexpression[aint2[k]].isIdentical(this.getStackFormula(k - i))) {
            break;
         }

         flag = true;
      }

      if (flag) {
         flag = false;
         if ((this.target == null || boundvariablemap.matchBinders(schematicrule.conclusion, this.target, aint1))
            && boundvariablemap.renameBinders(schematicrule.conclusion, expression, aint1, null)
            && (this.target == null || expression.isIdentical(this.target))) {
            flag = true;
         }
      }

      this.clashes = boundvariablemap.clashes;
      return flag;
   }

   boolean pruneDegenerateMatches(Vector vector) {
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         RuleApplication ruleapplication = (RuleApplication)vector.elementAt(j);
         if (!ruleapplication.form.name.equalsIgnoreCase("LL1") && !ruleapplication.form.name.equalsIgnoreCase("LL2")) {
            if (!ruleapplication.form.name.equalsIgnoreCase("LL3") && !ruleapplication.form.name.equalsIgnoreCase("LL4")) {
               if (ruleapplication.form.name.equalsIgnoreCase("AV3")) {
                  Vector vector5 = ruleapplication.instantiation.pendingLetters;
                  if (!vector5.isEmpty()) {
                     SchematicLetter schematicletter2 = (SchematicLetter)ruleapplication.instantiation.pendingLetters.elementAt(0);
                     Vector vector7 = schematicletter2.getDeferredMatches(false);
                     if (vector7 != null) {
                        int i1 = vector7.size();

                        for (int j1 = 0; j1 < i1; j1++) {
                           DeferredMatch deferredmatch3 = (DeferredMatch)vector7.elementAt(j1);
                           if (deferredmatch3.instance != null && deferredmatch3.instance.findSymbolOccurrences("%").size() == 0) {
                              this.removeMatch(vector, ruleapplication);
                              i--;
                              j--;
                              break;
                           }
                        }
                     }
                  }
               }
            } else {
               Vector vector4 = ruleapplication.instantiation.pendingLetters;
               if (!vector4.isEmpty()) {
                  SchematicLetter schematicletter1 = (SchematicLetter)ruleapplication.instantiation.pendingLetters.elementAt(0);
                  Vector vector6 = schematicletter1.getDeferredMatches(false);
                  if (vector6 != null) {
                     if (vector6.size() == 1) {
                        this.removeMatch(vector, ruleapplication);
                        i--;
                        j--;
                     } else {
                        DeferredMatch deferredmatch1 = (DeferredMatch)vector6.elementAt(0);
                        DeferredMatch deferredmatch2 = (DeferredMatch)vector6.elementAt(1);
                        Vector vector8 = deferredmatch1.instance.findDifferences(deferredmatch2.instance);
                        int k1 = vector8.size();
                        ExpressionPath expressionpath = k1 == 0 ? null : (ExpressionPath)vector8.elementAt(0);
                        int l1 = expressionpath == null ? 0 : expressionpath.depth;
                        if (l1 == 0) {
                           this.removeMatch(vector, ruleapplication);
                           i--;
                           j--;
                        } else {
                           Expression expression6 = deferredmatch1.instance.getSubexpression(expressionpath);
                           Expression expression3 = deferredmatch2.instance.getSubexpression(expressionpath);
                           boolean flag = true;

                           for (int l = 1; l < k1; l++) {
                              expressionpath = (ExpressionPath)vector8.elementAt(l);
                              if (!expression6.isIdentical(deferredmatch1.instance.getSubexpression(expressionpath))) {
                                 flag = false;
                              } else if (!expression3.isIdentical(deferredmatch2.instance.getSubexpression(expressionpath))) {
                                 flag = false;
                              }

                              if (!flag) {
                                 break;
                              }

                              if (expressionpath.depth < l1) {
                                 l1 = expressionpath.depth;
                              }
                           }

                           if (!flag) {
                              this.removeMatch(vector, ruleapplication);
                              i--;
                              j--;
                           } else if (l1 == 1) {
                              SimpleTerm simpleterm1 = new SimpleTerm(SchematicLetter.placeholder(0));
                              Expression expression4 = deferredmatch1.instance.copy();
                              Expression expression5 = deferredmatch1.pattern.copy();
                              expression5.children.setElementAt(simpleterm1, 0);

                              for (int i2 = 0; i2 < k1; i2++) {
                                 expression4 = InterchangeJustification.replaceAt(expression4, simpleterm1, (ExpressionPath)vector8.elementAt(i2));
                              }

                              if (expression4.findMislinkedVariables() != null || !ruleapplication.instantiation.addReplacement(expression5, expression4)) {
                                 this.removeMatch(vector, ruleapplication);
                                 i--;
                                 j--;
                              }
                           }
                        }
                     }
                  }
               }
            }
         } else {
            Vector vector1 = ruleapplication.instantiation.pendingLetters;
            if (!vector1.isEmpty()) {
               SchematicLetter schematicletter = (SchematicLetter)ruleapplication.instantiation.pendingLetters.elementAt(0);
               Vector vector2 = schematicletter.getDeferredMatches(false);
               if (vector2 != null) {
                  DeferredMatch deferredmatch = (DeferredMatch)vector2.elementAt(0);
                  Expression expression = deferredmatch.pattern.getChild(0);
                  Vector vector3 = deferredmatch.instance.findOccurrences(expression.instantiate(ruleapplication.instantiation));
                  int k = vector3.size();
                  if (k == 0) {
                     this.removeMatch(vector, ruleapplication);
                     i--;
                     j--;
                  } else if (k == 1) {
                     SimpleTerm simpleterm = new SimpleTerm(SchematicLetter.placeholder(0));
                     Expression expression1 = InterchangeJustification.replaceAt(deferredmatch.instance, simpleterm, (ExpressionPath)vector3.elementAt(0));
                     Expression expression2 = deferredmatch.pattern.copy();
                     expression2.children.setElementAt(simpleterm, 0);
                     if (expression1.findMislinkedVariables() != null || !ruleapplication.instantiation.addReplacement(expression2, expression1)) {
                        this.removeMatch(vector, ruleapplication);
                        i--;
                        j--;
                     }
                  }
               }
            }
         }
      }

      return true;
   }

   private void removeMatch(Vector vector, RuleApplication ruleapplication) {
      vector.removeElement(ruleapplication);
      this.fullMatches.removeElement(ruleapplication);
      if (this.fullMatches.isEmpty()) {
         this.hasFullMatch = false;
      }

      this.premiseMatches.removeElement(ruleapplication);
      if (this.premiseMatches.isEmpty()) {
         this.hasPremiseMatch = false;
      }
   }

   @Override
   public String getParamValue(String s) {
      if (s.equals("rule name")) {
         return this.ruleName;
      } else if (s.equals("asserted")) {
         return this.assertion == null ? "" : "\\l" + this.assertion + "\\l";
      } else if (s.equals("stack")) {
         String object = "\\l";

         for (int j1 = 0; j1 < this.argumentCount; j1++) {
            if (j1 != 0) {
               object = object + "\\n";
            }

            object = object + this.getStackFormula(-j1);
         }

         return object + "\\l";
      } else {
         if (s.length() >= 5 && s.substring(0, 5).equals("stack")) {
            int i;
            try {
               i = Integer.parseInt(s.substring(5).trim());
            } catch (NumberFormatException numberformatexception) {
               i = 0;
            }

            if (i > 0 && i <= this.argumentCount) {
               return "\\l" + this.getStackFormula(-i) + "\\l";
            }
         }

         if (s.equals("assumption")) {
            int l = this.line.box.assumedSide;
            if (l == -1) {
               return null;
            } else {
               return l == 2 ? "\\l" + this.line.box.getNode(1).getFormulaText(true) + "\\l" : "\\l" + this.line.box.getFormula().getChild(l) + "\\l";
            }
         } else if (s.equals("rule forms") && this.automaticForms != null) {
            String s4 = "\\l";
            int i1 = this.automaticForms.length;
            boolean flag3 = true;

            for (int i2 = 0; i2 < i1; i2++) {
               SchematicRule schematicrule1 = this.automaticForms[i2];
               if (schematicrule1.premises.length >= this.minPremises && schematicrule1.premises.length <= this.maxPremises) {
                  if (!flag3) {
                     s4 = s4 + "\\n";
                  }

                  s4 = s4 + this.automaticForms[i2].format(" . ", " .: ");
                  flag3 = false;
               }
            }

            return s4 + "\\l";
         } else if (s.equals("rule forms premises") && this.automaticForms != null) {
            int k = this.maxPremises;
            String object1 = "\\l";

            for (int l1 = 0; l1 < k; l1++) {
               if (l1 != 0) {
                  object1 = object1 + "\\n";
               }

               object1 = object1 + this.getStackFormula(-l1);
            }

            return object1 + "\\l";
         } else {
            if (s.equals("rule form")) {
               Vector vector = this.hasFullMatch ? this.fullMatches : (this.hasPremiseMatch ? this.premiseMatches : null);
               if (vector != null && vector.size() > 0) {
                  SchematicRule schematicrule = ((RuleApplication)vector.elementAt(0)).getForm();
                  return "\\l" + schematicrule.format(" . ", " .: ") + "\\l";
               }
            }

            if (s.equals("rule form premises")) {
               Vector vector1 = this.hasFullMatch ? this.fullMatches : (this.hasPremiseMatch ? this.premiseMatches : null);
               if (vector1 != null && vector1.size() > 0) {
                  RuleApplication ruleapplication = (RuleApplication)vector1.elementAt(0);
                  int k1 = ruleapplication.getPremiseCount();
                  String s3 = "\\l";

                  for (int j = 0; j < k1; j++) {
                     s3 = s3 + (j == 0 ? "" : "\\n") + this.getStackFormula(j - k1);
                  }

                  return s3 + "\\l";
               }
            }

            if (s.equals("rule form conclusion")) {
               return "\\l" + this.result + "\\l";
            } else {
               if (s.length() >= 4 && s.substring(0, 4).equals("rfsp")) {
                  boolean flag = this.maxPremises == 1;
                  String s1 = s.substring(4).trim();
                  if (s1.equals("s")) {
                     return flag ? "" : "s";
                  }

                  if (s1.equals("es")) {
                     return flag ? "es" : "";
                  }

                  if (s1.equals("those")) {
                     return flag ? "that" : "those";
                  }
               }

               if (s.length() >= 3 && s.substring(0, 3).equals("rfp")) {
                  boolean flag1 = false;
                  Vector vector2 = this.hasFullMatch ? this.fullMatches : (this.hasPremiseMatch ? this.premiseMatches : null);
                  if (vector2 != null && vector2.size() > 0) {
                     flag1 = ((RuleApplication)vector2.elementAt(0)).getPremiseCount() == 1;
                  }

                  String s2 = s.substring(3).trim();
                  if (s2.equals("s")) {
                     return flag1 ? "" : "s";
                  }

                  if (s2.equals("es")) {
                     return flag1 ? "es" : "";
                  }

                  if (s2.equals("those")) {
                     return flag1 ? "that" : "those";
                  }

                  if (s2.equals("are")) {
                     return flag1 ? "is" : "are";
                  }
               }

               if (s.length() >= 2 && s.substring(0, 2).equals("rf")) {
                  boolean flag2 = this.automaticForms != null && this.automaticForms.length == 1;
                  String s5 = s.substring(2).trim();
                  if (s5.equals("s")) {
                     return flag2 ? "" : "s";
                  }
               }

               return null;
            }
         }
      }
   }

   void reportError(String s) {
      this.reportError(s, null);
   }

   void reportError(String s, Hashtable hashtable) {
      if (this.preview) {
         if (this.previewError == null) {
            this.previewError = new ErrorRef(s, hashtable);
         }
      } else if (hashtable == null) {
         this.line.showMessage(s, this);
      } else {
         this.line.showMessage(s, this, hashtable);
      }
   }

   void putMessageObject(String s, Object object) {
      if (!this.preview) {
         this.line.setMessageButtonParam(s, object);
      }
   }

   void clearMessage() {
      this.line.clearMessage();
   }

   void clearMessage(int i) {
      this.line.clearMessage(i);
   }

   int getStackSize() {
      return this.stack.size();
   }

   DerivationNode getCitedNode(int i) {
      int j = this.citedNodes.size();
      if (i < 0) {
         i += j;
      }

      return i >= 0 && i < j ? (DerivationNode)this.citedNodes.elementAt(i) : null;
   }

   Expression getStackFormula(int i) {
      int j = this.stack.size();
      if (i < 0) {
         i += j;
      }

      return i >= 0 && i < j ? (Expression)this.stack.elementAt(i) : null;
   }

   BoundVariableNames getBoundVariableNames(int i) {
      Expression expression = this.getStackFormula(i);
      return expression == null ? null : expression.getBoundVariableNames();
   }

   BoundVariableNames getLineBoundVariableNames() {
      return this.lineFormula == null ? null : this.lineFormula.getBoundVariableNames();
   }

   void popStack(int i) {
      int j = this.stack.size();
      int k = j - i;
      if (k < 0) {
         k = 0;
      }

      if (this.finalStep) {
         this.consumedFormulas = new Vector();

         for (int l = 0; l < j - k; l++) {
            this.consumedFormulas.addElement(this.stack.elementAt(k + l));
         }
      }

      this.citedNodes.setSize(k);
      this.stack.setSize(k);
   }

   String getRuleName() {
      return this.ruleName;
   }

   static int parsePremiseNumber(String s) {
      if (s.equals("PR")) {
         return 0;
      } else if (s.length() < 2 || !s.substring(0, 2).equals("PR")) {
         return -1;
      } else if (s.length() > 2 && "-0".indexOf(s.substring(2, 3)) != -1) {
         return -1;
      } else {
         try {
            return Integer.parseInt(s.substring(2));
         } catch (NumberFormatException numberformatexception) {
            return -1;
         }
      }
   }
}
