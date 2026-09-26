package edu.ucla.phil.logic;

import java.util.Vector;

class C_YB {
   int f902;
   String[] f903;

   C_YB(String s) {
      if (m1538(s)) {
         boolean flag = false;
         Vector vector = new Vector();
         this.f902 = Integer.parseInt(s.substring(0, 3));
         this.f903 = null;
         s = s.substring(3).trim();

         while (s.length() != 0) {
            int i = m1536(s.indexOf(32), s.indexOf(9));
            String s1;
            if (i == -1) {
               s1 = s;
               s = "";
            } else {
               s1 = s.substring(0, i);
               s = s.substring(i).trim();
            }

            vector.addElement(s1);
         }

         this.f903 = new String[vector.size()];
         vector.copyInto(this.f903);
      } else {
         this.f902 = -1;
         this.f903 = null;
      }
   }

   static int m1536(int i, int j) {
      return i != -1 && (j == -1 || j >= i) ? i : j;
   }

   static int m1537(int i, int j) {
      return i != -1 && (j == -1 || j <= i) ? i : j;
   }

   static boolean m1538(String s) {
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
