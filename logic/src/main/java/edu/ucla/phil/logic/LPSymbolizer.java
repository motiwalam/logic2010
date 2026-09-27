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

class LPSymbolizer extends LogicModule implements SymbolizationConstants {
   int fontSize;
   static final String workFileName = "symwork.txt";
   static final String logFileName = "symdata.txt";
   static final String keyFileName = "keywork.txt";
   static final String answerFileName = "symAnswers";
   static final String digestVersKey = "symDigestVers";
   static SymbolizationProblemSet exercises = null;
   static Hashtable answers = null;
   static Hashtable messages = null;
   static SymbolizationProblemSet problems = null;
   static Hashtable userKey = null;
   static Vector instances = new Vector();
   static PrintQueue printQueue = new PrintQueue("Symbolization");
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
   SchemeEditor scheme;
   SymbolizationNode problem;
   SymbolizationTextPanel lastFocus;
   SymbolizationTextPanel focus;
   static String[] kaplan = LogicProgram.symbols;
   static int moduleIndex = 4;

   static boolean getExercises() {
      if (!SymbolizationMessages.loadMessages()) {
         LogicProgram.showFileError("not001", "the symbolization messages file");
         return false;
      } else if (!getAnswers()) {
         LogicProgram.showFileError("not002", "the symbolization answer file");
         return false;
      } else {
         resetOptions();
         readOptions(LogicProgram.openDataFile("options", false));
         readOptions(LogicProgram.openLocalFile("options"));
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

   static SymbolizationProblemSet readExercises() {
      return readExercises(LogicProgram.noCoreProblems, false, false);
   }

   static SymbolizationProblemSet readExercises(boolean flag, boolean flag1, boolean flag2) {
      SymbolizationProblemSet symbolizationproblemset = new SymbolizationProblemSet(false);
      if (!flag) {
         ScrambledReader scrambledreader = LogicProgram.openDataFile("symwork.txt", false);
         if (scrambledreader == null) {
            LogicProgram.showFileError("not001", "the Symbolization exercise file");
            return null;
         }

         if (!readProblems(scrambledreader, symbolizationproblemset, true)) {
            LogicProgram.showFileError("not002", "the Symbolization exercise file");
            return null;
         }
      }

      if (!flag1) {
         ScrambledReader scrambledreader1 = LogicProgram.openLocalFile("symwork.txt", flag2);
         if (scrambledreader1 != null
            && (flag ? !readProblems(scrambledreader1, symbolizationproblemset, true) : !mergeProblems(scrambledreader1, symbolizationproblemset, true))) {
            LogicProgram.showFileError("not002", "the local Symbolization exercise file");
            return null;
         }
      }

      return symbolizationproblemset;
   }

   LPSymbolizer(boolean flag) {
      super(flag);
      this.fontSize = LogicProgram.fontSize;
      this.scheme = new SchemeEditor();
      SymbolizationScrollPane symbolizationscrollpane = new SymbolizationScrollPane(this);
      symbolizationscrollpane.setViewportView(this.scheme);
      symbolizationscrollpane.getVerticalScrollBar().setUnitIncrement(22);
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      jpanel.add(symbolizationscrollpane, "Center");
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
      this.symbolized.setWrapLines(true);
      this.symbolized.setWrapWords(true);
      this.symbolized.setEditable(false);
      this.problem = new SymbolizationNode(this, 0);
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
      this.add(SymbolizationToolbar.create(this), "South");
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

      LPSymbolizer.SymbolizerStartup lpsymbolizer$symbolizerstartup = new LPSymbolizer.SymbolizerStartup(busyindicator, rectangle, s);
      startups.add(lpsymbolizer$symbolizerstartup);
      if (startups.size() <= 1) {
         if (exercises == null) {
            if (!getExercises()) {
               lpsymbolizer$symbolizerstartup.stopBusyIndicator();
               startups.remove(lpsymbolizer$symbolizerstartup);
               return;
            }

            exercises.restateProblems(lpsymbolizer$symbolizerstartup);
         } else {
            lpsymbolizer$symbolizerstartup.continueStartup();
         }
      }
   }

   static void startupAfterExercises(LPSymbolizer.SymbolizerStartup lpsymbolizer$symbolizerstartup) {
      if (problems == null) {
         if (!getProblems()) {
            lpsymbolizer$symbolizerstartup.stopBusyIndicator();
            startups.remove(lpsymbolizer$symbolizerstartup);
            return;
         }

         if (problems.mergeExercises()) {
            saveProblems();
         }

         problems.restateProblems(lpsymbolizer$symbolizerstartup);
      } else {
         lpsymbolizer$symbolizerstartup.continueStartup();
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
      SymbolizationToolbar symbolizationtoolbar = SymbolizationToolbar.create(this);
      Dimension dimension = symbolizationtoolbar.getPreferredSize();
      if (rectangle.width < dimension.width) {
         rectangle.width = dimension.width;
      }

      return rectangle;
   }

   static void allocateSymModule(LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
      LPSymbolizer lpsymbolizer = new LPSymbolizer(false);
      instances.addElement(lpsymbolizer);
      if (logicmodule$modulestartuptask.problemName == null || newProblem == null) {
         lpsymbolizer.loadProblem((String)null);
         if (newProblem == null) {
            newProblem = lpsymbolizer.saveProblem();
         }
      }

      if (logicmodule$modulestartuptask.problemName != null) {
         lpsymbolizer.loadProblem(logicmodule$modulestartuptask.problemName);
         logicmodule$modulestartuptask.problemName = null;
      }

      lpsymbolizer.setupFrame(LPInfo.programName + ": Symbolization");
      lpsymbolizer.frame.setBounds(lpsymbolizer.fixModuleRect(logicmodule$modulestartuptask.bounds));
      lpsymbolizer.frame.setVisible(true);
      lpsymbolizer.splitPane.setDividerLocation(lpsymbolizer.getVisibleRect().width / 4);
      lpsymbolizer.requestFocus();
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
               String s4 = taggedrecord.valueAt(taggedrecord.indexOfTag('C'));
               if (s4 == null) {
                  s4 = s;
               }

               String s5 = "insert into logic_problem (COMMENT,DTCREATION,PROBLEM_NAME,TPROBLEM,TPROBLEM_MD5,TWEB_FORM_PROBLEM,VERSION,SYNTAX,COMMON_NAME)";
               s5 = s5 + " values (" + ServerConnection.sqlQuote(s2) + ",GETDATE()," + ServerConnection.sqlQuote(s) + "," + ServerConnection.sqlQuote(s1) + ",";
               s5 = s5 + ServerConnection.sqlQuote(s3) + "," + ServerConnection.sqlQuote(s1) + "," + nameVersion(s) + "," + FormulaParser.getSyntax() + ",";
               s5 = s5 + ServerConnection.sqlQuote(s4) + ")";
               ServerConnection.stubReturnsNull(s5);
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
               String s4 = taggedrecord.valueAt(taggedrecord.indexOfTag('C'));
               if (s4 == null) {
                  s4 = s;
               }

               String s5 = "update logic_problem set tproblem = " + ServerConnection.sqlQuote(s1) + ", tproblem_md5 = " + ServerConnection.sqlQuote(s3);
               s5 = s5 + ", tweb_form_problem = " + ServerConnection.sqlQuote(s1) + ", comment = " + ServerConnection.sqlQuote(s2);
               s5 = s5 + ", version = " + nameVersion(s) + ", common_name = " + ServerConnection.sqlQuote(s4);
               s5 = s5 + " where problem_name = " + ServerConnection.sqlQuote(s) + " and syntax = " + FormulaParser.getSyntax();
               ServerConnection.stubReturnsNull(s5);
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
      if (!flag && !SymbolizationDialogs.confirmSaveChanges(this, null)) {
         return false;
      } else {
         this.reset();
         synchronized (moduleClasses[4]) {
            instances.removeElement(this);
            if (instances.isEmpty()) {
               PrintTask.waitForQueue(printQueue);
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
      return SymbolizationDialogs.confirmSaveChanges(this, null);
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
      readOptions(LogicProgram.openLocalFile("options"));
      logNeeds();
      if (needPrint != null && !needPrint.isEmpty() || needSubmit != null && !needSubmit.isEmpty()) {
         ProblemRecordEnumeration problemrecordenumeration = new ProblemRecordEnumeration(readWork());
         problemrecordenumeration.retainExisting(readExercises());
         MainMenu.mergeSubmitStatus(hashtable, "symdata.txt", "P", needPrint, problemrecordenumeration);
         problemrecordenumeration.reset();
         MainMenu.mergeSubmitStatus(hashtable1, "symdata.txt", "S", needSubmit, problemrecordenumeration);
      }

      resetOptions();
      return true;
   }

   static Vector getChangedProblems() {
      ProblemRecordEnumeration problemrecordenumeration = new ProblemRecordEnumeration(readWork());
      Hashtable hashtable = LogicProgram.checkSubmitLog("symdata.txt", "S", needSubmit, problemrecordenumeration);
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

   static SymbolizationEntry getProblemState(String s, SymbolizationProblemSet symbolizationproblemset, SymbolizationEntry symbolizationentry) {
      if (symbolizationentry == null) {
         symbolizationentry = new SymbolizationEntry(s, symbolizationproblemset, true);
      }

      if (!hasWork(s)) {
         symbolizationentry.resetState();
      } else {
         SymbolizationNode.evaluateWork(s, symbolizationproblemset, symbolizationentry);
      }

      return symbolizationentry;
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

   static synchronized boolean getProblems() {
      if (problems != null) {
         return true;
      } else if (!getUserKey()) {
         return false;
      } else {
         SymbolizationProblemSet symbolizationproblemset = readWork();
         if (symbolizationproblemset == null) {
            return false;
         } else {
            if (symbolizationproblemset.readFromPlainFile
               && !symbolizationproblemset.computeDigest(LogicProgram.user).equals(symbolizationproblemset.storedDigest)) {
               System.out.println("Could not digest file: symwork.txt");
               if (!UserSetup.hasAccess("indigestion", "instructor")) {
                  LogicProgram.showFileError("not003", "symwork.txt");
                  return false;
               }
            }

            problems = symbolizationproblemset;
            SymbolizationEntry.knownNames = ProblemEntry.findExtraProblems("symwork.txt", problems);
            ProblemEntry.markExtraProblems(exercises, SymbolizationEntry.knownNames);
            return true;
         }
      }
   }

   static SymbolizationProblemSet readWork() {
      if (!LogicProgram.checkSameUser()) {
         return null;
      } else {
         SymbolizationProblemSet symbolizationproblemset = new SymbolizationProblemSet(true);
         ScrambledReader scrambledreader = LogicProgram.openDataFile("symwork.txt", true);
         if (!LogicProgram.noCoreProblems || scrambledreader instanceof PlainRecordReader) {
            if (scrambledreader == null) {
               LogicProgram.showFileError("not001", "symwork.txt");
               return null;
            }

            if (scrambledreader instanceof PlainRecordReader) {
               symbolizationproblemset.readFromPlainFile = true;
            }

            if (!readProblems(scrambledreader, symbolizationproblemset, false)) {
               LogicProgram.showFileError("not002", "symwork.txt");
               return null;
            }
         }

         if (!(scrambledreader instanceof PlainRecordReader)) {
            scrambledreader = LogicProgram.openLocalFile("symwork.txt");
            if (scrambledreader != null && !mergeProblems(scrambledreader, symbolizationproblemset, false)) {
               LogicProgram.showFileError("not002", "symwork.txt");
               return null;
            }
         }

         return symbolizationproblemset;
      }
   }

   static boolean getUserKey() {
      userKey = new Hashtable();
      ScrambledReader scrambledreader = LogicProgram.openDataFile("keywork.txt", true);
      if (scrambledreader != null && scrambledreader instanceof PlainRecordReader && !readAnswers(scrambledreader, userKey)) {
         LogicProgram.showFileError("not002", "keywork.txt");
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
            BufferedWriter bufferedwriter = new BufferedWriter(LogicProgram.openWriter("keywork.txt", false, true));

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

   static boolean readProblems(Reader reader, SymbolizationProblemSet symbolizationproblemset, boolean flag) {
      return readProblems(reader, symbolizationproblemset, flag, false);
   }

   static boolean mergeProblems(Reader reader, SymbolizationProblemSet symbolizationproblemset, boolean flag) {
      return readProblems(reader, symbolizationproblemset, flag, true);
   }

   static boolean readProblems(Reader reader, SymbolizationProblemSet symbolizationproblemset, boolean flag, boolean flag1) {
      if (flag && symbolizationproblemset.answerGroups == null) {
         symbolizationproblemset.answerGroups = new Hashtable();
      }

      return LogicModule.readProblems(reader, symbolizationproblemset, flag, flag1);
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
      String s = taggedrecord.valueAt(taggedrecord.indexOfTag('-'));
      if (s != null) {
         return s;
      } else {
         s = taggedrecord.valueAt(taggedrecord.indexOfTag('+'));
         if (s == null) {
            return null;
         } else {
            DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\:");
            delimitedtokenizer.setInput(s);
            delimitedtokenizer.nextToken();
            return delimitedtokenizer.getRemaining();
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
         delimitedtokenizer.setInput(s);
         String s1 = delimitedtokenizer.nextToken();
         return delimitedtokenizer.getRemaining() == null ? null : s1;
      }
   }

   static int getHintCount(TaggedRecord taggedrecord) {
      Integer integer = taggedrecord.intValueAt(taggedrecord.indexOfTag('h'));
      return integer == null ? 0 : integer;
   }

   static boolean hasWork(String s) {
      return hasWork(new TaggedRecord(s));
   }

   static boolean hasWork(TaggedRecord taggedrecord) {
      return taggedrecord.indexOfTag('-') == -1 ? !connSymbol[0].equals(getProblemSymbol(taggedrecord)) : taggedrecord.indexOfTag('+') != -1;
   }

   static String getWork(TaggedRecord taggedrecord) {
      return taggedrecord.indexOfTag('-') == -1 && connSymbol[0].equals(getProblemSymbol(taggedrecord)) ? "" : taggedrecord.formatFields("+");
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
      this.directEntryDisabled = LogicProgram.selectorMatches(noDirect, s);
      this.errorMessagesDisabled = LogicProgram.selectorMatches(this.forPrint ? noPrintErr : noErrMess, s);
      this.hintsDisabled = LogicProgram.selectorMatches(noHints, s);
      this.checkDisabled = LogicProgram.selectorMatches(this.forPrint ? noPrintCheck : noCheck, s);
      if (LogicProgram.selectorMatches(chap1, s)) {
         this.chapter = new Integer(1);
      } else if (LogicProgram.selectorMatches(chap2, s)) {
         this.chapter = new Integer(2);
      } else if (LogicProgram.selectorMatches(chap3, s)) {
         this.chapter = new Integer(3);
      } else if (LogicProgram.selectorMatches(chap4, s)) {
         this.chapter = new Integer(4);
      } else if (LogicProgram.selectorMatches(chap5, s)) {
         this.chapter = new Integer(5);
      } else {
         this.chapter = null;
      }
   }

   static boolean equivalentCounts(String s) {
      return LogicProgram.selectorMatches(equCounts, getExerciseTitle(s));
   }

   void checkProblem() {
      this.problem.clearErrors();
      if (this.problem.countAnswers() != 0) {
         int i = -1;
         if (this.problem.isIncomplete()) {
            this.titlePanel.setStatus("Incomplete");
         } else if ((i = this.problem.findMatchingAnswer()) != -1) {
            int j = this.problem.findDuplicateSolution(i, problems);
            if (j != -1) {
               this.titlePanel.setStatus("Duplicate of\n" + SymbolizationNode.getAnswerSetName(j, this.problem.answerGroup));
            } else {
               this.titlePanel.setStatus("Correct");
            }
         } else if ((i = this.problem.findEquivalentAnswer()) != -1) {
            int k = this.problem.findDuplicateSolution(i, problems);
            if (k != -1) {
               this.titlePanel.setStatus("Duplicate of\n" + SymbolizationNode.getAnswerSetName(k, this.problem.answerGroup));
            } else if (equivalentCounts(this.problem.problemName)) {
               this.titlePanel.setStatus("Correct Equivalent");
            } else {
               this.titlePanel.setStatus("Equivalent, but Incorrect");
            }
         } else {
            SymbolizationNode symbolizationnode = this.problem.findClosestAnswer();
            ErrorMarker errormarker = new ErrorMarker();
            this.problem.matchTree(symbolizationnode, errormarker);
            this.titlePanel.setStatus(errormarker.quantifierUnrestricted ? "Incorrect: Quantifier Unrestricted" : "Incorrect");
         }
      }
   }

   static boolean getAnswers() {
      answers = new Hashtable();
      if (!LogicProgram.noCoreProblems) {
         ScrambledReader scrambledreader = LogicProgram.openDataFile("symAnswers", false);
         if (scrambledreader == null || !readAnswers(scrambledreader, answers)) {
            return false;
         }
      }

      ScrambledReader scrambledreader1 = LogicProgram.openLocalFile("symAnswers");
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
                  String s1 = TaggedRecord.nameOf(s);
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
         String s2 = problems.getRecord(s);
         if (s2 == null) {
            return null;
         }

         taggedrecord = new TaggedRecord(s2);
         flag = true;
         String s3 = taggedrecord.getOriginalName();
         if (s3 != null) {
            return getProblemAnswers(s3);
         }
      } else {
         String s4 = exercises.getRecord(s1);
         if (s4 == null) {
            return null;
         }

         taggedrecord = new TaggedRecord(s4);
         flag = false;
      }

      Vector vector = SymbolizationNode.lookupAnswers(taggedrecord, flag);
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
         String s2 = problems.getRecord(s);
         if (s2 == null) {
            return null;
         }

         taggedrecord = new TaggedRecord(s2);
         String s3 = taggedrecord.getOriginalName();
         if (s3 != null) {
            return getProblemScheme(s3);
         }
      } else {
         String s4 = exercises.getRecord(s1);
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
                        if (s3.equalsIgnoreCase("noDirect")) {
                           if (noDirect == null) {
                              noDirect = new ProblemSelector();
                           }

                           noDirect.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noErrMess")) {
                           if (noErrMess == null) {
                              noErrMess = new ProblemSelector();
                           }

                           noErrMess.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("noHints")) {
                           if (noHints == null) {
                              noHints = new ProblemSelector();
                           }

                           noHints.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
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
                        } else if (s3.equalsIgnoreCase("equCounts")) {
                           if (equCounts == null) {
                              equCounts = new ProblemSelector();
                           }

                           equCounts.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
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
                        } else if (s3.equalsIgnoreCase("chap3")) {
                           if (chap3 == null) {
                              chap3 = new ProblemSelector();
                           }

                           chap3.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("chap4")) {
                           if (chap4 == null) {
                              chap4 = new ProblemSelector();
                           }

                           chap4.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
                        } else if (s3.equalsIgnoreCase("chap5")) {
                           if (chap5 == null) {
                              chap5 = new ProblemSelector();
                           }

                           chap5.union(new ProblemSelector(s2.substring(k + 1)).addPrefix(s));
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

   void loadProblem(String s) {
      this.loadProblem(new TaggedRecord(s));
   }

   void loadProblem(TaggedRecord taggedrecord) {
      this.lastDirect = "";
      this.problemIndex = -1;
      this.loadExerciseInfo(taggedrecord);
      this.problem.loadRecord(taggedrecord);
      this.titlePanel.setStatement(this.problem.statement == null ? "" : this.problem.statement);
      this.errorCount = taggedrecord.getErrorCount();
      this.hintCount = getHintCount(taggedrecord);
      this.workTime = taggedrecord.getTimestamp();
      this.loadTime = 0L;
      if (this.problem.countAnswers() == 0) {
         this.titlePanel.setStatus("Answer Not Available");
      } else {
         this.titlePanel.setStatus(null);
      }

      this.setProblemTitle(this.problem.problemName);
      this.scheme.setScheme(this.problem.scheme);
      this.titlePanel.setNote(taggedrecord.valueAt(taggedrecord.indexOfTag('!')));
      this.updateWorkTime();
      this.problem.textPanel.textPane.requestFocus();
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
      this.loadProblem(TaggedRecord.toLine(TaggedRecord.formatField(s, '-')));
   }

   void removeWork() {
      this.problem.setConnective(0, false);
      this.problem.setEnglishText(this.problem.statement);
      this.updateSymbolization();
      this.problem.textPanel.textPane.requestFocus();
   }

   static String removeWork(TaggedRecord taggedrecord) {
      String s = TaggedRecord.formatField(taggedrecord.getName(), '$');
      s = s + TaggedRecord.formatField(getProblemStatement(taggedrecord), '-');
      return s + taggedrecord.formatFields("=@%u!");
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
      if (UserSetup.confirmSubmitAll("Symbolization")) {
         int[] aint = getExerciseIndices();
         BusyIndicator busyindicator = new BusyIndicator(this);
         Submission submission = ServerConnection.prepareSubmission(busyindicator);
         if (submission != null) {
            if (SymbolizationDialogs.confirmSaveChanges(this, null)) {
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
         int[] aint = SymbolizationDialogs.chooseProblemsToSubmit(this);
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
            int k = getProblemState(s, problems, null).state;
            submission.evaluation = SymbolizationEntry.STATE_CODES[k];
            submission.work = s;
            submission.problemName = taggedrecord.getName();
            submission.module = moduleAbbrs[moduleIndex];
            Integer integer = LogicProgram.parseInteger(taggedrecord.valueAt(taggedrecord.indexOfTag('h')));
            submission.helpCount = taggedrecord.getErrorCount() + (integer == null ? 0 : integer);
            submission.duration = taggedrecord.getTimestamp();
            boolean flag = LogicProgram.selectorMatches(logSubmit, getExerciseTitle(submission.problemName));
            if (ServerConnection.submit(submission, busyindicator)) {
               vector.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.appendSubmitLog("symdata.txt", "S", s, submission.getLogRecord());
               }
            } else {
               vector1.addElement(trimTitle(submission.problemName));
               if (flag) {
                  LogicProgram.appendSubmitLog("symdata.txt", "F", s);
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
         problemupload.resultText = SymbolizationMessages.getText("symnot009");
         int[] aint = SymbolizationDialogs.chooseProblemsToUpload(this);
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
            TaggedRecord taggedrecord = new TaggedRecord(s);
            String s1 = getProblemStatement(taggedrecord);
            problemupload.problemName = taggedrecord.getName();
            problemupload.text = s1;
            problemupload.webText = s1;
            problemupload.aux = getProblemScheme(problemupload.problemName);
            problemupload.type = moduleAbbrs[moduleIndex];
            problemupload.answers = getProblemAnswers(problemupload.problemName);
            stripUploadAnswers(problemupload.answers);
            if (problemupload.answers != null
               && problemupload.answers.length > 0
               && !isExercise(problemupload.problemName)
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

   static void stripUploadAnswers(String[] astring) {
      int i = astring == null ? 0 : astring.length;

      for (int j = 0; j < i; j++) {
         TaggedRecord taggedrecord = new TaggedRecord(astring[j]);
         astring[j] = taggedrecord.formatFields("+");
      }
   }

   void newProblem() {
      this.loadProblem(newProblem);
   }

   String saveProblem() {
      String s = this.problem.toRecord(true);
      if (this.errorCount != 0) {
         s = s + this.errorCount + "`e";
      }

      if (this.hintCount != 0) {
         s = s + this.hintCount + "`h";
      }

      if (this.updateWorkTime() != 0L) {
         s = s + this.workTime + "`t";
      }

      return TaggedRecord.toLine(s);
   }

   static boolean saveProblems(int i) {
      if (i != -1) {
         SymbolizationEntry symbolizationentry = (SymbolizationEntry)problems.getEntryAt(i);
         getProblemState(symbolizationentry.name, problems, symbolizationentry);
      }

      return saveProblems();
   }

   static boolean saveProblems() {
      if (!LogicProgram.checkSameUser()) {
         return false;
      } else {
         try {
            writeProblems(problems, new FileWriter(new File(LogicProgram.workDir, "symwork.txt")));
            writeUserKey();
            return true;
         } catch (IOException ioexception) {
            LogicProgram.showFileError("not004", "symwork.txt");
            return false;
         }
      }
   }

   boolean saveRenamed(String s) {
      if (s == null) {
         return true;
      } else {
         String s1 = this.problem.problemName;
         String s2 = this.problem.originalName;
         int i = this.problemIndex;
         if (s2 == null) {
            this.problem.originalName = s1;
            s = this.saveProblem();
         }

         this.problemIndex = -1;
         if (!this.saveProblems(s, true)) {
            this.problemIndex = i;
            this.problem.originalName = s2;
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
               String s2 = SymbolizationDialogs.askProblemName(flag ? this.problem.problemName : null);
               if (s2 == null) {
                  return false;
               }

               this.setProblemTitle(s2);
               SymbolizationEntry symbolizationentry = new SymbolizationEntry(TaggedRecord.withName(s, s2), problems, false);
               this.problemIndex = problems.registerEntry(symbolizationentry, false);
               this.problemIndex = this.problemIndex == -1 ? problems.size() : this.problemIndex + 1;
               problems.insertElementAt(symbolizationentry, this.problemIndex);
            } else {
               s1 = problems.getRecordAt(this.problemIndex);
               problems.replaceProblem(s, this.problemIndex);
            }

            if (!saveProblems(this.problemIndex)) {
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

   static Vector getStatements(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         ProblemEntry problementry = problems.getEntryAt(aint[j]);
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         String s1 = getProblemStatement(taggedrecord);
         String s2 = taggedrecord.valueAt(taggedrecord.indexOfTag('='));
         if (s2 == null) {
            taggedrecord = new TaggedRecord(exercises.getRecord(s));
            s2 = taggedrecord.valueAt(taggedrecord.indexOfTag('='));
         }

         if (s2 == null) {
            s2 = "No Scheme Available";
         }

         JPanel jpanel = new JPanel();
         jpanel.setLayout(new FixedColumnLayout(null, 1, new int[]{dimension.width}));
         LogicTextArea logictextarea;
         jpanel.add(logictextarea = new LogicTextArea(LogicProgram.expandEscapes("\\l" + s + ": " + s1 + "\n" + s2)));
         logictextarea.setLineWrap(true);
         logictextarea.setWrapStyleWord(true);
         logictextarea.setBackground(LogicProgram.printColors[1]);
         vector.add(jpanel);
      }

      return vector;
   }

   static Vector getAnswers(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         String s = "";
         String s1 = exercises.getRecordAt(aint[j]);
         TaggedRecord taggedrecord = new TaggedRecord(s1);
         String s2 = taggedrecord.getName();
         s = s + trimTitle(s2) + "\n";
         String s3 = getProblemStatement(taggedrecord);
         String s6 = s + s3 + "\n";
         String s4 = taggedrecord.valueAt(taggedrecord.indexOfTag('='));
         if (s4 != null) {
            s6 = s6 + s4 + "\n";
         }

         String s5 = taggedrecord.valueAt(taggedrecord.indexOfTag('!'));
         if (s5 != null) {
            s6 = s6 + s5 + "\n";
         }

         LPSymbolizer lpsymbolizer = new LPSymbolizer(true);
         lpsymbolizer.loadProblem(s1);
         Vector vector1 = lpsymbolizer.problem.answers;
         int k = vector1 == null ? 0 : vector1.size();

         for (int l = 0; l < k; l++) {
            lpsymbolizer.loadProblem((String)vector1.elementAt(l));
            s6 = s6 + lpsymbolizer.problem.toString() + "\n";
         }

         JPanel jpanel = new JPanel();
         jpanel.setLayout(new FixedColumnLayout(null, 1, new int[]{dimension.width}));
         LogicTextArea logictextarea;
         jpanel.add(logictextarea = new LogicTextArea(s6));
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
            String s2 = exercises.getRecord(s);
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
            String s4 = DelimitedTokenizer.escape(lpsymbolizer.problem.toString(), "\\");
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new FixedColumnLayout(null, 2, new int[]{20, dimension.width - 20}));
            WrappedTextPanel wrappedtextpanel = new WrappedTextPanel(SymbolizationEntry.STATE_CODES[k]);
            jpanel.add(wrappedtextpanel);
            LogicTextArea logictextarea;
            jpanel.add(logictextarea = new LogicTextArea(LogicProgram.expandEscapes(trimTitle(s) + ": " + s1 + "\\n" + s3 + "\\n\\l" + s4)));
            logictextarea.setLineWrap(true);
            logictextarea.setWrapStyleWord(true);
            logictextarea.setBackground(LogicProgram.printColors[1]);
            vector.add(jpanel);
         }

         if (LogicProgram.selectorMatches(logPrint, getExerciseTitle(s))) {
            LogicProgram.appendSubmitLog("symdata.txt", "R", problementry.name);
         }
      }

      return vector;
   }

   static Vector getPrintProblems(int[] aint, Dimension dimension) {
      int i = aint.length;
      Vector vector = new Vector(i);

      for (int j = 0; j < i; j++) {
         ProblemEntry problementry = problems.getEntryAt(aint[j]);
         int k = problementry.state;
         TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
         String s = taggedrecord.getName();
         if (!printIncorrect || k == 1) {
            LPSymbolizer lpsymbolizer = new LPSymbolizer(true);
            lpsymbolizer.loadProblem(taggedrecord);
            Vector vector1 = new Vector(2);
            String s1 = getProblemStatement(taggedrecord);
            String s2 = exercises.getRecord(s);
            String s3 = null;
            if (s2 != null) {
               TaggedRecord taggedrecord1 = new TaggedRecord(s2);
               s3 = taggedrecord1.valueAt(taggedrecord1.indexOfTag('='));
            }

            if (s3 == null) {
               s3 = "";
            }

            String s4 = DelimitedTokenizer.escape(lpsymbolizer.problem.toString(), "\\");
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new FixedColumnLayout(null, 2, new int[]{20, dimension.width - 20}));
            WrappedTextPanel wrappedtextpanel = new WrappedTextPanel(SymbolizationEntry.STATE_CODES[k]);
            jpanel.add(wrappedtextpanel);
            LogicTextArea logictextarea;
            jpanel.add(logictextarea = new LogicTextArea(LogicProgram.expandEscapes(trimTitle(s) + ": " + s1 + "\\n" + s3 + "\\n" + s4)));
            logictextarea.setLineWrap(true);
            logictextarea.setWrapStyleWord(true);
            logictextarea.setBackground(LogicProgram.printColors[1]);
            jpanel.doLayout();
            JPanel jpanel1 = new JPanel();
            jpanel1.setLayout(new BorderLayout());
            jpanel1.setBackground(LogicProgram.printColors[1]);
            jpanel1.add(lpsymbolizer.problem, "South");
            Dimension dimension1 = lpsymbolizer.problem.getPreferredSize();
            jpanel1.setPreferredSize(new Dimension(Math.max(dimension.width, dimension1.width), dimension1.height + 20));
            vector1.add(jpanel);
            vector1.add(jpanel1);
            vector.add(vector1);
         }

         if (LogicProgram.selectorMatches(logPrint, getExerciseTitle(s))) {
            LogicProgram.appendSubmitLog("symdata.txt", "P", problementry.name);
         }
      }

      return vector;
   }

   static LPSymbolizer getSymParent(Component object) {
      Component object1 = null;

      while (object != null && !((object1 = object.getParent()) instanceof LPSymbolizer)) {
         object = object1;
      }

      return (LPSymbolizer)object1;
   }

   static class SymbolizerStartup extends LogicModule.ModuleStartupTask {
      boolean exercisesReady = false;

      SymbolizerStartup(BusyIndicator busyindicator, Rectangle rectangle, String s) {
         super(busyindicator, rectangle, s);
      }

      @Override
      public void run() {
         LPSymbolizer.allocateSymModule(this);
      }

      @Override
      public void continueStartup() {
         if (!this.exercisesReady) {
            this.exercisesReady = true;
            LPSymbolizer.startupAfterExercises(this);
         } else {
            LPSymbolizer.continueStartup();
         }
      }
   }
}
