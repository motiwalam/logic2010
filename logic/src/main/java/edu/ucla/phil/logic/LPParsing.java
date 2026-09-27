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
   ParsingProblemPanel problem;
   int fontSize;
   Font font;
   static final String workFileName = "parwork.txt";
   static final String logFileName = "pardata.txt";
   static final String digestVersKey = "parDigestVers";
   static Class messageClass = ParsingMessage.class;
   static ParsingProblemSet exercises = null;
   static ParsingProblemSet problems = null;
   static Vector instances = new Vector();
   static PrintQueue printQueue = new PrintQueue("Parsing");
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
      if (!ParsingMessage.loadMessages()) {
         LogicProgram.showFileError("not001", "the parsing messages file");
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

   static ParsingProblemSet readExercises() {
      return readExercises(LogicProgram.noCoreProblems, false, false);
   }

   static ParsingProblemSet readExercises(boolean flag, boolean flag1, boolean flag2) {
      ParsingProblemSet parsingproblemset = new ParsingProblemSet();
      if (!flag) {
         ScrambledReader scrambledreader = LogicProgram.openDataFile("parwork.txt", false);
         if (scrambledreader == null) {
            LogicProgram.showFileError("not001", "the core Parsing exercise file");
            return null;
         }

         if (!readProblems(scrambledreader, parsingproblemset, true)) {
            LogicProgram.showFileError("not002", "the core Parsing exercise file");
            return null;
         }
      }

      if (!flag1) {
         ScrambledReader scrambledreader1 = LogicProgram.openLocalFile("parwork.txt", flag2);
         if (scrambledreader1 != null
            && (flag ? !readProblems(scrambledreader1, parsingproblemset, true) : !mergeProblems(scrambledreader1, parsingproblemset, true))) {
            LogicProgram.showFileError("not002", "the local Parsing exercise file");
            return null;
         }
      }

      return parsingproblemset;
   }

   LPParsing(boolean flag) {
      super(flag);
      this.fontSize = LogicProgram.fontSize;
      this.setLayout(new BorderLayout());
      this.add(this.titlePanel, "North");
      this.add(this.scroller = new JScrollPane(), "Center");
      this.scroller.getVerticalScrollBar().setUnitIncrement(22);
      this.scroller.setViewportView(this.scrollPanel = new SizedPanel());
      this.scrollPanel.add(this.problem = new ParsingProblemPanel(this), "Center");
      this.add(ParsingToolbar.create(this), "South");
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
         LPParsing.ParsingStartupTask lpparsing$parsingstartuptask = new LPParsing.ParsingStartupTask(busyindicator, rectangle, s);
         startups.add(lpparsing$parsingstartuptask);
         if (startups.size() <= 1) {
            if (problems == null) {
               if (!getProblems()) {
                  lpparsing$parsingstartuptask.stopBusyIndicator();
                  startups.remove(lpparsing$parsingstartuptask);
                  return;
               }

               if (problems.mergeExercises()) {
                  saveProblems();
               }

               problems.restateProblems(lpparsing$parsingstartuptask);
            } else {
               lpparsing$parsingstartuptask.continueStartup();
            }
         }
      }
   }

   static synchronized void continueStartup() {
      restating = false;
      int i = startups.size();

      for (int j = 0; j < i; j++) {
         ((LogicModule.ModuleStartupTask)startups.get(i - 1 - j)).busyIndicator.setBusy(false);
         ((LogicModule.ModuleStartupTask)startups.get(i - 1 - j)).busyIndicator = null;
      }

      while (!startups.isEmpty()) {
         LogicModule.ModuleStartupTask logicmodule$modulestartuptask = (LogicModule.ModuleStartupTask)startups.remove(0);
         SwingUtilities.invokeLater(logicmodule$modulestartuptask);
      }
   }

   Rectangle fixModuleRect(Rectangle rectangle) {
      ParsingToolbar parsingtoolbar = ParsingToolbar.create(this);
      Dimension dimension = parsingtoolbar.getPreferredSize();
      if (rectangle.width < dimension.width) {
         rectangle.width = dimension.width;
      }

      return rectangle;
   }

   static void allocateParModule(LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
      LPParsing lpparsing = new LPParsing(false);
      instances.addElement(lpparsing);
      if (logicmodule$modulestartuptask.problemName == null || newProblem == null) {
         lpparsing.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lpparsing.saveProblem();
         }
      }

      if (logicmodule$modulestartuptask.problemName != null) {
         lpparsing.loadProblem(logicmodule$modulestartuptask.problemName);
         logicmodule$modulestartuptask.problemName = null;
      }

      lpparsing.setupFrame(LPInfo.programName + ": Parsing");
      lpparsing.frame.setBounds(lpparsing.fixModuleRect(logicmodule$modulestartuptask.bounds));
      lpparsing.frame.setVisible(true);
      lpparsing.requestFocus();
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
      if (!flag && !ParsingDialogs.confirmSaveChanges(this, null)) {
         return false;
      } else {
         this.reset();
         synchronized (moduleClasses[2]) {
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
      return ParsingDialogs.confirmSaveChanges(this, null);
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
      readOptions(LogicProgram.openLocalFile("options", false));
      logNeeds();
      if (needPrint != null && !needPrint.isEmpty() || needSubmit != null && !needSubmit.isEmpty()) {
         ProblemRecordEnumeration problemrecordenumeration = new ProblemRecordEnumeration(readWork());
         problemrecordenumeration.retainExisting(readExercises());
         MainMenu.mergeSubmitStatus(hashtable, "pardata.txt", "R", needPrint, problemrecordenumeration);
         problemrecordenumeration.reset();
         MainMenu.mergeSubmitStatus(hashtable1, "pardata.txt", "S", needSubmit, problemrecordenumeration);
      }

      resetOptions();
      return true;
   }

   static Vector getChangedProblems() {
      ProblemRecordEnumeration problemrecordenumeration = new ProblemRecordEnumeration(readWork());
      Hashtable hashtable = LogicProgram.checkSubmitLog("pardata.txt", "S", needSubmit, problemrecordenumeration);
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
         ParsingProblemSet parsingproblemset = readWork();
         if (parsingproblemset == null) {
            return false;
         } else {
            if (parsingproblemset.readFromPlainFile && !parsingproblemset.computeDigest(LogicProgram.user).equals(parsingproblemset.storedDigest)) {
               System.out.println("Could not digest file: parwork.txt");
               if (!UserSetup.hasAccess("indigestion", "instructor")) {
                  LogicProgram.showFileError("not003", "parwork.txt");
                  return false;
               }
            }

            problems = parsingproblemset;
            ParsingProblemEntry.newProblemNames = ProblemEntry.findExtraProblems("parwork.txt", problems);
            ProblemEntry.markExtraProblems(exercises, ParsingProblemEntry.newProblemNames);
            return true;
         }
      }
   }

   static ParsingProblemSet readWork() {
      if (!LogicProgram.checkSameUser()) {
         return null;
      } else {
         ParsingProblemSet parsingproblemset = new ParsingProblemSet();
         ScrambledReader scrambledreader = LogicProgram.openDataFile("parwork.txt", true);
         if (!LogicProgram.noCoreProblems || scrambledreader instanceof PlainRecordReader) {
            if (scrambledreader == null) {
               LogicProgram.showFileError("not001", "parwork.txt");
               return null;
            }

            if (scrambledreader instanceof PlainRecordReader) {
               parsingproblemset.readFromPlainFile = true;
            }

            if (!readProblems(scrambledreader, parsingproblemset, false)) {
               LogicProgram.showFileError("not002", "parwork.txt");
               return null;
            }
         }

         if (!(scrambledreader instanceof PlainRecordReader)) {
            scrambledreader = LogicProgram.openLocalFile("parwork.txt", false);
            if (scrambledreader != null && !mergeProblems(scrambledreader, parsingproblemset, false)) {
               LogicProgram.showFileError("not002", "parwork.txt");
               return null;
            }
         }

         return parsingproblemset;
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
      this.titlePanel.setNote(taggedrecord.valueAt(taggedrecord.indexOfTag('!')));
      String s = taggedrecord.getName();
      this.checkNow = LogicProgram.selectorMatches(autoCheck, s);
      this.checkDisabled = LogicProgram.selectorMatches(this.forPrint ? noPrintCheck : noCheck, s);
      this.noDescent = LogicProgram.selectorMatches(mainOnly, s);
      this.checkNow = this.checkNow & !this.checkDisabled;
   }

   static boolean readProblems(Reader reader, ParsingProblemSet parsingproblemset, boolean flag) {
      return readProblems(reader, parsingproblemset, flag, false);
   }

   static boolean mergeProblems(Reader reader, ParsingProblemSet parsingproblemset, boolean flag) {
      return readProblems(reader, parsingproblemset, flag, true);
   }

   static boolean readProblems(Reader reader, ParsingProblemSet parsingproblemset, boolean flag, boolean flag1) {
      return LogicModule.readProblems(reader, parsingproblemset, flag, flag1);
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         TaggedRecord taggedrecord = new TaggedRecord(reader, true);
         String s = "";

         while (taggedrecord.readNext()) {
            String s1 = taggedrecord.getName();
            if (s1 != null && s1.trim().equalsIgnoreCase("parsing")) {
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
                        if (s3.equalsIgnoreCase("autoCheck")) {
                           if (autoCheck == null) {
                              autoCheck = new ProblemSelector();
                           }

                           autoCheck.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("addToDB")) {
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
                        } else if (s3.equalsIgnoreCase("mainOnly")) {
                           if (mainOnly == null) {
                              mainOnly = new ProblemSelector();
                           }

                           mainOnly.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
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
      this.problem.loadRecord(taggedrecord);
      this.errorCount = taggedrecord.getErrorCount();
      this.workTime = taggedrecord.getTimestamp();
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
      this.loadProblem(TaggedRecord.toLine(TaggedRecord.formatField(s, '=')));
   }

   void removeWork() {
      this.problem.resetWork();
   }

   static String removeWork(TaggedRecord taggedrecord) {
      return taggedrecord.formatFields("$=%u!");
   }

   void newProblem() {
      this.loadProblem(newProblem);
   }

   void check() {
      ErrorRef errorref = this.problem.checkProblem();
      if (!this.checkDisabled) {
         Hashtable hashtable = errorref.params;
         this.titlePanel.setStatus(hashtable == null ? "" : (String)hashtable.get("summary"));
      }
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
      if (UserSetup.confirmSubmitAll("Parsing")) {
         int[] aint = getExerciseIndices();
         BusyIndicator busyindicator = new BusyIndicator(this);
         Submission submission = ServerConnection.prepareSubmission(busyindicator);
         if (submission != null) {
            if (ParsingDialogs.confirmSaveChanges(this, null)) {
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
         int[] aint = ParsingDialogs.chooseProblemsToSubmit(this);
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
            submission.evaluation = ParsingProblemEntry.STATE_CODES[k];
            submission.work = s;
            submission.problemName = taggedrecord.getName();
            submission.module = moduleAbbrs[moduleIndex];
            submission.helpCount = taggedrecord.getErrorCount();
            submission.duration = taggedrecord.getTimestamp();
            boolean flag = LogicProgram.selectorMatches(logSubmit, getExerciseTitle(submission.problemName));
            if (ServerConnection.submit(submission, busyindicator)) {
               vector.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.appendSubmitLog("pardata.txt", "S", s, submission.getLogRecord());
               }
            } else {
               vector1.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.appendSubmitLog("pardata.txt", "F", s);
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
         int[] aint = ParsingDialogs.chooseProblemsToUpload(this);
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
            writeProblems(problems, new FileWriter(new File(LogicProgram.workDir, "parwork.txt")));
            return true;
         } catch (IOException ioexception) {
            LogicProgram.showFileError("not004", "parwork.txt");
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
               String s2 = ParsingDialogs.askProblemName(flag ? this.problem.problemName : null);
               if (s2 == null) {
                  return false;
               }

               this.setProblemTitle(s2);
               ParsingProblemEntry parsingproblementry = new ParsingProblemEntry(TaggedRecord.withName(s, s2), false);
               this.problemIndex = problems.registerEntry(parsingproblementry, false);
               this.problemIndex = this.problemIndex == -1 ? problems.size() : this.problemIndex + 1;
               problems.insertElementAt(parsingproblementry, this.problemIndex);
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
      if (s != null && !(s = s.trim()).equals("")) {
         this.problem.problemName = s;
         this.titlePanel.setTitleLabel(trimTitle(s));
      } else {
         this.problem.problemName = null;
         this.titlePanel.setTitleLabel(null);
      }
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
         FormulaParseNode formulaparsenode = new FormulaParseNode(s);
         if (s3 != null) {
            return s1.equals(formulaparsenode.getNotationCode()) && s3.charAt(0) == 84 ? 2 : 1;
         } else {
            return s1.equals(formulaparsenode.getNotationCode()) && s2.equals(formulaparsenode.getStructureString()) ? 2 : 1;
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
      return taggedrecord.formatFields("[]*");
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
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new FixedColumnLayout(null, 2, new int[]{20, dimension.width - 20}));
            jpanel.add(new WrappedTextPanel(ParsingProblemEntry.STATE_CODES[k]));
            LogicTextArea logictextarea;
            jpanel.add(logictextarea = new LogicTextArea(LogicProgram.expandEscapes("\\l" + s + ": " + s1)));
            logictextarea.setLineWrap(true);
            logictextarea.setWrapStyleWord(true);
            logictextarea.setBackground(LogicProgram.printColors[1]);
            vector.add(jpanel);
         }

         if (LogicProgram.selectorMatches(logPrint, getExerciseTitle(s))) {
            LogicProgram.appendSubmitLog("pardata.txt", "R", problementry.name);
         }
      }

      return vector;
   }

   static class ParsingStartupTask extends LogicModule.ModuleStartupTask {
      ParsingStartupTask(BusyIndicator busyindicator, Rectangle rectangle, String s) {
         super(busyindicator, rectangle, s);
      }

      @Override
      public void run() {
         LPParsing.allocateParModule(this);
      }

      @Override
      public void continueStartup() {
         LPParsing.continueStartup();
      }
   }
}
