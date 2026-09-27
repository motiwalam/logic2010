package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class TruthValueTreeLabel extends SizedPanel implements PropertyChangeListener, LogicConstants, AnchorProvider {
   static String[] SYMBOLS = LogicProgram.symbols;
   TruthValueTree tree;
   FormulaParseNode parseNode;
   LogicTextArea formulaText;
   ChoiceButton valueButton;

   TruthValueTreeLabel(TruthValueTree truthvaluetree, FormulaParseNode formulaparsenode) {
      this.tree = truthvaluetree;
      this.parseNode = formulaparsenode;
      this.setLayout(new StackedPairLayout(truthvaluetree.compact ? 0 : 4));
      this.add(this.formulaText = new LogicTextArea(LogicProgram.translateSymbols(this.getDisplayText(), maggie, SYMBOLS)));
      this.formulaText.setSize(this.formulaText.getPreferredSize());
      this.add(this.valueButton = new ChoiceButton(new String[]{"T", "F"}, "?"));
      this.valueButton.setSizeToWidest(true);
      this.valueButton.setBorder(new MarginBevelBorder(0, Color.gray, Color.black));
      this.valueButton.setMargin(new Insets(2, 2, 2, 2));
      this.valueButton.addPropertyChangeListener(this);
   }

   TruthValueTreeLabel(TruthValueTree truthvaluetree, String s, boolean flag) {
      this.tree = truthvaluetree;
      this.setLayout(new StackedPairLayout(truthvaluetree.compact ? 0 : 4));
      this.parseNode = flag ? new FormulaParseNode(s) : null;
      if (this.parseNode != null && this.parseNode.expression == null) {
         this.parseNode = null;
      }

      this.add(this.formulaText = new LogicTextArea(LogicProgram.translateSymbols(s, maggie, SYMBOLS)));
      this.add(this.valueButton = new ChoiceButton(new String[]{"T", "F"}, "?"));
      this.valueButton.setSizeToWidest(true);
      this.valueButton.setBorder(new MarginBevelBorder(0, Color.gray, Color.black));
      this.valueButton.setMargin(new Insets(2, 2, 2, 2));
      this.valueButton.addPropertyChangeListener(this);
   }

   TruthValueTreeLabel(TruthValueTree truthvaluetree, String s) {
      this(truthvaluetree, s, false);
   }

   IntervalSet getOperatorRanges() {
      if (this.parseNode == null) {
         return new IntervalSet();
      } else {
         FormulaParseNode formulaparsenode = this.parseNode.getRoot(true);
         if (this.tree.compact) {
            IntervalSet intervalset2 = this.getRawOperatorRanges();
            IntervalSet intervalset1 = this.widenRangesToParens(intervalset2.copy(true));
            return intervalset2.shift(-intervalset1.boundaries[0]);
         } else {
            IntervalSet intervalset = this.parseNode.expression instanceof ConnectiveFormula ? this.parseNode.getOperatorRanges() : this.parseNode.getRange();
            FormulaParseNode.convertRanges(formulaparsenode.strippedIndex, formulaparsenode.parenDepth, intervalset, false);
            return intervalset.shift(-this.parseNode.getTextRange()[0]);
         }
      }
   }

   Point getCharLocation(int i) {
      Point point = this.formulaText.getCharLocation(i);
      if (point == null) {
         return new Point(0, 0);
      } else {
         return point == null ? new Point(0, 0) : point;
      }
   }

   IntervalSet getOperatorPixelRanges() {
      IntervalSet intervalset = this.getOperatorRanges();
      int i = intervalset.count;
      LogicProgram.translateSymbols(this.getDisplayText(), maggie, SYMBOLS, intervalset.boundaries);

      for (int j = 0; j < i; j++) {
         intervalset.boundaries[j] = this.getCharLocation(intervalset.boundaries[j]).x;
      }

      return intervalset;
   }

   int getConnectiveCenterX() {
      if (this.formulaText != null && this.formulaText.getTextLength() != 0) {
         IntervalSet intervalset = this.getOperatorPixelRanges();
         return intervalset.count < 2
            ? (this.getCharLocation(0).x + this.getCharLocation(this.formulaText.getTextLength()).x) / 2
            : (intervalset.boundaries[0] + intervalset.boundaries[1]) / 2;
      } else {
         return 0;
      }
   }

   @Override
   public int getAnchorX() {
      int i = this.valueButton.getPreferredSize().width / 2;
      int j = this.getConnectiveCenterX();
      return i > j ? i : j;
   }

   IntervalSet getRawOperatorRanges() {
      if (this.parseNode == null) {
         return null;
      } else {
         FormulaParseNode formulaparsenode = this.parseNode.getRoot(true);
         IntervalSet intervalset = this.parseNode.expression instanceof ConnectiveFormula ? this.parseNode.getOperatorRanges() : this.parseNode.getRange();
         FormulaParseNode.convertRanges(formulaparsenode.strippedIndex, formulaparsenode.parenDepth, intervalset, false);
         return intervalset;
      }
   }

   IntervalSet widenRangesToParens(IntervalSet intervalset) {
      if (this.parseNode == null) {
         return intervalset;
      } else {
         FormulaParseNode formulaparsenode = this.parseNode.getRoot(true);
         int[] aint = formulaparsenode.strippedIndex;
         int[] aint1 = formulaparsenode.parenDepth;
         int i = aint.length - 1;

         for (int b0 = 0; b0 < intervalset.count - 1; b0 += 2) {
            int j = intervalset.boundaries[b0];
            int k = intervalset.boundaries[b0 + 1];
            int l = aint[j];
            int i1 = aint[k];

            while (j > 0 && aint[j - 1] == l && aint1[j - 1] <= aint1[j]) {
               j--;
            }

            while (k < i && aint[k + 1] == i1 && aint1[k + 1] <= aint1[k]) {
               k++;
            }

            intervalset.boundaries[b0] = j;
            intervalset.boundaries[b0 + 1] = k;
         }

         return intervalset;
      }
   }

   String getDisplayText() {
      if (this.parseNode == null) {
         return "";
      } else {
         return !this.tree.compact
            ? this.parseNode.toString()
            : this.widenRangesToParens(this.getRawOperatorRanges()).selectChars(this.parseNode.getRoot().text);
      }
   }

   @Override
   public void propertyChange(PropertyChangeEvent propertychangeevent) {
      if (propertychangeevent.getPropertyName().equals("text")) {
         this.tree.propagateValue();
         this.tree.getRootTree().checkValues(true);
         this.tree.cell.workPanel.cellEditor.invalidate();
         if (this.tree.errorShown && !this.tree.cell.workPanel.loading) {
            LPTruthAnalysis lptruthanalysis = this.tree.cell.workPanel.module;
            if (!lptruthanalysis.treeErrorsDisabled && !lptruthanalysis.noBeep) {
               Toolkit.getDefaultToolkit().beep();
            }

            lptruthanalysis.errorCount++;
         }
      }
   }

   @Override
   protected void paintComponent(Graphics graphics) {
      super.paintComponent(graphics);
      int i = ((StackedPairLayout)this.getLayout()).getVgap();
      if (i != 0) {
         int j = this.getComponentCount();
         if (j >= 2) {
            graphics.setColor(this.getForeground());
            Rectangle rectangle = this.getComponent(1).getBounds();
            int k = rectangle.x + rectangle.width / 2;
            int l = rectangle.y;
            graphics.drawLine(k, l - i, k, l);
         }
      }
   }
}
