package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_XA implements ResponseHandler {
   String f864 = C_GE.m644(".");
   int f865;
   String f866;
   ServerSession f867;
   String f868;
   String f869;
   String f870;
   int f871;
   C_w_D[] f872;

   C_XA(int i, ServerSession serversession, String s) {
      this.f865 = i;
      this.f866 = s;
      this.f867 = serversession;
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

   boolean m1468(int i, BusyIndicator busyindicator, NetworkTask networktask) {
      boolean flag = true;
      int j = this.m1463();
      if (busyindicator != null) {
         busyindicator.m2162(true);
      }

      while (j > i) {
         flag = false;
         if (!this.m1467() || !ServerConnection.m893(this, busyindicator, networktask) || !this.m1465()) {
            break;
         }

         flag = true;
         j--;
      }

      if (busyindicator != null) {
         busyindicator.m2162(false);
      }

      return flag;
   }

   @Override
   public void m4(String s, Hashtable hashtable) {
      this.f867.m4(s, hashtable);
   }

   @Override
   public ErrorRef m5() {
      return this.f867.m5();
   }
}
