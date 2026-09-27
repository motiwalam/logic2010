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

class DerivationDialogs implements DerivationConstants {
   static String[] SYMBOLS = LogicProgram.symbols;

   static void showMessage(String s) {
      showMessage(s, null, null, null, null, null);
   }

   static void showMessage(String s, Point point) {
      showMessage(s, null, null, null, null, point);
   }

   static void showMessage(String s, Hashtable hashtable) {
      showMessage(s, hashtable, null, null, null, null);
   }

   static void showMessage(String s, Hashtable hashtable, DerivationLine derivationline) {
      showMessage(s, hashtable, null, null, derivationline, null);
   }

   static void showMessage(String s, Hashtable hashtable, DerivationLineChecker derivationlinechecker) {
      showMessage(s, hashtable, null, derivationlinechecker, derivationlinechecker.line, null);
   }

   static void showMessage(String s, Hashtable hashtable, Hashtable hashtable1, DerivationLineChecker derivationlinechecker) {
      showMessage(s, hashtable, hashtable1, derivationlinechecker, derivationlinechecker.line, null);
   }

   static void showMessage(
      String s, Hashtable hashtable, Hashtable hashtable1, DerivationLineChecker derivationlinechecker, DerivationLine derivationline, Point point
   ) {
      Message message = DerivationMessage.get(s);
      String s1 = message.id;
      String s2 = DerivationMessage.format(message.text, hashtable, derivationlinechecker, derivationline);
      DerivationQueryHandler derivationqueryhandler = null;
      if (message.buttons != null) {
         derivationqueryhandler = new DerivationQueryHandler(derivationline, message.buttons);
         if (hashtable1 != null) {
            derivationqueryhandler.setProperties(hashtable1);
         }
      }

      MessageDialog.showMessage(s1, s2, point, derivationqueryhandler);
   }

   static boolean confirmSaveChanges(LPDerivation lpderivation, Point point) {
      String s = lpderivation.getChangedProblem();
      if (s == null) {
         return true;
      } else {
         lpderivation.frame.setVisible(true);
         LogicLabel logiclabel = new LogicLabel("Do you wish to save the current problem?");
         String[] astring = new String[]{"Yes", "No", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpderivation.frame, "", logiclabel, astring);
         messagedialog.showAt(point);
         return messagedialog.selectedButton == 0 && !lpderivation.saveProblems(s)
            ? false
            : messagedialog.selectedButton == 0 || messagedialog.selectedButton == 1;
      }
   }

   static boolean confirmDeleteProblem(LPDerivation lpderivation, Point point) {
      if (lpderivation.problem.getContentCount() != 1) {
         RadioGroupPanel radiogrouppanel = new RadioGroupPanel();
         String[] astring = new String[]{"Delete the work on this problem?", "Delete this problem?"};
         int i = astring.length;
         JRadioButton[] ajradiobutton = new JRadioButton[i];

         for (int j = 0; j < i; j++) {
            radiogrouppanel.add(ajradiobutton[j] = new LogicRadioButton(astring[j]));
         }

         ajradiobutton[0].setSelected(true);
         String[] astring2 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpderivation.frame, "", radiogrouppanel, astring2);
         messagedialog.showAt(null);
         if (messagedialog.selectedButton != 0) {
            return false;
         }

         if (ajradiobutton[0].isSelected()) {
            deleteWorkAndSave(lpderivation);
            return true;
         }

         if (!ajradiobutton[1].isSelected()) {
            return false;
         }
      } else if (lpderivation.problemIndex != -1 || !lpderivation.problem.getFormulaText(false).equals("")) {
         LogicLabel logiclabel = new LogicLabel("Delete this problem?");
         String[] astring1 = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog1 = new MessageDialog(lpderivation.frame, "", logiclabel, astring1);
         messagedialog1.showAt(null);
         if (messagedialog1.selectedButton != 0) {
            return false;
         }
      }

      if (lpderivation.problemIndex != -1) {
         LPDerivation.problems.removeProblem(lpderivation.problemIndex);
         LPDerivation.saveProblems();
      }

      lpderivation.newProblem();
      return true;
   }

   static boolean confirmDeleteWork(LPDerivation lpderivation, Point point) {
      if (lpderivation.problem.getContentCount() == 1) {
         return false;
      } else {
         LogicLabel logiclabel = new LogicLabel("Delete the work on this problem?");
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpderivation.frame, "", logiclabel, astring);
         messagedialog.showAt(point);
         if (messagedialog.selectedButton != 0) {
            return false;
         } else {
            deleteWorkAndSave(lpderivation);
            return true;
         }
      }
   }

   /**
    * Deletes the work on the module's current problem and saves the result.
    * (The original only removed the lines from the on-screen derivation, without
    * repainting or updating the saved work, so the work reappeared on the next load.)
    * Same steps as the "Delete Work" choice of deleteProblemsDialog (examples are left alone there too).
    */
   static void deleteWorkAndSave(LPDerivation lpderivation) {
      synchronized (LPDerivation.problems) {
         DerivationProblemEntry entry = lpderivation.problemIndex == -1
            ? null
            : (DerivationProblemEntry)LPDerivation.problems.getEntryAt(lpderivation.problemIndex);
         if (entry == null || LPDerivation.isExample(new TaggedRecord(entry.name).getName())) {
            lpderivation.removeWork();
            lpderivation.problem.revalidate();
            lpderivation.problem.repaint();
            return;
         }

         entry.name = LPDerivation.removeWork(new TaggedRecord(entry.name));
         entry.state = 0;
         lpderivation.loadProblem(entry.name);
         LPDerivation.saveProblems();
      }
   }

   static boolean openNextProblem(LPDerivation lpderivation) {
      if (confirmSaveChanges(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            int i = lpderivation.problemIndex + 1;
            if (i != 0 && LPDerivation.openInstance(i, true) != null) {
               return true;
            }

            String s = i == 0 ? null : LPDerivation.problems.getRecordAt(i);
            if (s == null) {
               return chooseProblem(lpderivation);
            }

            lpderivation.loadProblem(s);
            lpderivation.problemIndex = i;
            LPDerivation.problems.replaceProblem(lpderivation.saveProblem(), i);
            if (LogicModule.eraseWork) {
               lpderivation.removeWork();
            }
         }
      }

      lpderivation.requestFocus();
      return true;
   }

   static boolean chooseProblem(LPDerivation lpderivation) {
      boolean flag = false;
      if (confirmSaveChanges(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            ProblemListView problemlistview = createProblemList(lpderivation, false, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpderivation.frame, ProblemSet.markTitle("Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("derChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               int i = problemlistview.getSelectedProblem(problemlistview.rowToProblem);
               LPDerivation lpderivation1;
               if ((lpderivation1 = LPDerivation.openInstance(i, true)) != null) {
                  lpderivation1.showInFront();
                  return false;
               }

               if (i != -1) {
                  lpderivation.loadProblem(LPDerivation.problems.getRecordAt(i));
                  lpderivation.problemIndex = i;
                  LPDerivation.problems.replaceProblem(lpderivation.saveProblem(), i);
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

   static int[] chooseProblemsToSubmit(LPDerivation lpderivation) {
      int[] aint = null;
      if (confirmSaveChanges(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            ProblemListView problemlistview = createProblemList(lpderivation, true, false, null);
            if (LPDerivation.submitExam) {
               problemlistview.clearSelection();
            }

            Vector vector = LPDerivation.getChangedProblems();
            if (vector != null && vector.size() != 0) {
               int k = vector.size();

               for (int i = 0; i < k; i++) {
                  String s = (String)vector.get(i);
                  String s1 = TaggedRecord.nameOf(s);
                  int j = LPDerivation.problems.indexOfName(s1);
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
            MessageDialog messagedialog = new MessageDialog(lpderivation.frame, ProblemSet.markTitle("Submit Problems"), sizedpanel, astring);
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

      lpderivation.requestFocus();
      return aint;
   }

   static int[] chooseProblemsToUpload(LPDerivation lpderivation) {
      int[] aint = null;
      if (confirmSaveChanges(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            ProblemListView problemlistview = createProblemList(lpderivation, true, false, null);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            SizedPanel sizedpanel = new SizedPanel();
            MessageTextArea messagetextarea = new MessageTextArea(LogicProgram.expandEscapes(Message.getText("not089")));
            messagetextarea.setLineWrap(true);
            messagetextarea.setWrapStyleWord(true);
            sizedpanel.add(messagetextarea, "North");
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"Upload", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(lpderivation.frame, ProblemSet.markTitle("Upload Problems"), sizedpanel, astring);
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

      lpderivation.requestFocus();
      return aint;
   }

   static void deleteProblemsDialog(LPDerivation lpderivation) {
      synchronized (LPDerivation.problems) {
         ProblemListView problemlistview = createProblemList(lpderivation, true, true, null);
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"Delete Work", "Delete Problems", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(lpderivation.frame, ProblemSet.markTitle("Delete Problems"), jscrollpane, astring);
         problemlistview.setDialog(messagedialog, 0);
         Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.setBoundsKey("derChosen");
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
                     DerivationProblemEntry derivationproblementry1 = (DerivationProblemEntry)LPDerivation.problems.getEntryAt(l);
                     if (derivationproblementry1 != null) {
                        TaggedRecord taggedrecord1 = new TaggedRecord(derivationproblementry1.name);
                        if (!LPDerivation.isExample(taggedrecord1.getName())) {
                           derivationproblementry1.name = LPDerivation.removeWork(taggedrecord1);
                           derivationproblementry1.state = 0;
                           LPDerivation lpderivation2 = LPDerivation.openInstance(l, false);
                           if (lpderivation2 != null) {
                              lpderivation2.loadProblem(derivationproblementry1.name);
                           }
                        }
                     }
                  }
               }

               LPDerivation.saveProblems();
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
                     DerivationProblemEntry derivationproblementry = (DerivationProblemEntry)LPDerivation.problems.getEntryAt(j);
                     if (derivationproblementry != null) {
                        TaggedRecord taggedrecord = new TaggedRecord(derivationproblementry.name);
                        if (!LPDerivation.isExercise(taggedrecord.getName())) {
                           LPDerivation lpderivation1 = LPDerivation.openInstance(j, false);
                           LPDerivation.problems.removeProblem(j);
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

   static int[] chooseProblemsToPrint(LPDerivation lpderivation) {
      int[] aint = null;
      if (confirmSaveChanges(lpderivation, null)) {
         synchronized (LPDerivation.problems) {
            ProblemListView problemlistview = createProblemList(lpderivation, true, false, LPDerivation.noPrint);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.setViewportView(problemlistview);
            String[] astring = new String[]{"Print", "Print Results", "Print List", "Cancel"};
            problemlistview.cancelButton = 3;
            MessageDialog messagedialog = new MessageDialog(lpderivation.frame, ProblemSet.markTitle("Print Problems"), jscrollpane, astring);
            problemlistview.setDialog(messagedialog, 0);
            Dimension dimension = new Dimension(32 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
            messagedialog.setSize(dimension);
            messagedialog.setBoundsKey("derChosen");
            problemlistview.requestFocus();
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            if (messagedialog.selectedButton == 0) {
               aint = problemlistview.getSelectedProblems(problemlistview.rowToProblem);
            } else if (messagedialog.selectedButton == 1) {
               DerivationResultsPage.printResults(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            } else if (messagedialog.selectedButton == 2) {
               DerivationStatementsPage.printStatements(problemlistview.getSelectedProblems(problemlistview.rowToProblem));
            }
         }
      }

      lpderivation.requestFocus();
      return aint;
   }

   static void enterSubmittedProblem(LPDerivation lpderivation) {
      if (confirmSaveChanges(lpderivation, null)) {
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.centeredLocation(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         EditableTextPane editabletextpane = new EditableTextPane();
         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(editabletextpane);
         editabletextpane.setBackground(Color.WHITE);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "Submitted Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.setBoundsKey("derSubmitted");
         editabletextpane.requestFocus();
         messagedialog.showAt(point, true);
         moduleframe.dispose();
         if (messagedialog.selectedButton == 0) {
            lpderivation.loadProblem(editabletextpane.getText());
         }
      }

      lpderivation.requestFocus();
   }

   static ProblemListView createProblemList(LPDerivation lpderivation, boolean flag, boolean flag1, ProblemSelector problemselector) {
      return LPDerivation.problems.createListView(lpderivation, LPDerivation.exercises, flag, flag1, LPDerivation.monoProbs, problemselector);
   }

   static void enterUserProblem(LPDerivation lpderivation) {
      if (confirmSaveChanges(lpderivation, null)) {
         ModuleFrame moduleframe = new ModuleFrame();
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 10 * LogicProgram.fontSize);
         Point point = MessageDialog.centeredLocation(dimension);
         JScrollPane jscrollpane = new JScrollPane();
         FormulaEntryField formulaentryfield = new FormulaEntryField(moduleframe);
         if (lpderivation.lastUserProblem != null) {
            formulaentryfield.setText(LogicProgram.translateSymbols(lpderivation.lastUserProblem, maggie, SYMBOLS));
         }

         String[] astring = new String[]{"OK", "Cancel"};
         jscrollpane.setViewportView(formulaentryfield);
         formulaentryfield.setBackground(Color.WHITE);
         MessageDialog messagedialog = new MessageDialog(moduleframe, "User Problem", jscrollpane, astring);
         messagedialog.setSize(dimension);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.setBoundsKey("derUser");
         formulaentryfield.ownerDialog = messagedialog;
         formulaentryfield.requestFocus();
         messagedialog.showAt(point, true);
         moduleframe.dispose();
         if (messagedialog.selectedButton == 0) {
            String s = formulaentryfield.getText();
            if (!LPDerivation.validateUserProblem(s)) {
               return;
            }

            lpderivation.lastUserProblem = LogicProgram.translateSymbols(s, SYMBOLS, maggie);
            lpderivation.loadUserProblem(lpderivation.lastUserProblem);
         }
      }

      lpderivation.requestFocus();
   }

   static int chooseFormula(DerivationLine derivationline, Expression[] aexpression, String s) {
      if (derivationline.box.module.serialMode) {
         return -1;
      } else {
         int i = aexpression.length;
         RadioGroupPanel radiogrouppanel = new RadioGroupPanel();
         JPanel jpanel = new JPanel();
         JRadioButton[] ajradiobutton = new JRadioButton[i];
         jpanel.setLayout(new VerticalStackLayout());
         jpanel.add(MultiLineLabel.create(s));
         jpanel.add(radiogrouppanel);
         radiogrouppanel.setLayout(new VerticalStackLayout());

         for (int j = 0; j < i; j++) {
            ajradiobutton[j] = new DialogRadioButton(LogicProgram.translateSymbols(aexpression[j].toString()));
            radiogrouppanel.add(ajradiobutton[j]);
         }

         ajradiobutton[0].setSelected(true);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(derivationline.box.module.frame, "Line " + derivationline.getLineNumber(), jpanel, astring);
         messagedialog.setDefaultButtonIndex(0);
         derivationline.focusEditor(true);
         Rectangle rectangle = LogicProgram.boundsRelativeTo(derivationline.annotationEditor, null);
         messagedialog.pack();
         messagedialog.showAt(new Point(rectangle.x, rectangle.y + rectangle.height));
         if (messagedialog.selectedButton == 0) {
            for (int k = 0; k < i; k++) {
               if (ajradiobutton[k].isSelected()) {
                  return k;
               }
            }
         }

         return -1;
      }
   }

   static void showProgramHelp(Reader reader) {
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
      FormulaTextPane formulatextpane = new FormulaTextPane(LogicProgram.expandEscapes(s1));
      formulatextpane.setEditable(false);
      formulatextpane.setBackground(dialogWhite);
      String[] astring = new String[]{"OK"};
      jscrollpane.setViewportView(formulatextpane);
      MessageDialog messagedialog = new MessageDialog(moduleframe, "Program Help", jscrollpane, astring);
      Dimension dimension = new Dimension(45 * LogicProgram.fontSize, 32 * LogicProgram.fontSize);
      messagedialog.setSize(dimension);
      messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
      moduleframe.dispose();
   }

   static int chooseRuleInstance(DerivationLineChecker derivationlinechecker, Vector vector) {
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
            HighlightedText highlightedtext = null;
            if (object instanceof RuleApplication) {
               RuleApplication ruleapplication = (RuleApplication)object;
               highlightedtext = ruleapplication.createDisplay(null).toHighlightedText();
            } else if (object instanceof InterchangeJustification) {
               InterchangeJustification interchangejustification = (InterchangeJustification)object;
               highlightedtext = interchangejustification.toHighlightedText(derivationlinechecker);
            }

            if (highlightedtext != null && vector1.indexOf(highlightedtext) == -1) {
               vector1.addElement(highlightedtext);
               k++;
            } else {
               vector1.addElement(null);
            }
         }

         if (k == 1) {
            return 0;
         } else if (derivationlinechecker.line.box.module.serialMode) {
            return -1;
         } else {
            RadioGroupPanel radiogrouppanel = new RadioGroupPanel();
            SizedPanel sizedpanel1 = new SizedPanel();
            JRadioButton[] ajradiobutton = new JRadioButton[j];
            sizedpanel1.setLayout(new VerticalStackLayout());
            int l = derivationlinechecker.maxPremises;
            Hashtable hashtable = Message.params("n", l + "");
            if (l > 0) {
               sizedpanel1.add(MultiLineLabel.create(DerivationMessage.format(DerivationMessage.getText("derdlg003"), hashtable, derivationlinechecker)));

               for (int i1 = 0; i1 < l; i1++) {
                  sizedpanel1.add(LogicProgram.createFormulaRow(derivationlinechecker.getStackFormula(i1 - l).toString(), 14));
               }
            }

            if (derivationlinechecker.matchLine && derivationlinechecker.lineFormula != null) {
               String s1 = l > 0 ? "derdlg004" : "derdlg005";
               sizedpanel1.add(MultiLineLabel.create(DerivationMessage.format(DerivationMessage.getText(s1), hashtable, derivationlinechecker)));
               sizedpanel1.add(LogicProgram.createFormulaRow(derivationlinechecker.lineFormula.toString(), 14));
               sizedpanel1.add(MultiLineLabel.create(DerivationMessage.format(DerivationMessage.getText("derdlg006"), hashtable, derivationlinechecker)));
            } else {
               String s = "derdlg017";
               sizedpanel1.add(MultiLineLabel.create(DerivationMessage.format(DerivationMessage.getText(s), hashtable, derivationlinechecker)));
            }

            sizedpanel1.add(radiogrouppanel);
            radiogrouppanel.setLayout(new VerticalStackLayout());
            boolean flag = false;

            for (int j1 = 0; j1 < j; j1++) {
               HighlightedText highlightedtext1 = (HighlightedText)vector1.elementAt(j1);
               if (highlightedtext1 == null) {
                  ajradiobutton[j1] = new DialogRadioButton("");
               } else if (derivationlinechecker.matchLine && derivationlinechecker.lineFormula != null) {
                  Object object1 = vector.elementAt(j1);
                  if (object1 instanceof RuleApplication) {
                     RuleApplication ruleapplication1 = (RuleApplication)object1;
                     SchematicRule schematicrule = ruleapplication1.getForm();
                     ajradiobutton[j1] = new DialogRadioButton(LogicProgram.translateSymbols(schematicrule.formatInOrder(ruleapplication1.premiseOrder)));
                     radiogrouppanel.add(ajradiobutton[j1]);
                     if (!flag) {
                        JRadioButton jradiobutton = ajradiobutton[j1];
                        flag = true;
                        jradiobutton.setSelected(true);
                     }
                  } else if (object1 instanceof InterchangeJustification) {
                     InterchangeJustification interchangejustification1 = (InterchangeJustification)object1;
                     SchematicRule schematicrule1 = interchangejustification1.getEquivalenceRule(derivationlinechecker);
                     ajradiobutton[j1] = new DialogRadioButton(LogicProgram.translateSymbols(schematicrule1.toString()));
                     radiogrouppanel.add(ajradiobutton[j1]);
                     if (!flag) {
                        JRadioButton jradiobutton1 = ajradiobutton[j1];
                        flag = true;
                        jradiobutton1.setSelected(true);
                     }
                  } else {
                     ajradiobutton[j1] = new DialogRadioButton("");
                  }
               } else {
                  SizedPanel sizedpanel = new SizedPanel();
                  sizedpanel.setLimitWidth(LogicProgram.screenSize.width * 3 / 4);
                  sizedpanel.setMaximumOnly(true);
                  StyledDocument styleddocument = LogicProgram.translateToDocument(
                     highlightedtext1.text, maggie, SYMBOLS, TextHighlighter.fromRanges(highlightedtext1.layers)
                  );
                  EditableTextPane editabletextpane = new EditableTextPane(styleddocument, -1, -1, true);
                  editabletextpane.setWrapLines(true);
                  editabletextpane.setWrapWords(true);
                  editabletextpane.setEditable(false);
                  sizedpanel.add(editabletextpane, "Center");
                  RadioOptionRow radiooptionrow = new RadioOptionRow(sizedpanel);
                  ajradiobutton[j1] = radiooptionrow.radioButton;
                  radiogrouppanel.add(radiooptionrow);
                  if (!flag) {
                     JRadioButton jradiobutton2 = ajradiobutton[j1];
                     flag = true;
                     jradiobutton2.setSelected(true);
                  }
               }
            }

            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(
               derivationlinechecker.line.box.module.frame, "Line " + derivationlinechecker.line.getLineNumber(), sizedpanel1, astring
            );
            messagedialog.setDefaultButtonIndex(0);
            derivationlinechecker.line.focusEditor(true);
            Rectangle rectangle = LogicProgram.boundsRelativeTo(derivationlinechecker.line.annotationEditor, null);
            messagedialog.pack();
            messagedialog.showAt(new Point(rectangle.x, rectangle.y + rectangle.height));
            if (messagedialog.selectedButton != 0) {
               return -1;
            } else {
               boolean flag1 = false;
               int k1 = 0;

               while (k1 < j && !ajradiobutton[k1].isSelected()) {
                  k1++;
               }

               return k1 == j ? -1 : k1;
            }
         }
      }
   }

   static Expression chooseSideToShow(DerivationLineChecker derivationlinechecker, Expression expression, boolean flag) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new VerticalStackLayout());
      String[] astring = new String[]{"OK", "Cancel"};
      JRadioButton[] ajradiobutton = new JRadioButton[2];
      Expression[] aexpression = new Expression[2];
      RadioGroupPanel radiogrouppanel = new RadioGroupPanel();
      radiogrouppanel.setLayout(new VerticalStackLayout());

      for (int i = 0; i < 2; i++) {
         aexpression[i] = expression.getChild(i).copy();
         if (flag) {
            aexpression[i] = aexpression[i].negate();
         }

         radiogrouppanel.add(ajradiobutton[i] = new LogicRadioButton(LogicProgram.translateSymbols(aexpression[i].toString(), maggie, SYMBOLS)));
      }

      ajradiobutton[0].setSelected(true);
      jpanel.add(new LogicLabel("Please choose a formula to show"));
      jpanel.add(radiogrouppanel);
      EditableTextPane editabletextpane = new EditableTextPane();
      MessageDialog messagedialog = new MessageDialog(derivationlinechecker.line.box.module.frame, "Choose a Formula", jpanel, astring);
      if (messagedialog.fillFieldsAndChoose(derivationlinechecker.presetAnswers, new EditableTextPane[]{editabletextpane}, 0)) {
         String s = editabletextpane.getText();
         if (s.equalsIgnoreCase("L")) {
            return aexpression[0];
         }

         if (s.equalsIgnoreCase("R")) {
            return aexpression[1];
         }
      }

      if (derivationlinechecker.line.box.module.serialMode) {
         derivationlinechecker.reportError("dererr064");
         derivationlinechecker.line.box.module.complete = false;
         messagedialog.dispose();
         return null;
      } else {
         Rectangle rectangle = LogicProgram.boundsRelativeTo(derivationlinechecker.line, null);
         messagedialog.showAt(new Point(rectangle.x, rectangle.y + rectangle.height));
         if (messagedialog.selectedButton == 0) {
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

   static boolean instanceSchemeQuery(DerivationLineChecker derivationlinechecker, Justification justification) {
      if (derivationlinechecker.line.box.module.serialMode && derivationlinechecker.presetAnswers == null) {
         derivationlinechecker.reportError("dererr064");
         derivationlinechecker.line.box.module.complete = false;
         return false;
      } else {
         InterchangeJustification interchangejustification = null;
         RuleApplication ruleapplication = null;
         Object object = null;
         if (justification instanceof InterchangeJustification) {
            interchangejustification = (InterchangeJustification)justification;
            ruleapplication = interchangejustification.toRuleApplication();
            object = interchangejustification.path;
         } else {
            if (!(justification instanceof RuleApplication)) {
               throw new IllegalArgumentException("instanceSchemeQuery expects LPRuleInstance or LPInterchangeInstance");
            }

            interchangejustification = null;
            ruleapplication = (RuleApplication)justification;
            object = new ExpressionPath();
         }

         boolean flag = derivationlinechecker.matchLine && derivationlinechecker.lineFormula != null;
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new VerticalStackLayout());
         SchemeInstantiation schemeinstantiation = ruleapplication.instantiation;
         SchemeSubstitutionPanel schemesubstitutionpanel = null;
         RuleApplication ruleapplication1 = null;
         SchematicRule schematicrule = ruleapplication.form;
         int i = schematicrule.premises.length;
         Hashtable hashtable = Message.params("n", i + "");
         if (interchangejustification != null) {
            Message.putParam(hashtable, "rule name", schematicrule.name);
         }

         if (i > 0) {
            jpanel.add(MultiLineLabel.create(DerivationMessage.format(DerivationMessage.getText("derdlg003"), hashtable, derivationlinechecker)));

            for (int j = 0; j < i; j++) {
               jpanel.add(
                  LogicProgram.createFormulaRow(derivationlinechecker.getStackFormula(j - i).getSubexpression((ExpressionPath)object).toString(), 14, 350)
               );
            }
         }

         if (flag) {
            String s1 = i > 0 ? "derdlg004" : "derdlg005";
            jpanel.add(MultiLineLabel.create(DerivationMessage.format(DerivationMessage.getText(s1), hashtable, derivationlinechecker)));
            jpanel.add(LogicProgram.createFormulaRow(derivationlinechecker.lineFormula.getSubexpression((ExpressionPath)object).toString(), 14, 350));
            jpanel.add(MultiLineLabel.create(DerivationMessage.format(DerivationMessage.getText("derdlg007"), hashtable, derivationlinechecker)));
            jpanel.add(LogicProgram.createFormulaRow(schematicrule.formatInOrder(ruleapplication.premiseOrder), 14, 350));
            schemesubstitutionpanel = new SchemeSubstitutionPanel(schemeinstantiation, 250, derivationlinechecker.line.box.module.frame);
         } else {
            RuleApplicationDisplay ruleapplicationdisplay = ruleapplication.createDisplay(null);
            ruleapplication1 = ruleapplicationdisplay.application;
            String s = "derdlg019";
            jpanel.add(MultiLineLabel.create(DerivationMessage.format(DerivationMessage.getText(s), hashtable, derivationlinechecker)));
            HighlightedText highlightedtext = ruleapplicationdisplay.toHighlightedText();
            jpanel.add(LogicProgram.createFormulaRow(highlightedtext, 14, 350));
            schemesubstitutionpanel = new SchemeSubstitutionPanel(
               ruleapplicationdisplay.displayInstantiation, 250, derivationlinechecker.line.box.module.frame, true
            );
         }

         jpanel.add(schemesubstitutionpanel);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.line.box.module.frame, "Line " + derivationlinechecker.line.getLineNumber(), jpanel, astring
         );
         messagedialog.setDefaultButtonIndex(0);
         schemesubstitutionpanel.setMessageDialog(messagedialog);
         derivationlinechecker.line.focusEditor(true);
         Rectangle rectangle = LogicProgram.boundsRelativeTo(derivationlinechecker.line.annotationEditor, null);
         if (!messagedialog.fillFieldsAndChoose(derivationlinechecker.presetAnswers, schemesubstitutionpanel.getPendingFields(), 0)) {
            if (derivationlinechecker.line.box.module.serialMode) {
               derivationlinechecker.reportError("dererr064");
               derivationlinechecker.line.box.module.complete = false;
               messagedialog.dispose();
               return false;
            }

            messagedialog.pack();
            messagedialog.showAt(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            messagedialog.dispose();
         }

         if (messagedialog.selectedButton != 0) {
            derivationlinechecker.reportError("dererr028");
            derivationlinechecker.line.box.module.abort(true);
            return false;
         } else {
            SchemeInstantiation schemeinstantiation1 = schemesubstitutionpanel.readInstantiation();
            if (schemeinstantiation1 == null) {
               derivationlinechecker.reportError(schemesubstitutionpanel.errorId, schemesubstitutionpanel.errorParams);
               return false;
            } else {
               if (flag) {
                  if (!schemeinstantiation.mergeFrom(schemeinstantiation1)) {
                     derivationlinechecker.reportError(schemeinstantiation.errorId, schemeinstantiation.errorParams);
                     return false;
                  }
               } else {
                  while (!schemeinstantiation.pendingLetters.isEmpty()) {
                     Expression expression = ((SchematicLetter)schemeinstantiation.pendingLetters.elementAt(0)).toExpression();
                     Expression expression1 = expression.instantiate(ruleapplication1.instantiation).instantiate(schemeinstantiation1);
                     if (!schemeinstantiation.addReplacement(expression, expression1)) {
                        derivationlinechecker.reportError(schemeinstantiation.errorId, schemeinstantiation.errorParams);
                        return false;
                     }
                  }
               }

               return true;
            }
         }
      }
   }

   static boolean generalizationTermQuery(DerivationLineChecker derivationlinechecker, RuleApplication ruleapplication) {
      if (derivationlinechecker.line.box.module.serialMode) {
         derivationlinechecker.line.box.module.complete = false;
         derivationlinechecker.reportError("dererr064");
         return false;
      } else {
         SubstitutionValuesPanel substitutionvaluespanel = new SubstitutionValuesPanel(250);
         SchemeInstantiation schemeinstantiation = ruleapplication.instantiation;
         GeneralizationTermSelector generalizationtermselector = new GeneralizationTermSelector(
            derivationlinechecker.getStackFormula(-1), 250, derivationlinechecker.line.box.module.frame
         );
         generalizationtermselector.addSubstitutionListener(substitutionvaluespanel);
         if (!termSelectionQuery(derivationlinechecker, ruleapplication, "derdlg008", null, generalizationtermselector, substitutionvaluespanel, null)) {
            return false;
         } else {
            generalizationtermselector.removeSubstitutionListener(substitutionvaluespanel);
            String s = LogicProgram.translateSymbols(generalizationtermselector.getText(), SYMBOLS, maggie);
            Term term = generalizationtermselector.selectedTerms[0];
            if (!schemeinstantiation.addReplacement(schemeinstantiation.pendingLetters.elementAt(0).toString(), s)) {
               derivationlinechecker.reportError(schemeinstantiation.errorId, schemeinstantiation.errorParams);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean existentialVarQuery(DerivationLineChecker derivationlinechecker, RuleApplication ruleapplication) {
      if (derivationlinechecker.line.box.module.serialMode && derivationlinechecker.presetAnswers == null) {
         derivationlinechecker.line.box.module.complete = false;
         derivationlinechecker.reportError("dererr064");
         return false;
      } else {
         JPanel jpanel = new JPanel();
         JPanel jpanel1 = new JPanel();
         JPanel jpanel2 = new JPanel();
         jpanel.setLayout(new VerticalStackLayout());
         jpanel1.setLayout(new FlexGridLayout(1, 1, 0, 0, true, true));
         jpanel2.setLayout(new BorderLayout());
         SchemeInstantiation schemeinstantiation = ruleapplication.instantiation;
         FormulaEntryField formulaentryfield = new FormulaEntryField("", 250, derivationlinechecker.line.box.module.frame);
         jpanel1.add(formulaentryfield, new Point(0, 0));
         JScrollPane jscrollpane = new JScrollPane(jpanel1, 22, 31);
         jpanel2.add(jscrollpane);
         Hashtable hashtable = Message.params("gen var", "\\l" + ((Expression)derivationlinechecker.stack.elementAt(0)).getChild(0) + "\\l");
         jpanel.add(MultiLineLabel.create(DerivationMessage.format(DerivationMessage.getText("derdlg009"), hashtable, derivationlinechecker)));
         jpanel.add(jpanel2);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.line.box.module.frame, "Line " + derivationlinechecker.line.getLineNumber(), jpanel, astring
         );
         formulaentryfield.ownerDialog = messagedialog;
         derivationlinechecker.line.focusEditor(true);
         Rectangle rectangle = LogicProgram.boundsRelativeTo(derivationlinechecker.line.annotationEditor, null);
         messagedialog.setDefaultButtonIndex(0);
         DerivationQueryHandler derivationqueryhandler = new DerivationQueryHandler(derivationlinechecker.line, "OK:existentialVarQueryOK.Cancel");
         derivationqueryhandler.setProperty("just", derivationlinechecker);
         derivationqueryhandler.setProperty("inst", ruleapplication);
         derivationqueryhandler.setProperty("edit", formulaentryfield);
         messagedialog.addHandler(derivationqueryhandler);
         if (!messagedialog.fillFieldsAndChoose(derivationlinechecker.presetAnswers, new EditableTextPane[]{formulaentryfield}, 0)) {
            if (derivationlinechecker.line.box.module.serialMode) {
               derivationlinechecker.line.box.module.complete = false;
               derivationlinechecker.reportError("dererr064");
               messagedialog.dispose();
               return false;
            }

            messagedialog.pack();
            messagedialog.showAt(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            messagedialog.dispose();
         }

         if (messagedialog.selectedButton != 0) {
            derivationlinechecker.reportError("dererr028");
            derivationlinechecker.line.box.module.abort(true);
            return false;
         } else {
            String s = LogicProgram.translateSymbols(formulaentryfield.getText(), SYMBOLS, maggie);
            if (!schemeinstantiation.addReplacement(schemeinstantiation.pendingLetters.elementAt(0).toString(), s)) {
               derivationlinechecker.reportError(schemeinstantiation.errorId, schemeinstantiation.errorParams);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean universalTermQuery(DerivationLineChecker derivationlinechecker, RuleApplication ruleapplication) {
      if (derivationlinechecker.line.box.module.serialMode && derivationlinechecker.presetAnswers == null) {
         derivationlinechecker.line.box.module.complete = false;
         derivationlinechecker.reportError("dererr064");
         return false;
      } else {
         JPanel jpanel = new JPanel();
         JPanel jpanel1 = new JPanel();
         JPanel jpanel2 = new JPanel();
         jpanel.setLayout(new VerticalStackLayout());
         jpanel1.setLayout(new FlexGridLayout(1, 1, 0, 0, true, true));
         jpanel2.setLayout(new BorderLayout());
         SchemeInstantiation schemeinstantiation = ruleapplication.instantiation;
         FormulaEntryField formulaentryfield = new FormulaEntryField("", 250, derivationlinechecker.line.box.module.frame);
         jpanel1.add(formulaentryfield, new Point(0, 0));
         JScrollPane jscrollpane = new JScrollPane(jpanel1, 22, 31);
         jpanel2.add(jscrollpane);
         Hashtable hashtable = Message.params("gen var", "\\l" + ((Expression)derivationlinechecker.stack.elementAt(0)).getChild(0) + "\\l");
         jpanel.add(MultiLineLabel.create(DerivationMessage.format(DerivationMessage.getText("derdlg010"), hashtable, derivationlinechecker)));
         jpanel.add(jpanel2);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.line.box.module.frame, "Line " + derivationlinechecker.line.getLineNumber(), jpanel, astring
         );
         formulaentryfield.ownerDialog = messagedialog;
         derivationlinechecker.line.focusEditor(true);
         Rectangle rectangle = LogicProgram.boundsRelativeTo(derivationlinechecker.line.annotationEditor, null);
         messagedialog.setDefaultButtonIndex(0);
         DerivationQueryHandler derivationqueryhandler = new DerivationQueryHandler(derivationlinechecker.line, "OK:universalTermQueryOK.Cancel");
         derivationqueryhandler.setProperty("just", derivationlinechecker);
         derivationqueryhandler.setProperty("inst", ruleapplication);
         derivationqueryhandler.setProperty("edit", formulaentryfield);
         messagedialog.addHandler(derivationqueryhandler);
         if (!messagedialog.fillFieldsAndChoose(derivationlinechecker.presetAnswers, new EditableTextPane[]{formulaentryfield}, 0)) {
            if (derivationlinechecker.line.box.module.serialMode) {
               derivationlinechecker.line.box.module.complete = false;
               derivationlinechecker.reportError("dererr064");
               messagedialog.dispose();
               return false;
            }

            messagedialog.pack();
            messagedialog.showAt(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            messagedialog.dispose();
         }

         if (messagedialog.selectedButton != 0) {
            derivationlinechecker.reportError("dererr028");
            derivationlinechecker.line.box.module.abort(true);
            return false;
         } else {
            String s = LogicProgram.translateSymbols(formulaentryfield.getText(), SYMBOLS, maggie);
            if (!schemeinstantiation.addReplacement(schemeinstantiation.pendingLetters.elementAt(0).toString(), s)) {
               derivationlinechecker.reportError(schemeinstantiation.errorId, schemeinstantiation.errorParams);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean dummyVarQuery(DerivationLineChecker derivationlinechecker, RuleApplication ruleapplication, BoundVariableMap boundvariablemap) {
      if (derivationlinechecker.line.box.module.serialMode && derivationlinechecker.presetAnswers == null) {
         derivationlinechecker.line.box.module.complete = false;
         derivationlinechecker.reportError("dererr064");
         return false;
      } else {
         JPanel jpanel = new JPanel();
         JPanel jpanel1 = new JPanel();
         JPanel jpanel2 = new JPanel();
         jpanel.setLayout(new VerticalStackLayout());
         jpanel1.setLayout(new FlexGridLayout(1, 1, 0, 0, true, true));
         jpanel2.setLayout(new BorderLayout());
         FormulaEntryField formulaentryfield = new FormulaEntryField("", 250, derivationlinechecker.line.box.module.frame);
         jpanel1.add(formulaentryfield, new Point(0, 0));
         JScrollPane jscrollpane = new JScrollPane(jpanel1, 22, 31);
         jpanel2.add(jscrollpane);
         Expression expression = ruleapplication.getForm().conclusion;
         jpanel.add(MultiLineLabel.create(DerivationMessage.format(DerivationMessage.getText("derdlg011"), null, derivationlinechecker)));
         jpanel.add(jpanel2);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.line.box.module.frame, "Line " + derivationlinechecker.line.getLineNumber(), jpanel, astring
         );
         formulaentryfield.ownerDialog = messagedialog;
         derivationlinechecker.line.focusEditor(true);
         Rectangle rectangle = LogicProgram.boundsRelativeTo(derivationlinechecker.line.annotationEditor, null);
         messagedialog.setDefaultButtonIndex(0);
         DerivationQueryHandler derivationqueryhandler = new DerivationQueryHandler(derivationlinechecker.line, "OK:dummyVarQueryOK.Cancel");
         derivationqueryhandler.setProperty("just", derivationlinechecker);
         derivationqueryhandler.setProperty("inst", ruleapplication);
         derivationqueryhandler.setProperty("edit", formulaentryfield);
         messagedialog.addHandler(derivationqueryhandler);
         if (!messagedialog.fillFieldsAndChoose(derivationlinechecker.presetAnswers, new EditableTextPane[]{formulaentryfield}, 0)) {
            if (derivationlinechecker.line.box.module.serialMode) {
               derivationlinechecker.line.box.module.complete = false;
               derivationlinechecker.reportError("dererr064");
               messagedialog.dispose();
               return false;
            }

            messagedialog.pack();
            messagedialog.showAt(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            messagedialog.dispose();
         }

         if (messagedialog.selectedButton != 0) {
            derivationlinechecker.reportError("dererr028");
            derivationlinechecker.line.box.module.abort(true);
            return false;
         } else {
            String s = LogicProgram.translateSymbols(formulaentryfield.getText(), SYMBOLS, maggie);
            if (!boundvariablemap.assign(BoundVariableMap.binderVariable(expression.getBinders(), 0), s)) {
               derivationlinechecker.reportError("dererr060", Message.params("variable name", "\\l" + s + "\\l"));
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean termSelectionQuery(
      DerivationLineChecker derivationlinechecker,
      RuleApplication ruleapplication,
      String s,
      Hashtable hashtable,
      TermOccurrenceSelector termoccurrenceselector,
      SubstitutionValuesPanel substitutionvaluespanel,
      Term[] aterm
   ) {
      if (derivationlinechecker.line.box.module.serialMode) {
         derivationlinechecker.line.box.module.complete = false;
         derivationlinechecker.reportError("dererr064");
         return false;
      } else {
         int i = substitutionvaluespanel.placeholderCount;
         Message message = DerivationMessage.get(s);
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new VerticalStackLayout());
         JPanel jpanel1 = new JPanel();
         jpanel1.setLayout(new BorderLayout());
         JPanel jpanel2 = new JPanel();
         jpanel2.setLayout(new BorderLayout());
         JPanel jpanel3 = new JPanel();
         jpanel3.setLayout(new BorderLayout());
         JPanel jpanel4 = new JPanel();
         jpanel4.setLayout(new BorderLayout());
         jpanel3.add(termoccurrenceselector);
         jpanel4.add(substitutionvaluespanel);
         jpanel1.add(new JScrollPane(jpanel3, 22, 31));
         jpanel2.add(new JScrollPane(jpanel4, 22, 31));
         jpanel.add(MultiLineLabel.create(DerivationMessage.format(message.text, hashtable, derivationlinechecker)));
         jpanel.add(jpanel1);
         jpanel.add(jpanel2);
         if (aterm != null) {
            Vector vector = termoccurrenceselector.listeners;
            int j = aterm.length;

            for (int k = 0; k < j; k++) {
               Term term = aterm[k];
               if (term != null) {
                  Enumeration enumeration = vector.elements();

                  while (enumeration.hasMoreElements()) {
                     SubstitutionListener substitutionlistener = (SubstitutionListener)enumeration.nextElement();
                     substitutionlistener.setPlaceholderValue(k, LogicProgram.translateSymbols(term.toString(), maggie, SYMBOLS));
                  }

                  termoccurrenceselector.selectedTerms[k] = term;
                  termoccurrenceselector.selectionCounts[k]++;
               }
            }
         }

         DerivationQueryHandler derivationqueryhandler = new DerivationQueryHandler(derivationlinechecker.line, message.buttons);
         derivationqueryhandler.setProperty("undo", termoccurrenceselector);
         derivationqueryhandler.setProperty("just", derivationlinechecker);
         derivationqueryhandler.setProperty("inst", ruleapplication);
         derivationqueryhandler.setProperty("edit", termoccurrenceselector);
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.line.box.module.frame, "Line " + derivationlinechecker.line.getLineNumber(), jpanel, derivationqueryhandler.labels
         );
         termoccurrenceselector.showCaretOnDialogFocus(messagedialog);
         termoccurrenceselector.ownerDialog = messagedialog;
         messagedialog.setSize(messagedialog.getPreferredSize());
         derivationlinechecker.line.focusEditor(true);
         Rectangle rectangle = LogicProgram.boundsRelativeTo(derivationlinechecker.line.annotationEditor, null);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.addHandler(derivationqueryhandler);
         messagedialog.showAt(new Point(rectangle.x, rectangle.y + rectangle.height));
         if (messagedialog.selectedButton != 0) {
            derivationlinechecker.reportError("dererr028");
            derivationlinechecker.line.box.module.abort(true);
            return false;
         } else {
            return true;
         }
      }
   }

   static boolean leibniz12TermQuery(DerivationLineChecker derivationlinechecker, RuleApplication ruleapplication) {
      if (derivationlinechecker.line.box.module.serialMode) {
         derivationlinechecker.line.box.module.complete = false;
         derivationlinechecker.reportError("dererr064");
         return false;
      } else {
         int i = ruleapplication.form.premises[ruleapplication.premiseOrder[0]].symbol.equals("=") ? 0 : 1;
         Expression expression = derivationlinechecker.getStackFormula(i - 2);
         Expression expression1 = derivationlinechecker.getStackFormula(-i - 1);
         String s = ruleapplication.form.premises[ruleapplication.premiseOrder[1 - i]].getChild(0).symbol;
         String s1 = ruleapplication.form.conclusion.getChild(0).symbol;
         int j = ruleapplication.form.premises[ruleapplication.premiseOrder[i]].getChild(0).symbol.equals(s) ? 0 : 1;
         Term term = (Term)expression.getChild(j);
         SubstitutionValuesPanel substitutionvaluespanel = new SubstitutionValuesPanel(250);
         SchemeInstantiation schemeinstantiation = ruleapplication.instantiation;
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
         Leibniz12TermSelector leibniz12termselector = new Leibniz12TermSelector(expression1, 250, derivationlinechecker.line.box.module.frame, hashtable);
         leibniz12termselector.addSubstitutionListener(substitutionvaluespanel);
         if (!termSelectionQuery(
            derivationlinechecker, ruleapplication, "derdlg012", hashtable, leibniz12termselector, substitutionvaluespanel, new Term[]{term}
         )) {
            return false;
         } else {
            String s2 = LogicProgram.translateSymbols(leibniz12termselector.getText(), SYMBOLS, maggie);
            if (!schemeinstantiation.addReplacement(schemeinstantiation.pendingLetters.elementAt(0).toString(), s2)) {
               derivationlinechecker.reportError(schemeinstantiation.errorId, schemeinstantiation.errorParams);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean leibniz34TermQuery(DerivationLineChecker derivationlinechecker, RuleApplication ruleapplication) {
      if (derivationlinechecker.line.box.module.serialMode) {
         derivationlinechecker.line.box.module.complete = false;
         derivationlinechecker.reportError("dererr064");
         return false;
      } else {
         int i = ruleapplication.form.premises[ruleapplication.premiseOrder[0]].symbol.equals("~") ? 0 : 1;
         Expression expression = derivationlinechecker.getStackFormula(-i - 1);
         Expression expression1 = derivationlinechecker.getStackFormula(i - 2);
         String s = ruleapplication.form.premises[ruleapplication.premiseOrder[1 - i]].getChild(0).symbol;
         String s1 = ruleapplication.form.premises[ruleapplication.premiseOrder[i]].getChild(0).symbol;
         SubstitutionValuesPanel substitutionvaluespanel = new SubstitutionValuesPanel(250);
         SchemeInstantiation schemeinstantiation = ruleapplication.instantiation;
         Hashtable hashtable = Message.params("rule term", s, "other term", s1);
         int j = schemeinstantiation.pendingLetters.size();
         SchematicLetter schematicletter = null;

         for (int k = 0; k < j; k++) {
            SchematicLetter schematicletter1 = (SchematicLetter)schemeinstantiation.pendingLetters.elementAt(k);
            if (schematicletter1 instanceof PredicateLetter) {
               schematicletter = schematicletter1;
               break;
            }
         }

         Leibniz34TermSelector leibniz34termselector = new Leibniz34TermSelector(
            expression, 250, derivationlinechecker.line.box.module.frame, hashtable, schematicletter
         );
         leibniz34termselector.addSubstitutionListener(substitutionvaluespanel);
         if (!termSelectionQuery(derivationlinechecker, ruleapplication, "derdlg013", hashtable, leibniz34termselector, substitutionvaluespanel, null)) {
            return false;
         } else {
            String s2 = LogicProgram.translateSymbols(leibniz34termselector.getText(), SYMBOLS, maggie);
            if (!schemeinstantiation.addReplacement(schematicletter.toString(), s2)) {
               derivationlinechecker.reportError(schemeinstantiation.errorId, schemeinstantiation.errorParams);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean eulerTermQuery(DerivationLineChecker derivationlinechecker, RuleApplication ruleapplication) {
      if (derivationlinechecker.line.box.module.serialMode) {
         derivationlinechecker.line.box.module.complete = false;
         derivationlinechecker.reportError("dererr064");
         return false;
      } else {
         SubstitutionValuesPanel substitutionvaluespanel = new SubstitutionValuesPanel(250);
         SchemeInstantiation schemeinstantiation = ruleapplication.instantiation;
         Expression expression = derivationlinechecker.getStackFormula(-1);
         Term term = (Term)expression.getChild(0);
         Term term1 = (Term)expression.getChild(1);
         Term term2 = (Term)derivationlinechecker.lineFormula.getChild(0);
         Term term3 = (Term)derivationlinechecker.lineFormula.getChild(1);
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
         EulerTermSelector eulertermselector = new EulerTermSelector(term2, 250, derivationlinechecker.line.box.module.frame, hashtable);
         eulertermselector.addSubstitutionListener(substitutionvaluespanel);
         if (!termSelectionQuery(derivationlinechecker, ruleapplication, "derdlg015", hashtable, eulertermselector, substitutionvaluespanel, new Term[]{term})) {
            return false;
         } else {
            SchematicLetter schematicletter = (SchematicLetter)schemeinstantiation.pendingLetters.elementAt(0);
            String s = LogicProgram.translateSymbols(eulertermselector.getText(), SYMBOLS, maggie);
            if (!schemeinstantiation.addReplacement(schematicletter.toString(), s)) {
               derivationlinechecker.reportError(schemeinstantiation.errorId, schemeinstantiation.errorParams);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean interchangeFormulaQuery(DerivationLineChecker derivationlinechecker, InterchangeJustification interchangejustification) {
      if (derivationlinechecker.matchLine && derivationlinechecker.lineFormula != null && !interchangejustification.pathChosen) {
         interchangejustification.path = derivationlinechecker.lineFormula.getDifferencePath(derivationlinechecker.getStackFormula(-1));
         if (interchangejustification.pathChosen = interchangejustification.path != null) {
            return true;
         }
      } else {
         interchangejustification.pathChosen = false;
      }

      if (derivationlinechecker.line.box.module.serialMode) {
         derivationlinechecker.line.box.module.complete = false;
         derivationlinechecker.reportError("dererr064");
         return false;
      } else {
         SizedPanel sizedpanel = new SizedPanel();
         Message message = DerivationMessage.get("derdlg020");
         sizedpanel.add(MultiLineLabel.create(DerivationMessage.format(message.text, null, derivationlinechecker)), "North");
         SizedPanel sizedpanel1 = new SizedPanel();
         sizedpanel1.setLimitWidth(LogicProgram.screenSize.width * 3 / 4);
         sizedpanel1.setMaximumOnly(true);
         sizedpanel1.setBorder(new BevelBorder(1));
         sizedpanel.add(sizedpanel1, "Center");
         Expression expression = derivationlinechecker.getStackFormula(-1);
         String s = LPDerivation.officialIE ? expression.formatFull(1) : expression.formatMinimal(1);
         FormulaEntryField formulaentryfield = new FormulaEntryField(
            LogicProgram.translateSymbols(s, maggie, SYMBOLS), derivationlinechecker.line.box.module.frame
         );
         formulaentryfield.setEditable(false);
         formulaentryfield.getCaret().setVisible(true);
         formulaentryfield.setWrapLines(true);
         formulaentryfield.setWrapWords(true);
         sizedpanel1.add(formulaentryfield, "North");
         DerivationQueryHandler derivationqueryhandler = new DerivationQueryHandler(derivationlinechecker.line, message.buttons);
         derivationqueryhandler.setProperty("just", derivationlinechecker);
         derivationqueryhandler.setProperty("edit", formulaentryfield);
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.line.box.module.frame, "Line " + derivationlinechecker.line.getLineNumber(), sizedpanel, derivationqueryhandler.labels
         );
         formulaentryfield.ownerDialog = messagedialog;
         formulaentryfield.showCaretOnDialogFocus(messagedialog);
         Rectangle rectangle = LogicProgram.boundsRelativeTo(derivationlinechecker.line.annotationEditor, null);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.addHandler(derivationqueryhandler);
         messagedialog.pack();
         messagedialog.showAt(new Point(rectangle.x, rectangle.y + rectangle.height));
         if (messagedialog.selectedButton != 0) {
            derivationlinechecker.reportError("dererr028");
            derivationlinechecker.line.box.module.abort(true);
            return false;
         } else {
            int[] aint = new int[]{formulaentryfield.getSelectionStart(), formulaentryfield.getSelectionEnd()};
            String s1 = formulaentryfield.getText();
            s1 = LogicProgram.translateSymbols(s1, SYMBOLS, maggie, aint);
            FormulaParseNode formulaparsenode = new FormulaParseNode(s1);
            FormulaParseNode formulaparsenode1 = formulaparsenode.findNodeContaining(aint[0], aint[1]);
            interchangejustification.path = formulaparsenode1.getPath();
            return true;
         }
      }
   }

   static boolean interchangeRuleQuery(DerivationLineChecker derivationlinechecker, InterchangeJustification interchangejustification) {
      if (derivationlinechecker.line.box.module.serialMode && derivationlinechecker.presetAnswers == null) {
         derivationlinechecker.line.box.module.complete = false;
         derivationlinechecker.reportError("dererr064");
         return false;
      } else {
         SizedPanel sizedpanel = new SizedPanel();
         Message message = DerivationMessage.get("derdlg021");
         sizedpanel.add(MultiLineLabel.create(DerivationMessage.format(message.text, null, derivationlinechecker)), "North");
         SizedPanel sizedpanel1 = new SizedPanel();
         sizedpanel1.setBorder(new BevelBorder(1));
         sizedpanel.add(sizedpanel1);
         EditableTextPane editabletextpane = new EditableTextPane();
         sizedpanel1.add(editabletextpane, "Center");
         DerivationQueryHandler derivationqueryhandler = new DerivationQueryHandler(derivationlinechecker.line, message.buttons);
         derivationqueryhandler.setProperty("just", derivationlinechecker);
         derivationqueryhandler.setProperty("edit", editabletextpane);
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.line.box.module.frame, "Line " + derivationlinechecker.line.getLineNumber(), sizedpanel, derivationqueryhandler.labels
         );
         Rectangle rectangle = LogicProgram.boundsRelativeTo(derivationlinechecker.line.annotationEditor, null);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.addHandler(derivationqueryhandler);
         if (!messagedialog.fillFieldsAndChoose(derivationlinechecker.presetAnswers, new EditableTextPane[]{editabletextpane}, 0)) {
            if (derivationlinechecker.line.box.module.serialMode) {
               derivationlinechecker.line.box.module.complete = false;
               derivationlinechecker.reportError("dererr064");
               messagedialog.dispose();
               return false;
            }

            messagedialog.pack();
            messagedialog.showAt(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            messagedialog.dispose();
         }

         if (messagedialog.selectedButton != 0) {
            derivationlinechecker.reportError("dererr028");
            derivationlinechecker.line.box.module.abort(true);
            return false;
         } else {
            return applyInterchangeRule(derivationlinechecker, interchangejustification, (Rule)derivationqueryhandler.getProperty("rule"));
         }
      }
   }

   static boolean applyInterchangeRule(DerivationLineChecker derivationlinechecker, InterchangeJustification interchangejustification, Rule rule) {
      return applyInterchangeRule(derivationlinechecker, interchangejustification, rule, null);
   }

   static boolean applyInterchangeRule(
      DerivationLineChecker derivationlinechecker, InterchangeJustification interchangejustification, Rule rule, SchematicRule schematicrule
   ) {
      boolean flag = !InterchangeJustification.isConditionSubstitutionClean(derivationlinechecker, interchangejustification.path, schematicrule);
      Vector vector = InterchangeJustification.findApplications(derivationlinechecker, interchangejustification.path, rule, schematicrule);
      boolean flag1 = vector == null;
      int i = !flag && !flag1 ? vector.size() : 0;
      if (i == 0) {
         if (interchangejustification.pathChosen) {
            while (interchangejustification.path.depth > 0) {
               interchangejustification.path.depth--;
               flag = !InterchangeJustification.isConditionSubstitutionClean(derivationlinechecker, interchangejustification.path, schematicrule);
               vector = InterchangeJustification.findApplications(derivationlinechecker, interchangejustification.path, rule, schematicrule);
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
            Message.putParam(hashtable, "inner rule", rule.name);
            Message.putParam(hashtable, "inner exp", "\\l" + derivationlinechecker.getStackFormula(-1).getSubexpression(interchangejustification.path) + "\\l");
            if (schematicrule != null) {
               Message.putParam(hashtable, "condition name", schematicrule.name);
            }

            String s;
            if (flag) {
               s = "dererr099";
            } else if (flag1) {
               s = schematicrule == null ? "dererr085" : "dererr095";
            } else {
               s = schematicrule == null ? "dererr096" : "dererr097";
            }

            derivationlinechecker.reportError(s, hashtable);
            return false;
         }
      }

      int j = derivationlinechecker.lineFormula == null ? chooseRuleInstance(derivationlinechecker, vector) : 0;
      if (j == -1) {
         if (derivationlinechecker.line.box.module.serialMode) {
            derivationlinechecker.line.box.module.complete = false;
            derivationlinechecker.reportError("dererr064");
         } else {
            derivationlinechecker.reportError("dererr028");
            derivationlinechecker.line.box.module.abort(true);
         }

         return false;
      } else {
         InterchangeJustification interchangejustification1 = (InterchangeJustification)vector.elementAt(j);
         if (interchangejustification1.ruleApplication != null) {
            if (!instanceSchemeQuery(derivationlinechecker, interchangejustification1)) {
               return false;
            }

            if (interchangejustification1.getResult(derivationlinechecker) == null) {
               return false;
            }
         }

         interchangejustification.reversed = interchangejustification1.reversed;
         interchangejustification.ruleApplication = interchangejustification1.ruleApplication;
         interchangejustification.equivalenceLine = interchangejustification1.equivalenceLine;
         interchangejustification.equivalencePremise = interchangejustification1.equivalencePremise;
         interchangejustification.instantiation = interchangejustification1.instantiation;
         interchangejustification.boundVariables = interchangejustification1.boundVariables;
         interchangejustification.conditionLine = interchangejustification1.conditionLine;
         interchangejustification.conditionPremise = interchangejustification1.conditionPremise;
         interchangejustification.conditionInstance = interchangejustification1.conditionInstance;
         interchangejustification.conditionReversed = interchangejustification1.conditionReversed;
         return true;
      }
   }

   static boolean cieRuleQuery(DerivationLineChecker derivationlinechecker, InterchangeJustification interchangejustification) {
      if (derivationlinechecker.line.box.module.serialMode && derivationlinechecker.presetAnswers == null) {
         derivationlinechecker.line.box.module.complete = false;
         derivationlinechecker.reportError("dererr064");
         return false;
      } else {
         SizedPanel sizedpanel = new SizedPanel();
         SizedPanel sizedpanel1 = new SizedPanel();
         Message message = DerivationMessage.get("derdlg022");
         sizedpanel1.add(MultiLineLabel.create(DerivationMessage.format(message.text, null, derivationlinechecker)), "North");
         SizedPanel sizedpanel2 = new SizedPanel();
         sizedpanel2.setBorder(new BevelBorder(1));
         sizedpanel1.add(sizedpanel2, "Center");
         EditableTextPane editabletextpane = new EditableTextPane();
         sizedpanel2.add(editabletextpane, "Center");
         sizedpanel.add(sizedpanel1, "North");
         SizedPanel sizedpanel3 = new SizedPanel();
         Message message1 = DerivationMessage.get("derdlg023");
         sizedpanel3.add(MultiLineLabel.create(DerivationMessage.format(message1.text, null, derivationlinechecker)), "North");
         SizedPanel sizedpanel4 = new SizedPanel();
         sizedpanel4.setBorder(new BevelBorder(1));
         sizedpanel3.add(sizedpanel4, "Center");
         EditableTextPane editabletextpane1 = new EditableTextPane();
         sizedpanel4.add(editabletextpane1, "Center");
         sizedpanel.add(sizedpanel3, "South");
         DerivationQueryHandler derivationqueryhandler = new DerivationQueryHandler(derivationlinechecker.line, message.buttons);
         derivationqueryhandler.setProperty("just", derivationlinechecker);
         derivationqueryhandler.setProperty("ruleEdit", editabletextpane);
         derivationqueryhandler.setProperty("condEdit", editabletextpane1);
         MessageDialog messagedialog = new MessageDialog(
            derivationlinechecker.line.box.module.frame, "Line " + derivationlinechecker.line.getLineNumber(), sizedpanel, derivationqueryhandler.labels
         );
         Rectangle rectangle = LogicProgram.boundsRelativeTo(derivationlinechecker.line.annotationEditor, null);
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.addHandler(derivationqueryhandler);
         if (!messagedialog.fillFieldsAndChoose(derivationlinechecker.presetAnswers, new EditableTextPane[]{editabletextpane, editabletextpane1}, 0)) {
            if (derivationlinechecker.line.box.module.serialMode) {
               derivationlinechecker.line.box.module.complete = false;
               derivationlinechecker.reportError("dererr064");
               messagedialog.dispose();
               return false;
            }

            messagedialog.pack();
            messagedialog.showAt(new Point(rectangle.x, rectangle.y + rectangle.height));
         } else {
            messagedialog.dispose();
         }

         if (messagedialog.selectedButton != 0) {
            derivationlinechecker.reportError("dererr028");
            derivationlinechecker.line.box.module.abort(true);
            return false;
         } else {
            return applyInterchangeRule(
               derivationlinechecker,
               interchangejustification,
               (Rule)derivationqueryhandler.getProperty("rule"),
               (SchematicRule)derivationqueryhandler.getProperty("condition")
            );
         }
      }
   }

   static String askProblemName(String s) {
      if (s == null) {
         s = "User";
      }

      LinePanel linepanel = new LinePanel();
      EditableTextPane editabletextpane = new EditableTextPane(s, 300);
      LogicLabel logiclabel = new LogicLabel("Please supply a name for this problem");
      ModuleFrame moduleframe = new ModuleFrame();
      editabletextpane.select(0, 2147483647);
      logiclabel.setFocusable(false);
      linepanel.setLayout(new VerticalStackLayout(0));
      linepanel.add(logiclabel);
      linepanel.add(editabletextpane);
      String[] astring = new String[]{"OK", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(moduleframe, "", linepanel, astring);
      messagedialog.setDefaultButtonIndex(0);
      editabletextpane.requestFocus();
      messagedialog.showAt(null);
      moduleframe.dispose();
      if (messagedialog.selectedButton != 0) {
         return null;
      } else {
         s = editabletextpane.getText();
         if ((s = s.trim()).equals("")) {
            Message message = Message.get("not006");
            MessageDialog.showMessage(Message.get("not006"), null, null, null);
            return null;
         } else if (LPDerivation.problems.getRecord(s) != null) {
            LogicProgram.showProblemError("not007", s);
            return null;
         } else {
            return s;
         }
      }
   }

   static void showStrategicAdvice(LPDerivation lpderivation) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      OutlineNode outlinenode = OutlineNode.readOutline(LogicProgram.openDataFile("tips", false), 20);
      if (outlinenode == null) {
         LogicProgram.showFileError("not001", "the derivation advice file");
      } else {
         JScrollPane jscrollpane = new JScrollPane();
         AdvicePanel advicepanel = new AdvicePanel(jscrollpane, outlinenode);
         jscrollpane.setHorizontalScrollBarPolicy(31);
         jscrollpane.setViewportView(advicepanel);
         advicepanel.add(outlinenode);
         advicepanel.setBackground(dialogWhite);
         jpanel.add(jscrollpane, "Center");
         MessageDialog messagedialog = new MessageDialog(lpderivation.frame, "Strategic Advice", jpanel, new String[]{"OK", "Print"});
         Container container = messagedialog.getParent();

         while (container != null && !(container instanceof Window)) {
            container = container.getParent();
         }

         if (container != null) {
            container.addComponentListener(advicepanel);
         }

         Dimension dimension = new Dimension(40 * LogicProgram.fontSize, 24 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
         if (messagedialog.selectedButton == 1) {
            TextFilePrintJob.printRecord(new TaggedRecord(LogicProgram.openDataFile("tips", false)), LPDerivation.printQueue);
         }

         lpderivation.requestFocus();
      }
   }

   static void showInferenceRules(LPDerivation lpderivation, BusyIndicator busyindicator) {
      if (lpderivation == null && LPDerivation.ruleQuery != null) {
         LPDerivation.ruleQuery.requestFocus();
      } else {
         if (busyindicator != null) {
            busyindicator.setBusy(true);
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
            object = new RuleListDialog(new ModuleFrame(), "Inference Rules", jpanel, new String[]{"OK"});
            LPDerivation.ruleQuery = (RuleListDialog)object;
         } else {
            object = new MessageDialog(lpderivation.frame, "Available Inference Rules", jpanel, new String[]{"OK"});
         }

         problemlistview.cancelButton = 0;
         int[] aint = new int[3];
         if (LPDerivation.userRules != null && LPDerivation.userRules.size() > 0) {
            Message message = DerivationMessage.get("dertxt001");
            if (message != null) {
               String[] astring = LogicProgram.splitLines(LogicProgram.expandEscapes(message.text));
               int i = astring.length;

               for (int j = 0; j < i; j++) {
                  LogicLabel logiclabel = new LogicLabel(astring[j], 2);
                  logiclabel.setForeground(dialogBlue);
                  problemlistview.addItem(logiclabel);
               }
            }

            Vector vector;
            if (lpderivation == null) {
               vector = LogicProgram.ruleTable.properties.getRulesWithConverse(LPDerivation.userRules);
            } else {
               vector = lpderivation.enabledRules(LPDerivation.userRules);
            }

            addRuleRows(problemlistview, aint, LPDerivation.userRules, vector);
         }

         Vector vector1;
         if (lpderivation == null) {
            vector1 = LogicProgram.ruleTable.properties.getRulesWithConverse(LogicProgram.ruleTable);
         } else {
            vector1 = lpderivation.enabledRules(LogicProgram.ruleTable);
         }

         addRuleRows(problemlistview, aint, LogicProgram.ruleTable, vector1);
         IntervalSet intervalset;
         if (lpderivation == null) {
            intervalset = LogicProgram.ruleTable.properties.getTheoremsWithConverse(LogicProgram.ruleTable.theorems);
         } else {
            intervalset = lpderivation.enabledTheorems(LogicProgram.ruleTable.theorems);
         }

         addTheoremRows(problemlistview, aint, LogicProgram.ruleTable.theorems, intervalset);
         int k = problemlistview.getComponentCount();
         FixedColumnLayout fixedcolumnlayout = new FixedColumnLayout(problemlistview, aint.length, aint);

         for (int l = 0; l < k; l++) {
            Component component = problemlistview.getComponent(l);
            if (component instanceof Container) {
               ((Container)component).setLayout(fixedcolumnlayout);
            }
         }

         CellPanel cellpanel = new CellPanel();
         cellpanel.setLayout(new BoxLayout(cellpanel, 2));
         CheckMarkLabel checkmarklabel = new CheckMarkLabel(true);
         cellpanel.add(checkmarklabel);
         LogicLabel logiclabel1;
         if (lpderivation == null) {
            logiclabel1 = new LogicLabel("usable with Interchange of Equivalents");
         } else {
            logiclabel1 = new LogicLabel("available for this derivation");
         }

         cellpanel.add(logiclabel1);
         jpanel.add(cellpanel, "South");
         if (busyindicator != null) {
            busyindicator.setBusy(false);
         }

         Dimension dimension = new Dimension(22 * LogicProgram.fontSize, 26 * LogicProgram.fontSize);
         ((MessageDialog)object).setSize(dimension);
         ((MessageDialog)object).setBoundsKey("derRules");
         if (lpderivation == null) {
            ((MessageDialog)object).showModeless(MessageDialog.centeredLocation(dimension), true);
         } else {
            ((MessageDialog)object).showAt(MessageDialog.centeredLocation(dimension), true);
            lpderivation.requestFocus();
         }
      }
   }

   static void addRuleRows(ProblemListView problemlistview, int[] aint, RuleTable ruletable, Vector vector) {
      Enumeration enumeration = ruletable.ruleNames.elements();

      while (enumeration.hasMoreElements()) {
         String s = (String)enumeration.nextElement();
         Vector vector1 = (Vector)ruletable.headings.get(s);
         CheckMarkLabel checkmarklabel = null;
         if (vector1 != null) {
            int i = vector1.size();

            for (int j = 0; j < i; j++) {
               LogicLabel logiclabel = new LogicLabel(LogicProgram.expandEscapes((String)vector1.elementAt(j)), 2);
               logiclabel.setForeground(dialogBlue);
               problemlistview.addItem(logiclabel);
            }
         }

         CellPanel cellpanel = new CellPanel();
         cellpanel.setLayout(new FlowLayout(0, 0, 0));
         int k = aint.length;
         if (k == 3) {
            checkmarklabel = new CheckMarkLabel(vector.contains(s));
            cellpanel.add(wrapInCell(checkmarklabel));
         }

         LogicLabel logiclabel1;
         cellpanel.add(wrapInCell(logiclabel1 = new LogicLabel(s)));
         logiclabel1.setPreferredSize(new Dimension(6 * LogicProgram.fontSize, logiclabel1.getPreferredSize().height));
         logiclabel1.setHorizontalAlignment(2);
         Rule rule = LPDerivation.getRule(s);
         LogicLabel logiclabel2;
         cellpanel.add(wrapInCell(logiclabel2 = LogicProgram.createFormulaLabel(rule == null ? "" : rule.format(" . ", " .: "))));
         problemlistview.addItem(cellpanel);
         int i1 = LogicProgram.fontSize * 5 / 14;
         int l;
         if (k == 3 && (l = checkmarklabel.getPreferredSize().width) > aint[k - 3]) {
            aint[k - 3] = l;
         }

         if ((l = logiclabel1.getPreferredSize().width + i1) > aint[k - 2]) {
            aint[k - 2] = l;
         }

         if ((l = logiclabel2.getPreferredSize().width) > aint[k - 1]) {
            aint[k - 1] = l;
         }
      }
   }

   static void addTheoremRows(ProblemListView problemlistview, int[] aint, TheoremTable theoremtable, IntervalSet intervalset) {
      Enumeration enumeration = theoremtable.theoremNumbers.elements();

      while (enumeration.hasMoreElements()) {
         Integer integer = (Integer)enumeration.nextElement();
         Vector vector = (Vector)theoremtable.headings.get(integer);
         CheckMarkLabel checkmarklabel = null;
         if (vector != null) {
            int i = vector.size();

            for (int j = 0; j < i; j++) {
               LogicLabel logiclabel = new LogicLabel(LogicProgram.expandEscapes((String)vector.elementAt(j)), 2);
               logiclabel.setForeground(dialogBlue);
               problemlistview.addItem(logiclabel);
            }
         }

         CellPanel cellpanel = new CellPanel();
         cellpanel.setLayout(new FlowLayout(0, 0, 0));
         int k = aint.length;
         if (k == 3) {
            checkmarklabel = new CheckMarkLabel(intervalset.contains(integer));
            cellpanel.add(wrapInCell(checkmarklabel));
         }

         LogicLabel logiclabel1;
         cellpanel.add(wrapInCell(logiclabel1 = new LogicLabel("T" + integer)));
         Theorem theorem = LogicProgram.getTheorem(integer);
         LogicLabel logiclabel2;
         cellpanel.add(wrapInCell(logiclabel2 = LogicProgram.createFormulaLabel(theorem == null ? "" : theorem.toString())));
         problemlistview.addItem(cellpanel);
         int i1 = LogicProgram.fontSize * 5 / 14;
         int l;
         if (k == 3 && (l = checkmarklabel.getPreferredSize().width) > aint[k - 3]) {
            aint[k - 3] = l;
         }

         if ((l = logiclabel1.getPreferredSize().width + i1) > aint[k - 2]) {
            aint[k - 2] = l;
         }

         if ((l = logiclabel2.getPreferredSize().width) > aint[k - 1]) {
            aint[k - 1] = l;
         }
      }
   }

   static CellPanel wrapInCell(Component component) {
      CellPanel cellpanel = new CellPanel(false);
      cellpanel.setLayout(new FlowLayout(0, 0, 0));
      cellpanel.add(component);
      return cellpanel;
   }
}
