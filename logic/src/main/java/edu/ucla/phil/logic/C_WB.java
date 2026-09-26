package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

class C_WB implements C_v_D {
   static String[] f854 = LogicProgram.f596;

   static void m1427(String s) {
      m1430(s, null, null, null);
   }

   static void m1428(String s, Point point) {
      m1430(s, null, null, point);
   }

   static void m1429(String s, Hashtable hashtable) {
      m1430(s, hashtable, null, null);
   }

   static void m1430(String s, Hashtable hashtable, Hashtable hashtable1, Point point) {
      C_H c_h = C_h_E.m411(s);
      C_RE c_re = null;
      if (c_h.f373 != null) {
         c_re = new C_RE(c_h.f373);
         if (hashtable1 != null) {
            c_re.m448(hashtable1);
         }
      }

      C_UA.m1329(c_h, hashtable, point, c_re);
   }

   static boolean m1431(LPSymbolizer lpsymbolizer, Point point) {
      if (lpsymbolizer.problem.m1681()) {
         C_z_E c_z_e = new C_z_E();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            c_z_e.add(ajradiobutton[j] = new C_NF(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(lpsymbolizer.frame, "", c_z_e, astring2);
         c_ua.m1322(null);
         if (c_ua.f790 != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            lpsymbolizer.problem.m1690(0, false);
            lpsymbolizer.problem.f1045.f1438.requestFocus();
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lpsymbolizer.problemIndex != -1 || !lpsymbolizer.problem.m1682().equals(C_x_C.m2166(""))) {
         C_ZE c_ze = new C_ZE("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         C_UA c_ua1 = new C_UA(lpsymbolizer.frame, "", c_ze, astring1);
         c_ua1.m1322(null);
         if (c_ua1.f790 != 0) {
            return false;
         }
      }

      if (lpsymbolizer.problemIndex != -1) {
         LPSymbolizer.problems.m1101(lpsymbolizer.problemIndex);
         LPSymbolizer.saveProblems();
      }

      lpsymbolizer.newProblem();
      return true;
   }

   static boolean m1432(LPSymbolizer lpsymbolizer, Point point) {
      if (!lpsymbolizer.problem.m1681()) {
         return false;
      } else {
         C_ZE c_ze = new C_ZE("Delete the work on this problem?");
         String[] astring = new String[]{"OK", "Cancel"};
         C_UA c_ua = new C_UA(lpsymbolizer.frame, "", c_ze, astring);
         c_ua.m1322(point);
         if (c_ua.f790 != 0) {
            return false;
         } else {
            lpsymbolizer.removeWork();
            return true;
         }
      }
   }

   static boolean m1433(LPSymbolizer lpsymbolizer) {
      if (m1450(lpsymbolizer, null)) {
         synchronized (LPSymbolizer.problems) {
            int i = lpsymbolizer.problemIndex + 1;
            if (i != 0 && LPSymbolizer.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPSymbolizer.problems.m1778(i);
            if (s == null) {
               return m1434(lpsymbolizer);
            }

            lpsymbolizer.loadProblem(s);
            lpsymbolizer.problemIndex = i;
            LPSymbolizer.problems.m1776(lpsymbolizer.saveProblem(), i);
            if (C_U.eraseWork) {
               lpsymbolizer.removeWork();
            }
         }
      }

      lpsymbolizer.requestFocus();
      return true;
   }

   static boolean m1434(LPSymbolizer lpsymbolizer) {
      boolean flag = false;
      if (m1450(lpsymbolizer, null)) {
         synchronized (LPSymbolizer.problems) {
            C_YA c_ya = m1441(lpsymbolizer, false, true, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"OK", "Cancel"};
            C_UA c_ua = new C_UA(lpsymbolizer.frame, C_e_D.m1782("Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("symChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               int i = c_ya.m1532(c_ya.f899);
               LPSymbolizer lpsymbolizer1;
               if ((lpsymbolizer1 = LPSymbolizer.openInstance(i, true)) != null) {
                  lpsymbolizer1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lpsymbolizer.loadProblem(LPSymbolizer.problems.m1778(i));
                  lpsymbolizer.problemIndex = i;
                  LPSymbolizer.problems.m1776(lpsymbolizer.saveProblem(), i);
                  if (C_U.eraseWork) {
                     lpsymbolizer.removeWork();
                  }

                  flag = true;
               }
            } else if (lpsymbolizer.lastFocus != null) {
               lpsymbolizer.lastFocus.requestFocus();
            }
         }
      }

      return flag;
   }

   static String m1435(LPSymbolizer lpsymbolizer, C_y_B c_y_b) {
      Object object = null;
      C_YA c_ya = m1441(lpsymbolizer, false, true, null);
      JScrollPane jscrollpane = new JScrollPane();
      jscrollpane.setViewportView(c_ya);
      String[] astring = new String[]{"Copy", "Cancel"};
      Object object1 = c_y_b == null ? null : LogicProgram.m1045(c_y_b);
      if (object1 == null) {
         object1 = lpsymbolizer.frame;
      }

      C_UA c_ua = new C_UA((Frame)object1, C_e_D.m1782("Problems"), jscrollpane, astring);
      c_ya.m1528(c_ua, 0);
      Dimension dimension = new Dimension(20 * LogicProgram.f539, 25 * LogicProgram.f539);
      c_ua.setSize(dimension);
      c_ua.m1317("symSchemeChosen");
      c_ya.requestFocus();
      c_ua.m1323(C_UA.m1321(dimension), true);
      if (c_ua.f790 == 0) {
         int i = c_ya.m1532(c_ya.f899);
         if (i != -1) {
            return LPSymbolizer.getProblemScheme(C_XD.m1493(LPSymbolizer.problems.m1778(i)));
         }
      }

      return null;
   }

   static int[] m1436(LPSymbolizer lpsymbolizer) {
      int[] aint = null;
      if (m1450(lpsymbolizer, null)) {
         synchronized (LPSymbolizer.problems) {
            C_YA c_ya = m1441(lpsymbolizer, true, true, null);
            if (LPSymbolizer.submitExam) {
               c_ya.clearSelection();
            }

            Vector vector = LPSymbolizer.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = C_XD.m1493(s);
                  int j = LPSymbolizer.problems.m1767(s1);
                  if (j != -1) {
                     c_ya.m1531(j, c_ya.f899);
                  }
               }
            }

            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"Submit", "Cancel"};
            C_UA c_ua = new C_UA(lpsymbolizer.frame, C_e_D.m1782("Submit Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("symChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            }
         }
      }

      lpsymbolizer.requestFocus();
      return aint;
   }

   static int[] m1437(LPSymbolizer lpsymbolizer) {
      int[] aint = null;
      if (m1450(lpsymbolizer, null)) {
         synchronized (LPSymbolizer.problems) {
            C_YA c_ya = m1441(lpsymbolizer, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            C_LB c_lb = new C_LB();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(C_H.m412("not089")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            c_lb.add(c_s_b, "North");
            c_lb.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            C_UA c_ua = new C_UA(lpsymbolizer.frame, C_e_D.m1782("Upload Problems"), c_lb, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("symChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            }
         }
      }

      lpsymbolizer.requestFocus();
      return aint;
   }

   static void m1438(LPSymbolizer lpsymbolizer) {
      synchronized (LPSymbolizer.problems) {
         C_YA c_ya = m1441(lpsymbolizer, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(c_ya);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         C_UA c_ua = new C_UA(lpsymbolizer.frame, C_e_D.m1782("Delete Problems"), jscrollpane, astring);
         c_ya.m1528(c_ua, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
         c_ua.setSize(dimension);
         c_ua.m1317("symChosen");
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
                     C__C c__c1 = (C__C)LPSymbolizer.problems.m1779(l);
                     if (c__c1 != null) {
                        C_XD c_xd1 = new C_XD(c__c1.f1119);
                        if (!LPSymbolizer.isExample(c_xd1.m1494())) {
                           c__c1.f1119 = LPSymbolizer.removeWork(c_xd1);
                           c__c1.f1120 = 0;
                           LPSymbolizer lpsymbolizer2 = LPSymbolizer.openInstance(l, false);
                           if (lpsymbolizer2 != null) {
                              lpsymbolizer2.loadProblem(c__c1.f1119);
                           }
                        }
                     }
                  }
               }

               LPSymbolizer.saveProblems();
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
                     C__C c__c = (C__C)LPSymbolizer.problems.m1779(j);
                     if (c__c != null) {
                        C_XD c_xd = new C_XD(c__c.f1119);
                        if (!LPSymbolizer.isExercise(c_xd.m1494())) {
                           LPSymbolizer lpsymbolizer1 = LPSymbolizer.openInstance(j, false);
                           LPSymbolizer.problems.m1101(j);
                           if (lpsymbolizer1 != null) {
                              lpsymbolizer1.newProblem();
                           }
                        }
                     }
                  }
               }

               LPSymbolizer.saveProblems();
            }
         }
      }

      lpsymbolizer.requestFocus();
   }

   static int[] m1439(LPSymbolizer lpsymbolizer) {
      int[] aint = null;
      if (m1450(lpsymbolizer, null)) {
         synchronized (LPSymbolizer.problems) {
            C_YA c_ya = m1441(lpsymbolizer, true, true, LPSymbolizer.noPrint);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(c_ya);
            String[] astring = new String[]{"Print", "Print Results", "Print List", "Cancel"};
            c_ya.f898 = 3;
            C_UA c_ua = new C_UA(lpsymbolizer.frame, C_e_D.m1782("Print Problems"), jscrollpane, astring);
            c_ya.m1528(c_ua, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
            c_ua.setSize(dimension);
            c_ua.m1317("symChosen");
            c_ya.requestFocus();
            c_ua.m1323(C_UA.m1321(dimension), true);
            if (c_ua.f790 == 0) {
               aint = c_ya.m1533(c_ya.f899);
            } else if (c_ua.f790 == 1) {
               C_c_A.m1666(c_ya.m1533(c_ya.f899));
            } else if (c_ua.f790 == 2) {
               C_WF.m1460(c_ya.m1533(c_ya.f899));
            }
         }
      }

      lpsymbolizer.requestFocus();
      return aint;
   }

   static void m1440(LPSymbolizer lpsymbolizer) {
      if (m1450(lpsymbolizer, null)) {
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
         c_ua.m1317("symSubmitted");
         c_p_a.requestFocus();
         c_ua.m1323(point, true);
         c_0e.dispose();
         if (c_ua.f790 == 0) {
            lpsymbolizer.loadProblem(c_p_a.getText());
         }
      }

      lpsymbolizer.requestFocus();
   }

   static C_YA m1441(LPSymbolizer lpsymbolizer, boolean flag, boolean flag1, C_BE c_be) {
      return LPSymbolizer.problems.m1781(lpsymbolizer, LPSymbolizer.exercises, flag, flag1, LPSymbolizer.monoProbs, c_be);
   }

   static void m1442(LPSymbolizer lpsymbolizer) {
      m1443(lpsymbolizer, null);
   }

   static void m1443(LPSymbolizer lpsymbolizer, String s) {
      if (s != null || m1450(lpsymbolizer, null)) {
         C_0E c_0e = new C_0E();
         Dimension dimension = new Dimension(20 * LogicProgram.f539, 10 * LogicProgram.f539);
         Point point = C_UA.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         C_IF c_if = new C_IF(c_0e);
         if (s != null) {
            c_if.setText(s);
         } else if (LPSymbolizer.lastUserProblem != null) {
            c_if.setText(LPSymbolizer.lastUserProblem);
         }

         C_I c_i = new C_I("OK:ok.Clear:clear.Cancel:cancel;0");
         c_i.m447("edit", c_if);
         c_i.m447("symbolizer", lpsymbolizer);
         jscrollpane.setViewportView(c_if);
         c_if.setBackground(Color.WHITE);
         C_UA c_ua = new C_UA(c_0e, "User Problem", jscrollpane, c_i.m445());
         c_ua.setSize(dimension);
         c_ua.m1314(0);
         c_ua.m1317("symUser");
         c_if.f425 = c_ua;
         c_if.requestFocus();
         c_ua.m1323(point, true);
         c_0e.dispose();
         if (c_ua.f790 == 0) {
            String s1 = c_if.getText();
            if (!LPSymbolizer.validateUserProblem(s1)) {
               return;
            }

            LPSymbolizer.lastUserProblem = s1;
            if (s == null) {
               lpsymbolizer.loadUserProblem(LPSymbolizer.lastUserProblem);
            } else {
               lpsymbolizer.problem.f1055 = LPSymbolizer.lastUserProblem;
               lpsymbolizer.titlePanel.m1823(LPSymbolizer.lastUserProblem);
               lpsymbolizer.problem.m1680(LPSymbolizer.lastUserProblem);
               lpsymbolizer.updateSymbolization();
            }

            if (s == null) {
               m1444(lpsymbolizer, null);
            }
         }
      }

      lpsymbolizer.requestFocus();
   }

   static void m1444(LPSymbolizer lpsymbolizer, String s) {
      C_0E c_0e = new C_0E();
      Dimension dimension = new Dimension(25 * LogicProgram.f539, 15 * LogicProgram.f539);
      Point point = C_UA.m1321(dimension);
      JScrollPane jscrollpane = new JScrollPane();
      C_y_B c_y_b = new C_y_B(true);
      if (s != null) {
         if (s.indexOf(58) == -1) {
            c_y_b.m2182(":");
         } else {
            c_y_b.m2182(s);
         }
      } else if (LPSymbolizer.lastUserScheme != null && LPSymbolizer.lastUserScheme.indexOf(58) != -1) {
         c_y_b.m2182(LPSymbolizer.lastUserScheme);
      } else {
         c_y_b.m2182(":");
      }

      C_r_E c_r_e;
      if (s == null) {
         c_r_e = new C_r_E("OK:ok.Clear:clear.Browse:open.Help:help;0");
      } else {
         c_r_e = new C_r_E("OK:ok.Clear:clear.Browse:open.Cancel:cancel.Help:help;0");
      }

      c_r_e.m447("scheme", c_y_b);
      c_r_e.m447("symbolizer", lpsymbolizer);
      jscrollpane.setViewportView(c_y_b);
      c_y_b.setBackground(Color.WHITE);
      C_UA c_ua;
      if (s == null) {
         c_ua = new C_UA(c_0e, "Create Scheme", jscrollpane, c_r_e.m445());
      } else {
         c_ua = new C_UA(c_0e, "Edit Scheme", jscrollpane, c_r_e.m445());
      }

      c_ua.setSize(dimension);
      c_ua.m1314(0);
      c_ua.m1317("symUserScheme");
      c_ua.m1315(c_r_e);
      c_ua.m1316("OK", "Accept scheme");
      c_ua.m1316("Clear", "Reset scheme");
      c_ua.m1316("Browse", "Load scheme from another problem");
      c_y_b.requestFocus();
      c_ua.m1323(point, true);
      c_0e.dispose();
      if (c_ua.f790 == 0) {
         LPSymbolizer.lastUserScheme = c_y_b.m2184();
         lpsymbolizer.scheme.m2182(LPSymbolizer.lastUserScheme);
         if (lpsymbolizer.problem != null) {
            lpsymbolizer.problem.f1053 = LPSymbolizer.lastUserScheme;
         }
      }

      lpsymbolizer.requestFocus();
   }

   static void m1445(LPSymbolizer lpsymbolizer) {
      if ((lpsymbolizer.problem.f1058 || lpsymbolizer.problem.f1052 != null) && !lpsymbolizer.dontChange) {
         if (lpsymbolizer.problem.f1052 != null) {
            lpsymbolizer.problem.m1700();
         }

         String s = lpsymbolizer.problem.f1055;
         if (s == null) {
            s = "";
         }

         m1443(lpsymbolizer, s);
      } else {
         m1427("SymNot012");
      }
   }

   static void m1446(LPSymbolizer lpsymbolizer) {
      if ((lpsymbolizer.problem.f1058 || lpsymbolizer.problem.f1052 != null) && !lpsymbolizer.dontChange) {
         if (lpsymbolizer.problem.f1052 != null) {
            lpsymbolizer.problem.m1700();
         }

         String s = lpsymbolizer.problem.f1053;
         if (s == null) {
            s = ":";
         }

         m1444(lpsymbolizer, s);
      } else {
         m1427("SymNot012");
      }
   }

   static void m1447(LPSymbolizer lpsymbolizer) {
      if (lpsymbolizer.problem.f1058 || C_KC.f480 && !C_KC.f482 && C_u_C.m2102("symAnswerPrint", "instructor") == null) {
         if (lpsymbolizer.problem.f1052 != null && !lpsymbolizer.dontChange) {
            lpsymbolizer.problem.m1700();
         }

         C_YA c_ya = new C_YA(true);
         Vector vector = lpsymbolizer.problem.f1056;
         C_d_C c_d_c = new C_d_C(lpsymbolizer);
         int i = vector == null ? 0 : vector.size();

         for (int j = 0; j < i; j++) {
            c_d_c.m1703(new C_XD((String)vector.elementAt(j)));
            String s = c_d_c.toString();
            C_QC c_qc = new C_QC(s, 2);
            c_qc.m1187(s);
            c_qc.setOpaque(true);
            c_ya.m1526(c_qc);
         }

         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(c_ya);
         C_QD c_qd;
         if (!lpsymbolizer.problem.f1058 || lpsymbolizer.dontChange) {
            c_qd = new C_QD("Use:load.OK:ok;0");
         } else if (lpsymbolizer.problem.m1717()) {
            c_qd = new C_QD("Add:warn.Use:load.Delete:delete.Replace:warn.Help:help.OK:ok;1");
         } else {
            c_qd = new C_QD("Add:add.Use:load.Delete:delete.Replace:replace.Help:help.OK:ok;1");
         }

         c_qd.m447("symbolizer", lpsymbolizer);
         c_qd.m447("list", c_ya);
         C_UA c_ua = new C_UA(null, "Answer Manager", jscrollpane, c_qd.m445());
         c_ua.m1316("Add", "Add the answer currently in the workspace.");
         c_ua.m1316("Use", "Load the selected answer into the workspace.");
         c_ua.m1316("Delete", "Delete the selected answer(s).");
         c_ua.m1316("Replace", "Replace the selected answer(s) with the answer currently in the workspace.");
         c_ua.m1316("OK", "OK as is");
         c_ua.m1315(c_qd);
         c_ya.m1528(c_ua, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.f539, 40 * LogicProgram.f539);
         c_ua.setSize(dimension);
         c_ua.m1317("symEditAnswer");
         c_ya.requestFocus();
         c_ua.m1323(C_UA.m1321(dimension), true);
      } else {
         m1427("SymNot005");
      }
   }

   static int[] m1448(Frame frame) {
      Object object = null;
      C_YA c_ya = m1449(true);
      JScrollPane jscrollpane = new JScrollPane();
      jscrollpane.setViewportView(c_ya);
      String[] astring = new String[]{"Print", "Cancel"};
      C_UA c_ua = new C_UA(frame, C_e_D.m1782("Print Answers"), jscrollpane, astring);
      c_ya.m1528(c_ua, 0);
      Dimension dimension = new Dimension(32 * LogicProgram.f539, 32 * LogicProgram.f539);
      c_ua.setSize(dimension);
      c_ua.m1317("symChosen");
      c_ya.requestFocus();
      c_ua.m1323(C_UA.m1321(dimension), true);
      if (c_ua.f790 == 0) {
         C_PD.m1179(c_ya.m1533(c_ya.f899));
      }

      return (int[])object;
   }

   static C_YA m1449(boolean flag) {
      return LPSymbolizer.exercises.m1781(null, LPSymbolizer.exercises, flag, true, null, null);
   }

   static boolean m1450(LPSymbolizer lpsymbolizer, Point point) {
      String s = lpsymbolizer.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lpsymbolizer.frame.show();
         C_ZE c_ze = new C_ZE("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         C_UA c_ua = new C_UA(lpsymbolizer.frame, "", c_ze, astring);
         c_ua.m1322(point);
         return c_ua.f790 == 0 && !lpsymbolizer.saveProblems(s) ? false : c_ua.f790 == 0 || c_ua.f790 == 1;
      }
   }

   static String m1451(String s) {
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
         } else if (LPSymbolizer.problems.m1780(s) != null) {
            LogicProgram.m972("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static String m1452(String s, String s1, C_d_C c_d_c) {
      Point point = c_d_c.f1045.getLocationOnScreen();
      Point point1 = new Point(point.x, point.y + c_d_c.f1045.getHeight());
      C_IF c_if = new C_IF(c_d_c.f1044.frame);
      c_if.setBackground(Color.WHITE);
      String[] astring = new String[]{"OK", "Cancel"};
      C_LB c_lb = new C_LB();
      c_lb.setLayout(new BorderLayout());
      c_lb.setBorder(new EmptyBorder(7, 15, 7, 15));
      C_ZE c_ze = new C_ZE(s1, 0, 1);
      c_ze.setBorder(new EmptyBorder(0, 0, 5, 0));
      Dimension dimension = c_ze.getPreferredSize();
      c_if.setBorder(new BevelBorder(1));
      c_if.m2023(dimension.width);
      c_if.invalidate();
      c_lb.add(c_ze, "North");
      c_lb.add(c_if, "East");
      C_UA c_ua = new C_UA(c_d_c.f1044.frame, s, c_lb, astring);
      c_ua.m1314(0);
      c_if.f425 = c_ua;
      c_if.requestFocus();
      c_ua.m1323(point1, true);
      return c_ua.f790 != 0 ? null : LogicProgram.m995(c_if.getText(), f854, maggie);
   }

   static void m1453(LPSymbolizer lpsymbolizer) {
      if (lpsymbolizer.directEntryDisabled) {
         C_UA.m1328("Feature Disabled", "Direct entry is disabled for this problem.", null, null);
      } else {
         Dimension dimension = new Dimension(20 * LogicProgram.f539, 7 * LogicProgram.f539);
         Point point = C_UA.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         C_IF c_if = new C_IF(LogicProgram.m995(lpsymbolizer.lastDirect, maggie, f854), lpsymbolizer.frame);
         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(c_if);
         C_UA c_ua = new C_UA(lpsymbolizer.frame, "Direct Symbolization", jscrollpane, astring);
         c_ua.setSize(dimension);
         c_ua.m1314(0);
         c_ua.m1317("symDirect");
         c_if.f425 = c_ua;
         c_if.requestFocus();
         c_ua.m1323(point, true);
         if (c_ua.f790 == 0) {
            lpsymbolizer.lastDirect = LogicProgram.m995(c_if.getText(), f854, maggie);
            lpsymbolizer.problem.m1709(lpsymbolizer.lastDirect);
            if (lpsymbolizer.checkDisabled || lpsymbolizer.errorMessagesDisabled) {
               return;
            }

            C_d_C c_d_c = lpsymbolizer.problem.m1715();
            if (c_d_c == null) {
               return;
            }

            C_RF c_rf = c_d_c.m1698();
            C_RF c_rf1 = lpsymbolizer.problem.m1698();
            if (c_rf1 == null || c_rf == null || c_rf1.m1236(c_rf, new C_MB())) {
               lpsymbolizer.problem.m1742(lpsymbolizer.problem.m1715());
            }
         }
      }
   }

   static void m1454(String s, String s1, C_D c_d) {
      if (c_d != null) {
         C_d_C c_d_c = (C_d_C)c_d.m449("target");
         C_0E c_0e = new C_0E();
         JScrollPane jscrollpane = new JScrollPane();
         C_s_D c_s_d = new C_s_D(LogicProgram.m1004(s1));
         c_s_d.setEditable(false);
         c_s_d.setBackground(dialogWhite);
         c_s_d.setCaretPosition(0);
         String[] astring = c_d.m445();
         jscrollpane.setViewportView(c_s_d);
         C_UA c_ua = new C_UA(c_0e, s, jscrollpane, astring);
         c_ua.m1317("symShowError");
         c_ua.m1315(c_d);
         Rectangle rectangle = c_ua.m1320();
         Rectangle rectangle1 = LogicProgram.m1035(c_d_c.f1045.f1438, null);
         Dimension dimension = rectangle == null ? new Dimension(32 * LogicProgram.f539, 15 * LogicProgram.f539) : rectangle.getSize();
         Point point = new Point(rectangle1.x + rectangle1.width / 2 - dimension.width / 2, rectangle1.y + rectangle1.height);
         if (rectangle != null) {
            rectangle.x = point.x;
            rectangle.y = point.y;
         }

         c_ua.setSize(dimension);
         c_ua.m1323(point, true);
         c_0e.dispose();
      }
   }
}
