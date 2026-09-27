package edu.ucla.phil.logic;

import java.util.Vector;

class AnswerSet {
   String problemName;
   Vector answers;

   public AnswerSet(String s, Vector vector) {
      this.problemName = s;
      this.answers = vector;
   }
}
