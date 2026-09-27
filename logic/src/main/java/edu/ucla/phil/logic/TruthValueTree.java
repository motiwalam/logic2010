package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.BorderFactory;

class TruthValueTree extends CellPanel implements LogicConstants, ModuleComponentMarker {
   TruthValueTree parent = null;
   TruthValueTree linkedTree = null;
   TruthValueTree alignmentTree = null;
   TreeNodeLayout treeLayout;
   FormulaParseNode parseNode = null;
   TruthValueTreeLabel label;
   TruthTableCell cell;
   boolean errorShown = false;
   boolean correct = true;
   boolean compact;

   TruthValueTree(TruthTableCell truthtablecell, boolean flag) {
      this.compact = flag;
      this.cell = truthtablecell;
      this.setLayout(this.treeLayout = (TreeNodeLayout)(flag ? new InlineTreeNodeLayout() : new TreeNodeLayout()));
      this.setAnchor(this.label = new TruthValueTreeLabel(this, ""));
      this.add(this.label);
   }

   synchronized void setFormula(String s) {
      this.setFormula(s, this);
      this.refreshLinks(this);
   }

   void setFormula(String s, TruthValueTree truthvaluetree1) {
      if (truthvaluetree1 != null && this.linkedTree != null && truthvaluetree1 != this.linkedTree) {
         this.linkedTree.setFormula(s, truthvaluetree1);
      }

      this.setParseNode(new FormulaParseNode(s), null);
      if (this.parseNode == null) {
         this.setAnchor(this.label = new TruthValueTreeLabel(this, s));
         this.add(this.label);
      }

      this.setBorder(BorderFactory.createEtchedBorder());
   }

   synchronized void setParseNode(FormulaParseNode formulaparsenode) {
      this.setParseNode(formulaparsenode, this);
      this.refreshLinks(this);
   }

   void setAnchor(AnchorProvider anchorprovider) {
      this.treeLayout.setAnchor(anchorprovider);
   }

   void setParseNode(FormulaParseNode formulaparsenode, TruthValueTree truthvaluetree1) {
      if (truthvaluetree1 != null && this.linkedTree != null && truthvaluetree1 != this.linkedTree) {
         this.linkedTree.setParseNode(formulaparsenode, truthvaluetree1);
      }

      this.setAnchor(null);
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         this.remove(this.getComponent(0));
      }

      this.validate();
      if (formulaparsenode != null && formulaparsenode.expression != null) {
         this.parseNode = formulaparsenode;
         this.setAnchor(this.label = new TruthValueTreeLabel(this, formulaparsenode));
         this.add(this.label);
         if (formulaparsenode.expression instanceof ConnectiveFormula) {
            i = formulaparsenode.getChildCount();

            for (int k = 0; k < i; k++) {
               TruthValueTree truthvaluetree2;
               this.addChildTree(truthvaluetree2 = new TruthValueTree(this.cell, this.compact));
               truthvaluetree2.setParseNode(formulaparsenode.getChild(k), null);
            }
         }
      } else {
         this.parseNode = null;
      }
   }

   synchronized String setValues(String s, boolean flag) {
      return this.setValues(s, flag, this);
   }

   String setValues(String s, boolean flag, TruthValueTree truthvaluetree1) {
      if (truthvaluetree1 != null && this.linkedTree != null && truthvaluetree1 != this.linkedTree) {
         this.linkedTree.setValues(s, flag, truthvaluetree1);
      }

      TruthProblemPanel truthproblempanel = flag ? this.cell.workPanel : null;
      Point point = flag ? this.cell.position : null;
      char c0 = '?';
      boolean flag1 = false;
      if (truthproblempanel != null && truthproblempanel.evaluator != null && point != null) {
         int i = truthproblempanel.evaluator.indexOfLetter(this.getExpression());
         if (i != -1) {
            c0 = TruthTableGrid.rowAssignmentString(point.y, truthproblempanel.letterCount).charAt(i);
            flag1 = true;
         }
      }

      if (s != null && s.length() != 0) {
         if (c0 == '?') {
            c0 = s.charAt(0);
         }

         s = s.substring(1);
      }

      this.label.valueButton.setLocked(flag1);
      Color[] acolor = this.cell.workPanel.module.colors;
      this.label.valueButton.setColors(acolor[0], acolor[flag1 ? 2 : 1]);
      this.errorShown = false;
      this.label.valueButton.setSelectedIndex("?TF".indexOf(c0) - 1);
      int j = this.getChildTreeCount();

      for (int k = 0; k < j; k++) {
         s = this.getChildTree(k).setValues(s, flag, null);
      }

      return s;
   }

   void setLinkedTree(TruthValueTree truthvaluetree1) {
      this.linkedTree = truthvaluetree1;
      int i = this.getChildTreeCount();

      for (int j = 0; j < i; j++) {
         this.getChildTree(j).setLinkedTree(truthvaluetree1 == null ? null : truthvaluetree1.getChildTree(j));
      }
   }

   void setAlignmentTree(TruthValueTree truthvaluetree1) {
      this.alignmentTree = truthvaluetree1;
      int i = this.getChildTreeCount();

      for (int j = 0; j < i; j++) {
         this.getChildTree(j).setAlignmentTree(truthvaluetree1 == null ? null : truthvaluetree1.getChildTree(j));
      }
   }

   boolean isAlignedWith(TruthValueTree truthvaluetree1) {
      if (this.alignmentTree != truthvaluetree1) {
         return false;
      } else {
         if (truthvaluetree1 != null) {
            int i = this.getChildTreeCount();

            for (int j = 0; j < i; j++) {
               if (!this.getChildTree(j).isAlignedWith(truthvaluetree1.getChildTree(j))) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   void refreshLinks(TruthValueTree truthvaluetree1) {
      if (truthvaluetree1 != null && this.linkedTree != null && truthvaluetree1 != this.linkedTree) {
         this.linkedTree.refreshLinks(truthvaluetree1);
      }

      this.setLinkedTree(this.linkedTree);
      this.setAlignmentTree(this.alignmentTree);
   }

   @Override
   public synchronized void layout() {
      if (this.alignmentTree != null) {
         this.alignmentTree.layout();
      }

      super.layout();
   }

   boolean hasEnteredValues() {
      if (!this.label.valueButton.isLocked() && this.label.valueButton.getSelectedIndex() != -1) {
         return true;
      } else {
         int i = this.getChildTreeCount();

         for (int j = 0; j < i; j++) {
            if (this.getChildTree(j).hasEnteredValues()) {
               return true;
            }
         }

         return false;
      }
   }

   String getValues() {
      return this.appendValues("");
   }

   String appendValues(String s) {
      String s1 = s + this.getValueChar();
      int i = this.getChildTreeCount();

      for (int j = 0; j < i; j++) {
         s1 = this.getChildTree(j).appendValues(s1);
      }

      return s1;
   }

   synchronized void showError(boolean flag) {
      this.showError(flag, this);
   }

   void showError(boolean flag, TruthValueTree truthvaluetree1) {
      LPTruthAnalysis lptruthanalysis = this.cell.workPanel.module;
      if (truthvaluetree1 != null && this.linkedTree != null && truthvaluetree1 != this.linkedTree) {
         this.linkedTree.showError(flag, truthvaluetree1);
      }

      Color[] acolor = lptruthanalysis.colors;
      if (this.errorShown != flag && !this.label.valueButton.isLocked() && !lptruthanalysis.treeErrorsDisabled) {
         if (this.errorShown = flag) {
            if (lptruthanalysis.forPrint) {
               this.label.valueButton.setFont(lptruthanalysis.errorFont);
            } else {
               this.label.valueButton.setColors(acolor[4], acolor[3]);
            }
         } else if (lptruthanalysis.forPrint) {
            this.label.valueButton.setFont(lptruthanalysis.font);
         } else {
            this.label.valueButton.setColors(acolor[0], acolor[1]);
         }

         this.validate();
      }
   }

   synchronized boolean checkValues(boolean flag) {
      return this.checkValues(flag, this);
   }

   boolean checkValues(boolean flag, TruthValueTree truthvaluetree1) {
      if (truthvaluetree1 != null && this.linkedTree != null && truthvaluetree1 != this.linkedTree) {
         this.linkedTree.checkValues(flag, truthvaluetree1);
      }

      if (this.parseNode == null) {
         return false;
      } else {
         TruthProblemPanel truthproblempanel = flag ? this.cell.workPanel : null;
         Point point = flag ? this.cell.position : null;
         char c0 = this.getValueChar();
         this.correct = true;
         if (truthproblempanel != null && truthproblempanel.evaluator != null && point != null) {
            int i = truthproblempanel.evaluator.indexOfLetter(this.parseNode.expression);
            String s = TruthTableGrid.rowAssignmentString(point.y, truthproblempanel.letterCount);
            if (i != -1 && s.charAt(i) != c0) {
               this.correct = false;
            }
         }

         if (this.correct && c0 != '?') {
            String s1 = this.getConnective();
            if (s1.equals("~")) {
               char c2 = this.getChildTree(0).getValueChar();
               this.correct = c2 != '?' && c0 == 'T' == (c2 == 'F');
            } else if (s1.equals("->")) {
               char c3 = this.getChildTree(0).getValueChar();
               char c1 = this.getChildTree(1).getValueChar();
               this.correct = c3 != '?' && c1 != '?' && c0 == 'T' == (c3 == 'F' | c1 == 'T');
               if (!this.correct && !this.cell.workPanel.module.completeAllNodes) {
                  this.correct = c0 == 'T' && c3 == 'F' | c1 == 'T';
               }
            } else if (s1.equals("<->")) {
               char c4 = this.getChildTree(0).getValueChar();
               char c7 = this.getChildTree(1).getValueChar();
               this.correct = c4 != '?' && c7 != '?' && c0 == 'T' == (c4 == 'T' == (c7 == 'T'));
            } else if (s1.equals("&")) {
               char c5 = this.getChildTree(0).getValueChar();
               char c8 = this.getChildTree(1).getValueChar();
               this.correct = c5 != '?' && c8 != '?' && c0 == 'F' == (c5 == 'F' | c8 == 'F');
               if (!this.correct && !this.cell.workPanel.module.completeAllNodes) {
                  this.correct = c0 == 'F' && c5 == 'F' | c8 == 'F';
               }
            } else if (s1.equals("|")) {
               char c6 = this.getChildTree(0).getValueChar();
               char c9 = this.getChildTree(1).getValueChar();
               this.correct = c6 != '?' && c9 != '?' && c0 == 'T' == (c6 == 'T' | c9 == 'T');
               if (!this.correct && !this.cell.workPanel.module.completeAllNodes) {
                  this.correct = c0 == 'T' && c6 == 'T' | c9 == 'T';
               }
            }
         }

         this.showError(!this.correct, null);
         int j = this.getChildTreeCount();

         for (int k = 0; k < j; k++) {
            if (!this.getChildTree(k).checkValues(flag, null)) {
               this.correct = false;
            }
         }

         return this.correct;
      }
   }

   synchronized void propagateValue() {
      if (this.linkedTree != null) {
         this.linkedTree.setValueIndex(this.label.valueButton.getSelectedIndex(), this);
      }
   }

   void setValueIndex(int i, TruthValueTree truthvaluetree1) {
      if (truthvaluetree1 != null && this.linkedTree != null && truthvaluetree1 != this.linkedTree) {
         this.linkedTree.setValueIndex(i, truthvaluetree1);
      }

      this.label.valueButton.setSelectedIndex(i);
   }

   Expression getExpression() {
      return this.parseNode == null ? null : this.parseNode.expression;
   }

   String getConnective() {
      Expression expression = this.getExpression();
      return expression == null ? null : expression.getSymbol();
   }

   char getValueChar() {
      return this.label.valueButton.getDisplayText().charAt(0);
   }

   TruthValueTree addChildTree(TruthValueTree truthvaluetree1) {
      if (truthvaluetree1 != null) {
         this.add(truthvaluetree1.detach());
         truthvaluetree1.parent = this;
      }

      return truthvaluetree1;
   }

   TruthValueTree detach() {
      if (this.parent != null) {
         this.parent.remove(this);
         this.parent = null;
      }

      return this;
   }

   int getChildTreeCount() {
      return this.getComponentCount() - 1;
   }

   TruthValueTree getChildTree(int i) {
      Component component = this.getComponent(i + 1);
      return component instanceof TruthValueTree ? (TruthValueTree)component : null;
   }

   TruthValueTree getRootTree() {
      TruthValueTree truthvaluetree1 = this;

      while (truthvaluetree1.parent != null) {
         truthvaluetree1 = truthvaluetree1.parent;
      }

      return truthvaluetree1;
   }

   @Override
   protected void paintComponent(Graphics graphics) {
      super.paintComponent(graphics);
      if (!this.compact) {
         int i = this.getComponentCount();
         if (i != 0 && this.treeLayout.anchor != null) {
            Rectangle rectangle = this.getComponent(0).getBounds();
            graphics.setColor(this.getForeground());
            int l;
            int j1;
            int i1 = j1 = l = rectangle.x + this.treeLayout.anchor.getAnchorX();
            int k = rectangle.y + rectangle.height;
            int j = this.treeLayout.getVgap();

            for (int k1 = 1; k1 < i; k1++) {
               Component component = this.getComponent(k1);
               if (component instanceof TruthValueTree) {
                  component.validate();
               }

               rectangle = component instanceof TruthValueTree ? LogicProgram.boundsRelativeTo(((TruthValueTree)component).label, this) : component.getBounds();
               int l1 = rectangle.x + rectangle.width / 2;
               if (l1 < i1) {
                  i1 = l1;
               }

               if (l1 > j1) {
                  j1 = l1;
               }

               graphics.drawLine(l1, k + j / 2, l1, k + j);
            }

            if (i > 1) {
               graphics.drawLine(l, k, l, k + j / 2);
               graphics.drawLine(i1, k + j / 2, j1, k + j / 2);
            }
         }
      }
   }
}
