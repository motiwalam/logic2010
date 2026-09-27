package edu.ucla.phil.logic;

class RuleApplication extends Justification {
   static final int TYPE = 1;
   SchematicRule form;
   int[] premiseOrder;
   SchemeInstantiation instantiation;
   BoundVariableMap boundVariables;
   int failureKind;
   Object failureDetail;

   RuleApplication(SchematicRule schematicrule, int[] aint, SchemeInstantiation schemeinstantiation, BoundVariableMap boundvariablemap) {
      super(schematicrule.name);
      this.form = schematicrule;
      this.premiseOrder = aint;
      this.instantiation = schemeinstantiation;
      this.boundVariables = boundvariablemap;
      this.failureKind = 0;
      this.failureDetail = null;
   }

   @Override
   public Object clone() {
      RuleApplication ruleapplication1 = (RuleApplication)super.clone();
      if (this.premiseOrder != null) {
         ruleapplication1.premiseOrder = new int[this.premiseOrder.length];
         System.arraycopy(this.premiseOrder, 0, ruleapplication1.premiseOrder, 0, this.premiseOrder.length);
      }

      if (this.instantiation != null) {
         ruleapplication1.instantiation = (SchemeInstantiation)this.instantiation.clone();
      }

      if (this.boundVariables != null) {
         ruleapplication1.boundVariables = (BoundVariableMap)this.boundVariables.clone();
      }

      ruleapplication1.failureKind = 0;
      ruleapplication1.failureDetail = null;
      return ruleapplication1;
   }

   SchematicRule getForm() {
      return this.form;
   }

   int[] getPremiseOrder() {
      return this.premiseOrder;
   }

   SchemeInstantiation getInstantiation() {
      return this.instantiation;
   }

   BoundVariableMap getBoundVariables() {
      return this.boundVariables;
   }

   int getPremiseCount() {
      return this.form.premises.length;
   }

   Expression getPremise(int i) {
      return this.getPremise(i, null);
   }

   Expression getPremise(int i, DerivationLineChecker derivationlinechecker) {
      BinderMap bindermap = new BinderMap();
      Expression expression = this.form.premises[this.premiseOrder[i]].instantiate(this.instantiation, bindermap);
      this.boundVariables.renameBinders(this.form.premises[this.premiseOrder[i]], expression, bindermap, derivationlinechecker);
      return expression;
   }

   Expression getConclusion() {
      return this.getConclusion(null);
   }

   Expression getConclusion(DerivationLineChecker derivationlinechecker) {
      BinderMap bindermap = new BinderMap();
      Expression expression = this.form.conclusion.instantiate(this.instantiation, bindermap);
      this.boundVariables.renameBinders(this.form.conclusion, expression, bindermap, derivationlinechecker);
      return expression;
   }

   boolean isFullyInstantiated(Expression expression) {
      return expression.isFullyInstantiated(this.instantiation) && this.boundVariables.coversBinders(expression);
   }

   @Override
   boolean reapply(DerivationLineChecker derivationlinechecker) {
      Rule rule = LPDerivation.getRule(derivationlinechecker.ruleName);
      if (rule != null && rule.includes(this.form)) {
         int i = this.form.premises.length;
         derivationlinechecker.getClass();
         if (!derivationlinechecker.matchLine && !derivationlinechecker.finalStep
            ? i <= derivationlinechecker.argumentCount
            : i == derivationlinechecker.argumentCount) {
            for (int j = 0; j < i; j++) {
               Expression expression = this.getPremise(j);
               if (expression.findMislinkedVariables() != null || !expression.isIdentical(derivationlinechecker.getStackFormula(j - i))) {
                  return false;
               }
            }

            if (this.form
                  .indexByName(rule.getForms(derivationlinechecker.line.box.module, derivationlinechecker.interactive ? "manualOrDisabled" : "disabled"))
               == -1) {
               return false;
            } else if ((derivationlinechecker.result = this.getConclusion()).findMislinkedVariables() != null) {
               return false;
            } else if (!derivationlinechecker.checkInstantiationRestrictions(this.instantiation, true)) {
               return false;
            } else if (derivationlinechecker.matchLine && !derivationlinechecker.checkResultMatchesLine(true)) {
               return false;
            } else {
               derivationlinechecker.popStack(i);
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   String encode() {
      return "1:" + this;
   }

   static RuleApplication decode(String s) {
      int i;
      if ((i = s.indexOf(":")) == -1) {
         return null;
      } else {
         return !s.substring(0, i).equals(Integer.toString(1)) ? null : decodeBody(s.substring(i + 1));
      }
   }

   @Override
   public String toString() {
      return this.label + ExpressionPath.format(this.premiseOrder) + this.instantiation.encode() + "," + this.boundVariables.encode();
   }

   static RuleApplication decodeBody(String s) {
      int i;
      if ((i = s.indexOf("{")) == -1) {
         return null;
      } else {
         Rule rule = LPDerivation.getRule(s.substring(0, i));
         if (rule != null && rule instanceof SchematicRule) {
            String s1 = s.substring(i);
            if ((i = s1.indexOf("}")) == -1) {
               return null;
            } else {
               int[] aint = ExpressionPath.parse(s1.substring(0, i + 1));
               if (aint == null) {
                  return null;
               } else {
                  s = s1.substring(i + 1);
                  if ((i = s.indexOf(",")) == -1) {
                     return null;
                  } else {
                     SchemeInstantiation schemeinstantiation = SchemeInstantiation.decode(s.substring(0, i));
                     if (schemeinstantiation == null) {
                        return null;
                     } else {
                        s = s.substring(i + 1);
                        BoundVariableMap boundvariablemap = BoundVariableMap.decode(s);
                        return new RuleApplication((SchematicRule)rule, aint, schemeinstantiation, boundvariablemap);
                     }
                  }
               }
            }
         } else {
            return null;
         }
      }
   }

   RuleApplicationDisplay createDisplay(DerivationLineChecker derivationlinechecker) {
      return new RuleApplicationDisplay(this, derivationlinechecker);
   }
}
