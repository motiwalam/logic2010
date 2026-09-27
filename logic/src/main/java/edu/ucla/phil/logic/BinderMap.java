package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class BinderMap extends Hashtable {
   Expression getCounterpart(Expression expression) {
      return this.getCounterpart(null, expression, null);
   }

   Expression getCounterpart(Expression expression, Expression expression1, Vector vector) {
      Expression expression2 = null;
      Vector vector1 = vector == null ? null : (Vector)vector.clone();
      int i = vector1 == null ? 0 : vector1.size();

      while (true) {
         expression2 = (Expression)this.get(new BinderKey(expression, expression1, vector1));
         if (expression2 != null || i == 0) {
            return expression2;
         }

         vector1.setSize(--i);
      }
   }

   Expression putCounterpart(Expression expression, Expression expression1) {
      return this.putCounterpart(null, expression, null, expression1);
   }

   Expression putCounterpart(Expression expression, Expression expression1, Vector vector, Expression expression2) {
      return (Expression)this.put(new BinderKey(expression, expression1, vector), expression2);
   }

   int[][] getBinderCorrespondence(Expression expression, Expression expression1) {
      Vector vector = expression.getBinders();
      Vector vector1 = expression1.getBinders();
      int i = vector.size();
      Vector[] avector = new Vector[i];

      for (int j = 0; j < i; j++) {
         avector[j] = new Vector();
      }

      Enumeration enumeration = this.keys();

      while (enumeration.hasMoreElements()) {
         BinderKey binderkey = (BinderKey)enumeration.nextElement();
         int k;
         Expression expression2;
         if (binderkey.context == null && (k = vector.indexOf(binderkey.binder)) != -1 && (expression2 = (Expression)this.get(binderkey)) != null) {
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
