package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.util.Hashtable;
import javax.swing.BorderFactory;

class TruthProblemPanel extends SizedPanel implements LogicConstants {
   LPTruthAnalysis module;
   TruthTableGrid table;
   TruthCellEditor cellEditor;
   TruthTableSetupPanel setupPanel;
   LogicLabel setupPrompt;
   CellPanel setupContainer;
   CellPanel cellEditorContainer;
   CellPanel mainPanel;
   TruthCellEditorButtons cellEditorButtons;
   TautologyQuestionPanel questionPanel;
   TruthSetupButtons setupButtons;
   String problemName;
   String statement;
   static String[] displaySymbols = LogicProgram.symbols;
   TruthTableEvaluator evaluator;
   int answer;
   int premiseCount;
   int letterCount;
   ArgumentParser argument;
   boolean setupDone;
   boolean loading = false;

   TruthProblemPanel(LPTruthAnalysis lptruthanalysis) {
      this.module = lptruthanalysis;
      this.evaluator = null;
      this.premiseCount = 0;
      this.letterCount = 0;
      this.problemName = null;
      this.statement = null;
      this.setLayout(new BorderLayout());
      this.setupPrompt = new LogicLabel("", 2);
      this.setupContainer = new CellPanel();
      this.setupButtons = new TruthSetupButtons(this);
      this.add(this.mainPanel = new CellPanel(), "West");
      this.mainPanel.setLayout(new BorderLayout());
      this.questionPanel = new TautologyQuestionPanel(this);
      this.table = new TruthTableGrid(this);
      this.cellEditorContainer = new CellPanel();
      this.cellEditorContainer.setBorder(BorderFactory.createEtchedBorder());
      this.cellEditorContainer.setLayout(new VerticalStackLayout(17));
      this.cellEditorContainer.add(this.cellEditor = new TruthCellEditor(this));
      this.cellEditorContainer.add(this.cellEditorButtons = new TruthCellEditorButtons(this.cellEditor));
      this.clearProblem();
   }

   void clearProblem() {
      this.problemName = null;
      this.module.titlePanel.setTitleLabel("Problem: ");
      this.statement = null;
      this.module.lastUserProblem = null;
      this.module.titlePanel.setStatement("");
      this.questionPanel.setQuestion("Is this formula a tautology?");
      this.questionPanel.answerChooser.setVisible(true);
      this.setupContainer.removeAll();
      this.setupPanel = null;
      this.clearWork();
   }

   void clearWork() {
      this.module.titlePanel.setStatus("");
      this.cellEditor.setTrees(null, null);
      this.cellEditorContainer.setVisible(false);
      this.answer = -1;
      this.table.counterexampleRow = -1;
      this.mainPanel.removeAll();
      if (this.module.completeSetup) {
         this.mainPanel.add(this.setupPrompt, "North");
         this.mainPanel.add(this.setupContainer, "West");
         this.mainPanel.add(this.setupButtons, "South");
      } else {
         this.mainPanel.add(this.questionPanel, "North");
         this.mainPanel.add(this.table, "West");
         this.mainPanel.add(this.cellEditorContainer, "Center");
      }

      this.mainPanel.revalidate();
      this.setupDone = false;
      if (this.setupPanel != null) {
         this.setupPanel.loadSetup(null);
      }

      this.table.buildTable(null);
   }

   void loadProblem(TaggedRecord taggedrecord) {
      this.clearProblem();
      this.setProblemName(taggedrecord.getName());
      this.statement = LPTruthAnalysis.getProblemStatement(taggedrecord);
      if (this.statement == null) {
         this.module.titlePanel.setStatement("");
      } else {
         this.module.titlePanel.setStatement(LogicProgram.translateSymbols(this.statement, maggie, displaySymbols));
      }

      this.module.titlePanel.statementPane.validate();
      this.argument = new ArgumentParser(this.statement);
      this.evaluator = new TruthTableEvaluator(this.argument);
      this.premiseCount = this.argument.premises.length;
      this.letterCount = this.evaluator.sentenceLetters.size();
      this.module.assumeTautology = LPTruthAnalysis.assumeTautology(taggedrecord);
      if (this.module.assumeTautology) {
         this.questionPanel.setQuestion("Please complete a truth table for this formula.");
         this.questionPanel.answerChooser.setVisible(false);
      } else if (!this.argument.conclusionOnly) {
         this.questionPanel.setQuestion("Is this argument tautologically valid?");
      }

      Hashtable hashtable = new Hashtable();
      int[] aint = taggedrecord.indexesOfTag('@');
      int i = aint.length;

      for (int j = 0; j < i; j++) {
         String s = taggedrecord.valueAt(aint[j]);
         int k = s.indexOf(58);
         if (k != -1) {
            String s1 = s.substring(0, k);
            String s3 = s.substring(k + 1);
            String[] astring = new String[this.premiseCount + 1];

            for (int l = 0; l <= this.premiseCount; l++) {
               k = s3.indexOf(46);
               if (k == -1) {
                  astring[l] = s3;
                  s3 = "";
               } else {
                  astring[l] = s3.substring(0, k);
                  s3 = s3.substring(k + 1);
               }
            }

            hashtable.put(s1.toUpperCase(), astring);
         }
      }

      if (!this.module.assumeTautology) {
         Integer integer = taggedrecord.intValueAt(taggedrecord.indexOfTag('*'));
         this.answer = integer == null ? -1 : integer;
         integer = taggedrecord.intValueAt(taggedrecord.indexOfTag('#'));
         this.table.counterexampleRow = integer == null ? -1 : integer;
      }

      String s2 = taggedrecord.valueAt(taggedrecord.indexOfTag('&'));
      if (s2 != null || this.module.completeSetup) {
         this.setupContainer.add(this.setupPanel = new TruthTableSetupPanel(this));
         this.setupPanel.loadSetup(s2);
         if (this.setupDone) {
            this.showTable();
         }
      }

      this.loading = true;
      this.table.buildTable(hashtable);
      this.cellEditor.setTrees(null, null);
      this.loading = false;
   }

   void showTable() {
      this.table.buildHeader();
      this.mainPanel.removeAll();
      this.mainPanel.add(this.questionPanel, "North");
      this.mainPanel.add(this.table, "West");
      this.mainPanel.add(this.cellEditorContainer, "Center");
      this.mainPanel.validate();
   }

   String getWorkRecord() {
      String s = "";
      s = s + TaggedRecord.formatField(this.problemName, '$');
      String s2 = s + TaggedRecord.formatField(this.statement, '=');
      Hashtable hashtable = this.table.getCellCodes();

      for (int i = 0; i < this.table.rowCount; i++) {
         if (this.table.rowHasWork(i)) {
            String s1 = TruthTableGrid.rowAssignmentString(i, this.letterCount);
            String[] astring = (String[])hashtable.get(s1);
            int j = astring == null ? 0 : astring.length;
            if (j != 0) {
               for (int k = 0; k < j; k++) {
                  s1 = s1 + (k == 0 ? ":" : ".") + astring[k];
               }

               s2 = s2 + TaggedRecord.formatField(s1, '@');
            }
         }
      }

      if (this.table.counterexampleRow != -1) {
         s2 = s2 + this.table.counterexampleRow + "`#";
      }

      if (this.module.assumeTautology) {
         s2 = s2 + "taut`%";
      } else if (this.answer != -1) {
         s2 = s2 + this.answer + "`*";
      }

      return s2 + TaggedRecord.formatField(this.setupPanel == null ? null : this.setupPanel.getSetupCode(), '&');
   }

   void setProblemName(String s) {
      this.problemName = s;
      if (s != null && !(s = s.trim()).equals("")) {
         this.module.titlePanel.setTitleLabel(LPTruthAnalysis.trimTitle(s));
      } else {
         this.module.titlePanel.setTitleLabel(null);
      }
   }
}
