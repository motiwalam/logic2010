package edu.ucla.phil.logic;

class LabeledNumberChoice extends SizedPanel {
   int choiceCount;
   ChoiceButton choice;

   LabeledNumberChoice(String s, int i) {
      this.choiceCount = i;
      this.add(new LogicLabel(s), "West");
      String[] astring = new String[i];

      for (int j = 0; j < i; j++) {
         astring[j] = "" + j;
      }

      this.add(this.choice = new ChoiceButton(astring, 0), "Center");
   }

   int getSelectedNumber() {
      return this.choice.getSelectedIndex();
   }

   void setSelectedNumber(int i) {
      this.choice.setSelectedIndex(i);
   }
}
