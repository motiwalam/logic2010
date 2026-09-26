package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import javax.swing.border.EmptyBorder;

class C_f_D extends CellPanel implements C_LC {
   C_y_D f1112;
   C_ZE f1113;
   C_j_[] f1114;
   C_z_E f1115;
   static final String f1116 = "OIN";
   static final String[] f1117 = new String[]{"Official Notation", "Informal Notation", "Not Well Formed"};

   C_f_D(C_y_D c_y_d) {
      this.f1112 = c_y_d;
      this.setLayout(new C_m_A());
      this.setBorder(new EmptyBorder(0, 5, 0, 5));
      this.add(this.f1113 = new C_ZE(" "));
      this.f1114 = new C_j_[f1117.length];
      this.f1115 = new C_z_E();

      for (int i = 0; i < f1117.length; i++) {
         this.f1115.add(this.f1114[i] = new C_j_(f1117[i], this), null, i);
      }

      this.add(this.f1115);
   }

   void m1808(String s) {
      this.f1113.setText(s);
   }

   int m1809() {
      int i = this.f1114.length;

      for (int j = 0; j < i; j++) {
         if (this.f1114[j].isSelected()) {
            return j;
         }
      }

      return -1;
   }

   static String m1810(int i) {
      return i >= 0 && i < "OIN".length() ? "OIN".substring(i, i + 1) : null;
   }

   String m1811() {
      return m1810(this.m1809());
   }

   static int m1812(String s) {
      return s != null && s.length() == 1 ? "OIN".indexOf(s) : -1;
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      if (awtevent.getID() == 400) {
         LogicProgram.m1086(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
