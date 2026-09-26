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

class LPTruthAnalysis extends C_U {
   C_LB scrollPanel;
   C_k_E problem;
   int fontSize = LogicProgram.f539;
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
   static C_BE noCheck = null;
   static C_BE noTreeErr = null;
   static C_BE noTableErr = null;
   static C_BE noSetupErr = null;
   static C_BE noPrintCheck = null;
   static C_BE noPrintTreeErr = null;
   static C_BE noPrintTableErr = null;
   static C_BE noPrintSetupErr = null;
   static C_BE noCheckMess = null;
   static C_BE noPrint = null;
   static C_BE monoProbs = null;
   static C_BE addToDB = null;
   static C_BE updateDB = null;
   static C_BE doAllNodes = null;
   static C_BE doAllRows = null;
   static C_BE doAllWffs = null;
   static C_BE doSetUp = null;
   static C_BE logPrint = null;
   static C_BE logSubmit = null;
   static C_BE needPrint = null;
   static C_BE needSubmit = null;
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
   boolean noBeep = true;
   int errorCount;
   long workTime;
   long loadTime;
   String lastUserProblem;
   Hashtable probOptions;
   JScrollPane scroller;
   static int moduleIndex = 5;

   static boolean getExercises() {
      if (!C_FE.m410()) {
         LogicProgram.m971("not001", "the truth analysis messages file");
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

   static C_O readExercises() {
      return readExercises(LogicProgram.f584, false, false);
   }

   static C_O readExercises(boolean flag, boolean flag1, boolean flag2) {
      C_O c_o = new C_O();
      if (!flag) {
         C_XB c_xb = LogicProgram.m1062("truwork.txt", false);
         if (c_xb == null) {
            LogicProgram.m971("not001", "the core Truth Table exercise file");
            return null;
         }

         if (!readProblems(c_xb, c_o, true)) {
            LogicProgram.m971("not002", "the core Truth Table exercise file");
            return null;
         }
      }

      if (!flag1) {
         C_XB c_xb1 = LogicProgram.m1065("truwork.txt", flag2);
         if (c_xb1 != null && (flag ? !readProblems(c_xb1, c_o, true) : !mergeProblems(c_xb1, c_o, true))) {
            LogicProgram.m971("not002", "the local Truth Table exercise file");
            return null;
         }
      }

      return c_o;
   }

   LPTruthAnalysis(boolean flag) {
      super(flag);
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

   static synchronized void startup(Rectangle rectangle, C_x_A c_x_a, String s) {
      if (startups == null) {
         startups = new Vector();
      }

      if (exercises != null || getExercises()) {
         LPTruthAnalysis.C__A lptruthanalysis$c__a = new LPTruthAnalysis.C__A(c_x_a, rectangle, s);
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
         ((C_U.C__A)startups.get(i - 1 - j)).m1310();
      }

      while (!startups.isEmpty()) {
         C_U.C__A c_u$c__a = (C_U.C__A)startups.remove(0);
         SwingUtilities.invokeLater(c_u$c__a);
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

   static void allocateTruModule(C_U.C__A c_u$c__a) {
      LPTruthAnalysis lptruthanalysis = new LPTruthAnalysis(false);
      instances.addElement(lptruthanalysis);
      if (c_u$c__a.f784 == null || newProblem == null) {
         lptruthanalysis.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lptruthanalysis.saveProblem();
         }
      }

      if (c_u$c__a.f784 != null) {
         lptruthanalysis.loadProblem(c_u$c__a.f784);
         c_u$c__a.f784 = null;
      }

      lptruthanalysis.setupFrame(LPInfo.programName + ": Truth Tables");
      lptruthanalysis.frame.setBounds(lptruthanalysis.fixModuleRect(c_u$c__a.f783));
      lptruthanalysis.frame.setVisible(true);
      lptruthanalysis.requestFocus();
      lptruthanalysis.noBeep = false;
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
      return s != null && s.toLowerCase().startsWith("trutb") ? "1" : "NULL";
   }

   void setupFrame(String s) {
      this.frame = new C_0E(s);
      this.setFontSize(this.fontSize);
      this.setColors(this.colors);
      this.frame.f27 = this;
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
            if (c_o.f1079 && !c_o.m1777(LogicProgram.f533).equals(c_o.f1076)) {
               System.out.println("Could not digest file: truwork.txt");
               if (!C_u_C.m2101("indigestion", "instructor")) {
                  LogicProgram.m971("not003", "truwork.txt");
                  return false;
               }
            }

            problems = c_o;
            C_f_C.f1111 = C_f_F.m1816("truwork.txt", problems);
            C_f_F.m1815(exercises, C_f_C.f1111);
            return true;
         }
      }
   }

   static C_O readWork() {
      if (!LogicProgram.m976()) {
         return null;
      } else {
         C_O c_o = new C_O();
         C_XB c_xb = LogicProgram.m1062("truwork.txt", true);
         if (!LogicProgram.f584 || c_xb instanceof C_b_D) {
            if (c_xb == null) {
               LogicProgram.m971("not001", "truwork.txt");
               return null;
            }

            if (c_xb instanceof C_b_D) {
               c_o.f1079 = true;
            }

            if (!readProblems(c_xb, c_o, false)) {
               LogicProgram.m971("not002", "truwork.txt");
               return null;
            }
         }

         if (!(c_xb instanceof C_b_D)) {
            c_xb = LogicProgram.m1065("truwork.txt", false);
            if (c_xb != null && !mergeProblems(c_xb, c_o, false)) {
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
      return hasWork(new C_XD(s));
   }

   static boolean hasWork(C_XD c_xd) {
      return c_xd.m1478("@*#&") != -1;
   }

   static String getWork(C_XD c_xd) {
      return c_xd.m1484("@*#&");
   }

   static String removeWork(C_XD c_xd) {
      return c_xd.m1484("$=%u!");
   }

   C_c_B checkFull() {
      C_XF c_xf = this.problem.f1201;
      C_GD[][] ac_gd = c_xf.f890;
      int i = c_xf.f892;
      if (this.completeSetup) {
         if (this.problem.f1203 == null) {
            return new C_c_B("truerr013", C_H.m666("summary", "Incomplete"));
         }

         if (!this.problem.f1219) {
            C_c_B c_c_b = this.problem.f1203.m1956();
            if (c_c_b.f427 != null) {
               return c_c_b;
            }

            return new C_c_B("truerr014", C_H.m666("summary", "Incomplete"));
         }
      }

      for (int j = 0; j < c_xf.f894; j++) {
         if (checkRowError(ac_gd[j])) {
            return new C_c_B("truerr001", C_H.m666("summary", "Incorrect"));
         }
      }

      if (!this.assumeTautology) {
         if (this.problem.f1215 == 0) {
            if (i != -1) {
               return new C_c_B("truerr004", C_H.m666("summary", "Incorrect"));
            }
         } else {
            if (this.problem.f1215 != 1) {
               return new C_c_B("truerr003", C_H.m666("summary", "Incomplete"));
            }

            if (i == -1) {
               return new C_c_B("truerr006", C_H.m666("summary", "Incomplete"));
            }
         }
      }

      if (this.assumeTautology || this.completeAllRows || this.problem.f1215 == 0) {
         for (int k = 0; k < c_xf.f894; k++) {
            if (!checkRowComplete(ac_gd[k]) && (this.completeAllWffs || !checkRowValid(ac_gd[k]))) {
               return new C_c_B("truerr002", C_H.m666("summary", "Incomplete"));
            }
         }
      }

      if (this.assumeTautology) {
         return new C_c_B(null, C_H.m666("summary", "Correct"));
      } else if (this.problem.f1215 == 0) {
         for (int l = 0; l < c_xf.f894; l++) {
            if (!checkRowValid(ac_gd[l])) {
               return new C_c_B("truerr005", C_H.m666("summary", "Incorrect"));
            }
         }

         return new C_c_B(null, C_H.m666("summary", "Correct"));
      } else if (!this.completeAllWffs && checkRowValid(ac_gd[i])) {
         return new C_c_B("truerr008", C_H.m666("summary", "Incorrect"));
      } else if (!this.completeAllRows && !checkRowComplete(ac_gd[i])) {
         return new C_c_B("truerr007", C_H.m666("summary", "Incomplete"));
      } else {
         return this.completeAllWffs && checkRowValid(ac_gd[i])
            ? new C_c_B("truerr008", C_H.m666("summary", "Incorrect"))
            : new C_c_B(null, C_H.m666("summary", "Correct"));
      }
   }

   boolean check() {
      return this.checkFull().f427 == null;
   }

   void checkProblem() {
      C_c_B c_c_b = this.checkFull();
      if (c_c_b.f428 != null) {
         String s = (String)c_c_b.f428.get("summary");
         this.titlePanel.m1825(s == null ? "" : s);
      }

      if (c_c_b.f427 != null && !this.checkMessagesDisabled) {
         C_UA.m1329(C_FE.m411(c_c_b.f427), c_c_b.f428, null, null);
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
      this.assumeTautology = this.probOptions != null && this.probOptions.containsKey("taut");
      this.titlePanel.m1827(c_xd.m1483(c_xd.m1475('!')));
      String s = c_xd.m1494();
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
      return C_U.readProblems(reader, c_o, flag, flag1);
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
      if (C_u_C.m2105("Truth Tables")) {
         int[] aint = getExerciseIndices();
         C_x_A c_x_a = new C_x_A(this);
         C_0A c_0a = C_KC.m835(c_x_a);
         if (c_0a != null) {
            if (C_w_A.m2148(this, null)) {
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
         int[] aint = C_w_A.m2142(this);
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
            c_0a.f6 = C_f_C.f1128[k];
            c_0a.f8 = s;
            c_0a.f9 = c_xd.m1494();
            c_0a.f10 = moduleAbbrs[moduleIndex];
            c_0a.f11 = c_xd.m1498();
            c_0a.f12 = c_xd.m1499();
            boolean flag = LogicProgram.m1060(logSubmit, getExerciseTitle(c_0a.f9));
            if (C_KC.m836(c_0a, c_x_a)) {
               vector.addElement(trimTitle(c_0a.f9));
               if (flag) {
                  LogicProgram.m1083("trudata.txt", "S", s, c_0a.m3());
               }
            } else {
               vector1.addElement(trimTitle(c_0a.f9));
               if (flag) {
                  LogicProgram.m1082("trudata.txt", "F", s);
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
         int[] aint = C_w_A.m2143(this);
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
            c_ld.f514 = assumeTautology(c_xd) ? "taut" : null;
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

   static boolean assumeTautology(C_XD c_xd) {
      int[] aint = c_xd.m1477('%');
      if (aint != null) {
         int i = aint.length;

         for (int j = 0; j < i; j++) {
            String s = c_xd.m1483(aint[j]);
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

      return C_XD.m1509(s);
   }

   static boolean saveProblems() {
      if (!LogicProgram.m976()) {
         return false;
      } else {
         try {
            writeProblems(problems, new FileWriter(new File(LogicProgram.f555, "truwork.txt")));
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
               C_f_C c_f_c = new C_f_C(C_XD.m1495(s, s2), false);
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
      return C_XD.m1500(s).equals(C_XD.m1500(s1)) ? null : s;
   }

   static String getProblemStatement(String s) {
      return getProblemStatement(new C_XD(s));
   }

   static String getProblemStatement(C_XD c_xd) {
      return c_xd.m1483(c_xd.m1478("="));
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
      readOptions(LogicProgram.m1062("options", false));
      readOptions(LogicProgram.m1065("options", false));
      logNeeds();
      if (needPrint != null && !needPrint.m402() || needSubmit != null && !needSubmit.m402()) {
         C_a_A c_a_a = new C_a_A(readWork());
         c_a_a.m1634(readExercises());
         C_z_C.m2215(hashtable, "trudata.txt", "P", needPrint, c_a_a);
         c_a_a.m1635();
         C_z_C.m2215(hashtable1, "trudata.txt", "S", needSubmit, c_a_a);
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
         this.problem.doLayout();
      }
   }

   @Override
   public Frame getFrame() {
      return this.frame;
   }

   void setFontSize(int i) {
      this.fontSize = i;
      this.font = LogicProgram.m1029(i);
      this.errorFont = LogicProgram.m1030(i, 0);
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
      C_XD c_xd = new C_XD(s);
      return !hasWork(c_xd) ? 0 : new LPTruthAnalysis(false).getProblemState(c_xd);
   }

   @Override
   int getProblemState(C_XD c_xd) {
      if (!hasWork(c_xd)) {
         return 0;
      } else {
         this.noBeep = true;
         this.problem.m1910(c_xd);
         return this.check() ? 2 : 1;
      }
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         C_XD c_xd = new C_XD(reader, true);
         String s = "";

         while (c_xd.m1469()) {
            String s1 = c_xd.m1494();
            if (s1 != null && s1.trim().equalsIgnoreCase("truth")) {
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
                        if (s3.equalsIgnoreCase("addToDB")) {
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
                        } else if (s3.equalsIgnoreCase("noTreeErr")) {
                           if (noTreeErr == null) {
                              noTreeErr = new C_BE();
                           }

                           noTreeErr.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noTableErr")) {
                           if (noTableErr == null) {
                              noTableErr = new C_BE();
                           }

                           noTableErr.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noSetupErr")) {
                           if (noSetupErr == null) {
                              noSetupErr = new C_BE();
                           }

                           noSetupErr.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintCheck")) {
                           if (noPrintCheck == null) {
                              noPrintCheck = new C_BE();
                           }

                           noPrintCheck.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintTreeErr")) {
                           if (noPrintTreeErr == null) {
                              noPrintTreeErr = new C_BE();
                           }

                           noPrintTreeErr.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintTableErr")) {
                           if (noPrintTableErr == null) {
                              noPrintTableErr = new C_BE();
                           }

                           noPrintTableErr.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintSetupErr")) {
                           if (noPrintSetupErr == null) {
                              noPrintSetupErr = new C_BE();
                           }

                           noPrintSetupErr.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noCheckMess")) {
                           if (noCheckMess == null) {
                              noCheckMess = new C_BE();
                           }

                           noCheckMess.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrint")) {
                           if (noPrint == null) {
                              noPrint = new C_BE();
                           }

                           noPrint.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new C_BE();
                           }

                           monoProbs.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("doAllRows")) {
                           if (doAllRows == null) {
                              doAllRows = new C_BE();
                           }

                           doAllRows.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("doAllWffs")) {
                           if (doAllWffs == null) {
                              doAllWffs = new C_BE();
                           }

                           doAllWffs.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("doSetUp")) {
                           if (doSetUp == null) {
                              doSetUp = new C_BE();
                           }

                           doSetUp.m395(new C_BE(s2.substring(k + 1)).m403(s));
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
                        } else if (s3.equalsIgnoreCase("doAllNodes")) {
                           if (doAllNodes == null) {
                              doAllNodes = new C_BE();
                           }

                           doAllNodes.m395(new C_BE(s2.substring(k + 1)).m403(s));
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
      this.problem.m1910(c_xd);
      this.errorCount = c_xd.m1498();
      this.workTime = c_xd.m1499();
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
      C_VC c_vc = new C_VC(s);
      String s1 = c_vc.m1384();
      if (s1 != null) {
         Hashtable hashtable = C_H.m666("expression", LogicProgram.m995(s1, maggie, kaplan));
         C_UA.m1329(C_FE.m411("truerr009"), hashtable, null, null);
      } else if (c_vc.m1385() != 0) {
         C_UA.m1329(C_FE.m411("truerr010"), null, null, null);
      } else {
         String s2 = C_XD.m1509(C_XD.m1508(C_VC.m1383(s), '='));
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
         C_f_F c_f_f = problems.m1779(aint[j]);
         int k = c_f_f.f1120;
         C_XD c_xd = new C_XD(c_f_f.f1119);
         String s = c_xd.m1494();
         if (printIncorrect && k != 1) {
            if (LogicProgram.m1060(logPrint, getExerciseTitle(s))) {
               LogicProgram.m1082("trudata.txt", flag ? "R" : "P", c_f_f.f1119);
            }
         } else {
            String s1 = getProblemStatement(c_xd);
            s1 = LogicProgram.m995(s1, maggie, kaplan);
            LPTruthAnalysis lptruthanalysis = new LPTruthAnalysis(true);
            lptruthanalysis.loadProblem(c_f_f.f1119);
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

   static class C__A extends C_U.C__A {
      C__A(C_x_A c_x_a, Rectangle rectangle, String s) {
         super(c_x_a, rectangle, s);
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
