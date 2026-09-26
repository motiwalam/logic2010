package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_ND extends C_H {
   static Hashtable f648 = null;
   static String f649 = "parMessages";

   C_ND(String s) {
      super(s);
   }

   static boolean m410() {
      C_XB c_xb = LogicProgram.m1062(f649, false);
      if (c_xb == null) {
         return false;
      } else {
         f648 = m658(new C_XD(c_xb, true));
         return f648 != null;
      }
   }

   static C_H m411(String s) {
      C_H c_h = f648 == null ? null : (C_H)f648.get(s.toLowerCase());
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
