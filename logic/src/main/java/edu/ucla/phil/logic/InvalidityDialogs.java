package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

class InvalidityDialogs implements InvalidityConstants {
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
      Message message = InvalidityMessage.get(s);
      InvalidityDialogHandler invaliditydialoghandler = null;
      if (message.buttons != null) {
         invaliditydialoghandler = new InvalidityDialogHandler(message.buttons);
         if (hashtable1 != null) {
            invaliditydialoghandler.setProperties(hashtable1);
         }
      }

      MessageDialog.showMessage(message, hashtable, point, invaliditydialoghandler);
   }

   static boolean confirmSaveChanges(LPInvalidation lpinvalidation, Point point) {
      String s = lpinvalidation.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lpinvalidation.frame.show();
         LogicLabel logiclabel = new LogicLabel("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, "", logiclabel, astring);
         messagedialog.showAt(point);
         return messagedialog.selectedButton == 0 && !lpinvalidation.saveProblems(s)
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
         } else if (LPInvalidation.problems.getRecord(s) != null) {
            LogicProgram.showProblemError("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static boolean deleteProblemOrWork(LPInvalidation lpinvalidation, Point point) {
      if (lpinvalidation.size != 0) {
         RadioGroupPanel radiogrouppanel = new RadioGroupPanel();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            radiogrouppanel.add(ajradiobutton[j] = new LogicRadioButton(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, "", radiogrouppanel, astring2);
         messagedialog.showAt(null);
         if (messagedialog.selectedButton != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            lpinvalidation.removeWork();
            saveDeletedWork(lpinvalidation);
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lpinvalidation.problemIndex != -1 || lpinvalidation.statement != null) {
         LogicLabel logiclabel = new LogicLabel("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog1 = new MessageDialog(lpinvalidation.frame, "", logiclabel, astring1);
         messagedialog1.showAt(null);
         if (messagedialog1.selectedButton != 0) {
            return false;
         }
      }

      if (lpinvalidation.problemIndex != -1) {
         LPInvalidation.problems.removeProblem(lpinvalidation.problemIndex);
         LPInvalidation.saveProblems();
      }

      lpinvalidation.newProblem();
      return true;
   }

   static boolean deleteWork(LPInvalidation lpinvalidation, Point point) {
      if (lpinvalidation.size == 0) {
         return false;
      } else {
         LogicLabel logiclabel = new LogicLabel("Delete the work on this problem?");
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, "", logiclabel, astring);
         messagedialog.showAt(point);
         if (messagedialog.selectedButton != 0) {
            return false;
         } else {
            lpinvalidation.removeWork();
            saveDeletedWork(lpinvalidation);
            return true;
         }
      }
   }

   static boolean selectNextProblem(LPInvalidation lpinvalidation) {
      if (confirmSaveChanges(lpinvalidation, null)) {
         synchronized (LPInvalidation.problems) {
            int i = lpinvalidation.problemIndex + 1;
            if (i != 0 && LPInvalidation.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPInvalidation.problems.getRecordAt(i);
            if (s == null) {
               return chooseProblem(lpinvalidation);
            }

            lpinvalidation.loadProblem(s);
            lpinvalidation.problemIndex = i;
            LPInvalidation.problems.replaceProblem(lpinvalidation.saveProblem(), i);
            if (LogicModule.eraseWork) {
               lpinvalidation.removeWork();
            }
         }
      }

      lpinvalidation.requestFocus();
      return true;
   }

   static boolean chooseProblem(LPInvalidation lpinvalidation) {
      boolean flag = false;
      if (confirmSaveChanges(lpinvalidation, null)) {
         synchronized (LPInvalidation.problems) {
            ProblemListView problemlistview = createProblemListView(lpinvalidation, false, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, ProblemSet.markTitle("Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("invChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               int i = problemlistview.getSelectedProblem(problemlistview.rowToProblem);
               LPInvalidation lpinvalidation1;
               if ((lpinvalidation1 = LPInvalidation.openInstance(i, true)) != null) {
                  lpinvalidation1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lpinvalidation.loadProblem(LPInvalidation.problems.getRecordAt(i));
                  lpinvalidation.problemIndex = i;
                  LPInvalidation.problems.replaceProblem(lpinvalidation.saveProblem(), i);
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

   static int[] chooseSubmitProblems(LPInvalidation lpinvalidation) {
      int[] aint = null;
      if (confirmSaveChanges(lpinvalidation, null)) {
         synchronized (LPInvalidation.problems) {
            ProblemListView problemlistview = createProblemListView(lpinvalidation, true, false, null);
            if (LPInvalidation.submitExam) {
               problemlistview.clearSelection();
            }

            Vector vector = LPInvalidation.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = TaggedRecord.nameOf(s);
                  int j = LPInvalidation.problems.indexOfName(s1);
                  if (j != -1) {
                     problemlistview.selectProblem(j, problemlistview.rowToProblem);
                  }
               }
            }

            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            MessageTextArea messagetextarea = new MessageTextArea(LogicProgram.expandEscapes(Message.getText("not081")));
            messagetextarea.setLineWrap(true);
            messagetextarea.setWrapStyleWord(true);
            sizedpanel.add(messagetextarea, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Submit", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, ProblemSet.markTitle("Submit Problems"), sizedpanel, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("invChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            }
         }
      }

      lpinvalidation.requestFocus();
      return aint;
   }

   static int[] chooseUploadProblems(LPInvalidation lpinvalidation) {
      int[] aint = null;
      if (confirmSaveChanges(lpinvalidation, null)) {
         synchronized (LPInvalidation.problems) {
            ProblemListView problemlistview = createProblemListView(lpinvalidation, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            MessageTextArea messagetextarea = new MessageTextArea(LogicProgram.expandEscapes(Message.getText("not089")));
            messagetextarea.setLineWrap(true);
            messagetextarea.setWrapStyleWord(true);
            sizedpanel.add(messagetextarea, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, ProblemSet.markTitle("Upload Problems"), sizedpanel, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("invChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            }
         }
      }

      lpinvalidation.requestFocus();
      return aint;
   }

   static void deleteMultipleProblems(LPInvalidation lpinvalidation) {
      synchronized (LPInvalidation.problems) {
         ProblemListView problemlistview = createProblemListView(lpinvalidation, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, ProblemSet.markTitle("Delete Problems"), jscrollpane, astring);
         problemlistview.setDialog(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.setBoundsKey("invChosen");
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
                     DerivationProblemEntry derivationproblementry1 = (DerivationProblemEntry)LPInvalidation.problems.getEntryAt(l);
                     if (derivationproblementry1 != null) {
                        TaggedRecord taggedrecord1 = new TaggedRecord(derivationproblementry1.name);
                        if (!LPInvalidation.isExample(taggedrecord1.getName())) {
                           derivationproblementry1.name = LPInvalidation.removeWork(taggedrecord1);
                           derivationproblementry1.state = 0;
                           LPInvalidation lpinvalidation2 = LPInvalidation.openInstance(l, false);
                           if (lpinvalidation2 != null) {
                              lpinvalidation2.loadProblem(derivationproblementry1.name);
                           }
                        }
                     }
                  }
               }

               LPInvalidation.saveProblems();
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
                     DerivationProblemEntry derivationproblementry = (DerivationProblemEntry)LPInvalidation.problems.getEntryAt(j);
                     if (derivationproblementry != null) {
                        TaggedRecord taggedrecord = new TaggedRecord(derivationproblementry.name);
                        if (!LPInvalidation.isExercise(taggedrecord.getName())) {
                           LPInvalidation lpinvalidation1 = LPInvalidation.openInstance(j, false);
                           LPInvalidation.problems.removeProblem(j);
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

   static int[] choosePrintProblems(LPInvalidation lpinvalidation) {
      Object object = null;
      if (confirmSaveChanges(lpinvalidation, null)) {
         synchronized (LPInvalidation.problems) {
            ProblemListView problemlistview = createProblemListView(lpinvalidation, true, false, LPInvalidation.noPrint);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Print", "Print Results", "Print List", "Cancel"};
            problemlistview.cancelButton = 3;
            MessageDialog messagedialog = new MessageDialog(lpinvalidation.frame, ProblemSet.markTitle("Print Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("invChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               InvalidityProblemsPage.printProblems(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            } else if (messagedialog.selectedButton == 1) {
               InvalidityResultsPage.printResults(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            } else if (messagedialog.selectedButton == 2) {
               InvalidityStatementsPage.printStatements(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            }
         }
      }

      lpinvalidation.requestFocus();
      return (int[])object;
   }

   static void enterSubmittedProblem(LPInvalidation lpinvalidation) {
      if (confirmSaveChanges(lpinvalidation, null)) {
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
         messagedialog.setBoundsKey("invSubmitted");
         editabletextpane.requestFocus();
         messagedialog.showAt(point, true);
         moduleframe.dispose();
         if (messagedialog.selectedButton == 0) {
            lpinvalidation.loadProblem(editabletextpane.getText());
         }
      }

      lpinvalidation.requestFocus();
   }

   static ProblemListView createProblemListView(LPInvalidation lpinvalidation, boolean flag, boolean flag1, ProblemSelector problemselector) {
      return LPInvalidation.problems.createListView(lpinvalidation, LPInvalidation.exercises, flag, flag1, LPInvalidation.monoProbs, problemselector);
   }

   static void createUserProblem(LPInvalidation lpinvalidation) {
      if (confirmSaveChanges(lpinvalidation, null)) {
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.centeredLocation(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         FormulaEntryField formulaentryfield = new FormulaEntryField(moduleframe);
         if (lpinvalidation.lastUserProblem != null) {
            formulaentryfield.setText(LogicProgram.translateSymbols(lpinvalidation.lastUserProblem, maggie, displaySymbols));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         formulaentryfield.setBackground(Color.WHITE);
         jscrollpane.setViewportView(formulaentryfield);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "User Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.setBoundsKey("invUser");
         formulaentryfield.ownerDialog = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.showAt(point, true);
         moduleframe.dispose();
         if (messagedialog.selectedButton == 0) {
            String s = formulaentryfield.getText();
            if (!LPInvalidation.validateUserProblem(s)) {
               return;
            }

            lpinvalidation.lastUserProblem = LogicProgram.translateSymbols(s, displaySymbols, maggie);
            lpinvalidation.loadUserProblem(lpinvalidation.lastUserProblem);
         }
      }

      lpinvalidation.requestFocus();
   }

   static boolean editInterpretation(SymbolInterpretation symbolinterpretation, int i) {
      ModuleFrame moduleframe = new ModuleFrame();
      JScrollPane jscrollpane = new JScrollPane();
      InterpretationEditor interpretationeditor = new InterpretationEditor(symbolinterpretation, i);
      String[] astring = new String[]{"OK", "Cancel"};
      jscrollpane.setViewportView(interpretationeditor);
      MessageDialog messagedialog = new MessageDialog(moduleframe, "Extend " + symbolinterpretation.getSignature(), jscrollpane, astring);
      messagedialog.pack();
      Point point = MessageDialog.centeredLocation(messagedialog.getSize());
      messagedialog.setDefaultButtonIndex(0);
      messagedialog.showAt(point, true);
      moduleframe.dispose();
      if (messagedialog.selectedButton == 0) {
         interpretationeditor.applyToSymbol();
         return true;
      } else {
         return false;
      }
   }

   /**
    * Makes "Delete the work on this problem" stick. The original only cleared the work
    * on screen; the saved copy of the problem kept the work, so it came back the next
    * time the problem was loaded. Same steps as the "Delete Work" choice of the
    * multi-problem delete dialog (examples are left alone there too).
    */
   static void saveDeletedWork(LPInvalidation lpinvalidation) {
      if (lpinvalidation.problemIndex == -1) {
         return;
      }

      synchronized (LPInvalidation.problems) {
         ProblemEntry entry = LPInvalidation.problems.getEntryAt(lpinvalidation.problemIndex);
         if (entry != null && !LPInvalidation.isExample(new TaggedRecord(entry.name).getName())) {
            entry.name = LPInvalidation.removeWork(new TaggedRecord(entry.name));
            entry.state = 0;
            lpinvalidation.loadProblem(entry.name);
            LPInvalidation.saveProblems();
         }
      }
   }
}
