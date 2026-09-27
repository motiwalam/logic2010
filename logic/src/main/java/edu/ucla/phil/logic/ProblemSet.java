package edu.ucla.phil.logic;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.SwingUtilities;

abstract class ProblemSet extends Vector implements ModuleConstants {
   Hashtable entriesByName = new Hashtable();
   Hashtable headingsByName = null;
   String storedDigest = null;
   static String[] symbolTable = LogicProgram.symbols;
   boolean changed = false;
   boolean readFromPlainFile = false;
   boolean skipArgumentCheck = false;
   boolean plainColors = false;
   static boolean hideExtraProblems = false;

   abstract String getProblemStatement(TaggedRecord taggedrecord);

   abstract boolean hasWork(TaggedRecord taggedrecord);

   abstract String getWork(TaggedRecord taggedrecord);

   abstract String removeWork(TaggedRecord taggedrecord);

   abstract ProblemEntry createEntry(String s, boolean flag);

   abstract int getModuleIndex();

   synchronized int findInsertIndex(String s) {
      if (s == null) {
         return -1;
      } else {
         String s2 = LogicProgram.stripNamePrefix(s).toUpperCase();
         int i = this.size();

         for (int j = 0; j < i; j++) {
            String s1 = TaggedRecord.nameOf(((ProblemEntry)this.elementAt(j)).name);
            s1 = LogicProgram.stripNamePrefix(s1).toUpperCase();
            int k = s2.compareTo(s1);
            if (k == 0) {
               return -1;
            }

            if (k < 0) {
               return j;
            }
         }

         return i;
      }
   }

   int indexOfName(String s) {
      if (s == null) {
         return -1;
      } else {
         s = LogicProgram.stripNamePrefix(s);
         int i = this.size();

         for (int j = 0; j < i; j++) {
            String s1 = LogicProgram.stripNamePrefix(TaggedRecord.nameOf(((ProblemEntry)this.elementAt(j)).name));
            if (s.equalsIgnoreCase(s1)) {
               return j;
            }
         }

         return -1;
      }
   }

   String getStatement(String s) {
      return this.getProblemStatement(new TaggedRecord(s));
   }

   boolean hasWork(String s) {
      return this.hasWork(new TaggedRecord(s));
   }

   static boolean isExample(TaggedRecord taggedrecord) {
      Hashtable hashtable = taggedrecord.getKeyValues('%');
      return hashtable != null && hashtable.containsKey("eg");
   }

   synchronized int registerEntry(ProblemEntry problementry, boolean flag) {
      String s = TaggedRecord.nameOf(problementry.name);
      if (s == null) {
         return -1;
      } else {
         ProblemEntry problementry1 = (ProblemEntry)this.entriesByName.put(s.trim().toUpperCase(), problementry);
         int i = problementry1 == null ? -1 : this.indexOf(problementry1);
         if (i != -1) {
            int j = 1;

            while (this.getEntry(s + "-" + j) != null) {
               j++;
            }

            s = s + "-" + j;
            problementry1 = this.createEntry(TaggedRecord.withName(problementry1.name, s), flag);
            this.entriesByName.put(s.trim().toUpperCase(), problementry1);
            this.setElementAt(problementry1, i);
         }

         return i;
      }
   }

   synchronized ProblemEntry getEntry(String s) {
      return s == null ? null : (ProblemEntry)this.entriesByName.get(s.trim().toUpperCase());
   }

   synchronized boolean mergeExercises() {
      ProblemSet problemset1 = this.getExercises();
      int i = problemset1 == null ? 0 : problemset1.size();
      int j = 0;
      this.changed = false;

      for (int k = 0; k < i; k++) {
         String s = problemset1.getRecordAt(k);
         int l = this.mergeExercise(s, j);
         if (l != -1) {
            j = l + 1;
         }
      }

      return this.changed;
   }

   synchronized int mergeExercise(String s, int i) {
      TaggedRecord taggedrecord1 = new TaggedRecord(s);
      String s1 = taggedrecord1.getName();
      String s2 = this.getProblemStatement(taggedrecord1);
      if (s1 != null && s2 != null) {
         ProblemEntry problementry = this.getEntry(s1);
         int j = problementry == null ? -1 : this.indexOf(problementry);
         if (j == -1) {
            j = i - 1;
         } else {
            TaggedRecord taggedrecord;
            if (s2.equals(this.getProblemStatement(taggedrecord = new TaggedRecord(problementry.name)))) {
               return j;
            }

            if (!this.hasWork(taggedrecord) || isExample(taggedrecord)) {
               this.removeProblem(j--);
            }
         }

         this.changed = true;
         ProblemEntry problementry1 = this.createEntry(s, true);
         this.registerEntry(problementry1, true);
         this.insertElementAt(problementry1, j + 1);
         return j + 1;
      } else {
         return -1;
      }
   }

   synchronized int addProblem(String s, Vector vector, boolean flag) {
      return this.addProblem(new TaggedRecord(s), vector, flag);
   }

   int addProblem(TaggedRecord taggedrecord, Vector vector, boolean flag) {
      String s = taggedrecord.getName();
      String s1 = taggedrecord.getRawLine();
      if (s == null) {
         System.out.println("problem without title: " + s1);
         return -1;
      } else {
         int i = flag ? this.findInsertIndex(s) : this.size();
         if (i == -1) {
            System.out.println("local problem with duplicate name: " + s);
            return -1;
         } else {
            if (this.headingsByName != null && vector != null) {
               this.headingsByName.put(s, vector);
            }

            ProblemEntry problementry = this.createEntry(s1, true);
            problementry.hidden = taggedrecord.isHidden();
            this.registerEntry(problementry, true);
            if (flag) {
               this.insertElementAt(problementry, i);
            } else {
               this.addElement(problementry);
            }

            return i;
         }
      }
   }

   abstract void restateProblems(LogicModule.ModuleStartupTask modulestartuptask);

   synchronized int replaceProblem(String s, int i) {
      if (TaggedRecord.nameOf(s) == null) {
         return -1;
      } else {
         ProblemEntry problementry = (ProblemEntry)this.elementAt(i);
         if (problementry != null) {
            this.entriesByName.remove(TaggedRecord.nameOf(problementry.name).trim().toUpperCase());
         }

         problementry = this.createEntry(s, false);
         this.registerEntry(problementry, false);
         this.setElementAt(problementry, i);
         return i;
      }
   }

   synchronized void removeProblem(int i) {
      String s = TaggedRecord.nameOf(this.getRecordAt(i));
      if (s != null) {
         this.entriesByName.remove(s.trim().toUpperCase());
      }

      this.removeElementAt(i);
      Vector vector = this.getInstances();
      int j = vector.size();

      for (int k = 0; k < j; k++) {
         LogicModule logicmodule = (LogicModule)vector.elementAt(k);
         if (logicmodule.problemIndex == i) {
            logicmodule.problemIndex = -1;
         } else if (logicmodule.problemIndex > i) {
            logicmodule.problemIndex--;
         }
      }
   }

   synchronized String computeDigest(UserInfo userinfo) {
      return userinfo.computeDigest(new ProblemRecordEnumeration(this), (String)userinfo.get(this.getDigestVersKey()));
   }

   synchronized String getRecordAt(int i) {
      ProblemEntry problementry = this.getEntryAt(i);
      return problementry == null ? null : problementry.name;
   }

   synchronized ProblemEntry getEntryAt(int i) {
      return i >= 0 && i < this.size() ? (ProblemEntry)this.elementAt(i) : null;
   }

   synchronized String getRecord(String s) {
      if (s == null) {
         return null;
      } else {
         ProblemEntry problementry = this.getEntry(s);
         return problementry == null ? null : problementry.name;
      }
   }

   ProblemListView createListView(
      LogicModule logicmodule, ProblemSet problemset1, boolean flag, boolean flag1, ProblemSelector problemselector, ProblemSelector problemselector1
   ) {
      BusyIndicator busyindicator = new BusyIndicator(logicmodule, true);
      ProblemListView problemlistview = new ProblemListView(flag);
      Hashtable hashtable = problemset1 == null ? null : problemset1.headingsByName;
      int i = logicmodule == null ? -1 : logicmodule.problemIndex;
      Color[] acolor = new Color[]{dialogBlack, dialogRed, dialogGreen, dialogRed};
      Color[] acolor1 = new Color[]{dialogBlack, dialogOrange, dialogOrange, dialogOrange};
      int j = this.size();
      int[] aint = new int[j];

      for (int k = 0; k < j; k++) {
         ProblemEntry problementry = this.getEntryAt(k);
         if ((!problementry.hidden || flag1) && (!problementry.extraProblem || !hideExtraProblems)) {
            TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
            String s = taggedrecord.getName();
            Vector vector;
            if (hashtable != null && (vector = (Vector)hashtable.get(s)) != null) {
               int l = vector.size();

               for (int i1 = 0; i1 < l; i1++) {
                  String s1 = (String)vector.elementAt(i1);
                  if (s1 == null || s1.equals("")) {
                     s1 = " ";
                  }

                  String s2 = LogicProgram.expandEscapes(s1);
                  LogicLabel logiclabel = new LogicLabel(s2, 2);
                  logiclabel.setForeground(dialogBlue);
                  logiclabel.setEnabled(false);
                  logiclabel.setOpaque(true);
                  problemlistview.addItem(logiclabel);
               }
            }

            String s3 = lookupName(problemset1, s);
            if (!LogicProgram.selectorMatches(problemselector1, s3)) {
               String s4 = this.getProblemStatement(taggedrecord);
               if (s != null && s3 != null) {
                  s = UserSetup.stripFirstWord(s);
               }

               s = (s == null ? "" : s + ":  ") + (s4 == null ? "" : s4.trim());
               AnswerListLabel answerlistlabel = new AnswerListLabel(LogicProgram.translateSymbols(s, maggie, symbolTable), 2);
               answerlistlabel.setHoverText(LogicProgram.translateSymbols(s, maggie, symbolTable));
               answerlistlabel.setOpaque(true);
               if (!this.plainColors) {
                  boolean flag2 = LogicProgram.selectorMatches(problemselector, s3);
                  answerlistlabel.setForeground(flag2 ? acolor1[problementry.state] : acolor[problementry.state]);
               }

               aint[k] = problemlistview.getItemCount();
               problemlistview.addItem(answerlistlabel);
            }
         }
      }

      j = problemlistview.getItemCount();
      problemlistview.rowToProblem = new int[j];
      if (j > 0) {
         for (int j1 = 0; j1 < j; j1++) {
            problemlistview.rowToProblem[j1] = -1;
         }

         j = aint.length;
         int k1 = 0;

         while (k1 < j) {
            problemlistview.rowToProblem[aint[k1]] = k1++;
         }

         if (i != -1) {
            problemlistview.setSelectedIndex(aint[i]);
         } else if (!flag && j > 0) {
            problemlistview.setSelectedIndex(aint[0]);
         }
      }

      busyindicator.setBusy(false);
      return problemlistview;
   }

   static String markTitle(String s) {
      return hideExtraProblems ? "h " + s : s;
   }

   static String lookupName(ProblemSet problemset, String s) {
      String s1;
      return problemset != null && (s1 = problemset.getRecord(s)) != null ? TaggedRecord.nameOf(s1) : null;
   }

   ProblemSet getExercises() {
      return (ProblemSet)LogicModule.getStaticField(this.getModuleIndex(), "exercises");
   }

   Vector getInstances() {
      return (Vector)LogicModule.getStaticField(this.getModuleIndex(), "instances");
   }

   String getDigestVersKey() {
      return (String)LogicModule.getStaticField(this.getModuleIndex(), "digestVersKey");
   }

   class ProblemRestateTask implements Runnable {
      boolean changed;
      int count;
      int index;
      TaggedRecord record;
      LogicModule module;
      LogicModule.ModuleStartupTask startupTask;
      SymbolizationProblemSet symbolizerProblems;
      SymbolizationNode symbolizerChecker;

      ProblemRestateTask(int i, TaggedRecord taggedrecord, LogicModule logicmodule, LogicModule.ModuleStartupTask logicmodule$modulestartuptask) {
         this.count = i;
         this.record = taggedrecord;
         this.module = logicmodule;
         this.startupTask = logicmodule$modulestartuptask;
         this.changed = false;
         this.symbolizerProblems = null;
         this.symbolizerChecker = null;
         this.index = 0;
      }

      ProblemRestateTask(
         int i,
         TaggedRecord taggedrecord,
         SymbolizationNode symbolizationnode,
         SymbolizationProblemSet symbolizationproblemset,
         LogicModule.ModuleStartupTask logicmodule$modulestartuptask
      ) {
         this.count = i;
         this.record = taggedrecord;
         this.module = null;
         this.startupTask = logicmodule$modulestartuptask;
         this.changed = false;
         this.symbolizerProblems = symbolizationproblemset;
         this.symbolizerChecker = symbolizationnode;
         this.index = 0;
      }

      @Override
      public void run() {
         if (this.index == this.count) {
            if (!this.changed) {
               this.startupTask.continueStartup();
               return;
            }

            this.index = 0;
            this.changed = false;
         }

         ProblemEntry problementry = ProblemSet.this.getEntryAt(this.index);
         if (problementry != null && (problementry.state == 3 || problementry.state == 4)) {
            int i = problementry.state;
            this.record.clear();
            this.record.parse(problementry.name);
            if (this.symbolizerProblems != null && this.symbolizerChecker != null) {
               this.symbolizerChecker.evaluateWork(this.record, this.symbolizerProblems, (SymbolizationEntry)problementry);
            } else {
               problementry.state = this.module.getProblemState(this.record);
            }

            if (problementry.state != i) {
               this.changed = true;
            }
         }

         this.index++;
         SwingUtilities.invokeLater(this);
      }
   }
}
