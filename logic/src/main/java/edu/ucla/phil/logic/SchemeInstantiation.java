package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class SchemeInstantiation extends Hashtable implements ExpressionKinds {
   Vector pendingLetters;
   String errorId = null;
   Hashtable errorParams = null;

   SchemeInstantiation() {
      this.pendingLetters = new Vector();
   }

   SchemeInstantiation(SchemeInstantiation schemeinstantiation1) {
      this();
      this.mergeFrom(schemeinstantiation1);
   }

   @Override
   public Object clone() {
      SchemeInstantiation schemeinstantiation1 = (SchemeInstantiation)super.clone();
      schemeinstantiation1.pendingLetters = (Vector)this.pendingLetters.clone();
      schemeinstantiation1.errorId = null;
      schemeinstantiation1.errorParams = null;
      return schemeinstantiation1;
   }

   boolean mergeFrom(SchemeInstantiation schemeinstantiation1) {
      String s = null;
      Hashtable hashtable = null;
      if (schemeinstantiation1 != null) {
         Enumeration enumeration = schemeinstantiation1.keys();

         while (enumeration.hasMoreElements()) {
            SchematicLetter schematicletter = (SchematicLetter)enumeration.nextElement();
            LetterReplacement letterreplacement = schemeinstantiation1.getReplacement(schematicletter);
            if (!this.putReplacement(schematicletter, letterreplacement) && s == null) {
               s = this.errorId;
               hashtable = this.errorParams;
            }
         }

         enumeration = schemeinstantiation1.pendingLetters.elements();

         while (enumeration.hasMoreElements()) {
            SchematicLetter schematicletter1 = (SchematicLetter)enumeration.nextElement();
            if (!this.checkDeferredMatches(schematicletter1.getDeferredMatches(false)) && s == null) {
               s = this.errorId;
               hashtable = this.errorParams;
            }

            this.addPendingLetter(schematicletter1);
         }
      }

      this.errorId = s;
      this.errorParams = hashtable;
      return this.errorId == null;
   }

   @Override
   public void clear() {
      super.clear();
      this.pendingLetters.setSize(0);
      this.errorId = null;
      this.errorParams = null;
   }

   LetterReplacement getReplacement(SchematicLetter schematicletter) {
      return (LetterReplacement)this.get(schematicletter);
   }

   boolean checkDeferredMatches(Vector vector) {
      if (vector == null) {
         return true;
      } else {
         Enumeration enumeration = vector.elements();

         while (enumeration.hasMoreElements()) {
            DeferredMatch deferredmatch = (DeferredMatch)enumeration.nextElement();
            if (!deferredmatch.pattern.match(null, deferredmatch.instance, this, deferredmatch.binderMap, BinderKey.toVector(deferredmatch.contextTerms))) {
               this.errorId = "dererr062";
               this.errorParams = Message.params("pattern", "\\l" + deferredmatch.pattern + "\\l", "instance", "\\l" + deferredmatch.instance + "\\l");
               return false;
            }
         }

         return true;
      }
   }

   boolean putReplacement(SchematicLetter schematicletter, LetterReplacement letterreplacement) {
      LetterReplacement letterreplacement1 = this.getReplacement(schematicletter);
      if (letterreplacement1 == null) {
         Vector vector = null;
         int i;
         if ((i = this.pendingLetters.indexOf(schematicletter)) != -1) {
            vector = ((SchematicLetter)this.pendingLetters.elementAt(i)).getDeferredMatches(false);
            this.pendingLetters.removeElementAt(i);
         }

         this.put(schematicletter, letterreplacement);
         if (!this.checkDeferredMatches(vector)) {
            return false;
         }
      } else if (!letterreplacement1.sameReplacementAs(letterreplacement)) {
         this.errorId = "dererr073";
         this.errorParams = Message.params(
            "pattern",
            "\\l" + letterreplacement.pattern + "\\l",
            "new replacement",
            "\\l" + letterreplacement.replacement + "\\l",
            "old replacement",
            "\\l" + letterreplacement1.replacement + "\\l"
         );
         return false;
      }

      return true;
   }

   boolean addReplacement(Expression expression, Expression expression1) {
      LetterReplacement letterreplacement = new LetterReplacement(expression, expression1);
      if (letterreplacement.error != null) {
         this.errorId = letterreplacement.error.id;
         this.errorParams = letterreplacement.error.params;
         return false;
      } else {
         return this.putReplacement(expression.getSchematicLetter(), letterreplacement);
      }
   }

   boolean addReplacement(String s, String s1) {
      String s2 = null;

      Expression expression;
      Expression expression1;
      try {
         s2 = s;
         expression = LogicProgram.parseFormula(s, true, true);
         s2 = s1;
         expression1 = LogicProgram.parseFormula(s1, true, true);
      } catch (FormulaParseException formulaparseexception) {
         this.errorId = "dererr059";
         this.errorParams = Message.params("parser error", s2);
         return false;
      }

      return this.addReplacement(expression, expression1);
   }

   boolean parseReplacement(String s) {
      int i = s.indexOf(":");
      if (i == -1) {
         this.errorId = "dererr059";
         this.errorParams = Message.params("parser error", "scheme map needs pattern:replacement");
         return false;
      } else {
         return this.addReplacement(s.substring(0, i), s.substring(i + 1));
      }
   }

   static ErrorRef validateReplacement(Expression expression, Expression expression1) {
      Hashtable hashtable = Message.params("pattern", "\\l" + expression + "\\l", "replacement", "\\l" + expression1 + "\\l");
      if (expression.kind != 0 && expression.kind != 4) {
         if (expression instanceof SimpleTerm && ((SimpleTerm)expression).isBoundVariable()) {
            return new ErrorRef("dererr067", hashtable);
         }
      } else {
         for (int i = 0; i < expression.childCount; i++) {
            Expression expression2 = expression.getChild(i);
            if (!(expression2 instanceof SimpleTerm) || ((SimpleTerm)expression2).isBoundVariable()) {
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
            if (expression1 instanceof SimpleTerm && ((SimpleTerm)expression1).isBoundVariable()) {
               return new ErrorRef("dererr069", hashtable);
            } else {
               return expression1 instanceof Term ? null : new ErrorRef("dererr070", hashtable);
            }
      }
   }

   boolean addPendingLetter(Expression expression) {
      SchematicLetter schematicletter = expression.getSchematicLetter();
      if (schematicletter != null) {
         this.addPendingLetter(schematicletter);
         return true;
      } else {
         if (expression instanceof SimpleTerm && ((SimpleTerm)expression).isBoundVariable()) {
            this.errorId = "dererr067";
            this.errorParams = Message.params("pattern", "\\l" + expression + "\\l");
         } else {
            this.errorId = "dererr071";
            this.errorParams = Message.params("pattern", "\\l" + expression + "\\l");
         }

         return false;
      }
   }

   void addPendingLetter(SchematicLetter schematicletter) {
      if (schematicletter != null && !this.containsKey(schematicletter)) {
         int i = this.pendingLetters.indexOf(schematicletter);
         if (i == -1) {
            this.pendingLetters.addElement(schematicletter);
         } else {
            Vector vector;
            if ((vector = schematicletter.getDeferredMatches(false)) != null) {
               SchematicLetter schematicletter1 = (SchematicLetter)this.pendingLetters.elementAt(i);
               Vector vector1 = schematicletter1.getDeferredMatches(true);
               Enumeration enumeration = vector.elements();

               while (enumeration.hasMoreElements()) {
                  DeferredMatch deferredmatch = (DeferredMatch)enumeration.nextElement();
                  if (!vector1.contains(deferredmatch)) {
                     vector1.addElement(deferredmatch);
                  }
               }
            }
         }
      }
   }

   boolean deferMatch(Expression expression, Expression expression1, BinderMap bindermap, Vector vector) {
      return this.deferMatch(new DeferredMatch(expression, expression1, bindermap, vector));
   }

   boolean deferMatch(DeferredMatch deferredmatch) {
      SchematicLetter schematicletter = deferredmatch.pattern.getSchematicLetter();
      if (schematicletter == null) {
         return false;
      } else {
         int i = this.pendingLetters.indexOf(schematicletter);
         if (i != -1) {
            Vector vector = ((SchematicLetter)this.pendingLetters.elementAt(i)).getDeferredMatches(true);
            if (!vector.contains(deferredmatch)) {
               vector.addElement(deferredmatch);
            }
         }

         return true;
      }
   }

   Vector getPendingLetters() {
      return this.pendingLetters;
   }

   boolean hasNoDeferredMatches() {
      Enumeration enumeration = this.pendingLetters.elements();

      while (enumeration.hasMoreElements()) {
         if (((SchematicLetter)enumeration.nextElement()).getDeferredMatches(false) != null) {
            return false;
         }
      }

      return true;
   }

   String pendingLettersToString() {
      if (this.pendingLetters == null) {
         return "null";
      } else {
         String object = "";
         Enumeration enumeration = this.pendingLetters.elements();

         while (enumeration.hasMoreElements()) {
            SchematicLetter schematicletter = (SchematicLetter)enumeration.nextElement();
            object = object + (object.equals("") ? "" : ".") + schematicletter;
            Vector vector = schematicletter.getDeferredMatches(false);
            if (vector != null && !vector.isEmpty()) {
               object = object + vector;
            }
         }

         return (String)object;
      }
   }

   Vector collectUsedLetters(Expression expression) {
      SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation();
      if (expression != null) {
         expression.addPendingLetters(schemeinstantiation1);
      }

      Enumeration enumeration = this.elements();

      while (enumeration.hasMoreElements()) {
         LetterReplacement letterreplacement = (LetterReplacement)enumeration.nextElement();
         letterreplacement.replacement.addPendingLetters(schemeinstantiation1);
      }

      return schemeinstantiation1.pendingLetters;
   }

   SchemeInstantiation assignFreshLetters(Expression expression) {
      Vector vector = this.collectUsedLetters(expression);
      int i = this.pendingLetters.size();
      SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation();

      for (int j = 0; j < i; j++) {
         SchematicLetter schematicletter = (SchematicLetter)this.pendingLetters.elementAt(0);
         if (schematicletter.getDeferredMatches(false) != null) {
            SchematicLetter schematicletter1 = schematicletter.freshLetter(vector);
            schemeinstantiation1.addPendingLetter(schematicletter1);
            this.addReplacement(schematicletter.toExpression(), schematicletter1.toExpression());
         }
      }

      return schemeinstantiation1;
   }

   String getErrorId() {
      return this.errorId;
   }

   Hashtable getErrorParams() {
      return this.errorParams;
   }

   String encode() {
      String s = "";
      boolean flag = false;

      for (Enumeration enumeration = this.keys(); enumeration.hasMoreElements(); flag = true) {
         SchematicLetter schematicletter = (SchematicLetter)enumeration.nextElement();
         s = s + (flag ? "." : "") + this.getReplacement(schematicletter);
      }

      return s;
   }

   static SchemeInstantiation decode(String s) {
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

         if (!schemeinstantiation.parseReplacement(s1)) {
            return null;
         }
      }

      return schemeinstantiation;
   }
}
