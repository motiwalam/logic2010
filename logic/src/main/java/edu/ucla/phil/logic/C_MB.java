package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class C_MB extends Hashtable {
   C_RF m1091(C_RF c_rf) {
      return this.m1092(null, c_rf, null);
   }

   C_RF m1092(C_RF c_rf, C_RF c_rf1, Vector vector) {
      C_RF c_rf2 = null;
      Vector vector1 = vector == null ? null : (Vector)vector.clone();
      int i = vector1 == null ? 0 : vector1.size();

      while (true) {
         c_rf2 = (C_RF)this.get(new C_o_D(c_rf, c_rf1, vector1));
         if (c_rf2 != null || i == 0) {
            return c_rf2;
         }

         vector1.setSize(--i);
      }
   }

   C_RF m1093(C_RF c_rf, C_RF c_rf1) {
      return this.m1094(null, c_rf, null, c_rf1);
   }

   C_RF m1094(C_RF c_rf, C_RF c_rf1, Vector vector, C_RF c_rf2) {
      return (C_RF)this.put(new C_o_D(c_rf, c_rf1, vector), c_rf2);
   }

   int[][] m1095(C_RF c_rf, C_RF c_rf1) {
      Vector vector = c_rf.m1243();
      Vector vector1 = c_rf1.m1243();
      int i = vector.size();
      Vector[] avector = new Vector[i];

      for (int j = 0; j < i; j++) {
         avector[j] = new Vector();
      }

      Enumeration enumeration = this.keys();

      while (enumeration.hasMoreElements()) {
         C_o_D c_o_d = (C_o_D)enumeration.nextElement();
         int k;
         C_RF c_rf2;
         if (c_o_d.f1325 == null && (k = vector.indexOf(c_o_d.f1326)) != -1 && (c_rf2 = (C_RF)this.get(c_o_d)) != null) {
            avector[k].addElement(c_rf2);
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
