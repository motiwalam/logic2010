package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Vector;

public class QuantifiedFormula extends Formula {
   public QuantifiedFormula(String s) {
      super(s);
   }

   @Override
   void initKind() {
      this.kind = 1;
   }

   public void setVariable(SimpleTerm simpleterm) {
      this.children.addElement(simpleterm);
      this.childCount++;
   }

   public void setBody(Formula formula) {
      this.children.addElement(formula);
      this.childCount++;
   }

   SimpleTerm getVariable() {
      return (SimpleTerm)this.getChild(0);
   }

   Formula getBody() {
      return (Formula)this.getChild(1);
   }

   @Override
   Expression getSubexpression(int[] aint, int i, int j, Vector vector) {
      if (i == j) {
         return this;
      } else {
         if (vector != null) {
            vector.addElement(this);
         }

         Expression expression = this.getChild(aint[i]);
         return expression == null ? null : expression.getSubexpression(aint, i + 1, j, vector);
      }
   }

   @Override
   boolean isAlphaEquivalent(Expression expression, BinderMap bindermap) {
      if (expression == null) {
         return false;
      } else if (this.kind == expression.kind && this.symbol.equals(expression.symbol) && this.childCount == expression.childCount) {
         if (bindermap != null) {
            bindermap.putCounterpart(this, expression);
         }

         for (int i = 0; i < this.childCount; i++) {
            if (!this.getChild(i).isAlphaEquivalent(expression.getChild(i), bindermap)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, BinderMap bindermap, Vector vector) {
      QuantifiedFormula quantifiedformula1 = new QuantifiedFormula(this.symbol);
      bindermap.putCounterpart(expression, this, vector, quantifiedformula1);

      for (int i = 0; i < this.childCount; i++) {
         quantifiedformula1.addChild(this.getChild(i).instantiate(expression, schemeinstantiation, bindermap, vector));
      }

      return quantifiedformula1;
   }

   @Override
   Expression abstractQuantifiers(Vector vector, int i, SchemeInstantiation schemeinstantiation) {
      if (!this.getChild(1).containsVariableBoundBy(this)) {
         return this.getChild(1).abstractQuantifiers(vector, i, schemeinstantiation);
      } else {
         ((SimpleTerm)this.getChild(0)).symbol = SchematicLetter.placeholder(i);
         super.abstractQuantifiers(vector, i + 1, schemeinstantiation);
         Expression expression = this.getChild(1);
         boolean flag = false;
         if (this.symbol.equals("!")) {
            expression = (Expression)(expression.symbol.equals("~") ? expression.getChild(0) : expression.negate());
            this.children.setElementAt(expression, 1);
            this.symbol = "@";
            flag = true;
         }

         Enumeration enumeration = schemeinstantiation.keys();
         SchematicLetter schematicletter = null;
         expression = expression.copy();

         while (enumeration.hasMoreElements()) {
            SchematicLetter schematicletter1 = (SchematicLetter)enumeration.nextElement();
            if (schematicletter1 instanceof PredicateLetter) {
               LetterReplacement letterreplacement = schemeinstantiation.getReplacement(schematicletter1);
               if (TruthTableEvaluator.areEquivalent(expression, letterreplacement.replacement.getChild(1).copy())) {
                  schematicletter = schematicletter1;
                  break;
               }
            }
         }

         if (schematicletter == null) {
            schematicletter = PredicateLetter.freshPredicateLetter(i, vector);
            if (!schemeinstantiation.addReplacement(schematicletter.toExpression(), this)) {
               throw new RuntimeException("could not predicate a quantifier");
            }
         }

         return (Expression)(flag ? schematicletter.toExpression().negate() : schematicletter.toExpression());
      }
   }

   @Override
   Expression expandQuantifiers(int i, String s, boolean flag) {
      super.expandQuantifiers(i, s, flag);
      QuantifiedFormula quantifiedformula1 = new QuantifiedFormula(this.symbol);
      quantifiedformula1.setVariable(new SimpleTerm(LogicProgram.defaultVariable(0)));
      AtomicFormula atomicformula = new AtomicFormula(LogicProgram.predicateLetter(0));
      atomicformula.addArgument(new SimpleTerm(LogicProgram.defaultVariable(0)));
      quantifiedformula1.setBody(atomicformula);
      quantifiedformula1.linkVariables();
      atomicformula = new AtomicFormula(LogicProgram.predicateLetter(0));
      atomicformula.addArgument(new OperationTerm(LogicProgram.operationLetter(0)));
      String s1 = this.symbol.equals("!") ? "|" : "&";
      SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
      quantifiedformula1.match(this.copy(), schemeinstantiation);
      schemeinstantiation.addReplacement(LogicProgram.operationLetter(0), s + 0);
      LetterReplacement letterreplacement = schemeinstantiation.getReplacement(new OperationLetter(LogicProgram.operationLetter(0), 0));
      Object object = atomicformula.instantiate(schemeinstantiation);

      for (int j = 1; j < i; j++) {
         letterreplacement.replacement.symbol = s + j;
         Object object1 = object;
         Expression expression = atomicformula.instantiate(schemeinstantiation);
         object = new ConnectiveFormula(s1);
         ((ConnectiveFormula)object).setLeft((Formula)object1);
         ((ConnectiveFormula)object).setRight((Formula)expression);
      }

      ((Expression)object).linkVariables();
      return (Expression)object;
   }

   @Override
   Vector collectBinders(Vector vector) {
      vector.addElement(this);
      return super.collectBinders(vector);
   }

   @Override
   void linkVariables(VariableScope variablescope) {
      variablescope.push(this.getChild(0).symbol, this);
      super.linkVariables(variablescope);
      variablescope.pop(this.getChild(0).symbol);
   }

   @Override
   void findMislinkedVariables(VariableScope variablescope, Vector vector) {
      variablescope.push(this.getChild(0).symbol, this);
      super.findMislinkedVariables(variablescope, vector);
      variablescope.pop(this.getChild(0).symbol);
   }

   @Override
   boolean match(Expression expression, Expression expression1, SchemeInstantiation schemeinstantiation, BinderMap bindermap, Vector vector) {
      if (expression1 != null) {
         if (this.kind != expression1.kind || !this.symbol.equals(expression1.symbol) || this.childCount != expression1.childCount) {
            return false;
         }

         bindermap.putCounterpart(expression, this, vector, expression1);
      }

      for (int i = 0; i < this.childCount; i++) {
         if (!this.getChild(i).match(expression, expression1 == null ? null : expression1.getChild(i), schemeinstantiation, bindermap, vector)) {
            return false;
         }
      }

      return true;
   }

   String formatScope(Expression expression, int i) {
      return expression instanceof ConnectiveFormula && expression.childCount > 1 ? "(" + expression.formatMinimal(i) + ")" : expression.formatMinimal(i);
   }

   @Override
   String formatMinimal(int i) {
      return this.symbol + this.getChild(0) + this.formatScope(this.getChild(1), i);
   }

   @Override
   String formatFull(int i) {
      return this.symbol + this.getChild(0) + this.getChild(1).formatFull(i);
   }

   @Override
   void layoutDisplayTree(FormulaParseNode formulaparsenode) {
      super.layoutDisplayTree(formulaparsenode);
      formulaparsenode.length = (
            formulaparsenode.getChild(1).offset = (formulaparsenode.getChild(0).offset = this.symbol.length()) + formulaparsenode.getChild(0).length
         )
         + formulaparsenode.getChild(1).length;
   }
}
