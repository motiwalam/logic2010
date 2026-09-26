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

class C_w_A implements LogicConstants {
   static String[] f1424 = LogicProgram.symbols;

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
      Message message = C_FE.get(s);
      C_b_F c_b_f = null;
      if (message.buttons != null) {
         c_b_f = new C_b_F(message.buttons);
         if (hashtable1 != null) {
            c_b_f.m448(hashtable1);
         }
      }

      MessageDialog.showMessage(message, hashtable, point, c_b_f);
   }

   static String m2137(LPTruthAnalysis lptruthanalysis, String s) {
      if (s == null) {
         s = "User";
      }

      SizedPanel sizedpanel = new SizedPanel();
      EditableTextPane editabletextpane = new EditableTextPane(s, 300);
      C_ZE c_ze = new C_ZE("Please supply a name for this problem");
      editabletextpane.select(0, 2147483647);
      c_ze.setFocusable(false);
      sizedpanel.setLayout(new C_m_A(0));
      sizedpanel.add(c_ze);
      sizedpanel.add(editabletextpane);
      String[] astring = new String[]{"OK", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, "", sizedpanel, astring);
      messagedialog.m1314(0);
      editabletextpane.requestFocus();
      messagedialog.m1322(null);
      if (messagedialog.f790 != 0) {
         return null;
      } else {
         s = editabletextpane.getText();
         if ((s = s.trim()).equals("")) {
            Message message = Message.get("not006");
            MessageDialog.showMessage(Message.get("not006"), null, null, null);
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
         MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, "", c_z_e, astring2);
         messagedialog.m1322(null);
         if (messagedialog.f790 != 0) {
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
         MessageDialog messagedialog1 = new MessageDialog(lptruthanalysis.frame, "", c_ze, astring1);
         messagedialog1.m1322(null);
         if (messagedialog1.f790 != 0) {
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
         MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, "", c_ze, astring);
         messagedialog.m1322(point);
         if (messagedialog.f790 != 0) {
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
            if (LogicModule.eraseWork) {
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
            ProblemListView problemlistview = m2147(lptruthanalysis, false, false, null);
            problemlistview.setBackground(LogicConstants.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, ProblemSet.m1782("Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("truChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               int i = problemlistview.m1532(problemlistview.f899);
               LPTruthAnalysis lptruthanalysis1;
               if ((lptruthanalysis1 = LPTruthAnalysis.openInstance(i, true)) != null) {
                  lptruthanalysis1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lptruthanalysis.loadProblem(LPTruthAnalysis.problems.m1778(i));
                  lptruthanalysis.problemIndex = i;
                  LPTruthAnalysis.problems.m1776(lptruthanalysis.saveProblem(), i);
                  if (LogicModule.eraseWork) {
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
            ProblemListView problemlistview = m2147(lptruthanalysis, true, false, null);
            if (LPTruthAnalysis.submitExam) {
               problemlistview.clearSelection();
            }

            Vector vector = LPTruthAnalysis.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = TaggedRecord.m1493(s);
                  int j = LPTruthAnalysis.problems.m1767(s1);
                  if (j != -1) {
                     problemlistview.m1531(j, problemlistview.f899);
                  }
               }
            }

            problemlistview.setBackground(LogicConstants.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Submit", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, ProblemSet.m1782("Submit Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("truChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
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
            ProblemListView problemlistview = m2147(lptruthanalysis, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(Message.getText("not089")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            sizedpanel.add(c_s_b, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, ProblemSet.m1782("Upload Problems"), sizedpanel, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("truChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
            }
         }
      }

      lptruthanalysis.requestFocus();
      return aint;
   }

   static void m2144(LPTruthAnalysis lptruthanalysis) {
      synchronized (LPTruthAnalysis.problems) {
         ProblemListView problemlistview = m2147(lptruthanalysis, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, ProblemSet.m1782("Delete Problems"), jscrollpane, astring);
         problemlistview.m1528(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.m1317("truChosen");
         problemlistview.requestFocus();
         messagedialog.m1323(MessageDialog.m1321(dimension), true);
         if (messagedialog.f790 == 0) {
            int[] aint1 = problemlistview.m1533(problemlistview.f899);
            if (aint1.length != 0) {
               Message message1 = Message.get("not091");
               C_b_E c_b_e1 = new C_b_E(message1.buttons);
               MessageDialog.showMessage(message1, null, null, c_b_e1);
               if (c_b_e1.f1027 == 0) {
                  int k = aint1.length;

                  while (--k >= 0) {
                     int l = aint1[k];
                     C_f_C c_f_c1 = (C_f_C)LPTruthAnalysis.problems.m1779(l);
                     if (c_f_c1 != null) {
                        TaggedRecord taggedrecord1 = new TaggedRecord(c_f_c1.name);
                        if (!LPTruthAnalysis.isExample(taggedrecord1.getName())) {
                           c_f_c1.name = LPTruthAnalysis.removeWork(taggedrecord1);
                           c_f_c1.state = 0;
                           LPTruthAnalysis lptruthanalysis2 = LPTruthAnalysis.openInstance(l, false);
                           if (lptruthanalysis2 != null) {
                              lptruthanalysis2.loadProblem(c_f_c1.name);
                           }
                        }
                     }
                  }
               }

               LPTruthAnalysis.saveProblems();
            }
         } else if (messagedialog.f790 == 1) {
            int[] aint = problemlistview.m1533(problemlistview.f899);
            if (aint.length != 0) {
               Message message = Message.get("not090");
               C_b_E c_b_e = new C_b_E(message.buttons);
               MessageDialog.showMessage(message, null, null, c_b_e);
               if (c_b_e.f1027 == 0) {
                  int i = aint.length;

                  while (--i >= 0) {
                     int j = aint[i];
                     C_f_C c_f_c = (C_f_C)LPTruthAnalysis.problems.m1779(j);
                     if (c_f_c != null) {
                        TaggedRecord taggedrecord = new TaggedRecord(c_f_c.name);
                        if (!LPTruthAnalysis.isExercise(taggedrecord.getName())) {
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
            ProblemListView problemlistview = m2147(lptruthanalysis, true, false, LPTruthAnalysis.noPrint);
            problemlistview.setBackground(LogicConstants.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Print", "Print Results", "Print List", "Cancel"};
            problemlistview.f898 = 3;
            MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, ProblemSet.m1782("Print Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("truChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
            } else if (messagedialog.f790 == 1) {
               C_A.m71(problemlistview.m1533(problemlistview.f899));
            } else if (messagedialog.f790 == 2) {
               C_c_D.m1671(problemlistview.m1533(problemlistview.f899));
            }
         }
      }

      lptruthanalysis.requestFocus();
      return aint;
   }

   static void m2146(LPTruthAnalysis lptruthanalysis) {
      if (m2148(lptruthanalysis, null)) {
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         EditableTextPane editabletextpane = new EditableTextPane();
         String[] astring = new String[]{"OK", "Cancel"};
         editabletextpane.setBackground(Color.WHITE);
         jscrollpane.setViewportView(editabletextpane);
         MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, "Submitted Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.m1314(0);
         messagedialog.m1317("truSubmitted");
         editabletextpane.requestFocus();
         messagedialog.m1323(point, true);
         if (messagedialog.f790 == 0) {
            lptruthanalysis.loadProblem(editabletextpane.getText());
         }
      }

      lptruthanalysis.requestFocus();
   }

   static ProblemListView m2147(LPTruthAnalysis lptruthanalysis, boolean flag, boolean flag1, ProblemSelector problemselector) {
      return LPTruthAnalysis.problems.m1781(lptruthanalysis, LPTruthAnalysis.exercises, flag, flag1, LPTruthAnalysis.monoProbs, problemselector);
   }

   static boolean m2148(LPTruthAnalysis lptruthanalysis, Point point) {
      String s = lptruthanalysis.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         C_ZE c_ze = new C_ZE("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, "", c_ze, astring);
         messagedialog.m1322(point);
         return messagedialog.f790 == 0 && !lptruthanalysis.saveProblems(s) ? false : messagedialog.f790 == 0 || messagedialog.f790 == 1;
      }
   }

   static void m2149(LPTruthAnalysis lptruthanalysis) {
      if (m2148(lptruthanalysis, null)) {
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 15 * LogicProgram.fontSize);
         Point point = MessageDialog.m1321(dimension);
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new BorderLayout());
         JScrollPane jscrollpane = new JScrollPane();
         C_NE c_ne = new C_NE("Truth Table Only");
         jpanel.add(jscrollpane, "Center");
         jpanel.add(c_ne, "South");
         FormulaEntryField formulaentryfield = new FormulaEntryField(lptruthanalysis.frame);
         if (lptruthanalysis.lastUserProblem != null) {
            formulaentryfield.setText(LogicProgram.m995(lptruthanalysis.lastUserProblem, maggie, f1424));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(formulaentryfield);
         formulaentryfield.setBackground(Color.WHITE);
         MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, "User Problem", jpanel, astring);
         messagedialog.setSize(dimension);
         messagedialog.m1314(0);
         messagedialog.m1317("truUser");
         formulaentryfield.f425 = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.m1323(point, true);
         if (messagedialog.f790 == 0) {
            String s = formulaentryfield.getText();
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
