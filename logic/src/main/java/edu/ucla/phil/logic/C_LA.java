package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_LA extends Message {
   static Hashtable f501 = null;
   static String f502 = "invMessages";

   C_LA(String s) {
      super(s);
   }

   static boolean loadMessages() {
      ScrambledReader scrambledreader = LogicProgram.openDataFile(f502, false);
      if (scrambledreader == null) {
         return false;
      } else {
         f501 = parseMessages(new TaggedRecord(scrambledreader, true));
         return f501 != null;
      }
   }

   static Message get(String s) {
      Message message = f501 == null ? null : (Message)f501.get(s.toLowerCase());
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
