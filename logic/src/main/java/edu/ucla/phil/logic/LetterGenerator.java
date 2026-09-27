package edu.ucla.phil.logic;

import java.util.Enumeration;

class LetterGenerator implements Enumeration {
   String letters;
   int letterCount;
   int position;
   int suffix;

   LetterGenerator(String s) {
      this.letters = s;
      this.letterCount = this.letters == null ? 0 : this.letters.length();
      this.position = 0;
      this.suffix = -1;
   }

   @Override
   public boolean hasMoreElements() {
      return this.letterCount != 0;
   }

   @Override
   public Object nextElement() {
      if (this.letterCount == 0) {
         return null;
      } else {
         String s;
         for (s = ""; this.position >= this.letterCount; this.suffix++) {
            this.position = this.position - this.letterCount;
         }

         String s1 = s + this.letters.charAt(this.position);
         this.position++;
         if (this.suffix >= 0) {
            s1 = s1 + this.suffix;
         }

         return s1;
      }
   }
}
