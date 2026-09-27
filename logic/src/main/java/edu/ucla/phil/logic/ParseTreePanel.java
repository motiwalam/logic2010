package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.Box;

class ParseTreePanel extends CellPanel implements ModuleComponentMarker {
   LogicLabel statusLabel;
   ParseTreeNodePanel rootNode;
   LPParsing module;
   int unexpandedCount;

   ParseTreePanel(LPParsing lpparsing) {
      this.module = lpparsing;
      this.unexpandedCount = 0;
      this.setLayout(new VerticalStackLayout());
      this.add(this.statusLabel = new LogicLabel(" "));
      this.add(Box.createRigidArea(new Dimension(4, 4)));
      this.add(this.rootNode = new ParseTreeNodePanel(this));
      this.rootNode.treePanel = this;
      this.updateStatus();
   }

   void updateStatus() {
      if (this.module.noDescent) {
         int[] aint = this.rootNode.formulaText.selectedRange;
         boolean flag = aint == null || aint.length == 0;
         this.statusLabel.setText(this.module.checkNow ? (this.isComplete() ? "Correct" : (flag ? "Incomplete" : "Incorrect")) : " ");
      } else {
         this.statusLabel.setText(this.module.checkNow ? (this.isComplete() ? "Complete" : "Incomplete") : " ");
      }

      this.rootNode.validate();
   }

   boolean isComplete() {
      return this.module.noDescent ? this.rootNode.formulaText.selectionCorrect : this.unexpandedCount == 0;
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      if (awtevent.getID() == 400) {
         LogicProgram.forwardKeyEvent(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
