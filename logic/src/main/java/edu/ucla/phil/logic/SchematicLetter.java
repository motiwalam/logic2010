package edu.ucla.phil.logic;

import java.util.Vector;

abstract class SchematicLetter implements LogicConstants {
   @Override
   public abstract int hashCode();

   @Override
   public abstract boolean equals(Object object);

   @Override
   public abstract String toString();

   abstract String getLetter();

   abstract int getArity();

   abstract SchematicLetter freshLetter(Vector vector);

   abstract Vector getDeferredMatches(boolean flag);

   abstract Expression toExpression();

   static String placeholder(int i) {
      return "{" + (i + 1) + "}";
   }

   static String placeholders(int i, int j) {
      String s = "";

      for (int k = 0; k < j; k++) {
         s = s + placeholder(i + k);
      }

      return s;
   }
}
