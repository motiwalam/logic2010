package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_n_ extends C_H {
   static Hashtable f1299 = null;
   static String f1300 = "derMessages";

   C_n_(String s) {
      super(s);
   }

   static boolean m410() {
      C_XB c_xb = LogicProgram.m1062(f1300, false);
      if (c_xb == null) {
         return false;
      } else {
         f1299 = m658(new C_XD(c_xb, true));
         return f1299 != null;
      }
   }

   static C_H m411(String s) {
      C_H c_h = f1299 == null ? null : (C_H)f1299.get(s.toLowerCase());
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

   static String m1960(String s, Hashtable hashtable, C_a_ c_a_) {
      return m1962(s, hashtable, c_a_, c_a_ == null ? null : c_a_.f935);
   }

   static String m1961(String s, Hashtable hashtable, C_G c_g) {
      return m1962(s, hashtable, null, c_g);
   }

   static String m1962(String s, Hashtable hashtable, C_a_ c_a_, C_G c_g) {
      if (c_g != null && !c_g.f317.f915.doSubs) {
         return s;
      } else {
         C_k_A[] ac_k_a = new C_k_A[]{c_a_, c_g};
         return m662(s, hashtable, ac_k_a);
      }
   }
}
