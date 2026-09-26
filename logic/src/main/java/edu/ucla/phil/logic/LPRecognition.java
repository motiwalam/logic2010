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

class LPRecognition extends C_U {
   int fontSize = LogicProgram.f539;
   Font font;
   static final String workFileName = "recwork.txt";
   static final String logFileName = "recdata.txt";
   static final String digestVersKey = "recDigestVers";
   static Class messageClass = C_BF.class;
   static C_j_A exercises = null;
   static C_j_A problems = null;
   static C_BE noErrMess = null;
   static C_BE noPrintErr = null;
   static C_BE noCheck = null;
   static C_BE noPrintCheck = null;
   static C_BE noPrint = null;
   static C_BE monoProbs = null;
   static C_BE logPrint = null;
   static C_BE logSubmit = null;
   static C_BE needPrint = null;
   static C_BE needSubmit = null;
   static C_BE addToDB = null;
   static C_BE updateDB = null;
   static Vector instances = new Vector();
   static Vector activeRuleXRefs = null;
   static C_c_C printQueue = new C_c_C("Recognition");
   static String newProblem = null;
   static Vector startups;
   static boolean noUser = false;
   static boolean submitExam = false;
   static boolean printIncorrect = false;
   static boolean restating = false;
   C_AB problem;
   C_h_ buttonPanel;
   JScrollPane scroller;
   C_LB scrollPanel;
   Vector activeRules;
   C_n_F activeRange;
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
      this.setLayout(new BorderLayout());
      this.add(this.titlePanel, "North");
      this.scroller = new JScrollPane();
      this.add(this.scroller, "Center");
      this.scroller.getVerticalScrollBar().setUnitIncrement(22);
      this.scroller.setViewportView(this.scrollPanel = new C_LB());
      this.scroller.setBackground(C_n_A.bruinBlue);
      this.scrollPanel.add(this.problem = new C_AB(this), "Center");
      this.add(this.buttonPanel = C_h_.m1834(this), "South");
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

   static synchronized void startup(Rectangle rectangle, C_x_A c_x_a, String s) {
      if (startups == null) {
         startups = new Vector();
      }

      if (exercises != null || getExercises()) {
         LPRecognition.C__A lprecognition$c__a = new LPRecognition.C__A(c_x_a, rectangle, s);
         startups.add(lprecognition$c__a);
         if (startups.size() <= 1) {
            if (problems == null) {
               if (!getProblems()) {
                  lprecognition$c__a.m1310();
                  startups.remove(lprecognition$c__a);
                  return;
               }

               if (problems.m1773()) {
                  saveProblems();
               }

               problems.m1099(lprecognition$c__a);
            } else {
               lprecognition$c__a.m959();
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
      C_h_ c_h_ = C_h_.m1834(this);
      Dimension dimension = c_h_.getPreferredSize();
      if (rectangle.width < dimension.width) {
         rectangle.width = dimension.width;
      }

      return rectangle;
   }

   static void allocateRecModule(C_U.C__A c_u$c__a) {
      LPRecognition lprecognition = new LPRecognition(false);
      instances.addElement(lprecognition);
      if (c_u$c__a.f784 == null || newProblem == null) {
         lprecognition.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lprecognition.saveProblem();
         }
      }

      if (c_u$c__a.f784 != null) {
         lprecognition.loadProblem(c_u$c__a.f784);
         c_u$c__a.f784 = null;
      }

      lprecognition.setupFrame(LPInfo.programName + ": Recognizing Rules");
      lprecognition.buttonPanel.m1836();
      lprecognition.frame.setBounds(lprecognition.fixModuleRect(c_u$c__a.f783));
      lprecognition.frame.setVisible(true);
      lprecognition.requestFocus();
   }

   @Override
   public boolean shutdown(boolean flag) {
      if (!flag && !C_EC.m501(this, null)) {
         return false;
      } else {
         this.reset();
         synchronized (moduleClasses[3]) {
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
      this.problem.f139.requestFocus();
   }

   int getProblemIndex() {
      return this.problemIndex;
   }

   @Override
   public boolean save() {
      return C_EC.m501(this, null);
   }

   static boolean checkQuit(Hashtable hashtable, Hashtable hashtable1) {
      resetOptions();
      readOptions(LogicProgram.m1062("options", false));
      readOptions(LogicProgram.m1065("options", false));
      logNeeds();
      if (needPrint != null && !needPrint.m402() || needSubmit != null && !needSubmit.m402()) {
         C_a_A c_a_a = new C_a_A(readWork());
         c_a_a.m1634(readExercises());
         C_z_C.m2215(hashtable, "recdata.txt", "P", needPrint, c_a_a);
         c_a_a.m1635();
         C_z_C.m2215(hashtable1, "recdata.txt", "S", needSubmit, c_a_a);
      }

      resetOptions();
      return true;
   }

   static Vector getChangedProblems() {
      C_a_A c_a_a = new C_a_A(readWork());
      Hashtable hashtable = LogicProgram.m1084("recdata.txt", "S", needSubmit, c_a_a);
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
         int i = LogicProgram.m1038(this.scroller).width - 16;
         this.problem.f135.m934(i);
         this.problem.f135.invalidate();
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
      this.problem.f137.setFont(LogicProgram.m1029(i * 3 / 2));
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
         return isExercise(s) ? LogicProgram.m1000(s) : s.trim();
      }
   }

   String getChangedProblem() {
      String s = this.saveProblem();
      String s1 = this.problemIndex == -1 ? newProblem : problems.m1778(this.problemIndex);
      return C_XD.m1500(s).equals(C_XD.m1500(s1)) ? null : s;
   }

   static int getProblemState(String s) {
      return new LPRecognition(false).getProblemState(new C_XD(s));
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

   String saveProblem() {
      String s = this.problem.m218();
      if (this.errorCount != 0) {
         s = s + this.errorCount + "`e";
      }

      if (this.updateWorkTime() != 0L) {
         s = s + this.workTime + "`t";
      }

      return C_XD.m1509(s);
   }

   void setupFrame(String s) {
      this.frame = new C_0E(s);
      this.setFontSize(this.fontSize);
      this.setColors(this.colors);
      this.frame.f27 = this;
      this.frame.add(this, "Center");
   }

   static String getProblemStatement(String s) {
      return getProblemStatement(new C_XD(s));
   }

   static String getProblemStatement(C_XD c_xd) {
      return c_xd.m1483(c_xd.m1478("="));
   }

   static synchronized boolean getProblems() {
      if (problems != null) {
         return true;
      } else {
         C_j_A c_j_a = readWork();
         if (c_j_a == null) {
            return false;
         } else {
            if (c_j_a.f1079 && !c_j_a.m1777(LogicProgram.f533).equals(c_j_a.f1076)) {
               System.out.println("Could not digest file: recwork.txt");
               if (!C_u_C.m2101("indigestion", "instructor")) {
                  LogicProgram.m971("not003", "recwork.txt");
                  return false;
               }
            }

            problems = c_j_a;
            C_ED.f296 = C_f_F.m1816("recwork.txt", problems);
            C_f_F.m1815(exercises, C_ED.f296);
            return true;
         }
      }
   }

   static boolean getExercises() {
      if (!C_BF.m410()) {
         LogicProgram.m971("not001", "the rule recognition messages file");
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

   boolean checkProblem() {
      boolean flag = this.problem.m221();
      this.titlePanel.m1825(flag ? "Correct" : "Incorrect");
      return flag;
   }

   static boolean hasWork(String s) {
      return hasWork(new C_XD(s));
   }

   static boolean hasWork(C_XD c_xd) {
      return c_xd.m1475('*') != -1;
   }

   static String getWork(C_XD c_xd) {
      return c_xd.m1484("*");
   }

   void loadProblem(String s) {
      this.loadProblem(new C_XD(s));
   }

   void loadProblem(C_XD c_xd) {
      this.reset();
      this.loadExerciseInfo(c_xd);
      this.problem.m215(c_xd);
      this.errorCount = c_xd.m1498();
      this.workTime = c_xd.m1499();
      this.loadTime = 0L;
      this.updateWorkTime();
      this.problemIndex = -1;
      this.resize();
   }

   void loadUserProblem(String s) {
      C_VC c_vc = new C_VC(s);
      String s1 = c_vc.m1384();
      if (s1 != null) {
         Hashtable hashtable = C_H.m666("expression", LogicProgram.m995(s1, maggie, kaplan));
         C_UA.m1329(C_BF.m411("recerr001"), hashtable, null, null);
      } else if (!c_vc.f829 && c_vc.m1385() == 0) {
         this.loadProblem(C_XD.m1509(C_XD.m1508(C_VC.m1383(s), '=')));
      } else {
         C_H c_h = C_BF.m411("recerr002");
         C_UA.m1329(C_BF.m411("recerr002"), null, null, null);
      }
   }

   void newProblem() {
      this.loadProblem(newProblem);
   }

   void removeWork() {
      this.titlePanel.m1825(null);
      this.problem.m219();
   }

   static String removeWork(C_XD c_xd) {
      return c_xd.m1484("$=@~&%u!");
   }

   static boolean readProblems(Reader reader, C_j_A c_j_a, boolean flag) {
      return readProblems(reader, c_j_a, flag, false);
   }

   static boolean mergeProblems(Reader reader, C_j_A c_j_a, boolean flag) {
      return readProblems(reader, c_j_a, flag, true);
   }

   static boolean readProblems(Reader reader, C_j_A c_j_a, boolean flag, boolean flag1) {
      return C_U.readProblems(reader, c_j_a, flag, flag1);
   }

   static boolean saveProblems() {
      if (!LogicProgram.m976()) {
         return false;
      } else {
         try {
            writeProblems(problems, new FileWriter(new File(LogicProgram.f555, "recwork.txt")));
            return true;
         } catch (IOException ioexception) {
            LogicProgram.m971("not004", "recwork.txt");
            return false;
         }
      }
   }

   void setProblemTitle(String s) {
      if (s != null && (s = s.trim()).equals("")) {
         s = null;
      }

      this.problem.f123 = s;
      this.titlePanel.m1821(trimTitle(s));
   }

   boolean saveRenamed(String s) {
      if (s == null) {
         return true;
      } else {
         String s1 = this.problem.f123;
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
               String s2 = C_EC.m500(flag ? this.problem.f123 : null);
               if (s2 == null) {
                  return false;
               }

               this.setProblemTitle(s2);
               C_ED c_ed = new C_ED(C_XD.m1495(s, s2), false);
               this.problemIndex = problems.m1771(c_ed, false);
               this.problemIndex = this.problemIndex == -1 ? problems.size() : this.problemIndex + 1;
               problems.insertElementAt(c_ed, this.problemIndex);
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

   static C_j_A readExercises() {
      return readExercises(LogicProgram.f584, false, false);
   }

   static C_j_A readExercises(boolean flag, boolean flag1, boolean flag2) {
      C_j_A c_j_a = new C_j_A();
      if (!flag) {
         C_XB c_xb = LogicProgram.m1062("recwork.txt", false);
         if (c_xb == null) {
            LogicProgram.m971("not001", "the core Recognition exercise file");
            return null;
         }

         if (!readProblems(c_xb, c_j_a, true)) {
            LogicProgram.m971("not002", "the core Recognition exercise file");
            return null;
         }
      }

      if (!flag1) {
         C_XB c_xb1 = LogicProgram.m1065("recwork.txt", flag2);
         if (c_xb1 != null && (flag ? !readProblems(c_xb1, c_j_a, true) : !mergeProblems(c_xb1, c_j_a, true))) {
            LogicProgram.m971("not002", "the local Recognition exercise file");
            return null;
         }
      }

      return c_j_a;
   }

   static C_j_A readWork() {
      if (!LogicProgram.m976()) {
         return null;
      } else {
         C_j_A c_j_a = new C_j_A();
         C_XB c_xb = LogicProgram.m1062("recwork.txt", true);
         if (!LogicProgram.f584 || c_xb instanceof C_b_D) {
            if (c_xb == null) {
               LogicProgram.m971("not001", "recwork.txt");
               return null;
            }

            if (c_xb instanceof C_b_D) {
               c_j_a.f1079 = true;
            }

            if (!readProblems(c_xb, c_j_a, false)) {
               LogicProgram.m971("not002", "recwork.txt");
               return null;
            }
         }

         if (!(c_xb instanceof C_b_D)) {
            c_xb = LogicProgram.m1065("recwork.txt", false);
            if (c_xb != null && !mergeProblems(c_xb, c_j_a, false)) {
               LogicProgram.m971("not002", "recwork.txt");
               return null;
            }
         }

         return c_j_a;
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
      String s1 = c_xd.m1483(c_xd.m1475('o'));
      if (s1 != null) {
         s = s1;
      }

      this.probOptions = c_xd.m1506('%');
      this.dontChange = this.probOptions != null && this.probOptions.containsKey("eg");
      this.errorMessagesDisabled = LogicProgram.m1060(this.forPrint ? noPrintErr : noErrMess, s);
      this.checkDisabled = LogicProgram.m1060(this.forPrint ? noPrintCheck : noCheck, s);
      this.activeRules = new Vector();
      this.activeRange = new C_n_F();
      int j = activeRuleXRefs == null ? 0 : activeRuleXRefs.size();

      for (int i = 0; i < j; i++) {
         ((C_y_E)activeRuleXRefs.elementAt(i)).m2196(s, this.activeRules, this.activeRange);
      }
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         C_XD c_xd = new C_XD(reader, true);
         String s = "";

         while (c_xd.m1469()) {
            String s1 = c_xd.m1494();
            if (s1 != null && s1.trim().equalsIgnoreCase("recognition")) {
               int[] aint = c_xd.m1481("a+?");
               int i = aint.length;

               for (int j = 0; j < i; j++) {
                  char c0 = c_xd.m1474(aint[j]);
                  String s2 = c_xd.m1483(aint[j]);
                  if (c0 == 'a') {
                     activeRuleXRefs.addElement(new C_y_E(s2, null, true).m2195(s));
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
                              noErrMess = new C_BE();
                           }

                           noErrMess.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noCheck")) {
                           if (noCheck == null) {
                              noCheck = new C_BE();
                           }

                           noCheck.m395(new C_BE(s2.substring(k + 1)).m403(s));
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
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new C_BE();
                           }

                           monoProbs.m395(new C_BE(s2.substring(k + 1)).m403(s));
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

   boolean ruleActive(C_VB c_vb) {
      for (C_LF c_lf : c_vb.m1374()) {
         if (this.activeRules.indexOf(c_lf.f820) != -1) {
            return true;
         }

         C_QE c_qe = c_lf.f822;
         if (c_qe != null && this.activeRange.m1983(c_qe.f700)) {
            return true;
         }
      }

      return false;
   }

   C_VB activeRules() {
      C_VB c_vb = new C_VB("activeRules");
      int i = this.activeRules.size();

      for (int j = 0; j < i; j++) {
         c_vb.m1370(LogicProgram.m1026((String)this.activeRules.elementAt(j)));
      }

      Enumeration enumeration = this.activeRange.m1985();

      while (enumeration.hasMoreElements()) {
         c_vb.m1370(LogicProgram.f534.m2204((Integer)enumeration.nextElement()));
      }

      return c_vb;
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
      if (C_u_C.m2105("Recognizing Rules")) {
         int[] aint = getExerciseIndices();
         C_x_A c_x_a = new C_x_A(this);
         C_0A c_0a = C_KC.m835(c_x_a);
         if (c_0a != null) {
            if (C_EC.m501(this, null)) {
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
         int[] aint = C_EC.m506(this);
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
            c_0a.f6 = C_ED.f1128[k];
            c_0a.f8 = s;
            c_0a.f9 = c_xd.m1494();
            c_0a.f10 = moduleAbbrs[moduleIndex];
            c_0a.f11 = c_xd.m1498();
            c_0a.f12 = c_xd.m1499();
            boolean flag = LogicProgram.m1060(logSubmit, getExerciseTitle(c_0a.f9));
            if (C_KC.m836(c_0a, c_x_a)) {
               vector.addElement(trimTitle(c_0a.f9));
               if (flag) {
                  LogicProgram.m1083("recdata.txt", "S", s, c_0a.m3());
               }
            } else {
               vector1.addElement(trimTitle(c_0a.f9));
               if (flag) {
                  LogicProgram.m1082("recdata.txt", "F", s);
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
         int[] aint = C_EC.m508(this);
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
         if (!printIncorrect || k == 1) {
            String s1 = getProblemStatement(c_xd);
            s1 = LogicProgram.m995(s1, maggie, kaplan);
            String s2 = c_xd.m1483(c_xd.m1475('*'));
            LPRecognition lprecognition = new LPRecognition(true);
            lprecognition.loadProblem(c_f_f.f1119);
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new C_m_A());
            JPanel jpanel1 = new JPanel();
            jpanel1.setLayout(new C_u_(null, 2, new int[]{20, dimension.width - 20}));
            jpanel1.add(new C_f_E(lprecognition.checkDisabled ? " " : C_ED.f1128[k]));
            C_NC c_nc;
            jpanel1.add(c_nc = new C_NC(LogicProgram.m1004("\\l" + trimTitle(s) + ": " + s1)));
            c_nc.setLineWrap(true);
            c_nc.setWrapStyleWord(true);
            c_nc.setBackground(LogicProgram.f605[1]);
            jpanel.add(jpanel1);
            if (!flag && !lprecognition.checkDisabled && s2 != null) {
               C_e_E c_e_e = new C_e_E("Answer: " + s2);
               c_e_e.setBackground(LogicProgram.f605[1]);
               jpanel.add(c_e_e);
            }

            vector.add(jpanel);
         }

         if (LogicProgram.m1060(logPrint, getExerciseTitle(s))) {
            LogicProgram.m1082("recdata.txt", flag ? "R" : "P", c_f_f.f1119);
         }
      }

      return vector;
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
         C_e_E c_e_e;
         jpanel.add(c_e_e = new C_e_E(LogicProgram.m1004("\\l" + s + ": " + s1)));
         c_e_e.m1787(true);
         c_e_e.m1789(true);
         c_e_e.setBackground(LogicProgram.f605[1]);
         vector.add(jpanel);
      }

      return vector;
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

   static class C__A extends C_U.C__A {
      C__A(C_x_A c_x_a, Rectangle rectangle, String s) {
         super(c_x_a, rectangle, s);
      }

      @Override
      public void run() {
         LPRecognition.allocateRecModule(this);
      }

      @Override
      public void m959() {
         LPRecognition.continueStartup();
      }
   }
}
