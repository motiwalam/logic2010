package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.File;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class C_u_C implements C_n_A {
   static Vector f1396 = new Vector();
   static Vector f1397 = new Vector();

   static boolean m2101(String s, String s1) {
      return m2102(s, s1) == null;
   }

   static C_c_B m2102(String s, String s1) {
      return m2103(s, s1, null);
   }

   static C_c_B m2103(String s, String s1, String s2) {
      if (s1 != null && !LogicProgram.f576) {
         if (f1397.contains(s1.toLowerCase())) {
            return null;
         } else {
            Hashtable hashtable = C_H.m666("relation", s1);
            C_HB c_hb = C_j_C.m1871("not055", hashtable);
            if (c_hb == null) {
               return new C_c_B(null);
            } else if (c_hb.m1171(s1)) {
               if (!LogicProgram.f589) {
                  f1397.addElement(s1.toLowerCase());
               }

               return null;
            } else {
               Toolkit.getDefaultToolkit().beep();
               return new C_c_B("not053", hashtable);
            }
         }
      } else if (s == null) {
         return new C_c_B(null);
      } else {
         C_p_D c_p_d;
         if ((c_p_d = LogicProgram.m1040(s)) == null) {
            return new C_c_B("not012", C_H.m666("user", s));
         } else if (f1396.contains(s.toLowerCase())) {
            return null;
         } else if (c_p_d.f1335 != null && c_p_d.f1335.length() >= 6) {
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new C_m_A());
            C_H c_h;
            if (s2 != null && (c_h = C_H.m411(s2)) != null) {
               jpanel.add(new C_p_A(LogicProgram.m1004(c_h.f372)));
            }

            c_h = C_H.m411("not050");
            jpanel.add(new C_ZE(C_H.m661(c_h.f372, C_H.m666("user", c_p_d.f1334))));
            C_q_ c_q_;
            jpanel.add(c_q_ = new C_q_(20));
            c_q_.setEchoChar('*');
            String[] astring = new String[]{"OK", "Cancel"};
            C_UA c_ua = new C_UA(null, c_h.f370, jpanel, astring);
            c_ua.pack();
            c_ua.m1314(0);
            c_q_.requestFocus();
            c_ua.m1323(null, true);
            if (c_ua.f790 != 0) {
               return new C_c_B(null);
            } else if (new String(c_q_.getPassword()).equals(c_p_d.f1335)) {
               if (!LogicProgram.f589) {
                  f1396.addElement(c_p_d.f1334.toLowerCase());
               }

               return null;
            } else {
               Toolkit.getDefaultToolkit().beep();
               return new C_c_B("not011", C_H.m666("user", s));
            }
         } else {
            return new C_c_B("not012", C_H.m666("user", s));
         }
      }
   }

   static int m2104(C_c_B c_c_b) {
      String s;
      if (c_c_b != null && (s = c_c_b.m716()) != null) {
         C_H c_h = C_H.m411(s);
         C_b_E c_b_e = new C_b_E(c_h.f373);
         C_UA.m1329(c_h, c_c_b.m717(), null, c_b_e);
         return c_b_e.f1027;
      } else {
         return -1;
      }
   }

   static boolean m2105(String s) {
      if (LogicProgram.f576) {
         return false;
      } else {
         C_H c_h = C_H.m411("not069");
         Hashtable hashtable = C_H.m666("module", s);
         C_b_E c_b_e = new C_b_E(c_h.f373);
         C_UA.m1329(c_h, hashtable, null, c_b_e);
         return c_b_e.f1027 == 0;
      }
   }

   static Boolean m2106(C_OE c_oe, boolean flag) {
      C_H c_h = C_H.m411(flag ? "not035" : "not022");
      Hashtable hashtable = c_oe.m1165();
      C_b_E c_b_e = new C_b_E(c_h.f373);
      C_UA.m1329(c_h, hashtable, null, c_b_e);
      if (c_b_e.f1027 < 0 || c_b_e.f1027 > (flag ? 1 : 2)) {
         return Boolean.FALSE;
      } else if (c_b_e.f1027 == (flag ? 0 : 1)) {
         C_KC.m902();
         return null;
      } else if ((flag || c_b_e.f1027 != 2) && c_oe.m1163() != null) {
         return Boolean.TRUE;
      } else {
         String s = !flag && c_b_e.f1027 == 2 ? "not044" : "not045";
         return C_j_C.m1863(c_oe, s, true, false) == null ? Boolean.FALSE : Boolean.TRUE;
      }
   }

   static Boolean m2107(C_OE c_oe, boolean flag, boolean flag1) {
      C_H c_h;
      if (flag1) {
         c_h = C_H.m411("not094");
      } else if (flag) {
         c_h = C_H.m411("not035");
      } else {
         c_h = C_H.m411("not022");
      }

      Hashtable hashtable = c_oe.m1165();
      C_b_E c_b_e = new C_b_E(c_h.f373);
      String s = c_h.f372;
      if (hashtable != null) {
         s = C_H.m661(s, hashtable);
      }

      C_LB c_lb = new C_LB();
      c_lb.m934(LogicProgram.f541.width * 3 / 4);
      c_lb.m937(true);
      c_lb.setLayout(new BorderLayout());
      c_lb.setBackground(C_n_A.bruinAsh);
      C_p_A c_p_a = new C_p_A(LogicProgram.m1004(s));
      c_p_a.m1787(true);
      c_p_a.m1789(true);
      c_p_a.setEnabled(false);
      c_p_a.setDisabledTextColor(C_n_A.bruinBlack);
      c_p_a.setBackground(C_n_A.bruinAsh);
      c_lb.add(c_p_a, "North");
      C_LB c_lb1 = new C_LB();
      c_lb1.setLayout(new FlowLayout());
      c_lb1.setBackground(C_n_A.bruinAsh);
      boolean flag3 = LogicProgram.f603;
      c_lb1.add(new C_d_D("Monochrome: "));
      C_NE c_ne;
      c_lb1.add(c_ne = new C_NE(""));
      c_ne.setSelected(flag3);
      c_lb1.add(new C_CC(LogicProgram.f539, 2, true, Color.black));
      c_lb1.add(new C_d_D("Set Font Size: "));
      String s2 = new Integer(LogicProgram.f539).toString();
      C_p_A c_p_a1 = new C_p_A(s2);
      c_p_a1.setBackground(C_n_A.bruinWhite);
      c_lb1.add(c_p_a1);
      c_lb.add(c_lb1, "West");
      String[] astring = new String[]{"OK"};
      int i = 0;
      if (c_b_e != null) {
         astring = c_b_e.m445();
         i = c_b_e.m446();
      }

      C_UA c_ua = new C_UA(null, c_h.f370, c_lb, astring);
      c_ua.pack();
      c_ua.m1315(c_b_e);
      c_ua.m1314(i);
      if (i != -1) {
         c_ua.f791[i].requestFocus();
      }

      c_ua.m1322(null);
      boolean flag2 = c_ne.isSelected();
      if (flag2 != flag3) {
         LogicProgram.f532.m2151("monochrome", flag2 ? "true" : "false");
         LogicProgram.f532.m2155(LogicProgram.f558);
         LogicProgram.m1059();
      }

      String s1 = c_p_a1.getText().trim();
      if (!s1.equals(s2)) {
         int j = LogicProgram.m1028(s1);
         if (j < 6) {
            s1 = "6";
         } else if (j > C_SD.m1288() * 5 / 3) {
            s1 = "5/3";
         }

         LogicProgram.f531.m2151("font size", s1);
         LogicProgram.f531.m2155(LogicProgram.f557);
         LogicProgram.m1058();
      }

      if (flag1) {
         return c_b_e.f1027 != 0 ? Boolean.FALSE : Boolean.TRUE;
      } else if (c_b_e.f1027 < 0 || c_b_e.f1027 > (flag ? 1 : 2)) {
         return Boolean.FALSE;
      } else if (c_b_e.f1027 == (flag ? 0 : 1)) {
         C_KC.m902();
         return null;
      } else if ((flag || c_b_e.f1027 != 2) && c_oe.m1163() != null) {
         return Boolean.TRUE;
      } else {
         String s3 = !flag && c_b_e.f1027 == 2 ? "not044" : "not045";
         return C_j_C.m1863(c_oe, s3, true, false) == null ? Boolean.FALSE : Boolean.TRUE;
      }
   }

   static File m2108(File file1, File file2) {
      Hashtable hashtable = LogicProgram.m1072(file1, false);
      Hashtable hashtable1 = LogicProgram.m1072(file2, false);
      if (hashtable == null || LogicProgram.m1076(hashtable, "textDir", null) == null) {
         hashtable = null;
      }

      if (hashtable1 == null || LogicProgram.m1076(hashtable1, "textDir", null) == null) {
         hashtable1 = null;
      }

      if (hashtable == null && hashtable1 == null) {
         return null;
      } else {
         C_H c_h = C_H.m411("not087");
         String s = hashtable == null ? "Student.Local" : (hashtable1 == null ? "Student.Instructor" : "Student.Instructor.Local");
         C_b_E c_b_e = new C_b_E(s);
         C_UA.m1329(c_h, null, null, c_b_e);
         if (c_b_e.f1027 >= 0) {
            String s1 = c_b_e.f272[c_b_e.f1027];
            if ("Student".equals(s1)) {
               return null;
            }

            if ("Instructor".equals(s1)) {
               return file1;
            }

            if ("Local".equals(s1)) {
               return file2;
            }

            if ("Remove".equals(s1)) {
               m2109(file1);
               m2109(file2);
            }
         }

         return null;
      }
   }

   static boolean m2109(File file1) {
      boolean flag = true;
      if (file1 != null && file1.exists()) {
         if (file1.isDirectory()) {
            String[] astring = file1.list();
            int i = astring == null ? 0 : astring.length;

            for (int j = 0; j < i; j++) {
               if (!m2109(new File(file1, astring[j]))) {
                  flag = false;
               }
            }
         }

         if (!file1.delete()) {
            flag = false;
         }

         return flag;
      } else {
         return flag;
      }
   }

   static Boolean m2110(C_OE c_oe) {
      C_H c_h = C_H.m411("not074");
      C_b_E c_b_e = new C_b_E(c_h.f373);
      C_UA.m1329(c_h, null, null, c_b_e);
      if (c_b_e.f1027 == 0) {
         return C_KC.m902() == 0 ? null : Boolean.FALSE;
      } else {
         return c_b_e.f1027 == 1 ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   static boolean m2111() {
      C_H c_h = C_H.m411("not078");
      C_b_E c_b_e = new C_b_E(c_h.f373);
      C_UA.m1329(c_h, null, null, c_b_e);
      return c_b_e.f1027 != 0 ? false : C_KC.m903(true) == 0;
   }

   static boolean m2112(C_OE c_oe) {
      Hashtable hashtable = C_H.m667("institution", c_oe.m1156(), "studentID", c_oe.m1153());
      C_H c_h = C_H.m411("not075");
      C_b_E c_b_e = new C_b_E(c_h.f373);
      C_UA.m1329(c_h, hashtable, null, c_b_e);
      return c_b_e.f1027 != 0;
   }

   static Boolean m2113(C_OE c_oe) {
      Hashtable hashtable = C_H.m667("institution", c_oe.m1156(), "studentID", c_oe.m1153());
      C_H c_h = C_H.m411("not076");
      C_b_E c_b_e = new C_b_E(c_h.f373);
      C_UA.m1329(c_h, hashtable, null, c_b_e);
      if (c_b_e.f1027 == 0) {
         return Boolean.TRUE;
      } else {
         return c_b_e.f1027 == 1 ? Boolean.FALSE : null;
      }
   }

   static boolean m2114(String s, String s1, boolean flag) {
      boolean flag1 = !LogicProgram.f576 && (s != null || s1 != null);
      if (!flag && !flag1) {
         return false;
      } else {
         C_H c_h = C_H.m411(flag1 ? (flag ? "not019" : "not065") : "not038");
         if (LogicProgram.f576) {
            c_h = C_H.m411("not088");
         }

         C_b_E c_b_e = new C_b_E(c_h.f373);
         C_UA.m1329(c_h, null, null, c_b_e);
         if (flag && c_b_e.f1027 == 0) {
            C_KC.m903(false);
            return true;
         } else if (flag1 && c_b_e.f1027 == (flag ? 1 : 0)) {
            C_KC.m903(false);
            return C_KC.m899(s, s1, null);
         } else {
            return false;
         }
      }
   }

   static boolean m2115(C_x_A c_x_a) {
      boolean flag = !LogicProgram.f576 && LogicProgram.f581 && LogicProgram.f567 != null;
      boolean flag1 = LogicProgram.f582 && LogicProgram.f551 != null;
      if (!flag && !flag1) {
         return true;
      } else {
         C_H c_h;
         if (flag && !flag1) {
            c_h = C_H.m411("not067");
         } else if (!flag && flag1) {
            c_h = C_H.m411("not068");
         } else {
            c_h = C_H.m411("not031");
         }

         C_b_E c_b_e = new C_b_E(c_h.f373);
         C_UA.m1329(c_h, null, null, c_b_e);
         int i = c_b_e.f1027;
         if (i == -1) {
            return false;
         } else if (flag && flag1 ? i != 3 : i != 1) {
            if (flag && flag1 ? i != 4 : i != 2) {
               boolean flag2 = true;
               boolean flag3 = true;
               if (flag && (i == 0 || flag1 && i == 2)) {
                  flag2 = C_KC.m896(LogicProgram.f567, c_x_a);
               }

               if (flag1 && (flag ? i == 1 || i == 2 : i == 0)) {
                  flag3 = C_KC.m900(LogicProgram.f555, LogicProgram.f551);
               }

               return flag2 && flag3;
            } else {
               return false;
            }
         } else {
            return true;
         }
      }
   }

   static boolean m2116(C_XA c_xa) {
      int i = c_xa.f872 == null ? 0 : c_xa.f872.length;
      Vector vector = new Vector();
      C_YA c_ya = new C_YA(false);

      for (int j = 0; j < i; j++) {
         C_w_D c_w_d = c_xa.f872[j];
         if (c_w_d.m2159() && c_w_d.f1429.equalsIgnoreCase(c_xa.f868)) {
            vector.addElement(c_w_d);
            c_ya.m1526(new C_ZE(c_w_d.f1430));
         }
      }

      if ((i = vector.size()) == 0) {
         C_UA.m1329(C_H.m411("not014"), null, null, null);
         return false;
      } else {
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(c_ya);
         String[] astring = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(null, "Please Choose a Backup", jscrollpane, astring);
         c_ya.m1528(c_ua, 0);
         Dimension dimension = new Dimension(20 * LogicProgram.f539, 15 * LogicProgram.f539);
         c_ua.setSize(dimension);
         c_ua.m1317("bakChosen");
         c_ya.setSelectedIndex(i - 1);
         c_ya.requestFocus();
         c_ua.m1323(C_UA.m1321(dimension), true);
         if (c_ua.f790 != 0) {
            return false;
         } else {
            int k = c_ya.getSelectedIndex();
            if (k == -1) {
               return false;
            } else {
               c_xa.f871 = ((C_w_D)vector.elementAt(k)).f1431;
               return true;
            }
         }
      }
   }

   static boolean m2117(String s) {
      C_H c_h = C_H.m411(s);
      C_b_E c_b_e = new C_b_E(c_h.f373);
      C_UA.m1329(c_h, null, null, c_b_e);
      int i = c_b_e.f1027;
      return i == 0 && C_KC.m903(true) == 0;
   }

   static String m2118(String s) {
      if (s == null) {
         return null;
      } else {
         int i = s.indexOf(" ");
         if (i != -1) {
            s = s.substring(i);
         }

         return s.trim();
      }
   }

   static String m2119(String s, String s1) {
      if (s == null) {
         return null;
      } else {
         if (s.toUpperCase().startsWith(s1.toUpperCase())) {
            s = s.substring(s1.length());
         }

         return s.trim();
      }
   }
}
