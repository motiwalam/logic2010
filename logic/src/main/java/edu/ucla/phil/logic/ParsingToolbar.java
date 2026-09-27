package edu.ucla.phil.logic;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class ParsingToolbar extends CellPanel {
   LPParsing module;

   ParsingToolbar(LPParsing lpparsing) {
      super(false);
      this.module = lpparsing;
   }

   static ParsingToolbar create(LPParsing lpparsing) {
      ParsingToolbar parsingtoolbar = new ParsingToolbar(lpparsing);
      parsingtoolbar.buildButtons();
      return parsingtoolbar;
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
            ParsingDialogs.selectNextProblem(ParsingToolbar.this.module);
         }
      };
      AbstractAction abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            ParsingDialogs.selectProblem(ParsingToolbar.this.module);
         }
      };
      ActionButton actionbutton;
      jpanel.add(actionbutton = new ActionButton("Select", abstractaction1, abstractaction));
      actionbutton.setHelpText("Ctrl+O\nCtrl+N or Right Click to Select Next Problem");
      inputmap.put(KeyStroke.getKeyStroke(79, 128), "Select");
      actionmap.put("Select", abstractaction1);
      inputmap.put(KeyStroke.getKeyStroke(78, 128), "Select Next");
      actionmap.put("Select Next", abstractaction);
      if (!LPParsing.noUser) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               ParsingDialogs.createUserProblem(ParsingToolbar.this.module);
            }
         };
         if (LogicProgram.getCredentials("workEntry") != null) {
            abstractaction = new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent actionevent) {
                  if (LogicProgram.getCredentials("workEntry") != null && UserSetup.hasAccess("workEntry", "instructor")) {
                     ParsingDialogs.enterSubmittedProblem(ParsingToolbar.this.module);
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
            ParsingToolbar.this.module.check();
         }
      };
      jpanel.add(actionbutton = new ActionButton("Check", abstractaction1));
      actionbutton.setHelpText("Ctrl+K");
      inputmap.put(KeyStroke.getKeyStroke(75, 128), "Check");
      actionmap.put("Check", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            ParsingToolbar.this.module.saveRenamed(ParsingToolbar.this.module.saveProblem());
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = ParsingToolbar.this.module.getChangedProblem();
            if (s == null) {
               LPParsing.saveProblems();
            } else {
               ParsingToolbar.this.module.saveProblems(s);
            }

            ParsingToolbar.this.module.requestFocus();
         }
      };
      jpanel.add(actionbutton = new ActionButton("Save", abstractaction1, abstractaction));
      actionbutton.setHelpText("Ctrl+S\nRight Click to Save with a different name");
      inputmap.put(KeyStroke.getKeyStroke(83, 128), "Save");
      actionmap.put("Save", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            ParsingDialogs.deleteProblems(ParsingToolbar.this.module);
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (LPParsing.isExercise(ParsingToolbar.this.module.problem.problemName)) {
               ParsingDialogs.deleteWork(ParsingToolbar.this.module, null);
            } else {
               ParsingDialogs.deleteProblemOrWork(ParsingToolbar.this.module, null);
            }

            ParsingToolbar.this.module.requestFocus();
         }
      };
      jpanel.add(actionbutton = new ActionButton("Delete", abstractaction1, abstractaction));
      actionbutton.setHelpText("Right Click to Delete multiple problems");
      if (!LogicProgram.noNetwork) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               ParsingToolbar.this.module.submitProblems();
               ParsingToolbar.this.module.requestFocus();
            }
         };
         jpanel.add(actionbutton = new ActionButton("Submit", abstractaction1));
         actionbutton.setHelpText("Submit");
      }

      if (LogicProgram.printingEnabled && !LPParsing.submitExam) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               ParsingResultsPage.printResults(ParsingDialogs.printProblems(ParsingToolbar.this.module));
               ParsingToolbar.this.module.requestFocus();
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
            ParsingToolbar.this.module.frame.closeModule(false);
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
            String s = LogicProgram.getLink("feedback");
            DesktopLauncher.browse(s);
         }
      };
      jpanel1.add(new ActionButton("Feedback", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.getLink("parHelp");
            DesktopLauncher.open(LogicProgram.configDir, s);
         }
      };
      jpanel1.add(new ActionButton("Help", abstractaction1));
      if (ServerConnection.adminInstall && !LogicProgram.noNetwork && ServerConnection.uploadUrl != null) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               ParsingToolbar.this.module.uploadProblems();
               ParsingToolbar.this.module.requestFocus();
            }
         };
         jpanel1.add(actionbutton = new ActionButton("Upload", abstractaction1));
         actionbutton.setHelpText("Upload");
      }
   }
}
