package edu.ucla.phil.logic;

public class C_XE {
   int f879;
   int[] f880;
   int[] f881;

   public C_XE(int i) {
      this.f879 = i;
      if (i == 0) {
         this.f880 = null;
      } else {
         this.f880 = new int[i - 1];
      }

      this.f881 = new int[i];
      this.m1517();
   }

   public C_XE(int[] aint) {
      if (aint == null) {
         this.f879 = 0;
         this.f880 = null;
      } else {
         this.f879 = aint.length + 1;
         this.f880 = new int[this.f879 - 1];
      }

      this.f881 = new int[this.f879];
      this.m1516(aint);
   }

   public int[] m1512() {
      int[] aint = new int[this.f879];
      System.arraycopy(this.f881, 0, aint, 0, this.f879);
      return aint;
   }

   public boolean m1513() {
      int i;
      for (i = 0; i < this.f879 - 1 && ++this.f880[i] > i + 1; i++) {
         this.f880[i] = 0;
      }

      if (i < this.f879 - 1) {
         this.m1518(i + 2);
         return true;
      } else {
         this.m1518(this.f879);
         return false;
      }
   }

   public boolean m1514() {
      int i;
      for (i = 0; i < this.f879 - 1 && --this.f880[i] < 0; i++) {
         this.f880[i] = i + 1;
      }

      if (i < this.f879 - 1) {
         this.m1518(i + 2);
         return true;
      } else {
         this.m1518(this.f879);
         return false;
      }
   }

   public int[] m1515() {
      if (this.f880 == null) {
         return null;
      } else {
         int[] aint = new int[this.f879 - 1];
         System.arraycopy(this.f880, 0, aint, 0, this.f879 - 1);
         return aint;
      }
   }

   public void m1516(int[] aint) {
      this.m1517();
      if (aint != null) {
         for (int i = Math.min(aint.length - 1, this.f879 - 2); i >= 0; i--) {
            int j = aint[i] % (i + 2);
            if (j < 0) {
               j += i + 2;
            }

            this.m1519(i + 2, j);
            this.f880[i] = j;
         }
      }
   }

   public void m1517() {
      for (int i = 0; i < this.f879 - 1; i++) {
         this.f881[i] = i;
         this.f880[i] = 0;
      }

      if (this.f879 > 0) {
         this.f881[this.f879 - 1] = this.f879 - 1;
      }
   }

   @Override
   public String toString() {
      return ExpressionPath.m1753(this.f881);
   }

   void m1518(int i) {
      for (int k = 0; k < i - 1 - k; k++) {
         int j = this.f881[k];
         this.f881[k] = this.f881[i - 1 - k];
         this.f881[i - 1 - k] = j;
      }
   }

   void m1519(int i, int j) {
      if (j > i - j) {
         this.m1519(i, j - i);
      } else if (j < -i - j) {
         this.m1519(i, j + i);
      } else if (j > 0) {
         int[] aint = new int[j];
         System.arraycopy(this.f881, i - j, aint, 0, j);
         System.arraycopy(this.f881, 0, this.f881, j, i - j);
         System.arraycopy(aint, 0, this.f881, 0, j);
      } else if (j < 0) {
         int[] aint1 = new int[-j];
         System.arraycopy(this.f881, 0, aint1, 0, -j);
         System.arraycopy(this.f881, -j, this.f881, 0, i + j);
         System.arraycopy(aint1, 0, this.f881, i + j, -j);
      }
   }
}
