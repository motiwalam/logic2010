package edu.ucla.phil.logic;

import java.awt.Point;
import java.util.Hashtable;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

class TruthTableGrid extends SizedPanel implements LogicConstants {
   static String[] displaySymbols = LogicProgram.symbols;
   TruthProblemPanel workPanel;
   JScrollPane scrollPane;
   CellPanel headerRow;
   CellPanel rowsPanel;
   CellPanel[] rowPanels;
   FixedColumnLayout columnLayout;
   ExclusiveCheckBox[] counterexampleBoxes;
   TruthTableCell[][] cells;
   Point selectedCell;
   int counterexampleRow;
   int columnCount;
   int rowCount;

   TruthTableGrid(TruthProblemPanel truthproblempanel) {
      this.workPanel = truthproblempanel;
      this.setLayout(new VerticalStackLayout());
      this.add(this.headerRow = new CellPanel());
      this.add(this.scrollPane = new JScrollPane(this.rowsPanel = new CellPanel()));
      this.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.rowsPanel.setLayout(new VerticalStackLayout());
      this.rowsPanel.setBackground(truthproblempanel.module.colors[1]);
      this.rowsPanel.setForeground(truthproblempanel.module.colors[0]);
      this.counterexampleRow = -1;
      this.buildTable(null);
   }

   void buildHeader() {
      this.headerRow.removeAll();

      for (int i = 0; i < this.workPanel.letterCount; i++) {
         Expression expression = (Expression)this.workPanel.evaluator.sentenceLetters.elementAt(i);
         this.headerRow.add(new LogicLabel(LogicProgram.translateSymbols(expression.toString(), maggie, displaySymbols)));
      }

      for (int j = 0; j < this.workPanel.premiseCount; j++) {
         LogicLabel logiclabel;
         this.headerRow.add(logiclabel = new LogicLabel("Pr" + (j + 1)));
         logiclabel.setHorizontalAlignment(0);
      }

      LogicLabel logiclabel1;
      if (this.workPanel.argument != null && !this.workPanel.argument.conclusionOnly) {
         this.headerRow.add(logiclabel1 = new LogicLabel("Conc"));
      } else {
         this.headerRow.add(logiclabel1 = new LogicLabel("Form"));
      }

      logiclabel1.setHorizontalAlignment(0);
      this.headerRow.add(new LogicLabel(""));
   }

   void buildTable(Hashtable hashtable) {
      this.headerRow.removeAll();
      this.headerRow.invalidate();
      this.rowsPanel.removeAll();
      this.rowsPanel.invalidate();
      this.columnCount = 2 + this.workPanel.letterCount + this.workPanel.premiseCount;
      this.rowCount = this.workPanel.letterCount == 0 ? 0 : 1 << this.workPanel.letterCount;
      this.selectedCell = null;
      this.rowPanels = new CellPanel[this.rowCount];
      this.cells = new TruthTableCell[this.rowCount][this.workPanel.premiseCount + 1];
      this.counterexampleBoxes = new ExclusiveCheckBox[this.rowCount];
      this.buildHeader();

      for (int i = 0; i < this.rowCount; i++) {
         CellPanel cellpanel = new CellPanel();
         String s = rowAssignmentString(i, this.workPanel.letterCount);

         for (int j = 0; j < this.workPanel.letterCount; j++) {
            LogicLabel logiclabel = new LogicLabel(s.substring(j, j + 1));
            cellpanel.add(logiclabel);
         }

         String[] astring = hashtable == null ? null : (String[])hashtable.get(s);

         for (int k = 0; k <= this.workPanel.premiseCount; k++) {
            String s1 = astring == null ? null : astring[k];
            if (s1 == null) {
               s1 = "+?";
            }

            cellpanel.add(this.cells[i][k] = new TruthTableCell(s1, this.workPanel, new Point(k, i)));
         }

         if (!this.workPanel.module.assumeTautology) {
            cellpanel.add(this.counterexampleBoxes[i] = new ExclusiveCheckBox(this, i));
         }

         this.rowsPanel.add(this.rowPanels[i] = cellpanel);
      }

      if (!this.workPanel.module.assumeTautology) {
         if (this.counterexampleRow != -1 && this.counterexampleRow < this.rowCount) {
            this.counterexampleBoxes[this.counterexampleRow].setSelected(true);
         }

         this.workPanel.questionPanel.answerChooser.setSelectedIndex(this.workPanel.answer);
      }

      this.layoutColumns();
      this.setVisible(this.rowCount != 0);
   }

   void layoutColumns() {
      int[] aint = new int[this.columnCount];

      for (int i = 0; i < this.columnCount - 1; i++) {
         aint[i] = this.headerRow.getComponent(i).getPreferredSize().width + 10;
      }

      aint[this.columnCount - 1] = this.workPanel.module.assumeTautology ? 0 : ScaledCheckBoxIcon.getScaledSize() + 8;
      this.columnLayout = new FixedColumnLayout(this.columnCount);
      this.columnLayout.setColumnWidths(aint);
      this.headerRow.setLayout(this.columnLayout);

      for (int j = 0; j < this.rowCount; j++) {
         this.rowPanels[j].setLayout(this.columnLayout);
      }
   }

   static String rowAssignmentString(int i, int j) {
      if (i < 0) {
         return null;
      } else {
         String s = "";

         for (int k = 0; k < j; k++) {
            s = (i % 2 == 0 ? "T" : "F") + s;
            i /= 2;
         }

         return s;
      }
   }

   Hashtable getCellCodes() {
      Hashtable hashtable = new Hashtable();

      for (int i = 0; i < this.rowCount; i++) {
         String[] astring = new String[this.workPanel.premiseCount + 1];

         for (int j = 0; j <= this.workPanel.premiseCount; j++) {
            astring[j] = this.cells[i][j].getCode();
         }

         hashtable.put(rowAssignmentString(i, this.workPanel.letterCount), astring);
      }

      return hashtable;
   }

   boolean rowHasWork(int i) {
      int j = this.workPanel.premiseCount + 1;
      TruthTableCell[] atruthtablecell = this.cells[i];

      for (int k = 0; k < j; k++) {
         if (atruthtablecell[k].valueTree.hasEnteredValues()) {
            return true;
         }
      }

      return false;
   }
}
