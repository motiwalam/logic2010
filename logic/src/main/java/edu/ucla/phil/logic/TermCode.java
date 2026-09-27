package edu.ucla.phil.logic;

class TermCode {
   int year;
   int season;

   TermCode(int i, int j) {
      this.year = i;
      this.season = j;
   }

   int compareTerm(TermCode termcode1) {
      return this.year != termcode1.year ? this.year - termcode1.year : this.season - termcode1.season;
   }
}
