package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_FE extends C_H {
   static Hashtable f315 = null;
   static String f316 = "truMessages";

   C_FE(String s) {
      super(s);
   }

   static boolean m410() {
      C_XB c_xb = LogicProgram.m1062(f316, false);
      if (c_xb == null) {
         return false;
      } else {
         f315 = m658(new C_XD(c_xb, true));
         return f315 != null;
      }
   }

   static C_H m411(String s) {
      C_H c_h = f315 == null ? null : (C_H)f315.get(s.toLowerCase());
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
