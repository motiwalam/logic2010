package edu.ucla.phil.logic;

class PlaceholderEdit {
   int placeholderIndex;
   int position;
   String replacedText;

   PlaceholderEdit(int i, int j, String s) {
      this.placeholderIndex = i;
      this.position = j;
      this.replacedText = s;
   }
}
