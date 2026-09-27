package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TruthSetupButtons extends CellPanel implements ActionListener, ModuleComponentMarker {
   int stage;
   LogicLabel promptLabel;
   WideMenuButton okButton;
   TruthProblemPanel workPanel;

   TruthSetupButtons(TruthProblemPanel truthproblempanel) {
      this.workPanel = truthproblempanel;
      this.setLayout(new FlowLayout(0));
      this.add(this.promptLabel = new LogicLabel("Please click OK when you are finished."));
      this.add(this.okButton = new WideMenuButton("OK"));
      this.okButton.addActionListener(this);
      this.setStage(1);
   }

   void setStage(int i) {
      this.stage = i;
      if (i == 0) {
         this.workPanel.setupPrompt.setText("Please separate sentence letters with a period.");
      } else if (i == 1) {
         this.workPanel.setupPrompt.setText("Please fill in the proper truth values.");
      }
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (actionevent.getSource() == this.okButton) {
         if (this.stage == 0) {
            ErrorRef errorref = this.workPanel.setupPanel.checkLettersAndRows();
            if (this.workPanel.setupPanel.lettersEntered = errorref.id == null) {
               this.workPanel.setupPanel.showAssignmentGrid();
            } else if (!this.workPanel.module.checkMessagesDisabled) {
               MessageDialog.showMessage(TruthMessage.get(errorref.id), errorref.params, null, null);
            }
         } else if (this.stage == 1) {
            ErrorRef errorref1 = this.workPanel.setupPanel.checkAssignments();
            if (this.workPanel.setupDone = errorref1.id == null) {
               this.workPanel.showTable();
            } else if (!this.workPanel.module.checkMessagesDisabled) {
               MessageDialog.showMessage(TruthMessage.get(errorref1.id), errorref1.params, null, null);
            }
         }
      }
   }
}
