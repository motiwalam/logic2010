package edu.ucla.phil.logic;

import java.util.Vector;

public class SimpleTerm extends Term {
   private Expression f1172;

   public SimpleTerm(String s) {
      super(s);
   }

   @Override
   void initKind() {
      this.kind = 3;
   }

   public void m1848(Expression expression) {
      this.f1172 = expression;
   }

   boolean m1849() {
      return this.f1172 != null;
   }

   Expression m1850() {
      return this.f1172;
   }

   @Override
   boolean m1236(Expression expression, C_MB c_mb) {
      if (!(expression instanceof SimpleTerm)) {
         return false;
      } else {
         Expression expression1 = ((SimpleTerm)expression).f1172;
         if (this.f1172 != null) {
            if (expression1 == null) {
               return false;
            }

            boolean flag = this.m1261();
            boolean flag1 = ((SimpleTerm)expression).m1261();
            if (flag != flag1) {
               return false;
            }

            if (flag) {
               return this.f1172.m1219(this.symbol) == expression1.m1219(expression.symbol);
            }

            if (c_mb != null) {
               return c_mb.m1091(this.f1172) == expression1;
            }
         } else if (expression1 != null) {
            return false;
         }

         return this.symbol.equals(expression.symbol);
      }
   }

   @Override
   Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, C_MB c_mb, Vector vector) {
      Expression expression1 = this.f1172 == null ? null : c_mb.m1092(expression, this.f1172, vector);
      if (schemeinstantiation != null && expression == null && expression1 == null) {
         C_PB c_pb = new C_PB(this);
         LetterReplacement letterreplacement = schemeinstantiation.m1878(c_pb);
         if (letterreplacement != null) {
            return letterreplacement.f368.instantiate(this, schemeinstantiation, c_mb, vector);
         }

         if (this.f1172 != null) {
            schemeinstantiation.m1886(c_pb);
         }
      }

      if (this.m1261() && expression != null) {
         vector.addElement(this);
         Expression expression2 = expression.getChild(this.f1172.m1219(this.symbol)).instantiate(null, schemeinstantiation, c_mb, vector);
         vector.removeElementAt(vector.size() - 1);
         return expression2;
      } else {
         SimpleTerm simpleterm1 = new SimpleTerm(this.symbol);
         simpleterm1.f1172 = expression1;
         return simpleterm1;
      }
   }

   @Override
   void m1230(String s, ExpressionPath expressionpath, Vector vector) {
      if (s != null) {
         if (this.m1262() && s.equals(this.symbol)) {
            vector.addElement(expressionpath.clone());
         }
      }
   }

   @Override
   int m1242(Vector vector, int i) {
      if (this.f1172 != null) {
         Expression expression = this.f1172.getChild(0);
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
   void m1258(C_JD c_jd) {
      this.f1172 = (Expression)c_jd.get(this.symbol);
   }

   @Override
   void m1260(C_JD c_jd, Vector vector) {
      Expression expression = (Expression)c_jd.get(this.symbol);
      if (this.f1172 != expression) {
         vector.addElement(new Expression[]{expression, this});
      }
   }

   @Override
   void m1264(Expression expression) {
      if (this.f1172 == null && expression.m1219(this.symbol) != -1) {
         this.f1172 = expression;
      }
   }

   @Override
   boolean m1265(Expression expression) {
      return this.symbol.equals(LogicProgram.m1015(this.symbol)) && (expression == null || expression.m1219(this.symbol) == -1);
   }

   @Override
   boolean m1261() {
      return this.f1172 != null && this.f1172.m1263();
   }

   @Override
   boolean m1262() {
      return this.f1172 != null && !this.f1172.m1263();
   }

   static boolean m1851(String s) {
      return m1852(s, false);
   }

   static boolean m1852(String s, boolean flag) {
      Expression expression;
      try {
         expression = LogicProgram.m1008(s, true, flag);
      } catch (FormulaParseException formulaparseexception) {
         return false;
      }

      return expression instanceof SimpleTerm;
   }

   @Override
   void m1253(Vector vector, Vector vector1) {
      if (vector != null && !vector.contains(this.symbol)) {
         vector.addElement(this.symbol);
      }

      if (!this.m1262() && vector1 != null && !vector1.contains(this.symbol)) {
         vector1.addElement(this.symbol);
      }
   }

   @Override
   void m1277(Vector vector) {
      if (!this.m1262() && !vector.contains(this.symbol)) {
         vector.addElement(this.symbol);
      }
   }

   @Override
   boolean m1268(Expression expression, Expression expression1, SchemeInstantiation schemeinstantiation, C_MB c_mb, Vector vector) {
      if (this.f1172 == null) {
         if (expression1 != null) {
            return expression == null ? schemeinstantiation.m1881(this, expression1) : super.m1268(expression, expression1, schemeinstantiation, c_mb, vector);
         } else {
            return expression != null || schemeinstantiation.m1885(this) && schemeinstantiation.m1887(this, null, c_mb, vector);
         }
      } else if (this.m1261()) {
         vector.addElement(this);
         boolean flag = expression.getChild(this.f1172.m1219(this.symbol)).m1268(null, expression1, schemeinstantiation, c_mb, vector);
         vector.removeElementAt(vector.size() - 1);
         return flag;
      } else {
         return expression1 == null || expression1 instanceof SimpleTerm && ((SimpleTerm)expression1).m1850() == c_mb.m1092(expression, this.f1172, vector);
      }
   }

   @Override
   Expression m1247(Vector vector, int i, SchemeInstantiation schemeinstantiation) {
      if (this.m1262()) {
         this.symbol = this.f1172.getChild(0).symbol;
      }

      return this;
   }

   @Override
   boolean m1252(Expression expression) {
      return this.f1172 == expression;
   }

   @Override
   void m1246(Vector vector) {
      SchematicLetter schematicletter = this.getSchematicLetter();
      if (schematicletter != null && vector.indexOf(schematicletter) == -1) {
         vector.addElement(schematicletter);
      }
   }

   @Override
   SchematicLetter getSchematicLetter() {
      return this.m1262() ? null : new C_PB(this);
   }

   @Override
   boolean m1273(SchemeInstantiation schemeinstantiation) {
      return this.m1262() ? true : schemeinstantiation.m1885(this);
   }

   @Override
   boolean m1274(SchemeInstantiation schemeinstantiation) {
      return this.m1262() || schemeinstantiation.m1878(this.getSchematicLetter()) != null;
   }

   @Override
   boolean m1210() {
      return true;
   }

   @Override
   String m1207(int i) {
      return this.symbol;
   }

   @Override
   String m1209(int i) {
      return this.symbol;
   }

   @Override
   void m1211(C_DD c_dd) {
      super.m1211(c_dd);
      c_dd.f283 = this.symbol.length();
   }
}
