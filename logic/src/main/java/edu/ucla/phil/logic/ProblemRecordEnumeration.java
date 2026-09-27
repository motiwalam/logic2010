package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Vector;

class ProblemRecordEnumeration implements Enumeration {
   int position;
   Vector records = null;

   ProblemRecordEnumeration(ProblemSet problemset) {
      this.reset();
      if (problemset != null) {
         synchronized (problemset) {
            int i = problemset.size();
            if (i != 0) {
               this.records = new Vector();

               for (int j = 0; j < i; j++) {
                  this.records.addElement(problemset.getRecordAt(j));
               }
            }
         }
      }
   }

   ProblemRecordEnumeration(String s) {
      this.reset();
      this.records = new Vector();
      this.records.addElement(s);
   }

   void retainExisting(ProblemSet problemset) {
      if (this.records != null) {
         if (problemset == null) {
            this.records.removeAllElements();
         }

         int i = 0;

         while (i < this.records.size()) {
            if (problemset.getRecord(TaggedRecord.nameOf((String)this.records.elementAt(i))) == null) {
               this.records.removeElementAt(i);
            } else {
               i++;
            }
         }
      }
   }

   @Override
   public boolean hasMoreElements() {
      return this.records != null && this.position < this.records.size();
   }

   @Override
   public Object nextElement() {
      return this.records != null && this.position < this.records.size() ? this.records.elementAt(this.position++) : null;
   }

   void reset() {
      this.position = 0;
   }
}
