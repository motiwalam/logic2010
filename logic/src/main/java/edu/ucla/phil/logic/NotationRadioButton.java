package edu.ucla.phil.logic;

import java.awt.event.ItemEvent;

class NotationRadioButton extends LogicRadioButton {
   NotationChooser chooser;

   NotationRadioButton(String s, NotationChooser notationchooser) {
      super(s);
      this.chooser = notationchooser;
   }

   @Override
   protected void fireItemStateChanged(ItemEvent itemevent) {
      super.fireItemStateChanged(itemevent);
      boolean flag = itemevent.getStateChange() == 1;
      int i = this.chooser.getSelectedIndex();
      LPParsing lpparsing = this.chooser.problemPanel.module;
      if (flag && i == 2) {
         this.chooser.problemPanel.resetWork(false);
      }

      if (flag && lpparsing.checkNow && !lpparsing.checkDisabled) {
         FormulaParseNode formulaparsenode = new FormulaParseNode(this.chooser.problemPanel.statement);
         String s = formulaparsenode.getNotationCode();
         this.chooser.problemPanel.treePanel.setVisible(i == 0 && s.equals("O") || i == 1 && s.equals("I"));
         this.chooser.setResultText(s.equals(NotationChooser.codeForIndex(i)) ? "Correct" : "Incorrect");
      }
   }
}
