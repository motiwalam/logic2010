package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

class C_CD {
   String f258 = null;
   String f259 = null;
   C_d_C f260;
   C_d_C f261;
   Vector f262;
   Vector f263;
   C_RE f264;

   C_CD(C_d_C c_d_c, C_d_C c_d_c1, Vector vector, Vector vector1) {
      this.f260 = c_d_c;
      this.f261 = c_d_c1;
      this.f262 = (Vector)vector.clone();
      this.f263 = (Vector)vector1.clone();
   }

   void m424() {
      C_H c_h = C_h_E.m411("symnot001");
      this.f258 = c_h.f370;
      this.f259 = C_H.m661(c_h.f372, m425(this.f261, this.f262, this.f263));
      this.f264 = new C_RE(c_h.f373);
   }

   static Hashtable m425(C_d_C c_d_c, Vector vector, Vector vector1) {
      String s = C_f_A.m1807(c_d_c.m1682(), vector1, vector);
      String s1 = c_d_c.m1729(vector1, vector);
      return C_H.m667("right statement", s, "right type", s1);
   }

   void m426() {
      this.m424();
      this.f260.f1045.f1438.requestFocus();
      this.f264.m447("target", this.f260);
      this.f264.m447("answer", this.f261);
      this.f264.m447("targetBinders", (Vector)this.f262.clone());
      this.f264.m447("answerBinders", (Vector)this.f263.clone());
      C_WB.m1454(this.f258, this.f259, this.f264);
   }
}
