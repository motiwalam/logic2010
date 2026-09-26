package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_BF extends Message {
   static Hashtable f247 = null;
   static String f248 = "recMessages";

   C_BF(String s) {
      super(s);
   }

   static boolean loadMessages() {
      ScrambledReader scrambledreader = LogicProgram.openDataFile(f248, false);
      if (scrambledreader == null) {
         return false;
      } else {
         f247 = parseMessages(new TaggedRecord(scrambledreader, true));
         return f247 != null;
      }
   }

   static Message get(String s) {
      Message message = f247 == null ? null : (Message)f247.get(s.toLowerCase());
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
