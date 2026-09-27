package edu.ucla.phil.logic;

import java.util.Hashtable;

class AuthHeaderParams extends Hashtable {
   String header = null;
   String scheme = null;

   public AuthHeaderParams(String s, boolean flag) {
      if (s != null) {
         this.header = s.trim();
         this.parse(flag);
      }
   }

   String getScheme() {
      return this.scheme;
   }

   String getParam(String s) {
      return (String)this.get(s.toLowerCase());
   }

   void parse(boolean flag) {
      String s;
      if (flag) {
         this.scheme = null;
         s = this.header;
      } else {
         int i = this.header.indexOf(" ");
         if (i == -1) {
            this.scheme = this.header;
            return;
         }

         this.scheme = this.header.substring(0, i);
         s = this.header.substring(i + 1).trim();
      }

      int j;
      while (s != null && (j = s.indexOf("=")) != -1) {
         String s2 = s.substring(0, j).trim();
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\\",");
         delimitedtokenizer.setInput(s.substring(j + 1).trim());
         String s1 = delimitedtokenizer.nextToken().trim();
         if (delimitedtokenizer.getDelimiter() == '"') {
            DelimitedTokenizer delimitedtokenizer1 = new DelimitedTokenizer("\\\"");
            delimitedtokenizer1.setInput(delimitedtokenizer.getRemaining());
            s1 = delimitedtokenizer1.nextToken();
            delimitedtokenizer.setInput(delimitedtokenizer1.getRemaining());

            do {
               delimitedtokenizer.nextToken();
            } while (delimitedtokenizer.getDelimiter() == '"');
         }

         s = delimitedtokenizer.getRemaining();
         this.put(s2.toLowerCase(), s1);
      }
   }
}
