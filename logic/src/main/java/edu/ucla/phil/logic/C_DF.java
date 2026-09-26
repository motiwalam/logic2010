package edu.ucla.phil.logic;

import java.awt.Container;
import java.awt.Rectangle;
import javax.swing.JComponent;

class C_DF extends C_DC {
   int f290;
   int vgap;

   C_DF(int i, int j) {
      super(i, j);
      this.f290 = i;
      this.vgap = j;
   }

   @Override
   public void layoutContainer(Container container) {
      super.layoutContainer(container);
      int i = container.getComponentCount();

      for (int j = 0; j < i; j++) {
         JComponent jcomponent = (JComponent)container.getComponent(j);
         if (jcomponent instanceof C_g_B) {
            C_v_A c_v_a = ((C_g_B)jcomponent).m1546();
            Rectangle rectangle = jcomponent.getBounds();
            Rectangle rectangle1 = c_v_a.getBounds();
            int k = ((DerivationBox)c_v_a).f917.f322.getPreferredSize().height;
            rectangle.y = rectangle1.y + (k - rectangle.height) / 2;
            jcomponent.setBounds(rectangle);
         }

         if (jcomponent instanceof DerivationLine) {
            DerivationLine derivationline = (DerivationLine)jcomponent;
            if (derivationline.f327 != null) {
               derivationline.f327.invalidate();
            }
         }
      }

      DerivationBox derivationbox = (DerivationBox)container;
      LPDerivation lpderivation = derivationbox.f915;
      if (derivationbox.f918 != null && derivationbox.m2124()) {
         Rectangle rectangle2 = LogicProgram.m1035(derivationbox, lpderivation.problem);
         Rectangle rectangle3 = LogicProgram.m1035(derivationbox.f917, lpderivation.problem);
         Rectangle rectangle4 = LogicProgram.m1035(derivationbox.f918, lpderivation.problem);
         Rectangle rectangle5 = new Rectangle();
         rectangle5.x = rectangle2.x + lpderivation.indent - 1;
         rectangle5.y = rectangle2.y + rectangle3.height - 1;
         rectangle5.width = lpderivation.proofWidths[0] - lpderivation.hSpacer.width - rectangle5.x;
         rectangle5.height = rectangle4.y + rectangle4.height - lpderivation.vSpacer.height - rectangle5.y;
         derivationbox.f919 = rectangle5;
      } else {
         derivationbox.f919 = null;
      }

      lpderivation.numbers.validate();
   }
}
