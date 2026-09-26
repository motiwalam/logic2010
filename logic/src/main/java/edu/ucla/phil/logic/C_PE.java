package edu.ucla.phil.logic;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class C_PE extends C_TA {
   LPDerivation f674;

   C_PE(LPDerivation lpderivation) {
      super(false);
      this.f674 = lpderivation;
   }

   static C_PE m1180(LPDerivation lpderivation) {
      C_PE c_pe = new C_PE(lpderivation);
      c_pe.m1181();
      return c_pe;
   }

   void m1181() {
      this.setFont(new Font("Dialog", 0, 12));
      this.setLayout(new C_m_A(1, 0));
      InputMap inputmap = this.f674.getInputMap(1);
      ActionMap actionmap = this.f674.getActionMap();
      int i = this.f674.fontSize * 10 / 7;
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new C_b_C(1, 0, i / 4, i / 8));
      this.add(jpanel);
      AbstractAction abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_KB.m769(C_PE.this.f674);
         }
      };
      AbstractAction abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_KB.m770(C_PE.this.f674);
         }
      };
      C__D c__d;
      jpanel.add(c__d = new C__D("Select", abstractaction1, abstractaction));
      c__d.m1583("Ctrl+O\nCtrl+N or Right Click to Select Next Problem");
      inputmap.put(KeyStroke.getKeyStroke(79, 128), "Select");
      actionmap.put("Select", abstractaction1);
      inputmap.put(KeyStroke.getKeyStroke(78, 128), "Select Next");
      actionmap.put("Select Next", abstractaction);
      if (!LPDerivation.noUser) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_KB.m777(C_PE.this.f674);
            }
         };
         if (LogicProgram.m1040("workEntry") != null) {
            abstractaction = new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent actionevent) {
                  if (LogicProgram.m1040("workEntry") != null && C_u_C.m2101("workEntry", "instructor")) {
                     C_KB.m775(C_PE.this.f674);
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

      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_x_A c_x_a = new C_x_A(C_PE.this.f674);
            C_KB.m798(C_PE.this.f674, c_x_a);
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_x_A c_x_a = new C_x_A(C_PE.this.f674);
            C_KB.m798(null, c_x_a);
         }
      };
      jpanel.add(c__d = new C__D("Rules", abstractaction1, abstractaction));
      c__d.m1583("Display All Rules\nRight Click to Display Available Rules");
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (C_PE.this.f674.checkDisabled) {
               C_UA.m1328("Feature Disabled", "Checking is disabled for this problem.", null, null);
            } else {
               C_PE.this.f674.checkProblem();
            }

            C_PE.this.f674.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Check", abstractaction1));
      c__d.m1583("Ctrl+K");
      inputmap.put(KeyStroke.getKeyStroke(75, 128), "Check");
      actionmap.put("Check", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_PE.this.f674.saveRenamed(C_PE.this.f674.saveProblem());
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = C_PE.this.f674.getChangedProblem();
            if (s == null) {
               LPDerivation.saveProblems(C_PE.this.f674.problemIndex);
            } else {
               C_PE.this.f674.saveProblems(s);
            }

            C_PE.this.f674.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Save", abstractaction1, abstractaction));
      c__d.m1583("Ctrl+S\nRight Click to Save with a different name");
      inputmap.put(KeyStroke.getKeyStroke(83, 128), "Save");
      actionmap.put("Save", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_KB.m773(C_PE.this.f674);
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (LPDerivation.isExercise(C_PE.this.f674.problemTitle)) {
               C_KB.m768(C_PE.this.f674, null);
            } else {
               C_KB.m767(C_PE.this.f674, null);
            }

            C_PE.this.f674.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Delete", abstractaction1, abstractaction));
      c__d.m1583("Right Click to Delete multiple problems");
      if (!LogicProgram.f576) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_PE.this.f674.submitProblems();
               C_PE.this.f674.requestFocus();
            }
         };
         jpanel.add(c__d = new C__D("Submit", abstractaction1));
         c__d.m1583("Submit");
      }

      if (LogicProgram.f573 && !LPDerivation.submitExam) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_MC.m1096(C_KB.m774(C_PE.this.f674));
               C_PE.this.f674.requestFocus();
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
            LogicProgram.f586.showInFront();
         }
      };
      jpanel.add(new C__D("Menu", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_PE.this.f674.frame.m62(false);
         }
      };
      jpanel.add(c__d = new C__D("Close", abstractaction1));
      c__d.m1583("Alt+F4");
      inputmap.put(KeyStroke.getKeyStroke(115, 512), "Close");
      actionmap.put("Close", abstractaction1);
      JPanel jpanel1 = new JPanel();
      jpanel.setLayout(new C_b_C(1, 0, i / 4, i / 8));
      this.add(jpanel1);
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.m1078("feedback");
            C_Q.m1184(s);
         }
      };
      jpanel1.add(new C__D("Feedback", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.m1078("derStart");
            C_Q.m1186(LogicProgram.f550, s);
         }
      };
      jpanel1.add(new C__D("Starting", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.m1078("derHelp");
            C_Q.m1186(LogicProgram.f550, s);
         }
      };
      jpanel1.add(new C__D("Help", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.m1078("derFAQ");
            C_Q.m1186(LogicProgram.f550, s);
         }
      };
      jpanel1.add(new C__D("FAQ", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.m1078("derTips");
            C_Q.m1186(LogicProgram.f550, s);
         }
      };
      jpanel1.add(new C__D("Advice", abstractaction1));
      if (C_KC.f480 && !LogicProgram.f576 && C_KC.f451 != null) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_PE.this.f674.uploadProblems();
               C_PE.this.f674.requestFocus();
            }
         };
         jpanel1.add(c__d = new C__D("Upload", abstractaction1));
         c__d.m1583("Upload");
      }
   }
}
