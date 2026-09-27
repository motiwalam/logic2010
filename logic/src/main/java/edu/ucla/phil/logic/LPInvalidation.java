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
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

class LPInvalidation extends LogicModule implements LogicConstants {
   static final String workFileName = "invwork.txt";
   static final String logFileName = "invdata.txt";
   static final String digestVersKey = "invDigestVers";
   static Class messageClass = InvalidityMessage.class;
   static InvalidityProblemSet exercises = null;
   static InvalidityProblemSet problems = null;
   static Vector instances = new Vector();
   static PrintQueue printQueue = new PrintQueue("Invalidity");
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
   static ProblemSelector noExpand = null;
   static ProblemSelector fullExpand = null;
   static boolean noUser = false;
   static boolean submitExam = false;
   static boolean printIncorrect = false;
   static boolean restating = false;
   static String newProblem = null;
   boolean startingUp = false;
   static Vector startups;
   static String[] kaplan = LogicProgram.symbols;
   boolean checkDisabled;
   boolean dontChange;
   boolean expandOff;
   boolean expandAll;
   int errorCount;
   long workTime;
   long loadTime;
   JScrollPane scroller;
   SizedPanel scrollPanel;
   InvalidityProblemPanel problemPanel;
   String title;
   String unparsed;
   String lastUserProblem;
   ArgumentParser statement;
   Hashtable probOptions = null;
   Vector symbols;
   int size;
   int nonidentical;
   int fontSize;
   Font font;
   static int moduleIndex = 1;

   static boolean getExercises() {
      if (!InvalidityMessage.loadMessages()) {
         LogicProgram.showFileError("not001", "the invalidity messages file");
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

   static InvalidityProblemSet readExercises() {
      return readExercises(LogicProgram.noCoreProblems, false, false);
   }

   static InvalidityProblemSet readExercises(boolean flag, boolean flag1, boolean flag2) {
      InvalidityProblemSet invalidityproblemset = new InvalidityProblemSet();
      if (!flag) {
         ScrambledReader scrambledreader = LogicProgram.openDataFile("invwork.txt", false);
         if (scrambledreader == null) {
            LogicProgram.showFileError("not001", "the core Invalidity exercise file");
            return null;
         }

         if (!readProblems(scrambledreader, invalidityproblemset, true)) {
            LogicProgram.showFileError("not002", "the core Invalidity exercise file");
            return null;
         }
      }

      if (!flag1) {
         ScrambledReader scrambledreader1 = LogicProgram.openLocalFile("invwork.txt", flag2);
         if (scrambledreader1 != null
            && (flag ? !readProblems(scrambledreader1, invalidityproblemset, true) : !mergeProblems(scrambledreader1, invalidityproblemset, true))) {
            LogicProgram.showFileError("not002", "the local Invalidity exercise file");
            return null;
         }
      }

      return invalidityproblemset;
   }

   LPInvalidation(boolean flag) {
      super(flag);
      this.fontSize = LogicProgram.fontSize;
      this.reset();
      this.setLayout(new BorderLayout());
      this.add(this.titlePanel, "North");
      this.scroller = new JScrollPane();
      this.add(this.scroller, "Center");
      this.scroller.getVerticalScrollBar().setUnitIncrement(22);
      this.scroller.setViewportView(this.scrollPanel = new SizedPanel());
      this.scrollPanel.add(this.problemPanel = new InvalidityProblemPanel(this), "Center");
      this.add(InvalidityToolbar.create(this), "South");
      this.newProblem();
   }

   @Override
   int getModuleIndex() {
      return moduleIndex;
   }

   static synchronized boolean getProblems() {
      if (problems != null) {
         return true;
      } else {
         InvalidityProblemSet invalidityproblemset = readWork();
         if (invalidityproblemset == null) {
            return false;
         } else {
            if (invalidityproblemset.readFromPlainFile && !invalidityproblemset.computeDigest(LogicProgram.user).equals(invalidityproblemset.storedDigest)) {
               System.out.println("Could not digest file: invwork.txt");
               if (!UserSetup.hasAccess("indigestion", "instructor")) {
                  LogicProgram.showFileError("not003", "invwork.txt");
                  return false;
               }
            }

            problems = invalidityproblemset;
            InvalidityProblemEntry.workProblemNames = ProblemEntry.findExtraProblems("invwork.txt", problems);
            ProblemEntry.markExtraProblems(exercises, InvalidityProblemEntry.workProblemNames);
            if (DataFiles.hasLegacyWork(LogicProgram.workDir, "invwork.txt")) {
               saveProblems(); // work saved in the older format: save it in the readable one
            }

            return true;
         }
      }
   }

   static InvalidityProblemSet readWork() {
      if (!LogicProgram.checkSameUser()) {
         return null;
      } else {
         InvalidityProblemSet invalidityproblemset = new InvalidityProblemSet();
         ScrambledReader scrambledreader = LogicProgram.openDataFile("invwork.txt", true);
         if (!LogicProgram.noCoreProblems || scrambledreader instanceof PlainRecordReader) {
            if (scrambledreader == null) {
               LogicProgram.showFileError("not001", "invwork.txt");
               return null;
            }

            if (scrambledreader instanceof PlainRecordReader) {
               invalidityproblemset.readFromPlainFile = true;
            }

            if (!readProblems(scrambledreader, invalidityproblemset, false)) {
               LogicProgram.showFileError("not002", "invwork.txt");
               return null;
            }
         }

         if (!(scrambledreader instanceof PlainRecordReader)) {
            scrambledreader = LogicProgram.openLocalFile("invwork.txt", false);
            if (scrambledreader != null && !mergeProblems(scrambledreader, invalidityproblemset, false)) {
               LogicProgram.showFileError("not002", "invwork.txt");
               return null;
            }
         }

         return invalidityproblemset;
      }
   }

   static synchronized void startup(Rectangle rectangle, BusyIndicator busyindicator, String s) {
      if (startups == null) {
         startups = new Vector();
      }

      if (exercises != null || getExercises()) {
         LPInvalidation.InvalidityStartupTask lpinvalidation$invaliditystartuptask = new LPInvalidation.InvalidityStartupTask(busyindicator, rectangle, s);
         startups.add(lpinvalidation$invaliditystartuptask);
         if (startups.size() <= 1) {
            if (problems == null) {
               if (!getProblems()) {
                  lpinvalidation$invaliditystartuptask.stopBusyIndicator();
                  startups.remove(lpinvalidation$invaliditystartuptask);
                  return;
               }

               if (problems.mergeExercises()) {
                  saveProblems();
               }

               problems.restateProblems(lpinvalidation$invaliditystartuptask);
            } else {
               lpinvalidation$invaliditystartuptask.continueStartup();
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
      InvalidityToolbar invaliditytoolbar = InvalidityToolbar.create(this);
      Dimension dimension = invaliditytoolbar.getPreferredSize();
      if (rectangle.width < dimension.width) {
         rectangle.width = dimension.width;
      }

      return rectangle;
   }

   static void allocateInvModule(LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
      LPInvalidation lpinvalidation = new LPInvalidation(false);
      instances.addElement(lpinvalidation);
      if (logicmodule$modulestartuptask.problemName == null || newProblem == null) {
         lpinvalidation.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lpinvalidation.saveProblem();
         }
      }

      if (logicmodule$modulestartuptask.problemName != null) {
         lpinvalidation.loadProblem(logicmodule$modulestartuptask.problemName);
         logicmodule$modulestartuptask.problemName = null;
      }

      lpinvalidation.setupFrame(LPInfo.programName + ": Invalidity");
      lpinvalidation.frame.setBounds(lpinvalidation.fixModuleRect(logicmodule$modulestartuptask.bounds));
      lpinvalidation.frame.setVisible(true);
      lpinvalidation.requestFocus();
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
      return s != null && s.toLowerCase().startsWith("inval") ? "1" : "NULL";
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
      if (!flag && !InvalidityDialogs.confirmSaveChanges(this, null)) {
         return false;
      } else {
         this.reset();
         synchronized (moduleClasses[1]) {
            instances.removeElement(this);
            if (instances.isEmpty()) {
               PrintTask.waitForQueue(printQueue);
               problems = null;
               exercises = null;
               newProblem = null;
               resetOptions();
            }

            return true;
         }
      }
   }

   static synchronized LPInvalidation openInstance(int i, boolean flag) {
      if (i >= 0 && instances != null && !instances.isEmpty()) {
         int j = instances.size();

         for (int k = 0; k < j; k++) {
            LPInvalidation lpinvalidation = (LPInvalidation)instances.elementAt(k);
            if (lpinvalidation.problemIndex == i) {
               if (flag) {
                  lpinvalidation.requestFocus();
               }

               return lpinvalidation;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Override
   public boolean save() {
      return InvalidityDialogs.confirmSaveChanges(this, null);
   }

   static void resetOptions() {
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
      noExpand = null;
      fullExpand = null;
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
         MainMenu.mergeSubmitStatus(hashtable, "invdata.txt", "P", needPrint, problemrecordenumeration);
         problemrecordenumeration.reset();
         MainMenu.mergeSubmitStatus(hashtable1, "invdata.txt", "S", needSubmit, problemrecordenumeration);
      }

      resetOptions();
      return true;
   }

   static Vector getChangedProblems() {
      ProblemRecordEnumeration problemrecordenumeration = new ProblemRecordEnumeration(readWork());
      Hashtable hashtable = LogicProgram.checkSubmitLog("invdata.txt", "S", needSubmit, problemrecordenumeration);
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

   @Override
   public void resize() {
      if (this.problemPanel != null) {
         this.problemPanel.updateWidth();
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
         this.scrollPanel.setForeground(acolor[0]);
         this.scrollPanel.setBackground(acolor[1]);
         Graphics graphics = this.scroller.getGraphics();
         if (graphics != null) {
            this.scroller.paintAll(graphics);
         }
      }
   }

   void reset() {
      this.problemIndex = -1;
      if (this.problemPanel != null) {
         this.problemPanel.workspaceField.setText("");
      }

      this.errorCount = 0;
      this.workTime = 0L;
      this.loadTime = 0L;
      this.title = null;
      this.unparsed = null;
      this.statement = null;
      this.lastUserProblem = null;
      this.symbols = null;
      this.probOptions = null;
      this.size = 0;
      this.nonidentical = 0;
      this.expandOff = false;
      this.expandAll = false;
   }

   void loadProblem(String s) {
      this.loadProblem(new TaggedRecord(s));
   }

   void loadProblem(TaggedRecord taggedrecord) {
      this.reset();
      this.loadExerciseInfo(taggedrecord);
      this.title = taggedrecord.getName();
      this.unparsed = taggedrecord.valueAt(taggedrecord.indexOfTag('?'));
      this.statement = ArgumentParser.parse(this.unparsed);
      Integer integer = taggedrecord.intValueAt(taggedrecord.indexOfTag('#'));
      this.symbols = parseSymbolList(taggedrecord.valueAt(taggedrecord.indexOfTag('=')));
      this.setSize(integer == null ? 0 : integer);
      String s = taggedrecord.valueAt(taggedrecord.indexOfTag('&'));
      this.problemPanel.refresh();
      if (s != null) {
         try {
            this.problemPanel
               .workspaceField
               .setText(
                  LogicProgram.translateSymbols(new Utf8Codec(new Base64Codec(s).getBytes()).toString(), LogicProgram.encodedSymbols, LogicProgram.symbols)
               );
         } catch (IllegalArgumentException illegalargumentexception) {
         }
      }

      Integer integer1 = taggedrecord.intValueAt(taggedrecord.indexOfTag('e'));
      this.errorCount = integer1 == null ? 0 : integer1;
      this.workTime = taggedrecord.getTimestamp();
      this.updateWorkTime();
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
      ArgumentParser argumentparser = new ArgumentParser(s);
      String s1 = argumentparser.getUnparsedText();
      if (s1 != null) {
         Hashtable hashtable = Message.params("expression", s1);
         MessageDialog.showMessage(InvalidityMessage.get("inverr001"), hashtable, null, null);
      } else if (!argumentparser.conclusionOnly && argumentparser.getErrorCode() == 0) {
         this.loadProblem(TaggedRecord.toLine(TaggedRecord.formatField(ArgumentParser.normalizeDots(s), '?')));
      } else {
         MessageDialog.showMessage(InvalidityMessage.get("inverr002"), null, null, null);
      }
   }

   static Vector parseSymbolList(String s) {
      if (s == null) {
         return null;
      } else {
         Vector vector = new Vector();

         while (s != null) {
            int i = s.indexOf(46);
            SymbolInterpretation symbolinterpretation;
            if (i == -1) {
               symbolinterpretation = SymbolInterpretation.parse(s);
               s = null;
            } else {
               symbolinterpretation = SymbolInterpretation.parse(s.substring(0, i));
               s = s.substring(i + 1);
            }

            if (symbolinterpretation != null) {
               vector.addElement(symbolinterpretation);
            }
         }

         return vector;
      }
   }

   void setSize(int i) {
      this.size = i;
      if (this.symbols != null) {
         Enumeration enumeration = this.symbols.elements();

         while (enumeration.hasMoreElements()) {
            SymbolInterpretation symbolinterpretation = (SymbolInterpretation)enumeration.nextElement();
            symbolinterpretation.restrictToUniverse(i);
         }
      }

      this.problemPanel.refresh();
   }

   void removeWork() {
      this.size = 0;
      if (this.symbols != null) {
         Enumeration enumeration = this.symbols.elements();

         while (enumeration.hasMoreElements()) {
            SymbolInterpretation symbolinterpretation = (SymbolInterpretation)enumeration.nextElement();
            symbolinterpretation.clearValues();
         }
      }

      this.problemPanel.refresh();
   }

   static String removeWork(TaggedRecord taggedrecord) {
      return taggedrecord.formatFields("$?%u!");
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
      if (UserSetup.confirmSubmitAll("Invalidity")) {
         int[] aint = getExerciseIndices();
         BusyIndicator busyindicator = new BusyIndicator(this);
         Submission submission = ServerConnection.prepareSubmission(busyindicator);
         if (submission != null) {
            if (InvalidityDialogs.confirmSaveChanges(this, null)) {
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
         int[] aint = InvalidityDialogs.chooseSubmitProblems(this);
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
            submission.evaluation = InvalidityProblemEntry.STATE_CODES[k];
            submission.work = s;
            submission.problemName = taggedrecord.getName();
            submission.module = moduleAbbrs[moduleIndex];
            submission.helpCount = taggedrecord.getErrorCount();
            submission.duration = taggedrecord.getTimestamp();
            boolean flag = LogicProgram.selectorMatches(logSubmit, getExerciseTitle(submission.problemName));
            if (ServerConnection.submit(submission, busyindicator)) {
               vector.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.appendSubmitLog("invdata.txt", "S", s, submission.getLogRecord());
               }
            } else {
               vector1.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.appendSubmitLog("invdata.txt", "F", s);
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
         int[] aint = InvalidityDialogs.chooseUploadProblems(this);
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
            problemupload.aux = null;
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

   String saveProblem() {
      String s = "";
      s = s + TaggedRecord.formatField(this.title, '$');
      String s2 = s + TaggedRecord.formatField(this.unparsed, '?');
      if (this.size != 0) {
         s2 = s2 + this.size + "`#";
      }

      int i = this.symbols == null ? 0 : this.symbols.size();
      String s1 = i == 0 ? null : "";

      for (int j = 0; j < i; j++) {
         s1 = s1 + (j == 0 ? "" : ".") + ((SymbolInterpretation)this.symbols.elementAt(j)).encode();
      }

      String s3 = s2 + TaggedRecord.formatField(s1, '=');
      if (this.errorCount != 0) {
         s3 = s3 + this.errorCount + "`e";
      }

      if (this.updateWorkTime() != 0L) {
         s3 = s3 + this.workTime + "`t";
      }

      String s4 = new Base64Codec(new Utf8Codec(this.problemPanel.workspaceField.getText()).encode()).toString();
      if (!s4.equals("")) {
         s3 = s3 + TaggedRecord.formatField(s4, '&');
      }

      return TaggedRecord.toLine(s3);
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
      String s = taggedrecord.getName();
      this.probOptions = taggedrecord.getKeyValues('%');
      this.dontChange = this.probOptions != null && this.probOptions.containsKey("eg");
      this.titlePanel.setNote(taggedrecord.valueAt(taggedrecord.indexOfTag('!')));
      this.checkDisabled = LogicProgram.selectorMatches(this.forPrint ? noPrintCheck : noCheck, s);
      this.expandOff = LogicProgram.selectorMatches(noExpand, s);
      this.expandAll = LogicProgram.selectorMatches(fullExpand, s);
   }

   static int getProblemState(String s) {
      return new LPInvalidation(false).getProblemState(new TaggedRecord(s));
   }

   @Override
   int getProblemState(TaggedRecord taggedrecord) {
      if (!hasWork(taggedrecord)) {
         return 0;
      } else {
         this.loadProblem(taggedrecord);
         return this.checkProblem() ? 2 : 1;
      }
   }

   static boolean readProblems(Reader reader, InvalidityProblemSet invalidityproblemset, boolean flag) {
      return readProblems(reader, invalidityproblemset, flag, false);
   }

   static boolean mergeProblems(Reader reader, InvalidityProblemSet invalidityproblemset, boolean flag) {
      return readProblems(reader, invalidityproblemset, flag, true);
   }

   static boolean readProblems(Reader reader, InvalidityProblemSet invalidityproblemset, boolean flag, boolean flag1) {
      return LogicModule.readProblems(reader, invalidityproblemset, flag, flag1);
   }

   static String getProblemStatement(String s) {
      return getProblemStatement(new TaggedRecord(s));
   }

   static String getProblemStatement(TaggedRecord taggedrecord) {
      return taggedrecord.valueAt(taggedrecord.indexOfTag('?'));
   }

   static boolean hasWork(String s) {
      return hasWork(new TaggedRecord(s));
   }

   static boolean hasWork(TaggedRecord taggedrecord) {
      return taggedrecord.indexOfTag('#') != -1 || taggedrecord.indexOfTag('&') != -1;
   }

   static String getWork(TaggedRecord taggedrecord) {
      return taggedrecord.formatFields("#=&");
   }

   SymbolInterpretation getSymbol(String s, int i) {
      return this.getSymbol(new SymbolInterpretation(s, i));
   }

   SymbolInterpretation getSymbol(SymbolInterpretation symbolinterpretation) {
      if (this.symbols == null) {
         return null;
      } else {
         int i = this.symbols.indexOf(symbolinterpretation);
         return i == -1 ? null : (SymbolInterpretation)this.symbols.elementAt(i);
      }
   }

   private boolean bTest(Object object) {
      return object instanceof Boolean;
   }

   private boolean bValue(Object object) {
      return (Boolean)object;
   }

   private boolean iTest(Object object) {
      return object instanceof Integer;
   }

   private int iValue(Object object) {
      return (Integer)object;
   }

   boolean checkProblem() {
      if (this.size != 0 && this.statement != null) {
         int i = this.statement.premises.length;

         for (int j = 0; j < i; j++) {
            Object object = this.evaluate(closure(this.statement.premises[j]));
            if (!this.bTest(object) || !this.bValue(object)) {
               return false;
            }
         }

         Object object1 = this.evaluate(closure(this.statement.conclusion));
         return this.bTest(object1) && !this.bValue(object1);
      } else {
         return false;
      }
   }

   void evaluate() {
      Object object = null;
      if (this.size == 0) {
         object = "Universe is empty";
      } else if (this.statement == null) {
         object = "nothing to disprove";
      } else if (this.checkProblem()) {
         object = "Correct";
      } else {
         String s = "";
         int i = this.statement.premises.length;

         for (int j = 0; j < i; j++) {
            Object object1 = this.evaluate(closure(this.statement.premises[j]));
            s = s + (j == 0 ? "" : ".") + (this.bTest(object1) ? (this.bValue(object1) ? "T" : "F") : "N");
         }

         Object object2 = this.evaluate(closure(this.statement.conclusion));
         object = s + ".:" + (this.bTest(object2) ? (this.bValue(object2) ? "T" : "F") : "N");
      }

      this.titlePanel.setStatus(LogicProgram.translateSymbols(object + " ", maggie, kaplan));
   }

   Object evaluate(Expression expression) {
      return this.evaluate(expression, new VariableScope());
   }

   Object evaluate(Expression expression, VariableScope variablescope) {
      if (expression == null) {
         return null;
      } else {
         int i = expression.getChildCount();
         String s = expression.getSymbol();
         Object object = variablescope.getShadowed(s, 0);
         if (object != null) {
            return object;
         } else {
            int k = this.symbols.size();

            for (int j = 0; j < k; j++) {
               SymbolInterpretation symbolinterpretation = (SymbolInterpretation)this.symbols.elementAt(j);
               if (symbolinterpretation.name.equals(s) && symbolinterpretation.arity == i) {
                  int[] aint = i == 0 ? null : new int[i];

                  for (int l = 0; l < i; l++) {
                     object = this.evaluate(expression.getChild(l), variablescope);
                     if (!this.iTest(object)) {
                        return null;
                     }

                     aint[l] = this.iValue(object);
                  }

                  return symbolinterpretation.getValue(aint);
               }
            }

            if (s.equals("@")) {
               if (i < 2) {
                  return null;
               } else {
                  object = expression.getChild(0).symbol;

                  for (int k1 = 0; k1 < this.size; k1++) {
                     variablescope.push(object, new Integer(k1));
                     Object object4 = this.evaluate(expression.getChild(1), variablescope);
                     variablescope.pop(object);
                     if (!this.bTest(object4)) {
                        return null;
                     }

                     if (!this.bValue(object4)) {
                        return Boolean.FALSE;
                     }
                  }

                  return Boolean.TRUE;
               }
            } else if (s.equals("!")) {
               if (i < 2) {
                  return null;
               } else {
                  object = expression.getChild(0).symbol;

                  for (int j1 = 0; j1 < this.size; j1++) {
                     variablescope.push(object, new Integer(j1));
                     Object object3 = this.evaluate(expression.getChild(1), variablescope);
                     variablescope.pop(object);
                     if (!this.bTest(object3)) {
                        return null;
                     }

                     if (this.bValue(object3)) {
                        return Boolean.TRUE;
                     }
                  }

                  return Boolean.FALSE;
               }
            } else if (s.equals("%")) {
               if (i < 2) {
                  return null;
               } else {
                  int l1 = 0;
                  int i2 = 0;
                  object = expression.getChild(0).symbol;

                  for (int i1 = 0; i1 < this.size; i1++) {
                     variablescope.push(object, new Integer(i1));
                     Object object2 = this.evaluate(expression.getChild(1), variablescope);
                     variablescope.pop(object);
                     if (!this.bTest(object2)) {
                        return null;
                     }

                     if (this.bValue(object2)) {
                        if (++l1 == 1) {
                           i2 = i1;
                        }
                     }
                  }

                  return l1 == 1 ? new Integer(i2) : new Integer(this.nonidentical);
               }
            } else {
               object = i > 0 ? this.evaluate(expression.getChild(0), variablescope) : null;
               Object object1 = i > 1 ? this.evaluate(expression.getChild(1), variablescope) : null;
               if (s.equals("~")) {
                  return this.bTest(object) ? new Boolean(!this.bValue(object)) : null;
               } else if (s.equals("->")) {
                  return this.bTest(object) && this.bTest(object1) ? new Boolean(!this.bValue(object) || this.bValue(object1)) : null;
               } else if (s.equals("<->")) {
                  return this.bTest(object) && this.bTest(object1) ? new Boolean(this.bValue(object) == this.bValue(object1)) : null;
               } else if (s.equals("&")) {
                  return this.bTest(object) && this.bTest(object1) ? new Boolean(this.bValue(object) && this.bValue(object1)) : null;
               } else if (s.equals("|")) {
                  return this.bTest(object) && this.bTest(object1) ? new Boolean(this.bValue(object) || this.bValue(object1)) : null;
               } else if (!s.equals("=")) {
                  return null;
               } else {
                  return this.iTest(object) && this.iTest(object1) ? new Boolean(this.iValue(object) == this.iValue(object1)) : null;
               }
            }
         }
      }
   }

   static Expression closure(Expression expression) {
      return expression == null ? null : expression.universalClosure();
   }

   SymbolCollector getSymbolList() {
      SymbolCollector symbolcollector = new SymbolCollector();
      if (this.statement == null) {
         return symbolcollector;
      } else {
         int i = this.statement.premises.length;

         for (int j = 0; j < i; j++) {
            symbolcollector.collect(this.statement.premises[j]);
         }

         symbolcollector.collect(this.statement.conclusion);
         symbolcollector.mergeInterpretations(this);
         return symbolcollector;
      }
   }

   void openDerivation(boolean flag, BusyIndicator busyindicator) {
      if ((!flag || this.size != 0) && this.statement != null) {
         String s = "";
         if (flag) {
            String s1 = LogicProgram.defaultVariable(0);
            String s2 = LogicProgram.variableLetter(0);
            if (this.size == 1) {
               s = "@" + s1 + s1 + "=" + s2 + "0";
            } else {
               String s3 = "@" + s1 + "(" + s1 + "=" + s2 + "0";

               for (int i = 1; i < this.size; i++) {
                  s3 = s3 + "|" + s1 + "=" + s2 + i;
               }

               s = s3 + ")";
            }
         }

         int j = this.statement.premiseTexts.length;

         for (int k = 0; k < j; k++) {
            s = s + (k == 0 && !flag ? "" : " . ") + this.statement.premiseTexts[k];
         }

         s = s + " .: " + this.statement.conclusionText + "`-`=";
         LPDerivation.startup(MainMenu.nextModuleBounds(), busyindicator, s);
      }
   }

   void openTruthAnalysis(boolean flag, BusyIndicator busyindicator) {
      String s = "";
      String s1 = LogicProgram.variableLetter(0);
      if (flag) {
         if (this.size == 0 || this.statement == null) {
            return;
         }

         int j = this.statement.premiseTexts.length;

         for (int i = 0; i < j; i++) {
            s = s + (i != 0 ? " . " : "") + this.statement.premises[i].expandQuantifiers(this.size, s1);
         }

         s = s + " .: " + this.statement.conclusion.expandQuantifiers(this.size, s1) + "`=";
      } else {
         s = LogicProgram.translateSymbols(this.problemPanel.workspaceField.getSelectedText(), kaplan, maggie) + "`=";
      }

      LPTruthAnalysis.startup(MainMenu.nextModuleBounds(), busyindicator, s);
   }

   static Expression parse(String s) {
      try {
         return LogicProgram.parseFormula(s);
      } catch (FormulaParseException formulaparseexception) {
         return null;
      }
   }

   void expand(String s, boolean flag) {
      if (this.problemPanel != null && this.problemPanel.workspaceField != null) {
         FormulaEntryField formulaentryfield = this.problemPanel.workspaceField;
         if (this.size == 0) {
            MessageDialog.showMessage(InvalidityMessage.get("invnot002"), null, null, null);
            formulaentryfield.requestFocus();
         } else {
            String s1 = formulaentryfield.getSelectedText();
            String s2 = LogicProgram.translateSymbols(s1, kaplan, maggie);
            Expression expression = parse(s2);
            if (expression == null) {
               Hashtable hashtable = Message.params("expression", s2);
               MessageDialog.showMessage(InvalidityMessage.get("inverr001"), hashtable, null, null);
               formulaentryfield.requestFocus();
            } else {
               boolean flag1 = true;
               if (this.size == 1) {
                  Expression expression1 = expression.getChild(1);
                  flag1 = !(expression1 instanceof QuantifiedFormula) && !(expression1 instanceof DescriptionTerm) && expression1.getChildCount() > 1;
               }

               if (!flag && !(expression instanceof QuantifiedFormula)) {
                  Hashtable hashtable1 = Message.params("expression", s2);
                  MessageDialog.showMessage(InvalidityMessage.get("inverr003"), hashtable1, null, null);
                  formulaentryfield.requestFocus();
               } else {
                  expression = expression.expandQuantifiers(this.size, s, flag);
                  String s3 = LogicProgram.translateSymbols(expression.toString(), maggie, kaplan);
                  if (flag1) {
                     s3 = "(" + s3 + ")";
                  }

                  formulaentryfield.replaceSelection(s3);
                  formulaentryfield.requestFocus();
               }
            }
         }
      }
   }

   void copyStatement() {
      FormulaEntryField formulaentryfield = this.problemPanel.workspaceField;
      int i = formulaentryfield.getSelectionStart();
      int j = formulaentryfield.getSelectionEnd();
      if (i >= j) {
         i = j = formulaentryfield.getCaretPosition();
      }

      String s = formulaentryfield.getText();
      String s1 = this.titlePanel.getStatement();
      formulaentryfield.setText(s.substring(0, i) + s1 + s.substring(j));
      j = i + s1.length();
      formulaentryfield.select(i, j);
      formulaentryfield.requestFocus();
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         TaggedRecord taggedrecord = new TaggedRecord(reader, true);
         String s = "";

         while (taggedrecord.readNext()) {
            String s1 = taggedrecord.getName();
            if (s1 != null && s1.trim().equalsIgnoreCase("invalidation")) {
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
                        } else if (s3.equalsIgnoreCase("noPrint")) {
                           if (noPrint == null) {
                              noPrint = new ProblemSelector();
                           }

                           noPrint.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noCheck")) {
                           if (noCheck == null) {
                              noCheck = new ProblemSelector();
                           }

                           noCheck.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noPrintCheck")) {
                           if (noPrintCheck == null) {
                              noPrintCheck = new ProblemSelector();
                           }

                           noPrintCheck.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new ProblemSelector();
                           }

                           monoProbs.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
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
                        } else if (s3.equalsIgnoreCase("noExpand")) {
                           if (noExpand == null) {
                              noExpand = new ProblemSelector();
                           }

                           noExpand.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("fullExpand")) {
                           if (fullExpand == null) {
                              fullExpand = new ProblemSelector();
                           }

                           fullExpand.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
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

   void newProblem() {
      this.loadProblem(newProblem);
   }

   void setProblemTitle(String s) {
      this.title = s;
      this.titlePanel.setTitleLabel(trimTitle(s));
   }

   static String trimTitle(String s) {
      if (s == null) {
         return null;
      } else {
         return isExercise(s) ? LogicProgram.stripNamePrefix(s) : s.trim();
      }
   }

   static synchronized boolean getProblemInfo() {
      if (exercises == null && !getExercises()) {
         return false;
      } else {
         if (problems == null) {
            if (!getProblems()) {
               return false;
            }

            if (problems.mergeExercises()) {
               saveProblems();
            }
         }

         return true;
      }
   }

   static boolean saveProblems() {
      if (!LogicProgram.checkSameUser()) {
         return false;
      } else {
         try {
            writeProblems(problems, "invwork.txt");
            return true;
         } catch (IOException ioexception) {
            LogicProgram.showFileError("not004", "invwork.txt");
            return false;
         }
      }
   }

   boolean saveRenamed(String s) {
      if (s == null) {
         return true;
      } else {
         String s1 = this.title;
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
               String s2 = InvalidityDialogs.askProblemName(flag ? this.title : null);
               if (s2 == null) {
                  return false;
               }

               this.setProblemTitle(s2);
               InvalidityProblemEntry invalidityproblementry = new InvalidityProblemEntry(TaggedRecord.withName(s, s2), false);
               this.problemIndex = problems.registerEntry(invalidityproblementry, false);
               this.problemIndex = this.problemIndex == -1 ? problems.size() : this.problemIndex + 1;
               problems.insertElementAt(invalidityproblementry, this.problemIndex);
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

   String getChangedProblem() {
      String s = this.saveProblem();
      String s1 = this.problemIndex == -1 ? newProblem : problems.getRecordAt(this.problemIndex);
      return TaggedRecord.stripTimestamp(s).equals(TaggedRecord.stripTimestamp(s1)) ? null : s;
   }

   static Vector getStatements(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         ProblemEntry problementry = problems.getEntryAt(aint[j]);
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         String s1 = getProblemStatement(taggedrecord);
         CellPanel cellpanel = new CellPanel();
         cellpanel.setLayout(new FixedColumnLayout(null, 1, new int[]{dimension.width}));
         LogicTextArea logictextarea;
         cellpanel.add(logictextarea = new LogicTextArea(LogicProgram.expandEscapes("\\l" + s + ": " + s1)));
         logictextarea.setLineWrap(true);
         logictextarea.setWrapStyleWord(true);
         vector.add(cellpanel);
      }

      return vector;
   }

   static Vector getResults(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         ProblemEntry problementry = problems.getEntryAt(aint[j]);
         int k = problementry.state;
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         if (!printIncorrect || k == 1) {
            String s1 = getProblemStatement(taggedrecord);
            String s2 = exercises.getRecord(s);
            LPInvalidation lpinvalidation = new LPInvalidation(true);
            lpinvalidation.loadProblem(problementry.name);
            CellPanel cellpanel = new CellPanel();
            cellpanel.setLayout(new FixedColumnLayout(null, 2, new int[]{20, dimension.width - 20}));
            cellpanel.add(new WrappedTextPanel(lpinvalidation.checkDisabled ? " " : InvalidityProblemEntry.STATE_CODES[k]));
            LogicTextArea logictextarea;
            cellpanel.add(logictextarea = new LogicTextArea(LogicProgram.expandEscapes("\\l" + trimTitle(s) + ": " + s1)));
            logictextarea.setLineWrap(true);
            logictextarea.setWrapStyleWord(true);
            vector.add(cellpanel);
         }

         if (LogicProgram.selectorMatches(logPrint, getExerciseTitle(s))) {
            LogicProgram.appendSubmitLog("invdata.txt", "R", problementry.name);
         }
      }

      return vector;
   }

   static Vector getPrintProblems(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         ProblemEntry problementry = problems.getEntryAt(aint[j]);
         int k = problementry.state;
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         if (!printIncorrect || k == 1 || k != 2) {
            String s1 = getProblemStatement(taggedrecord);
            LPInvalidation lpinvalidation = new LPInvalidation(true);
            lpinvalidation.problemPanel.setLimitWidth(dimension.width - 20);
            lpinvalidation.loadProblem(problementry.name);
            CellPanel cellpanel = new CellPanel();
            cellpanel.setLayout(new FixedColumnLayout(null, 2, new int[]{20, dimension.width - 20}));
            cellpanel.add(new WrappedTextPanel(lpinvalidation.checkDisabled ? " " : InvalidityProblemEntry.STATE_CODES[k]));
            LogicTextArea logictextarea;
            cellpanel.add(logictextarea = new LogicTextArea(LogicProgram.expandEscapes("\\l" + trimTitle(s) + ": " + s1)));
            logictextarea.setLineWrap(true);
            logictextarea.setWrapStyleWord(true);
            logictextarea.setFocusable(false);
            cellpanel.add(new LogicLabel(LogicProgram.expandEscapes("\\l" + trimTitle(s) + ": " + s1)));
            CellPanel cellpanel1 = new CellPanel();
            cellpanel1.setLayout(new VerticalStackLayout());
            cellpanel1.add(cellpanel);
            cellpanel1.add(lpinvalidation.problemPanel, "Center");
            vector.add(cellpanel1);
         }

         if (LogicProgram.selectorMatches(logPrint, getExerciseTitle(s))) {
            LogicProgram.appendSubmitLog("invdata.txt", "P", problementry.name);
         }
      }

      return vector;
   }

   static class InvalidityStartupTask extends LogicModule.ModuleStartupTask {
      InvalidityStartupTask(BusyIndicator busyindicator, Rectangle rectangle, String s) {
         super(busyindicator, rectangle, s);
      }

      @Override
      public void run() {
         LPInvalidation.allocateInvModule(this);
      }

      @Override
      public void continueStartup() {
         LPInvalidation.continueStartup();
      }
   }
}
