package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;

class SymbolInterpretationRow extends SizedPanel implements ActionListener {
   SymbolInterpretation symbol;
   InvalidityProblemPanel problemPanel;
   SizedPanel buttonPanel;
   ActionButton symbolButton;
   LogicLabel valueLabel;
   FixedColumnLayout rowLayout;
   FlowLayout flowLayout;

   SymbolInterpretationRow(SymbolInterpretation symbolinterpretation, InvalidityProblemPanel invalidityproblempanel) {
      this.symbol = symbolinterpretation;
      this.problemPanel = invalidityproblempanel;
      this.setLayout(new BoxLayout(this, 0));
      this.add(this.buttonPanel = new SizedPanel());
      this.buttonPanel.add(this.symbolButton = new ActionButton(symbolinterpretation.name), "North");
      this.symbolButton.addActionListener(this);
      this.symbolButton.setHelpText("Click to set the value of " + symbolinterpretation.name);
      this.add(this.valueLabel = new LogicLabel(symbolinterpretation.describeValues(invalidityproblempanel.module.size)));
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      Object object = actionevent.getSource();
      if (object instanceof ActionButton) {
         int i = this.problemPanel.module.size;
         if (i == 0) {
            MessageDialog.showMessage("Empty Universe", "The Universe needs to have\nat least one element.", null, null);
         } else if (InvalidityDialogs.editInterpretation(this.symbol, i)) {
            this.valueLabel.setText(this.symbol.describeValues(this.problemPanel.module.size));
         }
      }
   }
}
