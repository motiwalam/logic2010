package edu.ucla.phil.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;

public class PreferencesFile extends Hashtable {
   Hashtable f1425 = new Hashtable();

   PreferencesFile() {
   }

   String get(String s) {
      return (String)this.get(s.trim().toUpperCase());
   }

   void put(String s, String s1) {
      String s2 = s.trim().toUpperCase();
      this.f1425.put(s2, s);
      this.put(s2, s1);
   }

   void m2152(String s) {
      String s1 = s.trim().toUpperCase();
      this.f1425.remove(s1);
      this.remove(s1);
   }

   void load(File file1) {
      if (file1.exists()) {
         try {
            this.m2154(new FileReader(file1));
         } catch (IOException ioexception) {
            System.out.println(ioexception.getMessage());
         }
      }
   }

   void m2154(Reader reader) {
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
                  this.put(s.substring(0, i), s.substring(i + 1));
               }
            }
         }

         try {
            bufferedreader.close();
            reader.close();
         } catch (IOException ioexception) {
            System.out.println(ioexception.getMessage());
         }
      }
   }

   void m2155(File file1) {
      if (!this.isEmpty()) {
         try {
            this.m2156(new FileWriter(file1));
         } catch (IOException ioexception) {
            System.out.println(ioexception.getMessage());
         }
      }
   }

   void m2156(Writer writer) {
      if (writer != null) {
         if (this.f1425 != null && !this.isEmpty()) {
            BufferedWriter bufferedwriter = writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer);
            Enumeration enumeration = this.keys();

            while (enumeration.hasMoreElements()) {
               String s = (String)enumeration.nextElement();
               String s1 = (String)this.f1425.get(s);

               try {
                  bufferedwriter.write(s1 + ":" + (String)this.get(s));
                  bufferedwriter.newLine();
               } catch (IOException ioexception2) {
                  break;
               }
            }

            try {
               bufferedwriter.close();
               writer.close();
            } catch (IOException ioexception) {
            }
         } else {
            try {
               writer.close();
            } catch (IOException ioexception1) {
            }
         }
      }
   }
}
