package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.io.IOException;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.text.StyledDocument;

class C_KB implements DerivationConstants {
   static String[] f444 = LogicProgram.symbols;

   static void m759(String s) {
      m765(s, null, null, null, null, null);
   }

   static void m760(String s, Point point) {
      m765(s, null, null, null, null, point);
   }

   static void m761(String s, Hashtable hashtable) {
      m765(s, hashtable, null, null, null, null);
   }

   static void m762(String s, Hashtable hashtable, DerivationLine derivationline) {
      m765(s, hashtable, null, null, derivationline, null);
   }

   static void m763(String s, Hashtable hashtable, DerivationLineChecker derivationlinechecker) {
      m765(s, hashtable, null, derivationlinechecker, derivationlinechecker.f935, null);
   }

   static void m764(String s, Hashtable hashtable, Hashtable hashtable1, DerivationLineChecker derivationlinechecker) {
      m765(s, hashtable, hashtable1, derivationlinechecker, derivationlinechecker.f935, null);
   }

   static void m765(
      String s, Hashtable hashtable, Hashtable hashtable1, DerivationLineChecker derivationlinechecker, DerivationLine derivationline, Point point
   ) {
      Message message = C_n_.get(s);
      String s1 = message.id;
      String s2 = C_n_.m1962(message.text, hashtable, derivationlinechecker, derivationline);
      C_y_ c_y_ = null;
      if (message.buttons != null) {
         c_y_ = new C_y_(derivationline, message.buttons);
         if (hashtable1 != null) {
            c_y_.m448(hashtable1);
         }
      }

      MessageDialog.showMessage(s1, s2, point, c_y_);
   }

   static boolean m766(LPDerivation lpderivation, Point point) {
      String s = lpderivation.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lpderivation.frame.setVisible(true);
         C_ZE c_ze = new C_ZE("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpderivation.frame, "", c_ze, astring);
         messagedialog.m1322(point);
         return messagedialog.f790 == 0 && !lpderivation.saveProblems(s) ? false : messagedialog.f790 == 0 || messagedialog.f790 == 1;
      }
   }

   static boolean m767(LPDerivation lpderivation, Point point) {
      if (lpderivation.problem.m1555() != 1) {
         C_z_E c_z_e = new C_z_E();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            c_z_e.add(ajradiobutton[j] = new C_NF(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpderivation.frame, "", c_z_e, astring2);
         messagedialog.m1322(null);
         if (messagedialog.f790 != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            lpderivation.removeWork();
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lpderivation.problemIndex != -1 || !lpderivation.problem.m7(false).equals("")) {
         C_ZE c_ze = new C_ZE("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog1 = new MessageDialog(lpderivation.frame, "", c_ze, astring1);
         messagedialog1.m1322(null);
         if (messagedialog1.f790 != 0) {
            return false;
         }
      }

      if (lpderivation.problemIndex != -1) {
         LPDerivation.problems.m1101(lpderivation.problemIndex);
         LPDerivation.saveProblems();
      }

      lpderivation.newProblem();
      return true;
   }

   static boolean m768(LPDerivation lpderivation, Point point) {
      if (lpderivation.problem.m1555() == 1) {
         return false;
      } else {
         C_ZE c_ze = new C_ZE("Delete the work on this problem?");
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpderivation.frame, "", c_ze, astring);
         messagedialog.m1322(point);
         if (messagedialog.f790 != 0) {
            return false;
         } else {
            lpderivation.removeWork();
            return true;
         }
      }
   }

   static boolean m769(LPDerivation lpderivation) {
      if (m766(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            int i = lpderivation.problemIndex + 1;
            if (i != 0 && LPDerivation.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPDerivation.problems.m1778(i);
            if (s == null) {
               return m770(lpderivation);
            }

            lpderivation.loadProblem(s);
            lpderivation.problemIndex = i;
            LPDerivation.problems.m1776(lpderivation.saveProblem(), i);
            if (LogicModule.eraseWork) {
               lpderivation.removeWork();
            }
         }
      }

      lpderivation.requestFocus();
      return true;
   }

   static boolean m770(LPDerivation lpderivation) {
      boolean flag = false;
      if (m766(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            ProblemListView problemlistview = m776(lpderivation, false, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpderivation.frame, ProblemSet.m1782("Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("derChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               int i = problemlistview.m1532(problemlistview.f899);
               LPDerivation lpderivation1;
               if ((lpderivation1 = LPDerivation.openInstance(i, true)) != null) {
                  lpderivation1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lpderivation.loadProblem(LPDerivation.problems.m1778(i));
                  lpderivation.problemIndex = i;
                  LPDerivation.problems.m1776(lpderivation.saveProblem(), i);
                  if (LogicModule.eraseWork) {
                     lpderivation.removeWork();
                  }

                  flag = true;
               }
            }
         }
      }

      lpderivation.requestFocus();
      return flag;
   }

   static int[] m771(LPDerivation lpderivation) {
      int[] aint = null;
      if (m766(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            ProblemListView problemlistview = m776(lpderivation, true, false, null);
            if (LPDerivation.submitExam) {
               problemlistview.clearSelection();
            }

            Vector vector = LPDerivation.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = TaggedRecord.m1493(s);
                  int j = LPDerivation.problems.m1767(s1);
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
            MessageDialog messagedialog = new MessageDialog(lpderivation.frame, ProblemSet.m1782("Submit Problems"), sizedpanel, astring);
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

      lpderivation.requestFocus();
      return aint;
   }

   static int[] m772(LPDerivation lpderivation) {
      int[] aint = null;
      if (m766(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            ProblemListView problemlistview = m776(lpderivation, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            C_s_B c_s_b = new C_s_B(LogicProgram.m1004(Message.getText("not089")));
            c_s_b.setLineWrap(true);
            c_s_b.setWrapStyleWord(true);
            sizedpanel.add(c_s_b, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpderivation.frame, ProblemSet.m1782("Upload Problems"), sizedpanel, astring);
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

      lpderivation.requestFocus();
      return aint;
   }

   static void m773(LPDerivation lpderivation) {
      synchronized (LPDerivation.problems) {
         ProblemListView problemlistview = m776(lpderivation, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpderivation.frame, ProblemSet.m1782("Delete Problems"), jscrollpane, astring);
         problemlistview.m1528(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.m1317("derChosen");
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
                     C_EE c_ee1 = (C_EE)LPDerivation.problems.m1779(l);
                     if (c_ee1 != null) {
                        TaggedRecord taggedrecord1 = new TaggedRecord(c_ee1.name);
                        if (!LPDerivation.isExample(taggedrecord1.getName())) {
                           c_ee1.name = LPDerivation.removeWork(taggedrecord1);
                           c_ee1.state = 0;
                           LPDerivation lpderivation2 = LPDerivation.openInstance(l, false);
                           if (lpderivation2 != null) {
                              lpderivation2.loadProblem(c_ee1.name);
                           }
                        }
                     }
                  }
               }

               LPDerivation.saveProblems();
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
                     C_EE c_ee = (C_EE)LPDerivation.problems.m1779(j);
                     if (c_ee != null) {
                        TaggedRecord taggedrecord = new TaggedRecord(c_ee.name);
                        if (!LPDerivation.isExercise(taggedrecord.getName())) {
                           LPDerivation lpderivation1 = LPDerivation.openInstance(j, false);
                           LPDerivation.problems.m1101(j);
                           if (lpderivation1 != null) {
                              lpderivation1.newProblem();
                           }
                        }
                     }
                  }
               }

               LPDerivation.saveProblems();
            }
         }
      }

      lpderivation.requestFocus();
   }

   static int[] m774(LPDerivation lpderivation) {
      int[] aint = null;
      if (m766(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            ProblemListView problemlistview = m776(lpderivation, true, false, LPDerivation.noPrint);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Print", "Print Results", "Print List", "Cancel"};
            problemlistview.f898 = 3;
            MessageDialog messagedialog = new MessageDialog(lpderivation.frame, ProblemSet.m1782("Print Problems"), jscrollpane, astring);
            problemlistview.m1528(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.m1317("derChosen");
            problemlistview.requestFocus();
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            if (messagedialog.f790 == 0) {
               aint = problemlistview.m1533(problemlistview.f899);
            } else if (messagedialog.f790 == 1) {
               C_Z.m1544(problemlistview.m1533(problemlistview.f899));
            } else if (messagedialog.f790 == 2) {
               C_g_A.m1819(problemlistview.m1533(problemlistview.f899));
            }
         }
      }

      lpderivation.requestFocus();
      return aint;
   }

   static void m775(LPDerivation lpderivation) {
      if (m766(lpderivation, null)) {
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         EditableTextPane editabletextpane = new EditableTextPane();
         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(editabletextpane);
         editabletextpane.setBackground(Color.WHITE);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "Submitted Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.m1314(0);
         messagedialog.m1317("derSubmitted");
         editabletextpane.requestFocus();
         messagedialog.m1323(point, true);
         moduleframe.dispose();
         if (messagedialog.f790 == 0) {
            lpderivation.loadProblem(editabletextpane.getText());
         }
      }

      lpderivation.requestFocus();
   }

   static ProblemListView m776(LPDerivation lpderivation, boolean flag, boolean flag1, ProblemSelector problemselector) {
      return LPDerivation.problems.m1781(lpderivation, LPDerivation.exercises, flag, flag1, LPDerivation.monoProbs, problemselector);
   }

   static void m777(LPDerivation lpderivation) {
      if (m766(lpderivation, null)) {
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.m1321(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         FormulaEntryField formulaentryfield = new FormulaEntryField(moduleframe);
         if (lpderivation.lastUserProblem != null) {
            formulaentryfield.setText(LogicProgram.m995(lpderivation.lastUserProblem, maggie, f444));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(formulaentryfield);
         formulaentryfield.setBackground(Color.WHITE);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "User Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.m1314(0);
         messagedialog.m1317("derUser");
         formulaentryfield.f425 = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.m1323(point, true);
         moduleframe.dispose();
         if (messagedialog.f790 == 0) {
            String s = formulaentryfield.getText();
            if (!LPDerivation.validateUserProblem(s)) {
               return;
            }

            lpderivation.lastUserProblem = LogicProgram.m995(s, f444, maggie);
            lpderivation.loadUserProblem(lpderivation.lastUserProblem);
         }
      }

      lpderivation.requestFocus();
   }

   static int m778(DerivationLine derivationline, Expression[] aexpression, String s) {
      if (derivationline.f317.f915.serialMode) {
         return -1;
      } else {
         int i = aexpression.length;
         C_z_E c_z_e = new C_z_E();
         JPanel jpanel = new JPanel();
         JRadioButton[] ajradiobutton = new JRadioButton[i];
         jpanel.setLayout(new C_m_A());
         jpanel.add(C_q_B.m2031(s));
         jpanel.add(c_z_e);
         c_z_e.setLayout(new C_m_A());

         for (int j = 0; j < i; j++) {
            ajradiobutton[j] = new C_NA(LogicProgram.m993(aexpression[j].toString()));
            c_z_e.add(ajradiobutton[j]);
         }

         ajradiobutton[0].setSelected(true);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(derivationline.f317.f915.frame, "Line " + derivationline.m30(), jpanel, astring);
         messagedialog.m1314(0);
         derivationline.m22(true);
         Rectangle rectangle = LogicProgram.m1035(derivationline.f324, null);
         messagedialog.pack();
         messagedialog.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         if (messagedialog.f790 == 0) {
            for (int k = 0; k < i; k++) {
               if (ajradiobutton[k].isSelected()) {
                  return k;
               }
            }
         }

         return -1;
      }
   }

   static void m779(Reader reader) {
      String s1 = "";
      ScrambledReader scrambledreader;
      if (reader instanceof ScrambledReader) {
         scrambledreader = (ScrambledReader)reader;
      } else {
         scrambledreader = new ScrambledReader(reader, LogicProgram.scrambleKey);
      }

      String s;
      try {
         while ((s = scrambledreader.readLine()) != null) {
            s1 = s1 + s + "\n";
         }
      } catch (IOException ioexception) {
      }

      ModuleFrame moduleframe = new ModuleFrame();
      JScrollPane jscrollpane = new JScrollPane();
      FormulaTextPane formulatextpane = new FormulaTextPane(LogicProgram.m1004(s1));
      formulatextpane.setEditable(false);
      formulatextpane.setBackground(dialogWhite);
      String[] astring = new String[]{"OK"};
      jscrollpane.setViewportView(formulatextpane);
      MessageDialog messagedialog = new MessageDialog(moduleframe, "Program Help", jscrollpane, astring);
      Dimension dimension = new Dimension(45 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
      messagedialog.setSize(dimension);
      messagedialog.m1323(MessageDialog.m1321(dimension), true);
      moduleframe.dispose();
   }

   static int m780(DerivationLineChecker derivationlinechecker, Vector vector) {
      int j = vector.size();
      if (j == 1) {
         return 0;
      } else if (j == 0) {
         return -1;
      } else {
         Vector vector1 = new Vector();
         int k = 0;

         for (int i = 0; i < j; i++) {
            Object object = vector.elementAt(i);
            C_e_B c_e_b = null;
            if (object instanceof C_HF) {
               C_HF c_hf = (C_HF)object;
               c_e_b = c_hf.m700(null).m542();
            } else if (object instanceof C_GA) {
               C_GA c_ga = (C_GA)object;
               c_e_b = c_ga.m606(derivationlinechecker);
            }

            if (c_e_b != null && vector1.indexOf(c_e_b) == -1) {
               vector1.addElement(c_e_b);
               k++;
            } else {
               vector1.addElement(null);
            }
         }

         if (k == 1) {
            return 0;
         } else if (derivationlinechecker.f935.f317.f915.serialMode) {
            return -1;
         } else {
            C_z_E c_z_e = new C_z_E();
            SizedPanel sizedpanel1 = new SizedPanel();
            JRadioButton[] ajradiobutton = new JRadioButton[j];
            sizedpanel1.setLayout(new C_m_A());
            int l = derivationlinechecker.f961;
            Hashtable hashtable = Message.params("n", l + "");
            if (l > 0) {
               sizedpanel1.add(C_q_B.m2031(C_n_.m1960(C_n_.getText("derdlg003"), hashtable, derivationlinechecker)));

               for (int i1 = 0; i1 < l; i1++) {
                  sizedpanel1.add(LogicProgram.m985(derivationlinechecker.m1628(i1 - l).toString(), 14));
               }
            }

            if (derivationlinechecker.f944 && derivationlinechecker.f941 != null) {
               String s1 = l > 0 ? "derdlg004" : "derdlg005";
               sizedpanel1.add(C_q_B.m2031(C_n_.m1960(C_n_.getText(s1), hashtable, derivationlinechecker)));
               sizedpanel1.add(LogicProgram.m985(derivationlinechecker.f941.toString(), 14));
               sizedpanel1.add(C_q_B.m2031(C_n_.m1960(C_n_.getText("derdlg006"), hashtable, derivationlinechecker)));
            } else {
               String s = "derdlg017";
               sizedpanel1.add(C_q_B.m2031(C_n_.m1960(C_n_.getText(s), hashtable, derivationlinechecker)));
            }

            sizedpanel1.add(c_z_e);
            c_z_e.setLayout(new C_m_A());
            boolean flag = false;

            for (int j1 = 0; j1 < j; j1++) {
               C_e_B c_e_b1 = (C_e_B)vector1.elementAt(j1);
               if (c_e_b1 == null) {
                  ajradiobutton[j1] = new C_NA("");
               } else if (derivationlinechecker.f944 && derivationlinechecker.f941 != null) {
                  Object object1 = vector.elementAt(j1);
                  if (object1 instanceof C_HF) {
                     C_HF c_hf1 = (C_HF)object1;
                     SchematicRule schematicrule = c_hf1.m688();
                     ajradiobutton[j1] = new C_NA(LogicProgram.m993(schematicrule.m957(c_hf1.f392)));
                     c_z_e.add(ajradiobutton[j1]);
                     if (!flag) {
                        JRadioButton jradiobutton = ajradiobutton[j1];
                        flag = true;
                        jradiobutton.setSelected(true);
                     }
                  } else if (object1 instanceof C_GA) {
                     C_GA c_ga1 = (C_GA)object1;
                     SchematicRule schematicrule1 = c_ga1.m624(derivationlinechecker);
                     ajradiobutton[j1] = new C_NA(LogicProgram.m993(schematicrule1.toString()));
                     c_z_e.add(ajradiobutton[j1]);
                     if (!flag) {
                        JRadioButton jradiobutton1 = ajradiobutton[j1];
                        flag = true;
                        jradiobutton1.setSelected(true);
                     }
                  } else {
                     ajradiobutton[j1] = new C_NA("");
                  }
               } else {
                  SizedPanel sizedpanel = new SizedPanel();
                  sizedpanel.m934(LogicProgram.f541.width * 3 / 4);
                  sizedpanel.m937(true);
                  StyledDocument styleddocument = LogicProgram.m998(c_e_b1.f1069, maggie, f444, C_CB.m420(c_e_b1.f1070));
                  EditableTextPane editabletextpane = new EditableTextPane(styleddocument, -1, -1, true);
                  editabletextpane.m1787(true);
                  editabletextpane.m1789(true);
                  editabletextpane.setEditable(false);
                  sizedpanel.add(editabletextpane, "Center");
                  C_HC c_hc = new C_HC(sizedpanel);
                  ajradiobutton[j1] = c_hc.f382;
                  c_z_e.add(c_hc);
                  if (!flag) {
                     JRadioButton jradiobutton2 = ajradiobutton[j1];
                     flag = true;
                     jradiobutton2.setSelected(true);
                  }
               }
            }

            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(
               derivationlinechecker.f935.f317.f915.frame, "Line " + derivationlinechecker.f935.m30(), sizedpanel1, astring
            );
            messagedialog.m1314(0);
            derivationlinechecker.f935.m22(true);
            Rectangle rectangle = LogicProgram.m1035(derivationlinechecker.f935.f324, null);
            messagedialog.pack();
            messagedialog.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
            if (messagedialog.f790 != 0) {
               return -1;
            } else {
               int k1 = 0;
               k1 = 0;

               while (k1 < j && !ajradiobutton[k1].isSelected()) {
                  k1++;
               }

               return k1 == j ? -1 : k1;
            }
         }
      }
   }

   static Expression m781(DerivationLineChecker derivationlinechecker, Expression expression, boolean flag) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new C_m_A());
      String[] astring = new String[]{"OK", "Cancel"};
      JRadioButton[] ajradiobutton = new JRadioButton[2];
      Expression[] aexpression = new Expression[2];
      C_z_E c_z_e = new C_z_E();
      c_z_e.setLayout(new C_m_A());

      for (int i = 0; i < 2; i++) {
         aexpression[i] = expression.getChild(i).copy();
         if (flag) {
            aexpression[i] = aexpression[i].negate();
         }

         c_z_e.add(ajradiobutton[i] = new C_NF(LogicProgram.m995(aexpression[i].toString(), maggie, f444)));
      }

      ajradiobutton[0].setSelected(true);
      jpanel.add(new C_ZE("Please choose a formula to show"));
      jpanel.add(c_z_e);
      EditableTextPane editabletextpane = new EditableTextPane();
      MessageDialog messagedialog = new MessageDialog(derivationlinechecker.f935.f317.f915.frame, "Choose a Formula", jpanel, astring);
      if (messagedialog.m1327(derivationlinechecker.f956, new EditableTextPane[]{editabletextpane}, 0)) {
         String s = editabletextpane.getText();
         if (s.equalsIgnoreCase("L")) {
            return aexpression[0];
         }

         if (s.equalsIgnoreCase("R")) {
            return aexpression[1];
         }
      }

      if (derivationlinechecker.f935.f317.f915.serialMode) {
         derivationlinechecker.m1621("dererr064");
         derivationlinechecker.f935.f317.f915.complete = false;
         messagedialog.dispose();
         return null;
      } else {
         Rectangle rectangle = LogicProgram.m1035(derivationlinechecker.f935, null);
         messagedialog.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         if (messagedialog.f790 == 0) {
            if (ajradiobutton[0].isSelected()) {
               return aexpression[0];
            }

            if (ajradiobutton[1].isSelected()) {
               return aexpression[1];
            }
         }

         return null;
      }
   }

   static boolean m782(DerivationLineChecker derivationlinechecker, Justification justification) {
      if (derivationlinechecker.f935.f317.f915.serialMode && derivationlinechecker.f956 == null) {
         derivationlinechecker.m1621("dererr064");
         derivationlinechecker.f935.f317.f915.complete = false;
         return false;
      } else {
         C_GA c_ga = null;
         C_HF c_hf = null;
         Object object = null;
         if (justification instanceof C_GA) {
            c_ga = (C_GA)justification;
            c_hf = c_ga.m625();
            object = c_ga.f343;
         } else {
            if (!(justification instanceof C_HF)) {
               throw new IllegalArgumentException("instanceSchemeQuery expects LPRuleInstance or LPInterchangeInstance");
            }

            c_ga = null;
            c_hf = (C_HF)justification;
            object = new ExpressionPath();
         }

         boolean flag = derivationlinechecker.f944 && derivationlinechecker.f941 != null;
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new C_m_A());
         SchemeInstantiation schemeinstantiation = c_hf.f393;
         C_f_ c_f_ = null;
         C_HF c_hf1 = null;
         SchematicRule schematicrule = c_hf.f391;
         int i = schematicrule.premises.length;
         Hashtable hashtable = Message.params("n", i + "");
         if (c_ga != null) {
            Message.putParam(hashtable, "rule name", schematicrule.f820);
         }

         if (i > 0) {
            jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.getText("derdlg003"), hashtable, derivationlinechecker)));

            for (int j = 0; j < i; j++) {
               jpanel.add(LogicProgram.m987(derivationlinechecker.m1628(j - i).m1220((ExpressionPath)object).toString(), 14, 350));
            }
         }

         if (flag) {
            String s1 = i > 0 ? "derdlg004" : "derdlg005";
            jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.getText(s1), hashtable, derivationlinechecker)));
            jpanel.add(LogicProgram.m987(derivationlinechecker.f941.m1220((ExpressionPath)object).toString(), 14, 350));
            jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.getText("derdlg007"), hashtable, derivationlinechecker)));
            jpanel.add(LogicProgram.m987(schematicrule.m957(c_hf.f392), 14, 350));
            c_f_ = new C_f_(schemeinstantiation, 250, derivationlinechecker.f935.f317.f915.frame);
         } else {
            C_FD c_fd = c_hf.m700(null);
            c_hf1 = c_fd.f311;
            String s = "derdlg019";
            jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.getText(s), hashtable, derivationlinechecker)));
            C_e_B c_e_b = c_fd.m542();
            jpanel.add(LogicProgram.m988(c_e_b, 14, 350));
            c_f_ = new C_f_(c_fd.f312, 250, derivationlinechecker.f935.f317.f915.frame, true);
         }

         jpanel.add(c_f_);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.f935.f317.f915.frame, "Line " + derivationlinechecker.f935.m30(), jpanel, astring
         );
         messagedialog.m1314(0);
         c_f_.m1801(messagedialog);
         derivationlinechecker.f935.m22(true);
         Rectangle rectangle = LogicProgram.m1035(derivationlinechecker.f935.f324, null);
         if (!messagedialog.m1327(derivationlinechecker.f956, c_f_.m1803(), 0)) {
            if (derivationlinechecker.f935.f317.f915.serialMode) {
               derivationlinechecker.m1621("dererr064");
               derivationlinechecker.f935.f317.f915.complete = false;
               messagedialog.dispose();
               return false;
            }

            messagedialog.pack();
            messagedialog.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            messagedialog.dispose();
         }

         if (messagedialog.f790 != 0) {
            derivationlinechecker.m1621("dererr028");
            derivationlinechecker.f935.f317.f915.abort(true);
            return false;
         } else {
            SchemeInstantiation schemeinstantiation1 = c_f_.m1802();
            if (schemeinstantiation1 == null) {
               derivationlinechecker.m1622(c_f_.f1099, c_f_.f1100);
               return false;
            } else {
               if (flag) {
                  if (!schemeinstantiation.m1877(schemeinstantiation1)) {
                     derivationlinechecker.m1622(schemeinstantiation.f1190, schemeinstantiation.f1191);
                     return false;
                  }
               } else {
                  while (!schemeinstantiation.f1189.isEmpty()) {
                     Expression expression = ((SchematicLetter)schemeinstantiation.f1189.elementAt(0)).m1175();
                     Expression expression1 = expression.instantiate(c_hf1.f393).instantiate(schemeinstantiation1);
                     if (!schemeinstantiation.m1881(expression, expression1)) {
                        derivationlinechecker.m1622(schemeinstantiation.f1190, schemeinstantiation.f1191);
                        return false;
                     }
                  }
               }

               return true;
            }
         }
      }
   }

   static boolean m783(DerivationLineChecker derivationlinechecker, C_HF c_hf) {
      if (derivationlinechecker.f935.f317.f915.serialMode) {
         derivationlinechecker.f935.f317.f915.complete = false;
         derivationlinechecker.m1621("dererr064");
         return false;
      } else {
         C_n_D c_n_d = new C_n_D(250);
         SchemeInstantiation schemeinstantiation = c_hf.f393;
         C_b_ c_b_ = new C_b_(derivationlinechecker.m1628(-1), 250, derivationlinechecker.f935.f317.f915.frame);
         c_b_.m1845(c_n_d);
         if (!m787(derivationlinechecker, c_hf, "derdlg008", null, c_b_, c_n_d, null)) {
            return false;
         } else {
            c_b_.m1846(c_n_d);
            String s = LogicProgram.m995(c_b_.getText(), f444, maggie);
            Term term = c_b_.f1166[0];
            if (!schemeinstantiation.m1882(schemeinstantiation.f1189.elementAt(0).toString(), s)) {
               derivationlinechecker.m1622(schemeinstantiation.f1190, schemeinstantiation.f1191);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m784(DerivationLineChecker derivationlinechecker, C_HF c_hf) {
      if (derivationlinechecker.f935.f317.f915.serialMode && derivationlinechecker.f956 == null) {
         derivationlinechecker.f935.f317.f915.complete = false;
         derivationlinechecker.m1621("dererr064");
         return false;
      } else {
         JPanel jpanel = new JPanel();
         JPanel jpanel1 = new JPanel();
         JPanel jpanel2 = new JPanel();
         jpanel.setLayout(new C_m_A());
         jpanel1.setLayout(new C_QF(1, 1, 0, 0, true, true));
         jpanel2.setLayout(new BorderLayout());
         SchemeInstantiation schemeinstantiation = c_hf.f393;
         FormulaEntryField formulaentryfield = new FormulaEntryField("", 250, derivationlinechecker.f935.f317.f915.frame);
         jpanel1.add(formulaentryfield, new Point(0, 0));
         JScrollPane jscrollpane = new JScrollPane(jpanel1, 22, 31);
         jpanel2.add(jscrollpane);
         Hashtable hashtable = Message.params("gen var", "\\l" + ((Expression)derivationlinechecker.f937.elementAt(0)).getChild(0) + "\\l");
         jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.getText("derdlg009"), hashtable, derivationlinechecker)));
         jpanel.add(jpanel2);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.f935.f317.f915.frame, "Line " + derivationlinechecker.f935.m30(), jpanel, astring
         );
         formulaentryfield.f425 = messagedialog;
         derivationlinechecker.f935.m22(true);
         Rectangle rectangle = LogicProgram.m1035(derivationlinechecker.f935.f324, null);
         messagedialog.m1314(0);
         C_y_ c_y_ = new C_y_(derivationlinechecker.f935, "OK:existentialVarQueryOK.Cancel");
         c_y_.m447("just", derivationlinechecker);
         c_y_.m447("inst", c_hf);
         c_y_.m447("edit", formulaentryfield);
         messagedialog.m1315(c_y_);
         if (!messagedialog.m1327(derivationlinechecker.f956, new EditableTextPane[]{formulaentryfield}, 0)) {
            if (derivationlinechecker.f935.f317.f915.serialMode) {
               derivationlinechecker.f935.f317.f915.complete = false;
               derivationlinechecker.m1621("dererr064");
               messagedialog.dispose();
               return false;
            }

            messagedialog.pack();
            messagedialog.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            messagedialog.dispose();
         }

         if (messagedialog.f790 != 0) {
            derivationlinechecker.m1621("dererr028");
            derivationlinechecker.f935.f317.f915.abort(true);
            return false;
         } else {
            String s = LogicProgram.m995(formulaentryfield.getText(), f444, maggie);
            if (!schemeinstantiation.m1882(schemeinstantiation.f1189.elementAt(0).toString(), s)) {
               derivationlinechecker.m1622(schemeinstantiation.f1190, schemeinstantiation.f1191);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m785(DerivationLineChecker derivationlinechecker, C_HF c_hf) {
      if (derivationlinechecker.f935.f317.f915.serialMode && derivationlinechecker.f956 == null) {
         derivationlinechecker.f935.f317.f915.complete = false;
         derivationlinechecker.m1621("dererr064");
         return false;
      } else {
         JPanel jpanel = new JPanel();
         JPanel jpanel1 = new JPanel();
         JPanel jpanel2 = new JPanel();
         jpanel.setLayout(new C_m_A());
         jpanel1.setLayout(new C_QF(1, 1, 0, 0, true, true));
         jpanel2.setLayout(new BorderLayout());
         SchemeInstantiation schemeinstantiation = c_hf.f393;
         FormulaEntryField formulaentryfield = new FormulaEntryField("", 250, derivationlinechecker.f935.f317.f915.frame);
         jpanel1.add(formulaentryfield, new Point(0, 0));
         JScrollPane jscrollpane = new JScrollPane(jpanel1, 22, 31);
         jpanel2.add(jscrollpane);
         Hashtable hashtable = Message.params("gen var", "\\l" + ((Expression)derivationlinechecker.f937.elementAt(0)).getChild(0) + "\\l");
         jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.getText("derdlg010"), hashtable, derivationlinechecker)));
         jpanel.add(jpanel2);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.f935.f317.f915.frame, "Line " + derivationlinechecker.f935.m30(), jpanel, astring
         );
         formulaentryfield.f425 = messagedialog;
         derivationlinechecker.f935.m22(true);
         Rectangle rectangle = LogicProgram.m1035(derivationlinechecker.f935.f324, null);
         messagedialog.m1314(0);
         C_y_ c_y_ = new C_y_(derivationlinechecker.f935, "OK:universalTermQueryOK.Cancel");
         c_y_.m447("just", derivationlinechecker);
         c_y_.m447("inst", c_hf);
         c_y_.m447("edit", formulaentryfield);
         messagedialog.m1315(c_y_);
         if (!messagedialog.m1327(derivationlinechecker.f956, new EditableTextPane[]{formulaentryfield}, 0)) {
            if (derivationlinechecker.f935.f317.f915.serialMode) {
               derivationlinechecker.f935.f317.f915.complete = false;
               derivationlinechecker.m1621("dererr064");
               messagedialog.dispose();
               return false;
            }

            messagedialog.pack();
            messagedialog.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            messagedialog.dispose();
         }

         if (messagedialog.f790 != 0) {
            derivationlinechecker.m1621("dererr028");
            derivationlinechecker.f935.f317.f915.abort(true);
            return false;
         } else {
            String s = LogicProgram.m995(formulaentryfield.getText(), f444, maggie);
            if (!schemeinstantiation.m1882(schemeinstantiation.f1189.elementAt(0).toString(), s)) {
               derivationlinechecker.m1622(schemeinstantiation.f1190, schemeinstantiation.f1191);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m786(DerivationLineChecker derivationlinechecker, C_HF c_hf, C__B c__b) {
      if (derivationlinechecker.f935.f317.f915.serialMode && derivationlinechecker.f956 == null) {
         derivationlinechecker.f935.f317.f915.complete = false;
         derivationlinechecker.m1621("dererr064");
         return false;
      } else {
         JPanel jpanel = new JPanel();
         JPanel jpanel1 = new JPanel();
         JPanel jpanel2 = new JPanel();
         jpanel.setLayout(new C_m_A());
         jpanel1.setLayout(new C_QF(1, 1, 0, 0, true, true));
         jpanel2.setLayout(new BorderLayout());
         FormulaEntryField formulaentryfield = new FormulaEntryField("", 250, derivationlinechecker.f935.f317.f915.frame);
         jpanel1.add(formulaentryfield, new Point(0, 0));
         JScrollPane jscrollpane = new JScrollPane(jpanel1, 22, 31);
         jpanel2.add(jscrollpane);
         Expression expression = c_hf.m688().conclusion;
         jpanel.add(C_q_B.m2031(C_n_.m1960(C_n_.getText("derdlg011"), null, derivationlinechecker)));
         jpanel.add(jpanel2);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.f935.f317.f915.frame, "Line " + derivationlinechecker.f935.m30(), jpanel, astring
         );
         formulaentryfield.f425 = messagedialog;
         derivationlinechecker.f935.m22(true);
         Rectangle rectangle = LogicProgram.m1035(derivationlinechecker.f935.f324, null);
         messagedialog.m1314(0);
         C_y_ c_y_ = new C_y_(derivationlinechecker.f935, "OK:dummyVarQueryOK.Cancel");
         c_y_.m447("just", derivationlinechecker);
         c_y_.m447("inst", c_hf);
         c_y_.m447("edit", formulaentryfield);
         messagedialog.m1315(c_y_);
         if (!messagedialog.m1327(derivationlinechecker.f956, new EditableTextPane[]{formulaentryfield}, 0)) {
            if (derivationlinechecker.f935.f317.f915.serialMode) {
               derivationlinechecker.f935.f317.f915.complete = false;
               derivationlinechecker.m1621("dererr064");
               messagedialog.dispose();
               return false;
            }

            messagedialog.pack();
            messagedialog.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            messagedialog.dispose();
         }

         if (messagedialog.f790 != 0) {
            derivationlinechecker.m1621("dererr028");
            derivationlinechecker.f935.f317.f915.abort(true);
            return false;
         } else {
            String s = LogicProgram.m995(formulaentryfield.getText(), f444, maggie);
            if (!c__b.m1571(C__B.m1579(expression.m1243(), 0), s)) {
               derivationlinechecker.m1622("dererr060", Message.params("variable name", "\\l" + s + "\\l"));
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m787(DerivationLineChecker derivationlinechecker, C_HF c_hf, String s, Hashtable hashtable, C_h_F c_h_f, C_n_D c_n_d, Term[] aterm) {
      if (derivationlinechecker.f935.f317.f915.serialMode) {
         derivationlinechecker.f935.f317.f915.complete = false;
         derivationlinechecker.m1621("dererr064");
         return false;
      } else {
         int i = c_n_d.f1303;
         Message message = C_n_.get(s);
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new C_m_A());
         JPanel jpanel1 = new JPanel();
         jpanel1.setLayout(new BorderLayout());
         JPanel jpanel2 = new JPanel();
         jpanel2.setLayout(new BorderLayout());
         JPanel jpanel3 = new JPanel();
         jpanel3.setLayout(new BorderLayout());
         JPanel jpanel4 = new JPanel();
         jpanel4.setLayout(new BorderLayout());
         jpanel3.add(c_h_f);
         jpanel4.add(c_n_d);
         jpanel1.add(new JScrollPane(jpanel3, 22, 31));
         jpanel2.add(new JScrollPane(jpanel4, 22, 31));
         jpanel.add(C_q_B.m2031(C_n_.m1960(message.text, hashtable, derivationlinechecker)));
         jpanel.add(jpanel1);
         jpanel.add(jpanel2);
         if (aterm != null) {
            Vector vector = c_h_f.f1168;
            int j = aterm.length;

            for (int k = 0; k < j; k++) {
               Term term = aterm[k];
               if (term != null) {
                  Enumeration enumeration = vector.elements();

                  while (enumeration.hasMoreElements()) {
                     C_s_ c_s_ = (C_s_)enumeration.nextElement();
                     c_s_.m1964(k, LogicProgram.m995(term.toString(), maggie, f444));
                  }

                  c_h_f.f1166[k] = term;
                  c_h_f.f1167[k]++;
               }
            }
         }

         C_y_ c_y_ = new C_y_(derivationlinechecker.f935, message.buttons);
         c_y_.m447("undo", c_h_f);
         c_y_.m447("just", derivationlinechecker);
         c_y_.m447("inst", c_hf);
         c_y_.m447("edit", c_h_f);
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.f935.f317.f915.frame, "Line " + derivationlinechecker.f935.m30(), jpanel, c_y_.f272
         );
         c_h_f.m715(messagedialog);
         c_h_f.f425 = messagedialog;
         messagedialog.setSize(messagedialog.getPreferredSize());
         derivationlinechecker.f935.m22(true);
         Rectangle rectangle = LogicProgram.m1035(derivationlinechecker.f935.f324, null);
         messagedialog.m1314(0);
         messagedialog.m1315(c_y_);
         messagedialog.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         if (messagedialog.f790 != 0) {
            derivationlinechecker.m1621("dererr028");
            derivationlinechecker.f935.f317.f915.abort(true);
            return false;
         } else {
            return true;
         }
      }
   }

   static boolean m788(DerivationLineChecker derivationlinechecker, C_HF c_hf) {
      if (derivationlinechecker.f935.f317.f915.serialMode) {
         derivationlinechecker.f935.f317.f915.complete = false;
         derivationlinechecker.m1621("dererr064");
         return false;
      } else {
         int i = c_hf.f391.premises[c_hf.f392[0]].symbol.equals("=") ? 0 : 1;
         Expression expression = derivationlinechecker.m1628(i - 2);
         Expression expression1 = derivationlinechecker.m1628(-i - 1);
         String s = c_hf.f391.premises[c_hf.f392[1 - i]].getChild(0).symbol;
         String s1 = c_hf.f391.conclusion.getChild(0).symbol;
         int j = c_hf.f391.premises[c_hf.f392[i]].getChild(0).symbol.equals(s) ? 0 : 1;
         Term term = (Term)expression.getChild(j);
         C_n_D c_n_d = new C_n_D(250);
         SchemeInstantiation schemeinstantiation = c_hf.f393;
         Hashtable hashtable = Message.params(
            "rule term",
            s,
            "other term",
            s1,
            "term A",
            "\\l" + term + "\\l",
            "term B",
            "\\l" + expression.getChild(1 - j) + "\\l",
            "wff A",
            "\\l" + expression1 + "\\l"
         );
         C_j_E c_j_e = new C_j_E(expression1, 250, derivationlinechecker.f935.f317.f915.frame, hashtable);
         c_j_e.m1845(c_n_d);
         if (!m787(derivationlinechecker, c_hf, "derdlg012", hashtable, c_j_e, c_n_d, new Term[]{term})) {
            return false;
         } else {
            String s2 = LogicProgram.m995(c_j_e.getText(), f444, maggie);
            if (!schemeinstantiation.m1882(schemeinstantiation.f1189.elementAt(0).toString(), s2)) {
               derivationlinechecker.m1622(schemeinstantiation.f1190, schemeinstantiation.f1191);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m789(DerivationLineChecker derivationlinechecker, C_HF c_hf) {
      if (derivationlinechecker.f935.f317.f915.serialMode) {
         derivationlinechecker.f935.f317.f915.complete = false;
         derivationlinechecker.m1621("dererr064");
         return false;
      } else {
         int i = c_hf.f391.premises[c_hf.f392[0]].symbol.equals("~") ? 0 : 1;
         Expression expression = derivationlinechecker.m1628(-i - 1);
         Expression expression1 = derivationlinechecker.m1628(i - 2);
         String s = c_hf.f391.premises[c_hf.f392[1 - i]].getChild(0).symbol;
         String s1 = c_hf.f391.premises[c_hf.f392[i]].getChild(0).symbol;
         C_n_D c_n_d = new C_n_D(250);
         SchemeInstantiation schemeinstantiation = c_hf.f393;
         Hashtable hashtable = Message.params("rule term", s, "other term", s1);
         int j = schemeinstantiation.f1189.size();
         SchematicLetter schematicletter = null;

         for (int k = 0; k < j; k++) {
            SchematicLetter schematicletter1 = (SchematicLetter)schemeinstantiation.f1189.elementAt(k);
            if (schematicletter1 instanceof C_w_C) {
               schematicletter = schematicletter1;
               break;
            }
         }

         C_KE c_ke = new C_KE(expression, 250, derivationlinechecker.f935.f317.f915.frame, hashtable, schematicletter);
         c_ke.m1845(c_n_d);
         if (!m787(derivationlinechecker, c_hf, "derdlg013", hashtable, c_ke, c_n_d, null)) {
            return false;
         } else {
            String s2 = LogicProgram.m995(c_ke.getText(), f444, maggie);
            if (!schemeinstantiation.m1882(schematicletter.toString(), s2)) {
               derivationlinechecker.m1622(schemeinstantiation.f1190, schemeinstantiation.f1191);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m790(DerivationLineChecker derivationlinechecker, C_HF c_hf) {
      if (derivationlinechecker.f935.f317.f915.serialMode) {
         derivationlinechecker.f935.f317.f915.complete = false;
         derivationlinechecker.m1621("dererr064");
         return false;
      } else {
         C_n_D c_n_d = new C_n_D(250);
         SchemeInstantiation schemeinstantiation = c_hf.f393;
         Expression expression = derivationlinechecker.m1628(-1);
         Term term = (Term)expression.getChild(0);
         Term term1 = (Term)expression.getChild(1);
         Term term2 = (Term)derivationlinechecker.f941.getChild(0);
         Term term3 = (Term)derivationlinechecker.f941.getChild(1);
         Hashtable hashtable = Message.params(
            "left premise term",
            "\\l" + term + "\\l",
            "left conclusion term",
            "\\l" + term2 + "\\l",
            "right premise term",
            "\\l" + term1 + "\\l",
            "right conclusion term",
            "\\l" + term3 + "\\l"
         );
         C_Y c_y = new C_Y(term2, 250, derivationlinechecker.f935.f317.f915.frame, hashtable);
         c_y.m1845(c_n_d);
         if (!m787(derivationlinechecker, c_hf, "derdlg015", hashtable, c_y, c_n_d, new Term[]{term})) {
            return false;
         } else {
            SchematicLetter schematicletter = (SchematicLetter)schemeinstantiation.f1189.elementAt(0);
            String s = LogicProgram.m995(c_y.getText(), f444, maggie);
            if (!schemeinstantiation.m1882(schematicletter.toString(), s)) {
               derivationlinechecker.m1622(schemeinstantiation.f1190, schemeinstantiation.f1191);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m791(DerivationLineChecker derivationlinechecker, C_GA c_ga) {
      if (derivationlinechecker.f944 && derivationlinechecker.f941 != null && !c_ga.f345) {
         c_ga.f343 = derivationlinechecker.f941.m1231(derivationlinechecker.m1628(-1));
         if (c_ga.f345 = c_ga.f343 != null) {
            return true;
         }
      } else {
         c_ga.f345 = false;
      }

      if (derivationlinechecker.f935.f317.f915.serialMode) {
         derivationlinechecker.f935.f317.f915.complete = false;
         derivationlinechecker.m1621("dererr064");
         return false;
      } else {
         SizedPanel sizedpanel = new SizedPanel();
         Message message = C_n_.get("derdlg020");
         sizedpanel.add(C_q_B.m2031(C_n_.m1960(message.text, null, derivationlinechecker)), "North");
         SizedPanel sizedpanel1 = new SizedPanel();
         sizedpanel1.m934(LogicProgram.f541.width * 3 / 4);
         sizedpanel1.m937(true);
         sizedpanel1.setBorder(new BevelBorder(1));
         sizedpanel.add(sizedpanel1, "Center");
         Expression expression = derivationlinechecker.m1628(-1);
         String s = LPDerivation.officialIE ? expression.m1209(1) : expression.m1207(1);
         FormulaEntryField formulaentryfield = new FormulaEntryField(LogicProgram.m995(s, maggie, f444), derivationlinechecker.f935.f317.f915.frame);
         formulaentryfield.setEditable(false);
         formulaentryfield.getCaret().setVisible(true);
         formulaentryfield.m1787(true);
         formulaentryfield.m1789(true);
         sizedpanel1.add(formulaentryfield, "North");
         C_y_ c_y_ = new C_y_(derivationlinechecker.f935, message.buttons);
         c_y_.m447("just", derivationlinechecker);
         c_y_.m447("edit", formulaentryfield);
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.f935.f317.f915.frame, "Line " + derivationlinechecker.f935.m30(), sizedpanel, c_y_.f272
         );
         formulaentryfield.f425 = messagedialog;
         formulaentryfield.m715(messagedialog);
         Rectangle rectangle = LogicProgram.m1035(derivationlinechecker.f935.f324, null);
         messagedialog.m1314(0);
         messagedialog.m1315(c_y_);
         messagedialog.pack();
         messagedialog.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         if (messagedialog.f790 != 0) {
            derivationlinechecker.m1621("dererr028");
            derivationlinechecker.f935.f317.f915.abort(true);
            return false;
         } else {
            int[] aint = new int[]{formulaentryfield.getSelectionStart(), formulaentryfield.getSelectionEnd()};
            String s1 = formulaentryfield.getText();
            s1 = LogicProgram.m996(s1, f444, maggie, aint);
            C_DD c_dd = new C_DD(s1);
            C_DD c_dd1 = c_dd.m480(aint[0], aint[1]);
            c_ga.f343 = c_dd1.m473();
            return true;
         }
      }
   }

   static boolean m792(DerivationLineChecker derivationlinechecker, C_GA c_ga) {
      if (derivationlinechecker.f935.f317.f915.serialMode && derivationlinechecker.f956 == null) {
         derivationlinechecker.f935.f317.f915.complete = false;
         derivationlinechecker.m1621("dererr064");
         return false;
      } else {
         SizedPanel sizedpanel = new SizedPanel();
         Message message = C_n_.get("derdlg021");
         sizedpanel.add(C_q_B.m2031(C_n_.m1960(message.text, null, derivationlinechecker)), "North");
         SizedPanel sizedpanel1 = new SizedPanel();
         sizedpanel1.setBorder(new BevelBorder(1));
         sizedpanel.add(sizedpanel1);
         EditableTextPane editabletextpane = new EditableTextPane();
         sizedpanel1.add(editabletextpane, "Center");
         C_y_ c_y_ = new C_y_(derivationlinechecker.f935, message.buttons);
         c_y_.m447("just", derivationlinechecker);
         c_y_.m447("edit", editabletextpane);
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.f935.f317.f915.frame, "Line " + derivationlinechecker.f935.m30(), sizedpanel, c_y_.f272
         );
         Rectangle rectangle = LogicProgram.m1035(derivationlinechecker.f935.f324, null);
         messagedialog.m1314(0);
         messagedialog.m1315(c_y_);
         if (!messagedialog.m1327(derivationlinechecker.f956, new EditableTextPane[]{editabletextpane}, 0)) {
            if (derivationlinechecker.f935.f317.f915.serialMode) {
               derivationlinechecker.f935.f317.f915.complete = false;
               derivationlinechecker.m1621("dererr064");
               messagedialog.dispose();
               return false;
            }

            messagedialog.pack();
            messagedialog.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            messagedialog.dispose();
         }

         if (messagedialog.f790 != 0) {
            derivationlinechecker.m1621("dererr028");
            derivationlinechecker.f935.f317.f915.abort(true);
            return false;
         } else {
            return m793(derivationlinechecker, c_ga, (Rule)c_y_.m449("rule"));
         }
      }
   }

   static boolean m793(DerivationLineChecker derivationlinechecker, C_GA c_ga, Rule rule) {
      return m794(derivationlinechecker, c_ga, rule, null);
   }

   static boolean m794(DerivationLineChecker derivationlinechecker, C_GA c_ga, Rule rule, SchematicRule schematicrule) {
      boolean flag = !C_GA.m601(derivationlinechecker, c_ga.f343, schematicrule);
      Vector vector = C_GA.m626(derivationlinechecker, c_ga.f343, rule, schematicrule);
      boolean flag1 = vector == null;
      int i = !flag && !flag1 ? vector.size() : 0;
      if (i == 0) {
         if (c_ga.f345) {
            while (c_ga.f343.depth > 0) {
               c_ga.f343.depth--;
               flag = !C_GA.m601(derivationlinechecker, c_ga.f343, schematicrule);
               vector = C_GA.m626(derivationlinechecker, c_ga.f343, rule, schematicrule);
               if (!flag && vector != null) {
                  flag1 = false;
                  i = vector.size();
               } else {
                  i = 0;
               }

               if (i != 0) {
                  break;
               }
            }
         }

         if (i == 0) {
            Hashtable hashtable = new Hashtable();
            Message.putParam(hashtable, "inner rule", rule.f820);
            Message.putParam(hashtable, "inner exp", "\\l" + derivationlinechecker.m1628(-1).m1220(c_ga.f343) + "\\l");
            if (schematicrule != null) {
               Message.putParam(hashtable, "condition name", schematicrule.f820);
            }

            String s;
            if (flag) {
               s = "dererr099";
            } else if (flag1) {
               s = schematicrule == null ? "dererr085" : "dererr095";
            } else {
               s = schematicrule == null ? "dererr096" : "dererr097";
            }

            derivationlinechecker.m1622(s, hashtable);
            return false;
         }
      }

      int j = derivationlinechecker.f941 == null ? m780(derivationlinechecker, vector) : 0;
      if (j == -1) {
         if (derivationlinechecker.f935.f317.f915.serialMode) {
            derivationlinechecker.f935.f317.f915.complete = false;
            derivationlinechecker.m1621("dererr064");
         } else {
            derivationlinechecker.m1621("dererr028");
            derivationlinechecker.f935.f317.f915.abort(true);
         }

         return false;
      } else {
         C_GA c_ga1 = (C_GA)vector.elementAt(j);
         if (c_ga1.f346 != null) {
            if (!m782(derivationlinechecker, c_ga1)) {
               return false;
            }

            if (c_ga1.m620(derivationlinechecker) == null) {
               return false;
            }
         }

         c_ga.f344 = c_ga1.f344;
         c_ga.f346 = c_ga1.f346;
         c_ga.f347 = c_ga1.f347;
         c_ga.f348 = c_ga1.f348;
         c_ga.f349 = c_ga1.f349;
         c_ga.f350 = c_ga1.f350;
         c_ga.f351 = c_ga1.f351;
         c_ga.f352 = c_ga1.f352;
         c_ga.f353 = c_ga1.f353;
         c_ga.f354 = c_ga1.f354;
         return true;
      }
   }

   static boolean m795(DerivationLineChecker derivationlinechecker, C_GA c_ga) {
      if (derivationlinechecker.f935.f317.f915.serialMode && derivationlinechecker.f956 == null) {
         derivationlinechecker.f935.f317.f915.complete = false;
         derivationlinechecker.m1621("dererr064");
         return false;
      } else {
         SizedPanel sizedpanel = new SizedPanel();
         SizedPanel sizedpanel1 = new SizedPanel();
         Message message = C_n_.get("derdlg022");
         sizedpanel1.add(C_q_B.m2031(C_n_.m1960(message.text, null, derivationlinechecker)), "North");
         SizedPanel sizedpanel2 = new SizedPanel();
         sizedpanel2.setBorder(new BevelBorder(1));
         sizedpanel1.add(sizedpanel2, "Center");
         EditableTextPane editabletextpane = new EditableTextPane();
         sizedpanel2.add(editabletextpane, "Center");
         sizedpanel.add(sizedpanel1, "North");
         SizedPanel sizedpanel3 = new SizedPanel();
         Message message1 = C_n_.get("derdlg023");
         sizedpanel3.add(C_q_B.m2031(C_n_.m1960(message1.text, null, derivationlinechecker)), "North");
         SizedPanel sizedpanel4 = new SizedPanel();
         sizedpanel4.setBorder(new BevelBorder(1));
         sizedpanel3.add(sizedpanel4, "Center");
         EditableTextPane editabletextpane1 = new EditableTextPane();
         sizedpanel4.add(editabletextpane1, "Center");
         sizedpanel.add(sizedpanel3, "South");
         C_y_ c_y_ = new C_y_(derivationlinechecker.f935, message.buttons);
         c_y_.m447("just", derivationlinechecker);
         c_y_.m447("ruleEdit", editabletextpane);
         c_y_.m447("condEdit", editabletextpane1);
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.f935.f317.f915.frame, "Line " + derivationlinechecker.f935.m30(), sizedpanel, c_y_.f272
         );
         Rectangle rectangle = LogicProgram.m1035(derivationlinechecker.f935.f324, null);
         messagedialog.m1314(0);
         messagedialog.m1315(c_y_);
         if (!messagedialog.m1327(derivationlinechecker.f956, new EditableTextPane[]{editabletextpane, editabletextpane1}, 0)) {
            if (derivationlinechecker.f935.f317.f915.serialMode) {
               derivationlinechecker.f935.f317.f915.complete = false;
               derivationlinechecker.m1621("dererr064");
               messagedialog.dispose();
               return false;
            }

            messagedialog.pack();
            messagedialog.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            messagedialog.dispose();
         }

         if (messagedialog.f790 != 0) {
            derivationlinechecker.m1621("dererr028");
            derivationlinechecker.f935.f317.f915.abort(true);
            return false;
         } else {
            return m794(derivationlinechecker, c_ga, (Rule)c_y_.m449("rule"), (SchematicRule)c_y_.m449("condition"));
         }
      }
   }

   static String m796(String s) {
      if (s == null) {
         s = "User";
      }

      C_M c_m = new C_M();
      EditableTextPane editabletextpane = new EditableTextPane(s, 300);
      C_ZE c_ze = new C_ZE("Please supply a name for this problem");
      ModuleFrame moduleframe = new ModuleFrame();
      editabletextpane.select(0, 2147483647);
      c_ze.setFocusable(false);
      c_m.setLayout(new C_m_A(0));
      c_m.add(c_ze);
      c_m.add(editabletextpane);
      String[] astring = new String[]{"OK", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(moduleframe, "", c_m, astring);
      messagedialog.m1314(0);
      editabletextpane.requestFocus();
      messagedialog.m1322(null);
      moduleframe.dispose();
      if (messagedialog.f790 != 0) {
         return null;
      } else {
         s = editabletextpane.getText();
         if ((s = s.trim()).equals("")) {
            Message message = Message.get("not006");
            MessageDialog.showMessage(Message.get("not006"), null, null, null);
            return null;
         } else if (LPDerivation.problems.m1780(s) != null) {
            LogicProgram.m972("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static void m797(LPDerivation lpderivation) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      C_JE c_je = C_JE.m726(LogicProgram.openDataFile("tips", false), 20);
      if (c_je == null) {
         LogicProgram.m971("not001", "the derivation advice file");
      } else {
         JScrollPane jscrollpane = new JScrollPane();
         C_JB c_jb = new C_JB(jscrollpane, c_je);
         jscrollpane.setHorizontalScrollBarPolicy(31);
         jscrollpane.setViewportView(c_jb);
         c_jb.add(c_je);
         c_jb.setBackground(dialogWhite);
         jpanel.add(jscrollpane, "Center");
         MessageDialog messagedialog = new MessageDialog(lpderivation.frame, "Strategic Advice", jpanel, new String[]{"OK", "Print"});
         Container container = messagedialog.getParent();

         while (container != null && !(container instanceof Window)) {
            container = container.getParent();
         }

         if (container != null) {
            container.addComponentListener(c_jb);
         }

         Dimension dimension = new Dimension(40 * LogicProgram.fontSize, 24 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.m1323(MessageDialog.m1321(dimension), true);
         if (messagedialog.f790 == 1) {
            C_v_B.m2130(new TaggedRecord(LogicProgram.openDataFile("tips", false)), LPDerivation.printQueue);
         }

         lpderivation.requestFocus();
      }
   }

   static void m798(LPDerivation lpderivation, BusyIndicator busyindicator) {
      if (lpderivation == null && LPDerivation.ruleQuery != null) {
         LPDerivation.ruleQuery.requestFocus();
      } else {
         if (busyindicator != null) {
            busyindicator.m2162(true);
         }

         JPanel jpanel = new JPanel();
         jpanel.setLayout(new BorderLayout());
         ProblemListView problemlistview = new ProblemListView(false);
         problemlistview.setEnabled(false);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         jpanel.add(jscrollpane, "Center");
         Object object;
         if (lpderivation == null) {
            object = new C_d_F(new ModuleFrame(), "Inference Rules", jpanel, new String[]{"OK"});
            LPDerivation.ruleQuery = (C_d_F)object;
         } else {
            object = new MessageDialog(lpderivation.frame, "Available Inference Rules", jpanel, new String[]{"OK"});
         }

         problemlistview.f898 = 0;
         int[] aint = new int[3];
         if (LPDerivation.userRules != null && LPDerivation.userRules.size() > 0) {
            Message message = C_n_.get("dertxt001");
            if (message != null) {
               String[] astring = LogicProgram.m1003(LogicProgram.m1004(message.text));
               int i = astring.length;

               for (int j = 0; j < i; j++) {
                  C_ZE c_ze = new C_ZE(astring[j], 2);
                  c_ze.setForeground(dialogBlue);
                  problemlistview.m1526(c_ze);
               }
            }

            Vector vector;
            if (lpderivation == null) {
               vector = LogicProgram.f534.f1472.m2071(LPDerivation.userRules);
            } else {
               vector = lpderivation.enabledRules(LPDerivation.userRules);
            }

            m799(problemlistview, aint, LPDerivation.userRules, vector);
         }

         Vector vector1;
         if (lpderivation == null) {
            vector1 = LogicProgram.f534.f1472.m2071(LogicProgram.f534);
         } else {
            vector1 = lpderivation.enabledRules(LogicProgram.f534);
         }

         m799(problemlistview, aint, LogicProgram.f534, vector1);
         C_n_F c_n_f;
         if (lpderivation == null) {
            c_n_f = LogicProgram.f534.f1472.m2072(LogicProgram.f534.f1468);
         } else {
            c_n_f = lpderivation.enabledTheorems(LogicProgram.f534.f1468);
         }

         m800(problemlistview, aint, LogicProgram.f534.f1468, c_n_f);
         int k = problemlistview.getComponentCount();
         C_u_ c_u_ = new C_u_(problemlistview, aint.length, aint);

         for (int l = 0; l < k; l++) {
            Component component = problemlistview.getComponent(l);
            if (component instanceof Container) {
               ((Container)component).setLayout(c_u_);
            }
         }

         CellPanel cellpanel = new CellPanel();
         cellpanel.setLayout(new BoxLayout(cellpanel, 2));
         C_E c_e = new C_E(true);
         cellpanel.add(c_e);
         C_ZE c_ze1;
         if (lpderivation == null) {
            c_ze1 = new C_ZE("usable with Interchange of Equivalents");
         } else {
            c_ze1 = new C_ZE("available for this derivation");
         }

         cellpanel.add(c_ze1);
         jpanel.add(cellpanel, "South");
         if (busyindicator != null) {
            busyindicator.m2162(false);
         }

         Dimension dimension = new Dimension(22 * LogicProgram.fontSize, 26 * LogicProgram.fontSize);
         ((MessageDialog)object).setSize(dimension);
         ((MessageDialog)object).m1317("derRules");
         if (lpderivation == null) {
            ((MessageDialog)object).m1324(MessageDialog.m1321(dimension), true);
         } else {
            ((MessageDialog)object).m1323(MessageDialog.m1321(dimension), true);
            lpderivation.requestFocus();
         }
      }
   }

   static void m799(ProblemListView problemlistview, int[] aint, RuleTable ruletable, Vector vector) {
      Enumeration enumeration = ruletable.f1469.elements();

      while (enumeration.hasMoreElements()) {
         String s = (String)enumeration.nextElement();
         Vector vector1 = (Vector)ruletable.f1471.get(s);
         C_E c_e = null;
         if (vector1 != null) {
            int i = vector1.size();

            for (int j = 0; j < i; j++) {
               C_ZE c_ze = new C_ZE(LogicProgram.m1004((String)vector1.elementAt(j)), 2);
               c_ze.setForeground(dialogBlue);
               problemlistview.m1526(c_ze);
            }
         }

         CellPanel cellpanel = new CellPanel();
         cellpanel.setLayout(new FlowLayout(0, 0, 0));
         int k = aint.length;
         if (k == 3) {
            c_e = new C_E(vector.contains(s));
            cellpanel.add(m801(c_e));
         }

         C_ZE c_ze1;
         cellpanel.add(m801(c_ze1 = new C_ZE(s)));
         c_ze1.setPreferredSize(new Dimension(6 * LogicProgram.fontSize, c_ze1.getPreferredSize().height));
         c_ze1.setHorizontalAlignment(2);
         Rule rule = LPDerivation.getRule(s);
         C_ZE c_ze2;
         cellpanel.add(m801(c_ze2 = LogicProgram.m992(rule == null ? "" : rule.m958(" . ", " .: "))));
         problemlistview.m1526(cellpanel);
         int i1 = LogicProgram.fontSize * 5 / 14;
         int l;
         if (k == 3 && (l = c_e.getPreferredSize().width) > aint[k - 3]) {
            aint[k - 3] = l;
         }

         if ((l = c_ze1.getPreferredSize().width + i1) > aint[k - 2]) {
            aint[k - 2] = l;
         }

         if ((l = c_ze2.getPreferredSize().width) > aint[k - 1]) {
            aint[k - 1] = l;
         }
      }
   }

   static void m800(ProblemListView problemlistview, int[] aint, TheoremTable theoremtable, C_n_F c_n_f) {
      Enumeration enumeration = theoremtable.f1464.m1985();

      while (enumeration.hasMoreElements()) {
         Integer integer = (Integer)enumeration.nextElement();
         Vector vector = (Vector)theoremtable.f1465.get(integer);
         C_E c_e = null;
         if (vector != null) {
            int i = vector.size();

            for (int j = 0; j < i; j++) {
               C_ZE c_ze = new C_ZE(LogicProgram.m1004((String)vector.elementAt(j)), 2);
               c_ze.setForeground(dialogBlue);
               problemlistview.m1526(c_ze);
            }
         }

         CellPanel cellpanel = new CellPanel();
         cellpanel.setLayout(new FlowLayout(0, 0, 0));
         int k = aint.length;
         if (k == 3) {
            c_e = new C_E(c_n_f.m1983(integer));
            cellpanel.add(m801(c_e));
         }

         C_ZE c_ze1;
         cellpanel.add(m801(c_ze1 = new C_ZE("T" + integer)));
         Theorem theorem = LogicProgram.m1025(integer);
         C_ZE c_ze2;
         cellpanel.add(m801(c_ze2 = LogicProgram.m992(theorem == null ? "" : theorem.toString())));
         problemlistview.m1526(cellpanel);
         int i1 = LogicProgram.fontSize * 5 / 14;
         int l;
         if (k == 3 && (l = c_e.getPreferredSize().width) > aint[k - 3]) {
            aint[k - 3] = l;
         }

         if ((l = c_ze1.getPreferredSize().width + i1) > aint[k - 2]) {
            aint[k - 2] = l;
         }

         if ((l = c_ze2.getPreferredSize().width) > aint[k - 1]) {
            aint[k - 1] = l;
         }
      }
   }

   static CellPanel m801(Component component) {
      CellPanel cellpanel = new CellPanel(false);
      cellpanel.setLayout(new FlowLayout(0, 0, 0));
      cellpanel.add(component);
      return cellpanel;
   }
}
