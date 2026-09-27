package edu.ucla.phil.logic;

class ButtonChoiceHandler extends DialogHandler {
   int choice = -1;

   ButtonChoiceHandler(String s) {
      super(s);
   }

   @Override
   boolean handleChoice(MessageDialog messagedialog) {
      String s = messagedialog.buttons[messagedialog.selectedButton].getText();
      this.choice = LogicProgram.indexOf(this.labels, s);
      return true;
   }
}
