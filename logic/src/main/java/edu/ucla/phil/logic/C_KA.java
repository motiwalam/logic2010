package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

class C_KA implements C_n_A {
   static String[] f443 = LogicProgram.f596;

   static void m742(String s) {
      m745(s, null, null, null);
   }

   static void m743(String s, Point point) {
      m745(s, null, null, point);
   }

   static void m744(String s, Hashtable hashtable) {
      m745(s, hashtable, null, null);
   }

   static void m745(String s, Hashtable hashtable, Hashtable hashtable1, Point point) {
      C_H c_h = C_ND.m411(s);
      C_QA c_qa = null;
      if (c_h.f373 != null) {
         c_qa = new C_QA(c_h.f373);
         if (hashtable1 != null) {
            c_qa.m448(hashtable1);
         }
      }

      C_UA.m1329(c_h, hashtable, point, c_qa);
   }

   static boolean m746(LPParsing lpparsing, Point point) {
      if (lpparsing.problem.f1456.m1809() != -1) {
         C_z_E c_z_e = new C_z_E();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            c_z_e.add(ajradiobutton[j] = new C_NF(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(lpparsing.frame, "", c_z_e, astring2);
         c_ua.m1322(null);
         if (c_ua.f790 != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            lpparsing.problem.m2189();
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lpparsing.problemIndex != -1 || lpparsing.problem.f1459 != null && !lpparsing.problem.f1459.equals("")) {
         C_ZE c_ze = new C_ZE("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         C_UA c_ua1 = new C_UA(lpparsing.frame, "", c_ze, astring1);
         c_ua1.m1322(null);
         if (c_ua1.f790 != 0) {
            return false;
         }
      }

      if (lpparsing.problemIndex != -1) {
         LPParsing.problems.m1101(lpparsing.problemIndex);
         LPParsing.saveProblems();
      }

      lpparsing.newProblem();
      return true;
   }

   static boolean m747(LPParsing lpparsing, Point point) {
      C_ZE c_ze = new C_ZE("Delete the work on this problem?");
      String[] astring = new String[]{"OK", "Cancel"};
      C_UA c_ua = new C_UA(lpparsing.frame, "", c_ze, astring);
      c_ua.m1322(point);
      if (c_ua.f790 != 0) {
         return false;
      } else {
         lpparsing.removeWork();
         return true;
      }
   }

   static boolean m748(LPParsing lpparsing) {
      if (m756(lpparsing, null)) {
         synchronized (LPParsing.problems) {
            int i = lpparsing.problemIndex + 1;
            if (i != 0 && LPParsing.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPParsing.problems.m1778(i);
            if (s == null) {
               return m749(lpparsing);
            }

            lpparsing.loadProblem(s);
            lpparsing.problemIndex = i;
            LPParsing.problems.m1776(lpparsing.saveProblem(), i);
            if (C_U.eraseWork) {
               lpparsing.removeWork();
            }
         }
      }

      lpparsing.requestFocus();
      return true;
   }

   static boolean m749(LPParsing lpparsing) {
      boolean flag = false;
      if (m756(lpparsing, null)) {
         synchronized (LPParsing.problems) {
            C_YA c_ya = m755(lpparsing, false, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"OK", "Cancel"};
            C_UA c_ua = new C_UA(lpparsing.frame, C_e_D.m1782("Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("parChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               int i = c_ya.m1532(c_ya.f899);
               LPParsing lpparsing1;
               if ((lpparsing1 = LPParsing.openInstance(i, true)) != null) {
                  lpparsing1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lpparsing.loadProblem(LPParsing.problems.m1778(i));
                  lpparsing.problemIndex = i;
                  LPParsing.problems.m1776(lpparsing.saveProblem(), i);
                  if (C_U.eraseWork) {
                     lpparsing.removeWork();
                  }

                  flag = true;
               }
            }
         }
      }

      lpparsing.requestFocus();
      return flag;
   }

   static int[] m750(LPParsing lpparsing) {
      int[] aint = null;
      if (m756(lpparsing, null)) {
         synchronized (LPParsing.problems) {
            C_YA c_ya = m755(lpparsing, true, false, null);
            if (LPParsing.submitExam) {
               c_ya.clearSelection();
            }

            Vector vector = LPParsing.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = C_XD.m1493(s);
                  int j = LPParsing.problems.m1767(s1);
                  if (j != -1) {
                     c_ya.m1531(j, c_ya.f899);
                  }
               }
            }

            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"Submit", "Cancel"};
            C_UA c_ua = new C_UA(lpparsing.frame, C_e_D.m1782("Submit Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("parChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            }
         }
      }

      lpparsing.requestFocus();
      return aint;
   }

   static int[] m751(LPParsing lpparsing) {
      int[] aint = null;
      if (m756(lpparsing, null)) {
         synchronized (LPParsing.problems) {
            C_YA c_ya = m755(lpparsing, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            C_LB c_lb = new C_LB();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(C_H.m412("not089")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            c_lb.add(c_s_b, "North");
            c_lb.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            C_UA c_ua = new C_UA(lpparsing.frame, C_e_D.m1782("Upload Problems"), c_lb, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("parChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            }
         }
      }

      lpparsing.requestFocus();
      return aint;
   }

   static void m752(LPParsing lpparsing) {
      synchronized (LPParsing.problems) {
         C_YA c_ya = m755(lpparsing, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(c_ya);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         C_UA c_ua = new C_UA(lpparsing.frame, C_e_D.m1782("Delete Problems"), jscrollpane, astring);
         c_ya.m1528(c_ua, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
         c_ua.setSize(dimension);
         c_ua.m1317("parChosen");
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
                     C_l_D c_l_d1 = (C_l_D)LPParsing.problems.m1779(l);
                     if (c_l_d1 != null) {
                        C_XD c_xd1 = new C_XD(c_l_d1.f1119);
                        if (!LPParsing.isExample(c_xd1.m1494())) {
                           c_l_d1.f1119 = LPParsing.removeWork(c_xd1);
                           c_l_d1.f1120 = 0;
                           LPParsing lpparsing2 = LPParsing.openInstance(l, false);
                           if (lpparsing2 != null) {
                              lpparsing2.loadProblem(c_l_d1.f1119);
                           }
                        }
                     }
                  }
               }

               LPParsing.saveProblems();
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
                     C_l_D c_l_d = (C_l_D)LPParsing.problems.m1779(j);
                     if (c_l_d != null) {
                        C_XD c_xd = new C_XD(c_l_d.f1119);
                        if (!LPParsing.isExercise(c_xd.m1494())) {
                           LPParsing lpparsing1 = LPParsing.openInstance(j, false);
                           LPParsing.problems.m1101(j);
                           if (lpparsing1 != null) {
                              lpparsing1.newProblem();
                           }
                        }
                     }
                  }
               }

               LPParsing.saveProblems();
            }
         }
      }

      lpparsing.requestFocus();
   }

   static int[] m753(LPParsing lpparsing) {
      Object object = null;
      if (m756(lpparsing, null)) {
         synchronized (LPParsing.problems) {
            C_YA c_ya = m755(lpparsing, true, false, LPParsing.noPrint);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"Print Results", "Print List", "Cancel"};
            c_ya.f898 = 2;
            C_UA c_ua = new C_UA(lpparsing.frame, C_e_D.m1782("Print Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("parChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               C_o_E.m2009(c_ya.m1533(c_ya.f899));
            } else if (c_ua.f790 == 1) {
               C_AE.m234(c_ya.m1533(c_ya.f899));
            }
         }
      }

      lpparsing.requestFocus();
      return (int[])object;
   }

   static void m754(LPParsing lpparsing) {
      if (m756(lpparsing, null)) {
         C_0E c_0e = new C_0E();
         Dimension dimension = new Dimension(20 * LogicProgram.f539, 10 * LogicProgram.f539);
         Point point = C_UA.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         C_p_A c_p_a = new C_p_A();
         String[] astring = new String[]{"OK", "Cancel"};
         c_p_a.setBackground(Color.WHITE);
         jscrollpane.setViewportView(c_p_a);
         C_UA c_ua = new C_UA(c_0e, "Submitted Problem", jscrollpane, astring);
         c_ua.setSize(dimension);
         c_ua.m1314(0);
         c_ua.m1317("parSubmitted");
         c_p_a.requestFocus();
         c_ua.m1323(point, true);
         c_0e.dispose();
         if (c_ua.f790 == 0) {
            lpparsing.loadProblem(c_p_a.getText());
         }
      }

      lpparsing.requestFocus();
   }

   static C_YA m755(LPParsing lpparsing, boolean flag, boolean flag1, C_BE c_be) {
      return LPParsing.problems.m1781(lpparsing, LPParsing.exercises, flag, flag1, LPParsing.monoProbs, c_be);
   }

   static boolean m756(LPParsing lpparsing, Point point) {
      String s = lpparsing.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lpparsing.frame.show();
         C_ZE c_ze = new C_ZE("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         C_UA c_ua = new C_UA(lpparsing.frame, "", c_ze, astring);
         c_ua.m1322(point);
         return c_ua.f790 == 0 && !lpparsing.saveProblems(s) ? false : c_ua.f790 == 0 || c_ua.f790 == 1;
      }
   }

   static String m757(String s) {
      if (s == null) {
         s = "User";
      }

      C_LB c_lb = new C_LB();
      C_p_A c_p_a = new C_p_A(s, 300);
      C_ZE c_ze = new C_ZE("Please supply a name for this problem");
      C_0E c_0e = new C_0E();
      c_p_a.select(0, 2147483647);
      c_ze.setFocusable(false);
      c_lb.setLayout(new C_m_A(0));
      c_lb.add(c_ze);
      c_lb.add(c_p_a);
      String[] astring = new String[]{"OK", "Cancel"};
      C_UA c_ua = new C_UA(c_0e, "", c_lb, astring);
      c_ua.m1314(0);
      c_p_a.requestFocus();
      c_ua.m1322(null);
      c_0e.dispose();
      if (c_ua.f790 != 0) {
         return null;
      } else {
         s = c_p_a.getText();
         if ((s = s.trim()).equals("")) {
            C_UA.m1329(C_H.m411("not006"), null, null, null);
            return null;
         } else if (LPParsing.problems.m1780(s) != null) {
            LogicProgram.m972("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static void m758(LPParsing lpparsing) {
      if (m756(lpparsing, null)) {
         C_0E c_0e = new C_0E();
         Dimension dimension = new Dimension(20 * LogicProgram.f539, 10 * LogicProgram.f539);
         Point point = C_UA.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         C_IF c_if = new C_IF(c_0e);
         if (lpparsing.lastUserProblem != null) {
            c_if.setText(LogicProgram.m995(lpparsing.lastUserProblem, maggie, f443));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(c_if);
         c_if.setBackground(Color.WHITE);
         C_UA c_ua = new C_UA(c_0e, "User Problem", jscrollpane, astring);
         c_ua.setSize(dimension);
         c_ua.m1314(0);
         c_ua.m1317("parUser");
         c_if.f425 = c_ua;
         c_if.requestFocus();
         c_ua.m1323(point, true);
         c_0e.dispose();
         if (c_ua.f790 == 0) {
            String s = c_if.getText();
            if (!LPParsing.validateUserProblem(s)) {
               return;
            }

            lpparsing.lastUserProblem = LogicProgram.m995(s, f443, maggie);
            lpparsing.loadUserProblem(lpparsing.lastUserProblem);
         }
      }

      lpparsing.requestFocus();
   }
}
