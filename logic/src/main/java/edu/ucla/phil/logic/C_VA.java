package edu.ucla.phil.logic;

public class C_VA {
   int f817;
   int f818;
   int[] f819;

   public C_VA(int i, int j) {
      if (j > i) {
         throw new IllegalArgumentException("size of combination exceeds length of data");
      } else {
         this.f817 = i;
         this.f818 = j;
         this.f819 = new int[j];
         this.m1364();
      }
   }

   public int[] m1359() {
      int[] aint = new int[this.f818];
      System.arraycopy(this.f819, 0, aint, 0, this.f818);
      return aint;
   }

   public int[] m1360() {
      int[] aint = new int[this.f818];
      System.arraycopy(this.f819, 0, aint, 0, this.f818);
      return aint;
   }

   public void m1361(int[] aint) {
      int i = aint == null ? 0 : aint.length;
      if (this.f818 < i) {
         i = this.f818;
      }

      if (i > 0) {
         System.arraycopy(aint, 0, this.f819, 0, i);
      }

      for (int j = i; j < this.f818; j++) {
         this.f819[j] = 0;
      }

      this.m1365();
   }

   public boolean m1362() {
      int i = 0;

      while (i < this.f818 - 1 && this.f819[i] == this.f819[i + 1] - 1) {
         this.f819[i] = i++;
      }

      this.f819[i]++;
      if (i >= this.f818 - 1 && this.f819[i] >= this.f817) {
         this.f819[i] = i;
         return false;
      } else {
         return true;
      }
   }

   public boolean m1363() {
      int i = 0;

      while (i < this.f818 && this.f819[i] == i) {
         i++;
      }

      if (i < this.f818) {
         this.f819[i]--;
      } else {
         this.f819[--i] = this.f817 - 1;
      }

      while (--i >= 0) {
         this.f819[i] = this.f819[i + 1] - 1;
      }

      return this.f819[0] < this.f817 - this.f818;
   }

   public void m1364() {
      int i = 0;

      while (i < this.f818) {
         this.f819[i] = i++;
      }
   }

   @Override
   public String toString() {
      String s = " [ ";

      for (int i = 0; i < this.f817; i++) {
         s = s + (i == 0 ? "" : ", ") + this.f819[i];
      }

      return s + " ]";
   }

   void m1365() {
      int i = 0;

      for (int j = 0; j < this.f818; j++) {
         if (this.f819[j] < i) {
            this.f819[j] = i;
         } else if (this.f819[j] > this.f817 - this.f818 + j) {
            this.f819[j] = this.f817 - this.f818 + j;
         }

         i = this.f819[j] + 1;
      }
   }
}
