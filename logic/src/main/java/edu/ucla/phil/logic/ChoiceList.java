package edu.ucla.phil.logic;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class ChoiceList extends JList implements ListSelectionListener {
   ChoiceButton choiceButton;

   ChoiceList(String s) {
      this.setBackground(null);
      this.setForeground(null);
   }

   ChoiceList(String[] astring, ChoiceButton choicebutton) {
      super(astring);
      this.setBackground(null);
      this.setForeground(null);
      this.choiceButton = choicebutton;
      this.getSelectionModel().addListSelectionListener(this);
   }

   @Override
   public void valueChanged(ListSelectionEvent listselectionevent) {
      int i = this.getSelectedIndex();
      if (i != -1) {
         this.choiceButton.setSelectedIndex(i);
      }

      this.clearSelection();
      this.choiceButton.popup.setVisible(false);
   }
}
