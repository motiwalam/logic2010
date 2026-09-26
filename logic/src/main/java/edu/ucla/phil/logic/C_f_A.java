package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;

class C_f_A extends C_g_C implements ActionListener, C_v_D {
   String f1104 = null;
   String f1105 = null;
   C_d_C f1106;
   C_d_C f1107;
   Vector f1108;
   Vector f1109;
   C_RE f1110;

   C_f_A(C_d_C c_d_c, C_d_C c_d_c1, Vector vector, Vector vector1) {
      super("Error");
      this.f1106 = c_d_c;
      this.f1107 = c_d_c1;
      this.f1108 = (Vector)vector.clone();
      this.f1109 = (Vector)vector1.clone();
      Color[] acolor = c_d_c.f1044.colors;
      this.setMargin(new Insets(0, 1, 0, 1));
      this.setForeground(acolor[4]);
      this.setBackground(acolor[3]);
      this.addActionListener(this);
   }

   void m1804() {
      C_H c_h = m1806(this.f1106, this.f1107, this.f1108, this.f1109);
      this.f1104 = c_h.f370;
      this.f1105 = C_H.m661(c_h.f372, m1805(this.f1106, this.f1107, this.f1108, this.f1109));
      this.f1110 = new C_RE(c_h.f373);
   }

   static Hashtable m1805(C_d_C c_d_c, C_d_C c_d_c1, Vector vector, Vector vector1) {
      String s = c_d_c.m1682();
      String s1 = c_d_c.m1728();
      String s2 = m1807(c_d_c1.m1682(), vector1, vector);
      String s3 = c_d_c1.m1729(vector1, vector);
      Hashtable hashtable = C_H.m669("wrong statement", s, "wrong type", s1, "right statement", s2, "right type", s3);
      if (c_d_c.m1726()) {
         C_H.m664(hashtable, "bound var", c_d_c.m1684());
      }

      return hashtable;
   }

   static C_H m1806(C_d_C c_d_c, C_d_C c_d_c1, Vector vector, Vector vector1) {
      C_H c_h;
      if (c_d_c1.f1048 == 11 && c_d_c.f1048 == 11) {
         String s = c_d_c.m1684();
         String s1 = c_d_c1.m1684();
         if (c_d_c1.m1725()) {
            s1 = C_d_C.m1730(s1, vector1, vector);
         } else {
            s1 = C_d_C.m1732(s1, vector1, vector);
         }

         if (s1 == null) {
            c_h = C_h_E.m411("symerr003");
         } else if (s1.equals(s)) {
            c_h = C_h_E.m411("symerr002");
         } else {
            c_h = C_h_E.m411("symerr001");
         }
      } else if (c_d_c1.f1048 == c_d_c.f1048 && c_d_c1.m1726()) {
         c_h = C_h_E.m411("symerr002");
      } else {
         c_h = C_h_E.m411("symerr001");
      }

      return c_h;
   }

   static String m1807(String s, Vector vector, Vector vector1) {
      Hashtable hashtable = new Hashtable();
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         String s1 = (String)vector.elementAt(j);
         String s2 = (String)vector1.elementAt(j);
         if (vector.lastIndexOf(s1) == j && vector1.lastIndexOf(s2) == j) {
            hashtable.put(s1, s2);
         }
      }

      return C_H.m662(s, hashtable, null);
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (this.f1106.f1044.hintsDisabled) {
         C_UA.m1328("Feature Disabled", "Hints are disabled for this problem.", null, null);
      } else {
         this.m1804();
         this.f1106.f1045.f1438.requestFocus();
         this.f1110.m447("target", this.f1106);
         this.f1110.m447("answer", this.f1107);
         this.f1110.m447("targetBinders", (Vector)this.f1108.clone());
         this.f1110.m447("answerBinders", (Vector)this.f1109.clone());
         C_WB.m1454(this.f1104, this.f1105, this.f1110);
      }
   }
}
