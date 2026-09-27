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
   TruthProblemPanel problem;
   int fontSize;
   Font font;
   Font errorFont;
   static final String workFileName = "truwork.txt";
   static final String logFileName = "trudata.txt";
   static final String digestVersKey = "truDigestVers";
   static Class messageClass = TruthMessage.class;
   static final int correct = 0;
   static final int incorrect = 1;
   static final int incomplete = 2;
   static TruthProblemSet exercises = null;
   static TruthProblemSet problems = null;
   static Vector instances = new Vector();
   static PrintQueue printQueue = new PrintQueue("Truth Tables");
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
      if (!TruthMessage.loadMessages()) {
         LogicProgram.showFileError("not001", "the truth analysis messages file");
         return false;
      } else {
         resetOptions();
         readOptions(LogicProgram.openDataFile("options", false));
         readOptions(LogicProgram.openLocalFile("options", false));
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

   static TruthProblemSet readExercises() {
      return readExercises(LogicProgram.noCoreProblems, false, false);
   }

   static TruthProblemSet readExercises(boolean flag, boolean flag1, boolean flag2) {
      TruthProblemSet truthproblemset = new TruthProblemSet();
      if (!flag) {
         ScrambledReader scrambledreader = LogicProgram.openDataFile("truwork.txt", false);
         if (scrambledreader == null) {
            LogicProgram.showFileError("not001", "the core Truth Table exercise file");
            return null;
         }

         if (!readProblems(scrambledreader, truthproblemset, true)) {
            LogicProgram.showFileError("not002", "the core Truth Table exercise file");
            return null;
         }
      }

      if (!flag1) {
         ScrambledReader scrambledreader1 = LogicProgram.openLocalFile("truwork.txt", flag2);
         if (scrambledreader1 != null
            && (flag ? !readProblems(scrambledreader1, truthproblemset, true) : !mergeProblems(scrambledreader1, truthproblemset, true))) {
            LogicProgram.showFileError("not002", "the local Truth Table exercise file");
            return null;
         }
      }

      return truthproblemset;
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
      this.scroller.setViewportView(this.problem = new TruthProblemPanel(this));
      this.add(TruthToolbar.create(this), "South");
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
         LPTruthAnalysis.TruthStartupTask lptruthanalysis$truthstartuptask = new LPTruthAnalysis.TruthStartupTask(busyindicator, rectangle, s);
         startups.add(lptruthanalysis$truthstartuptask);
         if (startups.size() <= 1) {
            if (problems == null) {
               if (!getProblems()) {
                  lptruthanalysis$truthstartuptask.stopBusyIndicator();
                  startups.remove(lptruthanalysis$truthstartuptask);
                  return;
               }

               if (problems.mergeExercises()) {
                  saveProblems();
               }

               problems.restateProblems(lptruthanalysis$truthstartuptask);
            } else {
               lptruthanalysis$truthstartuptask.continueStartup();
            }
         }
      }
   }

   static synchronized void continueStartup() {
      restating = false;
      int i = startups.size();

      for (int j = 0; j < i; j++) {
         ((LogicModule.ModuleStartupTask)startups.get(i - 1 - j)).stopBusyIndicator();
      }

      while (!startups.isEmpty()) {
         LogicModule.ModuleStartupTask logicmodule$modulestartuptask = (LogicModule.ModuleStartupTask)startups.remove(0);
         SwingUtilities.invokeLater(logicmodule$modulestartuptask);
      }
   }

   Rectangle fixModuleRect(Rectangle rectangle) {
      TruthToolbar truthtoolbar = TruthToolbar.create(this);
      Dimension dimension = truthtoolbar.getPreferredSize();
      if (rectangle.width < dimension.width) {
         rectangle.width = dimension.width;
      }

      return rectangle;
   }

   static void allocateTruModule(LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
      LPTruthAnalysis lptruthanalysis = new LPTruthAnalysis(false);
      instances.addElement(lptruthanalysis);
      if (logicmodule$modulestartuptask.problemName == null || newProblem == null) {
         lptruthanalysis.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lptruthanalysis.saveProblem();
         }
      }

      if (logicmodule$modulestartuptask.problemName != null) {
         lptruthanalysis.loadProblem(logicmodule$modulestartuptask.problemName);
         logicmodule$modulestartuptask.problemName = null;
      }

      lptruthanalysis.setupFrame(LPInfo.programName + ": Truth Tables");
      lptruthanalysis.frame.setBounds(lptruthanalysis.fixModuleRect(logicmodule$modulestartuptask.bounds));
      lptruthanalysis.frame.setVisible(true);
      lptruthanalysis.requestFocus();
      lptruthanalysis.noBeep = false;
   }

   static void insertDBProbs(ProblemSelector problemselector) {
      if (problemselector != null && !problemselector.isEmpty() && UserSetup.hasAccess("addToDB", "developer")) {
         int i = exercises.size();

         for (int j = 0; j < i; j++) {
            TaggedRecord taggedrecord = new TaggedRecord(exercises.getRecordAt(j));
            String s = taggedrecord.getName();
            if (problemselector.contains(s)) {
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
               String s4 = LogicProgram.translateSymbols(s1, maggie, html);
               String s5 = taggedrecord.valueAt(taggedrecord.indexOfTag('C'));
               if (s5 == null) {
                  s5 = s;
               }

               String s6 = "insert into logic_problem (COMMENT,DTCREATION,PROBLEM_NAME,TPROBLEM,TPROBLEM_MD5,TWEB_FORM_PROBLEM,VERSION,SYNTAX,COMMON_NAME)";
               s6 = s6 + " values (" + ServerConnection.sqlQuote(s2) + ",GETDATE()," + ServerConnection.sqlQuote(s) + "," + ServerConnection.sqlQuote(s1) + ",";
               s6 = s6 + ServerConnection.sqlQuote(s3) + "," + ServerConnection.sqlQuote(s4) + "," + nameVersion(s) + "," + FormulaParser.getSyntax() + ",";
               s6 = s6 + ServerConnection.sqlQuote(s5) + ")";
               ServerConnection.stubReturnsNull(s6);
            }
         }
      }
   }

   static void updateDBProbs(ProblemSelector problemselector) {
      if (problemselector != null && !problemselector.isEmpty() && UserSetup.hasAccess("addToDB", "developer")) {
         int i = exercises.size();

         for (int j = 0; j < i; j++) {
            TaggedRecord taggedrecord = new TaggedRecord(exercises.getRecordAt(j));
            String s = taggedrecord.getName();
            if (problemselector.contains(s)) {
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
               String s4 = LogicProgram.translateSymbols(s1, maggie, html);
               String s5 = taggedrecord.valueAt(taggedrecord.indexOfTag('C'));
               if (s5 == null) {
                  s5 = s;
               }

               String s6 = "update logic_problem set tproblem = " + ServerConnection.sqlQuote(s1) + ", tproblem_md5 = " + ServerConnection.sqlQuote(s3);
               s6 = s6 + ", tweb_form_problem = " + ServerConnection.sqlQuote(s4) + ", comment = " + ServerConnection.sqlQuote(s2);
               s6 = s6 + ", version = " + nameVersion(s) + ", common_name = " + ServerConnection.sqlQuote(s5);
               s6 = s6 + " where problem_name = " + ServerConnection.sqlQuote(s) + " and syntax = " + FormulaParser.getSyntax();
               ServerConnection.stubReturnsNull(s6);
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
         TruthProblemSet truthproblemset = readWork();
         if (truthproblemset == null) {
            return false;
         } else {
            if (truthproblemset.readFromPlainFile && !truthproblemset.computeDigest(LogicProgram.user).equals(truthproblemset.storedDigest)) {
               System.out.println("Could not digest file: truwork.txt");
               if (!UserSetup.hasAccess("indigestion", "instructor")) {
                  LogicProgram.showFileError("not003", "truwork.txt");
                  return false;
               }
            }

            problems = truthproblemset;
            TruthProblemEntry.workProblemNames = ProblemEntry.findExtraProblems("truwork.txt", problems);
            ProblemEntry.markExtraProblems(exercises, TruthProblemEntry.workProblemNames);
            if (DataFiles.hasLegacyWork(LogicProgram.workDir, "truwork.txt")) {
               saveProblems(); // work saved in the older format: save it in the readable one
            }

            return true;
         }
      }
   }

   static TruthProblemSet readWork() {
      if (!LogicProgram.checkSameUser()) {
         return null;
      } else {
         TruthProblemSet truthproblemset = new TruthProblemSet();
         ScrambledReader scrambledreader = LogicProgram.openDataFile("truwork.txt", true);
         if (!LogicProgram.noCoreProblems || scrambledreader instanceof PlainRecordReader) {
            if (scrambledreader == null) {
               LogicProgram.showFileError("not001", "truwork.txt");
               return null;
            }

            if (scrambledreader instanceof PlainRecordReader) {
               truthproblemset.readFromPlainFile = true;
            }

            if (!readProblems(scrambledreader, truthproblemset, false)) {
               LogicProgram.showFileError("not002", "truwork.txt");
               return null;
            }
         }

         if (!(scrambledreader instanceof PlainRecordReader)) {
            scrambledreader = LogicProgram.openLocalFile("truwork.txt", false);
            if (scrambledreader != null && !mergeProblems(scrambledreader, truthproblemset, false)) {
               LogicProgram.showFileError("not002", "truwork.txt");
               return null;
            }
         }

         return truthproblemset;
      }
   }

   boolean hasWork() {
      if (this.completeSetup && this.problem.setupPanel != null && this.problem.setupPanel.hasSetupWork()) {
         return true;
      } else if (this.problem.answer != -1) {
         return true;
      } else if (this.problem.table.counterexampleRow != -1) {
         return true;
      } else {
         int i = this.problem.table.rowCount;

         for (int j = 0; j < i; j++) {
            if (this.problem.table.rowHasWork(j)) {
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
      return taggedrecord.indexOfAnyTag("@*#&") != -1;
   }

   static String getWork(TaggedRecord taggedrecord) {
      return taggedrecord.formatFields("@*#&");
   }

   static String removeWork(TaggedRecord taggedrecord) {
      return taggedrecord.formatFields("$=%u!");
   }

   ErrorRef checkFull() {
      TruthTableGrid truthtablegrid = this.problem.table;
      TruthTableCell[][] atruthtablecell = truthtablegrid.cells;
      int i = truthtablegrid.counterexampleRow;
      if (this.completeSetup) {
         if (this.problem.setupPanel == null) {
            return new ErrorRef("truerr013", Message.params("summary", "Incomplete"));
         }

         if (!this.problem.setupDone) {
            ErrorRef errorref = this.problem.setupPanel.checkAssignments();
            if (errorref.id != null) {
               return errorref;
            }

            return new ErrorRef("truerr014", Message.params("summary", "Incomplete"));
         }
      }

      for (int j = 0; j < truthtablegrid.rowCount; j++) {
         if (checkRowError(atruthtablecell[j])) {
            return new ErrorRef("truerr001", Message.params("summary", "Incorrect"));
         }
      }

      if (!this.assumeTautology) {
         if (this.problem.answer == 0) {
            if (i != -1) {
               return new ErrorRef("truerr004", Message.params("summary", "Incorrect"));
            }
         } else {
            if (this.problem.answer != 1) {
               return new ErrorRef("truerr003", Message.params("summary", "Incomplete"));
            }

            if (i == -1) {
               return new ErrorRef("truerr006", Message.params("summary", "Incomplete"));
            }
         }
      }

      if (this.assumeTautology || this.completeAllRows || this.problem.answer == 0) {
         for (int k = 0; k < truthtablegrid.rowCount; k++) {
            if (!checkRowComplete(atruthtablecell[k]) && (this.completeAllWffs || !checkRowValid(atruthtablecell[k]))) {
               return new ErrorRef("truerr002", Message.params("summary", "Incomplete"));
            }
         }
      }

      if (this.assumeTautology) {
         return new ErrorRef(null, Message.params("summary", "Correct"));
      } else if (this.problem.answer == 0) {
         for (int l = 0; l < truthtablegrid.rowCount; l++) {
            if (!checkRowValid(atruthtablecell[l])) {
               return new ErrorRef("truerr005", Message.params("summary", "Incorrect"));
            }
         }

         return new ErrorRef(null, Message.params("summary", "Correct"));
      } else if (!this.completeAllWffs && checkRowValid(atruthtablecell[i])) {
         return new ErrorRef("truerr008", Message.params("summary", "Incorrect"));
      } else if (!this.completeAllRows && !checkRowComplete(atruthtablecell[i])) {
         return new ErrorRef("truerr007", Message.params("summary", "Incomplete"));
      } else {
         return this.completeAllWffs && checkRowValid(atruthtablecell[i])
            ? new ErrorRef("truerr008", Message.params("summary", "Incorrect"))
            : new ErrorRef(null, Message.params("summary", "Correct"));
      }
   }

   boolean check() {
      return this.checkFull().id == null;
   }

   void checkProblem() {
      ErrorRef errorref = this.checkFull();
      if (errorref.params != null) {
         String s = (String)errorref.params.get("summary");
         this.titlePanel.setStatus(s == null ? "" : s);
      }

      if (errorref.id != null && !this.checkMessagesDisabled) {
         MessageDialog.showMessage(TruthMessage.get(errorref.id), errorref.params, null, null);
      }
   }

   static boolean checkRowError(TruthTableCell[] atruthtablecell) {
      int i = atruthtablecell.length;

      for (int j = 0; j < i; j++) {
         if (atruthtablecell[j].isWrong) {
            return true;
         }
      }

      return false;
   }

   static boolean checkRowComplete(TruthTableCell[] atruthtablecell) {
      int i = atruthtablecell.length;

      for (int j = 0; j < i; j++) {
         if (atruthtablecell[j].getText().equals("?")) {
            return false;
         }
      }

      return true;
   }

   static boolean checkRowValid(TruthTableCell[] atruthtablecell) {
      int i = atruthtablecell.length;
      if (atruthtablecell[i - 1].getText().equals("T")) {
         return true;
      } else {
         for (int j = 0; j < i - 1; j++) {
            if (atruthtablecell[j].getText().equals("F")) {
               return true;
            }
         }

         return false;
      }
   }

   static String getExerciseTitle(String s) {
      String s1;
      return exercises != null && (s1 = exercises.getRecord(s)) != null ? TaggedRecord.nameOf(s1) : null;
   }

   static boolean isExercise(String s) {
      return exercises != null && s != null && exercises.getRecord(s) != null;
   }

   static boolean isExample(String s) {
      return exercises != null && s != null && TaggedRecord.isExample(exercises.getRecord(s));
   }

   void loadExerciseInfo(TaggedRecord taggedrecord) {
      taggedrecord = new TaggedRecord(exercises == null ? null : exercises.getRecord(taggedrecord.getName()));
      this.probOptions = taggedrecord.getKeyValues('%');
      this.dontChange = this.probOptions != null && this.probOptions.containsKey("eg");
      this.assumeTautology = this.probOptions != null && this.probOptions.containsKey("taut");
      this.titlePanel.setNote(taggedrecord.valueAt(taggedrecord.indexOfTag('!')));
      String s = taggedrecord.getName();
      this.completeAllNodes = LogicProgram.selectorMatches(doAllNodes, s);
      this.completeAllRows = LogicProgram.selectorMatches(doAllRows, s);
      this.completeAllWffs = LogicProgram.selectorMatches(doAllWffs, s);
      this.completeSetup = LogicProgram.selectorMatches(doSetUp, s);
      this.checkDisabled = LogicProgram.selectorMatches(this.forPrint ? noPrintCheck : noCheck, s);
      this.treeErrorsDisabled = LogicProgram.selectorMatches(this.forPrint ? noPrintTreeErr : noTreeErr, s);
      this.tableErrorsDisabled = LogicProgram.selectorMatches(this.forPrint ? noPrintTableErr : noTableErr, s);
      this.setupErrorsDisabled = LogicProgram.selectorMatches(this.forPrint ? noPrintSetupErr : noSetupErr, s);
      this.checkMessagesDisabled = LogicProgram.selectorMatches(noCheckMess, s);
      this.tableErrorsDisabled = this.tableErrorsDisabled | this.checkDisabled;
      this.treeErrorsDisabled = this.treeErrorsDisabled | this.tableErrorsDisabled;
   }

   static boolean readProblems(Reader reader, TruthProblemSet truthproblemset, boolean flag) {
      return readProblems(reader, truthproblemset, flag, false);
   }

   static boolean mergeProblems(Reader reader, TruthProblemSet truthproblemset, boolean flag) {
      return readProblems(reader, truthproblemset, flag, true);
   }

   static boolean readProblems(Reader reader, TruthProblemSet truthproblemset, boolean flag, boolean flag1) {
      return LogicModule.readProblems(reader, truthproblemset, flag, flag1);
   }

   static int[] getExerciseIndices() {
      if (problems != null && exercises != null) {
         ExpressionPath expressionpath = new ExpressionPath();
         int i = problems.size();

         for (int j = 0; j < i; j++) {
            if (isExercise(TaggedRecord.nameOf(problems.getRecordAt(j)))) {
               expressionpath.push(j);
            }
         }

         return expressionpath.toArray();
      } else {
         return null;
      }
   }

   void submitExam() {
      if (UserSetup.confirmSubmitAll("Truth Tables")) {
         int[] aint = getExerciseIndices();
         BusyIndicator busyindicator = new BusyIndicator(this);
         Submission submission = ServerConnection.prepareSubmission(busyindicator);
         if (submission != null) {
            if (TruthDialogs.confirmSaveChanges(this, null)) {
               submit(submission, aint, busyindicator);
               ServerConnection.finishSubmission(submission, busyindicator);
               AccountManager.showSubmissionResults(submission);
            } else {
               ServerConnection.finishSubmission(submission, busyindicator);
            }
         }
      }
   }

   void submitProblems() {
      BusyIndicator busyindicator = new BusyIndicator(this);
      Submission submission = ServerConnection.prepareSubmission(busyindicator);
      if (submission != null) {
         int[] aint = TruthDialogs.chooseSubmitProblems(this);
         if (aint == null) {
            ServerConnection.finishSubmission(submission, busyindicator);
         } else {
            submit(submission, aint, busyindicator);
            ServerConnection.finishSubmission(submission, busyindicator);
            AccountManager.showSubmissionResults(submission);
         }
      }
   }

   static void submit(Submission submission, int[] aint, BusyIndicator busyindicator) {
      synchronized (problems) {
         Vector vector = new Vector();
         Vector vector1 = new Vector();
         int i = aint.length;

         for (int j = 0; j < i; j++) {
            submission.reset();
            String s = problems.getRecordAt(aint[j]);
            TaggedRecord taggedrecord = new TaggedRecord(s);
            String s1 = getProblemStatement(taggedrecord);
            submission.problemMd5 = Scrambler.md5Base64(s1 == null ? "" : s1.trim());
            int k = getProblemState(s);
            submission.evaluation = TruthProblemEntry.STATE_CODES[k];
            submission.work = s;
            submission.problemName = taggedrecord.getName();
            submission.module = moduleAbbrs[moduleIndex];
            submission.helpCount = taggedrecord.getErrorCount();
            submission.duration = taggedrecord.getTimestamp();
            boolean flag = LogicProgram.selectorMatches(logSubmit, getExerciseTitle(submission.problemName));
            if (ServerConnection.submit(submission, busyindicator)) {
               vector.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.appendSubmitLog("trudata.txt", "S", s, submission.getLogRecord());
               }
            } else {
               vector1.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.appendSubmitLog("trudata.txt", "F", s);
               }
            }
         }

         submission.reset();
         vector.copyInto(submission.succeededNames = new String[vector.size()]);
         vector1.copyInto(submission.failedNames = new String[vector1.size()]);
      }
   }

   void uploadProblems() {
      BusyIndicator busyindicator = new BusyIndicator(this);
      ProblemUpload problemupload = ServerConnection.prepareUpload(busyindicator);
      if (problemupload != null) {
         problemupload.resultText = null;
         int[] aint = TruthDialogs.chooseUploadProblems(this);
         if (aint == null) {
            ServerConnection.finishUpload(problemupload, busyindicator);
         } else {
            upload(problemupload, aint, busyindicator);
            ServerConnection.finishUpload(problemupload, busyindicator);
            AccountManager.showUploadResults(problemupload);
         }
      }
   }

   static void upload(ProblemUpload problemupload, int[] aint, BusyIndicator busyindicator) {
      synchronized (problems) {
         Vector vector = new Vector();
         Vector vector1 = new Vector();
         int i = aint.length;

         for (int j = 0; j < i; j++) {
            problemupload.reset();
            String s = problems.getRecordAt(aint[j]);
            ProblemEntry problementry = problems.getEntryAt(aint[j]);
            TaggedRecord taggedrecord = new TaggedRecord(s);
            String s1 = getProblemStatement(taggedrecord);
            problemupload.problemName = taggedrecord.getName();
            problemupload.text = s1;
            problemupload.webText = LogicProgram.translateSymbols(s1, maggie, html);
            problemupload.aux = assumeTautology(taggedrecord) ? "taut" : null;
            problemupload.type = moduleAbbrs[moduleIndex];
            problemupload.answers = null;
            if (problementry != null
               && problementry.state == 2
               && !isExercise(problemupload.problemName)
               && taggedrecord.getOriginalName() == null
               && ServerConnection.uploadProblem(problemupload, busyindicator)) {
               vector.addElement(trimTitle(problemupload.problemName));
            } else {
               vector1.addElement(trimTitle(problemupload.problemName));
            }
         }

         vector.copyInto(problemupload.succeededNames = new String[vector.size()]);
         vector1.copyInto(problemupload.failedNames = new String[vector1.size()]);
      }
   }

   static boolean assumeTautology(TaggedRecord taggedrecord) {
      int[] aint = taggedrecord.indexesOfTag('%');
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
      String s = this.problem.getWorkRecord();
      if (this.errorCount != 0) {
         s = s + this.errorCount + "`e";
      }

      if (this.updateWorkTime() != 0L) {
         s = s + this.workTime + "`t";
      }

      return TaggedRecord.toLine(s);
   }

   static boolean saveProblems() {
      if (!LogicProgram.checkSameUser()) {
         return false;
      } else {
         try {
            writeProblems(problems, "truwork.txt");
            return true;
         } catch (IOException ioexception) {
            LogicProgram.showFileError("not004", "truwork.txt");
            return false;
         }
      }
   }

   boolean saveRenamed(String s) {
      if (s == null) {
         return true;
      } else {
         String s1 = this.problem.problemName;
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
               String s2 = TruthDialogs.askProblemName(this, flag ? this.problem.problemName : null);
               if (s2 == null) {
                  return false;
               }

               this.setProblemTitle(s2);
               TruthProblemEntry truthproblementry = new TruthProblemEntry(TaggedRecord.withName(s, s2), false);
               this.problemIndex = problems.registerEntry(truthproblementry, false);
               this.problemIndex = this.problemIndex == -1 ? problems.size() : this.problemIndex + 1;
               problems.insertElementAt(truthproblementry, this.problemIndex);
            } else {
               s1 = problems.getRecordAt(this.problemIndex);
               problems.replaceProblem(s, this.problemIndex);
            }

            if (!saveProblems()) {
               if (s1 == null) {
                  problems.removeProblem(this.problemIndex);
                  this.problemIndex = -1;
               } else {
                  problems.replaceProblem(s1, this.problemIndex);
               }

               return false;
            } else {
               return true;
            }
         }
      }
   }

   void setProblemTitle(String s) {
      this.problem.setProblemName(s);
   }

   static String trimTitle(String s) {
      if (s == null) {
         return null;
      } else {
         return isExercise(s) ? LogicProgram.stripNamePrefix(s) : s.trim();
      }
   }

   String getChangedProblem() {
      String s = this.saveProblem();
      String s1 = this.problemIndex == -1 ? newProblem : problems.getRecordAt(this.problemIndex);
      return TaggedRecord.stripTimestamp(s).equals(TaggedRecord.stripTimestamp(s1)) ? null : s;
   }

   static String getProblemStatement(String s) {
      return getProblemStatement(new TaggedRecord(s));
   }

   static String getProblemStatement(TaggedRecord taggedrecord) {
      return taggedrecord.valueAt(taggedrecord.indexOfAnyTag("="));
   }

   @Override
   public boolean shutdown(boolean flag) {
      if (!flag && !TruthDialogs.confirmSaveChanges(this, null)) {
         return false;
      } else {
         this.reset();
         synchronized (moduleClasses[5]) {
            instances.removeElement(this);
            if (instances.isEmpty()) {
               PrintTask.waitForQueue(printQueue);
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
      return TruthDialogs.confirmSaveChanges(this, null);
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
      readOptions(LogicProgram.openLocalFile("options", false));
      logNeeds();
      if (needPrint != null && !needPrint.isEmpty() || needSubmit != null && !needSubmit.isEmpty()) {
         ProblemRecordEnumeration problemrecordenumeration = new ProblemRecordEnumeration(readWork());
         problemrecordenumeration.retainExisting(readExercises());
         MainMenu.mergeSubmitStatus(hashtable, "trudata.txt", "P", needPrint, problemrecordenumeration);
         problemrecordenumeration.reset();
         MainMenu.mergeSubmitStatus(hashtable1, "trudata.txt", "S", needSubmit, problemrecordenumeration);
      }

      resetOptions();
      return true;
   }

   static Vector getChangedProblems() {
      ProblemRecordEnumeration problemrecordenumeration = new ProblemRecordEnumeration(readWork());
      Hashtable hashtable = LogicProgram.checkSubmitLog("trudata.txt", "S", needSubmit, problemrecordenumeration);
      if (hashtable == null) {
         return null;
      } else {
         Vector vector = (Vector)hashtable.get("handled");
         Vector vector1 = new Vector();
         problemrecordenumeration.reset();

         while (problemrecordenumeration.hasMoreElements()) {
            String s = (String)problemrecordenumeration.nextElement();
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
         this.problem.loadProblem(taggedrecord);
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
               int[] aint = taggedrecord.indexesOfAnyTag("+?");
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

                           addToDB.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("updateDB")) {
                           if (updateDB == null) {
                              updateDB = new ProblemSelector();
                           }

                           updateDB.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noCheck")) {
                           if (noCheck == null) {
                              noCheck = new ProblemSelector();
                           }

                           noCheck.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noTreeErr")) {
                           if (noTreeErr == null) {
                              noTreeErr = new ProblemSelector();
                           }

                           noTreeErr.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noTableErr")) {
                           if (noTableErr == null) {
                              noTableErr = new ProblemSelector();
                           }

                           noTableErr.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noSetupErr")) {
                           if (noSetupErr == null) {
                              noSetupErr = new ProblemSelector();
                           }

                           noSetupErr.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noPrintCheck")) {
                           if (noPrintCheck == null) {
                              noPrintCheck = new ProblemSelector();
                           }

                           noPrintCheck.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noPrintTreeErr")) {
                           if (noPrintTreeErr == null) {
                              noPrintTreeErr = new ProblemSelector();
                           }

                           noPrintTreeErr.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noPrintTableErr")) {
                           if (noPrintTableErr == null) {
                              noPrintTableErr = new ProblemSelector();
                           }

                           noPrintTableErr.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noPrintSetupErr")) {
                           if (noPrintSetupErr == null) {
                              noPrintSetupErr = new ProblemSelector();
                           }

                           noPrintSetupErr.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noCheckMess")) {
                           if (noCheckMess == null) {
                              noCheckMess = new ProblemSelector();
                           }

                           noCheckMess.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noPrint")) {
                           if (noPrint == null) {
                              noPrint = new ProblemSelector();
                           }

                           noPrint.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new ProblemSelector();
                           }

                           monoProbs.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("doAllRows")) {
                           if (doAllRows == null) {
                              doAllRows = new ProblemSelector();
                           }

                           doAllRows.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("doAllWffs")) {
                           if (doAllWffs == null) {
                              doAllWffs = new ProblemSelector();
                           }

                           doAllWffs.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("doSetUp")) {
                           if (doSetUp == null) {
                              doSetUp = new ProblemSelector();
                           }

                           doSetUp.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("logPrint")) {
                           if (logPrint == null) {
                              logPrint = new ProblemSelector();
                           }

                           logPrint.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("logSubmit")) {
                           if (logSubmit == null) {
                              logSubmit = new ProblemSelector();
                           }

                           logSubmit.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("needPrint")) {
                           if (needPrint == null) {
                              needPrint = new ProblemSelector();
                           }

                           needPrint.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("needSubmit")) {
                           if (needSubmit == null) {
                              needSubmit = new ProblemSelector();
                           }

                           needSubmit.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("doAllNodes")) {
                           if (doAllNodes == null) {
                              doAllNodes = new ProblemSelector();
                           }

                           doAllNodes.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
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
      if (needPrint != null && !needPrint.isEmpty()) {
         if (logPrint == null) {
            logPrint = new ProblemSelector();
         }

         logPrint.union(needPrint);
      }

      if (needSubmit != null && !needSubmit.isEmpty()) {
         if (logSubmit == null) {
            logSubmit = new ProblemSelector();
         }

         logSubmit.union(needSubmit);
      }
   }

   void loadProblem(String s) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      this.loadExerciseInfo(taggedrecord);
      this.problem.loadProblem(taggedrecord);
      this.errorCount = taggedrecord.getErrorCount();
      this.workTime = taggedrecord.getTimestamp();
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
      String s1 = argumentparser.getUnparsedText();
      if (s1 != null) {
         Hashtable hashtable = Message.params("expression", LogicProgram.translateSymbols(s1, maggie, kaplan));
         MessageDialog.showMessage(TruthMessage.get("truerr009"), hashtable, null, null);
      } else if (argumentparser.getErrorCode() != 0) {
         MessageDialog.showMessage(TruthMessage.get("truerr010"), null, null, null);
      } else {
         String s2 = TaggedRecord.toLine(TaggedRecord.formatField(ArgumentParser.normalizeDots(s), '='));
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
         ProblemEntry problementry = problems.getEntryAt(aint[j]);
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         String s1 = getProblemStatement(taggedrecord);
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new FixedColumnLayout(null, 1, new int[]{dimension.width}));
         LogicTextArea logictextarea;
         jpanel.add(logictextarea = new LogicTextArea(LogicProgram.expandEscapes("\\l" + s + ": " + s1)));
         logictextarea.setLineWrap(true);
         logictextarea.setWrapStyleWord(true);
         logictextarea.setBackground(LogicProgram.printColors[1]);
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
         ProblemEntry problementry = problems.getEntryAt(aint[j]);
         int k = problementry.state;
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         if (printIncorrect && k != 1) {
            if (LogicProgram.selectorMatches(logPrint, getExerciseTitle(s))) {
               LogicProgram.appendSubmitLog("trudata.txt", flag ? "R" : "P", problementry.name);
            }
         } else {
            String s1 = getProblemStatement(taggedrecord);
            s1 = LogicProgram.translateSymbols(s1, maggie, kaplan);
            LPTruthAnalysis lptruthanalysis = new LPTruthAnalysis(true);
            lptruthanalysis.loadProblem(problementry.name);
            Vector vector1 = new Vector(5);
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new FixedColumnLayout(null, 2, new int[]{20, dimension.width - 20}));
            jpanel.setBackground(LogicProgram.printColors[1]);
            jpanel.add(new WrappedTextPanel(lptruthanalysis.checkDisabled ? " " : TruthProblemEntry.STATE_CODES[k]));
            LogicTextArea logictextarea;
            jpanel.add(logictextarea = new LogicTextArea(LogicProgram.expandEscapes(trimTitle(s) + ": " + s1)));
            logictextarea.setLineWrap(true);
            logictextarea.setWrapStyleWord(true);
            logictextarea.setBackground(LogicProgram.printColors[1]);
            vector1.add(jpanel);
            if (!flag) {
               TruthProblemPanel truthproblempanel = lptruthanalysis.problem;
               TruthTableGrid truthtablegrid = truthproblempanel.table;
               vector1.add(truthproblempanel.questionPanel);
               vector1.add(truthtablegrid);
               if (truthtablegrid.counterexampleRow == -1) {
                  LogicLabel logiclabel;
                  vector1.add(logiclabel = new LogicLabel("No Row Checked"));
                  logiclabel.setBackground(LogicProgram.printColors[1]);
               } else {
                  LogicLabel logiclabel1;
                  vector1.add(
                     logiclabel1 = new LogicLabel(
                        "Row Checked: " + TruthTableGrid.rowAssignmentString(truthtablegrid.counterexampleRow, truthproblempanel.letterCount)
                     )
                  );
                  logiclabel1.setBackground(LogicProgram.printColors[1]);
               }

               TruthTableCell[][] atruthtablecell = truthtablegrid.cells;
               int l = atruthtablecell.length;

               for (int i1 = 0; i1 < l; i1++) {
                  JPanel jpanel1 = new JPanel();
                  jpanel1.setBackground(LogicProgram.printColors[1]);
                  TruthTableCell[] atruthtablecell1 = atruthtablecell[i1];
                  int j1 = atruthtablecell1.length;

                  for (int k1 = 0; k1 < j1; k1++) {
                     jpanel1.add(atruthtablecell1[k1].mirrorTree);
                  }

                  vector1.add(jpanel1);
               }
            }

            vector.add(vector1);
         }
      }

      return vector;
   }

   static class TruthStartupTask extends LogicModule.ModuleStartupTask {
      TruthStartupTask(BusyIndicator busyindicator, Rectangle rectangle, String s) {
         super(busyindicator, rectangle, s);
      }

      @Override
      public void run() {
         LPTruthAnalysis.allocateTruModule(this);
      }

      @Override
      public void continueStartup() {
         LPTruthAnalysis.continueStartup();
      }
   }
}
