package edu.ucla.phil.logic;

import java.util.Vector;

class AnswerManagerHandler extends DialogHandler implements SymbolizationConstants {
   static String[] displaySymbols = LogicProgram.symbols;

   AnswerManagerHandler(String s) {
      super(s);
   }

   @Override
   boolean handleChoice(MessageDialog messagedialog) {
      String s = this.getSelectedAction(messagedialog);
      LPSymbolizer lpsymbolizer = (LPSymbolizer)this.getProperty("symbolizer");
      ProblemListView problemlistview = (ProblemListView)this.getProperty("list");
      if (lpsymbolizer == null) {
         return true;
      } else if (s == null) {
         return false;
      } else if (s.equalsIgnoreCase("ok")) {
         return true;
      } else if (s.equalsIgnoreCase("add")) {
         return this.addAnswer(lpsymbolizer, problemlistview);
      } else if (s.equalsIgnoreCase("load")) {
         int[] aint = problemlistview.getSelectedProblems(null);
         if (aint.length != 1) {
            return false;
         } else {
            TaggedRecord taggedrecord = new TaggedRecord((String)lpsymbolizer.problem.answers.elementAt(aint[0]));
            lpsymbolizer.problem.loadRecord(taggedrecord, false, false);
            return true;
         }
      } else if (s.equalsIgnoreCase("delete")) {
         return this.deleteSelectedAnswers(lpsymbolizer, problemlistview);
      } else if (s.equalsIgnoreCase("replace")) {
         this.deleteSelectedAnswers(lpsymbolizer, problemlistview);
         return this.addAnswer(lpsymbolizer, problemlistview);
      } else if (s.equalsIgnoreCase("warn")) {
         MessageDialog.showMessage(SymbolizationMessages.get("symnot006"), null, null, null);
         return false;
      } else if (s.equalsIgnoreCase("help")) {
         MessageDialog.showMessage(SymbolizationMessages.get("symnot007"), null, null, null);
         return false;
      } else {
         return false;
      }
   }

   private boolean addAnswer(LPSymbolizer lpsymbolizer, ProblemListView problemlistview) {
      if (lpsymbolizer.problem.problemName != null || lpsymbolizer.saveProblems(lpsymbolizer.saveProblem()) && lpsymbolizer.problem.problemName != null) {
         int i = 1;

         String s;
         while (LPSymbolizer.userKey.get(s = lpsymbolizer.problem.problemName + "-" + i) != null) {
            i++;
         }

         String s1 = lpsymbolizer.problem.problemName;
         boolean flag = lpsymbolizer.problem.userAnswerKey;
         lpsymbolizer.problem.problemName = s;
         lpsymbolizer.problem.userAnswerKey = false;
         String s2 = lpsymbolizer.problem.toRecord(true);
         lpsymbolizer.problem.problemName = s1;
         lpsymbolizer.problem.userAnswerKey = flag;
         LPSymbolizer.userKey.put(s, s2);
         if (lpsymbolizer.problem.answerKeys != null && lpsymbolizer.problem.answerKeys != "") {
            lpsymbolizer.problem.answerKeys += "." + DelimitedTokenizer.escape(s, "\\.");
         } else {
            lpsymbolizer.problem.answerKeys = DelimitedTokenizer.escape(s, "\\.");
         }

         if (lpsymbolizer.problem.answers == null) {
            lpsymbolizer.problem.answers = new Vector();
         }

         lpsymbolizer.problem.answers.addElement(s2);
         String s3 = lpsymbolizer.problem.toString();
         AnswerListLabel answerlistlabel = new AnswerListLabel(s3, 2);
         answerlistlabel.setHoverText(s3);
         answerlistlabel.setOpaque(true);
         problemlistview.addItem(answerlistlabel);
         problemlistview.validate();
         saveChanges(lpsymbolizer);
         if (lpsymbolizer.problem.countAnswers() == 0) {
            lpsymbolizer.titlePanel.setStatus("Answer Not Available");
         } else {
            lpsymbolizer.titlePanel.setStatus(null);
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean deleteSelectedAnswers(LPSymbolizer lpsymbolizer, ProblemListView problemlistview) {
      int[] aint = problemlistview.getSelectedProblems(null);
      int i = aint.length;

      for (int j = i - 1; j >= 0; j--) {
         problemlistview.listModel.remove(aint[j]);
         lpsymbolizer.problem.answers.removeElementAt(aint[j]);
      }

      DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\.");
      delimitedtokenizer.setInput(lpsymbolizer.problem.answerKeys);
      int k = 0;
      int l = 0;
      String s = null;

      while (true) {
         String s1 = delimitedtokenizer.nextToken();
         if (s1 == null) {
            lpsymbolizer.problem.answerKeys = s;
            problemlistview.validate();
            saveChanges(lpsymbolizer);
            if (lpsymbolizer.problem.countAnswers() == 0) {
               lpsymbolizer.titlePanel.setStatus("Answer Not Available");
            } else {
               lpsymbolizer.titlePanel.setStatus(null);
            }

            return false;
         }

         if (!s1.equals("")) {
            if (l < aint.length && k == aint[l]) {
               LPSymbolizer.userKey.remove(s1);
               l++;
            } else if (s == null) {
               s = s1;
            } else {
               s = s + "." + s1;
            }

            k++;
         }
      }
   }

   static void saveChanges(LPSymbolizer lpsymbolizer) {
      LPSymbolizer.writeUserKey();
      String s = lpsymbolizer.getChangedProblem();
      if (s == null) {
         LPSymbolizer.saveProblems(lpsymbolizer.problemIndex);
      } else {
         lpsymbolizer.saveProblems(s);
      }
   }
}
