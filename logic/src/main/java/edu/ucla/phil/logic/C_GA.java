package edu.ucla.phil.logic;

import java.util.Vector;

class C_GA extends C_HD {
   static final int f342 = 4;
   C_e_ f343 = null;
   boolean f344 = false;
   boolean f345 = false;
   C_HF f346 = null;
   C_G f347 = null;
   Integer f348 = null;
   C_j_D f349 = null;
   C__B f350 = null;
   C_G f351 = null;
   Integer f352 = null;
   C_PF f353 = null;
   boolean f354 = false;

   C_GA() {
      super("IE");
   }

   C_GA(C_e_ c_e_, boolean flag, C_HF c_hf) {
      this(c_e_, flag, c_hf, false, null);
   }

   C_GA(C_e_ c_e_, boolean flag, C_HF c_hf, boolean flag1, C_LF c_lf) {
      this();
      this.f343 = c_e_;
      this.f344 = flag;
      this.f346 = c_hf;
      this.m599(flag1, c_lf);
   }

   C_GA(C_e_ c_e_, boolean flag, C_G c_g, C_j_D c_j_d, C__B c__b) {
      this(c_e_, flag, c_g, c_j_d, c__b, false, null);
   }

   C_GA(C_e_ c_e_, boolean flag, C_G c_g, C_j_D c_j_d, C__B c__b, boolean flag1, C_LF c_lf) {
      this();
      this.f343 = c_e_;
      this.f344 = flag;
      this.f347 = c_g;
      this.f349 = c_j_d;
      this.f350 = c__b;
      this.m599(flag1, c_lf);
   }

   C_GA(C_e_ c_e_, boolean flag, Integer integer, C_j_D c_j_d, C__B c__b) {
      this(c_e_, flag, integer, c_j_d, c__b, false, null);
   }

   C_GA(C_e_ c_e_, boolean flag, Integer integer, C_j_D c_j_d, C__B c__b, boolean flag1, C_LF c_lf) {
      this();
      this.f343 = c_e_;
      this.f344 = flag;
      this.f348 = integer;
      this.f349 = c_j_d;
      this.f350 = c__b;
      this.m599(flag1, c_lf);
   }

   void m599(boolean flag, C_LF c_lf) {
      this.f354 = flag;
      if (c_lf instanceof C_l_F) {
         this.f351 = ((C_l_F)c_lf).f1263;
         this.f352 = null;
         this.f353 = null;
      } else if (c_lf instanceof C_OF) {
         this.f351 = null;
         this.f352 = ((C_OF)c_lf).f667;
         this.f353 = null;
      } else if (c_lf instanceof C_PF) {
         this.f351 = null;
         this.f352 = null;
         this.f353 = (C_PF)c_lf;
      }
   }

   @Override
   boolean m600(C_a_ c_a_) {
      boolean flag = this.f351 == null && this.f352 == null && this.f353 == null;
      String s = flag ? "IE" : "CIE";
      if (!c_a_.f939.equals(s)) {
         return false;
      } else {
         c_a_.getClass();
         if (!c_a_.f944 && !c_a_.f945 ? c_a_.f938 >= 1 : c_a_.f938 == 1) {
            if (c_a_.m1628(-1).m1220(this.f343) == null) {
               return false;
            } else {
               if (this.f346 != null) {
                  if (!m631(c_a_, this.f346.f391, flag)) {
                     return false;
                  }

                  if (!m602(this.f346, c_a_, this.f343, this.m622(c_a_))) {
                     return false;
                  }
               } else if (this.f347 != null) {
                  LPDerivation lpderivation = c_a_.f935.f317.f915;
                  if (lpderivation.problem.m32(this.f347.m30()) != this.f347.m25()) {
                     return false;
                  }

                  if (!c_a_.f935.m565(this.f347)) {
                     return false;
                  }

                  if (!m603(this.f347, c_a_, this.f343, flag)) {
                     return false;
                  }
               } else {
                  if (this.f348 == null) {
                     return false;
                  }

                  int i = this.f348;
                  LPDerivation lpderivation1 = c_a_.f935.f317.f915;
                  if (lpderivation1.premises == null || i < 0 || i >= lpderivation1.premises.length) {
                     return false;
                  }

                  if (!m604(this.f348, c_a_, this.f343, flag)) {
                     return false;
                  }
               }

               if (c_a_.m1615(-1) != null) {
                  c_a_.f935.f317.f922 = false;
               }

               C_RF c_rf = this.m618(c_a_);
               if (!c_rf.m1235(c_a_.m1628(-1))) {
                  return false;
               } else {
                  c_a_.f942 = this.m620(c_a_);
                  if (c_a_.f944 && !c_a_.m1609(true)) {
                     return false;
                  } else {
                     c_a_.m1631(1);
                     return true;
                  }
               }
            }
         } else {
            return false;
         }
      }
   }

   static boolean m601(C_a_ c_a_, C_e_ c_e_, C_LF c_lf) {
      return c_lf == null || m617(c_a_.m1628(-1), c_lf.f527, c_e_).m1259() == null;
   }

   static boolean m602(C_HF c_hf, C_a_ c_a_, C_e_ c_e_, C_LF c_lf) {
      C_LF c_lf1 = c_hf.m688();
      String s = c_lf == null ? "notConditional" : "notConditionalBC";
      if (LogicProgram.f534.f1472 != null && LogicProgram.f534.f1472.hasProperty(c_lf1, s)) {
         c_a_.m1622("dererr098", C_H.m666("inner rule name", c_lf1.f820));
         return false;
      } else if (!m601(c_a_, c_e_, c_lf)) {
         c_a_.m1622("dererr099", C_H.m666("condition name", c_lf.f820));
         return false;
      } else {
         return true;
      }
   }

   static boolean m603(C_G c_g, C_a_ c_a_, C_e_ c_e_, boolean flag) {
      C_RF c_rf;
      if (flag) {
         c_rf = C_r_D.m2062(c_g.f332, false);
      } else {
         c_rf = C_r_D.m2065(c_g.f332);
      }

      if (c_rf == null) {
         c_a_.m1622("dererr086", C_H.m666("remote line number", c_g.m30() + ""));
         return false;
      } else if (m617(c_a_.m1628(-1), c_g.f332, c_e_).m1259() != null) {
         c_a_.m1622("dererr087", C_H.m666("remote line number", c_g.m30() + ""));
         return false;
      } else {
         return true;
      }
   }

   static boolean m604(Integer integer, C_a_ c_a_, C_e_ c_e_, boolean flag) {
      int i = integer;
      LPDerivation lpderivation = c_a_.f935.f317.f915;
      C_RF c_rf;
      if (flag) {
         c_rf = C_r_D.m2062(lpderivation.premises[i], false);
      } else {
         c_rf = C_r_D.m2065(lpderivation.premises[i]);
      }

      if (c_rf == null) {
         c_a_.m1622("dererr088", C_H.m666("premise index", i + 1 + ""));
         return false;
      } else if (m617(c_a_.m1628(-1), lpderivation.premises[i], c_e_).m1259() != null) {
         c_a_.m1622("dererr089", C_H.m666("premise index", i + 1 + ""));
         return false;
      } else {
         return true;
      }
   }

   static C_j_D m605(C_RF c_rf) {
      C_j_D c_j_d = new C_j_D();
      c_rf.m1266(null, c_j_d);
      boolean flag = false;
      int i = c_j_d.f1189.size();

      for (int j = 0; j < i; j++) {
         C_i_A c_i_a = (C_i_A)c_j_d.f1189.elementAt(0);
         C_RF c_rf1 = c_i_a.m1175();
         c_j_d.m1880(c_i_a, new C_GF(c_rf1, c_rf1));
      }

      return c_j_d;
   }

   C_e_B m606(C_a_ c_a_) {
      return this.m607(c_a_, true);
   }

   C_e_B m607(C_a_ c_a_, boolean flag) {
      if (this.f346 == null) {
         C_e_B c_e_b = this.m608(this.m619(c_a_, true)).m463();
         C_e_B c_e_b1 = this.m608(this.m621(c_a_, true)).m463();
         if (flag && !c_e_b.m1765(c_e_b1)) {
            return c_e_b1;
         } else {
            c_e_b.m1761(".:");
            c_e_b.m1760(c_e_b1);
            return c_e_b;
         }
      } else {
         return this.m625().m700(null).m543(flag);
      }
   }

   C_DD m608(C_RF c_rf) {
      C_DD c_dd = new C_DD(c_rf, true, -1);
      c_dd.m460(this.f349.f1189);
      c_dd.m461(this.f350);
      return c_dd;
   }

   @Override
   String m609() {
      return "4:" + this;
   }

   @Override
   public String toString() {
      Object object = "";
      if (this.f343 != null) {
         object = object + this.f343;
      }

      object = object + (this.f344 ? "<" : ">");
      if (this.f346 != null) {
         object = object + this.f346;
      } else if (this.f347 != null) {
         object = object + this.f347.m30();
         if (this.f349 != null) {
            object = object + "," + this.f349.m1896();
         }

         if (this.f350 != null) {
            object = object + ";" + this.f350.m1580();
         }
      } else if (this.f348 != null) {
         object = object + "#" + this.f348;
         if (this.f349 != null) {
            object = object + "," + this.f349.m1896();
         }

         if (this.f350 != null) {
            object = object + ";" + this.f350.m1580();
         }
      }

      if (this.f351 != null) {
         object = object + (this.f354 ? "?<" : "?>");
         object = object + this.f351.m30();
      } else if (this.f352 != null) {
         object = object + (this.f354 ? "?<" : "?>");
         object = object + "#" + this.f352;
      } else if (this.f353 != null) {
         object = object + (this.f354 ? "?<" : "?>");
         object = object + "#" + this.f353.m1182();
      }

      return (String)object;
   }

   static C_GA m610(String s, LPDerivation lpderivation) {
      int i = s.indexOf(":");
      if (i == -1) {
         return null;
      } else {
         return !s.substring(0, i).equals(Integer.toString(4)) ? null : m611(s.substring(i + 1), lpderivation);
      }
   }

   static C_GA m611(String s, LPDerivation lpderivation) {
      int i = C_XD.m1480(s.indexOf("?>"), s.indexOf("?<"));
      String s1 = i == -1 ? null : s.substring(i + 1);
      if (i != -1) {
         s = s.substring(0, i);
      }

      i = C_XD.m1480(s.indexOf(">"), s.indexOf("<"));
      if (i == -1) {
         return null;
      } else {
         C_GA c_ga = new C_GA();
         c_ga.f343 = i == 0 ? null : C_e_.m1746(C_e_.m1755(s.substring(0, i)));
         c_ga.f344 = s.charAt(i) == '<';
         s = s.substring(i + 1);
         if ((c_ga.f346 = C_HF.m699(s)) == null && (c_ga.f347 = m612(s, lpderivation)) == null && (c_ga.f348 = m613(s, lpderivation)) != null) {
         }

         if (c_ga.f347 != null || c_ga.f348 != null) {
            c_ga.f349 = m614(s);
            c_ga.f350 = m615(s);
         }

         if (s1 != null) {
            c_ga.f354 = s1.charAt(0) == '<';
            s1 = s1.substring(1);
            if ((c_ga.f351 = m612(s1, lpderivation)) == null && (c_ga.f352 = m613(s1, lpderivation)) == null && (c_ga.f353 = m616(s1)) != null) {
            }
         }

         return c_ga;
      }
   }

   static C_G m612(String s, LPDerivation lpderivation) {
      int i = s.indexOf(",");
      if (i != -1 || (i = s.indexOf(";")) != -1) {
         s = s.substring(0, i);
      }

      Integer integer = LogicProgram.m1010(s);
      if (integer == null) {
         return null;
      } else {
         C_0B c_0b = lpderivation.problem.m32(integer);
         if (c_0b == null) {
            return null;
         } else {
            return c_0b instanceof C_G ? (C_G)c_0b : ((C__)c_0b).f917;
         }
      }
   }

   static Integer m613(String s, LPDerivation lpderivation) {
      if (lpderivation.premises == null) {
         return null;
      } else {
         int i = s.indexOf(",");
         if (i != -1 || (i = s.indexOf(";")) != -1) {
            s = s.substring(0, i);
         }

         if (!s.startsWith("#")) {
            return null;
         } else {
            Integer integer = LogicProgram.m1010(s.substring(1));
            if (integer == null) {
               return null;
            } else {
               int j = integer;
               return j >= 0 && j < lpderivation.premises.length ? integer : null;
            }
         }
      }
   }

   static C_j_D m614(String s) {
      int i = s.indexOf(",");
      if (i == -1) {
         return null;
      } else {
         s = s.substring(i + 1);
         if ((i = s.indexOf(";")) != -1) {
            s = s.substring(0, i);
         }

         return C_j_D.m1897(s);
      }
   }

   static C__B m615(String s) {
      int i = s.indexOf(";");
      return i == -1 ? null : C__B.m1581(s.substring(i + 1));
   }

   static C_PF m616(String s) {
      return !s.startsWith("#") ? null : C_PF.m1183(s.substring(1));
   }

   static C_RF m617(C_RF c_rf, C_RF c_rf1, C_e_ c_e_) {
      c_rf1 = c_rf1.m1237();
      if (c_e_.f1062 == 0) {
         return c_rf1;
      } else {
         c_rf = c_rf.m1237();
         c_rf.m1222(c_e_.f1063, 0, c_e_.f1062 - 1, null).f740.setElementAt(c_rf1, c_e_.f1063[c_e_.f1062 - 1]);
         return c_rf;
      }
   }

   C_RF m618(C_a_ c_a_) {
      return this.m619(c_a_, false);
   }

   C_RF m619(C_a_ c_a_, boolean flag) {
      boolean flag1 = this.f351 == null && this.f352 == null && this.f353 == null;
      C_RF c_rf1;
      if (this.f346 != null) {
         C_MB c_mb = new C_MB();
         C_RF c_rf;
         if (flag1) {
            c_rf = C_r_D.m2073(this.f346.f391, this.f344);
         } else {
            c_rf = C_r_D.m2074(this.f346.f391, this.f354, this.f344);
         }

         if (c_rf == null) {
            return null;
         }

         c_rf1 = c_rf.m1239(this.f346.f393, c_mb);
         if (!this.f346.f394.m1574(c_rf, c_rf1, c_mb, flag ? null : c_a_)) {
            return null;
         }
      } else {
         if (this.f347 != null) {
            c_rf1 = this.f347.f332;
         } else if (this.f348 != null) {
            c_rf1 = c_a_.f935.f317.f915.premises[this.f348];
         } else {
            c_rf1 = null;
         }

         C_RF c_rf2;
         if (flag1) {
            c_rf2 = C_r_D.m2064(c_rf1, false, this.f344 ? 1 : 0);
         } else {
            c_rf2 = C_r_D.m2066(c_rf1, this.f354 ? 0 : 1, this.f344 ? 1 : 0);
         }

         if (c_rf2 == null) {
            return null;
         }

         C_MB c_mb1 = new C_MB();
         c_rf1 = c_rf2.m1239(this.f349, c_mb1);
         if (this.f350 != null) {
            this.f350.m1574(c_rf2, c_rf1, c_mb1, flag ? null : c_a_);
         }
      }

      return m617(c_a_.m1628(-1), c_rf1, this.f343).m1257();
   }

   C_RF m620(C_a_ c_a_) {
      return this.m621(c_a_, false);
   }

   C_RF m621(C_a_ c_a_, boolean flag) {
      boolean flag1 = this.f351 == null && this.f352 == null && this.f353 == null;
      C_RF c_rf1;
      if (this.f346 != null) {
         C_MB c_mb = new C_MB();
         C_RF c_rf;
         if (flag1) {
            c_rf = C_r_D.m2075(this.f346.f391, this.f344);
         } else {
            c_rf = C_r_D.m2076(this.f346.f391, this.f354, this.f344);
         }

         if (c_rf == null) {
            return null;
         }

         c_rf1 = c_rf.m1239(this.f346.f393, c_mb);
         if (!this.f346.f394.m1574(c_rf, c_rf1, c_mb, flag ? null : c_a_)) {
            return null;
         }
      } else {
         if (this.f347 != null) {
            c_rf1 = this.f347.f332;
         } else if (this.f348 != null) {
            c_rf1 = c_a_.f935.f317.f915.premises[this.f348];
         } else {
            c_rf1 = null;
         }

         C_RF c_rf2;
         if (flag1) {
            c_rf2 = C_r_D.m2064(c_rf1, false, this.f344 ? 0 : 1);
         } else {
            c_rf2 = C_r_D.m2066(c_rf1, this.f354 ? 0 : 1, this.f344 ? 0 : 1);
         }

         if (c_rf2 == null) {
            return null;
         }

         C_MB c_mb1 = new C_MB();
         c_rf1 = c_rf2.m1239(this.f349, c_mb1);
         if (this.f350 != null && !this.f350.m1574(c_rf2, c_rf1, c_mb1, flag ? null : c_a_)) {
            return null;
         }
      }

      return m617(c_a_.m1628(-1), c_rf1, this.f343).m1257();
   }

   C_LF m622(C_a_ c_a_) {
      if (this.f351 != null) {
         return new C_l_F(this.f351);
      } else if (this.f352 != null) {
         return new C_OF(c_a_.f935.f317.f915, this.f352 + 1);
      } else {
         return this.f353 != null ? this.f353 : null;
      }
   }

   static C_RF m623(C_j_D c_j_d, C_RF c_rf, C_RF c_rf1) {
      c_j_d = (C_j_D)c_j_d.clone();
      c_rf.m1266(null, c_j_d);
      Vector vector = c_j_d.m1893(c_rf1).f1189;
      c_rf = c_rf.m1238(c_j_d);
      int i = m630(c_rf);
      int j = m630(c_rf1);
      if (j < i) {
         return null;
      } else if (j == i) {
         return c_rf1;
      } else {
         Vector vector1 = new Vector();

         do {
            vector1.addElement(c_rf1.m1217(0).m1237());
            c_rf1 = c_rf1.m1217(1);
         } while (--j > i);

         C_j_D c_j_d1 = new C_j_D();
         int k = vector1.size();

         for (int l = 0; l < k; l++) {
            C_RF c_rf2 = (C_RF)vector1.elementAt(l);
            Vector vector2 = c_rf1.m1223(c_rf2);
            int i1 = vector2.size();

            for (int j1 = 0; j1 < i1; j1++) {
               C_RF c_rf3 = c_rf.m1220((C_e_)vector2.elementAt(j1));
               if (c_rf3 != null && !m629(c_rf3, vector) && !c_j_d1.m1881(c_rf2, c_rf3)) {
                  return null;
               }
            }
         }

         return c_rf1.m1238(c_j_d1);
      }
   }

   C_LF m624(C_a_ c_a_) {
      if (this.f346 != null) {
         return this.f346.m688();
      } else if (this.f347 != null) {
         return new C_l_F(this.f347);
      } else {
         return this.f348 != null ? new C_OF(c_a_.f935.f317.f915, this.f348 + 1) : null;
      }
   }

   C_HF m625() {
      if (this.f346 == null) {
         return null;
      } else {
         C_LF c_lf = new C_LF(this.f346.f391.f820);
         boolean flag = this.f346.f391.f526.length == 0;
         C_RF c_rf;
         if (this.f351 == null && this.f352 == null && this.f353 == null) {
            if (!flag) {
               return this.f346;
            }

            c_rf = C_r_D.m2061(this.f346.f391.f527);
         } else if (flag) {
            c_rf = C_r_D.m2065(this.f346.f391.f527);
            if (c_rf != null) {
               c_rf = c_rf.m1217(this.f354 ? 0 : 1);
            }
         } else {
            c_rf = this.f346.f391.f527;
         }

         if (c_rf == null) {
            return null;
         } else {
            c_lf.f526 = new C_RF[]{c_rf.m1217(this.f344 ? 1 : 0)};
            c_lf.f527 = c_rf.m1217(this.f344 ? 0 : 1);
            return new C_HF(c_lf, new int[]{0}, this.f346.f393, this.f346.f394);
         }
      }
   }

   static Vector m626(C_a_ c_a_, C_e_ c_e_, C_VB c_vb, C_LF c_lf) {
      C_RF c_rf = c_a_.m1628(-1).m1220(c_e_).m1237();
      C_RF c_rf1 = c_a_.f944 && !c_a_.f946 ? c_a_.f941 : null;
      if (c_rf1 != null) {
         c_rf1 = c_rf1.m1220(c_e_).m1237();
      }

      C_r_D c_r_d = LogicProgram.f534.f1472;
      String s = c_lf == null ? "notConditional" : "notConditionalBC";
      C_LF[] ac_lf = c_vb.m1373(c_r_d, s);
      LPDerivation lpderivation = c_a_.f935.f317.f915;
      String s1 = c_a_.f946 ? "manualOrDisabled" : "disabled";
      C_LF[] ac_lf1 = c_vb.m1373(lpderivation, s1);
      Vector vector = new Vector();
      Vector vector1 = new Vector();

      for (C_LF c_lf1 : ac_lf) {
         Vector vector2 = vector;
         if (LogicProgram.m1051(ac_lf1, c_lf1) == -1) {
            vector2 = vector1;
         } else if (!m631(c_a_, c_lf1, c_lf == null)) {
            vector2 = vector1;
         }

         C_RF c_rf2;
         C_RF c_rf3;
         boolean flag;
         int[] aint;
         C_RF c_rf4;
         if (c_lf1.f526.length == 0) {
            if (c_lf == null) {
               c_rf4 = C_r_D.m2061(c_lf1.f527);
            } else {
               c_rf4 = C_r_D.m2065(c_lf1.f527);
            }

            c_rf2 = c_rf4.m1217(0);
            c_rf3 = c_rf4.m1217(1);
            flag = c_rf4.f739.equals("<->");
            aint = new int[0];
         } else {
            c_rf4 = null;
            c_rf2 = c_lf1.f526[0];
            c_rf3 = c_lf1.f527;
            flag = false;
            aint = new int[]{0};
         }

         if (c_lf == null) {
            if (c_lf1 instanceof C_l_F) {
               if (c_rf4 != null && flag) {
                  C_G c_g = ((C_l_F)c_lf1).f1263;
                  C_j_D c_j_d = m605(c_lf1.f527);
                  C__B c__b = m627(c_j_d, c_rf2, c_rf3, c_rf, c_rf1);
                  if (c__b != null) {
                     vector2.addElement(new C_GA(c_e_, false, c_g, c_j_d, c__b));
                  }

                  c_j_d = m605(c_lf1.f527);
                  c__b = m627(c_j_d, c_rf3, c_rf2, c_rf, c_rf1);
                  if (c__b != null) {
                     vector2.addElement(new C_GA(c_e_, true, c_g, c_j_d, c__b));
                  }
               }
            } else if (c_lf1 instanceof C_OF) {
               if (c_rf4 != null && flag) {
                  Integer integer = ((C_OF)c_lf1).f667;
                  C_j_D c_j_d1 = m605(c_lf1.f527);
                  C__B c__b1 = m627(c_j_d1, c_rf2, c_rf3, c_rf, c_rf1);
                  if (c__b1 != null) {
                     vector2.addElement(new C_GA(c_e_, false, integer, c_j_d1, c__b1));
                  }

                  c_j_d1 = m605(c_lf1.f527);
                  c__b1 = m627(c_j_d1, c_rf3, c_rf2, c_rf, c_rf1);
                  if (c__b1 != null) {
                     vector2.addElement(new C_GA(c_e_, true, integer, c_j_d1, c__b1));
                  }
               }
            } else {
               C_j_D c_j_d2 = new C_j_D();
               C__B c__b2 = m627(c_j_d2, c_rf2, c_rf3, c_rf, c_rf1);
               if (c__b2 != null) {
                  C_HF c_hf = new C_HF(c_lf1, aint, c_j_d2, c__b2);
                  vector2.addElement(new C_GA(c_e_, false, c_hf));
               }

               if (flag) {
                  c_j_d2 = new C_j_D();
                  c__b2 = m627(c_j_d2, c_rf3, c_rf2, c_rf, c_rf1);
                  if (c__b2 != null) {
                     C_HF c_hf1 = new C_HF(c_lf1, aint, c_j_d2, c__b2);
                     vector2.addElement(new C_GA(c_e_, true, c_hf1));
                  }
               }
            }
         } else if (c_lf1 instanceof C_l_F) {
            if (c_rf4 != null) {
               C_G c_g1 = ((C_l_F)c_lf1).f1263;
               if (c_rf3.f739.equals("<->")) {
                  C_j_D c_j_d3 = m605(c_lf1.f527);
                  C__B c__b3 = m628(c_j_d3, c_rf3.m1217(0), c_rf3.m1217(1), c_rf2, c_rf, c_rf1, c_lf.f527);
                  if (c__b3 != null) {
                     vector2.addElement(new C_GA(c_e_, false, c_g1, c_j_d3, c__b3, false, c_lf));
                  }

                  c_j_d3 = m605(c_lf1.f527);
                  c__b3 = m628(c_j_d3, c_rf3.m1217(1), c_rf3.m1217(0), c_rf2, c_rf, c_rf1, c_lf.f527);
                  if (c__b3 != null) {
                     vector2.addElement(new C_GA(c_e_, true, c_g1, c_j_d3, c__b3, false, c_lf));
                  }
               }

               if (flag && c_rf2.f739.equals("<->")) {
                  C_j_D c_j_d4 = m605(c_lf1.f527);
                  C__B c__b4 = m628(c_j_d4, c_rf2.m1217(0), c_rf2.m1217(1), c_rf3, c_rf, c_rf1, c_lf.f527);
                  if (c__b4 != null) {
                     vector2.addElement(new C_GA(c_e_, false, c_g1, c_j_d4, c__b4, true, c_lf));
                  }

                  c_j_d4 = m605(c_lf1.f527);
                  c__b4 = m628(c_j_d4, c_rf2.m1217(1), c_rf2.m1217(0), c_rf3, c_rf, c_rf1, c_lf.f527);
                  if (c__b4 != null) {
                     vector2.addElement(new C_GA(c_e_, true, c_g1, c_j_d4, c__b4, true, c_lf));
                  }
               }
            }
         } else if (c_lf1 instanceof C_OF) {
            if (c_rf4 != null) {
               Integer integer1 = ((C_OF)c_lf1).f667;
               if (c_rf3.f739.equals("<->")) {
                  C_j_D c_j_d5 = m605(c_lf1.f527);
                  C__B c__b5 = m628(c_j_d5, c_rf3.m1217(0), c_rf3.m1217(1), c_rf2, c_rf, c_rf1, c_lf.f527);
                  if (c__b5 != null) {
                     vector2.addElement(new C_GA(c_e_, false, integer1, c_j_d5, c__b5, false, c_lf));
                  }

                  c_j_d5 = m605(c_lf1.f527);
                  c__b5 = m628(c_j_d5, c_rf3.m1217(1), c_rf3.m1217(0), c_rf2, c_rf, c_rf1, c_lf.f527);
                  if (c__b5 != null) {
                     vector2.addElement(new C_GA(c_e_, true, integer1, c_j_d5, c__b5, false, c_lf));
                  }
               }

               if (flag && c_rf2.f739.equals("<->")) {
                  C_j_D c_j_d6 = m605(c_lf1.f527);
                  C__B c__b6 = m628(c_j_d6, c_rf2.m1217(0), c_rf2.m1217(1), c_rf3, c_rf, c_rf1, c_lf.f527);
                  if (c__b6 != null) {
                     vector2.addElement(new C_GA(c_e_, false, integer1, c_j_d6, c__b6, true, c_lf));
                  }

                  c_j_d6 = m605(c_lf1.f527);
                  c__b6 = m628(c_j_d6, c_rf2.m1217(1), c_rf2.m1217(0), c_rf3, c_rf, c_rf1, c_lf.f527);
                  if (c__b6 != null) {
                     vector2.addElement(new C_GA(c_e_, true, integer1, c_j_d6, c__b6, true, c_lf));
                  }
               }
            }
         } else {
            if (c_rf3.f739.equals("<->")) {
               C_j_D c_j_d7 = new C_j_D();
               C__B c__b7 = m628(c_j_d7, c_rf3.m1217(0), c_rf3.m1217(1), c_rf2, c_rf, c_rf1, c_lf.f527);
               if (c__b7 != null) {
                  C_HF c_hf2 = new C_HF(c_lf1, aint, c_j_d7, c__b7);
                  vector2.addElement(new C_GA(c_e_, false, c_hf2, false, c_lf));
               }

               c_j_d7 = new C_j_D();
               c__b7 = m628(c_j_d7, c_rf3.m1217(1), c_rf3.m1217(0), c_rf2, c_rf, c_rf1, c_lf.f527);
               if (c__b7 != null) {
                  C_HF c_hf3 = new C_HF(c_lf1, aint, c_j_d7, c__b7);
                  vector2.addElement(new C_GA(c_e_, true, c_hf3, false, c_lf));
               }
            }

            if (flag && c_rf2.f739.equals("<->")) {
               C_j_D c_j_d8 = new C_j_D();
               C__B c__b8 = m628(c_j_d8, c_rf2.m1217(0), c_rf2.m1217(1), c_rf3, c_rf, c_rf1, c_lf.f527);
               if (c__b8 != null) {
                  C_HF c_hf4 = new C_HF(c_lf1, aint, c_j_d8, c__b8);
                  vector2.addElement(new C_GA(c_e_, false, c_hf4, true, c_lf));
               }

               c_j_d8 = new C_j_D();
               c__b8 = m628(c_j_d8, c_rf2.m1217(1), c_rf2.m1217(0), c_rf3, c_rf, c_rf1, c_lf.f527);
               if (c__b8 != null) {
                  C_HF c_hf5 = new C_HF(c_lf1, aint, c_j_d8, c__b8);
                  vector2.addElement(new C_GA(c_e_, true, c_hf5, true, c_lf));
               }
            }
         }
      }

      return vector1.isEmpty() && vector.isEmpty() ? null : vector;
   }

   static C__B m627(C_j_D c_j_d, C_RF c_rf, C_RF c_rf1, C_RF c_rf2, C_RF c_rf3) {
      return m628(c_j_d, c_rf, c_rf1, null, c_rf2, c_rf3, null);
   }

   static C__B m628(C_j_D c_j_d, C_RF c_rf, C_RF c_rf1, C_RF c_rf2, C_RF c_rf3, C_RF c_rf4, C_RF c_rf5) {
      C_MB c_mb = new C_MB();
      if (!c_rf.m1267(c_rf3, c_j_d, c_mb)) {
         return null;
      } else if (!c_rf1.m1267(c_rf4, c_j_d, c_mb)) {
         return null;
      } else {
         if (c_rf5 != null) {
            c_rf5 = m623(c_j_d, c_rf2, c_rf5);
            if (c_rf5 == null) {
               return null;
            }
         }

         if (c_rf2 != null && !c_rf2.m1267(c_rf5, c_j_d, c_mb)) {
            return null;
         } else {
            C__B c__b = new C__B();
            if (!c__b.m1572(c_rf, c_rf3, c_mb)) {
               return null;
            } else if (!c__b.m1572(c_rf1, c_rf4, c_mb)) {
               return null;
            } else if (c_rf2 != null && !c__b.m1572(c_rf2, c_rf5, c_mb)) {
               return null;
            } else {
               c_mb = new C_MB();
               C_RF c_rf6 = c_rf.m1239(c_j_d, c_mb);
               C_RF c_rf7 = c_rf1.m1239(c_j_d, c_mb);
               C_RF c_rf8 = c_rf2 == null ? null : c_rf2.m1239(c_j_d, c_mb);
               if (!c__b.m1574(c_rf, c_rf6, c_mb, null)) {
                  return null;
               } else if (!c__b.m1574(c_rf1, c_rf7, c_mb, null)) {
                  return null;
               } else {
                  return c_rf2 != null && !c__b.m1574(c_rf2, c_rf8, c_mb, null) ? null : c__b;
               }
            }
         }
      }
   }

   static boolean m629(C_RF c_rf, Vector vector) {
      int i = vector == null ? 0 : vector.size();

      for (int j = 0; j < i; j++) {
         if (!c_rf.m1225((C_i_A)vector.elementAt(j)).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   static int m630(C_RF c_rf) {
      C_RF c_rf1 = c_rf;

      int i;
      for (i = 0; c_rf1.f739.equals("@"); c_rf1 = c_rf1.m1217(1)) {
         i++;
      }

      return i;
   }

   static boolean m631(C_a_ c_a_, C_LF c_lf, boolean flag) {
      if (c_lf instanceof C_OF) {
         return true;
      } else if (c_lf instanceof C_l_F) {
         return c_a_.f935.m565(((C_l_F)c_lf).f1263);
      } else {
         LPDerivation lpderivation = c_a_.f935.f317.f915;
         String s = c_a_.f946 ? "manualOrDisabled" : "disabled";
         if (lpderivation.hasProperty(c_lf, s)) {
            return false;
         } else if (!c_lf.m952(lpderivation)) {
            lpderivation.proofMissing = true;
            return false;
         } else if (!flag) {
            return true;
         } else {
            C_r_D c_r_d = LogicProgram.f534.f1472;
            Vector vector = c_r_d.m2067(c_lf);
            int i = vector == null ? 0 : vector.size();

            for (int j = 0; j < i; j++) {
               C_VB c_vb = LPDerivation.getRule((String)vector.elementAt(j));
               if (c_vb instanceof C_LF && !lpderivation.hasProperty(c_vb, s)) {
                  if (c_vb.m952(lpderivation)) {
                     return true;
                  }

                  lpderivation.proofMissing = true;
               }
            }

            return false;
         }
      }
   }
}
