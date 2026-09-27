package edu.ucla.phil.logic;

import java.util.Vector;

public class SimpleTerm extends Term {
   private Expression binder;

   public SimpleTerm(String s) {
      super(s);
   }

   @Override
   void initKind() {
      this.kind = 3;
   }

   public void setBinder(Expression expression) {
      this.binder = expression;
   }

   boolean hasBinder() {
      return this.binder != null;
   }

   Expression getBinder() {
      return this.binder;
   }

   @Override
   boolean isAlphaEquivalent(Expression expression, BinderMap bindermap) {
      if (!(expression instanceof SimpleTerm)) {
         return false;
      } else {
         Expression expression1 = ((SimpleTerm)expression).binder;
         if (this.binder != null) {
            if (expression1 == null) {
               return false;
            }

            boolean flag = this.isArgumentPlaceholder();
            boolean flag1 = ((SimpleTerm)expression).isArgumentPlaceholder();
            if (flag != flag1) {
               return false;
            }

            if (flag) {
               return this.binder.indexOfChildSymbol(this.symbol) == expression1.indexOfChildSymbol(expression.symbol);
            }

            if (bindermap != null) {
               return bindermap.getCounterpart(this.binder) == expression1;
            }
         } else if (expression1 != null) {
            return false;
         }

         return this.symbol.equals(expression.symbol);
      }
   }

   @Override
   Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, BinderMap bindermap, Vector vector) {
      Expression expression1 = this.binder == null ? null : bindermap.getCounterpart(expression, this.binder, vector);
      if (schemeinstantiation != null && expression == null && expression1 == null) {
         TermLetter termletter = new TermLetter(this);
         LetterReplacement letterreplacement = schemeinstantiation.getReplacement(termletter);
         if (letterreplacement != null) {
            return letterreplacement.replacement.instantiate(this, schemeinstantiation, bindermap, vector);
         }

         if (this.binder != null) {
            schemeinstantiation.addPendingLetter(termletter);
         }
      }

      if (this.isArgumentPlaceholder() && expression != null) {
         vector.addElement(this);
         Expression expression2 = expression.getChild(this.binder.indexOfChildSymbol(this.symbol)).instantiate(null, schemeinstantiation, bindermap, vector);
         vector.removeElementAt(vector.size() - 1);
         return expression2;
      } else {
         SimpleTerm simpleterm1 = new SimpleTerm(this.symbol);
         simpleterm1.binder = expression1;
         return simpleterm1;
      }
   }

   @Override
   void findBoundVariableOccurrences(String s, ExpressionPath expressionpath, Vector vector) {
      if (s != null) {
         if (this.isBoundVariable() && s.equals(this.symbol)) {
            vector.addElement(expressionpath.clone());
         }
      }
   }

   @Override
   int renameBoundVariables(Vector vector, int i) {
      if (this.binder != null) {
         Expression expression = this.binder.getChild(0);
         if (this == expression && i < vector.size()) {
            String s = (String)vector.elementAt(i);
            if (s != null && !s.equals("")) {
               this.symbol = s;
            }

            i++;
         } else {
            this.symbol = expression.symbol;
         }
      }

      return i;
   }

   @Override
   void linkVariables(VariableScope variablescope) {
      this.binder = (Expression)variablescope.get(this.symbol);
   }

   @Override
   void findMislinkedVariables(VariableScope variablescope, Vector vector) {
      Expression expression = (Expression)variablescope.get(this.symbol);
      if (this.binder != expression) {
         vector.addElement(new Expression[]{expression, this});
      }
   }

   @Override
   void linkArgumentPlaceholders(Expression expression) {
      if (this.binder == null && expression.indexOfChildSymbol(this.symbol) != -1) {
         this.binder = expression;
      }
   }

   @Override
   boolean hasUndeclaredPlaceholder(Expression expression) {
      return this.symbol.equals(LogicProgram.findNumberedPlaceholder(this.symbol)) && (expression == null || expression.indexOfChildSymbol(this.symbol) == -1);
   }

   @Override
   boolean isArgumentPlaceholder() {
      return this.binder != null && this.binder.bindsArguments();
   }

   @Override
   boolean isBoundVariable() {
      return this.binder != null && !this.binder.bindsArguments();
   }

   static boolean isSimpleTerm(String s) {
      return isSimpleTerm(s, false);
   }

   static boolean isSimpleTerm(String s, boolean flag) {
      Expression expression;
      try {
         expression = LogicProgram.parseFormula(s, true, flag);
      } catch (FormulaParseException formulaparseexception) {
         return false;
      }

      return expression instanceof SimpleTerm;
   }

   @Override
   void collectTermSymbols(Vector vector, Vector vector1) {
      if (vector != null && !vector.contains(this.symbol)) {
         vector.addElement(this.symbol);
      }

      if (!this.isBoundVariable() && vector1 != null && !vector1.contains(this.symbol)) {
         vector1.addElement(this.symbol);
      }
   }

   @Override
   void collectFreeVariables(Vector vector) {
      if (!this.isBoundVariable() && !vector.contains(this.symbol)) {
         vector.addElement(this.symbol);
      }
   }

   @Override
   boolean match(Expression expression, Expression expression1, SchemeInstantiation schemeinstantiation, BinderMap bindermap, Vector vector) {
      if (this.binder == null) {
         if (expression1 != null) {
            return expression == null
               ? schemeinstantiation.addReplacement(this, expression1)
               : super.match(expression, expression1, schemeinstantiation, bindermap, vector);
         } else {
            return expression != null || schemeinstantiation.addPendingLetter(this) && schemeinstantiation.deferMatch(this, null, bindermap, vector);
         }
      } else if (this.isArgumentPlaceholder()) {
         vector.addElement(this);
         boolean flag = expression.getChild(this.binder.indexOfChildSymbol(this.symbol)).match(null, expression1, schemeinstantiation, bindermap, vector);
         vector.removeElementAt(vector.size() - 1);
         return flag;
      } else {
         return expression1 == null
            || expression1 instanceof SimpleTerm && ((SimpleTerm)expression1).getBinder() == bindermap.getCounterpart(expression, this.binder, vector);
      }
   }

   @Override
   Expression abstractQuantifiers(Vector vector, int i, SchemeInstantiation schemeinstantiation) {
      if (this.isBoundVariable()) {
         this.symbol = this.binder.getChild(0).symbol;
      }

      return this;
   }

   @Override
   boolean containsVariableBoundBy(Expression expression) {
      return this.binder == expression;
   }

   @Override
   void collectSchematicLetters(Vector vector) {
      SchematicLetter schematicletter = this.getSchematicLetter();
      if (schematicletter != null && vector.indexOf(schematicletter) == -1) {
         vector.addElement(schematicletter);
      }
   }

   @Override
   SchematicLetter getSchematicLetter() {
      return this.isBoundVariable() ? null : new TermLetter(this);
   }

   @Override
   boolean addPendingLetters(SchemeInstantiation schemeinstantiation) {
      return this.isBoundVariable() ? true : schemeinstantiation.addPendingLetter(this);
   }

   @Override
   boolean isFullyInstantiated(SchemeInstantiation schemeinstantiation) {
      return this.isBoundVariable() || schemeinstantiation.getReplacement(this.getSchematicLetter()) != null;
   }

   @Override
   boolean usesArgumentParens() {
      return true;
   }

   @Override
   String formatMinimal(int i) {
      return this.symbol;
   }

   @Override
   String formatFull(int i) {
      return this.symbol;
   }

   @Override
   void layoutDisplayTree(FormulaParseNode formulaparsenode) {
      super.layoutDisplayTree(formulaparsenode);
      formulaparsenode.length = this.symbol.length();
   }
}
