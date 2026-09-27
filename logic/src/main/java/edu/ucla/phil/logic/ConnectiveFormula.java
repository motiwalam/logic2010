package edu.ucla.phil.logic;

import java.util.Vector;

public class ConnectiveFormula extends Formula {
   public ConnectiveFormula(String s) {
      super(s);
   }

   @Override
   void initKind() {
      this.kind = 2;
   }

   public void setLeft(Formula formula) {
      this.children.addElement(formula);
      this.childCount++;
   }

   public void setRight(Formula formula) {
      this.children.addElement(formula);
      this.childCount++;
   }

   Formula getLeft() {
      return (Formula)this.getChild(0);
   }

   Formula getRight() {
      return (Formula)this.getChild(1);
   }

   @Override
   Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, BinderMap bindermap, Vector vector) {
      ConnectiveFormula connectiveformula1 = new ConnectiveFormula(this.symbol);
      connectiveformula1.displayAsInequality = this.displayAsInequality;

      for (int i = 0; i < this.childCount; i++) {
         connectiveformula1.addChild(this.getChild(i).instantiate(expression, schemeinstantiation, bindermap, vector));
      }

      return connectiveformula1;
   }

   String formatOperand(Formula formula, boolean flag, int i) {
      String s = formula.formatMinimal(i);
      if (!(formula instanceof ConnectiveFormula) || formula.childCount == 1) {
         return s;
      } else if (this.childCount != 1 && !this.symbol.equals("&") && !this.symbol.equals("|")) {
         return !formula.symbol.equals("->") && !formula.symbol.equals("<->") ? s : "(" + s + ")";
      } else {
         return !flag && this.symbol.equals(formula.symbol) ? s : "(" + s + ")";
      }
   }

   @Override
   String formatMinimal(int i) {
      Formula formula = (Formula)this.getChild(0);
      if ((i == -1 || i == 0 && this.displayAsInequality) && this.symbol.equals("~") && formula.symbol.equals("=")) {
         return formula.getChild(0).formatMinimal(i) + "<>" + formula.getChild(1).formatMinimal(i);
      } else if (this.childCount == 1) {
         return this.symbol + this.formatOperand(formula, true, i);
      } else {
         Formula formula1 = (Formula)this.getChild(1);
         return this.formatOperand(formula, false, i) + this.symbol + this.formatOperand(formula1, true, i);
      }
   }

   @Override
   String formatFull(int i) {
      Formula formula = (Formula)this.getChild(0);
      if ((i == -1 || i == 0 && this.displayAsInequality) && this.symbol.equals("~") && formula.symbol.equals("=")) {
         return formula.getChild(0).formatFull(i) + "<>" + formula.getChild(1).formatFull(i);
      } else if (this.childCount == 1) {
         return this.symbol + formula.formatFull(i);
      } else {
         Formula formula1 = (Formula)this.getChild(1);
         return "(" + formula.formatFull(i) + this.symbol + formula1.formatFull(i) + ")";
      }
   }

   @Override
   void layoutDisplayTree(FormulaParseNode formulaparsenode) {
      super.layoutDisplayTree(formulaparsenode);
      if (this.displayAsInequality && this.symbol.equals("~") && this.getChild(0).symbol.equals("=")) {
         formulaparsenode.children = formulaparsenode.getChild(0).children;
         formulaparsenode.getChild(0).parent = formulaparsenode;
         formulaparsenode.getChild(1).parent = formulaparsenode;
         formulaparsenode.length = (
               formulaparsenode.getChild(1).offset = (formulaparsenode.getChild(0).offset = 0) + formulaparsenode.getChild(0).length + "<>".length()
            )
            + formulaparsenode.getChild(1).length;
      } else if (this.childCount == 1) {
         formulaparsenode.length = (formulaparsenode.getChild(0).offset = this.symbol.length()) + formulaparsenode.getChild(0).length;
      } else {
         formulaparsenode.length = (
               formulaparsenode.getChild(1).offset = (formulaparsenode.getChild(0).offset = 0) + formulaparsenode.getChild(0).length + this.symbol.length()
            )
            + formulaparsenode.getChild(1).length;
      }
   }

   public ConnectiveFormula markAsInequality() {
      this.displayAsInequality = true;
      return this;
   }
}
