package edu.ucla.phil.logic;

public class PermutationIterator {
   int size;
   int[] counters;
   int[] permutation;

   public PermutationIterator(int i) {
      this.size = i;
      if (i == 0) {
         this.counters = null;
      } else {
         this.counters = new int[i - 1];
      }

      this.permutation = new int[i];
      this.reset();
   }

   public PermutationIterator(int[] aint) {
      if (aint == null) {
         this.size = 0;
         this.counters = null;
      } else {
         this.size = aint.length + 1;
         this.counters = new int[this.size - 1];
      }

      this.permutation = new int[this.size];
      this.setCounters(aint);
   }

   public int[] current() {
      int[] aint = new int[this.size];
      System.arraycopy(this.permutation, 0, aint, 0, this.size);
      return aint;
   }

   public boolean next() {
      int i;
      for (i = 0; i < this.size - 1 && ++this.counters[i] > i + 1; i++) {
         this.counters[i] = 0;
      }

      if (i < this.size - 1) {
         this.reversePrefix(i + 2);
         return true;
      } else {
         this.reversePrefix(this.size);
         return false;
      }
   }

   public boolean previous() {
      int i;
      for (i = 0; i < this.size - 1 && --this.counters[i] < 0; i++) {
         this.counters[i] = i + 1;
      }

      if (i < this.size - 1) {
         this.reversePrefix(i + 2);
         return true;
      } else {
         this.reversePrefix(this.size);
         return false;
      }
   }

   public int[] getCounters() {
      if (this.counters == null) {
         return null;
      } else {
         int[] aint = new int[this.size - 1];
         System.arraycopy(this.counters, 0, aint, 0, this.size - 1);
         return aint;
      }
   }

   public void setCounters(int[] aint) {
      this.reset();
      if (aint != null) {
         for (int i = Math.min(aint.length - 1, this.size - 2); i >= 0; i--) {
            int j = aint[i] % (i + 2);
            if (j < 0) {
               j += i + 2;
            }

            this.rotatePrefix(i + 2, j);
            this.counters[i] = j;
         }
      }
   }

   public void reset() {
      for (int i = 0; i < this.size - 1; i++) {
         this.permutation[i] = i;
         this.counters[i] = 0;
      }

      if (this.size > 0) {
         this.permutation[this.size - 1] = this.size - 1;
      }
   }

   @Override
   public String toString() {
      return ExpressionPath.format(this.permutation);
   }

   void reversePrefix(int i) {
      for (int k = 0; k < i - 1 - k; k++) {
         int j = this.permutation[k];
         this.permutation[k] = this.permutation[i - 1 - k];
         this.permutation[i - 1 - k] = j;
      }
   }

   void rotatePrefix(int i, int j) {
      if (j > i - j) {
         this.rotatePrefix(i, j - i);
      } else if (j < -i - j) {
         this.rotatePrefix(i, j + i);
      } else if (j > 0) {
         int[] aint = new int[j];
         System.arraycopy(this.permutation, i - j, aint, 0, j);
         System.arraycopy(this.permutation, 0, this.permutation, j, i - j);
         System.arraycopy(aint, 0, this.permutation, 0, j);
      } else if (j < 0) {
         int[] aint1 = new int[-j];
         System.arraycopy(this.permutation, 0, aint1, 0, -j);
         System.arraycopy(this.permutation, -j, this.permutation, 0, i + j);
         System.arraycopy(aint1, 0, this.permutation, i + j, -j);
      }
   }
}
