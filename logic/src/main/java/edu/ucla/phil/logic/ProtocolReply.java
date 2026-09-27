package edu.ucla.phil.logic;

import java.util.Vector;

class ProtocolReply {
   int code;
   String[] words;

   ProtocolReply(String s) {
      if (isStatusLine(s)) {
         boolean flag = false;
         Vector vector = new Vector();
         this.code = Integer.parseInt(s.substring(0, 3));
         this.words = null;
         String s2 = s.substring(3).trim();

         while (s2.length() != 0) {
            int i = firstIndex(s2.indexOf(32), s2.indexOf(9));
            String s1;
            if (i == -1) {
               s1 = s2;
               s2 = "";
            } else {
               s1 = s2.substring(0, i);
               s2 = s2.substring(i).trim();
            }

            vector.addElement(s1);
         }

         this.words = new String[vector.size()];
         vector.copyInto(this.words);
      } else {
         this.code = -1;
         this.words = null;
      }
   }

   static int firstIndex(int i, int j) {
      return i != -1 && (j == -1 || j >= i) ? i : j;
   }

   static int lastIndex(int i, int j) {
      return i != -1 && (j == -1 || j <= i) ? i : j;
   }

   static boolean isStatusLine(String s) {
      if (s != null && s.length() >= 4) {
         for (int i = 0; i < 3; i++) {
            if (!Character.isDigit(s.charAt(i))) {
               return false;
            }
         }

         return s.charAt(3) == ' ';
      } else {
         return false;
      }
   }
}
