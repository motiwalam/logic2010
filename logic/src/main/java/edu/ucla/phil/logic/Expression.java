package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

public abstract class Expression implements ExpressionKinds, LogicConstants {
   protected int kind;
   protected String symbol;
   protected Vector children;
   protected int childCount;
   protected boolean displayAsInequality;

   Expression(String s) {
      this.symbol = s;
      this.children = new Vector();
      this.childCount = 0;
      this.displayAsInequality = false;
      this.initKind();
   }

   @Override
   public String toString() {
      return this.format(true, 0);
   }

   String format(boolean flag, int i) {
      return flag ? this.formatMinimal(i) : this.formatFull(i);
   }

   String toCanonicalString() {
      return this.formatMinimal(-1);
   }

   abstract String formatMinimal(int i);

   String toFullyParenthesizedString() {
      return this.formatFull(1);
   }

   abstract String formatFull(int i);

   boolean usesArgumentParens() {
      return false;
   }

   void layoutDisplayTree(FormulaParseNode formulaparsenode) {
      formulaparsenode.children = this.childCount == 0 ? null : new Vector();

      for (int i = 0; i < this.childCount; i++) {
         FormulaParseNode formulaparsenode1 = new FormulaParseNode(this.getChild(i));
         formulaparsenode1.parent = formulaparsenode;
         formulaparsenode.children.addElement(formulaparsenode1);
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

   int indexOfChildSymbol(String s, int i) {
      int j = i;

      while (j < this.childCount && !s.equals(this.getChild(j).symbol)) {
         j++;
      }

      return j == this.childCount ? -1 : j;
   }

   int indexOfChildSymbol(String s) {
      return this.indexOfChildSymbol(s, 0);
   }

   Expression getSubexpression(ExpressionPath expressionpath) {
      return this.getSubexpression(expressionpath, null);
   }

   Expression getSubexpression(ExpressionPath expressionpath, Vector vector) {
      return expressionpath == null ? null : this.getSubexpression(expressionpath.indexes, 0, expressionpath.depth, vector);
   }

   Expression getSubexpression(int[] aint, int i, int j, Vector vector) {
      if (i == j) {
         return this;
      } else {
         Expression expression1 = this.getChild(aint[i]);
         return expression1 == null ? null : expression1.getSubexpression(aint, i + 1, j, vector);
      }
   }

   Vector findOccurrences(Expression expression1) {
      Vector vector = new Vector();
      this.findOccurrences(expression1, new ExpressionPath(), vector);
      return vector;
   }

   void findOccurrences(Expression expression1, ExpressionPath expressionpath, Vector vector) {
      if (this.isIdentical(expression1)) {
         vector.addElement(expressionpath.clone());
      } else {
         for (int i = 0; i < this.childCount; i++) {
            expressionpath.push(i);
            this.getChild(i).findOccurrences(expression1, expressionpath, vector);
            expressionpath.depth--;
         }
      }
   }

   Vector findLetterOccurrences(SchematicLetter schematicletter) {
      Vector vector = new Vector();
      this.findLetterOccurrences(schematicletter, new ExpressionPath(), vector);
      return vector;
   }

   void findLetterOccurrences(SchematicLetter schematicletter, ExpressionPath expressionpath, Vector vector) {
      if (schematicletter != null) {
         if (schematicletter.equals(this.getSchematicLetter())) {
            vector.addElement(expressionpath.clone());
         }

         for (int i = 0; i < this.childCount; i++) {
            expressionpath.push(i);
            this.getChild(i).findLetterOccurrences(schematicletter, expressionpath, vector);
            expressionpath.depth--;
         }
      }
   }

   Vector findSymbolOccurrences(String s) {
      Vector vector = new Vector();
      this.findSymbolOccurrences(s, new ExpressionPath(), vector);
      return vector;
   }

   void findSymbolOccurrences(String s, ExpressionPath expressionpath, Vector vector) {
      if (s.equals(this.symbol)) {
         vector.addElement(expressionpath.clone());
      }

      for (int i = 0; i < this.childCount; i++) {
         expressionpath.push(i);
         this.getChild(i).findSymbolOccurrences(s, expressionpath, vector);
         expressionpath.depth--;
      }
   }

   Vector findBoundVariableOccurrences(String s) {
      Vector vector = new Vector();
      this.findBoundVariableOccurrences(s, new ExpressionPath(), vector);
      return vector;
   }

   void findBoundVariableOccurrences(String s, ExpressionPath expressionpath, Vector vector) {
      if (s != null) {
         for (int i = 0; i < this.childCount; i++) {
            expressionpath.push(i);
            this.getChild(i).findBoundVariableOccurrences(s, expressionpath, vector);
            expressionpath.depth--;
         }
      }
   }

   ExpressionPath getDifferencePath(Expression expression1) {
      return this.commonPathPrefix(this.findDifferences(expression1));
   }

   ExpressionPath commonPathPrefix(Vector vector) {
      int i = vector == null ? 0 : vector.size();
      if (i == 0) {
         return null;
      } else {
         ExpressionPath expressionpath = (ExpressionPath)vector.elementAt(0);

         for (int j = 1; j < i; j++) {
            expressionpath.depth = expressionpath.commonPrefixLength((ExpressionPath)vector.elementAt(j));
         }

         return expressionpath;
      }
   }

   Vector findDifferences(Expression expression1) {
      Vector vector = new Vector();
      this.findDifferences(expression1, new ExpressionPath(), vector);
      return vector;
   }

   private void findDifferences(Expression expression1, ExpressionPath expressionpath, Vector vector) {
      if (expression1 != null && this.symbol.equals(expression1.symbol) && this.childCount == expression1.childCount) {
         for (int i = 0; i < this.childCount; i++) {
            expressionpath.push(i);
            this.getChild(i).findDifferences(expression1.getChild(i), expressionpath, vector);
            expressionpath.depth--;
         }
      } else {
         vector.addElement(expressionpath.clone());
      }
   }

   boolean isIdentical(Expression expression1) {
      if (expression1 != null && this.symbol.equals(expression1.symbol) && this.childCount == expression1.childCount) {
         for (int i = 0; i < this.childCount; i++) {
            if (!this.getChild(i).isIdentical(expression1.getChild(i))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   boolean isAlphaEquivalent(Expression expression1, BinderMap bindermap) {
      if (expression1 != null && this.symbol.equals(expression1.symbol) && this.childCount == expression1.childCount) {
         for (int i = 0; i < this.childCount; i++) {
            if (!this.getChild(i).isAlphaEquivalent(expression1.getChild(i), bindermap)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   Expression copy() {
      return this.instantiate(null, null, new BinderMap(), new Vector());
   }

   Expression instantiate(SchemeInstantiation schemeinstantiation) {
      return this.instantiate(null, schemeinstantiation, new BinderMap(), new Vector());
   }

   Expression instantiate(SchemeInstantiation schemeinstantiation, BinderMap bindermap) {
      return this.instantiate(null, schemeinstantiation, bindermap, new Vector());
   }

   abstract Expression instantiate(Expression expression, SchemeInstantiation schemeinstantiation, BinderMap bindermap, Vector vector);

   void renameBoundVariables(Vector vector) {
      if (vector != null) {
         this.renameBoundVariables(vector, 0);
      }
   }

   int renameBoundVariables(Vector vector, int i) {
      for (int j = 0; j < this.childCount; j++) {
         i = this.getChild(j).renameBoundVariables(vector, i);
      }

      return i;
   }

   Vector getBinders() {
      return this.collectBinders(new Vector());
   }

   Vector collectBinders(Vector vector) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).collectBinders(vector);
      }

      return vector;
   }

   Vector getSchematicLetters() {
      Vector vector = new Vector();
      this.collectSchematicLetters(vector);
      return vector;
   }

   void collectSchematicLetters(Vector vector) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).collectSchematicLetters(vector);
      }
   }

   Expression abstractQuantifiers(Vector vector, int i, SchemeInstantiation schemeinstantiation) {
      for (int j = 0; j < this.childCount; j++) {
         this.children.setElementAt(this.getChild(j).abstractQuantifiers(vector, i, schemeinstantiation), j);
      }

      return this;
   }

   Expression toTruthFunctionalForm() {
      return this.copy().abstractQuantifiers(this.getSchematicLetters(), 0, new SchemeInstantiation());
   }

   Expression expandOutermostQuantifier(int i, String s) {
      return this.copy().expandQuantifiers(i, s, false);
   }

   Expression expandQuantifiers(int i, String s) {
      return this.copy().expandQuantifiers(i, s, true);
   }

   Expression expandQuantifiers(int i, String s, boolean flag) {
      if (flag) {
         for (int j = 0; j < this.childCount; j++) {
            this.children.setElementAt(this.getChild(j).expandQuantifiers(i, s, true), j);
         }
      }

      return this;
   }

   boolean containsVariableBoundBy(Expression expression1) {
      for (int i = 0; i < this.childCount; i++) {
         if (this.getChild(i).containsVariableBoundBy(expression1)) {
            return true;
         }
      }

      return false;
   }

   void collectTermSymbols(Vector vector, Vector vector1) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).collectTermSymbols(vector, vector1);
      }
   }

   BoundVariableNames getBoundVariableNames() {
      Vector vector = this.getBinders();
      int i = vector.size();
      BoundVariableNames boundvariablenames = new BoundVariableNames();

      for (int j = 0; j < i; j++) {
         boundvariablenames.addElement(((Expression)vector.elementAt(j)).getChild(0).getSymbol());
      }

      return boundvariablenames;
   }

   boolean isNegationOf(Expression expression1) {
      return this.kind == 2 && this.symbol.equals("~") && this.getChild(0).isIdentical(expression1);
   }

   public ConnectiveFormula negate() {
      ConnectiveFormula connectiveformula = new ConnectiveFormula("~");
      connectiveformula.addChild(this);
      return connectiveformula;
   }

   Expression linkVariables() {
      this.linkVariables(new VariableScope());
      return this;
   }

   void linkVariables(VariableScope variablescope) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).linkVariables(variablescope);
      }
   }

   Vector findMislinkedVariables() {
      Vector vector = new Vector();
      this.findMislinkedVariables(new VariableScope(), vector);
      return vector.size() == 0 ? null : vector;
   }

   void findMislinkedVariables(VariableScope variablescope, Vector vector) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).findMislinkedVariables(variablescope, vector);
      }
   }

   boolean isArgumentPlaceholder() {
      return false;
   }

   boolean isBoundVariable() {
      return false;
   }

   boolean bindsArguments() {
      return false;
   }

   void linkArgumentPlaceholders(Expression expression1) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).linkArgumentPlaceholders(expression1);
      }
   }

   boolean hasUndeclaredPlaceholder(Expression expression1) {
      for (int i = 0; i < this.childCount; i++) {
         if (this.getChild(i).hasUndeclaredPlaceholder(expression1)) {
            return true;
         }
      }

      return false;
   }

   boolean match(Expression expression1, SchemeInstantiation schemeinstantiation) {
      return this.match(null, expression1, schemeinstantiation, new BinderMap(), new Vector());
   }

   boolean match(Expression expression1, SchemeInstantiation schemeinstantiation, BinderMap bindermap) {
      return this.match(null, expression1, schemeinstantiation, bindermap, new Vector());
   }

   boolean match(Expression expression1, Expression expression2, SchemeInstantiation schemeinstantiation, BinderMap bindermap, Vector vector) {
      if (expression2 == null || this.kind == expression2.kind && this.symbol.equals(expression2.symbol) && this.childCount == expression2.childCount) {
         for (int i = 0; i < this.childCount; i++) {
            if (!this.getChild(i).match(expression1, expression2 == null ? null : expression2.getChild(i), schemeinstantiation, bindermap, vector)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   boolean matchLetter(Expression expression1, SchemeInstantiation schemeinstantiation, BinderMap bindermap, Vector vector) {
      SchematicLetter schematicletter = this.getSchematicLetter();
      LetterReplacement letterreplacement;
      if ((letterreplacement = schemeinstantiation.getReplacement(schematicletter)) != null) {
         return letterreplacement.replacement.match(this, expression1, schemeinstantiation, bindermap, vector);
      } else {
         if (expression1 != null) {
            letterreplacement = this.buildReplacement(expression1, bindermap, vector);
            if (letterreplacement.error == null) {
               return schemeinstantiation.putReplacement(schematicletter, letterreplacement);
            }

            Hashtable hashtable = letterreplacement.error.params;
            if (hashtable == null || hashtable.get("addMissingKey") == null) {
               schemeinstantiation.errorId = letterreplacement.error.id;
               schemeinstantiation.errorParams = hashtable;
               return false;
            }
         }

         ErrorRef errorref = this.checkReplacementType(expression1);
         if (errorref != null) {
            schemeinstantiation.errorId = errorref.id;
            schemeinstantiation.errorParams = errorref.params;
            return false;
         } else {
            return this.addPendingLetters(schemeinstantiation) && schemeinstantiation.deferMatch(this, expression1, bindermap, vector);
         }
      }
   }

   ErrorRef checkReplacementType(Expression expression) {
      return null;
   }

   LetterReplacement buildReplacement(Expression expression1, BinderMap bindermap, Vector vector) {
      SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
      SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation();
      Hashtable hashtable = Message.params("pattern", "\\l" + this + "\\l", "replacement", "\\l" + expression1 + "\\l");

      for (int i = 0; i < this.childCount; i++) {
         Expression expression2;
         if (!(expression2 = this.getChild(i)).isBoundVariable()) {
            Message.putParam(hashtable, "n", i + 1 + "");
            hashtable.put("addMissingKey", "");
            return new LetterReplacement(new ErrorRef("dererr065", hashtable));
         }

         Expression expression3 = ((SimpleTerm)expression2).getBinder();
         Expression expression4 = bindermap.getCounterpart(null, expression3, vector).getChild(0);
         SimpleTerm simpleterm = new SimpleTerm(SchematicLetter.placeholder(i));
         if (!schemeinstantiation.addReplacement(new SimpleTerm(expression2.symbol), simpleterm)) {
            Message.putParam(hashtable, "n", i + 1 + "");
            hashtable.put("addMissingKey", "");
            return new LetterReplacement(new ErrorRef("dererr066", hashtable));
         }

         schemeinstantiation1.addReplacement(new SimpleTerm(expression4.symbol), simpleterm);
      }

      Expression expression5 = this.instantiate(schemeinstantiation);
      Expression expression6 = expression1.instantiate(schemeinstantiation1);
      LetterReplacement letterreplacement = new LetterReplacement(expression5, expression6);
      if (letterreplacement.error == null) {
         Message.putParam(hashtable, "dummy pattern", expression5 + "");
         Message.putParam(hashtable, "dummy replacement", expression6 + "");
         if (!schemeinstantiation1.getPendingLetters().isEmpty()) {
            letterreplacement.error = new ErrorRef("dererr061");
         } else if (expression6.findMislinkedVariables() != null) {
            letterreplacement.error = new ErrorRef("dererr061");
         }
      }

      return letterreplacement;
   }

   SchematicLetter getSchematicLetter() {
      return null;
   }

   boolean addPendingLetters(SchemeInstantiation schemeinstantiation) {
      boolean flag = true;

      for (int i = 0; i < this.childCount; i++) {
         flag &= this.getChild(i).addPendingLetters(schemeinstantiation);
      }

      return flag;
   }

   boolean isFullyInstantiated(SchemeInstantiation schemeinstantiation) {
      for (int i = 0; i < this.childCount; i++) {
         if (!this.getChild(i).isFullyInstantiated(schemeinstantiation)) {
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
      this.collectFreeVariables(vector);
      return vector;
   }

   void collectFreeVariables(Vector vector) {
      for (int i = 0; i < this.childCount; i++) {
         this.getChild(i).collectFreeVariables(vector);
      }
   }
}
