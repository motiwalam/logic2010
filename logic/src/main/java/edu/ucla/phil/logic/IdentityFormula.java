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

   public void m2176(Term term) {
      this.children.addElement(term);
      this.childCount++;
   }

   public void m2177(Term term) {
      this.children.addElement(term);
      this.childCount++;
   }

   @Override
   Expression m1247(Vector vector, int i, SchemeInstantiation schemeinstantiation) {
      super.m1247(vector, i, schemeinstantiation);
      Expression expression = this.getChild(0);
      Expression expression1 = this.getChild(1);
      if (expression.m1206().compareTo(expression1.m1206()) > 0) {
         this.children.setElementAt(expression, 1);
         this.children.setElementAt(expression1, 0);
      }

      return this;
   }

   @Override
   Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, C_MB c_mb, Vector vector) {
      IdentityFormula identityformula1 = new IdentityFormula(this.symbol);

      for (int i = 0; i < this.childCount; i++) {
         identityformula1.addChild(this.getChild(i).instantiate(expression, schemeinstantiation, c_mb, vector));
      }

      return identityformula1;
   }

   @Override
   String m1207(int i) {
      return this.getChild(0).m1207(i) + this.symbol + this.getChild(1).m1207(i);
   }

   @Override
   String m1209(int i) {
      return this.getChild(0).m1209(i) + this.symbol + this.getChild(1).m1209(i);
   }

   @Override
   void m1211(C_DD c_dd) {
      super.m1211(c_dd);
      c_dd.f283 = (c_dd.m458(1).f282 = (c_dd.m458(0).f282 = 0) + c_dd.m458(0).f283 + this.symbol.length()) + c_dd.m458(1).f283;
   }
}
