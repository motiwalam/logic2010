package edu.ucla.phil.logic;

import java.util.Vector;

class C_QD extends DialogHandler implements C_v_D {
   static String[] f699 = LogicProgram.symbols;

   C_QD(String s) {
      super(s);
   }

   @Override
   boolean m451(MessageDialog messagedialog) {
      String s = this.m450(messagedialog);
      LPSymbolizer lpsymbolizer = (LPSymbolizer)this.m449("symbolizer");
      ProblemListView problemlistview = (ProblemListView)this.m449("list");
      if (lpsymbolizer == null) {
         return true;
      } else if (s == null) {
         return false;
      } else if (s.equalsIgnoreCase("ok")) {
         return true;
      } else if (s.equalsIgnoreCase("add")) {
         return this.m1188(lpsymbolizer, problemlistview);
      } else if (s.equalsIgnoreCase("load")) {
         int[] aint = problemlistview.m1533(null);
         if (aint.length != 1) {
            return false;
         } else {
            TaggedRecord taggedrecord = new TaggedRecord((String)lpsymbolizer.problem.f1056.elementAt(aint[0]));
            lpsymbolizer.problem.m1705(taggedrecord, false, false);
            return true;
         }
      } else if (s.equalsIgnoreCase("delete")) {
         return this.m1189(lpsymbolizer, problemlistview);
      } else if (s.equalsIgnoreCase("replace")) {
         this.m1189(lpsymbolizer, problemlistview);
         return this.m1188(lpsymbolizer, problemlistview);
      } else if (s.equalsIgnoreCase("warn")) {
         MessageDialog.showMessage(C_h_E.get("symnot006"), null, null, null);
         return false;
      } else if (s.equalsIgnoreCase("help")) {
         MessageDialog.showMessage(C_h_E.get("symnot007"), null, null, null);
         return false;
      } else {
         return false;
      }
   }

   private boolean m1188(LPSymbolizer lpsymbolizer, ProblemListView problemlistview) {
      if (lpsymbolizer.problem.f1051 != null || lpsymbolizer.saveProblems(lpsymbolizer.saveProblem()) && lpsymbolizer.problem.f1051 != null) {
         int i = 1;

         String s;
         while (LPSymbolizer.userKey.get(s = lpsymbolizer.problem.f1051 + "-" + i) != null) {
            i++;
         }

         String s1 = lpsymbolizer.problem.f1051;
         boolean flag = lpsymbolizer.problem.f1058;
         lpsymbolizer.problem.f1051 = s;
         lpsymbolizer.problem.f1058 = false;
         String s2 = lpsymbolizer.problem.m1699(true);
         lpsymbolizer.problem.f1051 = s1;
         lpsymbolizer.problem.f1058 = flag;
         LPSymbolizer.userKey.put(s, s2);
         if (lpsymbolizer.problem.f1054 != null && lpsymbolizer.problem.f1054 != "") {
            lpsymbolizer.problem.f1054 = lpsymbolizer.problem.f1054 + "." + DelimitedTokenizer.m1139(s, "\\.");
         } else {
            lpsymbolizer.problem.f1054 = DelimitedTokenizer.m1139(s, "\\.");
         }

         if (lpsymbolizer.problem.f1056 == null) {
            lpsymbolizer.problem.f1056 = new Vector();
         }

         lpsymbolizer.problem.f1056.addElement(s2);
         String s3 = lpsymbolizer.problem.toString();
         C_QC c_qc = new C_QC(s3, 2);
         c_qc.m1187(s3);
         c_qc.setOpaque(true);
         problemlistview.m1526(c_qc);
         problemlistview.validate();
         m1190(lpsymbolizer);
         if (lpsymbolizer.problem.m1714() == 0) {
            lpsymbolizer.titlePanel.m1825("Answer Not Available");
         } else {
            lpsymbolizer.titlePanel.m1825(null);
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean m1189(LPSymbolizer lpsymbolizer, ProblemListView problemlistview) {
      int[] aint = problemlistview.m1533(null);
      int i = aint.length;

      for (int j = i - 1; j >= 0; j--) {
         problemlistview.f901.remove(aint[j]);
         lpsymbolizer.problem.f1056.removeElementAt(aint[j]);
      }

      DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\.");
      delimitedtokenizer.m1132(lpsymbolizer.problem.f1054);
      int k = 0;
      int l = 0;
      String s = null;

      while (true) {
         String s1 = delimitedtokenizer.m1135();
         if (s1 == null) {
            lpsymbolizer.problem.f1054 = s;
            problemlistview.validate();
            m1190(lpsymbolizer);
            if (lpsymbolizer.problem.m1714() == 0) {
               lpsymbolizer.titlePanel.m1825("Answer Not Available");
            } else {
               lpsymbolizer.titlePanel.m1825(null);
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

   static void m1190(LPSymbolizer lpsymbolizer) {
      LPSymbolizer.writeUserKey();
      String s = lpsymbolizer.getChangedProblem();
      if (s == null) {
         LPSymbolizer.saveProblems(lpsymbolizer.problemIndex);
      } else {
         lpsymbolizer.saveProblems(s);
      }
   }
}
