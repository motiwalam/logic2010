package edu.ucla.phil.logic;

import java.util.Vector;

public class MembershipFormula extends Formula {
   public MembershipFormula(String s) {
      super(s);
   }

   @Override
   void initKind() {
      this.kind = 7;
   }

   public void setElement(Term term) {
      this.children.addElement(term);
      this.childCount++;
   }

   public void setSet(Term term) {
      this.children.addElement(term);
      this.childCount++;
   }

   @Override
   Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, BinderMap bindermap, Vector vector) {
      MembershipFormula membershipformula1 = new MembershipFormula(this.symbol);

      for (int i = 0; i < this.childCount; i++) {
         membershipformula1.addChild(this.getChild(i).instantiate(expression, schemeinstantiation, bindermap, vector));
      }

      return membershipformula1;
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
