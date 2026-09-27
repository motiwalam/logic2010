package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Hashtable;

class BusyIndicator {
   Cursor savedCursor;
   Component component;
   BaseDialog dialog;

   BusyIndicator(Component componentx, boolean flag) {
      this(componentx);
      this.setBusy(flag);
   }

   BusyIndicator(Component componentx) {
      this.component = componentx;
      this.savedCursor = null;
      this.dialog = null;
   }

   void setBusy(boolean flag) {
      if (!flag && this.dialog != null) {
         MessageDialog.closeNotice(this.dialog);
         this.dialog = null;
      }

      if (this.component != null) {
         if (flag && this.savedCursor == null) {
            this.savedCursor = this.component.getCursor();
            this.component.setCursor(Cursor.getPredefinedCursor(3));
         } else if (!flag && this.savedCursor != null) {
            this.component.setCursor(this.savedCursor);
            this.savedCursor = null;
         }
      }
   }

   void showBusyMessage(Message message, Hashtable hashtable) {
      if (this.savedCursor == null) {
         this.dialog = MessageDialog.showNotice(message, hashtable);
         this.setBusy(true);
      }
   }
}
