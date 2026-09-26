package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class C_D {
   Hashtable f270;
   int f271;
   String[] f272;
   String[] f273;

   C_D(String s) {
      if (s == null) {
         s = "OK";
      }

      this.f270 = null;
      this.f271 = -1;
      Vector vector = new Vector();
      Vector vector1 = new Vector();
      C_OA c_oa = new C_OA("\\;");
      C_OA c_oa1 = new C_OA("\\:.");
      C_OA c_oa2 = new C_OA("\\.");
      c_oa.m1132(s);
      c_oa1.m1132(c_oa.m1136(true));
      String s1 = c_oa.m1133();
      if (s1 != null) {
         Integer integer = LogicProgram.m1010(s1.trim());
         if (integer != null) {
            this.f271 = integer;
         }
      }

      while (true) {
         String s3 = c_oa1.m1135();
         String s2 = null;
         if (s3 == null) {
            this.f272 = new String[vector.size()];
            this.f273 = new String[vector.size()];
            vector.copyInto(this.f272);
            vector1.copyInto(this.f273);
            return;
         }

         if (c_oa1.m1134() == ':') {
            c_oa2.m1132(c_oa1.m1133());
            s2 = c_oa2.m1135().trim();
            c_oa1.m1132(c_oa2.m1133());
         }

         vector.addElement(s3.trim());
         vector1.addElement(s2);
      }
   }

   String[] m445() {
      return this.f272;
   }

   int m446() {
      return this.f271;
   }

   void m447(String s, Object object) {
      if (object != null) {
         if (this.f270 == null) {
            this.f270 = new Hashtable();
         }

         this.f270.put(s, object);
      }
   }

   void m448(Hashtable hashtable) {
      Enumeration enumeration = hashtable.keys();

      while (enumeration.hasMoreElements()) {
         String s = (String)enumeration.nextElement();
         Object object = hashtable.get(s);
         this.m447(s, object);
      }
   }

   Object m449(String s) {
      return this.f270 == null ? null : this.f270.get(s);
   }

   String m450(C_UA c_ua) {
      String s = c_ua.f791[c_ua.f790].getText();
      int i = LogicProgram.m1051(this.f272, s);
      return i == -1 ? null : this.f273[i];
   }

   boolean m451(C_UA c_ua) {
      String s = this.m450(c_ua);
      return s == null ? true : true;
   }
}
