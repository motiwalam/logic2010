package edu.ucla.phil.logic;

import java.util.Enumeration;

public class C_n_F {
   boolean f1313;
   int f1314;
   private int f1315;
   int[] f1316;

   public C_n_F() {
      this.m1979();
   }

   public C_n_F(String s) {
      this();
      this.f1313 = s.indexOf("~") != -1;
      int[] aint = ExpressionPath.m1755(s);
      int i = aint == null ? 0 : aint.length;

      for (int j = 0; j < i; j++) {
         this.m1986(aint[j]);
      }
   }

   public static C_n_F m1970(int i) {
      return new C_n_F().m1986(i).m1986(i + 1);
   }

   public static C_n_F m1971(int i, int j) {
      C_n_F c_n_f = new C_n_F();
      if (j > 0) {
         c_n_f.m1986(i).m1986(i + j);
      }

      return c_n_f;
   }

   public static C_n_F m1972(int i) {
      return new C_n_F().m1986(i);
   }

   public static C_n_F m1973(int i) {
      return new C_n_F().m1986(i + 1);
   }

   public C_n_F m1974(boolean flag) {
      C_n_F c_n_f1 = new C_n_F();
      c_n_f1.f1313 = this.f1313;
      c_n_f1.f1314 = this.f1314;
      c_n_f1.f1316 = this.f1314 == 0 ? null : (flag ? new int[this.f1314] : this.f1316);
      if (this.f1314 != 0 && flag) {
         System.arraycopy(this.f1316, 0, c_n_f1.f1316, 0, this.f1314);
      }

      return c_n_f1;
   }

   public C_n_F m1975(C_n_F c_n_f1) {
      return this.m1980().m1977(c_n_f1.m1974(false).m1980()).m1980();
   }

   public C_n_F m1976(C_n_F c_n_f1) {
      return this.m1977(c_n_f1.m1974(false).m1980());
   }

   public C_n_F m1977(C_n_F c_n_f1) {
      if (c_n_f1.f1314 == 0) {
         return c_n_f1.f1313 ? this : this.m1979();
      } else {
         C_n_F c_n_f2 = new C_n_F();
         C_n_F c_n_f3 = this.m1974(false);

         C_n_F c_n_f4;
         for (c_n_f4 = c_n_f1.m1974(false); c_n_f3.f1315 < c_n_f3.f1314; c_n_f3.m1978()) {
            int i = c_n_f3.f1316[c_n_f3.f1315];
            int j = c_n_f4.f1316[c_n_f4.f1315];
            if (i > j || i == j && !c_n_f3.f1313 && c_n_f4.f1313) {
               C_n_F c_n_f5 = c_n_f3;
               c_n_f3 = c_n_f4;
               c_n_f4 = c_n_f5;
               i = j;
            }

            if (c_n_f4.f1313) {
               c_n_f2.m1986(i);
            }
         }

         if (c_n_f3.f1313) {
            for (int k = c_n_f4.f1315; k < c_n_f4.f1314; k++) {
               c_n_f2.m1986(c_n_f4.f1316[k]);
            }
         }

         this.f1313 = this.f1313 & c_n_f1.f1313;
         this.f1314 = c_n_f2.f1314;
         if (this.f1314 == 0) {
            this.f1316 = null;
         } else {
            this.f1316 = new int[this.f1314];
            System.arraycopy(c_n_f2.f1316, 0, this.f1316, 0, this.f1314);
         }

         return this;
      }
   }

   private void m1978() {
      this.f1315++;
      this.f1313 = !this.f1313;
   }

   public C_n_F m1979() {
      this.f1313 = false;
      this.f1314 = 0;
      this.f1315 = 0;
      this.f1316 = null;
      return this;
   }

   public C_n_F m1980() {
      this.f1313 = !this.f1313;
      return this;
   }

   public C_n_F m1981(int i) {
      for (int j = 0; j < this.f1314; j++) {
         this.f1316[j] = this.f1316[j] + i;
      }

      return this;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof C_n_F)) {
         return false;
      } else {
         C_n_F c_n_f1 = (C_n_F)object;
         if (this.f1313 == c_n_f1.f1313 && this.f1314 == c_n_f1.f1314) {
            for (int i = 0; i < this.f1314; i++) {
               if (this.f1316[i] != c_n_f1.f1316[i]) {
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
      int i = this.f1313 ? 1 : 0;

      for (int j = 0; j < this.f1314; j++) {
         i = i * 40503 + this.f1316[j];
      }

      return i;
   }

   public boolean m1982() {
      return this.f1314 == 0 && !this.f1313;
   }

   public boolean m1983(int i) {
      return !m1970(i).m1977(this).m1982();
   }

   String m1984(String s) {
      if (s == null) {
         return null;
      } else {
         C_n_F c_n_f1 = m1971(0, s.length());
         c_n_f1 = c_n_f1.m1977(this);
         String s1 = "";
         int i = c_n_f1.f1314 - 1;

         for (byte b0 = 0; b0 < i; b0 += 2) {
            s1 = s1 + s.substring(c_n_f1.f1316[b0], c_n_f1.f1316[b0 + 1]);
         }

         return s1;
      }
   }

   @Override
   public String toString() {
      return (this.f1313 ? "~" : "") + ExpressionPath.m1754(this.f1316, 0, this.f1314);
   }

   public Enumeration m1985() {
      return new C_SC(this);
   }

   C_n_F m1986(int i) {
      for (int j = this.f1314; j >= 0; j--) {
         if (j == 0 || this.f1316[j - 1] < i) {
            this.m1988(this.f1314 + 1);
            System.arraycopy(this.f1316, j, this.f1316, j + 1, this.f1314 - j);
            this.f1316[j] = i;
            this.f1314++;
            break;
         }

         if (this.f1316[j - 1] == i) {
            System.arraycopy(this.f1316, j, this.f1316, j - 1, this.f1314 - j);
            this.f1314--;
            break;
         }
      }

      return this;
   }

   C_n_F m1987(int[] aint) {
      int i = aint.length;

      for (int j = 0; j < i; j++) {
         this.m1986(aint[j]);
      }

      return this;
   }

   void m1988(int i) {
      int j = this.f1316 == null ? 0 : this.f1316.length;
      if (i > j) {
         if (j == 0) {
            j = 1;
         }

         while (i > j) {
            j *= 2;
         }

         int[] aint = new int[j];
         if (this.f1316 != null) {
            System.arraycopy(this.f1316, 0, aint, 0, this.f1314);
         }

         this.f1316 = aint;
      }
   }
}
