package edu.ucla.phil.logic;

import java.util.Vector;

class C_y_E {
   Vector f1461 = null;
   C_n_F f1462 = null;
   ProblemSelector f1463 = null;

   C_y_E() {
   }

   C_y_E(String s, C_RD c_rd) {
      this(s, c_rd, false);
   }

   C_y_E(String s, C_RD object, boolean flag) {
      this();
      int i = s.indexOf(58);
      if (object == null) {
         object = new C_o_F();
      }

      if (i == -1) {
         ((C_RD)object).m1204(s, this.f1461 = new Vector(), this.f1462 = new C_n_F(), flag);
         this.f1463 = new ProblemSelector().m401();
      } else {
         ((C_RD)object).m1204(s.substring(0, i), this.f1461 = new Vector(), this.f1462 = new C_n_F(), flag);
         this.f1463 = new ProblemSelector(s.substring(i + 1));
      }
   }

   C_y_E m2195(String s) {
      this.f1463.m403(s);
      return this;
   }

   void m2196(String s, Vector vector, C_n_F c_n_f) {
      if (this.f1463 != null && (s == null ? this.f1463.m405('u') : this.f1463.m404(s))) {
         LogicProgram.m1049(this.f1461, vector, true);
         c_n_f.m1975(this.f1462);
      }
   }
}
