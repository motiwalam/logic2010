package edu.ucla.phil.logic;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class C_VC {
   String f824;
   String[] f825 = new String[0];
   String f826 = null;
   C_RF[] f827 = new C_RF[0];
   C_RF f828 = null;
   boolean f829;
   C_HF[] f830 = null;
   C_HF[] f831 = null;
   static final String[] f832 = new String[]{"no error", "no conclusion", "no premises or conclusion"};

   C_VC(String s) {
      this(s, false);
   }

   C_VC(String s, boolean flag) {
      if ((this.f824 = s) != null) {
         if (this.f829 = s.indexOf(".") == -1) {
            s = ".:" + s;
         }

         int i = s.indexOf(".:");
         if (i != -1) {
            this.f826 = s.substring(i + 2).trim();

            try {
               this.f828 = LogicProgram.m1009(this.f826, false, false, flag);
            } catch (C_k_B c_k_b1) {
            }

            s = s.substring(0, i);
         }

         Vector vector = new Vector();
         Vector vector1 = new Vector();

         while (s.length() > 0) {
            int j = s.indexOf(".");
            String s1 = (j == -1 ? s : s.substring(0, j)).trim();
            s = j == -1 ? "" : s.substring(j + 1);

            try {
               C_RF c_rf = LogicProgram.m1006(s1);
               if (c_rf != null) {
                  vector.addElement(s1);
                  vector1.addElement(c_rf);
               }
            } catch (C_k_B c_k_b) {
               vector.addElement(s1);
               vector1.addElement(null);
            }
         }

         this.f825 = new String[vector.size()];
         vector.copyInto(this.f825);
         this.f827 = new C_RF[vector1.size()];
         vector1.copyInto(this.f827);
      }
   }

   static C_VC m1382(String s) {
      return s == null ? null : new C_VC(s);
   }

   static String m1383(String s) {
      String s1 = s;
      if (s == null) {
         return null;
      } else {
         do {
            Pattern pattern = Pattern.compile("\\.( *\\.)");
            s = s1;
            Matcher matcher = pattern.matcher(s1);
            s1 = matcher.replaceAll("$1");
         } while (!s1.equals(s));

         Matcher matcher1 = Pattern.compile("^ *\\.(.*)\\. *$").matcher(s1);
         return matcher1.replaceAll("$1");
      }
   }

   String m1384() {
      int i = this.f827.length;

      for (int j = 0; j < i; j++) {
         if (this.f827[j] == null) {
            return this.f825[j];
         }
      }

      return this.f828 == null && this.f826 != null && !this.f826.equals("") ? this.f826 : null;
   }

   int m1385() {
      return this.m1386(true);
   }

   int m1386(boolean flag) {
      if (this.f824 == null || this.f826 != null && !this.f826.equals("")) {
         return !flag && this.f829 ? 1 : 0;
      } else {
         return this.f827.length == 0 ? 2 : 1;
      }
   }

   static String m1387(C_VC c_vc, boolean flag, boolean flag1) {
      if (c_vc == null) {
         return f832[2];
      } else {
         String s = c_vc.m1384();
         if (s != null) {
            return "could not parse \"" + s + "\"";
         } else {
            int i = c_vc.m1386(flag1);
            return i == 0 && flag ? null : f832[i];
         }
      }
   }

   String m1388(boolean flag, boolean flag1) {
      return m1387(this, flag, flag1);
   }

   int m1389(C_VB c_vb) {
      if (c_vb != null && this.m1388(true, true) == null) {
         int i = this.f827 == null ? 0 : this.f827.length;
         C_LF[] ac_lf = c_vb.m1374();
         int j = ac_lf.length;
         Vector vector = new Vector();
         Vector vector1 = new Vector();

         for (int k = 0; k < j; k++) {
            C_LF c_lf = ac_lf[k];
            if ((c_lf.f526 == null ? 0 : c_lf.f526.length) == i) {
               C_j_D c_j_d = new C_j_D();
               boolean flag = c_lf.f527.m1266(this.f828, c_j_d);
               C_XE c_xe = new C_XE(i);

               while (true) {
                  label149: {
                     int[] aint = c_xe.m1512();
                     C_j_D c_j_d1 = new C_j_D();
                     C__B c__b = new C__B();
                     boolean flag1 = false;
                     if (i > 0) {
                        C_j_D[] ac_j_d = new C_j_D[i];

                        for (int l = 0; l < i; l++) {
                           ac_j_d[l] = new C_j_D();
                           if (!c_lf.f526[aint[l]].m1266(this.f827[l], ac_j_d[l])) {
                              break label149;
                           }
                        }

                        for (int k1 = 0; k1 < i; k1++) {
                           if (!c_j_d1.m1877(ac_j_d[k1])) {
                              break label149;
                           }
                        }

                        if (c_j_d1.m1890()) {
                           for (int l1 = 0; l1 < i; l1++) {
                              if (!c__b.m1576(c_lf.f526[aint[l1]], this.f827[l1], c_j_d1)) {
                                 break label149;
                              }
                           }
                        } else {
                           flag1 = true;
                        }
                     }

                     if (!flag1) {
                        vector1.addElement(new C_HF(c_lf, aint, (C_j_D)c_j_d1.clone(), (C__B)c__b.clone()));
                     }

                     label99:
                     if (flag && c_j_d1.m1877(c_j_d) && c_j_d1.m1890()) {
                        if (flag1) {
                           for (int j1 = 0; j1 < i; j1++) {
                              if (!c__b.m1576(c_lf.f526[aint[j1]], this.f827[j1], c_j_d1)) {
                                 break label99;
                              }
                           }

                           vector1.addElement(new C_HF(c_lf, aint, (C_j_D)c_j_d1.clone(), (C__B)c__b.clone()));
                        }

                        label92:
                        if (c__b.m1576(c_lf.f527, this.f828, c_j_d1)) {
                           if (c_lf.f820.equalsIgnoreCase("EI")) {
                              C_RF c_rf = c_lf.m950().m1217(0).m1238(c_j_d1);
                              if (!(c_rf instanceof C_i_)) {
                                 break label92;
                              }
                           }

                           vector.addElement(new C_HF(c_lf, aint, c_j_d1, c__b));
                        }
                     }
                  }

                  if (!c_xe.m1513()) {
                     break;
                  }
               }
            }
         }

         int i1;
         if ((i1 = vector1.size()) == 0) {
            this.f830 = null;
         } else {
            this.f830 = new C_HF[i1];
            vector1.copyInto(this.f830);
         }

         if ((i1 = vector.size()) == 0) {
            this.f831 = null;
         } else {
            this.f831 = new C_HF[i1];
            vector.copyInto(this.f831);
         }

         return this.f830 == null ? 0 : (this.f831 == null ? 1 : 2);
      } else {
         this.f830 = null;
         this.f831 = null;
         return 0;
      }
   }

   C_RF m1390() {
      if (this.m1384() == null && this.m1385() == 0) {
         if (this.f827.length == 0) {
            return this.f828.m1237();
         } else {
            Object object = this.f827[0].m1237();
            if (!(object instanceof C_y_A)) {
               return null;
            } else if (!(this.f828 instanceof C_y_A)) {
               return null;
            } else {
               int i = this.f827.length;

               for (int j = 1; j < i; j++) {
                  if (!(this.f827[j] instanceof C_y_A)) {
                     return null;
                  }

                  C_q_F c_q_f = new C_q_F("&");
                  c_q_f.m2040((C_y_A)object);
                  c_q_f.m2041((C_y_A)this.f827[j].m1237());
                  object = c_q_f;
               }

               C_q_F c_q_f1 = new C_q_F("->");
               c_q_f1.m2040((C_y_A)object);
               c_q_f1.m2041((C_y_A)this.f828.m1237());
               return c_q_f1;
            }
         }
      } else {
         return null;
      }
   }

   @Override
   public String toString() {
      return this.m1391(".", ".:");
   }

   String m1391(String s, String s1) {
      if (this.f829) {
         return this.f826;
      } else {
         String s2 = "";
         int i = this.f827.length;

         for (int j = 0; j < i; j++) {
            s2 = s2 + (j == 0 ? "" : s) + this.f825[j];
         }

         if (this.f826 != null) {
            s2 = s2 + s1 + this.f826;
         }

         return s2;
      }
   }
}
