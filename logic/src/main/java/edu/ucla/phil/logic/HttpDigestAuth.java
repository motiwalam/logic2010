package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

class HttpDigestAuth {
   String scheme;
   String realm;
   String nonce;
   int nonceCount;
   String opaque;
   boolean stale;
   String algorithm;
   Vector qopOptions;
   String cnonce;
   Credentials credentials;
   static Hashtable credentialCache = new Hashtable();

   HttpDigestAuth(String s, String s1) {
      this.parseChallenge(s, s1);
      this.credentials = null;
   }

   void parseChallenge(String s, String s1) {
      AuthHeaderParams authheaderparams = new AuthHeaderParams(s, false);
      this.scheme = authheaderparams.getScheme();
      this.realm = authheaderparams.getParam("realm");
      this.nonce = authheaderparams.getParam("nonce");
      this.nonceCount = 0;
      this.opaque = authheaderparams.getParam("opaque");
      this.stale = "true".equalsIgnoreCase(authheaderparams.getParam("stale"));
      this.algorithm = authheaderparams.getParam("algorithm");
      this.qopOptions = splitList(authheaderparams.getParam("qop"), true, true);
      this.cnonce = s1;
   }

   boolean lookupCachedCredentials() {
      if (this.realm == null) {
         return false;
      } else {
         Credentials credentialsx = (Credentials)credentialCache.get(this.realm.toLowerCase());
         if (credentialsx == null) {
            return false;
         } else {
            this.credentials = credentialsx;
            return true;
         }
      }
   }

   void setCredentials(String s, String s1) {
      this.credentials = new Credentials(s, s1);
      if (this.realm != null) {
         credentialCache.put(this.realm.toLowerCase(), this.credentials);
      }
   }

   String getAuthorizationHeader(String s, String s1, String s2) {
      String s3;
      if (this.qopOptions == null) {
         s3 = null;
      } else if (s2 != null && this.qopOptions.contains("auth-int")) {
         s3 = "auth-int";
      } else if (this.qopOptions.contains("auth")) {
         s3 = "auth";
      } else {
         s3 = null;
      }

      if (this.scheme == null) {
         return null;
      } else if (this.scheme.equalsIgnoreCase("Basic")) {
         String s6 = this.getBasicToken();
         return s6 == null ? null : "Basic " + s6;
      } else if (this.scheme.equalsIgnoreCase("Digest")) {
         String s4 = this.computeDigestResponse(s3, s, s1, s2);
         if (s4 == null) {
            return null;
         } else {
            String s5 = "Digest ";
            s5 = s5 + "username=\"" + this.credentials.user + "\"";
            s5 = s5 + ", realm=\"" + this.realm + "\"";
            s5 = s5 + ", nonce=\"" + this.nonce + "\"";
            s5 = s5 + ", uri=\"" + s1 + "\"";
            String s7 = s5 + ", response=\"" + s4 + "\"";
            if (this.algorithm != null) {
               s7 = s7 + ", algorithm=\"" + this.algorithm + "\"";
            }

            if (s3 != null) {
               s5 = s7 + ", nc=\"" + LogicProgram.toHex8(this.nonceCount) + "\"";
               s5 = s5 + ", cnonce=\"" + this.cnonce + "\"";
               s7 = s5 + ", qop=\"" + s3 + "\"";
            }

            if (this.opaque != null) {
               s7 = s7 + ", opaque=\"" + this.opaque + "\"";
            }

            return s7;
         }
      } else {
         return null;
      }
   }

   String checkAuthenticationInfo(String s, String s1, String s2, boolean flag) {
      AuthHeaderParams authheaderparams = new AuthHeaderParams(s, true);
      String s3 = authheaderparams.getParam("nextnonce");
      String s4 = authheaderparams.getParam("rspauth");
      String s5 = authheaderparams.getParam("qop");
      String s6 = authheaderparams.getParam("cnonce");
      Integer integer = LogicProgram.parseInteger(authheaderparams.getParam("nc"), 16);
      if (!flag) {
         return s3;
      } else if (s4 != null && s6.equals(this.cnonce) && integer != null && integer == this.nonceCount) {
         if (s5 != null) {
            if (s5.equalsIgnoreCase("auth")) {
               s5 = "auth";
            } else {
               if (!s5.equalsIgnoreCase("auth-int")) {
                  return null;
               }

               s5 = "auth-int";
            }
         }

         String s7 = this.computeDigestResponse(s5, "", s1, s2);
         return !s4.equals(s7) ? null : s3;
      } else {
         return null;
      }
   }

   String getBasicToken() {
      return this.realm == null ? null : Scrambler.md5Base64(this.credentials.user + ":" + this.credentials.password);
   }

   String computeDigestResponse(String s, String s1, String s2, String s3) {
      if (this.realm == null || this.nonce == null) {
         return null;
      } else if (s == null && this.qopOptions != null) {
         return null;
      } else {
         String s4;
         if (this.algorithm != null && !this.algorithm.equalsIgnoreCase("MD5")) {
            if (!this.algorithm.equalsIgnoreCase("MD5-sess")) {
               return null;
            }

            s4 = Scrambler.md5Hex(this.credentials.user + ":" + this.realm + ":" + this.credentials.password) + ":" + this.nonce + ":" + this.cnonce;
         } else {
            s4 = this.credentials.user + ":" + this.realm + ":" + this.credentials.password;
         }

         String s5;
         if (s != null && !s.equals("auth")) {
            s5 = s1 + ":" + s2 + ":" + s3;
         } else {
            s5 = s1 + ":" + s2;
         }

         if (s == null) {
            return Scrambler.md5Hex(Scrambler.md5Hex(s4) + ":" + this.nonce + ":" + Scrambler.md5Hex(s5));
         } else {
            this.nonceCount++;
            return Scrambler.md5Hex(
               Scrambler.md5Hex(s4) + ":" + this.nonce + ":" + LogicProgram.toHex8(this.nonceCount) + ":" + this.cnonce + ":" + s + ":" + Scrambler.md5Hex(s5)
            );
         }
      }
   }

   static Vector splitList(String s, boolean flag, boolean flag1) {
      if (s == null) {
         return null;
      } else {
         Vector vector = new Vector();

         while (s != null) {
            int i = s.indexOf(44);
            String s1;
            if (i == -1) {
               s1 = s;
               s = null;
            } else {
               s1 = s.substring(0, i);
               s = s.substring(i + 1);
            }

            if (flag) {
               s1 = s1.trim();
            }

            if (flag1) {
               s1 = s1.toLowerCase();
            }

            vector.addElement(s1);
         }

         return vector;
      }
   }
}
