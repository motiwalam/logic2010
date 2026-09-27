package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.Box;

class SymbolizationNode extends SizedPanel implements SymbolizationConstants {
   public static final int TYPE_FORMULA = 0;
   public static final int TYPE_TERM = 1;
   public static final int TYPE_ANY = 2;
   protected LPSymbolizer symbolizer;
   protected SymbolizationTextPanel textPanel;
   protected Vector slots;
   protected int[] slotAnchors = new int[]{24, 19, 23};
   protected int connective;
   int outType;
   int[] argTypes;
   String problemName;
   String originalName;
   String scheme;
   String answerKeys;
   String statement;
   Vector answers;
   Vector answerGroup;
   boolean userAnswerKey;
   static String[] displaySymbols = LogicProgram.symbols;

   SymbolizationNode(LPSymbolizer lpsymbolizer, int i) {
      this.symbolizer = lpsymbolizer;
      this.connective = 0;
      this.outType = connOutTypes[this.connective];
      this.argTypes = (int[])connArgTypes[this.connective].clone();
      this.problemName = null;
      this.originalName = null;
      this.statement = null;
      this.slots = new Vector(3);
      this.answers = null;
      this.answerGroup = null;
      this.userAnswerKey = true;
      this.setLayout(new GridBagLayout());
      GridBagConstraints gridbagconstraints = new GridBagConstraints();
      gridbagconstraints.gridx = 0;
      gridbagconstraints.gridy = 0;
      gridbagconstraints.gridwidth = 3;
      gridbagconstraints.anchor = 19;
      gridbagconstraints.weighty = 0.0;
      gridbagconstraints.insets = new Insets(1, 0, 0, 0);
      this.add(this.textPanel = new SymbolizationTextPanel(this, null, i), gridbagconstraints);
      if (this.getParentNode() == null) {
         GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
         gridbagconstraints1.gridx = 0;
         gridbagconstraints1.gridy = 2;
         gridbagconstraints1.gridwidth = 3;
         gridbagconstraints1.anchor = 10;
         gridbagconstraints1.weighty = 0.05;
         this.add(Box.createGlue(), gridbagconstraints1);
      }
   }

   SymbolizationNode(LPSymbolizer lpsymbolizer) {
      this(lpsymbolizer, 0);
   }

   void setEnglishText(String s) {
      String s1 = "";
      if (s != null) {
         while (true) {
            int i = s.indexOf(92);
            if (i == -1) {
               s1 = s1 + s;
               break;
            }

            s1 = s1 + s.substring(0, i);
            if (i + 1 < s.length()) {
               char c0 = s.charAt(i + 1);
               if (c0 == 'n') {
                  s1 = s1 + '\n';
               } else {
                  s1 = s1 + c0;
               }

               s = s.substring(i + 2);
            } else {
               s = "";
            }
         }
      }

      this.textPanel.textPane.setText(SymbolizationTextPanel.collapseWhitespace(s1));
   }

   boolean isModified() {
      return this.connective != 0 ? true : !this.getEnglishText().equals(SymbolizationTextPanel.collapseWhitespace(this.statement));
   }

   String getEnglishText() {
      String s = "";
      if (this.textPanel != null && this.textPanel.textPane != null) {
         String s1 = SymbolizationTextPanel.collapseWhitespace(this.textPanel.textPane.getText());

         while (true) {
            int i = s1.indexOf(92);
            int j = s1.indexOf(10);
            if (i == -1 && j == -1) {
               return s + s1;
            }

            if (i == -1 || j != -1 && i >= j) {
               s = s + s1.substring(0, j) + "\\n";
               s1 = s1.substring(j + 1);
            } else {
               s = s + s1.substring(0, i) + "\\\\";
               s1 = s1.substring(i + 1);
            }
         }
      } else {
         return "";
      }
   }

   String getNodeCode() {
      String s = connSymbol[this.connective];
      int i = "*@!%".indexOf(s);
      if (i != -1) {
         s = s + this.getConnectivePanel().label;
      }

      if (i == 0) {
         s = DelimitedTokenizer.escape(s, "\\{") + ExpressionPath.format(this.argTypes) + this.outType;
      }

      return s;
   }

   String getLabel() {
      SymbolizationConnectivePanel symbolizationconnectivepanel = this.getConnectivePanel();
      return symbolizationconnectivepanel == null ? null : symbolizationconnectivepanel.label;
   }

   SymbolizationConnectivePanel getConnectivePanel() {
      Enumeration enumeration = this.slots.elements();

      while (enumeration.hasMoreElements()) {
         Component component = (Component)enumeration.nextElement();
         if (component instanceof SymbolizationConnectivePanel) {
            return (SymbolizationConnectivePanel)component;
         }
      }

      return null;
   }

   SymbolizationNode getChildNode(int i) {
      Enumeration enumeration = this.slots.elements();

      while (enumeration.hasMoreElements()) {
         Component component = (Component)enumeration.nextElement();
         if (component instanceof SymbolizationNode) {
            if (i == 0) {
               return (SymbolizationNode)component;
            }

            i--;
         }
      }

      return null;
   }

   int indexOfChildNode(SymbolizationNode symbolizationnode1) {
      int i = 0;
      Enumeration enumeration = this.slots.elements();

      while (enumeration.hasMoreElements()) {
         Component component = (Component)enumeration.nextElement();
         if (component instanceof SymbolizationNode) {
            if (component == symbolizationnode1) {
               return i;
            }

            i++;
         }
      }

      return -1;
   }

   SymbolizationNode getParentNode() {
      Container container = this.getParent();
      return container instanceof SymbolizationNode ? (SymbolizationNode)container : null;
   }

   void clearNode() {
      this.setConnective(0, false);
      this.problemName = null;
      this.originalName = null;
      this.scheme = null;
      this.answerKeys = null;
      this.statement = null;
      this.answers = null;
      this.answerGroup = null;
      this.userAnswerKey = true;
      this.textPanel.textPane.setText("");
      this.textPanel.normalizeText();
   }

   synchronized SymbolizationNode setConnective(int i, boolean flag) {
      return this.setConnective(i, null, flag);
   }

   synchronized SymbolizationNode setConnective(int i, String s, boolean flag) {
      int j;
      int[] aint;
      switch (i) {
         case 6:
         case 7:
         case 8:
            if (i != this.connective && s == null) {
               s = this.suggestBoundVariable();
            }

            if (s == null) {
               s = SymbolizationDialogs.askForSymbol("", "Bound Variable:", this);
            }

            if (s == null || s.equals("") || this.connective == i && s.equals(this.getLabel())) {
               return this;
            }

            s = s.trim();
            j = connOutTypes[i];
            aint = connArgTypes[i];
            break;
         case 9:
         case 10:
         default:
            if (this.connective == i) {
               return this;
            }

            j = connOutTypes[i];
            aint = connArgTypes[i];
            break;
         case 11:
            DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\{");
            if (s == null) {
               s = delimitedtokenizer.escape(SymbolizationDialogs.askForSymbol("", "Atomic Expression:", this));
            }

            if (s != null) {
               s = s.trim();
            }

            if (s == null || s.equals("") || this.connective == i && s.equals(this.getLabel())) {
               return this;
            }

            delimitedtokenizer.setInput(s);
            s = delimitedtokenizer.nextToken().trim();
            j = 0;
            aint = null;
            boolean flag1 = false;
            if (delimitedtokenizer.getDelimiter() == '{') {
               String s1 = "{" + delimitedtokenizer.getRemaining();
               int k = s1.indexOf(125);
               if (k != -1) {
                  try {
                     j = Integer.parseInt(s1.substring(k + 1));
                     aint = ExpressionPath.parse(s1.substring(0, k + 1));
                     if (aint != null) {
                        flag1 = true;
                     }
                  } catch (Exception exception) {
                  }
               }
            }

            if (!flag1) {
               try {
                  Expression expression = LogicProgram.parseFormula(s, true, false);
                  j = expression instanceof Term ? 1 : 0;
               } catch (FormulaParseException formulaparseexception) {
                  if (flag) {
                     SymbolizationDialogs.showMessage("symnot010", Message.putParam(null, "source", s));
                  }

                  return null;
               }

               aint = connArgTypes[i];
            }
      }

      if (j != 2) {
         SymbolizationNode symbolizationnode1 = this.getParentNode();
         if (symbolizationnode1 != null) {
            int[] aint1 = symbolizationnode1.argTypes;
            int l = aint1.length;

            int j1;
            for (j1 = 0; j1 < l; j1++) {
               if (symbolizationnode1.getChildNode(j1) == this) {
                  if (aint1[j1] != j) {
                     String s2 = "Formula or term";
                     if (aint1[j1] < expTypes.length) {
                        s2 = expTypes[aint1[j1]];
                     }

                     if (flag) {
                        SymbolizationDialogs.showMessage("symnot011", Message.putParam(null, "type", s2.toLowerCase()));
                     }

                     return null;
                  }
                  break;
               }
            }

            if (j1 == l) {
               SymbolizationDialogs.showMessage("could not find child");
            }
         }
      }

      ChildRecordSnapshot childrecordsnapshot;
      if (this.argTypes.length != 0 && aint.length != 0) {
         childrecordsnapshot = new ChildRecordSnapshot(this);
      } else {
         childrecordsnapshot = null;
      }

      if (this.slots != null) {
         Enumeration enumeration = this.slots.elements();

         while (enumeration.hasMoreElements()) {
            Component component = (Component)enumeration.nextElement();
            if (component != null) {
               this.remove(component);
            }
         }

         this.slots.removeAllElements();
         this.slots.setSize(3);
      }

      SymbolizationNode symbolizationnode2 = this;
      this.outType = j;
      this.argTypes = aint;
      switch (this.connective = i) {
         case 0:
            this.textPanel.normalizeText();
            symbolizationnode2 = this;
            break;
         case 1:
            this.putSlot(new SymbolizationConnectivePanel(this, this.connective), 0);
            this.putSlot(symbolizationnode2 = new SymbolizationNode(this.symbolizer), 2);
            break;
         case 2:
         case 3:
         case 4:
         case 5:
            this.putSlot(symbolizationnode2 = new SymbolizationNode(this.symbolizer, 1), 0);
            this.putSlot(new SymbolizationConnectivePanel(this, this.connective), 1);
            this.putSlot(new SymbolizationNode(this.symbolizer, -1), 2);
            break;
         case 6:
         case 7:
         case 8:
            this.putSlot(new SymbolizationConnectivePanel(this, this.connective, s), 0);
            this.putSlot(symbolizationnode2 = new SymbolizationNode(this.symbolizer), 2);
            break;
         case 9:
         case 10:
            this.putSlot(symbolizationnode2 = new SymbolizationNode(this.symbolizer), 0);
            this.putSlot(new SymbolizationConnectivePanel(this, this.connective), 1);
            this.putSlot(new SymbolizationNode(this.symbolizer), 2);
            break;
         case 11:
            this.putSlot(Box.createGlue(), 0);
            this.putSlot(new SymbolizationConnectivePanel(this, this.connective, s), 1);
            this.putSlot(Box.createGlue(), 2);
            int i1 = this.argTypes.length;
            if (i1 <= 1 && (!isNonPredicateSymbol(s) || i1 <= 0)) {
               boolean flag2 = false;
            } else {
               boolean flag3 = true;
            }

            symbolizationnode2 = i1 == 0 ? this : this.getChildNode(0);
            this.textPanel.normalizeText();
      }

      this.revalidate();
      if (this.symbolizer != null) {
         this.symbolizer.repaintTree();
      }

      if (childrecordsnapshot != null) {
         childrecordsnapshot.restore(this);
      }

      if (childrecordsnapshot != null) {
         symbolizationnode2 = null;
      }

      if (this.symbolizer != null) {
         this.symbolizer.updateSymbolization();
      }

      return symbolizationnode2;
   }

   void putSlot(Component component, int i) {
      GridBagConstraints gridbagconstraints = new GridBagConstraints();
      gridbagconstraints.gridx = i;
      gridbagconstraints.gridy = 1;
      gridbagconstraints.anchor = this.slotAnchors[i];
      gridbagconstraints.weightx = i == 1 ? 0.0 : 1.0;
      gridbagconstraints.weighty = 1.0;
      gridbagconstraints.insets = new Insets(1, 0, 0, 0);
      this.add(component, gridbagconstraints);
      this.slots.setElementAt(component, i);
   }

   static boolean isNonPredicateSymbol(String s) {
      return s != null && s.length() != 0 ? "FGHIJKLMNO".indexOf(s.charAt(0)) == -1 : true;
   }

   String suggestBoundVariable() {
      int i = this.argTypes.length;

      for (int j = 0; j < i; j++) {
         if (this.getChildNode(j).containsBinder()) {
            return null;
         }
      }

      String s1 = "xyzuvwlmnopqrst";
      SymbolizationNode symbolizationnode1 = this;

      while ((symbolizationnode1 = symbolizationnode1.getParentNode()) != null) {
         if (symbolizationnode1.isBinder()) {
            String s = symbolizationnode1.getLabel();
            int k = s1.indexOf(s);
            if (k != -1) {
               s1 = s1.substring(0, k) + s1.substring(k + 1);
            }
         }
      }

      return s1.length() == 0 ? null : s1.substring(0, 1);
   }

   void showHint() {
      SymbolizationNode symbolizationnode1 = this.symbolizer.problem;
      SymbolizationNode symbolizationnode2 = symbolizationnode1.findClosestAnswer();
      if (symbolizationnode2 != null) {
         symbolizationnode1.clearErrors();
         HintCollector hintcollector = new HintCollector(this);
         symbolizationnode1.matchTree(symbolizationnode2, new Vector(), new Vector(), hintcollector);
         SymbolizationHint symbolizationhint = hintcollector.getHint();
         if (symbolizationhint == null) {
            SymbolizationErrorButton symbolizationerrorbutton = null;
            Vector vector = hintcollector.getErrors();
            int i = vector.size();
            SymbolizationNode symbolizationnode3 = this;

            while (symbolizationerrorbutton == null && (symbolizationnode3 = symbolizationnode3.getParentNode()) != null) {
               for (int j = 0; j < i; j++) {
                  SymbolizationErrorButton symbolizationerrorbutton1 = (SymbolizationErrorButton)vector.elementAt(j);
                  if (symbolizationerrorbutton1.target == symbolizationnode3) {
                     symbolizationerrorbutton = symbolizationerrorbutton1;
                     break;
                  }
               }
            }

            if (symbolizationerrorbutton != null) {
               SymbolizationConnectivePanel symbolizationconnectivepanel = symbolizationerrorbutton.target.getConnectivePanel();
               if (symbolizationconnectivepanel != null) {
                  symbolizationconnectivepanel.add(symbolizationerrorbutton);
                  this.symbolizer.errorCount++;
                  symbolizationconnectivepanel.enableTarget.setEnabled(true);
                  symbolizationconnectivepanel.revalidate();
               }
            }
         } else {
            symbolizationhint.show();
            this.symbolizer.hintCount++;
         }
      }
   }

   boolean containsBinder() {
      if (this.isBinder()) {
         return true;
      } else {
         int i = this.argTypes.length;

         for (int j = 0; j < i; j++) {
            if (this.getChildNode(j).containsBinder()) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public void invalidate() {
      SymbolizationNode symbolizationnode1 = this.getParentNode();
      if (symbolizationnode1 != null) {
         symbolizationnode1.invalidate();
      }

      super.invalidate();
   }

   int getConnective() {
      return this.connective;
   }

   Expression toExpression() {
      if (this.isIncomplete()) {
         return null;
      } else {
         try {
            return LogicProgram.parseFormula(LogicProgram.translateSymbols(this.toString(), displaySymbols, maggie));
         } catch (FormulaParseException formulaparseexception) {
            return null;
         }
      }
   }

   @Override
   public String toString() {
      String object = LogicProgram.translateSymbols(connSymbol[this.connective], maggie, displaySymbols);
      String s = LogicProgram.translateSymbols(this.getLabel(), maggie, displaySymbols);
      switch (this.connective) {
         case 0:
            return this.getEnglishText();
         case 1:
            return object + this.getChildNode(0);
         case 2:
         case 3:
         case 4:
         case 5:
            return "(" + this.getChildNode(0) + " " + object + " " + this.getChildNode(1) + ")";
         case 6:
         case 7:
         case 8:
            return object + s + " " + this.getChildNode(0);
         case 9:
         case 10:
            return this.getChildNode(0) + " " + object + " " + this.getChildNode(1);
         case 11:
            String object1 = s;
            int i = this.argTypes.length;
            boolean flag = i > 1 || this.outType == 1 && i > 0;
            if (flag) {
               object1 = s + "(";
            }

            for (int j = 0; j < i; j++) {
               object1 = object1 + this.getChildNode(j);
            }

            if (flag) {
               object1 = object1 + ")";
            }

            return (String)object1;
         default:
            return "";
      }
   }

   String toRecord(boolean flag) {
      String s = "";
      String s1 = this.getEnglishText();
      if (flag) {
         s = s + TaggedRecord.formatField(this.problemName, '$');
         s = s + TaggedRecord.formatField(this.statement, '-');
         s = s + TaggedRecord.formatField(this.originalName, 'o');
         if (this.userAnswerKey && this.originalName == null) {
            s = s + TaggedRecord.formatField(this.scheme, '=');
            s = s + TaggedRecord.formatField(this.answerKeys, '@');
         }
      }

      if (!flag || this.connective != 0 || !s1.equals(SymbolizationTextPanel.collapseWhitespace(this.statement))) {
         s = s + TaggedRecord.formatField(DelimitedTokenizer.escape(this.getNodeCode(), "\\:") + ":" + s1, '+');
      }

      int i = connArgTypes[this.connective].length;

      for (int j = 0; j < i; j++) {
         s = s + this.getChildNode(j).toRecord(false);
      }

      return s;
   }

   void copyAnswersToUserKey() {
      if (this.originalName != null && this.answers != null) {
         String[] astring = new String[this.answers.size()];
         this.answers.copyInto(astring);
         this.answers = null;
         this.answerKeys = null;
         int i = astring.length;

         for (int j = 0; j < i; j++) {
            this.addUserAnswer(astring[j]);
         }

         this.originalName = null;
         this.userAnswerKey = true;
         LPSymbolizer.writeUserKey();
         String s = this.symbolizer.getChangedProblem();
         if (s == null) {
            LPSymbolizer.saveProblems(this.symbolizer.problemIndex);
         } else {
            this.symbolizer.saveProblems(s);
         }
      }
   }

   void addUserAnswer(String s) {
      int i = 1;

      String s1;
      while (LPSymbolizer.userKey.get(s1 = this.problemName + "-" + i) != null) {
         i++;
      }

      String s2 = TaggedRecord.withName(s, s1);
      LPSymbolizer.userKey.put(s1, s2);
      if (this.answerKeys != null && this.answerKeys != "") {
         this.answerKeys = this.answerKeys + "." + DelimitedTokenizer.escape(s1, "\\.");
      } else {
         this.answerKeys = DelimitedTokenizer.escape(s1, "\\.");
      }

      if (this.answers == null) {
         this.answers = new Vector();
      }

      this.answers.addElement(s2);
   }

   void removeUserAnswers() {
      if (this.answerKeys != null && this.userAnswerKey && this.originalName == null) {
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\.");
         delimitedtokenizer.setInput(this.answerKeys);

         while (true) {
            String s = delimitedtokenizer.nextToken();
            if (s == null) {
               this.answerKeys = null;
               break;
            }

            if (!s.equals("")) {
               LPSymbolizer.userKey.remove(s);
            }
         }
      }
   }

   void loadRecord(TaggedRecord taggedrecord) {
      this.loadRecord(taggedrecord, true);
   }

   void loadRecord(TaggedRecord taggedrecord, boolean flag) {
      this.loadRecord(taggedrecord, flag, true);
   }

   void loadRecord(TaggedRecord taggedrecord, boolean flag, boolean flag1) {
      if (flag1) {
         this.clearNode();
      }

      this.statement = LPSymbolizer.getProblemStatement(taggedrecord);
      int[] aint = taggedrecord.indexesOfTag('+');
      if (flag) {
         if (aint.length == 0 && this.statement != null) {
            int i = taggedrecord.getFieldCount();
            taggedrecord.insertField('+', DelimitedTokenizer.escape(connSymbol[0], "\\:") + ":" + this.statement, i);
            aint = new int[]{i};
         }

         this.problemName = taggedrecord.getName();
         this.originalName = taggedrecord.valueAt(taggedrecord.indexOfTag('o'));
      }

      this.readNodes(aint, 0, taggedrecord);
      if (flag && (LPSymbolizer.exercises != null || LPSymbolizer.problems != null) && (this.problemName != null || this.originalName != null)) {
         String s2 = null;
         Hashtable hashtable = null;
         if (LPSymbolizer.exercises != null) {
            s2 = LPSymbolizer.exercises.getRecord(this.originalName == null ? this.problemName : this.originalName);
            hashtable = LPSymbolizer.exercises.answerGroups;
            if (s2 != null) {
               this.userAnswerKey = false;
            }
         }

         if (s2 == null && LPSymbolizer.problems != null) {
            s2 = LPSymbolizer.problems.getRecord(this.originalName == null ? this.problemName : this.originalName);
            hashtable = LPSymbolizer.problems.answerGroups;
            if (s2 == null) {
               s2 = taggedrecord.toString();
            }
         }

         if (s2 != null) {
            taggedrecord = new TaggedRecord(s2);
            String s;
            if (this.originalName == null || (s = LPSymbolizer.getProblemStatement(taggedrecord)) != null && s.equals(this.statement)) {
               this.scheme = taggedrecord.valueAt(taggedrecord.indexOfTag('='));
               this.answerKeys = taggedrecord.valueAt(taggedrecord.indexOfTag('@'));
               this.answers = lookupAnswers(this.answerKeys, this.userAnswerKey);
               if (hashtable != null) {
                  String s1 = taggedrecord.valueAt(taggedrecord.indexOfTag('g'));
                  if (s1 != null) {
                     this.answerGroup = (Vector)hashtable.get(s1);
                  }
               }
            } else {
               this.originalName = null;
            }
         }
      }
   }

   int readNodes(int[] aint, int i, TaggedRecord taggedrecord) {
      if (aint != null && aint.length != 0) {
         String s1 = null;
         this.outType = 0;

         String s;
         try {
            s = taggedrecord.valueAt(aint[i]);
            i++;
         } catch (IndexOutOfBoundsException indexoutofboundsexception) {
            System.out.println("invalid symbolization node index: " + i);
            return -1;
         }

         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\:");
         delimitedtokenizer.setInput(s);
         String s2 = delimitedtokenizer.nextToken().trim();
         if (s2 == null) {
            System.out.println("invalid symbolization node: " + s);
            return -1;
         } else if (s2.equals("")) {
            System.out.println("blank symbolization connective");
            return -1;
         } else {
            if ("*@!%".indexOf(s2.charAt(0)) != -1) {
               s1 = s2.substring(1).trim();
               s2 = s2.substring(0, 1);
            }

            int j = LogicProgram.indexOf(connSymbol, s2);
            if (j == -1) {
               System.out.println("unknown symbolization connective: " + s2);
               return -1;
            } else {
               this.setConnective(j, s1, false);
               this.setEnglishText(delimitedtokenizer.getRemaining());
               this.textPanel.normalizeText();
               int k = connArgTypes[j].length;

               for (int l = 0; l < k; l++) {
                  SymbolizationNode symbolizationnode1 = this.getChildNode(l);
                  if (symbolizationnode1 == null) {
                     return -1;
                  }

                  i = symbolizationnode1.readNodes(aint, i, taggedrecord);
                  if (i == -1) {
                     break;
                  }
               }

               return i;
            }
         }
      } else {
         return -1;
      }
   }

   static Vector lookupAnswers(TaggedRecord taggedrecord, boolean flag) {
      String s = taggedrecord.valueAt(taggedrecord.indexOfTag('@'));
      return lookupAnswers(s, flag);
   }

   static Vector lookupAnswers(String s, boolean flag) {
      if (s == null) {
         return null;
      } else {
         Hashtable hashtable = flag ? LPSymbolizer.userKey : LPSymbolizer.answers;
         if (hashtable == null) {
            return null;
         } else {
            DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\.");
            delimitedtokenizer.setInput(s);
            Vector vector = new Vector();

            while (true) {
               String s1 = delimitedtokenizer.nextToken();
               if (s1 == null) {
                  return vector;
               }

               if (!s1.equals("")) {
                  String s2 = (String)hashtable.get(s1);
                  if (s2 != null) {
                     vector.addElement(s2);
                  }
               }
            }
         }
      }
   }

   void buildFromText(String s) {
      Expression expression;
      try {
         expression = LogicProgram.parseFormula(s);
      } catch (FormulaParseException formulaparseexception) {
         String s1 = "\\l" + s + "\\l is not a well formed expression";
         MessageDialog.showMessage("Badly Formed Expression", s1, null, null);
         return;
      }

      this.setConnective(0, false);
      this.buildFromExpression(expression);
      this.textPanel.requestFocus();
   }

   void buildFromExpression(Expression expression) {
      if (expression != null) {
         String s = null;
         int i = expression.childCount;
         int j = LogicProgram.indexOf(connSymbol, expression.symbol);
         if (isBinder(j)) {
            this.setConnective(j, expression.getChild(0).symbol, true);
            this.getChildNode(0).buildFromExpression(expression.getChild(1));
         } else {
            if (j == -1) {
               j = 11;
               s = DelimitedTokenizer.escape(expression.toString(), "\\{");
               i = 0;
            }

            this.setConnective(j, s, true);

            for (int k = 0; k < i; k++) {
               this.getChildNode(k).buildFromExpression(expression.getChild(k));
            }
         }
      }
   }

   Vector getAnswerSets() {
      if (this.answerGroup != null) {
         return this.answerGroup;
      } else {
         Vector vector = new Vector();
         vector.addElement(new AnswerSet(this.problemName, this.answers));
         return vector;
      }
   }

   int findMatchingAnswer() {
      Vector vector = this.getAnswerSets();
      int i = vector.size();
      if (i != 0 && !this.isIncomplete()) {
         for (int j = 0; j < i; j++) {
            AnswerSet answerset = (AnswerSet)vector.elementAt(j);
            int k = answerset.answers == null ? 0 : answerset.answers.size();

            for (int l = 0; l < k; l++) {
               SymbolizationNode symbolizationnode1 = new SymbolizationNode(null);
               symbolizationnode1.loadRecord(new TaggedRecord((String)answerset.answers.elementAt(l)));
               if (symbolizationnode1.isIncomplete()) {
                  System.out.println(symbolizationnode1.problemName + " is incomplete.");
               } else if (this.matchTree(symbolizationnode1, null)) {
                  return j;
               }
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   int findEquivalentAnswer() {
      Vector vector = this.getAnswerSets();
      int i = vector.size();
      Expression expression = this.toExpression();
      if (i != 0 && expression != null) {
         for (int j = 0; j < i; j++) {
            AnswerSet answerset = (AnswerSet)vector.elementAt(j);
            int k = answerset.answers == null ? 0 : answerset.answers.size();

            for (int l = 0; l < k; l++) {
               SymbolizationNode symbolizationnode1 = new SymbolizationNode(null);
               symbolizationnode1.loadRecord(new TaggedRecord((String)answerset.answers.elementAt(l)));
               if (symbolizationnode1.isIncomplete()) {
                  System.out.println(symbolizationnode1.problemName + " is incomplete.");
               } else {
                  Expression expression1 = symbolizationnode1.toExpression();
                  if (expression1 == null) {
                     System.out.println(symbolizationnode1.problemName + " could not be parsed.");
                  } else if (areEquivalent(expression, expression1)) {
                     return j;
                  }
               }
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   int countAnswers() {
      Vector vector = this.getAnswerSets();
      int i = vector.size();
      int j = 0;

      for (int k = 0; k < i; k++) {
         if (this.findDuplicateSolution(k, LPSymbolizer.problems) == -1) {
            AnswerSet answerset = (AnswerSet)vector.elementAt(k);
            j += answerset.answers == null ? 0 : answerset.answers.size();
         }
      }

      return j;
   }

   SymbolizationNode findClosestAnswer() {
      Vector vector = this.getAnswerSets();
      int i = vector.size();
      SymbolizationNode symbolizationnode1 = null;
      int j = 0;

      for (int k = 0; k < i; k++) {
         if (this.findDuplicateSolution(k, LPSymbolizer.problems) == -1) {
            AnswerSet answerset = (AnswerSet)vector.elementAt(k);
            int l = answerset.answers == null ? 0 : answerset.answers.size();

            for (int i1 = 0; i1 < l; i1++) {
               SymbolizationNode symbolizationnode2 = new SymbolizationNode(null);
               symbolizationnode2.loadRecord(new TaggedRecord((String)answerset.answers.elementAt(i1)));
               int j1 = this.countMatchingNodes(symbolizationnode2);
               if (symbolizationnode1 == null || j1 > j) {
                  symbolizationnode1 = symbolizationnode2;
                  j = j1;
               }
            }
         }
      }

      return symbolizationnode1;
   }

   static boolean areEquivalent(Expression expression, Expression expression1) {
      if (expression instanceof Formula && expression1 instanceof Formula) {
         ConnectiveFormula connectiveformula = new ConnectiveFormula("<->");
         connectiveformula.addChild(expression);
         connectiveformula.addChild(expression1);
         return new TruthTableEvaluator(connectiveformula.toTruthFunctionalForm()).isAllTrue();
      } else {
         return false;
      }
   }

   boolean isIncomplete() {
      if (this.connective == 0) {
         return true;
      } else {
         int i = this.argTypes.length;

         for (int j = 0; j < i; j++) {
            if (this.getChildNode(j).isIncomplete()) {
               return true;
            }
         }

         return false;
      }
   }

   static String getAnswerSetName(int i, Vector vector) {
      return vector != null && i >= 0 && i < vector.size() ? ((AnswerSet)vector.elementAt(i)).problemName : null;
   }

   static int indexOfAnswerSet(String s, Vector vector) {
      if (s != null && vector != null) {
         int i = vector.size();

         for (int j = 0; j < i; j++) {
            if (s.equals(((AnswerSet)vector.elementAt(j)).problemName)) {
               return j;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   int findDuplicateSolution(int i, SymbolizationProblemSet symbolizationproblemset) {
      int j = indexOfAnswerSet(this.problemName, this.answerGroup);
      if (symbolizationproblemset != null && j != -1) {
         int k = this.answerGroup.size();

         for (int l = 0; l < k; l++) {
            if (l != j) {
               SymbolizationEntry symbolizationentry = (SymbolizationEntry)symbolizationproblemset.getEntry(
                  ((AnswerSet)this.answerGroup.elementAt(l)).problemName
               );
               if (symbolizationentry != null && symbolizationentry.state == 2 && symbolizationentry.answerIndex == i) {
                  return l;
               }
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   int countMatchingNodes(SymbolizationNode symbolizationnode1) {
      MatchCounter matchcounter = new MatchCounter();
      this.matchTree(symbolizationnode1, new Vector(), new Vector(), matchcounter);
      return matchcounter.getCount();
   }

   boolean matchTree(SymbolizationNode symbolizationnode1, NodeMatchListener nodematchlistener) {
      return this.matchTree(symbolizationnode1, new Vector(), new Vector(), nodematchlistener);
   }

   boolean matchTree(SymbolizationNode symbolizationnode1, Vector vector, Vector vector1, NodeMatchListener nodematchlistener) {
      if ((this.connective == 0 || this.connective == symbolizationnode1.connective)
         && (this.connective != 11 || this.atomicMatches(symbolizationnode1, vector, vector1))) {
         if (nodematchlistener != null) {
            nodematchlistener.nodeMatched(this, symbolizationnode1, vector, vector1);
         }

         if (this.isBinder()) {
            vector.addElement(this.getConnectivePanel().label);
            vector1.addElement(symbolizationnode1.getConnectivePanel().label);
         }

         int i = this.argTypes.length;
         boolean flag = true;

         for (int j = 0; j < i; j++) {
            if (!this.getChildNode(j).matchTree(symbolizationnode1.getChildNode(j), vector, vector1, nodematchlistener)) {
               if (nodematchlistener == null) {
                  return false;
               }

               flag = false;
            }
         }

         if (this.isBinder()) {
            vector.setSize(vector.size() - 1);
            vector1.setSize(vector1.size() - 1);
         }

         return flag;
      } else {
         if (nodematchlistener != null) {
            nodematchlistener.nodeMismatched(this, symbolizationnode1, vector, vector1);
         }

         return false;
      }
   }

   boolean atomicMatches(SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
      if (this.outType != symbolizationnode1.outType) {
         return false;
      } else {
         int i = this.argTypes.length;
         if (symbolizationnode1.argTypes.length != i) {
            return false;
         } else {
            for (int j = 0; j < i; j++) {
               if (this.argTypes[j] != symbolizationnode1.argTypes[j]) {
                  return false;
               }
            }

            String s1 = this.getLabel();
            String s = symbolizationnode1.getLabel();
            return this.atomsEquivalent(s1, s, vector, vector1);
         }
      }
   }

   boolean isLowercaseTerm() {
      if (this.connective == 11 && this.outType == 1 && this.argTypes.length == 0) {
         String s = this.getLabel();
         return s != null && s.length() != 0 ? s.equals(s.toLowerCase()) : false;
      } else {
         return false;
      }
   }

   boolean isBinder() {
      return isBinder(this.connective);
   }

   static boolean isBinder(int i) {
      return i == 6 || i == 7 || i == 8;
   }

   String describeType() {
      return this.describeType(null, null);
   }

   String describeType(Vector vector, Vector vector1) {
      if (this.connective == 11) {
         String s = this.getLabel();
         return "the atomic expression \\l" + translateExpression(s, vector, vector1) + "\\l";
      } else {
         return connWords[this.connective];
      }
   }

   static String translateVariable(String s, Vector vector, Vector vector1) {
      return translateVariable(s, vector, vector1, null);
   }

   static String translateVariable(String s, Vector vector, Vector vector1, ExpressionPath expressionpath) {
      String s1 = s;
      int i = vector == null ? -1 : vector.lastIndexOf(s);
      if (i != -1) {
         if (vector1 == null || vector.size() != vector1.size()) {
            return null;
         }

         s1 = (String)vector1.elementAt(i);
      }

      if (vector1 != null && vector1.lastIndexOf(s1) != i) {
         if (expressionpath != null) {
            expressionpath.push(vector1.lastIndexOf(s1));
         }

         return null;
      } else {
         return s1;
      }
   }

   static String translateExpression(String s, Vector vector, Vector vector1) {
      return translateExpression(s, vector, vector1, null);
   }

   static String translateExpression(String s, Vector vector, Vector vector1, ExpressionPath expressionpath) {
      Expression expression;
      try {
         expression = LogicProgram.parseFormula(s, true, false);
      } catch (FormulaParseException formulaparseexception) {
         return s;
      }

      expression = translateExpression(expression, vector, vector1, expressionpath);
      return expression == null ? null : expression.toString();
   }

   static Expression translateExpression(Expression object, Vector vector, Vector vector1, ExpressionPath expressionpath) {
      if (object == null) {
         return null;
      } else {
         int j = vector == null ? 0 : vector.size();
         if (vector1 == null ? j == 0 : vector1.size() == j) {
            for (int i = j - 1; i >= 0; i--) {
               QuantifiedFormula quantifiedformula = new QuantifiedFormula("@");
               quantifiedformula.addChild(new SimpleTerm((String)vector.elementAt(i)));
               quantifiedformula.addChild((Expression)object);
               object = quantifiedformula;
            }

            ((Expression)object).linkVariables();
            ((Expression)object).renameBoundVariables(vector1);
            if (hasBindingConflicts((Expression)object, expressionpath)) {
               return null;
            } else {
               for (int k = 0; k < j; k++) {
                  object = ((Expression)object).getChild(1);
               }

               return ((Expression)object).copy();
            }
         } else {
            return null;
         }
      }
   }

   static boolean hasBindingConflicts(Expression expression, ExpressionPath expressionpath) {
      Vector vector = expression.findMislinkedVariables();
      if (vector == null) {
         return false;
      } else {
         if (expressionpath != null) {
            int i = vector.size();

            for (int j = 0; j < i; j++) {
               Expression expression1 = expression;
               Expression expression2 = ((Expression[])vector.elementAt(j))[0];

               for (int k = 0; expression1 instanceof QuantifiedFormula; expression1 = expression1.getChild(1)) {
                  if (expression1 == expression2) {
                     expressionpath.push(k);
                     break;
                  }

                  k++;
               }
            }
         }

         return true;
      }
   }

   static boolean bindsSameVariable(String s, String s1, Vector vector, Vector vector1) {
      int i = vector1.lastIndexOf(s1);
      int j = vector.lastIndexOf(s);
      return i == j && (i != -1 || s1.equals(s));
   }

   boolean atomsEquivalent(String s, String s1, Vector vector, Vector vector1) {
      Expression expression;
      Expression expression1;
      try {
         expression = LogicProgram.parseFormula(s, true);
         expression1 = LogicProgram.parseFormula(s1, true);
      } catch (FormulaParseException formulaparseexception) {
         expression = null;
         expression1 = null;
      }

      expression1 = translateExpression(expression1, vector1, vector, null);
      return expression1 == null ? s.equals(s1) : expression1.isAlphaEquivalent(expression, new BinderMap());
   }

   static boolean isTranslatable(String s, Vector vector, Vector vector1) {
      return translateVariable(s, vector, vector1) != null;
   }

   void clearErrors() {
      SymbolizationConnectivePanel symbolizationconnectivepanel = this.getConnectivePanel();
      if (symbolizationconnectivepanel != null) {
         symbolizationconnectivepanel.clearError();
      }

      int i = this.argTypes.length;

      for (int j = 0; j < i; j++) {
         this.getChildNode(j).clearErrors();
      }
   }

   static SymbolizationEntry evaluateWork(String s, SymbolizationProblemSet symbolizationproblemset, SymbolizationEntry symbolizationentry) {
      if (symbolizationentry == null) {
         symbolizationentry = new SymbolizationEntry(s, symbolizationproblemset, true);
      }

      new SymbolizationNode(null).evaluateWork(new TaggedRecord(s), symbolizationproblemset, symbolizationentry);
      return symbolizationentry;
   }

   void evaluateWork(TaggedRecord taggedrecord, SymbolizationProblemSet symbolizationproblemset, SymbolizationEntry symbolizationentry) {
      if (symbolizationentry != null) {
         if (!LPSymbolizer.hasWork(taggedrecord)) {
            symbolizationentry.resetState();
         } else {
            this.loadRecord(taggedrecord);
            if (this.isIncomplete()) {
               symbolizationentry.answerIndex = -1;
            } else if ((symbolizationentry.answerIndex = this.findMatchingAnswer()) == -1 && LPSymbolizer.equivalentCounts(this.problemName)) {
               symbolizationentry.answerIndex = this.findEquivalentAnswer();
            }

            if (symbolizationentry.answerIndex != -1 && this.findDuplicateSolution(symbolizationentry.answerIndex, symbolizationproblemset) == -1) {
               symbolizationentry.state = 2;
            } else {
               symbolizationentry.state = 1;
            }
         }
      }
   }

   void copyTextFrom(SymbolizationNode symbolizationnode1) {
      if (symbolizationnode1 != null) {
         this.copyTextFrom(symbolizationnode1, new Vector(), new Vector());
      }
   }

   void copyTextFrom(SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
      if (this.connective == symbolizationnode1.connective) {
         if (this.isBinder()) {
            vector.addElement(this.getConnectivePanel().label);
            vector1.addElement(symbolizationnode1.getConnectivePanel().label);
         }

         int i = this.argTypes.length;

         for (int j = 0; j < i; j++) {
            SymbolizationNode symbolizationnode2 = this.getChildNode(j);
            SymbolizationNode symbolizationnode3 = symbolizationnode1.getChildNode(j);
            symbolizationnode2.textPanel
               .textPane
               .setText(SymbolizationErrorButton.substituteVariables(symbolizationnode3.textPanel.textPane.getText(), vector1, vector));
            symbolizationnode2.validate();
            symbolizationnode2.copyTextFrom(symbolizationnode3, vector, vector1);
         }

         if (this.isBinder()) {
            vector.setSize(vector.size() - 1);
            vector1.setSize(vector1.size() - 1);
         }
      }
   }
}
