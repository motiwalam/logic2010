package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

class C_R extends JPanel implements ActionListener, C_LC {
   LPDerivation f713;

   C_R(LPDerivation lpderivation) {
      this.f713 = lpderivation;
      this.setLayout(new FlowLayout(0, 0, 0));
      this.enableEvents(8L);
   }

   JMenuBar m1199(LPDerivation lpderivation) {
      JMenuBar jmenubar = new JMenuBar();
      jmenubar.setFont(new Font("Dialog", 0, 12));
      JMenu jmenu;
      jmenubar.add(jmenu = new JMenu("Problem"));
      JMenuItem jmenuitem;
      jmenu.add(jmenuitem = new JMenuItem("New"));
      jmenuitem.addActionListener(this);
      jmenu.add(jmenuitem = new JMenuItem("Select"));
      jmenuitem.addActionListener(this);
      jmenu.add(jmenuitem = new JMenuItem("Save"));
      jmenuitem.addActionListener(this);
      jmenu.add(jmenuitem = new JMenuItem("Delete"));
      jmenuitem.addActionListener(this);
      jmenu.add(jmenuitem = new JMenuItem("Check"));
      jmenuitem.addActionListener(this);
      jmenu.add(jmenuitem = new JMenuItem("Print"));
      jmenuitem.addActionListener(this);
      jmenu.add(jmenuitem = new JMenuItem("Rules"));
      jmenuitem.addActionListener(this);
      new JMenu();
      jmenubar.add(jmenu = new JMenu("Program"));
      jmenu.add(jmenuitem = new JMenuItem("Feedback"));
      jmenuitem.addActionListener(this);
      jmenu.add(jmenuitem = new JMenuItem("Course Web Site"));
      jmenuitem.addActionListener(this);
      jmenu.add(jmenuitem = new JMenuItem("Starting"));
      jmenuitem.addActionListener(this);
      jmenu.add(jmenuitem = new JMenuItem("Help"));
      jmenuitem.addActionListener(this);
      jmenu.add(jmenuitem = new JMenuItem("FAQ"));
      jmenuitem.addActionListener(this);
      jmenu.add(jmenuitem = new JMenuItem("Advice"));
      jmenuitem.addActionListener(this);
      jmenu.add(jmenuitem = new JMenuItem("Menu"));
      jmenuitem.addActionListener(this);
      return jmenubar;
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      Object object = actionevent.getSource();
      if (object instanceof JMenuItem) {
         Container container = ((JMenuItem)object).getParent();
         if (container instanceof JMenu) {
            String s = ((JMenuItem)object).getText();
            String s1 = ((JMenu)container).getText();
            if (s != null && s1 != null) {
               if (s1.equals("Problem")) {
                  if (s.equals("New")) {
                     if (C_KB.m766(this.f713, null)) {
                        this.f713.newProblem();
                     }

                     this.f713.requestFocus();
                     return;
                  }

                  if (s.equals("Select")) {
                     C_KB.m770(this.f713);
                     return;
                  }

                  if (s.equals("Save")) {
                     String s10 = this.f713.getChangedProblem();
                     if (s10 == null) {
                        LPDerivation.saveProblems(this.f713.problemIndex);
                     } else {
                        this.f713.saveProblems(s10);
                     }

                     this.f713.requestFocus();
                     return;
                  }

                  if (s.equals("Delete")) {
                     if (LPDerivation.isExercise(this.f713.problemTitle)) {
                        C_KB.m768(this.f713, null);
                     } else {
                        C_KB.m767(this.f713, null);
                     }

                     this.f713.requestFocus();
                     return;
                  }

                  if (s.equals("Check")) {
                     this.f713.checkProblem();
                     this.f713.requestFocus();
                     return;
                  }

                  if (s.equals("Print")) {
                     C_MC.m1096(C_KB.m774(this.f713));
                     this.f713.requestFocus();
                     return;
                  }

                  if (s.equals("Rules")) {
                     BusyIndicator busyindicator = new BusyIndicator(this.f713);
                     C_KB.m798(this.f713, busyindicator);
                     return;
                  }
               }

               if (s1.equals("Program")) {
                  if (s.equals("Old Feedback")) {
                     String s9 = LogicProgram.m1054();
                     if (s9 != null) {
                        FileWriter filewriter = LogicProgram.m1068("feedback.txt", true, false);
                        if (filewriter != null) {
                           try {
                              String s4 = "----------------";
                              BufferedWriter bufferedwriter;
                              if (filewriter instanceof BufferedWriter) {
                                 bufferedwriter = (BufferedWriter)filewriter;
                              } else {
                                 bufferedwriter = new BufferedWriter(filewriter);
                              }

                              bufferedwriter.write(s9, 0, s9.length());
                              bufferedwriter.newLine();
                              bufferedwriter.write(s4, 0, s4.length());
                              bufferedwriter.newLine();
                              bufferedwriter.close();
                           } catch (IOException ioexception) {
                              System.out.println("trouble writing to feedback.txt");
                           }
                        } else {
                           System.out.println("could not open feedback.txt");
                        }
                     }

                     return;
                  }

                  if (s.equals("Feedback")) {
                     String s8 = LogicProgram.getLink("browser");
                     String s14 = LogicProgram.getLink("feedback");
                     String[] astring4 = new String[]{s8, s14};

                     try {
                        Runtime.getRuntime().exec(astring4);
                     } catch (IOException ioexception1) {
                     }

                     return;
                  }

                  if (s.equals("Course Web Site")) {
                     String s7 = LogicProgram.getLink("browser");
                     String s13 = LogicProgram.getLink("website");
                     String[] astring3 = new String[]{s7, "\"" + s13};

                     try {
                        Runtime.getRuntime().exec(astring3);
                     } catch (IOException ioexception2) {
                     }

                     return;
                  }

                  if (s.equals("Old Help")) {
                     ScrambledReader scrambledreader = LogicProgram.openDataFile("help.txt", false);
                     if (scrambledreader != null) {
                        C_KB.m779(scrambledreader);
                     }

                     return;
                  }

                  if (s.equals("Help")) {
                     String s6 = LogicProgram.getLink("word");
                     String s12 = LogicProgram.getLink("derhelp");
                     String[] astring2 = new String[]{s6, "\"" + s12 + "\""};

                     try {
                        Runtime.getRuntime().exec(astring2);
                     } catch (IOException ioexception3) {
                     }

                     return;
                  }

                  if (s.equals("Starting")) {
                     String s5 = LogicProgram.getLink("word");
                     String s11 = LogicProgram.getLink("derstart");
                     String[] astring1 = new String[]{s5, "\"" + s11 + "\""};

                     try {
                        Runtime.getRuntime().exec(astring1);
                     } catch (IOException ioexception4) {
                     }

                     return;
                  }

                  if (s.equals("FAQ")) {
                     String s2 = LogicProgram.getLink("word");
                     String s3 = LogicProgram.getLink("faq");
                     String[] astring = new String[]{s2, "\"" + s3 + "\""};

                     try {
                        Runtime.getRuntime().exec(astring);
                     } catch (IOException ioexception5) {
                     }

                     return;
                  }

                  if (s.equals("Advice")) {
                     C_KB.m797(this.f713);
                     return;
                  }

                  if (s.equals("Menu")) {
                     this.f713.frame.m62(false);
                     return;
                  }
               }
            }
         }
      }
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      int i = awtevent.getID();
      if (i == 400) {
         LogicProgram.m1086(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
