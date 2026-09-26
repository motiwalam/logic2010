package edu.ucla.phil.logic;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class ServerSession implements ResponseHandler {
   Credentials credentials;
   String nonce;
   int nonceCount;
   Hashtable params;
   String[] digestFields;
   String cnonce;
   ErrorRef f23;
   private static int f24 = 0;

   ServerSession(Credentials credentials, String s) {
      this.credentials = credentialsx;
      this.m49(s);
      this.params = null;
      this.digestFields = null;
      this.cnonce = null;
      this.f23 = null;
   }

   void m49(String s) {
      this.nonce = s;
      this.nonceCount = 0;
   }

   void m50() {
      this.nonceCount++;
      this.cnonce = m51();
   }

   static String m51() {
      return Scrambler.md5Hex(System.currentTimeMillis() + ":" + LogicProgram.m1014(++f24));
   }

   void m52(Hashtable hashtable, String[] astring) {
      this.params = hashtable;
      this.digestFields = astring;
   }

   void setParams(Hashtable hashtable, String s) {
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
      int i = this.digestFields == null ? 0 : this.digestFields.length;

      for (int j = 0; j < i; j++) {
         s = s + (j == 0 ? "" : ".") + this.digestFields[j];
      }

      return s;
   }

   String digestParams() {
      Md5OutputStream md5outputstream = new Md5OutputStream();
      int i = this.digestFields == null ? 0 : this.digestFields.length;

      for (int j = 0; j < i; j++) {
         Object object = this.params.get(this.digestFields[j]);
         if (object instanceof String) {
            md5outputstream.write(((String)object).trim().getBytes());
         } else if (object instanceof byte[]) {
            md5outputstream.write((byte[])object);
         } else if (object instanceof File) {
            try {
               md5outputstream.m926((File)object, true);
            } catch (IOException ioexception) {
            }
         }
      }

      return new HexEncoder(md5outputstream.digest()).m1947(true);
   }

   String encodeForm() {
      this.m50();
      String s = "";
      String s1 = LogicProgram.m1014(this.nonceCount);
      if (this.params != null) {
         Enumeration enumeration = this.params.keys();

         while (enumeration.hasMoreElements()) {
            String s2 = (String)enumeration.nextElement();
            s = s + s2 + "=" + URLEncoder.encode((String)this.params.get(s2)) + "&";
         }
      }

      s = s + "user=" + URLEncoder.encode(this.credentials.user) + "&";
      s = s + "nonce=" + URLEncoder.encode(this.nonce) + "&";
      s = s + "nc=" + s1 + "&";
      s = s + "dgst=" + URLEncoder.encode(this.m55()) + "&";
      s = s
         + "auth="
         + Scrambler.md5Hex(this.credentials.user + ":" + this.nonce + ":" + s1 + ":" + this.credentials.password + ":" + this.digestParams())
         + "&";
      return s + "cnonce=" + URLEncoder.encode(this.cnonce);
   }

   Vector encodeMultipart(String s) {
      this.m50();
      Vector vector = new Vector();
      String s1 = LogicProgram.m1014(this.nonceCount);
      if (this.params != null) {
         Enumeration enumeration = this.params.keys();

         while (enumeration.hasMoreElements()) {
            String s2 = (String)enumeration.nextElement();
            this.m59(vector, s, s2, this.params.get(s2));
         }
      }

      this.m59(vector, s, "user", this.credentials.user);
      this.m59(vector, s, "nonce", this.nonce);
      this.m59(vector, s, "nc", s1);
      this.m59(vector, s, "dgst", this.m55());
      this.m59(
         vector, s, "auth", Scrambler.md5Hex(this.credentials.user + ":" + this.nonce + ":" + s1 + ":" + this.credentials.password + ":" + this.digestParams())
      );
      this.m59(vector, s, "cnonce", this.cnonce);
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
         this.f23 = new ErrorRef(null);
      }

      this.f23.m4(s, hashtable);
   }

   @Override
   public ErrorRef m5() {
      return this.f23;
   }
}
