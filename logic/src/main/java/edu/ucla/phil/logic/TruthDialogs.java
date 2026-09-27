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

class TruthDialogs implements LogicConstants {
   static String[] displaySymbols = LogicProgram.symbols;

   static void showMessage(String s) {
      showMessage(s, null, null, null);
   }

   static void showMessage(String s, Point point) {
      showMessage(s, null, null, point);
   }

   static void showMessage(String s, Hashtable hashtable) {
      showMessage(s, hashtable, null, null);
   }

   static void showMessage(String s, Hashtable hashtable, Hashtable hashtable1, Point point) {
      Message message = TruthMessage.get(s);
      TruthDialogHandler truthdialoghandler = null;
      if (message.buttons != null) {
         truthdialoghandler = new TruthDialogHandler(message.buttons);
         if (hashtable1 != null) {
            truthdialoghandler.setProperties(hashtable1);
         }
      }

      MessageDialog.showMessage(message, hashtable, point, truthdialoghandler);
   }

   static String askProblemName(LPTruthAnalysis lptruthanalysis, String s) {
      if (s == null) {
         s = "User";
      }

      SizedPanel sizedpanel = new SizedPanel();
      EditableTextPane editabletextpane = new EditableTextPane(s, 300);
      LogicLabel logiclabel = new LogicLabel("Please supply a name for this problem");
      editabletextpane.select(0, 2147483647);
      logiclabel.setFocusable(false);
      sizedpanel.setLayout(new VerticalStackLayout(0));
      sizedpanel.add(logiclabel);
      sizedpanel.add(editabletextpane);
      String[] astring = new String[]{"OK", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, "", sizedpanel, astring);
      messagedialog.setDefaultButtonIndex(0);
      editabletextpane.requestFocus();
      messagedialog.showAt(null);
      if (messagedialog.selectedButton != 0) {
         return null;
      } else {
         s = editabletextpane.getText();
         if ((s = s.trim()).equals("")) {
            Message message = Message.get("not006");
            MessageDialog.showMessage(Message.get("not006"), null, null, null);
            return null;
         } else if (LPTruthAnalysis.problems.getRecord(s) != null) {
            LogicProgram.showProblemError("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static boolean deleteProblemOrWork(LPTruthAnalysis lptruthanalysis, Point point) {
      if (lptruthanalysis.hasWork()) {
         RadioGroupPanel radiogrouppanel = new RadioGroupPanel();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            radiogrouppanel.add(ajradiobutton[j] = new LogicRadioButton(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, "", radiogrouppanel, astring2);
         messagedialog.showAt(null);
         if (messagedialog.selectedButton != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            lptruthanalysis.problem.clearWork();
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lptruthanalysis.problemIndex != -1 || lptruthanalysis.problem.statement != null && !lptruthanalysis.problem.statement.equals("")) {
         LogicLabel logiclabel = new LogicLabel("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog1 = new MessageDialog(lptruthanalysis.frame, "", logiclabel, astring1);
         messagedialog1.showAt(null);
         if (messagedialog1.selectedButton != 0) {
            return false;
         }
      }

      if (lptruthanalysis.problemIndex != -1) {
         LPTruthAnalysis.problems.removeProblem(lptruthanalysis.problemIndex);
         LPTruthAnalysis.saveProblems();
      }

      lptruthanalysis.newProblem();
      return true;
   }

   static boolean deleteWork(LPTruthAnalysis lptruthanalysis, Point point) {
      if (!lptruthanalysis.hasWork()) {
         return false;
      } else {
         LogicLabel logiclabel = new LogicLabel("Delete the work on this problem?");
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, "", logiclabel, astring);
         messagedialog.showAt(point);
         if (messagedialog.selectedButton != 0) {
            return false;
         } else {
            lptruthanalysis.problem.clearWork();
            return true;
         }
      }
   }

   static boolean selectNextProblem(LPTruthAnalysis lptruthanalysis) {
      if (confirmSaveChanges(lptruthanalysis, null)) {
         synchronized (LPTruthAnalysis.problems) {
            int i = lptruthanalysis.problemIndex + 1;
            if (i != 0 && LPTruthAnalysis.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPTruthAnalysis.problems.getRecordAt(i);
            if (s == null) {
               return chooseProblem(lptruthanalysis);
            }

            lptruthanalysis.loadProblem(s);
            lptruthanalysis.problemIndex = i;
            LPTruthAnalysis.problems.replaceProblem(lptruthanalysis.saveProblem(), i);
            if (LogicModule.eraseWork) {
               lptruthanalysis.problem.clearWork();
            }
         }
      }

      lptruthanalysis.requestFocus();
      return true;
   }

   static boolean chooseProblem(LPTruthAnalysis lptruthanalysis) {
      boolean flag = false;
      if (confirmSaveChanges(lptruthanalysis, null)) {
         synchronized (LPTruthAnalysis.problems) {
            ProblemListView problemlistview = createProblemListView(lptruthanalysis, false, false, null);
            problemlistview.setBackground(LogicConstants.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, ProblemSet.markTitle("Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("truChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               int i = problemlistview.getSelectedProblem(problemlistview.rowToProblem);
               LPTruthAnalysis lptruthanalysis1;
               if ((lptruthanalysis1 = LPTruthAnalysis.openInstance(i, true)) != null) {
                  lptruthanalysis1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lptruthanalysis.loadProblem(LPTruthAnalysis.problems.getRecordAt(i));
                  lptruthanalysis.problemIndex = i;
                  LPTruthAnalysis.problems.replaceProblem(lptruthanalysis.saveProblem(), i);
                  if (LogicModule.eraseWork) {
                     lptruthanalysis.problem.clearWork();
                  }

                  flag = true;
               }
            }
         }
      }

      lptruthanalysis.requestFocus();
      return flag;
   }

   static int[] chooseSubmitProblems(LPTruthAnalysis lptruthanalysis) {
      int[] aint = null;
      if (confirmSaveChanges(lptruthanalysis, null)) {
         synchronized (LPTruthAnalysis.problems) {
            ProblemListView problemlistview = createProblemListView(lptruthanalysis, true, false, null);
            if (LPTruthAnalysis.submitExam) {
               problemlistview.clearSelection();
            }

            Vector vector = LPTruthAnalysis.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = TaggedRecord.nameOf(s);
                  int j = LPTruthAnalysis.problems.indexOfName(s1);
                  if (j != -1) {
                     problemlistview.selectProblem(j, problemlistview.rowToProblem);
                  }
               }
            }

            problemlistview.setBackground(LogicConstants.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Submit", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, ProblemSet.markTitle("Submit Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("truChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            }
         }
      }

      lptruthanalysis.requestFocus();
      return aint;
   }

   static int[] chooseUploadProblems(LPTruthAnalysis lptruthanalysis) {
      int[] aint = null;
      if (confirmSaveChanges(lptruthanalysis, null)) {
         synchronized (LPTruthAnalysis.problems) {
            ProblemListView problemlistview = createProblemListView(lptruthanalysis, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            MessageTextArea messagetextarea = new MessageTextArea(LogicProgram.expandEscapes(Message.getText("not089")));
            messagetextarea.setLineWrap(true);
            messagetextarea.setWrapStyleWord(true);
            sizedpanel.add(messagetextarea, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, ProblemSet.markTitle("Upload Problems"), sizedpanel, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("truChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            }
         }
      }

      lptruthanalysis.requestFocus();
      return aint;
   }

   static void deleteMultipleProblems(LPTruthAnalysis lptruthanalysis) {
      synchronized (LPTruthAnalysis.problems) {
         ProblemListView problemlistview = createProblemListView(lptruthanalysis, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, ProblemSet.markTitle("Delete Problems"), jscrollpane, astring);
         problemlistview.setDialog(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.setBoundsKey("truChosen");
         problemlistview.requestFocus();
         messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
         if (messagedialog.selectedButton == 0) {
            int[] aint1 = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            if (aint1.length != 0) {
               Message message1 = Message.get("not091");
               ButtonChoiceHandler buttonchoicehandler1 = new ButtonChoiceHandler(message1.buttons);
               MessageDialog.showMessage(message1, null, null, buttonchoicehandler1);
               if (buttonchoicehandler1.choice == 0) {
                  int k = aint1.length;

                  while (--k >= 0) {
                     int l = aint1[k];
                     TruthProblemEntry truthproblementry1 = (TruthProblemEntry)LPTruthAnalysis.problems.getEntryAt(l);
                     if (truthproblementry1 != null) {
                        TaggedRecord taggedrecord1 = new TaggedRecord(truthproblementry1.name);
                        if (!LPTruthAnalysis.isExample(taggedrecord1.getName())) {
                           truthproblementry1.name = LPTruthAnalysis.removeWork(taggedrecord1);
                           truthproblementry1.state = 0;
                           LPTruthAnalysis lptruthanalysis2 = LPTruthAnalysis.openInstance(l, false);
                           if (lptruthanalysis2 != null) {
                              lptruthanalysis2.loadProblem(truthproblementry1.name);
                           }
                        }
                     }
                  }
               }

               LPTruthAnalysis.saveProblems();
            }
         } else if (messagedialog.selectedButton == 1) {
            int[] aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            if (aint.length != 0) {
               Message message = Message.get("not090");
               ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
               MessageDialog.showMessage(message, null, null, buttonchoicehandler);
               if (buttonchoicehandler.choice == 0) {
                  int i = aint.length;

                  while (--i >= 0) {
                     int j = aint[i];
                     TruthProblemEntry truthproblementry = (TruthProblemEntry)LPTruthAnalysis.problems.getEntryAt(j);
                     if (truthproblementry != null) {
                        TaggedRecord taggedrecord = new TaggedRecord(truthproblementry.name);
                        if (!LPTruthAnalysis.isExercise(taggedrecord.getName())) {
                           LPTruthAnalysis lptruthanalysis1 = LPTruthAnalysis.openInstance(j, false);
                           LPTruthAnalysis.problems.removeProblem(j);
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

   static int[] choosePrintProblems(LPTruthAnalysis lptruthanalysis) {
      int[] aint = null;
      if (confirmSaveChanges(lptruthanalysis, null)) {
         synchronized (LPTruthAnalysis.problems) {
            ProblemListView problemlistview = createProblemListView(lptruthanalysis, true, false, LPTruthAnalysis.noPrint);
            problemlistview.setBackground(LogicConstants.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Print", "Print Results", "Print List", "Cancel"};
            problemlistview.cancelButton = 3;
            MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, ProblemSet.markTitle("Print Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("truChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            } else if (messagedialog.selectedButton == 1) {
               TruthResultsPage.printResults(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            } else if (messagedialog.selectedButton == 2) {
               TruthStatementsPage.printStatements(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            }
         }
      }

      lptruthanalysis.requestFocus();
      return aint;
   }

   static void enterSubmittedProblem(LPTruthAnalysis lptruthanalysis) {
      if (confirmSaveChanges(lptruthanalysis, null)) {
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.centeredLocation(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         EditableTextPane editabletextpane = new EditableTextPane();
         String[] astring = new String[]{"OK", "Cancel"};
         editabletextpane.setBackground(Color.WHITE);
         jscrollpane.setViewportView(editabletextpane);
         MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, "Submitted Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.setBoundsKey("truSubmitted");
         editabletextpane.requestFocus();
         messagedialog.showAt(point, true);
         if (messagedialog.selectedButton == 0) {
            lptruthanalysis.loadProblem(editabletextpane.getText());
         }
      }

      lptruthanalysis.requestFocus();
   }

   static ProblemListView createProblemListView(LPTruthAnalysis lptruthanalysis, boolean flag, boolean flag1, ProblemSelector problemselector) {
      return LPTruthAnalysis.problems.createListView(lptruthanalysis, LPTruthAnalysis.exercises, flag, flag1, LPTruthAnalysis.monoProbs, problemselector);
   }

   static boolean confirmSaveChanges(LPTruthAnalysis lptruthanalysis, Point point) {
      String s = lptruthanalysis.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         LogicLabel logiclabel = new LogicLabel("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, "", logiclabel, astring);
         messagedialog.showAt(point);
         return messagedialog.selectedButton == 0 && !lptruthanalysis.saveProblems(s)
            ? false
            : messagedialog.selectedButton == 0 || messagedialog.selectedButton == 1;
      }
   }

   static void createUserProblem(LPTruthAnalysis lptruthanalysis) {
      if (confirmSaveChanges(lptruthanalysis, null)) {
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 15 * LogicProgram.fontSize);
         Point point = MessageDialog.centeredLocation(dimension);
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new BorderLayout());
         JScrollPane jscrollpane = new JScrollPane();
         ScaledCheckBox scaledcheckbox = new ScaledCheckBox("Truth Table Only");
         jpanel.add(jscrollpane, "Center");
         jpanel.add(scaledcheckbox, "South");
         FormulaEntryField formulaentryfield = new FormulaEntryField(lptruthanalysis.frame);
         if (lptruthanalysis.lastUserProblem != null) {
            formulaentryfield.setText(LogicProgram.translateSymbols(lptruthanalysis.lastUserProblem, maggie, displaySymbols));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(formulaentryfield);
         formulaentryfield.setBackground(Color.WHITE);
         MessageDialog messagedialog = new MessageDialog(lptruthanalysis.frame, "User Problem", jpanel, astring);
         messagedialog.setSize(dimension);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.setBoundsKey("truUser");
         formulaentryfield.ownerDialog = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.showAt(point, true);
         if (messagedialog.selectedButton == 0) {
            String s = formulaentryfield.getText();
            if (!LPTruthAnalysis.validateUserProblem(s)) {
               return;
            }

            lptruthanalysis.lastUserProblem = LogicProgram.translateSymbols(s, displaySymbols, maggie);
            lptruthanalysis.loadUserProblem(lptruthanalysis.lastUserProblem, scaledcheckbox.isSelected());
         }
      }

      lptruthanalysis.requestFocus();
   }
}
