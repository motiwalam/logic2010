package edu.ucla.phil.logic;

import java.awt.BorderLayout;

class TruthCellEditor extends CellPanel implements ModuleComponentMarker {
   TruthValueTree valueTree;
   TruthValueTree mirrorTree;
   SizedSeparator separator;
   TruthProblemPanel workPanel;

   TruthCellEditor(TruthProblemPanel truthproblempanel) {
      this.workPanel = truthproblempanel;
      this.setLayout(new BorderLayout());
      this.valueTree = null;
      this.mirrorTree = null;
   }

   void setTrees(TruthValueTree truthvaluetree, TruthValueTree truthvaluetree1) {
      this.removeAll();
      if ((this.valueTree = truthvaluetree) != null) {
         this.add(truthvaluetree, "North");
      }

      if (truthvaluetree != null && truthvaluetree1 != null) {
         this.add(this.separator = new SizedSeparator(20, 2, false, LogicConstants.bruinGold), "Center");
      }

      if ((this.mirrorTree = truthvaluetree1) != null) {
         this.add(truthvaluetree1, "South");
      }

      if (truthvaluetree != null || truthvaluetree1 != null) {
         this.validate();
      }
   }
}
