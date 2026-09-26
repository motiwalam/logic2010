package edu.ucla.phil.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

class C_K extends CellPanel implements ActionListener, C_LC {
   C_B f441;
   JButton f442;

   C_K(C_B c_b) {
      this.f441 = c_b;
      this.add(this.f442 = new C_g_C("OK"));
      this.f442.addActionListener(this);
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      C_GD c_gd = this.f441.f145.f846;
      if (actionevent.getActionCommand().equalsIgnoreCase("ok")) {
         c_gd.m642();
         c_gd.setSelected(false);
      }
   }
}
