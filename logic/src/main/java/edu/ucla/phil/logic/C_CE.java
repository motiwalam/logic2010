package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

class C_CE implements C_h_B {
   static String[] f265 = LogicProgram.symbols;

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
      Message message = C_LA.get(s);
      C_GB c_gb = null;
      if (message.buttons != null) {
         c_gb = new C_GB(message.buttons);
         if (hashtable1 != null) {
            c_gb.m448(hashtable1);
         }
      }

      MessageDialog.showMessage(message, hashtable, point, c_gb);
   }

   static boolean m431(LPInvalidation lpinvalidation, Point point) {
      String s = lpinvalidation.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lpinvalidation.frame.show();
         C_ZE c_ze = new C_ZE("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, "", c_ze, astring);
         messagedialog.m1322(point);
         return messagedialog.f790 == 0 && !lpinvalidation.saveProblems(s) ? false : messagedialog.f790 == 0 || messagedialog.f790 == 1;
      }
   }

   static String m432(String s) {
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
         MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, "", c_z_e, astring2);
         messagedialog.m1322(null);
         if (messagedialog.f790 != 0) {
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
         MessageDialog messagedialog1 = new MessageDialog(lpinvalidation.frame, "", c_ze, astring1);
         messagedialog1.m1322(null);
         if (messagedialog1.f790 != 0) {
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
         MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, "", c_ze, astring);
         messagedialog.m1322(point);
         if (messagedialog.f790 != 0) {
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
            if (LogicModule.eraseWork) {
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
            ProblemListView problemlistview = m442(lpinvalidation, false, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, ProblemSet.m1782("Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("invChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               int i = problemlistview.m1532(problemlistview.f899);
               LPInvalidation lpinvalidation1;
               if ((lpinvalidation1 = LPInvalidation.openInstance(i, true)) != null) {
                  lpinvalidation1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lpinvalidation.loadProblem(LPInvalidation.problems.m1778(i));
                  lpinvalidation.problemIndex = i;
                  LPInvalidation.problems.m1776(lpinvalidation.saveProblem(), i);
                  if (LogicModule.eraseWork) {
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
            ProblemListView problemlistview = m442(lpinvalidation, true, false, null);
            if (LPInvalidation.submitExam) {
               problemlistview.clearSelection();
            }

            Vector vector = LPInvalidation.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = TaggedRecord.m1493(s);
                  int j = LPInvalidation.problems.m1767(s1);
                  if (j != -1) {
                     problemlistview.m1531(j, problemlistview.f899);
                  }
               }
            }

            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(Message.getText("not081")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            sizedpanel.add(c_s_b, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Submit", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, ProblemSet.m1782("Submit Problems"), sizedpanel, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("invChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
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
            ProblemListView problemlistview = m442(lpinvalidation, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(Message.getText("not089")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            sizedpanel.add(c_s_b, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, ProblemSet.m1782("Upload Problems"), sizedpanel, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("invChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
            }
         }
      }

      lpinvalidation.requestFocus();
      return aint;
   }

   static void m439(LPInvalidation lpinvalidation) {
      synchronized (LPInvalidation.problems) {
         ProblemListView problemlistview = m442(lpinvalidation, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, ProblemSet.m1782("Delete Problems"), jscrollpane, astring);
         problemlistview.m1528(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.m1317("invChosen");
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
                     C_EE c_ee1 = (C_EE)LPInvalidation.problems.m1779(l);
                     if (c_ee1 != null) {
                        TaggedRecord taggedrecord1 = new TaggedRecord(c_ee1.name);
                        if (!LPInvalidation.isExample(taggedrecord1.getName())) {
                           c_ee1.name = LPInvalidation.removeWork(taggedrecord1);
                           c_ee1.state = 0;
                           LPInvalidation lpinvalidation2 = LPInvalidation.openInstance(l, false);
                           if (lpinvalidation2 != null) {
                              lpinvalidation2.loadProblem(c_ee1.name);
                           }
                        }
                     }
                  }
               }

               LPInvalidation.saveProblems();
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
                     C_EE c_ee = (C_EE)LPInvalidation.problems.m1779(j);
                     if (c_ee != null) {
                        TaggedRecord taggedrecord = new TaggedRecord(c_ee.name);
                        if (!LPInvalidation.isExercise(taggedrecord.getName())) {
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
            ProblemListView problemlistview = m442(lpinvalidation, true, false, LPInvalidation.noPrint);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Print", "Print Results", "Print List", "Cancel"};
            problemlistview.f898 = 3;
            MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, ProblemSet.m1782("Print Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("invChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               C_q_C.m2038(problemlistview.m1533(problemlistview.f899));
            } else if (messagedialog.f790 == 1) {
               C_e_F.m1800(problemlistview.m1533(problemlistview.f899));
            } else if (messagedialog.f790 == 2) {
               C_k_D.m1907(problemlistview.m1533(problemlistview.f899));
            }
         }
      }

      lpinvalidation.requestFocus();
      return (int[])object;
   }

   static void m441(LPInvalidation lpinvalidation) {
      if (m431(lpinvalidation, null)) {
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
         messagedialog.m1317("invSubmitted");
         editabletextpane.requestFocus();
         messagedialog.m1323(point, true);
         moduleframe.dispose();
         if (messagedialog.f790 == 0) {
            lpinvalidation.loadProblem(editabletextpane.getText());
         }
      }

      lpinvalidation.requestFocus();
   }

   static ProblemListView m442(LPInvalidation lpinvalidation, boolean flag, boolean flag1, ProblemSelector problemselector) {
      return LPInvalidation.problems.m1781(lpinvalidation, LPInvalidation.exercises, flag, flag1, LPInvalidation.monoProbs, problemselector);
   }

   static void m443(LPInvalidation lpinvalidation) {
      if (m431(lpinvalidation, null)) {
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         FormulaEntryField formulaentryfield = new FormulaEntryField(moduleframe);
         if (lpinvalidation.lastUserProblem != null) {
            formulaentryfield.setText(LogicProgram.m995(lpinvalidation.lastUserProblem, maggie, f265));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         formulaentryfield.setBackground(Color.WHITE);
         jscrollpane.setViewportView(formulaentryfield);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "User Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.m1314(0);
         messagedialog.m1317("invUser");
         formulaentryfield.f425 = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.m1323(point, true);
         moduleframe.dispose();
         if (messagedialog.f790 == 0) {
            String s = formulaentryfield.getText();
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
      ModuleFrame moduleframe = new ModuleFrame();
      JScrollPane jscrollpane = new JScrollPane();
      C_e_A c_e_a = new C_e_A(c_ie, i);
      String[] astring = new String[]{"OK", "Cancel"};
      jscrollpane.setViewportView(c_e_a);
      MessageDialog messagedialog = new MessageDialog(moduleframe, "Extend " + c_ie.m710(), jscrollpane, astring);
      messagedialog.pack();
      Point point = MessageDialog.m1321(messagedialog.getSize());
      messagedialog.m1314(0);
      messagedialog.m1323(point, true);
      moduleframe.dispose();
      if (messagedialog.f790 == 0) {
         c_e_a.m1756();
         return true;
      } else {
         return false;
      }
   }
}
