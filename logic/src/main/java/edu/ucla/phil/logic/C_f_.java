package edu.ucla.phil.logic;

import java.awt.Event;
import java.awt.Frame;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.text.StyledDocument;

class C_f_ extends C_M implements C_n_A {
   JScrollPane f1095;
   String[] f1096;
   C_IF[] f1097;
   C_IF f1098;
   String f1099 = null;
   Hashtable f1100 = null;
   int f1101;
   int f1102;
   static String[] f1103 = LogicProgram.f596;

   C_f_(C_j_D c_j_d, int i, Frame frame) {
      this(c_j_d, i, frame, false);
   }

   C_f_(C_j_D c_j_d, int i, Frame frame, boolean flag) {
      this.f1101 = c_j_d.size();
      this.f1102 = c_j_d.f1189.size();
      C_LB c_lb = new C_LB();
      C_QF c_qf = new C_QF(2, this.f1101 + this.f1102, 0, 0, true, true);
      c_qf.setVgap(2);
      c_qf.setHgap(2);
      c_lb.setLayout(c_qf);
      this.f1096 = new String[this.f1101 + this.f1102];
      this.f1097 = new C_IF[this.f1101 + this.f1102];
      Enumeration enumeration = c_j_d.keys();

      for (int j = 0; enumeration.hasMoreElements(); j++) {
         C_GF c_gf = c_j_d.m1878((C_i_A)enumeration.nextElement());
         this.f1096[j] = LogicProgram.m995(c_gf.f367.toString(), maggie, f1103);
         this.f1097[j] = new C_IF(LogicProgram.m995(c_gf.f368.toString(), maggie, f1103), i, frame);
         this.f1097[j].setName("Substitution for " + this.f1096[j]);
         C_ZE c_ze;
         c_lb.add(c_ze = new C_ZE(this.f1096[j]), new Point(0, j));
         c_ze.setFocusable(false);
         c_lb.add(this.f1097[j], new Point(1, j));
         this.f1097[j].setEditable(false);
      }

      for (int l = this.f1101; l < this.f1101 + this.f1102; l++) {
         C_i_A c_i_a = (C_i_A)c_j_d.f1189.elementAt(l - this.f1101);
         C_CB c_cb = null;
         C_n_F c_n_f = null;
         if (flag) {
            c_n_f = C_n_F.m1971(0, c_i_a.m1173().length());
            Vector vector = new Vector();

            for (int k = this.f1101; k < l; k++) {
               vector.addElement(null);
            }

            vector.addElement(c_n_f);
            c_cb = C_CB.m420(vector);
         }

         String s = this.f1096[l] = LogicProgram.m997(c_i_a.toString(), maggie, f1103, c_n_f);
         if (flag) {
            StyledDocument styleddocument = c_cb.m418(s);
            C_p_A c_p_a;
            c_lb.add(c_p_a = new C_p_A(styleddocument, -1, -1, false), new Point(0, l));
            c_p_a.setEditable(false);
            c_p_a.setFocusable(false);
         } else {
            C_ZE c_ze1;
            c_lb.add(c_ze1 = new C_ZE(s), new Point(0, l));
            c_ze1.setFocusable(false);
         }

         this.f1097[l] = new C_IF("", i, frame);
         this.f1097[l].setName("Substitution for " + c_i_a);
         c_lb.add(this.f1097[l], new Point(1, l));
      }

      this.f1098 = this.f1102 == 0 ? null : this.f1097[this.f1101];
      this.add(this.f1095 = new JScrollPane(c_lb, 22, 31));
   }

   void m1801(C_UA c_ua) {
      int i = this.f1097.length;

      for (int j = 0; j < i; j++) {
         if (this.f1097[j] != null) {
            this.f1097[j].f425 = c_ua;
         }
      }
   }

   @Override
   public void addNotify() {
      super.addNotify();
      this.validate();
      this.getParent().invalidate();
      this.m936(this.getPreferredSize());
      this.f1095.getVerticalScrollBar().setUnitIncrement(this.getGraphics().getFontMetrics().getHeight());
   }

   @Override
   public boolean gotFocus(Event event, Object object) {
      boolean flag = super.gotFocus(event, object);
      if (this.f1098 != null) {
         this.f1098.requestFocus();
         this.f1098 = null;
      }

      return flag;
   }

   C_j_D m1802() {
      C_j_D c_j_d = new C_j_D();
      String s = null;

      for (int i = 0; i < this.f1096.length; i++) {
         C_RF c_rf;
         C_RF c_rf1;
         try {
            s = this.f1096[i];
            c_rf = LogicProgram.m1008(LogicProgram.m995(s, f1103, maggie), true, true);
            s = this.f1097[i].getText();
            c_rf1 = LogicProgram.m1008(LogicProgram.m995(s, f1103, maggie), true, true);
         } catch (C_k_B c_k_b) {
            this.f1099 = "dererr059";
            this.f1100 = C_H.m666("parser error", s);
            return null;
         }

         if (c_rf1 == null) {
            c_j_d.m1885(c_rf);
         } else {
            if (c_rf1.m1265(c_rf)) {
               this.f1099 = "dererr072";
               this.f1100 = C_H.m667("pattern", "\\l" + c_rf + "\\l", "replacement", "\\l" + c_rf1 + "\\l");
               return null;
            }

            if (!c_j_d.m1881(c_rf, c_rf1)) {
               this.f1099 = c_j_d.f1190;
               this.f1100 = c_j_d.f1191;
               return null;
            }
         }
      }

      return c_j_d;
   }

   C_p_A[] m1803() {
      if (this.f1102 == 0) {
         return null;
      } else {
         C_p_A[] ac_p_a = new C_p_A[this.f1102];

         for (int i = 0; i < this.f1102; i++) {
            ac_p_a[i] = this.f1097[this.f1101 + i];
         }

         return ac_p_a;
      }
   }
}
