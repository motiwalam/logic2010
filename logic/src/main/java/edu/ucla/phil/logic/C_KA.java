package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

class C_KA implements LogicConstants {
   static String[] f443 = LogicProgram.symbols;

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
      Message message = C_ND.get(s);
      C_QA c_qa = null;
      if (message.buttons != null) {
         c_qa = new C_QA(message.buttons);
         if (hashtable1 != null) {
            c_qa.m448(hashtable1);
         }
      }

      MessageDialog.showMessage(message, hashtable, point, c_qa);
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
         MessageDialog messagedialog = new MessageDialog(lpparsing.frame, "", c_z_e, astring2);
         messagedialog.m1322(null);
         if (messagedialog.f790 != 0) {
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
         MessageDialog messagedialog1 = new MessageDialog(lpparsing.frame, "", c_ze, astring1);
         messagedialog1.m1322(null);
         if (messagedialog1.f790 != 0) {
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
      MessageDialog messagedialog = new MessageDialog(lpparsing.frame, "", c_ze, astring);
      messagedialog.m1322(point);
      if (messagedialog.f790 != 0) {
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
            if (LogicModule.eraseWork) {
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
            ProblemListView problemlistview = m755(lpparsing, false, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpparsing.frame, ProblemSet.m1782("Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("parChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               int i = problemlistview.m1532(problemlistview.f899);
               LPParsing lpparsing1;
               if ((lpparsing1 = LPParsing.openInstance(i, true)) != null) {
                  lpparsing1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lpparsing.loadProblem(LPParsing.problems.m1778(i));
                  lpparsing.problemIndex = i;
                  LPParsing.problems.m1776(lpparsing.saveProblem(), i);
                  if (LogicModule.eraseWork) {
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
            ProblemListView problemlistview = m755(lpparsing, true, false, null);
            if (LPParsing.submitExam) {
               problemlistview.clearSelection();
            }

            Vector vector = LPParsing.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = TaggedRecord.m1493(s);
                  int j = LPParsing.problems.m1767(s1);
                  if (j != -1) {
                     problemlistview.m1531(j, problemlistview.f899);
                  }
               }
            }

            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Submit", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpparsing.frame, ProblemSet.m1782("Submit Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("parChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
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
            ProblemListView problemlistview = m755(lpparsing, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(Message.getText("not089")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            sizedpanel.add(c_s_b, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpparsing.frame, ProblemSet.m1782("Upload Problems"), sizedpanel, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("parChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
            }
         }
      }

      lpparsing.requestFocus();
      return aint;
   }

   static void m752(LPParsing lpparsing) {
      synchronized (LPParsing.problems) {
         ProblemListView problemlistview = m755(lpparsing, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpparsing.frame, ProblemSet.m1782("Delete Problems"), jscrollpane, astring);
         problemlistview.m1528(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.m1317("parChosen");
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
                     C_l_D c_l_d1 = (C_l_D)LPParsing.problems.m1779(l);
                     if (c_l_d1 != null) {
                        TaggedRecord taggedrecord1 = new TaggedRecord(c_l_d1.name);
                        if (!LPParsing.isExample(taggedrecord1.getName())) {
                           c_l_d1.name = LPParsing.removeWork(taggedrecord1);
                           c_l_d1.state = 0;
                           LPParsing lpparsing2 = LPParsing.openInstance(l, false);
                           if (lpparsing2 != null) {
                              lpparsing2.loadProblem(c_l_d1.name);
                           }
                        }
                     }
                  }
               }

               LPParsing.saveProblems();
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
                     C_l_D c_l_d = (C_l_D)LPParsing.problems.m1779(j);
                     if (c_l_d != null) {
                        TaggedRecord taggedrecord = new TaggedRecord(c_l_d.name);
                        if (!LPParsing.isExercise(taggedrecord.getName())) {
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
            ProblemListView problemlistview = m755(lpparsing, true, false, LPParsing.noPrint);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Print Results", "Print List", "Cancel"};
            problemlistview.f898 = 2;
            MessageDialog messagedialog = new MessageDialog(lpparsing.frame, ProblemSet.m1782("Print Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("parChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               C_o_E.m2009(problemlistview.m1533(problemlistview.f899));
            } else if (messagedialog.f790 == 1) {
               C_AE.m234(problemlistview.m1533(problemlistview.f899));
            }
         }
      }

      lpparsing.requestFocus();
      return (int[])object;
   }

   static void m754(LPParsing lpparsing) {
      if (m756(lpparsing, null)) {
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
         messagedialog.m1317("parSubmitted");
         editabletextpane.requestFocus();
         messagedialog.m1323(point, true);
         moduleframe.dispose();
         if (messagedialog.f790 == 0) {
            lpparsing.loadProblem(editabletextpane.getText());
         }
      }

      lpparsing.requestFocus();
   }

   static ProblemListView m755(LPParsing lpparsing, boolean flag, boolean flag1, ProblemSelector problemselector) {
      return LPParsing.problems.m1781(lpparsing, LPParsing.exercises, flag, flag1, LPParsing.monoProbs, problemselector);
   }

   static boolean m756(LPParsing lpparsing, Point point) {
      String s = lpparsing.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lpparsing.frame.show();
         C_ZE c_ze = new C_ZE("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpparsing.frame, "", c_ze, astring);
         messagedialog.m1322(point);
         return messagedialog.f790 == 0 && !lpparsing.saveProblems(s) ? false : messagedialog.f790 == 0 || messagedialog.f790 == 1;
      }
   }

   static String m757(String s) {
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
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         FormulaEntryField formulaentryfield = new FormulaEntryField(moduleframe);
         if (lpparsing.lastUserProblem != null) {
            formulaentryfield.setText(LogicProgram.m995(lpparsing.lastUserProblem, maggie, f443));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(formulaentryfield);
         formulaentryfield.setBackground(Color.WHITE);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "User Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.m1314(0);
         messagedialog.m1317("parUser");
         formulaentryfield.f425 = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.m1323(point, true);
         moduleframe.dispose();
         if (messagedialog.f790 == 0) {
            String s = formulaentryfield.getText();
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
