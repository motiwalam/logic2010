package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

class RecognitionDialogs implements LogicConstants {
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
      Message message = RecognitionMessage.get(s);
      RecognitionDialogHandler recognitiondialoghandler = null;
      if (message.buttons != null) {
         recognitiondialoghandler = new RecognitionDialogHandler(message.buttons);
         if (hashtable1 != null) {
            recognitiondialoghandler.setProperties(hashtable1);
         }
      }

      MessageDialog.showMessage(message, hashtable, point, recognitiondialoghandler);
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
         } else if (LPRecognition.problems.getRecord(s) != null) {
            LogicProgram.showProblemError("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static boolean confirmSaveChanges(LPRecognition lprecognition, Point point) {
      String s = lprecognition.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lprecognition.frame.setVisible(true);
         LogicLabel logiclabel = new LogicLabel("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lprecognition.frame, "", logiclabel, astring);
         messagedialog.showAt(point);
         return messagedialog.selectedButton == 0 && !lprecognition.saveProblems(s)
            ? false
            : messagedialog.selectedButton == 0 || messagedialog.selectedButton == 1;
      }
   }

   static boolean confirmRemoveWork(LPRecognition lprecognition, Point point) {
      if (lprecognition.problem.answer == null) {
         return false;
      } else {
         LogicLabel logiclabel = new LogicLabel("Delete the work on this problem?");
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lprecognition.frame, "", logiclabel, astring);
         messagedialog.showAt(point);
         if (messagedialog.selectedButton != 0) {
            return false;
         } else {
            lprecognition.removeWork();
            saveDeletedWork(lprecognition);
            return true;
         }
      }
   }

   static boolean confirmDelete(LPRecognition lprecognition, Point point) {
      if (lprecognition.problem.answer != null) {
         RadioGroupPanel radiogrouppanel = new RadioGroupPanel();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            radiogrouppanel.add(ajradiobutton[j] = new LogicRadioButton(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lprecognition.frame, "", radiogrouppanel, astring2);
         messagedialog.showAt(null);
         if (messagedialog.selectedButton != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            lprecognition.problem.clearAnswer();
            saveDeletedWork(lprecognition);
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lprecognition.problemIndex != -1 || lprecognition.problem.statement != null && !lprecognition.problem.statement.equals("")) {
         LogicLabel logiclabel = new LogicLabel("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog1 = new MessageDialog(lprecognition.frame, "", logiclabel, astring1);
         messagedialog1.showAt(null);
         if (messagedialog1.selectedButton != 0) {
            return false;
         }
      }

      if (lprecognition.problemIndex != -1) {
         LPRecognition.problems.removeProblem(lprecognition.problemIndex);
         LPRecognition.saveProblems();
      }

      lprecognition.newProblem();
      return true;
   }

   static boolean selectNextProblem(LPRecognition lprecognition) {
      if (confirmSaveChanges(lprecognition, null)) {
         synchronized (LPRecognition.problems) {
            int i = lprecognition.problemIndex + 1;
            if (i != 0 && LPRecognition.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPRecognition.problems.getRecordAt(i);
            if (s == null) {
               return selectProblem(lprecognition);
            }

            lprecognition.loadProblem(s);
            lprecognition.problemIndex = i;
            LPRecognition.problems.replaceProblem(lprecognition.saveProblem(), i);
            if (LogicModule.eraseWork) {
               lprecognition.removeWork();
            }
         }
      }

      lprecognition.requestFocus();
      return true;
   }

   static boolean selectProblem(LPRecognition lprecognition) {
      boolean flag = false;
      if (confirmSaveChanges(lprecognition, null)) {
         synchronized (LPRecognition.problems) {
            ProblemListView problemlistview = createProblemList(lprecognition, false, false, null);
            problemlistview.setBackground(LogicConstants.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lprecognition.frame, ProblemSet.markTitle("Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("recChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               int i = problemlistview.getSelectedProblem(problemlistview.rowToProblem);
               LPRecognition lprecognition1;
               if ((lprecognition1 = LPRecognition.openInstance(i, true)) != null) {
                  lprecognition1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lprecognition.loadProblem(LPRecognition.problems.getRecordAt(i));
                  lprecognition.problemIndex = i;
                  LPRecognition.problems.replaceProblem(lprecognition.saveProblem(), i);
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

   static int[] chooseProblemsToSubmit(LPRecognition lprecognition) {
      int[] aint = null;
      if (confirmSaveChanges(lprecognition, null)) {
         synchronized (LPRecognition.problems) {
            ProblemListView problemlistview = createProblemList(lprecognition, true, false, null);
            if (LPRecognition.submitExam) {
               problemlistview.clearSelection();
            }

            Vector vector = LPRecognition.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = TaggedRecord.nameOf(s);
                  int j = LPRecognition.problems.indexOfName(s1);
                  if (j != -1) {
                     problemlistview.selectProblem(j, problemlistview.rowToProblem);
                  }
               }
            }

            problemlistview.setBackground(LogicConstants.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Submit", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lprecognition.frame, ProblemSet.markTitle("Submit Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("recChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            }
         }
      }

      lprecognition.requestFocus();
      return aint;
   }

   static void enterSubmittedProblem(LPRecognition lprecognition) {
      if (confirmSaveChanges(lprecognition, null)) {
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
         messagedialog.setBoundsKey("recSubmitted");
         editabletextpane.requestFocus();
         messagedialog.showAt(point, true);
         moduleframe.dispose();
         if (messagedialog.selectedButton == 0) {
            lprecognition.loadProblem(editabletextpane.getText());
         }
      }

      lprecognition.requestFocus();
   }

   static int[] chooseProblemsToUpload(LPRecognition lprecognition) {
      int[] aint = null;
      if (confirmSaveChanges(lprecognition, null)) {
         synchronized (LPRecognition.problems) {
            ProblemListView problemlistview = createProblemList(lprecognition, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            MessageTextArea messagetextarea = new MessageTextArea(LogicProgram.expandEscapes(Message.getText("not089")));
            messagetextarea.setLineWrap(true);
            messagetextarea.setWrapStyleWord(true);
            sizedpanel.add(messagetextarea, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lprecognition.frame, ProblemSet.markTitle("Upload Problems"), sizedpanel, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("derChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            }
         }
      }

      lprecognition.requestFocus();
      return aint;
   }

   static void deleteProblems(LPRecognition lprecognition) {
      synchronized (LPRecognition.problems) {
         ProblemListView problemlistview = createProblemList(lprecognition, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lprecognition.frame, ProblemSet.markTitle("Delete Problems"), jscrollpane, astring);
         problemlistview.setDialog(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.setBoundsKey("recChosen");
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
                     RecognitionProblemEntry recognitionproblementry1 = (RecognitionProblemEntry)LPRecognition.problems.getEntryAt(l);
                     if (recognitionproblementry1 != null) {
                        TaggedRecord taggedrecord1 = new TaggedRecord(recognitionproblementry1.name);
                        if (!LPRecognition.isExample(taggedrecord1.getName())) {
                           recognitionproblementry1.name = LPRecognition.removeWork(taggedrecord1);
                           recognitionproblementry1.state = 0;
                           LPRecognition lprecognition2 = LPRecognition.openInstance(l, false);
                           if (lprecognition2 != null) {
                              lprecognition2.loadProblem(recognitionproblementry1.name);
                           }
                        }
                     }
                  }
               }

               LPRecognition.saveProblems();
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
                     RecognitionProblemEntry recognitionproblementry = (RecognitionProblemEntry)LPRecognition.problems.getEntryAt(j);
                     if (recognitionproblementry != null) {
                        TaggedRecord taggedrecord = new TaggedRecord(recognitionproblementry.name);
                        if (!LPRecognition.isExercise(taggedrecord.getName())) {
                           LPRecognition lprecognition1 = LPRecognition.openInstance(j, false);
                           LPRecognition.problems.removeProblem(j);
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

   static void enterUserProblem(LPRecognition lprecognition) {
      if (confirmSaveChanges(lprecognition, null)) {
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.centeredLocation(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         FormulaEntryField formulaentryfield = new FormulaEntryField(moduleframe);
         if (lprecognition.lastUserProblem != null) {
            formulaentryfield.setText(LogicProgram.translateSymbols(lprecognition.lastUserProblem, maggie, SYMBOLS));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(formulaentryfield);
         formulaentryfield.setBackground(Color.WHITE);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "User Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.setBoundsKey("recUser");
         formulaentryfield.ownerDialog = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.showAt(point, true);
         moduleframe.dispose();
         if (messagedialog.selectedButton == 0) {
            String s = formulaentryfield.getText();
            if (!LPRecognition.validateUserProblem(s)) {
               return;
            }

            lprecognition.lastUserProblem = LogicProgram.translateSymbols(s, SYMBOLS, maggie);
            lprecognition.loadUserProblem(lprecognition.lastUserProblem);
         }
      }

      lprecognition.requestFocus();
   }

   static int[] printProblems(LPRecognition lprecognition) {
      Object object = null;
      if (confirmSaveChanges(lprecognition, null)) {
         synchronized (LPRecognition.problems) {
            ProblemListView problemlistview = createProblemList(lprecognition, true, false, LPRecognition.noPrint);
            problemlistview.setBackground(LogicConstants.bruinAsh);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Print Results", "Print List", "Cancel"};
            problemlistview.cancelButton = 2;
            MessageDialog messagedialog = new MessageDialog(lprecognition.frame, ProblemSet.markTitle("Print Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("recChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               RecognitionResultsPrintJob.print(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            } else if (messagedialog.selectedButton == 1) {
               RecognitionListPrintJob.print(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            }
         }
      }

      lprecognition.requestFocus();
      return (int[])object;
   }

   static ProblemListView createProblemList(LPRecognition lprecognition, boolean flag, boolean flag1, ProblemSelector problemselector) {
      return LPRecognition.problems.createListView(lprecognition, LPRecognition.exercises, flag, flag1, LPRecognition.monoProbs, problemselector);
   }

   /**
    * Makes "Delete the work on this problem" stick. The original only cleared the work
    * on screen; the saved copy of the problem kept the work, so it came back the next
    * time the problem was loaded. Same steps as the "Delete Work" choice of the
    * multi-problem delete dialog (examples are left alone there too).
    */
   static void saveDeletedWork(LPRecognition lprecognition) {
      if (lprecognition.problemIndex == -1) {
         return;
      }

      synchronized (LPRecognition.problems) {
         ProblemEntry entry = LPRecognition.problems.getEntryAt(lprecognition.problemIndex);
         if (entry != null && !LPRecognition.isExample(new TaggedRecord(entry.name).getName())) {
            entry.name = LPRecognition.removeWork(new TaggedRecord(entry.name));
            entry.state = 0;
            lprecognition.loadProblem(entry.name);
            LPRecognition.saveProblems();
         }
      }
   }
}
