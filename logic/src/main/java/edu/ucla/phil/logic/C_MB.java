package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class C_MB extends Hashtable {
   Expression m1091(Expression expression) {
      return this.m1092(null, expression, null);
   }

   Expression m1092(Expression expression, Expression expression1, Vector vector) {
      Expression expression2 = null;
      Vector vector1 = vector == null ? null : (Vector)vector.clone();
      int i = vector1 == null ? 0 : vector1.size();

      while (true) {
         expression2 = (Expression)this.get(new C_o_D(expression, expression1, vector1));
         if (expression2 != null || i == 0) {
            return expression2;
         }

         vector1.setSize(--i);
      }
   }

   Expression m1093(Expression expression, Expression expression1) {
      return this.m1094(null, expression, null, expression1);
   }

   Expression m1094(Expression expression, Expression expression1, Vector vector, Expression expression2) {
      return (Expression)this.put(new C_o_D(expression, expression1, vector), expression2);
   }

   int[][] m1095(Expression expression, Expression expression1) {
      Vector vector = expression.m1243();
      Vector vector1 = expression1.m1243();
      int i = vector.size();
      Vector[] avector = new Vector[i];

      for (int j = 0; j < i; j++) {
         avector[j] = new Vector();
      }

      Enumeration enumeration = this.keys();

      while (enumeration.hasMoreElements()) {
         C_o_D c_o_d = (C_o_D)enumeration.nextElement();
         int k;
         Expression expression2;
         if (c_o_d.f1325 == null && (k = vector.indexOf(c_o_d.f1326)) != -1 && (expression2 = (Expression)this.get(c_o_d)) != null) {
            avector[k].addElement(expression2);
         }
      }

      int[][] aint = new int[i][];

      for (int j1 = 0; j1 < i; j1++) {
         Vector vector2 = avector[j1];
         int l = vector2.size();
         aint[j1] = new int[l];

         for (int i1 = 0; i1 < l; i1++) {
            aint[j1][i1] = vector1.indexOf(vector2.elementAt(i1));
         }
      }

      return aint;
   }
}
