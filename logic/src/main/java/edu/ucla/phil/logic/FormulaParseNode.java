package edu.ucla.phil.logic;

import java.util.Vector;

class FormulaParseNode implements LogicConstants {
   FormulaParseNode parent = null;
   Expression expression;
   boolean informal = false;
   int identityStyle = 0;
   Vector children = null;
   int offset = 0;
   int length = 0;
   String text = null;
   Vector letterRanges = null;
   int[] strippedIndex = null;
   int[] parenDepth = null;
   static final String[] STRIPPED_STRINGS = new String[]{"(", ")", " "};
   static final String[] EMPTY_STRINGS = new String[]{"", "", ""};

   FormulaParseNode(Expression expressionx, boolean flag, int i) {
      this.expression = expressionx;
      this.informal = flag;
      this.identityStyle = i;
      if (expressionx != null) {
         expressionx.layoutDisplayTree(this);
      }
   }

   FormulaParseNode(Expression expressionx, boolean flag) {
      this(expressionx, flag, 0);
   }

   FormulaParseNode(Expression expressionx) {
      this(expressionx, false);
   }

   FormulaParseNode(String s) {
      this(parseFormula(s));
      this.text = s;
   }

   static Expression parseFormula(String s) {
      if (s == null) {
         return null;
      } else {
         try {
            return LogicProgram.parseFormula(s, true, false);
         } catch (FormulaParseException formulaparseexception) {
            return null;
         }
      }
   }

   FormulaParseNode getParent() {
      return this.parent;
   }

   Expression getExpression() {
      return this.expression;
   }

   int getChildCount() {
      return this.children == null ? 0 : this.children.size();
   }

   FormulaParseNode getChild(int i) {
      return i >= 0 && this.children != null && i < this.children.size() ? (FormulaParseNode)this.children.elementAt(i) : null;
   }

   int[] getTextRange() {
      FormulaParseNode formulaparsenode1 = this.getRoot(true);
      return this.expression == null
         ? new int[]{0, this.text.length()}
         : findDisplayRange(
            formulaparsenode1.strippedIndex,
            formulaparsenode1.parenDepth,
            this.getAbsoluteOffset(),
            this.length,
            this.expression instanceof ConnectiveFormula && this.expression.getChildCount() > 1
         );
   }

   void addLetterRanges(Vector vector) {
      int i = vector == null ? 0 : vector.size();
      if (i > 0 && this.letterRanges == null) {
         this.letterRanges = new Vector();
      }

      for (int j = 0; j < i; j++) {
         this.letterRanges.addElement(rangesOf(this.findLetterNodes((SchematicLetter)vector.elementAt(j))));
      }
   }

   void addTermRanges(BoundVariableMap boundvariablemap) {
      int i = boundvariablemap == null ? 0 : boundvariablemap.freshCount;
      if (i > 0 && this.letterRanges == null) {
         this.letterRanges = new Vector();
      }

      for (int j = 0; j < i; j++) {
         this.letterRanges.addElement(rangesOf(this.findTermNodes(SchematicLetter.placeholder(j))));
      }
   }

   static IntervalSet rangesOf(Vector vector) {
      IntervalSet intervalset = new IntervalSet();
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         FormulaParseNode formulaparsenode = (FormulaParseNode)vector.elementAt(j);
         int[] aint = formulaparsenode.getTextRange();
         aint[1] = aint[0] + formulaparsenode.expression.symbol.length();
         intervalset.toggleBoundaries(aint);
      }

      return intervalset;
   }

   @Override
   public String toString() {
      int[] aint = this.getTextRange();
      return this.getRoot().text.substring(aint[0], aint[1]);
   }

   HighlightedText toStyledText() {
      FormulaParseNode formulaparsenode1 = this.getRoot(true);
      int[] aint = this.getTextRange();
      return new HighlightedText(formulaparsenode1.text, formulaparsenode1.letterRanges).substring(aint[0], aint[1]);
   }

   String getNotationCode() {
      String s = LogicProgram.translateSymbols(this.toString(), new String[]{" "}, new String[]{""});
      if (s.equals("")) {
         return "O";
      } else if (this.expression == null) {
         return "N";
      } else {
         return s.equals(this.expression.toFullyParenthesizedString()) ? "O" : "I";
      }
   }

   String getStructureString() {
      if (this.expression == null) {
         return "0";
      } else {
         int i = this.getChildCount();
         String s = "" + i;

         for (int j = 0; j < i; j++) {
            s = s + "," + this.getChild(j).getStructureString();
         }

         return s;
      }
   }

   int getAbsoluteOffset() {
      return this.offset + (this.parent == null ? 0 : this.parent.getAbsoluteOffset());
   }

   int getLength() {
      return this.length;
   }

   IntervalSet getRange() {
      int i = this.getAbsoluteOffset();
      return IntervalSet.range(i, this.length);
   }

   IntervalSet getOperatorRanges() {
      IntervalSet intervalset = this.getRange();
      int i = this.children == null ? 0 : this.children.size();

      for (int j = 0; j < i; j++) {
         intervalset.subtract(this.getChild(j).getRange());
      }

      return intervalset;
   }

   FormulaParseNode getRoot() {
      return this.getRoot(false);
   }

   FormulaParseNode getRoot(boolean flag) {
      FormulaParseNode node = this;
      FormulaParseNode parent;
      while ((parent = node.parent) != null) {
         node = parent;
      }

      if (flag) {
         node.prepareText();
      }

      return node;
   }

   FormulaParseNode findNode(Expression expressionx) {
      if (this.expression == expressionx) {
         return this;
      } else {
         int i = this.getChildCount();

         for (int j = 0; j < i; j++) {
            FormulaParseNode formulaparsenode1;
            if ((formulaparsenode1 = this.getChild(j).findNode(expressionx)) != null) {
               return formulaparsenode1;
            }
         }

         return null;
      }
   }

   ExpressionPath getPath() {
      if (this.parent == null) {
         return new ExpressionPath();
      } else {
         ExpressionPath expressionpath = this.parent.getPath();
         expressionpath.push(this.parent.children.indexOf(this));
         return expressionpath;
      }
   }

   Vector findLetterNodes(SchematicLetter schematicletter) {
      Vector vector = new Vector();
      this.collectLetterNodes(schematicletter, vector);
      return vector;
   }

   void collectLetterNodes(SchematicLetter schematicletter, Vector vector) {
      if (schematicletter.equals(this.expression.getSchematicLetter())) {
         vector.addElement(this);
      }

      int i = this.getChildCount();

      for (int j = 0; j < i; j++) {
         this.getChild(j).collectLetterNodes(schematicletter, vector);
      }
   }

   Vector findTermNodes(String s) {
      Vector vector = new Vector();
      this.collectTermNodes(s, vector);
      return vector;
   }

   void collectTermNodes(String s, Vector vector) {
      if (this.expression instanceof SimpleTerm && ((SimpleTerm)this.expression).isBoundVariable() && s.equals(this.expression.symbol)) {
         vector.addElement(this);
      }

      int i = this.getChildCount();

      for (int j = 0; j < i; j++) {
         this.getChild(j).collectTermNodes(s, vector);
      }
   }

   FormulaParseNode findNodeForDisplayRange(String s, int i, int j) {
      int[] aint = new int[]{i, j};
      LogicProgram.translateSymbols(s, STRIPPED_STRINGS, EMPTY_STRINGS, aint);
      return this.findNodeForRange(aint[0], aint[1]);
   }

   FormulaParseNode findNodeForRange(int i, int j) {
      int k = this.getChildCount();

      for (int l = 0; l < k; l++) {
         FormulaParseNode formulaparsenode1 = this.getChild(l);
         if (i >= formulaparsenode1.offset && j <= formulaparsenode1.offset + formulaparsenode1.length) {
            return formulaparsenode1.findNodeForRange(i - formulaparsenode1.offset, j - formulaparsenode1.offset);
         }
      }

      return this;
   }

   FormulaParseNode findNodeContaining(int i, int j) {
      return this.findNodeContaining(i, j, false);
   }

   FormulaParseNode findNodeContaining(int i, int j, boolean flag) {
      int k = this.getChildCount();

      for (int l = 0; l < k; l++) {
         FormulaParseNode formulaparsenode1 = this.getChild(l);
         int[] aint = formulaparsenode1.getTextRange();
         if (aint[0] <= i && j <= aint[1] && (!flag || aint[0] != i || j != aint[1])) {
            return formulaparsenode1.findNodeContaining(i, j, flag);
         }
      }

      return this;
   }

   void prepareText() {
      if (this.text == null) {
         this.text = this.expression == null ? "" : this.expression.format(this.informal, this.identityStyle);
      }

      if (this.strippedIndex == null) {
         this.strippedIndex = computeStrippedIndex(this.text);
      }

      if (this.parenDepth == null) {
         this.parenDepth = computeParenDepth(this.text);
      }
   }

   static int[] computeStrippedIndex(String s) {
      int i = s.length() + 1;
      int[] aint = new int[i];
      int j = 0;

      while (j < i) {
         aint[j] = j++;
      }

      LogicProgram.translateSymbols(s, STRIPPED_STRINGS, EMPTY_STRINGS, aint);
      return aint;
   }

   static int[] computeParenDepth(String s) {
      int i = s.length() + 1;
      int[] aint = new int[i];
      aint[0] = 0;

      for (int j = 1; j < i; j++) {
         int k = "()".indexOf(s.charAt(j - 1));
         if (k == 0) {
            aint[j] = aint[j - 1] + 1;
         } else if (k == 1) {
            aint[j] = aint[j - 1] - 1;
         } else {
            aint[j] = aint[j - 1];
         }
      }

      return aint;
   }

   static int[] findDisplayRange(String s, int i, int j, boolean flag) {
      return findDisplayRange(computeStrippedIndex(s), computeParenDepth(s), i, j, flag);
   }

   static int[] findDisplayRange(int[] aint, int[] aint1, int i, int j, boolean flag) {
      int k = aint.length;
      int l = i;
      int i1 = i + j;
      int j1 = 1;

      while (j1 < k && aint[j1] <= l) {
         j1++;
      }

      j1--;
      k--;
      int k1 = j1;

      while (k1 < k && aint[k1] < i1) {
         k1++;
      }

      int l1 = aint1[j1];

      for (int i2 = j1 + 1; i2 <= k1; i2++) {
         if (aint1[i2] < l1) {
            l1 = aint1[i2];
         }
      }

      while (j1 > 0 && aint1[j1] > l1) {
         j1--;
      }

      while (k1 < k && aint1[k1] > l1) {
         k1++;
      }

      if (aint1[j1] == l1 && aint1[k1] == l1 && aint[j1] == l && aint[k1] == i1) {
         int[] aint2 = new int[]{j1, k1};
         if (flag) {
            widenToParens(aint, aint1, aint2);
         }

         return aint2;
      } else {
         return null;
      }
   }

   static void convertRanges(int[] aint, int[] aint1, IntervalSet intervalset, boolean flag) {
      if (!intervalset.inverted) {
         for (int b0 = 0; b0 < intervalset.count - 1; b0 += 2) {
            int[] aint2 = findDisplayRange(aint, aint1, intervalset.boundaries[b0], intervalset.boundaries[b0 + 1] - intervalset.boundaries[b0], flag);
            if (aint2 != null) {
               intervalset.boundaries[b0] = aint2[0];
               intervalset.boundaries[b0 + 1] = aint2[1];
            }
         }
      }
   }

   static int widenToParens(int[] aint, int[] aint1, int[] aint2) {
      int i = aint2[0];
      int j = aint2[1];
      int k = aint.length - 1;
      int l = aint[i];
      int i1 = aint[j];
      int j1 = aint1[i];
      int k1 = j1;

      while (true) {
         while (i > 0 && aint1[i - 1] == k1 && aint[i - 1] == l) {
            i--;
         }

         while (j < k && aint1[j + 1] == k1 && aint[j + 1] == i1) {
            j++;
         }

         if (k1 <= 0 || i <= 0 || aint1[i - 1] != k1 - 1 || j >= k || aint1[j + 1] != k1 - 1) {
            while (i < j && aint1[i + 1] == k1 && aint[i + 1] == l) {
               i++;
            }

            while (j > i && aint1[j - 1] == k1 && aint[j - 1] == i1) {
               j--;
            }

            aint2[0] = i;
            aint2[1] = j;
            return j1 - k1;
         }

         i--;
         j++;
         k1--;
      }
   }

   static void adjustRangeEnds(int[] aint, int[] aint1) {
      int i = aint1[0];
      int j = aint1[1];
      int k = aint.length - 1;
      int l = aint[i];
      int i1 = aint[j];
      if (l > i1) {
         while (i > 0 && aint[i - 1] > i1) {
            i--;
         }
      } else if (i1 > l) {
         while (j < k && aint[j + 1] > l) {
            k++;
         }
      }

      aint1[0] = i;
      aint1[1] = j;
   }

   boolean isParenthesizationValid() {
      if (this.text != null && !this.text.trim().equals("")) {
         this.prepareText();
         return this.checkParenthesization(this.strippedIndex, this.parenDepth);
      } else {
         return true;
      }
   }

   boolean checkParenthesization(int[] aint, int[] aint1) {
      if (this.expression == null) {
         return this.text == null || this.text.trim().equals("");
      } else {
         int i = widenToParens(aint, aint1, findDisplayRange(aint, aint1, this.getAbsoluteOffset(), this.length, false));
         if (this.parent != null && this.parent.expression.usesArgumentParens() && this.parent.getChildCount() == 1) {
            i--;
         }

         if (i < 0) {
            return false;
         } else {
            if (!this.expression.symbol.equals("->") && !this.expression.symbol.equals("<->")) {
               if (!this.expression.symbol.equals("&") && !this.expression.symbol.equals("|")) {
                  if (i != 0) {
                     return false;
                  }
               } else if (this.parent == null) {
                  if (i > 1) {
                     return false;
                  }
               } else if (!this.parent.expression.symbol.equals("->") && !this.parent.expression.symbol.equals("<->")) {
                  if (this.parent.expression.symbol.equals(this.expression.symbol) && this.parent.getChild(0) == this) {
                     if (i > 1) {
                        return false;
                     }
                  } else if (i != 1) {
                     return false;
                  }
               } else if (i > 1) {
                  return false;
               }
            } else if (this.parent == null) {
               if (i > 1) {
                  return false;
               }
            } else if (i != 1) {
               return false;
            }

            int j = this.getChildCount();

            for (int k = 0; k < j; k++) {
               if (!this.getChild(k).checkParenthesization(aint, aint1)) {
                  return false;
               }
            }

            return true;
         }
      }
   }
}
