package edu.ucla.phil.logic;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JPanel;

class C__B extends Hashtable implements C_n_A {
   Vector f925 = null;
   int f926 = 0;

   @Override
   public Object clone() {
      C__B c__b1 = (C__B)super.clone();
      c__b1.f925 = null;
      c__b1.f926 = 0;
      return c__b1;
   }

   String m1569(String s) {
      return (String)this.get(s);
   }

   boolean m1570(String s, String s1, boolean flag) {
      if (!C_i_.m1852(s1, flag)) {
         return false;
      } else {
         this.put(s, s1);
         return true;
      }
   }

   boolean m1571(String s, String s1) {
      return this.m1570(s, s1, false);
   }

   boolean m1572(C_RF c_rf, C_RF c_rf1, C_MB c_mb) {
      return c_rf1 == null ? true : this.m1573(c_rf, c_rf1, c_mb.m1095(c_rf, c_rf1));
   }

   boolean m1573(C_RF c_rf, C_RF c_rf1, int[][] aint) {
      if (c_rf1 == null) {
         return true;
      } else {
         Vector vector = c_rf.m1243();
         Vector vector1 = c_rf1.m1243();
         int i = vector.size();

         for (int j = 0; j < i; j++) {
            int[] aint1 = aint[j];
            int k = aint1 == null ? 0 : aint1.length;

            for (int l = 0; l < k; l++) {
               String s = m1579(vector1, aint1[l]);
               if (s != null) {
                  String s1 = m1579(vector, j);
                  String s2 = this.m1569(s1);
                  if (s2 == null) {
                     this.m1571(s1, s);
                  } else if (!s2.equals(s)) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   boolean m1574(C_RF c_rf, C_RF c_rf1, C_MB c_mb, C_a_ c_a_) {
      return this.m1575(c_rf, c_rf1, c_mb.m1095(c_rf, c_rf1), c_a_);
   }

   boolean m1575(C_RF c_rf, C_RF c_rf1, int[][] aint, C_a_ c_a_) {
      C_G c_g = c_a_ == null ? null : c_a_.f935;
      Vector vector = c_rf.m1243();
      Vector vector1 = c_rf1.m1243();
      C_L c_l = new C_L();
      c_l.setSize(vector1.size());
      int i = vector.size();
      String[] astring = new String[i];

      for (int j = 0; j < i; j++) {
         astring[j] = m1579(vector, j);
      }

      label120:
      while (true) {
         this.f926 = 0;
         C__B c__b1 = (C__B)this.clone();

         for (int i1 = 0; i1 < i; i1++) {
            String s = c__b1.m1569(astring[i1]);
            if (s == null) {
               s = C_i_A.m1853(this.f926++);
               c__b1.m1570(astring[i1], s, true);
            }

            int[] aint1 = aint[i1];
            int k = aint1 == null ? 0 : aint1.length;

            for (int l = 0; l < k; l++) {
               c_l.setElementAt(s, aint1[l]);
            }
         }

         if (this.f926 != 0 && c_g != null) {
            if (c_g.f317.f915.serialMode && c_a_.f956 == null) {
               c_a_.m1621("dererr064");
               c_g.f317.f915.complete = false;
               return false;
            }

            C_j_D c_j_d = new C_j_D();
            C_PB[] ac_pb = new C_PB[this.f926];

            for (int j1 = 0; j1 < this.f926; j1++) {
               c_j_d.m1886(ac_pb[j1] = new C_PB(C_i_A.m1853(j1)));
            }

            C_f_ c_f_ = new C_f_(c_j_d, 50, c_g.f317.f915.frame, true);
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new C_m_A(0));
            String s1 = this.f926 == 1 ? "" : "s";
            String s2 = this.f926 == 1 ? "a " : "";
            jpanel.add(new C_ZE("Given the expression"));
            C_RF c_rf2 = c_rf1.m1237();
            c_rf2.m1241(c_l);
            String s3 = c_rf2.toString();
            C_CB c_cb = m1577(s3, 0, this.f926);
            jpanel.add(LogicProgram.m991(s3, 0, 14, 250, c_cb));
            jpanel.add(new C_ZE("please choose " + s2 + "symbol" + s1 + " for"));
            jpanel.add(new C_ZE("the following bound variable" + s1));
            jpanel.add(c_f_);
            String[] astring1 = new String[]{"OK", "Cancel"};
            C_UA c_ua = new C_UA(c_g.f317.f915.frame, "Line " + c_g.m30(), jpanel, astring1);
            c_ua.m1314(0);
            c_f_.m1801(c_ua);
            c_g.m22(true);
            if (!c_ua.m1327(c_a_.f956, c_f_.m1803(), 0)) {
               if (c_g.f317.f915.serialMode) {
                  c_a_.m1621("dererr064");
                  c_g.f317.f915.complete = false;
                  c_ua.dispose();
                  return false;
               }

               Rectangle rectangle = LogicProgram.m1035(c_g.f324, null);
               c_ua.pack();
               c_ua.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
            } else {
               c_ua.dispose();
            }

            if (c_ua.f790 != 1 && c_ua.f790 != -1) {
               if ((c_j_d = c_f_.m1802()) == null) {
                  c_g.m12(c_f_.f1099, c_f_.f1100);
                  return false;
               }

               this.f926 = 0;
               int k1 = 0;

               while (true) {
                  if (k1 >= i) {
                     continue label120;
                  }

                  if (aint[k1] != null && aint[k1].length != 0) {
                     String s4 = this.m1569(astring[k1]);
                     if (s4 == null) {
                        C_GF c_gf = c_j_d.m1878(ac_pb[this.f926++]);
                        if (c_gf != null && !this.m1571(astring[k1], c_gf.f368.f739)) {
                           c_g.m553("dererr060", c_a_, C_H.m666("variable name", "\\l" + c_gf.f368.f739 + "\\l"));
                           return false;
                        }
                     }
                  }

                  k1++;
               }
            }

            c_g.m11("dererr028");
            c_g.f317.f915.abort(true);
            return false;
         }

         c_rf1.m1241(c_l);
         this.f925 = c_rf1.m1259();
         if (this.f925 != null) {
            if (c_g != null) {
               c_g.m550("dererr061", c_a_);
            }

            return false;
         }

         return true;
      }
   }

   boolean m1576(C_RF c_rf, C_RF c_rf1, C_j_D c_j_d) {
      C_MB c_mb = new C_MB();
      C_RF c_rf2 = c_rf.m1239(c_j_d, c_mb);
      int[][] aint = c_mb.m1095(c_rf, c_rf2);
      if (!this.m1573(c_rf, c_rf1, aint)) {
         return false;
      } else {
         return !this.m1575(c_rf, c_rf2, aint, null) ? false : c_rf2.m1235(c_rf1);
      }
   }

   static C_CB m1577(String s, int i, int j) {
      Vector vector = new Vector();

      for (int k = 0; k < j; k++) {
         C_n_F c_n_f = new C_n_F();
         String s1 = C_i_A.m1853(i + k);
         int l = -1;

         while ((l = s.indexOf(s1, l + 1)) != -1) {
            c_n_f.m1986(l).m1986(l + s1.length());
         }

         vector.addElement(c_n_f);
      }

      return C_CB.m420(vector);
   }

   boolean m1578(C_RF c_rf) {
      Vector vector = c_rf.m1243();
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         if (this.m1569(m1579(vector, j)) == null) {
            return false;
         }
      }

      return true;
   }

   static String m1579(Vector vector, int i) {
      return ((C_RF)vector.elementAt(i)).m1217(0).m1214();
   }

   String m1580() {
      boolean flag = false;
      String s = "";

      for (Enumeration enumeration = this.keys(); enumeration.hasMoreElements(); flag = true) {
         String s1 = (String)enumeration.nextElement();
         s = s + (flag ? "." : "") + s1 + ":" + this.m1569(s1);
      }

      return s;
   }

   static C__B m1581(String s) {
      C__B c__b = new C__B();

      while (!s.equals("")) {
         int i = s.indexOf(".");
         String s1;
         if (i == -1) {
            s1 = s;
            s = "";
         } else {
            s1 = s.substring(0, i);
            s = s.substring(i + 1);
         }

         int j = s1.indexOf(":");
         if (j != -1) {
            c__b.m1571(s1.substring(0, j).trim(), s1.substring(j + 1).trim());
         }
      }

      return c__b;
   }
}
