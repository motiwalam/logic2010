package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_h_E extends C_H {
   static Hashtable f1162 = null;
   static String f1163 = "symMessages";

   C_h_E(String s) {
      super(s);
   }

   static boolean m410() {
      C_XB c_xb = LogicProgram.m1062(f1163, false);
      if (c_xb == null) {
         return false;
      } else {
         f1162 = m658(new C_XD(c_xb, true));
         return f1162 != null;
      }
   }

   static C_H m411(String s) {
      C_H c_h = f1162 == null ? null : (C_H)f1162.get(s.toLowerCase());
      if (c_h == null) {
         c_h = new C_H(s);
         c_h.f371 = "bad error id";
         c_h.f372 = "The program has encountered an unknown error id.  Please report this: " + c_h.f370;
         c_h.f374 = true;
      }

      return c_h;
   }

   static String m412(String s) {
      return m659(m411(s));
   }
}
