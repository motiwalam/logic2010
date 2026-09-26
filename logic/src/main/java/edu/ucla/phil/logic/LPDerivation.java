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

class LPDerivation extends C_U implements C_w_E, C_DE {
   int fontSize = LogicProgram.f539;
   Font font;
   C__ problem = null;
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
   static C_BE noCommand = null;
   static C_BE noQueue = null;
   static C_BE noCheck = null;
   static C_BE noErrMess = null;
   static C_BE monoProbs = null;
   static C_BE noPrint = null;
   static C_BE noPrintCheck = null;
   static C_BE noPrintErr = null;
   static C_BE addToDB = null;
   static C_BE updateDB = null;
   static C_BE chap1 = null;
   static C_BE chap2 = null;
   static C_BE logPrint = null;
   static C_BE logSubmit = null;
   static C_BE needPrint = null;
   static C_BE needSubmit = null;
   static C_BE logShowCmd = null;
   static C_BE noMixedMode = null;
   static boolean officialIE = false;
   static boolean noUser = false;
   static boolean submitExam = false;
   static boolean printIncorrect = false;
   static boolean restating = false;
   C_RF[] premises;
   C_RF conclusion;
   static final String workFileName = "derwork.txt";
   static final String logFileName = "derdata.txt";
   static final String digestVersKey = "derDigestVers";
   static Class messageClass = C_n_.class;
   static C_MD exercises = null;
   static C_MD problems = null;
   static Vector instances = new Vector();
   static C_c_C printQueue = new C_c_C("Derivation");
   static C_z_B userRules = null;
   static C_z_B derivationRules = null;
   static C_d_F ruleQuery = null;
   static String newProblem = null;
   static Vector startups;
   String problemTitle;
   String lastUserProblem;
   int[][] widthInfo = new int[][]{{2, 0}, {20, 4}, {5, 1}, {10, 2}, {1, 0}};
   int lineColumns = this.widthInfo.length;
   int[] lastWidthsSet = new int[]{0, 0, 0};
   int phase;
   int errorCount;
   int printIndex;
   long workTime;
   long loadTime;
   boolean aborted;
   boolean doShowLog;
   Vector varNames;
   Hashtable probOptions = null;
   static int moduleIndex = 0;

   static boolean getExercises() {
      if (!C_n_.m410()) {
         LogicProgram.m971("not001", "the derivation messages file");
         return false;
      } else {
         derivationRules = getDerivationRules();
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

   static C_MD readExercises() {
      return readExercises(LogicProgram.f584, false, false);
   }

   static C_MD readExercises(boolean flag, boolean flag1, boolean flag2) {
      C_MD c_md = new C_MD();
      if (!flag) {
         C_XB c_xb = LogicProgram.m1062("derwork.txt", false);
         if (c_xb == null) {
            LogicProgram.m971("not001", "the core Derivation exercise file");
            return null;
         }

         if (!readProblems(c_xb, c_md, true)) {
            LogicProgram.m971("not002", "the core Derivation exercise file");
            return null;
         }
      }

      if (!flag1) {
         C_XB c_xb1 = LogicProgram.m1065("derwork.txt", flag2);
         if (c_xb1 != null && (flag ? !readProblems(c_xb1, c_md, true) : !mergeProblems(c_xb1, c_md, true))) {
            LogicProgram.m971("not002", "the local Derivation exercise file");
            return null;
         }
      }

      return c_md;
   }

   LPDerivation(boolean flag) {
      super(flag);
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
      this.problemPanel.add(this.problem = new C__(this), "Center");
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

   static synchronized void startup(Rectangle rectangle, C_x_A c_x_a, String s) {
      if (startups == null) {
         startups = new Vector();
      }

      if (exercises != null || getExercises()) {
         LPDerivation.C__A lpderivation$c__a = new LPDerivation.C__A(c_x_a, rectangle, s);
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
         ((C_U.C__A)startups.get(i - 1 - j)).m1310();
      }

      while (!startups.isEmpty()) {
         C_U.C__A c_u$c__a = (C_U.C__A)startups.remove(0);
         SwingUtilities.invokeLater(c_u$c__a);
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

   static void allocateDerModule(C_U.C__A c_u$c__a) {
      LPDerivation lpderivation = new LPDerivation(false);
      instances.addElement(lpderivation);
      if (c_u$c__a.f784 == null || newProblem == null) {
         lpderivation.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lpderivation.saveProblem();
         }
      }

      if (c_u$c__a.f784 != null) {
         lpderivation.loadProblem(c_u$c__a.f784);
         c_u$c__a.f784 = null;
      }

      lpderivation.setupFrame(LPInfo.programName + ": Derivation");
      lpderivation.frame.setBounds(lpderivation.fixModuleRect(c_u$c__a.f783));
      lpderivation.frame.setVisible(true);
      lpderivation.requestFocus();
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
      return s != null && s.toLowerCase().startsWith("deriv") ? "1" : "NULL";
   }

   void setupFrame(String s) {
      this.frame = new C_0E(s);
      this.doSubs = true;
      this.setFontSize(this.fontSize);
      this.setColors(this.colors);
      this.frame.add(this, "Center");
      this.frame.f27 = this;
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
      readOptions(LogicProgram.m1062("options", false));
      readOptions(LogicProgram.m1065("options", false));
      logNeeds();
      if (needPrint != null && !needPrint.m402() || needSubmit != null && !needSubmit.m402()) {
         C_a_A c_a_a = new C_a_A(readWork());
         c_a_a.m1634(readExercises());
         C_z_C.m2215(hashtable, "derdata.txt", "P", needPrint, c_a_a);
         c_a_a.m1635();
         C_z_C.m2215(hashtable1, "derdata.txt", "S", needSubmit, c_a_a);
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
            if ((vector == null || !vector.contains(s)) && hasWork(new C_XD(s))) {
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
      this.font = LogicProgram.m1029(i);
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

   static String removeWork(C_XD c_xd) {
      String s = C_XD.m1508(c_xd.m1494(), '$');
      s = s + C_XD.m1508(getProblemStatement(c_xd), '-') + "`=";
      return s + c_xd.m1484("%u!");
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
      this.problemPanel.add(this.problem = new C__(this), "Center");
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
      return C_XD.m1500(s).equals(C_XD.m1500(s1)) ? null : s;
   }

   static boolean saveProblems(int i) {
      if (i != -1) {
         C_f_F c_f_f = problems.m1779(i);
         c_f_f.f1120 = getProblemState(c_f_f.f1119);
      }

      return saveProblems();
   }

   static boolean saveProblems() {
      if (!LogicProgram.m976()) {
         return false;
      } else {
         try {
            writeProblems(problems, new FileWriter(new File(LogicProgram.f555, "derwork.txt")));
            return true;
         } catch (IOException ioexception) {
            LogicProgram.m971("not004", "derwork.txt");
            return false;
         }
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
      if (C_u_C.m2105("Derivation")) {
         int[] aint = getExerciseIndices();
         C_x_A c_x_a = new C_x_A(this);
         C_0A c_0a = C_KC.m835(c_x_a);
         if (c_0a != null) {
            if (C_KB.m766(this, null)) {
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
         int[] aint = C_KB.m771(this);
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
            LPDerivation lpderivation = new LPDerivation(false);
            lpderivation.doSubs = true;
            String s = problems.m1778(aint[j]);
            C_XD c_xd = new C_XD(s);
            String s1 = getProblemStatement(c_xd);
            c_0a.f7 = C_z_D.m2225(s1 == null ? "" : s1.trim());
            c_0a.f6 = C_EE.f1128[lpderivation.getProblemState(c_xd)];
            c_0a.f8 = s + lpderivation.saveMessages();
            c_0a.f9 = c_xd.m1494();
            c_0a.f10 = moduleAbbrs[moduleIndex];
            c_0a.f11 = c_xd.m1498();
            c_0a.f12 = c_xd.m1499();
            boolean flag = LogicProgram.m1060(logSubmit, getExerciseTitle(c_0a.f9));
            if (C_KC.m836(c_0a, c_x_a)) {
               vector.addElement(trimTitle(c_0a.f9));
               if (flag) {
                  LogicProgram.m1083("derdata.txt", "S", s, c_0a.m3());
               }
            } else {
               vector1.addElement(trimTitle(c_0a.f9));
               if (flag) {
                  LogicProgram.m1082("derdata.txt", "F", s);
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
         int[] aint = C_KB.m772(this);
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
               C_EE c_ee = new C_EE(C_XD.m1495(s, this.problemTitle), false);
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

      return C_XD.m1509(s);
   }

   String saveMessages() {
      return this.problem.m48();
   }

   String saveTitle() {
      return C_XD.m1508(this.problemTitle, '$');
   }

   static String trimTitle(String s) {
      if (s == null) {
         return null;
      } else {
         return isExercise(s) ? LogicProgram.m1000(s) : s.trim();
      }
   }

   void loadProblem(String s) {
      this.loadProblem(new C_XD(s));
   }

   void loadProblem(C_XD c_xd) {
      this.reset();
      int i = 0;
      C__ c__ = this.problem;
      C_G c_g = null;
      this.loadExerciseInfo(c_xd);
      int j = c_xd.m1482();

      for (int k = 0; k < j; k++) {
         char c0 = c_xd.m1474(k);
         String s = c_xd.m1483(k);
         if ((c0 == '-' || c0 == '+') && c__ != null) {
            if (c_g == null) {
               this.titlePanel.m1823(LogicProgram.m995(s, maggie, kaplan));
            } else {
               i++;
               c__ = c__.m1557(-1);
               if (c0 == '+') {
                  c__.m2123(false);
               }
            }

            c_g = c__.f917;
            c_g.m581(i);
            c_g.m6(s);
            c_g.m594();
         } else if (c0 == '<' && c__ != null) {
            i++;
            c_g = c__.m1556(-1);
            c_g.m581(i);
            c_g.m6(s);
            c_g.m594();
         } else if (c0 == '>' && c__ != null) {
            c_g.m8(LogicProgram.m995(s, rob, kaplan));
            c_g.m587(false);
         } else if (c0 == '#' && c__ != null) {
            i++;
            c_g = c__.m1556(-1);
            c_g.m581(i);
            c_g.m8(LogicProgram.m995(s, rob, kaplan));
            c_g.m587(false);
            c_g.m574();
            c__ = c__.f916;
         } else if (c0 == '=' && c__ != null) {
            c__ = c__.f916;
         } else if (c0 == ':' && c__ != null) {
            if (c_g.f336 == null) {
               c_g.f336 = new Vector();
            }

            c_g.f336.addElement(C_HD.m686(s, this));
         } else if (c0 == '$') {
            this.setProblemTitle(s);
         } else if (c0 == 's') {
            if (c_g.f340 != null) {
               c_g.f340.setText(LogicProgram.m995(s, rob, kaplan));
            }
         } else if (c0 == 'e') {
            Integer integer = LogicProgram.m1010(s);
            this.errorCount = integer == null ? 0 : integer;
         } else if (c0 == 't') {
            Long olong = LogicProgram.m1012(s);
            this.workTime = olong == null ? 0L : olong;
         } else if (c0 == 'm' && !restating) {
            int i1 = s.indexOf(58);
            if (i1 != -1) {
               Integer integer1 = LogicProgram.m1010(s.substring(0, i1).trim());
               if (integer1 != null) {
                  int l = integer1;
                  if (l >= 0) {
                     Object object = l == 0 ? this.problem : this.problem.m32(l);
                     if (object != null) {
                        ((C_0B)object).m10(s.substring(i1 + 1), true);
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
      C_VC c_vc = new C_VC(s, true);
      String s1 = c_vc.m1384();
      if (s1 != null) {
         Hashtable hashtable = C_H.m666("expression", LogicProgram.m995(s1, maggie, kaplan));
         C_UA.m1329(C_n_.m411("dererr082"), hashtable, null, null);
      } else if (!c_vc.f829 && c_vc.m1385() == 0) {
         this.loadProblem(C_XD.m1509(C_XD.m1508(C_VC.m1383(s), '-') + C_XD.m1508("", '=')));
      } else {
         C_UA.m1329(C_n_.m411("dererr083"), null, null, null);
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

   static C_z_B getDerivationRules() {
      C_z_B c_z_b = new C_z_B(null);
      C_VB c_vb = new C_VB("CD");
      C_LF c_lf;
      c_vb.m1370(c_lf = new C_LF("CD/C"));
      c_z_b.m2202(c_lf);
      c_vb.m1370(c_lf = new C_LF("CD/D"));
      c_z_b.m2202(c_lf);
      c_vb.m1370(c_lf = new C_LF("CD/I"));
      c_z_b.m2202(c_lf);
      c_z_b.m2202(c_vb);
      c_vb = new C_VB("DD");
      c_vb.m1370(c_lf = new C_LF("DD/C"));
      c_z_b.m2202(c_lf);
      c_vb.m1370(c_lf = new C_LF("DD/D"));
      c_z_b.m2202(c_lf);
      c_vb.m1370(c_lf = new C_LF("DD/I"));
      c_z_b.m2202(c_lf);
      c_z_b.m2202(c_vb);
      c_vb = new C_VB("ID");
      c_vb.m1370(c_lf = new C_LF("ID/C"));
      c_z_b.m2202(c_lf);
      c_vb.m1370(c_lf = new C_LF("ID/D"));
      c_z_b.m2202(c_lf);
      c_vb.m1370(c_lf = new C_LF("ID/I"));
      c_z_b.m2202(c_lf);
      c_z_b.m2202(c_vb);
      c_z_b.m2202(new C_LF("UD"));
      c_z_b.m2202(new C_LF("IE"));
      c_z_b.m2202(new C_LF("CIE"));
      c_vb = new C_VB("BD");
      c_vb.m1370(c_lf = new C_LF("BD/B"));
      c_z_b.m2202(c_lf);
      c_z_b.m2202(c_vb);
      return c_z_b;
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
            if ((integer = C_LF.m1366(s1)) != null) {
               c_n_f.m1975(C_n_F.m1970(integer));
            } else {
               C_VB c_vb;
               if ((c_vb = getRule(s1)) != null) {
                  if (flag) {
                     C_LF[] ac_lf = c_vb.m1374();
                     int j = ac_lf.length;

                     for (int k = 0; k < j; k++) {
                        if (!vector.contains(ac_lf[k].f820)) {
                           vector.addElement(ac_lf[k].f820);
                        }
                     }
                  } else if (!vector.contains(c_vb.f820)) {
                     vector.addElement(c_vb.f820);
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

   C_c_B checkDerivationRule(String s, boolean flag) {
      C_VB c_vb = derivationRules == null ? null : derivationRules.m2203(s);
      if (c_vb == null) {
         return null;
      } else if (this.hasProperty(c_vb, "disabled")) {
         return new C_c_B("dererr041");
      } else if (flag && this.hasProperty(c_vb, "manual")) {
         return new C_c_B("dererr040");
      } else {
         return c_vb.m952(this) ? null : new C_c_B("dererr016");
      }
   }

   static C_VB getRule(String s) {
      C_VB c_vb = LogicProgram.m1026(s);
      if (c_vb == null && derivationRules != null) {
         c_vb = derivationRules.m2203(s);
      }

      if (c_vb == null && userRules != null) {
         c_vb = userRules.m2203(s);
      }

      return c_vb;
   }

   Vector enabledRules(C_z_B c_z_b) {
      Vector vector = new Vector();
      Vector vector1 = c_z_b.f1469;
      int i = vector1.size();

      for (int j = 0; j < i; j++) {
         String s = (String)vector1.elementAt(j);
         C_VB c_vb = c_z_b.m2203(s);
         if (!c_vb.m1193(this, "disabled", false) && c_vb.m953(this)) {
            vector.addElement(s);
         }
      }

      return vector;
   }

   C_n_F enabledTheorems(C_z_ c_z_) {
      C_n_F c_n_f = new C_n_F();
      Enumeration enumeration = c_z_.f1464.m1985();

      while (enumeration.hasMoreElements()) {
         Integer integer = (Integer)enumeration.nextElement();
         C_QE c_qe = c_z_.m2199(integer);
         if (!c_qe.m1193(this, "disabled", false) && c_qe.m953(this)) {
            c_n_f.m1975(C_n_F.m1970(c_qe.f700));
         }
      }

      return c_n_f;
   }

   @Override
   public boolean hasProperty(C_VB c_vb, String s) {
      String s1 = c_vb instanceof C_DB ? "UR" : c_vb.f820;
      if (s.equals("disabled")) {
         return this.disabledRules != null && this.disabledRules.contains(s1) || this.isWeaklyDisabled(c_vb);
      } else if (s.equals("manual")) {
         return this.manualRules != null && this.manualRules.contains(s1);
      } else if (s.equals("manualOrDisabled")) {
         return this.manualRules != null && this.manualRules.contains(s1) || this.hasProperty(c_vb, "disabled");
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
   public Vector getProofs(C_LF c_lf) {
      if (c_lf instanceof C_DB) {
         return ((C_DB)c_lf).m452();
      } else {
         return exercises.f618 != null && !c_lf.m1193(this, "assumed", false) ? (Vector)exercises.f618.get(c_lf.f820) : null;
      }
   }

   @Override
   public Vector getProofs(Integer integer) {
      return exercises.f618 != null && !this.hasProperty(integer, "assumed") ? (Vector)exercises.f618.get(integer) : null;
   }

   boolean isWeaklyDisabled(C_VB c_vb) {
      if (this.problemTitle != null && this.hasProperty(c_vb, "weakAss")) {
         Vector vector = c_vb instanceof C_LF ? this.getProofs((C_LF)c_vb) : null;
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
      C_f_F c_f_f = problems.m1772(s);
      return c_f_f != null && c_f_f.f1120 == 2;
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         C_TB c_tb = new C_TB();
         C_XD c_xd = new C_XD(reader, true);
         String s = "";

         while (c_xd.m1469()) {
            String s1 = c_xd.m1494();
            if (s1 != null && s1.trim().equalsIgnoreCase("derivation")) {
               int[] aint = c_xd.m1481("dDmMaA+?");
               int i = aint.length;

               for (int j = 0; j < i; j++) {
                  char c0 = c_xd.m1474(aint[j]);
                  String s2 = c_xd.m1483(aint[j]);
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
                              noCommand = new C_BE();
                           }

                           noCommand.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noQueue")) {
                           if (noQueue == null) {
                              noQueue = new C_BE();
                           }

                           noQueue.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("chap1")) {
                           if (chap1 == null) {
                              chap1 = new C_BE();
                           }

                           chap1.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("chap2")) {
                           if (chap2 == null) {
                              chap2 = new C_BE();
                           }

                           chap2.m395(new C_BE(s2.substring(k + 1)).m403(s));
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
                        } else if (s3.equalsIgnoreCase("noCheck")) {
                           if (noCheck == null) {
                              noCheck = new C_BE();
                           }

                           noCheck.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noErrMess")) {
                           if (noErrMess == null) {
                              noErrMess = new C_BE();
                           }

                           noErrMess.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new C_BE();
                           }

                           monoProbs.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrint")) {
                           if (noPrint == null) {
                              noPrint = new C_BE();
                           }

                           noPrint.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintCheck")) {
                           if (noPrintCheck == null) {
                              noPrintCheck = new C_BE();
                           }

                           noPrintCheck.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintErr")) {
                           if (noPrintErr == null) {
                              noPrintErr = new C_BE();
                           }

                           noPrintErr.m395(new C_BE(s2.substring(k + 1)).m403(s));
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
                        } else if (s3.equalsIgnoreCase("logShowCmd")) {
                           if (logShowCmd == null) {
                              logShowCmd = new C_BE();
                           }

                           logShowCmd.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noMixedMode")) {
                           if (noMixedMode == null) {
                              noMixedMode = new C_BE();
                           }

                           noMixedMode.m395(new C_BE(s2.substring(k + 1)).m403(s));
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
      String s = c_xd.m1494();
      this.probOptions = c_xd.m1506('%');
      this.dontChange = this.probOptions != null && this.probOptions.containsKey("eg");
      this.titlePanel.m1827(c_xd.m1483(c_xd.m1475('!')));
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
      C_XD c_xd = new C_XD(s);
      return !hasWork(c_xd) ? 0 : new LPDerivation(false).getProblemState(c_xd);
   }

   @Override
   int getProblemState(C_XD c_xd) {
      if (!hasWork(c_xd)) {
         return 0;
      } else {
         this.loadProblem(c_xd);
         if (this.checkProblem()) {
            return 2;
         } else {
            return this.proofMissing ? 3 : 1;
         }
      }
   }

   static String getProblemStatement(String s) {
      return getProblemStatement(new C_XD(s));
   }

   static String getProblemStatement(C_XD c_xd) {
      return c_xd.m1483(c_xd.m1478("-+"));
   }

   static int countProblemLines(String s) {
      return countProblemLines(new C_XD(s));
   }

   static int countProblemLines(C_XD c_xd) {
      int i = c_xd.m1482();
      int j = 0;
      int k = 0;

      for (int l = 0; l < i; l++) {
         char c0 = c_xd.m1474(l);
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

   static String getWork(C_XD c_xd) {
      return c_xd.m1484("-+<#=");
   }

   static String getProblemRuleProven(String s) {
      return getProblemRuleProven(new C_XD(s));
   }

   static String getProblemRuleProven(C_XD c_xd) {
      return c_xd.m1483(c_xd.m1475('p'));
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
            if (c_md.f1079 && !c_md.m1777(LogicProgram.f533).equals(c_md.f1076)) {
               System.out.println("Could not digest file: derwork.txt");
               if (!C_u_C.m2101("indigestion", "instructor")) {
                  LogicProgram.m971("not003", "derwork.txt");
                  return false;
               }
            }

            problems = c_md;
            c_md.m1100();
            C_EE.f297 = C_f_F.m1816("derwork.txt", problems);
            C_f_F.m1815(exercises, C_EE.f297);
            return true;
         }
      }
   }

   static C_MD readWork() {
      if (!LogicProgram.m976()) {
         return null;
      } else {
         C_MD c_md = new C_MD();
         C_XB c_xb = LogicProgram.m1062("derwork.txt", true);
         if (!LogicProgram.f584 || c_xb instanceof C_b_D) {
            if (c_xb == null) {
               LogicProgram.m971("not001", "derwork.txt");
               return null;
            }

            if (c_xb instanceof C_b_D) {
               c_md.f1079 = true;
            }

            if (!readProblems(c_xb, c_md, false)) {
               LogicProgram.m971("not002", "derwork.txt");
               return null;
            }
         }

         if (!(c_xb instanceof C_b_D)) {
            c_xb = LogicProgram.m1065("derwork.txt", false);
            if (c_xb != null && !mergeProblems(c_xb, c_md, false)) {
               LogicProgram.m971("not002", "derwork.txt");
               return null;
            }
         }

         return c_md;
      }
   }

   static boolean hasWork(C_XD c_xd) {
      return countProblemLines(c_xd) > 1;
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

      return C_U.readProblems(reader, c_md, flag, flag1);
   }

   void setupPrintProblem(Dimension dimension) {
      this.setWidths(true, this.fontSize, dimension.width);
      this.setColors(this.colors);
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
         C_f_F c_f_f = problems.m1779(aint[j]);
         int k = c_f_f.f1120;
         C_XD c_xd = new C_XD(c_f_f.f1119);
         String s = c_xd.m1494();
         String s1 = getExerciseTitle(s);
         if (!printIncorrect || k == 1 || k == 3) {
            boolean flag = LogicProgram.m1060(noPrintCheck, s1);
            String s2 = getProblemStatement(c_xd);
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
            LogicProgram.m1082("derdata.txt", "R", c_f_f.f1119);
         }
      }

      return vector;
   }

   static Vector getPrintProblems(int[] aint, Dimension dimension) {
      Vector vector = new Vector(aint.length);

      for (int i = 0; i < aint.length; i++) {
         C_f_F c_f_f = problems.m1779(aint[i]);
         String s = getExerciseTitle(C_XD.m1493(c_f_f.f1119));
         if (LogicProgram.m1060(logPrint, s)) {
            LogicProgram.m1082("derdata.txt", "P", c_f_f.f1119);
         }

         if (!printIncorrect || c_f_f.f1120 == 1 || c_f_f.f1120 == 3) {
            LPDerivation lpderivation = new LPDerivation(true);
            lpderivation.setupFrame("Logic Program: Print");
            lpderivation.loadProblem(c_f_f.f1119);
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
      C_VC c_vc = new C_VC(this.problem.m7(true), true);
      this.conclusion = c_vc.f828;
      this.premises = c_vc.f827;
      String s = c_vc.m1384();
      this.problem.m13();
      if (s != null) {
         this.problem.m12("dererr059", C_H.m666("parser error", s));
         this.problem.f917.f333 = false;
      } else {
         this.problem.f917.f333 = true;
      }
   }

   boolean isPremise(C_RF c_rf) {
      int i = this.premises.length;
      if (c_rf == null) {
         return false;
      } else {
         for (int j = 0; j < i; j++) {
            if (c_rf.m1235(this.premises[j])) {
               return true;
            }
         }

         return false;
      }
   }

   C_RF[] getPremises() {
      return this.premises;
   }

   boolean isConclusion(C_RF c_rf) {
      return c_rf == null ? false : c_rf.m1235(this.conclusion);
   }

   static class C__A extends C_U.C__A {
      C__A(C_x_A c_x_a, Rectangle rectangle, String s) {
         super(c_x_a, rectangle, s);
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
