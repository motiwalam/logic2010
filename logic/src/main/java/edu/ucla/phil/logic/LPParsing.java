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

class LPParsing extends LogicModule implements LogicConstants {
   SizedPanel scrollPanel;
   C_y_D problem;
   int fontSize;
   Font font;
   static final String workFileName = "parwork.txt";
   static final String logFileName = "pardata.txt";
   static final String digestVersKey = "parDigestVers";
   static Class messageClass = C_ND.class;
   static C_ZC exercises = null;
   static C_ZC problems = null;
   static Vector instances = new Vector();
   static C_c_C printQueue = new C_c_C("Parsing");
   static ProblemSelector autoCheck = null;
   static ProblemSelector addToDB = null;
   static ProblemSelector updateDB = null;
   static ProblemSelector noPrint = null;
   static ProblemSelector noCheck = null;
   static ProblemSelector noPrintCheck = null;
   static ProblemSelector monoProbs = null;
   static ProblemSelector logPrint = null;
   static ProblemSelector logSubmit = null;
   static ProblemSelector needPrint = null;
   static ProblemSelector needSubmit = null;
   static ProblemSelector mainOnly = null;
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
      if (!C_ND.loadMessages()) {
         LogicProgram.m971("not001", "the parsing messages file");
         return false;
      } else {
         resetOptions();
         readOptions(LogicProgram.openDataFile("options", false));
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
         ScrambledReader scrambledreader = LogicProgram.openDataFile("parwork.txt", false);
         if (scrambledreader == null) {
            LogicProgram.m971("not001", "the core Parsing exercise file");
            return null;
         }

         if (!readProblems(scrambledreader, c_zc, true)) {
            LogicProgram.m971("not002", "the core Parsing exercise file");
            return null;
         }
      }

      if (!flag1) {
         ScrambledReader scrambledreader1 = LogicProgram.m1065("parwork.txt", flag2);
         if (scrambledreader1 != null && (flag ? !readProblems(scrambledreader1, c_zc, true) : !mergeProblems(scrambledreader1, c_zc, true))) {
            LogicProgram.m971("not002", "the local Parsing exercise file");
            return null;
         }
      }

      return c_zc;
   }

   LPParsing(boolean flag) {
      super(flag);
      this.fontSize = LogicProgram.fontSize;
      this.setLayout(new BorderLayout());
      this.add(this.titlePanel, "North");
      this.add(this.scroller = new JScrollPane(), "Center");
      this.scroller.getVerticalScrollBar().setUnitIncrement(22);
      this.scroller.setViewportView(this.scrollPanel = new SizedPanel());
      this.scrollPanel.add(this.problem = new C_y_D(this), "Center");
      this.add(C_RB.m1201(this), "South");
      this.newProblem();
   }

   @Override
   int getModuleIndex() {
      return moduleIndex;
   }

   static synchronized void startup(Rectangle rectangle, BusyIndicator busyindicator, String s) {
      if (startups == null) {
         startups = new Vector();
      }

      if (exercises != null || getExercises()) {
         LPParsing.C__A lpparsing$c__a = new LPParsing.C__A(busyindicator, rectangle, s);
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
         ((LogicModule.C__A)startups.get(i - 1 - j)).f782.m2162(false);
         ((LogicModule.C__A)startups.get(i - 1 - j)).f782 = null;
      }

      while (!startups.isEmpty()) {
         LogicModule.C__A logicmodule$c__a = (LogicModule.C__A)startups.remove(0);
         SwingUtilities.invokeLater(logicmodule$c__a);
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

   static void allocateParModule(LogicModule.C__A logicmodule$c__a) {
      LPParsing lpparsing = new LPParsing(false);
      instances.addElement(lpparsing);
      if (logicmodule$c__a.f784 == null || newProblem == null) {
         lpparsing.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lpparsing.saveProblem();
         }
      }

      if (logicmodule$c__a.f784 != null) {
         lpparsing.loadProblem(logicmodule$c__a.f784);
         logicmodule$c__a.f784 = null;
      }

      lpparsing.setupFrame(LPInfo.programName + ": Parsing");
      lpparsing.frame.setBounds(lpparsing.fixModuleRect(logicmodule$c__a.f783));
      lpparsing.frame.setVisible(true);
      lpparsing.requestFocus();
   }

   static void insertDBProbs(ProblemSelector problemselector) {
      if (problemselector != null && !problemselector.m402() && UserSetup.m2101("addToDB", "developer")) {
         int i = exercises.size();

         for (int j = 0; j < i; j++) {
            TaggedRecord taggedrecord = new TaggedRecord(exercises.m1778(j));
            String s = taggedrecord.getName();
            if (problemselector.m404(s)) {
               if (DiagnosticsLog.out != null) {
                  DiagnosticsLog.out.println(LogicProgram.utcTimestamp());
                  DiagnosticsLog.out.println("adding " + s);
               }

               String s1 = getProblemStatement(taggedrecord);
               String s2 = taggedrecord.valueAt(taggedrecord.indexOfTag('!'));
               if (s2 != null && s2.length() > 255) {
                  s2 = s2.substring(0, 252) + "...";
               }

               String s3 = Scrambler.md5Base64(s1.trim());
               String s4 = LogicProgram.m995(s1, maggie, html);
               String s5 = taggedrecord.valueAt(taggedrecord.indexOfTag('C'));
               if (s5 == null) {
                  s5 = s;
               }

               String s6 = "insert into logic_problem (COMMENT,DTCREATION,PROBLEM_NAME,TPROBLEM,TPROBLEM_MD5,TWEB_FORM_PROBLEM,VERSION,SYNTAX,COMMON_NAME)";
               s6 = s6 + " values (" + ServerConnection.m815(s2) + ",GETDATE()," + ServerConnection.m815(s) + "," + ServerConnection.m815(s1) + ",";
               s6 = s6 + ServerConnection.m815(s3) + "," + ServerConnection.m815(s4) + "," + nameVersion(s) + "," + FormulaParser.getSyntax() + ",";
               s6 = s6 + ServerConnection.m815(s5) + ")";
               ServerConnection.m814(s6);
            }
         }
      }
   }

   static void updateDBProbs(ProblemSelector problemselector) {
      if (problemselector != null && !problemselector.m402() && UserSetup.m2101("addToDB", "developer")) {
         int i = exercises.size();

         for (int j = 0; j < i; j++) {
            TaggedRecord taggedrecord = new TaggedRecord(exercises.m1778(j));
            String s = taggedrecord.getName();
            if (problemselector.m404(s)) {
               if (DiagnosticsLog.out != null) {
                  DiagnosticsLog.out.println(LogicProgram.utcTimestamp());
                  DiagnosticsLog.out.println("updating " + s);
               }

               String s1 = getProblemStatement(taggedrecord);
               String s2 = taggedrecord.valueAt(taggedrecord.indexOfTag('!'));
               if (s2 != null && s2.length() > 255) {
                  s2 = s2.substring(0, 252) + "...";
               }

               String s3 = Scrambler.md5Base64(s1.trim());
               String s4 = LogicProgram.m995(s1, maggie, html);
               String s5 = taggedrecord.valueAt(taggedrecord.indexOfTag('C'));
               if (s5 == null) {
                  s5 = s;
               }

               String s6 = "update logic_problem set tproblem = " + ServerConnection.m815(s1) + ", tproblem_md5 = " + ServerConnection.m815(s3);
               s6 = s6 + ", tweb_form_problem = " + ServerConnection.m815(s4) + ", comment = " + ServerConnection.m815(s2);
               s6 = s6 + ", version = " + nameVersion(s) + ", common_name = " + ServerConnection.m815(s5);
               s6 = s6 + " where problem_name = " + ServerConnection.m815(s) + " and syntax = " + FormulaParser.getSyntax();
               ServerConnection.m814(s6);
            }
         }
      }
   }

   static String nameVersion(String s) {
      return s != null && s.toLowerCase().startsWith("pars") ? "1" : "NULL";
   }

   void setupFrame(String s) {
      this.frame = new ModuleFrame(s);
      this.setFontSize(this.fontSize);
      this.setColors(this.colors);
      this.frame.module = this;
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
      readOptions(LogicProgram.openDataFile("options", false));
      readOptions(LogicProgram.m1065("options", false));
      logNeeds();
      if (needPrint != null && !needPrint.m402() || needSubmit != null && !needSubmit.m402()) {
         C_a_A c_a_a = new C_a_A(readWork());
         c_a_a.m1634(readExercises());
         MainMenu.m2215(hashtable, "pardata.txt", "R", needPrint, c_a_a);
         c_a_a.m1635();
         MainMenu.m2215(hashtable1, "pardata.txt", "S", needSubmit, c_a_a);
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
            if ((vector == null || !vector.contains(s)) && hasWork(new TaggedRecord(s))) {
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
      this.font = LogicProgram.getFont(i);
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
            if (c_zc.f1079 && !c_zc.m1777(LogicProgram.user).equals(c_zc.f1076)) {
               System.out.println("Could not digest file: parwork.txt");
               if (!UserSetup.m2101("indigestion", "instructor")) {
                  LogicProgram.m971("not003", "parwork.txt");
                  return false;
               }
            }

            problems = c_zc;
            C_l_D.f1254 = ProblemEntry.m1816("parwork.txt", problems);
            ProblemEntry.m1815(exercises, C_l_D.f1254);
            return true;
         }
      }
   }

   static C_ZC readWork() {
      if (!LogicProgram.m976()) {
         return null;
      } else {
         C_ZC c_zc = new C_ZC();
         ScrambledReader scrambledreader = LogicProgram.openDataFile("parwork.txt", true);
         if (!LogicProgram.f584 || scrambledreader instanceof PlainRecordReader) {
            if (scrambledreader == null) {
               LogicProgram.m971("not001", "parwork.txt");
               return null;
            }

            if (scrambledreader instanceof PlainRecordReader) {
               c_zc.f1079 = true;
            }

            if (!readProblems(scrambledreader, c_zc, false)) {
               LogicProgram.m971("not002", "parwork.txt");
               return null;
            }
         }

         if (!(scrambledreader instanceof PlainRecordReader)) {
            scrambledreader = LogicProgram.m1065("parwork.txt", false);
            if (scrambledreader != null && !mergeProblems(scrambledreader, c_zc, false)) {
               LogicProgram.m971("not002", "parwork.txt");
               return null;
            }
         }

         return c_zc;
      }
   }

   static String getExerciseTitle(String s) {
      String s1;
      return exercises != null && (s1 = exercises.m1780(s)) != null ? TaggedRecord.m1493(s1) : null;
   }

   static boolean isExercise(String s) {
      return exercises != null && s != null && exercises.m1780(s) != null;
   }

   static boolean isExample(String s) {
      return exercises != null && s != null && TaggedRecord.m1502(exercises.m1780(s));
   }

   void loadExerciseInfo(TaggedRecord taggedrecord) {
      taggedrecord = new TaggedRecord(exercises == null ? null : exercises.m1780(taggedrecord.getName()));
      this.probOptions = taggedrecord.m1506('%');
      this.dontChange = this.probOptions != null && this.probOptions.containsKey("eg");
      this.titlePanel.m1827(taggedrecord.valueAt(taggedrecord.indexOfTag('!')));
      String s = taggedrecord.getName();
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
      return LogicModule.readProblems(reader, c_zc, flag, flag1);
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         TaggedRecord taggedrecord = new TaggedRecord(reader, true);
         String s = "";

         while (taggedrecord.readNext()) {
            String s1 = taggedrecord.getName();
            if (s1 != null && s1.trim().equalsIgnoreCase("parsing")) {
               int[] aint = taggedrecord.m1481("+?");
               int i = aint.length;

               for (int j = 0; j < i; j++) {
                  char c0 = taggedrecord.tagAt(aint[j]);
                  String s2 = taggedrecord.valueAt(aint[j]);
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
                              autoCheck = new ProblemSelector();
                           }

                           autoCheck.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("addToDB")) {
                           if (addToDB == null) {
                              addToDB = new ProblemSelector();
                           }

                           addToDB.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("updateDB")) {
                           if (updateDB == null) {
                              updateDB = new ProblemSelector();
                           }

                           updateDB.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrint")) {
                           if (noPrint == null) {
                              noPrint = new ProblemSelector();
                           }

                           noPrint.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noCheck")) {
                           if (noCheck == null) {
                              noCheck = new ProblemSelector();
                           }

                           noCheck.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintCheck")) {
                           if (noPrintCheck == null) {
                              noPrintCheck = new ProblemSelector();
                           }

                           noPrintCheck.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new ProblemSelector();
                           }

                           monoProbs.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("logPrint")) {
                           if (logPrint == null) {
                              logPrint = new ProblemSelector();
                           }

                           logPrint.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("logSubmit")) {
                           if (logSubmit == null) {
                              logSubmit = new ProblemSelector();
                           }

                           logSubmit.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("needPrint")) {
                           if (needPrint == null) {
                              needPrint = new ProblemSelector();
                           }

                           needPrint.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("needSubmit")) {
                           if (needSubmit == null) {
                              needSubmit = new ProblemSelector();
                           }

                           needSubmit.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("mainOnly")) {
                           if (mainOnly == null) {
                              mainOnly = new ProblemSelector();
                           }

                           mainOnly.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("prefix")) {
                           s = s2.substring(k + 1);
                        } else if (s3.equalsIgnoreCase("termprefix")) {
                           String s4 = ServerConnection.institution + "." + ServerConnection.term + ".";
                           if (ServerConnection.ident.length() > 0) {
                              s4 = s4 + ServerConnection.ident + ".";
                           }

                           s = s4 + s2.substring(k + 1);
                        }
                     }
                  }
               }
            }
         }

         taggedrecord.close();
      }
   }

   static void logNeeds() {
      if (needPrint != null && !needPrint.m402()) {
         if (logPrint == null) {
            logPrint = new ProblemSelector();
         }

         logPrint.m395(needPrint);
      }

      if (needSubmit != null && !needSubmit.m402()) {
         if (logSubmit == null) {
            logSubmit = new ProblemSelector();
         }

         logSubmit.m395(needSubmit);
      }
   }

   void loadProblem(String s) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      this.loadExerciseInfo(taggedrecord);
      this.problem.m2193(taggedrecord);
      this.errorCount = taggedrecord.m1498();
      this.workTime = taggedrecord.m1499();
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
      this.loadProblem(TaggedRecord.m1509(TaggedRecord.m1508(s, '=')));
   }

   void removeWork() {
      this.problem.m2189();
   }

   static String removeWork(TaggedRecord taggedrecord) {
      return taggedrecord.m1484("$=%u!");
   }

   void newProblem() {
      this.loadProblem(newProblem);
   }

   void check() {
      ErrorRef errorref = this.problem.m2192();
      if (!this.checkDisabled) {
         Hashtable hashtable = errorref.f428;
         this.titlePanel.m1825(hashtable == null ? "" : (String)hashtable.get("summary"));
      }
   }

   static int[] getExerciseIndices() {
      if (problems != null && exercises != null) {
         ExpressionPath expressionpath = new ExpressionPath();
         int i = problems.size();

         for (int j = 0; j < i; j++) {
            if (isExercise(TaggedRecord.m1493(problems.m1778(j)))) {
               expressionpath.m1749(j);
            }
         }

         return expressionpath.m1752();
      } else {
         return null;
      }
   }

   void submitExam() {
      if (UserSetup.m2105("Parsing")) {
         int[] aint = getExerciseIndices();
         BusyIndicator busyindicator = new BusyIndicator(this);
         Submission submission = ServerConnection.prepareSubmission(busyindicator);
         if (submission != null) {
            if (C_KA.m756(this, null)) {
               submit(submission, aint, busyindicator);
               ServerConnection.m838(submission, busyindicator);
               AccountManager.m1873(submission);
            } else {
               ServerConnection.m838(submission, busyindicator);
            }
         }
      }
   }

   void submitProblems() {
      BusyIndicator busyindicator = new BusyIndicator(this);
      Submission submission = ServerConnection.prepareSubmission(busyindicator);
      if (submission != null) {
         int[] aint = C_KA.m750(this);
         if (aint == null) {
            ServerConnection.m838(submission, busyindicator);
         } else {
            submit(submission, aint, busyindicator);
            ServerConnection.m838(submission, busyindicator);
            AccountManager.m1873(submission);
         }
      }
   }

   static void submit(Submission submission, int[] aint, BusyIndicator busyindicator) {
      synchronized (problems) {
         Vector vector = new Vector();
         Vector vector1 = new Vector();
         int i = aint.length;

         for (int j = 0; j < i; j++) {
            submission.m1();
            String s = problems.m1778(aint[j]);
            TaggedRecord taggedrecord = new TaggedRecord(s);
            String s1 = getProblemStatement(taggedrecord);
            submission.problemMd5 = Scrambler.md5Base64(s1 == null ? "" : s1.trim());
            int k = getProblemState(s);
            submission.evaluation = C_l_D.f1128[k];
            submission.work = s;
            submission.problemName = taggedrecord.getName();
            submission.module = moduleAbbrs[moduleIndex];
            submission.helpCount = taggedrecord.m1498();
            submission.duration = taggedrecord.m1499();
            boolean flag = LogicProgram.m1060(logSubmit, getExerciseTitle(submission.problemName));
            if (ServerConnection.submit(submission, busyindicator)) {
               vector.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.m1083("pardata.txt", "S", s, submission.m3());
               }
            } else {
               vector1.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.m1082("pardata.txt", "F", s);
               }
            }
         }

         submission.m1();
         vector.copyInto(submission.f15 = new String[vector.size()]);
         vector1.copyInto(submission.f16 = new String[vector1.size()]);
      }
   }

   void uploadProblems() {
      BusyIndicator busyindicator = new BusyIndicator(this);
      C_LD c_ld = ServerConnection.m840(busyindicator);
      if (c_ld != null) {
         c_ld.f520 = null;
         int[] aint = C_KA.m751(this);
         if (aint == null) {
            ServerConnection.m843(c_ld, busyindicator);
         } else {
            upload(c_ld, aint, busyindicator);
            ServerConnection.m843(c_ld, busyindicator);
            AccountManager.m1875(c_ld);
         }
      }
   }

   static void upload(C_LD c_ld, int[] aint, BusyIndicator busyindicator) {
      synchronized (problems) {
         Vector vector = new Vector();
         Vector vector1 = new Vector();
         int i = aint.length;

         for (int j = 0; j < i; j++) {
            c_ld.m944();
            String s = problems.m1778(aint[j]);
            ProblemEntry problementry = problems.m1779(aint[j]);
            TaggedRecord taggedrecord = new TaggedRecord(s);
            String s1 = getProblemStatement(taggedrecord);
            c_ld.f510 = taggedrecord.getName();
            c_ld.f511 = s1;
            c_ld.f512 = LogicProgram.m995(s1, maggie, html);
            c_ld.f514 = null;
            c_ld.f513 = moduleAbbrs[moduleIndex];
            c_ld.f515 = null;
            if (problementry != null
               && problementry.state == 2
               && !isExercise(c_ld.f510)
               && taggedrecord.m1497() == null
               && ServerConnection.m841(c_ld, busyindicator)) {
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

      return TaggedRecord.m1509(s);
   }

   static boolean saveProblems() {
      if (!LogicProgram.m976()) {
         return false;
      } else {
         try {
            writeProblems(problems, new FileWriter(new File(LogicProgram.workDir, "parwork.txt")));
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
               C_l_D c_l_d = new C_l_D(TaggedRecord.m1495(s, s2), false);
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
      return TaggedRecord.m1500(s).equals(TaggedRecord.m1500(s1)) ? null : s;
   }

   static String getProblemStatement(String s) {
      return getProblemStatement(new TaggedRecord(s));
   }

   static String getProblemStatement(TaggedRecord taggedrecord) {
      return taggedrecord.valueAt(taggedrecord.m1478("="));
   }

   static int getProblemState(String s) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      return !hasWork(taggedrecord) ? 0 : getProblemState_static(taggedrecord);
   }

   static int getProblemState_static(TaggedRecord taggedrecord) {
      if (!hasWork(taggedrecord)) {
         return 0;
      } else {
         String s = taggedrecord.valueAt(taggedrecord.indexOfTag('='));
         if (s == null) {
            s = "";
         }

         String s1 = taggedrecord.valueAt(taggedrecord.indexOfTag('['));
         if (s1 == null) {
            s1 = "";
         }

         String s2 = taggedrecord.valueAt(taggedrecord.indexOfTag(']'));
         if (s2 == null) {
            s2 = "0";
         }

         String s3 = taggedrecord.valueAt(taggedrecord.indexOfTag('*'));
         C_DD c_dd = new C_DD(s);
         if (s3 != null) {
            return s1.equals(c_dd.m464()) && s3.charAt(0) == 84 ? 2 : 1;
         } else {
            return s1.equals(c_dd.m464()) && s2.equals(c_dd.m465()) ? 2 : 1;
         }
      }
   }

   @Override
   int getProblemState(TaggedRecord taggedrecord) {
      return getProblemState_static(taggedrecord);
   }

   static boolean hasWork(String s) {
      return hasWork(new TaggedRecord(s));
   }

   static boolean hasWork(TaggedRecord taggedrecord) {
      return taggedrecord.indexOfTag('[') != -1 || taggedrecord.indexOfTag(']') != -1 || taggedrecord.indexOfTag('*') != -1;
   }

   static String getWork(TaggedRecord taggedrecord) {
      return taggedrecord.m1484("[]*");
   }

   static Vector getStatements(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         ProblemEntry problementry = problems.m1779(aint[j]);
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         String s1 = getProblemStatement(taggedrecord);
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
         ProblemEntry problementry = problems.m1779(aint[j]);
         int k = problementry.state;
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         if (!printIncorrect || k == 1) {
            String s1 = getProblemStatement(taggedrecord);
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
            LogicProgram.m1082("pardata.txt", "R", problementry.name);
         }
      }

      return vector;
   }

   static class C__A extends LogicModule.C__A {
      C__A(BusyIndicator busyindicator, Rectangle rectangle, String s) {
         super(busyindicator, rectangle, s);
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
