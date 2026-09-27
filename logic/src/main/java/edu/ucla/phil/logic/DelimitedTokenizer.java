package edu.ucla.phil.logic;

class DelimitedTokenizer {
   char delimiter;
   String remaining;
   String delimiters;

   DelimitedTokenizer(String s) {
      if (s == null || s.length() == 0) {
         s = "\\";
      }

      this.delimiters = s;
      this.delimiter = s.charAt(0);
      this.remaining = null;
   }

   void setInput(String s) {
      this.delimiter = this.delimiters.charAt(0);
      this.remaining = s;
   }

   String getRemaining() {
      return this.remaining;
   }

   char getDelimiter() {
      return this.delimiter;
   }

   String nextToken() {
      return this.nextToken(false);
   }

   String nextToken(boolean flag) {
      if (this.remaining == null) {
         return null;
      } else {
         int i = this.delimiters.length();
         String s = "";

         while (true) {
            int j = -1;
            int k = -1;

            for (int l = 0; l < i; l++) {
               int i1 = this.remaining.indexOf(this.delimiters.charAt(l));
               if (i1 != -1 && (j == -1 || i1 < j)) {
                  j = i1;
                  k = l;
               }
            }

            if (k == -1) {
               s = s + this.remaining;
               this.delimiter = this.delimiters.charAt(0);
               this.remaining = null;
               return s;
            }

            String s1 = s + this.remaining.substring(0, j);
            if (k != 0) {
               this.delimiter = this.remaining.charAt(j);
               this.remaining = this.remaining.substring(j + 1);
               return s1;
            }

            if (flag) {
               s1 = s1 + this.delimiters.charAt(0);
            }

            if (j >= this.remaining.length() - 1) {
               this.delimiter = this.delimiters.charAt(0);
               this.remaining = null;
               return s1;
            }

            s = s1 + this.remaining.charAt(j + 1);
            this.remaining = this.remaining.substring(j + 2);
         }
      }
   }

   String escape(String s) {
      return escape(s, this.delimiters, false);
   }

   String escape(String s, boolean flag) {
      return escape(s, this.delimiters, flag);
   }

   static String escape(String s, String s1) {
      return escape(s, s1, false);
   }

   static String escape(String s, String s1, boolean flag) {
      if (s == null) {
         return null;
      } else {
         if (s1 == null || s1.length() == 0) {
            s1 = "\\";
         }

         char c0 = s1.charAt(0);
         int i = s1.length();
         String s2 = "";

         while (true) {
            int j = -1;

            for (int k = 0; k < i; k++) {
               int l = s.indexOf(s1.charAt(k));
               if (l != -1 && (j == -1 || l < j)) {
                  j = l;
               }
            }

            if (j == -1) {
               return s2 + s;
            }

            if (flag && s.charAt(j) == c0) {
               if (j + 1 < s.length()) {
                  j++;
               }

               s2 = s2 + s.substring(0, j + 1);
               s = s.substring(j + 1);
            } else {
               s2 = s2 + s.substring(0, j) + c0 + s.charAt(j);
               s = s.substring(j + 1);
            }
         }
      }
   }
}
