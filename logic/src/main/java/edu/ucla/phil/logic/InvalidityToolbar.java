package edu.ucla.phil.logic;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class InvalidityToolbar extends CellPanel {
   LPInvalidation module;

   InvalidityToolbar(LPInvalidation lpinvalidation) {
      super(false);
      this.module = lpinvalidation;
   }

   static InvalidityToolbar create(LPInvalidation lpinvalidation) {
      InvalidityToolbar invaliditytoolbar = new InvalidityToolbar(lpinvalidation);
      invaliditytoolbar.buildButtons();
      return invaliditytoolbar;
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
            InvalidityDialogs.selectNextProblem(InvalidityToolbar.this.module);
         }
      };
      AbstractAction abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            InvalidityDialogs.chooseProblem(InvalidityToolbar.this.module);
         }
      };
      ActionButton actionbutton;
      jpanel.add(actionbutton = new ActionButton("Select", abstractaction1, abstractaction));
      actionbutton.setHelpText("Ctrl+O\nCtrl+N or Right Click to Select Next Problem");
      inputmap.put(KeyStroke.getKeyStroke(79, 128), "Select");
      actionmap.put("Select", abstractaction1);
      inputmap.put(KeyStroke.getKeyStroke(78, 128), "Select Next");
      actionmap.put("Select Next", abstractaction);
      if (!LPInvalidation.noUser) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               InvalidityDialogs.createUserProblem(InvalidityToolbar.this.module);
            }
         };
         if (LogicProgram.getCredentials("workEntry") != null) {
            abstractaction = new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent actionevent) {
                  if (LogicProgram.getCredentials("workEntry") != null && UserSetup.hasAccess("workEntry", "instructor")) {
                     InvalidityDialogs.enterSubmittedProblem(InvalidityToolbar.this.module);
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
            if (InvalidityToolbar.this.module.checkDisabled) {
               MessageDialog.showMessage("Feature Disabled", "Checking is disabled for this problem.", null, null);
            } else {
               InvalidityToolbar.this.module.evaluate();
            }

            InvalidityToolbar.this.module.requestFocus();
         }
      };
      jpanel.add(actionbutton = new ActionButton("Check", abstractaction1));
      actionbutton.setHelpText("Ctrl+K");
      inputmap.put(KeyStroke.getKeyStroke(75, 128), "Check");
      actionmap.put("Check", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            InvalidityToolbar.this.module.saveRenamed(InvalidityToolbar.this.module.saveProblem());
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s1 = InvalidityToolbar.this.module.getChangedProblem();
            if (s1 == null) {
               LPInvalidation.saveProblems();
            } else {
               InvalidityToolbar.this.module.saveProblems(s1);
            }

            InvalidityToolbar.this.module.requestFocus();
         }
      };
      jpanel.add(actionbutton = new ActionButton("Save", abstractaction1, abstractaction));
      actionbutton.setHelpText("Ctrl+S\nRight Click to Save with a different name");
      inputmap.put(KeyStroke.getKeyStroke(83, 128), "Save");
      actionmap.put("Save", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            InvalidityDialogs.deleteMultipleProblems(InvalidityToolbar.this.module);
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (LPInvalidation.isExercise(InvalidityToolbar.this.module.title)) {
               InvalidityDialogs.deleteWork(InvalidityToolbar.this.module, null);
            } else {
               InvalidityDialogs.deleteProblemOrWork(InvalidityToolbar.this.module, null);
            }

            InvalidityToolbar.this.module.requestFocus();
         }
      };
      jpanel.add(actionbutton = new ActionButton("Delete", abstractaction1, abstractaction));
      actionbutton.setHelpText("Right Click to Delete multiple problems");
      if (!LogicProgram.noNetwork) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               InvalidityToolbar.this.module.submitProblems();
               InvalidityToolbar.this.module.requestFocus();
            }
         };
         jpanel.add(actionbutton = new ActionButton("Submit", abstractaction1));
         actionbutton.setHelpText("Submit");
      }

      if (LogicProgram.printingEnabled && !LPInvalidation.submitExam) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               InvalidityResultsPage.printResults(InvalidityDialogs.choosePrintProblems(InvalidityToolbar.this.module));
               InvalidityToolbar.this.module.requestFocus();
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
            InvalidityToolbar.this.module.frame.closeModule(false);
         }
      };
      jpanel.add(actionbutton = new ActionButton("Close", abstractaction1));
      actionbutton.setHelpText("Alt+F4");
      inputmap.put(KeyStroke.getKeyStroke(115, 512), "Close");
      actionmap.put("Close", abstractaction1);
      JPanel jpanel1 = new JPanel();
      jpanel1.setLayout(new AlignedFlowLayout(1, 0, i / 4, i / 8));
      this.add(jpanel1);
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s1 = LogicProgram.getLink("feedback");
            DesktopLauncher.browse(s1);
         }
      };
      jpanel1.add(new ActionButton("Feedback", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s1 = LogicProgram.getLink("invHelp");
            DesktopLauncher.open(LogicProgram.configDir, s1);
         }
      };
      jpanel1.add(new ActionButton("Help", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            boolean flag = false;
            if ((!flag || InvalidityToolbar.this.module.size != 0) && InvalidityToolbar.this.module.statement != null) {
               BusyIndicator busyindicator = new BusyIndicator(InvalidityToolbar.this, true);
               InvalidityToolbar.this.module.openDerivation(flag, busyindicator);
            } else {
               MessageDialog.showMessage(InvalidityMessage.get("invnot001"), null, null, null);
            }
         }
      };
      jpanel1.add(new ActionButton("Derivation", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            boolean flag = false;
            if (!flag || InvalidityToolbar.this.module.size != 0 && InvalidityToolbar.this.module.statement != null) {
               BusyIndicator busyindicator = new BusyIndicator(InvalidityToolbar.this, true);
               InvalidityToolbar.this.module.openTruthAnalysis(flag, busyindicator);
            } else {
               MessageDialog.showMessage(InvalidityMessage.get("invnot003"), null, null, null);
            }
         }
      };
      jpanel1.add(new ActionButton("Truth Table", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            InvalidityToolbar.this.module.copyStatement();
         }
      };
      jpanel1.add(actionbutton = new ActionButton("Copy", abstractaction1));
      actionbutton.setHelpText("Copy problem to workspace.");
      final String s = LogicProgram.variableLetter(0);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (InvalidityToolbar.this.module.expandOff) {
               MessageDialog.showMessage(InvalidityMessage.get("invnot004"), null, null, null);
            } else if (InvalidityToolbar.this.module.expandAll) {
               InvalidityToolbar.this.module.expand(s, true);
            } else {
               InvalidityToolbar.this.module.expand(s, false);
            }
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (InvalidityToolbar.this.module.expandOff) {
               MessageDialog.showMessage(InvalidityMessage.get("invnot004"), null, null, null);
            } else {
               InvalidityToolbar.this.module.expand(s, false);
            }
         }
      };
      jpanel1.add(actionbutton = new ActionButton("Expand", abstractaction1, abstractaction));
      actionbutton.setHelpText("Expand outermost selected Quantifier.");
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s1 = LogicProgram.getLink("invRefs");
            DesktopLauncher.open(LogicProgram.configDir, s1);
         }
      };
      jpanel1.add(new ActionButton("References", abstractaction1));
      if (ServerConnection.adminInstall && !LogicProgram.noNetwork && ServerConnection.uploadUrl != null) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               InvalidityToolbar.this.module.uploadProblems();
               InvalidityToolbar.this.module.requestFocus();
            }
         };
         jpanel1.add(actionbutton = new ActionButton("Upload", abstractaction1));
         actionbutton.setHelpText("Upload");
      }
   }
}
