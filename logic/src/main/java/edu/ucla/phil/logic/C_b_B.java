package edu.ucla.phil.logic;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class C_b_B extends C_TA {
   LPSymbolizer f995;

   C_b_B(LPSymbolizer lpsymbolizer) {
      super(false);
      this.f995 = lpsymbolizer;
   }

   static C_b_B m1663(LPSymbolizer lpsymbolizer) {
      C_b_B c_b_b = new C_b_B(lpsymbolizer);
      c_b_b.m1664();
      return c_b_b;
   }

   void m1664() {
      this.setFont(new Font("Dialog", 0, 12));
      this.setLayout(new C_m_A(1, 0));
      InputMap inputmap = this.f995.getInputMap(1);
      ActionMap actionmap = this.f995.getActionMap();
      int i = this.f995.fontSize * 10 / 7;
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new C_b_C(1, 0, i / 4, i / 8));
      this.add(jpanel);
      AbstractAction abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_WB.m1433(C_b_B.this.f995);
         }
      };
      AbstractAction abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_WB.m1434(C_b_B.this.f995);
         }
      };
      C__D c__d;
      jpanel.add(c__d = new C__D("Select", abstractaction1, abstractaction));
      c__d.m1583("Ctrl+O\nCtrl+N or Right Click to Select Next Problem");
      inputmap.put(KeyStroke.getKeyStroke(79, 128), "Select");
      actionmap.put("Select", abstractaction1);
      inputmap.put(KeyStroke.getKeyStroke(78, 128), "Select Next");
      actionmap.put("Select Next", abstractaction);
      if (!LPSymbolizer.noUser) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_WB.m1442(C_b_B.this.f995);
            }
         };
         if (LogicProgram.m1040("workEntry") != null) {
            abstractaction = new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent actionevent) {
                  if (LogicProgram.m1040("workEntry") != null && C_u_C.m2101("workEntry", "instructor")) {
                     C_WB.m1440(C_b_B.this.f995);
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
            C_WB.m1453(C_b_B.this.f995);
         }
      };
      jpanel.add(c__d = new C__D("Direct", abstractaction1));
      c__d.m1583("Enter Expression Directly");
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (C_b_B.this.f995.checkDisabled) {
               C_UA.m1328("Feature Disabled", "Checking is disabled for this problem.", null, null);
            } else {
               C_b_B.this.f995.checkProblem();
            }

            C_b_B.this.f995.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Check", abstractaction1));
      c__d.m1583("Ctrl+K");
      inputmap.put(KeyStroke.getKeyStroke(75, 128), "Check");
      actionmap.put("Check", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_b_B.this.f995.saveRenamed(C_b_B.this.f995.saveProblem());
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = C_b_B.this.f995.getChangedProblem();
            if (s == null) {
               LPSymbolizer.saveProblems(C_b_B.this.f995.problemIndex);
            } else {
               C_b_B.this.f995.saveProblems(s);
            }

            C_b_B.this.f995.requestFocus();
         }
      };
      jpanel.add(c__d = new C__D("Save", abstractaction1, abstractaction));
      c__d.m1583("Ctrl+S\nRight Click to Save with a different name");
      inputmap.put(KeyStroke.getKeyStroke(83, 128), "Save");
      actionmap.put("Save", abstractaction1);
      abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_WB.m1438(C_b_B.this.f995);
         }
      };
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            boolean flag;
            if (LPSymbolizer.isExercise(C_b_B.this.f995.problem.f1051)) {
               flag = C_WB.m1432(C_b_B.this.f995, null);
            } else {
               flag = C_WB.m1431(C_b_B.this.f995, null);
            }

            if (flag) {
               C_b_B.this.f995.problem.f1045.requestFocus();
            } else if (C_b_B.this.f995.lastFocus != null) {
               C_b_B.this.f995.lastFocus.requestFocus();
            }
         }
      };
      jpanel.add(c__d = new C__D("Delete", abstractaction1, abstractaction));
      c__d.m1583("Right Click to Delete multiple problems");
      if (!LogicProgram.f576) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_b_B.this.f995.submitProblems();
               C_b_B.this.f995.requestFocus();
            }
         };
         jpanel.add(c__d = new C__D("Submit", abstractaction1));
         c__d.m1583("Submit");
      }

      if (LogicProgram.f573 && !LPSymbolizer.submitExam) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_TC.m1307(C_WB.m1439(C_b_B.this.f995));
               C_b_B.this.f995.requestFocus();
            }
         };
         if (LogicProgram.m1040("symAnswerPrint") != null) {
            abstractaction = new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent actionevent) {
                  if (LogicProgram.m1040("symAnswerPrint") != null && C_u_C.m2101("symAnswerPrint", "instructor")) {
                     C_PD.m1179(C_WB.m1448(C_b_B.this.f995.frame));
                  }

                  C_b_B.this.f995.requestFocus();
               }
            };
            jpanel.add(c__d = new C__D("Print", abstractaction1, abstractaction));
            c__d.m1583("Ctrl+P\nRight Click to Print Answers to Problems");
         } else {
            jpanel.add(c__d = new C__D("Print", abstractaction1));
            c__d.m1583("Ctrl+P");
         }

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
            C_b_B.this.f995.frame.m62(false);
         }
      };
      jpanel.add(c__d = new C__D("Close", abstractaction1));
      c__d.m1583("Alt+F4");
      inputmap.put(KeyStroke.getKeyStroke(115, 512), "Close");
      actionmap.put("Close", abstractaction1);
      JPanel jpanel1 = new JPanel();
      jpanel1.setLayout(new C_b_C(1, 0, i / 4, i / 8));
      this.add(jpanel1);
      if (C_KC.f480) {
         C_F c_f = new C_F() {
            @Override
            public void m514(C_b_A c_b_a1, int j, int j) {
               String s = c_b_a1.m1654(j);
               if (s != null) {
                  if (s.equalsIgnoreCase("Statement")) {
                     C_WB.m1445(C_b_B.this.f995);
                  } else if (s.equalsIgnoreCase("Scheme")) {
                     C_WB.m1446(C_b_B.this.f995);
                  } else if (s.equalsIgnoreCase("Answers")) {
                     C_WB.m1447(C_b_B.this.f995);
                  }

                  c_b_a1.m1650(2, false);
               }
            }
         };
         C_b_A c_b_a = new C_b_A(new String[]{"Statement", "Scheme", "Answers"}, "Edit", 2, false);
         c_b_a.m1656(true);
         c_b_a.m1659(c_f);
         jpanel1.add(c_b_a);
      }

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
            String s = LogicProgram.m1078("symHelp");
            C_Q.m1186(LogicProgram.f550, s);
         }
      };
      jpanel1.add(new C__D("Help", abstractaction1));
      abstractaction1 = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            String s = LogicProgram.m1078("symSamp");
            C_Q.m1186(LogicProgram.f550, s);
         }
      };
      jpanel1.add(new C__D("Examples", abstractaction1));
      if (C_KC.f480 && !LogicProgram.f576 && C_KC.f451 != null) {
         abstractaction1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionevent) {
               C_b_B.this.f995.uploadProblems();
               C_b_B.this.f995.requestFocus();
            }
         };
         jpanel1.add(c__d = new C__D("Upload", abstractaction1));
         c__d.m1583("Upload");
      }
   }
}
