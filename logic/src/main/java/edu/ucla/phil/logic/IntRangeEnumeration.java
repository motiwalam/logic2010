package edu.ucla.phil.logic;

import java.util.Enumeration;

class IntRangeEnumeration implements Enumeration {
   int current;
   int rangeIndex;
   IntervalSet rangeSet;

   public IntRangeEnumeration(IntervalSet intervalset) {
      this.rangeSet = intervalset;
      this.rangeIndex = intervalset.inverted ? 1 : 0;
      this.current = this.rangeIndex < intervalset.count ? intervalset.boundaries[this.rangeIndex] : 0;
   }

   @Override
   public boolean hasMoreElements() {
      return this.rangeIndex + 1 < this.rangeSet.count && this.current < this.rangeSet.boundaries[this.rangeIndex + 1];
   }

   @Override
   public Object nextElement() {
      Integer integer = null;
      if (this.hasMoreElements()) {
         integer = new Integer(this.current);
         if (++this.current >= this.rangeSet.boundaries[this.rangeIndex + 1]) {
            this.rangeIndex += 2;
            this.current = this.rangeIndex < this.rangeSet.count ? this.rangeSet.boundaries[this.rangeIndex] : 0;
         }
      }

      return integer;
   }
}
