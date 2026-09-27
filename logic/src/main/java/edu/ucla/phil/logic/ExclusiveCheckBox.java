package edu.ucla.phil.logic;

import java.awt.event.ItemEvent;

class ExclusiveCheckBox extends ScaledCheckBox {
   TruthTableGrid grid;
   int index;

   ExclusiveCheckBox(TruthTableGrid truthtablegrid, int i) {
      this.grid = truthtablegrid;
      this.index = i;
      this.setBackground(truthtablegrid.workPanel.module.colors[1]);
      this.setForeground(truthtablegrid.workPanel.module.colors[0]);
   }

   @Override
   protected void fireItemStateChanged(ItemEvent itemevent) {
      boolean flag = itemevent.getStateChange() == 1;
      int i = this.grid.counterexampleRow;
      if (flag) {
         if (i != -1 && i != this.index) {
            this.grid.counterexampleBoxes[i].setSelected(false);
         }

         this.grid.counterexampleRow = this.index;
      } else if (i != -1 && i == this.index) {
         this.grid.counterexampleRow = -1;
      }

      super.fireItemStateChanged(itemevent);
   }
}
