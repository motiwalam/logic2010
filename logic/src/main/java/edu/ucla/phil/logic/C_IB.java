package edu.ucla.phil.logic;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class C_IB extends C_TA {
   LPInvalidation f397;

   C_IB(LPInvalidation lpinvalidation) {
      super(false);
      this.f397 = lpinvalidation;
   }

   static C_IB m701(LPInvalidation lpinvalidation) {
      C_IB c_ib = new C_IB(lpinvalidation);
      c_ib.m702();
      return c_ib;
   }

   void m702() {
      this.setFont(new Font("Dialog", 0, 12));
      this.setLayout(new C_m_A(1, 0));
      InputMap inputmap = this.f397.getInputMap(1);
      ActionMap actionmap = this.f397.getActionMap();
      int i = this.f397.fontSize * 10 / 7;
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new C_b_C(1, 0, i / 4, i / 8));
      this.add(jpanel);
      AbstractAction abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_CE.m435(C_IB.this.f397);
         }
      };
      AbstractAction abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_CE.m436(C_IB.this.f397);
         }
      };
      C__D c__d;
      jpanel.add(c__d = new C__D("Select", abstractaction1, abstractaction));
      c__d.m1583("Ctrl+O\nCtrl+N or Right Click to Select Next Problem");
      inputmap.put(KeyStroke.getKeyStroke(79, 128), "Select");
      actionmap.put("Select", abstractaction1);
      inputmap.put(KeyStroke.getKeyStroke(78, 128), "Select Next");
      actionmap.put("Select Next", abstractaction);
      if (!LPInvalidation.noUser) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_CE.m443(C_IB.this.f397);
            }
         };
         if (LogicProgram.m1040("workEntry") != null) {
            abstractaction = new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent actionevent) {
                  if (LogicProgram.m1040("workEntry") != null && C_u_C.m2101("workEntry", "instructor")) {
                     C_CE.m441(C_IB.this.f397);
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
            if (C_IB.this.f397.checkDisabled) {
               C_UA.m1328("Feature Disabled", "Checking is disabled for this problem.", null, null);
            } else {
               C_IB.this.f397.evaluate();
            }

            C_IB.this.f397.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Check", abstractaction1));
      c__d.m1583("Ctrl+K");
      inputmap.put(KeyStroke.getKeyStroke(75, 128), "Check");
      actionmap.put("Check", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_IB.this.f397.saveRenamed(C_IB.this.f397.saveProblem());
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s1 = C_IB.this.f397.getChangedProblem();
            if (s1 == null) {
               LPInvalidation.saveProblems();
            } else {
               C_IB.this.f397.saveProblems(s1);
            }

            C_IB.this.f397.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Save", abstractaction1, abstractaction));
      c__d.m1583("Ctrl+S\nRight Click to Save with a different name");
      inputmap.put(KeyStroke.getKeyStroke(83, 128), "Save");
      actionmap.put("Save", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_CE.m439(C_IB.this.f397);
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (LPInvalidation.isExercise(C_IB.this.f397.title)) {
               C_CE.m434(C_IB.this.f397, null);
            } else {
               C_CE.m433(C_IB.this.f397, null);
            }

            C_IB.this.f397.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Delete", abstractaction1, abstractaction));
      c__d.m1583("Right Click to Delete multiple problems");
      if (!LogicProgram.f576) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_IB.this.f397.submitProblems();
               C_IB.this.f397.requestFocus();
            }
         };
         jpanel.add(c__d = new C__D("Submit", abstractaction1));
         c__d.m1583("Submit");
      }

      if (LogicProgram.f573 && !LPInvalidation.submitExam) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_e_F.m1800(C_CE.m440(C_IB.this.f397));
               C_IB.this.f397.requestFocus();
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
            C_IB.this.f397.frame.m62(false);
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
            String s1 = LogicProgram.m1078("feedback");
            C_Q.m1184(s1);
         }
      };
      jpanel1.add(new C__D("Feedback", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s1 = LogicProgram.m1078("invHelp");
            C_Q.m1186(LogicProgram.f550, s1);
         }
      };
      jpanel1.add(new C__D("Help", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            boolean flag = false;
            if ((!flag || C_IB.this.f397.size != 0) && C_IB.this.f397.statement != null) {
               C_x_A c_x_a = new C_x_A(C_IB.this, true);
               C_IB.this.f397.openDerivation(flag, c_x_a);
            } else {
               C_UA.m1329(C_LA.m411("invnot001"), null, null, null);
            }
         }
      };
      jpanel1.add(new C__D("Derivation", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            boolean flag = false;
            if (!flag || C_IB.this.f397.size != 0 && C_IB.this.f397.statement != null) {
               C_x_A c_x_a = new C_x_A(C_IB.this, true);
               C_IB.this.f397.openTruthAnalysis(flag, c_x_a);
            } else {
               C_UA.m1329(C_LA.m411("invnot003"), null, null, null);
            }
         }
      };
      jpanel1.add(new C__D("Truth Table", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_IB.this.f397.copyStatement();
         }
      };
      jpanel1.add(c__d = new C__D("Copy", abstractaction1));
      c__d.m1583("Copy problem to workspace.");
      final String s = LogicProgram.m1019(0);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (C_IB.this.f397.expandOff) {
               C_UA.m1329(C_LA.m411("invnot004"), null, null, null);
            } else if (C_IB.this.f397.expandAll) {
               C_IB.this.f397.expand(s, true);
            } else {
               C_IB.this.f397.expand(s, false);
            }
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (C_IB.this.f397.expandOff) {
               C_UA.m1329(C_LA.m411("invnot004"), null, null, null);
            } else {
               C_IB.this.f397.expand(s, false);
            }
         }
      };
      jpanel1.add(c__d = new C__D("Expand", abstractaction1, abstractaction));
      c__d.m1583("Expand outermost selected Quantifier.");
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s1 = LogicProgram.m1078("invRefs");
            C_Q.m1186(LogicProgram.f550, s1);
         }
      };
      jpanel1.add(new C__D("References", abstractaction1));
      if (C_KC.f480 && !LogicProgram.f576 && C_KC.f451 != null) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_IB.this.f397.uploadProblems();
               C_IB.this.f397.requestFocus();
            }
         };
         jpanel1.add(c__d = new C__D("Upload", abstractaction1));
         c__d.m1583("Upload");
      }
   }
}
