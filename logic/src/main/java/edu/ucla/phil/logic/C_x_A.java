package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Hashtable;

class C_x_A {
   Cursor f1432;
   Component f1433;
   C_u_B f1434;

   C_x_A(Component component, boolean flag) {
      this(component);
      this.m2162(flag);
   }

   C_x_A(Component component) {
      this.f1433 = component;
      this.f1432 = null;
      this.f1434 = null;
   }

   void m2162(boolean flag) {
      if (!flag && this.f1434 != null) {
         C_UA.m1331(this.f1434);
         this.f1434 = null;
      }

      if (this.f1433 != null) {
         if (flag && this.f1432 == null) {
            this.f1432 = this.f1433.getCursor();
            this.f1433.setCursor(Cursor.getPredefinedCursor(3));
         } else if (!flag && this.f1432 != null) {
            this.f1433.setCursor(this.f1432);
            this.f1432 = null;
         }
      }
   }

   void m2163(C_H c_h, Hashtable hashtable) {
      if (this.f1432 == null) {
         this.f1434 = C_UA.m1330(c_h, hashtable);
         this.m2162(true);
      }
   }
}
