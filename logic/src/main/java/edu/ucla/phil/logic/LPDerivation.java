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

class LPDerivation extends LogicModule implements C_w_E, DerivationConstants {
   int fontSize;
   Font font;
   DerivationBox problem;
   C_M numbers;
   C_M topHat;
   C_l_A userComment;
   C_M commentSpacer;
   C_M bottomFeeder;
   C_l_E focus;
   C_l_E lastFocus;
   JScrollPane scroller;
   C_M problemPanel;
   C_M scrollPanel;
   C_PE buttonPanel;
   C_R menuPanel;
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
   C_n_F disabledRange;
   C_n_F manualRange;
   C_n_F weakAssRange;
   C_n_F assumedRange;
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
   static Class messageClass = C_n_.class;
   static C_MD exercises = null;
   static C_MD problems = null;
   static Vector instances = new Vector();
   static C_c_C printQueue = new C_c_C("Derivation");
   static RuleTable userRules = null;
   static RuleTable derivationRules = null;
   static C_d_F ruleQuery = null;
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
      if (!C_n_.loadMessages()) {
         LogicProgram.m971("not001", "the derivation messages file");
         return false;
      } else {
         derivationRules = getDerivationRules();
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

   static C_MD readExercises() {
      return readExercises(LogicProgram.f584, false, false);
   }

   static C_MD readExercises(boolean flag, boolean flag1, boolean flag2) {
      C_MD c_md = new C_MD();
      if (!flag) {
         ScrambledReader scrambledreader = LogicProgram.openDataFile("derwork.txt", false);
         if (scrambledreader == null) {
            LogicProgram.m971("not001", "the core Derivation exercise file");
            return null;
         }

         if (!readProblems(scrambledreader, c_md, true)) {
            LogicProgram.m971("not002", "the core Derivation exercise file");
            return null;
         }
      }

      if (!flag1) {
         ScrambledReader scrambledreader1 = LogicProgram.m1065("derwork.txt", flag2);
         if (scrambledreader1 != null && (flag ? !readProblems(scrambledreader1, c_md, true) : !mergeProblems(scrambledreader1, c_md, true))) {
            LogicProgram.m971("not002", "the local Derivation exercise file");
            return null;
         }
      }

      return c_md;
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
      this.scroller.setViewportView(this.scrollPanel = new C_M());
      this.scroller.getVerticalScrollBar().setUnitIncrement(22);
      this.scrollPanel.setLayout(new BoxLayout(this.scrollPanel, 3));
      this.scrollPanel.add(this.problemPanel = new C_M());
      this.scrollPanel.add(this.bottomFeeder = new C_M());
      this.problemPanel.add(this.topHat = new C_M(), "North");
      this.topHat.add(this.userComment = new C_l_A(true), "Center");
      this.topHat.add(this.commentSpacer = new C_M(), "South");
      this.problemPanel.add(this.numbers = new C_M(this.numberSize), "West");
      this.problemPanel.add(this.problem = new DerivationBox(this), "Center");
      this.numbers.f608 = this.problem.f917;
      this.numbers.setLayout(new C_ZF(this));
      this.problem.f917.f323.setEditable(false);
      this.buttonPanel = C_PE.m1180(this);
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
         LPDerivation.C__A lpderivation$c__a = new LPDerivation.C__A(busyindicator, rectangle, s);
         startups.add(lpderivation$c__a);
         if (startups.size() <= 1) {
            if (problems == null) {
               if (!getProblems()) {
                  lpderivation$c__a.m1310();
                  startups.remove(lpderivation$c__a);
                  return;
               }

               if (problems.m1773()) {
                  saveProblems();
               }

               problems.m1099(lpderivation$c__a);
            } else {
               lpderivation$c__a.m959();
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
      C_PE c_pe = C_PE.m1180(this);
      Dimension dimension = c_pe.getPreferredSize();
      if (rectangle.width < dimension.width) {
         rectangle.width = dimension.width;
      }

      return rectangle;
   }

   static void allocateDerModule(LogicModule.C__A logicmodule$c__a) {
      LPDerivation lpderivation = new LPDerivation(false);
      instances.addElement(lpderivation);
      if (logicmodule$c__a.f784 == null || newProblem == null) {
         lpderivation.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lpderivation.saveProblem();
         }
      }

      if (logicmodule$c__a.f784 != null) {
         lpderivation.loadProblem(logicmodule$c__a.f784);
         logicmodule$c__a.f784 = null;
      }

      lpderivation.setupFrame(LPInfo.programName + ": Derivation");
      lpderivation.frame.setBounds(lpderivation.fixModuleRect(logicmodule$c__a.f783));
      lpderivation.frame.setVisible(true);
      lpderivation.requestFocus();
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
      if (!flag && !C_KB.m766(this, null)) {
         return false;
      } else {
         this.newProblem();
         synchronized (moduleClasses[0]) {
            instances.removeElement(this);
            if (instances.isEmpty()) {
               C_l_B.m1924(printQueue);
               problems = null;
               userRules = null;
               derivationRules = null;
               exercises = null;
               newProblem = null;
               startups = null;
               if (ruleQuery != null) {
                  ruleQuery.m1325();
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
      return C_KB.m766(this, null);
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
      readOptions(LogicProgram.m1065("options", false));
      logNeeds();
      if (needPrint != null && !needPrint.m402() || needSubmit != null && !needSubmit.m402()) {
         C_a_A c_a_a = new C_a_A(readWork());
         c_a_a.m1634(readExercises());
         MainMenu.m2215(hashtable, "derdata.txt", "P", needPrint, c_a_a);
         c_a_a.m1635();
         MainMenu.m2215(hashtable1, "derdata.txt", "S", needSubmit, c_a_a);
      }

      resetOptions();
      return true;
   }

   static Vector getChangedProblems() {
      C_a_A c_a_a = new C_a_A(readWork());
      Hashtable hashtable = LogicProgram.m1084("derdata.txt", "S", needSubmit, c_a_a);
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
      this.problem.m35(acolor);
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
         this.setWidths(flag, this.fontSize, LogicProgram.m1038(this.scroller).width - 15);
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
         this.commentSpacer.m935(this.vSpacer.height);
         this.bottomFeeder.m935(this.indent * 10);

         for (int l1 = 1; l1 < this.lineColumns; l1++) {
            this.proofWidths[l1 - 1] = this.widthInfo[l1][0] * this.indent + this.widthInfo[l1][1] * i2 / l;
         }

         this.proofWidths[0] = this.proofWidths[0] + j1 * this.indent;
         System.arraycopy(this.proofWidths, 0, this.problemWidths, 0, this.lineColumns - 1);
         this.problemWidths[0] = this.problemWidths[0] + this.problemWidths[1];
         this.problemWidths[1] = 0;
         this.userComment.m1920(this.numberSize.width, this.problemWidths[0] + this.problemWidths[2]);
         this.problem.m34();
      }
   }

   int getMaxDepth(boolean flag) {
      return this.problem == null ? -1 : this.problem.m36(flag);
   }

   @Override
   public void requestFocus() {
      if (this.lastFocus != null) {
         this.lastFocus.requestFocus();
      } else if (this.problem != null) {
         this.problem.f917.f323.requestFocus();
      }
   }

   void removeWork() {
      while (this.problem.m1555() > 1) {
         this.problem.remove((Component)this.problem.m1560(1));
         this.numbers.remove(1);
      }

      while (this.numbers.getComponentCount() > 1) {
         this.numbers.remove(1);
      }

      this.problem.m22(false);
   }

   static String removeWork(TaggedRecord taggedrecord) {
      String s = TaggedRecord.m1508(taggedrecord.getName(), '$');
      s = s + TaggedRecord.m1508(getProblemStatement(taggedrecord), '-') + "`=";
      return s + taggedrecord.m1484("%u!");
   }

   void newProblem() {
      this.loadProblem(newProblem);
   }

   void reset() {
      if (this.focus != null) {
         this.focus.f1258.m568();
      }

      this.problemPanel.remove(this.problem);
      this.problemPanel.remove(this.numbers);
      this.titlePanel.m1829();
      this.userComment.m1916(null);
      this.problem = null;
      this.lastUserProblem = null;
      this.problemPanel.add(this.numbers = new C_M(this.numberSize), "West");
      this.problemPanel.add(this.problem = new DerivationBox(this), "Center");
      this.numbers.f608 = this.problem.f917;
      this.numbers.setLayout(new C_ZF(this));
      this.numbers.setFont(this.font);
      this.problem.f917.f323.setEditable(false);
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
      this.problem.m1559();
   }

   String getChangedProblem() {
      String s = this.saveProblem();
      String s1 = this.problemIndex == -1 ? newProblem : problems.m1778(this.problemIndex);
      return TaggedRecord.m1500(s).equals(TaggedRecord.m1500(s1)) ? null : s;
   }

   static boolean saveProblems(int i) {
      if (i != -1) {
         ProblemEntry problementry = problems.m1779(i);
         problementry.state = getProblemState(problementry.name);
      }

      return saveProblems();
   }

   static boolean saveProblems() {
      if (!LogicProgram.m976()) {
         return false;
      } else {
         try {
            writeProblems(problems, new FileWriter(new File(LogicProgram.workDir, "derwork.txt")));
            return true;
         } catch (IOException ioexception) {
            LogicProgram.m971("not004", "derwork.txt");
            return false;
         }
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
      if (UserSetup.m2105("Derivation")) {
         int[] aint = getExerciseIndices();
         BusyIndicator busyindicator = new BusyIndicator(this);
         Submission submission = ServerConnection.prepareSubmission(busyindicator);
         if (submission != null) {
            if (C_KB.m766(this, null)) {
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
         int[] aint = C_KB.m771(this);
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
            LPDerivation lpderivation = new LPDerivation(false);
            lpderivation.doSubs = true;
            String s = problems.m1778(aint[j]);
            TaggedRecord taggedrecord = new TaggedRecord(s);
            String s1 = getProblemStatement(taggedrecord);
            submission.problemMd5 = Scrambler.md5Base64(s1 == null ? "" : s1.trim());
            submission.evaluation = C_EE.f1128[lpderivation.getProblemState(taggedrecord)];
            submission.work = s + lpderivation.saveMessages();
            submission.problemName = taggedrecord.getName();
            submission.module = moduleAbbrs[moduleIndex];
            submission.helpCount = taggedrecord.m1498();
            submission.duration = taggedrecord.m1499();
            boolean flag = LogicProgram.m1060(logSubmit, getExerciseTitle(submission.problemName));
            if (ServerConnection.submit(submission, busyindicator)) {
               vector.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.m1083("derdata.txt", "S", s, submission.m3());
               }
            } else {
               vector1.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.m1082("derdata.txt", "F", s);
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
         int[] aint = C_KB.m772(this);
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
               String s2 = C_KB.m796(flag ? this.problemTitle : null);
               if (s2 == null) {
                  return false;
               }

               this.setProblemTitle(s2);
               C_EE c_ee = new C_EE(TaggedRecord.m1495(s, this.problemTitle), false);
               this.problemIndex = problems.m1771(c_ee, false);
               this.problemIndex = this.problemIndex == -1 ? problems.size() : this.problemIndex + 1;
               problems.insertElementAt(c_ee, this.problemIndex);
            } else {
               s1 = problems.m1778(this.problemIndex);
               problems.m1776(s, this.problemIndex);
            }

            if (saveProblems(this.problemIndex)) {
               if (this.problemTitle != null && this.problemTitle.toUpperCase().startsWith("UR")) {
                  C_DB c_db = (C_DB)userRules.m2203(this.problemTitle);
                  if (c_db == null) {
                     c_db = new C_DB(this.problemTitle);
                     if (c_db.f823 == null) {
                        userRules.m2202(c_db);
                     }
                  } else {
                     c_db.m453(this.problemTitle);
                     if (c_db.f823 != null) {
                        userRules.m2206(c_db);
                     }
                  }
               }

               return true;
            } else {
               if (s1 == null) {
                  problems.m1101(this.problemIndex);
                  this.problemIndex = -1;
               } else {
                  problems.m1776(s1, this.problemIndex);
               }

               return false;
            }
         }
      }
   }

   String saveProblem() {
      if (this.focus != null && this.focus == this.focus.f1258.f324) {
         this.focus.f1258.m585();
      }

      String s = this.saveTitle() + this.problem.m47();
      if (this.errorCount != 0) {
         s = s + this.errorCount + "`e";
      }

      if (this.updateWorkTime() != 0L) {
         s = s + this.workTime + "`t";
      }

      return TaggedRecord.m1509(s);
   }

   String saveMessages() {
      return this.problem.m48();
   }

   String saveTitle() {
      return TaggedRecord.m1508(this.problemTitle, '$');
   }

   static String trimTitle(String s) {
      if (s == null) {
         return null;
      } else {
         return isExercise(s) ? LogicProgram.m1000(s) : s.trim();
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
      int j = taggedrecord.m1482();

      for (int k = 0; k < j; k++) {
         char c0 = taggedrecord.tagAt(k);
         String s = taggedrecord.valueAt(k);
         if ((c0 == '-' || c0 == '+') && derivationbox != null) {
            if (derivationline == null) {
               this.titlePanel.m1823(LogicProgram.m995(s, maggie, kaplan));
            } else {
               i++;
               derivationbox = derivationbox.m1557(-1);
               if (c0 == '+') {
                  derivationbox.m2123(false);
               }
            }

            derivationline = derivationbox.f917;
            derivationline.m581(i);
            derivationline.m6(s);
            derivationline.m594();
         } else if (c0 == '<' && derivationbox != null) {
            i++;
            derivationline = derivationbox.m1556(-1);
            derivationline.m581(i);
            derivationline.m6(s);
            derivationline.m594();
         } else if (c0 == '>' && derivationbox != null) {
            derivationline.m8(LogicProgram.m995(s, rob, kaplan));
            derivationline.m587(false);
         } else if (c0 == '#' && derivationbox != null) {
            i++;
            derivationline = derivationbox.m1556(-1);
            derivationline.m581(i);
            derivationline.m8(LogicProgram.m995(s, rob, kaplan));
            derivationline.m587(false);
            derivationline.m574();
            derivationbox = derivationbox.f916;
         } else if (c0 == '=' && derivationbox != null) {
            derivationbox = derivationbox.f916;
         } else if (c0 == ':' && derivationbox != null) {
            if (derivationline.f336 == null) {
               derivationline.f336 = new Vector();
            }

            derivationline.f336.addElement(Justification.m686(s, this));
         } else if (c0 == '$') {
            this.setProblemTitle(s);
         } else if (c0 == 's') {
            if (derivationline.f340 != null) {
               derivationline.f340.setText(LogicProgram.m995(s, rob, kaplan));
            }
         } else if (c0 == 'e') {
            Integer integer = LogicProgram.parseInteger(s);
            this.errorCount = integer == null ? 0 : integer;
         } else if (c0 == 't') {
            Long olong = LogicProgram.m1012(s);
            this.workTime = olong == null ? 0L : olong;
         } else if (c0 == 'm' && !restating) {
            int i1 = s.indexOf(58);
            if (i1 != -1) {
               Integer integer1 = LogicProgram.parseInteger(s.substring(0, i1).trim());
               if (integer1 != null) {
                  int l = integer1;
                  if (l >= 0) {
                     Object object = l == 0 ? this.problem : this.problem.m32(l);
                     if (object != null) {
                        ((DerivationNode)object).m10(s.substring(i1 + 1), true);
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
      String s1 = argumentparser.m1384();
      if (s1 != null) {
         Hashtable hashtable = Message.params("expression", LogicProgram.m995(s1, maggie, kaplan));
         MessageDialog.showMessage(C_n_.get("dererr082"), hashtable, null, null);
      } else if (!argumentparser.f829 && argumentparser.m1385() == 0) {
         this.loadProblem(TaggedRecord.m1509(TaggedRecord.m1508(ArgumentParser.m1383(s), '-') + TaggedRecord.m1508("", '=')));
      } else {
         MessageDialog.showMessage(C_n_.get("dererr083"), null, null, null);
      }
   }

   void setProblemTitle(String s) {
      if (s != null && !(s = s.trim()).equals("")) {
         this.problemTitle = s;
         s = trimTitle(this.problemTitle);
         this.problem.f917.f322.setText(s + ": ");
         this.titlePanel.m1821(s);
      } else {
         this.problemTitle = null;
         this.problem.f917.f322.setText("Problem: ");
         this.titlePanel.m1821(null);
      }
   }

   static RuleTable getDerivationRules() {
      RuleTable ruletable = new RuleTable(null);
      Rule rule = new Rule("CD");
      SchematicRule schematicrule;
      rule.m1370(schematicrule = new SchematicRule("CD/C"));
      ruletable.m2202(schematicrule);
      rule.m1370(schematicrule = new SchematicRule("CD/D"));
      ruletable.m2202(schematicrule);
      rule.m1370(schematicrule = new SchematicRule("CD/I"));
      ruletable.m2202(schematicrule);
      ruletable.m2202(rule);
      rule = new Rule("DD");
      rule.m1370(schematicrule = new SchematicRule("DD/C"));
      ruletable.m2202(schematicrule);
      rule.m1370(schematicrule = new SchematicRule("DD/D"));
      ruletable.m2202(schematicrule);
      rule.m1370(schematicrule = new SchematicRule("DD/I"));
      ruletable.m2202(schematicrule);
      ruletable.m2202(rule);
      rule = new Rule("ID");
      rule.m1370(schematicrule = new SchematicRule("ID/C"));
      ruletable.m2202(schematicrule);
      rule.m1370(schematicrule = new SchematicRule("ID/D"));
      ruletable.m2202(schematicrule);
      rule.m1370(schematicrule = new SchematicRule("ID/I"));
      ruletable.m2202(schematicrule);
      ruletable.m2202(rule);
      ruletable.m2202(new SchematicRule("UD"));
      ruletable.m2202(new SchematicRule("IE"));
      ruletable.m2202(new SchematicRule("CIE"));
      rule = new Rule("BD");
      rule.m1370(schematicrule = new SchematicRule("BD/B"));
      ruletable.m2202(schematicrule);
      ruletable.m2202(rule);
      return ruletable;
   }

   static void listRules(String s, Vector vector, C_n_F c_n_f, boolean flag) {
      s = s.trim();

      while (!s.equals("")) {
         int i = s.indexOf(".");
         String s1;
         if (i == -1) {
            s1 = s;
            s = "";
         } else {
            s1 = s.substring(0, i).trim();
            s = s.substring(i + 1).trim();
         }

         if ("~{".indexOf(s1.charAt(0)) != -1) {
            c_n_f.m1975(new C_n_F(s1));
         } else {
            Integer integer;
            if ((integer = SchematicRule.m1366(s1)) != null) {
               c_n_f.m1975(C_n_F.m1970(integer));
            } else {
               Rule rule;
               if ((rule = getRule(s1)) != null) {
                  if (flag) {
                     SchematicRule[] aschematicrule = rule.m1374();
                     int j = aschematicrule.length;

                     for (int k = 0; k < j; k++) {
                        if (!vector.contains(aschematicrule[k].f820)) {
                           vector.addElement(aschematicrule[k].f820);
                        }
                     }
                  } else if (!vector.contains(rule.f820)) {
                     vector.addElement(rule.f820);
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
      Rule rule = derivationRules == null ? null : derivationRules.m2203(s);
      if (rule == null) {
         return null;
      } else if (this.hasProperty(rule, "disabled")) {
         return new ErrorRef("dererr041");
      } else if (flag && this.hasProperty(rule, "manual")) {
         return new ErrorRef("dererr040");
      } else {
         return rule.m952(this) ? null : new ErrorRef("dererr016");
      }
   }

   static Rule getRule(String s) {
      Rule rule = LogicProgram.m1026(s);
      if (rule == null && derivationRules != null) {
         rule = derivationRules.m2203(s);
      }

      if (rule == null && userRules != null) {
         rule = userRules.m2203(s);
      }

      return rule;
   }

   Vector enabledRules(RuleTable ruletable) {
      Vector vector = new Vector();
      Vector vector1 = ruletable.f1469;
      int i = vector1.size();

      for (int j = 0; j < i; j++) {
         String s = (String)vector1.elementAt(j);
         Rule rule = ruletable.m2203(s);
         if (!rule.m1193(this, "disabled", false) && rule.m953(this)) {
            vector.addElement(s);
         }
      }

      return vector;
   }

   C_n_F enabledTheorems(TheoremTable theoremtable) {
      C_n_F c_n_f = new C_n_F();
      Enumeration enumeration = theoremtable.f1464.m1985();

      while (enumeration.hasMoreElements()) {
         Integer integer = (Integer)enumeration.nextElement();
         Theorem theorem = theoremtable.m2199(integer);
         if (!theorem.m1193(this, "disabled", false) && theorem.m953(this)) {
            c_n_f.m1975(C_n_F.m1970(theorem.f700));
         }
      }

      return c_n_f;
   }

   @Override
   public boolean hasProperty(Rule rule, String s) {
      String s1 = rule instanceof C_DB ? "UR" : rule.f820;
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
         return this.disabledRange != null && this.disabledRange.m1983(i) || this.isWeaklyDisabled(integer);
      } else if (s.equals("manual")) {
         return this.manualRange != null && this.manualRange.m1983(i);
      } else if (s.equals("manualOrDisabled")) {
         return this.manualRange != null && this.manualRange.m1983(i) || this.hasProperty(integer, "disabled");
      } else if (s.equals("weakAss")) {
         return this.weakAssRange != null && this.weakAssRange.m1983(i);
      } else if (!s.equals("assumed")) {
         throw new IllegalArgumentException("unknown property: " + s);
      } else {
         return this.assumedRange != null && this.assumedRange.m1983(i);
      }
   }

   @Override
   public Vector getProofs(SchematicRule schematicrule) {
      if (schematicrule instanceof C_DB) {
         return ((C_DB)schematicrule).m452();
      } else {
         return exercises.f618 != null && !schematicrule.m1193(this, "assumed", false) ? (Vector)exercises.f618.get(schematicrule.f820) : null;
      }
   }

   @Override
   public Vector getProofs(Integer integer) {
      return exercises.f618 != null && !this.hasProperty(integer, "assumed") ? (Vector)exercises.f618.get(integer) : null;
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
      ProblemEntry problementry = problems.m1772(s);
      return problementry != null && problementry.state == 2;
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         C_TB c_tb = new C_TB();
         TaggedRecord taggedrecord = new TaggedRecord(reader, true);
         String s = "";

         while (taggedrecord.readNext()) {
            String s1 = taggedrecord.getName();
            if (s1 != null && s1.trim().equalsIgnoreCase("derivation")) {
               int[] aint = taggedrecord.m1481("dDmMaA+?");
               int i = aint.length;

               for (int j = 0; j < i; j++) {
                  char c0 = taggedrecord.tagAt(aint[j]);
                  String s2 = taggedrecord.valueAt(aint[j]);
                  if (c0 == 'd') {
                     disabledRuleXRefs.addElement(new C_y_E(s2, c_tb).m2195(s));
                  } else if (c0 == 'D') {
                     disabledRuleXRefs.addElement(new C_y_E(s2, c_tb, true).m2195(s));
                  } else if (c0 == 'm') {
                     manualRuleXRefs.addElement(new C_y_E(s2, c_tb).m2195(s));
                  } else if (c0 == 'M') {
                     manualRuleXRefs.addElement(new C_y_E(s2, c_tb, true).m2195(s));
                  } else if (c0 == 'a') {
                     weakAssRuleXRefs.addElement(new C_y_E(s2, c_tb, true).m2195(s));
                  } else if (c0 == 'A') {
                     assumedRuleXRefs.addElement(new C_y_E(s2, c_tb, true).m2195(s));
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

                           noCommand.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noQueue")) {
                           if (noQueue == null) {
                              noQueue = new ProblemSelector();
                           }

                           noQueue.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("chap1")) {
                           if (chap1 == null) {
                              chap1 = new ProblemSelector();
                           }

                           chap1.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("chap2")) {
                           if (chap2 == null) {
                              chap2 = new ProblemSelector();
                           }

                           chap2.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
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
                        } else if (s3.equalsIgnoreCase("noCheck")) {
                           if (noCheck == null) {
                              noCheck = new ProblemSelector();
                           }

                           noCheck.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noErrMess")) {
                           if (noErrMess == null) {
                              noErrMess = new ProblemSelector();
                           }

                           noErrMess.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new ProblemSelector();
                           }

                           monoProbs.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrint")) {
                           if (noPrint == null) {
                              noPrint = new ProblemSelector();
                           }

                           noPrint.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintCheck")) {
                           if (noPrintCheck == null) {
                              noPrintCheck = new ProblemSelector();
                           }

                           noPrintCheck.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintErr")) {
                           if (noPrintErr == null) {
                              noPrintErr = new ProblemSelector();
                           }

                           noPrintErr.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
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
                        } else if (s3.equalsIgnoreCase("logShowCmd")) {
                           if (logShowCmd == null) {
                              logShowCmd = new ProblemSelector();
                           }

                           logShowCmd.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noMixedMode")) {
                           if (noMixedMode == null) {
                              noMixedMode = new ProblemSelector();
                           }

                           noMixedMode.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
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
      String s = taggedrecord.getName();
      this.probOptions = taggedrecord.m1506('%');
      this.dontChange = this.probOptions != null && this.probOptions.containsKey("eg");
      this.titlePanel.m1827(taggedrecord.valueAt(taggedrecord.indexOfTag('!')));
      this.commandMode = !LogicProgram.m1060(noCommand, s);
      this.queuedMode = !LogicProgram.m1060(noQueue, s);
      this.checkDisabled = LogicProgram.m1060(this.forPrint ? noPrintCheck : noCheck, s);
      this.errorMessagesDisabled = LogicProgram.m1060(this.forPrint ? noPrintErr : noErrMess, s);
      this.mixedModeDisabled = LogicProgram.m1060(noMixedMode, s);
      this.doShowLog = LogicProgram.m1060(logShowCmd, s);
      if (LogicProgram.m1060(chap1, s)) {
         this.chapter = new Integer(1);
      } else if (LogicProgram.m1060(chap2, s)) {
         this.chapter = new Integer(2);
      } else {
         this.chapter = null;
      }

      this.disabledRules = new Vector();
      this.manualRules = new Vector();
      this.weakAssRules = new Vector();
      this.assumedRules = new Vector();
      this.disabledRange = new C_n_F();
      this.manualRange = new C_n_F();
      this.weakAssRange = new C_n_F();
      this.assumedRange = new C_n_F();
      int j = disabledRuleXRefs.size();

      for (int i = 0; i < j; i++) {
         ((C_y_E)disabledRuleXRefs.elementAt(i)).m2196(s, this.disabledRules, this.disabledRange);
      }

      j = manualRuleXRefs.size();

      for (int k = 0; k < j; k++) {
         ((C_y_E)manualRuleXRefs.elementAt(k)).m2196(s, this.manualRules, this.manualRange);
      }

      j = weakAssRuleXRefs.size();

      for (int l = 0; l < j; l++) {
         ((C_y_E)weakAssRuleXRefs.elementAt(l)).m2196(s, this.weakAssRules, this.weakAssRange);
      }

      j = assumedRuleXRefs.size();

      for (int i1 = 0; i1 < j; i1++) {
         ((C_y_E)assumedRuleXRefs.elementAt(i1)).m2196(s, this.assumedRules, this.assumedRange);
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
      return taggedrecord.valueAt(taggedrecord.m1478("-+"));
   }

   static int countProblemLines(String s) {
      return countProblemLines(new TaggedRecord(s));
   }

   static int countProblemLines(TaggedRecord taggedrecord) {
      int i = taggedrecord.m1482();
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
      return taggedrecord.m1484("-+<#=");
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
      if (this.focus != null && this.focus == this.focus.f1258.f324) {
         this.focus.f1258.m585();
      }

      if (this.problem.m43() & this.problem.m46()) {
         this.problem.m1561("derinf005", 4);
         this.titlePanel.m1825("Correct");
         this.serialMode = false;
         return true;
      } else {
         this.problem.m1561(this.aborted ? "dererr056" : (this.complete ? "dererr057" : "dererr058"), 4);
         this.titlePanel.m1825(this.aborted ? "" : (this.complete ? "Incorrect" : "Incomplete"));
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
         C_MD c_md = readWork();
         if (c_md == null) {
            return false;
         } else {
            if (c_md.f1079 && !c_md.m1777(LogicProgram.user).equals(c_md.f1076)) {
               System.out.println("Could not digest file: derwork.txt");
               if (!UserSetup.m2101("indigestion", "instructor")) {
                  LogicProgram.m971("not003", "derwork.txt");
                  return false;
               }
            }

            problems = c_md;
            c_md.m1100();
            C_EE.f297 = ProblemEntry.m1816("derwork.txt", problems);
            ProblemEntry.m1815(exercises, C_EE.f297);
            return true;
         }
      }
   }

   static C_MD readWork() {
      if (!LogicProgram.m976()) {
         return null;
      } else {
         C_MD c_md = new C_MD();
         ScrambledReader scrambledreader = LogicProgram.openDataFile("derwork.txt", true);
         if (!LogicProgram.f584 || scrambledreader instanceof PlainRecordReader) {
            if (scrambledreader == null) {
               LogicProgram.m971("not001", "derwork.txt");
               return null;
            }

            if (scrambledreader instanceof PlainRecordReader) {
               c_md.f1079 = true;
            }

            if (!readProblems(scrambledreader, c_md, false)) {
               LogicProgram.m971("not002", "derwork.txt");
               return null;
            }
         }

         if (!(scrambledreader instanceof PlainRecordReader)) {
            scrambledreader = LogicProgram.m1065("derwork.txt", false);
            if (scrambledreader != null && !mergeProblems(scrambledreader, c_md, false)) {
               LogicProgram.m971("not002", "derwork.txt");
               return null;
            }
         }

         return c_md;
      }
   }

   static boolean hasWork(TaggedRecord taggedrecord) {
      return countProblemLines(taggedrecord) > 1;
   }

   static boolean readProblems(Reader reader, C_MD c_md, boolean flag) {
      return readProblems(reader, c_md, flag, false);
   }

   static boolean mergeProblems(Reader reader, C_MD c_md, boolean flag) {
      return readProblems(reader, c_md, flag, true);
   }

   static boolean readProblems(Reader reader, C_MD c_md, boolean flag, boolean flag1) {
      if (flag && c_md.f618 == null) {
         c_md.f618 = new Hashtable();
      }

      return LogicModule.readProblems(reader, c_md, flag, flag1);
   }

   void setupPrintProblem(Dimension dimension) {
      this.setWidths(true, this.fontSize, dimension.width);
      this.setColors(this.colors);
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
         jpanel.setLayout(new C_u_A(null, 1, new int[]{dimension.width}));
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
         String s1 = getExerciseTitle(s);
         if (!printIncorrect || k == 1 || k == 3) {
            boolean flag = LogicProgram.m1060(noPrintCheck, s1);
            String s2 = getProblemStatement(taggedrecord);
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new C_u_A(null, 2, new int[]{20, dimension.width - 20}));
            jpanel.add(new C_f_E(flag ? " " : C_EE.f1128[k]));
            C_NC c_nc;
            jpanel.add(c_nc = new C_NC(LogicProgram.m1004("\\l" + s + ": " + s2)));
            c_nc.setLineWrap(true);
            c_nc.setWrapStyleWord(true);
            c_nc.setBackground(LogicProgram.f605[1]);
            vector.add(jpanel);
         }

         if (LogicProgram.m1060(logPrint, s1)) {
            LogicProgram.m1082("derdata.txt", "R", problementry.name);
         }
      }

      return vector;
   }

   static Vector getPrintProblems(int[] aint, Dimension dimension) {
      Vector vector = new Vector(aint.length);

      for (int i = 0; i < aint.length; i++) {
         ProblemEntry problementry = problems.m1779(aint[i]);
         String s = getExerciseTitle(TaggedRecord.m1493(problementry.name));
         if (LogicProgram.m1060(logPrint, s)) {
            LogicProgram.m1082("derdata.txt", "P", problementry.name);
         }

         if (!printIncorrect || problementry.state == 1 || problementry.state == 3) {
            LPDerivation lpderivation = new LPDerivation(true);
            lpderivation.setupFrame("Logic Program: Print");
            lpderivation.loadProblem(problementry.name);
            lpderivation.problem.m1550();
            lpderivation.checkProblem();
            lpderivation.setupPrintProblem(dimension);
            vector.add(lpderivation.problemPanel);
            lpderivation.frame.dispose();
         }
      }

      return vector;
   }

   void parseProblem() {
      ArgumentParser argumentparser = new ArgumentParser(this.problem.m7(true), true);
      this.conclusion = argumentparser.f828;
      this.premises = argumentparser.f827;
      String s = argumentparser.m1384();
      this.problem.m13();
      if (s != null) {
         this.problem.m12("dererr059", Message.params("parser error", s));
         this.problem.f917.f333 = false;
      } else {
         this.problem.f917.f333 = true;
      }
   }

   boolean isPremise(Expression expression) {
      int i = this.premises.length;
      if (expression == null) {
         return false;
      } else {
         for (int j = 0; j < i; j++) {
            if (expression.m1235(this.premises[j])) {
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
      return expression == null ? false : expression.m1235(this.conclusion);
   }

   static class C__A extends LogicModule.C__A {
      C__A(BusyIndicator busyindicator, Rectangle rectangle, String s) {
         super(busyindicator, rectangle, s);
      }

      @Override
      public void run() {
         LPDerivation.allocateDerModule(this);
      }

      @Override
      public void m959() {
         LPDerivation.continueStartup();
      }
   }
}
