package edu.ucla.phil.logic.pkgA;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class Syntax2CharStream {
   public static final boolean f35 = true;
   static int f36;
   static int f37;
   static int f38;
   public static int f39 = -1;
   private static int[] f40;
   private static int[] f41;
   private static int f42 = 0;
   private static int f43 = 1;
   private static boolean f44 = false;
   private static boolean f45 = false;
   private static Reader f46;
   private static char[] f47;
   private static int f48 = 0;
   private static int f49 = 0;

   private static final void m73(boolean flag) {
      char[] achar = new char[f36 + 2048];
      int[] aint = new int[f36 + 2048];
      int[] aint1 = new int[f36 + 2048];

      try {
         if (flag) {
            System.arraycopy(f47, f38, achar, 0, f36 - f38);
            System.arraycopy(f47, 0, achar, f36 - f38, f39);
            f47 = achar;
            System.arraycopy(f40, f38, aint, 0, f36 - f38);
            System.arraycopy(f40, 0, aint, f36 - f38, f39);
            f40 = aint;
            System.arraycopy(f41, f38, aint1, 0, f36 - f38);
            System.arraycopy(f41, 0, aint1, f36 - f38, f39);
            f41 = aint1;
            f48 = f39 = f39 + (f36 - f38);
         } else {
            System.arraycopy(f47, f38, achar, 0, f36 - f38);
            f47 = achar;
            System.arraycopy(f40, f38, aint, 0, f36 - f38);
            f40 = aint;
            System.arraycopy(f41, f38, aint1, 0, f36 - f38);
            f41 = aint1;
            f48 = f39 = f39 - f38;
         }
      } catch (Throwable throwable) {
         throw new Error(throwable.getMessage());
      }

      f36 += 2048;
      f37 = f36;
      f38 = 0;
   }

   private static final void m74() throws IOException {
      if (f48 == f37) {
         if (f37 == f36) {
            if (f38 > 2048) {
               f48 = 0;
               f39 = 0;
               f37 = f38;
            } else if (f38 < 0) {
               f48 = 0;
               f39 = 0;
            } else {
               m73(false);
            }
         } else if (f37 > f38) {
            f37 = f36;
         } else if (f38 - f37 < 2048) {
            m73(true);
         } else {
            f37 = f38;
         }
      }

      try {
         int i;
         if ((i = f46.read(f47, f48, f37 - f48)) == -1) {
            f46.close();
            throw new IOException();
         } else {
            f48 += i;
         }
      } catch (IOException ioexception) {
         f39--;
         m84(0);
         if (f38 == -1) {
            f38 = f39;
         }

         throw ioexception;
      }
   }

   public static final char m75() throws IOException {
      f38 = -1;
      char c0 = m77();
      f38 = f39;
      return c0;
   }

   private static final void m76(char c0) {
      f42++;
      if (f45) {
         f45 = false;
         int j = f43;
         f42 = 1;
         f43 = j + 1;
      } else if (f44) {
         f44 = false;
         if (c0 == '\n') {
            f45 = true;
         } else {
            int i = f43;
            f42 = 1;
            f43 = i + 1;
         }
      }

      switch (c0) {
         case '\t':
            f42--;
            f42 = f42 + (8 - (f42 & 7));
            break;
         case '\n':
            f45 = true;
         case '\u000b':
         case '\f':
         default:
            break;
         case '\r':
            f44 = true;
      }

      f40[f39] = f43;
      f41[f39] = f42;
   }

   public static final char m77() throws IOException {
      if (f49 > 0) {
         f49--;
         return (char)(255 & f47[f39 == f36 - 1 ? (f39 = 0) : ++f39]);
      } else {
         if (++f39 >= f48) {
            m74();
         }

         char c0 = (char)(255 & f47[f39]);
         m76(c0);
         return c0;
      }
   }

   public static final int m78() {
      return f41[f39];
   }

   public static final int m79() {
      return f40[f39];
   }

   public static final int m80() {
      return f41[f39];
   }

   public static final int m81() {
      return f40[f39];
   }

   public static final int m82() {
      return f41[f38];
   }

   public static final int m83() {
      return f40[f38];
   }

   public static final void m84(int i) {
      f49 += i;
      if ((f39 -= i) < 0) {
         f39 = f39 + f36;
      }
   }

   public Syntax2CharStream(Reader reader, int i, int j, int k) {
      if (f46 != null) {
         throw new Error(
            "\n   ERROR: Second call to the constructor of a static ASCII_CharStream.  You must\n       either use ReInit() or set the JavaCC option STATIC to false\n       during the generation of this class."
         );
      } else {
         f46 = reader;
         f43 = i;
         f42 = j - 1;
         f36 = k;
         f37 = k;
         f47 = new char[k];
         f40 = new int[k];
         f41 = new int[k];
      }
   }

   public Syntax2CharStream(Reader reader, int i, int j) {
      this(reader, i, j, 4096);
   }

   public static void m85(Reader reader, int i, int j, int k) {
      f46 = reader;
      f43 = i;
      f42 = j - 1;
      if (f47 == null || k != f47.length) {
         f36 = k;
         f37 = k;
         f47 = new char[k];
         f40 = new int[k];
         f41 = new int[k];
      }

      f44 = false;
      f45 = false;
      f48 = 0;
      f49 = 0;
      f38 = 0;
      f39 = -1;
   }

   public static void m86(Reader reader, int i, int j) {
      m85(reader, i, j, 4096);
   }

   public Syntax2CharStream(InputStream inputstream, int i, int j, int k) {
      this(new InputStreamReader(inputstream), i, j, 4096);
   }

   public Syntax2CharStream(InputStream inputstream, int i, int j) {
      this(inputstream, i, j, 4096);
   }

   public static void m87(InputStream inputstream, int i, int j, int k) {
      m85(new InputStreamReader(inputstream), i, j, 4096);
   }

   public static void m88(InputStream inputstream, int i, int j) {
      m87(inputstream, i, j, 4096);
   }

   public static final String m89() {
      return f39 >= f38 ? new String(f47, f38, f39 - f38 + 1) : new String(f47, f38, f36 - f38) + new String(f47, 0, f39 + 1);
   }

   public static final char[] m90(int i) {
      char[] achar = new char[i];
      if (f39 + 1 >= i) {
         System.arraycopy(f47, f39 - i + 1, achar, 0, i);
      } else {
         System.arraycopy(f47, f36 - (i - f39 - 1), achar, 0, i - f39 - 1);
         System.arraycopy(f47, 0, achar, i - f39 - 1, f39 + 1);
      }

      return achar;
   }

   public static void m91() {
      f47 = null;
      f40 = null;
      f41 = null;
   }

   public static void m92(int i, int j) {
      int k = f38;
      int l;
      if (f39 >= f38) {
         l = f39 - f38 + f49 + 1;
      } else {
         l = f36 - f38 + f39 + 1 + f49;
      }

      int i1 = 0;
      int j1 = 0;
      int k1 = 0;
      int l1 = 0;

      int i2;
      for (i2 = 0; i1 < l; i1++) {
         if (f40[j1 = k % f36] != f40[k1 = ++k % f36]) {
            break;
         }

         f40[j1] = i;
         l1 = i2 + f41[k1] - f41[j1];
         f41[j1] = j + i2;
         i2 = l1;
      }

      if (i1 < l) {
         f40[j1] = i++;
         f41[j1] = j + i2;

         while (i1++ < l) {
            if (f40[j1 = k % f36] != f40[++k % f36]) {
               f40[j1] = i++;
            } else {
               f40[j1] = i;
            }
         }
      }

      f43 = f40[j1];
      f42 = f41[j1];
   }
}
