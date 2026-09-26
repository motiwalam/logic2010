package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_XA implements C_v_ {
   String f864 = C_GE.m644(".");
   int f865;
   String f866;
   C_0C f867;
   String f868;
   String f869;
   String f870;
   int f871;
   C_w_D[] f872;

   C_XA(int i, C_0C c_0c, String s) {
      this.f865 = i;
      this.f866 = s;
      this.f867 = c_0c;
      this.m1461();
   }

   void m1461() {
      this.f869 = null;
      this.f868 = null;
      this.f870 = null;
      this.f871 = 0;
      this.f872 = null;
   }

   boolean m1462() {
      return this.f866 != null && this.f867 != null;
   }

   int m1463() {
      return this.m1464(this.f868);
   }

   int m1464(String s) {
      if (s == null) {
         return 0;
      } else {
         int i = this.f872 == null ? 0 : this.f872.length;
         int j = 0;

         for (int k = 0; k < i; k++) {
            C_w_D c_w_d = this.f872[k];
            if (c_w_d.m2159() && c_w_d.f1429.equalsIgnoreCase(s)) {
               j++;
            }
         }

         return j;
      }
   }

   boolean m1465() {
      int i = this.f872 == null ? 0 : this.f872.length;

      for (int j = 0; j < i; j++) {
         C_w_D c_w_d = this.f872[j];
         if (c_w_d.m2159() && c_w_d.f1431 == this.f871) {
            c_w_d.m2160();
            return true;
         }
      }

      return false;
   }

   boolean m1466() {
      if (this.f868 == null) {
         return false;
      } else {
         C_w_D c_w_d = null;
         int i = this.f872 == null ? 0 : this.f872.length;

         for (int j = 0; j < i; j++) {
            C_w_D c_w_d1 = this.f872[j];
            if (c_w_d1.m2159() && c_w_d1.f1429.equalsIgnoreCase(this.f868) && (c_w_d == null || c_w_d1.f1430.compareTo(c_w_d.f1430) > 0)) {
               c_w_d = c_w_d1;
            }
         }

         if (c_w_d == null) {
            return false;
         } else {
            this.f871 = c_w_d.f1431;
            return true;
         }
      }
   }

   boolean m1467() {
      if (this.f868 == null) {
         return false;
      } else {
         C_w_D c_w_d = null;
         int i = this.f872 == null ? 0 : this.f872.length;

         for (int j = 0; j < i; j++) {
            C_w_D c_w_d1 = this.f872[j];
            if (c_w_d1.m2159() && c_w_d1.f1429.equalsIgnoreCase(this.f868) && (c_w_d == null || c_w_d1.f1430.compareTo(c_w_d.f1430) < 0)) {
               c_w_d = c_w_d1;
            }
         }

         if (c_w_d == null) {
            return false;
         } else {
            this.f871 = c_w_d.f1431;
            return true;
         }
      }
   }

   boolean m1468(int i, C_x_A c_x_a, C_r_A c_r_a) {
      boolean flag = true;
      int j = this.m1463();
      if (c_x_a != null) {
         c_x_a.m2162(true);
      }

      while (j > i) {
         flag = false;
         if (!this.m1467() || !C_KC.m893(this, c_x_a, c_r_a) || !this.m1465()) {
            break;
         }

         flag = true;
         j--;
      }

      if (c_x_a != null) {
         c_x_a.m2162(false);
      }

      return flag;
   }

   @Override
   public void m4(String s, Hashtable hashtable) {
      this.f867.m4(s, hashtable);
   }

   @Override
   public C_c_B m5() {
      return this.f867.m5();
   }
}
