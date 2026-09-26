package edu.ucla.phil.logic;

public class ProblemSelector {
   boolean f242;
   int f243;
   private int f244;
   C_0D[] f245;
   String f246;

   public ProblemSelector() {
      this.m399();
   }

   public ProblemSelector(String s) {
      this();
      int i = s.indexOf(123);
      if (i != -1) {
         this.f246 = s.substring(0, i);
         this.f242 = this.f246.indexOf(126) != -1;
         this.f246 = m409(this.f246, "~", true);
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\\"");
         delimitedtokenizer.m1132(s.substring(i + 1));
         ProblemSelector problemselector1 = new ProblemSelector();

         while (true) {
            String s1 = delimitedtokenizer.m1135();
            if (delimitedtokenizer.m1134() == '\\' || s1.indexOf(125) != -1) {
               break;
            }

            boolean flag = s1.indexOf(126) == -1;
            s1 = delimitedtokenizer.m1135();
            if (delimitedtokenizer.m1134() == '\\') {
               break;
            }

            problemselector1.m406(s1, flag);
         }

         if ((this.f243 = problemselector1.f243) == 0) {
            this.f245 = null;
         } else {
            this.f245 = new C_0D[this.f243];
            System.arraycopy(problemselector1.f245, 0, this.f245, 0, this.f243);
         }
      }
   }

   public static ProblemSelector m391(String s) {
      return new ProblemSelector().m406(s, true).m406(s, false);
   }

   public static ProblemSelector m392(String s) {
      return new ProblemSelector().m406(s, true);
   }

   public static ProblemSelector m393(String s) {
      return new ProblemSelector().m406(s, false);
   }

   public ProblemSelector m394(boolean flag) {
      ProblemSelector problemselector1 = new ProblemSelector();
      problemselector1.f242 = this.f242;
      problemselector1.f243 = this.f243;
      problemselector1.f246 = this.f246;
      problemselector1.f245 = this.f243 == 0 ? null : (flag ? new C_0D[this.f243] : this.f245);
      if (this.f243 != 0 && flag) {
         System.arraycopy(this.f245, 0, problemselector1.f245, 0, this.f243);
      }

      return problemselector1;
   }

   public ProblemSelector m395(ProblemSelector problemselector1) {
      return this.m401().m397(problemselector1.m394(false).m401()).m401();
   }

   public ProblemSelector m396(ProblemSelector problemselector1) {
      return this.m397(problemselector1.m394(false).m401());
   }

   public ProblemSelector m397(ProblemSelector problemselector1) {
      if (problemselector1.f242) {
         this.f246 = this.f242 ? m409(this.f246 + problemselector1.f246, "", true) : m409(this.f246, problemselector1.f246, true);
      } else {
         this.f246 = this.f242 ? m409(problemselector1.f246, this.f246, true) : m409(this.f246, problemselector1.f246, false);
      }

      if (problemselector1.f243 == 0) {
         return problemselector1.f242 ? this : this.m400(false);
      } else {
         ProblemSelector problemselector2 = new ProblemSelector();
         ProblemSelector problemselector3 = this.m394(false);

         ProblemSelector problemselector4;
         for (problemselector4 = problemselector1.m394(false); problemselector3.f244 < problemselector3.f243; problemselector3.m398()) {
            C_0D c_0d = problemselector3.f245[problemselector3.f244];
            C_0D c_0d1 = problemselector4.f245[problemselector4.f244];
            int i = c_0d.m60(c_0d1);
            if (i > 0 || i == 0 && !problemselector3.f242 && problemselector4.f242) {
               ProblemSelector problemselector5 = problemselector3;
               problemselector3 = problemselector4;
               problemselector4 = problemselector5;
               c_0d = c_0d1;
            }

            if (problemselector4.f242) {
               problemselector2.m407(c_0d);
            }
         }

         if (problemselector3.f242) {
            for (int j = problemselector4.f244; j < problemselector4.f243; j++) {
               problemselector2.m407(problemselector4.f245[j]);
            }
         }

         this.f242 = this.f242 & problemselector1.f242;
         this.f243 = problemselector2.f243;
         if (this.f243 == 0) {
            this.f245 = null;
         } else {
            this.f245 = new C_0D[this.f243];
            System.arraycopy(problemselector2.f245, 0, this.f245, 0, this.f243);
         }

         return this;
      }
   }

   private void m398() {
      this.f244++;
      this.f242 = !this.f242;
   }

   public ProblemSelector m399() {
      return this.m400(true);
   }

   private ProblemSelector m400(boolean flag) {
      this.f242 = false;
      this.f243 = 0;
      this.f244 = 0;
      this.f245 = null;
      if (flag) {
         this.f246 = "";
      }

      return this;
   }

   public ProblemSelector m401() {
      this.f242 = !this.f242;
      return this;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof ProblemSelector)) {
         return false;
      } else {
         ProblemSelector problemselector1 = (ProblemSelector)object;
         if (this.f242 == problemselector1.f242 && this.f243 == problemselector1.f243) {
            if (this.f246.length() != problemselector1.f246.length()) {
               return false;
            } else if (m409(this.f246, problemselector1.f246, true).length() != 0) {
               return false;
            } else {
               for (int i = 0; i < this.f243; i++) {
                  if (!this.f245[i].equals(problemselector1.f245[i])) {
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
      int i = this.f242 ? 1 : 0;

      for (int j = 0; j < this.f243; j++) {
         C_0D c_0d = this.f245[j];
         i = i * 40503 + (c_0d.f26 ? 1 : 0) + c_0d.f25.hashCode();
      }

      return i;
   }

   public boolean m402() {
      return this.f243 == 0 && !this.f242 && this.f246.length() == 0;
   }

   public ProblemSelector m403(String s) {
      if (s != null && !s.equals("")) {
         for (int i = 0; i < this.f243; i++) {
            this.f245[i].f25 = s + this.f245[i].f25;
         }
      }

      return this;
   }

   public boolean m404(String s) {
      return s == null ? false : !m391(s).m397(this).m402();
   }

   public boolean m405(char c0) {
      return this.f246.indexOf(c0) == -1 ? this.f242 : !this.f242;
   }

   @Override
   public String toString() {
      String s = "";

      for (int i = 0; i < this.f243; i++) {
         s = s + (i == 0 ? "" : ",") + this.f245[i];
      }

      return (this.f242 ? "~" : "") + this.f246 + "{" + s + "}";
   }

   ProblemSelector m406(String s, boolean flag) {
      return this.m407(new C_0D(s, flag));
   }

   ProblemSelector m407(C_0D c_0d) {
      if (c_0d != null) {
         for (int i = this.f243; i >= 0; i--) {
            int j;
            if (i == 0 || (j = this.f245[i - 1].m60(c_0d)) < 0) {
               this.m408(this.f243 + 1);
               if (i < this.f243) {
                  System.arraycopy(this.f245, i, this.f245, i + 1, this.f243 - i);
               }

               this.f245[i] = c_0d;
               this.f243++;
               break;
            }

            if (j == 0) {
               if (i < this.f243) {
                  System.arraycopy(this.f245, i, this.f245, i - 1, this.f243 - i);
               }

               this.f243--;
               break;
            }
         }
      }

      return this;
   }

   void m408(int i) {
      int j = this.f245 == null ? 0 : this.f245.length;
      if (i > j) {
         if (j == 0) {
            j = 1;
         }

         while (i > j) {
            j *= 2;
         }

         C_0D[] ac_0d = new C_0D[j];
         if (this.f245 != null) {
            System.arraycopy(this.f245, 0, ac_0d, 0, this.f243);
         }

         this.f245 = ac_0d;
      }
   }

   static String m409(String s, String s1, boolean flag) {
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
