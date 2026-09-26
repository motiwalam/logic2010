package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_h_E extends Message {
   static Hashtable f1162 = null;
   static String f1163 = "symMessages";

   C_h_E(String s) {
      super(s);
   }

   static boolean loadMessages() {
      ScrambledReader scrambledreader = LogicProgram.openDataFile(f1163, false);
      if (scrambledreader == null) {
         return false;
      } else {
         f1162 = parseMessages(new TaggedRecord(scrambledreader, true));
         return f1162 != null;
      }
   }

   static Message get(String s) {
      Message message = f1162 == null ? null : (Message)f1162.get(s.toLowerCase());
      if (message == null) {
         message = new Message(s);
         message.title = "bad error id";
         message.text = "The program has encountered an unknown error id.  Please report this: " + message.id;
         message.isError = true;
      }

      return message;
   }

   static String getText(String s) {
      return m659(get(s));
   }
}
