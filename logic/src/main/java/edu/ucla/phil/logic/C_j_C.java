package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Hashtable;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

class C_j_C implements C_n_A {
   static C_MF m1863(C_OE c_oe, String s, boolean flag, boolean flag1) {
      C_MF c_mf;
      if (flag && C_KC.m859()) {
         c_mf = C_KC.m861();
         if (c_mf == null) {
            C_UA.m1329(C_H.m411("not079"), null, null, null);
            return null;
         }
      } else {
         String s1 = C_H.m661(C_H.m412(s), c_oe.m1165());
         s1 = C_H.m661(s1, C_KC.m850());
         C_n_E c_n_e = new C_n_E(c_oe, flag);
         if (c_n_e.f1308 == null) {
            return null;
         }

         if (c_n_e.f1308.length == 0) {
            C_H c_h = C_H.m411(c_oe instanceof C_HB ? "not042" : "not043");
            C_UA.m1329(c_h, C_H.m666("site", c_oe.m1156()), null, null);
            return null;
         }

         C_LB c_lb = new C_LB();
         c_lb.setLayout(new BorderLayout());
         C_s_B c_s_b = new C_s_B(C_SD.m1292(s1));
         c_s_b.setLineWrap(false);
         c_lb.add(c_s_b, "North");
         c_lb.add(c_n_e, "Center");
         c_lb.add(new C_s_B(C_SD.m1292("\t")), "South");
         String[] astring;
         if (flag1) {
            astring = new String[]{"Continue", "Switch", flag ? "Quit" : "Cancel"};
         } else {
            astring = new String[]{"OK", flag ? "Quit" : "Cancel"};
         }

         C_UA c_ua = new C_UA(null, "Please Choose A Course", c_lb, astring);
         c_n_e.f1312 = c_ua;
         c_ua.m1314(0);
         c_ua.f789 = true;
         c_ua.m1322(C_UA.m1321(c_ua.getPreferredSize()));
         if (flag1 && c_ua.f790 == 0) {
            c_mf = C_KC.m861();
         } else {
            if (c_ua.f790 != (flag1 ? 1 : 0)) {
               return null;
            }

            c_mf = c_n_e.m1966();
         }
      }

      c_oe.put("institution", c_mf.f620 == null ? "" : c_mf.f620);
      c_oe.put("term", c_mf.f621 == null ? "" : c_mf.f621);
      c_oe.put("className", c_mf.f622 == null ? "" : c_mf.f622);
      c_oe.f665 = c_mf.f623;
      c_oe.f662 = true;
      return c_mf;
   }

   static String m1864(C_0C c_0c, C_OE c_oe, C_x_A c_x_a) {
      Boolean obool = C_KC.m906(c_0c, c_oe, c_x_a, (C_r_A)null);
      if (obool == null) {
         return null;
      } else if (!obool) {
         if (c_oe instanceof C_HB) {
            Hashtable hashtable = C_H.m667("sid", (String)c_oe.get("studentID"), "site", (String)c_oe.get("institution"));
            C_UA.m1329(C_H.m411("not028"), hashtable, null, null);
            return null;
         } else {
            return m1866(c_0c, c_oe, c_x_a);
         }
      } else {
         C_LB c_lb = new C_LB();
         C_QF c_qf = new C_QF(2, 1, 0, 0, true, true);
         c_qf.setVgap(1);
         c_lb.setLayout(c_qf);
         c_lb.add(new C_ZE("password: "));
         C_q_ c_q_;
         c_lb.add(c_q_ = new C_q_(20));
         String[] astring = new String[]{"OK", "Cancel", "Change"};
         C_UA c_ua = new C_UA(null, "Please Enter Your Logic Password", c_lb, astring);
         c_ua.f791[0].setEnabled(false);
         new C_UE(c_ua, 0, c_q_);
         c_q_.requestFocus();
         c_ua.m1322(C_UA.m1321(c_ua.getPreferredSize()));
         String s = null;
         if (c_ua.f790 == 0) {
            s = C__F.m1588(c_q_);
         } else if (c_ua.f790 == 2) {
            s = m1867(c_0c, c_oe, c_x_a);
         }

         return s;
      }
   }

   static String m1865(C_0C c_0c, C_OE c_oe, C__F c__f, String s) {
      if (c__f.f933 != null) {
         return m1868(c_0c, c_oe, c__f, s);
      } else {
         C_LB c_lb = new C_LB();
         C_QF c_qf = new C_QF(2, 1, 0, 0, true, true);
         c_qf.setVgap(1);
         c_lb.setLayout(c_qf);
         c_lb.add(new C_ZE("password: "));
         C_q_ c_q_;
         c_lb.add(c_q_ = new C_q_(20));
         C_LB c_lb1 = new C_LB();
         c_lb1.setLayout(new BoxLayout(c_lb1, 3));
         c_lb1.add(new C_s_B(LogicProgram.m1004(s + "\\n" + C_H.m412("not056"))));
         c_lb1.add(c_lb);
         String[] astring = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(null, "Please Enter Your Logic Password", c_lb1, astring);
         c_ua.f791[0].setEnabled(false);
         new C_UE(c_ua, 0, c_q_);
         c_q_.requestFocus();
         c_ua.m1322(C_UA.m1321(c_ua.getPreferredSize()));
         return c_ua.f790 != 0 ? null : (c__f.f932 = C__F.m1588(c_q_));
      }
   }

   static String m1866(C_0C c_0c, C_OE c_oe, C_x_A c_x_a) {
      C_LB c_lb = new C_LB();
      C_QF c_qf = new C_QF(2, 2, 0, 0, true, true);
      c_qf.setVgap(1);
      c_lb.setLayout(c_qf);
      c_lb.add(new C_ZE("password: "));
      C_q_ c_q_;
      c_lb.add(c_q_ = new C_q_(20));
      c_lb.add(new C_ZE("confirm: "));
      C_q_ c_q_1;
      c_lb.add(c_q_1 = new C_q_(20));
      c_lb.setBorder(new EmptyBorder(10, 5, 10, 5));
      C_LB c_lb1 = new C_LB();
      c_lb1.setLayout(new BorderLayout());
      C_s_B c_s_b = new C_s_B(LogicProgram.m1004(C_H.m412("not057")));
      c_lb1.add(c_s_b, "North");
      c_lb1.add(c_lb, "Center");
      c_lb1.setBorder(new EmptyBorder(10, 5, 10, 5));
      String[] astring = new String[]{"Create", "Cancel"};
      C_UA c_ua = new C_UA(null, "Please Create A Logic Password", c_lb1, astring);
      c_ua.f791[0].setEnabled(false);
      c_ua.f789 = false;
      new C_UE(c_ua, 0, c_q_, c_q_1);
      c_q_.requestFocus();
      c_ua.f789 = true;
      c_ua.m1322(C_UA.m1321(c_ua.getPreferredSize()));
      if (c_ua.f790 != 0) {
         return null;
      } else {
         C__F c__f = new C__F(c_q_);
         if (c_x_a != null) {
            c_x_a.m2162(true);
         }

         String s = C_SF.m1293(c_oe.m1156()).m1294();
         Integer integer = C_KC.m908(c_0c, c_oe, c__f, "Account " + c_oe.m1153() + " at " + s + "\nexists and has a different logic password.", 0);
         if (c_x_a != null) {
            c_x_a.m2162(false);
         }

         if (integer == null) {
            return null;
         } else {
            C_UA.m1329(C_H.m411("not013"), null, null, null);
            return c__f.f932;
         }
      }
   }

   static String m1867(C_0C c_0c, C_OE c_oe, C_x_A c_x_a) {
      C_LB c_lb = new C_LB();
      C_QF c_qf = new C_QF(2, 3, 0, 0, true, true);
      c_qf.setVgap(1);
      c_lb.setLayout(c_qf);
      c_lb.add(new C_ZE("old password: "));
      C_q_ c_q_;
      c_lb.add(c_q_ = new C_q_(20));
      c_lb.add(new C_ZE("new password: "));
      C_q_ c_q_1;
      c_lb.add(c_q_1 = new C_q_(20));
      c_lb.add(new C_ZE("confirm: "));
      C_q_ c_q_2;
      c_lb.add(c_q_2 = new C_q_(20));
      String[] astring = new String[]{"OK", "Cancel"};
      C_UA c_ua = new C_UA(null, "Please Supply A New Logic Password", c_lb, astring);
      c_ua.f791[0].setEnabled(false);
      new C_UE(c_ua, 0, c_q_1, c_q_2, c_q_);
      c_q_.requestFocus();
      c_ua.m1322(C_UA.m1321(c_ua.getPreferredSize()));
      if (c_ua.f790 != 0) {
         return null;
      } else {
         C__F c__f = new C__F(c_q_, c_q_1);
         if (c_x_a != null) {
            c_x_a.m2162(true);
         }

         Integer integer = C_KC.m908(c_0c, c_oe, c__f, "The old logic password is incorrect.", 2);
         if (c_x_a != null) {
            c_x_a.m2162(false);
         }

         if (integer == null) {
            return null;
         } else {
            if (c_x_a != null) {
               c_x_a.m2162(true);
            }

            boolean flag = C_KC.m915(c_0c, integer, c__f.f932, c__f.f933);
            if (c_x_a != null) {
               c_x_a.m2162(false);
            }

            return flag ? c__f.f933 : null;
         }
      }
   }

   static String m1868(C_0C c_0c, C_OE c_oe, C__F c__f, String s) {
      C_LB c_lb = new C_LB();
      C_QF c_qf = new C_QF(2, 3, 0, 0, true, true);
      c_qf.setVgap(1);
      c_lb.setLayout(c_qf);
      c_lb.add(new C_ZE("old password: "));
      C_q_ c_q_;
      c_lb.add(c_q_ = new C_q_(20));
      c_lb.add(new C_ZE("new password: "));
      C_q_ c_q_1;
      c_lb.add(c_q_1 = new C_q_(20));
      c_lb.add(new C_ZE("confirm: "));
      C_q_ c_q_2;
      c_lb.add(c_q_2 = new C_q_(20));
      C_LB c_lb1 = new C_LB();
      c_lb1.setLayout(new C_m_A());
      c_lb1.add(new C_s_B(LogicProgram.m1004(s + "\\n" + C_H.m412("not056"))));
      c_lb1.add(c_lb);
      String[] astring = new String[]{"OK", "Cancel"};
      C_UA c_ua = new C_UA(null, "Please Supply A New Logic Password", c_lb1, astring);
      c_ua.f791[0].setEnabled(false);
      new C_UE(c_ua, 0, c_q_1, c_q_2, c_q_);
      c_q_.requestFocus();
      c_ua.m1322(C_UA.m1321(c_ua.getPreferredSize()));
      if (c_ua.f790 != 0) {
         return null;
      } else {
         c__f.f932 = C__F.m1588(c_q_);
         return c__f.f933 = C__F.m1588(c_q_1);
      }
   }

   static C_HB m1869() {
      return m1870(null);
   }

   static C_HB m1870(String s) {
      C_HB c_hb = new C_HB();

      while (!c_hb.m683()) {
         if (!c_hb.m682(s)) {
            return null;
         }
      }

      return c_hb;
   }

   static C_HB m1871(String s, Hashtable hashtable) {
      C_0C c_0c = C_KC.m829(null);
      if (c_0c == null) {
         return null;
      } else {
         C_HB c_hb = m1870(C_H.m661(C_H.m412(s), hashtable));
         C__F c__f = c_hb == null ? null : new C__F(m1864(c_0c, c_hb, null));
         if (c__f != null && c__f.f932 != null) {
            hashtable = C_H.m667("site", c_hb.m1156(), "sid", c_hb.m1153());
            String s1 = C_H.m661(C_H.m412("not051"), hashtable);
            Integer integer = C_KC.m908(c_0c, c_hb, c__f, s1, 1);
            if (integer == null) {
               c_hb = null;
            }

            C_KC.m832(c_0c, null);
            return c_hb;
         } else {
            C_KC.m832(c_0c, null);
            return null;
         }
      }
   }

   static Boolean m1872(C_OE c_oe) {
      if (c_oe.f663) {
         return Boolean.TRUE;
      } else {
         C_0C c_0c = C_KC.m829(null);
         Boolean obool = c_0c == null ? Boolean.FALSE : C_KC.m911(c_0c, c_oe);

         try {
            if (obool == null) {
               if (m1866(c_0c, c_oe, null) == null) {
                  return Boolean.FALSE;
               }
            } else if (!obool) {
               C_H c_h = C_H.m411("not029");
               C_b_E c_b_e = new C_b_E(c_h.f373);
               C_UA.m1329(c_h, null, null, c_b_e);
               if (c_b_e.f1027 != 1 || C_KC.m902() != 0) {
                  return Boolean.FALSE;
               }

               return null;
            }

            return Boolean.TRUE;
         } finally {
            if (c_0c != null) {
               C_KC.m832(c_0c, null);
            }
         }
      }
   }

   static void m1873(C_0A c_0a) {
      m1874(c_0a.f15, c_0a.f16);
   }

   static void m1874(String[] astring, String[] astring1) {
      Dimension dimension = new Dimension(24 * LogicProgram.f539, 30 * LogicProgram.f539);
      C_LB c_lb = new C_LB();
      c_lb.setLayout(new BorderLayout());
      C_LB c_lb1 = new C_LB();
      c_lb1.setLayout(new C_m_A());
      Hashtable hashtable = C_H.m666("user", LogicProgram.f533.m1169());
      if (astring1.length == 0 && astring.length == 0) {
         c_lb1.add(new C_ZE("There were no problems to submit."));
         c_lb.add(c_lb1, "North");
      } else {
         C_LB c_lb2 = new C_LB();
         c_lb2.setLayout(new C_m_A());
         if (astring1.length == 0) {
            String s = C_H.m661("All problems were successfully\nsubmitted for\n<user>:", hashtable);
            c_lb1.add(new C_NC(LogicProgram.m1004(s)));
            int k1 = astring.length;

            for (int i1 = 0; i1 < k1; i1++) {
               c_lb2.add(new C_ZE(astring[i1]));
            }
         } else if (astring.length == 0) {
            c_lb1.add(new C_ZE("No problems could be submitted for"));
            c_lb1.add(new C_ZE(LogicProgram.f533.m1169() + ":"));
            int j1 = astring1.length;

            for (int l = 0; l < j1; l++) {
               c_lb2.add(new C_ZE(astring1[l]));
            }
         } else {
            c_lb1.add(new C_ZE("Submission results for"));
            c_lb1.add(new C_ZE(LogicProgram.f533.m1169() + ":"));
            c_lb2.add(new C_ZE("Succeeded:"));
            int j = astring.length;

            for (int i = 0; i < j; i++) {
               c_lb2.add(new C_ZE("  " + astring[i]));
            }

            c_lb2.add(new C_ZE("Failed:"));
            j = astring1.length;

            for (int k = 0; k < j; k++) {
               c_lb2.add(new C_ZE("  " + astring1[k]));
            }
         }

         c_lb.add(c_lb1, "North");
         JScrollPane jscrollpane = new JScrollPane(c_lb2);
         c_lb.add(jscrollpane, "Center");
      }

      String[] astring2 = new String[]{"OK"};
      C_UA c_ua = new C_UA(null, "Submission Results", c_lb, astring2);
      c_ua.setSize(dimension);
      c_ua.m1323(C_UA.m1321(dimension), true);
   }

   static void m1875(C_LD c_ld) {
      m1876(c_ld.f518, c_ld.f519, c_ld.f520);
   }

   static void m1876(String[] astring, String[] astring1, String s) {
      Dimension dimension = new Dimension(16 * LogicProgram.f539, 20 * LogicProgram.f539);
      C_LB c_lb = new C_LB();
      boolean flag = false;
      c_lb.setLayout(new BorderLayout());
      C_LB c_lb1 = new C_LB();
      c_lb1.setLayout(new C_m_A());
      if (astring1.length == 0 && astring.length == 0) {
         c_lb1.add(new C_ZE("There were no problems to upload."));
         c_lb.add(c_lb1, "North");
      } else {
         C_LB c_lb2 = new C_LB();
         c_lb2.setLayout(new C_m_A());
         if (astring1.length == 0) {
            c_lb1.add(new C_ZE("All problems were successfully uploaded."));
            int k1 = astring.length;

            for (int i1 = 0; i1 < k1; i1++) {
               c_lb2.add(new C_ZE(astring[i1]));
            }

            c_lb1.add(c_lb2);
         } else if (astring.length == 0) {
            c_lb1.add(new C_ZE("No problems could be uploaded."));
            int j1 = astring1.length;

            for (int l = 0; l < j1; l++) {
               c_lb2.add(new C_ZE(astring1[l]));
            }

            c_lb1.add(c_lb2);
            flag = true;
         } else {
            c_lb1.add(new C_ZE("Upload results:"));
            c_lb2.add(new C_ZE("Succeeded:"));
            int j = astring.length;

            for (int i = 0; i < j; i++) {
               c_lb2.add(new C_ZE("  " + astring[i]));
            }

            c_lb2.add(new C_ZE("Failed:"));
            j = astring1.length;

            for (int k = 0; k < j; k++) {
               c_lb2.add(new C_ZE("  " + astring1[k]));
            }

            c_lb1.add(c_lb2);
            flag = true;
         }

         if (flag) {
            C_H c_h = C_H.m411("not092");
            c_lb1.add(new C_NC(LogicProgram.m1004(c_h.f372)));
         }

         c_lb.add(c_lb1, "North");
         JScrollPane jscrollpane = new JScrollPane(c_lb2);
         c_lb.add(jscrollpane, "Center");
         if (astring1.length != 0 && s != null) {
            C_p_A c_p_a = new C_p_A(LogicProgram.m1004(s));
            c_p_a.m1787(true);
            c_p_a.m1789(true);
            c_p_a.setEnabled(false);
            c_lb.add(c_p_a, "South");
         }
      }

      String[] astring2 = new String[]{"OK"};
      C_UA c_ua = new C_UA(null, "Upload Results", c_lb, astring2);
      c_ua.setSize(dimension);
      c_ua.m1323(C_UA.m1321(dimension), true);
   }
}
