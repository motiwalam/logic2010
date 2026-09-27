package edu.ucla.phil.logic;

import java.util.Vector;

class BoundVariableNames extends Vector {
   String encode() {
      String s = "";
      int i = this.size();

      for (int j = 0; j < i; j++) {
         String s1 = (String)this.elementAt(j);
         s = s + (s1 == null ? "" : s1) + ".";
      }

      return s;
   }

   static BoundVariableNames decode(String s) {
      BoundVariableNames boundvariablenames = new BoundVariableNames();

      int i;
      while ((i = s.indexOf(".")) != -1) {
         String s1 = s.substring(0, i).trim();
         boundvariablenames.addElement(s1.equals("") ? null : s1);
         s = s.substring(i + 1);
      }

      return boundvariablenames;
   }
}
