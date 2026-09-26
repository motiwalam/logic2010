package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Vector;

public class DescriptionTerm extends Term {
   private SimpleTerm f862;
   private Formula f863;

   public DescriptionTerm(String s) {
      super(s);
   }

   @Override
   void initKind() {
      this.kind = 5;
   }

   public void m1455(SimpleTerm simpleterm) {
      this.children.addElement(simpleterm);
      this.childCount++;
   }

   public void m1456(Formula formula) {
      this.children.addElement(formula);
      this.childCount++;
   }

   SimpleTerm m1457() {
      return (SimpleTerm)this.getChild(0);
   }

   Formula m1458() {
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
      DescriptionTerm descriptionterm1 = new DescriptionTerm(this.symbol);
      c_mb.m1094(expression, this, vector, descriptionterm1);

      for (int i = 0; i < this.childCount; i++) {
         descriptionterm1.addChild(this.getChild(i).instantiate(expression, schemeinstantiation, c_mb, vector));
      }

      return descriptionterm1;
   }

   @Override
   Expression m1247(Vector vector, int i, SchemeInstantiation schemeinstantiation) {
      if (!this.getChild(1).m1252(this)) {
         return this.getChild(1).m1247(vector, i, schemeinstantiation);
      } else {
         ((SimpleTerm)this.getChild(0)).symbol = SchematicLetter.m1853(i);
         super.m1247(vector, i + 1, schemeinstantiation);
         Enumeration enumeration = schemeinstantiation.keys();
         SchematicLetter schematicletter = null;
         Expression expression = this.getChild(1).copy();

         while (enumeration.hasMoreElements()) {
            SchematicLetter schematicletter1 = (SchematicLetter)enumeration.nextElement();
            if (schematicletter1 instanceof C_W) {
               LetterReplacement letterreplacement = schemeinstantiation.m1878(schematicletter1);
               if (C_HA.m681(expression, letterreplacement.f368.getChild(1).copy())) {
                  schematicletter = schematicletter1;
                  break;
               }
            }
         }

         if (schematicletter == null) {
            schematicletter = C_W.m1423(i, vector);
            if (!schemeinstantiation.m1881(schematicletter.m1175(), this)) {
               throw new RuntimeException("could not terminate a descriptive");
            }
         }

         return schematicletter.m1175();
      }
   }

   @Override
   Expression m1251(int i, String s, boolean flag) {
      if (flag) {
         super.m1251(i, s, true).m1257();
      }

      return this;
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

   String m1459(Expression expression, int i) {
      return expression instanceof ConnectiveFormula && expression.childCount > 1 ? "(" + expression.m1207(i) + ")" : expression.m1207(i);
   }

   @Override
   String m1207(int i) {
      return this.symbol + this.getChild(0) + this.m1459(this.getChild(1), i);
   }

   @Override
   String m1209(int i) {
      return this.symbol + this.getChild(0) + ((Formula)this.getChild(1)).m1209(i);
   }

   @Override
   void m1211(C_DD c_dd) {
      super.m1211(c_dd);
      c_dd.f283 = (c_dd.m458(1).f282 = (c_dd.m458(0).f282 = this.symbol.length()) + c_dd.m458(0).f283) + c_dd.m458(1).f283;
   }
}
