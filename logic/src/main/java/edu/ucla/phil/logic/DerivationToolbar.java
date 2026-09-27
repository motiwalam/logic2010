package edu.ucla.phil.logic;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class DerivationToolbar extends CellPanel {
   LPDerivation module;

   DerivationToolbar(LPDerivation lpderivation) {
      super(false);
      this.module = lpderivation;
   }

   static DerivationToolbar create(LPDerivation lpderivation) {
      DerivationToolbar derivationtoolbar = new DerivationToolbar(lpderivation);
      derivationtoolbar.buildButtons();
      return derivationtoolbar;
   }

   void buildButtons() {
      this.setFont(new Font("Dialog", 0, 12));
      this.setLayout(new VerticalStackLayout(1, 0));
      InputMap inputmap = this.module.getInputMap(1);
      ActionMap actionmap = this.module.getActionMap();
      int i = this.module.fontSize * 10 / 7;
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new AlignedFlowLayout(1, 0, i / 4, i / 8));
      this.add(jpanel);
      AbstractAction abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            DerivationDialogs.openNextProblem(DerivationToolbar.this.module);
         }
      };
      AbstractAction abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            DerivationDialogs.chooseProblem(DerivationToolbar.this.module);
         }
      };
      ActionButton actionbutton;
      jpanel.add(actionbutton = new ActionButton("Select", abstractaction1, abstractaction));
      actionbutton.setHelpText("Ctrl+O\nCtrl+N or Right Click to Select Next Problem");
      inputmap.put(KeyStroke.getKeyStroke(79, 128), "Select");
      actionmap.put("Select", abstractaction1);
      inputmap.put(KeyStroke.getKeyStroke(78, 128), "Select Next");
      actionmap.put("Select Next", abstractaction);
      if (!LPDerivation.noUser) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               DerivationDialogs.enterUserProblem(DerivationToolbar.this.module);
            }
         };
         if (LogicProgram.getCredentials("workEntry") != null) {
            abstractaction = new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent actionevent) {
                  if (LogicProgram.getCredentials("workEntry") != null && UserSetup.hasAccess("workEntry", "instructor")) {
                     DerivationDialogs.enterSubmittedProblem(DerivationToolbar.this.module);
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

      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            BusyIndicator busyindicator = new BusyIndicator(DerivationToolbar.this.module);
            DerivationDialogs.showInferenceRules(DerivationToolbar.this.module, busyindicator);
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            BusyIndicator busyindicator = new BusyIndicator(DerivationToolbar.this.module);
            DerivationDialogs.showInferenceRules(null, busyindicator);
         }
      };
      jpanel.add(actionbutton = new ActionButton("Rules", abstractaction1, abstractaction));
      actionbutton.setHelpText("Display All Rules\nRight Click to Display Available Rules");
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (DerivationToolbar.this.module.checkDisabled) {
               MessageDialog.showMessage("Feature Disabled", "Checking is disabled for this problem.", null, null);
            } else {
               DerivationToolbar.this.module.checkProblem();
            }

            DerivationToolbar.this.module.requestFocus();
         }
      };
      jpanel.add(actionbutton = new ActionButton("Check", abstractaction1));
      actionbutton.setHelpText("Ctrl+K");
      inputmap.put(KeyStroke.getKeyStroke(75, 128), "Check");
      actionmap.put("Check", abstractaction1);
      final ActionButton stackbutton = new ActionButton("Stack");
      stackbutton.primaryAction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            DerivationToolbar.this.module.toggleStackView();
            stackbutton.setText(DerivationToolbar.this.module.stackView.isVisible() ? "Hide Stack" : "Stack");
            DerivationToolbar.this.module.requestFocus();
         }
      };
      jpanel.add(stackbutton);
      stackbutton.setHelpText("Show the formulas on the stack of the justification at the cursor");
      final ActionButton rulesbutton = new ActionButton("Applicable");
      rulesbutton.primaryAction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            DerivationToolbar.this.module.toggleRulesView();
            rulesbutton.setText(DerivationToolbar.this.module.rulesView.isVisible() ? "Hide Applicable" : "Applicable");
            DerivationToolbar.this.module.requestFocus();
         }
      };
      jpanel.add(rulesbutton);
      rulesbutton.setHelpText("Show the rules that apply to the stack of the justification at the cursor, and what they give");
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            DerivationToolbar.this.module.saveRenamed(DerivationToolbar.this.module.saveProblem());
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = DerivationToolbar.this.module.getChangedProblem();
            if (s == null) {
               LPDerivation.saveProblems(DerivationToolbar.this.module.problemIndex);
            } else {
               DerivationToolbar.this.module.saveProblems(s);
            }

            DerivationToolbar.this.module.requestFocus();
         }
      };
      jpanel.add(actionbutton = new ActionButton("Save", abstractaction1, abstractaction));
      actionbutton.setHelpText("Ctrl+S\nRight Click to Save with a different name");
      inputmap.put(KeyStroke.getKeyStroke(83, 128), "Save");
      actionmap.put("Save", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            DerivationDialogs.deleteProblemsDialog(DerivationToolbar.this.module);
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (LPDerivation.isExercise(DerivationToolbar.this.module.problemTitle)) {
               DerivationDialogs.confirmDeleteWork(DerivationToolbar.this.module, null);
            } else {
               DerivationDialogs.confirmDeleteProblem(DerivationToolbar.this.module, null);
            }

            DerivationToolbar.this.module.requestFocus();
         }
      };
      jpanel.add(actionbutton = new ActionButton("Delete", abstractaction1, abstractaction));
      actionbutton.setHelpText("Right Click to Delete multiple problems");
      if (!LogicProgram.noNetwork) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               DerivationToolbar.this.module.submitProblems();
               DerivationToolbar.this.module.requestFocus();
            }
         };
         jpanel.add(actionbutton = new ActionButton("Submit", abstractaction1));
         actionbutton.setHelpText("Submit");
      }

      if (LogicProgram.printingEnabled && !LPDerivation.submitExam) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               DerivationProblemsPage.printProblems(DerivationDialogs.chooseProblemsToPrint(DerivationToolbar.this.module));
               DerivationToolbar.this.module.requestFocus();
            }
         };
         jpanel.add(actionbutton = new ActionButton("Print", abstractaction1));
         actionbutton.setHelpText("Ctrl+P");
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
            DerivationToolbar.this.module.frame.closeModule(false);
         }
      };
      jpanel.add(actionbutton = new ActionButton("Close", abstractaction1));
      actionbutton.setHelpText("Alt+F4");
      inputmap.put(KeyStroke.getKeyStroke(115, 512), "Close");
      actionmap.put("Close", abstractaction1);
      JPanel jpanel1 = new JPanel();
      jpanel.setLayout(new AlignedFlowLayout(1, 0, i / 4, i / 8));
      this.add(jpanel1);
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
            String s = LogicProgram.getLink("derStart");
            DesktopLauncher.open(LogicProgram.configDir, s);
         }
      };
      jpanel1.add(new ActionButton("Starting", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.getLink("derHelp");
            DesktopLauncher.open(LogicProgram.configDir, s);
         }
      };
      jpanel1.add(new ActionButton("Help", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.getLink("derFAQ");
            DesktopLauncher.open(LogicProgram.configDir, s);
         }
      };
      jpanel1.add(new ActionButton("FAQ", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.getLink("derTips");
            DesktopLauncher.open(LogicProgram.configDir, s);
         }
      };
      jpanel1.add(new ActionButton("Advice", abstractaction1));
      if (ServerConnection.adminInstall && !LogicProgram.noNetwork && ServerConnection.uploadUrl != null) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               DerivationToolbar.this.module.uploadProblems();
               DerivationToolbar.this.module.requestFocus();
            }
         };
         jpanel1.add(actionbutton = new ActionButton("Upload", abstractaction1));
         actionbutton.setHelpText("Upload");
      }
   }
}
