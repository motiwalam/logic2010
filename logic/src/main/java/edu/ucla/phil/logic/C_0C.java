package edu.ucla.phil.logic;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class C_0C implements C_v_ {
   C_p_D f17;
   String f18;
   int f19;
   Hashtable f20;
   String[] f21;
   String f22;
   C_c_B f23;
   private static int f24 = 0;

   C_0C(C_p_D c_p_d, String s) {
      this.f17 = c_p_d;
      this.m49(s);
      this.f20 = null;
      this.f21 = null;
      this.f22 = null;
      this.f23 = null;
   }

   void m49(String s) {
      this.f18 = s;
      this.f19 = 0;
   }

   void m50() {
      this.f19++;
      this.f22 = m51();
   }

   static String m51() {
      return C_z_D.m2230(System.currentTimeMillis() + ":" + LogicProgram.m1014(++f24));
   }

   void m52(Hashtable hashtable, String[] astring) {
      this.f20 = hashtable;
      this.f21 = astring;
   }

   void m53(Hashtable hashtable, String s) {
      this.m52(hashtable, m54(s));
   }

   static String[] m54(String s) {
      if (s != null && s.length() != 0) {
         Vector vector = new Vector();

         while (true) {
            int i = s.indexOf(".");
            if (i == -1) {
               vector.addElement(s);
               String[] astring = new String[vector.size()];
               vector.copyInto(astring);
               return astring;
            }

            vector.addElement(s.substring(0, i));
            s = s.substring(i + 1);
         }
      } else {
         return null;
      }
   }

   String m55() {
      String s = "";
      int i = this.f21 == null ? 0 : this.f21.length;

      for (int j = 0; j < i; j++) {
         s = s + (j == 0 ? "" : ".") + this.f21[j];
      }

      return s;
   }

   String m56() {
      C_KD c_kd = new C_KD();
      int i = this.f21 == null ? 0 : this.f21.length;

      for (int j = 0; j < i; j++) {
         Object object = this.f20.get(this.f21[j]);
         if (object instanceof String) {
            c_kd.write(((String)object).trim().getBytes());
         } else if (object instanceof byte[]) {
            c_kd.write((byte[])object);
         } else if (object instanceof File) {
            try {
               c_kd.m926((File)object, true);
            } catch (IOException ioexception) {
            }
         }
      }

      return new C_m_C(c_kd.m923()).m1947(true);
   }

   String m57() {
      this.m50();
      String s = "";
      String s1 = LogicProgram.m1014(this.f19);
      if (this.f20 != null) {
         Enumeration enumeration = this.f20.keys();

         while (enumeration.hasMoreElements()) {
            String s2 = (String)enumeration.nextElement();
            s = s + s2 + "=" + URLEncoder.encode((String)this.f20.get(s2)) + "&";
         }
      }

      s = s + "user=" + URLEncoder.encode(this.f17.f1334) + "&";
      s = s + "nonce=" + URLEncoder.encode(this.f18) + "&";
      s = s + "nc=" + s1 + "&";
      s = s + "dgst=" + URLEncoder.encode(this.m55()) + "&";
      s = s + "auth=" + C_z_D.m2230(this.f17.f1334 + ":" + this.f18 + ":" + s1 + ":" + this.f17.f1335 + ":" + this.m56()) + "&";
      return s + "cnonce=" + URLEncoder.encode(this.f22);
   }

   Vector m58(String s) {
      this.m50();
      Vector vector = new Vector();
      String s1 = LogicProgram.m1014(this.f19);
      if (this.f20 != null) {
         Enumeration enumeration = this.f20.keys();

         while (enumeration.hasMoreElements()) {
            String s2 = (String)enumeration.nextElement();
            this.m59(vector, s, s2, this.f20.get(s2));
         }
      }

      this.m59(vector, s, "user", this.f17.f1334);
      this.m59(vector, s, "nonce", this.f18);
      this.m59(vector, s, "nc", s1);
      this.m59(vector, s, "dgst", this.m55());
      this.m59(vector, s, "auth", C_z_D.m2230(this.f17.f1334 + ":" + this.f18 + ":" + s1 + ":" + this.f17.f1335 + ":" + this.m56()));
      this.m59(vector, s, "cnonce", this.f22);
      vector.addElement("--" + s + "--");
      return vector;
   }

   void m59(Vector vector, String s, String s1, Object object) {
      vector.addElement("--" + s);
      vector.addElement("Content-Disposition: form-data; name=\"" + s1 + "\"");
      vector.addElement("");
      vector.addElement(object);
   }

   @Override
   public void m4(String s, Hashtable hashtable) {
      if (this.f23 == null) {
         this.f23 = new C_c_B(null);
      }

      this.f23.m4(s, hashtable);
   }

   @Override
   public C_c_B m5() {
      return this.f23;
   }
}
