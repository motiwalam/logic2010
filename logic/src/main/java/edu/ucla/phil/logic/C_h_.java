package edu.ucla.phil.logic;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class C_h_ extends CellPanel {
   LPRecognition f1136;
   C__D f1137;

   C_h_(LPRecognition lprecognition) {
      super(false);
      this.f1136 = lprecognition;
   }

   static C_h_ m1834(LPRecognition lprecognition) {
      C_h_ c_h_ = new C_h_(lprecognition);
      c_h_.m1835();
      return c_h_;
   }

   void m1835() {
      this.setFont(new Font("Dialog", 0, 12));
      this.setLayout(new C_m_A(1, 0));
      InputMap inputmap = this.f1136.getInputMap(1);
      ActionMap actionmap = this.f1136.getActionMap();
      int i = this.f1136.fontSize * 10 / 7;
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new C_b_C(1, 0, i / 4, i / 8));
      this.add(jpanel);
      AbstractAction abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_EC.m504(C_h_.this.f1136);
         }
      };
      AbstractAction abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_EC.m505(C_h_.this.f1136);
         }
      };
      C__D c__d;
      jpanel.add(c__d = new C__D("Select", abstractaction1, abstractaction));
      c__d.m1583("Ctrl+O\nCtrl+N or Right Click to Select Next Problem");
      inputmap.put(KeyStroke.getKeyStroke(79, 128), "Select");
      actionmap.put("Select", abstractaction1);
      inputmap.put(KeyStroke.getKeyStroke(78, 128), "Select Next");
      actionmap.put("Select Next", abstractaction);
      if (!LPRecognition.noUser) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_EC.m510(C_h_.this.f1136);
            }
         };
         if (LogicProgram.getCredentials("workEntry") != null) {
            abstractaction = new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent actionevent) {
                  if (LogicProgram.getCredentials("workEntry") != null && UserSetup.m2101("workEntry", "instructor")) {
                     C_EC.m507(C_h_.this.f1136);
                  }
               }
            };
            jpanel.add(c__d = new C__D("User", abstractaction1, abstractaction));
            c__d.m1583("Create a User Problem\nRight Click to Enter a Submitted Problem");
         } else {
            jpanel.add(c__d = new C__D("User", abstractaction1));
            c__d.m1583("Create a User Problem");
         }
      }

      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (C_h_.this.f1136.checkDisabled) {
               MessageDialog.showMessage("Feature Disabled", "Checking is disabled for this problem.", null, null);
            } else {
               C_h_.this.f1136.checkProblem();
            }

            C_h_.this.f1136.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Check", abstractaction1));
      c__d.m1583("Ctrl+K");
      inputmap.put(KeyStroke.getKeyStroke(75, 128), "Check");
      actionmap.put("Check", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_h_.this.f1136.saveRenamed(C_h_.this.f1136.saveProblem());
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = C_h_.this.f1136.getChangedProblem();
            if (s == null) {
               LPRecognition.saveProblems();
            } else {
               C_h_.this.f1136.saveProblems(s);
            }

            C_h_.this.f1136.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Save", abstractaction1, abstractaction));
      c__d.m1583("Ctrl+S\nRight Click to Save with a different name");
      inputmap.put(KeyStroke.getKeyStroke(83, 128), "Save");
      actionmap.put("Save", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_EC.m509(C_h_.this.f1136);
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (LPRecognition.isExercise(C_h_.this.f1136.problem.f123)) {
               C_EC.m502(C_h_.this.f1136, null);
            } else {
               C_EC.m503(C_h_.this.f1136, null);
            }

            C_h_.this.f1136.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Delete", abstractaction1, abstractaction));
      c__d.m1583("Right Click to Delete multiple problems");
      if (!LogicProgram.noNetwork) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_h_.this.f1136.submitProblems();
               C_h_.this.f1136.requestFocus();
            }
         };
         jpanel.add(c__d = new C__D("Submit", abstractaction1));
         c__d.m1583("Submit");
      }

      if (LogicProgram.printingEnabled && !LPRecognition.submitExam) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_MC.m1096(C_EC.m511(C_h_.this.f1136));
               C_h_.this.f1136.requestFocus();
            }
         };
         jpanel.add(c__d = new C__D("Print", abstractaction1));
         c__d.m1583("Ctrl+P");
         inputmap.put(KeyStroke.getKeyStroke(80, 128), "Print");
         actionmap.put("Print", abstractaction1);
      }

      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            LogicProgram.mainMenu.showInFront();
         }
      };
      jpanel.add(new C__D("Menu", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_h_.this.f1136.frame.m62(false);
         }
      };
      jpanel.add(c__d = new C__D("Close", abstractaction1));
      c__d.m1583("Alt+F4");
      inputmap.put(KeyStroke.getKeyStroke(115, 512), "Close");
      actionmap.put("Close", abstractaction1);
      JPanel jpanel1 = new JPanel();
      jpanel1.setLayout(new C_b_C(1, 0, i / 4, i / 8));
      this.add(jpanel1);
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.getLink("feedback");
            C_Q.m1184(s);
         }
      };
      jpanel1.add(new C__D("Feedback", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.getLink("recHelp");
            C_Q.m1186(LogicProgram.configDir, s);
         }
      };
      jpanel1.add(new C__D("Help", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.getLink("recRefs");
            C_Q.m1186(LogicProgram.configDir, s);
         }
      };
      jpanel1.add(new C__D("References", abstractaction1));
      if (ServerConnection.f480 && !LogicProgram.noNetwork && ServerConnection.uploadUrl != null) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_h_.this.f1136.uploadProblems();
               C_h_.this.f1136.requestFocus();
            }
         };
         jpanel1.add(c__d = new C__D("Upload", abstractaction1));
         c__d.m1583("Upload");
      }
   }

   public void m1836() {
      this.f1136.frame.getRootPane().setDefaultButton(this.f1137);
   }
}
