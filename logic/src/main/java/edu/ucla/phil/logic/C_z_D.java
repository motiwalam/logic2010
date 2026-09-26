package edu.ucla.phil.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

class C_z_D {
   static final String f1490 = "\t !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
   static final int f1491 = "\t !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~".length();
   static final int[] f1492 = m2221("\t !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");
   static final String f1493 = "the Logic Program is protected by international copyright law";

   static String m2217(String s) {
      return m2218(s, "the Logic Program is protected by international copyright law");
   }

   static String m2218(String s, String s1) {
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

   static String m2219(String s) {
      return m2220(s, "the Logic Program is protected by international copyright law");
   }

   static String m2220(String s, String s1) {
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

   static String m2223(File file1) {
      if (file1 == null) {
         return null;
      } else {
         C_KD c_kd = new C_KD();

         try {
            c_kd.m925(file1);
         } catch (IOException ioexception) {
            return null;
         }

         return new C_o_B(c_kd.m923()).toString();
      }
   }

   static String m2224(byte[] abyte) {
      if (abyte == null) {
         return null;
      } else {
         C_KD c_kd = new C_KD();
         c_kd.write(abyte);
         return new C_o_B(c_kd.m923()).toString();
      }
   }

   static String m2225(String s) {
      return s == null ? null : m2224(s.getBytes());
   }

   static String m2226(File file1) {
      return m2227(file1, true);
   }

   static String m2227(File file1, boolean flag) {
      if (file1 == null) {
         return null;
      } else {
         C_KD c_kd = new C_KD();

         try {
            c_kd.m925(file1);
         } catch (IOException ioexception) {
            return null;
         }

         return new C_m_C(c_kd.m923()).m1947(flag);
      }
   }

   static String m2228(byte[] abyte) {
      return m2229(abyte, true);
   }

   static String m2229(byte[] abyte, boolean flag) {
      if (abyte == null) {
         return null;
      } else {
         C_KD c_kd = new C_KD();
         c_kd.write(abyte);
         return new C_m_C(c_kd.m923()).m1947(flag);
      }
   }

   static String m2230(String s) {
      return m2231(s, true);
   }

   static String m2231(String s, boolean flag) {
      return s == null ? null : m2229(s.getBytes(), flag);
   }
}
