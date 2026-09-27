package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.BorderFactory;

class TruthTableSetupPanel extends CellPanel implements LogicConstants, ChoiceListener, ModuleComponentMarker {
   static String[] displaySymbols = LogicProgram.symbols;
   TruthProblemPanel workPanel;
   TruthTableEvaluator evaluator;
   int letterCount;
   int rowCount;
   boolean lettersEntered;
   CellPanel headerRow;
   LogicLabel lastHeaderLabel;
   CellPanel[] rowPanels;
   CellPanel rowsPanel;
   SizedPanel letterEntryPanel;
   SizedTextField lettersField;
   SizedTextField rowCountField;
   FixedColumnLayout columnLayout;

   TruthTableSetupPanel(TruthProblemPanel truthproblempanel) {
      this.workPanel = truthproblempanel;
      this.evaluator = truthproblempanel.evaluator;
      this.lettersEntered = false;
      this.letterCount = this.evaluator == null ? 0 : this.evaluator.sentenceLetters.size();
      this.rowCount = this.letterCount == 0 ? 0 : 1 << this.letterCount;
      this.rowPanels = new CellPanel[this.rowCount];
      this.setLayout(new BorderLayout());
      this.add(this.headerRow = new CellPanel(), "North");
      this.headerRow.setBackground(LogicConstants.bruinBlue);
      this.buildHeader();
      this.add(this.rowsPanel = new CellPanel(), "Center");
      this.rowsPanel.setLayout(new VerticalStackLayout());
      this.rowsPanel.setBorder(BorderFactory.createEtchedBorder());

      for (int i = 0; i < this.rowCount; i++) {
         CellPanel cellpanel = new CellPanel();
         String s = TruthTableGrid.rowAssignmentString(i, this.letterCount);

         for (int j = 0; j < this.letterCount; j++) {
            ChoiceButton choicebutton;
            cellpanel.add(choicebutton = new ChoiceButton(new String[]{"T", "F"}, "?"));
            choicebutton.setBorder(BorderFactory.createBevelBorder(0, Color.gray, Color.black));
            choicebutton.setUserData(new Integer(i >> this.letterCount - j - 1 & 1));
            choicebutton.addChoiceListener(this);
         }

         this.rowsPanel.add(this.rowPanels[i] = cellpanel);
      }

      this.layoutColumns();
      Color[] acolor = truthproblempanel.module.colors;
      this.letterEntryPanel = new SizedPanel();
      FlexGridLayout flexgridlayout;
      this.letterEntryPanel.setLayout(flexgridlayout = new FlexGridLayout(2, 2, 0, 0, true, false));
      flexgridlayout.setVgap(1);
      LogicLabel logiclabel;
      this.letterEntryPanel.add(logiclabel = new LogicLabel("Sentence Letters: "));
      logiclabel.setFocusable(false);
      this.letterEntryPanel.add(this.lettersField = new SizedTextField("", 100));
      this.lettersField.setForeground(acolor[1]);
      this.lettersField.setBackground(acolor[0]);
      this.letterEntryPanel.add(logiclabel = new LogicLabel("Number of Rows: "));
      logiclabel.setFocusable(false);
      this.letterEntryPanel.add(this.rowCountField = new SizedTextField("", 50));
      this.rowCountField.setForeground(acolor[1]);
      this.rowCountField.setBackground(acolor[0]);
   }

   void buildHeader() {
      this.headerRow.removeAll();

      for (int i = 0; i < this.letterCount; i++) {
         Expression expression = (Expression)this.evaluator.sentenceLetters.elementAt(i);
         this.headerRow.add(this.lastHeaderLabel = new LogicLabel(LogicProgram.translateSymbols(expression.toString(), maggie, displaySymbols)));
         this.lastHeaderLabel.setHorizontalAlignment(0);
      }
   }

   void showAssignmentGrid() {
      this.workPanel.setupButtons.setStage(1);
      this.workPanel.table.buildTable(null);
      this.buildHeader();
      this.removeAll();
      this.add(this.headerRow, "North");
      this.add(this.rowsPanel, "Center");
      this.validate();
   }

   void loadSetup(String s) {
      if (!(this.lettersEntered = s != null)) {
         this.workPanel.setupDone = false;
         this.workPanel.setupButtons.setStage(0);
         this.lettersField.setText("");
         this.rowCountField.setText("");
         this.removeAll();
         this.add(this.letterEntryPanel, "Center");
         this.validate();
      } else {
         int i = s.indexOf(58);
         String s1;
         if (this.workPanel.setupDone = i == -1) {
            s1 = s;
            s = null;
         } else {
            s1 = s.substring(0, i);
            s = s.substring(i + 1);
         }

         this.evaluator.parseLetterOrder(s1);
         if (this.workPanel.setupDone) {
            return;
         }
      }

      int l = 0;
      int i1 = s == null ? 0 : s.length();

      for (int j = 0; j < this.rowCount; j++) {
         CellPanel cellpanel = this.rowPanels[j];

         for (int k = 0; k < this.letterCount; k++) {
            ChoiceButton choicebutton = (ChoiceButton)cellpanel.getComponent(k);
            choicebutton.setSelectedIndex(l < i1 ? "TF".indexOf(s.charAt(l++)) : -1, false);
            this.highlightCell(choicebutton, false);
         }
      }
   }

   String getSetupCode() {
      if (!this.lettersEntered) {
         return null;
      } else {
         String s = "";

         for (int i = 0; i < this.letterCount; i++) {
            s = s + (i == 0 ? "" : ".") + this.evaluator.sentenceLetters.elementAt(i);
         }

         if (this.workPanel.setupDone) {
            return s;
         } else {
            String s2 = s + ":";
            String s1 = "";
            boolean flag = false;

            for (int l = 0; l < this.rowCount; l++) {
               CellPanel cellpanel = this.rowPanels[l];

               for (int j = 0; j < this.letterCount; j++) {
                  int k = ((ChoiceButton)cellpanel.getComponent(j)).getSelectedIndex();
                  s2 = s2 + "?TF".charAt(k + 1);
                  if (k != -1) {
                     flag = true;
                  }
               }
            }

            return flag ? s2 + s1 : s2;
         }
      }
   }

   boolean hasSetupWork() {
      return this.getSetupCode() != null;
   }

   boolean isAssignmentCorrect() {
      return this.checkAssignments().id == null;
   }

   ErrorRef checkAssignments() {
      if (this.workPanel.setupDone) {
         return new ErrorRef(null, Message.params("summary", "Correct"));
      } else if (this.workPanel.module.completeSetup && !this.lettersEntered) {
         return new ErrorRef("truerr020", Message.params("summary", "Incomplete"));
      } else {
         boolean flag = false;
         boolean flag1 = false;

         for (int i = 0; i < this.rowCount; i++) {
            CellPanel cellpanel = this.rowPanels[i];

            for (int j = 0; j < this.letterCount; j++) {
               ChoiceButton choicebutton = (ChoiceButton)cellpanel.getComponent(j);
               int k = choicebutton.getSelectedIndex();
               if (k == -1) {
                  flag = true;
               } else if (k != (Integer)choicebutton.getUserData()) {
                  flag1 = true;
               }
            }
         }

         if (flag1) {
            return new ErrorRef("truerr011", Message.params("summary", "Incorrect"));
         } else {
            return flag ? new ErrorRef("truerr012", Message.params("summary", "Incomplete")) : new ErrorRef(null, Message.params("summary", "Correct"));
         }
      }
   }

   ErrorRef checkLettersAndRows() {
      if (this.lettersEntered) {
         return new ErrorRef(null, Message.params("summary", "Correct"));
      } else {
         String s = LogicProgram.translateSymbols(this.lettersField.getText(), displaySymbols, maggie);
         ErrorRef errorref = this.evaluator.parseLetterOrder(s);
         if (errorref.id != null) {
            return (ErrorRef)errorref.putParam("summary", "Incorrect");
         } else {
            Integer integer = LogicProgram.parseInteger(this.rowCountField.getText());
            return integer != null && integer == this.rowCount
               ? (ErrorRef)errorref.putParam("summary", "Correct")
               : new ErrorRef("truerr019", Message.params("summary", "Incorrect"));
         }
      }
   }

   @Override
   public void choiceChanged(ChoiceButton choicebutton, int i, int j) {
      this.highlightCell(choicebutton, true);
   }

   void highlightCell(ChoiceButton choicebutton, boolean flag) {
      if (!this.workPanel.module.setupErrorsDisabled) {
         int i = choicebutton.getSelectedIndex();
         Color[] acolor = this.workPanel.module.colors;
         if (i >= 0 && i != (Integer)choicebutton.getUserData()) {
            if (this.workPanel.module.forPrint) {
               choicebutton.setFont(this.workPanel.module.errorFont);
            } else {
               choicebutton.setColors(acolor[4], acolor[3]);
               if (flag) {
                  Toolkit.getDefaultToolkit().beep();
               }
            }
         } else if (this.workPanel.module.forPrint) {
            choicebutton.setFont(this.workPanel.module.font);
         } else {
            choicebutton.setColors(acolor[0], acolor[1]);
         }
      }
   }

   @Override
   public void addNotify() {
      super.addNotify();
      this.layoutColumns();
   }

   void layoutColumns() {
      if (this.letterCount != 0) {
         int[] aint = new int[this.letterCount];

         for (int i = 0; i < this.letterCount; i++) {
            aint[i] = this.headerRow.getComponent(i).getPreferredSize().width + 10;
         }

         FixedColumnLayout fixedcolumnlayout = new FixedColumnLayout(this.letterCount);
         fixedcolumnlayout.setColumnWidths(aint);
         this.headerRow.setLayout(fixedcolumnlayout);

         for (int j = 0; j < this.rowCount; j++) {
            this.rowPanels[j].setLayout(fixedcolumnlayout);
         }
      }
   }
}
