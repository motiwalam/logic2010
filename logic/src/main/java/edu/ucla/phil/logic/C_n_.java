package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_n_ extends Message {
   static Hashtable f1299 = null;
   static String f1300 = "derMessages";

   C_n_(String s) {
      super(s);
   }

   static boolean loadMessages() {
      ScrambledReader scrambledreader = LogicProgram.openDataFile(f1300, false);
      if (scrambledreader == null) {
         return false;
      } else {
         f1299 = parseMessages(new TaggedRecord(scrambledreader, true));
         return f1299 != null;
      }
   }

   static Message get(String s) {
      Message message = f1299 == null ? null : (Message)f1299.get(s.toLowerCase());
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

   static String m1960(String s, Hashtable hashtable, DerivationLineChecker derivationlinechecker) {
      return m1962(s, hashtable, derivationlinechecker, derivationlinechecker == null ? null : derivationlinechecker.f935);
   }

   static String m1961(String s, Hashtable hashtable, DerivationLine derivationline) {
      return m1962(s, hashtable, null, derivationline);
   }

   static String m1962(String s, Hashtable hashtable, DerivationLineChecker derivationlinechecker, DerivationLine derivationline) {
      if (derivationline != null && !derivationline.f317.f915.doSubs) {
         return s;
      } else {
         C_k_A[] ac_k_a = new C_k_A[]{derivationlinechecker, derivationline};
         return m662(s, hashtable, ac_k_a);
      }
   }
}
