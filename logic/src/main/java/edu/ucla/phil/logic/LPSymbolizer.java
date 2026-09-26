package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.io.BufferedWriter;
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
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

class LPSymbolizer extends C_U implements C_v_D {
   int fontSize = LogicProgram.f539;
   static final String workFileName = "symwork.txt";
   static final String logFileName = "symdata.txt";
   static final String keyFileName = "keywork.txt";
   static final String answerFileName = "symAnswers";
   static final String digestVersKey = "symDigestVers";
   static C_h_C exercises = null;
   static Hashtable answers = null;
   static Hashtable messages = null;
   static C_h_C problems = null;
   static Hashtable userKey = null;
   static Vector instances = new Vector();
   static C_c_C printQueue = new C_c_C("Symbolization");
   static C_BE noDirect = null;
   static C_BE noErrMess = null;
   static C_BE noHints = null;
   static C_BE noCheck = null;
   static C_BE noPrint = null;
   static C_BE noPrintCheck = null;
   static C_BE noPrintErr = null;
   static C_BE monoProbs = null;
   static C_BE equCounts = null;
   static C_BE addToDB = null;
   static C_BE updateDB = null;
   static C_BE chap1 = null;
   static C_BE chap2 = null;
   static C_BE chap3 = null;
   static C_BE chap4 = null;
   static C_BE chap5 = null;
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
   boolean directEntryDisabled;
   boolean errorMessagesDisabled;
   boolean hintsDisabled;
   boolean checkDisabled;
   boolean dontChange;
   Integer chapter;
   int errorCount;
   int hintCount;
   long workTime;
   long loadTime;
   String lastDirect;
   JScrollPane treeScroller;
   static String lastUserProblem = "";
   static String lastUserScheme = "";
   Hashtable probOptions;
   JSplitPane splitPane;
   C_e_E symbolized;
   C_y_B scheme = new C_y_B();
   C_d_C problem;
   C_x_C lastFocus;
   C_x_C focus;
   static String[] kaplan = LogicProgram.f596;
   static int moduleIndex = 4;

   static boolean getExercises() {
      if (!C_h_E.m410()) {
         LogicProgram.m971("not001", "the symbolization messages file");
         return false;
      } else if (!getAnswers()) {
         LogicProgram.m971("not002", "the symbolization answer file");
         return false;
      } else {
         resetOptions();
         readOptions(LogicProgram.m1062("options", false));
         readOptions(LogicProgram.m1064("options"));
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

   static C_h_C readExercises() {
      return readExercises(LogicProgram.f584, false, false);
   }

   static C_h_C readExercises(boolean flag, boolean flag1, boolean flag2) {
      C_h_C c_h_c = new C_h_C(false);
      if (!flag) {
         C_XB c_xb = LogicProgram.m1062("symwork.txt", false);
         if (c_xb == null) {
            LogicProgram.m971("not001", "the Symbolization exercise file");
            return null;
         }

         if (!readProblems(c_xb, c_h_c, true)) {
            LogicProgram.m971("not002", "the Symbolization exercise file");
            return null;
         }
      }

      if (!flag1) {
         C_XB c_xb1 = LogicProgram.m1065("symwork.txt", flag2);
         if (c_xb1 != null && (flag ? !readProblems(c_xb1, c_h_c, true) : !mergeProblems(c_xb1, c_h_c, true))) {
            LogicProgram.m971("not002", "the local Symbolization exercise file");
            return null;
         }
      }

      return c_h_c;
   }

   LPSymbolizer(boolean flag) {
      super(flag);
      C_c_F c_c_f = new C_c_F(this);
      c_c_f.setViewportView(this.scheme);
      c_c_f.getVerticalScrollBar().setUnitIncrement(22);
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      jpanel.add(c_c_f, "Center");
      this.scheme.setForeground(this.colors[0]);
      this.scheme.setBackground(this.colors[1]);
      this.symbolized = new C_e_E();
      StyledDocument styleddocument = this.symbolized.getStyledDocument();
      SimpleAttributeSet simpleattributeset = new SimpleAttributeSet();
      StyleConstants.setAlignment(simpleattributeset, 1);
      StyleConstants.setForeground(simpleattributeset, this.colors[0]);
      StyleConstants.setBackground(simpleattributeset, this.colors[1]);
      StyleConstants.setFontFamily(simpleattributeset, this.symbolized.getFont().getFamily());
      StyleConstants.setFontSize(simpleattributeset, this.symbolized.getFont().getSize());
      StyleConstants.setBold(simpleattributeset, this.symbolized.getFont().isBold());
      this.symbolized.setParagraphAttributes(simpleattributeset, true);
      this.symbolized.m1787(true);
      this.symbolized.m1789(true);
      this.symbolized.setEditable(false);
      this.problem = new C_d_C(this, 0);
      this.errorCount = 0;
      this.hintCount = 0;
      this.workTime = 0L;
      this.loadTime = 0L;
      this.lastDirect = "";
      this.chapter = null;
      this.probOptions = null;
      this.treeScroller = new JScrollPane();
      this.treeScroller.setViewportView(this.problem);
      this.treeScroller.getVerticalScrollBar().setUnitIncrement(22);
      JPanel jpanel2 = new JPanel();
      Color color = jpanel2.getForeground();
      Color color1 = jpanel2.getBackground();
      jpanel2.setLayout(new BorderLayout());
      jpanel2.setBorder(new BevelBorder(1, color1, color));
      jpanel2.setBackground(this.colors[1]);
      jpanel2.add(this.symbolized, "Center");
      JPanel jpanel1 = new JPanel();
      jpanel1.setLayout(new BorderLayout());
      jpanel1.add(jpanel2, "North");
      jpanel1.add(this.treeScroller, "Center");
      this.problem.setForeground(this.colors[0]);
      this.problem.setBackground(this.colors[1]);
      this.setLayout(new BorderLayout());
      JPanel jpanel3 = new JPanel();
      jpanel3.setLayout(new BorderLayout());
      jpanel3.add(this.splitPane = new JSplitPane(1, jpanel, jpanel1), "Center");
      this.add(this.titlePanel, "North");
      this.add(jpanel3, "Center");
      this.add(C_b_B.m1663(this), "South");
      this.focus = null;
      this.lastFocus = null;
      this.newProblem();
   }

   @Override
   int getModuleIndex() {
      return moduleIndex;
   }

   void repaintTree() {
      this.treeScroller.repaint();
   }

   static synchronized void startup(Rectangle rectangle, C_x_A c_x_a, String s) {
      if (startups == null) {
         startups = new Vector();
      }

      LPSymbolizer.C__A lpsymbolizer$c__a = new LPSymbolizer.C__A(c_x_a, rectangle, s);
      startups.add(lpsymbolizer$c__a);
      if (startups.size() <= 1) {
         if (exercises == null) {
            if (!getExercises()) {
               lpsymbolizer$c__a.m1310();
               startups.remove(lpsymbolizer$c__a);
               return;
            }

            exercises.m1099(lpsymbolizer$c__a);
         } else {
            lpsymbolizer$c__a.m959();
         }
      }
   }

   static void startupAfterExercises(LPSymbolizer.C__A lpsymbolizer$c__a) {
      if (problems == null) {
         if (!getProblems()) {
            lpsymbolizer$c__a.m1310();
            startups.remove(lpsymbolizer$c__a);
            return;
         }

         if (problems.m1773()) {
            saveProblems();
         }

         problems.m1099(lpsymbolizer$c__a);
      } else {
         lpsymbolizer$c__a.m959();
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
      C_b_B c_b_b = C_b_B.m1663(this);
      Dimension dimension = c_b_b.getPreferredSize();
      if (rectangle.width < dimension.width) {
         rectangle.width = dimension.width;
      }

      return rectangle;
   }

   static void allocateSymModule(C_U.C__A c_u$c__a) {
      LPSymbolizer lpsymbolizer = new LPSymbolizer(false);
      instances.addElement(lpsymbolizer);
      if (c_u$c__a.f784 == null || newProblem == null) {
         lpsymbolizer.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lpsymbolizer.saveProblem();
         }
      }

      if (c_u$c__a.f784 != null) {
         lpsymbolizer.loadProblem(c_u$c__a.f784);
         c_u$c__a.f784 = null;
      }

      lpsymbolizer.setupFrame(LPInfo.programName + ": Symbolization");
      lpsymbolizer.frame.setBounds(lpsymbolizer.fixModuleRect(c_u$c__a.f783));
      lpsymbolizer.frame.setVisible(true);
      lpsymbolizer.splitPane.setDividerLocation(lpsymbolizer.getVisibleRect().width / 4);
      lpsymbolizer.requestFocus();
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
               String s4 = c_xd.m1483(c_xd.m1475('C'));
               if (s4 == null) {
                  s4 = s;
               }

               String s5 = "insert into logic_problem (COMMENT,DTCREATION,PROBLEM_NAME,TPROBLEM,TPROBLEM_MD5,TWEB_FORM_PROBLEM,VERSION,SYNTAX,COMMON_NAME)";
               s5 = s5 + " values (" + C_KC.m815(s2) + ",GETDATE()," + C_KC.m815(s) + "," + C_KC.m815(s1) + ",";
               s5 = s5 + C_KC.m815(s3) + "," + C_KC.m815(s1) + "," + nameVersion(s) + "," + C_FB.m537() + ",";
               s5 = s5 + C_KC.m815(s4) + ")";
               C_KC.m814(s5);
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
               String s4 = c_xd.m1483(c_xd.m1475('C'));
               if (s4 == null) {
                  s4 = s;
               }

               String s5 = "update logic_problem set tproblem = " + C_KC.m815(s1) + ", tproblem_md5 = " + C_KC.m815(s3);
               s5 = s5 + ", tweb_form_problem = " + C_KC.m815(s1) + ", comment = " + C_KC.m815(s2);
               s5 = s5 + ", version = " + nameVersion(s) + ", common_name = " + C_KC.m815(s4);
               s5 = s5 + " where problem_name = " + C_KC.m815(s) + " and syntax = " + C_FB.m537();
               C_KC.m814(s5);
            }
         }
      }
   }

   static String nameVersion(String s) {
      return s != null && s.toLowerCase().startsWith("inval") ? "1" : "NULL";
   }

   void setupFrame(String s) {
      this.frame = new C_0E(s);
      this.frame.f27 = this;
      this.frame.add(this, "Center");
   }

   @Override
   public boolean shutdown(boolean flag) {
      if (!flag && !C_WB.m1450(this, null)) {
         return false;
      } else {
         this.reset();
         synchronized (moduleClasses[4]) {
            instances.removeElement(this);
            if (instances.isEmpty()) {
               C_l_B.m1924(printQueue);
               userKey = null;
               problems = null;
               answers = null;
               exercises = null;
               messages = null;
               newProblem = null;
               startups = null;
               resetOptions();
            }

            return true;
         }
      }
   }

   static synchronized LPSymbolizer openInstance(int i, boolean flag) {
      if (i >= 0 && instances != null && !instances.isEmpty()) {
         int j = instances.size();

         for (int k = 0; k < j; k++) {
            LPSymbolizer lpsymbolizer = (LPSymbolizer)instances.elementAt(k);
            if (lpsymbolizer.problemIndex == i) {
               if (flag) {
                  lpsymbolizer.requestFocus();
               }

               return lpsymbolizer;
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
      return C_WB.m1450(this, null);
   }

   static void resetOptions() {
      noDirect = null;
      noErrMess = null;
      noHints = null;
      noCheck = null;
      noPrint = null;
      noPrintCheck = null;
      noPrintErr = null;
      monoProbs = null;
      equCounts = null;
      addToDB = null;
      updateDB = null;
      chap1 = null;
      chap2 = null;
      chap3 = null;
      chap2 = null;
      chap5 = null;
      logPrint = null;
      logSubmit = null;
      needPrint = null;
      needSubmit = null;
      noUser = false;
      submitExam = false;
      printIncorrect = false;
      restating = false;
   }

   static boolean checkQuit(Hashtable hashtable, Hashtable hashtable1) {
      resetOptions();
      readOptions(LogicProgram.m1062("options", false));
      readOptions(LogicProgram.m1064("options"));
      logNeeds();
      if (needPrint != null && !needPrint.m402() || needSubmit != null && !needSubmit.m402()) {
         C_a_A c_a_a = new C_a_A(readWork());
         c_a_a.m1634(readExercises());
         C_z_C.m2215(hashtable, "symdata.txt", "P", needPrint, c_a_a);
         c_a_a.m1635();
         C_z_C.m2215(hashtable1, "symdata.txt", "S", needSubmit, c_a_a);
      }

      resetOptions();
      return true;
   }

   static Vector getChangedProblems() {
      C_a_A c_a_a = new C_a_A(readWork());
      Hashtable hashtable = LogicProgram.m1084("symdata.txt", "S", needSubmit, c_a_a);
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
   }

   @Override
   public Frame getFrame() {
      return this.frame;
   }

   void updateSymbolization() {
      this.symbolized.setText(this.problem.toString());
   }

   @Override
   int getProblemState(C_XD c_xd) {
      System.out.println("LPSymbolizer.getProblemState(LPTagReader reader) should not be called!");
      return 4;
   }

   static C__C getProblemState(String s, C_h_C c_h_c, C__C c__c) {
      if (c__c == null) {
         c__c = new C__C(s, c_h_c, true);
      }

      if (!hasWork(s)) {
         c__c.m1582();
      } else {
         C_d_C.m1740(s, c_h_c, c__c);
      }

      return c__c;
   }

   void setProblemTitle(String s) {
      if (s != null && !(s = s.trim()).equals("")) {
         this.problem.f1051 = s;
         this.titlePanel.m1821(trimTitle(s));
      } else {
         this.problem.f1051 = null;
         this.titlePanel.m1821(null);
      }
   }

   static String trimTitle(String s) {
      if (s == null) {
         return null;
      } else {
         return isExercise(s) ? LogicProgram.m1000(s) : s.trim();
      }
   }

   static synchronized boolean getProblems() {
      if (problems != null) {
         return true;
      } else if (!getUserKey()) {
         return false;
      } else {
         C_h_C c_h_c = readWork();
         if (c_h_c == null) {
            return false;
         } else {
            if (c_h_c.f1079 && !c_h_c.m1777(LogicProgram.f533).equals(c_h_c.f1076)) {
               System.out.println("Could not digest file: symwork.txt");
               if (!C_u_C.m2101("indigestion", "instructor")) {
                  LogicProgram.m971("not003", "symwork.txt");
                  return false;
               }
            }

            problems = c_h_c;
            C__C.f928 = C_f_F.m1816("symwork.txt", problems);
            C_f_F.m1815(exercises, C__C.f928);
            return true;
         }
      }
   }

   static C_h_C readWork() {
      if (!LogicProgram.m976()) {
         return null;
      } else {
         C_h_C c_h_c = new C_h_C(true);
         C_XB c_xb = LogicProgram.m1062("symwork.txt", true);
         if (!LogicProgram.f584 || c_xb instanceof C_b_D) {
            if (c_xb == null) {
               LogicProgram.m971("not001", "symwork.txt");
               return null;
            }

            if (c_xb instanceof C_b_D) {
               c_h_c.f1079 = true;
            }

            if (!readProblems(c_xb, c_h_c, false)) {
               LogicProgram.m971("not002", "symwork.txt");
               return null;
            }
         }

         if (!(c_xb instanceof C_b_D)) {
            c_xb = LogicProgram.m1064("symwork.txt");
            if (c_xb != null && !mergeProblems(c_xb, c_h_c, false)) {
               LogicProgram.m971("not002", "symwork.txt");
               return null;
            }
         }

         return c_h_c;
      }
   }

   static boolean getUserKey() {
      userKey = new Hashtable();
      C_XB c_xb = LogicProgram.m1062("keywork.txt", true);
      if (c_xb != null && c_xb instanceof C_b_D && !readAnswers(c_xb, userKey)) {
         LogicProgram.m971("not002", "keywork.txt");
         return false;
      } else {
         return true;
      }
   }

   static boolean writeUserKey() {
      synchronized (userKey) {
         if (userKey == null) {
            return true;
         } else {
            Enumeration enumeration = userKey.elements();
            BufferedWriter bufferedwriter = new BufferedWriter(LogicProgram.m1068("keywork.txt", false, true));

            try {
               while (enumeration.hasMoreElements()) {
                  String s = (String)enumeration.nextElement();
                  bufferedwriter.write(s, 0, s.length());
                  bufferedwriter.newLine();
               }

               bufferedwriter.close();
            } catch (IOException ioexception) {
               return false;
            }

            return true;
         }
      }
   }

   static boolean readProblems(Reader reader, C_h_C c_h_c, boolean flag) {
      return readProblems(reader, c_h_c, flag, false);
   }

   static boolean mergeProblems(Reader reader, C_h_C c_h_c, boolean flag) {
      return readProblems(reader, c_h_c, flag, true);
   }

   static boolean readProblems(Reader reader, C_h_C c_h_c, boolean flag, boolean flag1) {
      if (flag && c_h_c.f1158 == null) {
         c_h_c.f1158 = new Hashtable();
      }

      return C_U.readProblems(reader, c_h_c, flag, flag1);
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
      String s = c_xd.m1483(c_xd.m1475('-'));
      if (s != null) {
         return s;
      } else {
         s = c_xd.m1483(c_xd.m1475('+'));
         if (s == null) {
            return null;
         } else {
            C_OA c_oa = new C_OA("\\:");
            c_oa.m1132(s);
            c_oa.m1135();
            return c_oa.m1133();
         }
      }
   }

   static String getProblemSymbol(String s) {
      return getProblemSymbol(new C_XD(s));
   }

   static String getProblemSymbol(C_XD c_xd) {
      String s = c_xd.m1483(c_xd.m1475('+'));
      if (s == null) {
         return c_xd.m1475('-') == -1 ? null : connSymbol[0];
      } else {
         C_OA c_oa = new C_OA("\\:");
         c_oa.m1132(s);
         String s1 = c_oa.m1135();
         return c_oa.m1133() == null ? null : s1;
      }
   }

   static int getHintCount(C_XD c_xd) {
      Integer integer = c_xd.m1485(c_xd.m1475('h'));
      return integer == null ? 0 : integer;
   }

   static boolean hasWork(String s) {
      return hasWork(new C_XD(s));
   }

   static boolean hasWork(C_XD c_xd) {
      return c_xd.m1475('-') == -1 ? !connSymbol[0].equals(getProblemSymbol(c_xd)) : c_xd.m1475('+') != -1;
   }

   static String getWork(C_XD c_xd) {
      return c_xd.m1475('-') == -1 && connSymbol[0].equals(getProblemSymbol(c_xd)) ? "" : c_xd.m1484("+");
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
      this.directEntryDisabled = LogicProgram.m1060(noDirect, s);
      this.errorMessagesDisabled = LogicProgram.m1060(this.forPrint ? noPrintErr : noErrMess, s);
      this.hintsDisabled = LogicProgram.m1060(noHints, s);
      this.checkDisabled = LogicProgram.m1060(this.forPrint ? noPrintCheck : noCheck, s);
      if (LogicProgram.m1060(chap1, s)) {
         this.chapter = new Integer(1);
      } else if (LogicProgram.m1060(chap2, s)) {
         this.chapter = new Integer(2);
      } else if (LogicProgram.m1060(chap3, s)) {
         this.chapter = new Integer(3);
      } else if (LogicProgram.m1060(chap4, s)) {
         this.chapter = new Integer(4);
      } else if (LogicProgram.m1060(chap5, s)) {
         this.chapter = new Integer(5);
      } else {
         this.chapter = null;
      }
   }

   static boolean equivalentCounts(String s) {
      return LogicProgram.m1060(equCounts, getExerciseTitle(s));
   }

   void checkProblem() {
      this.problem.m1739();
      if (this.problem.m1714() != 0) {
         int i = -1;
         if (this.problem.m1717()) {
            this.titlePanel.m1825("Incomplete");
         } else if ((i = this.problem.m1712()) != -1) {
            int j = this.problem.m1720(i, problems);
            if (j != -1) {
               this.titlePanel.m1825("Duplicate of\n" + C_d_C.m1718(j, this.problem.f1057));
            } else {
               this.titlePanel.m1825("Correct");
            }
         } else if ((i = this.problem.m1713()) != -1) {
            int k = this.problem.m1720(i, problems);
            if (k != -1) {
               this.titlePanel.m1825("Duplicate of\n" + C_d_C.m1718(k, this.problem.f1057));
            } else if (equivalentCounts(this.problem.f1051)) {
               this.titlePanel.m1825("Correct Equivalent");
            } else {
               this.titlePanel.m1825("Equivalent, but Incorrect");
            }
         } else {
            C_d_C c_d_c = this.problem.m1715();
            C_r_C c_r_c = new C_r_C();
            this.problem.m1722(c_d_c, c_r_c);
            this.titlePanel.m1825(c_r_c.f1353 ? "Incorrect: Quantifier Unrestricted" : "Incorrect");
         }
      }
   }

   static boolean getAnswers() {
      answers = new Hashtable();
      if (!LogicProgram.f584) {
         C_XB c_xb = LogicProgram.m1062("symAnswers", false);
         if (c_xb == null || !readAnswers(c_xb, answers)) {
            return false;
         }
      }

      C_XB c_xb1 = LogicProgram.m1064("symAnswers");
      return c_xb1 == null || readAnswers(c_xb1, answers);
   }

   static boolean readAnswers(Reader reader, Hashtable hashtable) {
      if (reader == null) {
         return false;
      } else {
         C_XB c_xb;
         if (reader instanceof C_XB) {
            c_xb = (C_XB)reader;
         } else {
            c_xb = new C_XB(reader, LogicProgram.f537);
         }

         try {
            String s;
            while ((s = c_xb.readLine()) != null) {
               if (!C_XD.m1511(s)) {
                  String s1 = C_XD.m1493(s);
                  if (s1 != null && !(s1 = s1.trim()).equals("")) {
                     hashtable.put(s1, s);
                  }
               }
            }

            c_xb.close();
            return true;
         } catch (IOException ioexception) {
            return false;
         }
      }
   }

   static String[] getProblemAnswers(String s) {
      String s1 = getExerciseTitle(s);
      C_XD c_xd;
      boolean flag;
      if (s1 == null) {
         String s2 = problems.m1780(s);
         if (s2 == null) {
            return null;
         }

         c_xd = new C_XD(s2);
         flag = true;
         String s3 = c_xd.m1497();
         if (s3 != null) {
            return getProblemAnswers(s3);
         }
      } else {
         String s4 = exercises.m1780(s1);
         if (s4 == null) {
            return null;
         }

         c_xd = new C_XD(s4);
         flag = false;
      }

      Vector vector = C_d_C.m1707(c_xd, flag);
      if (vector == null) {
         return null;
      } else {
         String[] astring = new String[vector.size()];
         vector.copyInto(astring);
         return astring;
      }
   }

   static String getProblemScheme(String s) {
      String s1 = getExerciseTitle(s);
      C_XD c_xd;
      if (s1 == null) {
         String s2 = problems.m1780(s);
         if (s2 == null) {
            return null;
         }

         c_xd = new C_XD(s2);
         String s3 = c_xd.m1497();
         if (s3 != null) {
            return getProblemScheme(s3);
         }
      } else {
         String s4 = exercises.m1780(s1);
         if (s4 == null) {
            return null;
         }

         c_xd = new C_XD(s4);
      }

      return c_xd.m1483(c_xd.m1475('='));
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         C_XD c_xd = new C_XD(reader, true);
         String s = "";

         while (c_xd.m1469()) {
            String s1 = c_xd.m1494();
            if (s1 != null && s1.trim().equalsIgnoreCase("symbolization")) {
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
                        if (s3.equalsIgnoreCase("noDirect")) {
                           if (noDirect == null) {
                              noDirect = new C_BE();
                           }

                           noDirect.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noErrMess")) {
                           if (noErrMess == null) {
                              noErrMess = new C_BE();
                           }

                           noErrMess.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noHints")) {
                           if (noHints == null) {
                              noHints = new C_BE();
                           }

                           noHints.m395(new C_BE(s2.substring(k + 1)).m403(s));
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
                        } else if (s3.equalsIgnoreCase("equCounts")) {
                           if (equCounts == null) {
                              equCounts = new C_BE();
                           }

                           equCounts.m395(new C_BE(s2.substring(k + 1)).m403(s));
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
                        } else if (s3.equalsIgnoreCase("chap3")) {
                           if (chap3 == null) {
                              chap3 = new C_BE();
                           }

                           chap3.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("chap4")) {
                           if (chap4 == null) {
                              chap4 = new C_BE();
                           }

                           chap4.m395(new C_BE(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("chap5")) {
                           if (chap5 == null) {
                              chap5 = new C_BE();
                           }

                           chap5.m395(new C_BE(s2.substring(k + 1)).m403(s));
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

   void loadProblem(String s) {
      this.loadProblem(new C_XD(s));
   }

   void loadProblem(C_XD c_xd) {
      this.lastDirect = "";
      this.problemIndex = -1;
      this.loadExerciseInfo(c_xd);
      this.problem.m1703(c_xd);
      this.titlePanel.m1823(this.problem.f1055 == null ? "" : this.problem.f1055);
      this.errorCount = c_xd.m1498();
      this.hintCount = getHintCount(c_xd);
      this.workTime = c_xd.m1499();
      this.loadTime = 0L;
      if (this.problem.m1714() == 0) {
         this.titlePanel.m1825("Answer Not Available");
      } else {
         this.titlePanel.m1825(null);
      }

      this.setProblemTitle(this.problem.f1051);
      this.scheme.m2182(this.problem.f1053);
      this.titlePanel.m1827(c_xd.m1483(c_xd.m1475('!')));
      this.updateWorkTime();
      this.problem.f1045.f1438.requestFocus();
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
      this.loadProblem(C_XD.m1509(C_XD.m1508(s, '-')));
   }

   void removeWork() {
      this.problem.m1690(0, false);
      this.problem.m1680(this.problem.f1055);
      this.updateSymbolization();
      this.problem.f1045.f1438.requestFocus();
   }

   static String removeWork(C_XD c_xd) {
      String s = C_XD.m1508(c_xd.m1494(), '$');
      s = s + C_XD.m1508(getProblemStatement(c_xd), '-');
      return s + c_xd.m1484("=@%u!");
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
      if (C_u_C.m2105("Symbolization")) {
         int[] aint = getExerciseIndices();
         C_x_A c_x_a = new C_x_A(this);
         C_0A c_0a = C_KC.m835(c_x_a);
         if (c_0a != null) {
            if (C_WB.m1450(this, null)) {
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
         int[] aint = C_WB.m1436(this);
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
            int k = getProblemState(s, problems, null).f1120;
            c_0a.f6 = C__C.f1128[k];
            c_0a.f8 = s;
            c_0a.f9 = c_xd.m1494();
            c_0a.f10 = moduleAbbrs[moduleIndex];
            Integer integer = LogicProgram.m1010(c_xd.m1483(c_xd.m1475('h')));
            c_0a.f11 = c_xd.m1498() + (integer == null ? 0 : integer);
            c_0a.f12 = c_xd.m1499();
            boolean flag = LogicProgram.m1060(logSubmit, getExerciseTitle(c_0a.f9));
            if (C_KC.m836(c_0a, c_x_a)) {
               vector.addElement(trimTitle(c_0a.f9));
               if (flag) {
                  LogicProgram.m1083("symdata.txt", "S", s, c_0a.m3());
               }
            } else {
               vector1.addElement(trimTitle(c_0a.f9));
               if (flag) {
                  LogicProgram.m1082("symdata.txt", "F", s);
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
         c_ld.f520 = C_h_E.m412("symnot009");
         int[] aint = C_WB.m1437(this);
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
            C_XD c_xd = new C_XD(s);
            String s1 = getProblemStatement(c_xd);
            c_ld.f510 = c_xd.m1494();
            c_ld.f511 = s1;
            c_ld.f512 = s1;
            c_ld.f514 = getProblemScheme(c_ld.f510);
            c_ld.f513 = moduleAbbrs[moduleIndex];
            c_ld.f515 = getProblemAnswers(c_ld.f510);
            stripUploadAnswers(c_ld.f515);
            if (c_ld.f515 != null && c_ld.f515.length > 0 && !isExercise(c_ld.f510) && C_KC.m841(c_ld, c_x_a)) {
               vector.addElement(trimTitle(c_ld.f510));
            } else {
               vector1.addElement(trimTitle(c_ld.f510));
            }
         }

         vector.copyInto(c_ld.f518 = new String[vector.size()]);
         vector1.copyInto(c_ld.f519 = new String[vector1.size()]);
      }
   }

   static void stripUploadAnswers(String[] astring) {
      int i = astring == null ? 0 : astring.length;

      for (int j = 0; j < i; j++) {
         C_XD c_xd = new C_XD(astring[j]);
         astring[j] = c_xd.m1484("+");
      }
   }

   void newProblem() {
      this.loadProblem(newProblem);
   }

   String saveProblem() {
      String s = this.problem.m1699(true);
      if (this.errorCount != 0) {
         s = s + this.errorCount + "`e";
      }

      if (this.hintCount != 0) {
         s = s + this.hintCount + "`h";
      }

      if (this.updateWorkTime() != 0L) {
         s = s + this.workTime + "`t";
      }

      return C_XD.m1509(s);
   }

   static boolean saveProblems(int i) {
      if (i != -1) {
         C__C c__c = (C__C)problems.m1779(i);
         getProblemState(c__c.f1119, problems, c__c);
      }

      return saveProblems();
   }

   static boolean saveProblems() {
      if (!LogicProgram.m976()) {
         return false;
      } else {
         try {
            writeProblems(problems, new FileWriter(new File(LogicProgram.f555, "symwork.txt")));
            writeUserKey();
            return true;
         } catch (IOException ioexception) {
            LogicProgram.m971("not004", "symwork.txt");
            return false;
         }
      }
   }

   boolean saveRenamed(String s) {
      if (s == null) {
         return true;
      } else {
         String s1 = this.problem.f1051;
         String s2 = this.problem.f1052;
         int i = this.problemIndex;
         if (s2 == null) {
            this.problem.f1052 = s1;
            s = this.saveProblem();
         }

         this.problemIndex = -1;
         if (!this.saveProblems(s, true)) {
            this.problemIndex = i;
            this.problem.f1052 = s2;
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
               String s2 = C_WB.m1451(flag ? this.problem.f1051 : null);
               if (s2 == null) {
                  return false;
               }

               this.setProblemTitle(s2);
               C__C c__c = new C__C(C_XD.m1495(s, s2), problems, false);
               this.problemIndex = problems.m1771(c__c, false);
               this.problemIndex = this.problemIndex == -1 ? problems.size() : this.problemIndex + 1;
               problems.insertElementAt(c__c, this.problemIndex);
            } else {
               s1 = problems.m1778(this.problemIndex);
               problems.m1776(s, this.problemIndex);
            }

            if (!saveProblems(this.problemIndex)) {
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

   static Vector getStatements(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         C_f_F c_f_f = problems.m1779(aint[j]);
         C_XD c_xd = new C_XD(c_f_f.f1119);
         String s = c_xd.m1494();
         String s1 = getProblemStatement(c_xd);
         String s2 = c_xd.m1483(c_xd.m1475('='));
         if (s2 == null) {
            c_xd = new C_XD(exercises.m1780(s));
            s2 = c_xd.m1483(c_xd.m1475('='));
         }

         if (s2 == null) {
            s2 = "No Scheme Available";
         }

         JPanel jpanel = new JPanel();
         jpanel.setLayout(new C_u_(null, 1, new int[]{dimension.width}));
         C_NC c_nc;
         jpanel.add(c_nc = new C_NC(LogicProgram.m1004("\\l" + s + ": " + s1 + "\n" + s2)));
         c_nc.setLineWrap(true);
         c_nc.setWrapStyleWord(true);
         c_nc.setBackground(LogicProgram.f605[1]);
         vector.add(jpanel);
      }

      return vector;
   }

   static Vector getAnswers(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         String s = "";
         String s1 = exercises.m1778(aint[j]);
         C_XD c_xd = new C_XD(s1);
         String s2 = c_xd.m1494();
         s = s + trimTitle(s2) + "\n";
         String s3 = getProblemStatement(c_xd);
         s = s + s3 + "\n";
         String s4 = c_xd.m1483(c_xd.m1475('='));
         if (s4 != null) {
            s = s + s4 + "\n";
         }

         String s5 = c_xd.m1483(c_xd.m1475('!'));
         if (s5 != null) {
            s = s + s5 + "\n";
         }

         LPSymbolizer lpsymbolizer = new LPSymbolizer(true);
         lpsymbolizer.loadProblem(s1);
         Vector vector1 = lpsymbolizer.problem.f1056;
         int k = vector1 == null ? 0 : vector1.size();

         for (int l = 0; l < k; l++) {
            lpsymbolizer.loadProblem((String)vector1.elementAt(l));
            s = s + lpsymbolizer.problem.toString() + "\n";
         }

         JPanel jpanel = new JPanel();
         jpanel.setLayout(new C_u_(null, 1, new int[]{dimension.width}));
         C_NC c_nc;
         jpanel.add(c_nc = new C_NC(s));
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
         if (!printIncorrect || k == 1) {
            String s1 = getProblemStatement(c_xd);
            String s2 = exercises.m1780(s);
            String s3 = null;
            if (s2 != null) {
               C_XD c_xd1 = new C_XD(s2);
               s3 = c_xd1.m1483(c_xd1.m1475('='));
            }

            if (s3 == null) {
               s3 = "";
            }

            LPSymbolizer lpsymbolizer = new LPSymbolizer(true);
            lpsymbolizer.loadProblem(c_xd);
            String s4 = C_OA.m1139(lpsymbolizer.problem.toString(), "\\");
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new C_u_(null, 2, new int[]{20, dimension.width - 20}));
            C_f_E c_f_e = new C_f_E(C__C.f1128[k]);
            jpanel.add(c_f_e);
            C_NC c_nc;
            jpanel.add(c_nc = new C_NC(LogicProgram.m1004(trimTitle(s) + ": " + s1 + "\\n" + s3 + "\\n\\l" + s4)));
            c_nc.setLineWrap(true);
            c_nc.setWrapStyleWord(true);
            c_nc.setBackground(LogicProgram.f605[1]);
            vector.add(jpanel);
         }

         if (LogicProgram.m1060(logPrint, getExerciseTitle(s))) {
            LogicProgram.m1082("symdata.txt", "R", c_f_f.f1119);
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
         if (!printIncorrect || k == 1) {
            LPSymbolizer lpsymbolizer = new LPSymbolizer(true);
            lpsymbolizer.loadProblem(c_xd);
            Vector vector1 = new Vector(2);
            String s1 = getProblemStatement(c_xd);
            String s2 = exercises.m1780(s);
            String s3 = null;
            if (s2 != null) {
               C_XD c_xd1 = new C_XD(s2);
               s3 = c_xd1.m1483(c_xd1.m1475('='));
            }

            if (s3 == null) {
               s3 = "";
            }

            String s4 = C_OA.m1139(lpsymbolizer.problem.toString(), "\\");
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new C_u_(null, 2, new int[]{20, dimension.width - 20}));
            C_f_E c_f_e = new C_f_E(C__C.f1128[k]);
            jpanel.add(c_f_e);
            C_NC c_nc;
            jpanel.add(c_nc = new C_NC(LogicProgram.m1004(trimTitle(s) + ": " + s1 + "\\n" + s3 + "\\n" + s4)));
            c_nc.setLineWrap(true);
            c_nc.setWrapStyleWord(true);
            c_nc.setBackground(LogicProgram.f605[1]);
            jpanel.doLayout();
            JPanel jpanel1 = new JPanel();
            jpanel1.setLayout(new BorderLayout());
            jpanel1.setBackground(LogicProgram.f605[1]);
            jpanel1.add(lpsymbolizer.problem, "South");
            Dimension dimension1 = lpsymbolizer.problem.getPreferredSize();
            jpanel1.setPreferredSize(new Dimension(Math.max(dimension.width, dimension1.width), dimension1.height + 20));
            vector1.add(jpanel);
            vector1.add(jpanel1);
            vector.add(vector1);
         }

         if (LogicProgram.m1060(logPrint, getExerciseTitle(s))) {
            LogicProgram.m1082("symdata.txt", "P", c_f_f.f1119);
         }
      }

      return vector;
   }

   static LPSymbolizer getSymParent(Component object) {
      Object object1 = null;

      while (object != null && !((object1 = object.getParent()) instanceof LPSymbolizer)) {
         object = object1;
      }

      return (LPSymbolizer)object1;
   }

   static class C__A extends C_U.C__A {
      boolean f528 = false;

      C__A(C_x_A c_x_a, Rectangle rectangle, String s) {
         super(c_x_a, rectangle, s);
      }

      @Override
      public void run() {
         LPSymbolizer.allocateSymModule(this);
      }

      @Override
      public void m959() {
         if (!this.f528) {
            this.f528 = true;
            LPSymbolizer.startupAfterExercises(this);
         } else {
            LPSymbolizer.continueStartup();
         }
      }
   }
}
