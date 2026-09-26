package edu.ucla.phil.logic;

import java.util.Hashtable;

class Message {
   String id;
   String title;
   String text;
   String buttons;
   boolean isError;
   static Hashtable f375 = null;
   static String f376 = "messages";

   Message(String s) {
      this.id = s;
      this.title = s;
      this.text = "no further explanation available";
      this.buttons = null;
      this.isError = false;
   }

   static boolean loadMessages() {
      ScrambledReader scrambledreader = LogicProgram.openDataFile(f376, false);
      if (scrambledreader == null) {
         return false;
      } else {
         f375 = parseMessages(new TaggedRecord(scrambledreader, true));
         return f375 != null;
      }
   }

   static boolean m657(int i) {
      Class oclass = (Class)LogicModule.getStaticField(i, "messageClass");
      String s = (String)LogicModule.getStaticField(oclass, "linkName");
      ScrambledReader scrambledreader = LogicProgram.openDataFile(s, false);
      if (scrambledreader == null) {
         return false;
      } else {
         Hashtable hashtable = parseMessages(new TaggedRecord(scrambledreader, true));
         LogicModule.setStaticField(oclass, "messages", hashtable);
         return hashtable != null;
      }
   }

   static Hashtable parseMessages(TaggedRecord taggedrecord) {
      if (taggedrecord == null) {
         return null;
      } else {
         Hashtable hashtable = new Hashtable();

         while (taggedrecord.readNext()) {
            int i;
            if ((i = taggedrecord.indexOfTag('n')) != -1) {
               Message message = new Message(taggedrecord.valueAt(i).trim());
               if ((i = taggedrecord.m1478("ie")) != -1) {
                  message.title = taggedrecord.valueAt(i).trim();
                  message.isError = taggedrecord.tagAt(i) == 'e';
               }

               if ((i = taggedrecord.indexOfTag('x')) != -1) {
                  message.text = taggedrecord.valueAt(i).trim();
               }

               if ((i = taggedrecord.indexOfTag('b')) != -1) {
                  message.buttons = taggedrecord.valueAt(i);
               }

               hashtable.put(message.id.toLowerCase(), message);
            }
         }

         return hashtable;
      }
   }

   static Message get(String s) {
      Message message = f375 == null ? null : (Message)f375.get(s.toLowerCase());
      if (message == null) {
         message = new Message(s);
         message.title = "bad error id";
         message.text = "The program has encountered an unknown error id.  Please report this: " + message.id;
         message.buttons = "OK";
         message.isError = true;
      }

      return message;
   }

   static String getText(String s) {
      return m659(get(s));
   }

   static String m659(Message message) {
      return message == null ? null : message.text;
   }

   static String m660(String s, int i) {
      String s1 = "";
      String s2 = " ";

      while (s2.length() < i) {
         s2 = s2 + s2;
      }

      s2 = s2.substring(0, i);
      String s3 = "\\n";
      int j = -1;

      do {
         if ((j = s.indexOf(s3)) == -1) {
            s1 = s1 + s2 + s;
         } else {
            s1 = s1 + s2 + s.substring(0, j + s3.length());
            s = s.substring(j + s3.length());
         }
      } while (j != -1);

      return s1;
   }

   static String substitute(String s, Hashtable hashtable) {
      return m662(s, hashtable, null);
   }

   static String m662(String s, Hashtable hashtable, C_k_A[] ac_k_a) {
      if (s == null) {
         return null;
      } else {
         String s1 = "";
         int i = 0;
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\<");
         DelimitedTokenizer delimitedtokenizer1 = new DelimitedTokenizer("\\>");
         delimitedtokenizer.m1132(s);
         m671(hashtable);

         while (true) {
            s1 = s1 + delimitedtokenizer.m1136(true);
            if (delimitedtokenizer.m1134() != '<') {
               return s1;
            }

            delimitedtokenizer1.m1132(delimitedtokenizer.m1133());
            String s3 = delimitedtokenizer1.m1136(true);
            if (delimitedtokenizer1.m1134() != '>') {
               return s1 + "<" + s3;
            }

            delimitedtokenizer.m1132(delimitedtokenizer1.m1133());
            s3 = s3.toLowerCase();
            if (s3.length() >= 6 && s3.substring(0, 6).equals("indent")) {
               try {
                  i = Integer.parseInt(s3.substring(6).trim());
               } catch (NumberFormatException numberformatexception) {
                  i = 0;
               }
            } else {
               String s2;
               if (hashtable != null && (s2 = (String)hashtable.get(s3)) != null) {
                  s1 = s1 + m660(s2, i);
               } else if ((s2 = m663(ac_k_a, s3)) != null) {
                  s1 = s1 + m660(s2, i);
               } else {
                  s1 = s1 + m660("<" + s3 + ">", i);
               }

               i = 0;
            }
         }
      }
   }

   static String m663(C_k_A[] ac_k_a, String s) {
      if (ac_k_a != null) {
         int i = ac_k_a.length;

         for (int j = 0; j < i; j++) {
            String s1 = ac_k_a[j] == null ? null : ac_k_a[j].m547(s);
            if (s1 != null) {
               return s1;
            }
         }
      }

      return null;
   }

   static Hashtable putParam(Hashtable hashtable, String s, String s1) {
      if (hashtable == null) {
         hashtable = new Hashtable();
      }

      if (s1 == null) {
         s1 = "";
      }

      hashtable.put(s.toLowerCase(), s1);
      return hashtable;
   }

   static Hashtable m665(Hashtable hashtable, Hashtable hashtable1) {
      return LogicProgram.m1050(hashtable, hashtable1, true);
   }

   static Hashtable params(String s, String s1) {
      return putParam(null, s.toLowerCase(), s1);
   }

   static Hashtable params(String s, String s1, String s2, String s3) {
      return putParam(params(s, s1), s2, s3);
   }

   static Hashtable params(String s, String s1, String s2, String s3, String s4, String s5) {
      return putParam(params(s, s1, s2, s3), s4, s5);
   }

   static Hashtable params(String s, String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
      return putParam(params(s, s1, s2, s3, s4, s5), s6, s7);
   }

   static Hashtable params(String s, String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9) {
      return putParam(params(s, s1, s2, s3, s4, s5, s6, s7), s8, s9);
   }

   static void m671(Hashtable hashtable) {
      if (hashtable != null) {
         String s = (String)hashtable.get("n");
         if (s != null) {
            s = s.trim().toLowerCase();
            if (!s.equals("1") && !s.equals("one") && !s.equals("a") && !s.equals("an") && !s.equals("the")) {
               hashtable.put("s", "s");
            } else {
               hashtable.put("s", "");
            }
         }
      }
   }
}
