package edu.ucla.phil.logic;

import java.util.Vector;

class Theorem extends SchematicRule {
   Integer f700;

   Theorem(Integer integer, String s) {
      super("T" + integer, ".:" + s);
      this.f700 = integer;
   }

   Theorem(Integer integer, Expression expression) {
      super("T" + integer, null, expression);
      this.f700 = integer;
   }

   Integer m1191() {
      return this.f700;
   }

   Expression m1192() {
      return this.conclusion;
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
      return s + this.conclusion.toString();
   }
}
