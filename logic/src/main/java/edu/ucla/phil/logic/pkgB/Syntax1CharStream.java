package edu.ucla.phil.logic.pkgB;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class Syntax1CharStream {
   public static final boolean f154 = true;
   static int f155;
   static int f156;
   static int f157;
   public static int f158 = -1;
   private static int[] f159;
   private static int[] f160;
   private static int f161 = 0;
   private static int f162 = 1;
   private static boolean f163 = false;
   private static boolean f164 = false;
   private static Reader f165;
   private static char[] f166;
   private static int f167 = 0;
   private static int f168 = 0;

   private static final void m237(boolean flag) {
      char[] achar = new char[f155 + 2048];
      int[] aint = new int[f155 + 2048];
      int[] aint1 = new int[f155 + 2048];

      try {
         if (flag) {
            System.arraycopy(f166, f157, achar, 0, f155 - f157);
            System.arraycopy(f166, 0, achar, f155 - f157, f158);
            f166 = achar;
            System.arraycopy(f159, f157, aint, 0, f155 - f157);
            System.arraycopy(f159, 0, aint, f155 - f157, f158);
            f159 = aint;
            System.arraycopy(f160, f157, aint1, 0, f155 - f157);
            System.arraycopy(f160, 0, aint1, f155 - f157, f158);
            f160 = aint1;
            f167 = f158 = f158 + (f155 - f157);
         } else {
            System.arraycopy(f166, f157, achar, 0, f155 - f157);
            f166 = achar;
            System.arraycopy(f159, f157, aint, 0, f155 - f157);
            f159 = aint;
            System.arraycopy(f160, f157, aint1, 0, f155 - f157);
            f160 = aint1;
            f167 = f158 = f158 - f157;
         }
      } catch (Throwable throwable) {
         throw new Error(throwable.getMessage());
      }

      f155 += 2048;
      f156 = f155;
      f157 = 0;
   }

   private static final void m238() throws IOException {
      if (f167 == f156) {
         if (f156 == f155) {
            if (f157 > 2048) {
               f167 = 0;
               f158 = 0;
               f156 = f157;
            } else if (f157 < 0) {
               f167 = 0;
               f158 = 0;
            } else {
               m237(false);
            }
         } else if (f156 > f157) {
            f156 = f155;
         } else if (f157 - f156 < 2048) {
            m237(true);
         } else {
            f156 = f157;
         }
      }

      try {
         int i;
         if ((i = f165.read(f166, f167, f156 - f167)) == -1) {
            f165.close();
            throw new IOException();
         } else {
            f167 += i;
         }
      } catch (IOException ioexception) {
         f158--;
         m248(0);
         if (f157 == -1) {
            f157 = f158;
         }

         throw ioexception;
      }
   }

   public static final char m239() throws IOException {
      f157 = -1;
      char c0 = m241();
      f157 = f158;
      return c0;
   }

   private static final void m240(char c0) {
      f161++;
      if (f164) {
         f164 = false;
         int j = f162;
         f161 = 1;
         f162 = j + 1;
      } else if (f163) {
         f163 = false;
         if (c0 == '\n') {
            f164 = true;
         } else {
            int i = f162;
            f161 = 1;
            f162 = i + 1;
         }
      }

      switch (c0) {
         case '\t':
            f161--;
            f161 = f161 + (8 - (f161 & 7));
            break;
         case '\n':
            f164 = true;
         case '\u000b':
         case '\f':
         default:
            break;
         case '\r':
            f163 = true;
      }

      f159[f158] = f162;
      f160[f158] = f161;
   }

   public static final char m241() throws IOException {
      if (f168 > 0) {
         f168--;
         return (char)(255 & f166[f158 == f155 - 1 ? (f158 = 0) : ++f158]);
      } else {
         if (++f158 >= f167) {
            m238();
         }

         char c0 = (char)(255 & f166[f158]);
         m240(c0);
         return c0;
      }
   }

   public static final int m242() {
      return f160[f158];
   }

   public static final int m243() {
      return f159[f158];
   }

   public static final int m244() {
      return f160[f158];
   }

   public static final int m245() {
      return f159[f158];
   }

   public static final int m246() {
      return f160[f157];
   }

   public static final int m247() {
      return f159[f157];
   }

   public static final void m248(int i) {
      f168 += i;
      if ((f158 -= i) < 0) {
         f158 = f158 + f155;
      }
   }

   public Syntax1CharStream(Reader reader, int i, int j, int k) {
      if (f165 != null) {
         throw new Error(
            "\n   ERROR: Second call to the constructor of a static ASCII_CharStream.  You must\n       either use ReInit() or set the JavaCC option STATIC to false\n       during the generation of this class."
         );
      } else {
         f165 = reader;
         f162 = i;
         f161 = j - 1;
         f155 = k;
         f156 = k;
         f166 = new char[k];
         f159 = new int[k];
         f160 = new int[k];
      }
   }

   public Syntax1CharStream(Reader reader, int i, int j) {
      this(reader, i, j, 4096);
   }

   public static void m249(Reader reader, int i, int j, int k) {
      f165 = reader;
      f162 = i;
      f161 = j - 1;
      if (f166 == null || k != f166.length) {
         f155 = k;
         f156 = k;
         f166 = new char[k];
         f159 = new int[k];
         f160 = new int[k];
      }

      f163 = false;
      f164 = false;
      f167 = 0;
      f168 = 0;
      f157 = 0;
      f158 = -1;
   }

   public static void m250(Reader reader, int i, int j) {
      m249(reader, i, j, 4096);
   }

   public Syntax1CharStream(InputStream inputstream, int i, int j, int k) {
      this(new InputStreamReader(inputstream), i, j, 4096);
   }

   public Syntax1CharStream(InputStream inputstream, int i, int j) {
      this(inputstream, i, j, 4096);
   }

   public static void m251(InputStream inputstream, int i, int j, int k) {
      m249(new InputStreamReader(inputstream), i, j, 4096);
   }

   public static void m252(InputStream inputstream, int i, int j) {
      m251(inputstream, i, j, 4096);
   }

   public static final String m253() {
      return f158 >= f157 ? new String(f166, f157, f158 - f157 + 1) : new String(f166, f157, f155 - f157) + new String(f166, 0, f158 + 1);
   }

   public static final char[] m254(int i) {
      char[] achar = new char[i];
      if (f158 + 1 >= i) {
         System.arraycopy(f166, f158 - i + 1, achar, 0, i);
      } else {
         System.arraycopy(f166, f155 - (i - f158 - 1), achar, 0, i - f158 - 1);
         System.arraycopy(f166, 0, achar, i - f158 - 1, f158 + 1);
      }

      return achar;
   }

   public static void m255() {
      f166 = null;
      f159 = null;
      f160 = null;
   }

   public static void m256(int i, int j) {
      int k = f157;
      int l;
      if (f158 >= f157) {
         l = f158 - f157 + f168 + 1;
      } else {
         l = f155 - f157 + f158 + 1 + f168;
      }

      int i1 = 0;
      int j1 = 0;
      int k1 = 0;
      int l1 = 0;

      int i2;
      for (i2 = 0; i1 < l; i1++) {
         if (f159[j1 = k % f155] != f159[k1 = ++k % f155]) {
            break;
         }

         f159[j1] = i;
         l1 = i2 + f160[k1] - f160[j1];
         f160[j1] = j + i2;
         i2 = l1;
      }

      if (i1 < l) {
         f159[j1] = i++;
         f160[j1] = j + i2;

         while (i1++ < l) {
            if (f159[j1 = k % f155] != f159[++k % f155]) {
               f159[j1] = i++;
            } else {
               f159[j1] = i;
            }
         }
      }

      f162 = f159[j1];
      f161 = f160[j1];
   }
}
