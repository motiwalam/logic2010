package edu.ucla.phil.logic;

import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;

class C_t_B extends C_LB {
   C_ZE f1374;

   static C_t_B m2084() {
      return m2085(LogicProgram.f533, C_KC.m861());
   }

   static C_t_B m2085(C_OE c_oe, C_MF c_mf) {
      C_t_B c_t_b = new C_t_B();
      JPanel jpanel = new JPanel();
      jpanel.setBackground(LogicProgram.f605[1]);
      jpanel.setLayout(new C_m_A(0, LogicProgram.f539 / 5));
      c_t_b.add(jpanel, "East");
      C_ZE c_ze;
      jpanel.add(c_ze = c_t_b.f1374 = new C_ZE("Page ? of ?"));
      c_ze.setBackground(LogicProgram.f605[1]);
      SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE, M/d/yy K:mm a");
      jpanel.add(c_ze = new C_ZE("Date: " + simpledateformat.format(new Date())));
      c_ze.setBackground(LogicProgram.f605[1]);
      JPanel jpanel1 = new JPanel();
      jpanel1.setBackground(LogicProgram.f605[1]);
      c_t_b.add(jpanel1, "Center");
      JPanel jpanel2 = new JPanel();
      jpanel2.setBackground(LogicProgram.f605[1]);
      jpanel2.setLayout(new C_m_A(0, LogicProgram.f539 / 5));
      c_t_b.add(jpanel2, "West");
      if (c_oe != null) {
         jpanel2.add(c_ze = new C_ZE("Class: " + c_oe.m1149("className", "")));
         c_ze.setBackground(LogicProgram.f605[1]);
         jpanel.add(c_ze = new C_ZE("Name: " + c_oe.m1169()));
         c_ze.setBackground(LogicProgram.f605[1]);
      }

      if (c_mf != null) {
         C_LB c_lb = new C_LB();
         c_lb.setLayout(new GridBagLayout());
         jpanel2.add(c_lb);
         C_n_E.m1967(c_lb, c_mf);
      }

      C_LB c_lb1 = new C_LB(-1, LogicProgram.f539 * 2);
      c_lb1.setBackground(LogicProgram.f605[1]);
      c_t_b.add(c_lb1, "South");
      return c_t_b;
   }
}
