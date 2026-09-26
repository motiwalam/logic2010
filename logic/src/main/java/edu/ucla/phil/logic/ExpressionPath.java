package edu.ucla.phil.logic;

import java.util.Vector;

public class ExpressionPath implements Cloneable {
   int depth;
   int[] indexes;

   public ExpressionPath() {
      this.depth = 0;
      this.indexes = new int[3];
   }

   private ExpressionPath(int[] aint) {
      this.depth = aint.length;
      this.indexes = aint;
   }

   public static ExpressionPath m1746(int[] aint) {
      return aint == null ? null : new ExpressionPath(aint);
   }

   @Override
   public Object clone() {
      ExpressionPath expressionpath1 = null;

      try {
         expressionpath1 = (ExpressionPath)super.clone();
      } catch (CloneNotSupportedException clonenotsupportedexception) {
      }

      expressionpath1.indexes = new int[this.depth];
      if (this.depth > 0) {
         System.arraycopy(this.indexes, 0, expressionpath1.indexes, 0, this.depth);
      }

      return expressionpath1;
   }

   public void m1747(int[] aint) {
      this.m1748(aint, 0, aint.length);
   }

   public void m1748(int[] aint, int i, int j) {
      if (this.indexes.length < this.depth + j) {
         int k = this.indexes.length;

         while (k < this.depth + j) {
            k = 2 * k + 1;
         }

         int[] aint1 = new int[k];
         if (this.depth > 0) {
            System.arraycopy(this.indexes, 0, aint1, 0, this.depth);
         }

         this.indexes = aint1;
      }

      if (j > 0) {
         System.arraycopy(aint, i, this.indexes, this.depth, j);
      }

      this.depth += j;
   }

   public void m1749(int i) {
      if (this.indexes.length <= this.depth) {
         int j = this.indexes.length;

         while (j <= this.depth) {
            j = 2 * j + 1;
         }

         int[] aint = new int[j];
         if (this.depth > 0) {
            System.arraycopy(this.indexes, 0, aint, 0, this.depth);
         }

         this.indexes = aint;
      }

      this.indexes[this.depth] = i;
      this.depth++;
   }

   public int m1750(int i) {
      for (int j = 0; j < this.depth; j++) {
         if (this.indexes[j] == i) {
            return j;
         }
      }

      return -1;
   }

   public int m1751(ExpressionPath expressionpath1) {
      if (expressionpath1 == null) {
         return 0;
      } else {
         int i = Math.min(this.depth, expressionpath1.depth);

         for (int j = 0; j < i; j++) {
            if (this.indexes[j] != expressionpath1.indexes[j]) {
               return j;
            }
         }

         return i;
      }
   }

   public int[] m1752() {
      int[] aint = new int[this.depth];
      if (this.depth > 0) {
         System.arraycopy(this.indexes, 0, aint, 0, this.depth);
      }

      return aint;
   }

   @Override
   public String toString() {
      return m1754(this.indexes, 0, this.depth);
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
      if (!(object instanceof ExpressionPath)) {
         return false;
      } else {
         ExpressionPath expressionpath1 = (ExpressionPath)object;
         if (expressionpath1.depth != this.depth) {
            return false;
         } else {
            for (int i = 0; i < this.depth; i++) {
               if (expressionpath1.indexes[i] != this.indexes[i]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Override
   public int hashCode() {
      int i = this.depth;

      for (int j = 0; j < this.depth; j++) {
         i = i * 40503 + this.indexes[j];
      }

      return i;
   }
}
