package edu.ucla.phil.logic;

import java.awt.Rectangle;

class C_i_F extends Rectangle {
   C_i_F f1174;
   Object f1175;

   C_i_F(int i, int j, int k, Object object) {
      super(j, k);
      this.y = i;
      this.f1175 = object;
      this.f1174 = null;
   }

   C_i_F(int i, Object object) {
      this(i, 1, 1, object);
   }

   C_i_F m1862(C_i_F c_i_f1) {
      C_i_F c_i_f2 = this;

      while (c_i_f2 != null && c_i_f2.y + c_i_f2.height <= c_i_f1.y) {
         c_i_f2 = c_i_f2.f1174;
      }

      if (c_i_f2 != null && c_i_f2.x <= 0) {
         C_i_F c_i_f3 = c_i_f2;

         while (true) {
            while (c_i_f2.f1174 != null && c_i_f2.f1174.y + c_i_f2.f1174.height <= c_i_f1.y) {
               c_i_f2.f1174 = c_i_f2.f1174.f1174;
            }

            if (c_i_f2.f1174 == null || c_i_f2.f1174.x > c_i_f2.x + c_i_f2.width) {
               c_i_f1.f1174 = c_i_f2.f1174;
               c_i_f2.f1174 = c_i_f1;
               c_i_f1.x = c_i_f2.x + c_i_f2.width;
               return c_i_f3;
            }

            c_i_f2 = c_i_f2.f1174;
         }
      } else {
         c_i_f1.f1174 = c_i_f2;
         c_i_f1.x = 0;
         return c_i_f1;
      }
   }

   @Override
   public String toString() {
      return "\r\nHTableCell[x=" + this.x + ",y=" + this.y + ",width=" + this.width + ",height=" + this.height + ",content=" + this.f1175 + "]";
   }
}
