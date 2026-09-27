package edu.ucla.phil.logic;

public class ProblemSelector {
   boolean complemented;
   int boundaryCount;
   private int cursor;
   SelectorBoundary[] boundaries;
   String flagChars;

   public ProblemSelector() {
      this.clear();
   }

   public ProblemSelector(String s) {
      this();
      int i = s.indexOf(123);
      if (i != -1) {
         this.flagChars = s.substring(0, i);
         this.complemented = this.flagChars.indexOf(126) != -1;
         this.flagChars = combineChars(this.flagChars, "~", true);
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\\"");
         delimitedtokenizer.setInput(s.substring(i + 1));
         ProblemSelector problemselector1 = new ProblemSelector();

         while (true) {
            String s1 = delimitedtokenizer.nextToken();
            if (delimitedtokenizer.getDelimiter() == '\\' || s1.indexOf(125) != -1) {
               break;
            }

            boolean flag = s1.indexOf(126) == -1;
            s1 = delimitedtokenizer.nextToken();
            if (delimitedtokenizer.getDelimiter() == '\\') {
               break;
            }

            problemselector1.toggleBoundary(s1, flag);
         }

         if ((this.boundaryCount = problemselector1.boundaryCount) == 0) {
            this.boundaries = null;
         } else {
            this.boundaries = new SelectorBoundary[this.boundaryCount];
            System.arraycopy(problemselector1.boundaries, 0, this.boundaries, 0, this.boundaryCount);
         }
      }
   }

   public static ProblemSelector single(String s) {
      return new ProblemSelector().toggleBoundary(s, true).toggleBoundary(s, false);
   }

   public static ProblemSelector startingAt(String s) {
      return new ProblemSelector().toggleBoundary(s, true);
   }

   public static ProblemSelector after(String s) {
      return new ProblemSelector().toggleBoundary(s, false);
   }

   public ProblemSelector copy(boolean flag) {
      ProblemSelector problemselector1 = new ProblemSelector();
      problemselector1.complemented = this.complemented;
      problemselector1.boundaryCount = this.boundaryCount;
      problemselector1.flagChars = this.flagChars;
      problemselector1.boundaries = this.boundaryCount == 0 ? null : (flag ? new SelectorBoundary[this.boundaryCount] : this.boundaries);
      if (this.boundaryCount != 0 && flag) {
         System.arraycopy(this.boundaries, 0, problemselector1.boundaries, 0, this.boundaryCount);
      }

      return problemselector1;
   }

   public ProblemSelector union(ProblemSelector problemselector1) {
      return this.complement().intersect(problemselector1.copy(false).complement()).complement();
   }

   public ProblemSelector subtract(ProblemSelector problemselector1) {
      return this.intersect(problemselector1.copy(false).complement());
   }

   public ProblemSelector intersect(ProblemSelector problemselector1) {
      if (problemselector1.complemented) {
         this.flagChars = this.complemented
            ? combineChars(this.flagChars + problemselector1.flagChars, "", true)
            : combineChars(this.flagChars, problemselector1.flagChars, true);
      } else {
         this.flagChars = this.complemented
            ? combineChars(problemselector1.flagChars, this.flagChars, true)
            : combineChars(this.flagChars, problemselector1.flagChars, false);
      }

      if (problemselector1.boundaryCount == 0) {
         return problemselector1.complemented ? this : this.reset(false);
      } else {
         ProblemSelector problemselector2 = new ProblemSelector();
         ProblemSelector problemselector3 = this.copy(false);

         ProblemSelector problemselector4;
         for (problemselector4 = problemselector1.copy(false); problemselector3.cursor < problemselector3.boundaryCount; problemselector3.advanceCursor()) {
            SelectorBoundary selectorboundary = problemselector3.boundaries[problemselector3.cursor];
            SelectorBoundary selectorboundary1 = problemselector4.boundaries[problemselector4.cursor];
            int i = selectorboundary.compareBoundary(selectorboundary1);
            if (i > 0 || i == 0 && !problemselector3.complemented && problemselector4.complemented) {
               ProblemSelector problemselector5 = problemselector3;
               problemselector3 = problemselector4;
               problemselector4 = problemselector5;
               selectorboundary = selectorboundary1;
            }

            if (problemselector4.complemented) {
               problemselector2.toggleBoundary(selectorboundary);
            }
         }

         if (problemselector3.complemented) {
            for (int j = problemselector4.cursor; j < problemselector4.boundaryCount; j++) {
               problemselector2.toggleBoundary(problemselector4.boundaries[j]);
            }
         }

         this.complemented = this.complemented & problemselector1.complemented;
         this.boundaryCount = problemselector2.boundaryCount;
         if (this.boundaryCount == 0) {
            this.boundaries = null;
         } else {
            this.boundaries = new SelectorBoundary[this.boundaryCount];
            System.arraycopy(problemselector2.boundaries, 0, this.boundaries, 0, this.boundaryCount);
         }

         return this;
      }
   }

   private void advanceCursor() {
      this.cursor++;
      this.complemented = !this.complemented;
   }

   public ProblemSelector clear() {
      return this.reset(true);
   }

   private ProblemSelector reset(boolean flag) {
      this.complemented = false;
      this.boundaryCount = 0;
      this.cursor = 0;
      this.boundaries = null;
      if (flag) {
         this.flagChars = "";
      }

      return this;
   }

   public ProblemSelector complement() {
      this.complemented = !this.complemented;
      return this;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof ProblemSelector)) {
         return false;
      } else {
         ProblemSelector problemselector1 = (ProblemSelector)object;
         if (this.complemented == problemselector1.complemented && this.boundaryCount == problemselector1.boundaryCount) {
            if (this.flagChars.length() != problemselector1.flagChars.length()) {
               return false;
            } else if (combineChars(this.flagChars, problemselector1.flagChars, true).length() != 0) {
               return false;
            } else {
               for (int i = 0; i < this.boundaryCount; i++) {
                  if (!this.boundaries[i].equals(problemselector1.boundaries[i])) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      int i = this.complemented ? 1 : 0;

      for (int j = 0; j < this.boundaryCount; j++) {
         SelectorBoundary selectorboundary = this.boundaries[j];
         i = i * 40503 + (selectorboundary.before ? 1 : 0) + selectorboundary.name.hashCode();
      }

      return i;
   }

   public boolean isEmpty() {
      return this.boundaryCount == 0 && !this.complemented && this.flagChars.length() == 0;
   }

   public ProblemSelector addPrefix(String s) {
      if (s != null && !s.equals("")) {
         for (int i = 0; i < this.boundaryCount; i++) {
            this.boundaries[i].name = s + this.boundaries[i].name;
         }
      }

      return this;
   }

   public boolean contains(String s) {
      return s == null ? false : !single(s).intersect(this).isEmpty();
   }

   public boolean hasFlag(char c0) {
      return this.flagChars.indexOf(c0) == -1 ? this.complemented : !this.complemented;
   }

   @Override
   public String toString() {
      String s = "";

      for (int i = 0; i < this.boundaryCount; i++) {
         s = s + (i == 0 ? "" : ",") + this.boundaries[i];
      }

      return (this.complemented ? "~" : "") + this.flagChars + "{" + s + "}";
   }

   ProblemSelector toggleBoundary(String s, boolean flag) {
      return this.toggleBoundary(new SelectorBoundary(s, flag));
   }

   ProblemSelector toggleBoundary(SelectorBoundary selectorboundary) {
      if (selectorboundary != null) {
         for (int i = this.boundaryCount; i >= 0; i--) {
            int j;
            if (i == 0 || (j = this.boundaries[i - 1].compareBoundary(selectorboundary)) < 0) {
               this.ensureCapacity(this.boundaryCount + 1);
               if (i < this.boundaryCount) {
                  System.arraycopy(this.boundaries, i, this.boundaries, i + 1, this.boundaryCount - i);
               }

               this.boundaries[i] = selectorboundary;
               this.boundaryCount++;
               break;
            }

            if (j == 0) {
               if (i < this.boundaryCount) {
                  System.arraycopy(this.boundaries, i, this.boundaries, i - 1, this.boundaryCount - i);
               }

               this.boundaryCount--;
               break;
            }
         }
      }

      return this;
   }

   void ensureCapacity(int i) {
      int j = this.boundaries == null ? 0 : this.boundaries.length;
      if (i > j) {
         if (j == 0) {
            j = 1;
         }

         while (i > j) {
            j *= 2;
         }

         SelectorBoundary[] aselectorboundary = new SelectorBoundary[j];
         if (this.boundaries != null) {
            System.arraycopy(this.boundaries, 0, aselectorboundary, 0, this.boundaryCount);
         }

         this.boundaries = aselectorboundary;
      }
   }

   static String combineChars(String s, String s1, boolean flag) {
      String s2 = "";
      if (flag || s1.length() != 0) {
         int i = s.length();

         for (int j = 0; j < i; j++) {
            char c0 = s.charAt(j);
            if (s2.indexOf(c0) == -1 && (s1.indexOf(c0) == -1 ? flag : !flag)) {
               s2 = s2 + c0;
            }
         }
      }

      return s2;
   }
}
