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

   public void m1991(SimpleTerm simpleterm) {
      this.children.addElement(simpleterm);
      this.childCount++;
   }

   public void m1992(Formula formula) {
      this.children.addElement(formula);
      this.childCount++;
   }

   SimpleTerm m1993() {
      return (SimpleTerm)this.getChild(0);
   }

   Formula m1994() {
      return (Formula)this.getChild(1);
   }

   @Override
   Expression m1222(int[] aint, int i, int j, Vector vector) {
      if (i == j) {
         return this;
      } else {
         if (vector != null) {
            vector.addElement(this);
         }

         Expression expression = this.getChild(aint[i]);
         return expression == null ? null : expression.m1222(aint, i + 1, j, vector);
      }
   }

   @Override
   boolean m1236(Expression expression, C_MB c_mb) {
      if (expression == null) {
         return false;
      } else if (this.kind == expression.kind && this.symbol.equals(expression.symbol) && this.childCount == expression.childCount) {
         if (c_mb != null) {
            c_mb.m1093(this, expression);
         }

         for (int i = 0; i < this.childCount; i++) {
            if (!this.getChild(i).m1236(expression.getChild(i), c_mb)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, C_MB c_mb, Vector vector) {
      QuantifiedFormula quantifiedformula1 = new QuantifiedFormula(this.symbol);
      c_mb.m1094(expression, this, vector, quantifiedformula1);

      for (int i = 0; i < this.childCount; i++) {
         quantifiedformula1.addChild(this.getChild(i).instantiate(expression, schemeinstantiation, c_mb, vector));
      }

      return quantifiedformula1;
   }

   @Override
   Expression m1247(Vector vector, int i, SchemeInstantiation schemeinstantiation) {
      if (!this.getChild(1).m1252(this)) {
         return this.getChild(1).m1247(vector, i, schemeinstantiation);
      } else {
         ((SimpleTerm)this.getChild(0)).symbol = SchematicLetter.m1853(i);
         super.m1247(vector, i + 1, schemeinstantiation);
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
            if (schematicletter1 instanceof C_w_C) {
               LetterReplacement letterreplacement = schemeinstantiation.m1878(schematicletter1);
               if (C_HA.m681(expression, letterreplacement.f368.getChild(1).copy())) {
                  schematicletter = schematicletter1;
                  break;
               }
            }
         }

         if (schematicletter == null) {
            schematicletter = C_w_C.m2157(i, vector);
            if (!schemeinstantiation.m1881(schematicletter.m1175(), this)) {
               throw new RuntimeException("could not predicate a quantifier");
            }
         }

         return (Expression)(flag ? schematicletter.m1175().negate() : schematicletter.m1175());
      }
   }

   @Override
   Expression m1251(int i, String s, boolean flag) {
      super.m1251(i, s, flag);
      QuantifiedFormula quantifiedformula1 = new QuantifiedFormula(this.symbol);
      quantifiedformula1.m1991(new SimpleTerm(LogicProgram.m1020(0)));
      AtomicFormula atomicformula = new AtomicFormula(LogicProgram.m1017(0));
      atomicformula.m2028(new SimpleTerm(LogicProgram.m1020(0)));
      quantifiedformula1.m1992(atomicformula);
      quantifiedformula1.m1257();
      atomicformula = new AtomicFormula(LogicProgram.m1017(0));
      atomicformula.m2028(new OperationTerm(LogicProgram.m1018(0)));
      String s1 = this.symbol.equals("!") ? "|" : "&";
      SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
      quantifiedformula1.m1266(this.copy(), schemeinstantiation);
      schemeinstantiation.m1882(LogicProgram.m1018(0), s + 0);
      LetterReplacement letterreplacement = schemeinstantiation.m1878(new C_W(LogicProgram.m1018(0), 0));
      Object object = atomicformula.instantiate(schemeinstantiation);

      for (int j = 1; j < i; j++) {
         letterreplacement.f368.symbol = s + j;
         Object object1 = object;
         Expression expression = atomicformula.instantiate(schemeinstantiation);
         object = new ConnectiveFormula(s1);
         ((ConnectiveFormula)object).m2040((Formula)object1);
         ((ConnectiveFormula)object).m2041((Formula)expression);
      }

      ((Expression)object).m1257();
      return (Expression)object;
   }

   @Override
   Vector m1244(Vector vector) {
      vector.addElement(this);
      return super.m1244(vector);
   }

   @Override
   void m1258(C_JD c_jd) {
      c_jd.m723(this.getChild(0).symbol, this);
      super.m1258(c_jd);
      c_jd.m724(this.getChild(0).symbol);
   }

   @Override
   void m1260(C_JD c_jd, Vector vector) {
      c_jd.m723(this.getChild(0).symbol, this);
      super.m1260(c_jd, vector);
      c_jd.m724(this.getChild(0).symbol);
   }

   @Override
   boolean m1268(Expression expression, Expression expression1, SchemeInstantiation schemeinstantiation, C_MB c_mb, Vector vector) {
      if (expression1 != null) {
         if (this.kind != expression1.kind || !this.symbol.equals(expression1.symbol) || this.childCount != expression1.childCount) {
            return false;
         }

         c_mb.m1094(expression, this, vector, expression1);
      }

      for (int i = 0; i < this.childCount; i++) {
         if (!this.getChild(i).m1268(expression, expression1 == null ? null : expression1.getChild(i), schemeinstantiation, c_mb, vector)) {
            return false;
         }
      }

      return true;
   }

   String m1995(Expression expression, int i) {
      return expression instanceof ConnectiveFormula && expression.childCount > 1 ? "(" + expression.m1207(i) + ")" : expression.m1207(i);
   }

   @Override
   String m1207(int i) {
      return this.symbol + this.getChild(0) + this.m1995(this.getChild(1), i);
   }

   @Override
   String m1209(int i) {
      return this.symbol + this.getChild(0) + this.getChild(1).m1209(i);
   }

   @Override
   void m1211(C_DD c_dd) {
      super.m1211(c_dd);
      c_dd.f283 = (c_dd.m458(1).f282 = (c_dd.m458(0).f282 = this.symbol.length()) + c_dd.m458(0).f283) + c_dd.m458(1).f283;
   }
}
