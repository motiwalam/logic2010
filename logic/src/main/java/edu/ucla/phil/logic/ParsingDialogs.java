package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

class ParsingDialogs implements LogicConstants {
   static String[] SYMBOLS = LogicProgram.symbols;

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
      Message message = ParsingMessage.get(s);
      ParsingDialogHandler parsingdialoghandler = null;
      if (message.buttons != null) {
         parsingdialoghandler = new ParsingDialogHandler(message.buttons);
         if (hashtable1 != null) {
            parsingdialoghandler.setProperties(hashtable1);
         }
      }

      MessageDialog.showMessage(message, hashtable, point, parsingdialoghandler);
   }

   static boolean deleteProblemOrWork(LPParsing lpparsing, Point point) {
      if (lpparsing.problem.notationChooser.getSelectedIndex() != -1) {
         RadioGroupPanel radiogrouppanel = new RadioGroupPanel();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            radiogrouppanel.add(ajradiobutton[j] = new LogicRadioButton(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpparsing.frame, "", radiogrouppanel, astring2);
         messagedialog.showAt(null);
         if (messagedialog.selectedButton != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            lpparsing.problem.resetWork();
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lpparsing.problemIndex != -1 || lpparsing.problem.statement != null && !lpparsing.problem.statement.equals("")) {
         LogicLabel logiclabel = new LogicLabel("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog1 = new MessageDialog(lpparsing.frame, "", logiclabel, astring1);
         messagedialog1.showAt(null);
         if (messagedialog1.selectedButton != 0) {
            return false;
         }
      }

      if (lpparsing.problemIndex != -1) {
         LPParsing.problems.removeProblem(lpparsing.problemIndex);
         LPParsing.saveProblems();
      }

      lpparsing.newProblem();
      return true;
   }

   static boolean deleteWork(LPParsing lpparsing, Point point) {
      LogicLabel logiclabel = new LogicLabel("Delete the work on this problem?");
      String[] astring = new String[]{"OK", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(lpparsing.frame, "", logiclabel, astring);
      messagedialog.showAt(point);
      if (messagedialog.selectedButton != 0) {
         return false;
      } else {
         lpparsing.removeWork();
         return true;
      }
   }

   static boolean selectNextProblem(LPParsing lpparsing) {
      if (confirmSaveChanges(lpparsing, null)) {
         synchronized (LPParsing.problems) {
            int i = lpparsing.problemIndex + 1;
            if (i != 0 && LPParsing.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPParsing.problems.getRecordAt(i);
            if (s == null) {
               return selectProblem(lpparsing);
            }

            lpparsing.loadProblem(s);
            lpparsing.problemIndex = i;
            LPParsing.problems.replaceProblem(lpparsing.saveProblem(), i);
            if (LogicModule.eraseWork) {
               lpparsing.removeWork();
            }
         }
      }

      lpparsing.requestFocus();
      return true;
   }

   static boolean selectProblem(LPParsing lpparsing) {
      boolean flag = false;
      if (confirmSaveChanges(lpparsing, null)) {
         synchronized (LPParsing.problems) {
            ProblemListView problemlistview = createProblemListView(lpparsing, false, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpparsing.frame, ProblemSet.markTitle("Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("parChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               int i = problemlistview.getSelectedProblem(problemlistview.rowToProblem);
               LPParsing lpparsing1;
               if ((lpparsing1 = LPParsing.openInstance(i, true)) != null) {
                  lpparsing1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lpparsing.loadProblem(LPParsing.problems.getRecordAt(i));
                  lpparsing.problemIndex = i;
                  LPParsing.problems.replaceProblem(lpparsing.saveProblem(), i);
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

   static int[] chooseProblemsToSubmit(LPParsing lpparsing) {
      int[] aint = null;
      if (confirmSaveChanges(lpparsing, null)) {
         synchronized (LPParsing.problems) {
            ProblemListView problemlistview = createProblemListView(lpparsing, true, false, null);
            if (LPParsing.submitExam) {
               problemlistview.clearSelection();
            }

            Vector vector = LPParsing.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = TaggedRecord.nameOf(s);
                  int j = LPParsing.problems.indexOfName(s1);
                  if (j != -1) {
                     problemlistview.selectProblem(j, problemlistview.rowToProblem);
                  }
               }
            }

            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Submit", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpparsing.frame, ProblemSet.markTitle("Submit Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("parChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            }
         }
      }

      lpparsing.requestFocus();
      return aint;
   }

   static int[] chooseProblemsToUpload(LPParsing lpparsing) {
      int[] aint = null;
      if (confirmSaveChanges(lpparsing, null)) {
         synchronized (LPParsing.problems) {
            ProblemListView problemlistview = createProblemListView(lpparsing, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            MessageTextArea messagetextarea = new MessageTextArea(LogicProgram.expandEscapes(Message.getText("not089")));
            messagetextarea.setLineWrap(true);
            messagetextarea.setWrapStyleWord(true);
            sizedpanel.add(messagetextarea, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpparsing.frame, ProblemSet.markTitle("Upload Problems"), sizedpanel, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("parChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            }
         }
      }

      lpparsing.requestFocus();
      return aint;
   }

   static void deleteProblems(LPParsing lpparsing) {
      synchronized (LPParsing.problems) {
         ProblemListView problemlistview = createProblemListView(lpparsing, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpparsing.frame, ProblemSet.markTitle("Delete Problems"), jscrollpane, astring);
         problemlistview.setDialog(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.setBoundsKey("parChosen");
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
                     ParsingProblemEntry parsingproblementry1 = (ParsingProblemEntry)LPParsing.problems.getEntryAt(l);
                     if (parsingproblementry1 != null) {
                        TaggedRecord taggedrecord1 = new TaggedRecord(parsingproblementry1.name);
                        if (!LPParsing.isExample(taggedrecord1.getName())) {
                           parsingproblementry1.name = LPParsing.removeWork(taggedrecord1);
                           parsingproblementry1.state = 0;
                           LPParsing lpparsing2 = LPParsing.openInstance(l, false);
                           if (lpparsing2 != null) {
                              lpparsing2.loadProblem(parsingproblementry1.name);
                           }
                        }
                     }
                  }
               }

               LPParsing.saveProblems();
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
                     ParsingProblemEntry parsingproblementry = (ParsingProblemEntry)LPParsing.problems.getEntryAt(j);
                     if (parsingproblementry != null) {
                        TaggedRecord taggedrecord = new TaggedRecord(parsingproblementry.name);
                        if (!LPParsing.isExercise(taggedrecord.getName())) {
                           LPParsing lpparsing1 = LPParsing.openInstance(j, false);
                           LPParsing.problems.removeProblem(j);
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

   static int[] printProblems(LPParsing lpparsing) {
      Object object = null;
      if (confirmSaveChanges(lpparsing, null)) {
         synchronized (LPParsing.problems) {
            ProblemListView problemlistview = createProblemListView(lpparsing, true, false, LPParsing.noPrint);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Print Results", "Print List", "Cancel"};
            problemlistview.cancelButton = 2;
            MessageDialog messagedialog = new MessageDialog(lpparsing.frame, ProblemSet.markTitle("Print Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("parChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               ParsingResultsPage.printResults(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            } else if (messagedialog.selectedButton == 1) {
               ParsingStatementsPage.printStatements(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            }
         }
      }

      lpparsing.requestFocus();
      return (int[])object;
   }

   static void enterSubmittedProblem(LPParsing lpparsing) {
      if (confirmSaveChanges(lpparsing, null)) {
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.centeredLocation(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         EditableTextPane editabletextpane = new EditableTextPane();
         String[] astring = new String[]{"OK", "Cancel"};
         editabletextpane.setBackground(Color.WHITE);
         jscrollpane.setViewportView(editabletextpane);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "Submitted Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.setBoundsKey("parSubmitted");
         editabletextpane.requestFocus();
         messagedialog.showAt(point, true);
         moduleframe.dispose();
         if (messagedialog.selectedButton == 0) {
            lpparsing.loadProblem(editabletextpane.getText());
         }
      }

      lpparsing.requestFocus();
   }

   static ProblemListView createProblemListView(LPParsing lpparsing, boolean flag, boolean flag1, ProblemSelector problemselector) {
      return LPParsing.problems.createListView(lpparsing, LPParsing.exercises, flag, flag1, LPParsing.monoProbs, problemselector);
   }

   static boolean confirmSaveChanges(LPParsing lpparsing, Point point) {
      String s = lpparsing.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lpparsing.frame.show();
         LogicLabel logiclabel = new LogicLabel("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpparsing.frame, "", logiclabel, astring);
         messagedialog.showAt(point);
         return messagedialog.selectedButton == 0 && !lpparsing.saveProblems(s)
            ? false
            : messagedialog.selectedButton == 0 || messagedialog.selectedButton == 1;
      }
   }

   static String askProblemName(String s) {
      if (s == null) {
         s = "User";
      }

      SizedPanel sizedpanel = new SizedPanel();
      EditableTextPane editabletextpane = new EditableTextPane(s, 300);
      LogicLabel logiclabel = new LogicLabel("Please supply a name for this problem");
      ModuleFrame moduleframe = new ModuleFrame();
      editabletextpane.select(0, 2147483647);
      logiclabel.setFocusable(false);
      sizedpanel.setLayout(new VerticalStackLayout(0));
      sizedpanel.add(logiclabel);
      sizedpanel.add(editabletextpane);
      String[] astring = new String[]{"OK", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(moduleframe, "", sizedpanel, astring);
      messagedialog.setDefaultButtonIndex(0);
      editabletextpane.requestFocus();
      messagedialog.showAt(null);
      moduleframe.dispose();
      if (messagedialog.selectedButton != 0) {
         return null;
      } else {
         s = editabletextpane.getText();
         if ((s = s.trim()).equals("")) {
            MessageDialog.showMessage(Message.get("not006"), null, null, null);
            return null;
         } else if (LPParsing.problems.getRecord(s) != null) {
            LogicProgram.showProblemError("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static void createUserProblem(LPParsing lpparsing) {
      if (confirmSaveChanges(lpparsing, null)) {
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.centeredLocation(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         FormulaEntryField formulaentryfield = new FormulaEntryField(moduleframe);
         if (lpparsing.lastUserProblem != null) {
            formulaentryfield.setText(LogicProgram.translateSymbols(lpparsing.lastUserProblem, maggie, SYMBOLS));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(formulaentryfield);
         formulaentryfield.setBackground(Color.WHITE);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "User Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.setBoundsKey("parUser");
         formulaentryfield.ownerDialog = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.showAt(point, true);
         moduleframe.dispose();
         if (messagedialog.selectedButton == 0) {
            String s = formulaentryfield.getText();
            if (!LPParsing.validateUserProblem(s)) {
               return;
            }

            lpparsing.lastUserProblem = LogicProgram.translateSymbols(s, SYMBOLS, maggie);
            lpparsing.loadUserProblem(lpparsing.lastUserProblem);
         }
      }

      lpparsing.requestFocus();
   }
}
