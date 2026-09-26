package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.io.IOException;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.text.StyledDocument;

class C_KB implements C_DE {
   static String[] f444 = LogicProgram.f596;

   static void m759(String s) {
      m765(s, null, null, null, null, null);
   }

   static void m760(String s, Point point) {
      m765(s, null, null, null, null, point);
   }

   static void m761(String s, Hashtable hashtable) {
      m765(s, hashtable, null, null, null, null);
   }

   static void m762(String s, Hashtable hashtable, C_G c_g) {
      m765(s, hashtable, null, null, c_g, null);
   }

   static void m763(String s, Hashtable hashtable, C_a_ c_a_) {
      m765(s, hashtable, null, c_a_, c_a_.f935, null);
   }

   static void m764(String s, Hashtable hashtable, Hashtable hashtable1, C_a_ c_a_) {
      m765(s, hashtable, hashtable1, c_a_, c_a_.f935, null);
   }

   static void m765(String s, Hashtable hashtable, Hashtable hashtable1, C_a_ c_a_, C_G c_g, Point point) {
      C_H c_h = C_n_.m411(s);
      String s1 = c_h.f370;
      String s2 = C_n_.m1962(c_h.f372, hashtable, c_a_, c_g);
      C_y_ c_y_ = null;
      if (c_h.f373 != null) {
         c_y_ = new C_y_(c_g, c_h.f373);
         if (hashtable1 != null) {
            c_y_.m448(hashtable1);
         }
      }

      C_UA.m1328(s1, s2, point, c_y_);
   }

   static boolean m766(LPDerivation lpderivation, Point point) {
      String s = lpderivation.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lpderivation.frame.setVisible(true);
         C_ZE c_ze = new C_ZE("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         C_UA c_ua = new C_UA(lpderivation.frame, "", c_ze, astring);
         c_ua.m1322(point);
         return c_ua.f790 == 0 && !lpderivation.saveProblems(s) ? false : c_ua.f790 == 0 || c_ua.f790 == 1;
      }
   }

   static boolean m767(LPDerivation lpderivation, Point point) {
      if (lpderivation.problem.m1555() != 1) {
         C_z_E c_z_e = new C_z_E();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            c_z_e.add(ajradiobutton[j] = new C_NF(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(lpderivation.frame, "", c_z_e, astring2);
         c_ua.m1322(null);
         if (c_ua.f790 != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            lpderivation.removeWork();
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lpderivation.problemIndex != -1 || !lpderivation.problem.m7(false).equals("")) {
         C_ZE c_ze = new C_ZE("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         C_UA c_ua1 = new C_UA(lpderivation.frame, "", c_ze, astring1);
         c_ua1.m1322(null);
         if (c_ua1.f790 != 0) {
            return false;
         }
      }

      if (lpderivation.problemIndex != -1) {
         LPDerivation.problems.m1101(lpderivation.problemIndex);
         LPDerivation.saveProblems();
      }

      lpderivation.newProblem();
      return true;
   }

   static boolean m768(LPDerivation lpderivation, Point point) {
      if (lpderivation.problem.m1555() == 1) {
         return false;
      } else {
         C_ZE c_ze = new C_ZE("Delete the work on this problem?");
         String[] astring = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(lpderivation.frame, "", c_ze, astring);
         c_ua.m1322(point);
         if (c_ua.f790 != 0) {
            return false;
         } else {
            lpderivation.removeWork();
            return true;
         }
      }
   }

   static boolean m769(LPDerivation lpderivation) {
      if (m766(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            int i = lpderivation.problemIndex + 1;
            if (i != 0 && LPDerivation.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPDerivation.problems.m1778(i);
            if (s == null) {
               return m770(lpderivation);
            }

            lpderivation.loadProblem(s);
            lpderivation.problemIndex = i;
            LPDerivation.problems.m1776(lpderivation.saveProblem(), i);
            if (C_U.eraseWork) {
               lpderivation.removeWork();
            }
         }
      }

      lpderivation.requestFocus();
      return true;
   }

   static boolean m770(LPDerivation lpderivation) {
      boolean flag = false;
      if (m766(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            C_YA c_ya = m776(lpderivation, false, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"OK", "Cancel"};
            C_UA c_ua = new C_UA(lpderivation.frame, C_e_D.m1782("Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("derChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               int i = c_ya.m1532(c_ya.f899);
               LPDerivation lpderivation1;
               if ((lpderivation1 = LPDerivation.openInstance(i, true)) != null) {
                  lpderivation1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lpderivation.loadProblem(LPDerivation.problems.m1778(i));
                  lpderivation.problemIndex = i;
                  LPDerivation.problems.m1776(lpderivation.saveProblem(), i);
                  if (C_U.eraseWork) {
                     lpderivation.removeWork();
                  }

                  flag = true;
               }
            }
         }
      }

      lpderivation.requestFocus();
      return flag;
   }

   static int[] m771(LPDerivation lpderivation) {
      int[] aint = null;
      if (m766(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            C_YA c_ya = m776(lpderivation, true, false, null);
            if (LPDerivation.submitExam) {
               c_ya.clearSelection();
            }

            Vector vector = LPDerivation.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = C_XD.m1493(s);
                  int j = LPDerivation.problems.m1767(s1);
                  if (j != -1) {
                     c_ya.m1531(j, c_ya.f899);
                  }
               }
            }

            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            C_LB c_lb = new C_LB();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(C_H.m412("not081")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            c_lb.add(c_s_b, "North");
            c_lb.add(jscrollpane, "Center");
            String[] astring = new String[]{"Submit", "Cancel"};
            C_UA c_ua = new C_UA(lpderivation.frame, C_e_D.m1782("Submit Problems"), c_lb, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("derChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            }
         }
      }

      lpderivation.requestFocus();
      return aint;
   }

   static int[] m772(LPDerivation lpderivation) {
      int[] aint = null;
      if (m766(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            C_YA c_ya = m776(lpderivation, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            C_LB c_lb = new C_LB();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(C_H.m412("not089")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            c_lb.add(c_s_b, "North");
            c_lb.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            C_UA c_ua = new C_UA(lpderivation.frame, C_e_D.m1782("Upload Problems"), c_lb, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("derChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            }
         }
      }

      lpderivation.requestFocus();
      return aint;
   }

   static void m773(LPDerivation lpderivation) {
      synchronized (LPDerivation.problems) {
         C_YA c_ya = m776(lpderivation, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(c_ya);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         C_UA c_ua = new C_UA(lpderivation.frame, C_e_D.m1782("Delete Problems"), jscrollpane, astring);
         c_ya.m1528(c_ua, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
         c_ua.setSize(dimension);
         c_ua.m1317("derChosen");
         c_ya.requestFocus();
         c_ua.m1323(C_UA.m1321(dimension), true);
         if (c_ua.f790 == 0) {
            int[] aint1 = c_ya.m1533(c_ya.f899);
            if (aint1.length != 0) {
               C_H c_h1 = C_H.m411("not091");
               C_b_E c_b_e1 = new C_b_E(c_h1.f373);
               C_UA.m1329(c_h1, null, null, c_b_e1);
               if (c_b_e1.f1027 == 0) {
                  int k = aint1.length;

                  while (--k >= 0) {
                     int l = aint1[k];
                     C_EE c_ee1 = (C_EE)LPDerivation.problems.m1779(l);
                     if (c_ee1 != null) {
                        C_XD c_xd1 = new C_XD(c_ee1.f1119);
                        if (!LPDerivation.isExample(c_xd1.m1494())) {
                           c_ee1.f1119 = LPDerivation.removeWork(c_xd1);
                           c_ee1.f1120 = 0;
                           LPDerivation lpderivation2 = LPDerivation.openInstance(l, false);
                           if (lpderivation2 != null) {
                              lpderivation2.loadProblem(c_ee1.f1119);
                           }
                        }
                     }
                  }
               }

               LPDerivation.saveProblems();
            }
         } else if (c_ua.f790 == 1) {
            int[] aint = c_ya.m1533(c_ya.f899);
            if (aint.length != 0) {
               C_H c_h = C_H.m411("not090");
               C_b_E c_b_e = new C_b_E(c_h.f373);
               C_UA.m1329(c_h, null, null, c_b_e);
               if (c_b_e.f1027 == 0) {
                  int i = aint.length;

                  while (--i >= 0) {
                     int j = aint[i];
                     C_EE c_ee = (C_EE)LPDerivation.problems.m1779(j);
                     if (c_ee != null) {
                        C_XD c_xd = new C_XD(c_ee.f1119);
                        if (!LPDerivation.isExercise(c_xd.m1494())) {
                           LPDerivation lpderivation1 = LPDerivation.openInstance(j, false);
                           LPDerivation.problems.m1101(j);
                           if (lpderivation1 != null) {
                              lpderivation1.newProblem();
                           }
                        }
                     }
                  }
               }

               LPDerivation.saveProblems();
            }
         }
      }

      lpderivation.requestFocus();
   }

   static int[] m774(LPDerivation lpderivation) {
      int[] aint = null;
      if (m766(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            C_YA c_ya = m776(lpderivation, true, false, LPDerivation.noPrint);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"Print", "Print Results", "Print List", "Cancel"};
            c_ya.f898 = 3;
            C_UA c_ua = new C_UA(lpderivation.frame, C_e_D.m1782("Print Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("derChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            } else if (c_ua.f790 == 1) {
               C_Z.m1544(c_ya.m1533(c_ya.f899));
            } else if (c_ua.f790 == 2) {
               C_g_A.m1819(c_ya.m1533(c_ya.f899));
            }
         }
      }

      lpderivation.requestFocus();
      return aint;
   }

   static void m775(LPDerivation lpderivation) {
      if (m766(lpderivation, null)) {
         C_0E c_0e = new C_0E();
         Dimension dimension = new Dimension(20 * LogicProgram.f539, 10 * LogicProgram.f539);
         Point point = C_UA.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         C_p_A c_p_a = new C_p_A();
         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(c_p_a);
         c_p_a.setBackground(Color.WHITE);
         C_UA c_ua = new C_UA(c_0e, "Submitted Problem", jscrollpane, astring);
         c_ua.setSize(dimension);
         c_ua.m1314(0);
         c_ua.m1317("derSubmitted");
         c_p_a.requestFocus();
         c_ua.m1323(point, true);
         c_0e.dispose();
         if (c_ua.f790 == 0) {
            lpderivation.loadProblem(c_p_a.getText());
         }
      }

      lpderivation.requestFocus();
   }

   static C_YA m776(LPDerivation lpderivation, boolean flag, boolean flag1, C_BE c_be) {
      return LPDerivation.problems.m1781(lpderivation, LPDerivation.exercises, flag, flag1, LPDerivation.monoProbs, c_be);
   }

   static void m777(LPDerivation lpderivation) {
      if (m766(lpderivation, null)) {
         C_0E c_0e = new C_0E();
         Dimension dimension = new Dimension(20 * LogicProgram.f539, 10 * LogicProgram.f539);
         Point point = C_UA.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         C_IF c_if = new C_IF(c_0e);
         if (lpderivation.lastUserProblem != null) {
            c_if.setText(LogicProgram.m995(lpderivation.lastUserProblem, maggie, f444));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(c_if);
         c_if.setBackground(Color.WHITE);
         C_UA c_ua = new C_UA(c_0e, "User Problem", jscrollpane, astring);
         c_ua.setSize(dimension);
         c_ua.m1314(0);
         c_ua.m1317("derUser");
         c_if.f425 = c_ua;
         c_if.requestFocus();
         c_ua.m1323(point, true);
         c_0e.dispose();
         if (c_ua.f790 == 0) {
            String s = c_if.getText();
            if (!LPDerivation.validateUserProblem(s)) {
               return;
            }

            lpderivation.lastUserProblem = LogicProgram.m995(s, f444, maggie);
            lpderivation.loadUserProblem(lpderivation.lastUserProblem);
         }
      }

      lpderivation.requestFocus();
   }

   static int m778(C_G c_g, C_RF[] ac_rf, String s) {
      if (c_g.f317.f915.serialMode) {
         return -1;
      } else {
         int i = ac_rf.length;
         C_z_E c_z_e = new C_z_E();
         JPanel jpanel = new JPanel();
         JRadioButton[] ajradiobutton = new JRadioButton[i];
         jpanel.setLayout(new C_m_A());
         jpanel.add(C_q_B.m2031(s));
         jpanel.add(c_z_e);
         c_z_e.setLayout(new C_m_A());

         for (int j = 0; j < i; j++) {
            ajradiobutton[j] = new C_NA(LogicProgram.m993(ac_rf[j].toString()));
            c_z_e.add(ajradiobutton[j]);
         }

         ajradiobutton[0].setSelected(true);
         String[] astring = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(c_g.f317.f915.frame, "Line " + c_g.m30(), jpanel, astring);
         c_ua.m1314(0);
         c_g.m22(true);
         Rectangle rectangle = LogicProgram.m1035(c_g.f324, null);
         c_ua.pack();
         c_ua.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         if (c_ua.f790 == 0) {
            for (int k = 0; k < i; k++) {
               if (ajradiobutton[k].isSelected()) {
                  return k;
               }
            }
         }

         return -1;
      }
   }

   static void m779(Reader reader) {
      String s1 = "";
      C_XB c_xb;
      if (reader instanceof C_XB) {
         c_xb = (C_XB)reader;
      } else {
         c_xb = new C_XB(reader, LogicProgram.f537);
      }

      String s;
      try {
         while ((s = c_xb.readLine()) != null) {
            s1 = s1 + s + "\n";
         }
      } catch (IOException ioexception) {
      }

      C_0E c_0e = new C_0E();
      JScrollPane jscrollpane = new JScrollPane();
      C_s_D c_s_d = new C_s_D(LogicProgram.m1004(s1));
      c_s_d.setEditable(false);
      c_s_d.setBackground(dialogWhite);
      String[] astring = new String[]{"OK"};
      jscrollpane.setViewportView(c_s_d);
      C_UA c_ua = new C_UA(c_0e, "Program Help", jscrollpane, astring);
      Dimension dimension = new Dimension(45 * LogicProgram.f539, 32 * LogicProgram.f539);
      c_ua.setSize(dimension);
      c_ua.m1323(C_UA.m1321(dimension), true);
      c_0e.dispose();
   }

   static int m780(C_a_ c_a_, Vector vector) {
      int j = vector.size();
      if (j == 1) {
         return 0;
      } else if (j == 0) {
         return -1;
      } else {
         Vector vector1 = new Vector();
         int k = 0;

         for (int i = 0; i < j; i++) {
            Object object = vector.elementAt(i);
            C_e_B c_e_b = null;
            if (object instanceof C_HF) {
               C_HF c_hf = (C_HF)object;
               c_e_b = c_hf.m700(null).m542();
            } else if (object instanceof C_GA) {
               C_GA c_ga = (C_GA)object;
               c_e_b = c_ga.m606(c_a_);
            }

            if (c_e_b != null && vector1.indexOf(c_e_b) == -1) {
               vector1.addElement(c_e_b);
               k++;
            } else {
               vector1.addElement(null);
            }
         }

         if (k == 1) {
            return 0;
         } else if (c_a_.f935.f317.f915.serialMode) {
            return -1;
         } else {
            C_z_E c_z_e = new C_z_E();
            C_LB c_lb1 = new C_LB();
            JRadioButton[] ajradiobutton = new JRadioButton[j];
            c_lb1.setLayout(new C_m_A());
            int l = c_a_.f961;
            Hashtable hashtable = C_H.m666("n", l + "");
            if (l > 0) {
               c_lb1.add(C_q_B.m2031(C_n_.m1960(C_n_.m412("derdlg003"), hashtable, c_a_)));

               for (int i1 = 0; i1 < l; i1++) {
                  c_lb1.add(LogicProgram.m985(c_a_.m1628(i1 - l).toString(), 14));
               }
            }

            if (c_a_.f944 && c_a_.f941 != null) {
               String s1 = l > 0 ? "derdlg004" : "derdlg005";
               c_lb1.add(C_q_B.m2031(C_n_.m1960(C_n_.m412(s1), hashtable, c_a_)));
               c_lb1.add(LogicProgram.m985(c_a_.f941.toString(), 14));
               c_lb1.add(C_q_B.m2031(C_n_.m1960(C_n_.m412("derdlg006"), hashtable, c_a_)));
            } else {
               String s = "derdlg017";
               c_lb1.add(C_q_B.m2031(C_n_.m1960(C_n_.m412(s), hashtable, c_a_)));
            }

            c_lb1.add(c_z_e);
            c_z_e.setLayout(new C_m_A());
            boolean flag = false;

            for (int j1 = 0; j1 < j; j1++) {
               C_e_B c_e_b1 = (C_e_B)vector1.elementAt(j1);
               if (c_e_b1 == null) {
                  ajradiobutton[j1] = new C_NA("");
               } else if (c_a_.f944 && c_a_.f941 != null) {
                  Object object1 = vector.elementAt(j1);
                  if (object1 instanceof C_HF) {
                     C_HF c_hf1 = (C_HF)object1;
                     C_LF c_lf = c_hf1.m688();
                     ajradiobutton[j1] = new C_NA(LogicProgram.m993(c_lf.m957(c_hf1.f392)));
                     c_z_e.add(ajradiobutton[j1]);
                     if (!flag) {
                        JRadioButton jradiobutton = ajradiobutton[j1];
                        flag = true;
                        jradiobutton.setSelected(true);
                     }
                  } else if (object1 instanceof C_GA) {
                     C_GA c_ga1 = (C_GA)object1;
                     C_LF c_lf1 = c_ga1.m624(c_a_);
                     ajradiobutton[j1] = new C_NA(LogicProgram.m993(c_lf1.toString()));
                     c_z_e.add(ajradiobutton[j1]);
                     if (!flag) {
                        JRadioButton jradiobutton1 = ajradiobutton[j1];
                        flag = true;
                        jradiobutton1.setSelected(true);
                     }
                  } else {
                     ajradiobutton[j1] = new C_NA("");
                  }
               } else {
                  C_LB c_lb = new C_LB();
                  c_lb.m934(LogicProgram.f541.width * 3 / 4);
                  c_lb.m937(true);
                  StyledDocument styleddocument = LogicProgram.m998(c_e_b1.f1069, maggie, f444, C_CB.m420(c_e_b1.f1070));
                  C_p_A c_p_a = new C_p_A(styleddocument, -1, -1, true);
                  c_p_a.m1787(true);
                  c_p_a.m1789(true);
                  c_p_a.setEditable(false);
                  c_lb.add(c_p_a, "Center");
                  C_HC c_hc = new C_HC(c_lb);
                  ajradiobutton[j1] = c_hc.f382;
                  c_z_e.add(c_hc);
                  if (!flag) {
                     JRadioButton jradiobutton2 = ajradiobutton[j1];
                     flag = true;
                     jradiobutton2.setSelected(true);
                  }
               }
            }

            String[] astring = new String[]{"OK", "Cancel"};
            C_UA c_ua = new C_UA(c_a_.f935.f317.f915.frame, "Line " + c_a_.f935.m30(), c_lb1, astring);
            c_ua.m1314(0);
            c_a_.f935.m22(true);
            Rectangle rectangle = LogicProgram.m1035(c_a_.f935.f324, null);
            c_ua.pack();
            c_ua.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
            if (c_ua.f790 != 0) {
               return -1;
            } else {
               int k1 = 0;
               k1 = 0;

               while (k1 < j && !ajradiobutton[k1].isSelected()) {
                  k1++;
               }

               return k1 == j ? -1 : k1;
            }
         }
      }
   }

   static C_RF m781(C_a_ c_a_, C_RF c_rf, boolean flag) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new C_m_A());
      String[] astring = new String[]{"OK", "Cancel"};
      JRadioButton[] ajradiobutton = new JRadioButton[2];
      C_RF[] ac_rf = new C_RF[2];
      C_z_E c_z_e = new C_z_E();
      c_z_e.setLayout(new C_m_A());

      for (int i = 0; i < 2; i++) {
         ac_rf[i] = c_rf.m1217(i).m1237();
         if (flag) {
            ac_rf[i] = ac_rf[i].m1256();
         }

         c_z_e.add(ajradiobutton[i] = new C_NF(LogicProgram.m995(ac_rf[i].toString(), maggie, f444)));
      }

      ajradiobutton[0].setSelected(true);
      jpanel.add(new C_ZE("Please choose a formula to show"));
      jpanel.add(c_z_e);
      C_p_A c_p_a = new C_p_A();
      C_UA c_ua = new C_UA(c_a_.f935.f317.f915.frame, "Choose a Formula", jpanel, astring);
      if (c_ua.m1327(c_a_.f956, new C_p_A[]{c_p_a}, 0)) {
         String s = c_p_a.getText();
         if (s.equalsIgnoreCase("L")) {
            return ac_rf[0];
         }

         if (s.equalsIgnoreCase("R")) {
            return ac_rf[1];
         }
      }

      if (c_a_.f935.f317.f915.serialMode) {
         c_a_.m1621("dererr064");
         c_a_.f935.f317.f915.complete = false;
         c_ua.dispose();
         return null;
      } else {
         Rectangle rectangle = LogicProgram.m1035(c_a_.f935, null);
         c_ua.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         if (c_ua.f790 == 0) {
            if (ajradiobutton[0].isSelected()) {
               return ac_rf[0];
            }

            if (ajradiobutton[1].isSelected()) {
               return ac_rf[1];
            }
         }

         return null;
      }
   }

   static boolean m782(C_a_ c_a_, C_HD c_hd) {
      if (c_a_.f935.f317.f915.serialMode && c_a_.f956 == null) {
         c_a_.m1621("dererr064");
         c_a_.f935.f317.f915.complete = false;
         return false;
      } else {
         C_GA c_ga = null;
         C_HF c_hf = null;
         Object object = null;
         if (c_hd instanceof C_GA) {
            c_ga = (C_GA)c_hd;
            c_hf = c_ga.m625();
            object = c_ga.f343;
         } else {
            if (!(c_hd instanceof C_HF)) {
               throw new IllegalArgumentException("instanceSchemeQuery expects LPRuleInstance or LPInterchangeInstance");
            }

            c_ga = null;
            c_hf = (C_HF)c_hd;
            object = new C_e_();
         }

         boolean flag = c_a_.f944 && c_a_.f941 != null;
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new C_m_A());
         C_j_D c_j_d = c_hf.f393;
         C_f_ c_f_ = null;
         C_HF c_hf1 = null;
         C_LF c_lf = c_hf.f391;
         int i = c_lf.f526.length;
         Hashtable hashtable = C_H.m666("n", i + "");
         if (c_ga != null) {
            C_H.m664(hashtable, "rule name", c_lf.f820);
         }

         if (i > 0) {
            jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.m412("derdlg003"), hashtable, c_a_)));

            for (int j = 0; j < i; j++) {
               jpanel.add(LogicProgram.m987(c_a_.m1628(j - i).m1220((C_e_)object).toString(), 14, 350));
            }
         }

         if (flag) {
            String s1 = i > 0 ? "derdlg004" : "derdlg005";
            jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.m412(s1), hashtable, c_a_)));
            jpanel.add(LogicProgram.m987(c_a_.f941.m1220((C_e_)object).toString(), 14, 350));
            jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.m412("derdlg007"), hashtable, c_a_)));
            jpanel.add(LogicProgram.m987(c_lf.m957(c_hf.f392), 14, 350));
            c_f_ = new C_f_(c_j_d, 250, c_a_.f935.f317.f915.frame);
         } else {
            C_FD c_fd = c_hf.m700(null);
            c_hf1 = c_fd.f311;
            String s = "derdlg019";
            jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.m412(s), hashtable, c_a_)));
            C_e_B c_e_b = c_fd.m542();
            jpanel.add(LogicProgram.m988(c_e_b, 14, 350));
            c_f_ = new C_f_(c_fd.f312, 250, c_a_.f935.f317.f915.frame, true);
         }

         jpanel.add(c_f_);
         String[] astring = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(c_a_.f935.f317.f915.frame, "Line " + c_a_.f935.m30(), jpanel, astring);
         c_ua.m1314(0);
         c_f_.m1801(c_ua);
         c_a_.f935.m22(true);
         Rectangle rectangle = LogicProgram.m1035(c_a_.f935.f324, null);
         if (!c_ua.m1327(c_a_.f956, c_f_.m1803(), 0)) {
            if (c_a_.f935.f317.f915.serialMode) {
               c_a_.m1621("dererr064");
               c_a_.f935.f317.f915.complete = false;
               c_ua.dispose();
               return false;
            }

            c_ua.pack();
            c_ua.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            c_ua.dispose();
         }

         if (c_ua.f790 != 0) {
            c_a_.m1621("dererr028");
            c_a_.f935.f317.f915.abort(true);
            return false;
         } else {
            C_j_D c_j_d1 = c_f_.m1802();
            if (c_j_d1 == null) {
               c_a_.m1622(c_f_.f1099, c_f_.f1100);
               return false;
            } else {
               if (flag) {
                  if (!c_j_d.m1877(c_j_d1)) {
                     c_a_.m1622(c_j_d.f1190, c_j_d.f1191);
                     return false;
                  }
               } else {
                  while (!c_j_d.f1189.isEmpty()) {
                     C_RF c_rf = ((C_i_A)c_j_d.f1189.elementAt(0)).m1175();
                     C_RF c_rf1 = c_rf.m1238(c_hf1.f393).m1238(c_j_d1);
                     if (!c_j_d.m1881(c_rf, c_rf1)) {
                        c_a_.m1622(c_j_d.f1190, c_j_d.f1191);
                        return false;
                     }
                  }
               }

               return true;
            }
         }
      }
   }

   static boolean m783(C_a_ c_a_, C_HF c_hf) {
      if (c_a_.f935.f317.f915.serialMode) {
         c_a_.f935.f317.f915.complete = false;
         c_a_.m1621("dererr064");
         return false;
      } else {
         C_n_D c_n_d = new C_n_D(250);
         C_j_D c_j_d = c_hf.f393;
         C_b_ c_b_ = new C_b_(c_a_.m1628(-1), 250, c_a_.f935.f317.f915.frame);
         c_b_.m1845(c_n_d);
         if (!m787(c_a_, c_hf, "derdlg008", null, c_b_, c_n_d, null)) {
            return false;
         } else {
            c_b_.m1846(c_n_d);
            String s = LogicProgram.m995(c_b_.getText(), f444, maggie);
            C_X c_x = c_b_.f1166[0];
            if (!c_j_d.m1882(c_j_d.f1189.elementAt(0).toString(), s)) {
               c_a_.m1622(c_j_d.f1190, c_j_d.f1191);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m784(C_a_ c_a_, C_HF c_hf) {
      if (c_a_.f935.f317.f915.serialMode && c_a_.f956 == null) {
         c_a_.f935.f317.f915.complete = false;
         c_a_.m1621("dererr064");
         return false;
      } else {
         JPanel jpanel = new JPanel();
         JPanel jpanel1 = new JPanel();
         JPanel jpanel2 = new JPanel();
         jpanel.setLayout(new C_m_A());
         jpanel1.setLayout(new C_QF(1, 1, 0, 0, true, true));
         jpanel2.setLayout(new BorderLayout());
         C_j_D c_j_d = c_hf.f393;
         C_IF c_if = new C_IF("", 250, c_a_.f935.f317.f915.frame);
         jpanel1.add(c_if, new Point(0, 0));
         JScrollPane jscrollpane = new JScrollPane(jpanel1, 22, 31);
         jpanel2.add(jscrollpane);
         Hashtable hashtable = C_H.m666("gen var", "\\l" + ((C_RF)c_a_.f937.elementAt(0)).m1217(0) + "\\l");
         jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.m412("derdlg009"), hashtable, c_a_)));
         jpanel.add(jpanel2);
         String[] astring = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(c_a_.f935.f317.f915.frame, "Line " + c_a_.f935.m30(), jpanel, astring);
         c_if.f425 = c_ua;
         c_a_.f935.m22(true);
         Rectangle rectangle = LogicProgram.m1035(c_a_.f935.f324, null);
         c_ua.m1314(0);
         C_y_ c_y_ = new C_y_(c_a_.f935, "OK:existentialVarQueryOK.Cancel");
         c_y_.m447("just", c_a_);
         c_y_.m447("inst", c_hf);
         c_y_.m447("edit", c_if);
         c_ua.m1315(c_y_);
         if (!c_ua.m1327(c_a_.f956, new C_p_A[]{c_if}, 0)) {
            if (c_a_.f935.f317.f915.serialMode) {
               c_a_.f935.f317.f915.complete = false;
               c_a_.m1621("dererr064");
               c_ua.dispose();
               return false;
            }

            c_ua.pack();
            c_ua.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            c_ua.dispose();
         }

         if (c_ua.f790 != 0) {
            c_a_.m1621("dererr028");
            c_a_.f935.f317.f915.abort(true);
            return false;
         } else {
            String s = LogicProgram.m995(c_if.getText(), f444, maggie);
            if (!c_j_d.m1882(c_j_d.f1189.elementAt(0).toString(), s)) {
               c_a_.m1622(c_j_d.f1190, c_j_d.f1191);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m785(C_a_ c_a_, C_HF c_hf) {
      if (c_a_.f935.f317.f915.serialMode && c_a_.f956 == null) {
         c_a_.f935.f317.f915.complete = false;
         c_a_.m1621("dererr064");
         return false;
      } else {
         JPanel jpanel = new JPanel();
         JPanel jpanel1 = new JPanel();
         JPanel jpanel2 = new JPanel();
         jpanel.setLayout(new C_m_A());
         jpanel1.setLayout(new C_QF(1, 1, 0, 0, true, true));
         jpanel2.setLayout(new BorderLayout());
         C_j_D c_j_d = c_hf.f393;
         C_IF c_if = new C_IF("", 250, c_a_.f935.f317.f915.frame);
         jpanel1.add(c_if, new Point(0, 0));
         JScrollPane jscrollpane = new JScrollPane(jpanel1, 22, 31);
         jpanel2.add(jscrollpane);
         Hashtable hashtable = C_H.m666("gen var", "\\l" + ((C_RF)c_a_.f937.elementAt(0)).m1217(0) + "\\l");
         jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.m412("derdlg010"), hashtable, c_a_)));
         jpanel.add(jpanel2);
         String[] astring = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(c_a_.f935.f317.f915.frame, "Line " + c_a_.f935.m30(), jpanel, astring);
         c_if.f425 = c_ua;
         c_a_.f935.m22(true);
         Rectangle rectangle = LogicProgram.m1035(c_a_.f935.f324, null);
         c_ua.m1314(0);
         C_y_ c_y_ = new C_y_(c_a_.f935, "OK:universalTermQueryOK.Cancel");
         c_y_.m447("just", c_a_);
         c_y_.m447("inst", c_hf);
         c_y_.m447("edit", c_if);
         c_ua.m1315(c_y_);
         if (!c_ua.m1327(c_a_.f956, new C_p_A[]{c_if}, 0)) {
            if (c_a_.f935.f317.f915.serialMode) {
               c_a_.f935.f317.f915.complete = false;
               c_a_.m1621("dererr064");
               c_ua.dispose();
               return false;
            }

            c_ua.pack();
            c_ua.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            c_ua.dispose();
         }

         if (c_ua.f790 != 0) {
            c_a_.m1621("dererr028");
            c_a_.f935.f317.f915.abort(true);
            return false;
         } else {
            String s = LogicProgram.m995(c_if.getText(), f444, maggie);
            if (!c_j_d.m1882(c_j_d.f1189.elementAt(0).toString(), s)) {
               c_a_.m1622(c_j_d.f1190, c_j_d.f1191);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m786(C_a_ c_a_, C_HF c_hf, C__B c__b) {
      if (c_a_.f935.f317.f915.serialMode && c_a_.f956 == null) {
         c_a_.f935.f317.f915.complete = false;
         c_a_.m1621("dererr064");
         return false;
      } else {
         JPanel jpanel = new JPanel();
         JPanel jpanel1 = new JPanel();
         JPanel jpanel2 = new JPanel();
         jpanel.setLayout(new C_m_A());
         jpanel1.setLayout(new C_QF(1, 1, 0, 0, true, true));
         jpanel2.setLayout(new BorderLayout());
         C_IF c_if = new C_IF("", 250, c_a_.f935.f317.f915.frame);
         jpanel1.add(c_if, new Point(0, 0));
         JScrollPane jscrollpane = new JScrollPane(jpanel1, 22, 31);
         jpanel2.add(jscrollpane);
         C_RF c_rf = c_hf.m688().f527;
         jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.m412("derdlg011"), null, c_a_)));
         jpanel.add(jpanel2);
         String[] astring = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(c_a_.f935.f317.f915.frame, "Line " + c_a_.f935.m30(), jpanel, astring);
         c_if.f425 = c_ua;
         c_a_.f935.m22(true);
         Rectangle rectangle = LogicProgram.m1035(c_a_.f935.f324, null);
         c_ua.m1314(0);
         C_y_ c_y_ = new C_y_(c_a_.f935, "OK:dummyVarQueryOK.Cancel");
         c_y_.m447("just", c_a_);
         c_y_.m447("inst", c_hf);
         c_y_.m447("edit", c_if);
         c_ua.m1315(c_y_);
         if (!c_ua.m1327(c_a_.f956, new C_p_A[]{c_if}, 0)) {
            if (c_a_.f935.f317.f915.serialMode) {
               c_a_.f935.f317.f915.complete = false;
               c_a_.m1621("dererr064");
               c_ua.dispose();
               return false;
            }

            c_ua.pack();
            c_ua.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            c_ua.dispose();
         }

         if (c_ua.f790 != 0) {
            c_a_.m1621("dererr028");
            c_a_.f935.f317.f915.abort(true);
            return false;
         } else {
            String s = LogicProgram.m995(c_if.getText(), f444, maggie);
            if (!c__b.m1571(C__B.m1579(c_rf.m1243(), 0), s)) {
               c_a_.m1622("dererr060", C_H.m666("variable name", "\\l" + s + "\\l"));
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m787(C_a_ c_a_, C_HF c_hf, String s, Hashtable hashtable, C_h_F c_h_f, C_n_D c_n_d, C_X[] ac_x) {
      if (c_a_.f935.f317.f915.serialMode) {
         c_a_.f935.f317.f915.complete = false;
         c_a_.m1621("dererr064");
         return false;
      } else {
         int i = c_n_d.f1303;
         C_H c_h = C_n_.m411(s);
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new C_m_A());
         JPanel jpanel1 = new JPanel();
         jpanel1.setLayout(new BorderLayout());
         JPanel jpanel2 = new JPanel();
         jpanel2.setLayout(new BorderLayout());
         JPanel jpanel3 = new JPanel();
         jpanel3.setLayout(new BorderLayout());
         JPanel jpanel4 = new JPanel();
         jpanel4.setLayout(new BorderLayout());
         jpanel3.add(c_h_f);
         jpanel4.add(c_n_d);
         jpanel1.add(new JScrollPane(jpanel3, 22, 31));
         jpanel2.add(new JScrollPane(jpanel4, 22, 31));
         jpanel.add(C_q_B.m2031(C_n_.m1960(c_h.f372, hashtable, c_a_)));
         jpanel.add(jpanel1);
         jpanel.add(jpanel2);
         if (ac_x != null) {
            Vector vector = c_h_f.f1168;
            int j = ac_x.length;

            for (int k = 0; k < j; k++) {
               C_X c_x = ac_x[k];
               if (c_x != null) {
                  Enumeration enumeration = vector.elements();

                  while (enumeration.hasMoreElements()) {
                     C_s_ c_s_ = (C_s_)enumeration.nextElement();
                     c_s_.m1964(k, LogicProgram.m995(c_x.toString(), maggie, f444));
                  }

                  c_h_f.f1166[k] = c_x;
                  c_h_f.f1167[k]++;
               }
            }
         }

         C_y_ c_y_ = new C_y_(c_a_.f935, c_h.f373);
         c_y_.m447("undo", c_h_f);
         c_y_.m447("just", c_a_);
         c_y_.m447("inst", c_hf);
         c_y_.m447("edit", c_h_f);
         C_UA c_ua = new C_UA(c_a_.f935.f317.f915.frame, "Line " + c_a_.f935.m30(), jpanel, c_y_.f272);
         c_h_f.m715(c_ua);
         c_h_f.f425 = c_ua;
         c_ua.setSize(c_ua.getPreferredSize());
         c_a_.f935.m22(true);
         Rectangle rectangle = LogicProgram.m1035(c_a_.f935.f324, null);
         c_ua.m1314(0);
         c_ua.m1315(c_y_);
         c_ua.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         if (c_ua.f790 != 0) {
            c_a_.m1621("dererr028");
            c_a_.f935.f317.f915.abort(true);
            return false;
         } else {
            return true;
         }
      }
   }

   static boolean m788(C_a_ c_a_, C_HF c_hf) {
      if (c_a_.f935.f317.f915.serialMode) {
         c_a_.f935.f317.f915.complete = false;
         c_a_.m1621("dererr064");
         return false;
      } else {
         int i = c_hf.f391.f526[c_hf.f392[0]].f739.equals("=") ? 0 : 1;
         C_RF c_rf = c_a_.m1628(i - 2);
         C_RF c_rf1 = c_a_.m1628(-i - 1);
         String s = c_hf.f391.f526[c_hf.f392[1 - i]].m1217(0).f739;
         String s1 = c_hf.f391.f527.m1217(0).f739;
         int j = c_hf.f391.f526[c_hf.f392[i]].m1217(0).f739.equals(s) ? 0 : 1;
         C_X c_x = (C_X)c_rf.m1217(j);
         C_n_D c_n_d = new C_n_D(250);
         C_j_D c_j_d = c_hf.f393;
         Hashtable hashtable = C_H.m670(
            "rule term", s, "other term", s1, "term A", "\\l" + c_x + "\\l", "term B", "\\l" + c_rf.m1217(1 - j) + "\\l", "wff A", "\\l" + c_rf1 + "\\l"
         );
         C_j_E c_j_e = new C_j_E(c_rf1, 250, c_a_.f935.f317.f915.frame, hashtable);
         c_j_e.m1845(c_n_d);
         if (!m787(c_a_, c_hf, "derdlg012", hashtable, c_j_e, c_n_d, new C_X[]{c_x})) {
            return false;
         } else {
            String s2 = LogicProgram.m995(c_j_e.getText(), f444, maggie);
            if (!c_j_d.m1882(c_j_d.f1189.elementAt(0).toString(), s2)) {
               c_a_.m1622(c_j_d.f1190, c_j_d.f1191);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m789(C_a_ c_a_, C_HF c_hf) {
      if (c_a_.f935.f317.f915.serialMode) {
         c_a_.f935.f317.f915.complete = false;
         c_a_.m1621("dererr064");
         return false;
      } else {
         int i = c_hf.f391.f526[c_hf.f392[0]].f739.equals("~") ? 0 : 1;
         C_RF c_rf = c_a_.m1628(-i - 1);
         C_RF c_rf1 = c_a_.m1628(i - 2);
         String s = c_hf.f391.f526[c_hf.f392[1 - i]].m1217(0).f739;
         String s1 = c_hf.f391.f526[c_hf.f392[i]].m1217(0).f739;
         C_n_D c_n_d = new C_n_D(250);
         C_j_D c_j_d = c_hf.f393;
         Hashtable hashtable = C_H.m667("rule term", s, "other term", s1);
         int j = c_j_d.f1189.size();
         C_i_A c_i_a = null;

         for (int k = 0; k < j; k++) {
            C_i_A c_i_a1 = (C_i_A)c_j_d.f1189.elementAt(k);
            if (c_i_a1 instanceof C_w_C) {
               c_i_a = c_i_a1;
               break;
            }
         }

         C_KE c_ke = new C_KE(c_rf, 250, c_a_.f935.f317.f915.frame, hashtable, c_i_a);
         c_ke.m1845(c_n_d);
         if (!m787(c_a_, c_hf, "derdlg013", hashtable, c_ke, c_n_d, null)) {
            return false;
         } else {
            String s2 = LogicProgram.m995(c_ke.getText(), f444, maggie);
            if (!c_j_d.m1882(c_i_a.toString(), s2)) {
               c_a_.m1622(c_j_d.f1190, c_j_d.f1191);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m790(C_a_ c_a_, C_HF c_hf) {
      if (c_a_.f935.f317.f915.serialMode) {
         c_a_.f935.f317.f915.complete = false;
         c_a_.m1621("dererr064");
         return false;
      } else {
         C_n_D c_n_d = new C_n_D(250);
         C_j_D c_j_d = c_hf.f393;
         C_RF c_rf = c_a_.m1628(-1);
         C_X c_x = (C_X)c_rf.m1217(0);
         C_X c_x1 = (C_X)c_rf.m1217(1);
         C_X c_x2 = (C_X)c_a_.f941.m1217(0);
         C_X c_x3 = (C_X)c_a_.f941.m1217(1);
         Hashtable hashtable = C_H.m669(
            "left premise term",
            "\\l" + c_x + "\\l",
            "left conclusion term",
            "\\l" + c_x2 + "\\l",
            "right premise term",
            "\\l" + c_x1 + "\\l",
            "right conclusion term",
            "\\l" + c_x3 + "\\l"
         );
         C_Y c_y = new C_Y(c_x2, 250, c_a_.f935.f317.f915.frame, hashtable);
         c_y.m1845(c_n_d);
         if (!m787(c_a_, c_hf, "derdlg015", hashtable, c_y, c_n_d, new C_X[]{c_x})) {
            return false;
         } else {
            C_i_A c_i_a = (C_i_A)c_j_d.f1189.elementAt(0);
            String s = LogicProgram.m995(c_y.getText(), f444, maggie);
            if (!c_j_d.m1882(c_i_a.toString(), s)) {
               c_a_.m1622(c_j_d.f1190, c_j_d.f1191);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m791(C_a_ c_a_, C_GA c_ga) {
      if (c_a_.f944 && c_a_.f941 != null && !c_ga.f345) {
         c_ga.f343 = c_a_.f941.m1231(c_a_.m1628(-1));
         if (c_ga.f345 = c_ga.f343 != null) {
            return true;
         }
      } else {
         c_ga.f345 = false;
      }

      if (c_a_.f935.f317.f915.serialMode) {
         c_a_.f935.f317.f915.complete = false;
         c_a_.m1621("dererr064");
         return false;
      } else {
         C_LB c_lb = new C_LB();
         C_H c_h = C_n_.m411("derdlg020");
         c_lb.add(C_q_B.m2031(C_n_.m1960(c_h.f372, null, c_a_)), "North");
         C_LB c_lb1 = new C_LB();
         c_lb1.m934(LogicProgram.f541.width * 3 / 4);
         c_lb1.m937(true);
         c_lb1.setBorder(new BevelBorder(1));
         c_lb.add(c_lb1, "Center");
         C_RF c_rf = c_a_.m1628(-1);
         String s = LPDerivation.officialIE ? c_rf.m1209(1) : c_rf.m1207(1);
         C_IF c_if = new C_IF(LogicProgram.m995(s, maggie, f444), c_a_.f935.f317.f915.frame);
         c_if.setEditable(false);
         c_if.getCaret().setVisible(true);
         c_if.m1787(true);
         c_if.m1789(true);
         c_lb1.add(c_if, "North");
         C_y_ c_y_ = new C_y_(c_a_.f935, c_h.f373);
         c_y_.m447("just", c_a_);
         c_y_.m447("edit", c_if);
         C_UA c_ua = new C_UA(c_a_.f935.f317.f915.frame, "Line " + c_a_.f935.m30(), c_lb, c_y_.f272);
         c_if.f425 = c_ua;
         c_if.m715(c_ua);
         Rectangle rectangle = LogicProgram.m1035(c_a_.f935.f324, null);
         c_ua.m1314(0);
         c_ua.m1315(c_y_);
         c_ua.pack();
         c_ua.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         if (c_ua.f790 != 0) {
            c_a_.m1621("dererr028");
            c_a_.f935.f317.f915.abort(true);
            return false;
         } else {
            int[] aint = new int[]{c_if.getSelectionStart(), c_if.getSelectionEnd()};
            String s1 = c_if.getText();
            s1 = LogicProgram.m996(s1, f444, maggie, aint);
            C_DD c_dd = new C_DD(s1);
            C_DD c_dd1 = c_dd.m480(aint[0], aint[1]);
            c_ga.f343 = c_dd1.m473();
            return true;
         }
      }
   }

   static boolean m792(C_a_ c_a_, C_GA c_ga) {
      if (c_a_.f935.f317.f915.serialMode && c_a_.f956 == null) {
         c_a_.f935.f317.f915.complete = false;
         c_a_.m1621("dererr064");
         return false;
      } else {
         C_LB c_lb = new C_LB();
         C_H c_h = C_n_.m411("derdlg021");
         c_lb.add(C_q_B.m2031(C_n_.m1960(c_h.f372, null, c_a_)), "North");
         C_LB c_lb1 = new C_LB();
         c_lb1.setBorder(new BevelBorder(1));
         c_lb.add(c_lb1);
         C_p_A c_p_a = new C_p_A();
         c_lb1.add(c_p_a, "Center");
         C_y_ c_y_ = new C_y_(c_a_.f935, c_h.f373);
         c_y_.m447("just", c_a_);
         c_y_.m447("edit", c_p_a);
         C_UA c_ua = new C_UA(c_a_.f935.f317.f915.frame, "Line " + c_a_.f935.m30(), c_lb, c_y_.f272);
         Rectangle rectangle = LogicProgram.m1035(c_a_.f935.f324, null);
         c_ua.m1314(0);
         c_ua.m1315(c_y_);
         if (!c_ua.m1327(c_a_.f956, new C_p_A[]{c_p_a}, 0)) {
            if (c_a_.f935.f317.f915.serialMode) {
               c_a_.f935.f317.f915.complete = false;
               c_a_.m1621("dererr064");
               c_ua.dispose();
               return false;
            }

            c_ua.pack();
            c_ua.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            c_ua.dispose();
         }

         if (c_ua.f790 != 0) {
            c_a_.m1621("dererr028");
            c_a_.f935.f317.f915.abort(true);
            return false;
         } else {
            return m793(c_a_, c_ga, (C_VB)c_y_.m449("rule"));
         }
      }
   }

   static boolean m793(C_a_ c_a_, C_GA c_ga, C_VB c_vb) {
      return m794(c_a_, c_ga, c_vb, null);
   }

   static boolean m794(C_a_ c_a_, C_GA c_ga, C_VB c_vb, C_LF c_lf) {
      boolean flag = !C_GA.m601(c_a_, c_ga.f343, c_lf);
      Vector vector = C_GA.m626(c_a_, c_ga.f343, c_vb, c_lf);
      boolean flag1 = vector == null;
      int i = !flag && !flag1 ? vector.size() : 0;
      if (i == 0) {
         if (c_ga.f345) {
            while (c_ga.f343.f1062 > 0) {
               c_ga.f343.f1062--;
               flag = !C_GA.m601(c_a_, c_ga.f343, c_lf);
               vector = C_GA.m626(c_a_, c_ga.f343, c_vb, c_lf);
               if (!flag && vector != null) {
                  flag1 = false;
                  i = vector.size();
               } else {
                  i = 0;
               }

               if (i != 0) {
                  break;
               }
            }
         }

         if (i == 0) {
            Hashtable hashtable = new Hashtable();
            C_H.m664(hashtable, "inner rule", c_vb.f820);
            C_H.m664(hashtable, "inner exp", "\\l" + c_a_.m1628(-1).m1220(c_ga.f343) + "\\l");
            if (c_lf != null) {
               C_H.m664(hashtable, "condition name", c_lf.f820);
            }

            String s;
            if (flag) {
               s = "dererr099";
            } else if (flag1) {
               s = c_lf == null ? "dererr085" : "dererr095";
            } else {
               s = c_lf == null ? "dererr096" : "dererr097";
            }

            c_a_.m1622(s, hashtable);
            return false;
         }
      }

      int j = c_a_.f941 == null ? m780(c_a_, vector) : 0;
      if (j == -1) {
         if (c_a_.f935.f317.f915.serialMode) {
            c_a_.f935.f317.f915.complete = false;
            c_a_.m1621("dererr064");
         } else {
            c_a_.m1621("dererr028");
            c_a_.f935.f317.f915.abort(true);
         }

         return false;
      } else {
         C_GA c_ga1 = (C_GA)vector.elementAt(j);
         if (c_ga1.f346 != null) {
            if (!m782(c_a_, c_ga1)) {
               return false;
            }

            if (c_ga1.m620(c_a_) == null) {
               return false;
            }
         }

         c_ga.f344 = c_ga1.f344;
         c_ga.f346 = c_ga1.f346;
         c_ga.f347 = c_ga1.f347;
         c_ga.f348 = c_ga1.f348;
         c_ga.f349 = c_ga1.f349;
         c_ga.f350 = c_ga1.f350;
         c_ga.f351 = c_ga1.f351;
         c_ga.f352 = c_ga1.f352;
         c_ga.f353 = c_ga1.f353;
         c_ga.f354 = c_ga1.f354;
         return true;
      }
   }

   static boolean m795(C_a_ c_a_, C_GA c_ga) {
      if (c_a_.f935.f317.f915.serialMode && c_a_.f956 == null) {
         c_a_.f935.f317.f915.complete = false;
         c_a_.m1621("dererr064");
         return false;
      } else {
         C_LB c_lb = new C_LB();
         C_LB c_lb1 = new C_LB();
         C_H c_h = C_n_.m411("derdlg022");
         c_lb1.add(C_q_B.m2031(C_n_.m1960(c_h.f372, null, c_a_)), "North");
         C_LB c_lb2 = new C_LB();
         c_lb2.setBorder(new BevelBorder(1));
         c_lb1.add(c_lb2, "Center");
         C_p_A c_p_a = new C_p_A();
         c_lb2.add(c_p_a, "Center");
         c_lb.add(c_lb1, "North");
         C_LB c_lb3 = new C_LB();
         C_H c_h1 = C_n_.m411("derdlg023");
         c_lb3.add(C_q_B.m2031(C_n_.m1960(c_h1.f372, null, c_a_)), "North");
         C_LB c_lb4 = new C_LB();
         c_lb4.setBorder(new BevelBorder(1));
         c_lb3.add(c_lb4, "Center");
         C_p_A c_p_a1 = new C_p_A();
         c_lb4.add(c_p_a1, "Center");
         c_lb.add(c_lb3, "South");
         C_y_ c_y_ = new C_y_(c_a_.f935, c_h.f373);
         c_y_.m447("just", c_a_);
         c_y_.m447("ruleEdit", c_p_a);
         c_y_.m447("condEdit", c_p_a1);
         C_UA c_ua = new C_UA(c_a_.f935.f317.f915.frame, "Line " + c_a_.f935.m30(), c_lb, c_y_.f272);
         Rectangle rectangle = LogicProgram.m1035(c_a_.f935.f324, null);
         c_ua.m1314(0);
         c_ua.m1315(c_y_);
         if (!c_ua.m1327(c_a_.f956, new C_p_A[]{c_p_a, c_p_a1}, 0)) {
            if (c_a_.f935.f317.f915.serialMode) {
               c_a_.f935.f317.f915.complete = false;
               c_a_.m1621("dererr064");
               c_ua.dispose();
               return false;
            }

            c_ua.pack();
            c_ua.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            c_ua.dispose();
         }

         if (c_ua.f790 != 0) {
            c_a_.m1621("dererr028");
            c_a_.f935.f317.f915.abort(true);
            return false;
         } else {
            return m794(c_a_, c_ga, (C_VB)c_y_.m449("rule"), (C_LF)c_y_.m449("condition"));
         }
      }
   }

   static String m796(String s) {
      if (s == null) {
         s = "User";
      }

      C_M c_m = new C_M();
      C_p_A c_p_a = new C_p_A(s, 300);
      C_ZE c_ze = new C_ZE("Please supply a name for this problem");
      C_0E c_0e = new C_0E();
      c_p_a.select(0, 2147483647);
      c_ze.setFocusable(false);
      c_m.setLayout(new C_m_A(0));
      c_m.add(c_ze);
      c_m.add(c_p_a);
      String[] astring = new String[]{"OK", "Cancel"};
      C_UA c_ua = new C_UA(c_0e, "", c_m, astring);
      c_ua.m1314(0);
      c_p_a.requestFocus();
      c_ua.m1322(null);
      c_0e.dispose();
      if (c_ua.f790 != 0) {
         return null;
      } else {
         s = c_p_a.getText();
         if ((s = s.trim()).equals("")) {
            C_H c_h = C_H.m411("not006");
            C_UA.m1329(C_H.m411("not006"), null, null, null);
            return null;
         } else if (LPDerivation.problems.m1780(s) != null) {
            LogicProgram.m972("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static void m797(LPDerivation lpderivation) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      C_JE c_je = C_JE.m726(LogicProgram.m1062("tips", false), 20);
      if (c_je == null) {
         LogicProgram.m971("not001", "the derivation advice file");
      } else {
         JScrollPane jscrollpane = new JScrollPane();
         C_JB c_jb = new C_JB(jscrollpane, c_je);
         jscrollpane.setHorizontalScrollBarPolicy(31);
         jscrollpane.setViewportView(c_jb);
         c_jb.add(c_je);
         c_jb.setBackground(dialogWhite);
         jpanel.add(jscrollpane, "Center");
         C_UA c_ua = new C_UA(lpderivation.frame, "Strategic Advice", jpanel, new String[]{"OK", "Print"});
         Container container = c_ua.getParent();

         while (container != null && !(container instanceof Window)) {
            container = container.getParent();
         }

         if (container != null) {
            container.addComponentListener(c_jb);
         }

         Dimension dimension = new Dimension(40 * LogicProgram.f539, 24 * LogicProgram.f539);
         c_ua.setSize(dimension);
         c_ua.m1323(C_UA.m1321(dimension), true);
         if (c_ua.f790 == 1) {
            C_v_B.m2130(new C_XD(LogicProgram.m1062("tips", false)), LPDerivation.printQueue);
         }

         lpderivation.requestFocus();
      }
   }

   static void m798(LPDerivation lpderivation, C_x_A c_x_a) {
      if (lpderivation == null && LPDerivation.ruleQuery != null) {
         LPDerivation.ruleQuery.requestFocus();
      } else {
         if (c_x_a != null) {
            c_x_a.m2162(true);
         }

         JPanel jpanel = new JPanel();
         jpanel.setLayout(new BorderLayout());
         C_YA c_ya = new C_YA(false);
         c_ya.setEnabled(false);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(c_ya);
         jpanel.add(jscrollpane, "Center");
         Object object;
         if (lpderivation == null) {
            object = new C_d_F(new C_0E(), "Inference Rules", jpanel, new String[]{"OK"});
            LPDerivation.ruleQuery = (C_d_F)object;
         } else {
            object = new C_UA(lpderivation.frame, "Available Inference Rules", jpanel, new String[]{"OK"});
         }

         c_ya.f898 = 0;
         int[] aint = new int[3];
         if (LPDerivation.userRules != null && LPDerivation.userRules.size() > 0) {
            C_H c_h = C_n_.m411("dertxt001");
            if (c_h != null) {
               String[] astring = LogicProgram.m1003(LogicProgram.m1004(c_h.f372));
               int i = astring.length;

               for (int j = 0; j < i; j++) {
                  C_ZE c_ze = new C_ZE(astring[j], 2);
                  c_ze.setForeground(dialogBlue);
                  c_ya.m1526(c_ze);
               }
            }

            Vector vector;
            if (lpderivation == null) {
               vector = LogicProgram.f534.f1472.m2071(LPDerivation.userRules);
            } else {
               vector = lpderivation.enabledRules(LPDerivation.userRules);
            }

            m799(c_ya, aint, LPDerivation.userRules, vector);
         }

         Vector vector1;
         if (lpderivation == null) {
            vector1 = LogicProgram.f534.f1472.m2071(LogicProgram.f534);
         } else {
            vector1 = lpderivation.enabledRules(LogicProgram.f534);
         }

         m799(c_ya, aint, LogicProgram.f534, vector1);
         C_n_F c_n_f;
         if (lpderivation == null) {
            c_n_f = LogicProgram.f534.f1472.m2072(LogicProgram.f534.f1468);
         } else {
            c_n_f = lpderivation.enabledTheorems(LogicProgram.f534.f1468);
         }

         m800(c_ya, aint, LogicProgram.f534.f1468, c_n_f);
         int k = c_ya.getComponentCount();
         C_u_ c_u_ = new C_u_(c_ya, aint.length, aint);

         for (int l = 0; l < k; l++) {
            Component component = c_ya.getComponent(l);
            if (component instanceof Container) {
               ((Container)component).setLayout(c_u_);
            }
         }

         C_TA c_ta = new C_TA();
         c_ta.setLayout(new BoxLayout(c_ta, 2));
         C_E c_e = new C_E(true);
         c_ta.add(c_e);
         C_ZE c_ze1;
         if (lpderivation == null) {
            c_ze1 = new C_ZE("usable with Interchange of Equivalents");
         } else {
            c_ze1 = new C_ZE("available for this derivation");
         }

         c_ta.add(c_ze1);
         jpanel.add(c_ta, "South");
         if (c_x_a != null) {
            c_x_a.m2162(false);
         }

         Dimension dimension = new Dimension(22 * LogicProgram.f539, 26 * LogicProgram.f539);
         ((C_UA)object).setSize(dimension);
         ((C_UA)object).m1317("derRules");
         if (lpderivation == null) {
            ((C_UA)object).m1324(C_UA.m1321(dimension), true);
         } else {
            ((C_UA)object).m1323(C_UA.m1321(dimension), true);
            lpderivation.requestFocus();
         }
      }
   }

   static void m799(C_YA c_ya, int[] aint, C_z_B c_z_b, Vector vector) {
      Enumeration enumeration = c_z_b.f1469.elements();

      while (enumeration.hasMoreElements()) {
         String s = (String)enumeration.nextElement();
         Vector vector1 = (Vector)c_z_b.f1471.get(s);
         C_E c_e = null;
         if (vector1 != null) {
            int i = vector1.size();

            for (int j = 0; j < i; j++) {
               C_ZE c_ze = new C_ZE(LogicProgram.m1004((String)vector1.elementAt(j)), 2);
               c_ze.setForeground(dialogBlue);
               c_ya.m1526(c_ze);
            }
         }

         C_TA c_ta = new C_TA();
         c_ta.setLayout(new FlowLayout(0, 0, 0));
         int k = aint.length;
         if (k == 3) {
            c_e = new C_E(vector.contains(s));
            c_ta.add(m801(c_e));
         }

         C_ZE c_ze1;
         c_ta.add(m801(c_ze1 = new C_ZE(s)));
         c_ze1.setPreferredSize(new Dimension(6 * LogicProgram.f539, c_ze1.getPreferredSize().height));
         c_ze1.setHorizontalAlignment(2);
         C_VB c_vb = LPDerivation.getRule(s);
         C_ZE c_ze2;
         c_ta.add(m801(c_ze2 = LogicProgram.m992(c_vb == null ? "" : c_vb.m958(" . ", " .: "))));
         c_ya.m1526(c_ta);
         int i1 = LogicProgram.f539 * 5 / 14;
         int l;
         if (k == 3 && (l = c_e.getPreferredSize().width) > aint[k - 3]) {
            aint[k - 3] = l;
         }

         if ((l = c_ze1.getPreferredSize().width + i1) > aint[k - 2]) {
            aint[k - 2] = l;
         }

         if ((l = c_ze2.getPreferredSize().width) > aint[k - 1]) {
            aint[k - 1] = l;
         }
      }
   }

   static void m800(C_YA c_ya, int[] aint, C_z_ c_z_, C_n_F c_n_f) {
      Enumeration enumeration = c_z_.f1464.m1985();

      while (enumeration.hasMoreElements()) {
         Integer integer = (Integer)enumeration.nextElement();
         Vector vector = (Vector)c_z_.f1465.get(integer);
         C_E c_e = null;
         if (vector != null) {
            int i = vector.size();

            for (int j = 0; j < i; j++) {
               C_ZE c_ze = new C_ZE(LogicProgram.m1004((String)vector.elementAt(j)), 2);
               c_ze.setForeground(dialogBlue);
               c_ya.m1526(c_ze);
            }
         }

         C_TA c_ta = new C_TA();
         c_ta.setLayout(new FlowLayout(0, 0, 0));
         int k = aint.length;
         if (k == 3) {
            c_e = new C_E(c_n_f.m1983(integer));
            c_ta.add(m801(c_e));
         }

         C_ZE c_ze1;
         c_ta.add(m801(c_ze1 = new C_ZE("T" + integer)));
         C_QE c_qe = LogicProgram.m1025(integer);
         C_ZE c_ze2;
         c_ta.add(m801(c_ze2 = LogicProgram.m992(c_qe == null ? "" : c_qe.toString())));
         c_ya.m1526(c_ta);
         int i1 = LogicProgram.f539 * 5 / 14;
         int l;
         if (k == 3 && (l = c_e.getPreferredSize().width) > aint[k - 3]) {
            aint[k - 3] = l;
         }

         if ((l = c_ze1.getPreferredSize().width + i1) > aint[k - 2]) {
            aint[k - 2] = l;
         }

         if ((l = c_ze2.getPreferredSize().width) > aint[k - 1]) {
            aint[k - 1] = l;
         }
      }
   }

   static C_TA m801(Component component) {
      C_TA c_ta = new C_TA(false);
      c_ta.setLayout(new FlowLayout(0, 0, 0));
      c_ta.add(component);
      return c_ta;
   }
}
