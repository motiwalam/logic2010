package edu.ucla.phil.logic;

import java.util.Enumeration;

public class IntervalSet {
   boolean inverted;
   int count;
   private int cursor;
   int[] boundaries;

   public IntervalSet() {
      this.clear();
   }

   public IntervalSet(String s) {
      this();
      this.inverted = s.indexOf("~") != -1;
      int[] aint = ExpressionPath.parse(s);
      int i = aint == null ? 0 : aint.length;

      for (int j = 0; j < i; j++) {
         this.toggleBoundary(aint[j]);
      }
   }

   public static IntervalSet singleton(int i) {
      return new IntervalSet().toggleBoundary(i).toggleBoundary(i + 1);
   }

   public static IntervalSet range(int i, int j) {
      IntervalSet intervalset = new IntervalSet();
      if (j > 0) {
         intervalset.toggleBoundary(i).toggleBoundary(i + j);
      }

      return intervalset;
   }

   public static IntervalSet atLeast(int i) {
      return new IntervalSet().toggleBoundary(i);
   }

   public static IntervalSet greaterThan(int i) {
      return new IntervalSet().toggleBoundary(i + 1);
   }

   public IntervalSet copy(boolean flag) {
      IntervalSet intervalset1 = new IntervalSet();
      intervalset1.inverted = this.inverted;
      intervalset1.count = this.count;
      intervalset1.boundaries = this.count == 0 ? null : (flag ? new int[this.count] : this.boundaries);
      if (this.count != 0 && flag) {
         System.arraycopy(this.boundaries, 0, intervalset1.boundaries, 0, this.count);
      }

      return intervalset1;
   }

   public IntervalSet union(IntervalSet intervalset1) {
      return this.complement().intersect(intervalset1.copy(false).complement()).complement();
   }

   public IntervalSet subtract(IntervalSet intervalset1) {
      return this.intersect(intervalset1.copy(false).complement());
   }

   public IntervalSet intersect(IntervalSet intervalset1) {
      if (intervalset1.count == 0) {
         return intervalset1.inverted ? this : this.clear();
      } else {
         IntervalSet intervalset2 = new IntervalSet();
         IntervalSet intervalset3 = this.copy(false);

         IntervalSet intervalset4;
         for (intervalset4 = intervalset1.copy(false); intervalset3.cursor < intervalset3.count; intervalset3.advance()) {
            int i = intervalset3.boundaries[intervalset3.cursor];
            int j = intervalset4.boundaries[intervalset4.cursor];
            if (i > j || i == j && !intervalset3.inverted && intervalset4.inverted) {
               IntervalSet intervalset5 = intervalset3;
               intervalset3 = intervalset4;
               intervalset4 = intervalset5;
               i = j;
            }

            if (intervalset4.inverted) {
               intervalset2.toggleBoundary(i);
            }
         }

         if (intervalset3.inverted) {
            for (int k = intervalset4.cursor; k < intervalset4.count; k++) {
               intervalset2.toggleBoundary(intervalset4.boundaries[k]);
            }
         }

         this.inverted = this.inverted & intervalset1.inverted;
         this.count = intervalset2.count;
         if (this.count == 0) {
            this.boundaries = null;
         } else {
            this.boundaries = new int[this.count];
            System.arraycopy(intervalset2.boundaries, 0, this.boundaries, 0, this.count);
         }

         return this;
      }
   }

   private void advance() {
      this.cursor++;
      this.inverted = !this.inverted;
   }

   public IntervalSet clear() {
      this.inverted = false;
      this.count = 0;
      this.cursor = 0;
      this.boundaries = null;
      return this;
   }

   public IntervalSet complement() {
      this.inverted = !this.inverted;
      return this;
   }

   public IntervalSet shift(int i) {
      for (int j = 0; j < this.count; j++) {
         this.boundaries[j] += i;
      }

      return this;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof IntervalSet)) {
         return false;
      } else {
         IntervalSet intervalset1 = (IntervalSet)object;
         if (this.inverted == intervalset1.inverted && this.count == intervalset1.count) {
            for (int i = 0; i < this.count; i++) {
               if (this.boundaries[i] != intervalset1.boundaries[i]) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      int i = this.inverted ? 1 : 0;

      for (int j = 0; j < this.count; j++) {
         i = i * 40503 + this.boundaries[j];
      }

      return i;
   }

   public boolean isEmpty() {
      return this.count == 0 && !this.inverted;
   }

   public boolean contains(int i) {
      return !singleton(i).intersect(this).isEmpty();
   }

   String selectChars(String s) {
      if (s == null) {
         return null;
      } else {
         IntervalSet intervalset1 = range(0, s.length());
         IntervalSet intervalset2 = intervalset1.intersect(this);
         String s1 = "";
         int i = intervalset2.count - 1;

         for (int b0 = 0; b0 < i; b0 += 2) {
            s1 = s1 + s.substring(intervalset2.boundaries[b0], intervalset2.boundaries[b0 + 1]);
         }

         return s1;
      }
   }

   @Override
   public String toString() {
      return (this.inverted ? "~" : "") + ExpressionPath.format(this.boundaries, 0, this.count);
   }

   public Enumeration elements() {
      return new IntRangeEnumeration(this);
   }

   IntervalSet toggleBoundary(int i) {
      for (int j = this.count; j >= 0; j--) {
         if (j == 0 || this.boundaries[j - 1] < i) {
            this.ensureCapacity(this.count + 1);
            System.arraycopy(this.boundaries, j, this.boundaries, j + 1, this.count - j);
            this.boundaries[j] = i;
            this.count++;
            break;
         }

         if (this.boundaries[j - 1] == i) {
            System.arraycopy(this.boundaries, j, this.boundaries, j - 1, this.count - j);
            this.count--;
            break;
         }
      }

      return this;
   }

   IntervalSet toggleBoundaries(int[] aint) {
      int i = aint.length;

      for (int j = 0; j < i; j++) {
         this.toggleBoundary(aint[j]);
      }

      return this;
   }

   void ensureCapacity(int i) {
      int j = this.boundaries == null ? 0 : this.boundaries.length;
      if (i > j) {
         if (j == 0) {
            j = 1;
         }

         while (i > j) {
            j *= 2;
         }

         int[] aint = new int[j];
         if (this.boundaries != null) {
            System.arraycopy(this.boundaries, 0, aint, 0, this.count);
         }

         this.boundaries = aint;
      }
   }
}
