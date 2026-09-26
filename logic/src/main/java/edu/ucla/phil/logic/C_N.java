package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

class C_N {
   String f629;
   String f630;
   String f631;
   int f632;
   String f633;
   boolean f634;
   String f635;
   Vector f636;
   String f637;
   C_p_D f638;
   static Hashtable f639 = new Hashtable();

   C_N(String s, String s1) {
      this.m1121(s, s1);
      this.f638 = null;
   }

   void m1121(String s, String s1) {
      C_OD c_od = new C_OD(s, false);
      this.f629 = c_od.m1142();
      this.f630 = c_od.m1143("realm");
      this.f631 = c_od.m1143("nonce");
      this.f632 = 0;
      this.f633 = c_od.m1143("opaque");
      this.f634 = "true".equalsIgnoreCase(c_od.m1143("stale"));
      this.f635 = c_od.m1143("algorithm");
      this.f636 = m1128(c_od.m1143("qop"), true, true);
      this.f637 = s1;
   }

   boolean m1122() {
      if (this.f630 == null) {
         return false;
      } else {
         C_p_D c_p_d = (C_p_D)f639.get(this.f630.toLowerCase());
         if (c_p_d == null) {
            return false;
         } else {
            this.f638 = c_p_d;
            return true;
         }
      }
   }

   void m1123(String s, String s1) {
      this.f638 = new C_p_D(s, s1);
      if (this.f630 != null) {
         f639.put(this.f630.toLowerCase(), this.f638);
      }
   }

   String m1124(String s, String s1, String s2) {
      String s3;
      if (this.f636 == null) {
         s3 = null;
      } else if (s2 != null && this.f636.contains("auth-int")) {
         s3 = "auth-int";
      } else if (this.f636.contains("auth")) {
         s3 = "auth";
      } else {
         s3 = null;
      }

      if (this.f629 == null) {
         return null;
      } else if (this.f629.equalsIgnoreCase("Basic")) {
         String s6 = this.m1126();
         return s6 == null ? null : "Basic " + s6;
      } else if (this.f629.equalsIgnoreCase("Digest")) {
         String s4 = this.m1127(s3, s, s1, s2);
         if (s4 == null) {
            return null;
         } else {
            String s5 = "Digest ";
            s5 = s5 + "username=\"" + this.f638.f1334 + "\"";
            s5 = s5 + ", realm=\"" + this.f630 + "\"";
            s5 = s5 + ", nonce=\"" + this.f631 + "\"";
            s5 = s5 + ", uri=\"" + s1 + "\"";
            s5 = s5 + ", response=\"" + s4 + "\"";
            if (this.f635 != null) {
               s5 = s5 + ", algorithm=\"" + this.f635 + "\"";
            }

            if (s3 != null) {
               s5 = s5 + ", nc=\"" + LogicProgram.m1014(this.f632) + "\"";
               s5 = s5 + ", cnonce=\"" + this.f637 + "\"";
               s5 = s5 + ", qop=\"" + s3 + "\"";
            }

            if (this.f633 != null) {
               s5 = s5 + ", opaque=\"" + this.f633 + "\"";
            }

            return s5;
         }
      } else {
         return null;
      }
   }

   String m1125(String s, String s1, String s2, boolean flag) {
      C_OD c_od = new C_OD(s, true);
      String s3 = c_od.m1143("nextnonce");
      String s4 = c_od.m1143("rspauth");
      String s5 = c_od.m1143("qop");
      String s6 = c_od.m1143("cnonce");
      Integer integer = LogicProgram.m1011(c_od.m1143("nc"), 16);
      if (!flag) {
         return s3;
      } else if (s4 != null && s6.equals(this.f637) && integer != null && integer == this.f632) {
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

         String s7 = this.m1127(s5, "", s1, s2);
         return !s4.equals(s7) ? null : s3;
      } else {
         return null;
      }
   }

   String m1126() {
      return this.f630 == null ? null : C_z_D.m2225(this.f638.f1334 + ":" + this.f638.f1335);
   }

   String m1127(String s, String s1, String s2, String s3) {
      if (this.f630 == null || this.f631 == null) {
         return null;
      } else if (s == null && this.f636 != null) {
         return null;
      } else {
         String s4;
         if (this.f635 != null && !this.f635.equalsIgnoreCase("MD5")) {
            if (!this.f635.equalsIgnoreCase("MD5-sess")) {
               return null;
            }

            s4 = C_z_D.m2230(this.f638.f1334 + ":" + this.f630 + ":" + this.f638.f1335) + ":" + this.f631 + ":" + this.f637;
         } else {
            s4 = this.f638.f1334 + ":" + this.f630 + ":" + this.f638.f1335;
         }

         String s5;
         if (s != null && !s.equals("auth")) {
            s5 = s1 + ":" + s2 + ":" + s3;
         } else {
            s5 = s1 + ":" + s2;
         }

         if (s == null) {
            return C_z_D.m2230(C_z_D.m2230(s4) + ":" + this.f631 + ":" + C_z_D.m2230(s5));
         } else {
            this.f632++;
            return C_z_D.m2230(C_z_D.m2230(s4) + ":" + this.f631 + ":" + LogicProgram.m1014(this.f632) + ":" + this.f637 + ":" + s + ":" + C_z_D.m2230(s5));
         }
      }
   }

   static Vector m1128(String s, boolean flag, boolean flag1) {
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
