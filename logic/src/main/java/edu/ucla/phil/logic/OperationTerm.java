package edu.ucla.phil.logic;

import java.util.Vector;

public class OperationTerm extends Term {
   public OperationTerm(String s) {
      super(s);
   }

   @Override
   void initKind() {
      this.kind = 4;
   }

   public void m1963(Term term) {
      this.children.addElement(term);
      this.childCount++;
   }

   @Override
   Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, C_MB c_mb, Vector vector) {
      if (expression == null && schemeinstantiation != null) {
         LetterReplacement letterreplacement = schemeinstantiation.m1878(this.getSchematicLetter());
         if (letterreplacement != null) {
            return letterreplacement.f368.instantiate(this, schemeinstantiation, c_mb, vector);
         }
      }

      OperationTerm operationterm1 = new OperationTerm(this.symbol);

      for (int i = 0; i < this.childCount; i++) {
         operationterm1.addChild(this.getChild(i).instantiate(expression, schemeinstantiation, c_mb, vector));
      }

      return operationterm1;
   }

   @Override
   boolean m1268(Expression expression, Expression expression1, SchemeInstantiation schemeinstantiation, C_MB c_mb, Vector vector) {
      return expression != null
         ? super.m1268(expression, expression1, schemeinstantiation, c_mb, vector)
         : this.m1269(expression1, schemeinstantiation, c_mb, vector);
   }

   @Override
   ErrorRef m1270(Expression expression) {
      return expression != null && (!(expression instanceof Term) || expression.m1262())
         ? new ErrorRef("dererr070", Message.params("pattern", "\\l" + this + "\\l", "replacement", "\\l" + expression + "\\l"))
         : null;
   }

   @Override
   void m1246(Vector vector) {
      SchematicLetter schematicletter = this.getSchematicLetter();
      if (vector.indexOf(schematicletter) == -1) {
         vector.addElement(schematicletter);
      }

      super.m1246(vector);
   }

   @Override
   SchematicLetter getSchematicLetter() {
      return new C_W(this);
   }

   @Override
   boolean m1273(SchemeInstantiation schemeinstantiation) {
      return schemeinstantiation.m1885(this) & super.m1273(schemeinstantiation);
   }

   @Override
   boolean m1274(SchemeInstantiation schemeinstantiation) {
      return schemeinstantiation.m1878(this.getSchematicLetter()) == null ? false : super.m1274(schemeinstantiation);
   }

   @Override
   boolean m1263() {
      return true;
   }

   @Override
   boolean m1210() {
      return true;
   }

   @Override
   String m1207(int i) {
      String s = this.symbol;
      boolean flag = this.childCount > 1 || this.childCount > 0 && this.m1210();
      if (flag) {
         s = s + "(";
      }

      for (int j = 0; j < this.childCount; j++) {
         s = s + this.getChild(j).m1207(i);
      }

      if (flag) {
         s = s + ")";
      }

      return s;
   }

   @Override
   String m1209(int i) {
      String s = this.symbol;
      boolean flag = this.childCount > 1 || this.childCount > 0 && this.m1210();
      if (flag) {
         s = s + "(";
      }

      for (int j = 0; j < this.childCount; j++) {
         s = s + this.getChild(j).m1209(i);
      }

      if (flag) {
         s = s + ")";
      }

      return s;
   }

   @Override
   void m1211(C_DD c_dd) {
      super.m1211(c_dd);
      c_dd.f283 = this.symbol.length();
      int i = c_dd.m457();

      for (int j = 0; j < i; j++) {
         C_DD c_dd1 = c_dd.m458(j);
         c_dd1.f282 = c_dd.f283;
         c_dd.f283 = c_dd.f283 + c_dd1.f283;
      }
   }
}
