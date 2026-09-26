package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_LA extends C_H {
   static Hashtable f501 = null;
   static String f502 = "invMessages";

   C_LA(String s) {
      super(s);
   }

   static boolean m410() {
      C_XB c_xb = LogicProgram.m1062(f502, false);
      if (c_xb == null) {
         return false;
      } else {
         f501 = m658(new C_XD(c_xb, true));
         return f501 != null;
      }
   }

   static C_H m411(String s) {
      C_H c_h = f501 == null ? null : (C_H)f501.get(s.toLowerCase());
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
