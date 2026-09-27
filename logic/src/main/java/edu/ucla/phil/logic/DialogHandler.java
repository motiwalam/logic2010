package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class DialogHandler {
   Hashtable properties;
   int defaultIndex;
   String[] labels;
   String[] actions;

   DialogHandler(String s) {
      if (s == null) {
         s = "OK";
      }

      this.properties = null;
      this.defaultIndex = -1;
      Vector vector = new Vector();
      Vector vector1 = new Vector();
      DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\;");
      DelimitedTokenizer delimitedtokenizer1 = new DelimitedTokenizer("\\:.");
      DelimitedTokenizer delimitedtokenizer2 = new DelimitedTokenizer("\\.");
      delimitedtokenizer.setInput(s);
      delimitedtokenizer1.setInput(delimitedtokenizer.nextToken(true));
      String s1 = delimitedtokenizer.getRemaining();
      if (s1 != null) {
         Integer integer = LogicProgram.parseInteger(s1.trim());
         if (integer != null) {
            this.defaultIndex = integer;
         }
      }

      while (true) {
         String s3 = delimitedtokenizer1.nextToken();
         String s2 = null;
         if (s3 == null) {
            this.labels = new String[vector.size()];
            this.actions = new String[vector.size()];
            vector.copyInto(this.labels);
            vector1.copyInto(this.actions);
            return;
         }

         if (delimitedtokenizer1.getDelimiter() == ':') {
            delimitedtokenizer2.setInput(delimitedtokenizer1.getRemaining());
            s2 = delimitedtokenizer2.nextToken().trim();
            delimitedtokenizer1.setInput(delimitedtokenizer2.getRemaining());
         }

         vector.addElement(s3.trim());
         vector1.addElement(s2);
      }
   }

   String[] getLabels() {
      return this.labels;
   }

   int getDefaultIndex() {
      return this.defaultIndex;
   }

   void setProperty(String s, Object object) {
      if (object != null) {
         if (this.properties == null) {
            this.properties = new Hashtable();
         }

         this.properties.put(s, object);
      }
   }

   void setProperties(Hashtable hashtable) {
      Enumeration enumeration = hashtable.keys();

      while (enumeration.hasMoreElements()) {
         String s = (String)enumeration.nextElement();
         Object object = hashtable.get(s);
         this.setProperty(s, object);
      }
   }

   Object getProperty(String s) {
      return this.properties == null ? null : this.properties.get(s);
   }

   String getSelectedAction(MessageDialog messagedialog) {
      String s = messagedialog.buttons[messagedialog.selectedButton].getText();
      int i = LogicProgram.indexOf(this.labels, s);
      return i == -1 ? null : this.actions[i];
   }

   boolean handleChoice(MessageDialog messagedialog) {
      String s = this.getSelectedAction(messagedialog);
      return s == null ? true : true;
   }
}
