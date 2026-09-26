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

   public void m2087(Term term) {
      this.children.addElement(term);
      this.childCount++;
   }

   public void m2088(Term term) {
      this.children.addElement(term);
      this.childCount++;
   }

   @Override
   Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, C_MB c_mb, Vector vector) {
      MembershipFormula membershipformula1 = new MembershipFormula(this.symbol);

      for (int i = 0; i < this.childCount; i++) {
         membershipformula1.addChild(this.getChild(i).instantiate(expression, schemeinstantiation, c_mb, vector));
      }

      return membershipformula1;
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
