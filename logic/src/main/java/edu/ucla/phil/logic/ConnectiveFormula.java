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

   public void m2040(Formula formula) {
      this.children.addElement(formula);
      this.childCount++;
   }

   public void m2041(Formula formula) {
      this.children.addElement(formula);
      this.childCount++;
   }

   Formula m2042() {
      return (Formula)this.getChild(0);
   }

   Formula m2043() {
      return (Formula)this.getChild(1);
   }

   @Override
   Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, C_MB c_mb, Vector vector) {
      ConnectiveFormula connectiveformula1 = new ConnectiveFormula(this.symbol);
      connectiveformula1.f742 = this.f742;

      for (int i = 0; i < this.childCount; i++) {
         connectiveformula1.addChild(this.getChild(i).instantiate(expression, schemeinstantiation, c_mb, vector));
      }

      return connectiveformula1;
   }

   String m2044(Formula formula, boolean flag, int i) {
      String s = formula.m1207(i);
      if (!(formula instanceof ConnectiveFormula) || formula.childCount == 1) {
         return s;
      } else if (this.childCount != 1 && !this.symbol.equals("&") && !this.symbol.equals("|")) {
         return !formula.symbol.equals("->") && !formula.symbol.equals("<->") ? s : "(" + s + ")";
      } else {
         return !flag && this.symbol.equals(formula.symbol) ? s : "(" + s + ")";
      }
   }

   @Override
   String m1207(int i) {
      Formula formula = (Formula)this.getChild(0);
      if ((i == -1 || i == 0 && this.f742) && this.symbol.equals("~") && formula.symbol.equals("=")) {
         return formula.getChild(0).m1207(i) + "<>" + formula.getChild(1).m1207(i);
      } else if (this.childCount == 1) {
         return this.symbol + this.m2044(formula, true, i);
      } else {
         Formula formula1 = (Formula)this.getChild(1);
         return this.m2044(formula, false, i) + this.symbol + this.m2044(formula1, true, i);
      }
   }

   @Override
   String m1209(int i) {
      Formula formula = (Formula)this.getChild(0);
      if ((i == -1 || i == 0 && this.f742) && this.symbol.equals("~") && formula.symbol.equals("=")) {
         return formula.getChild(0).m1209(i) + "<>" + formula.getChild(1).m1209(i);
      } else if (this.childCount == 1) {
         return this.symbol + formula.m1209(i);
      } else {
         Formula formula1 = (Formula)this.getChild(1);
         return "(" + formula.m1209(i) + this.symbol + formula1.m1209(i) + ")";
      }
   }

   @Override
   void m1211(C_DD c_dd) {
      super.m1211(c_dd);
      if (this.f742 && this.symbol.equals("~") && this.getChild(0).symbol.equals("=")) {
         c_dd.f281 = c_dd.m458(0).f281;
         c_dd.m458(0).f277 = c_dd;
         c_dd.m458(1).f277 = c_dd;
         c_dd.f283 = (c_dd.m458(1).f282 = (c_dd.m458(0).f282 = 0) + c_dd.m458(0).f283 + "<>".length()) + c_dd.m458(1).f283;
      } else if (this.childCount == 1) {
         c_dd.f283 = (c_dd.m458(0).f282 = this.symbol.length()) + c_dd.m458(0).f283;
      } else {
         c_dd.f283 = (c_dd.m458(1).f282 = (c_dd.m458(0).f282 = 0) + c_dd.m458(0).f283 + this.symbol.length()) + c_dd.m458(1).f283;
      }
   }

   public ConnectiveFormula m2045() {
      this.f742 = true;
      return this;
   }
}
