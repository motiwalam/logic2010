package edu.ucla.phil.logic;

import java.util.Vector;

public class C_e_ implements Cloneable {
   int f1062;
   int[] f1063;

   public C_e_() {
      this.f1062 = 0;
      this.f1063 = new int[3];
   }

   private C_e_(int[] aint) {
      this.f1062 = aint.length;
      this.f1063 = aint;
   }

   public static C_e_ m1746(int[] aint) {
      return aint == null ? null : new C_e_(aint);
   }

   @Override
   public Object clone() {
      C_e_ c_e_1 = null;

      try {
         c_e_1 = (C_e_)super.clone();
      } catch (CloneNotSupportedException clonenotsupportedexception) {
      }

      c_e_1.f1063 = new int[this.f1062];
      if (this.f1062 > 0) {
         System.arraycopy(this.f1063, 0, c_e_1.f1063, 0, this.f1062);
      }

      return c_e_1;
   }

   public void m1747(int[] aint) {
      this.m1748(aint, 0, aint.length);
   }

   public void m1748(int[] aint, int i, int j) {
      if (this.f1063.length < this.f1062 + j) {
         int k = this.f1063.length;

         while (k < this.f1062 + j) {
            k = 2 * k + 1;
         }

         int[] aint1 = new int[k];
         if (this.f1062 > 0) {
            System.arraycopy(this.f1063, 0, aint1, 0, this.f1062);
         }

         this.f1063 = aint1;
      }

      if (j > 0) {
         System.arraycopy(aint, i, this.f1063, this.f1062, j);
      }

      this.f1062 += j;
   }

   public void m1749(int i) {
      if (this.f1063.length <= this.f1062) {
         int j = this.f1063.length;

         while (j <= this.f1062) {
            j = 2 * j + 1;
         }

         int[] aint = new int[j];
         if (this.f1062 > 0) {
            System.arraycopy(this.f1063, 0, aint, 0, this.f1062);
         }

         this.f1063 = aint;
      }

      this.f1063[this.f1062] = i;
      this.f1062++;
   }

   public int m1750(int i) {
      for (int j = 0; j < this.f1062; j++) {
         if (this.f1063[j] == i) {
            return j;
         }
      }

      return -1;
   }

   public int m1751(C_e_ c_e_1) {
      if (c_e_1 == null) {
         return 0;
      } else {
         int i = Math.min(this.f1062, c_e_1.f1062);

         for (int j = 0; j < i; j++) {
            if (this.f1063[j] != c_e_1.f1063[j]) {
               return j;
            }
         }

         return i;
      }
   }

   public int[] m1752() {
      int[] aint = new int[this.f1062];
      if (this.f1062 > 0) {
         System.arraycopy(this.f1063, 0, aint, 0, this.f1062);
      }

      return aint;
   }

   @Override
   public String toString() {
      return m1754(this.f1063, 0, this.f1062);
   }

   public static String m1753(int[] aint) {
      return m1754(aint, 0, aint.length);
   }

   public static String m1754(int[] aint, int i, int j) {
      String s = "{";

      for (int k = 0; k < j; k++) {
         s = s + (k == 0 ? "" : ",") + aint[i + k];
      }

      return s + "}";
   }

   public static int[] m1755(String s) {
      int i = s.indexOf("{");
      int j = s.indexOf("}");
      if (i != -1 && j != -1 && j >= i) {
         String s1 = s.substring(i + 1, j).trim();
         Vector vector = new Vector();

         while (!s1.equals("")) {
            int k = s1.indexOf(",");
            if (k == -1) {
               vector.addElement(s1);
               s1 = "";
            } else {
               vector.addElement(s1.substring(0, k).trim());
               s1 = s1.substring(k + 1).trim();
            }
         }

         int i1 = vector.size();
         int[] aint = new int[i1];

         for (int l = 0; l < i1; l++) {
            try {
               aint[l] = Integer.parseInt((String)vector.elementAt(l));
            } catch (NumberFormatException numberformatexception) {
               return null;
            }
         }

         return aint;
      } else {
         return null;
      }
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof C_e_)) {
         return false;
      } else {
         C_e_ c_e_1 = (C_e_)object;
         if (c_e_1.f1062 != this.f1062) {
            return false;
         } else {
            for (int i = 0; i < this.f1062; i++) {
               if (c_e_1.f1063[i] != this.f1063[i]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Override
   public int hashCode() {
      int i = this.f1062;

      for (int j = 0; j < this.f1062; j++) {
         i = i * 40503 + this.f1063[j];
      }

      return i;
   }
}
