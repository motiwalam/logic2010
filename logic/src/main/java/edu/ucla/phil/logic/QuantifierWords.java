package edu.ucla.phil.logic;

/**
 * The quantifier words of the text notation: "forall x" for @x and "exists x" for !x.
 *
 * Inside the program formulas are written with @ and !. The words are accepted wherever
 * formula text is read (LogicProgram.parseFormula, and the formula fields of the data
 * files) and are written into the formula fields of the saved work (DataFiles).
 *
 * toWords and toSymbols are exact inverses on the program's formulas: toWords puts one blank
 * between the word and its variable, and one after the variable unless the variable ends the
 * formula or is followed by a closing character; toSymbols removes exactly those blanks. So
 * "@xFx" is written "forall x Fx" and "@x Fx" is written "forall x  Fx".
 */
final class QuantifierWords {
   static final String[] WORDS = new String[]{"forall", "exists"};
   static final String SYMBOLS = "@!";
   static final String CLOSING = ")]}.,:;";

   /** The length of the variable (a lowercase letter and any digits) at i in s, or 0. */
   static int variableLength(String s, int i) {
      if (i >= s.length() || s.charAt(i) < 'a' || s.charAt(i) > 'z') {
         return 0;
      } else {
         int j = i + 1;

         while (j < s.length() && Character.isDigit(s.charAt(j))) {
            j++;
         }

         return j - i;
      }
   }

   /** "@x" and "!x" become "forall x" and "exists x". */
   static String toWords(String s) {
      if (s == null || s.indexOf('@') == -1 && s.indexOf('!') == -1) {
         return s;
      } else {
         StringBuilder stringbuilder = new StringBuilder();
         int i = 0;

         while (i < s.length()) {
            char c0 = s.charAt(i);
            int j = SYMBOLS.indexOf(c0);
            int k = j == -1 ? 0 : variableLength(s, i + 1);
            if (k == 0) {
               stringbuilder.append(c0);
               i++;
            } else {
               stringbuilder.append(WORDS[j]).append(' ').append(s, i + 1, i + 1 + k);
               i += 1 + k;
               if (i < s.length() && CLOSING.indexOf(s.charAt(i)) == -1) {
                  stringbuilder.append(' ');
               }
            }
         }

         return stringbuilder.toString();
      }
   }

   /** "forall x" and "exists x" (blanks optional) become "@x" and "!x". */
   static String toSymbols(String s) {
      return toSymbols(s, -1)[0];
   }

   /**
    * Converts s, and gives in result[1] the position in s of position pos of the result
    * (to report parse errors at the place the user typed).
    */
   static String[] toSymbols(String s, int pos) {
      if (s == null || s.indexOf("forall") == -1 && s.indexOf("exists") == -1) {
         return new String[]{s, String.valueOf(pos)};
      } else {
         StringBuilder stringbuilder = new StringBuilder();
         int i = 0;
         int j = pos;

         while (i < s.length()) {
            int k = -1;

            for (int l = 0; l < WORDS.length; l++) {
               if (s.startsWith(WORDS[l], i)) {
                  k = l;
               }
            }

            int m = i + (k == -1 ? 0 : WORDS[k].length());
            while (k != -1 && m < s.length() && s.charAt(m) == ' ') {
               m++;
            }

            int n = k == -1 ? 0 : variableLength(s, m);
            if (n == 0) {
               if (stringbuilder.length() == pos) {
                  j = i;
               }

               stringbuilder.append(s.charAt(i));
               i++;
            } else {
               if (stringbuilder.length() == pos) {
                  j = i;
               }

               stringbuilder.append(SYMBOLS.charAt(k)).append(s, m, m + n);
               i = m + n;
               if (i < s.length() && s.charAt(i) == ' ') {
                  i++;
               }
            }
         }

         if (stringbuilder.length() == pos) {
            j = s.length();
         }

         return new String[]{stringbuilder.toString(), String.valueOf(j)};
      }
   }
}
