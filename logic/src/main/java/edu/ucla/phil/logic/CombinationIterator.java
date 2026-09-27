package edu.ucla.phil.logic;

public class CombinationIterator {
   int n;
   int k;
   int[] indices;

   public CombinationIterator(int i, int j) {
      if (j > i) {
         throw new IllegalArgumentException("size of combination exceeds length of data");
      } else {
         this.n = i;
         this.k = j;
         this.indices = new int[j];
         this.reset();
      }
   }

   public int[] getCombination() {
      int[] aint = new int[this.k];
      System.arraycopy(this.indices, 0, aint, 0, this.k);
      return aint;
   }

   public int[] getIndices() {
      int[] aint = new int[this.k];
      System.arraycopy(this.indices, 0, aint, 0, this.k);
      return aint;
   }

   public void setCombination(int[] aint) {
      int i = aint == null ? 0 : aint.length;
      if (this.k < i) {
         i = this.k;
      }

      if (i > 0) {
         System.arraycopy(aint, 0, this.indices, 0, i);
      }

      for (int j = i; j < this.k; j++) {
         this.indices[j] = 0;
      }

      this.normalize();
   }

   public boolean next() {
      int i = 0;

      while (i < this.k - 1 && this.indices[i] == this.indices[i + 1] - 1) {
         this.indices[i] = i++;
      }

      this.indices[i]++;
      if (i >= this.k - 1 && this.indices[i] >= this.n) {
         this.indices[i] = i;
         return false;
      } else {
         return true;
      }
   }

   public boolean previous() {
      int i = 0;

      while (i < this.k && this.indices[i] == i) {
         i++;
      }

      if (i < this.k) {
         this.indices[i]--;
      } else {
         this.indices[--i] = this.n - 1;
      }

      while (--i >= 0) {
         this.indices[i] = this.indices[i + 1] - 1;
      }

      return this.indices[0] < this.n - this.k;
   }

   public void reset() {
      int i = 0;

      while (i < this.k) {
         this.indices[i] = i++;
      }
   }

   @Override
   public String toString() {
      String s = " [ ";

      for (int i = 0; i < this.n; i++) {
         s = s + (i == 0 ? "" : ", ") + this.indices[i];
      }

      return s + " ]";
   }

   void normalize() {
      int i = 0;

      for (int j = 0; j < this.k; j++) {
         if (this.indices[j] < i) {
            this.indices[j] = i;
         } else if (this.indices[j] > this.n - this.k + j) {
            this.indices[j] = this.n - this.k + j;
         }

         i = this.indices[j] + 1;
      }
   }
}
