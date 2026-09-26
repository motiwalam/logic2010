package edu.ucla.phil.logic;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class C_S extends JList implements ListSelectionListener {
   C_b_A f743;

   C_S(String s) {
      this.setBackground(null);
      this.setForeground(null);
   }

   C_S(String[] astring, C_b_A c_b_a) {
      super(astring);
      this.setBackground(null);
      this.setForeground(null);
      this.f743 = c_b_a;
      this.getSelectionModel().addListSelectionListener(this);
   }

   @Override
   public void valueChanged(ListSelectionEvent listselectionevent) {
      int i = this.getSelectedIndex();
      if (i != -1) {
         this.f743.m1649(i);
      }

      this.clearSelection();
      this.f743.f990.setVisible(false);
   }
}
