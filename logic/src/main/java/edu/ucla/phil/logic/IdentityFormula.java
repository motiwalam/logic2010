package edu.ucla.phil.logic;

import java.util.Vector;

public class IdentityFormula extends Formula {
   public IdentityFormula(String s) {
      super(s);
   }

   @Override
   void initKind() {
      this.kind = 6;
   }

   public void setLeft(Term term) {
      this.children.addElement(term);
      this.childCount++;
   }

   public void setRight(Term term) {
      this.children.addElement(term);
      this.childCount++;
   }

   @Override
   Expression abstractQuantifiers(Vector vector, int i, SchemeInstantiation schemeinstantiation) {
      super.abstractQuantifiers(vector, i, schemeinstantiation);
      Expression expression = this.getChild(0);
      Expression expression1 = this.getChild(1);
      if (expression.toCanonicalString().compareTo(expression1.toCanonicalString()) > 0) {
         this.children.setElementAt(expression, 1);
         this.children.setElementAt(expression1, 0);
      }

      return this;
   }

   @Override
   Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, BinderMap bindermap, Vector vector) {
      IdentityFormula identityformula1 = new IdentityFormula(this.symbol);

      for (int i = 0; i < this.childCount; i++) {
         identityformula1.addChild(this.getChild(i).instantiate(expression, schemeinstantiation, bindermap, vector));
      }

      return identityformula1;
   }

   @Override
   String formatMinimal(int i) {
      return this.getChild(0).formatMinimal(i) + this.symbol + this.getChild(1).formatMinimal(i);
   }

   @Override
   String formatFull(int i) {
      return this.getChild(0).formatFull(i) + this.symbol + this.getChild(1).formatFull(i);
   }

   @Override
   void layoutDisplayTree(FormulaParseNode formulaparsenode) {
      super.layoutDisplayTree(formulaparsenode);
      formulaparsenode.length = (
            formulaparsenode.getChild(1).offset = (formulaparsenode.getChild(0).offset = 0) + formulaparsenode.getChild(0).length + this.symbol.length()
         )
         + formulaparsenode.getChild(1).length;
   }
}
