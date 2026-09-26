package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

class C_CE implements C_h_B {
   static String[] f265 = LogicProgram.f596;

   static void m427(String s) {
      m430(s, null, null, null);
   }

   static void m428(String s, Point point) {
      m430(s, null, null, point);
   }

   static void m429(String s, Hashtable hashtable) {
      m430(s, hashtable, null, null);
   }

   static void m430(String s, Hashtable hashtable, Hashtable hashtable1, Point point) {
      C_H c_h = C_LA.m411(s);
      C_GB c_gb = null;
      if (c_h.f373 != null) {
         c_gb = new C_GB(c_h.f373);
         if (hashtable1 != null) {
            c_gb.m448(hashtable1);
         }
      }

      C_UA.m1329(c_h, hashtable, point, c_gb);
   }

   static boolean m431(LPInvalidation lpinvalidation, Point point) {
      String s = lpinvalidation.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lpinvalidation.frame.show();
         C_ZE c_ze = new C_ZE("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         C_UA c_ua = new C_UA(lpinvalidation.frame, "", c_ze, astring);
         c_ua.m1322(point);
         return c_ua.f790 == 0 && !lpinvalidation.saveProblems(s) ? false : c_ua.f790 == 0 || c_ua.f790 == 1;
      }
   }

   static String m432(String s) {
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
         } else if (LPInvalidation.problems.m1780(s) != null) {
            LogicProgram.m972("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static boolean m433(LPInvalidation lpinvalidation, Point point) {
      if (lpinvalidation.size != 0) {
         C_z_E c_z_e = new C_z_E();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            c_z_e.add(ajradiobutton[j] = new C_NF(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(lpinvalidation.frame, "", c_z_e, astring2);
         c_ua.m1322(null);
         if (c_ua.f790 != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            lpinvalidation.removeWork();
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lpinvalidation.problemIndex != -1 || lpinvalidation.statement != null) {
         C_ZE c_ze = new C_ZE("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         C_UA c_ua1 = new C_UA(lpinvalidation.frame, "", c_ze, astring1);
         c_ua1.m1322(null);
         if (c_ua1.f790 != 0) {
            return false;
         }
      }

      if (lpinvalidation.problemIndex != -1) {
         LPInvalidation.problems.m1101(lpinvalidation.problemIndex);
         LPInvalidation.saveProblems();
      }

      lpinvalidation.newProblem();
      return true;
   }

   static boolean m434(LPInvalidation lpinvalidation, Point point) {
      if (lpinvalidation.size == 0) {
         return false;
      } else {
         C_ZE c_ze = new C_ZE("Delete the work on this problem?");
         String[] astring = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(lpinvalidation.frame, "", c_ze, astring);
         c_ua.m1322(point);
         if (c_ua.f790 != 0) {
            return false;
         } else {
            lpinvalidation.removeWork();
            return true;
         }
      }
   }

   static boolean m435(LPInvalidation lpinvalidation) {
      if (m431(lpinvalidation, null)) {
         synchronized (LPInvalidation.problems) {
            int i = lpinvalidation.problemIndex + 1;
            if (i != 0 && LPInvalidation.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPInvalidation.problems.m1778(i);
            if (s == null) {
               return m436(lpinvalidation);
            }

            lpinvalidation.loadProblem(s);
            lpinvalidation.problemIndex = i;
            LPInvalidation.problems.m1776(lpinvalidation.saveProblem(), i);
            if (C_U.eraseWork) {
               lpinvalidation.removeWork();
            }
         }
      }

      lpinvalidation.requestFocus();
      return true;
   }

   static boolean m436(LPInvalidation lpinvalidation) {
      boolean flag = false;
      if (m431(lpinvalidation, null)) {
         synchronized (LPInvalidation.problems) {
            C_YA c_ya = m442(lpinvalidation, false, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"OK", "Cancel"};
            C_UA c_ua = new C_UA(lpinvalidation.frame, C_e_D.m1782("Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("invChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               int i = c_ya.m1532(c_ya.f899);
               LPInvalidation lpinvalidation1;
               if ((lpinvalidation1 = LPInvalidation.openInstance(i, true)) != null) {
                  lpinvalidation1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lpinvalidation.loadProblem(LPInvalidation.problems.m1778(i));
                  lpinvalidation.problemIndex = i;
                  LPInvalidation.problems.m1776(lpinvalidation.saveProblem(), i);
                  if (C_U.eraseWork) {
                     lpinvalidation.removeWork();
                  }

                  flag = true;
               }
            }
         }
      }

      lpinvalidation.requestFocus();
      return flag;
   }

   static int[] m437(LPInvalidation lpinvalidation) {
      int[] aint = null;
      if (m431(lpinvalidation, null)) {
         synchronized (LPInvalidation.problems) {
            C_YA c_ya = m442(lpinvalidation, true, false, null);
            if (LPInvalidation.submitExam) {
               c_ya.clearSelection();
            }

            Vector vector = LPInvalidation.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = C_XD.m1493(s);
                  int j = LPInvalidation.problems.m1767(s1);
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
            C_UA c_ua = new C_UA(lpinvalidation.frame, C_e_D.m1782("Submit Problems"), c_lb, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("invChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            }
         }
      }

      lpinvalidation.requestFocus();
      return aint;
   }

   static int[] m438(LPInvalidation lpinvalidation) {
      int[] aint = null;
      if (m431(lpinvalidation, null)) {
         synchronized (LPInvalidation.problems) {
            C_YA c_ya = m442(lpinvalidation, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            C_LB c_lb = new C_LB();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(C_H.m412("not089")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            c_lb.add(c_s_b, "North");
            c_lb.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            C_UA c_ua = new C_UA(lpinvalidation.frame, C_e_D.m1782("Upload Problems"), c_lb, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("invChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            }
         }
      }

      lpinvalidation.requestFocus();
      return aint;
   }

   static void m439(LPInvalidation lpinvalidation) {
      synchronized (LPInvalidation.problems) {
         C_YA c_ya = m442(lpinvalidation, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(c_ya);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         C_UA c_ua = new C_UA(lpinvalidation.frame, C_e_D.m1782("Delete Problems"), jscrollpane, astring);
         c_ya.m1528(c_ua, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
         c_ua.setSize(dimension);
         c_ua.m1317("invChosen");
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
                     C_EE c_ee1 = (C_EE)LPInvalidation.problems.m1779(l);
                     if (c_ee1 != null) {
                        C_XD c_xd1 = new C_XD(c_ee1.f1119);
                        if (!LPInvalidation.isExample(c_xd1.m1494())) {
                           c_ee1.f1119 = LPInvalidation.removeWork(c_xd1);
                           c_ee1.f1120 = 0;
                           LPInvalidation lpinvalidation2 = LPInvalidation.openInstance(l, false);
                           if (lpinvalidation2 != null) {
                              lpinvalidation2.loadProblem(c_ee1.f1119);
                           }
                        }
                     }
                  }
               }

               LPInvalidation.saveProblems();
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
                     C_EE c_ee = (C_EE)LPInvalidation.problems.m1779(j);
                     if (c_ee != null) {
                        C_XD c_xd = new C_XD(c_ee.f1119);
                        if (!LPInvalidation.isExercise(c_xd.m1494())) {
                           LPInvalidation lpinvalidation1 = LPInvalidation.openInstance(j, false);
                           LPInvalidation.problems.m1101(j);
                           if (lpinvalidation1 != null) {
                              lpinvalidation1.newProblem();
                           }
                        }
                     }
                  }
               }

               LPInvalidation.saveProblems();
            }
         }
      }

      lpinvalidation.requestFocus();
   }

   static int[] m440(LPInvalidation lpinvalidation) {
      Object object = null;
      if (m431(lpinvalidation, null)) {
         synchronized (LPInvalidation.problems) {
            C_YA c_ya = m442(lpinvalidation, true, false, LPInvalidation.noPrint);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"Print", "Print Results", "Print List", "Cancel"};
            c_ya.f898 = 3;
            C_UA c_ua = new C_UA(lpinvalidation.frame, C_e_D.m1782("Print Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("invChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               C_q_C.m2038(c_ya.m1533(c_ya.f899));
            } else if (c_ua.f790 == 1) {
               C_e_F.m1800(c_ya.m1533(c_ya.f899));
            } else if (c_ua.f790 == 2) {
               C_k_D.m1907(c_ya.m1533(c_ya.f899));
            }
         }
      }

      lpinvalidation.requestFocus();
      return (int[])object;
   }

   static void m441(LPInvalidation lpinvalidation) {
      if (m431(lpinvalidation, null)) {
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
         c_ua.m1317("invSubmitted");
         c_p_a.requestFocus();
         c_ua.m1323(point, true);
         c_0e.dispose();
         if (c_ua.f790 == 0) {
            lpinvalidation.loadProblem(c_p_a.getText());
         }
      }

      lpinvalidation.requestFocus();
   }

   static C_YA m442(LPInvalidation lpinvalidation, boolean flag, boolean flag1, C_BE c_be) {
      return LPInvalidation.problems.m1781(lpinvalidation, LPInvalidation.exercises, flag, flag1, LPInvalidation.monoProbs, c_be);
   }

   static void m443(LPInvalidation lpinvalidation) {
      if (m431(lpinvalidation, null)) {
         C_0E c_0e = new C_0E();
         Dimension dimension = new Dimension(20 * LogicProgram.f539, 10 * LogicProgram.f539);
         Point point = C_UA.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         C_IF c_if = new C_IF(c_0e);
         if (lpinvalidation.lastUserProblem != null) {
            c_if.setText(LogicProgram.m995(lpinvalidation.lastUserProblem, maggie, f265));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         c_if.setBackground(Color.WHITE);
         jscrollpane.setViewportView(c_if);
         C_UA c_ua = new C_UA(c_0e, "User Problem", jscrollpane, astring);
         c_ua.setSize(dimension);
         c_ua.m1314(0);
         c_ua.m1317("invUser");
         c_if.f425 = c_ua;
         c_if.requestFocus();
         c_ua.m1323(point, true);
         c_0e.dispose();
         if (c_ua.f790 == 0) {
            String s = c_if.getText();
            if (!LPInvalidation.validateUserProblem(s)) {
               return;
            }

            lpinvalidation.lastUserProblem = LogicProgram.m995(s, f265, maggie);
            lpinvalidation.loadUserProblem(lpinvalidation.lastUserProblem);
         }
      }

      lpinvalidation.requestFocus();
   }

   static boolean m444(C_IE c_ie, int i) {
      C_0E c_0e = new C_0E();
      JScrollPane jscrollpane = new JScrollPane();
      C_e_A c_e_a = new C_e_A(c_ie, i);
      String[] astring = new String[]{"OK", "Cancel"};
      jscrollpane.setViewportView(c_e_a);
      C_UA c_ua = new C_UA(c_0e, "Extend " + c_ie.m710(), jscrollpane, astring);
      c_ua.pack();
      Point point = C_UA.m1321(c_ua.getSize());
      c_ua.m1314(0);
      c_ua.m1323(point, true);
      c_0e.dispose();
      if (c_ua.f790 == 0) {
         c_e_a.m1756();
         return true;
      } else {
         return false;
      }
   }
}
