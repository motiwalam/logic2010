package edu.ucla.phil.logic;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class SymbolizationToolbar extends CellPanel {
   LPSymbolizer symbolizer;

   SymbolizationToolbar(LPSymbolizer lpsymbolizer) {
      super(false);
      this.symbolizer = lpsymbolizer;
   }

   static SymbolizationToolbar create(LPSymbolizer lpsymbolizer) {
      SymbolizationToolbar symbolizationtoolbar = new SymbolizationToolbar(lpsymbolizer);
      symbolizationtoolbar.build();
      return symbolizationtoolbar;
   }

   void build() {
      this.setFont(new Font("Dialog", 0, 12));
      this.setLayout(new VerticalStackLayout(1, 0));
      InputMap inputmap = this.symbolizer.getInputMap(1);
      ActionMap actionmap = this.symbolizer.getActionMap();
      int i = this.symbolizer.fontSize * 10 / 7;
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new AlignedFlowLayout(1, 0, i / 4, i / 8));
      this.add(jpanel);
      AbstractAction abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            SymbolizationDialogs.selectNextProblem(SymbolizationToolbar.this.symbolizer);
         }
      };
      AbstractAction abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            SymbolizationDialogs.chooseProblem(SymbolizationToolbar.this.symbolizer);
         }
      };
      ActionButton actionbutton;
      jpanel.add(actionbutton = new ActionButton("Select", abstractaction1, abstractaction));
      actionbutton.setHelpText("Ctrl+O\nCtrl+N or Right Click to Select Next Problem");
      inputmap.put(KeyStroke.getKeyStroke(79, 128), "Select");
      actionmap.put("Select", abstractaction1);
      inputmap.put(KeyStroke.getKeyStroke(78, 128), "Select Next");
      actionmap.put("Select Next", abstractaction);
      if (!LPSymbolizer.noUser) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               SymbolizationDialogs.createUserProblem(SymbolizationToolbar.this.symbolizer);
            }
         };
         if (LogicProgram.getCredentials("workEntry") != null) {
            abstractaction = new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent actionevent) {
                  if (LogicProgram.getCredentials("workEntry") != null && UserSetup.hasAccess("workEntry", "instructor")) {
                     SymbolizationDialogs.enterSubmittedProblem(SymbolizationToolbar.this.symbolizer);
                  }
               }
            };
            jpanel.add(actionbutton = new ActionButton("User", abstractaction1, abstractaction));
            actionbutton.setHelpText("Create a User Problem\nRight Click to Enter a Submitted Problem");
         } else {
            jpanel.add(actionbutton = new ActionButton("User", abstractaction1));
            actionbutton.setHelpText("Create a User Problem");
         }
      }

      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            SymbolizationDialogs.enterDirectSymbolization(SymbolizationToolbar.this.symbolizer);
         }
      };
      jpanel.add(actionbutton = new ActionButton("Direct", abstractaction1));
      actionbutton.setHelpText("Enter Expression Directly");
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (SymbolizationToolbar.this.symbolizer.checkDisabled) {
               MessageDialog.showMessage("Feature Disabled", "Checking is disabled for this problem.", null, null);
            } else {
               SymbolizationToolbar.this.symbolizer.checkProblem();
            }

            SymbolizationToolbar.this.symbolizer.requestFocus();
         }
      };
      jpanel.add(actionbutton = new ActionButton("Check", abstractaction1));
      actionbutton.setHelpText("Ctrl+K");
      inputmap.put(KeyStroke.getKeyStroke(75, 128), "Check");
      actionmap.put("Check", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            SymbolizationToolbar.this.symbolizer.saveRenamed(SymbolizationToolbar.this.symbolizer.saveProblem());
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = SymbolizationToolbar.this.symbolizer.getChangedProblem();
            if (s == null) {
               LPSymbolizer.saveProblems(SymbolizationToolbar.this.symbolizer.problemIndex);
            } else {
               SymbolizationToolbar.this.symbolizer.saveProblems(s);
            }

            SymbolizationToolbar.this.symbolizer.requestFocus();
         }
      };
      jpanel.add(actionbutton = new ActionButton("Save", abstractaction1, abstractaction));
      actionbutton.setHelpText("Ctrl+S\nRight Click to Save with a different name");
      inputmap.put(KeyStroke.getKeyStroke(83, 128), "Save");
      actionmap.put("Save", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            SymbolizationDialogs.deleteProblems(SymbolizationToolbar.this.symbolizer);
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            boolean flag;
            if (LPSymbolizer.isExercise(SymbolizationToolbar.this.symbolizer.problem.problemName)) {
               flag = SymbolizationDialogs.deleteExerciseWork(SymbolizationToolbar.this.symbolizer, null);
            } else {
               flag = SymbolizationDialogs.deleteProblem(SymbolizationToolbar.this.symbolizer, null);
            }

            if (flag) {
               SymbolizationToolbar.this.symbolizer.problem.textPanel.requestFocus();
            } else if (SymbolizationToolbar.this.symbolizer.lastFocus != null) {
               SymbolizationToolbar.this.symbolizer.lastFocus.requestFocus();
            }
         }
      };
      jpanel.add(actionbutton = new ActionButton("Delete", abstractaction1, abstractaction));
      actionbutton.setHelpText("Right Click to Delete multiple problems");
      if (!LogicProgram.noNetwork) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               SymbolizationToolbar.this.symbolizer.submitProblems();
               SymbolizationToolbar.this.symbolizer.requestFocus();
            }
         };
         jpanel.add(actionbutton = new ActionButton("Submit", abstractaction1));
         actionbutton.setHelpText("Submit");
      }

      if (LogicProgram.printingEnabled && !LPSymbolizer.submitExam) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               SymbolizationProblemPrinter.printSelected(SymbolizationDialogs.chooseProblemsToPrint(SymbolizationToolbar.this.symbolizer));
               SymbolizationToolbar.this.symbolizer.requestFocus();
            }
         };
         if (LogicProgram.getCredentials("symAnswerPrint") != null) {
            abstractaction = new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent actionevent) {
                  if (LogicProgram.getCredentials("symAnswerPrint") != null && UserSetup.hasAccess("symAnswerPrint", "instructor")) {
                     AnswerPrinter.printSelected(SymbolizationDialogs.chooseAnswersToPrint(SymbolizationToolbar.this.symbolizer.frame));
                  }

                  SymbolizationToolbar.this.symbolizer.requestFocus();
               }
            };
            jpanel.add(actionbutton = new ActionButton("Print", abstractaction1, abstractaction));
            actionbutton.setHelpText("Ctrl+P\nRight Click to Print Answers to Problems");
         } else {
            jpanel.add(actionbutton = new ActionButton("Print", abstractaction1));
            actionbutton.setHelpText("Ctrl+P");
         }

         inputmap.put(KeyStroke.getKeyStroke(80, 128), "Print");
         actionmap.put("Print", abstractaction1);
      }

      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            LogicProgram.mainMenu.showInFront();
         }
      };
      jpanel.add(new ActionButton("Menu", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            SymbolizationToolbar.this.symbolizer.frame.closeModule(false);
         }
      };
      jpanel.add(actionbutton = new ActionButton("Close", abstractaction1));
      actionbutton.setHelpText("Alt+F4");
      inputmap.put(KeyStroke.getKeyStroke(115, 512), "Close");
      actionmap.put("Close", abstractaction1);
      JPanel jpanel1 = new JPanel();
      jpanel1.setLayout(new AlignedFlowLayout(1, 0, i / 4, i / 8));
      this.add(jpanel1);
      if (ServerConnection.adminInstall) {
         ChoiceListener choicelistener = new ChoiceListener() {
            @Override
            public void choiceChanged(ChoiceButton choicebutton1, int i0, int j) {
               String s = choicebutton1.getChoice(j);
               if (s != null) {
                  if (s.equalsIgnoreCase("Statement")) {
                     SymbolizationDialogs.editStatement(SymbolizationToolbar.this.symbolizer);
                  } else if (s.equalsIgnoreCase("Scheme")) {
                     SymbolizationDialogs.editScheme(SymbolizationToolbar.this.symbolizer);
                  } else if (s.equalsIgnoreCase("Answers")) {
                     SymbolizationDialogs.showAnswerManager(SymbolizationToolbar.this.symbolizer);
                  }

                  choicebutton1.setSelectedIndex(2, false);
               }
            }
         };
         ChoiceButton choicebutton = new ChoiceButton(new String[]{"Statement", "Scheme", "Answers"}, "Edit", 2, false);
         choicebutton.setShowPlaceholder(true);
         choicebutton.addChoiceListener(choicelistener);
         jpanel1.add(choicebutton);
      }

      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.getLink("feedback");
            DesktopLauncher.browse(s);
         }
      };
      jpanel1.add(new ActionButton("Feedback", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.getLink("symHelp");
            DesktopLauncher.open(LogicProgram.configDir, s);
         }
      };
      jpanel1.add(new ActionButton("Help", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.getLink("symSamp");
            DesktopLauncher.open(LogicProgram.configDir, s);
         }
      };
      jpanel1.add(new ActionButton("Examples", abstractaction1));
      if (ServerConnection.adminInstall && !LogicProgram.noNetwork && ServerConnection.uploadUrl != null) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               SymbolizationToolbar.this.symbolizer.uploadProblems();
               SymbolizationToolbar.this.symbolizer.requestFocus();
            }
         };
         jpanel1.add(actionbutton = new ActionButton("Upload", abstractaction1));
         actionbutton.setHelpText("Upload");
      }
   }
}
