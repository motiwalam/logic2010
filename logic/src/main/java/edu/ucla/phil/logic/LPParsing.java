package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

class LPParsing extends C_U implements C_n_A {
   C_LB scrollPanel;
   C_y_D problem;
   int fontSize = LogicProgram.f539;
   Font font;
   static final String workFileName = "parwork.txt";
   static final String logFileName = "pardata.txt";
   static final String digestVersKey = "parDigestVers";
   static Class messageClass = C_ND.class;
   static C_ZC exercises = null;
   static C_ZC problems = null;
   static Vector instances = new Vector();
   static C_c_C printQueue = new C_c_C("Parsing");
   static C_BE autoCheck = null;
   static C_BE addToDB = null;
   static C_BE updateDB = null;
   static C_BE noPrint = null;
   static C_BE noCheck = null;
   static C_BE noPrintCheck = null;
   static C_BE monoProbs = null;
   static C_BE logPrint = null;
   static C_BE logSubmit = null;
   static C_BE needPrint = null;
   static C_BE needSubmit = null;
   static C_BE mainOnly = null;
   static boolean noUser = false;
   static boolean submitExam = false;
   static boolean printIncorrect = false;
   static boolean restating = false;
   static String newProblem = null;
   static Vector startups = null;
   boolean checkNow;
   boolean checkDisabled;
   boolean dontChange;
   boolean noDescent;
   Hashtable probOptions;
   int errorCount;
   long workTime;
   long loadTime;
   String lastUserProblem;
   JScrollPane scroller;
   static int moduleIndex = 2;

   static boolean getExercises() {
      if (!C_ND.m410()) {
         LogicProgram.m971("not001", "the parsing messages file");
         return false;
      } else {
         resetOptions();
         readOptions(LogicProgram.m1062("options", false));
         readOptions(LogicProgram.m1065("options", false));
         logNeeds();
         if ((exercises = readExercises()) == null) {
            return false;
         } else {
            insertDBProbs(addToDB);
            updateDBProbs(updateDB);
            return true;
         }
      }
   }

   static C_ZC readExercises() {
      return readExercises(LogicProgram.f584, false, false);
   }

   static C_ZC readExercises(boolean flag, boolean flag1, boolean flag2) {
      C_ZC c_zc = new C_ZC();
      if (!flag) {
         C_XB c_xb = LogicProgram.m1062("parwork.txt", false);
         if (c_xb == null) {
            LogicProgram.m971("not001", "the core Parsing exercise file");
            return null;
         }

         if (!readProblems(c_xb, c_zc, true)) {
            LogicProgram.m971("not002", "the core Parsing exercise file");
            return null;
         }
      }

      if (!flag1) {
         C_XB c_xb1 = LogicProgram.m1065("parwork.txt", flag2);
         if (c_xb1 != null && (flag ? !readProblems(c_xb1, c_zc, true) : !mergeProblems(c_xb1, c_zc, true))) {
            LogicProgram.m971("not002", "the local Parsing exercise file");
            return null;
         }
      }

      return c_zc;
   }

   LPParsing(boolean flag) {
      super(flag);
      this.setLayout(new BorderLayout());
      this.add(this.titlePanel, "North");
      this.add(this.scroller = new JScrollPane(), "Center");
      this.scroller.getVerticalScrollBar().setUnitIncrement(22);
      this.scroller.setViewportView(this.scrollPanel = new C_LB());
      this.scrollPanel.add(this.problem = new C_y_D(this), "Center");
      this.add(C_RB.m1201(this), "South");
      this.newProblem();
   }

   @Override
   int getModuleIndex() {
      return moduleIndex;
   }

   static synchronized void startup(Rectangle rectangle, C_x_A c_x_a, String s) {
      if (startups == null) {
         startups = new Vector();
      }

      if (exercises != null || getExercises()) {
         LPParsing.C__A lpparsing$c__a = new LPParsing.C__A(c_x_a, rectangle, s);
         startups.add(lpparsing$c__a);
         if (startups.size() <= 1) {
            if (problems == null) {
               if (!getProblems()) {
                  lpparsing$c__a.m1310();
                  startups.remove(lpparsing$c__a);
                  return;
               }

               if (problems.m1773()) {
                  saveProblems();
               }

               problems.m1099(lpparsing$c__a);
            } else {
               lpparsing$c__a.m959();
            }
         }
      }
   }

   static synchronized void continueStartup() {
      restating = false;
      int i = startups.size();

      for (int j = 0; j < i; j++) {
         ((C_U.C__A)startups.get(i - 1 - j)).f782.m2162(false);
         ((C_U.C__A)startups.get(i - 1 - j)).f782 = null;
      }

      while (!startups.isEmpty()) {
         C_U.C__A c_u$c__a = (C_U.C__A)startups.remove(0);
         SwingUtilities.invokeLater(c_u$c__a);
      }
   }

   Rectangle fixModuleRect(Rectangle rectangle) {
      C_RB c_rb = C_RB.m1201(this);
      Dimension dimension = c_rb.getPreferredSize();
      if (rectangle.width < dimension.width) {
         rectangle.width = dimension.width;
      }

      return rectangle;
   }

   static void allocateParModule(C_U.C__A c_u$c__a) {
      LPParsing lpparsing = new LPParsing(false);
      instances.addElement(lpparsing);
      if (c_u$c__a.f784 == null || newProblem == null) {
         lpparsing.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lpparsing.saveProblem();
         }
      }

      if (c_u$c__a.f784 != null) {
         lpparsing.loadProblem(c_u$c__a.f784);
         c_u$c__a.f784 = null;
      }

      lpparsing.setupFrame(LPInfo.programName + ": Parsing");
      lpparsing.frame.setBounds(lpparsing.fixModuleRect(c_u$c__a.f783));
      lpparsing.frame.setVisible(true);
      lpparsing.requestFocus();
   }

   static void insertDBProbs(C_BE c_be) {
      if (c_be != null && !c_be.m402() && C_u_C.m2101("addToDB", "developer")) {
         int i = exercises.size();

         for (int j = 0; j < i; j++) {
            C_XD c_xd = new C_XD(exercises.m1778(j));
            String s = c_xd.m1494();
            if (c_be.m404(s)) {
               if (C_k_C.f1199 != null) {
                  C_k_C.f1199.println(LogicProgram.m1079());
                  C_k_C.f1199.println("adding " + s);
               }

               String s1 = getProblemStatement(c_xd);
               String s2 = c_xd.m1483(c_xd.m1475('!'));
               if (s2 != null && s2.length() > 255) {
                  s2 = s2.substring(0, 252) + "...";
               }

               String s3 = C_z_D.m2225(s1.trim());
               String s4 = LogicProgram.m995(s1, maggie, html);
               String s5 = c_xd.m1483(c_xd.m1475('C'));
               if (s5 == null) {
                  s5 = s;
               }

               String s6 = "insert into logic_problem (COMMENT,DTCREATION,PROBLEM_NAME,TPROBLEM,TPROBLEM_MD5,TWEB_FORM_PROBLEM,VERSION,SYNTAX,COMMON_NAME)";
               s6 = s6 + " values (" + C_KC.m815(s2) + ",GETDATE()," + C_KC.m815(s) + "," + C_KC.m815(s1) + ",";
               s6 = s6 + C_KC.m815(s3) + "," + C_KC.m815(s4) + "," + nameVersion(s) + "," + C_FB.m537() + ",";
               s6 = s6 + C_KC.m815(s5) + ")";
               C_KC.m814(s6);
            }
         }
      }
   }

   static void updateDBProbs(C_BE c_be) {
      if (c_be != null && !c_be.m402() && C_u_C.m2101("addToDB", "developer")) {
         int i = exercises.size();

         for (int j = 0; j < i; j++) {
            C_XD c_xd = new C_XD(exercises.m1778(j));
            String s = c_xd.m1494();
            if (c_be.m404(s)) {
               if (C_k_C.f1199 != null) {
                  C_k_C.f1199.println(LogicProgram.m1079());
                  C_k_C.f1199.println("updating " + s);
               }

               String s1 = getProblemStatement(c_xd);
               String s2 = c_xd.m1483(c_xd.m1475('!'));
               if (s2 != null && s2.length() > 255) {
                  s2 = s2.substring(0, 252) + "...";
               }

               String s3 = C_z_D.m2225(s1.trim());
               String s4 = LogicProgram.m995(s1, maggie, html);
               String s5 = c_xd.m1483(c_xd.m1475('C'));
               if (s5 == null) {
                  s5 = s;
               }

               String s6 = "update logic_problem set tproblem = " + C_KC.m815(s1) + ", tproblem_md5 = " + C_KC.m815(s3);
               s6 = s6 + ", tweb_form_problem = " + C_KC.m815(s4) + ", comment = " + C_KC.m815(s2);
               s6 = s6 + ", version = " + nameVersion(s) + ", common_name = " + C_KC.m815(s5);
               s6 = s6 + " where problem_name = " + C_KC.m815(s) + " and syntax = " + C_FB.m537();
               C_KC.m814(s6);
            }
         }
      }
   }

   static String nameVersion(String s) {
      return s != null && s.toLowerCase().startsWith("pars") ? "1" : "NULL";
   }

   void setupFrame(String s) {
      this.frame = new C_0E(s);
      this.setFontSize(this.fontSize);
      this.setColors(this.colors);
      this.frame.f27 = this;
      this.frame.add(this, "Center");
   }

   @Override
   public boolean shutdown(boolean flag) {
      if (!flag && !C_KA.m756(this, null)) {
         return false;
      } else {
         this.reset();
         synchronized (moduleClasses[2]) {
            instances.removeElement(this);
            if (instances.isEmpty()) {
               C_l_B.m1924(printQueue);
               problems = null;
               exercises = null;
               newProblem = null;
               startups = null;
               resetOptions();
            }

            return true;
         }
      }
   }

   static synchronized LPParsing openInstance(int i, boolean flag) {
      if (i >= 0 && instances != null && !instances.isEmpty()) {
         int j = instances.size();

         for (int k = 0; k < j; k++) {
            LPParsing lpparsing = (LPParsing)instances.elementAt(k);
            if (lpparsing.problemIndex == i) {
               if (flag) {
                  lpparsing.requestFocus();
               }

               return lpparsing;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   int getProblemIndex() {
      return this.problemIndex;
   }

   @Override
   public boolean save() {
      return C_KA.m756(this, null);
   }

   static void resetOptions() {
      autoCheck = null;
      addToDB = null;
      updateDB = null;
      noPrint = null;
      noCheck = null;
      noPrintCheck = null;
      monoProbs = null;
      logPrint = null;
      logSubmit = null;
      needPrint = null;
      needSubmit = null;
      mainOnly = null;
      noUser = false;
      submitExam = false;
      printIncorrect = false;
      restating = false;
   }

   static boolean checkQuit(Hashtable hashtable, Hashtable hashtable1) {
      resetOptions();
      readOptions(LogicProgram.m1062("options", false));
      readOptions(LogicProgram.m1065("options", false));
      logNeeds();
      if (needPrint != null && !needPrint.m402() || needSubmit != null && !needSubmit.m402()) {
         C_a_A c_a_a = new C_a_A(readWork());
         c_a_a.m1634(readExercises());
         C_z_C.m2215(hashtable, "pardata.txt", "R", needPrint, c_a_a);
         c_a_a.m1635();
         C_z_C.m2215(hashtable1, "pardata.txt", "S", needSubmit, c_a_a);
      }

      resetOptions();
      return true;
   }

   static Vector getChangedProblems() {
      C_a_A c_a_a = new C_a_A(readWork());
      Hashtable hashtable = LogicProgram.m1084("pardata.txt", "S", needSubmit, c_a_a);
      if (hashtable == null) {
         return null;
      } else {
         Vector vector = (Vector)hashtable.get("handled");
         Vector vector1 = new Vector();
         c_a_a.m1635();

         while (c_a_a.hasMoreElements()) {
            String s = (String)c_a_a.nextElement();
            if ((vector == null || !vector.contains(s)) && hasWork(new C_XD(s))) {
               vector1.add(s);
            }
         }

         return vector1;
      }
   }

   void reset() {
      this.newProblem();
   }

   @Override
   public void resize() {
      if (this.frame != null) {
         ;
      }
   }

   @Override
   public Frame getFrame() {
      return this.frame;
   }

   void setFontSize(int i) {
      this.fontSize = i;
      this.font = LogicProgram.m1029(i);
      if (this.frame != null) {
         this.frame.setFont(this.font);
      }
   }

   void setColors(Color[] acolor) {
      this.colors = acolor;
      if (this.scroller != null) {
         this.problem.setForeground(acolor[0]);
         this.problem.setBackground(acolor[1]);
         Graphics graphics = this.scroller.getGraphics();
         if (graphics != null) {
            this.scroller.paintAll(graphics);
         }
      }
   }

   static synchronized boolean getProblems() {
      if (problems != null) {
         return true;
      } else {
         C_ZC c_zc = readWork();
         if (c_zc == null) {
            return false;
         } else {
            if (c_zc.f1079 && !c_zc.m1777(LogicProgram.f533).equals(c_zc.f1076)) {
               System.out.println("Could not digest file: parwork.txt");
               if (!C_u_C.m2101("indigestion", "instructor")) {
                  LogicProgram.m971("not003", "parwork.txt");
                  return false;
               }
            }

            problems = c_zc;
            C_l_D.f1254 = C_f_F.m1816("parwork.txt", problems);
            C_f_F.m1815(exercises, C_l_D.f1254);
            return true;
         }
      }
   }

   static C_ZC readWork() {
      if (!LogicProgram.m976()) {
         return null;
      } else {
         C_ZC c_zc = new C_ZC();
         C_XB c_xb = LogicProgram.m1062("parwork.txt", true);
         if (!LogicProgram.f584 || c_xb instanceof C_b_D) {
            if (c_xb == null) {
               LogicProgram.m971("not001", "parwork.txt");
               return null;
            }

            if (c_xb instanceof C_b_D) {
               c_zc.f1079 = true;
            }

            if (!readProblems(c_xb, c_zc, false)) {
               LogicProgram.m971("not002", "parwork.txt");
               return null;
            }
         }

         if (!(c_xb instanceof C_b_D)) {
            c_xb = LogicProgram.m1065("parwork.txt", false);
            if (c_xb != null && !mergeProblems(c_xb, c_zc, false)) {
               LogicProgram.m971("not002", "parwork.txt");
               return null;
            }
         }

         return c_zc;
      }
   }

   static String getExerciseTitle(String s) {
      String s1;
      return exercises != null && (s1 = exercises.m1780(s)) != null ? C_XD.m1493(s1) : null;
   }

   static boolean isExercise(String s) {
      return exercises != null && s != null && exercises.m1780(s) != null;
   }

   static boolean isExample(String s) {
      return exercises != null && s != null && C_XD.m1502(exercises.m1780(s));
   }

   void loadExerciseInfo(C_XD c_xd) {
      c_xd = new C_XD(exercises == null ? null : exercises.m1780(c_xd.m1494()));
      this.probOptions = c_xd.m1506('%');
      this.dontChange = this.probOptions != null && this.probOptions.containsKey("eg");
      this.titlePanel.m1827(c_xd.m1483(c_xd.m1475('!')));
      String s = c_xd.m1494();
      this.checkNow = LogicProgram.m1060(autoCheck, s);
      this.checkDisabled = LogicProgram.m1060(this.forPrint ? noPrintCheck : noCheck, s);
      this.noDescent = LogicProgram.m1060(mainOnly, s);
      this.checkNow = this.checkNow & !this.checkDisabled;
   }

   static boolean readProblems(Reader reader, C_ZC c_zc, boolean flag) {
      return readProblems(reader, c_zc, flag, false);
   }

   static boolean mergeProblems(Reader reader, C_ZC c_zc, boolean flag) {
      return readProblems(reader, c_zc, flag, true);
   }

   static boolean readProblems(Reader reader, C_ZC c_zc, boolean flag, boolean flag1) {
      return C_U.readProblems(reader, c_zc, flag, flag1);
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         C_XD c_xd = new C_XD(reader, true);
         String s = "";

         while (c_xd.m1469()) {
            String s1 = c_xd.m1494();
            if (s1 != null && s1.trim().equalsIgnoreCase("parsing")) {
               int[] aint = c_xd.m1481("+?");
               int i = aint.length;

               for (int j = 0; j < i; j++) {
                  char c0 = c_xd.m1474(aint[j]);
                  String s2 = c_xd.m1483(aint[j]);
                  if (c0 == '+') {
                     if (s2.equalsIgnoreCase("noUser")) {
                        noUser = true;
                     } else if (s2.equalsIgnoreCase("submitExam")) {
                        submitExam = true;
                     } else if (s2.equalsIgnoreCase("printIncorrect")) {
                        printIncorrect = true;
                     }
                  } else if (c0 == '?') {
                     int k = s2.indexOf(58);
                     if (k != -1) {
                        String s3 = s2.substring(0, k).trim();
                        if (s3.equalsIgnoreCase("autoCheck")) {
                           if (autoCheck == null) {
                              autoCheck = new C_BE();
                           }

                           autoCheck.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("addToDB")) {
                           if (addToDB == null) {
                              addToDB = new C_BE();
                           }

                           addToDB.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("updateDB")) {
                           if (updateDB == null) {
                              updateDB = new C_BE();
                           }

                           updateDB.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrint")) {
                           if (noPrint == null) {
                              noPrint = new C_BE();
                           }

                           noPrint.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noCheck")) {
                           if (noCheck == null) {
                              noCheck = new C_BE();
                           }

                           noCheck.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintCheck")) {
                           if (noPrintCheck == null) {
                              noPrintCheck = new C_BE();
                           }

                           noPrintCheck.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new C_BE();
                           }

                           monoProbs.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("logPrint")) {
                           if (logPrint == null) {
                              logPrint = new C_BE();
                           }

                           logPrint.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("logSubmit")) {
                           if (logSubmit == null) {
                              logSubmit = new C_BE();
                           }

                           logSubmit.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("needPrint")) {
                           if (needPrint == null) {
                              needPrint = new C_BE();
                           }

                           needPrint.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("needSubmit")) {
                           if (needSubmit == null) {
                              needSubmit = new C_BE();
                           }

                           needSubmit.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("mainOnly")) {
                           if (mainOnly == null) {
                              mainOnly = new C_BE();
                           }

                           mainOnly.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("prefix")) {
                           s = s2.substring(k + 1);
                        } else if (s3.equalsIgnoreCase("termprefix")) {
                           String s4 = C_KC.f471 + "." + C_KC.f472 + ".";
                           if (C_KC.f474.length() > 0) {
                              s4 = s4 + C_KC.f474 + ".";
                           }

                           s = s4 + s2.substring(k + 1);
                        }
                     }
                  }
               }
            }
         }

         c_xd.m1470();
      }
   }

   static void logNeeds() {
      if (needPrint != null && !needPrint.m402()) {
         if (logPrint == null) {
            logPrint = new C_BE();
         }

         logPrint.m395(needPrint);
      }

      if (needSubmit != null && !needSubmit.m402()) {
         if (logSubmit == null) {
            logSubmit = new C_BE();
         }

         logSubmit.m395(needSubmit);
      }
   }

   void loadProblem(String s) {
      C_XD c_xd = new C_XD(s);
      this.loadExerciseInfo(c_xd);
      this.problem.m2193(c_xd);
      this.errorCount = c_xd.m1498();
      this.workTime = c_xd.m1499();
      this.updateWorkTime();
      this.problemIndex = -1;
   }

   long updateWorkTime() {
      long i = (new Date().getTime() + 500L) / 1000L;
      if (this.loadTime != 0L) {
         this.workTime = this.workTime + (i - this.loadTime);
      }

      this.loadTime = i;
      return this.workTime;
   }

   void loadUserProblem(String s) {
      this.loadProblem(C_XD.m1509(C_XD.m1508(s, '=')));
   }

   void removeWork() {
      this.problem.m2189();
   }

   static String removeWork(C_XD c_xd) {
      return c_xd.m1484("$=%u!");
   }

   void newProblem() {
      this.loadProblem(newProblem);
   }

   void check() {
      C_c_B c_c_b = this.problem.m2192();
      if (!this.checkDisabled) {
         Hashtable hashtable = c_c_b.f428;
         this.titlePanel.m1825(hashtable == null ? "" : (String)hashtable.get("summary"));
      }
   }

   static int[] getExerciseIndices() {
      if (problems != null && exercises != null) {
         C_e_ c_e_ = new C_e_();
         int i = problems.size();

         for (int j = 0; j < i; j++) {
            if (isExercise(C_XD.m1493(problems.m1778(j)))) {
               c_e_.m1749(j);
            }
         }

         return c_e_.m1752();
      } else {
         return null;
      }
   }

   void submitExam() {
      if (C_u_C.m2105("Parsing")) {
         int[] aint = getExerciseIndices();
         C_x_A c_x_a = new C_x_A(this);
         C_0A c_0a = C_KC.m835(c_x_a);
         if (c_0a != null) {
            if (C_KA.m756(this, null)) {
               submit(c_0a, aint, c_x_a);
               C_KC.m838(c_0a, c_x_a);
               C_j_C.m1873(c_0a);
            } else {
               C_KC.m838(c_0a, c_x_a);
            }
         }
      }
   }

   void submitProblems() {
      C_x_A c_x_a = new C_x_A(this);
      C_0A c_0a = C_KC.m835(c_x_a);
      if (c_0a != null) {
         int[] aint = C_KA.m750(this);
         if (aint == null) {
            C_KC.m838(c_0a, c_x_a);
         } else {
            submit(c_0a, aint, c_x_a);
            C_KC.m838(c_0a, c_x_a);
            C_j_C.m1873(c_0a);
         }
      }
   }

   static void submit(C_0A c_0a, int[] aint, C_x_A c_x_a) {
      synchronized (problems) {
         Vector vector = new Vector();
         Vector vector1 = new Vector();
         int i = aint.length;

         for (int j = 0; j < i; j++) {
            c_0a.m1();
            String s = problems.m1778(aint[j]);
            C_XD c_xd = new C_XD(s);
            String s1 = getProblemStatement(c_xd);
            c_0a.f7 = C_z_D.m2225(s1 == null ? "" : s1.trim());
            int k = getProblemState(s);
            c_0a.f6 = C_l_D.f1128[k];
            c_0a.f8 = s;
            c_0a.f9 = c_xd.m1494();
            c_0a.f10 = moduleAbbrs[moduleIndex];
            c_0a.f11 = c_xd.m1498();
            c_0a.f12 = c_xd.m1499();
            boolean flag = LogicProgram.m1060(logSubmit, getExerciseTitle(c_0a.f9));
            if (C_KC.m836(c_0a, c_x_a)) {
               vector.addElement(trimTitle(c_0a.f9));
               if (flag) {
                  LogicProgram.m1083("pardata.txt", "S", s, c_0a.m3());
               }
            } else {
               vector1.addElement(trimTitle(c_0a.f9));
               if (flag) {
                  LogicProgram.m1082("pardata.txt", "F", s);
               }
            }
         }

         c_0a.m1();
         vector.copyInto(c_0a.f15 = new String[vector.size()]);
         vector1.copyInto(c_0a.f16 = new String[vector1.size()]);
      }
   }

   void uploadProblems() {
      C_x_A c_x_a = new C_x_A(this);
      C_LD c_ld = C_KC.m840(c_x_a);
      if (c_ld != null) {
         c_ld.f520 = null;
         int[] aint = C_KA.m751(this);
         if (aint == null) {
            C_KC.m843(c_ld, c_x_a);
         } else {
            upload(c_ld, aint, c_x_a);
            C_KC.m843(c_ld, c_x_a);
            C_j_C.m1875(c_ld);
         }
      }
   }

   static void upload(C_LD c_ld, int[] aint, C_x_A c_x_a) {
      synchronized (problems) {
         Vector vector = new Vector();
         Vector vector1 = new Vector();
         int i = aint.length;

         for (int j = 0; j < i; j++) {
            c_ld.m944();
            String s = problems.m1778(aint[j]);
            C_f_F c_f_f = problems.m1779(aint[j]);
            C_XD c_xd = new C_XD(s);
            String s1 = getProblemStatement(c_xd);
            c_ld.f510 = c_xd.m1494();
            c_ld.f511 = s1;
            c_ld.f512 = LogicProgram.m995(s1, maggie, html);
            c_ld.f514 = null;
            c_ld.f513 = moduleAbbrs[moduleIndex];
            c_ld.f515 = null;
            if (c_f_f != null && c_f_f.f1120 == 2 && !isExercise(c_ld.f510) && c_xd.m1497() == null && C_KC.m841(c_ld, c_x_a)) {
               vector.addElement(trimTitle(c_ld.f510));
            } else {
               vector1.addElement(trimTitle(c_ld.f510));
            }
         }

         vector.copyInto(c_ld.f518 = new String[vector.size()]);
         vector1.copyInto(c_ld.f519 = new String[vector1.size()]);
      }
   }

   String saveProblem() {
      String s = this.problem.m2194();
      if (this.errorCount != 0) {
         s = s + this.errorCount + "`e";
      }

      if (this.updateWorkTime() != 0L) {
         s = s + this.workTime + "`t";
      }

      return C_XD.m1509(s);
   }

   static boolean saveProblems() {
      if (!LogicProgram.m976()) {
         return false;
      } else {
         try {
            writeProblems(problems, new FileWriter(new File(LogicProgram.f555, "parwork.txt")));
            return true;
         } catch (IOException ioexception) {
            LogicProgram.m971("not004", "parwork.txt");
            return false;
         }
      }
   }

   boolean saveRenamed(String s) {
      if (s == null) {
         return true;
      } else {
         String s1 = this.problem.f1458;
         int i = this.problemIndex;
         this.problemIndex = -1;
         if (!this.saveProblems(s, true)) {
            this.problemIndex = i;
            this.setProblemTitle(s1);
            return false;
         } else {
            return true;
         }
      }
   }

   boolean saveProblems(String s) {
      return this.dontChange ? this.saveRenamed(s) : this.saveProblems(s, false);
   }

   boolean saveProblems(String s, boolean flag) {
      if (s == null) {
         return true;
      } else {
         String s1 = null;
         synchronized (problems) {
            if (this.problemIndex == -1) {
               String s2 = C_KA.m757(flag ? this.problem.f1458 : null);
               if (s2 == null) {
                  return false;
               }

               this.setProblemTitle(s2);
               C_l_D c_l_d = new C_l_D(C_XD.m1495(s, s2), false);
               this.problemIndex = problems.m1771(c_l_d, false);
               this.problemIndex = this.problemIndex == -1 ? problems.size() : this.problemIndex + 1;
               problems.insertElementAt(c_l_d, this.problemIndex);
            } else {
               s1 = problems.m1778(this.problemIndex);
               problems.m1776(s, this.problemIndex);
            }

            if (!saveProblems()) {
               if (s1 == null) {
                  problems.m1101(this.problemIndex);
                  this.problemIndex = -1;
               } else {
                  problems.m1776(s1, this.problemIndex);
               }

               return false;
            } else {
               return true;
            }
         }
      }
   }

   void setProblemTitle(String s) {
      if (s != null && !(s = s.trim()).equals("")) {
         this.problem.f1458 = s;
         this.titlePanel.m1821(trimTitle(s));
      } else {
         this.problem.f1458 = null;
         this.titlePanel.m1821(null);
      }
   }

   static String trimTitle(String s) {
      if (s == null) {
         return null;
      } else {
         return isExercise(s) ? LogicProgram.m1000(s) : s.trim();
      }
   }

   String getChangedProblem() {
      String s = this.saveProblem();
      String s1 = this.problemIndex == -1 ? newProblem : problems.m1778(this.problemIndex);
      return C_XD.m1500(s).equals(C_XD.m1500(s1)) ? null : s;
   }

   static String getProblemStatement(String s) {
      return getProblemStatement(new C_XD(s));
   }

   static String getProblemStatement(C_XD c_xd) {
      return c_xd.m1483(c_xd.m1478("="));
   }

   static int getProblemState(String s) {
      C_XD c_xd = new C_XD(s);
      return !hasWork(c_xd) ? 0 : getProblemState_static(c_xd);
   }

   static int getProblemState_static(C_XD c_xd) {
      if (!hasWork(c_xd)) {
         return 0;
      } else {
         String s = c_xd.m1483(c_xd.m1475('='));
         if (s == null) {
            s = "";
         }

         String s1 = c_xd.m1483(c_xd.m1475('['));
         if (s1 == null) {
            s1 = "";
         }

         String s2 = c_xd.m1483(c_xd.m1475(']'));
         if (s2 == null) {
            s2 = "0";
         }

         String s3 = c_xd.m1483(c_xd.m1475('*'));
         C_DD c_dd = new C_DD(s);
         if (s3 != null) {
            return s1.equals(c_dd.m464()) && s3.charAt(0) == 84 ? 2 : 1;
         } else {
            return s1.equals(c_dd.m464()) && s2.equals(c_dd.m465()) ? 2 : 1;
         }
      }
   }

   @Override
   int getProblemState(C_XD c_xd) {
      return getProblemState_static(c_xd);
   }

   static boolean hasWork(String s) {
      return hasWork(new C_XD(s));
   }

   static boolean hasWork(C_XD c_xd) {
      return c_xd.m1475('[') != -1 || c_xd.m1475(']') != -1 || c_xd.m1475('*') != -1;
   }

   static String getWork(C_XD c_xd) {
      return c_xd.m1484("[]*");
   }

   static Vector getStatements(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         C_f_F c_f_f = problems.m1779(aint[j]);
         C_XD c_xd = new C_XD(c_f_f.f1119);
         String s = c_xd.m1494();
         String s1 = getProblemStatement(c_xd);
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new C_u_(null, 1, new int[]{dimension.width}));
         C_NC c_nc;
         jpanel.add(c_nc = new C_NC(LogicProgram.m1004("\\l" + s + ": " + s1)));
         c_nc.setLineWrap(true);
         c_nc.setWrapStyleWord(true);
         c_nc.setBackground(LogicProgram.f605[1]);
         vector.add(jpanel);
      }

      return vector;
   }

   static Vector getResults(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         C_f_F c_f_f = problems.m1779(aint[j]);
         int k = c_f_f.f1120;
         C_XD c_xd = new C_XD(c_f_f.f1119);
         String s = c_xd.m1494();
         if (!printIncorrect || k == 1) {
            String s1 = getProblemStatement(c_xd);
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new C_u_(null, 2, new int[]{20, dimension.width - 20}));
            jpanel.add(new C_f_E(C_l_D.f1128[k]));
            C_NC c_nc;
            jpanel.add(c_nc = new C_NC(LogicProgram.m1004("\\l" + s + ": " + s1)));
            c_nc.setLineWrap(true);
            c_nc.setWrapStyleWord(true);
            c_nc.setBackground(LogicProgram.f605[1]);
            vector.add(jpanel);
         }

         if (LogicProgram.m1060(logPrint, getExerciseTitle(s))) {
            LogicProgram.m1082("pardata.txt", "R", c_f_f.f1119);
         }
      }

      return vector;
   }

   static class C__A extends C_U.C__A {
      C__A(C_x_A c_x_a, Rectangle rectangle, String s) {
         super(c_x_a, rectangle, s);
      }

      @Override
      public void run() {
         LPParsing.allocateParModule(this);
      }

      @Override
      public void m959() {
         LPParsing.continueStartup();
      }
   }
}
