package edu.ucla.phil.logic;

import java.util.Vector;

public class AtomicFormula extends Formula {
   private Formula wrappedFormula;

   public AtomicFormula(String s) {
      super(s);
   }

   AtomicFormula(Formula formula) {
      super(null);
      this.wrappedFormula = formula;
   }

   @Override
   void initKind() {
      this.kind = 0;
   }

   public void addArgument(Term term) {
      this.children.addElement(term);
      this.childCount++;
   }

   Formula getWrappedFormula() {
      return this.wrappedFormula;
   }

   @Override
   Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, BinderMap bindermap, Vector vector) {
      if (expression == null && schemeinstantiation != null) {
         LetterReplacement letterreplacement = schemeinstantiation.getReplacement(this.getSchematicLetter());
         if (letterreplacement != null) {
            return letterreplacement.replacement.instantiate(this, schemeinstantiation, bindermap, vector);
         }
      }

      AtomicFormula atomicformula1 = new AtomicFormula(this.symbol);

      for (int i = 0; i < this.childCount; i++) {
         atomicformula1.addChild(this.getChild(i).instantiate(expression, schemeinstantiation, bindermap, vector));
      }

      return atomicformula1;
   }

   @Override
   boolean match(Expression expression, Expression expression1, SchemeInstantiation schemeinstantiation, BinderMap bindermap, Vector vector) {
      return expression != null
         ? super.match(expression, expression1, schemeinstantiation, bindermap, vector)
         : this.matchLetter(expression1, schemeinstantiation, bindermap, vector);
   }

   @Override
   ErrorRef checkReplacementType(Expression expression) {
      return expression != null && !(expression instanceof Formula)
         ? new ErrorRef("dererr068", Message.params("pattern", "\\l" + this + "\\l", "replacement", "\\l" + expression + "\\l"))
         : null;
   }

   @Override
   void collectSchematicLetters(Vector vector) {
      SchematicLetter schematicletter = this.getSchematicLetter();
      if (vector.indexOf(schematicletter) == -1) {
         vector.addElement(schematicletter);
      }

      super.collectSchematicLetters(vector);
   }

   @Override
   SchematicLetter getSchematicLetter() {
      return new PredicateLetter(this);
   }

   @Override
   boolean addPendingLetters(SchemeInstantiation schemeinstantiation) {
      return schemeinstantiation.addPendingLetter(this) & super.addPendingLetters(schemeinstantiation);
   }

   @Override
   boolean isFullyInstantiated(SchemeInstantiation schemeinstantiation) {
      return schemeinstantiation.getReplacement(this.getSchematicLetter()) == null ? false : super.isFullyInstantiated(schemeinstantiation);
   }

   @Override
   boolean bindsArguments() {
      return true;
   }

   static boolean isPredicateLetter(String s) {
      return LogicProgram.predicateLetters.indexOf(s.charAt(0)) != -1;
   }

   @Override
   boolean usesArgumentParens() {
      return !isPredicateLetter(this.symbol);
   }

   @Override
   String formatMinimal(int i) {
      String s = this.symbol;
      boolean flag = this.childCount > 1 || this.childCount > 0 && this.usesArgumentParens();
      if (flag) {
         s = s + "(";
      }

      for (int j = 0; j < this.childCount; j++) {
         s = s + this.getChild(j).formatMinimal(i);
      }

      if (flag) {
         s = s + ")";
      }

      return s;
   }

   @Override
   String formatFull(int i) {
      String s = this.symbol;
      boolean flag = this.childCount > 1 || this.childCount > 0 && this.usesArgumentParens();
      if (flag) {
         s = s + "(";
      }

      for (int j = 0; j < this.childCount; j++) {
         s = s + this.getChild(j).formatFull(i);
      }

      if (flag) {
         s = s + ")";
      }

      return s;
   }

   @Override
   void layoutDisplayTree(FormulaParseNode formulaparsenode) {
      super.layoutDisplayTree(formulaparsenode);
      formulaparsenode.length = this.symbol.length();
      int i = formulaparsenode.getChildCount();

      for (int j = 0; j < i; j++) {
         FormulaParseNode formulaparsenode1 = formulaparsenode.getChild(j);
         formulaparsenode1.offset = formulaparsenode.length;
         formulaparsenode.length = formulaparsenode.length + formulaparsenode1.length;
      }
   }
}
