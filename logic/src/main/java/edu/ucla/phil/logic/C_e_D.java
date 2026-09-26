package edu.ucla.phil.logic;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.SwingUtilities;

abstract class C_e_D extends Vector implements C_XC {
   Hashtable f1074 = new Hashtable();
   Hashtable f1075 = null;
   String f1076 = null;
   static String[] f1077 = LogicProgram.f596;
   boolean f1078 = false;
   boolean f1079 = false;
   boolean f1080 = false;
   boolean f1081 = false;
   static boolean f1082 = false;

   abstract String m1106(C_XD c_xd);

   abstract boolean m1103(C_XD c_xd);

   abstract String m1104(C_XD c_xd);

   abstract String m1105(C_XD c_xd);

   abstract C_f_F m1102(String s, boolean flag);

   abstract int m1107();

   synchronized int m1766(String s) {
      if (s == null) {
         return -1;
      } else {
         s = LogicProgram.m1000(s).toUpperCase();
         int i = this.size();

         for (int j = 0; j < i; j++) {
            String s1 = C_XD.m1493(((C_f_F)this.elementAt(j)).f1119);
            s1 = LogicProgram.m1000(s1).toUpperCase();
            int k = s.compareTo(s1);
            if (k == 0) {
               return -1;
            }

            if (k < 0) {
               return j;
            }
         }

         return i;
      }
   }

   int m1767(String s) {
      if (s == null) {
         return -1;
      } else {
         s = LogicProgram.m1000(s);
         int i = this.size();

         for (int j = 0; j < i; j++) {
            String s1 = LogicProgram.m1000(C_XD.m1493(((C_f_F)this.elementAt(j)).f1119));
            if (s.equalsIgnoreCase(s1)) {
               return j;
            }
         }

         return -1;
      }
   }

   String m1768(String s) {
      return this.m1106(new C_XD(s));
   }

   boolean m1769(String s) {
      return this.m1103(new C_XD(s));
   }

   static boolean m1770(C_XD c_xd) {
      Hashtable hashtable = c_xd.m1506('%');
      return hashtable != null && hashtable.containsKey("eg");
   }

   synchronized int m1771(C_f_F c_f_f, boolean flag) {
      String s = C_XD.m1493(c_f_f.f1119);
      if (s == null) {
         return -1;
      } else {
         C_f_F c_f_f1 = this.f1074.put(s.trim().toUpperCase(), c_f_f);
         int i = c_f_f1 == null ? -1 : this.indexOf(c_f_f1);
         if (i != -1) {
            int j = 1;

            while (this.m1772(s + "-" + j) != null) {
               j++;
            }

            s = s + "-" + j;
            c_f_f1 = this.m1102(C_XD.m1495(c_f_f1.f1119, s), flag);
            this.f1074.put(s.trim().toUpperCase(), c_f_f1);
            this.setElementAt(c_f_f1, i);
         }

         return i;
      }
   }

   synchronized C_f_F m1772(String s) {
      return s == null ? null : (C_f_F)this.f1074.get(s.trim().toUpperCase());
   }

   synchronized boolean m1773() {
      C_e_D c_e_d1 = this.m1784();
      int i = c_e_d1 == null ? 0 : c_e_d1.size();
      int j = 0;
      this.f1078 = false;

      for (int k = 0; k < i; k++) {
         String s = c_e_d1.m1778(k);
         int l = this.m1774(s, j);
         if (l != -1) {
            j = l + 1;
         }
      }

      return this.f1078;
   }

   synchronized int m1774(String s, int i) {
      C_XD c_xd1 = new C_XD(s);
      String s1 = c_xd1.m1494();
      String s2 = this.m1106(c_xd1);
      if (s1 != null && s2 != null) {
         C_f_F c_f_f = this.m1772(s1);
         int j = c_f_f == null ? -1 : this.indexOf(c_f_f);
         if (j == -1) {
            j = i - 1;
         } else {
            C_XD c_xd;
            if (s2.equals(this.m1106(c_xd = new C_XD(c_f_f.f1119)))) {
               return j;
            }

            if (!this.m1103(c_xd) || m1770(c_xd)) {
               this.m1101(j--);
            }
         }

         this.f1078 = true;
         C_f_F c_f_f1 = this.m1102(s, true);
         this.m1771(c_f_f1, true);
         this.insertElementAt(c_f_f1, j + 1);
         return j + 1;
      } else {
         return -1;
      }
   }

   synchronized int m1098(String s, Vector vector, boolean flag) {
      return this.m1775(new C_XD(s), vector, flag);
   }

   int m1775(C_XD c_xd, Vector vector, boolean flag) {
      String s = c_xd.m1494();
      String s1 = c_xd.m1487();
      if (s == null) {
         System.out.println("problem without title: " + s1);
         return -1;
      } else {
         int i = flag ? this.m1766(s) : this.size();
         if (i == -1) {
            System.out.println("local problem with duplicate name: " + s);
            return -1;
         } else {
            if (this.f1075 != null && vector != null) {
               this.f1075.put(s, vector);
            }

            C_f_F c_f_f = this.m1102(s1, true);
            c_f_f.f1121 = c_xd.m1505();
            this.m1771(c_f_f, true);
            if (flag) {
               this.insertElementAt(c_f_f, i);
            } else {
               this.addElement(c_f_f);
            }

            return i;
         }
      }
   }

   abstract void m1099(C_U.C__A c__a);

   synchronized int m1776(String s, int i) {
      if (C_XD.m1493(s) == null) {
         return -1;
      } else {
         C_f_F c_f_f = (C_f_F)this.elementAt(i);
         if (c_f_f != null) {
            this.f1074.remove(C_XD.m1493(c_f_f.f1119).trim().toUpperCase());
         }

         c_f_f = this.m1102(s, false);
         this.m1771(c_f_f, false);
         this.setElementAt(c_f_f, i);
         return i;
      }
   }

   synchronized void m1101(int i) {
      String s = C_XD.m1493(this.m1778(i));
      if (s != null) {
         this.f1074.remove(s.trim().toUpperCase());
      }

      this.removeElementAt(i);
      Vector vector = this.m1785();
      int j = vector.size();

      for (int k = 0; k < j; k++) {
         C_U c_u = (C_U)vector.elementAt(k);
         if (c_u.problemIndex == i) {
            c_u.problemIndex = -1;
         } else if (c_u.problemIndex > i) {
            c_u.problemIndex--;
         }
      }
   }

   synchronized String m1777(C_OE c_oe) {
      return c_oe.m1148(new C_a_A(this), (String)c_oe.get(this.m1786()));
   }

   synchronized String m1778(int i) {
      C_f_F c_f_f = this.m1779(i);
      return c_f_f == null ? null : c_f_f.f1119;
   }

   synchronized C_f_F m1779(int i) {
      return i >= 0 && i < this.size() ? (C_f_F)this.elementAt(i) : null;
   }

   synchronized String m1780(String s) {
      if (s == null) {
         return null;
      } else {
         C_f_F c_f_f = this.m1772(s);
         return c_f_f == null ? null : c_f_f.f1119;
      }
   }

   C_YA m1781(C_U c_u, C_e_D c_e_d1, boolean flag, boolean flag1, C_BE c_be, C_BE c_be1) {
      C_x_A c_x_a = new C_x_A(c_u, true);
      C_YA c_ya = new C_YA(flag);
      Hashtable hashtable = c_e_d1 == null ? null : c_e_d1.f1075;
      int i = c_u == null ? -1 : c_u.problemIndex;
      Color[] acolor = new Color[]{dialogBlack, dialogRed, dialogGreen, dialogRed};
      Color[] acolor1 = new Color[]{dialogBlack, dialogOrange, dialogOrange, dialogOrange};
      int j = this.size();
      int[] aint = new int[j];

      for (int k = 0; k < j; k++) {
         C_f_F c_f_f = this.m1779(k);
         if ((!c_f_f.f1121 || flag1) && (!c_f_f.f1122 || !f1082)) {
            C_XD c_xd = new C_XD(c_f_f.f1119);
            String s = c_xd.m1494();
            Vector vector;
            if (hashtable != null && (vector = (Vector)hashtable.get(s)) != null) {
               int l = vector.size();

               for (int i1 = 0; i1 < l; i1++) {
                  String s1 = (String)vector.elementAt(i1);
                  if (s1 == null || s1.equals("")) {
                     s1 = " ";
                  }

                  String s2 = LogicProgram.m1004(s1);
                  C_ZE c_ze = new C_ZE(s2, 2);
                  c_ze.setForeground(dialogBlue);
                  c_ze.setEnabled(false);
                  c_ze.setOpaque(true);
                  c_ya.m1526(c_ze);
               }
            }

            String s3 = m1783(c_e_d1, s);
            if (!LogicProgram.m1060(c_be1, s3)) {
               String s4 = this.m1106(c_xd);
               if (s != null && s3 != null) {
                  s = C_u_C.m2118(s);
               }

               s = (s == null ? "" : s + ":  ") + (s4 == null ? "" : s4.trim());
               C_QC c_qc = new C_QC(LogicProgram.m995(s, maggie, f1077), 2);
               c_qc.m1187(LogicProgram.m995(s, maggie, f1077));
               c_qc.setOpaque(true);
               if (!this.f1081) {
                  boolean flag2 = LogicProgram.m1060(c_be, s3);
                  c_qc.setForeground(flag2 ? acolor1[c_f_f.f1120] : acolor[c_f_f.f1120]);
               }

               aint[k] = c_ya.m1527();
               c_ya.m1526(c_qc);
            }
         }
      }

      j = c_ya.m1527();
      c_ya.f899 = new int[j];
      if (j > 0) {
         for (int j1 = 0; j1 < j; j1++) {
            c_ya.f899[j1] = -1;
         }

         j = aint.length;
         int k1 = 0;

         while (k1 < j) {
            c_ya.f899[aint[k1]] = k1++;
         }

         if (i != -1) {
            c_ya.setSelectedIndex(aint[i]);
         } else if (!flag && j > 0) {
            c_ya.setSelectedIndex(aint[0]);
         }
      }

      c_x_a.m2162(false);
      return c_ya;
   }

   static String m1782(String s) {
      return f1082 ? "h " + s : s;
   }

   static String m1783(C_e_D c_e_d, String s) {
      String s1;
      return c_e_d != null && (s1 = c_e_d.m1780(s)) != null ? C_XD.m1493(s1) : null;
   }

   C_e_D m1784() {
      return (C_e_D)C_U.getStaticField(this.m1107(), "exercises");
   }

   Vector m1785() {
      return (Vector)C_U.getStaticField(this.m1107(), "instances");
   }

   String m1786() {
      return (String)C_U.getStaticField(this.m1107(), "digestVersKey");
   }

   class C__A implements Runnable {
      boolean f1083;
      int f1084;
      int f1085;
      C_XD f1086;
      C_U f1087;
      C_U.C__A f1088;
      C_h_C f1089;
      C_d_C f1090;

      C__A(int i, C_XD c_xd, C_U c_u, C_U.C__A c_u$c__a) {
         this.f1084 = i;
         this.f1086 = c_xd;
         this.f1087 = c_u;
         this.f1088 = c_u$c__a;
         this.f1083 = false;
         this.f1089 = null;
         this.f1090 = null;
         this.f1085 = 0;
      }

      C__A(int i, C_XD c_xd, C_d_C c_d_c, C_h_C c_h_c, C_U.C__A c_u$c__a) {
         this.f1084 = i;
         this.f1086 = c_xd;
         this.f1087 = null;
         this.f1088 = c_u$c__a;
         this.f1083 = false;
         this.f1089 = c_h_c;
         this.f1090 = c_d_c;
         this.f1085 = 0;
      }

      @Override
      public void run() {
         if (this.f1085 == this.f1084) {
            if (!this.f1083) {
               this.f1088.m959();
               return;
            }

            this.f1085 = 0;
            this.f1083 = false;
         }

         C_f_F c_f_f = C_e_D.this.m1779(this.f1085);
         if (c_f_f != null && (c_f_f.f1120 == 3 || c_f_f.f1120 == 4)) {
            int i = c_f_f.f1120;
            this.f1086.m1471();
            this.f1086.m1473(c_f_f.f1119);
            if (this.f1089 != null && this.f1090 != null) {
               this.f1090.m1741(this.f1086, this.f1089, (C__C)c_f_f);
            } else {
               c_f_f.f1120 = this.f1087.getProblemState(this.f1086);
            }

            if (c_f_f.f1120 != i) {
               this.f1083 = true;
            }
         }

         this.f1085++;
         SwingUtilities.invokeLater(this);
      }
   }
}
