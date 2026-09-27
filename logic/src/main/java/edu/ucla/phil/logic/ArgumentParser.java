package edu.ucla.phil.logic;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ArgumentParser {
   String source;
   String[] premiseTexts = new String[0];
   String conclusionText = null;
   Expression[] premises = new Expression[0];
   Expression conclusion = null;
   boolean conclusionOnly;
   RuleApplication[] premiseMatches = null;
   RuleApplication[] fullMatches = null;
   static final String[] ERROR_MESSAGES = new String[]{"no error", "no conclusion", "no premises or conclusion"};

   ArgumentParser(String s) {
      this(s, false);
   }

   ArgumentParser(String s, boolean flag) {
      if ((this.source = s) != null) {
         if (this.conclusionOnly = s.indexOf(".") == -1) {
            s = ".:" + s;
         }

         int i = s.indexOf(".:");
         if (i != -1) {
            this.conclusionText = s.substring(i + 2).trim();

            try {
               this.conclusion = LogicProgram.parseFormula(this.conclusionText, false, false, flag);
            } catch (FormulaParseException formulaparseexception1) {
            }

            s = s.substring(0, i);
         }

         Vector vector = new Vector();
         Vector vector1 = new Vector();

         while (s.length() > 0) {
            int j = s.indexOf(".");
            String s1 = (j == -1 ? s : s.substring(0, j)).trim();
            s = j == -1 ? "" : s.substring(j + 1);

            try {
               Expression expression = LogicProgram.parseFormula(s1);
               if (expression != null) {
                  vector.addElement(s1);
                  vector1.addElement(expression);
               }
            } catch (FormulaParseException formulaparseexception) {
               vector.addElement(s1);
               vector1.addElement(null);
            }
         }

         this.premiseTexts = new String[vector.size()];
         vector.copyInto(this.premiseTexts);
         this.premises = new Expression[vector1.size()];
         vector1.copyInto(this.premises);
      }
   }

   static ArgumentParser parse(String s) {
      return s == null ? null : new ArgumentParser(s);
   }

   static String normalizeDots(String s) {
      String s1 = s;
      if (s == null) {
         return null;
      } else {
         do {
            Pattern pattern = Pattern.compile("\\.( *\\.)");
            s = s1;
            Matcher matcher = pattern.matcher(s1);
            s1 = matcher.replaceAll("$1");
         } while (!s1.equals(s));

         Matcher matcher1 = Pattern.compile("^ *\\.(.*)\\. *$").matcher(s1);
         return matcher1.replaceAll("$1");
      }
   }

   String getUnparsedText() {
      int i = this.premises.length;

      for (int j = 0; j < i; j++) {
         if (this.premises[j] == null) {
            return this.premiseTexts[j];
         }
      }

      return this.conclusion == null && this.conclusionText != null && !this.conclusionText.equals("") ? this.conclusionText : null;
   }

   int getErrorCode() {
      return this.getErrorCode(true);
   }

   int getErrorCode(boolean flag) {
      if (this.source == null || this.conclusionText != null && !this.conclusionText.equals("")) {
         return !flag && this.conclusionOnly ? 1 : 0;
      } else {
         return this.premises.length == 0 ? 2 : 1;
      }
   }

   static String describeError(ArgumentParser argumentparser, boolean flag, boolean flag1) {
      if (argumentparser == null) {
         return ERROR_MESSAGES[2];
      } else {
         String s = argumentparser.getUnparsedText();
         if (s != null) {
            return "could not parse \"" + s + "\"";
         } else {
            int i = argumentparser.getErrorCode(flag1);
            return i == 0 && flag ? null : ERROR_MESSAGES[i];
         }
      }
   }

   String describeError(boolean flag, boolean flag1) {
      return describeError(this, flag, flag1);
   }

   int matchRule(Rule rule) {
      if (rule != null && this.describeError(true, true) == null) {
         int i = this.premises == null ? 0 : this.premises.length;
         SchematicRule[] aschematicrule = rule.getAllForms();
         int j = aschematicrule.length;
         Vector vector = new Vector();
         Vector vector1 = new Vector();

         for (int k = 0; k < j; k++) {
            SchematicRule schematicrule = aschematicrule[k];
            if ((schematicrule.premises == null ? 0 : schematicrule.premises.length) == i) {
               SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
               boolean flag = schematicrule.conclusion.match(this.conclusion, schemeinstantiation);
               PermutationIterator permutationiterator = new PermutationIterator(i);

               while (true) {
                  label149: {
                     int[] aint = permutationiterator.current();
                     SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation();
                     BoundVariableMap boundvariablemap = new BoundVariableMap();
                     boolean flag1 = false;
                     if (i > 0) {
                        SchemeInstantiation[] aschemeinstantiation = new SchemeInstantiation[i];

                        for (int l = 0; l < i; l++) {
                           aschemeinstantiation[l] = new SchemeInstantiation();
                           if (!schematicrule.premises[aint[l]].match(this.premises[l], aschemeinstantiation[l])) {
                              break label149;
                           }
                        }

                        for (int k1 = 0; k1 < i; k1++) {
                           if (!schemeinstantiation1.mergeFrom(aschemeinstantiation[k1])) {
                              break label149;
                           }
                        }

                        if (schemeinstantiation1.hasNoDeferredMatches()) {
                           for (int l1 = 0; l1 < i; l1++) {
                              if (!boundvariablemap.matches(schematicrule.premises[aint[l1]], this.premises[l1], schemeinstantiation1)) {
                                 break label149;
                              }
                           }
                        } else {
                           flag1 = true;
                        }
                     }

                     if (!flag1) {
                        vector1.addElement(
                           new RuleApplication(
                              schematicrule, aint, (SchemeInstantiation)schemeinstantiation1.clone(), (BoundVariableMap)boundvariablemap.clone()
                           )
                        );
                     }

                     label99:
                     if (flag && schemeinstantiation1.mergeFrom(schemeinstantiation) && schemeinstantiation1.hasNoDeferredMatches()) {
                        if (flag1) {
                           for (int j1 = 0; j1 < i; j1++) {
                              if (!boundvariablemap.matches(schematicrule.premises[aint[j1]], this.premises[j1], schemeinstantiation1)) {
                                 break label99;
                              }
                           }

                           vector1.addElement(
                              new RuleApplication(
                                 schematicrule, aint, (SchemeInstantiation)schemeinstantiation1.clone(), (BoundVariableMap)boundvariablemap.clone()
                              )
                           );
                        }

                        label92:
                        if (boundvariablemap.matches(schematicrule.conclusion, this.conclusion, schemeinstantiation1)) {
                           if (schematicrule.name.equalsIgnoreCase("EI")) {
                              Expression expression = schematicrule.getConclusion().getChild(0).instantiate(schemeinstantiation1);
                              if (!(expression instanceof SimpleTerm)) {
                                 break label92;
                              }
                           }

                           vector.addElement(new RuleApplication(schematicrule, aint, schemeinstantiation1, boundvariablemap));
                        }
                     }
                  }

                  if (!permutationiterator.next()) {
                     break;
                  }
               }
            }
         }

         int i1;
         if ((i1 = vector1.size()) == 0) {
            this.premiseMatches = null;
         } else {
            this.premiseMatches = new RuleApplication[i1];
            vector1.copyInto(this.premiseMatches);
         }

         if ((i1 = vector.size()) == 0) {
            this.fullMatches = null;
         } else {
            this.fullMatches = new RuleApplication[i1];
            vector.copyInto(this.fullMatches);
         }

         return this.premiseMatches == null ? 0 : (this.fullMatches == null ? 1 : 2);
      } else {
         this.premiseMatches = null;
         this.fullMatches = null;
         return 0;
      }
   }

   Expression toConditional() {
      if (this.getUnparsedText() == null && this.getErrorCode() == 0) {
         if (this.premises.length == 0) {
            return this.conclusion.copy();
         } else {
            Object object = this.premises[0].copy();
            if (!(object instanceof Formula)) {
               return null;
            } else if (!(this.conclusion instanceof Formula)) {
               return null;
            } else {
               int i = this.premises.length;

               for (int j = 1; j < i; j++) {
                  if (!(this.premises[j] instanceof Formula)) {
                     return null;
                  }

                  ConnectiveFormula connectiveformula = new ConnectiveFormula("&");
                  connectiveformula.setLeft((Formula)object);
                  connectiveformula.setRight((Formula)this.premises[j].copy());
                  object = connectiveformula;
               }

               ConnectiveFormula connectiveformula1 = new ConnectiveFormula("->");
               connectiveformula1.setLeft((Formula)object);
               connectiveformula1.setRight((Formula)this.conclusion.copy());
               return connectiveformula1;
            }
         }
      } else {
         return null;
      }
   }

   @Override
   public String toString() {
      return this.format(".", ".:");
   }

   String format(String s, String s1) {
      if (this.conclusionOnly) {
         return this.conclusionText;
      } else {
         String s2 = "";
         int i = this.premises.length;

         for (int j = 0; j < i; j++) {
            s2 = s2 + (j == 0 ? "" : s) + this.premiseTexts[j];
         }

         if (this.conclusionText != null) {
            s2 = s2 + s1 + this.conclusionText;
         }

         return s2;
      }
   }
}
