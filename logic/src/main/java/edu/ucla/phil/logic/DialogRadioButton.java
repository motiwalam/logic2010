package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Event;

class DialogRadioButton extends LogicRadioButton {
   DialogRadioButton(String s) {
      super(s);
   }

   DialogRadioButton(Component component) {
      super(component);
   }

   @Override
   public boolean handleEvent(Event event) {
      if (event.id == 401 && event.key == 10) {
         Component object = this;

         while (object != null && !(object instanceof MessageDialog)) {
            object = object.getParent();
         }

         if (object != null) {
            return ((MessageDialog)object).handleEvent(event);
         }
      }

      return super.handleEvent(event);
   }
}
