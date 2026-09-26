package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_ND extends Message {
   static Hashtable f648 = null;
   static String f649 = "parMessages";

   C_ND(String s) {
      super(s);
   }

   static boolean loadMessages() {
      ScrambledReader scrambledreader = LogicProgram.openDataFile(f649, false);
      if (scrambledreader == null) {
         return false;
      } else {
         f648 = parseMessages(new TaggedRecord(scrambledreader, true));
         return f648 != null;
      }
   }

   static Message get(String s) {
      Message message = f648 == null ? null : (Message)f648.get(s.toLowerCase());
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
