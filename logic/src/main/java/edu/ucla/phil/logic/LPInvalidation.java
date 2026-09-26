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

class LPInvalidation extends C_U implements C_n_A {
   static final String workFileName = "invwork.txt";
   static final String logFileName = "invdata.txt";
   static final String digestVersKey = "invDigestVers";
   static Class messageClass = C_LA.class;
   static C_PC exercises = null;
   static C_PC problems = null;
   static Vector instances = new Vector();
   static C_c_C printQueue = new C_c_C("Invalidity");
   static C_BE addToDB = null;
   static C_BE updateDB = null;
   static C_BE noPrint = null;
   static C_BE noCheck = null;
   static C_BE noPrintCheck = null;
   static C_BE monoProbs = null;
   static C_BE logPrint = null;
   static C_BE logSubmit = null;
   static C_BE needPrint = null;
   static C_BE needSubmit = null;
   static C_BE noExpand = null;
   static C_BE fullExpand = null;
   static boolean noUser = false;
   static boolean submitExam = false;
   static boolean printIncorrect = false;
   static boolean restating = false;
   static String newProblem = null;
   boolean startingUp = false;
   static Vector startups;
   static String[] kaplan = LogicProgram.f596;
   boolean checkDisabled;
   boolean dontChange;
   boolean expandOff;
   boolean expandAll;
   int errorCount;
   long workTime;
   long loadTime;
   JScrollPane scroller;
   C_LB scrollPanel;
   C_SA problemPanel;
   String title;
   String unparsed;
   String lastUserProblem;
   C_VC statement;
   Hashtable probOptions = null;
   Vector symbols;
   int size;
   int nonidentical;
   int fontSize = LogicProgram.f539;
   Font font;
   static int moduleIndex = 1;

   static boolean getExercises() {
      if (!C_LA.m410()) {
         LogicProgram.m971("not001", "the invalidity messages file");
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

   static C_PC readExercises() {
      return readExercises(LogicProgram.f584, false, false);
   }

   static C_PC readExercises(boolean flag, boolean flag1, boolean flag2) {
      C_PC c_pc = new C_PC();
      if (!flag) {
         C_XB c_xb = LogicProgram.m1062("invwork.txt", false);
         if (c_xb == null) {
            LogicProgram.m971("not001", "the core Invalidity exercise file");
            return null;
         }

         if (!readProblems(c_xb, c_pc, true)) {
            LogicProgram.m971("not002", "the core Invalidity exercise file");
            return null;
         }
      }

      if (!flag1) {
         C_XB c_xb1 = LogicProgram.m1065("invwork.txt", flag2);
         if (c_xb1 != null && (flag ? !readProblems(c_xb1, c_pc, true) : !mergeProblems(c_xb1, c_pc, true))) {
            LogicProgram.m971("not002", "the local Invalidity exercise file");
            return null;
         }
      }

      return c_pc;
   }

   LPInvalidation(boolean flag) {
      super(flag);
      this.reset();
      this.setLayout(new BorderLayout());
      this.add(this.titlePanel, "North");
      this.scroller = new JScrollPane();
      this.add(this.scroller, "Center");
      this.scroller.getVerticalScrollBar().setUnitIncrement(22);
      this.scroller.setViewportView(this.scrollPanel = new C_LB());
      this.scrollPanel.add(this.problemPanel = new C_SA(this), "Center");
      this.add(C_IB.m701(this), "South");
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
         C_PC c_pc = readWork();
         if (c_pc == null) {
            return false;
         } else {
            if (c_pc.f1079 && !c_pc.m1777(LogicProgram.f533).equals(c_pc.f1076)) {
               System.out.println("Could not digest file: invwork.txt");
               if (!C_u_C.m2101("indigestion", "instructor")) {
                  LogicProgram.m971("not003", "invwork.txt");
                  return false;
               }
            }

            problems = c_pc;
            C_u_D.f1398 = C_f_F.m1816("invwork.txt", problems);
            C_f_F.m1815(exercises, C_u_D.f1398);
            return true;
         }
      }
   }

   static C_PC readWork() {
      if (!LogicProgram.m976()) {
         return null;
      } else {
         C_PC c_pc = new C_PC();
         C_XB c_xb = LogicProgram.m1062("invwork.txt", true);
         if (!LogicProgram.f584 || c_xb instanceof C_b_D) {
            if (c_xb == null) {
               LogicProgram.m971("not001", "invwork.txt");
               return null;
            }

            if (c_xb instanceof C_b_D) {
               c_pc.f1079 = true;
            }

            if (!readProblems(c_xb, c_pc, false)) {
               LogicProgram.m971("not002", "invwork.txt");
               return null;
            }
         }

         if (!(c_xb instanceof C_b_D)) {
            c_xb = LogicProgram.m1065("invwork.txt", false);
            if (c_xb != null && !mergeProblems(c_xb, c_pc, false)) {
               LogicProgram.m971("not002", "invwork.txt");
               return null;
            }
         }

         return c_pc;
      }
   }

   static synchronized void startup(Rectangle rectangle, C_x_A c_x_a, String s) {
      if (startups == null) {
         startups = new Vector();
      }

      if (exercises != null || getExercises()) {
         LPInvalidation.C__A lpinvalidation$c__a = new LPInvalidation.C__A(c_x_a, rectangle, s);
         startups.add(lpinvalidation$c__a);
         if (startups.size() <= 1) {
            if (problems == null) {
               if (!getProblems()) {
                  lpinvalidation$c__a.m1310();
                  startups.remove(lpinvalidation$c__a);
                  return;
               }

               if (problems.m1773()) {
                  saveProblems();
               }

               problems.m1099(lpinvalidation$c__a);
            } else {
               lpinvalidation$c__a.m959();
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
      C_IB c_ib = C_IB.m701(this);
      Dimension dimension = c_ib.getPreferredSize();
      if (rectangle.width < dimension.width) {
         rectangle.width = dimension.width;
      }

      return rectangle;
   }

   static void allocateInvModule(C_U.C__A c_u$c__a) {
      LPInvalidation lpinvalidation = new LPInvalidation(false);
      instances.addElement(lpinvalidation);
      if (c_u$c__a.f784 == null || newProblem == null) {
         lpinvalidation.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lpinvalidation.saveProblem();
         }
      }

      if (c_u$c__a.f784 != null) {
         lpinvalidation.loadProblem(c_u$c__a.f784);
         c_u$c__a.f784 = null;
      }

      lpinvalidation.setupFrame(LPInfo.programName + ": Invalidity");
      lpinvalidation.frame.setBounds(lpinvalidation.fixModuleRect(c_u$c__a.f783));
      lpinvalidation.frame.setVisible(true);
      lpinvalidation.requestFocus();
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
      return s != null && s.toLowerCase().startsWith("inval") ? "1" : "NULL";
   }

   void setupFrame(String s) {
      this.frame = new C_0E(s);
      this.setFontSize(this.fontSize);
      this.setColors(this.colors);
      this.frame.f27 = this;
      this.frame.add(this, "Center");
   }

   @Override
   public boolean shutdown(boolean flag) {
      if (!flag && !C_CE.m431(this, null)) {
         return false;
      } else {
         this.reset();
         synchronized (moduleClasses[1]) {
            instances.removeElement(this);
            if (instances.isEmpty()) {
               C_l_B.m1924(printQueue);
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
      return C_CE.m431(this, null);
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
      readOptions(LogicProgram.m1062("options", false));
      readOptions(LogicProgram.m1065("options", false));
      logNeeds();
      if (needPrint != null && !needPrint.m402() || needSubmit != null && !needSubmit.m402()) {
         C_a_A c_a_a = new C_a_A(readWork());
         c_a_a.m1634(readExercises());
         C_z_C.m2215(hashtable, "invdata.txt", "P", needPrint, c_a_a);
         c_a_a.m1635();
         C_z_C.m2215(hashtable1, "invdata.txt", "S", needSubmit, c_a_a);
      }

      resetOptions();
      return true;
   }

   static Vector getChangedProblems() {
      C_a_A c_a_a = new C_a_A(readWork());
      Hashtable hashtable = LogicProgram.m1084("invdata.txt", "S", needSubmit, c_a_a);
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
      if (this.problemPanel != null) {
         this.problemPanel.m1279();
      }
   }

   @Override
   public Frame getFrame() {
      return this.frame;
   }

   void setFontSize(int i) {
      this.fontSize = i;
      this.font = LogicProgram.m1029(i);
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
         this.problemPanel.f752.setText("");
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
      this.loadProblem(new C_XD(s));
   }

   void loadProblem(C_XD c_xd) {
      this.reset();
      this.loadExerciseInfo(c_xd);
      this.title = c_xd.m1494();
      this.unparsed = c_xd.m1483(c_xd.m1475('?'));
      this.statement = C_VC.m1382(this.unparsed);
      Integer integer = c_xd.m1485(c_xd.m1475('#'));
      this.symbols = parseSymbolList(c_xd.m1483(c_xd.m1475('=')));
      this.setSize(integer == null ? 0 : integer);
      String s = c_xd.m1483(c_xd.m1475('&'));
      this.problemPanel.m1278();
      if (s != null) {
         try {
            this.problemPanel.f752.setText(LogicProgram.m995(new C_UB(new C_o_B(s).m2000()).toString(), LogicProgram.f597, LogicProgram.f596));
         } catch (IllegalArgumentException illegalargumentexception) {
         }
      }

      Integer integer1 = c_xd.m1485(c_xd.m1475('e'));
      this.errorCount = integer1 == null ? 0 : integer1;
      this.workTime = c_xd.m1499();
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
      C_VC c_vc = new C_VC(s);
      String s1 = c_vc.m1384();
      if (s1 != null) {
         Hashtable hashtable = C_H.m666("expression", s1);
         C_UA.m1329(C_LA.m411("inverr001"), hashtable, null, null);
      } else if (!c_vc.f829 && c_vc.m1385() == 0) {
         this.loadProblem(C_XD.m1509(C_XD.m1508(C_VC.m1383(s), '?')));
      } else {
         C_UA.m1329(C_LA.m411("inverr002"), null, null, null);
      }
   }

   static Vector parseSymbolList(String s) {
      if (s == null) {
         return null;
      } else {
         Vector vector = new Vector();

         while (s != null) {
            int i = s.indexOf(46);
            C_IE c_ie;
            if (i == -1) {
               c_ie = C_IE.m706(s);
               s = null;
            } else {
               c_ie = C_IE.m706(s.substring(0, i));
               s = s.substring(i + 1);
            }

            if (c_ie != null) {
               vector.addElement(c_ie);
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
            C_IE c_ie = (C_IE)enumeration.nextElement();
            c_ie.m707(i);
         }
      }

      this.problemPanel.m1278();
   }

   void removeWork() {
      this.size = 0;
      if (this.symbols != null) {
         Enumeration enumeration = this.symbols.elements();

         while (enumeration.hasMoreElements()) {
            C_IE c_ie = (C_IE)enumeration.nextElement();
            c_ie.m705();
         }
      }

      this.problemPanel.m1278();
   }

   static String removeWork(C_XD c_xd) {
      return c_xd.m1484("$?%u!");
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
      if (C_u_C.m2105("Invalidity")) {
         int[] aint = getExerciseIndices();
         C_x_A c_x_a = new C_x_A(this);
         C_0A c_0a = C_KC.m835(c_x_a);
         if (c_0a != null) {
            if (C_CE.m431(this, null)) {
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
         int[] aint = C_CE.m437(this);
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
            c_0a.f6 = C_u_D.f1128[k];
            c_0a.f8 = s;
            c_0a.f9 = c_xd.m1494();
            c_0a.f10 = moduleAbbrs[moduleIndex];
            c_0a.f11 = c_xd.m1498();
            c_0a.f12 = c_xd.m1499();
            boolean flag = LogicProgram.m1060(logSubmit, getExerciseTitle(c_0a.f9));
            if (C_KC.m836(c_0a, c_x_a)) {
               vector.addElement(trimTitle(c_0a.f9));
               if (flag) {
                  LogicProgram.m1083("invdata.txt", "S", s, c_0a.m3());
               }
            } else {
               vector1.addElement(trimTitle(c_0a.f9));
               if (flag) {
                  LogicProgram.m1082("invdata.txt", "F", s);
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
         int[] aint = C_CE.m438(this);
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

   String saveProblem() {
      String s = "";
      s = s + C_XD.m1508(this.title, '$');
      s = s + C_XD.m1508(this.unparsed, '?');
      if (this.size != 0) {
         s = s + this.size + "`#";
      }

      int i = this.symbols == null ? 0 : this.symbols.size();
      String s1 = i == 0 ? null : "";

      for (int j = 0; j < i; j++) {
         s1 = s1 + (j == 0 ? "" : ".") + ((C_IE)this.symbols.elementAt(j)).m709();
      }

      s = s + C_XD.m1508(s1, '=');
      if (this.errorCount != 0) {
         s = s + this.errorCount + "`e";
      }

      if (this.updateWorkTime() != 0L) {
         s = s + this.workTime + "`t";
      }

      String s2 = new C_o_B(new C_UB(this.problemPanel.f752.getText()).m1337()).toString();
      if (!s2.equals("")) {
         s = s + C_XD.m1508(s2, '&');
      }

      return C_XD.m1509(s);
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
      this.checkDisabled = LogicProgram.m1060(this.forPrint ? noPrintCheck : noCheck, s);
      this.expandOff = LogicProgram.m1060(noExpand, s);
      this.expandAll = LogicProgram.m1060(fullExpand, s);
   }

   static int getProblemState(String s) {
      return new LPInvalidation(false).getProblemState(new C_XD(s));
   }

   @Override
   int getProblemState(C_XD c_xd) {
      if (!hasWork(c_xd)) {
         return 0;
      } else {
         this.loadProblem(c_xd);
         return this.checkProblem() ? 2 : 1;
      }
   }

   static boolean readProblems(Reader reader, C_PC c_pc, boolean flag) {
      return readProblems(reader, c_pc, flag, false);
   }

   static boolean mergeProblems(Reader reader, C_PC c_pc, boolean flag) {
      return readProblems(reader, c_pc, flag, true);
   }

   static boolean readProblems(Reader reader, C_PC c_pc, boolean flag, boolean flag1) {
      return C_U.readProblems(reader, c_pc, flag, flag1);
   }

   static String getProblemStatement(String s) {
      return getProblemStatement(new C_XD(s));
   }

   static String getProblemStatement(C_XD c_xd) {
      return c_xd.m1483(c_xd.m1475('?'));
   }

   static boolean hasWork(String s) {
      return hasWork(new C_XD(s));
   }

   static boolean hasWork(C_XD c_xd) {
      return c_xd.m1475('#') != -1 || c_xd.m1475('&') != -1;
   }

   static String getWork(C_XD c_xd) {
      return c_xd.m1484("#=&");
   }

   C_IE getSymbol(String s, int i) {
      return this.getSymbol(new C_IE(s, i));
   }

   C_IE getSymbol(C_IE c_ie) {
      if (this.symbols == null) {
         return null;
      } else {
         int i = this.symbols.indexOf(c_ie);
         return i == -1 ? null : (C_IE)this.symbols.elementAt(i);
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
         int i = this.statement.f827.length;

         for (int j = 0; j < i; j++) {
            Object object = this.evaluate(closure(this.statement.f827[j]));
            if (!this.bTest(object) || !this.bValue(object)) {
               return false;
            }
         }

         Object object1 = this.evaluate(closure(this.statement.f828));
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
         object = "";
         int i = this.statement.f827.length;

         for (int j = 0; j < i; j++) {
            Object object1 = this.evaluate(closure(this.statement.f827[j]));
            object = object + (j == 0 ? "" : ".") + (this.bTest(object1) ? (this.bValue(object1) ? "T" : "F") : "N");
         }

         Object object2 = this.evaluate(closure(this.statement.f828));
         object = object + ".:" + (this.bTest(object2) ? (this.bValue(object2) ? "T" : "F") : "N");
      }

      this.titlePanel.m1825(LogicProgram.m995(object + " ", maggie, kaplan));
   }

   Object evaluate(C_RF c_rf) {
      return this.evaluate(c_rf, new C_JD());
   }

   Object evaluate(C_RF c_rf, C_JD c_jd) {
      if (c_rf == null) {
         return null;
      } else {
         int i = c_rf.m1216();
         String s = c_rf.m1214();
         Object object = c_jd.m725(s, 0);
         if (object != null) {
            return object;
         } else {
            int k = this.symbols.size();

            for (int j = 0; j < k; j++) {
               C_IE c_ie = (C_IE)this.symbols.elementAt(j);
               if (c_ie.f422.equals(s) && c_ie.f423 == i) {
                  int[] aint = i == 0 ? null : new int[i];

                  for (int l = 0; l < i; l++) {
                     object = this.evaluate(c_rf.m1217(l), c_jd);
                     if (!this.iTest(object)) {
                        return null;
                     }

                     aint[l] = this.iValue(object);
                  }

                  return c_ie.m708(aint);
               }
            }

            if (s.equals("@")) {
               if (i < 2) {
                  return null;
               } else {
                  object = c_rf.m1217(0).f739;

                  for (int k1 = 0; k1 < this.size; k1++) {
                     c_jd.m723(object, new Integer(k1));
                     Object object4 = this.evaluate(c_rf.m1217(1), c_jd);
                     c_jd.m724(object);
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
                  object = c_rf.m1217(0).f739;

                  for (int j1 = 0; j1 < this.size; j1++) {
                     c_jd.m723(object, new Integer(j1));
                     Object object3 = this.evaluate(c_rf.m1217(1), c_jd);
                     c_jd.m724(object);
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
                  object = c_rf.m1217(0).f739;

                  for (int i1 = 0; i1 < this.size; i1++) {
                     c_jd.m723(object, new Integer(i1));
                     Object object2 = this.evaluate(c_rf.m1217(1), c_jd);
                     c_jd.m724(object);
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
               object = i > 0 ? this.evaluate(c_rf.m1217(0), c_jd) : null;
               Object object1 = i > 1 ? this.evaluate(c_rf.m1217(1), c_jd) : null;
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

   static C_RF closure(C_RF c_rf) {
      return c_rf == null ? null : c_rf.m1275();
   }

   C_h_D getSymbolList() {
      C_h_D c_h_d = new C_h_D();
      if (this.statement == null) {
         return c_h_d;
      } else {
         int i = this.statement.f827.length;

         for (int j = 0; j < i; j++) {
            c_h_d.m1839(this.statement.f827[j]);
         }

         c_h_d.m1839(this.statement.f828);
         c_h_d.m1840(this);
         return c_h_d;
      }
   }

   void openDerivation(boolean flag, C_x_A c_x_a) {
      if ((!flag || this.size != 0) && this.statement != null) {
         String s = "";
         if (flag) {
            String s1 = LogicProgram.m1020(0);
            String s2 = LogicProgram.m1019(0);
            if (this.size == 1) {
               s = "@" + s1 + s1 + "=" + s2 + "0";
            } else {
               s = "@" + s1 + "(" + s1 + "=" + s2 + "0";

               for (int i = 1; i < this.size; i++) {
                  s = s + "|" + s1 + "=" + s2 + i;
               }

               s = s + ")";
            }
         }

         int j = this.statement.f825.length;

         for (int k = 0; k < j; k++) {
            s = s + (k == 0 && !flag ? "" : " . ") + this.statement.f825[k];
         }

         s = s + " .: " + this.statement.f826 + "`-`=";
         LPDerivation.startup(C_z_C.m2216(), c_x_a, s);
      }
   }

   void openTruthAnalysis(boolean flag, C_x_A c_x_a) {
      String s = "";
      String s1 = LogicProgram.m1019(0);
      if (flag) {
         if (this.size == 0 || this.statement == null) {
            return;
         }

         int j = this.statement.f825.length;

         for (int i = 0; i < j; i++) {
            s = s + (i != 0 ? " . " : "") + this.statement.f827[i].m1250(this.size, s1);
         }

         s = s + " .: " + this.statement.f828.m1250(this.size, s1) + "`=";
      } else {
         s = LogicProgram.m995(this.problemPanel.f752.getSelectedText(), kaplan, maggie) + "`=";
      }

      LPTruthAnalysis.startup(C_z_C.m2216(), c_x_a, s);
   }

   static C_RF parse(String s) {
      try {
         return LogicProgram.m1006(s);
      } catch (C_k_B c_k_b) {
         return null;
      }
   }

   void expand(String s, boolean flag) {
      if (this.problemPanel != null && this.problemPanel.f752 != null) {
         C_IF c_if = this.problemPanel.f752;
         if (this.size == 0) {
            C_UA.m1329(C_LA.m411("invnot002"), null, null, null);
            c_if.requestFocus();
         } else {
            String s1 = c_if.getSelectedText();
            String s2 = LogicProgram.m995(s1, kaplan, maggie);
            C_RF c_rf = parse(s2);
            if (c_rf == null) {
               Hashtable hashtable = C_H.m666("expression", s2);
               C_UA.m1329(C_LA.m411("inverr001"), hashtable, null, null);
               c_if.requestFocus();
            } else {
               boolean flag1 = true;
               if (this.size == 1) {
                  C_RF c_rf1 = c_rf.m1217(1);
                  flag1 = !(c_rf1 instanceof C_o_A) && !(c_rf1 instanceof C_WE) && c_rf1.m1216() > 1;
               }

               if (!flag && !(c_rf instanceof C_o_A)) {
                  Hashtable hashtable1 = C_H.m666("expression", s2);
                  C_UA.m1329(C_LA.m411("inverr003"), hashtable1, null, null);
                  c_if.requestFocus();
               } else {
                  c_rf = c_rf.m1251(this.size, s, flag);
                  s2 = LogicProgram.m995(c_rf.toString(), maggie, kaplan);
                  if (flag1) {
                     s2 = "(" + s2 + ")";
                  }

                  c_if.replaceSelection(s2);
                  c_if.requestFocus();
               }
            }
         }
      }
   }

   void copyStatement() {
      C_IF c_if = this.problemPanel.f752;
      int i = c_if.getSelectionStart();
      int j = c_if.getSelectionEnd();
      if (i >= j) {
         i = j = c_if.getCaretPosition();
      }

      String s = c_if.getText();
      String s1 = this.titlePanel.m1824();
      c_if.setText(s.substring(0, i) + s1 + s.substring(j));
      j = i + s1.length();
      c_if.select(i, j);
      c_if.requestFocus();
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         C_XD c_xd = new C_XD(reader, true);
         String s = "";

         while (c_xd.m1469()) {
            String s1 = c_xd.m1494();
            if (s1 != null && s1.trim().equalsIgnoreCase("invalidation")) {
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
                        } else if (s3.equalsIgnoreCase("noPrint")) {
                           if (noPrint == null) {
                              noPrint = new C_BE();
                           }

                           noPrint.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noCheck")) {
                           if (noCheck == null) {
                              noCheck = new C_BE();
                           }

                           noCheck.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noPrintCheck")) {
                           if (noPrintCheck == null) {
                              noPrintCheck = new C_BE();
                           }

                           noPrintCheck.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new C_BE();
                           }

                           monoProbs.m395(new C_BE(s2.substring(k + 1)).m403(s));
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
                        } else if (s3.equalsIgnoreCase("noExpand")) {
                           if (noExpand == null) {
                              noExpand = new C_BE();
                           }

                           noExpand.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("fullExpand")) {
                           if (fullExpand == null) {
                              fullExpand = new C_BE();
                           }

                           fullExpand.m395(new C_BE(s2.substring(k + 1)).m403(s));
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

   void newProblem() {
      this.loadProblem(newProblem);
   }

   void setProblemTitle(String s) {
      this.title = s;
      this.titlePanel.m1821(trimTitle(s));
   }

   static String trimTitle(String s) {
      if (s == null) {
         return null;
      } else {
         return isExercise(s) ? LogicProgram.m1000(s) : s.trim();
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

            if (problems.m1773()) {
               saveProblems();
            }
         }

         return true;
      }
   }

   static boolean saveProblems() {
      if (!LogicProgram.m976()) {
         return false;
      } else {
         try {
            writeProblems(problems, new FileWriter(new File(LogicProgram.f555, "invwork.txt")));
            return true;
         } catch (IOException ioexception) {
            LogicProgram.m971("not004", "invwork.txt");
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
               String s2 = C_CE.m432(flag ? this.title : null);
               if (s2 == null) {
                  return false;
               }

               this.setProblemTitle(s2);
               C_u_D c_u_d = new C_u_D(C_XD.m1495(s, s2), false);
               this.problemIndex = problems.m1771(c_u_d, false);
               this.problemIndex = this.problemIndex == -1 ? problems.size() : this.problemIndex + 1;
               problems.insertElementAt(c_u_d, this.problemIndex);
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

   String getChangedProblem() {
      String s = this.saveProblem();
      String s1 = this.problemIndex == -1 ? newProblem : problems.m1778(this.problemIndex);
      return C_XD.m1500(s).equals(C_XD.m1500(s1)) ? null : s;
   }

   static Vector getStatements(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         C_f_F c_f_f = problems.m1779(aint[j]);
         C_XD c_xd = new C_XD(c_f_f.f1119);
         String s = c_xd.m1494();
         String s1 = getProblemStatement(c_xd);
         C_TA c_ta = new C_TA();
         c_ta.setLayout(new C_u_(null, 1, new int[]{dimension.width}));
         C_NC c_nc;
         c_ta.add(c_nc = new C_NC(LogicProgram.m1004("\\l" + s + ": " + s1)));
         c_nc.setLineWrap(true);
         c_nc.setWrapStyleWord(true);
         vector.add(c_ta);
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
         if (!printIncorrect || k == 1) {
            String s1 = getProblemStatement(c_xd);
            String s2 = exercises.m1780(s);
            LPInvalidation lpinvalidation = new LPInvalidation(true);
            lpinvalidation.loadProblem(c_f_f.f1119);
            C_TA c_ta = new C_TA();
            c_ta.setLayout(new C_u_(null, 2, new int[]{20, dimension.width - 20}));
            c_ta.add(new C_f_E(lpinvalidation.checkDisabled ? " " : C_u_D.f1128[k]));
            C_NC c_nc;
            c_ta.add(c_nc = new C_NC(LogicProgram.m1004("\\l" + trimTitle(s) + ": " + s1)));
            c_nc.setLineWrap(true);
            c_nc.setWrapStyleWord(true);
            vector.add(c_ta);
         }

         if (LogicProgram.m1060(logPrint, getExerciseTitle(s))) {
            LogicProgram.m1082("invdata.txt", "R", c_f_f.f1119);
         }
      }

      return vector;
   }

   static Vector getPrintProblems(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         C_f_F c_f_f = problems.m1779(aint[j]);
         int k = c_f_f.f1120;
         C_XD c_xd = new C_XD(c_f_f.f1119);
         String s = c_xd.m1494();
         if (!printIncorrect || k == 1 || k != 2) {
            String s1 = getProblemStatement(c_xd);
            LPInvalidation lpinvalidation = new LPInvalidation(true);
            lpinvalidation.problemPanel.m934(dimension.width - 20);
            lpinvalidation.loadProblem(c_f_f.f1119);
            C_TA c_ta = new C_TA();
            c_ta.setLayout(new C_u_(null, 2, new int[]{20, dimension.width - 20}));
            c_ta.add(new C_f_E(lpinvalidation.checkDisabled ? " " : C_u_D.f1128[k]));
            C_NC c_nc;
            c_ta.add(c_nc = new C_NC(LogicProgram.m1004("\\l" + trimTitle(s) + ": " + s1)));
            c_nc.setLineWrap(true);
            c_nc.setWrapStyleWord(true);
            c_nc.setFocusable(false);
            c_ta.add(new C_ZE(LogicProgram.m1004("\\l" + trimTitle(s) + ": " + s1)));
            C_TA c_ta1 = new C_TA();
            c_ta1.setLayout(new C_m_A());
            c_ta1.add(c_ta);
            c_ta1.add(lpinvalidation.problemPanel, "Center");
            vector.add(c_ta1);
         }

         if (LogicProgram.m1060(logPrint, getExerciseTitle(s))) {
            LogicProgram.m1082("invdata.txt", "P", c_f_f.f1119);
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
         LPInvalidation.allocateInvModule(this);
      }

      @Override
      public void m959() {
         LPInvalidation.continueStartup();
      }
   }
}
