package edu.ucla.phil.logic;

class DelimitedTokenizer {
   char f650;
   String f651;
   String f652;

   DelimitedTokenizer(String s) {
      if (s == null || s.length() == 0) {
         s = "\\";
      }

      this.f652 = s;
      this.f650 = s.charAt(0);
      this.f651 = null;
   }

   void m1132(String s) {
      this.f650 = this.f652.charAt(0);
      this.f651 = s;
   }

   String m1133() {
      return this.f651;
   }

   char m1134() {
      return this.f650;
   }

   String m1135() {
      return this.m1136(false);
   }

   String m1136(boolean flag) {
      if (this.f651 == null) {
         return null;
      } else {
         int i = this.f652.length();
         String s = "";

         while (true) {
            int j = -1;
            int k = -1;

            for (int l = 0; l < i; l++) {
               int i1 = this.f651.indexOf(this.f652.charAt(l));
               if (i1 != -1 && (j == -1 || i1 < j)) {
                  j = i1;
                  k = l;
               }
            }

            if (k == -1) {
               s = s + this.f651;
               this.f650 = this.f652.charAt(0);
               this.f651 = null;
               return s;
            }

            s = s + this.f651.substring(0, j);
            if (k != 0) {
               this.f650 = this.f651.charAt(j);
               this.f651 = this.f651.substring(j + 1);
               return s;
            }

            if (flag) {
               s = s + this.f652.charAt(0);
            }

            if (j >= this.f651.length() - 1) {
               this.f650 = this.f652.charAt(0);
               this.f651 = null;
               return s;
            }

            s = s + this.f651.charAt(j + 1);
            this.f651 = this.f651.substring(j + 2);
         }
      }
   }

   String m1137(String s) {
      return m1140(s, this.f652, false);
   }

   String m1138(String s, boolean flag) {
      return m1140(s, this.f652, flag);
   }

   static String m1139(String s, String s1) {
      return m1140(s, s1, false);
   }

   static String m1140(String s, String s1, boolean flag) {
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
