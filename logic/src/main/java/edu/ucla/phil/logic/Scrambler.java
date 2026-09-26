package edu.ucla.phil.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

class Scrambler {
   static final String ALPHABET = "\t !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
   static final int f1491 = "\t !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~".length();
   static final int[] f1492 = m2221("\t !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");
   static final String DEFAULT_KEY = "the Logic Program is protected by international copyright law";

   static String scramble(String s) {
      return scramble(s, "the Logic Program is protected by international copyright law");
   }

   static String scramble(String s, String s1) {
      if (s != null && s1 != null) {
         String s2 = "";
         int i = s.length();
         int j = s1.length();
         int k = 0;

         for (int l = 0; l < i; l++) {
            char c0 = s.charAt(l);
            if (j != 0) {
               char c1 = s1.charAt(l % j);
               if (c1 < f1492.length && f1492[c1] != -1) {
                  k += f1492[c1];
               }
            }

            if (c0 < f1492.length && f1492[c0] != -1) {
               int i1 = k + f1492[c0];
               s2 = s2 + "\t !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~".charAt(k = i1 % f1491);
            } else {
               s2 = s2 + c0;
            }
         }

         return s2;
      } else {
         return s;
      }
   }

   static String unscramble(String s) {
      return unscramble(s, "the Logic Program is protected by international copyright law");
   }

   static String unscramble(String s, String s1) {
      if (s != null && s1 != null) {
         String s2 = "";
         int i = s.length();
         int j = s1.length();
         int k = 0;

         for (int l = 0; l < i; l++) {
            char c0 = s.charAt(l);
            if (j != 0) {
               char c1 = s1.charAt(l % j);
               if (c1 < f1492.length && f1492[c1] != -1) {
                  k += f1492[c1];
               }
            }

            if (c0 < f1492.length && f1492[c0] != -1) {
               s2 = s2
                  + "\t !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~"
                     .charAt(f1491 - 1 - (k - f1492[c0] + f1491 - 1) % f1491);
               k = f1492[c0];
            } else {
               s2 = s2 + c0;
            }
         }

         return s2;
      } else {
         return s;
      }
   }

   private static int[] m2221(String s) {
      int i = -1;
      int j = s.length();

      for (int k = 0; k < j; k++) {
         char c0 = s.charAt(k);
         if (c0 > i) {
            i = c0;
         }
      }

      int[] aint = new int[i + 1];

      for (int l = 0; l <= i; l++) {
         aint[l] = -1;
      }

      for (int i1 = 0; i1 < j; i1++) {
         char c1 = s.charAt(i1);
         if (aint[c1] == -1) {
            aint[c1] = i1;
         }
      }

      return aint;
   }

   static String[][] m2222() {
      BufferedReader bufferedreader = null;
      Vector vector = new Vector();
      String[][] astring = (String[][])null;

      String s1;
      try {
         String s = System.getProperty("scrambler.map");
         File file1;
         if (s != null) {
            file1 = new File(s);
         } else {
            file1 = new File(System.getProperty("user.dir"), "scrambler.txt");
         }

         if (file1.exists()) {
            bufferedreader = new BufferedReader(new FileReader(file1));

            while ((s1 = bufferedreader.readLine()) != null) {
               int i = s1.indexOf(":");
               if (i != -1 && s1.charAt(0) != '#') {
                  vector.addElement(new String[]{s1.substring(0, i).trim(), s1.substring(i + 1).trim()});
               }
            }

            astring = new String[vector.size()][];
            vector.copyInto(astring);
            return astring;
         }

         s1 = (String[][])null;
      } catch (IOException ioexception1) {
         return astring;
      } finally {
         if (bufferedreader != null) {
            try {
               bufferedreader.close();
            } catch (IOException ioexception) {
            }
         }
      }

      return s1;
   }

   static String md5Base64(File file1) {
      if (file1 == null) {
         return null;
      } else {
         Md5OutputStream md5outputstream = new Md5OutputStream();

         try {
            md5outputstream.m925(file1);
         } catch (IOException ioexception) {
            return null;
         }

         return new Base64Codec(md5outputstream.digest()).toString();
      }
   }

   static String md5Base64(byte[] abyte) {
      if (abyte == null) {
         return null;
      } else {
         Md5OutputStream md5outputstream = new Md5OutputStream();
         md5outputstream.write(abyte);
         return new Base64Codec(md5outputstream.digest()).toString();
      }
   }

   static String md5Base64(String s) {
      return s == null ? null : md5Base64(s.getBytes());
   }

   static String md5Hex(File file1) {
      return md5Hex(file1, true);
   }

   static String md5Hex(File file1, boolean flag) {
      if (file1 == null) {
         return null;
      } else {
         Md5OutputStream md5outputstream = new Md5OutputStream();

         try {
            md5outputstream.m925(file1);
         } catch (IOException ioexception) {
            return null;
         }

         return new HexEncoder(md5outputstream.digest()).m1947(flag);
      }
   }

   static String md5Hex(byte[] abyte) {
      return md5Hex(abyte, true);
   }

   static String md5Hex(byte[] abyte, boolean flag) {
      if (abyte == null) {
         return null;
      } else {
         Md5OutputStream md5outputstream = new Md5OutputStream();
         md5outputstream.write(abyte);
         return new HexEncoder(md5outputstream.digest()).m1947(flag);
      }
   }

   static String md5Hex(String s) {
      return md5Hex(s, true);
   }

   static String md5Hex(String s, boolean flag) {
      return s == null ? null : md5Hex(s.getBytes(), flag);
   }
}
