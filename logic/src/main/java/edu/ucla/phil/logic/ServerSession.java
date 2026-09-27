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
   ErrorRef error;
   private static int cnonceCounter = 0;

   ServerSession(Credentials credentialsx, String s) {
      this.credentials = credentialsx;
      this.setNonce(s);
      this.params = null;
      this.digestFields = null;
      this.cnonce = null;
      this.error = null;
   }

   void setNonce(String s) {
      this.nonce = s;
      this.nonceCount = 0;
   }

   void nextRequest() {
      this.nonceCount++;
      this.cnonce = generateCnonce();
   }

   static String generateCnonce() {
      return Scrambler.md5Hex(System.currentTimeMillis() + ":" + LogicProgram.toHex8(++cnonceCounter));
   }

   void setParams(Hashtable hashtable, String[] astring) {
      this.params = hashtable;
      this.digestFields = astring;
   }

   void setParams(Hashtable hashtable, String s) {
      this.setParams(hashtable, splitFieldList(s));
   }

   static String[] splitFieldList(String s) {
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

   String joinDigestFields() {
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
               md5outputstream.writeFile((File)object, true);
            } catch (IOException ioexception) {
            }
         }
      }

      return new HexEncoder(md5outputstream.digest()).toHexString(true);
   }

   String encodeForm() {
      this.nextRequest();
      String s = "";
      String s1 = LogicProgram.toHex8(this.nonceCount);
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
      s = s + "dgst=" + URLEncoder.encode(this.joinDigestFields()) + "&";
      s = s
         + "auth="
         + Scrambler.md5Hex(this.credentials.user + ":" + this.nonce + ":" + s1 + ":" + this.credentials.password + ":" + this.digestParams())
         + "&";
      return s + "cnonce=" + URLEncoder.encode(this.cnonce);
   }

   Vector encodeMultipart(String s) {
      this.nextRequest();
      Vector vector = new Vector();
      String s1 = LogicProgram.toHex8(this.nonceCount);
      if (this.params != null) {
         Enumeration enumeration = this.params.keys();

         while (enumeration.hasMoreElements()) {
            String s2 = (String)enumeration.nextElement();
            this.addMultipartField(vector, s, s2, this.params.get(s2));
         }
      }

      this.addMultipartField(vector, s, "user", this.credentials.user);
      this.addMultipartField(vector, s, "nonce", this.nonce);
      this.addMultipartField(vector, s, "nc", s1);
      this.addMultipartField(vector, s, "dgst", this.joinDigestFields());
      this.addMultipartField(
         vector, s, "auth", Scrambler.md5Hex(this.credentials.user + ":" + this.nonce + ":" + s1 + ":" + this.credentials.password + ":" + this.digestParams())
      );
      this.addMultipartField(vector, s, "cnonce", this.cnonce);
      vector.addElement("--" + s + "--");
      return vector;
   }

   void addMultipartField(Vector vector, String s, String s1, Object object) {
      vector.addElement("--" + s);
      vector.addElement("Content-Disposition: form-data; name=\"" + s1 + "\"");
      vector.addElement("");
      vector.addElement(object);
   }

   @Override
   public void setError(String s, Hashtable hashtable) {
      if (this.error == null) {
         this.error = new ErrorRef(null);
      }

      this.error.setError(s, hashtable);
   }

   @Override
   public ErrorRef getError() {
      return this.error;
   }
}
