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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

class LPRecognition extends LogicModule {
   int fontSize;
   Font font;
   static final String workFileName = "recwork.txt";
   static final String logFileName = "recdata.txt";
   static final String digestVersKey = "recDigestVers";
   static Class messageClass = RecognitionMessage.class;
   static RecognitionProblemSet exercises = null;
   static RecognitionProblemSet problems = null;
   static ProblemSelector noErrMess = null;
   static ProblemSelector noPrintErr = null;
   static ProblemSelector noCheck = null;
   static ProblemSelector noPrintCheck = null;
   static ProblemSelector noPrint = null;
   static ProblemSelector monoProbs = null;
   static ProblemSelector logPrint = null;
   static ProblemSelector logSubmit = null;
   static ProblemSelector needPrint = null;
   static ProblemSelector needSubmit = null;
   static ProblemSelector addToDB = null;
   static ProblemSelector updateDB = null;
   static Vector instances = new Vector();
   static Vector activeRuleXRefs = null;
   static PrintQueue printQueue = new PrintQueue("Recognition");
   static String newProblem = null;
   static Vector startups;
   static boolean noUser = false;
   static boolean submitExam = false;
   static boolean printIncorrect = false;
   static boolean restating = false;
   RecognitionProblemPanel problem;
   RecognitionButtonPanel buttonPanel;
   JScrollPane scroller;
   SizedPanel scrollPanel;
   Vector activeRules;
   IntervalSet activeRange;
   boolean dontChange;
   boolean errorMessagesDisabled;
   boolean checkDisabled;
   int errorCount;
   long workTime;
   long loadTime;
   String lastUserProblem;
   Hashtable probOptions;
   static int moduleIndex = 3;

   LPRecognition(boolean flag) {
      super(flag);
      this.fontSize = LogicProgram.fontSize;
      this.setLayout(new BorderLayout());
      this.add(this.titlePanel, "North");
      this.scroller = new JScrollPane();
      this.add(this.scroller, "Center");
      this.scroller.getVerticalScrollBar().setUnitIncrement(22);
      this.scroller.setViewportView(this.scrollPanel = new SizedPanel());
      this.scroller.setBackground(LogicConstants.bruinBlue);
      this.scrollPanel.add(this.problem = new RecognitionProblemPanel(this), "Center");
      this.add(this.buttonPanel = RecognitionButtonPanel.create(this), "South");
      this.newProblem();
   }

   @Override
   int getModuleIndex() {
      return moduleIndex;
   }

   void reset() {
      this.errorMessagesDisabled = false;
      this.checkDisabled = false;
      this.problemIndex = -1;
      this.errorCount = 0;
      this.workTime = 0L;
      this.loadTime = 0L;
      this.lastUserProblem = null;
      this.probOptions = null;
      this.activeRules = null;
      this.activeRange = null;
      this.removeWork();
   }

   static synchronized void startup(Rectangle rectangle, BusyIndicator busyindicator, String s) {
      if (startups == null) {
         startups = new Vector();
      }

      if (exercises != null || getExercises()) {
         LPRecognition.RecognitionStartup lprecognition$recognitionstartup = new LPRecognition.RecognitionStartup(busyindicator, rectangle, s);
         startups.add(lprecognition$recognitionstartup);
         if (startups.size() <= 1) {
            if (problems == null) {
               if (!getProblems()) {
                  lprecognition$recognitionstartup.stopBusyIndicator();
                  startups.remove(lprecognition$recognitionstartup);
                  return;
               }

               if (problems.mergeExercises()) {
                  saveProblems();
               }

               problems.restateProblems(lprecognition$recognitionstartup);
            } else {
               lprecognition$recognitionstartup.continueStartup();
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
      RecognitionButtonPanel recognitionbuttonpanel = RecognitionButtonPanel.create(this);
      Dimension dimension = recognitionbuttonpanel.getPreferredSize();
      if (rectangle.width < dimension.width) {
         rectangle.width = dimension.width;
      }

      return rectangle;
   }

   static void allocateRecModule(LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
      LPRecognition lprecognition = new LPRecognition(false);
      instances.addElement(lprecognition);
      if (logicmodule$modulestartuptask.problemName == null || newProblem == null) {
         lprecognition.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lprecognition.saveProblem();
         }
      }

      if (logicmodule$modulestartuptask.problemName != null) {
         lprecognition.loadProblem(logicmodule$modulestartuptask.problemName);
         logicmodule$modulestartuptask.problemName = null;
      }

      lprecognition.setupFrame(LPInfo.programName + ": Recognizing Rules");
      lprecognition.buttonPanel.installDefaultButton();
      lprecognition.frame.setBounds(lprecognition.fixModuleRect(logicmodule$modulestartuptask.bounds));
      lprecognition.frame.setVisible(true);
      lprecognition.requestFocus();
   }

   @Override
   public boolean shutdown(boolean flag) {
      if (!flag && !RecognitionDialogs.confirmSaveChanges(this, null)) {
         return false;
      } else {
         this.reset();
         synchronized (moduleClasses[3]) {
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

   static synchronized LPRecognition openInstance(int i, boolean flag) {
      if (i >= 0 && instances != null && !instances.isEmpty()) {
         int j = instances.size();

         for (int k = 0; k < j; k++) {
            LPRecognition lprecognition = (LPRecognition)instances.elementAt(k);
            if (lprecognition.problemIndex == i) {
               if (flag) {
                  lprecognition.requestFocus();
               }

               return lprecognition;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Override
   public void requestFocus() {
      this.problem.ruleField.requestFocus();
   }

   int getProblemIndex() {
      return this.problemIndex;
   }

   @Override
   public boolean save() {
      return RecognitionDialogs.confirmSaveChanges(this, null);
   }

   static boolean checkQuit(Hashtable hashtable, Hashtable hashtable1) {
      resetOptions();
      readOptions(LogicProgram.openDataFile("options", false));
      readOptions(LogicProgram.openLocalFile("options", false));
      logNeeds();
      if (needPrint != null && !needPrint.isEmpty() || needSubmit != null && !needSubmit.isEmpty()) {
         ProblemRecordEnumeration problemrecordenumeration = new ProblemRecordEnumeration(readWork());
         problemrecordenumeration.retainExisting(readExercises());
         MainMenu.mergeSubmitStatus(hashtable, "recdata.txt", "P", needPrint, problemrecordenumeration);
         problemrecordenumeration.reset();
         MainMenu.mergeSubmitStatus(hashtable1, "recdata.txt", "S", needSubmit, problemrecordenumeration);
      }

      resetOptions();
      return true;
   }

   static Vector getChangedProblems() {
      ProblemRecordEnumeration problemrecordenumeration = new ProblemRecordEnumeration(readWork());
      Hashtable hashtable = LogicProgram.checkSubmitLog("recdata.txt", "S", needSubmit, problemrecordenumeration);
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

   static void resetOptions() {
      noUser = false;
      submitExam = false;
      printIncorrect = false;
      activeRuleXRefs = new Vector();
      noErrMess = null;
      noCheck = null;
      noPrint = null;
      noPrintCheck = null;
      noPrintErr = null;
      monoProbs = null;
      addToDB = null;
      updateDB = null;
      logPrint = null;
      logSubmit = null;
      needPrint = null;
      needSubmit = null;
      restating = false;
   }

   @Override
   public void resize() {
      if (this.frame != null) {
         int i = LogicProgram.getInteriorBounds(this.scroller).width - 16;
         this.problem.resultPanel.setLimitWidth(i);
         this.problem.resultPanel.invalidate();
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
      this.problem.verdictLabel.setFont(LogicProgram.getFont(i * 3 / 2));
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

   static int getProblemState(String s) {
      return new LPRecognition(false).getProblemState(new TaggedRecord(s));
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

   void setupFrame(String s) {
      this.frame = new ModuleFrame(s);
      this.setFontSize(this.fontSize);
      this.setColors(this.colors);
      this.frame.module = this;
      this.frame.add(this, "Center");
   }

   static String getProblemStatement(String s) {
      return getProblemStatement(new TaggedRecord(s));
   }

   static String getProblemStatement(TaggedRecord taggedrecord) {
      return taggedrecord.valueAt(taggedrecord.indexOfAnyTag("="));
   }

   static synchronized boolean getProblems() {
      if (problems != null) {
         return true;
      } else {
         RecognitionProblemSet recognitionproblemset = readWork();
         if (recognitionproblemset == null) {
            return false;
         } else {
            if (recognitionproblemset.readFromPlainFile && !recognitionproblemset.computeDigest(LogicProgram.user).equals(recognitionproblemset.storedDigest)) {
               System.out.println("Could not digest file: recwork.txt");
               if (!UserSetup.hasAccess("indigestion", "instructor")) {
                  LogicProgram.showFileError("not003", "recwork.txt");
                  return false;
               }
            }

            problems = recognitionproblemset;
            RecognitionProblemEntry.savedWork = ProblemEntry.findExtraProblems("recwork.txt", problems);
            ProblemEntry.markExtraProblems(exercises, RecognitionProblemEntry.savedWork);
            return true;
         }
      }
   }

   static boolean getExercises() {
      if (!RecognitionMessage.loadMessages()) {
         LogicProgram.showFileError("not001", "the rule recognition messages file");
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

   boolean checkProblem() {
      boolean flag = this.problem.checkAnswer();
      this.titlePanel.setStatus(flag ? "Correct" : "Incorrect");
      return flag;
   }

   static boolean hasWork(String s) {
      return hasWork(new TaggedRecord(s));
   }

   static boolean hasWork(TaggedRecord taggedrecord) {
      return taggedrecord.indexOfTag('*') != -1;
   }

   static String getWork(TaggedRecord taggedrecord) {
      return taggedrecord.formatFields("*");
   }

   void loadProblem(String s) {
      this.loadProblem(new TaggedRecord(s));
   }

   void loadProblem(TaggedRecord taggedrecord) {
      this.reset();
      this.loadExerciseInfo(taggedrecord);
      this.problem.loadProblem(taggedrecord);
      this.errorCount = taggedrecord.getErrorCount();
      this.workTime = taggedrecord.getTimestamp();
      this.loadTime = 0L;
      this.updateWorkTime();
      this.problemIndex = -1;
      this.resize();
   }

   void loadUserProblem(String s) {
      ArgumentParser argumentparser = new ArgumentParser(s);
      String s1 = argumentparser.getUnparsedText();
      if (s1 != null) {
         Hashtable hashtable = Message.params("expression", LogicProgram.translateSymbols(s1, maggie, kaplan));
         MessageDialog.showMessage(RecognitionMessage.get("recerr001"), hashtable, null, null);
      } else if (!argumentparser.conclusionOnly && argumentparser.getErrorCode() == 0) {
         this.loadProblem(TaggedRecord.toLine(TaggedRecord.formatField(ArgumentParser.normalizeDots(s), '=')));
      } else {
         Message message = RecognitionMessage.get("recerr002");
         MessageDialog.showMessage(RecognitionMessage.get("recerr002"), null, null, null);
      }
   }

   void newProblem() {
      this.loadProblem(newProblem);
   }

   void removeWork() {
      this.titlePanel.setStatus(null);
      this.problem.clearAnswer();
   }

   static String removeWork(TaggedRecord taggedrecord) {
      return taggedrecord.formatFields("$=@~&%u!");
   }

   static boolean readProblems(Reader reader, RecognitionProblemSet recognitionproblemset, boolean flag) {
      return readProblems(reader, recognitionproblemset, flag, false);
   }

   static boolean mergeProblems(Reader reader, RecognitionProblemSet recognitionproblemset, boolean flag) {
      return readProblems(reader, recognitionproblemset, flag, true);
   }

   static boolean readProblems(Reader reader, RecognitionProblemSet recognitionproblemset, boolean flag, boolean flag1) {
      return LogicModule.readProblems(reader, recognitionproblemset, flag, flag1);
   }

   static boolean saveProblems() {
      if (!LogicProgram.checkSameUser()) {
         return false;
      } else {
         try {
            writeProblems(problems, new FileWriter(new File(LogicProgram.workDir, "recwork.txt")));
            return true;
         } catch (IOException ioexception) {
            LogicProgram.showFileError("not004", "recwork.txt");
            return false;
         }
      }
   }

   void setProblemTitle(String s) {
      if (s != null && (s = s.trim()).equals("")) {
         s = null;
      }

      this.problem.problemName = s;
      this.titlePanel.setTitleLabel(trimTitle(s));
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
               String s2 = RecognitionDialogs.askProblemName(flag ? this.problem.problemName : null);
               if (s2 == null) {
                  return false;
               }

               this.setProblemTitle(s2);
               RecognitionProblemEntry recognitionproblementry = new RecognitionProblemEntry(TaggedRecord.withName(s, s2), false);
               this.problemIndex = problems.registerEntry(recognitionproblementry, false);
               this.problemIndex = this.problemIndex == -1 ? problems.size() : this.problemIndex + 1;
               problems.insertElementAt(recognitionproblementry, this.problemIndex);
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

   static RecognitionProblemSet readExercises() {
      return readExercises(LogicProgram.noCoreProblems, false, false);
   }

   static RecognitionProblemSet readExercises(boolean flag, boolean flag1, boolean flag2) {
      RecognitionProblemSet recognitionproblemset = new RecognitionProblemSet();
      if (!flag) {
         ScrambledReader scrambledreader = LogicProgram.openDataFile("recwork.txt", false);
         if (scrambledreader == null) {
            LogicProgram.showFileError("not001", "the core Recognition exercise file");
            return null;
         }

         if (!readProblems(scrambledreader, recognitionproblemset, true)) {
            LogicProgram.showFileError("not002", "the core Recognition exercise file");
            return null;
         }
      }

      if (!flag1) {
         ScrambledReader scrambledreader1 = LogicProgram.openLocalFile("recwork.txt", flag2);
         if (scrambledreader1 != null
            && (flag ? !readProblems(scrambledreader1, recognitionproblemset, true) : !mergeProblems(scrambledreader1, recognitionproblemset, true))) {
            LogicProgram.showFileError("not002", "the local Recognition exercise file");
            return null;
         }
      }

      return recognitionproblemset;
   }

   static RecognitionProblemSet readWork() {
      if (!LogicProgram.checkSameUser()) {
         return null;
      } else {
         RecognitionProblemSet recognitionproblemset = new RecognitionProblemSet();
         ScrambledReader scrambledreader = LogicProgram.openDataFile("recwork.txt", true);
         if (!LogicProgram.noCoreProblems || scrambledreader instanceof PlainRecordReader) {
            if (scrambledreader == null) {
               LogicProgram.showFileError("not001", "recwork.txt");
               return null;
            }

            if (scrambledreader instanceof PlainRecordReader) {
               recognitionproblemset.readFromPlainFile = true;
            }

            if (!readProblems(scrambledreader, recognitionproblemset, false)) {
               LogicProgram.showFileError("not002", "recwork.txt");
               return null;
            }
         }

         if (!(scrambledreader instanceof PlainRecordReader)) {
            scrambledreader = LogicProgram.openLocalFile("recwork.txt", false);
            if (scrambledreader != null && !mergeProblems(scrambledreader, recognitionproblemset, false)) {
               LogicProgram.showFileError("not002", "recwork.txt");
               return null;
            }
         }

         return recognitionproblemset;
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
      TaggedRecord taggedrecord1 = new TaggedRecord(exercises == null ? null : exercises.getRecord(taggedrecord.getName()));
      String s = taggedrecord1.getName();
      String s1 = taggedrecord1.valueAt(taggedrecord1.indexOfTag('o'));
      if (s1 != null) {
         s = s1;
      }

      this.probOptions = taggedrecord1.getKeyValues('%');
      this.dontChange = this.probOptions != null && this.probOptions.containsKey("eg");
      this.errorMessagesDisabled = LogicProgram.selectorMatches(this.forPrint ? noPrintErr : noErrMess, s);
      this.checkDisabled = LogicProgram.selectorMatches(this.forPrint ? noPrintCheck : noCheck, s);
      this.activeRules = new Vector();
      this.activeRange = new IntervalSet();
      int j = activeRuleXRefs == null ? 0 : activeRuleXRefs.size();

      for (int i = 0; i < j; i++) {
         ((RuleCrossReference)activeRuleXRefs.elementAt(i)).applyTo(s, this.activeRules, this.activeRange);
      }
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         TaggedRecord taggedrecord = new TaggedRecord(reader, true);
         String s = "";

         while (taggedrecord.readNext()) {
            String s1 = taggedrecord.getName();
            if (s1 != null && s1.trim().equalsIgnoreCase("recognition")) {
               int[] aint = taggedrecord.indexesOfAnyTag("a+?");
               int i = aint.length;

               for (int j = 0; j < i; j++) {
                  char c0 = taggedrecord.tagAt(aint[j]);
                  String s2 = taggedrecord.valueAt(aint[j]);
                  if (c0 == 'a') {
                     activeRuleXRefs.addElement(new RuleCrossReference(s2, null, true).withPrefix(s));
                  } else if (c0 == '+') {
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
                        if (s3.equalsIgnoreCase("noErrMess")) {
                           if (noErrMess == null) {
                              noErrMess = new ProblemSelector();
                           }

                           noErrMess.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noCheck")) {
                           if (noCheck == null) {
                              noCheck = new ProblemSelector();
                           }

                           noCheck.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noPrint")) {
                           if (noPrint == null) {
                              noPrint = new ProblemSelector();
                           }

                           noPrint.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noPrintCheck")) {
                           if (noPrintCheck == null) {
                              noPrintCheck = new ProblemSelector();
                           }

                           noPrintCheck.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noPrintErr")) {
                           if (noPrintErr == null) {
                              noPrintErr = new ProblemSelector();
                           }

                           noPrintErr.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new ProblemSelector();
                           }

                           monoProbs.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
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

   boolean ruleActive(Rule rule) {
      for (SchematicRule schematicrule : rule.getAllForms()) {
         if (this.activeRules.indexOf(schematicrule.name) != -1) {
            return true;
         }

         Theorem theorem = schematicrule.sourceTheorem;
         if (theorem != null && this.activeRange.contains(theorem.number)) {
            return true;
         }
      }

      return false;
   }

   Rule activeRules() {
      Rule rule = new Rule("activeRules");
      int i = this.activeRules.size();

      for (int j = 0; j < i; j++) {
         rule.addComponent(LogicProgram.getRule((String)this.activeRules.elementAt(j)));
      }

      Enumeration enumeration = this.activeRange.elements();

      while (enumeration.hasMoreElements()) {
         rule.addComponent(LogicProgram.ruleTable.getTheorem((Integer)enumeration.nextElement()));
      }

      return rule;
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
      if (UserSetup.confirmSubmitAll("Recognizing Rules")) {
         int[] aint = getExerciseIndices();
         BusyIndicator busyindicator = new BusyIndicator(this);
         Submission submission = ServerConnection.prepareSubmission(busyindicator);
         if (submission != null) {
            if (RecognitionDialogs.confirmSaveChanges(this, null)) {
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
         int[] aint = RecognitionDialogs.chooseProblemsToSubmit(this);
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
            submission.evaluation = RecognitionProblemEntry.STATE_CODES[k];
            submission.work = s;
            submission.problemName = taggedrecord.getName();
            submission.module = moduleAbbrs[moduleIndex];
            submission.helpCount = taggedrecord.getErrorCount();
            submission.duration = taggedrecord.getTimestamp();
            boolean flag = LogicProgram.selectorMatches(logSubmit, getExerciseTitle(submission.problemName));
            if (ServerConnection.submit(submission, busyindicator)) {
               vector.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.appendSubmitLog("recdata.txt", "S", s, submission.getLogRecord());
               }
            } else {
               vector1.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.appendSubmitLog("recdata.txt", "F", s);
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
         int[] aint = RecognitionDialogs.chooseProblemsToUpload(this);
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
         if (!printIncorrect || k == 1) {
            String s1 = getProblemStatement(taggedrecord);
            s1 = LogicProgram.translateSymbols(s1, maggie, kaplan);
            String s2 = taggedrecord.valueAt(taggedrecord.indexOfTag('*'));
            LPRecognition lprecognition = new LPRecognition(true);
            lprecognition.loadProblem(problementry.name);
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new VerticalStackLayout());
            JPanel jpanel1 = new JPanel();
            jpanel1.setLayout(new FixedColumnLayout(null, 2, new int[]{20, dimension.width - 20}));
            jpanel1.add(new WrappedTextPanel(lprecognition.checkDisabled ? " " : RecognitionProblemEntry.STATE_CODES[k]));
            LogicTextArea logictextarea;
            jpanel1.add(logictextarea = new LogicTextArea(LogicProgram.expandEscapes("\\l" + trimTitle(s) + ": " + s1)));
            logictextarea.setLineWrap(true);
            logictextarea.setWrapStyleWord(true);
            logictextarea.setBackground(LogicProgram.printColors[1]);
            jpanel.add(jpanel1);
            if (!flag && !lprecognition.checkDisabled && s2 != null) {
               StyledTextPane styledtextpane = new StyledTextPane("Answer: " + s2);
               styledtextpane.setBackground(LogicProgram.printColors[1]);
               jpanel.add(styledtextpane);
            }

            vector.add(jpanel);
         }

         if (LogicProgram.selectorMatches(logPrint, getExerciseTitle(s))) {
            LogicProgram.appendSubmitLog("recdata.txt", flag ? "R" : "P", problementry.name);
         }
      }

      return vector;
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
         StyledTextPane styledtextpane;
         jpanel.add(styledtextpane = new StyledTextPane(LogicProgram.expandEscapes("\\l" + s + ": " + s1)));
         styledtextpane.setWrapLines(true);
         styledtextpane.setWrapWords(true);
         styledtextpane.setBackground(LogicProgram.printColors[1]);
         vector.add(jpanel);
      }

      return vector;
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
      return s != null && s.toLowerCase().startsWith("recog") ? "1" : "NULL";
   }

   long updateWorkTime() {
      long i = (new Date().getTime() + 500L) / 1000L;
      if (this.loadTime != 0L) {
         this.workTime = this.workTime + (i - this.loadTime);
      }

      this.loadTime = i;
      return this.workTime;
   }

   static class RecognitionStartup extends LogicModule.ModuleStartupTask {
      RecognitionStartup(BusyIndicator busyindicator, Rectangle rectangle, String s) {
         super(busyindicator, rectangle, s);
      }

      @Override
      public void run() {
         LPRecognition.allocateRecModule(this);
      }

      @Override
      public void continueStartup() {
         LPRecognition.continueStartup();
      }
   }
}
