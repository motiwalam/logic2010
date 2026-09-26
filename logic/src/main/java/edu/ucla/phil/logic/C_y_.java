package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

class C_y_ extends DialogHandler implements DerivationConstants {
   DerivationLine f1446;
   static String[] f1447 = LogicProgram.symbols;

   C_y_(DerivationLine derivationline, String s) {
      super(s);
      this.f1446 = derivationline;
   }

   @Override
   boolean m451(MessageDialog messagedialog) {
      String s = this.m450(messagedialog);
      if (s == null) {
         return true;
      } else if (s.equalsIgnoreCase("query")) {
         LPDerivation lpderivation = this.f1446.f317.f915;
         C_l_E c_l_e = lpderivation.focus;
         if (c_l_e != null && c_l_e == c_l_e.f1258.f324) {
            c_l_e.f1258.m585();
         }

         lpderivation.abort(false);
         lpderivation.resetVarNames();
         this.f1446.f336 = null;
         this.f1446.m595(false);
         return true;
      } else if (s.equalsIgnoreCase("replace")) {
         Expression expression2 = (Expression)this.m449("sum");
         Vector vector2 = (Vector)this.m449("caches");
         this.f1446.m557(3);
         this.f1446.m6(expression2.toString());
         this.f1446.f332 = expression2;
         this.f1446.f333 = true;
         this.f1446.f336 = vector2;
         return true;
      } else if (s.equalsIgnoreCase("err033")) {
         DerivationLineChecker derivationlinechecker10 = (DerivationLineChecker)this.m449("just");
         if (derivationlinechecker10 != null && derivationlinechecker10.f955 != null) {
            String s2 = "";
            int l = derivationlinechecker10.f955.size();

            for (int j1 = 0; j1 < l; j1++) {
               s2 = s2 + (j1 == 0 ? "" : "\\n") + derivationlinechecker10.f955.elementAt(j1);
            }

            Hashtable hashtable4 = Message.params(
               "rule form premises", "\\l" + s2 + "\\l", "rule form conclusion", "\\l" + derivationlinechecker10.f942 + "\\l"
            );
            this.f1446.m554("dererr033", derivationlinechecker10, 3, hashtable4);
         }

         C_y_ c_y_1 = this.f1446.f326.f759;
         if (c_y_1 != null) {
            c_y_1.m447("sum", derivationlinechecker10.f942);
            c_y_1.m447("caches", this.f1446.f336);
         }

         return true;
      } else if (s.equalsIgnoreCase("err103")) {
         DerivationLineChecker derivationlinechecker9 = (DerivationLineChecker)this.m449("just");
         if (derivationlinechecker9 != null && derivationlinechecker9.f955 != null) {
            String s1 = "";
            int k = derivationlinechecker9.f955.size();

            for (int i1 = 0; i1 < k; i1++) {
               s1 = s1 + (i1 == 0 ? "" : "\\n") + derivationlinechecker9.f955.elementAt(i1);
            }

            Hashtable hashtable3 = Message.params("rule form premises", "\\l" + s1 + "\\l");
            this.f1446.m554("dererr103", derivationlinechecker9, 3, hashtable3);
         }

         return true;
      } else if (s.equalsIgnoreCase("undo")) {
         EditableTextPane editabletextpane2 = (EditableTextPane)this.m449("undo");
         if (editabletextpane2 != null) {
            if (!editabletextpane2.m1844()) {
               C_KB.m759("dernot012");
            }

            editabletextpane2.requestFocus();
         }

         return false;
      } else if (s.equalsIgnoreCase("dummyVarQueryOK")) {
         DerivationLineChecker derivationlinechecker8 = (DerivationLineChecker)this.m449("just");
         C_HF c_hf5 = (C_HF)this.m449("inst");
         EditableTextPane editabletextpane7 = (EditableTextPane)this.m449("edit");
         C__B c__b = new C__B();
         String s7 = LogicProgram.m995(editabletextpane7.getText(), f1447, maggie);
         Expression expression3 = c_hf5.m688().conclusion;
         if (!c__b.m1571(C__B.m1579(expression3.m1243(), 0), s7)) {
            C_KB.m761("dernot017", Message.params("gen var", "\\l" + s7 + "\\l"));
            return false;
         } else {
            Expression expression7 = c_hf5.m688().premises[0].getChild(0).instantiate(c_hf5.m690());
            Expression expression9 = derivationlinechecker8.m1628(-1).copy();
            C_MB c_mb = new C_MB();
            Expression expression16 = expression3.m1239(c_hf5.m690(), c_mb);
            if (!c__b.m1574(expression3, expression16, c_mb, null)) {
               Vector vector6 = c__b.f925;
               Hashtable hashtable8 = Message.params(
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
                  expression22 = simpleterm5.m1850();
                  Message.putParam(hashtable8, "op phrase", "\\l" + ((Expression)object1).symbol + ((Expression)object1).getChild(0).symbol + "\\l");
                  Message.putParam(hashtable8, "op wfe", "\\l" + object1 + "\\l");
               }

               if (expression22 != null) {
                  C_KB.m761("dernot018", hashtable8);
               } else {
                  C_KB.m761("dernot029", hashtable8);
               }

               new C_GC(editabletextpane7).start();
               return false;
            } else {
               return true;
            }
         }
      } else if (s.equalsIgnoreCase("existentialVarQueryOK")) {
         DerivationLineChecker derivationlinechecker7 = (DerivationLineChecker)this.m449("just");
         C_HF c_hf4 = (C_HF)this.m449("inst");
         EditableTextPane editabletextpane6 = (EditableTextPane)this.m449("edit");
         SchemeInstantiation schemeinstantiation2 = new SchemeInstantiation(c_hf4.m690());
         String s6 = LogicProgram.m995(editabletextpane6.getText(), f1447, maggie);
         Hashtable hashtable6 = Message.params(
            "inst var",
            "\\l" + s6 + "\\l",
            "gen var",
            "\\l" + derivationlinechecker7.m1628(-1).getChild(0) + "\\l",
            "gen wff",
            "\\l" + derivationlinechecker7.m1628(-1).getChild(1) + "\\l",
            "gen all",
            "\\l" + derivationlinechecker7.m1628(-1) + "\\l"
         );

         Expression expression6;
         try {
            expression6 = LogicProgram.m1008(s6, true, false);
         } catch (FormulaParseException formulaparseexception) {
            C_KB.m761("dernot022", hashtable6);
            new C_GC(editabletextpane6).start();
            return false;
         }

         if (expression6 == null) {
            return false;
         } else if (!(expression6 instanceof SimpleTerm)) {
            C_KB.m761("dernot023", hashtable6);
            new C_GC(editabletextpane6).start();
            return false;
         } else if (!schemeinstantiation2.m1882(schemeinstantiation2.f1189.elementAt(0).toString(), s6)) {
            C_KB.m761(schemeinstantiation2.f1190, schemeinstantiation2.f1191);
            new C_GC(editabletextpane6).start();
            return false;
         } else if (!derivationlinechecker7.m1618(c_hf4, schemeinstantiation2)) {
            Vector vector5 = derivationlinechecker7.f962;
            derivationlinechecker7.f962 = null;
            hashtable6 = Message.putParam(hashtable6, "inst wff", "\\l" + c_hf4.m688().conclusion.instantiate(schemeinstantiation2) + "\\l");
            if (vector5 != null && vector5.size() != 0) {
               Expression[] aexpression4 = (Expression[])vector5.elementAt(0);
               Expression expression15 = aexpression4[0];
               SimpleTerm simpleterm4 = (SimpleTerm)aexpression4[1];
               Message.putParam(hashtable6, "op phrase", "\\l" + expression15.symbol + expression15.getChild(0).symbol + "\\l");
               Message.putParam(hashtable6, "op wfe", "\\l" + expression15 + "\\l");
            }

            C_KB.m763("dernot024", hashtable6, derivationlinechecker7);
            new C_GC(editabletextpane6).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("universalTermQueryOK")) {
         DerivationLineChecker derivationlinechecker6 = (DerivationLineChecker)this.m449("just");
         C_HF c_hf3 = (C_HF)this.m449("inst");
         EditableTextPane editabletextpane5 = (EditableTextPane)this.m449("edit");
         SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation(c_hf3.m690());
         String s5 = LogicProgram.m995(editabletextpane5.getText(), f1447, maggie);
         Hashtable hashtable5 = Message.params(
            "inst term",
            "\\l" + s5 + "\\l",
            "gen var",
            "\\l" + derivationlinechecker6.m1628(-1).getChild(0) + "\\l",
            "gen wff",
            "\\l" + derivationlinechecker6.m1628(-1).getChild(1) + "\\l",
            "gen all",
            "\\l" + derivationlinechecker6.m1628(-1) + "\\l"
         );

         Expression expression5;
         try {
            expression5 = LogicProgram.m1008(s5, true, false);
         } catch (FormulaParseException formulaparseexception1) {
            C_KB.m761("dernot019", hashtable5);
            new C_GC(editabletextpane5).start();
            return false;
         }

         if (expression5 == null) {
            return false;
         } else if (!(expression5 instanceof Term)) {
            C_KB.m761("dernot020", hashtable5);
            new C_GC(editabletextpane5).start();
            return false;
         } else if (!schemeinstantiation1.m1882(schemeinstantiation1.f1189.elementAt(0).toString(), s5)) {
            C_KB.m761(schemeinstantiation1.f1190, schemeinstantiation1.f1191);
            new C_GC(editabletextpane5).start();
            return false;
         } else if (!derivationlinechecker6.m1618(c_hf3, schemeinstantiation1)) {
            Vector vector4 = derivationlinechecker6.f962;
            derivationlinechecker6.f962 = null;
            hashtable5 = Message.putParam(hashtable5, "inst wff", "\\l" + c_hf3.m688().conclusion.instantiate(schemeinstantiation1) + "\\l");
            if (vector4 != null && vector4.size() != 0) {
               Expression[] aexpression3 = (Expression[])vector4.elementAt(0);
               Expression expression14 = aexpression3[0];
               SimpleTerm simpleterm3 = (SimpleTerm)aexpression3[1];
               Message.putParam(hashtable5, "op phrase", "\\l" + expression14.symbol + expression14.getChild(0).symbol + "\\l");
               Message.putParam(hashtable5, "op wfe", "\\l" + expression14 + "\\l");
            }

            C_KB.m763("dernot021", hashtable5, derivationlinechecker6);
            new C_GC(editabletextpane5).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("leibniz12TermQueryOK")) {
         DerivationLineChecker derivationlinechecker5 = (DerivationLineChecker)this.m449("just");
         C_HF c_hf2 = (C_HF)this.m449("inst");
         C_j_E c_j_e = (C_j_E)this.m449("edit");
         Hashtable hashtable2 = Message.m665(c_j_e.f1192, null);
         SchemeInstantiation schemeinstantiation3 = new SchemeInstantiation(c_hf2.m690());
         String s10 = LogicProgram.m995(c_j_e.getText(), f1447, maggie);
         schemeinstantiation3.m1882(schemeinstantiation3.f1189.elementAt(0).toString(), s10);
         Expression expression4 = c_hf2.f391.conclusion.instantiate(schemeinstantiation3);
         Message.putParam(hashtable2, "sub wff", "\\l" + expression4 + "\\l");
         Vector vector3 = expression4.m1259();
         if (vector3 != null) {
            Expression[] aexpression2 = (Expression[])vector3.elementAt(0);
            Expression expression13 = aexpression2[0];
            SimpleTerm simpleterm2 = (SimpleTerm)aexpression2[1];
            Message.putParam(hashtable2, "op phrase", "\\l" + expression13.symbol + expression13.getChild(0).symbol + "\\l");
            Message.putParam(hashtable2, "op wfe", "\\l" + expression13 + "\\l");
            Message.putParam(hashtable2, "op var", "\\l" + simpleterm2.symbol + "\\l");
            C_KB.m763("dernot034", hashtable2, derivationlinechecker5);
            new C_GC(c_j_e).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("leibniz34TermQueryOK")) {
         DerivationLineChecker derivationlinechecker4 = (DerivationLineChecker)this.m449("just");
         C_HF c_hf1 = (C_HF)this.m449("inst");
         C_KE c_ke = (C_KE)this.m449("edit");
         Hashtable hashtable1 = Message.m665(c_ke.f498, null);
         SchematicLetter schematicletter1 = c_ke.f499;
         SchemeInstantiation schemeinstantiation4 = new SchemeInstantiation(c_hf1.m690());
         String s11 = LogicProgram.m995(c_ke.getText(), f1447, maggie);
         schemeinstantiation4.m1882(schematicletter1.toString(), s11);
         int k1 = c_hf1.f391.premises[c_hf1.f392[0]].symbol.equals("~") ? 0 : 1;
         Expression expression11 = c_hf1.f391.premises[c_hf1.f392[k1]];
         Expression expression12 = derivationlinechecker4.m1628(k1 - 2);
         Expression expression17 = expression11.instantiate(schemeinstantiation4);
         Expression expression18 = expression11.getChild(0).getChild(0).instantiate(schemeinstantiation4);
         Expression expression19 = c_hf1.f391.premises[c_hf1.f392[1 - k1]];
         Expression expression20 = derivationlinechecker4.m1628(-k1 - 1);
         Expression expression21 = expression19.instantiate(schemeinstantiation4);
         Expression expression23 = expression19.getChild(0).instantiate(schemeinstantiation4);
         Expression expression24 = c_hf1.f391.conclusion.instantiate(schemeinstantiation4);
         Message.putParam(hashtable1, "term A", "\\l" + expression23 + "\\l");
         Message.putParam(hashtable1, "wff A", "\\l" + expression21 + "\\l");
         Message.putParam(hashtable1, "term B", "\\l" + expression18 + "\\l");
         Message.putParam(hashtable1, "wff B", "\\l" + expression12 + "\\l");
         Message.putParam(hashtable1, "sub wff", "\\l" + expression17 + "\\l");
         Message.putParam(hashtable1, "rule premise A", "\\l" + expression19 + "\\l");
         Message.putParam(hashtable1, "rule premise B", "\\l" + expression11 + "\\l");
         Vector vector7 = expression17.m1259();
         if (vector7 != null) {
            Expression[] aexpression6 = (Expression[])vector7.elementAt(0);
            Expression expression1 = aexpression6[0];
            SimpleTerm simpleterm1 = (SimpleTerm)aexpression6[1];
            Message.putParam(hashtable1, "op phrase", "\\l" + expression1.symbol + expression1.getChild(0).symbol + "\\l");
            Message.putParam(hashtable1, "op wfe", "\\l" + expression1 + "\\l");
            Message.putParam(hashtable1, "op var", "\\l" + simpleterm1.symbol + "\\l");
            C_KB.m763("dernot040", hashtable1, derivationlinechecker4);
            new C_GC(c_ke).start();
            return false;
         } else if (!expression12.m1235(expression17)) {
            C_KB.m763("dernot041", hashtable1, derivationlinechecker4);
            new C_GC(c_ke).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("eulerTermQueryOK")) {
         DerivationLineChecker derivationlinechecker3 = (DerivationLineChecker)this.m449("just");
         C_HF c_hf = (C_HF)this.m449("inst");
         C_Y c_y = (C_Y)this.m449("edit");
         SchemeInstantiation schemeinstantiation = new SchemeInstantiation(c_hf.m690());
         SchematicLetter schematicletter = (SchematicLetter)schemeinstantiation.f1189.elementAt(0);
         String s9 = LogicProgram.m995(c_y.getText(), f1447, maggie);
         schemeinstantiation.m1882(schematicletter.toString(), s9);
         Term term4 = (Term)c_hf.f391.conclusion.getChild(0);
         Term term5 = (Term)c_hf.f391.conclusion.getChild(1);
         Expression expression10 = derivationlinechecker3.m1628(-1);
         Term term6 = (Term)expression10.getChild(0);
         Term term = (Term)expression10.getChild(1);
         Term term1 = (Term)derivationlinechecker3.f941.getChild(0);
         Term term2 = (Term)derivationlinechecker3.f941.getChild(1);
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
         Vector vector1 = term3.m1259();
         if (vector1 != null) {
            Expression[] aexpression = (Expression[])vector1.elementAt(0);
            Expression expression = aexpression[0];
            SimpleTerm simpleterm = (SimpleTerm)aexpression[1];
            Message.putParam(hashtable, "op phrase", "\\l" + expression.symbol + expression.getChild(0).symbol + "\\l");
            Message.putParam(hashtable, "op wfe", "\\l" + expression + "\\l");
            Message.putParam(hashtable, "op var", "\\l" + simpleterm.symbol + "\\l");
            C_KB.m763("dernot046", hashtable, derivationlinechecker3);
            new C_GC(c_y).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("interchangeFormulaQueryOK")) {
         DerivationLineChecker derivationlinechecker2 = (DerivationLineChecker)this.m449("just");
         EditableTextPane editabletextpane4 = (EditableTextPane)this.m449("edit");
         String s3 = editabletextpane4.getText();
         int[] aint = new int[]{editabletextpane4.getSelectionStart(), editabletextpane4.getSelectionEnd()};
         String s4 = LogicProgram.m996(s3, f1447, maggie, aint);
         String s8 = editabletextpane4.getSelectedText();
         Hashtable hashtable7 = new Hashtable();
         Message.putParam(hashtable7, "full text", s3);
         Message.putParam(hashtable7, "selection", s8);
         if (s8 != null && s8.length() != 0) {
            Expression expression8;
            try {
               expression8 = LogicProgram.m1008(LogicProgram.m995(s8, f1447, maggie), true, false);
            } catch (FormulaParseException formulaparseexception2) {
               C_KB.m761("dernot054", hashtable7);
               editabletextpane4.requestFocus();
               return false;
            }

            if (!(expression8 instanceof Formula)) {
               C_KB.m761("dernot055", hashtable7);
               editabletextpane4.requestFocus();
               return false;
            } else {
               C_DD c_dd = new C_DD(s4);
               if (!expression8.m1235(c_dd.m480(aint[0], aint[1]).f278)) {
                  Message.putParam(hashtable7, "full expression", LogicProgram.m995(c_dd.f278.m1207(1), maggie, f1447));
                  C_KB.m761("dernot056", hashtable7);
                  editabletextpane4.requestFocus();
                  return false;
               } else {
                  return true;
               }
            }
         } else {
            C_KB.m759("dernot013");
            editabletextpane4.requestFocus();
            return false;
         }
      } else if (s.equalsIgnoreCase("interchangeRuleQueryOK")) {
         DerivationLineChecker derivationlinechecker1 = (DerivationLineChecker)this.m449("just");
         EditableTextPane editabletextpane3 = (EditableTextPane)this.m449("edit");
         Rule rule1 = this.m2179(derivationlinechecker1, editabletextpane3, new ErrorRef("dernot057"));
         if (rule1 == null) {
            return false;
         } else {
            this.m447("rule", rule1);
            return true;
         }
      } else if (!s.equalsIgnoreCase("cieRuleQueryOK")) {
         return true;
      } else {
         DerivationLineChecker derivationlinechecker = (DerivationLineChecker)this.m449("just");
         EditableTextPane editabletextpane = (EditableTextPane)this.m449("ruleEdit");
         EditableTextPane editabletextpane1 = (EditableTextPane)this.m449("condEdit");
         Rule rule = this.m2179(derivationlinechecker, editabletextpane, new ErrorRef("dernot057"));
         if (rule == null) {
            return false;
         } else {
            Object object = this.m2179(derivationlinechecker, editabletextpane1, new ErrorRef("dernot059"));
            if (object == null) {
               return false;
            } else {
               if (!(object instanceof C_l_F) && !(object instanceof C_OF)) {
                  if (!(object instanceof SchematicRule)) {
                     Vector vector = new Vector();
                     SchematicRule[] aschematicrule = ((Rule)object).m1374();
                     int i = 0;

                     for (int j = 0; j < aschematicrule.length; j++) {
                        SchematicRule schematicrule = aschematicrule[j];
                        if (schematicrule.premises.length == 0 && C_GA.m631(derivationlinechecker, schematicrule, false)) {
                           vector.addElement(schematicrule.conclusion);
                           aschematicrule[i++] = schematicrule;
                        }
                     }

                     if (i == 0) {
                        C_KB.m761("dernot060", Message.params("condition", ((Rule)object).f820));
                        return false;
                     }

                     Expression[] aexpression1 = new Expression[i];
                     vector.copyInto(aexpression1);
                     i = C_KB.m778(derivationlinechecker.f935, aexpression1, "Please choose a form of " + ((Rule)object).f820);
                     if (i == -1) {
                        editabletextpane1.requestFocus();
                        return false;
                     }

                     object = aschematicrule[i];
                  }

                  C_HF c_hf6 = new C_HF((SchematicRule)object, new int[0], new SchemeInstantiation(), new C__B());
                  c_hf6.f391.conclusion.m1266(null, c_hf6.f393);
                  if (!C_KB.m782(derivationlinechecker, c_hf6)) {
                     editabletextpane1.requestFocus();
                     return false;
                  }

                  object = new C_PF(c_hf6.f391, c_hf6.f393);
               }

               this.m447("rule", rule);
               this.m447("condition", object);
               return true;
            }
         }
      }
   }

   Rule m2179(DerivationLineChecker derivationlinechecker, EditableTextPane editabletextpane, ErrorRef errorref) {
      String s = editabletextpane.getText().trim();
      if (s.length() == 0) {
         C_KB.m761(errorref.f427, errorref.f428);
         editabletextpane.requestFocus();
         return null;
      } else {
         Object object;
         Integer integer;
         if ((integer = LogicProgram.parseInteger(s)) != null) {
            try {
               object = new C_l_F(derivationlinechecker.f935.f317.f915, integer);
            } catch (IllegalArgumentException illegalargumentexception) {
               C_KB.m761("dererr003", Message.params("remote line number", s));
               editabletextpane.requestFocus();
               return null;
            }
         } else {
            int i;
            if ((i = DerivationLineChecker.m1633(s.toUpperCase())) != -1) {
               try {
                  LPDerivation lpderivation = derivationlinechecker.f935.f317.f915;
                  Expression[] aexpression = lpderivation.premises;
                  if (aexpression == null || aexpression.length == 0) {
                     C_KB.m759("dererr029");
                     editabletextpane.requestFocus();
                     return null;
                  }

                  if (i == 0) {
                     Vector vector = new Vector();

                     for (i = 1; i <= aexpression.length; i++) {
                        vector.addElement(new C_OF(lpderivation, i));
                     }

                     object = new Rule("all premises", vector);
                  } else {
                     object = new C_OF(lpderivation, i);
                  }
               } catch (IllegalArgumentException illegalargumentexception1) {
                  C_KB.m761("dererr030", Message.params("premise index", i + ""));
                  editabletextpane.requestFocus();
                  return null;
               }
            } else if ((object = LPDerivation.getRule(s)) == null) {
               C_KB.m761("dernot058", Message.params("rule name", s));
               editabletextpane.requestFocus();
               return null;
            }
         }

         return (Rule)object;
      }
   }
}
