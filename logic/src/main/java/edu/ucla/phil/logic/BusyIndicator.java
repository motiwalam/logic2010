package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Hashtable;

class BusyIndicator {
   Cursor f1432;
   Component f1433;
   BaseDialog f1434;

   BusyIndicator(Component component, boolean flag) {
      this(component);
      this.m2162(flag);
   }

   BusyIndicator(Component component) {
      this.f1433 = component;
      this.f1432 = null;
      this.f1434 = null;
   }

   void m2162(boolean flag) {
      if (!flag && this.f1434 != null) {
         MessageDialog.m1331(this.f1434);
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

   void m2163(Message message, Hashtable hashtable) {
      if (this.f1432 == null) {
         this.f1434 = MessageDialog.m1330(message, hashtable);
         this.m2162(true);
      }
   }
}
