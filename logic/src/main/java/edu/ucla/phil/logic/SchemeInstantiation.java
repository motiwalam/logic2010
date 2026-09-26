package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class SchemeInstantiation extends Hashtable implements ExpressionKinds {
   Vector f1189;
   String f1190 = null;
   Hashtable f1191 = null;

   SchemeInstantiation() {
      this.f1189 = new Vector();
   }

   SchemeInstantiation(SchemeInstantiation schemeinstantiation1) {
      this();
      this.m1877(schemeinstantiation1);
   }

   @Override
   public Object clone() {
      SchemeInstantiation schemeinstantiation1 = (SchemeInstantiation)super.clone();
      schemeinstantiation1.f1189 = (Vector)this.f1189.clone();
      schemeinstantiation1.f1190 = null;
      schemeinstantiation1.f1191 = null;
      return schemeinstantiation1;
   }

   boolean m1877(SchemeInstantiation schemeinstantiation1) {
      String s = null;
      Hashtable hashtable = null;
      if (schemeinstantiation1 != null) {
         Enumeration enumeration = schemeinstantiation1.keys();

         while (enumeration.hasMoreElements()) {
            SchematicLetter schematicletter = (SchematicLetter)enumeration.nextElement();
            LetterReplacement letterreplacement = schemeinstantiation1.m1878(schematicletter);
            if (!this.m1880(schematicletter, letterreplacement) && s == null) {
               s = this.f1190;
               hashtable = this.f1191;
            }
         }

         enumeration = schemeinstantiation1.f1189.elements();

         while (enumeration.hasMoreElements()) {
            SchematicLetter schematicletter1 = (SchematicLetter)enumeration.nextElement();
            if (!this.m1879(schematicletter1.m1176(false)) && s == null) {
               s = this.f1190;
               hashtable = this.f1191;
            }

            this.m1886(schematicletter1);
         }
      }

      this.f1190 = s;
      this.f1191 = hashtable;
      return this.f1190 == null;
   }

   @Override
   public void clear() {
      super.clear();
      this.f1189.setSize(0);
      this.f1190 = null;
      this.f1191 = null;
   }

   LetterReplacement m1878(SchematicLetter schematicletter) {
      return (LetterReplacement)this.get(schematicletter);
   }

   boolean m1879(Vector vector) {
      if (vector == null) {
         return true;
      } else {
         Enumeration enumeration = vector.elements();

         while (enumeration.hasMoreElements()) {
            C_m_B c_m_b = (C_m_B)enumeration.nextElement();
            if (!c_m_b.f1276.m1268(null, c_m_b.f1277, this, c_m_b.f1278, C_o_D.m2008(c_m_b.f1279))) {
               this.f1190 = "dererr062";
               this.f1191 = Message.params("pattern", "\\l" + c_m_b.f1276 + "\\l", "instance", "\\l" + c_m_b.f1277 + "\\l");
               return false;
            }
         }

         return true;
      }
   }

   boolean m1880(SchematicLetter schematicletter, LetterReplacement letterreplacement) {
      LetterReplacement letterreplacement1 = this.m1878(schematicletter);
      if (letterreplacement1 == null) {
         Vector vector = null;
         int i;
         if ((i = this.f1189.indexOf(schematicletter)) != -1) {
            vector = ((SchematicLetter)this.f1189.elementAt(i)).m1176(false);
            this.f1189.removeElementAt(i);
         }

         this.put(schematicletter, letterreplacement);
         if (!this.m1879(vector)) {
            return false;
         }
      } else if (!letterreplacement1.m656(letterreplacement)) {
         this.f1190 = "dererr073";
         this.f1191 = Message.params(
            "pattern",
            "\\l" + letterreplacement.f367 + "\\l",
            "new replacement",
            "\\l" + letterreplacement.f368 + "\\l",
            "old replacement",
            "\\l" + letterreplacement1.f368 + "\\l"
         );
         return false;
      }

      return true;
   }

   boolean m1881(Expression expression, Expression expression1) {
      LetterReplacement letterreplacement = new LetterReplacement(expression, expression1);
      if (letterreplacement.f369 != null) {
         this.f1190 = letterreplacement.f369.f427;
         this.f1191 = letterreplacement.f369.f428;
         return false;
      } else {
         return this.m1880(expression.getSchematicLetter(), letterreplacement);
      }
   }

   boolean m1882(String s, String s1) {
      String s2 = null;

      Expression expression;
      Expression expression1;
      try {
         s2 = s;
         expression = LogicProgram.m1008(s, true, true);
         s2 = s1;
         expression1 = LogicProgram.m1008(s1, true, true);
      } catch (FormulaParseException formulaparseexception) {
         this.f1190 = "dererr059";
         this.f1191 = Message.params("parser error", s2);
         return false;
      }

      return this.m1881(expression, expression1);
   }

   boolean m1883(String s) {
      int i = s.indexOf(":");
      if (i == -1) {
         this.f1190 = "dererr059";
         this.f1191 = Message.params("parser error", "scheme map needs pattern:replacement");
         return false;
      } else {
         return this.m1882(s.substring(0, i), s.substring(i + 1));
      }
   }

   static ErrorRef m1884(Expression expression, Expression expression1) {
      Hashtable hashtable = Message.params("pattern", "\\l" + expression + "\\l", "replacement", "\\l" + expression1 + "\\l");
      if (expression.kind != 0 && expression.kind != 4) {
         if (expression instanceof SimpleTerm && ((SimpleTerm)expression).m1262()) {
            return new ErrorRef("dererr067", hashtable);
         }
      } else {
         for (int i = 0; i < expression.childCount; i++) {
            Expression expression2 = expression.getChild(i);
            if (!(expression2 instanceof SimpleTerm) || ((SimpleTerm)expression2).m1262()) {
               return new ErrorRef("dererr094", Message.putParam(hashtable, "n", i + 1 + ""));
            }
         }
      }

      switch (expression.kind) {
         case 0:
            return expression1 instanceof Formula ? null : new ErrorRef("dererr068", hashtable);
         case 1:
         case 2:
         default:
            return new ErrorRef("dererr071", hashtable);
         case 3:
         case 4:
            if (expression1 instanceof SimpleTerm && ((SimpleTerm)expression1).m1262()) {
               return new ErrorRef("dererr069", hashtable);
            } else {
               return expression1 instanceof Term ? null : new ErrorRef("dererr070", hashtable);
            }
      }
   }

   boolean m1885(Expression expression) {
      SchematicLetter schematicletter = expression.getSchematicLetter();
      if (schematicletter != null) {
         this.m1886(schematicletter);
         return true;
      } else {
         if (expression instanceof SimpleTerm && ((SimpleTerm)expression).m1262()) {
            this.f1190 = "dererr067";
            this.f1191 = Message.params("pattern", "\\l" + expression + "\\l");
         } else {
            this.f1190 = "dererr071";
            this.f1191 = Message.params("pattern", "\\l" + expression + "\\l");
         }

         return false;
      }
   }

   void m1886(SchematicLetter schematicletter) {
      if (schematicletter != null && !this.containsKey(schematicletter)) {
         int i = this.f1189.indexOf(schematicletter);
         if (i == -1) {
            this.f1189.addElement(schematicletter);
         } else {
            Vector vector;
            if ((vector = schematicletter.m1176(false)) != null) {
               SchematicLetter schematicletter1 = (SchematicLetter)this.f1189.elementAt(i);
               Vector vector1 = schematicletter1.m1176(true);
               Enumeration enumeration = vector.elements();

               while (enumeration.hasMoreElements()) {
                  C_m_B c_m_b = (C_m_B)enumeration.nextElement();
                  if (!vector1.contains(c_m_b)) {
                     vector1.addElement(c_m_b);
                  }
               }
            }
         }
      }
   }

   boolean m1887(Expression expression, Expression expression1, C_MB c_mb, Vector vector) {
      return this.m1888(new C_m_B(expression, expression1, c_mb, vector));
   }

   boolean m1888(C_m_B c_m_b) {
      SchematicLetter schematicletter = c_m_b.f1276.getSchematicLetter();
      if (schematicletter == null) {
         return false;
      } else {
         int i = this.f1189.indexOf(schematicletter);
         if (i != -1) {
            Vector vector = ((SchematicLetter)this.f1189.elementAt(i)).m1176(true);
            if (!vector.contains(c_m_b)) {
               vector.addElement(c_m_b);
            }
         }

         return true;
      }
   }

   Vector m1889() {
      return this.f1189;
   }

   boolean m1890() {
      Enumeration enumeration = this.f1189.elements();

      while (enumeration.hasMoreElements()) {
         if (((SchematicLetter)enumeration.nextElement()).m1176(false) != null) {
            return false;
         }
      }

      return true;
   }

   String m1891() {
      if (this.f1189 == null) {
         return "null";
      } else {
         Object object = "";
         Enumeration enumeration = this.f1189.elements();

         while (enumeration.hasMoreElements()) {
            SchematicLetter schematicletter = (SchematicLetter)enumeration.nextElement();
            object = object + (object.equals("") ? "" : ".") + schematicletter;
            Vector vector = schematicletter.m1176(false);
            if (vector != null && !vector.isEmpty()) {
               object = object + vector;
            }
         }

         return (String)object;
      }
   }

   Vector m1892(Expression expression) {
      SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation();
      if (expression != null) {
         expression.m1273(schemeinstantiation1);
      }

      Enumeration enumeration = this.elements();

      while (enumeration.hasMoreElements()) {
         LetterReplacement letterreplacement = (LetterReplacement)enumeration.nextElement();
         letterreplacement.f368.m1273(schemeinstantiation1);
      }

      return schemeinstantiation1.f1189;
   }

   SchemeInstantiation m1893(Expression expression) {
      Vector vector = this.m1892(expression);
      int i = this.f1189.size();
      SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation();

      for (int j = 0; j < i; j++) {
         SchematicLetter schematicletter = (SchematicLetter)this.f1189.elementAt(0);
         if (schematicletter.m1176(false) != null) {
            SchematicLetter schematicletter1 = schematicletter.m1177(vector);
            schemeinstantiation1.m1886(schematicletter1);
            this.m1881(schematicletter.m1175(), schematicletter1.m1175());
         }
      }

      return schemeinstantiation1;
   }

   String m1894() {
      return this.f1190;
   }

   Hashtable m1895() {
      return this.f1191;
   }

   String m1896() {
      String s = "";
      boolean flag = false;

      for (Enumeration enumeration = this.keys(); enumeration.hasMoreElements(); flag = true) {
         SchematicLetter schematicletter = (SchematicLetter)enumeration.nextElement();
         s = s + (flag ? "." : "") + this.m1878(schematicletter);
      }

      return s;
   }

   static SchemeInstantiation m1897(String s) {
      SchemeInstantiation schemeinstantiation = new SchemeInstantiation();

      while (!s.equals("")) {
         String s1;
         int i;
         if ((i = s.indexOf(".")) == -1) {
            s1 = s;
            s = "";
         } else {
            s1 = s.substring(0, i);
            s = s.substring(i + 1);
         }

         if (!schemeinstantiation.m1883(s1)) {
            return null;
         }
      }

      return schemeinstantiation;
   }
}
