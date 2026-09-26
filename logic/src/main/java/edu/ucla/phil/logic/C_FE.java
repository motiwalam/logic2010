package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_FE extends Message {
   static Hashtable f315 = null;
   static String f316 = "truMessages";

   C_FE(String s) {
      super(s);
   }

   static boolean loadMessages() {
      ScrambledReader scrambledreader = LogicProgram.openDataFile(f316, false);
      if (scrambledreader == null) {
         return false;
      } else {
         f315 = parseMessages(new TaggedRecord(scrambledreader, true));
         return f315 != null;
      }
   }

   static Message get(String s) {
      Message message = f315 == null ? null : (Message)f315.get(s.toLowerCase());
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
