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

class LPSymbolizer extends LogicModule implements C_v_D {
   int fontSize;
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
   static ProblemSelector noDirect = null;
   static ProblemSelector noErrMess = null;
   static ProblemSelector noHints = null;
   static ProblemSelector noCheck = null;
   static ProblemSelector noPrint = null;
   static ProblemSelector noPrintCheck = null;
   static ProblemSelector noPrintErr = null;
   static ProblemSelector monoProbs = null;
   static ProblemSelector equCounts = null;
   static ProblemSelector addToDB = null;
   static ProblemSelector updateDB = null;
   static ProblemSelector chap1 = null;
   static ProblemSelector chap2 = null;
   static ProblemSelector chap3 = null;
   static ProblemSelector chap4 = null;
   static ProblemSelector chap5 = null;
   static ProblemSelector logPrint = null;
   static ProblemSelector logSubmit = null;
   static ProblemSelector needPrint = null;
   static ProblemSelector needSubmit = null;
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
   StyledTextPane symbolized;
   C_y_B scheme;
   C_d_C problem;
   C_x_C lastFocus;
   C_x_C focus;
   static String[] kaplan = LogicProgram.symbols;
   static int moduleIndex = 4;

   static boolean getExercises() {
      if (!C_h_E.loadMessages()) {
         LogicProgram.m971("not001", "the symbolization messages file");
         return false;
      } else if (!getAnswers()) {
         LogicProgram.m971("not002", "the symbolization answer file");
         return false;
      } else {
         resetOptions();
         readOptions(LogicProgram.openDataFile("options", false));
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
         ScrambledReader scrambledreader = LogicProgram.openDataFile("symwork.txt", false);
         if (scrambledreader == null) {
            LogicProgram.m971("not001", "the Symbolization exercise file");
            return null;
         }

         if (!readProblems(scrambledreader, c_h_c, true)) {
            LogicProgram.m971("not002", "the Symbolization exercise file");
            return null;
         }
      }

      if (!flag1) {
         ScrambledReader scrambledreader1 = LogicProgram.m1065("symwork.txt", flag2);
         if (scrambledreader1 != null && (flag ? !readProblems(scrambledreader1, c_h_c, true) : !mergeProblems(scrambledreader1, c_h_c, true))) {
            LogicProgram.m971("not002", "the local Symbolization exercise file");
            return null;
         }
      }

      return c_h_c;
   }

   LPSymbolizer(boolean flag) {
      super(flag);
      this.fontSize = LogicProgram.fontSize;
      this.scheme = new C_y_B();
      C_c_F c_c_f = new C_c_F(this);
      c_c_f.setViewportView(this.scheme);
      c_c_f.getVerticalScrollBar().setUnitIncrement(22);
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      jpanel.add(c_c_f, "Center");
      this.scheme.setForeground(this.colors[0]);
      this.scheme.setBackground(this.colors[1]);
      this.symbolized = new StyledTextPane();
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

   static synchronized void startup(Rectangle rectangle, BusyIndicator busyindicator, String s) {
      if (startups == null) {
         startups = new Vector();
      }

      LPSymbolizer.C__A lpsymbolizer$c__a = new LPSymbolizer.C__A(busyindicator, rectangle, s);
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
         ((LogicModule.C__A)startups.get(i - 1 - j)).m1310();
      }

      while (!startups.isEmpty()) {
         LogicModule.C__A logicmodule$c__a = (LogicModule.C__A)startups.remove(0);
         SwingUtilities.invokeLater(logicmodule$c__a);
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

   static void allocateSymModule(LogicModule.C__A logicmodule$c__a) {
      LPSymbolizer lpsymbolizer = new LPSymbolizer(false);
      instances.addElement(lpsymbolizer);
      if (logicmodule$c__a.f784 == null || newProblem == null) {
         lpsymbolizer.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lpsymbolizer.saveProblem();
         }
      }

      if (logicmodule$c__a.f784 != null) {
         lpsymbolizer.loadProblem(logicmodule$c__a.f784);
         logicmodule$c__a.f784 = null;
      }

      lpsymbolizer.setupFrame(LPInfo.programName + ": Symbolization");
      lpsymbolizer.frame.setBounds(lpsymbolizer.fixModuleRect(logicmodule$c__a.f783));
      lpsymbolizer.frame.setVisible(true);
      lpsymbolizer.splitPane.setDividerLocation(lpsymbolizer.getVisibleRect().width / 4);
      lpsymbolizer.requestFocus();
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
               String s4 = taggedrecord.valueAt(taggedrecord.indexOfTag('C'));
               if (s4 == null) {
                  s4 = s;
               }

               String s5 = "insert into logic_problem (COMMENT,DTCREATION,PROBLEM_NAME,TPROBLEM,TPROBLEM_MD5,TWEB_FORM_PROBLEM,VERSION,SYNTAX,COMMON_NAME)";
               s5 = s5 + " values (" + ServerConnection.m815(s2) + ",GETDATE()," + ServerConnection.m815(s) + "," + ServerConnection.m815(s1) + ",";
               s5 = s5 + ServerConnection.m815(s3) + "," + ServerConnection.m815(s1) + "," + nameVersion(s) + "," + FormulaParser.getSyntax() + ",";
               s5 = s5 + ServerConnection.m815(s4) + ")";
               ServerConnection.m814(s5);
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
               String s4 = taggedrecord.valueAt(taggedrecord.indexOfTag('C'));
               if (s4 == null) {
                  s4 = s;
               }

               String s5 = "update logic_problem set tproblem = " + ServerConnection.m815(s1) + ", tproblem_md5 = " + ServerConnection.m815(s3);
               s5 = s5 + ", tweb_form_problem = " + ServerConnection.m815(s1) + ", comment = " + ServerConnection.m815(s2);
               s5 = s5 + ", version = " + nameVersion(s) + ", common_name = " + ServerConnection.m815(s4);
               s5 = s5 + " where problem_name = " + ServerConnection.m815(s) + " and syntax = " + FormulaParser.getSyntax();
               ServerConnection.m814(s5);
            }
         }
      }
   }

   static String nameVersion(String s) {
      return s != null && s.toLowerCase().startsWith("inval") ? "1" : "NULL";
   }

   void setupFrame(String s) {
      this.frame = new ModuleFrame(s);
      this.frame.module = this;
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
      readOptions(LogicProgram.openDataFile("options", false));
      readOptions(LogicProgram.m1064("options"));
      logNeeds();
      if (needPrint != null && !needPrint.m402() || needSubmit != null && !needSubmit.m402()) {
         C_a_A c_a_a = new C_a_A(readWork());
         c_a_a.m1634(readExercises());
         MainMenu.m2215(hashtable, "symdata.txt", "P", needPrint, c_a_a);
         c_a_a.m1635();
         MainMenu.m2215(hashtable1, "symdata.txt", "S", needSubmit, c_a_a);
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
   }

   @Override
   public Frame getFrame() {
      return this.frame;
   }

   void updateSymbolization() {
      this.symbolized.setText(this.problem.toString());
   }

   @Override
   int getProblemState(TaggedRecord taggedrecord) {
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
            if (c_h_c.f1079 && !c_h_c.m1777(LogicProgram.user).equals(c_h_c.f1076)) {
               System.out.println("Could not digest file: symwork.txt");
               if (!UserSetup.m2101("indigestion", "instructor")) {
                  LogicProgram.m971("not003", "symwork.txt");
                  return false;
               }
            }

            problems = c_h_c;
            C__C.f928 = ProblemEntry.m1816("symwork.txt", problems);
            ProblemEntry.m1815(exercises, C__C.f928);
            return true;
         }
      }
   }

   static C_h_C readWork() {
      if (!LogicProgram.m976()) {
         return null;
      } else {
         C_h_C c_h_c = new C_h_C(true);
         ScrambledReader scrambledreader = LogicProgram.openDataFile("symwork.txt", true);
         if (!LogicProgram.f584 || scrambledreader instanceof PlainRecordReader) {
            if (scrambledreader == null) {
               LogicProgram.m971("not001", "symwork.txt");
               return null;
            }

            if (scrambledreader instanceof PlainRecordReader) {
               c_h_c.f1079 = true;
            }

            if (!readProblems(scrambledreader, c_h_c, false)) {
               LogicProgram.m971("not002", "symwork.txt");
               return null;
            }
         }

         if (!(scrambledreader instanceof PlainRecordReader)) {
            scrambledreader = LogicProgram.m1064("symwork.txt");
            if (scrambledreader != null && !mergeProblems(scrambledreader, c_h_c, false)) {
               LogicProgram.m971("not002", "symwork.txt");
               return null;
            }
         }

         return c_h_c;
      }
   }

   static boolean getUserKey() {
      userKey = new Hashtable();
      ScrambledReader scrambledreader = LogicProgram.openDataFile("keywork.txt", true);
      if (scrambledreader != null && scrambledreader instanceof PlainRecordReader && !readAnswers(scrambledreader, userKey)) {
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

      return LogicModule.readProblems(reader, c_h_c, flag, flag1);
   }

   String getChangedProblem() {
      String s = this.saveProblem();
      String s1 = this.problemIndex == -1 ? newProblem : problems.m1778(this.problemIndex);
      return TaggedRecord.m1500(s).equals(TaggedRecord.m1500(s1)) ? null : s;
   }

   static String getProblemStatement(String s) {
      return getProblemStatement(new TaggedRecord(s));
   }

   static String getProblemStatement(TaggedRecord taggedrecord) {
      String s = taggedrecord.valueAt(taggedrecord.indexOfTag('-'));
      if (s != null) {
         return s;
      } else {
         s = taggedrecord.valueAt(taggedrecord.indexOfTag('+'));
         if (s == null) {
            return null;
         } else {
            DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\:");
            delimitedtokenizer.m1132(s);
            delimitedtokenizer.m1135();
            return delimitedtokenizer.m1133();
         }
      }
   }

   static String getProblemSymbol(String s) {
      return getProblemSymbol(new TaggedRecord(s));
   }

   static String getProblemSymbol(TaggedRecord taggedrecord) {
      String s = taggedrecord.valueAt(taggedrecord.indexOfTag('+'));
      if (s == null) {
         return taggedrecord.indexOfTag('-') == -1 ? null : connSymbol[0];
      } else {
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\:");
         delimitedtokenizer.m1132(s);
         String s1 = delimitedtokenizer.m1135();
         return delimitedtokenizer.m1133() == null ? null : s1;
      }
   }

   static int getHintCount(TaggedRecord taggedrecord) {
      Integer integer = taggedrecord.m1485(taggedrecord.indexOfTag('h'));
      return integer == null ? 0 : integer;
   }

   static boolean hasWork(String s) {
      return hasWork(new TaggedRecord(s));
   }

   static boolean hasWork(TaggedRecord taggedrecord) {
      return taggedrecord.indexOfTag('-') == -1 ? !connSymbol[0].equals(getProblemSymbol(taggedrecord)) : taggedrecord.indexOfTag('+') != -1;
   }

   static String getWork(TaggedRecord taggedrecord) {
      return taggedrecord.indexOfTag('-') == -1 && connSymbol[0].equals(getProblemSymbol(taggedrecord)) ? "" : taggedrecord.m1484("+");
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
         ScrambledReader scrambledreader = LogicProgram.openDataFile("symAnswers", false);
         if (scrambledreader == null || !readAnswers(scrambledreader, answers)) {
            return false;
         }
      }

      ScrambledReader scrambledreader1 = LogicProgram.m1064("symAnswers");
      return scrambledreader1 == null || readAnswers(scrambledreader1, answers);
   }

   static boolean readAnswers(Reader reader, Hashtable hashtable) {
      if (reader == null) {
         return false;
      } else {
         ScrambledReader scrambledreader;
         if (reader instanceof ScrambledReader) {
            scrambledreader = (ScrambledReader)reader;
         } else {
            scrambledreader = new ScrambledReader(reader, LogicProgram.scrambleKey);
         }

         try {
            String s;
            while ((s = scrambledreader.readLine()) != null) {
               if (!TaggedRecord.isBlankOrComment(s)) {
                  String s1 = TaggedRecord.m1493(s);
                  if (s1 != null && !(s1 = s1.trim()).equals("")) {
                     hashtable.put(s1, s);
                  }
               }
            }

            scrambledreader.close();
            return true;
         } catch (IOException ioexception) {
            return false;
         }
      }
   }

   static String[] getProblemAnswers(String s) {
      String s1 = getExerciseTitle(s);
      TaggedRecord taggedrecord;
      boolean flag;
      if (s1 == null) {
         String s2 = problems.m1780(s);
         if (s2 == null) {
            return null;
         }

         taggedrecord = new TaggedRecord(s2);
         flag = true;
         String s3 = taggedrecord.m1497();
         if (s3 != null) {
            return getProblemAnswers(s3);
         }
      } else {
         String s4 = exercises.m1780(s1);
         if (s4 == null) {
            return null;
         }

         taggedrecord = new TaggedRecord(s4);
         flag = false;
      }

      Vector vector = C_d_C.m1707(taggedrecord, flag);
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
      TaggedRecord taggedrecord;
      if (s1 == null) {
         String s2 = problems.m1780(s);
         if (s2 == null) {
            return null;
         }

         taggedrecord = new TaggedRecord(s2);
         String s3 = taggedrecord.m1497();
         if (s3 != null) {
            return getProblemScheme(s3);
         }
      } else {
         String s4 = exercises.m1780(s1);
         if (s4 == null) {
            return null;
         }

         taggedrecord = new TaggedRecord(s4);
      }

      return taggedrecord.valueAt(taggedrecord.indexOfTag('='));
   }

   static void readOptions(Reader reader) {
      if (reader != null) {
         TaggedRecord taggedrecord = new TaggedRecord(reader, true);
         String s = "";

         while (taggedrecord.readNext()) {
            String s1 = taggedrecord.getName();
            if (s1 != null && s1.trim().equalsIgnoreCase("symbolization")) {
               int[] aint = taggedrecord.m1481("+?");
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
                        if (s3.equalsIgnoreCase("noDirect")) {
                           if (noDirect == null) {
                              noDirect = new ProblemSelector();
                           }

                           noDirect.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noErrMess")) {
                           if (noErrMess == null) {
                              noErrMess = new ProblemSelector();
                           }

                           noErrMess.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noHints")) {
                           if (noHints == null) {
                              noHints = new ProblemSelector();
                           }

                           noHints.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("noCheck")) {
                           if (noCheck == null) {
                              noCheck = new ProblemSelector();
                           }

                           noCheck.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
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
                        } else if (s3.equalsIgnoreCase("monoProbs")) {
                           if (monoProbs == null) {
                              monoProbs = new ProblemSelector();
                           }

                           monoProbs.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("equCounts")) {
                           if (equCounts == null) {
                              equCounts = new ProblemSelector();
                           }

                           equCounts.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
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
                        } else if (s3.equalsIgnoreCase("chap3")) {
                           if (chap3 == null) {
                              chap3 = new ProblemSelector();
                           }

                           chap3.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("chap4")) {
                           if (chap4 == null) {
                              chap4 = new ProblemSelector();
                           }

                           chap4.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
                        } else if (s3.equalsIgnoreCase("chap5")) {
                           if (chap5 == null) {
                              chap5 = new ProblemSelector();
                           }

                           chap5.m395(new ProblemSelector(s2.substring(k + 1)).m403(s));
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

   void loadProblem(String s) {
      this.loadProblem(new TaggedRecord(s));
   }

   void loadProblem(TaggedRecord taggedrecord) {
      this.lastDirect = "";
      this.problemIndex = -1;
      this.loadExerciseInfo(taggedrecord);
      this.problem.m1703(taggedrecord);
      this.titlePanel.m1823(this.problem.f1055 == null ? "" : this.problem.f1055);
      this.errorCount = taggedrecord.m1498();
      this.hintCount = getHintCount(taggedrecord);
      this.workTime = taggedrecord.m1499();
      this.loadTime = 0L;
      if (this.problem.m1714() == 0) {
         this.titlePanel.m1825("Answer Not Available");
      } else {
         this.titlePanel.m1825(null);
      }

      this.setProblemTitle(this.problem.f1051);
      this.scheme.m2182(this.problem.f1053);
      this.titlePanel.m1827(taggedrecord.valueAt(taggedrecord.indexOfTag('!')));
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
      this.loadProblem(TaggedRecord.m1509(TaggedRecord.m1508(s, '-')));
   }

   void removeWork() {
      this.problem.m1690(0, false);
      this.problem.m1680(this.problem.f1055);
      this.updateSymbolization();
      this.problem.f1045.f1438.requestFocus();
   }

   static String removeWork(TaggedRecord taggedrecord) {
      String s = TaggedRecord.m1508(taggedrecord.getName(), '$');
      s = s + TaggedRecord.m1508(getProblemStatement(taggedrecord), '-');
      return s + taggedrecord.m1484("=@%u!");
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
      if (UserSetup.m2105("Symbolization")) {
         int[] aint = getExerciseIndices();
         BusyIndicator busyindicator = new BusyIndicator(this);
         Submission submission = ServerConnection.prepareSubmission(busyindicator);
         if (submission != null) {
            if (C_WB.m1450(this, null)) {
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
         int[] aint = C_WB.m1436(this);
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
            String s = problems.m1778(aint[j]);
            TaggedRecord taggedrecord = new TaggedRecord(s);
            String s1 = getProblemStatement(taggedrecord);
            submission.problemMd5 = Scrambler.md5Base64(s1 == null ? "" : s1.trim());
            int k = getProblemState(s, problems, null).state;
            submission.evaluation = C__C.f1128[k];
            submission.work = s;
            submission.problemName = taggedrecord.getName();
            submission.module = moduleAbbrs[moduleIndex];
            Integer integer = LogicProgram.parseInteger(taggedrecord.valueAt(taggedrecord.indexOfTag('h')));
            submission.helpCount = taggedrecord.m1498() + (integer == null ? 0 : integer);
            submission.duration = taggedrecord.m1499();
            boolean flag = LogicProgram.m1060(logSubmit, getExerciseTitle(submission.problemName));
            if (ServerConnection.submit(submission, busyindicator)) {
               vector.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.m1083("symdata.txt", "S", s, submission.m3());
               }
            } else {
               vector1.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.m1082("symdata.txt", "F", s);
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
         c_ld.f520 = C_h_E.getText("symnot009");
         int[] aint = C_WB.m1437(this);
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
            TaggedRecord taggedrecord = new TaggedRecord(s);
            String s1 = getProblemStatement(taggedrecord);
            c_ld.f510 = taggedrecord.getName();
            c_ld.f511 = s1;
            c_ld.f512 = s1;
            c_ld.f514 = getProblemScheme(c_ld.f510);
            c_ld.f513 = moduleAbbrs[moduleIndex];
            c_ld.f515 = getProblemAnswers(c_ld.f510);
            stripUploadAnswers(c_ld.f515);
            if (c_ld.f515 != null && c_ld.f515.length > 0 && !isExercise(c_ld.f510) && ServerConnection.m841(c_ld, busyindicator)) {
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
         TaggedRecord taggedrecord = new TaggedRecord(astring[j]);
         astring[j] = taggedrecord.m1484("+");
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

      return TaggedRecord.m1509(s);
   }

   static boolean saveProblems(int i) {
      if (i != -1) {
         C__C c__c = (C__C)problems.m1779(i);
         getProblemState(c__c.name, problems, c__c);
      }

      return saveProblems();
   }

   static boolean saveProblems() {
      if (!LogicProgram.m976()) {
         return false;
      } else {
         try {
            writeProblems(problems, new FileWriter(new File(LogicProgram.workDir, "symwork.txt")));
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
               C__C c__c = new C__C(TaggedRecord.m1495(s, s2), problems, false);
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
         ProblemEntry problementry = problems.m1779(aint[j]);
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         String s1 = getProblemStatement(taggedrecord);
         String s2 = taggedrecord.valueAt(taggedrecord.indexOfTag('='));
         if (s2 == null) {
            taggedrecord = new TaggedRecord(exercises.m1780(s));
            s2 = taggedrecord.valueAt(taggedrecord.indexOfTag('='));
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
         TaggedRecord taggedrecord = new TaggedRecord(s1);
         String s2 = taggedrecord.getName();
         s = s + trimTitle(s2) + "\n";
         String s3 = getProblemStatement(taggedrecord);
         s = s + s3 + "\n";
         String s4 = taggedrecord.valueAt(taggedrecord.indexOfTag('='));
         if (s4 != null) {
            s = s + s4 + "\n";
         }

         String s5 = taggedrecord.valueAt(taggedrecord.indexOfTag('!'));
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
         ProblemEntry problementry = problems.m1779(aint[j]);
         int k = problementry.state;
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         if (!printIncorrect || k == 1) {
            String s1 = getProblemStatement(taggedrecord);
            String s2 = exercises.m1780(s);
            String s3 = null;
            if (s2 != null) {
               TaggedRecord taggedrecord1 = new TaggedRecord(s2);
               s3 = taggedrecord1.valueAt(taggedrecord1.indexOfTag('='));
            }

            if (s3 == null) {
               s3 = "";
            }

            LPSymbolizer lpsymbolizer = new LPSymbolizer(true);
            lpsymbolizer.loadProblem(taggedrecord);
            String s4 = DelimitedTokenizer.m1139(lpsymbolizer.problem.toString(), "\\");
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
            LogicProgram.m1082("symdata.txt", "R", problementry.name);
         }
      }

      return vector;
   }

   static Vector getPrintProblems(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         ProblemEntry problementry = problems.m1779(aint[j]);
         int k = problementry.state;
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         if (!printIncorrect || k == 1) {
            LPSymbolizer lpsymbolizer = new LPSymbolizer(true);
            lpsymbolizer.loadProblem(taggedrecord);
            Vector vector1 = new Vector(2);
            String s1 = getProblemStatement(taggedrecord);
            String s2 = exercises.m1780(s);
            String s3 = null;
            if (s2 != null) {
               TaggedRecord taggedrecord1 = new TaggedRecord(s2);
               s3 = taggedrecord1.valueAt(taggedrecord1.indexOfTag('='));
            }

            if (s3 == null) {
               s3 = "";
            }

            String s4 = DelimitedTokenizer.m1139(lpsymbolizer.problem.toString(), "\\");
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
            LogicProgram.m1082("symdata.txt", "P", problementry.name);
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

   static class C__A extends LogicModule.C__A {
      boolean f528 = false;

      C__A(BusyIndicator busyindicator, Rectangle rectangle, String s) {
         super(busyindicator, rectangle, s);
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
