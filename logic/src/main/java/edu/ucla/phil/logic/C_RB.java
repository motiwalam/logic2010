package edu.ucla.phil.logic;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class C_RB extends C_TA {
   LPParsing f716;

   C_RB(LPParsing lpparsing) {
      super(false);
      this.f716 = lpparsing;
   }

   static C_RB m1201(LPParsing lpparsing) {
      C_RB c_rb = new C_RB(lpparsing);
      c_rb.m1202();
      return c_rb;
   }

   void m1202() {
      this.setFont(new Font("Dialog", 0, 12));
      this.setLayout(new C_m_A(1, 0));
      InputMap inputmap = this.f716.getInputMap(1);
      ActionMap actionmap = this.f716.getActionMap();
      int i = this.f716.fontSize * 10 / 7;
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new C_b_C(1, 0, i / 4, i / 8));
      this.add(jpanel);
      AbstractAction abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_KA.m748(C_RB.this.f716);
         }
      };
      AbstractAction abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_KA.m749(C_RB.this.f716);
         }
      };
      C__D c__d;
      jpanel.add(c__d = new C__D("Select", abstractaction1, abstractaction));
      c__d.m1583("Ctrl+O\nCtrl+N or Right Click to Select Next Problem");
      inputmap.put(KeyStroke.getKeyStroke(79, 128), "Select");
      actionmap.put("Select", abstractaction1);
      inputmap.put(KeyStroke.getKeyStroke(78, 128), "Select Next");
      actionmap.put("Select Next", abstractaction);
      if (!LPParsing.noUser) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_KA.m758(C_RB.this.f716);
            }
         };
         if (LogicProgram.m1040("workEntry") != null) {
            abstractaction = new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent actionevent) {
                  if (LogicProgram.m1040("workEntry") != null && C_u_C.m2101("workEntry", "instructor")) {
                     C_KA.m754(C_RB.this.f716);
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
            C_RB.this.f716.check();
         }
      };
      jpanel.add(c__d = new C__D("Check", abstractaction1));
      c__d.m1583("Ctrl+K");
      inputmap.put(KeyStroke.getKeyStroke(75, 128), "Check");
      actionmap.put("Check", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_RB.this.f716.saveRenamed(C_RB.this.f716.saveProblem());
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = C_RB.this.f716.getChangedProblem();
            if (s == null) {
               LPParsing.saveProblems();
            } else {
               C_RB.this.f716.saveProblems(s);
            }

            C_RB.this.f716.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Save", abstractaction1, abstractaction));
      c__d.m1583("Ctrl+S\nRight Click to Save with a different name");
      inputmap.put(KeyStroke.getKeyStroke(83, 128), "Save");
      actionmap.put("Save", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_KA.m752(C_RB.this.f716);
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (LPParsing.isExercise(C_RB.this.f716.problem.f1458)) {
               C_KA.m747(C_RB.this.f716, null);
            } else {
               C_KA.m746(C_RB.this.f716, null);
            }

            C_RB.this.f716.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Delete", abstractaction1, abstractaction));
      c__d.m1583("Right Click to Delete multiple problems");
      if (!LogicProgram.f576) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_RB.this.f716.submitProblems();
               C_RB.this.f716.requestFocus();
            }
         };
         jpanel.add(c__d = new C__D("Submit", abstractaction1));
         c__d.m1583("Submit");
      }

      if (LogicProgram.f573 && !LPParsing.submitExam) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_o_E.m2009(C_KA.m753(C_RB.this.f716));
               C_RB.this.f716.requestFocus();
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
            C_RB.this.f716.frame.m62(false);
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
            String s = LogicProgram.m1078("feedback");
            C_Q.m1184(s);
         }
      };
      jpanel1.add(new C__D("Feedback", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.m1078("parHelp");
            C_Q.m1186(LogicProgram.f550, s);
         }
      };
      jpanel1.add(new C__D("Help", abstractaction1));
      if (C_KC.f480 && !LogicProgram.f576 && C_KC.f451 != null) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_RB.this.f716.uploadProblems();
               C_RB.this.f716.requestFocus();
            }
         };
         jpanel1.add(c__d = new C__D("Upload", abstractaction1));
         c__d.m1583("Upload");
      }
   }
}
