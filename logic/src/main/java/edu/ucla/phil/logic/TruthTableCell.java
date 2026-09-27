package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;

class TruthTableCell extends JToggleButton implements LogicConstants {
   String cellCode;
   TruthProblemPanel workPanel;
   Point position;
   TruthValueTree valueTree;
   TruthValueTree mirrorTree;
   boolean isWrong;

   TruthTableCell(String s, TruthProblemPanel truthproblempanel, Point point) {
      super("");
      this.workPanel = truthproblempanel;
      this.position = point;
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.isWrong = false;
      this.valueTree = new TruthValueTree(this, false);
      this.mirrorTree = new TruthValueTree(this, true);
      this.valueTree.setLinkedTree(this.mirrorTree);
      this.mirrorTree.setLinkedTree(this.valueTree);
      this.valueTree.setAlignmentTree(this.mirrorTree);
      this.setBorder(new BevelBorder(0));
      if (truthproblempanel.argument != null) {
         FormulaParseNode formulaparsenode;
         if (point.x < truthproblempanel.premiseCount) {
            formulaparsenode = new FormulaParseNode(truthproblempanel.argument.premises[point.x]);
         } else {
            formulaparsenode = new FormulaParseNode(truthproblempanel.argument.conclusion);
         }

         this.valueTree.setParseNode(formulaparsenode);
      }

      this.loadCode(s, true);
   }

   void loadCode(String s, boolean flag) {
      String s1 = extractValue(s);
      boolean flag1 = hasErrorSign(s);
      int i = flag ? this.workPanel.evaluator.indexOfLetter(this.valueTree.getExpression()) : -1;
      if (i != -1) {
         String s2 = this.getRowAssignment().substring(i, i + 1);
         if (s1.equals("?")) {
            s1 = s2;
            flag1 = false;
         } else {
            flag1 = !s1.equals(s2);
         }
      }

      this.valueTree.setValues(extractTreeValues(s), true);
      this.valueTree.checkValues(true);
      this.setText(s1);
      this.setWrong(flag1);
      this.cellCode = this.valueTree.getValues() + (flag1 ? "-" : "+") + s1;
   }

   String getTreeCode() {
      return this.valueTree.getValues() + (this.valueTree.correct ? "+" : "-") + this.valueTree.label.valueButton.getDisplayText();
   }

   void applyCode(String s) {
      this.setText(extractValue(s));
      this.setWrong(hasErrorSign(s));
      this.cellCode = s;
   }

   void setWrong(boolean flag) {
      Color[] acolor = this.workPanel.module.colors;
      if ((this.isWrong = flag) && !this.workPanel.module.tableErrorsDisabled) {
         if (this.workPanel.module.forPrint) {
            this.setFont(this.workPanel.module.errorFont);
         }

         this.setForeground(acolor[4]);
         this.setBackground(acolor[3]);
      } else {
         if (this.workPanel.module.forPrint) {
            this.setFont(this.workPanel.module.font);
         }

         this.setForeground(acolor[0]);
         this.setBackground(acolor[1]);
      }

      this.invalidate();
   }

   String getCode() {
      return this.valueTree.getValues() + (this.isWrong ? "-" : "+") + this.getText();
   }

   String getRowAssignment() {
      return TruthTableGrid.rowAssignmentString(this.position.y, this.workPanel.letterCount);
   }

   static String extractValue(String s) {
      int i = findSignIndex(s);
      return i == -1 ? "?" : s.substring(i + 1);
   }

   static String extractTreeValues(String s) {
      int i = findSignIndex(s);
      return i == -1 ? s : s.substring(0, i);
   }

   static int findSignIndex(String s) {
      if (s == null) {
         return -1;
      } else {
         int i = s.indexOf(43);
         return i == -1 ? s.indexOf(45) : i;
      }
   }

   static boolean hasErrorSign(String s) {
      return s.indexOf(45) != -1;
   }

   @Override
   protected void fireActionPerformed(ActionEvent actionevent) {
      Point point = this.workPanel.table.selectedCell;
      this.setSelected(point == null || !point.equals(this.position));
      super.fireActionPerformed(actionevent);
   }

   @Override
   public void setSelected(boolean flag) {
      Point point = this.workPanel.table.selectedCell;
      if (flag) {
         if (point != null && !point.equals(this.position)) {
            this.workPanel.table.cells[point.y][point.x].setSelected(false);
         }

         this.workPanel.table.selectedCell = this.position;
         this.workPanel.cellEditorContainer.setVisible(true);
         this.workPanel.cellEditor.setTrees(this.valueTree, this.mirrorTree);
      } else if (point != null && point.equals(this.position)) {
         this.commitTreeValue();
         this.workPanel.table.selectedCell = null;
         this.workPanel.cellEditor.setTrees(null, null);
         this.workPanel.cellEditorContainer.setVisible(false);
      }

      super.setSelected(flag);
   }

   void commitTreeValue() {
      String s = this.getTreeCode();
      if (!hasErrorSign(s) && s.charAt(0) == '?') {
      }

      this.applyCode(s);
   }

   @Override
   public void paintBorder(Graphics graphics) {
      super.paintBorder(graphics);
      if (this.isSelected()) {
         Rectangle rectangle = this.getBounds();
         graphics.drawRect(0, 0, rectangle.width - 1, rectangle.height - 1);
      }
   }
}
