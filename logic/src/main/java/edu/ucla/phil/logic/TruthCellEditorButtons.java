package edu.ucla.phil.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

class TruthCellEditorButtons extends CellPanel implements ActionListener, ModuleComponentMarker {
   TruthCellEditor editor;
   JButton okButton;

   TruthCellEditorButtons(TruthCellEditor truthcelleditor) {
      this.editor = truthcelleditor;
      this.add(this.okButton = new WideMenuButton("OK"));
      this.okButton.addActionListener(this);
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      TruthTableCell truthtablecell = this.editor.valueTree.cell;
      if (actionevent.getActionCommand().equalsIgnoreCase("ok")) {
         truthtablecell.commitTreeValue();
         truthtablecell.setSelected(false);
      }
   }
}
