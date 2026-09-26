package edu.ucla.phil.logic;

import java.util.Vector;

abstract class C_i_A implements C_n_A {
   @Override
   public abstract int hashCode();

   @Override
   public abstract boolean equals(Object object);

   @Override
   public abstract String toString();

   abstract String m1173();

   abstract int m1174();

   abstract C_i_A m1177(Vector vector);

   abstract Vector m1176(boolean flag);

   abstract C_RF m1175();

   static String m1853(int i) {
      return "{" + (i + 1) + "}";
   }

   static String m1854(int i, int j) {
      String s = "";

      for (int k = 0; k < j; k++) {
         s = s + m1853(i + k);
      }

      return s;
   }
}
