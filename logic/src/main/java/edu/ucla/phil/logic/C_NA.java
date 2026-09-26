package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Event;

class C_NA extends C_NF {
   C_NA(String s) {
      super(s);
   }

   C_NA(Component component) {
      super(component);
   }

   @Override
   public boolean handleEvent(Event event) {
      if (event.id == 401 && event.key == 10) {
         Object object = this;

         while (object != null && !(object instanceof C_UA)) {
            object = object.getParent();
         }

         if (object != null) {
            return ((C_UA)object).handleEvent(event);
         }
      }

      return super.handleEvent(event);
   }
}
