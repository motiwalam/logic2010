package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Hashtable;
import java.util.Vector;

class DerivationBox extends CollapsibleNode implements DerivationNode, DerivationConstants {
   LPDerivation module;
   DerivationBox parentBox;
   DerivationLine showLine;
   DerivationLine cancelLine;
   Rectangle bracketBounds;
   int assumptionType;
   Vector boxVariables;
   boolean strategyConsistent;
   int assumedSide;
   private static final boolean DEBUG = false;

   DerivationBox(DerivationBox derivationbox1) {
      this.parentBox = derivationbox1;
      this.module = derivationbox1.module;
      this.setHeader(this.showLine = new DerivationLine(this, true));
      this.cancelLine = null;
      this.assumptionType = 0;
      this.assumedSide = -1;
      this.resetVariables();
      this.bracketBounds = null;
      this.setLayout(new DerivationBoxLayout(this.module.indent, 0));
      this.setExpanded(true);
   }

   DerivationBox(LPDerivation lpderivation) {
      this.parentBox = null;
      this.module = lpderivation;
      this.setHeader(this.showLine = new DerivationLine(this, true));
      this.cancelLine = null;
      this.assumptionType = 0;
      this.assumedSide = -1;
      this.resetVariables();
      this.bracketBounds = null;
      this.setLayout(new DerivationBoxLayout(lpderivation.indent, 0));
      this.setExpanded(true);
   }

   DerivationBox(DerivationLine derivationline) {
      this.parentBox = derivationline.box;
      this.module = this.parentBox.module;
      this.setHeader(this.showLine = derivationline);
      this.cancelLine = null;
      this.assumptionType = 0;
      this.assumedSide = -1;
      this.resetVariables();
      this.bracketBounds = null;
      this.setLayout(new DerivationBoxLayout(this.module.indent, 0));
      this.setExpanded(true);
   }

   @Override
   public void setToggle(Component object) {
      if (object != null) {
         object = new BoxToggleButton(this);
      }

      super.setToggle((Component)object);
   }

   @Override
   public DerivationBox getEnclosingBox() {
      return this.parentBox;
   }

   @Override
   public void paintBorder(Graphics graphics) {
      super.paintBorder(graphics);
      this.drawBracket(graphics);
   }

   void drawBracket(Graphics graphics) {
      Rectangle rectangle = LogicProgram.boundsRelativeTo(this, this.module.problem);
      this.drawBracket(graphics, rectangle);
   }

   @Override
   public void setFont(Font font) {
      super.setFont(font);
      int i = this.getContentCount();

      for (int j = 0; j < i; j++) {
         this.getNode(j).setFont(font);
      }

      this.setLayout(new DerivationBoxLayout(this.module.indent, 0));
   }

   @Override
   public void applyColors(Color[] acolor) {
      int i = this.getContentCount();

      for (int j = 0; j < i; j++) {
         this.getNode(j).applyColors(acolor);
      }
   }

   @Override
   public void layoutColumns() {
      int i = this.getContentCount();

      for (int j = 0; j < i; j++) {
         this.getNode(j).layoutColumns();
      }
   }

   void expandAll() {
      this.setExpanded(true);
      int i = this.getContentCount();

      for (int j = 0; j < i; j++) {
         DerivationNode derivationnode = this.getNode(j);
         if (derivationnode instanceof DerivationBox) {
            ((DerivationBox)derivationnode).expandAll();
         }
      }
   }

   void highlightShowLabel(boolean flag) {
      this.showLine.showLabel.setForeground(flag ? this.module.colors[1] : this.module.colors[0]);
      this.showLine.showLabel.setBackground(flag ? this.module.colors[0] : this.module.colors[2]);
   }

   void drawBracket(Graphics graphics, Rectangle rectangle) {
      if (this.bracketBounds != null) {
         byte b0 = 2;
         byte b1 = 1;
         int i = this.bracketBounds.x - rectangle.x - b1;
         int j = this.bracketBounds.y - rectangle.y - b1;
         int k = this.bracketBounds.width + 1 * b1;
         int l = this.bracketBounds.height + 2 * b1;

         for (int i1 = 0; i1 < b0; i1++) {
            graphics.drawLine(i, j, i + k - 1, j);
            graphics.drawLine(i, j, i, j + l);
            graphics.drawLine(i, j + l, i + k - 1, j + l);
            i--;
            j--;
            k++;
            l += 2;
         }
      }
   }

   @Override
   int toComponentIndex(int i) {
      int j = 0;
      int k = this.getComponentCount();

      while (true) {
         while (j >= k || !(this.getComponent(j) instanceof ExpandToggleButton)) {
            if (j >= k) {
               return k;
            }

            if (i == 0) {
               return j;
            }

            i--;
            j++;
         }

         j++;
      }
   }

   @Override
   int toContentIndex(int i) {
      int j = 0;
      int k = this.getComponentCount();
      if (k > i) {
         k = i;
      }

      for (int l = 0; l < k; l++) {
         if (!(this.getComponent(l) instanceof ExpandToggleButton)) {
            j++;
         }
      }

      return j;
   }

   @Override
   int getContentCount() {
      int i = this.getComponentCount();
      int j = i;

      while (--j >= 0) {
         if (this.getComponent(j) instanceof ExpandToggleButton) {
            i--;
         }
      }

      return i;
   }

   @Override
   public int getLineNumber() {
      return this.showLine.getLineNumber();
   }

   @Override
   public DerivationNode findLine(int i) {
      if (i <= 0) {
         return null;
      } else {
         int j = 1;
         int l = this.getContentCount() - 1;
         if (l < j) {
            if (LogicProgram.debug) {
               System.out.println(LPDerivation.trimTitle(this.module.problemTitle) + ": bad line number (" + i + ")");
            }

            return null;
         } else {
            DerivationNode derivationnode;
            int i1;
            if ((i1 = (derivationnode = this.getNode(j)).getLineNumber()) == i) {
               return derivationnode;
            } else if (i < i1) {
               return null;
            } else {
               DerivationNode derivationnode1 = derivationnode;
               if ((i1 = (derivationnode = this.getNode(l)).getLineNumber()) == i) {
                  return derivationnode;
               } else if (i > i1) {
                  return derivationnode.findLine(i);
               } else {
                  while (l - j > 1) {
                     int k;
                     if ((i1 = (derivationnode = this.getNode(k = (j + l) / 2)).getLineNumber()) == i) {
                        return derivationnode;
                     }

                     if (i < i1) {
                        l = k;
                     } else {
                        j = k;
                        derivationnode1 = derivationnode;
                     }
                  }

                  return derivationnode1.findLine(i);
               }
            }
         }
      }
   }

   DerivationLine insertLine(int i) {
      DerivationLine derivationline = new DerivationLine(this, false);
      this.add(derivationline, i);
      derivationline.layoutColumns();
      this.module.setWidths(false);
      this.module.problem.renumberAll();
      return derivationline;
   }

   DerivationBox insertBox(int i) {
      DerivationBox derivationbox1 = new DerivationBox(this);
      this.add(derivationbox1, i);
      derivationbox1.layoutColumns();
      this.module.setWidths(false);
      this.module.problem.renumberAll();
      return derivationbox1;
   }

   public void renumberAll() {
      this.renumberLines(0);
      this.refreshReferenceNumbers();
   }

   @Override
   public void refreshReferenceNumbers() {
      int i = this.getContentCount();

      for (int j = 0; j < i; j++) {
         this.getNode(j).refreshReferenceNumbers();
      }
   }

   @Override
   public int renumberLines(int i) {
      int j = this.getContentCount();

      for (int k = 0; k < j; k++) {
         i = this.getNode(k).renumberLines(i);
      }

      return i;
   }

   @Override
   public int getMaxBoxDepth(boolean flag) {
      if (flag && !this.isExpanded()) {
         return 0;
      } else {
         int i = this.getContentCount();
         int j = 0;

         for (int k = 1; k < i; k++) {
            int l = this.getNode(k).getMaxBoxDepth(flag) + 1;
            if (l > j) {
               j = l;
            }
         }

         return j;
      }
   }

   void resetVariables() {
      this.boxVariables = null;
      int i = this.getContentCount();

      for (int j = 1; j < i; j++) {
         DerivationNode derivationnode = this.getNode(j);
         if (derivationnode instanceof DerivationBox) {
            ((DerivationBox)derivationnode).resetVariables();
         }
      }
   }

   @Override
   public int getBoxDepth() {
      return this.parentBox == null ? 0 : this.parentBox.getBoxDepth() + 1;
   }

   @Override
   public int countLines(boolean flag) {
      int i = 1;
      if (!flag || this.isExpanded()) {
         int j = this.getContentCount();

         for (int k = 1; k < j; k++) {
            i += this.getNode(k).countLines(flag);
         }
      }

      return i;
   }

   @Override
   public DerivationLine insertLineAfter() {
      return this.showLine.insertLineAfter();
   }

   @Override
   public void deleteNode(boolean flag) {
      if (flag) {
         int i = this.getContentCount();

         while (--i > 0) {
            this.getNode(i).deleteNode(true);
         }
      }

      this.showLine.deleteNode(false);
      this.module.problem.renumberAll();
   }

   DerivationNode getNode(int i) {
      return (DerivationNode)this.getComponent(this.toComponentIndex(i));
   }

   @Override
   public void setFormulaText(String s) {
      this.showLine.setFormulaText(s);
   }

   @Override
   public String getFormulaText(boolean flag) {
      return this.showLine.getFormulaText(flag);
   }

   @Override
   public void setAnnotationText(String s) {
      this.showLine.setAnnotationText(s);
   }

   @Override
   public String getAnnotationText(boolean flag) {
      return this.showLine.getAnnotationText(flag);
   }

   @Override
   public void setMessageText(String s, boolean flag) {
      this.showLine.setMessageText(s, flag);
   }

   @Override
   public void showMessage(String s) {
      this.showLine.showMessage(s);
   }

   void showMessage(String s, int i) {
      this.showLine.showMessage(s, i);
   }

   @Override
   public void showMessage(String s, Hashtable hashtable) {
      this.showLine.showMessage(s, hashtable);
   }

   @Override
   public void clearMessage() {
      this.showLine.clearMessage();
   }

   void clearMessage(int i) {
      this.showLine.clearMessage();
   }

   @Override
   public DerivationLineEditor getFormulaEditor() {
      return this.showLine.formulaEditor;
   }

   @Override
   public DerivationLineEditor getAnnotationEditor() {
      return null;
   }

   int indexOfChild(Component component) {
      int i = this.getComponentCount();
      Component[] acomponent = this.getComponents();

      for (int j = 0; j < i; j++) {
         if (acomponent[j] == component) {
            return j;
         }
      }

      return -1;
   }

   @Override
   public int getIndexInBox() {
      return this.parentBox == null ? -1 : this.parentBox.toContentIndex(this.parentBox.indexOfChild(this));
   }

   void ensureFocusVisible() {
      DerivationLineEditor derivationlineeditor = this.module.focus;
      if (derivationlineeditor != null && !derivationlineeditor.line.areEnclosingBoxesExpanded()) {
         this.focusEditor(false);
      }
   }

   @Override
   public void focusEditor(boolean flag) {
      this.showLine.focusEditor(false);
   }

   @Override
   public void requestFocus() {
   }

   @Override
   public DerivationNode getNextNode(boolean flag) {
      return this.showLine.getNextNode(flag);
   }

   @Override
   public DerivationNode getPreviousNode(boolean flag) {
      return this.showLine.getPreviousNode(flag);
   }

   @Override
   public DerivationNode getHeadNode() {
      return this;
   }

   @Override
   public boolean isShowLine() {
      return true;
   }

   @Override
   public boolean isCancelLine() {
      return false;
   }

   @Override
   public boolean areEnclosingBoxesExpanded() {
      return this.showLine.areEnclosingBoxesExpanded();
   }

   @Override
   public void expandEnclosingBoxes() {
      this.showLine.expandEnclosingBoxes();
   }

   @Override
   public Rectangle getBoundsInProblemPanel() {
      return this.showLine.getBoundsInProblemPanel();
   }

   @Override
   public void moveIntoPreviousBox() {
      int i = this.getIndexInBox();
      if (i > 0) {
         DerivationNode derivationnode = this.parentBox.getNode(i - 1);
         if (derivationnode instanceof DerivationBox) {
            DerivationLineEditor derivationlineeditor = this.module.focus;
            if (derivationlineeditor != null && derivationlineeditor.line == this.showLine) {
               this.parentBox.highlightShowLabel(false);
            }

            this.parentBox.remove(this);
            ((DerivationBox)derivationnode).add(this, -1);
            this.parentBox = (DerivationBox)derivationnode;
            if (derivationlineeditor != null && derivationlineeditor.line == this.showLine) {
               this.parentBox.highlightShowLabel(true);
            }
         }
      }
   }

   @Override
   public void moveOutOfBox() {
      if (this.parentBox != null) {
         int i = this.getIndexInBox();
         if (i == this.parentBox.getContentCount() - 1) {
            DerivationBox derivationbox1 = this.parentBox.parentBox;
            if (derivationbox1 != null) {
               DerivationLineEditor derivationlineeditor = this.module.focus;
               if (derivationlineeditor != null && derivationlineeditor.line == this.showLine) {
                  this.parentBox.highlightShowLabel(false);
               }

               this.parentBox.remove(this);
               derivationbox1.add(this, derivationbox1.toComponentIndex(this.parentBox.getIndexInBox() + 1));
               this.parentBox = derivationbox1;
               if (derivationlineeditor != null && derivationlineeditor.line == this.showLine) {
                  this.parentBox.highlightShowLabel(true);
               }
            }
         }
      }
   }

   @Override
   public void addReferrer(LineReference linereference) {
      this.showLine.addReferrer(linereference);
   }

   @Override
   public void removeReferrer(LineReference linereference) {
      this.showLine.removeReferrer(linereference);
   }

   @Override
   public void detachReferrers() {
      this.showLine.detachReferrers();
   }

   @Override
   public void retargetReferrers() {
      this.showLine.retargetReferrers();
   }

   @Override
   public boolean checkSyntax() {
      boolean flag = true;
      int i = this.getContentCount();

      for (int j = 0; j < i; j++) {
         flag = this.getNode(j).checkSyntax() && flag;
      }

      return flag;
   }

   @Override
   public Expression getFormula() {
      return this.showLine.getFormula();
   }

   @Override
   public String encodeWork() {
      String s = "";
      int i = this.getContentCount();

      for (int j = 0; j < i; j++) {
         s = s + this.getNode(j).encodeWork();
      }

      if (this.cancelLine == null) {
         s = s + "`=";
      }

      return s;
   }

   @Override
   public String encodeMessages() {
      String s = "";
      int i = this.getContentCount();

      for (int j = 0; j < i; j++) {
         s = s + this.getNode(j).encodeMessages();
      }

      return s;
   }

   @Override
   public boolean verify() {
      int i = this.module.setPhase(4);
      if (this.parentBox != null) {
         this.parentBox.strategyConsistent = false;
      }

      this.strategyConsistent = true;

      try {
         int j = this.getContentCount();
         this.clearMessage();
         boolean flag;
         if (this.parentBox != null) {
            this.assumptionType = 0;
            this.assumedSide = -1;
            flag = this.showLine.verify();
            if (this.cancelLine == null) {
               this.showMessage("dererr055");
               this.module.complete = false;
               flag = false;
            }
         } else {
            flag = this.module.conclusion != null;
            if (!flag) {
               if (this.module.premises.length == 0) {
                  this.showMessage("dererr052");
               } else {
                  this.showMessage("dererr053");
               }
            } else {
               boolean flag1 = false;
               int k = 1;

               while (k < j && !(flag1 = this.module.isConclusion(this.getNode(k).getFormula()))) {
                  k++;
               }

               if (!flag1) {
                  this.showMessage("dererr054");
                  flag = false;
               }
            }
         }

         this.addShowVariablesToModule();

         for (int l = 1; l < j; l++) {
            flag &= this.getNode(l).verify();
            if (this.module.aborted()) {
               return false;
            }
         }

         this.exportVariablesToParent();
         return flag;
      } finally {
         this.module.setPhase(i);
      }
   }

   void addShowVariablesToModule() {
      if (this.parentBox != null) {
         if (this.module.varNames == null) {
            this.module.varNames = new Vector();
         }

         Expression expression = this.showLine.getFormula();
         if (expression != null) {
            expression.collectTermSymbols(this.module.varNames, null);
         }
      }
   }

   void exportVariablesToParent() {
      if (this.parentBox != null) {
         if (this.parentBox.boxVariables == null) {
            this.parentBox.boxVariables = new Vector();
         }

         Expression expression = this.showLine.getFormula();
         if (expression != null) {
            expression.collectTermSymbols(null, this.parentBox.boxVariables);
         }
      }
   }

   boolean isUniversalVariableFree() {
      Expression expression = this.showLine.getFormula();
      if (expression != null && expression.getSymbol().equals("@")) {
         String s = ((SimpleTerm)expression.getChild(0)).getSymbol();

         for (DerivationBox derivationbox1 = this.parentBox; derivationbox1 != null; derivationbox1 = derivationbox1.parentBox) {
            if (derivationbox1.boxVariables != null && derivationbox1.boxVariables.contains(s)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
