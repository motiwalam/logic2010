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
   static String[] f854 = LogicProgram.symbols;

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
      Message message = C_h_E.get(s);
      C_RE c_re = null;
      if (message.buttons != null) {
         c_re = new C_RE(message.buttons);
         if (hashtable1 != null) {
            c_re.m448(hashtable1);
         }
      }

      MessageDialog.showMessage(message, hashtable, point, c_re);
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
         MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, "", c_z_e, astring2);
         messagedialog.m1322(null);
         if (messagedialog.f790 != 0) {
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
         MessageDialog messagedialog1 = new MessageDialog(lpsymbolizer.frame, "", c_ze, astring1);
         messagedialog1.m1322(null);
         if (messagedialog1.f790 != 0) {
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
         MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, "", c_ze, astring);
         messagedialog.m1322(point);
         if (messagedialog.f790 != 0) {
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
            if (LogicModule.eraseWork) {
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
            ProblemListView problemlistview = m1441(lpsymbolizer, false, true, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, ProblemSet.m1782("Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("symChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               int i = problemlistview.m1532(problemlistview.f899);
               LPSymbolizer lpsymbolizer1;
               if ((lpsymbolizer1 = LPSymbolizer.openInstance(i, true)) != null) {
                  lpsymbolizer1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lpsymbolizer.loadProblem(LPSymbolizer.problems.m1778(i));
                  lpsymbolizer.problemIndex = i;
                  LPSymbolizer.problems.m1776(lpsymbolizer.saveProblem(), i);
                  if (LogicModule.eraseWork) {
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
      ProblemListView problemlistview = m1441(lpsymbolizer, false, true, null);
      JScrollPane jscrollpane = new JScrollPane();
      jscrollpane.setViewportView(problemlistview);
      String[] astring = new String[]{"Copy", "Cancel"};
      Object object1 = c_y_b == null ? null : LogicProgram.m1045(c_y_b);
      if (object1 == null) {
         object1 = lpsymbolizer.frame;
      }

      MessageDialog messagedialog = new MessageDialog((Frame)object1, ProblemSet.m1782("Problems"), jscrollpane, astring);
      problemlistview.m1528(messagedialog, 0);
      Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 25 * LogicProgram.fontSize);
      messagedialog.setSize(dimension);
      messagedialog.m1317("symSchemeChosen");
      problemlistview.requestFocus();
      messagedialog.m1323(MessageDialog.m1321(dimension), true);
      if (messagedialog.f790 == 0) {
         int i = problemlistview.m1532(problemlistview.f899);
         if (i != -1) {
            return LPSymbolizer.getProblemScheme(TaggedRecord.m1493(LPSymbolizer.problems.m1778(i)));
         }
      }

      return null;
   }

   static int[] m1436(LPSymbolizer lpsymbolizer) {
      int[] aint = null;
      if (m1450(lpsymbolizer, null)) {
         synchronized (LPSymbolizer.problems) {
            ProblemListView problemlistview = m1441(lpsymbolizer, true, true, null);
            if (LPSymbolizer.submitExam) {
               problemlistview.clearSelection();
            }

            Vector vector = LPSymbolizer.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = TaggedRecord.m1493(s);
                  int j = LPSymbolizer.problems.m1767(s1);
                  if (j != -1) {
                     problemlistview.m1531(j, problemlistview.f899);
                  }
               }
            }

            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Submit", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, ProblemSet.m1782("Submit Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("symChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
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
            ProblemListView problemlistview = m1441(lpsymbolizer, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(Message.getText("not089")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            sizedpanel.add(c_s_b, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, ProblemSet.m1782("Upload Problems"), sizedpanel, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("symChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
            }
         }
      }

      lpsymbolizer.requestFocus();
      return aint;
   }

   static void m1438(LPSymbolizer lpsymbolizer) {
      synchronized (LPSymbolizer.problems) {
         ProblemListView problemlistview = m1441(lpsymbolizer, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, ProblemSet.m1782("Delete Problems"), jscrollpane, astring);
         problemlistview.m1528(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.m1317("symChosen");
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
                     C__C c__c1 = (C__C)LPSymbolizer.problems.m1779(l);
                     if (c__c1 != null) {
                        TaggedRecord taggedrecord1 = new TaggedRecord(c__c1.name);
                        if (!LPSymbolizer.isExample(taggedrecord1.getName())) {
                           c__c1.name = LPSymbolizer.removeWork(taggedrecord1);
                           c__c1.state = 0;
                           LPSymbolizer lpsymbolizer2 = LPSymbolizer.openInstance(l, false);
                           if (lpsymbolizer2 != null) {
                              lpsymbolizer2.loadProblem(c__c1.name);
                           }
                        }
                     }
                  }
               }

               LPSymbolizer.saveProblems();
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
                     C__C c__c = (C__C)LPSymbolizer.problems.m1779(j);
                     if (c__c != null) {
                        TaggedRecord taggedrecord = new TaggedRecord(c__c.name);
                        if (!LPSymbolizer.isExercise(taggedrecord.getName())) {
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
            ProblemListView problemlistview = m1441(lpsymbolizer, true, true, LPSymbolizer.noPrint);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Print", "Print Results", "Print List", "Cancel"};
            problemlistview.f898 = 3;
            MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, ProblemSet.m1782("Print Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("symChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
            } else if (messagedialog.f790 == 1) {
               C_c_A.m1666(problemlistview.m1533(problemlistview.f899));
            } else if (messagedialog.f790 == 2) {
               C_WF.m1460(problemlistview.m1533(problemlistview.f899));
            }
         }
      }

      lpsymbolizer.requestFocus();
      return aint;
   }

   static void m1440(LPSymbolizer lpsymbolizer) {
      if (m1450(lpsymbolizer, null)) {
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
         messagedialog.m1317("symSubmitted");
         editabletextpane.requestFocus();
         messagedialog.m1323(point, true);
         moduleframe.dispose();
         if (messagedialog.f790 == 0) {
            lpsymbolizer.loadProblem(editabletextpane.getText());
         }
      }

      lpsymbolizer.requestFocus();
   }

   static ProblemListView m1441(LPSymbolizer lpsymbolizer, boolean flag, boolean flag1, ProblemSelector problemselector) {
      return LPSymbolizer.problems.m1781(lpsymbolizer, LPSymbolizer.exercises, flag, flag1, LPSymbolizer.monoProbs, problemselector);
   }

   static void m1442(LPSymbolizer lpsymbolizer) {
      m1443(lpsymbolizer, null);
   }

   static void m1443(LPSymbolizer lpsymbolizer, String s) {
      if (s != null || m1450(lpsymbolizer, null)) {
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         FormulaEntryField formulaentryfield = new FormulaEntryField(moduleframe);
         if (s != null) {
            formulaentryfield.setText(s);
         } else if (LPSymbolizer.lastUserProblem != null) {
            formulaentryfield.setText(LPSymbolizer.lastUserProblem);
         }

         C_I c_i = new C_I("OK:ok.Clear:clear.Cancel:cancel;0");
         c_i.m447("edit", formulaentryfield);
         c_i.m447("symbolizer", lpsymbolizer);
         jscrollpane.setViewportView(formulaentryfield);
         formulaentryfield.setBackground(Color.WHITE);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "User Problem", jscrollpane, c_i.m445());
         messagedialog.setSize(dimension);
         messagedialog.m1314(0);
         messagedialog.m1317("symUser");
         formulaentryfield.f425 = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.m1323(point, true);
         moduleframe.dispose();
         if (messagedialog.f790 == 0) {
            String s1 = formulaentryfield.getText();
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
      ModuleFrame moduleframe = new ModuleFrame();
      Dimension dimension = new Dimension(25 * LogicProgram.fontSize, 15 * LogicProgram.fontSize);
      Point point = MessageDialog.m1321(dimension);
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
      MessageDialog messagedialog;
      if (s == null) {
         messagedialog = new MessageDialog(moduleframe, "Create Scheme", jscrollpane, c_r_e.m445());
      } else {
         messagedialog = new MessageDialog(moduleframe, "Edit Scheme", jscrollpane, c_r_e.m445());
      }

      messagedialog.setSize(dimension);
      messagedialog.m1314(0);
      messagedialog.m1317("symUserScheme");
      messagedialog.m1315(c_r_e);
      messagedialog.m1316("OK", "Accept scheme");
      messagedialog.m1316("Clear", "Reset scheme");
      messagedialog.m1316("Browse", "Load scheme from another problem");
      c_y_b.requestFocus();
      messagedialog.m1323(point, true);
      moduleframe.dispose();
      if (messagedialog.f790 == 0) {
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
      if (lpsymbolizer.problem.f1058 || ServerConnection.f480 && !ServerConnection.f482 && UserSetup.m2102("symAnswerPrint", "instructor") == null) {
         if (lpsymbolizer.problem.f1052 != null && !lpsymbolizer.dontChange) {
            lpsymbolizer.problem.m1700();
         }

         ProblemListView problemlistview = new ProblemListView(true);
         Vector vector = lpsymbolizer.problem.f1056;
         C_d_C c_d_c = new C_d_C(lpsymbolizer);
         int i = vector == null ? 0 : vector.size();

         for (int j = 0; j < i; j++) {
            c_d_c.m1703(new TaggedRecord((String)vector.elementAt(j)));
            String s = c_d_c.toString();
            C_QC c_qc = new C_QC(s, 2);
            c_qc.m1187(s);
            c_qc.setOpaque(true);
            problemlistview.m1526(c_qc);
         }

         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         C_QD c_qd;
         if (!lpsymbolizer.problem.f1058 || lpsymbolizer.dontChange) {
            c_qd = new C_QD("Use:load.OK:ok;0");
         } else if (lpsymbolizer.problem.m1717()) {
            c_qd = new C_QD("Add:warn.Use:load.Delete:delete.Replace:warn.Help:help.OK:ok;1");
         } else {
            c_qd = new C_QD("Add:add.Use:load.Delete:delete.Replace:replace.Help:help.OK:ok;1");
         }

         c_qd.m447("symbolizer", lpsymbolizer);
         c_qd.m447("list", problemlistview);
         MessageDialog messagedialog = new MessageDialog(null, "Answer Manager", jscrollpane, c_qd.m445());
         messagedialog.m1316("Add", "Add the answer currently in the workspace.");
         messagedialog.m1316("Use", "Load the selected answer into the workspace.");
         messagedialog.m1316("Delete", "Delete the selected answer(s).");
         messagedialog.m1316("Replace", "Replace the selected answer(s) with the answer currently in the workspace.");
         messagedialog.m1316("OK", "OK as is");
         messagedialog.m1315(c_qd);
         problemlistview.m1528(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 40 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.m1317("symEditAnswer");
         problemlistview.requestFocus();
         messagedialog.m1323(MessageDialog.m1321(dimension), true);
      } else {
         m1427("SymNot005");
      }
   }

   static int[] m1448(Frame frame) {
      Object object = null;
      ProblemListView problemlistview = m1449(true);
      JScrollPane jscrollpane = new JScrollPane();
      jscrollpane.setViewportView(problemlistview);
      String[] astring = new String[]{"Print", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(frame, ProblemSet.m1782("Print Answers"), jscrollpane, astring);
      problemlistview.m1528(messagedialog, 0);
      Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
      messagedialog.setSize(dimension);
      messagedialog.m1317("symChosen");
      problemlistview.requestFocus();
      messagedialog.m1323(MessageDialog.m1321(dimension), true);
      if (messagedialog.f790 == 0) {
         C_PD.m1179(problemlistview.m1533(problemlistview.f899));
      }

      return (int[])object;
   }

   static ProblemListView m1449(boolean flag) {
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
         MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, "", c_ze, astring);
         messagedialog.m1322(point);
         return messagedialog.f790 == 0 && !lpsymbolizer.saveProblems(s) ? false : messagedialog.f790 == 0 || messagedialog.f790 == 1;
      }
   }

   static String m1451(String s) {
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
      FormulaEntryField formulaentryfield = new FormulaEntryField(c_d_c.f1044.frame);
      formulaentryfield.setBackground(Color.WHITE);
      String[] astring = new String[]{"OK", "Cancel"};
      SizedPanel sizedpanel = new SizedPanel();
      sizedpanel.setLayout(new BorderLayout());
      sizedpanel.setBorder(new EmptyBorder(7, 15, 7, 15));
      C_ZE c_ze = new C_ZE(s1, 0, 1);
      c_ze.setBorder(new EmptyBorder(0, 0, 5, 0));
      Dimension dimension = c_ze.getPreferredSize();
      formulaentryfield.setBorder(new BevelBorder(1));
      formulaentryfield.m2023(dimension.width);
      formulaentryfield.invalidate();
      sizedpanel.add(c_ze, "North");
      sizedpanel.add(formulaentryfield, "East");
      MessageDialog messagedialog = new MessageDialog(c_d_c.f1044.frame, s, sizedpanel, astring);
      messagedialog.m1314(0);
      formulaentryfield.f425 = messagedialog;
      formulaentryfield.requestFocus();
      messagedialog.m1323(point1, true);
      return messagedialog.f790 != 0 ? null : LogicProgram.m995(formulaentryfield.getText(), f854, maggie);
   }

   static void m1453(LPSymbolizer lpsymbolizer) {
      if (lpsymbolizer.directEntryDisabled) {
         MessageDialog.showMessage("Feature Disabled", "Direct entry is disabled for this problem.", null, null);
      } else {
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 7 * LogicProgram.fontSize);
         Point point = MessageDialog.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         FormulaEntryField formulaentryfield = new FormulaEntryField(LogicProgram.m995(lpsymbolizer.lastDirect, maggie, f854), lpsymbolizer.frame);
         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(formulaentryfield);
         MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, "Direct Symbolization", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.m1314(0);
         messagedialog.m1317("symDirect");
         formulaentryfield.f425 = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.m1323(point, true);
         if (messagedialog.f790 == 0) {
            lpsymbolizer.lastDirect = LogicProgram.m995(formulaentryfield.getText(), f854, maggie);
            lpsymbolizer.problem.m1709(lpsymbolizer.lastDirect);
            if (lpsymbolizer.checkDisabled || lpsymbolizer.errorMessagesDisabled) {
               return;
            }

            C_d_C c_d_c = lpsymbolizer.problem.m1715();
            if (c_d_c == null) {
               return;
            }

            Expression expression = c_d_c.m1698();
            Expression expression1 = lpsymbolizer.problem.m1698();
            if (expression1 == null || expression == null || expression1.m1236(expression, new C_MB())) {
               lpsymbolizer.problem.m1742(lpsymbolizer.problem.m1715());
            }
         }
      }
   }

   static void m1454(String s, String s1, DialogHandler dialoghandler) {
      if (dialoghandler != null) {
         C_d_C c_d_c = (C_d_C)dialoghandler.m449("target");
         ModuleFrame moduleframe = new ModuleFrame();
         JScrollPane jscrollpane = new JScrollPane();
         FormulaTextPane formulatextpane = new FormulaTextPane(LogicProgram.m1004(s1));
         formulatextpane.setEditable(false);
         formulatextpane.setBackground(dialogWhite);
         formulatextpane.setCaretPosition(0);
         String[] astring = dialoghandler.m445();
         jscrollpane.setViewportView(formulatextpane);
         MessageDialog messagedialog = new MessageDialog(moduleframe, s, jscrollpane, astring);
         messagedialog.m1317("symShowError");
         messagedialog.m1315(dialoghandler);
         Rectangle rectangle = messagedialog.m1320();
         Rectangle rectangle1 = LogicProgram.m1035(c_d_c.f1045.f1438, null);
         Dimension dimension = rectangle == null ? new Dimension(32 * LogicProgram.fontSize, 15 * LogicProgram.fontSize) : rectangle.getSize();
         Point point = new Point(rectangle1.x + rectangle1.width / 2 - dimension.width / 2, rectangle1.y + rectangle1.height);
         if (rectangle != null) {
            rectangle.x = point.x;
            rectangle.y = point.y;
         }

         messagedialog.setSize(dimension);
         messagedialog.m1323(point, true);
         moduleframe.dispose();
      }
   }
}
