package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

public abstract class Expression implements ExpressionKinds, LogicConstants {
   protected int kind;
   protected String symbol;
   protected Vector children;
   protected int childCount;
   protected boolean f742;

   Expression(String s) {
      this.symbol = s;
      this.children = new Vector();
      this.childCount = 0;
      this.f742 = false;
      this.initKind();
   }

   @Override
   public String toString() {
      return this.m1205(true, 0);
   }

   String m1205(boolean flag, int i) {
      return flag ? this.m1207(i) : this.m1209(i);
   }

   String m1206() {
      return this.m1207(-1);
   }

   abstract String m1207(int i);

   String m1208() {
      return this.m1209(1);
   }

   abstract String m1209(int i);

   boolean m1210() {
      return false;
   }

   void m1211(C_DD c_dd) {
      c_dd.f281 = this.childCount == 0 ? null : new Vector();

      for (int i = 0; i < this.childCount; i++) {
         C_DD c_dd1 = new C_DD(this.getChild(i));
         c_dd1.f277 = c_dd;
         c_dd.f281.addElement(c_dd1);
      }
   }

   abstract void initKind();

   int getKind() {
      return this.kind;
   }

   public String getSymbol() {
      return this.symbol;
   }

   void addChild(Expression expression1) {
      this.children.addElement(expression1);
      this.childCount++;
   }

   int getChildCount() {
      return this.childCount;
   }

   Expression getChild(int i) {
      return i >= 0 && i < this.childCount ? (Expression)this.children.elementAt(i) : null;
   }

   int m1218(String s, int i) {
      int j = i;

      while (j < this.childCount && !s.equals(this.getChild(j).symbol)) {
         j++;
      }

      return j == this.childCount ? -1 : j;
   }

   int m1219(String s) {
      return this.m1218(s, 0);
   }

   Expression m1220(ExpressionPath expressionpath) {
      return this.m1221(expressionpath, null);
   }

   Expression m1221(ExpressionPath expressionpath, Vector vector) {
      return expressionpath == null ? null : this.m1222(expressionpath.indexes, 0, expressionpath.depth, vector);
   }

   Expression m1222(int[] aint, int i, int j, Vector vector) {
      if (i == j) {
         return this;
      } else {
         Expression expression1 = this.getChild(aint[i]);
         return expression1 == null ? null : expression1.m1222(aint, i + 1, j, vector);
      }
   }

   Vector m1223(Expression expression1) {
      Vector vector = new Vector();
      this.m1224(expression1, new ExpressionPath(), vector);
      return vector;
   }

   void m1224(Expression expression1, ExpressionPath expressionpath, Vector vector) {
      if (this.m1235(expression1)) {
         vector.addElement(expressionpath.clone());
      } else {
         for (int i = 0; i < this.childCount; i++) {
            expressionpath.m1749(i);
            this.getChild(i).m1224(expression1, expressionpath, vector);
            expressionpath.depth--;
         }
      }
   }

   Vector m1225(SchematicLetter schematicletter) {
      Vector vector = new Vector();
      this.m1226(schematicletter, new ExpressionPath(), vector);
      return vector;
   }

   void m1226(SchematicLetter schematicletter, ExpressionPath expressionpath, Vector vector) {
      if (schematicletter != null) {
         if (schematicletter.equals(this.getSchematicLetter())) {
            vector.addElement(expressionpath.clone());
         }

         for (int i = 0; i < this.childCount; i++) {
            expressionpath.m1749(i);
            this.getChild(i).m1226(schematicletter, expressionpath, vector);
            expressionpath.depth--;
         }
      }
   }

   Vector m1227(String s) {
      Vector vector = new Vector();
      this.m1228(s, new ExpressionPath(), vector);
      return vector;
   }

   void m1228(String s, ExpressionPath expressionpath, Vector vector) {
      if (s.equals(this.symbol)) {
         vector.addElement(expressionpath.clone());
      }

      for (int i = 0; i < this.childCount; i++) {
         expressionpath.m1749(i);
         this.getChild(i).m1228(s, expressionpath, vector);
         expressionpath.depth--;
      }
   }

   Vector m1229(String s) {
      Vector vector = new Vector();
      this.m1230(s, new ExpressionPath(), vector);
      return vector;
   }

   void m1230(String s, ExpressionPath expressionpath, Vector vector) {
      if (s != null) {
         for (int i = 0; i < this.childCount; i++) {
            expressionpath.m1749(i);
            this.getChild(i).m1230(s, expressionpath, vector);
            expressionpath.depth--;
         }
      }
   }

   ExpressionPath m1231(Expression expression1) {
      return this.m1232(this.m1233(expression1));
   }

   ExpressionPath m1232(Vector vector) {
      int i = vector == null ? 0 : vector.size();
      if (i == 0) {
         return null;
      } else {
         ExpressionPath expressionpath = (ExpressionPath)vector.elementAt(0);

         for (int j = 1; j < i; j++) {
            expressionpath.depth = expressionpath.m1751((ExpressionPath)vector.elementAt(j));
         }

         return expressionpath;
      }
   }

   Vector m1233(Expression expression1) {
      Vector vector = new Vector();
      this.m1234(expression1, new ExpressionPath(), vector);
      return vector;
   }

   private void m1234(Expression expression1, ExpressionPath expressionpath, Vector vector) {
      if (expression1 != null && this.symbol.equals(expression1.symbol) && this.childCount == expression1.childCount) {
         for (int i = 0; i < this.childCount; i++) {
            expressionpath.m1749(i);
            this.getChild(i).m1234(expression1.getChild(i), expressionpath, vector);
            expressionpath.depth--;
         }
      } else {
         vector.addElement(expressionpath.clone());
      }
   }

   boolean m1235(Expression expression1) {
      if (expression1 != null && this.symbol.equals(expression1.symbol) && this.childCount == expression1.childCount) {
         for (int i = 0; i < this.childCount; i++) {
            if (!this.getChild(i).m1235(expression1.getChild(i))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   boolean m1236(Expression expression1, C_MB c_mb) {
      if (expression1 != null && this.symbol.equals(expression1.symbol) && this.childCount == expression1.childCount) {
         for (int i = 0; i < this.childCount; i++) {
            if (!this.getChild(i).m1236(expression1.getChild(i), c_mb)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   Expression copy() {
      return this.instantiate(null, null, new C_MB(), new Vector());
   }

   Expression instantiate(SchemeInstantiation schemeinstantiation) {
      return this.instantiate(null, schemeinstantiation, new C_MB(), new Vector());
   }

   Expression m1239(SchemeInstantiation schemeinstantiation, C_MB c_mb) {
      return this.instantiate(null, schemeinstantiation, c_mb, new Vector());
   }

   abstract Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, C_MB c_mb, Vector vector);

   void m1241(Vector vector) {
      if (vector != null) {
         this.m1242(vector, 0);
      }
   }

   int m1242(Vector vector, int i) {
      for (int j = 0; j < this.childCount; j++) {
         i = this.getChild(j).m1242(vector, i);
      }

      return i;
   }

   Vector m1243() {
      return this.m1244(new Vector());
   }

   Vector m1244(Vector vector) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).m1244(vector);
      }

      return vector;
   }

   Vector m1245() {
      Vector vector = new Vector();
      this.m1246(vector);
      return vector;
   }

   void m1246(Vector vector) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).m1246(vector);
      }
   }

   Expression m1247(Vector vector, int i, SchemeInstantiation schemeinstantiation) {
      for (int j = 0; j < this.childCount; j++) {
         this.children.setElementAt(this.getChild(j).m1247(vector, i, schemeinstantiation), j);
      }

      return this;
   }

   Expression m1248() {
      return this.copy().m1247(this.m1245(), 0, new SchemeInstantiation());
   }

   Expression m1249(int i, String s) {
      return this.copy().m1251(i, s, false);
   }

   Expression m1250(int i, String s) {
      return this.copy().m1251(i, s, true);
   }

   Expression m1251(int i, String s, boolean flag) {
      if (flag) {
         for (int j = 0; j < this.childCount; j++) {
            this.children.setElementAt(this.getChild(j).m1251(i, s, true), j);
         }
      }

      return this;
   }

   boolean m1252(Expression expression1) {
      for (int i = 0; i < this.childCount; i++) {
         if (this.getChild(i).m1252(expression1)) {
            return true;
         }
      }

      return false;
   }

   void m1253(Vector vector, Vector vector1) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).m1253(vector, vector1);
      }
   }

   C_L m1254() {
      Vector vector = this.m1243();
      int i = vector.size();
      C_L c_l = new C_L();

      for (int j = 0; j < i; j++) {
         c_l.addElement(((Expression)vector.elementAt(j)).getChild(0).getSymbol());
      }

      return c_l;
   }

   boolean m1255(Expression expression1) {
      return this.kind == 2 && this.symbol.equals("~") && this.getChild(0).m1235(expression1);
   }

   public ConnectiveFormula negate() {
      ConnectiveFormula connectiveformula = new ConnectiveFormula("~");
      connectiveformula.addChild(this);
      return connectiveformula;
   }

   Expression m1257() {
      this.m1258(new C_JD());
      return this;
   }

   void m1258(C_JD c_jd) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).m1258(c_jd);
      }
   }

   Vector m1259() {
      Vector vector = new Vector();
      this.m1260(new C_JD(), vector);
      return vector.size() == 0 ? null : vector;
   }

   void m1260(C_JD c_jd, Vector vector) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).m1260(c_jd, vector);
      }
   }

   boolean m1261() {
      return false;
   }

   boolean m1262() {
      return false;
   }

   boolean m1263() {
      return false;
   }

   void m1264(Expression expression1) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).m1264(expression1);
      }
   }

   boolean m1265(Expression expression1) {
      for (int i = 0; i < this.childCount; i++) {
         if (this.getChild(i).m1265(expression1)) {
            return true;
         }
      }

      return false;
   }

   boolean m1266(Expression expression1, SchemeInstantiation schemeinstantiation) {
      return this.m1268(null, expression1, schemeinstantiation, new C_MB(), new Vector());
   }

   boolean m1267(Expression expression1, SchemeInstantiation schemeinstantiation, C_MB c_mb) {
      return this.m1268(null, expression1, schemeinstantiation, c_mb, new Vector());
   }

   boolean m1268(Expression expression1, Expression expression2, SchemeInstantiation schemeinstantiation, C_MB c_mb, Vector vector) {
      if (expression2 == null || this.kind == expression2.kind && this.symbol.equals(expression2.symbol) && this.childCount == expression2.childCount) {
         for (int i = 0; i < this.childCount; i++) {
            if (!this.getChild(i).m1268(expression1, expression2 == null ? null : expression2.getChild(i), schemeinstantiation, c_mb, vector)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   boolean m1269(Expression expression1, SchemeInstantiation schemeinstantiation, C_MB c_mb, Vector vector) {
      SchematicLetter schematicletter = this.getSchematicLetter();
      LetterReplacement letterreplacement;
      if ((letterreplacement = schemeinstantiation.m1878(schematicletter)) != null) {
         return letterreplacement.f368.m1268(this, expression1, schemeinstantiation, c_mb, vector);
      } else {
         if (expression1 != null) {
            letterreplacement = this.m1271(expression1, c_mb, vector);
            if (letterreplacement.f369 == null) {
               return schemeinstantiation.m1880(schematicletter, letterreplacement);
            }

            Hashtable hashtable = letterreplacement.f369.f428;
            if (hashtable == null || hashtable.get("addMissingKey") == null) {
               schemeinstantiation.f1190 = letterreplacement.f369.f427;
               schemeinstantiation.f1191 = hashtable;
               return false;
            }
         }

         ErrorRef errorref = this.m1270(expression1);
         if (errorref != null) {
            schemeinstantiation.f1190 = errorref.f427;
            schemeinstantiation.f1191 = errorref.f428;
            return false;
         } else {
            return this.m1273(schemeinstantiation) && schemeinstantiation.m1887(this, expression1, c_mb, vector);
         }
      }
   }

   ErrorRef m1270(Expression expression) {
      return null;
   }

   LetterReplacement m1271(Expression expression1, C_MB c_mb, Vector vector) {
      SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
      SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation();
      Hashtable hashtable = Message.params("pattern", "\\l" + this + "\\l", "replacement", "\\l" + expression1 + "\\l");

      for (int i = 0; i < this.childCount; i++) {
         Expression expression2;
         if (!(expression2 = this.getChild(i)).m1262()) {
            Message.putParam(hashtable, "n", i + 1 + "");
            hashtable.put("addMissingKey", "");
            return new LetterReplacement(new ErrorRef("dererr065", hashtable));
         }

         Expression expression3 = ((SimpleTerm)expression2).m1850();
         Expression expression4 = c_mb.m1092(null, expression3, vector).getChild(0);
         SimpleTerm simpleterm = new SimpleTerm(SchematicLetter.m1853(i));
         if (!schemeinstantiation.m1881(new SimpleTerm(expression2.symbol), simpleterm)) {
            Message.putParam(hashtable, "n", i + 1 + "");
            hashtable.put("addMissingKey", "");
            return new LetterReplacement(new ErrorRef("dererr066", hashtable));
         }

         schemeinstantiation1.m1881(new SimpleTerm(expression4.symbol), simpleterm);
      }

      Expression expression5 = this.instantiate(schemeinstantiation);
      Expression expression6 = expression1.instantiate(schemeinstantiation1);
      LetterReplacement letterreplacement = new LetterReplacement(expression5, expression6);
      if (letterreplacement.f369 == null) {
         Message.putParam(hashtable, "dummy pattern", expression5 + "");
         Message.putParam(hashtable, "dummy replacement", expression6 + "");
         if (!schemeinstantiation1.m1889().isEmpty()) {
            letterreplacement.f369 = new ErrorRef("dererr061");
         } else if (expression6.m1259() != null) {
            letterreplacement.f369 = new ErrorRef("dererr061");
         }
      }

      return letterreplacement;
   }

   SchematicLetter getSchematicLetter() {
      return null;
   }

   boolean m1273(SchemeInstantiation schemeinstantiation) {
      boolean flag = true;

      for (int i = 0; i < this.childCount; i++) {
         flag &= this.getChild(i).m1273(schemeinstantiation);
      }

      return flag;
   }

   boolean m1274(SchemeInstantiation schemeinstantiation) {
      for (int i = 0; i < this.childCount; i++) {
         if (!this.getChild(i).m1274(schemeinstantiation)) {
            return false;
         }
      }

      return true;
   }

   Expression universalClosure() {
      return null;
   }

   Vector getFreeVariables() {
      Vector vector = new Vector();
      this.m1277(vector);
      return vector;
   }

   void m1277(Vector vector) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).m1277(vector);
      }
   }
}
