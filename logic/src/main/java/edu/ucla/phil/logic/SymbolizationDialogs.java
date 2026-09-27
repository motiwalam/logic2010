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

class SymbolizationDialogs implements SymbolizationConstants {
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
      Message message = SymbolizationMessages.get(s);
      NodeMessageHandler nodemessagehandler = null;
      if (message.buttons != null) {
         nodemessagehandler = new NodeMessageHandler(message.buttons);
         if (hashtable1 != null) {
            nodemessagehandler.setProperties(hashtable1);
         }
      }

      MessageDialog.showMessage(message, hashtable, point, nodemessagehandler);
   }

   static boolean deleteProblem(LPSymbolizer lpsymbolizer, Point point) {
      if (lpsymbolizer.problem.isModified()) {
         RadioGroupPanel radiogrouppanel = new RadioGroupPanel();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            radiogrouppanel.add(ajradiobutton[j] = new LogicRadioButton(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, "", radiogrouppanel, astring2);
         messagedialog.showAt(null);
         if (messagedialog.selectedButton != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            lpsymbolizer.problem.setConnective(0, false);
            lpsymbolizer.problem.textPanel.textPane.requestFocus();
            saveDeletedWork(lpsymbolizer);
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lpsymbolizer.problemIndex != -1 || !lpsymbolizer.problem.getEnglishText().equals(SymbolizationTextPanel.collapseWhitespace(""))) {
         LogicLabel logiclabel = new LogicLabel("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog1 = new MessageDialog(lpsymbolizer.frame, "", logiclabel, astring1);
         messagedialog1.showAt(null);
         if (messagedialog1.selectedButton != 0) {
            return false;
         }
      }

      if (lpsymbolizer.problemIndex != -1) {
         LPSymbolizer.problems.removeProblem(lpsymbolizer.problemIndex);
         LPSymbolizer.saveProblems();
      }

      lpsymbolizer.newProblem();
      return true;
   }

   static boolean deleteExerciseWork(LPSymbolizer lpsymbolizer, Point point) {
      if (!lpsymbolizer.problem.isModified()) {
         return false;
      } else {
         LogicLabel logiclabel = new LogicLabel("Delete the work on this problem?");
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, "", logiclabel, astring);
         messagedialog.showAt(point);
         if (messagedialog.selectedButton != 0) {
            return false;
         } else {
            lpsymbolizer.removeWork();
            saveDeletedWork(lpsymbolizer);
            return true;
         }
      }
   }

   static boolean selectNextProblem(LPSymbolizer lpsymbolizer) {
      if (confirmSaveChanges(lpsymbolizer, null)) {
         synchronized (LPSymbolizer.problems) {
            int i = lpsymbolizer.problemIndex + 1;
            if (i != 0 && LPSymbolizer.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPSymbolizer.problems.getRecordAt(i);
            if (s == null) {
               return chooseProblem(lpsymbolizer);
            }

            lpsymbolizer.loadProblem(s);
            lpsymbolizer.problemIndex = i;
            LPSymbolizer.problems.replaceProblem(lpsymbolizer.saveProblem(), i);
            if (LogicModule.eraseWork) {
               lpsymbolizer.removeWork();
            }
         }
      }

      lpsymbolizer.requestFocus();
      return true;
   }

   static boolean chooseProblem(LPSymbolizer lpsymbolizer) {
      boolean flag = false;
      if (confirmSaveChanges(lpsymbolizer, null)) {
         synchronized (LPSymbolizer.problems) {
            ProblemListView problemlistview = createProblemList(lpsymbolizer, false, true, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, ProblemSet.markTitle("Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("symChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               int i = problemlistview.getSelectedProblem(problemlistview.rowToProblem);
               LPSymbolizer lpsymbolizer1;
               if ((lpsymbolizer1 = LPSymbolizer.openInstance(i, true)) != null) {
                  lpsymbolizer1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lpsymbolizer.loadProblem(LPSymbolizer.problems.getRecordAt(i));
                  lpsymbolizer.problemIndex = i;
                  LPSymbolizer.problems.replaceProblem(lpsymbolizer.saveProblem(), i);
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

   static String chooseScheme(LPSymbolizer lpsymbolizer, SchemeEditor schemeeditor) {
      Object object = null;
      ProblemListView problemlistview = createProblemList(lpsymbolizer, false, true, null);
      JScrollPane jscrollpane = new JScrollPane();
      jscrollpane.setViewportView(problemlistview);
      String[] astring = new String[]{"Copy", "Cancel"};
      Object object1 = schemeeditor == null ? null : LogicProgram.findJFrame(schemeeditor);
      if (object1 == null) {
         object1 = lpsymbolizer.frame;
      }

      MessageDialog messagedialog = new MessageDialog((Frame)object1, ProblemSet.markTitle("Problems"), jscrollpane, astring);
      problemlistview.setDialog(messagedialog, 0);
      Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 25 * LogicProgram.fontSize);
      messagedialog.setSize(dimension);
      messagedialog.setBoundsKey("symSchemeChosen");
      problemlistview.requestFocus();
      messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
      if (messagedialog.selectedButton == 0) {
         int i = problemlistview.getSelectedProblem(problemlistview.rowToProblem);
         if (i != -1) {
            return LPSymbolizer.getProblemScheme(TaggedRecord.nameOf(LPSymbolizer.problems.getRecordAt(i)));
         }
      }

      return null;
   }

   static int[] chooseProblemsToSubmit(LPSymbolizer lpsymbolizer) {
      int[] aint = null;
      if (confirmSaveChanges(lpsymbolizer, null)) {
         synchronized (LPSymbolizer.problems) {
            ProblemListView problemlistview = createProblemList(lpsymbolizer, true, true, null);
            if (LPSymbolizer.submitExam) {
               problemlistview.clearSelection();
            }

            Vector vector = LPSymbolizer.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = TaggedRecord.nameOf(s);
                  int j = LPSymbolizer.problems.indexOfName(s1);
                  if (j != -1) {
                     problemlistview.selectProblem(j, problemlistview.rowToProblem);
                  }
               }
            }

            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Submit", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, ProblemSet.markTitle("Submit Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("symChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            }
         }
      }

      lpsymbolizer.requestFocus();
      return aint;
   }

   static int[] chooseProblemsToUpload(LPSymbolizer lpsymbolizer) {
      int[] aint = null;
      if (confirmSaveChanges(lpsymbolizer, null)) {
         synchronized (LPSymbolizer.problems) {
            ProblemListView problemlistview = createProblemList(lpsymbolizer, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            MessageTextArea messagetextarea = new MessageTextArea(LogicProgram.expandEscapes(Message.getText("not089")));
            messagetextarea.setLineWrap(true);
            messagetextarea.setWrapStyleWord(true);
            sizedpanel.add(messagetextarea, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, ProblemSet.markTitle("Upload Problems"), sizedpanel, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("symChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            }
         }
      }

      lpsymbolizer.requestFocus();
      return aint;
   }

   static void deleteProblems(LPSymbolizer lpsymbolizer) {
      synchronized (LPSymbolizer.problems) {
         ProblemListView problemlistview = createProblemList(lpsymbolizer, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, ProblemSet.markTitle("Delete Problems"), jscrollpane, astring);
         problemlistview.setDialog(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.setBoundsKey("symChosen");
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
                     SymbolizationEntry symbolizationentry1 = (SymbolizationEntry)LPSymbolizer.problems.getEntryAt(l);
                     if (symbolizationentry1 != null) {
                        TaggedRecord taggedrecord1 = new TaggedRecord(symbolizationentry1.name);
                        if (!LPSymbolizer.isExample(taggedrecord1.getName())) {
                           symbolizationentry1.name = LPSymbolizer.removeWork(taggedrecord1);
                           symbolizationentry1.state = 0;
                           LPSymbolizer lpsymbolizer2 = LPSymbolizer.openInstance(l, false);
                           if (lpsymbolizer2 != null) {
                              lpsymbolizer2.loadProblem(symbolizationentry1.name);
                           }
                        }
                     }
                  }
               }

               LPSymbolizer.saveProblems();
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
                     SymbolizationEntry symbolizationentry = (SymbolizationEntry)LPSymbolizer.problems.getEntryAt(j);
                     if (symbolizationentry != null) {
                        TaggedRecord taggedrecord = new TaggedRecord(symbolizationentry.name);
                        if (!LPSymbolizer.isExercise(taggedrecord.getName())) {
                           LPSymbolizer lpsymbolizer1 = LPSymbolizer.openInstance(j, false);
                           LPSymbolizer.problems.removeProblem(j);
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

   static int[] chooseProblemsToPrint(LPSymbolizer lpsymbolizer) {
      int[] aint = null;
      if (confirmSaveChanges(lpsymbolizer, null)) {
         synchronized (LPSymbolizer.problems) {
            ProblemListView problemlistview = createProblemList(lpsymbolizer, true, true, LPSymbolizer.noPrint);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Print", "Print Results", "Print List", "Cancel"};
            problemlistview.cancelButton = 3;
            MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, ProblemSet.markTitle("Print Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("symChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            } else if (messagedialog.selectedButton == 1) {
               SymbolizationResultsPrinter.printSelected(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            } else if (messagedialog.selectedButton == 2) {
               StatementListPrinter.printSelected(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            }
         }
      }

      lpsymbolizer.requestFocus();
      return aint;
   }

   static void enterSubmittedProblem(LPSymbolizer lpsymbolizer) {
      if (confirmSaveChanges(lpsymbolizer, null)) {
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
         messagedialog.setBoundsKey("symSubmitted");
         editabletextpane.requestFocus();
         messagedialog.showAt(point, true);
         moduleframe.dispose();
         if (messagedialog.selectedButton == 0) {
            lpsymbolizer.loadProblem(editabletextpane.getText());
         }
      }

      lpsymbolizer.requestFocus();
   }

   static ProblemListView createProblemList(LPSymbolizer lpsymbolizer, boolean flag, boolean flag1, ProblemSelector problemselector) {
      return LPSymbolizer.problems.createListView(lpsymbolizer, LPSymbolizer.exercises, flag, flag1, LPSymbolizer.monoProbs, problemselector);
   }

   static void createUserProblem(LPSymbolizer lpsymbolizer) {
      editUserProblem(lpsymbolizer, null);
   }

   static void editUserProblem(LPSymbolizer lpsymbolizer, String s) {
      if (s != null || confirmSaveChanges(lpsymbolizer, null)) {
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.centeredLocation(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         FormulaEntryField formulaentryfield = new FormulaEntryField(moduleframe);
         if (s != null) {
            formulaentryfield.setText(s);
         } else if (LPSymbolizer.lastUserProblem != null) {
            formulaentryfield.setText(LPSymbolizer.lastUserProblem);
         }

         UserProblemDialogHandler userproblemdialoghandler = new UserProblemDialogHandler("OK:ok.Clear:clear.Cancel:cancel;0");
         userproblemdialoghandler.setProperty("edit", formulaentryfield);
         userproblemdialoghandler.setProperty("symbolizer", lpsymbolizer);
         jscrollpane.setViewportView(formulaentryfield);
         formulaentryfield.setBackground(Color.WHITE);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "User Problem", jscrollpane, userproblemdialoghandler.getLabels());
         messagedialog.setSize(dimension);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.setBoundsKey("symUser");
         formulaentryfield.ownerDialog = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.showAt(point, true);
         moduleframe.dispose();
         if (messagedialog.selectedButton == 0) {
            String s1 = formulaentryfield.getText();
            if (!LPSymbolizer.validateUserProblem(s1)) {
               return;
            }

            LPSymbolizer.lastUserProblem = s1;
            if (s == null) {
               lpsymbolizer.loadUserProblem(LPSymbolizer.lastUserProblem);
            } else {
               lpsymbolizer.problem.statement = LPSymbolizer.lastUserProblem;
               lpsymbolizer.titlePanel.setStatement(LPSymbolizer.lastUserProblem);
               lpsymbolizer.problem.setEnglishText(LPSymbolizer.lastUserProblem);
               lpsymbolizer.updateSymbolization();
            }

            if (s == null) {
               showSchemeDialog(lpsymbolizer, null);
            }
         }
      }

      lpsymbolizer.requestFocus();
   }

   static void showSchemeDialog(LPSymbolizer lpsymbolizer, String s) {
      ModuleFrame moduleframe = new ModuleFrame();
      Dimension dimension = new Dimension(25 * LogicProgram.fontSize, 15 * LogicProgram.fontSize);
      Point point = MessageDialog.centeredLocation(dimension);
      JScrollPane jscrollpane = new JScrollPane();
      SchemeEditor schemeeditor = new SchemeEditor(true);
      if (s != null) {
         if (s.indexOf(58) == -1) {
            schemeeditor.setScheme(":");
         } else {
            schemeeditor.setScheme(s);
         }
      } else if (LPSymbolizer.lastUserScheme != null && LPSymbolizer.lastUserScheme.indexOf(58) != -1) {
         schemeeditor.setScheme(LPSymbolizer.lastUserScheme);
      } else {
         schemeeditor.setScheme(":");
      }

      SchemeDialogHandler schemedialoghandler;
      if (s == null) {
         schemedialoghandler = new SchemeDialogHandler("OK:ok.Clear:clear.Browse:open.Help:help;0");
      } else {
         schemedialoghandler = new SchemeDialogHandler("OK:ok.Clear:clear.Browse:open.Cancel:cancel.Help:help;0");
      }

      schemedialoghandler.setProperty("scheme", schemeeditor);
      schemedialoghandler.setProperty("symbolizer", lpsymbolizer);
      jscrollpane.setViewportView(schemeeditor);
      schemeeditor.setBackground(Color.WHITE);
      MessageDialog messagedialog;
      if (s == null) {
         messagedialog = new MessageDialog(moduleframe, "Create Scheme", jscrollpane, schemedialoghandler.getLabels());
      } else {
         messagedialog = new MessageDialog(moduleframe, "Edit Scheme", jscrollpane, schemedialoghandler.getLabels());
      }

      messagedialog.setSize(dimension);
      messagedialog.setDefaultButtonIndex(0);
      messagedialog.setBoundsKey("symUserScheme");
      messagedialog.addHandler(schemedialoghandler);
      messagedialog.setButtonTip("OK", "Accept scheme");
      messagedialog.setButtonTip("Clear", "Reset scheme");
      messagedialog.setButtonTip("Browse", "Load scheme from another problem");
      schemeeditor.requestFocus();
      messagedialog.showAt(point, true);
      moduleframe.dispose();
      if (messagedialog.selectedButton == 0) {
         LPSymbolizer.lastUserScheme = schemeeditor.getScheme();
         lpsymbolizer.scheme.setScheme(LPSymbolizer.lastUserScheme);
         if (lpsymbolizer.problem != null) {
            lpsymbolizer.problem.scheme = LPSymbolizer.lastUserScheme;
         }
      }

      lpsymbolizer.requestFocus();
   }

   static void editStatement(LPSymbolizer lpsymbolizer) {
      if ((lpsymbolizer.problem.userAnswerKey || lpsymbolizer.problem.originalName != null) && !lpsymbolizer.dontChange) {
         if (lpsymbolizer.problem.originalName != null) {
            lpsymbolizer.problem.copyAnswersToUserKey();
         }

         String s = lpsymbolizer.problem.statement;
         if (s == null) {
            s = "";
         }

         editUserProblem(lpsymbolizer, s);
      } else {
         showMessage("SymNot012");
      }
   }

   static void editScheme(LPSymbolizer lpsymbolizer) {
      if ((lpsymbolizer.problem.userAnswerKey || lpsymbolizer.problem.originalName != null) && !lpsymbolizer.dontChange) {
         if (lpsymbolizer.problem.originalName != null) {
            lpsymbolizer.problem.copyAnswersToUserKey();
         }

         String s = lpsymbolizer.problem.scheme;
         if (s == null) {
            s = ":";
         }

         showSchemeDialog(lpsymbolizer, s);
      } else {
         showMessage("SymNot012");
      }
   }

   static void showAnswerManager(LPSymbolizer lpsymbolizer) {
      if (lpsymbolizer.problem.userAnswerKey
         || ServerConnection.adminInstall && !ServerConnection.linkedFromNonetDir && UserSetup.checkAccess("symAnswerPrint", "instructor") == null) {
         if (lpsymbolizer.problem.originalName != null && !lpsymbolizer.dontChange) {
            lpsymbolizer.problem.copyAnswersToUserKey();
         }

         ProblemListView problemlistview = new ProblemListView(true);
         Vector vector = lpsymbolizer.problem.answers;
         SymbolizationNode symbolizationnode = new SymbolizationNode(lpsymbolizer);
         int i = vector == null ? 0 : vector.size();

         for (int j = 0; j < i; j++) {
            symbolizationnode.loadRecord(new TaggedRecord((String)vector.elementAt(j)));
            String s = symbolizationnode.toString();
            AnswerListLabel answerlistlabel = new AnswerListLabel(s, 2);
            answerlistlabel.setHoverText(s);
            answerlistlabel.setOpaque(true);
            problemlistview.addItem(answerlistlabel);
         }

         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         AnswerManagerHandler answermanagerhandler;
         if (!lpsymbolizer.problem.userAnswerKey || lpsymbolizer.dontChange) {
            answermanagerhandler = new AnswerManagerHandler("Use:load.OK:ok;0");
         } else if (lpsymbolizer.problem.isIncomplete()) {
            answermanagerhandler = new AnswerManagerHandler("Add:warn.Use:load.Delete:delete.Replace:warn.Help:help.OK:ok;1");
         } else {
            answermanagerhandler = new AnswerManagerHandler("Add:add.Use:load.Delete:delete.Replace:replace.Help:help.OK:ok;1");
         }

         answermanagerhandler.setProperty("symbolizer", lpsymbolizer);
         answermanagerhandler.setProperty("list", problemlistview);
         MessageDialog messagedialog = new MessageDialog(null, "Answer Manager", jscrollpane, answermanagerhandler.getLabels());
         messagedialog.setButtonTip("Add", "Add the answer currently in the workspace.");
         messagedialog.setButtonTip("Use", "Load the selected answer into the workspace.");
         messagedialog.setButtonTip("Delete", "Delete the selected answer(s).");
         messagedialog.setButtonTip("Replace", "Replace the selected answer(s) with the answer currently in the workspace.");
         messagedialog.setButtonTip("OK", "OK as is");
         messagedialog.addHandler(answermanagerhandler);
         problemlistview.setDialog(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 40 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.setBoundsKey("symEditAnswer");
         problemlistview.requestFocus();
         messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
      } else {
         showMessage("SymNot005");
      }
   }

   static int[] chooseAnswersToPrint(Frame frame) {
      Object object = null;
      ProblemListView problemlistview = createExerciseList(true);
      JScrollPane jscrollpane = new JScrollPane();
      jscrollpane.setViewportView(problemlistview);
      String[] astring = new String[]{"Print", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(frame, ProblemSet.markTitle("Print Answers"), jscrollpane, astring);
      problemlistview.setDialog(messagedialog, 0);
      Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
      messagedialog.setSize(dimension);
      messagedialog.setBoundsKey("symChosen");
      problemlistview.requestFocus();
      messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
      if (messagedialog.selectedButton == 0) {
         AnswerPrinter.printSelected(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
      }

      return (int[])object;
   }

   static ProblemListView createExerciseList(boolean flag) {
      return LPSymbolizer.exercises.createListView(null, LPSymbolizer.exercises, flag, true, null, null);
   }

   static boolean confirmSaveChanges(LPSymbolizer lpsymbolizer, Point point) {
      String s = lpsymbolizer.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lpsymbolizer.frame.show();
         LogicLabel logiclabel = new LogicLabel("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, "", logiclabel, astring);
         messagedialog.showAt(point);
         return messagedialog.selectedButton == 0 && !lpsymbolizer.saveProblems(s)
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
         } else if (LPSymbolizer.problems.getRecord(s) != null) {
            LogicProgram.showProblemError("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static String askForSymbol(String s, String s1, SymbolizationNode symbolizationnode) {
      Point point = symbolizationnode.textPanel.getLocationOnScreen();
      Point point1 = new Point(point.x, point.y + symbolizationnode.textPanel.getHeight());
      FormulaEntryField formulaentryfield = new FormulaEntryField(symbolizationnode.symbolizer.frame);
      formulaentryfield.setBackground(Color.WHITE);
      String[] astring = new String[]{"OK", "Cancel"};
      SizedPanel sizedpanel = new SizedPanel();
      sizedpanel.setLayout(new BorderLayout());
      sizedpanel.setBorder(new EmptyBorder(7, 15, 7, 15));
      LogicLabel logiclabel = new LogicLabel(s1, 0, 1);
      logiclabel.setBorder(new EmptyBorder(0, 0, 5, 0));
      Dimension dimension = logiclabel.getPreferredSize();
      formulaentryfield.setBorder(new BevelBorder(1));
      formulaentryfield.setFixedWidth(dimension.width);
      formulaentryfield.invalidate();
      sizedpanel.add(logiclabel, "North");
      sizedpanel.add(formulaentryfield, "East");
      MessageDialog messagedialog = new MessageDialog(symbolizationnode.symbolizer.frame, s, sizedpanel, astring);
      messagedialog.setDefaultButtonIndex(0);
      formulaentryfield.ownerDialog = messagedialog;
      formulaentryfield.requestFocus();
      messagedialog.showAt(point1, true);
      return messagedialog.selectedButton != 0 ? null : LogicProgram.translateSymbols(formulaentryfield.getText(), displaySymbols, maggie);
   }

   static void enterDirectSymbolization(LPSymbolizer lpsymbolizer) {
      if (lpsymbolizer.directEntryDisabled) {
         MessageDialog.showMessage("Feature Disabled", "Direct entry is disabled for this problem.", null, null);
      } else {
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 7 * LogicProgram.fontSize);
         Point point = MessageDialog.centeredLocation(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         FormulaEntryField formulaentryfield = new FormulaEntryField(
            LogicProgram.translateSymbols(lpsymbolizer.lastDirect, maggie, displaySymbols), lpsymbolizer.frame
         );
         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(formulaentryfield);
         MessageDialog messagedialog = new MessageDialog(lpsymbolizer.frame, "Direct Symbolization", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.setBoundsKey("symDirect");
         formulaentryfield.ownerDialog = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.showAt(point, true);
         if (messagedialog.selectedButton == 0) {
            lpsymbolizer.lastDirect = LogicProgram.translateSymbols(formulaentryfield.getText(), displaySymbols, maggie);
            lpsymbolizer.problem.buildFromText(lpsymbolizer.lastDirect);
            if (lpsymbolizer.checkDisabled || lpsymbolizer.errorMessagesDisabled) {
               return;
            }

            SymbolizationNode symbolizationnode = lpsymbolizer.problem.findClosestAnswer();
            if (symbolizationnode == null) {
               return;
            }

            Expression expression = symbolizationnode.toExpression();
            Expression expression1 = lpsymbolizer.problem.toExpression();
            if (expression1 == null || expression == null || expression1.isAlphaEquivalent(expression, new BinderMap())) {
               lpsymbolizer.problem.copyTextFrom(lpsymbolizer.problem.findClosestAnswer());
            }
         }
      }
   }

   static void showNodeMessage(String s, String s1, DialogHandler dialoghandler) {
      if (dialoghandler != null) {
         SymbolizationNode symbolizationnode = (SymbolizationNode)dialoghandler.getProperty("target");
         ModuleFrame moduleframe = new ModuleFrame();
         JScrollPane jscrollpane = new JScrollPane();
         FormulaTextPane formulatextpane = new FormulaTextPane(LogicProgram.expandEscapes(s1));
         formulatextpane.setEditable(false);
         formulatextpane.setBackground(dialogWhite);
         formulatextpane.setCaretPosition(0);
         String[] astring = dialoghandler.getLabels();
         jscrollpane.setViewportView(formulatextpane);
         MessageDialog messagedialog = new MessageDialog(moduleframe, s, jscrollpane, astring);
         messagedialog.setBoundsKey("symShowError");
         messagedialog.addHandler(dialoghandler);
         Rectangle rectangle = messagedialog.getSavedBounds();
         Rectangle rectangle1 = LogicProgram.boundsRelativeTo(symbolizationnode.textPanel.textPane, null);
         Dimension dimension = rectangle == null ? new Dimension(32 * LogicProgram.fontSize, 15 * LogicProgram.fontSize) : rectangle.getSize();
         Point point = new Point(rectangle1.x + rectangle1.width / 2 - dimension.width / 2, rectangle1.y + rectangle1.height);
         if (rectangle != null) {
            rectangle.x = point.x;
            rectangle.y = point.y;
         }

         messagedialog.setSize(dimension);
         messagedialog.showAt(point, true);
         moduleframe.dispose();
      }
   }

   /**
    * Makes "Delete the work on this problem" stick. The original only cleared the work
    * on screen; the saved copy of the problem kept the work, so it came back the next
    * time the problem was loaded. Same steps as the "Delete Work" choice of the
    * multi-problem delete dialog (examples are left alone there too).
    */
   static void saveDeletedWork(LPSymbolizer lpsymbolizer) {
      if (lpsymbolizer.problemIndex == -1) {
         return;
      }

      synchronized (LPSymbolizer.problems) {
         ProblemEntry entry = LPSymbolizer.problems.getEntryAt(lpsymbolizer.problemIndex);
         if (entry != null && !LPSymbolizer.isExample(new TaggedRecord(entry.name).getName())) {
            entry.name = LPSymbolizer.removeWork(new TaggedRecord(entry.name));
            entry.state = 0;
            lpsymbolizer.loadProblem(entry.name);
            LPSymbolizer.saveProblems();
         }
      }
   }
}
