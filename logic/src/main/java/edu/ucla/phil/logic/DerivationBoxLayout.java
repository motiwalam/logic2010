package edu.ucla.phil.logic;

import java.awt.Container;
import java.awt.Rectangle;
import javax.swing.JComponent;

class DerivationBoxLayout extends IndentedTreeLayout {
   int indentGap;
   int vgap;

   DerivationBoxLayout(int i, int j) {
      super(i, j);
      this.indentGap = i;
      this.vgap = j;
   }

   @Override
   public void layoutContainer(Container container) {
      super.layoutContainer(container);
      int i = container.getComponentCount();

      for (int j = 0; j < i; j++) {
         JComponent jcomponent = (JComponent)container.getComponent(j);
         if (jcomponent instanceof BoxToggleButton) {
            CollapsibleNode collapsiblenode = ((BoxToggleButton)jcomponent).getTarget();
            Rectangle rectangle = jcomponent.getBounds();
            Rectangle rectangle1 = collapsiblenode.getBounds();
            int k = ((DerivationBox)collapsiblenode).showLine.showLabel.getPreferredSize().height;
            rectangle.y = rectangle1.y + (k - rectangle.height) / 2;
            jcomponent.setBounds(rectangle);
         }

         if (jcomponent instanceof DerivationLine) {
            DerivationLine derivationline = (DerivationLine)jcomponent;
            if (derivationline.numberLabel != null) {
               derivationline.numberLabel.invalidate();
            }
         }
      }

      DerivationBox derivationbox = (DerivationBox)container;
      LPDerivation lpderivation = derivationbox.module;
      if (derivationbox.cancelLine != null && derivationbox.isExpanded()) {
         Rectangle rectangle2 = LogicProgram.boundsRelativeTo(derivationbox, lpderivation.problem);
         Rectangle rectangle3 = LogicProgram.boundsRelativeTo(derivationbox.showLine, lpderivation.problem);
         Rectangle rectangle4 = LogicProgram.boundsRelativeTo(derivationbox.cancelLine, lpderivation.problem);
         Rectangle rectangle5 = new Rectangle();
         rectangle5.x = rectangle2.x + lpderivation.indent - 1;
         rectangle5.y = rectangle2.y + rectangle3.height - 1;
         rectangle5.width = lpderivation.proofWidths[0] - lpderivation.hSpacer.width - rectangle5.x;
         rectangle5.height = rectangle4.y + rectangle4.height - lpderivation.vSpacer.height - rectangle5.y;
         derivationbox.bracketBounds = rectangle5;
      } else {
         derivationbox.bracketBounds = null;
      }

      lpderivation.numbers.validate();
   }
}
