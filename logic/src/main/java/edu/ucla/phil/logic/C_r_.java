package edu.ucla.phil.logic;

import java.awt.Rectangle;
import java.util.Vector;

class C_r_ extends Vector {
   C_i_F f1343 = null;

   void m2046(C_i_F c_i_f) {
      this.addElement(c_i_f);
      this.f1343 = this.f1343 == null ? c_i_f : this.f1343.m1862(c_i_f);
   }

   int m2047(String s, int i) {
      int j = s.length();
      String s1 = System.getProperty("line.separator");
      this.setSize(0);
      this.f1343 = null;
      int k = 0;
      int l = C_FA.m534(s, C_FA.m528("table", s, i));
      if (l == -1) {
         return -1;
      } else {
         int i1 = C_FA.m528("/table", s, l);
         if (i1 == -1) {
            return -1;
         } else {
            for (int j1 = C_FA.m534(s, C_FA.m529("tr", s, l, i1)); j1 != -1; k++) {
               int k1 = C_FA.m529("/tr", s, j1, i1);
               if (k1 == -1) {
                  return -1;
               }

               int l1 = C_FA.m529("td", s, j1, k1);

               while (l1 != -1) {
                  C_FA c_fa = C_FA.m515(s, l1);
                  int i2 = c_fa.m520("colspan", 1);
                  int j2 = c_fa.m520("rowspan", 1);
                  l1 = C_FA.m534(s, l1);
                  int k2 = C_FA.m529("/td", s, l1, k1);
                  if (k2 == -1) {
                     return -1;
                  }

                  String s2 = "";

                  for (int l2 = C_FA.m529(null, s, l1, k2); l2 != -1; l2 = C_FA.m529(null, s, l1, k2)) {
                     s2 = s2 + s.substring(l1, l2);
                     c_fa = C_FA.m515(s, l2);
                     l1 = C_FA.m534(s, l2);
                  }

                  s2 = s2 + s.substring(l1, k2);
                  this.m2046(new C_i_F(k, i2, j2, C_FA.m522(s2)));
                  l1 = C_FA.m529("td", s, C_FA.m534(s, k2), k1);
               }

               j1 = C_FA.m534(s, C_FA.m529("tr", s, C_FA.m534(s, k1), i1));
            }

            return C_FA.m534(s, i1);
         }
      }
   }

   C_i_F m2048(int i, int j) {
      int k = this.size();

      for (int l = 0; l < k; l++) {
         C_i_F c_i_f = (C_i_F)this.elementAt(l);
         if (i >= c_i_f.x && i < c_i_f.x + c_i_f.width && j >= c_i_f.y && j < c_i_f.y + c_i_f.height) {
            return c_i_f;
         }
      }

      return null;
   }

   Rectangle m2049() {
      Rectangle rectangle = new Rectangle();
      int i = this.size();

      for (int j = 0; j < i; j++) {
         rectangle.add((Rectangle)this.elementAt(j));
      }

      return rectangle;
   }

   Object[][] m2050() {
      Rectangle rectangle = this.m2049();
      Object[][] aobject = new Object[rectangle.height][rectangle.width];
      int i = this.size();

      for (int j = 0; j < i; j++) {
         C_i_F c_i_f = (C_i_F)this.elementAt(j);
         aobject[c_i_f.y][c_i_f.x] = c_i_f.f1175;
      }

      return aobject;
   }
}
