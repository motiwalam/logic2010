package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

class LPDerivation extends LogicModule implements RulePropertySource, DerivationConstants {
   int fontSize;
   Font font;
   DerivationBox problem;
   LinePanel numbers;
   LinePanel topHat;
   CommentPanel userComment;
   LinePanel commentSpacer;
   LinePanel bottomFeeder;
   DerivationLineEditor focus;
   DerivationLineEditor lastFocus;
   JScrollPane scroller;
   LinePanel problemPanel;
   LinePanel scrollPanel;
   DerivationToolbar buttonPanel;
   DerivationMenuBar menuPanel;
   int[] proofWidths;
   int[] problemWidths;
   Dimension hSpacer;
   Dimension vSpacer;
   Dimension numberSize;
   int indent;
   boolean commandMode;
   boolean queuedMode;
   boolean dontChange;
   Integer chapter;
   boolean serialMode;
   boolean checkDisabled;
   boolean errorMessagesDisabled;
   boolean mixedModeDisabled;
   boolean requestAid;
   boolean complete;
   boolean proofMissing;
   boolean doSubs;
   Vector disabledRules;
   Vector manualRules;
   Vector weakAssRules;
   Vector assumedRules;
   IntervalSet disabledRange;
   IntervalSet manualRange;
   IntervalSet weakAssRange;
   IntervalSet assumedRange;
   static Vector disabledRuleXRefs = null;
   static Vector manualRuleXRefs = null;
   static Vector weakAssRuleXRefs = null;
   static Vector assumedRuleXRefs = null;
   static ProblemSelector noCommand = null;
   static ProblemSelector noQueue = null;
   static ProblemSelector noCheck = null;
   static ProblemSelector noErrMess = null;
   static ProblemSelector monoProbs = null;
   static ProblemSelector noPrint = null;
   static ProblemSelector noPrintCheck = null;
   static ProblemSelector noPrintErr = null;
   static ProblemSelector addToDB = null;
   static ProblemSelector updateDB = null;
   static ProblemSelector chap1 = null;
   static ProblemSelector chap2 = null;
   static ProblemSelector logPrint = null;
   static ProblemSelector logSubmit = null;
   static ProblemSelector needPrint = null;
   static ProblemSelector needSubmit = null;
   static ProblemSelector logShowCmd = null;
   static ProblemSelector noMixedMode = null;
   static boolean officialIE = false;
   static boolean noUser = false;
   static boolean submitExam = false;
   static boolean printIncorrect = false;
   static boolean restating = false;
   Expression[] premises;
   Expression conclusion;
   static final String workFileName = "derwork.txt";
   static final String logFileName = "derdata.txt";
   static final String digestVersKey = "derDigestVers";
   static Class messageClass = DerivationMessage.class;
   static DerivationProblemSet exercises = null;
   static DerivationProblemSet problems = null;
   static Vector instances = new Vector();
   static PrintQueue printQueue = new PrintQueue("Derivation");
   static RuleTable userRules = null;
   static RuleTable derivationRules = null;
   static RuleListDialog ruleQuery = null;
   static String newProblem = null;
   static Vector startups;
   String problemTitle;
   String lastUserProblem;
   int[][] widthInfo;
   int lineColumns;
   int[] lastWidthsSet;
   int phase;
   int errorCount;
   int printIndex;
   long workTime;
   long loadTime;
   boolean aborted;
   boolean doShowLog;
   Vector varNames;
   Hashtable probOptions;
   static int moduleIndex = 0;

   static boolean getExercises() {
      if (!DerivationMessage.loadMessages()) {
         LogicProgram.showFileError("not001", "the derivation messages file");
         return false;
      } else {
         derivationRules = getDerivationRules();
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

   static DerivationProblemSet readExercises() {
      return readExercises(LogicProgram.noCoreProblems, false, false);
   }

   static DerivationProblemSet readExercises(boolean flag, boolean flag1, boolean flag2) {
      DerivationProblemSet derivationproblemset = new DerivationProblemSet();
      if (!flag) {
         ScrambledReader scrambledreader = LogicProgram.openDataFile("derwork.txt", false);
         if (scrambledreader == null) {
            LogicProgram.showFileError("not001", "the core Derivation exercise file");
            return null;
         }

         if (!readProblems(scrambledreader, derivationproblemset, true)) {
            LogicProgram.showFileError("not002", "the core Derivation exercise file");
            return null;
         }
      }

      if (!flag1) {
         ScrambledReader scrambledreader1 = LogicProgram.openLocalFile("derwork.txt", flag2);
         if (scrambledreader1 != null
            && (flag ? !readProblems(scrambledreader1, derivationproblemset, true) : !mergeProblems(scrambledreader1, derivationproblemset, true))) {
            LogicProgram.showFileError("not002", "the local Derivation exercise file");
            return null;
         }
      }

      return derivationproblemset;
   }

   LPDerivation(boolean flag) {
      super(flag);
      this.fontSize = LogicProgram.fontSize;
      this.problem = null;
      this.widthInfo = new int[][]{{2, 0}, {20, 4}, {5, 1}, {10, 2}, {1, 0}};
      this.lineColumns = this.widthInfo.length;
      this.lastWidthsSet = new int[]{0, 0, 0};
      this.probOptions = null;
      this.setLayout(new BorderLayout());
      this.proofWidths = new int[this.lineColumns - 1];
      this.problemWidths = new int[this.lineColumns - 1];
      this.hSpacer = new Dimension(-1, -1);
      this.vSpacer = new Dimension(-1, -1);
      this.numberSize = new Dimension(-1, -1);
      this.add(this.titlePanel, "North");
      this.scroller = new JScrollPane();
      this.add(this.scroller, "Center");
      this.scroller.setViewportView(this.scrollPanel = new LinePanel());
      this.scroller.getVerticalScrollBar().setUnitIncrement(22);
      this.scrollPanel.setLayout(new BoxLayout(this.scrollPanel, 3));
      this.scrollPanel.add(this.problemPanel = new LinePanel());
      this.scrollPanel.add(this.bottomFeeder = new LinePanel());
      this.problemPanel.add(this.topHat = new LinePanel(), "North");
      this.topHat.add(this.userComment = new CommentPanel(true), "Center");
      this.topHat.add(this.commentSpacer = new LinePanel(), "South");
      this.problemPanel.add(this.numbers = new LinePanel(this.numberSize), "West");
      this.problemPanel.add(this.problem = new DerivationBox(this), "Center");
      this.numbers.line = this.problem.showLine;
      this.numbers.setLayout(new DerivationOverlayLayout(this));
      this.problem.showLine.formulaEditor.setEditable(false);
      this.buttonPanel = DerivationToolbar.create(this);
      this.add(this.buttonPanel, "South");
      this.focus = null;
      this.lastFocus = this.focus;
      this.commandMode = true;
      this.queuedMode = true;
      this.chapter = null;
      this.serialMode = false;
      this.checkDisabled = false;
      this.errorMessagesDisabled = false;
      this.mixedModeDisabled = false;
      this.requestAid = false;
      this.proofMissing = false;
      this.doSubs = false;
      this.disabledRules = null;
      this.manualRules = null;
      this.weakAssRules = null;
      this.assumedRules = null;
      this.disabledRange = null;
      this.manualRange = null;
      this.weakAssRange = null;
      this.assumedRange = null;
      this.premises = null;
      this.conclusion = null;
      this.problemTitle = null;
      this.probOptions = null;
      this.phase = 0;
      this.aborted = false;
      this.doShowLog = false;
      this.printIndex = 0;
      this.newProblem();
      this.resetVarNames();
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
         LPDerivation.DerivationStartupTask lpderivation$derivationstartuptask = new LPDerivation.DerivationStartupTask(busyindicator, rectangle, s);
         startups.add(lpderivation$derivationstartuptask);
         if (startups.size() <= 1) {
            if (problems == null) {
               if (!getProblems()) {
                  lpderivation$derivationstartuptask.stopBusyIndicator();
                  startups.remove(lpderivation$derivationstartuptask);
                  return;
               }

               if (problems.mergeExercises()) {
                  saveProblems();
               }

               problems.restateProblems(lpderivation$derivationstartuptask);
            } else {
               lpderivation$derivationstartuptask.continueStartup();
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
      DerivationToolbar derivationtoolbar = DerivationToolbar.create(this);
      Dimension dimension = derivationtoolbar.getPreferredSize();
      if (rectangle.width < dimension.width) {
         rectangle.width = dimension.width;
      }

      return rectangle;
   }

   static void allocateDerModule(LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
      LPDerivation lpderivation = new LPDerivation(false);
      instances.addElement(lpderivation);
      if (logicmodule$modulestartuptask.problemName == null || newProblem == null) {
         lpderivation.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lpderivation.saveProblem();
         }
      }

      if (logicmodule$modulestartuptask.problemName != null) {
         lpderivation.loadProblem(logicmodule$modulestartuptask.problemName);
         logicmodule$modulestartuptask.problemName = null;
      }

      lpderivation.setupFrame(LPInfo.programName + ": Derivation");
      lpderivation.frame.setBounds(lpderivation.fixModuleRect(logicmodule$modulestartuptask.bounds));
      lpderivation.frame.setVisible(true);
      lpderivation.requestFocus();
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
      return s != null && s.toLowerCase().startsWith("deriv") ? "1" : "NULL";
   }

   void setupFrame(String s) {
      this.frame = new ModuleFrame(s);
      this.doSubs = true;
      this.setFontSize(this.fontSize);
      this.setColors(this.colors);
      this.frame.add(this, "Center");
      this.frame.module = this;
      this.setWidths(true);
   }

   @Override
   public boolean shutdown(boolean flag) {
      if (!flag && !DerivationDialogs.confirmSaveChanges(this, null)) {
         return false;
      } else {
         this.newProblem();
         synchronized (moduleClasses[0]) {
            instances.removeElement(this);
            if (instances.isEmpty()) {
               PrintTask.waitForQueue(printQueue);
               problems = null;
               userRules = null;
               derivationRules = null;
               exercises = null;
               newProblem = null;
               startups = null;
               if (ruleQuery != null) {
                  ruleQuery.close();
               }

               resetOptions();
            }

            return true;
         }
      }
   }

   static synchronized LPDerivation openInstance(int i, boolean flag) {
      if (i >= 0 && instances != null && !instances.isEmpty()) {
         int j = instances.size();

         for (int k = 0; k < j; k++) {
            LPDerivation lpderivation = (LPDerivation)instances.elementAt(k);
            if (lpderivation.problemIndex == i) {
               if (flag) {
                  lpderivation.requestFocus();
               }

               return lpderivation;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Override
   public boolean save() {
      return DerivationDialogs.confirmSaveChanges(this, null);
   }

   static void resetOptions() {
      disabledRuleXRefs = new Vector();
      manualRuleXRefs = new Vector();
      weakAssRuleXRefs = new Vector();
      assumedRuleXRefs = new Vector();
      noCommand = null;
      noQueue = null;
      noCheck = null;
      noErrMess = null;
      monoProbs = null;
      noPrint = null;
      noPrintCheck = null;
      noPrintErr = null;
      addToDB = null;
      updateDB = null;
      chap1 = null;
      chap2 = null;
      logPrint = null;
      logSubmit = null;
      needPrint = null;
      needSubmit = null;
      logShowCmd = null;
      officialIE = false;
      noUser = false;
      submitExam = false;
      printIncorrect = false;
      restating = false;
   }

   static boolean checkQuit(Hashtable hashtable, Hashtable hashtable1) {
      derivationRules = getDerivationRules();
      resetOptions();
      readOptions(LogicProgram.openDataFile("options", false));
      readOptions(LogicProgram.openLocalFile("options", false));
      logNeeds();
      if (needPrint != null && !needPrint.isEmpty() || needSubmit != null && !needSubmit.isEmpty()) {
         ProblemRecordEnumeration problemrecordenumeration = new ProblemRecordEnumeration(readWork());
         problemrecordenumeration.retainExisting(readExercises());
         MainMenu.mergeSubmitStatus(hashtable, "derdata.txt", "P", needPrint, problemrecordenumeration);
         problemrecordenumeration.reset();
         MainMenu.mergeSubmitStatus(hashtable1, "derdata.txt", "S", needSubmit, problemrecordenumeration);
      }

      resetOptions();
      return true;
   }

   static Vector getChangedProblems() {
      ProblemRecordEnumeration problemrecordenumeration = new ProblemRecordEnumeration(readWork());
      Hashtable hashtable = LogicProgram.checkSubmitLog("derdata.txt", "S", needSubmit, problemrecordenumeration);
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
      this.setWidths(false);
   }

   @Override
   public Frame getFrame() {
      return this.frame;
   }

   synchronized int setPhase(int i) {
      int j = this.phase;
      this.phase = i;
      return j;
   }

   void setColors(Color[] acolor) {
      this.colors = acolor;
      this.problem.applyColors(acolor);
      if (this.scrollPanel != null) {
         this.scrollPanel.setForeground(acolor[0]);
         this.scrollPanel.setBackground(acolor[1]);
         Graphics graphics = this.scrollPanel.getGraphics();
         if (graphics != null) {
            this.scrollPanel.paintAll(graphics);
         }
      }
   }

   int getFontSize() {
      return this.fontSize;
   }

   void setFontSize(int i) {
      this.fontSize = i;
      this.font = LogicProgram.getFont(i);
      this.setWidths(false);
      this.titlePanel.setFont(this.font);
      this.userComment.setFont(this.font);
      this.problem.setFont(this.font);
      this.numbers.setFont(this.font);
   }

   synchronized void setWidths(boolean flag) {
      if (this.frame != null) {
         this.setWidths(flag, this.fontSize, LogicProgram.getInteriorBounds(this.scroller).width - 15);
      }
   }

   synchronized void setWidths(boolean flag, int i, int j) {
      this.indent = i * 10 / 7;
      this.vSpacer.height = this.indent / 4;
      this.hSpacer.width = this.indent / 4;
      int k = 0;
      int l = 0;
      int i1 = this.getMaxDepth(true);
      int j1 = 0;
      if (i1 + 10 > this.widthInfo[1][0]) {
         j1 = (i1 + 10 - this.widthInfo[1][0] + 5 - 1) / 5 * 5;
         k = j1;
      }

      for (int k1 = 0; k1 < this.lineColumns; k1++) {
         k += this.widthInfo[k1][0];
         l += this.widthInfo[k1][1];
      }

      int i2 = j > k * this.indent ? j - k * this.indent : 0;
      this.numberSize.width = this.widthInfo[0][0] * this.indent + this.widthInfo[0][1] * i2 / l;
      if (flag || this.lastWidthsSet[0] != i || this.lastWidthsSet[1] != j || this.lastWidthsSet[2] != j1) {
         this.lastWidthsSet[0] = i;
         this.lastWidthsSet[1] = j;
         this.lastWidthsSet[2] = j1;
         this.commentSpacer.setLimitHeight(this.vSpacer.height);
         this.bottomFeeder.setLimitHeight(this.indent * 10);

         for (int l1 = 1; l1 < this.lineColumns; l1++) {
            this.proofWidths[l1 - 1] = this.widthInfo[l1][0] * this.indent + this.widthInfo[l1][1] * i2 / l;
         }

         this.proofWidths[0] += j1 * this.indent;
         System.arraycopy(this.proofWidths, 0, this.problemWidths, 0, this.lineColumns - 1);
         this.problemWidths[0] += this.problemWidths[1];
         this.problemWidths[1] = 0;
         this.userComment.setColumnWidths(this.numberSize.width, this.problemWidths[0] + this.problemWidths[2]);
         this.problem.layoutColumns();
      }
   }

   int getMaxDepth(boolean flag) {
      return this.problem == null ? -1 : this.problem.getMaxBoxDepth(flag);
   }

   @Override
   public void requestFocus() {
      if (this.lastFocus != null) {
         this.lastFocus.requestFocus();
      } else if (this.problem != null) {
         this.problem.showLine.formulaEditor.requestFocus();
      }
   }

   void removeWork() {
      while (this.problem.getContentCount() > 1) {
         this.problem.remove((Component)this.problem.getNode(1));
         this.numbers.remove(1);
      }

      while (this.numbers.getComponentCount() > 1) {
         this.numbers.remove(1);
      }

      this.problem.focusEditor(false);
   }

   static String removeWork(TaggedRecord taggedrecord) {
      String s = TaggedRecord.formatField(taggedrecord.getName(), '$');
      s = s + TaggedRecord.formatField(getProblemStatement(taggedrecord), '-') + "`=";
      return s + taggedrecord.formatFields("%u!");
   }

   void newProblem() {
      this.loadProblem(newProblem);
   }

   void reset() {
      if (this.focus != null) {
         this.focus.line.suppressEditorFocusLoss();
      }

      this.problemPanel.remove(this.problem);
      this.problemPanel.remove(this.numbers);
      this.titlePanel.clearFields();
      this.userComment.setComment(null);
      this.problem = null;
      this.lastUserProblem = null;
      this.problemPanel.add(this.numbers = new LinePanel(this.numberSize), "West");
      this.problemPanel.add(this.problem = new DerivationBox(this), "Center");
      this.numbers.line = this.problem.showLine;
      this.numbers.setLayout(new DerivationOverlayLayout(this));
      this.numbers.setFont(this.font);
      this.problem.showLine.formulaEditor.setEditable(false);
      this.setWidths(true);
      this.focus = null;
      this.lastFocus = this.focus;
      this.commandMode = true;
      this.queuedMode = true;
      this.chapter = null;
      this.serialMode = false;
      this.proofMissing = false;
      this.disabledRules = null;
      this.manualRules = null;
      this.disabledRange = null;
      this.manualRange = null;
      this.premises = null;
      this.conclusion = null;
      this.problemIndex = -1;
      this.problemTitle = null;
      this.probOptions = null;
      this.requestFocus();
      this.phase = 0;
      this.errorCount = 0;
      this.workTime = 0L;
      this.loadTime = 0L;
      this.aborted = false;
      this.doShowLog = false;
      this.resetVarNames();
   }

   void resetVarNames() {
      this.varNames = null;
      this.problem.resetVariables();
   }

   String getChangedProblem() {
      String s = this.saveProblem();
      String s1 = this.problemIndex == -1 ? newProblem : problems.getRecordAt(this.problemIndex);
      return TaggedRecord.stripTimestamp(s).equals(TaggedRecord.stripTimestamp(s1)) ? null : s;
   }

   static boolean saveProblems(int i) {
      if (i != -1) {
         ProblemEntry problementry = problems.getEntryAt(i);
         problementry.state = getProblemState(problementry.name);
      }

      return saveProblems();
   }

   static boolean saveProblems() {
      if (!LogicProgram.checkSameUser()) {
         return false;
      } else {
         try {
            writeProblems(problems, "derwork.txt");
            return true;
         } catch (IOException ioexception) {
            LogicProgram.showFileError("not004", "derwork.txt");
            return false;
         }
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
      if (UserSetup.confirmSubmitAll("Derivation")) {
         int[] aint = getExerciseIndices();
         BusyIndicator busyindicator = new BusyIndicator(this);
         Submission submission = ServerConnection.prepareSubmission(busyindicator);
         if (submission != null) {
            if (DerivationDialogs.confirmSaveChanges(this, null)) {
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
         int[] aint = DerivationDialogs.chooseProblemsToSubmit(this);
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
            LPDerivation lpderivation = new LPDerivation(false);
            lpderivation.doSubs = true;
            String s = problems.getRecordAt(aint[j]);
            TaggedRecord taggedrecord = new TaggedRecord(s);
            String s1 = getProblemStatement(taggedrecord);
            submission.problemMd5 = Scrambler.md5Base64(s1 == null ? "" : s1.trim());
            submission.evaluation = DerivationProblemEntry.STATE_CODES[lpderivation.getProblemState(taggedrecord)];
            submission.work = s + lpderivation.saveMessages();
            submission.problemName = taggedrecord.getName();
            submission.module = moduleAbbrs[moduleIndex];
            submission.helpCount = taggedrecord.getErrorCount();
            submission.duration = taggedrecord.getTimestamp();
            boolean flag = LogicProgram.selectorMatches(logSubmit, getExerciseTitle(submission.problemName));
            if (ServerConnection.submit(submission, busyindicator)) {
               vector.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.appendSubmitLog("derdata.txt", "S", s, submission.getLogRecord());
               }
            } else {
               vector1.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.appendSubmitLog("derdata.txt", "F", s);
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
         int[] aint = DerivationDialogs.chooseProblemsToUpload(this);
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

   boolean saveRenamed(String s) {
      if (s == null) {
         return true;
      } else {
         String s1 = this.problemTitle;
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
               String s2 = DerivationDialogs.askProblemName(flag ? this.problemTitle : null);
               if (s2 == null) {
                  return false;
               }

               this.setProblemTitle(s2);
               DerivationProblemEntry derivationproblementry = new DerivationProblemEntry(TaggedRecord.withName(s, this.problemTitle), false);
               this.problemIndex = problems.registerEntry(derivationproblementry, false);
               this.problemIndex = this.problemIndex == -1 ? problems.size() : this.problemIndex + 1;
               problems.insertElementAt(derivationproblementry, this.problemIndex);
            } else {
               s1 = problems.getRecordAt(this.problemIndex);
               problems.replaceProblem(s, this.problemIndex);
            }

            if (saveProblems(this.problemIndex)) {
               if (this.problemTitle != null && this.problemTitle.toUpperCase().startsWith("UR")) {
                  UserRule userrule = (UserRule)userRules.getRule(this.problemTitle);
                  if (userrule == null) {
                     userrule = new UserRule(this.problemTitle);
                     if (userrule.error == null) {
                        userRules.addRule(userrule);
                     }
                  } else {
                     userrule.loadFromProblem(this.problemTitle);
                     if (userrule.error != null) {
                        userRules.removeRule(userrule);
                     }
                  }
               }

               return true;
            } else {
               if (s1 == null) {
                  problems.removeProblem(this.problemIndex);
                  this.problemIndex = -1;
               } else {
                  problems.replaceProblem(s1, this.problemIndex);
               }

               return false;
            }
         }
      }
   }

   String saveProblem() {
      if (this.focus != null && this.focus == this.focus.line.annotationEditor) {
         this.focus.line.resolveRelativeReferences();
      }

      String s = this.saveTitle() + this.problem.encodeWork();
      if (this.errorCount != 0) {
         s = s + this.errorCount + "`e";
      }

      if (this.updateWorkTime() != 0L) {
         s = s + this.workTime + "`t";
      }

      return TaggedRecord.toLine(s);
   }

   String saveMessages() {
      return this.problem.encodeMessages();
   }

   String saveTitle() {
      return TaggedRecord.formatField(this.problemTitle, '$');
   }

   static String trimTitle(String s) {
      if (s == null) {
         return null;
      } else {
         return isExercise(s) ? LogicProgram.stripNamePrefix(s) : s.trim();
      }
   }

   void loadProblem(String s) {
      this.loadProblem(new TaggedRecord(s));
   }

   void loadProblem(TaggedRecord taggedrecord) {
      this.reset();
      int i = 0;
      DerivationBox derivationbox = this.problem;
      DerivationLine derivationline = null;
      this.loadExerciseInfo(taggedrecord);
      int j = taggedrecord.getFieldCount();

      for (int k = 0; k < j; k++) {
         char c0 = taggedrecord.tagAt(k);
         String s = taggedrecord.valueAt(k);
         if ((c0 == '-' || c0 == '+') && derivationbox != null) {
            if (derivationline == null) {
               this.titlePanel.setStatement(LogicProgram.translateSymbols(s, maggie, kaplan));
            } else {
               i++;
               derivationbox = derivationbox.insertBox(-1);
               if (c0 == '+') {
                  derivationbox.setExpanded(false);
               }
            }

            derivationline = derivationbox.showLine;
            derivationline.setLineNumber(i);
            derivationline.setFormulaText(s);
            derivationline.parseFormula();
         } else if (c0 == '<' && derivationbox != null) {
            i++;
            derivationline = derivationbox.insertLine(-1);
            derivationline.setLineNumber(i);
            derivationline.setFormulaText(s);
            derivationline.parseFormula();
         } else if (c0 == '>' && derivationbox != null) {
            derivationline.setAnnotationText(LogicProgram.translateSymbols(s, rob, kaplan));
            derivationline.parseReferences(false);
         } else if (c0 == '#' && derivationbox != null) {
            i++;
            derivationline = derivationbox.insertLine(-1);
            derivationline.setLineNumber(i);
            derivationline.setAnnotationText(LogicProgram.translateSymbols(s, rob, kaplan));
            derivationline.parseReferences(false);
            derivationline.boxAndCancel();
            derivationbox = derivationbox.parentBox;
         } else if (c0 == '=' && derivationbox != null) {
            derivationbox = derivationbox.parentBox;
         } else if (c0 == ':' && derivationbox != null) {
            if (derivationline.justifications == null) {
               derivationline.justifications = new Vector();
            }

            derivationline.justifications.addElement(Justification.decode(s, this));
         } else if (c0 == '$') {
            this.setProblemTitle(s);
         } else if (c0 == 's') {
            if (derivationline.commandLog != null) {
               derivationline.commandLog.setText(LogicProgram.translateSymbols(s, rob, kaplan));
            }
         } else if (c0 == 'e') {
            Integer integer = LogicProgram.parseInteger(s);
            this.errorCount = integer == null ? 0 : integer;
         } else if (c0 == 't') {
            Long olong = LogicProgram.parseLong(s);
            this.workTime = olong == null ? 0L : olong;
         } else if (c0 == 'm' && !restating) {
            int i1 = s.indexOf(58);
            if (i1 != -1) {
               Integer integer1 = LogicProgram.parseInteger(s.substring(0, i1).trim());
               if (integer1 != null) {
                  int l = integer1;
                  if (l >= 0) {
                     Object object = l == 0 ? this.problem : this.problem.findLine(l);
                     if (object != null) {
                        ((DerivationNode)object).setMessageText(s.substring(i1 + 1), true);
                     }
                  }
               }
            }
         }
      }

      this.updateWorkTime();
      this.setWidths(true);
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
      ArgumentParser argumentparser = new ArgumentParser(s, true);
      String s1 = argumentparser.getUnparsedText();
      if (s1 != null) {
         Hashtable hashtable = Message.params("expression", LogicProgram.translateSymbols(s1, maggie, kaplan));
         MessageDialog.showMessage(DerivationMessage.get("dererr082"), hashtable, null, null);
      } else if (!argumentparser.conclusionOnly && argumentparser.getErrorCode() == 0) {
         this.loadProblem(TaggedRecord.toLine(TaggedRecord.formatField(ArgumentParser.normalizeDots(s), '-') + TaggedRecord.formatField("", '=')));
      } else {
         MessageDialog.showMessage(DerivationMessage.get("dererr083"), null, null, null);
      }
   }

   void setProblemTitle(String s) {
      if (s != null && !(s = s.trim()).equals("")) {
         this.problemTitle = s;
         s = trimTitle(this.problemTitle);
         this.problem.showLine.showLabel.setText(s + ": ");
         this.titlePanel.setTitleLabel(s);
      } else {
         this.problemTitle = null;
         this.problem.showLine.showLabel.setText("Problem: ");
         this.titlePanel.setTitleLabel(null);
      }
   }

   static RuleTable getDerivationRules() {
      RuleTable ruletable = new RuleTable(null);
      Rule rule = new Rule("CD");
      SchematicRule schematicrule;
      rule.addComponent(schematicrule = new SchematicRule("CD/C"));
      ruletable.addRule(schematicrule);
      rule.addComponent(schematicrule = new SchematicRule("CD/D"));
      ruletable.addRule(schematicrule);
      rule.addComponent(schematicrule = new SchematicRule("CD/I"));
      ruletable.addRule(schematicrule);
      ruletable.addRule(rule);
      rule = new Rule("DD");
      rule.addComponent(schematicrule = new SchematicRule("DD/C"));
      ruletable.addRule(schematicrule);
      rule.addComponent(schematicrule = new SchematicRule("DD/D"));
      ruletable.addRule(schematicrule);
      rule.addComponent(schematicrule = new SchematicRule("DD/I"));
      ruletable.addRule(schematicrule);
      ruletable.addRule(rule);
      rule = new Rule("ID");
      rule.addComponent(schematicrule = new SchematicRule("ID/C"));
      ruletable.addRule(schematicrule);
      rule.addComponent(schematicrule = new SchematicRule("ID/D"));
      ruletable.addRule(schematicrule);
      rule.addComponent(schematicrule = new SchematicRule("ID/I"));
      ruletable.addRule(schematicrule);
      ruletable.addRule(rule);
      ruletable.addRule(new SchematicRule("UD"));
      ruletable.addRule(new SchematicRule("IE"));
      ruletable.addRule(new SchematicRule("CIE"));
      rule = new Rule("BD");
      rule.addComponent(schematicrule = new SchematicRule("BD/B"));
      ruletable.addRule(schematicrule);
      ruletable.addRule(rule);
      return ruletable;
   }

   static void listRules(String s, Vector vector, IntervalSet intervalset, boolean flag) {
      String s2 = s.trim();

      while (!s2.equals("")) {
         int i = s2.indexOf(".");
         String s1;
         if (i == -1) {
            s1 = s2;
            s2 = "";
         } else {
            s1 = s2.substring(0, i).trim();
            s2 = s2.substring(i + 1).trim();
         }

         if ("~{".indexOf(s1.charAt(0)) != -1) {
            intervalset.union(new IntervalSet(s1));
         } else {
            Integer integer;
            if ((integer = SchematicRule.parseTheoremNumber(s1)) != null) {
               intervalset.union(IntervalSet.singleton(integer));
            } else {
               Rule rule;
               if ((rule = getRule(s1)) != null) {
                  if (flag) {
                     SchematicRule[] aschematicrule = rule.getAllForms();
                     int j = aschematicrule.length;

                     for (int k = 0; k < j; k++) {
                        if (!vector.contains(aschematicrule[k].name)) {
                           vector.addElement(aschematicrule[k].name);
                        }
                     }
                  } else if (!vector.contains(rule.name)) {
                     vector.addElement(rule.name);
                  }
               } else if (s1.equalsIgnoreCase("UR")) {
                  if (!vector.contains("UR")) {
                     vector.addElement("UR");
                  }
               } else {
                  System.out.println("unknown rule: " + s1);
               }
            }
         }
      }
   }

   ErrorRef checkDerivationRule(String s, boolean flag) {
      Rule rule = derivationRules == null ? null : derivationRules.getRule(s);
      if (rule == null) {
         return null;
      } else if (this.hasProperty(rule, "disabled")) {
         return new ErrorRef("dererr041");
      } else if (flag && this.hasProperty(rule, "manual")) {
         return new ErrorRef("dererr040");
      } else {
         return rule.isProven(this) ? null : new ErrorRef("dererr016");
      }
   }

   static Rule getRule(String s) {
      Rule rule = LogicProgram.getRule(s);
      if (rule == null && derivationRules != null) {
         rule = derivationRules.getRule(s);
      }

      if (rule == null && userRules != null) {
         rule = userRules.getRule(s);
      }

      return rule;
   }

   Vector enabledRules(RuleTable ruletable) {
      Vector vector = new Vector();
      Vector vector1 = ruletable.ruleNames;
      int i = vector1.size();

      for (int j = 0; j < i; j++) {
         String s = (String)vector1.elementAt(j);
         Rule rule = ruletable.getRule(s);
         if (!rule.testProperty(this, "disabled", false) && rule.isAnyFormProven(this)) {
            vector.addElement(s);
         }
      }

      return vector;
   }

   IntervalSet enabledTheorems(TheoremTable theoremtable) {
      IntervalSet intervalset = new IntervalSet();
      Enumeration enumeration = theoremtable.theoremNumbers.elements();

      while (enumeration.hasMoreElements()) {
         Integer integer = (Integer)enumeration.nextElement();
         Theorem theorem = theoremtable.getTheorem(integer);
         if (!theorem.testProperty(this, "disabled", false) && theorem.isAnyFormProven(this)) {
            intervalset.union(IntervalSet.singleton(theorem.number));
         }
      }

      return intervalset;
   }

   @Override
   public boolean hasProperty(Rule rule, String s) {
      String s1 = rule instanceof UserRule ? "UR" : rule.name;
      if (s.equals("disabled")) {
         return this.disabledRules != null && this.disabledRules.contains(s1) || this.isWeaklyDisabled(rule);
      } else if (s.equals("manual")) {
         return this.manualRules != null && this.manualRules.contains(s1);
      } else if (s.equals("manualOrDisabled")) {
         return this.manualRules != null && this.manualRules.contains(s1) || this.hasProperty(rule, "disabled");
      } else if (s.equals("weakAss")) {
         return this.weakAssRules != null && this.weakAssRules.contains(s1);
      } else if (!s.equals("assumed")) {
         throw new IllegalArgumentException("unknown property: " + s);
      } else {
         return this.assumedRules != null && this.assumedRules.contains(s1);
      }
   }

   @Override
   public boolean hasProperty(Integer integer, String s) {
      int i = integer;
      if (s.equals("disabled")) {
         return this.disabledRange != null && this.disabledRange.contains(i) || this.isWeaklyDisabled(integer);
      } else if (s.equals("manual")) {
         return this.manualRange != null && this.manualRange.contains(i);
      } else if (s.equals("manualOrDisabled")) {
         return this.manualRange != null && this.manualRange.contains(i) || this.hasProperty(integer, "disabled");
      } else if (s.equals("weakAss")) {
         return this.weakAssRange != null && this.weakAssRange.contains(i);
      } else if (!s.equals("assumed")) {
         throw new IllegalArgumentException("unknown property: " + s);
      } else {
         return this.assumedRange != null && this.assumedRange.contains(i);
      }
   }

   @Override
   public Vector getProofs(SchematicRule schematicrule) {
      if (schematicrule instanceof UserRule) {
         return ((UserRule)schematicrule).getSourceProblems();
      } else {
         return exercises.ruleProofIndex != null && !schematicrule.testProperty(this, "assumed", false)
            ? (Vector)exercises.ruleProofIndex.get(schematicrule.name)
            : null;
      }
   }

   @Override
   public Vector getProofs(Integer integer) {
      return exercises.ruleProofIndex != null && !this.hasProperty(integer, "assumed") ? (Vector)exercises.ruleProofIndex.get(integer) : null;
   }

   boolean isWeaklyDisabled(Rule rule) {
      if (this.problemTitle != null && this.hasProperty(rule, "weakAss")) {
         Vector vector = rule instanceof SchematicRule ? this.getProofs((SchematicRule)rule) : null;
         return vector != null && vector.contains(this.problemTitle);
      } else {
         return false;
      }
   }

   boolean isWeaklyDisabled(Integer integer) {
      Vector vector;
      return this.problemTitle != null && this.hasProperty(integer, "weakAss") && (vector = this.getProofs(integer)) != null
         ? vector.contains(this.problemTitle)
         : false;
   }

   @Override
   public String excludedProof() {
      return this.problemTitle;
   }

   @Override
   public boolean checkProof(String s) {
      ProblemEntry problementry = problems.getEntry(s);
      return problementry != null && problementry.state == 2;
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         DerivationRuleLister derivationrulelister = new DerivationRuleLister();
         TaggedRecord taggedrecord = new TaggedRecord(reader, true);
         String s = "";

         while (taggedrecord.readNext()) {
            String s1 = taggedrecord.getName();
            if (s1 != null && s1.trim().equalsIgnoreCase("derivation")) {
               int[] aint = taggedrecord.indexesOfAnyTag("dDmMaA+?");
               int i = aint.length;

               for (int j = 0; j < i; j++) {
                  char c0 = taggedrecord.tagAt(aint[j]);
                  String s2 = taggedrecord.valueAt(aint[j]);
                  if (c0 == 'd') {
                     disabledRuleXRefs.addElement(new RuleCrossReference(s2, derivationrulelister).withPrefix(s));
                  } else if (c0 == 'D') {
                     disabledRuleXRefs.addElement(new RuleCrossReference(s2, derivationrulelister, true).withPrefix(s));
                  } else if (c0 == 'm') {
                     manualRuleXRefs.addElement(new RuleCrossReference(s2, derivationrulelister).withPrefix(s));
                  } else if (c0 == 'M') {
                     manualRuleXRefs.addElement(new RuleCrossReference(s2, derivationrulelister, true).withPrefix(s));
                  } else if (c0 == 'a') {
                     weakAssRuleXRefs.addElement(new RuleCrossReference(s2, derivationrulelister, true).withPrefix(s));
                  } else if (c0 == 'A') {
                     assumedRuleXRefs.addElement(new RuleCrossReference(s2, derivationrulelister, true).withPrefix(s));
                  } else if (c0 == '+') {
                     if (s2.equalsIgnoreCase("officialIE")) {
                        officialIE = true;
                     } else if (s2.equalsIgnoreCase("noUser")) {
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
                        if (s3.equalsIgnoreCase("noCommand")) {
                           if (noCommand == null) {
                              noCommand = new ProblemSelector();
                           }

                           noCommand.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noQueue")) {
                           if (noQueue == null) {
                              noQueue = new ProblemSelector();
                           }

                           noQueue.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("chap1")) {
                           if (chap1 == null) {
                              chap1 = new ProblemSelector();
                           }

                           chap1.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("chap2")) {
                           if (chap2 == null) {
                              chap2 = new ProblemSelector();
                           }

                           chap2.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
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
                        } else if (s3.equalsIgnoreCase("noCheck")) {
                           if (noCheck == null) {
                              noCheck = new ProblemSelector();
                           }

                           noCheck.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noErrMess")) {
                           if (noErrMess == null) {
                              noErrMess = new ProblemSelector();
                           }

                           noErrMess.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new ProblemSelector();
                           }

                           monoProbs.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
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
                        } else if (s3.equalsIgnoreCase("logShowCmd")) {
                           if (logShowCmd == null) {
                              logShowCmd = new ProblemSelector();
                           }

                           logShowCmd.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noMixedMode")) {
                           if (noMixedMode == null) {
                              noMixedMode = new ProblemSelector();
                           }

                           noMixedMode.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
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
      this.commandMode = !LogicProgram.selectorMatches(noCommand, s);
      this.queuedMode = !LogicProgram.selectorMatches(noQueue, s);
      this.checkDisabled = LogicProgram.selectorMatches(this.forPrint ? noPrintCheck : noCheck, s);
      this.errorMessagesDisabled = LogicProgram.selectorMatches(this.forPrint ? noPrintErr : noErrMess, s);
      this.mixedModeDisabled = LogicProgram.selectorMatches(noMixedMode, s);
      this.doShowLog = LogicProgram.selectorMatches(logShowCmd, s);
      if (LogicProgram.selectorMatches(chap1, s)) {
         this.chapter = new Integer(1);
      } else if (LogicProgram.selectorMatches(chap2, s)) {
         this.chapter = new Integer(2);
      } else {
         this.chapter = null;
      }

      this.disabledRules = new Vector();
      this.manualRules = new Vector();
      this.weakAssRules = new Vector();
      this.assumedRules = new Vector();
      this.disabledRange = new IntervalSet();
      this.manualRange = new IntervalSet();
      this.weakAssRange = new IntervalSet();
      this.assumedRange = new IntervalSet();
      int j = disabledRuleXRefs.size();

      for (int i = 0; i < j; i++) {
         ((RuleCrossReference)disabledRuleXRefs.elementAt(i)).applyTo(s, this.disabledRules, this.disabledRange);
      }

      j = manualRuleXRefs.size();

      for (int k = 0; k < j; k++) {
         ((RuleCrossReference)manualRuleXRefs.elementAt(k)).applyTo(s, this.manualRules, this.manualRange);
      }

      j = weakAssRuleXRefs.size();

      for (int l = 0; l < j; l++) {
         ((RuleCrossReference)weakAssRuleXRefs.elementAt(l)).applyTo(s, this.weakAssRules, this.weakAssRange);
      }

      j = assumedRuleXRefs.size();

      for (int i1 = 0; i1 < j; i1++) {
         ((RuleCrossReference)assumedRuleXRefs.elementAt(i1)).applyTo(s, this.assumedRules, this.assumedRange);
      }
   }

   static int getProblemState(String s) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      return !hasWork(taggedrecord) ? 0 : new LPDerivation(false).getProblemState(taggedrecord);
   }

   @Override
   int getProblemState(TaggedRecord taggedrecord) {
      if (!hasWork(taggedrecord)) {
         return 0;
      } else {
         this.loadProblem(taggedrecord);
         if (this.checkProblem()) {
            return 2;
         } else {
            return this.proofMissing ? 3 : 1;
         }
      }
   }

   static String getProblemStatement(String s) {
      return getProblemStatement(new TaggedRecord(s));
   }

   static String getProblemStatement(TaggedRecord taggedrecord) {
      return taggedrecord.valueAt(taggedrecord.indexOfAnyTag("-+"));
   }

   static int countProblemLines(String s) {
      return countProblemLines(new TaggedRecord(s));
   }

   static int countProblemLines(TaggedRecord taggedrecord) {
      int i = taggedrecord.getFieldCount();
      int j = 0;
      int k = 0;

      for (int l = 0; l < i; l++) {
         char c0 = taggedrecord.tagAt(l);
         if (c0 == '-' || c0 == '+') {
            j++;
            k++;
         } else if (c0 == '<') {
            j++;
         } else if (c0 == '#') {
            j++;
            if (--k == 0) {
               break;
            }
         } else if (c0 == '=') {
            if (--k == 0) {
               break;
            }
         }
      }

      return j;
   }

   static String getWork(TaggedRecord taggedrecord) {
      return taggedrecord.formatFields("-+<#=");
   }

   static String getProblemRuleProven(String s) {
      return getProblemRuleProven(new TaggedRecord(s));
   }

   static String getProblemRuleProven(TaggedRecord taggedrecord) {
      return taggedrecord.valueAt(taggedrecord.indexOfTag('p'));
   }

   boolean checkProblem() {
      this.complete = true;
      this.aborted = false;
      this.resetVarNames();
      this.serialMode = true;
      this.proofMissing = false;
      if (this.focus != null && this.focus == this.focus.line.annotationEditor) {
         this.focus.line.resolveRelativeReferences();
      }

      if (this.problem.checkSyntax() & this.problem.verify()) {
         this.problem.showMessage("derinf005", 4);
         this.titlePanel.setStatus("Correct");
         this.serialMode = false;
         return true;
      } else {
         this.problem.showMessage(this.aborted ? "dererr056" : (this.complete ? "dererr057" : "dererr058"), 4);
         this.titlePanel.setStatus(this.aborted ? "" : (this.complete ? "Incorrect" : "Incomplete"));
         this.serialMode = false;
         return false;
      }
   }

   void abort(boolean flag) {
      this.aborted = flag;
   }

   boolean aborted() {
      return this.aborted;
   }

   static synchronized boolean getProblems() {
      if (problems != null) {
         return true;
      } else {
         DerivationProblemSet derivationproblemset = readWork();
         if (derivationproblemset == null) {
            return false;
         } else {
            if (derivationproblemset.readFromPlainFile && !derivationproblemset.computeDigest(LogicProgram.user).equals(derivationproblemset.storedDigest)) {
               System.out.println("Could not digest file: derwork.txt");
               if (!UserSetup.hasAccess("indigestion", "instructor")) {
                  LogicProgram.showFileError("not003", "derwork.txt");
                  return false;
               }
            }

            problems = derivationproblemset;
            derivationproblemset.rebuildUserRules();
            DerivationProblemEntry.workProblemNames = ProblemEntry.findExtraProblems("derwork.txt", problems);
            ProblemEntry.markExtraProblems(exercises, DerivationProblemEntry.workProblemNames);
            if (DataFiles.hasLegacyWork(LogicProgram.workDir, "derwork.txt")) {
               saveProblems(); // work saved in the older format: save it in the readable one
            }

            return true;
         }
      }
   }

   static DerivationProblemSet readWork() {
      if (!LogicProgram.checkSameUser()) {
         return null;
      } else {
         DerivationProblemSet derivationproblemset = new DerivationProblemSet();
         ScrambledReader scrambledreader = LogicProgram.openDataFile("derwork.txt", true);
         if (!LogicProgram.noCoreProblems || scrambledreader instanceof PlainRecordReader) {
            if (scrambledreader == null) {
               LogicProgram.showFileError("not001", "derwork.txt");
               return null;
            }

            if (scrambledreader instanceof PlainRecordReader) {
               derivationproblemset.readFromPlainFile = true;
            }

            if (!readProblems(scrambledreader, derivationproblemset, false)) {
               LogicProgram.showFileError("not002", "derwork.txt");
               return null;
            }
         }

         if (!(scrambledreader instanceof PlainRecordReader)) {
            scrambledreader = LogicProgram.openLocalFile("derwork.txt", false);
            if (scrambledreader != null && !mergeProblems(scrambledreader, derivationproblemset, false)) {
               LogicProgram.showFileError("not002", "derwork.txt");
               return null;
            }
         }

         return derivationproblemset;
      }
   }

   static boolean hasWork(TaggedRecord taggedrecord) {
      return countProblemLines(taggedrecord) > 1;
   }

   static boolean readProblems(Reader reader, DerivationProblemSet derivationproblemset, boolean flag) {
      return readProblems(reader, derivationproblemset, flag, false);
   }

   static boolean mergeProblems(Reader reader, DerivationProblemSet derivationproblemset, boolean flag) {
      return readProblems(reader, derivationproblemset, flag, true);
   }

   static boolean readProblems(Reader reader, DerivationProblemSet derivationproblemset, boolean flag, boolean flag1) {
      if (flag && derivationproblemset.ruleProofIndex == null) {
         derivationproblemset.ruleProofIndex = new Hashtable();
      }

      return LogicModule.readProblems(reader, derivationproblemset, flag, flag1);
   }

   void setupPrintProblem(Dimension dimension) {
      this.setWidths(true, this.fontSize, dimension.width);
      this.setColors(this.colors);
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
         jpanel.setLayout(new FixedColumnLineLayout(null, 1, new int[]{dimension.width}));
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
         String s1 = getExerciseTitle(s);
         if (!printIncorrect || k == 1 || k == 3) {
            boolean flag = LogicProgram.selectorMatches(noPrintCheck, s1);
            String s2 = getProblemStatement(taggedrecord);
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new FixedColumnLineLayout(null, 2, new int[]{20, dimension.width - 20}));
            jpanel.add(new WrappedTextPanel(flag ? " " : DerivationProblemEntry.STATE_CODES[k]));
            LogicTextArea logictextarea;
            jpanel.add(logictextarea = new LogicTextArea(LogicProgram.expandEscapes("\\l" + s + ": " + s2)));
            logictextarea.setLineWrap(true);
            logictextarea.setWrapStyleWord(true);
            logictextarea.setBackground(LogicProgram.printColors[1]);
            vector.add(jpanel);
         }

         if (LogicProgram.selectorMatches(logPrint, s1)) {
            LogicProgram.appendSubmitLog("derdata.txt", "R", problementry.name);
         }
      }

      return vector;
   }

   static Vector getPrintProblems(int[] aint, Dimension dimension) {
      Vector vector = new Vector(aint.length);

      for (int i = 0; i < aint.length; i++) {
         ProblemEntry problementry = problems.getEntryAt(aint[i]);
         String s = getExerciseTitle(TaggedRecord.nameOf(problementry.name));
         if (LogicProgram.selectorMatches(logPrint, s)) {
            LogicProgram.appendSubmitLog("derdata.txt", "P", problementry.name);
         }

         if (!printIncorrect || problementry.state == 1 || problementry.state == 3) {
            LPDerivation lpderivation = new LPDerivation(true);
            lpderivation.setupFrame("Logic Program: Print");
            lpderivation.loadProblem(problementry.name);
            lpderivation.problem.expandAll();
            lpderivation.checkProblem();
            lpderivation.setupPrintProblem(dimension);
            vector.add(lpderivation.problemPanel);
            lpderivation.frame.dispose();
         }
      }

      return vector;
   }

   void parseProblem() {
      ArgumentParser argumentparser = new ArgumentParser(this.problem.getFormulaText(true), true);
      this.conclusion = argumentparser.conclusion;
      this.premises = argumentparser.premises;
      String s = argumentparser.getUnparsedText();
      this.problem.clearMessage();
      if (s != null) {
         this.problem.showMessage("dererr059", Message.params("parser error", s));
         this.problem.showLine.syntaxOk = false;
      } else {
         this.problem.showLine.syntaxOk = true;
      }
   }

   boolean isPremise(Expression expression) {
      int i = this.premises.length;
      if (expression == null) {
         return false;
      } else {
         for (int j = 0; j < i; j++) {
            if (expression.isIdentical(this.premises[j])) {
               return true;
            }
         }

         return false;
      }
   }

   Expression[] getPremises() {
      return this.premises;
   }

   boolean isConclusion(Expression expression) {
      return expression == null ? false : expression.isIdentical(this.conclusion);
   }

   static class DerivationStartupTask extends LogicModule.ModuleStartupTask {
      DerivationStartupTask(BusyIndicator busyindicator, Rectangle rectangle, String s) {
         super(busyindicator, rectangle, s);
      }

      @Override
      public void run() {
         LPDerivation.allocateDerModule(this);
      }

      @Override
      public void continueStartup() {
         LPDerivation.continueStartup();
      }
   }
}
