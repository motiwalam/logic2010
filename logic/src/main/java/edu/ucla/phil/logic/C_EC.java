package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

class C_EC implements LogicConstants {
   static String[] f295 = LogicProgram.symbols;

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
      Message message = C_BF.get(s);
      C_i_E c_i_e = null;
      if (message.buttons != null) {
         c_i_e = new C_i_E(message.buttons);
         if (hashtable1 != null) {
            c_i_e.m448(hashtable1);
         }
      }

      MessageDialog.showMessage(message, hashtable, point, c_i_e);
   }

   static String m500(String s) {
      if (s == null) {
         s = "User";
      }

      SizedPanel sizedpanel = new SizedPanel();
      EditableTextPane editabletextpane = new EditableTextPane(s, 300);
      C_ZE c_ze = new C_ZE("Please supply a name for this problem");
      ModuleFrame moduleframe = new ModuleFrame();
      editabletextpane.select(0, 2147483647);
      c_ze.setFocusable(false);
      sizedpanel.setLayout(new C_m_A(0));
      sizedpanel.add(c_ze);
      sizedpanel.add(editabletextpane);
      String[] astring = new String[]{"OK", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(moduleframe, "", sizedpanel, astring);
      messagedialog.m1314(0);
      editabletextpane.requestFocus();
      messagedialog.m1322(null);
      moduleframe.dispose();
      if (messagedialog.f790 != 0) {
         return null;
      } else {
         s = editabletextpane.getText();
         if ((s = s.trim()).equals("")) {
            MessageDialog.showMessage(Message.get("not006"), null, null, null);
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
         MessageDialog messagedialog = new MessageDialog(lprecognition.frame, "", c_ze, astring);
         messagedialog.m1322(point);
         return messagedialog.f790 == 0 && !lprecognition.saveProblems(s) ? false : messagedialog.f790 == 0 || messagedialog.f790 == 1;
      }
   }

   static boolean m502(LPRecognition lprecognition, Point point) {
      if (lprecognition.problem.f128 == null) {
         return false;
      } else {
         C_ZE c_ze = new C_ZE("Delete the work on this problem?");
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lprecognition.frame, "", c_ze, astring);
         messagedialog.m1322(point);
         if (messagedialog.f790 != 0) {
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
         MessageDialog messagedialog = new MessageDialog(lprecognition.frame, "", c_z_e, astring2);
         messagedialog.m1322(null);
         if (messagedialog.f790 != 0) {
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
         MessageDialog messagedialog1 = new MessageDialog(lprecognition.frame, "", c_ze, astring1);
         messagedialog1.m1322(null);
         if (messagedialog1.f790 != 0) {
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
            if (LogicModule.eraseWork) {
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
            ProblemListView problemlistview = m512(lprecognition, false, false, null);
            problemlistview.setBackground(LogicConstants.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lprecognition.frame, ProblemSet.m1782("Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("recChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               int i = problemlistview.m1532(problemlistview.f899);
               LPRecognition lprecognition1;
               if ((lprecognition1 = LPRecognition.openInstance(i, true)) != null) {
                  lprecognition1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lprecognition.loadProblem(LPRecognition.problems.m1778(i));
                  lprecognition.problemIndex = i;
                  LPRecognition.problems.m1776(lprecognition.saveProblem(), i);
                  if (LogicModule.eraseWork) {
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
            ProblemListView problemlistview = m512(lprecognition, true, false, null);
            if (LPRecognition.submitExam) {
               problemlistview.clearSelection();
            }

            Vector vector = LPRecognition.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = TaggedRecord.m1493(s);
                  int j = LPRecognition.problems.m1767(s1);
                  if (j != -1) {
                     problemlistview.m1531(j, problemlistview.f899);
                  }
               }
            }

            problemlistview.setBackground(LogicConstants.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Submit", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lprecognition.frame, ProblemSet.m1782("Submit Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("recChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
            }
         }
      }

      lprecognition.requestFocus();
      return aint;
   }

   static void m507(LPRecognition lprecognition) {
      if (m501(lprecognition, null)) {
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         EditableTextPane editabletextpane = new EditableTextPane();
         String[] astring = new String[]{"OK", "Cancel"};
         editabletextpane.setBackground(Color.WHITE);
         jscrollpane.setViewportView(editabletextpane);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "Submitted Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.m1314(0);
         messagedialog.m1317("recSubmitted");
         editabletextpane.requestFocus();
         messagedialog.m1323(point, true);
         moduleframe.dispose();
         if (messagedialog.f790 == 0) {
            lprecognition.loadProblem(editabletextpane.getText());
         }
      }

      lprecognition.requestFocus();
   }

   static int[] m508(LPRecognition lprecognition) {
      int[] aint = null;
      if (m501(lprecognition, null)) {
         synchronized (LPRecognition.problems) {
            ProblemListView problemlistview = m512(lprecognition, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(Message.getText("not089")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            sizedpanel.add(c_s_b, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lprecognition.frame, ProblemSet.m1782("Upload Problems"), sizedpanel, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("derChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
            }
         }
      }

      lprecognition.requestFocus();
      return aint;
   }

   static void m509(LPRecognition lprecognition) {
      synchronized (LPRecognition.problems) {
         ProblemListView problemlistview = m512(lprecognition, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lprecognition.frame, ProblemSet.m1782("Delete Problems"), jscrollpane, astring);
         problemlistview.m1528(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.m1317("recChosen");
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
                     C_ED c_ed1 = (C_ED)LPRecognition.problems.m1779(l);
                     if (c_ed1 != null) {
                        TaggedRecord taggedrecord1 = new TaggedRecord(c_ed1.name);
                        if (!LPRecognition.isExample(taggedrecord1.getName())) {
                           c_ed1.name = LPRecognition.removeWork(taggedrecord1);
                           c_ed1.state = 0;
                           LPRecognition lprecognition2 = LPRecognition.openInstance(l, false);
                           if (lprecognition2 != null) {
                              lprecognition2.loadProblem(c_ed1.name);
                           }
                        }
                     }
                  }
               }

               LPRecognition.saveProblems();
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
                     C_ED c_ed = (C_ED)LPRecognition.problems.m1779(j);
                     if (c_ed != null) {
                        TaggedRecord taggedrecord = new TaggedRecord(c_ed.name);
                        if (!LPRecognition.isExercise(taggedrecord.getName())) {
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
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         FormulaEntryField formulaentryfield = new FormulaEntryField(moduleframe);
         if (lprecognition.lastUserProblem != null) {
            formulaentryfield.setText(LogicProgram.m995(lprecognition.lastUserProblem, maggie, f295));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(formulaentryfield);
         formulaentryfield.setBackground(Color.WHITE);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "User Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.m1314(0);
         messagedialog.m1317("recUser");
         formulaentryfield.f425 = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.m1323(point, true);
         moduleframe.dispose();
         if (messagedialog.f790 == 0) {
            String s = formulaentryfield.getText();
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
            ProblemListView problemlistview = m512(lprecognition, true, false, LPRecognition.noPrint);
            problemlistview.setBackground(LogicConstants.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Print Results", "Print List", "Cancel"};
            problemlistview.f898 = 2;
            MessageDialog messagedialog = new MessageDialog(lprecognition.frame, ProblemSet.m1782("Print Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("recChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               C_c_.m1665(problemlistview.m1533(problemlistview.f899));
            } else if (messagedialog.f790 == 1) {
               C_UF.m1358(problemlistview.m1533(problemlistview.f899));
            }
         }
      }

      lprecognition.requestFocus();
      return (int[])object;
   }

   static ProblemListView m512(LPRecognition lprecognition, boolean flag, boolean flag1, ProblemSelector problemselector) {
      return LPRecognition.problems.m1781(lprecognition, LPRecognition.exercises, flag, flag1, LPRecognition.monoProbs, problemselector);
   }
}
