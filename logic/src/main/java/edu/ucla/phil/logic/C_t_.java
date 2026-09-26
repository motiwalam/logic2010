package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class C_t_ extends JPanel implements C_LC {
   C_ZE f1371;
   JTextArea f1372;
   C_JE f1373;

   C_t_(C_JE c_je) {
      this.f1373 = c_je;
      this.f1371 = new C_ZE();
      this.f1372 = new C_NC();
      this.f1372.setEditable(false);
      this.f1372.setLineWrap(true);
      this.f1372.setWrapStyleWord(true);
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      jpanel.add(this.f1371, "North");
      JPanel jpanel1 = new JPanel();
      jpanel1.setLayout(c_je.f437);
      jpanel1.add(this.f1372);
      this.add(jpanel);
      this.add(jpanel1);
      int i = c_je.f438 / 4;
      int j = c_je.f438 / 10;
      this.setLayout(new FlowLayout(0, i, j));
   }
}
