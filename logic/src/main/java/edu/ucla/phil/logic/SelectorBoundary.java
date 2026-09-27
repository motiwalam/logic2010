package edu.ucla.phil.logic;

class SelectorBoundary {
   String name;
   boolean before;

   SelectorBoundary(String s, boolean flag) {
      this.name = s;
      this.before = flag;
   }

   int compareBoundary(SelectorBoundary selectorboundary1) {
      int i = this.name.compareTo(selectorboundary1.name);
      if (i != 0) {
         return i;
      } else if (this.before == selectorboundary1.before) {
         return 0;
      } else {
         return this.before ? -1 : 1;
      }
   }

   @Override
   public String toString() {
      return (this.before ? "" : "~") + "\"" + DelimitedTokenizer.escape(this.name, "\\\"") + "\"";
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof SelectorBoundary)) {
         return false;
      } else {
         SelectorBoundary selectorboundary1 = (SelectorBoundary)object;
         return this.before != selectorboundary1.before ? false : this.name.equals(selectorboundary1.name);
      }
   }
}
