package edu.ucla.phil.logic;

import java.util.Vector;

class C_QE extends C_LF {
   Integer f700;

   C_QE(Integer integer, String s) {
      super("T" + integer, ".:" + s);
      this.f700 = integer;
   }

   C_QE(Integer integer, C_RF c_rf) {
      super("T" + integer, null, c_rf);
      this.f700 = integer;
   }

   Integer m1191() {
      return this.f700;
   }

   C_RF m1192() {
      return this.f527;
   }

   @Override
   boolean m1193(C_w_E c_w_e, String s, boolean flag) {
      return c_w_e.hasProperty(this.f700, s);
   }

   @Override
   Vector m954(C_w_E c_w_e) {
      return c_w_e.getProofs(this.f700);
   }

   @Override
   public String toString() {
      return this.m958("", "");
   }

   @Override
   String m958(String s1, String s) {
      return s + this.f527.toString();
   }
}
