package edu.ucla.phil.logic;

class AnswerListLabel extends LogicLabel {
   AnswerListLabel(String s) {
      super(s);
   }

   AnswerListLabel(String s, int i) {
      super(s, i);
   }

   @Override
   public String toString() {
      return this.getText();
   }

   void setHoverText(String s) {
      this.setToolTipText(s);
   }
}
