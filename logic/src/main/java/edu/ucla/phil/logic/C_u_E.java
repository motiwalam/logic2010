package edu.ucla.phil.logic;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class C_u_E extends CellPanel {
   LPTruthAnalysis f1399;

   C_u_E(LPTruthAnalysis lptruthanalysis) {
      super(false);
      this.f1399 = lptruthanalysis;
   }

   static C_u_E m2120(LPTruthAnalysis lptruthanalysis) {
      C_u_E c_u_e = new C_u_E(lptruthanalysis);
      c_u_e.m2121();
      return c_u_e;
   }

   void m2121() {
      this.setFont(new Font("Dialog", 0, 12));
      this.setLayout(new C_m_A(1, 0));
      InputMap inputmap = this.f1399.getInputMap(1);
      ActionMap actionmap = this.f1399.getActionMap();
      int i = this.f1399.fontSize * 10 / 7;
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new C_b_C(1, 0, i / 4, i / 8));
      this.add(jpanel);
      AbstractAction abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_w_A.m2140(C_u_E.this.f1399);
         }
      };
      AbstractAction abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_w_A.m2141(C_u_E.this.f1399);
         }
      };
      C__D c__d;
      jpanel.add(c__d = new C__D("Select", abstractaction1, abstractaction));
      c__d.m1583("Ctrl+O\nCtrl+N or Right Click to Select Next Problem");
      inputmap.put(KeyStroke.getKeyStroke(79, 128), "Select");
      actionmap.put("Select", abstractaction1);
      inputmap.put(KeyStroke.getKeyStroke(78, 128), "Select Next");
      actionmap.put("Select Next", abstractaction);
      if (!LPTruthAnalysis.noUser) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_w_A.m2149(C_u_E.this.f1399);
            }
         };
         if (LogicProgram.getCredentials("workEntry") != null) {
            abstractaction = new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent actionevent) {
                  if (LogicProgram.getCredentials("workEntry") != null && UserSetup.m2101("workEntry", "instructor")) {
                     C_w_A.m2146(C_u_E.this.f1399);
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
            if (C_u_E.this.f1399.checkDisabled) {
               MessageDialog.showMessage("Feature Disabled", "Checking is disabled for this problem.", null, null);
            } else {
               C_u_E.this.f1399.checkProblem();
            }

            C_u_E.this.f1399.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Check", abstractaction1));
      c__d.m1583("Ctrl+K");
      inputmap.put(KeyStroke.getKeyStroke(75, 128), "Check");
      actionmap.put("Check", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_u_E.this.f1399.saveRenamed(C_u_E.this.f1399.saveProblem());
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = C_u_E.this.f1399.getChangedProblem();
            if (s == null) {
               LPTruthAnalysis.saveProblems();
            } else {
               C_u_E.this.f1399.saveProblems(s);
            }

            C_u_E.this.f1399.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Save", abstractaction1, abstractaction));
      c__d.m1583("Ctrl+S\nRight Click to Save with a different name");
      inputmap.put(KeyStroke.getKeyStroke(83, 128), "Save");
      actionmap.put("Save", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_w_A.m2144(C_u_E.this.f1399);
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (LPTruthAnalysis.isExercise(C_u_E.this.f1399.problem.f1211)) {
               C_w_A.m2139(C_u_E.this.f1399, null);
            } else {
               C_w_A.m2138(C_u_E.this.f1399, null);
            }

            C_u_E.this.f1399.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Delete", abstractaction1, abstractaction));
      c__d.m1583("Right Click to Delete multiple problems");
      if (!LogicProgram.noNetwork) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_u_E.this.f1399.submitProblems();
               C_u_E.this.f1399.requestFocus();
            }
         };
         jpanel.add(c__d = new C__D("Submit", abstractaction1));
         c__d.m1583("Submit");
      }

      if (LogicProgram.printingEnabled && !LPTruthAnalysis.submitExam) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_x_.m2161(C_w_A.m2145(C_u_E.this.f1399));
               C_u_E.this.f1399.requestFocus();
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
            C_u_E.this.f1399.frame.m62(false);
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
            String s = LogicProgram.getLink("truHelp");
            C_Q.m1186(LogicProgram.configDir, s);
         }
      };
      jpanel1.add(new C__D("Help", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.getLink("truRefs");
            C_Q.m1186(LogicProgram.configDir, s);
         }
      };
      jpanel1.add(new C__D("References", abstractaction1));
      if (ServerConnection.f480 && !LogicProgram.noNetwork && ServerConnection.uploadUrl != null) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_u_E.this.f1399.uploadProblems();
               C_u_E.this.f1399.requestFocus();
            }
         };
         jpanel1.add(c__d = new C__D("Upload", abstractaction1));
         c__d.m1583("Upload");
      }
   }
}
