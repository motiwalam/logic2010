package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

class DerivationQueryHandler extends DialogHandler implements DerivationConstants {
   DerivationLine line;
   static String[] SYMBOLS = LogicProgram.symbols;

   DerivationQueryHandler(DerivationLine derivationline, String s) {
      super(s);
      this.line = derivationline;
   }

   @Override
   boolean handleChoice(MessageDialog messagedialog) {
      String s = this.getSelectedAction(messagedialog);
      if (s == null) {
         return true;
      } else if (s.equalsIgnoreCase("query")) {
         LPDerivation lpderivation = this.line.box.module;
         DerivationLineEditor derivationlineeditor = lpderivation.focus;
         if (derivationlineeditor != null && derivationlineeditor == derivationlineeditor.line.annotationEditor) {
            derivationlineeditor.line.resolveRelativeReferences();
         }

         lpderivation.abort(false);
         lpderivation.resetVarNames();
         this.line.justifications = null;
         this.line.checkLine(false);
         return true;
      } else if (s.equalsIgnoreCase("replace")) {
         Expression expression2 = (Expression)this.getProperty("sum");
         Vector vector2 = (Vector)this.getProperty("caches");
         this.line.clearMessage(3);
         this.line.setFormulaText(expression2.toString());
         this.line.formula = expression2;
         this.line.syntaxOk = true;
         this.line.justifications = vector2;
         return true;
      } else if (s.equalsIgnoreCase("err033")) {
         DerivationLineChecker derivationlinechecker10 = (DerivationLineChecker)this.getProperty("just");
         if (derivationlinechecker10 != null && derivationlinechecker10.consumedFormulas != null) {
            String s2 = "";
            int l = derivationlinechecker10.consumedFormulas.size();

            for (int j1 = 0; j1 < l; j1++) {
               s2 = s2 + (j1 == 0 ? "" : "\\n") + derivationlinechecker10.consumedFormulas.elementAt(j1);
            }

            Hashtable hashtable4 = Message.params(
               "rule form premises", "\\l" + s2 + "\\l", "rule form conclusion", "\\l" + derivationlinechecker10.result + "\\l"
            );
            this.line.showMessage("dererr033", derivationlinechecker10, 3, hashtable4);
         }

         DerivationQueryHandler derivationqueryhandler1 = this.line.messageButton.handler;
         if (derivationqueryhandler1 != null) {
            derivationqueryhandler1.setProperty("sum", derivationlinechecker10.result);
            derivationqueryhandler1.setProperty("caches", this.line.justifications);
         }

         return true;
      } else if (s.equalsIgnoreCase("err103")) {
         DerivationLineChecker derivationlinechecker9 = (DerivationLineChecker)this.getProperty("just");
         if (derivationlinechecker9 != null && derivationlinechecker9.consumedFormulas != null) {
            String s1 = "";
            int k = derivationlinechecker9.consumedFormulas.size();

            for (int i1 = 0; i1 < k; i1++) {
               s1 = s1 + (i1 == 0 ? "" : "\\n") + derivationlinechecker9.consumedFormulas.elementAt(i1);
            }

            Hashtable hashtable3 = Message.params("rule form premises", "\\l" + s1 + "\\l");
            this.line.showMessage("dererr103", derivationlinechecker9, 3, hashtable3);
         }

         return true;
      } else if (s.equalsIgnoreCase("undo")) {
         EditableTextPane editabletextpane2 = (EditableTextPane)this.getProperty("undo");
         if (editabletextpane2 != null) {
            if (!editabletextpane2.undo()) {
               DerivationDialogs.showMessage("dernot012");
            }

            editabletextpane2.requestFocus();
         }

         return false;
      } else if (s.equalsIgnoreCase("dummyVarQueryOK")) {
         DerivationLineChecker derivationlinechecker8 = (DerivationLineChecker)this.getProperty("just");
         RuleApplication ruleapplication5 = (RuleApplication)this.getProperty("inst");
         EditableTextPane editabletextpane7 = (EditableTextPane)this.getProperty("edit");
         BoundVariableMap boundvariablemap = new BoundVariableMap();
         String s7 = LogicProgram.translateSymbols(editabletextpane7.getText(), SYMBOLS, maggie);
         Expression expression3 = ruleapplication5.getForm().conclusion;
         if (!boundvariablemap.assign(BoundVariableMap.binderVariable(expression3.getBinders(), 0), s7)) {
            DerivationDialogs.showMessage("dernot017", Message.params("gen var", "\\l" + s7 + "\\l"));
            return false;
         } else {
            Expression expression7 = ruleapplication5.getForm().premises[0].getChild(0).instantiate(ruleapplication5.getInstantiation());
            Expression expression9 = derivationlinechecker8.getStackFormula(-1).copy();
            BinderMap bindermap = new BinderMap();
            Expression expression16 = expression3.instantiate(ruleapplication5.getInstantiation(), bindermap);
            if (!boundvariablemap.renameBinders(expression3, expression16, bindermap, null)) {
               Vector vector6 = boundvariablemap.clashes;
               Hashtable hashtable10 = Message.params(
                  "inst wff",
                  "\\l" + expression9 + "\\l",
                  "inst term",
                  "\\l" + expression7 + "\\l",
                  "gen var",
                  "\\l" + expression16.getChild(0) + "\\l",
                  "gen wff",
                  "\\l" + expression16.getChild(1) + "\\l",
                  "gen all",
                  "\\l" + expression16 + "\\l"
               );
               Object object1 = null;
               SimpleTerm simpleterm5 = null;
               Expression expression22 = null;
               if (vector6 != null && vector6.size() != 0) {
                  Expression[] aexpression5 = (Expression[])vector6.elementAt(0);
                  object1 = aexpression5[0];
                  simpleterm5 = (SimpleTerm)aexpression5[1];
                  expression22 = simpleterm5.getBinder();
                  Message.putParam(hashtable10, "op phrase", "\\l" + ((Expression)object1).symbol + ((Expression)object1).getChild(0).symbol + "\\l");
                  Message.putParam(hashtable10, "op wfe", "\\l" + object1 + "\\l");
               }

               if (expression22 != null) {
                  DerivationDialogs.showMessage("dernot018", hashtable10);
               } else {
                  DerivationDialogs.showMessage("dernot029", hashtable10);
               }

               new DelayedFocusRequest(editabletextpane7).start();
               return false;
            } else {
               return true;
            }
         }
      } else if (s.equalsIgnoreCase("existentialVarQueryOK")) {
         DerivationLineChecker derivationlinechecker7 = (DerivationLineChecker)this.getProperty("just");
         RuleApplication ruleapplication4 = (RuleApplication)this.getProperty("inst");
         EditableTextPane editabletextpane6 = (EditableTextPane)this.getProperty("edit");
         SchemeInstantiation schemeinstantiation2 = new SchemeInstantiation(ruleapplication4.getInstantiation());
         String s6 = LogicProgram.translateSymbols(editabletextpane6.getText(), SYMBOLS, maggie);
         Hashtable hashtable7 = Message.params(
            "inst var",
            "\\l" + s6 + "\\l",
            "gen var",
            "\\l" + derivationlinechecker7.getStackFormula(-1).getChild(0) + "\\l",
            "gen wff",
            "\\l" + derivationlinechecker7.getStackFormula(-1).getChild(1) + "\\l",
            "gen all",
            "\\l" + derivationlinechecker7.getStackFormula(-1) + "\\l"
         );

         Expression expression6;
         try {
            expression6 = LogicProgram.parseFormula(s6, true, false);
         } catch (FormulaParseException formulaparseexception) {
            DerivationDialogs.showMessage("dernot022", hashtable7);
            new DelayedFocusRequest(editabletextpane6).start();
            return false;
         }

         if (expression6 == null) {
            return false;
         } else if (!(expression6 instanceof SimpleTerm)) {
            DerivationDialogs.showMessage("dernot023", hashtable7);
            new DelayedFocusRequest(editabletextpane6).start();
            return false;
         } else if (!schemeinstantiation2.addReplacement(schemeinstantiation2.pendingLetters.elementAt(0).toString(), s6)) {
            DerivationDialogs.showMessage(schemeinstantiation2.errorId, schemeinstantiation2.errorParams);
            new DelayedFocusRequest(editabletextpane6).start();
            return false;
         } else if (!derivationlinechecker7.verifyApplication(ruleapplication4, schemeinstantiation2)) {
            Vector vector5 = derivationlinechecker7.clashes;
            derivationlinechecker7.clashes = null;
            Hashtable hashtable8 = Message.putParam(
               hashtable7, "inst wff", "\\l" + ruleapplication4.getForm().conclusion.instantiate(schemeinstantiation2) + "\\l"
            );
            if (vector5 != null && vector5.size() != 0) {
               Expression[] aexpression4 = (Expression[])vector5.elementAt(0);
               Expression expression15 = aexpression4[0];
               SimpleTerm simpleterm4 = (SimpleTerm)aexpression4[1];
               Message.putParam(hashtable8, "op phrase", "\\l" + expression15.symbol + expression15.getChild(0).symbol + "\\l");
               Message.putParam(hashtable8, "op wfe", "\\l" + expression15 + "\\l");
            }

            DerivationDialogs.showMessage("dernot024", hashtable8, derivationlinechecker7);
            new DelayedFocusRequest(editabletextpane6).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("universalTermQueryOK")) {
         DerivationLineChecker derivationlinechecker6 = (DerivationLineChecker)this.getProperty("just");
         RuleApplication ruleapplication3 = (RuleApplication)this.getProperty("inst");
         EditableTextPane editabletextpane5 = (EditableTextPane)this.getProperty("edit");
         SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation(ruleapplication3.getInstantiation());
         String s5 = LogicProgram.translateSymbols(editabletextpane5.getText(), SYMBOLS, maggie);
         Hashtable hashtable5 = Message.params(
            "inst term",
            "\\l" + s5 + "\\l",
            "gen var",
            "\\l" + derivationlinechecker6.getStackFormula(-1).getChild(0) + "\\l",
            "gen wff",
            "\\l" + derivationlinechecker6.getStackFormula(-1).getChild(1) + "\\l",
            "gen all",
            "\\l" + derivationlinechecker6.getStackFormula(-1) + "\\l"
         );

         Expression expression5;
         try {
            expression5 = LogicProgram.parseFormula(s5, true, false);
         } catch (FormulaParseException formulaparseexception1) {
            DerivationDialogs.showMessage("dernot019", hashtable5);
            new DelayedFocusRequest(editabletextpane5).start();
            return false;
         }

         if (expression5 == null) {
            return false;
         } else if (!(expression5 instanceof Term)) {
            DerivationDialogs.showMessage("dernot020", hashtable5);
            new DelayedFocusRequest(editabletextpane5).start();
            return false;
         } else if (!schemeinstantiation1.addReplacement(schemeinstantiation1.pendingLetters.elementAt(0).toString(), s5)) {
            DerivationDialogs.showMessage(schemeinstantiation1.errorId, schemeinstantiation1.errorParams);
            new DelayedFocusRequest(editabletextpane5).start();
            return false;
         } else if (!derivationlinechecker6.verifyApplication(ruleapplication3, schemeinstantiation1)) {
            Vector vector4 = derivationlinechecker6.clashes;
            derivationlinechecker6.clashes = null;
            Hashtable hashtable6 = Message.putParam(
               hashtable5, "inst wff", "\\l" + ruleapplication3.getForm().conclusion.instantiate(schemeinstantiation1) + "\\l"
            );
            if (vector4 != null && vector4.size() != 0) {
               Expression[] aexpression3 = (Expression[])vector4.elementAt(0);
               Expression expression14 = aexpression3[0];
               SimpleTerm simpleterm3 = (SimpleTerm)aexpression3[1];
               Message.putParam(hashtable6, "op phrase", "\\l" + expression14.symbol + expression14.getChild(0).symbol + "\\l");
               Message.putParam(hashtable6, "op wfe", "\\l" + expression14 + "\\l");
            }

            DerivationDialogs.showMessage("dernot021", hashtable6, derivationlinechecker6);
            new DelayedFocusRequest(editabletextpane5).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("leibniz12TermQueryOK")) {
         DerivationLineChecker derivationlinechecker5 = (DerivationLineChecker)this.getProperty("just");
         RuleApplication ruleapplication2 = (RuleApplication)this.getProperty("inst");
         Leibniz12TermSelector leibniz12termselector = (Leibniz12TermSelector)this.getProperty("edit");
         Hashtable hashtable2 = Message.mergeParams(leibniz12termselector.extraParams, null);
         SchemeInstantiation schemeinstantiation3 = new SchemeInstantiation(ruleapplication2.getInstantiation());
         String s10 = LogicProgram.translateSymbols(leibniz12termselector.getText(), SYMBOLS, maggie);
         schemeinstantiation3.addReplacement(schemeinstantiation3.pendingLetters.elementAt(0).toString(), s10);
         Expression expression4 = ruleapplication2.form.conclusion.instantiate(schemeinstantiation3);
         Message.putParam(hashtable2, "sub wff", "\\l" + expression4 + "\\l");
         Vector vector3 = expression4.findMislinkedVariables();
         if (vector3 != null) {
            Expression[] aexpression2 = (Expression[])vector3.elementAt(0);
            Expression expression13 = aexpression2[0];
            SimpleTerm simpleterm2 = (SimpleTerm)aexpression2[1];
            Message.putParam(hashtable2, "op phrase", "\\l" + expression13.symbol + expression13.getChild(0).symbol + "\\l");
            Message.putParam(hashtable2, "op wfe", "\\l" + expression13 + "\\l");
            Message.putParam(hashtable2, "op var", "\\l" + simpleterm2.symbol + "\\l");
            DerivationDialogs.showMessage("dernot034", hashtable2, derivationlinechecker5);
            new DelayedFocusRequest(leibniz12termselector).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("leibniz34TermQueryOK")) {
         DerivationLineChecker derivationlinechecker4 = (DerivationLineChecker)this.getProperty("just");
         RuleApplication ruleapplication1 = (RuleApplication)this.getProperty("inst");
         Leibniz34TermSelector leibniz34termselector = (Leibniz34TermSelector)this.getProperty("edit");
         Hashtable hashtable1 = Message.mergeParams(leibniz34termselector.extraParams, null);
         SchematicLetter schematicletter1 = leibniz34termselector.ruleLetter;
         SchemeInstantiation schemeinstantiation4 = new SchemeInstantiation(ruleapplication1.getInstantiation());
         String s11 = LogicProgram.translateSymbols(leibniz34termselector.getText(), SYMBOLS, maggie);
         schemeinstantiation4.addReplacement(schematicletter1.toString(), s11);
         int k1 = ruleapplication1.form.premises[ruleapplication1.premiseOrder[0]].symbol.equals("~") ? 0 : 1;
         Expression expression11 = ruleapplication1.form.premises[ruleapplication1.premiseOrder[k1]];
         Expression expression12 = derivationlinechecker4.getStackFormula(k1 - 2);
         Expression expression17 = expression11.instantiate(schemeinstantiation4);
         Expression expression18 = expression11.getChild(0).getChild(0).instantiate(schemeinstantiation4);
         Expression expression19 = ruleapplication1.form.premises[ruleapplication1.premiseOrder[1 - k1]];
         Expression expression20 = derivationlinechecker4.getStackFormula(-k1 - 1);
         Expression expression21 = expression19.instantiate(schemeinstantiation4);
         Expression expression23 = expression19.getChild(0).instantiate(schemeinstantiation4);
         Expression expression24 = ruleapplication1.form.conclusion.instantiate(schemeinstantiation4);
         Message.putParam(hashtable1, "term A", "\\l" + expression23 + "\\l");
         Message.putParam(hashtable1, "wff A", "\\l" + expression21 + "\\l");
         Message.putParam(hashtable1, "term B", "\\l" + expression18 + "\\l");
         Message.putParam(hashtable1, "wff B", "\\l" + expression12 + "\\l");
         Message.putParam(hashtable1, "sub wff", "\\l" + expression17 + "\\l");
         Message.putParam(hashtable1, "rule premise A", "\\l" + expression19 + "\\l");
         Message.putParam(hashtable1, "rule premise B", "\\l" + expression11 + "\\l");
         Vector vector7 = expression17.findMislinkedVariables();
         if (vector7 != null) {
            Expression[] aexpression6 = (Expression[])vector7.elementAt(0);
            Expression expression1 = aexpression6[0];
            SimpleTerm simpleterm1 = (SimpleTerm)aexpression6[1];
            Message.putParam(hashtable1, "op phrase", "\\l" + expression1.symbol + expression1.getChild(0).symbol + "\\l");
            Message.putParam(hashtable1, "op wfe", "\\l" + expression1 + "\\l");
            Message.putParam(hashtable1, "op var", "\\l" + simpleterm1.symbol + "\\l");
            DerivationDialogs.showMessage("dernot040", hashtable1, derivationlinechecker4);
            new DelayedFocusRequest(leibniz34termselector).start();
            return false;
         } else if (!expression12.isIdentical(expression17)) {
            DerivationDialogs.showMessage("dernot041", hashtable1, derivationlinechecker4);
            new DelayedFocusRequest(leibniz34termselector).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("eulerTermQueryOK")) {
         DerivationLineChecker derivationlinechecker3 = (DerivationLineChecker)this.getProperty("just");
         RuleApplication ruleapplication = (RuleApplication)this.getProperty("inst");
         EulerTermSelector eulertermselector = (EulerTermSelector)this.getProperty("edit");
         SchemeInstantiation schemeinstantiation = new SchemeInstantiation(ruleapplication.getInstantiation());
         SchematicLetter schematicletter = (SchematicLetter)schemeinstantiation.pendingLetters.elementAt(0);
         String s9 = LogicProgram.translateSymbols(eulertermselector.getText(), SYMBOLS, maggie);
         schemeinstantiation.addReplacement(schematicletter.toString(), s9);
         Term term4 = (Term)ruleapplication.form.conclusion.getChild(0);
         Term term5 = (Term)ruleapplication.form.conclusion.getChild(1);
         Expression expression10 = derivationlinechecker3.getStackFormula(-1);
         Term term6 = (Term)expression10.getChild(0);
         Term term = (Term)expression10.getChild(1);
         Term term1 = (Term)derivationlinechecker3.lineFormula.getChild(0);
         Term term2 = (Term)derivationlinechecker3.lineFormula.getChild(1);
         Term term3 = (Term)term5.instantiate(schemeinstantiation);
         Hashtable hashtable = Message.params(
            "left premise term",
            "\\l" + term6 + "\\l",
            "left conclusion term",
            "\\l" + term1 + "\\l",
            "right premise term",
            "\\l" + term + "\\l",
            "right conclusion term",
            "\\l" + term2 + "\\l"
         );
         Message.putParam(hashtable, "scheme conclusion term", "\\l" + term3 + "\\l");
         Vector vector1 = term3.findMislinkedVariables();
         if (vector1 != null) {
            Expression[] aexpression = (Expression[])vector1.elementAt(0);
            Expression expression = aexpression[0];
            SimpleTerm simpleterm = (SimpleTerm)aexpression[1];
            Message.putParam(hashtable, "op phrase", "\\l" + expression.symbol + expression.getChild(0).symbol + "\\l");
            Message.putParam(hashtable, "op wfe", "\\l" + expression + "\\l");
            Message.putParam(hashtable, "op var", "\\l" + simpleterm.symbol + "\\l");
            DerivationDialogs.showMessage("dernot046", hashtable, derivationlinechecker3);
            new DelayedFocusRequest(eulertermselector).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("interchangeFormulaQueryOK")) {
         DerivationLineChecker derivationlinechecker2 = (DerivationLineChecker)this.getProperty("just");
         EditableTextPane editabletextpane4 = (EditableTextPane)this.getProperty("edit");
         String s3 = editabletextpane4.getText();
         int[] aint = new int[]{editabletextpane4.getSelectionStart(), editabletextpane4.getSelectionEnd()};
         String s4 = LogicProgram.translateSymbols(s3, SYMBOLS, maggie, aint);
         String s8 = editabletextpane4.getSelectedText();
         Hashtable hashtable9 = new Hashtable();
         Message.putParam(hashtable9, "full text", s3);
         Message.putParam(hashtable9, "selection", s8);
         if (s8 != null && s8.length() != 0) {
            Expression expression8;
            try {
               expression8 = LogicProgram.parseFormula(LogicProgram.translateSymbols(s8, SYMBOLS, maggie), true, false);
            } catch (FormulaParseException formulaparseexception2) {
               DerivationDialogs.showMessage("dernot054", hashtable9);
               editabletextpane4.requestFocus();
               return false;
            }

            if (!(expression8 instanceof Formula)) {
               DerivationDialogs.showMessage("dernot055", hashtable9);
               editabletextpane4.requestFocus();
               return false;
            } else {
               FormulaParseNode formulaparsenode = new FormulaParseNode(s4);
               if (!expression8.isIdentical(formulaparsenode.findNodeContaining(aint[0], aint[1]).expression)) {
                  Message.putParam(hashtable9, "full expression", LogicProgram.translateSymbols(formulaparsenode.expression.formatMinimal(1), maggie, SYMBOLS));
                  DerivationDialogs.showMessage("dernot056", hashtable9);
                  editabletextpane4.requestFocus();
                  return false;
               } else {
                  return true;
               }
            }
         } else {
            DerivationDialogs.showMessage("dernot013");
            editabletextpane4.requestFocus();
            return false;
         }
      } else if (s.equalsIgnoreCase("interchangeRuleQueryOK")) {
         DerivationLineChecker derivationlinechecker1 = (DerivationLineChecker)this.getProperty("just");
         EditableTextPane editabletextpane3 = (EditableTextPane)this.getProperty("edit");
         Rule rule1 = this.parseRuleReference(derivationlinechecker1, editabletextpane3, new ErrorRef("dernot057"));
         if (rule1 == null) {
            return false;
         } else {
            this.setProperty("rule", rule1);
            return true;
         }
      } else if (!s.equalsIgnoreCase("cieRuleQueryOK")) {
         return true;
      } else {
         DerivationLineChecker derivationlinechecker = (DerivationLineChecker)this.getProperty("just");
         EditableTextPane editabletextpane = (EditableTextPane)this.getProperty("ruleEdit");
         EditableTextPane editabletextpane1 = (EditableTextPane)this.getProperty("condEdit");
         Rule rule = this.parseRuleReference(derivationlinechecker, editabletextpane, new ErrorRef("dernot057"));
         if (rule == null) {
            return false;
         } else {
            Object object = this.parseRuleReference(derivationlinechecker, editabletextpane1, new ErrorRef("dernot059"));
            if (object == null) {
               return false;
            } else {
               if (!(object instanceof LineRule) && !(object instanceof PremiseRule)) {
                  if (!(object instanceof SchematicRule)) {
                     Vector vector = new Vector();
                     SchematicRule[] aschematicrule = ((Rule)object).getAllForms();
                     int i = 0;

                     for (int j = 0; j < aschematicrule.length; j++) {
                        SchematicRule schematicrule = aschematicrule[j];
                        if (schematicrule.premises.length == 0 && InterchangeJustification.isRuleUsable(derivationlinechecker, schematicrule, false)) {
                           vector.addElement(schematicrule.conclusion);
                           aschematicrule[i++] = schematicrule;
                        }
                     }

                     if (i == 0) {
                        DerivationDialogs.showMessage("dernot060", Message.params("condition", ((Rule)object).name));
                        return false;
                     }

                     Expression[] aexpression1 = new Expression[i];
                     vector.copyInto(aexpression1);
                     i = DerivationDialogs.chooseFormula(derivationlinechecker.line, aexpression1, "Please choose a form of " + ((Rule)object).name);
                     if (i == -1) {
                        editabletextpane1.requestFocus();
                        return false;
                     }

                     object = aschematicrule[i];
                  }

                  RuleApplication ruleapplication6 = new RuleApplication((SchematicRule)object, new int[0], new SchemeInstantiation(), new BoundVariableMap());
                  ruleapplication6.form.conclusion.match(null, ruleapplication6.instantiation);
                  if (!DerivationDialogs.instanceSchemeQuery(derivationlinechecker, ruleapplication6)) {
                     editabletextpane1.requestFocus();
                     return false;
                  }

                  object = new RuleInstance(ruleapplication6.form, ruleapplication6.instantiation);
               }

               this.setProperty("rule", rule);
               this.setProperty("condition", object);
               return true;
            }
         }
      }
   }

   Rule parseRuleReference(DerivationLineChecker derivationlinechecker, EditableTextPane editabletextpane, ErrorRef errorref) {
      String s = editabletextpane.getText().trim();
      if (s.length() == 0) {
         DerivationDialogs.showMessage(errorref.id, errorref.params);
         editabletextpane.requestFocus();
         return null;
      } else {
         Object object;
         Integer integer;
         if ((integer = LogicProgram.parseInteger(s)) != null) {
            try {
               object = new LineRule(derivationlinechecker.line.box.module, integer);
            } catch (IllegalArgumentException illegalargumentexception) {
               DerivationDialogs.showMessage("dererr003", Message.params("remote line number", s));
               editabletextpane.requestFocus();
               return null;
            }
         } else {
            int i;
            if ((i = DerivationLineChecker.parsePremiseNumber(s.toUpperCase())) != -1) {
               try {
                  LPDerivation lpderivation = derivationlinechecker.line.box.module;
                  Expression[] aexpression = lpderivation.premises;
                  if (aexpression == null || aexpression.length == 0) {
                     DerivationDialogs.showMessage("dererr029");
                     editabletextpane.requestFocus();
                     return null;
                  }

                  if (i == 0) {
                     Vector vector = new Vector();

                     for (i = 1; i <= aexpression.length; i++) {
                        vector.addElement(new PremiseRule(lpderivation, i));
                     }

                     object = new Rule("all premises", vector);
                  } else {
                     object = new PremiseRule(lpderivation, i);
                  }
               } catch (IllegalArgumentException illegalargumentexception1) {
                  DerivationDialogs.showMessage("dererr030", Message.params("premise index", i + ""));
                  editabletextpane.requestFocus();
                  return null;
               }
            } else if ((object = LPDerivation.getRule(s)) == null) {
               DerivationDialogs.showMessage("dernot058", Message.params("rule name", s));
               editabletextpane.requestFocus();
               return null;
            }
         }

         return (Rule)object;
      }
   }
}
