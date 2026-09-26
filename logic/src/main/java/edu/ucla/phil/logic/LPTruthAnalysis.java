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

class LPTruthAnalysis extends LogicModule {
   SizedPanel scrollPanel;
   C_k_E problem;
   int fontSize;
   Font font;
   Font errorFont;
   static final String workFileName = "truwork.txt";
   static final String logFileName = "trudata.txt";
   static final String digestVersKey = "truDigestVers";
   static Class messageClass = C_FE.class;
   static final int correct = 0;
   static final int incorrect = 1;
   static final int incomplete = 2;
   static C_O exercises = null;
   static C_O problems = null;
   static Vector instances = new Vector();
   static C_c_C printQueue = new C_c_C("Truth Tables");
   static ProblemSelector noCheck = null;
   static ProblemSelector noTreeErr = null;
   static ProblemSelector noTableErr = null;
   static ProblemSelector noSetupErr = null;
   static ProblemSelector noPrintCheck = null;
   static ProblemSelector noPrintTreeErr = null;
   static ProblemSelector noPrintTableErr = null;
   static ProblemSelector noPrintSetupErr = null;
   static ProblemSelector noCheckMess = null;
   static ProblemSelector noPrint = null;
   static ProblemSelector monoProbs = null;
   static ProblemSelector addToDB = null;
   static ProblemSelector updateDB = null;
   static ProblemSelector doAllNodes = null;
   static ProblemSelector doAllRows = null;
   static ProblemSelector doAllWffs = null;
   static ProblemSelector doSetUp = null;
   static ProblemSelector logPrint = null;
   static ProblemSelector logSubmit = null;
   static ProblemSelector needPrint = null;
   static ProblemSelector needSubmit = null;
   static boolean noUser = false;
   static boolean submitExam = false;
   static boolean printIncorrect = false;
   static boolean restating = false;
   static String newProblem = null;
   static Vector startups;
   boolean completeAllNodes;
   boolean completeAllRows;
   boolean completeAllWffs;
   boolean completeSetup;
   boolean assumeTautology;
   boolean checkDisabled;
   boolean treeErrorsDisabled;
   boolean tableErrorsDisabled;
   boolean setupErrorsDisabled;
   boolean checkMessagesDisabled;
   boolean dontChange;
   boolean noBeep;
   int errorCount;
   long workTime;
   long loadTime;
   String lastUserProblem;
   Hashtable probOptions;
   JScrollPane scroller;
   static int moduleIndex = 5;

   static boolean getExercises() {
      if (!C_FE.loadMessages()) {
         LogicProgram.m971("not001", "the truth analysis messages file");
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

   static C_O readExercises() {
      return readExercises(LogicProgram.f584, false, false);
   }

   static C_O readExercises(boolean flag, boolean flag1, boolean flag2) {
      C_O c_o = new C_O();
      if (!flag) {
         ScrambledReader scrambledreader = LogicProgram.openDataFile("truwork.txt", false);
         if (scrambledreader == null) {
            LogicProgram.m971("not001", "the core Truth Table exercise file");
            return null;
         }

         if (!readProblems(scrambledreader, c_o, true)) {
            LogicProgram.m971("not002", "the core Truth Table exercise file");
            return null;
         }
      }

      if (!flag1) {
         ScrambledReader scrambledreader1 = LogicProgram.m1065("truwork.txt", flag2);
         if (scrambledreader1 != null && (flag ? !readProblems(scrambledreader1, c_o, true) : !mergeProblems(scrambledreader1, c_o, true))) {
            LogicProgram.m971("not002", "the local Truth Table exercise file");
            return null;
         }
      }

      return c_o;
   }

   LPTruthAnalysis(boolean flag) {
      super(flag);
      this.fontSize = LogicProgram.fontSize;
      this.noBeep = true;
      this.completeAllNodes = false;
      this.completeAllRows = false;
      this.completeAllWffs = false;
      this.setLayout(new BorderLayout());
      this.add(this.titlePanel, "North");
      this.scroller = new JScrollPane();
      this.add(this.scroller, "Center");
      this.scroller.getVerticalScrollBar().setUnitIncrement(22);
      this.scroller.setViewportView(this.problem = new C_k_E(this));
      this.add(C_u_E.m2120(this), "South");
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
         LPTruthAnalysis.C__A lptruthanalysis$c__a = new LPTruthAnalysis.C__A(busyindicator, rectangle, s);
         startups.add(lptruthanalysis$c__a);
         if (startups.size() <= 1) {
            if (problems == null) {
               if (!getProblems()) {
                  lptruthanalysis$c__a.m1310();
                  startups.remove(lptruthanalysis$c__a);
                  return;
               }

               if (problems.m1773()) {
                  saveProblems();
               }

               problems.m1099(lptruthanalysis$c__a);
            } else {
               lptruthanalysis$c__a.m959();
            }
         }
      }
   }

   static synchronized void continueStartup() {
      restating = false;
      int i = startups.size();

      for (int j = 0; j < i; j++) {
         ((LogicModule.C__A)startups.get(i - 1 - j)).m1310();
      }

      while (!startups.isEmpty()) {
         LogicModule.C__A logicmodule$c__a = (LogicModule.C__A)startups.remove(0);
         SwingUtilities.invokeLater(logicmodule$c__a);
      }
   }

   Rectangle fixModuleRect(Rectangle rectangle) {
      C_u_E c_u_e = C_u_E.m2120(this);
      Dimension dimension = c_u_e.getPreferredSize();
      if (rectangle.width < dimension.width) {
         rectangle.width = dimension.width;
      }

      return rectangle;
   }

   static void allocateTruModule(LogicModule.C__A logicmodule$c__a) {
      LPTruthAnalysis lptruthanalysis = new LPTruthAnalysis(false);
      instances.addElement(lptruthanalysis);
      if (logicmodule$c__a.f784 == null || newProblem == null) {
         lptruthanalysis.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lptruthanalysis.saveProblem();
         }
      }

      if (logicmodule$c__a.f784 != null) {
         lptruthanalysis.loadProblem(logicmodule$c__a.f784);
         logicmodule$c__a.f784 = null;
      }

      lptruthanalysis.setupFrame(LPInfo.programName + ": Truth Tables");
      lptruthanalysis.frame.setBounds(lptruthanalysis.fixModuleRect(logicmodule$c__a.f783));
      lptruthanalysis.frame.setVisible(true);
      lptruthanalysis.requestFocus();
      lptruthanalysis.noBeep = false;
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
      return s != null && s.toLowerCase().startsWith("trutb") ? "1" : "NULL";
   }

   void setupFrame(String s) {
      this.frame = new ModuleFrame(s);
      this.setFontSize(this.fontSize);
      this.setColors(this.colors);
      this.frame.module = this;
      this.frame.add(this, "Center");
   }

   static synchronized boolean getProblems() {
      if (problems != null) {
         return true;
      } else {
         C_O c_o = readWork();
         if (c_o == null) {
            return false;
         } else {
            if (c_o.f1079 && !c_o.m1777(LogicProgram.user).equals(c_o.f1076)) {
               System.out.println("Could not digest file: truwork.txt");
               if (!UserSetup.m2101("indigestion", "instructor")) {
                  LogicProgram.m971("not003", "truwork.txt");
                  return false;
               }
            }

            problems = c_o;
            C_f_C.f1111 = ProblemEntry.m1816("truwork.txt", problems);
            ProblemEntry.m1815(exercises, C_f_C.f1111);
            return true;
         }
      }
   }

   static C_O readWork() {
      if (!LogicProgram.m976()) {
         return null;
      } else {
         C_O c_o = new C_O();
         ScrambledReader scrambledreader = LogicProgram.openDataFile("truwork.txt", true);
         if (!LogicProgram.f584 || scrambledreader instanceof PlainRecordReader) {
            if (scrambledreader == null) {
               LogicProgram.m971("not001", "truwork.txt");
               return null;
            }

            if (scrambledreader instanceof PlainRecordReader) {
               c_o.f1079 = true;
            }

            if (!readProblems(scrambledreader, c_o, false)) {
               LogicProgram.m971("not002", "truwork.txt");
               return null;
            }
         }

         if (!(scrambledreader instanceof PlainRecordReader)) {
            scrambledreader = LogicProgram.m1065("truwork.txt", false);
            if (scrambledreader != null && !mergeProblems(scrambledreader, c_o, false)) {
               LogicProgram.m971("not002", "truwork.txt");
               return null;
            }
         }

         return c_o;
      }
   }

   boolean hasWork() {
      if (this.completeSetup && this.problem.f1203 != null && this.problem.f1203.m1954()) {
         return true;
      } else if (this.problem.f1215 != -1) {
         return true;
      } else if (this.problem.f1201.f892 != -1) {
         return true;
      } else {
         int i = this.problem.f1201.f894;

         for (int j = 0; j < i; j++) {
            if (this.problem.f1201.m1525(j)) {
               return true;
            }
         }

         return false;
      }
   }

   static boolean hasWork(String s) {
      return hasWork(new TaggedRecord(s));
   }

   static boolean hasWork(TaggedRecord taggedrecord) {
      return taggedrecord.m1478("@*#&") != -1;
   }

   static String getWork(TaggedRecord taggedrecord) {
      return taggedrecord.m1484("@*#&");
   }

   static String removeWork(TaggedRecord taggedrecord) {
      return taggedrecord.m1484("$=%u!");
   }

   ErrorRef checkFull() {
      C_XF c_xf = this.problem.f1201;
      C_GD[][] ac_gd = c_xf.f890;
      int i = c_xf.f892;
      if (this.completeSetup) {
         if (this.problem.f1203 == null) {
            return new ErrorRef("truerr013", Message.params("summary", "Incomplete"));
         }

         if (!this.problem.f1219) {
            ErrorRef errorref = this.problem.f1203.m1956();
            if (errorref.f427 != null) {
               return errorref;
            }

            return new ErrorRef("truerr014", Message.params("summary", "Incomplete"));
         }
      }

      for (int j = 0; j < c_xf.f894; j++) {
         if (checkRowError(ac_gd[j])) {
            return new ErrorRef("truerr001", Message.params("summary", "Incorrect"));
         }
      }

      if (!this.assumeTautology) {
         if (this.problem.f1215 == 0) {
            if (i != -1) {
               return new ErrorRef("truerr004", Message.params("summary", "Incorrect"));
            }
         } else {
            if (this.problem.f1215 != 1) {
               return new ErrorRef("truerr003", Message.params("summary", "Incomplete"));
            }

            if (i == -1) {
               return new ErrorRef("truerr006", Message.params("summary", "Incomplete"));
            }
         }
      }

      if (this.assumeTautology || this.completeAllRows || this.problem.f1215 == 0) {
         for (int k = 0; k < c_xf.f894; k++) {
            if (!checkRowComplete(ac_gd[k]) && (this.completeAllWffs || !checkRowValid(ac_gd[k]))) {
               return new ErrorRef("truerr002", Message.params("summary", "Incomplete"));
            }
         }
      }

      if (this.assumeTautology) {
         return new ErrorRef(null, Message.params("summary", "Correct"));
      } else if (this.problem.f1215 == 0) {
         for (int l = 0; l < c_xf.f894; l++) {
            if (!checkRowValid(ac_gd[l])) {
               return new ErrorRef("truerr005", Message.params("summary", "Incorrect"));
            }
         }

         return new ErrorRef(null, Message.params("summary", "Correct"));
      } else if (!this.completeAllWffs && checkRowValid(ac_gd[i])) {
         return new ErrorRef("truerr008", Message.params("summary", "Incorrect"));
      } else if (!this.completeAllRows && !checkRowComplete(ac_gd[i])) {
         return new ErrorRef("truerr007", Message.params("summary", "Incomplete"));
      } else {
         return this.completeAllWffs && checkRowValid(ac_gd[i])
            ? new ErrorRef("truerr008", Message.params("summary", "Incorrect"))
            : new ErrorRef(null, Message.params("summary", "Correct"));
      }
   }

   boolean check() {
      return this.checkFull().f427 == null;
   }

   void checkProblem() {
      ErrorRef errorref = this.checkFull();
      if (errorref.f428 != null) {
         String s = (String)errorref.f428.get("summary");
         this.titlePanel.m1825(s == null ? "" : s);
      }

      if (errorref.f427 != null && !this.checkMessagesDisabled) {
         MessageDialog.showMessage(C_FE.get(errorref.f427), errorref.f428, null, null);
      }
   }

   static boolean checkRowError(C_GD[] ac_gd) {
      int i = ac_gd.length;

      for (int j = 0; j < i; j++) {
         if (ac_gd[j].f362) {
            return true;
         }
      }

      return false;
   }

   static boolean checkRowComplete(C_GD[] ac_gd) {
      int i = ac_gd.length;

      for (int j = 0; j < i; j++) {
         if (ac_gd[j].getText().equals("?")) {
            return false;
         }
      }

      return true;
   }

   static boolean checkRowValid(C_GD[] ac_gd) {
      int i = ac_gd.length;
      if (ac_gd[i - 1].getText().equals("T")) {
         return true;
      } else {
         for (int j = 0; j < i - 1; j++) {
            if (ac_gd[j].getText().equals("F")) {
               return true;
            }
         }

         return false;
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
      this.assumeTautology = this.probOptions != null && this.probOptions.containsKey("taut");
      this.titlePanel.m1827(taggedrecord.valueAt(taggedrecord.indexOfTag('!')));
      String s = taggedrecord.getName();
      this.completeAllNodes = LogicProgram.m1060(doAllNodes, s);
      this.completeAllRows = LogicProgram.m1060(doAllRows, s);
      this.completeAllWffs = LogicProgram.m1060(doAllWffs, s);
      this.completeSetup = LogicProgram.m1060(doSetUp, s);
      this.checkDisabled = LogicProgram.m1060(this.forPrint ? noPrintCheck : noCheck, s);
      this.treeErrorsDisabled = LogicProgram.m1060(this.forPrint ? noPrintTreeErr : noTreeErr, s);
      this.tableErrorsDisabled = LogicProgram.m1060(this.forPrint ? noPrintTableErr : noTableErr, s);
      this.setupErrorsDisabled = LogicProgram.m1060(this.forPrint ? noPrintSetupErr : noSetupErr, s);
      this.checkMessagesDisabled = LogicProgram.m1060(noCheckMess, s);
      this.tableErrorsDisabled = this.tableErrorsDisabled | this.checkDisabled;
      this.treeErrorsDisabled = this.treeErrorsDisabled | this.tableErrorsDisabled;
   }

   static boolean readProblems(Reader reader, C_O c_o, boolean flag) {
      return readProblems(reader, c_o, flag, false);
   }

   static boolean mergeProblems(Reader reader, C_O c_o, boolean flag) {
      return readProblems(reader, c_o, flag, true);
   }

   static boolean readProblems(Reader reader, C_O c_o, boolean flag, boolean flag1) {
      return LogicModule.readProblems(reader, c_o, flag, flag1);
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
      if (UserSetup.m2105("Truth Tables")) {
         int[] aint = getExerciseIndices();
         BusyIndicator busyindicator = new BusyIndicator(this);
         Submission submission = ServerConnection.prepareSubmission(busyindicator);
         if (submission != null) {
            if (C_w_A.m2148(this, null)) {
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
         int[] aint = C_w_A.m2142(this);
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
            submission.evaluation = C_f_C.f1128[k];
            submission.work = s;
            submission.problemName = taggedrecord.getName();
            submission.module = moduleAbbrs[moduleIndex];
            submission.helpCount = taggedrecord.m1498();
            submission.duration = taggedrecord.m1499();
            boolean flag = LogicProgram.m1060(logSubmit, getExerciseTitle(submission.problemName));
            if (ServerConnection.submit(submission, busyindicator)) {
               vector.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.m1083("trudata.txt", "S", s, submission.m3());
               }
            } else {
               vector1.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.m1082("trudata.txt", "F", s);
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
         int[] aint = C_w_A.m2143(this);
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
            c_ld.f514 = assumeTautology(taggedrecord) ? "taut" : null;
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

   static boolean assumeTautology(TaggedRecord taggedrecord) {
      int[] aint = taggedrecord.m1477('%');
      if (aint != null) {
         int i = aint.length;

         for (int j = 0; j < i; j++) {
            String s = taggedrecord.valueAt(aint[j]);
            if (s != null && s.equalsIgnoreCase("taut")) {
               return true;
            }
         }
      }

      return false;
   }

   String saveProblem() {
      String s = this.problem.m1912();
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
            writeProblems(problems, new FileWriter(new File(LogicProgram.workDir, "truwork.txt")));
            return true;
         } catch (IOException ioexception) {
            LogicProgram.m971("not004", "truwork.txt");
            return false;
         }
      }
   }

   boolean saveRenamed(String s) {
      if (s == null) {
         return true;
      } else {
         String s1 = this.problem.f1211;
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
               String s2 = C_w_A.m2137(this, flag ? this.problem.f1211 : null);
               if (s2 == null) {
                  return false;
               }

               this.setProblemTitle(s2);
               C_f_C c_f_c = new C_f_C(TaggedRecord.m1495(s, s2), false);
               this.problemIndex = problems.m1771(c_f_c, false);
               this.problemIndex = this.problemIndex == -1 ? problems.size() : this.problemIndex + 1;
               problems.insertElementAt(c_f_c, this.problemIndex);
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
      this.problem.m1913(s);
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

   @Override
   public boolean shutdown(boolean flag) {
      if (!flag && !C_w_A.m2148(this, null)) {
         return false;
      } else {
         this.reset();
         synchronized (moduleClasses[5]) {
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

   static synchronized LPTruthAnalysis openInstance(int i, boolean flag) {
      if (i >= 0 && instances != null && !instances.isEmpty()) {
         int j = instances.size();

         for (int k = 0; k < j; k++) {
            LPTruthAnalysis lptruthanalysis = (LPTruthAnalysis)instances.elementAt(k);
            if (lptruthanalysis.problemIndex == i) {
               if (flag) {
                  lptruthanalysis.requestFocus();
               }

               return lptruthanalysis;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Override
   public boolean save() {
      return C_w_A.m2148(this, null);
   }

   static void resetOptions() {
      noCheck = null;
      noTreeErr = null;
      noTableErr = null;
      noSetupErr = null;
      noPrintCheck = null;
      noPrintTreeErr = null;
      noPrintTableErr = null;
      noPrintSetupErr = null;
      noCheckMess = null;
      noPrint = null;
      monoProbs = null;
      addToDB = null;
      updateDB = null;
      doAllRows = null;
      doAllWffs = null;
      doSetUp = null;
      logPrint = null;
      logSubmit = null;
      needPrint = null;
      needSubmit = null;
      doAllNodes = null;
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
         MainMenu.m2215(hashtable, "trudata.txt", "P", needPrint, c_a_a);
         c_a_a.m1635();
         MainMenu.m2215(hashtable1, "trudata.txt", "S", needSubmit, c_a_a);
      }

      resetOptions();
      return true;
   }

   static Vector getChangedProblems() {
      C_a_A c_a_a = new C_a_A(readWork());
      Hashtable hashtable = LogicProgram.m1084("trudata.txt", "S", needSubmit, c_a_a);
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
         this.problem.doLayout();
      }
   }

   @Override
   public Frame getFrame() {
      return this.frame;
   }

   void setFontSize(int i) {
      this.fontSize = i;
      this.font = LogicProgram.getFont(i);
      this.errorFont = LogicProgram.getFont(i, 0);
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

   static int getProblemState(String s) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      return !hasWork(taggedrecord) ? 0 : new LPTruthAnalysis(false).getProblemState(taggedrecord);
   }

   @Override
   int getProblemState(TaggedRecord taggedrecord) {
      if (!hasWork(taggedrecord)) {
         return 0;
      } else {
         this.noBeep = true;
         this.problem.m1910(taggedrecord);
         return this.check() ? 2 : 1;
      }
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         TaggedRecord taggedrecord = new TaggedRecord(reader, true);
         String s = "";

         while (taggedrecord.readNext()) {
            String s1 = taggedrecord.getName();
            if (s1 != null && s1.trim().equalsIgnoreCase("truth")) {
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
                        if (s3.equalsIgnoreCase("addToDB")) {
                           if (addToDB == null) {
                              addToDB = new ProblemSelector();
                           }

                           addToDB.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("updateDB")) {
                           if (updateDB == null) {
                              updateDB = new ProblemSelector();
                           }

                           updateDB.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noCheck")) {
                           if (noCheck == null) {
                              noCheck = new ProblemSelector();
                           }

                           noCheck.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noTreeErr")) {
                           if (noTreeErr == null) {
                              noTreeErr = new ProblemSelector();
                           }

                           noTreeErr.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noTableErr")) {
                           if (noTableErr == null) {
                              noTableErr = new ProblemSelector();
                           }

                           noTableErr.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noSetupErr")) {
                           if (noSetupErr == null) {
                              noSetupErr = new ProblemSelector();
                           }

                           noSetupErr.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintCheck")) {
                           if (noPrintCheck == null) {
                              noPrintCheck = new ProblemSelector();
                           }

                           noPrintCheck.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintTreeErr")) {
                           if (noPrintTreeErr == null) {
                              noPrintTreeErr = new ProblemSelector();
                           }

                           noPrintTreeErr.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintTableErr")) {
                           if (noPrintTableErr == null) {
                              noPrintTableErr = new ProblemSelector();
                           }

                           noPrintTableErr.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintSetupErr")) {
                           if (noPrintSetupErr == null) {
                              noPrintSetupErr = new ProblemSelector();
                           }

                           noPrintSetupErr.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noCheckMess")) {
                           if (noCheckMess == null) {
                              noCheckMess = new ProblemSelector();
                           }

                           noCheckMess.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrint")) {
                           if (noPrint == null) {
                              noPrint = new ProblemSelector();
                           }

                           noPrint.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new ProblemSelector();
                           }

                           monoProbs.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("doAllRows")) {
                           if (doAllRows == null) {
                              doAllRows = new ProblemSelector();
                           }

                           doAllRows.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("doAllWffs")) {
                           if (doAllWffs == null) {
                              doAllWffs = new ProblemSelector();
                           }

                           doAllWffs.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("doSetUp")) {
                           if (doSetUp == null) {
                              doSetUp = new ProblemSelector();
                           }

                           doSetUp.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
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
                        } else if (s3.equalsIgnoreCase("doAllNodes")) {
                           if (doAllNodes == null) {
                              doAllNodes = new ProblemSelector();
                           }

                           doAllNodes.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
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
      this.problem.m1910(taggedrecord);
      this.errorCount = taggedrecord.m1498();
      this.workTime = taggedrecord.m1499();
      this.loadTime = 0L;
      this.updateWorkTime();
      this.problemIndex = -1;
      this.resize();
   }

   long updateWorkTime() {
      long i = (new Date().getTime() + 500L) / 1000L;
      if (this.loadTime != 0L) {
         this.workTime = this.workTime + (i - this.loadTime);
      }

      this.loadTime = i;
      return this.workTime;
   }

   void loadUserProblem(String s, boolean flag) {
      ArgumentParser argumentparser = new ArgumentParser(s);
      String s1 = argumentparser.m1384();
      if (s1 != null) {
         Hashtable hashtable = Message.params("expression", LogicProgram.m995(s1, maggie, kaplan));
         MessageDialog.showMessage(C_FE.get("truerr009"), hashtable, null, null);
      } else if (argumentparser.m1385() != 0) {
         MessageDialog.showMessage(C_FE.get("truerr010"), null, null, null);
      } else {
         String s2 = TaggedRecord.m1509(TaggedRecord.m1508(ArgumentParser.m1383(s), '='));
         if (flag) {
            s2 = s2 + "taut`%";
         }

         this.loadProblem(s2);
      }
   }

   void newProblem() {
      this.loadProblem(newProblem);
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

   static Vector getPrintProblems(int[] aint, Dimension dimension) {
      return getPrintProblems(aint, dimension, false);
   }

   static Vector getResults(int[] aint, Dimension dimension) {
      return getPrintProblems(aint, dimension, true);
   }

   static Vector getPrintProblems(int[] aint, Dimension dimension, boolean flag) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         ProblemEntry problementry = problems.m1779(aint[j]);
         int k = problementry.state;
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         if (printIncorrect && k != 1) {
            if (LogicProgram.m1060(logPrint, getExerciseTitle(s))) {
               LogicProgram.m1082("trudata.txt", flag ? "R" : "P", problementry.name);
            }
         } else {
            String s1 = getProblemStatement(taggedrecord);
            s1 = LogicProgram.m995(s1, maggie, kaplan);
            LPTruthAnalysis lptruthanalysis = new LPTruthAnalysis(true);
            lptruthanalysis.loadProblem(problementry.name);
            Vector vector1 = new Vector(5);
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new C_u_(null, 2, new int[]{20, dimension.width - 20}));
            jpanel.setBackground(LogicProgram.f605[1]);
            jpanel.add(new C_f_E(lptruthanalysis.checkDisabled ? " " : C_f_C.f1128[k]));
            C_NC c_nc;
            jpanel.add(c_nc = new C_NC(LogicProgram.m1004(trimTitle(s) + ": " + s1)));
            c_nc.setLineWrap(true);
            c_nc.setWrapStyleWord(true);
            c_nc.setBackground(LogicProgram.f605[1]);
            vector1.add(jpanel);
            if (!flag) {
               C_k_E c_k_e = lptruthanalysis.problem;
               C_XF c_xf = c_k_e.f1201;
               vector1.add(c_k_e.f1209);
               vector1.add(c_xf);
               if (c_xf.f892 == -1) {
                  C_ZE c_ze;
                  vector1.add(c_ze = new C_ZE("No Row Checked"));
                  c_ze.setBackground(LogicProgram.f605[1]);
               } else {
                  C_ZE c_ze1;
                  vector1.add(c_ze1 = new C_ZE("Row Checked: " + C_XF.m1523(c_xf.f892, c_k_e.f1217)));
                  c_ze1.setBackground(LogicProgram.f605[1]);
               }

               C_GD[][] ac_gd = c_xf.f890;
               int l = ac_gd.length;

               for (int i1 = 0; i1 < l; i1++) {
                  JPanel jpanel1 = new JPanel();
                  jpanel1.setBackground(LogicProgram.f605[1]);
                  C_GD[] ac_gd1 = ac_gd[i1];
                  int j1 = ac_gd1.length;

                  for (int k1 = 0; k1 < j1; k1++) {
                     jpanel1.add(ac_gd1[k1].f361);
                  }

                  vector1.add(jpanel1);
               }
            }

            vector.add(vector1);
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
         LPTruthAnalysis.allocateTruModule(this);
      }

      @Override
      public void m959() {
         LPTruthAnalysis.continueStartup();
      }
   }
}
