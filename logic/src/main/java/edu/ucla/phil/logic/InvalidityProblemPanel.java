package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;

class InvalidityProblemPanel extends SizedPanel implements InvalidityConstants, ChoiceListener {
   static String[] displaySymbols = LogicProgram.symbols;
   LPInvalidation module;
   SizedPanel headerPanel;
   SizedPanel sizeRow;
   SizedPanel universeRow;
   SizedPanel workArea;
   SizedPanel symbolsPanel;
   SizedPanel sizePanel;
   FormulaEntryField workspaceField;
   ChoiceButton sizeChooser;
   LogicLabel sizeLabel;
   LogicLabel universeLabel;

   InvalidityProblemPanel(LPInvalidation lpinvalidation) {
      this.module = lpinvalidation;
      this.add(this.headerPanel = new SizedPanel(), "North");
      this.headerPanel.add(this.sizeRow = new SizedPanel(), "Center");
      this.headerPanel.add(this.universeRow = new SizedPanel(), "South");
      this.sizeRow.add(this.sizePanel = new SizedPanel(), "West");
      this.sizePanel.setLayout(new FlowLayout(0, 0, 0));
      this.sizePanel.setBorder(new EmptyBorder(2, 0, 0, 0));
      this.sizePanel.add(this.sizeLabel = new LogicLabel("Size of the Universe: "));
      this.sizePanel
         .add(
            this.sizeChooser = new ChoiceButton(
               new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"}, "click to set", -1
            )
         );
      this.sizeChooser.addChoiceListener(this);
      this.universeRow.add(this.universeLabel = new LogicLabel(), "West");
      this.universeLabel.setBorder(new EmptyBorder(0, 0, 2, 0));
      this.add(this.workArea = new SizedPanel(), "Center");
      this.workArea.add(this.workspaceField = new FormulaEntryField(true), "North");
      this.workspaceField.setBackground(bruinWhite);
      this.workspaceField.setForeground(bruinBlack);
      this.workArea.add(this.symbolsPanel = new SizedPanel(), "Center");
      this.symbolsPanel.setLayout(new VerticalStackLayout());
      this.symbolsPanel.setBorder(new EmptyBorder(2, 0, 2, 0));
      this.updateWidth();
   }

   void refresh() {
      String s = LPInvalidation.trimTitle(this.module.title);
      this.module.titlePanel.setTitleLabel(s);
      this.module.titlePanel.setStatement(this.module.unparsed == null ? "" : LogicProgram.translateSymbols(this.module.unparsed, maggie, displaySymbols));
      this.sizeChooser.setSelectedIndex(this.module.size - 1);
      this.module.titlePanel.setStatus("");
      String s1 = "Universe: {";

      for (int i = 0; i < this.module.size; i++) {
         s1 = s1 + (i == 0 ? "" : ", ") + i;
      }

      s1 = s1 + "}";
      this.universeLabel.setText(s1);
      SymbolCollector symbolcollector = this.module.getSymbolList();
      this.module.symbols = symbolcollector.getAllSymbols();
      this.symbolsPanel.removeAll();
      this.symbolsPanel.invalidate();
      int j = symbolcollector.predicates.size();

      for (int k = 0; k < j; k++) {
         this.symbolsPanel.add(new SymbolInterpretationRow((PredicateInterpretation)symbolcollector.predicates.elementAt(k), this));
      }

      j = symbolcollector.operations.size();

      for (int l = 0; l < j; l++) {
         this.symbolsPanel.add(new SymbolInterpretationRow((OperationInterpretation)symbolcollector.operations.elementAt(l), this));
      }

      this.updateWidth();
      this.repaint();
   }

   @Override
   public void addNotify() {
      super.addNotify();
      if (this.workspaceField.ownerFrame == null) {
         this.workspaceField.ownerFrame = this.module.frame;
      }

      if (this.module.forPrint) {
         this.relayoutSymbolRows(this.getSizeLimit().width);
      }
   }

   void updateWidth() {
      if (this.module.frame != null) {
         int i = LogicProgram.getInteriorBounds(this.module.scroller).width - 16;
         this.setLimitWidth(i);
         this.relayoutSymbolRows(i);
      }
   }

   void relayoutSymbolRows(int j1) {
      int j = this.symbolsPanel.getComponentCount();
      int k = 0;

      for (int i = 0; i < j; i++) {
         Component component = this.symbolsPanel.getComponent(i);
         if (component instanceof SymbolInterpretationRow) {
            int l = ((SymbolInterpretationRow)component).buttonPanel.getPreferredSize().width;
            if (l > k) {
               k = l;
            }
         }
      }

      for (int i1 = 0; i1 < j; i1++) {
         Component component1 = this.symbolsPanel.getComponent(i1);
         if (component1 instanceof SymbolInterpretationRow) {
            component1.invalidate();
         }
      }

      this.invalidate();
   }

   @Override
   public void choiceChanged(ChoiceButton choicebutton, int i, int j) {
      if (i != j) {
         this.module.setSize(j + 1);
      }
   }
}
