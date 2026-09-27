package edu.ucla.phil.logic;

import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

class ExpandToggleButton extends BasicArrowButton implements SwingConstants {
   private CollapsibleNode target;

   ExpandToggleButton(CollapsibleNode collapsiblenode, boolean flag) {
      super(flag ? 5 : 3);
      this.target = collapsiblenode;
   }

   ExpandToggleButton(CollapsibleNode collapsiblenode) {
      this(collapsiblenode, true);
   }

   boolean isExpanded() {
      return this.getDirection() != 3;
   }

   CollapsibleNode getTarget() {
      return this.target;
   }

   void setExpanded(boolean flag) {
      if (flag != this.isExpanded()) {
         this.setDirection(flag ? 5 : 3);
         this.revalidate();
      }
   }

   @Override
   protected void fireActionPerformed(ActionEvent actionevent) {
      this.setExpanded(!this.isExpanded());
      super.fireActionPerformed(actionevent);
   }
}
