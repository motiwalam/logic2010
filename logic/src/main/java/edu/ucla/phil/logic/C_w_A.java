package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

class C_w_A implements C_n_A {
   static String[] f1424 = LogicProgram.f596;

   static void m2133(String s) {
      m2136(s, null, null, null);
   }

   static void m2134(String s, Point point) {
      m2136(s, null, null, point);
   }

   static void m2135(String s, Hashtable hashtable) {
      m2136(s, hashtable, null, null);
   }

   static void m2136(String s, Hashtable hashtable, Hashtable hashtable1, Point point) {
      C_H c_h = C_FE.m411(s);
      C_b_F c_b_f = null;
      if (c_h.f373 != null) {
         c_b_f = new C_b_F(c_h.f373);
         if (hashtable1 != null) {
            c_b_f.m448(hashtable1);
         }
      }

      C_UA.m1329(c_h, hashtable, point, c_b_f);
   }

   static String m2137(LPTruthAnalysis lptruthanalysis, String s) {
      if (s == null) {
         s = "User";
      }

      C_LB c_lb = new C_LB();
      C_p_A c_p_a = new C_p_A(s, 300);
      C_ZE c_ze = new C_ZE("Please supply a name for this problem");
      c_p_a.select(0, 2147483647);
      c_ze.setFocusable(false);
      c_lb.setLayout(new C_m_A(0));
      c_lb.add(c_ze);
      c_lb.add(c_p_a);
      String[] astring = new String[]{"OK", "Cancel"};
      C_UA c_ua = new C_UA(lptruthanalysis.frame, "", c_lb, astring);
      c_ua.m1314(0);
      c_p_a.requestFocus();
      c_ua.m1322(null);
      if (c_ua.f790 != 0) {
         return null;
      } else {
         s = c_p_a.getText();
         if ((s = s.trim()).equals("")) {
            C_H c_h = C_H.m411("not006");
            C_UA.m1329(C_H.m411("not006"), null, null, null);
            return null;
         } else if (LPTruthAnalysis.problems.m1780(s) != null) {
            LogicProgram.m972("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static boolean m2138(LPTruthAnalysis lptruthanalysis, Point point) {
      if (lptruthanalysis.hasWork()) {
         C_z_E c_z_e = new C_z_E();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            c_z_e.add(ajradiobutton[j] = new C_NF(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(lptruthanalysis.frame, "", c_z_e, astring2);
         c_ua.m1322(null);
         if (c_ua.f790 != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            lptruthanalysis.problem.m1909();
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lptruthanalysis.problemIndex != -1 || lptruthanalysis.problem.f1212 != null && !lptruthanalysis.problem.f1212.equals("")) {
         C_ZE c_ze = new C_ZE("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         C_UA c_ua1 = new C_UA(lptruthanalysis.frame, "", c_ze, astring1);
         c_ua1.m1322(null);
         if (c_ua1.f790 != 0) {
            return false;
         }
      }

      if (lptruthanalysis.problemIndex != -1) {
         LPTruthAnalysis.problems.m1101(lptruthanalysis.problemIndex);
         LPTruthAnalysis.saveProblems();
      }

      lptruthanalysis.newProblem();
      return true;
   }

   static boolean m2139(LPTruthAnalysis lptruthanalysis, Point point) {
      if (!lptruthanalysis.hasWork()) {
         return false;
      } else {
         C_ZE c_ze = new C_ZE("Delete the work on this problem?");
         String[] astring = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(lptruthanalysis.frame, "", c_ze, astring);
         c_ua.m1322(point);
         if (c_ua.f790 != 0) {
            return false;
         } else {
            lptruthanalysis.problem.m1909();
            return true;
         }
      }
   }

   static boolean m2140(LPTruthAnalysis lptruthanalysis) {
      if (m2148(lptruthanalysis, null)) {
         synchronized (LPTruthAnalysis.problems) {
            int i = lptruthanalysis.problemIndex + 1;
            if (i != 0 && LPTruthAnalysis.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPTruthAnalysis.problems.m1778(i);
            if (s == null) {
               return m2141(lptruthanalysis);
            }

            lptruthanalysis.loadProblem(s);
            lptruthanalysis.problemIndex = i;
            LPTruthAnalysis.problems.m1776(lptruthanalysis.saveProblem(), i);
            if (C_U.eraseWork) {
               lptruthanalysis.problem.m1909();
            }
         }
      }

      lptruthanalysis.requestFocus();
      return true;
   }

   static boolean m2141(LPTruthAnalysis lptruthanalysis) {
      boolean flag = false;
      if (m2148(lptruthanalysis, null)) {
         synchronized (LPTruthAnalysis.problems) {
            C_YA c_ya = m2147(lptruthanalysis, false, false, null);
            c_ya.setBackground(C_n_A.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"OK", "Cancel"};
            C_UA c_ua = new C_UA(lptruthanalysis.frame, C_e_D.m1782("Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("truChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               int i = c_ya.m1532(c_ya.f899);
               LPTruthAnalysis lptruthanalysis1;
               if ((lptruthanalysis1 = LPTruthAnalysis.openInstance(i, true)) != null) {
                  lptruthanalysis1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lptruthanalysis.loadProblem(LPTruthAnalysis.problems.m1778(i));
                  lptruthanalysis.problemIndex = i;
                  LPTruthAnalysis.problems.m1776(lptruthanalysis.saveProblem(), i);
                  if (C_U.eraseWork) {
                     lptruthanalysis.problem.m1909();
                  }

                  flag = true;
               }
            }
         }
      }

      lptruthanalysis.requestFocus();
      return flag;
   }

   static int[] m2142(LPTruthAnalysis lptruthanalysis) {
      int[] aint = null;
      if (m2148(lptruthanalysis, null)) {
         synchronized (LPTruthAnalysis.problems) {
            C_YA c_ya = m2147(lptruthanalysis, true, false, null);
            if (LPTruthAnalysis.submitExam) {
               c_ya.clearSelection();
            }

            Vector vector = LPTruthAnalysis.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = C_XD.m1493(s);
                  int j = LPTruthAnalysis.problems.m1767(s1);
                  if (j != -1) {
                     c_ya.m1531(j, c_ya.f899);
                  }
               }
            }

            c_ya.setBackground(C_n_A.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"Submit", "Cancel"};
            C_UA c_ua = new C_UA(lptruthanalysis.frame, C_e_D.m1782("Submit Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("truChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            }
         }
      }

      lptruthanalysis.requestFocus();
      return aint;
   }

   static int[] m2143(LPTruthAnalysis lptruthanalysis) {
      int[] aint = null;
      if (m2148(lptruthanalysis, null)) {
         synchronized (LPTruthAnalysis.problems) {
            C_YA c_ya = m2147(lptruthanalysis, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            C_LB c_lb = new C_LB();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(C_H.m412("not089")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            c_lb.add(c_s_b, "North");
            c_lb.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            C_UA c_ua = new C_UA(lptruthanalysis.frame, C_e_D.m1782("Upload Problems"), c_lb, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("truChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            }
         }
      }

      lptruthanalysis.requestFocus();
      return aint;
   }

   static void m2144(LPTruthAnalysis lptruthanalysis) {
      synchronized (LPTruthAnalysis.problems) {
         C_YA c_ya = m2147(lptruthanalysis, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(c_ya);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         C_UA c_ua = new C_UA(lptruthanalysis.frame, C_e_D.m1782("Delete Problems"), jscrollpane, astring);
         c_ya.m1528(c_ua, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
         c_ua.setSize(dimension);
         c_ua.m1317("truChosen");
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
                     C_f_C c_f_c1 = (C_f_C)LPTruthAnalysis.problems.m1779(l);
                     if (c_f_c1 != null) {
                        C_XD c_xd1 = new C_XD(c_f_c1.f1119);
                        if (!LPTruthAnalysis.isExample(c_xd1.m1494())) {
                           c_f_c1.f1119 = LPTruthAnalysis.removeWork(c_xd1);
                           c_f_c1.f1120 = 0;
                           LPTruthAnalysis lptruthanalysis2 = LPTruthAnalysis.openInstance(l, false);
                           if (lptruthanalysis2 != null) {
                              lptruthanalysis2.loadProblem(c_f_c1.f1119);
                           }
                        }
                     }
                  }
               }

               LPTruthAnalysis.saveProblems();
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
                     C_f_C c_f_c = (C_f_C)LPTruthAnalysis.problems.m1779(j);
                     if (c_f_c != null) {
                        C_XD c_xd = new C_XD(c_f_c.f1119);
                        if (!LPTruthAnalysis.isExercise(c_xd.m1494())) {
                           LPTruthAnalysis lptruthanalysis1 = LPTruthAnalysis.openInstance(j, false);
                           LPTruthAnalysis.problems.m1101(j);
                           if (lptruthanalysis1 != null) {
                              lptruthanalysis1.newProblem();
                           }
                        }
                     }
                  }
               }

               LPTruthAnalysis.saveProblems();
            }
         }
      }

      lptruthanalysis.requestFocus();
   }

   static int[] m2145(LPTruthAnalysis lptruthanalysis) {
      int[] aint = null;
      if (m2148(lptruthanalysis, null)) {
         synchronized (LPTruthAnalysis.problems) {
            C_YA c_ya = m2147(lptruthanalysis, true, false, LPTruthAnalysis.noPrint);
            c_ya.setBackground(C_n_A.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"Print", "Print Results", "Print List", "Cancel"};
            c_ya.f898 = 3;
            C_UA c_ua = new C_UA(lptruthanalysis.frame, C_e_D.m1782("Print Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("truChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            } else if (c_ua.f790 == 1) {
               C_A.m71(c_ya.m1533(c_ya.f899));
            } else if (c_ua.f790 == 2) {
               C_c_D.m1671(c_ya.m1533(c_ya.f899));
            }
         }
      }

      lptruthanalysis.requestFocus();
      return aint;
   }

   static void m2146(LPTruthAnalysis lptruthanalysis) {
      if (m2148(lptruthanalysis, null)) {
         Dimension dimension = new Dimension(20 * LogicProgram.f539, 10 * LogicProgram.f539);
         Point point = C_UA.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         C_p_A c_p_a = new C_p_A();
         String[] astring = new String[]{"OK", "Cancel"};
         c_p_a.setBackground(Color.WHITE);
         jscrollpane.setViewportView(c_p_a);
         C_UA c_ua = new C_UA(lptruthanalysis.frame, "Submitted Problem", jscrollpane, astring);
         c_ua.setSize(dimension);
         c_ua.m1314(0);
         c_ua.m1317("truSubmitted");
         c_p_a.requestFocus();
         c_ua.m1323(point, true);
         if (c_ua.f790 == 0) {
            lptruthanalysis.loadProblem(c_p_a.getText());
         }
      }

      lptruthanalysis.requestFocus();
   }

   static C_YA m2147(LPTruthAnalysis lptruthanalysis, boolean flag, boolean flag1, C_BE c_be) {
      return LPTruthAnalysis.problems.m1781(lptruthanalysis, LPTruthAnalysis.exercises, flag, flag1, LPTruthAnalysis.monoProbs, c_be);
   }

   static boolean m2148(LPTruthAnalysis lptruthanalysis, Point point) {
      String s = lptruthanalysis.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         C_ZE c_ze = new C_ZE("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         C_UA c_ua = new C_UA(lptruthanalysis.frame, "", c_ze, astring);
         c_ua.m1322(point);
         return c_ua.f790 == 0 && !lptruthanalysis.saveProblems(s) ? false : c_ua.f790 == 0 || c_ua.f790 == 1;
      }
   }

   static void m2149(LPTruthAnalysis lptruthanalysis) {
      if (m2148(lptruthanalysis, null)) {
         Dimension dimension = new Dimension(20 * LogicProgram.f539, 15 * LogicProgram.f539);
         Point point = C_UA.m1321(dimension);
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new BorderLayout());
         JScrollPane jscrollpane = new JScrollPane();
         C_NE c_ne = new C_NE("Truth Table Only");
         jpanel.add(jscrollpane, "Center");
         jpanel.add(c_ne, "South");
         C_IF c_if = new C_IF(lptruthanalysis.frame);
         if (lptruthanalysis.lastUserProblem != null) {
            c_if.setText(LogicProgram.m995(lptruthanalysis.lastUserProblem, maggie, f1424));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(c_if);
         c_if.setBackground(Color.WHITE);
         C_UA c_ua = new C_UA(lptruthanalysis.frame, "User Problem", jpanel, astring);
         c_ua.setSize(dimension);
         c_ua.m1314(0);
         c_ua.m1317("truUser");
         c_if.f425 = c_ua;
         c_if.requestFocus();
         c_ua.m1323(point, true);
         if (c_ua.f790 == 0) {
            String s = c_if.getText();
            if (!LPTruthAnalysis.validateUserProblem(s)) {
               return;
            }

            lptruthanalysis.lastUserProblem = LogicProgram.m995(s, f1424, maggie);
            lptruthanalysis.loadUserProblem(lptruthanalysis.lastUserProblem, c_ne.isSelected());
         }
      }

      lptruthanalysis.requestFocus();
   }
}
