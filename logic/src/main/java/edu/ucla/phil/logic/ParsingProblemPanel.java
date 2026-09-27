package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Vector;

class ParsingProblemPanel extends SizedPanel implements LogicConstants {
   LPParsing module;
   NotationChooser notationChooser;
   ParseTreePanel treePanel;
   String problemName;
   String statement;
   static String[] SYMBOLS = LogicProgram.symbols;

   ParsingProblemPanel(LPParsing lpparsing) {
      this.module = lpparsing;
      this.setLayout(new VerticalStackLayout());
      SizedPanel sizedpanel;
      this.add(sizedpanel = new SizedPanel());
      sizedpanel.setLayout(new FlowLayout());
      this.problemName = null;
      this.statement = null;
      SizedPanel sizedpanel1;
      this.add(sizedpanel1 = new SizedPanel());
      sizedpanel1.setLayout(new GridBagLayout());
      GridBagConstraints gridbagconstraints = new GridBagConstraints();
      gridbagconstraints.anchor = 23;
      gridbagconstraints.gridx = 0;
      gridbagconstraints.gridy = 0;
      gridbagconstraints.weighty = 1.0;
      sizedpanel1.add(this.notationChooser = new NotationChooser(this), gridbagconstraints);
      gridbagconstraints.gridx = 1;
      gridbagconstraints.weightx = 1.0;
      sizedpanel1.add(this.treePanel = new ParseTreePanel(lpparsing), gridbagconstraints);
      this.treePanel.setVisible(false);
   }

   void clearProblem() {
      this.problemName = null;
      this.module.titlePanel.setTitleLabel("Problem: ");
      this.statement = null;
      this.module.titlePanel.setStatement("");
      this.module.lastUserProblem = null;
      this.module.loadTime = 0L;
      this.treePanel.rootNode.setFormula("");
      this.resetWork();
   }

   void resetWork() {
      this.resetWork(true);
   }

   void resetWork(boolean flag) {
      this.module.titlePanel.setStatus("");
      if (flag) {
         int i = this.notationChooser.getSelectedIndex();
         if (i != -1) {
            this.notationChooser.buttons[i].setSelected(false);
         }

         this.notationChooser.setResultText(" ");
      }

      this.treePanel.setVisible(!this.module.checkNow);
      this.treePanel.rootNode.setExpanded(false);
      this.treePanel.updateStatus();
      this.treePanel.rootNode.formulaText.clearHighlight();
      this.validate();
   }

   boolean isCorrect() {
      return this.checkProblem().id == null;
   }

   ErrorRef checkProblem() {
      String s = null;
      String s1 = "Correct";
      int i = this.notationChooser.getSelectedIndex();
      FormulaParseNode formulaparsenode = new FormulaParseNode(this.statement);
      String s2 = formulaparsenode.getNotationCode();
      if (i == -1) {
         s = "parerr001";
         s1 = "Incomplete";
      } else if (NotationChooser.indexForCode(s2) != i) {
         s = "parerr002";
         s1 = "Incorrect";
      }

      if (!this.module.checkDisabled) {
         this.notationChooser.setResultText(s1);
      }

      if (i != 2) {
         if (!this.treePanel.isComplete()) {
            if (!this.module.noDescent) {
               if (s == null) {
                  s = "parerr003";
                  s1 = "Incomplete";
               }

               if (!this.module.checkDisabled) {
                  this.treePanel.statusLabel.setText("Incomplete");
               }
            } else {
               int[] aint = this.treePanel.rootNode.formulaText.selectedRange;
               if (aint != null && aint.length != 0) {
                  if (!"parerr002".equalsIgnoreCase(s)) {
                     s = "parerr004";
                     s1 = "Incorrect";
                  }

                  if (!this.module.checkDisabled) {
                     this.treePanel.statusLabel.setText("Incorrect");
                  }
               } else {
                  if (s == null) {
                     s = "parerr003";
                     s1 = "Incomplete";
                  }

                  if (!this.module.checkDisabled) {
                     this.treePanel.statusLabel.setText("Incomplete");
                  }
               }
            }
         } else if (!this.module.checkDisabled) {
            this.treePanel.statusLabel.setText(this.module.noDescent ? "Correct" : "Complete");
         }
      }

      return new ErrorRef(s, Message.params("summary", s1));
   }

   void loadRecord(TaggedRecord taggedrecord) {
      this.clearProblem();
      this.problemName = taggedrecord.getName();
      if (this.problemName != null && !this.problemName.trim().equals("")) {
         this.module.titlePanel.setTitleLabel(LPParsing.trimTitle(this.problemName));
      } else {
         this.module.titlePanel.setTitleLabel(null);
      }

      this.statement = LPParsing.getProblemStatement(taggedrecord);
      if (this.statement == null) {
         this.module.titlePanel.setStatement("");
      } else {
         this.module.titlePanel.setStatement(LogicProgram.translateSymbols(this.statement, maggie, SYMBOLS));
      }

      String s = taggedrecord.valueAt(taggedrecord.indexOfTag(']'));
      this.treePanel.rootNode.setFormula(this.statement);
      this.treePanel.rootNode.restoreExpansion(s);
      s = taggedrecord.valueAt(taggedrecord.indexOfTag('['));
      int i = NotationChooser.indexForCode(s);
      if (i >= 0 && i < this.notationChooser.buttons.length) {
         this.notationChooser.buttons[i].setSelected(true);
      }

      if ((s = taggedrecord.valueAt(taggedrecord.indexOfTag('*'))) != null) {
         this.module.noDescent = true;
         this.treePanel.rootNode.formulaText.selectionCorrect = s.charAt(0) == 'T';
         Vector vector = new Vector();
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\,");
         delimitedtokenizer.setInput(s.substring(1));

         Integer integer;
         while ((integer = LogicProgram.parseInteger(delimitedtokenizer.nextToken())) != null) {
            vector.add(integer);
         }

         int j = vector.size();
         if (j == 0) {
            this.treePanel.rootNode.formulaText.selectedRange = null;
         } else {
            int[] aint = new int[j];

            for (int k = 0; k < j; k++) {
               aint[k] = (Integer)vector.elementAt(k);
            }

            this.treePanel.rootNode.formulaText.setHighlight(this.treePanel.rootNode.formulaText.selectedRange = aint);
            this.repaint();
         }
      }
   }

   String getWorkRecord() {
      String s = "";
      s = s + TaggedRecord.formatField(this.problemName, '$');
      String s3 = s + TaggedRecord.formatField(this.statement, '=');
      int i = this.notationChooser.getSelectedIndex();
      if (i != -1) {
         s3 = s3 + TaggedRecord.formatField(NotationChooser.codeForIndex(i), '[');
      }

      String s1 = this.treePanel.rootNode.getExpansionString(false);
      if (!s1.equals("0")) {
         s3 = s3 + TaggedRecord.formatField(s1, ']');
      }

      if (this.module.noDescent) {
         String s2 = this.treePanel.rootNode.formulaText.selectionCorrect ? "T" : "F";
         int[] aint = this.treePanel.rootNode.formulaText.selectedRange;
         if (aint != null && aint.length != 0) {
            int j = aint.length;

            for (int k = 0; k < j; k++) {
               s2 = s2 + (k == 0 ? "" : ",") + aint[k];
            }

            s3 = s3 + TaggedRecord.formatField(s2, '*');
         }
      }

      return s3;
   }
}
