package edu.ucla.phil.logic;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class C_DA implements ListCellRenderer {
   @Override
   public Component getListCellRendererComponent(JList jlist, Object object, int i, boolean flag, boolean flag1) {
      if (object instanceof CellRenderable) {
         ((CellRenderable)object).prerender(flag, flag1);
      }

      if (flag && ((Component)object).isEnabled()) {
         if (object instanceof CellRenderable) {
            ((CellRenderable)object).setCellBackground(jlist.getSelectionBackground());
            ((CellRenderable)object).setCellForeground(jlist.getSelectionForeground());
         } else {
            ((Component)object).setBackground(jlist.getSelectionBackground());
            ((Component)object).setForeground(jlist.getSelectionForeground());
         }
      }

      if (object instanceof C_ZE && !((Component)object).isEnabled()) {
         C_d_D c_d_d = new C_d_D(((C_ZE)object).getText());
         c_d_d.setBackground(null);
         c_d_d.setForeground(ModuleConstants.dialogBlue);
         return c_d_d;
      } else {
         return (Component)object;
      }
   }
}
