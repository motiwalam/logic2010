package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

class C_EC implements C_n_A {
   static String[] f295 = LogicProgram.f596;

   static void m496(String s) {
      m499(s, null, null, null);
   }

   static void m497(String s, Point point) {
      m499(s, null, null, point);
   }

   static void m498(String s, Hashtable hashtable) {
      m499(s, hashtable, null, null);
   }

   static void m499(String s, Hashtable hashtable, Hashtable hashtable1, Point point) {
      C_H c_h = C_BF.m411(s);
      C_i_E c_i_e = null;
      if (c_h.f373 != null) {
         c_i_e = new C_i_E(c_h.f373);
         if (hashtable1 != null) {
            c_i_e.m448(hashtable1);
         }
      }

      C_UA.m1329(c_h, hashtable, point, c_i_e);
   }

   static String m500(String s) {
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
         } else if (LPRecognition.problems.m1780(s) != null) {
            LogicProgram.m972("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static boolean m501(LPRecognition lprecognition, Point point) {
      String s = lprecognition.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lprecognition.frame.setVisible(true);
         C_ZE c_ze = new C_ZE("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         C_UA c_ua = new C_UA(lprecognition.frame, "", c_ze, astring);
         c_ua.m1322(point);
         return c_ua.f790 == 0 && !lprecognition.saveProblems(s) ? false : c_ua.f790 == 0 || c_ua.f790 == 1;
      }
   }

   static boolean m502(LPRecognition lprecognition, Point point) {
      if (lprecognition.problem.f128 == null) {
         return false;
      } else {
         C_ZE c_ze = new C_ZE("Delete the work on this problem?");
         String[] astring = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(lprecognition.frame, "", c_ze, astring);
         c_ua.m1322(point);
         if (c_ua.f790 != 0) {
            return false;
         } else {
            lprecognition.removeWork();
            return true;
         }
      }
   }

   static boolean m503(LPRecognition lprecognition, Point point) {
      if (lprecognition.problem.f128 != null) {
         C_z_E c_z_e = new C_z_E();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            c_z_e.add(ajradiobutton[j] = new C_NF(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(lprecognition.frame, "", c_z_e, astring2);
         c_ua.m1322(null);
         if (c_ua.f790 != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            lprecognition.problem.m219();
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lprecognition.problemIndex != -1 || lprecognition.problem.f124 != null && !lprecognition.problem.f124.equals("")) {
         C_ZE c_ze = new C_ZE("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         C_UA c_ua1 = new C_UA(lprecognition.frame, "", c_ze, astring1);
         c_ua1.m1322(null);
         if (c_ua1.f790 != 0) {
            return false;
         }
      }

      if (lprecognition.problemIndex != -1) {
         LPRecognition.problems.m1101(lprecognition.problemIndex);
         LPRecognition.saveProblems();
      }

      lprecognition.newProblem();
      return true;
   }

   static boolean m504(LPRecognition lprecognition) {
      if (m501(lprecognition, null)) {
         synchronized (LPRecognition.problems) {
            int i = lprecognition.problemIndex + 1;
            if (i != 0 && LPRecognition.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPRecognition.problems.m1778(i);
            if (s == null) {
               return m505(lprecognition);
            }

            lprecognition.loadProblem(s);
            lprecognition.problemIndex = i;
            LPRecognition.problems.m1776(lprecognition.saveProblem(), i);
            if (C_U.eraseWork) {
               lprecognition.removeWork();
            }
         }
      }

      lprecognition.requestFocus();
      return true;
   }

   static boolean m505(LPRecognition lprecognition) {
      boolean flag = false;
      if (m501(lprecognition, null)) {
         synchronized (LPRecognition.problems) {
            C_YA c_ya = m512(lprecognition, false, false, null);
            c_ya.setBackground(C_n_A.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"OK", "Cancel"};
            C_UA c_ua = new C_UA(lprecognition.frame, C_e_D.m1782("Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("recChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               int i = c_ya.m1532(c_ya.f899);
               LPRecognition lprecognition1;
               if ((lprecognition1 = LPRecognition.openInstance(i, true)) != null) {
                  lprecognition1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lprecognition.loadProblem(LPRecognition.problems.m1778(i));
                  lprecognition.problemIndex = i;
                  LPRecognition.problems.m1776(lprecognition.saveProblem(), i);
                  if (C_U.eraseWork) {
                     lprecognition.removeWork();
                  }

                  flag = true;
               }
            }
         }
      }

      lprecognition.requestFocus();
      return flag;
   }

   static int[] m506(LPRecognition lprecognition) {
      int[] aint = null;
      if (m501(lprecognition, null)) {
         synchronized (LPRecognition.problems) {
            C_YA c_ya = m512(lprecognition, true, false, null);
            if (LPRecognition.submitExam) {
               c_ya.clearSelection();
            }

            Vector vector = LPRecognition.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = C_XD.m1493(s);
                  int j = LPRecognition.problems.m1767(s1);
                  if (j != -1) {
                     c_ya.m1531(j, c_ya.f899);
                  }
               }
            }

            c_ya.setBackground(C_n_A.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"Submit", "Cancel"};
            C_UA c_ua = new C_UA(lprecognition.frame, C_e_D.m1782("Submit Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("recChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            }
         }
      }

      lprecognition.requestFocus();
      return aint;
   }

   static void m507(LPRecognition lprecognition) {
      if (m501(lprecognition, null)) {
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
         c_ua.m1317("recSubmitted");
         c_p_a.requestFocus();
         c_ua.m1323(point, true);
         c_0e.dispose();
         if (c_ua.f790 == 0) {
            lprecognition.loadProblem(c_p_a.getText());
         }
      }

      lprecognition.requestFocus();
   }

   static int[] m508(LPRecognition lprecognition) {
      int[] aint = null;
      if (m501(lprecognition, null)) {
         synchronized (LPRecognition.problems) {
            C_YA c_ya = m512(lprecognition, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            C_LB c_lb = new C_LB();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(C_H.m412("not089")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            c_lb.add(c_s_b, "North");
            c_lb.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            C_UA c_ua = new C_UA(lprecognition.frame, C_e_D.m1782("Upload Problems"), c_lb, astring);
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

      lprecognition.requestFocus();
      return aint;
   }

   static void m509(LPRecognition lprecognition) {
      synchronized (LPRecognition.problems) {
         C_YA c_ya = m512(lprecognition, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(c_ya);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         C_UA c_ua = new C_UA(lprecognition.frame, C_e_D.m1782("Delete Problems"), jscrollpane, astring);
         c_ya.m1528(c_ua, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
         c_ua.setSize(dimension);
         c_ua.m1317("recChosen");
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
                     C_ED c_ed1 = (C_ED)LPRecognition.problems.m1779(l);
                     if (c_ed1 != null) {
                        C_XD c_xd1 = new C_XD(c_ed1.f1119);
                        if (!LPRecognition.isExample(c_xd1.m1494())) {
                           c_ed1.f1119 = LPRecognition.removeWork(c_xd1);
                           c_ed1.f1120 = 0;
                           LPRecognition lprecognition2 = LPRecognition.openInstance(l, false);
                           if (lprecognition2 != null) {
                              lprecognition2.loadProblem(c_ed1.f1119);
                           }
                        }
                     }
                  }
               }

               LPRecognition.saveProblems();
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
                     C_ED c_ed = (C_ED)LPRecognition.problems.m1779(j);
                     if (c_ed != null) {
                        C_XD c_xd = new C_XD(c_ed.f1119);
                        if (!LPRecognition.isExercise(c_xd.m1494())) {
                           LPRecognition lprecognition1 = LPRecognition.openInstance(j, false);
                           LPRecognition.problems.m1101(j);
                           if (lprecognition1 != null) {
                              lprecognition1.newProblem();
                           }
                        }
                     }
                  }
               }

               LPRecognition.saveProblems();
            }
         }
      }

      lprecognition.requestFocus();
   }

   static void m510(LPRecognition lprecognition) {
      if (m501(lprecognition, null)) {
         C_0E c_0e = new C_0E();
         Dimension dimension = new Dimension(20 * LogicProgram.f539, 10 * LogicProgram.f539);
         Point point = C_UA.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         C_IF c_if = new C_IF(c_0e);
         if (lprecognition.lastUserProblem != null) {
            c_if.setText(LogicProgram.m995(lprecognition.lastUserProblem, maggie, f295));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(c_if);
         c_if.setBackground(Color.WHITE);
         C_UA c_ua = new C_UA(c_0e, "User Problem", jscrollpane, astring);
         c_ua.setSize(dimension);
         c_ua.m1314(0);
         c_ua.m1317("recUser");
         c_if.f425 = c_ua;
         c_if.requestFocus();
         c_ua.m1323(point, true);
         c_0e.dispose();
         if (c_ua.f790 == 0) {
            String s = c_if.getText();
            if (!LPRecognition.validateUserProblem(s)) {
               return;
            }

            lprecognition.lastUserProblem = LogicProgram.m995(s, f295, maggie);
            lprecognition.loadUserProblem(lprecognition.lastUserProblem);
         }
      }

      lprecognition.requestFocus();
   }

   static int[] m511(LPRecognition lprecognition) {
      Object object = null;
      if (m501(lprecognition, null)) {
         synchronized (LPRecognition.problems) {
            C_YA c_ya = m512(lprecognition, true, false, LPRecognition.noPrint);
            c_ya.setBackground(C_n_A.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"Print Results", "Print List", "Cancel"};
            c_ya.f898 = 2;
            C_UA c_ua = new C_UA(lprecognition.frame, C_e_D.m1782("Print Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("recChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               C_c_.m1665(c_ya.m1533(c_ya.f899));
            } else if (c_ua.f790 == 1) {
               C_UF.m1358(c_ya.m1533(c_ya.f899));
            }
         }
      }

      lprecognition.requestFocus();
      return (int[])object;
   }

   static C_YA m512(LPRecognition lprecognition, boolean flag, boolean flag1, C_BE c_be) {
      return LPRecognition.problems.m1781(lprecognition, LPRecognition.exercises, flag, flag1, LPRecognition.monoProbs, c_be);
   }
}
