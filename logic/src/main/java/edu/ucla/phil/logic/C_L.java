package edu.ucla.phil.logic;

import java.util.Vector;

class C_L extends Vector {
   String m932() {
      String s = "";
      int i = this.size();

      for (int j = 0; j < i; j++) {
         String s1 = (String)this.elementAt(j);
         s = s + (s1 == null ? "" : s1) + ".";
      }

      return s;
   }

   static C_L m933(String s) {
      C_L c_l = new C_L();

      int i;
      while ((i = s.indexOf(".")) != -1) {
         String s1 = s.substring(0, i).trim();
         c_l.addElement(s1.equals("") ? null : s1);
         s = s.substring(i + 1);
      }

      return c_l;
   }
}
