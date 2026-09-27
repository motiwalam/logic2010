package edu.ucla.phil.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public class LinkFileEditor {
   public static void setLineInFiles(String[] astring) {
      String s = null;
      String s1 = null;
      int i = astring.length;
      if (i > 0) {
         s = astring[0];
         int j = s.indexOf(58);
         s1 = (j == -1 ? s : s.substring(0, j + 1)).toLowerCase();
      }

      for (int k = 1; k < i; k++) {
         BufferedReader bufferedreader;
         try {
            bufferedreader = new BufferedReader(new FileReader(astring[k]));
         } catch (FileNotFoundException filenotfoundexception) {
            System.out.println("could not find \"" + astring[k] + "\".");
            continue;
         }

         Vector vector = new Vector();

         String s2;
         try {
            while ((s2 = bufferedreader.readLine()) != null) {
               vector.addElement(s2);
            }
         } catch (IOException ioexception6) {
            System.out.println("could not read \"" + astring[k] + "\".");

            try {
               bufferedreader.close();
            } catch (IOException ioexception4) {
            }
            continue;
         }

         try {
            bufferedreader.close();
         } catch (IOException ioexception3) {
         }

         BufferedWriter bufferedwriter;
         try {
            bufferedwriter = new BufferedWriter(new FileWriter(astring[k]));
         } catch (IOException ioexception5) {
            System.out.println("could not write to \"" + astring[k] + "\".");
            continue;
         }

         Enumeration enumeration = vector.elements();

         while (enumeration.hasMoreElements()) {
            try {
               String s3 = (String)enumeration.nextElement();
               if (s3.toLowerCase().startsWith(s1)) {
                  s3 = s;
                  System.out.println("\"" + astring[k] + "\" set " + s + ".");
               }

               bufferedwriter.write(s3);
               bufferedwriter.newLine();
            } catch (IOException ioexception2) {
               System.out.println("could not write to \"" + astring[k] + "\".");

               try {
                  bufferedwriter.close();
               } catch (IOException ioexception1) {
               }
            }
         }

         try {
            bufferedwriter.close();
         } catch (IOException ioexception) {
         }
      }

      System.exit(0);
   }
}
