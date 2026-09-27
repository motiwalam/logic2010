package edu.ucla.phil.logic;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ProblemListCellRenderer implements ListCellRenderer {
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

      if (object instanceof LogicLabel && !((Component)object).isEnabled()) {
         FontLabel fontlabel = new FontLabel(((LogicLabel)object).getText());
         fontlabel.setBackground(null);
         fontlabel.setForeground(ModuleConstants.dialogBlue);
         return fontlabel;
      } else {
         return (Component)object;
      }
   }
}
