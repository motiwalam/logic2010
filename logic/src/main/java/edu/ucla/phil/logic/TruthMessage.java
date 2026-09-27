package edu.ucla.phil.logic;

import java.util.Hashtable;

class TruthMessage extends Message {
   static Hashtable messages = null;
   static String linkName = "truMessages";

   TruthMessage(String s) {
      super(s);
   }

   static boolean loadMessages() {
      ScrambledReader scrambledreader = LogicProgram.openDataFile(linkName, false);
      if (scrambledreader == null) {
         return false;
      } else {
         messages = parseMessages(new TaggedRecord(scrambledreader, true));
         return messages != null;
      }
   }

   static Message get(String s) {
      Message message = messages == null ? null : (Message)messages.get(s.toLowerCase());
      if (message == null) {
         message = new Message(s);
         message.title = "bad error id";
         message.text = "The program has encountered an unknown error id.  Please report this: " + message.id;
         message.isError = true;
      }

      return message;
   }

   static String getText(String s) {
      return getText(get(s));
   }
}
