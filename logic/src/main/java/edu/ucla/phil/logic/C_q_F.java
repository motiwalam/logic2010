package edu.ucla.phil.logic;

import java.util.Vector;

public class C_q_F extends C_y_A {
   public C_q_F(String s) {
      super(s);
   }

   @Override
   void m1212() {
      this.f738 = 2;
   }

   public void m2040(C_y_A c_y_a) {
      this.f740.addElement(c_y_a);
      this.f741++;
   }

   public void m2041(C_y_A c_y_a) {
      this.f740.addElement(c_y_a);
      this.f741++;
   }

   C_y_A m2042() {
      return (C_y_A)this.m1217(0);
   }

   C_y_A m2043() {
      return (C_y_A)this.m1217(1);
   }

   @Override
   C_RF m1240(C_RF c_rf, C_j_D c_j_d, C_MB c_mb, Vector vector) {
      C_q_F c_q_f1 = new C_q_F(this.f739);
      c_q_f1.f742 = this.f742;

      for (int i = 0; i < this.f741; i++) {
         c_q_f1.m1215(this.m1217(i).m1240(c_rf, c_j_d, c_mb, vector));
      }

      return c_q_f1;
   }

   String m2044(C_y_A c_y_a, boolean flag, int i) {
      String s = c_y_a.m1207(i);
      if (!(c_y_a instanceof C_q_F) || c_y_a.f741 == 1) {
         return s;
      } else if (this.f741 != 1 && !this.f739.equals("&") && !this.f739.equals("|")) {
         return !c_y_a.f739.equals("->") && !c_y_a.f739.equals("<->") ? s : "(" + s + ")";
      } else {
         return !flag && this.f739.equals(c_y_a.f739) ? s : "(" + s + ")";
      }
   }

   @Override
   String m1207(int i) {
      C_y_A c_y_a = (C_y_A)this.m1217(0);
      if ((i == -1 || i == 0 && this.f742) && this.f739.equals("~") && c_y_a.f739.equals("=")) {
         return c_y_a.m1217(0).m1207(i) + "<>" + c_y_a.m1217(1).m1207(i);
      } else if (this.f741 == 1) {
         return this.f739 + this.m2044(c_y_a, true, i);
      } else {
         C_y_A c_y_a1 = (C_y_A)this.m1217(1);
         return this.m2044(c_y_a, false, i) + this.f739 + this.m2044(c_y_a1, true, i);
      }
   }

   @Override
   String m1209(int i) {
      C_y_A c_y_a = (C_y_A)this.m1217(0);
      if ((i == -1 || i == 0 && this.f742) && this.f739.equals("~") && c_y_a.f739.equals("=")) {
         return c_y_a.m1217(0).m1209(i) + "<>" + c_y_a.m1217(1).m1209(i);
      } else if (this.f741 == 1) {
         return this.f739 + c_y_a.m1209(i);
      } else {
         C_y_A c_y_a1 = (C_y_A)this.m1217(1);
         return "(" + c_y_a.m1209(i) + this.f739 + c_y_a1.m1209(i) + ")";
      }
   }

   @Override
   void m1211(C_DD c_dd) {
      super.m1211(c_dd);
      if (this.f742 && this.f739.equals("~") && this.m1217(0).f739.equals("=")) {
         c_dd.f281 = c_dd.m458(0).f281;
         c_dd.m458(0).f277 = c_dd;
         c_dd.m458(1).f277 = c_dd;
         c_dd.f283 = (c_dd.m458(1).f282 = (c_dd.m458(0).f282 = 0) + c_dd.m458(0).f283 + "<>".length()) + c_dd.m458(1).f283;
      } else if (this.f741 == 1) {
         c_dd.f283 = (c_dd.m458(0).f282 = this.f739.length()) + c_dd.m458(0).f283;
      } else {
         c_dd.f283 = (c_dd.m458(1).f282 = (c_dd.m458(0).f282 = 0) + c_dd.m458(0).f283 + this.f739.length()) + c_dd.m458(1).f283;
      }
   }

   public C_q_F m2045() {
      this.f742 = true;
      return this;
   }
}
