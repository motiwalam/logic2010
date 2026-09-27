package edu.ucla.phil.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;

class OverrideSettings extends Hashtable {
   String lookup(String s, String s1) {
      String s2 = (String)this.get(s);
      return s2 == null ? s1 : s2;
   }

   void load(Reader reader) {
      if (reader != null) {
         BufferedReader bufferedreader = reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);

         while (true) {
            String s;
            try {
               s = bufferedreader.readLine();
            } catch (IOException ioexception1) {
               break;
            }

            if (s == null) {
               break;
            }

            if (!s.startsWith("#")) {
               int i = s.indexOf(":");
               if (i != -1) {
                  String s1 = s.substring(0, i);
                  this.put(s1, s.substring(i + 1));
               }
            }
         }

         try {
            bufferedreader.close();
            reader.close();
         } catch (IOException ioexception) {
         }
      }
   }
}
