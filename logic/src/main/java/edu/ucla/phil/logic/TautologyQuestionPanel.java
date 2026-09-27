package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.Box;

class TautologyQuestionPanel extends SizedPanel implements ChoiceListener {
   ChoiceButton answerChooser;
   LogicLabel questionLabel;
   TruthProblemPanel workPanel;

   TautologyQuestionPanel(TruthProblemPanel truthproblempanel) {
      this.workPanel = truthproblempanel;
      CellPanel cellpanel = new CellPanel();
      cellpanel.setLayout(new BorderLayout());
      this.add(cellpanel, "West");
      cellpanel.add(this.questionLabel = new LogicLabel(""), "West");
      cellpanel.add(Box.createRigidArea(new Dimension(10, 2)));
      cellpanel.add(this.answerChooser = new ChoiceButton(new String[]{"yes", "no"}, "?"), "East");
      this.answerChooser.setBackground(truthproblempanel.module.colors[1]);
      this.answerChooser.setForeground(truthproblempanel.module.colors[0]);
      this.answerChooser.setBorder(new MarginBevelBorder(0, Color.gray, Color.black));
      this.answerChooser.setMargin(new Insets(2, 2, 2, 2));
      this.answerChooser.addChoiceListener(this);
   }

   void setQuestion(String s) {
      this.questionLabel.setText(s);
      this.validate();
   }

   @Override
   public void choiceChanged(ChoiceButton choicebutton, int j, int i) {
      if (choicebutton == this.answerChooser) {
         this.workPanel.answer = i;
      }
   }
}
