package edu.ucla.phil.logic.pkgB;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class Syntax1CharStream {
   public static final boolean staticFlag = true;
   static int bufsize;
   static int available;
   static int tokenBegin;
   public static int bufpos = -1;
   private static int[] bufline;
   private static int[] bufcolumn;
   private static int column = 0;
   private static int line = 1;
   private static boolean prevCharIsCR = false;
   private static boolean prevCharIsLF = false;
   private static Reader inputStream;
   private static char[] buffer;
   private static int maxNextCharInd = 0;
   private static int inBuf = 0;

   private static final void ExpandBuff(boolean flag) {
      char[] achar = new char[bufsize + 2048];
      int[] aint = new int[bufsize + 2048];
      int[] aint1 = new int[bufsize + 2048];

      try {
         if (flag) {
            System.arraycopy(buffer, tokenBegin, achar, 0, bufsize - tokenBegin);
            System.arraycopy(buffer, 0, achar, bufsize - tokenBegin, bufpos);
            buffer = achar;
            System.arraycopy(bufline, tokenBegin, aint, 0, bufsize - tokenBegin);
            System.arraycopy(bufline, 0, aint, bufsize - tokenBegin, bufpos);
            bufline = aint;
            System.arraycopy(bufcolumn, tokenBegin, aint1, 0, bufsize - tokenBegin);
            System.arraycopy(bufcolumn, 0, aint1, bufsize - tokenBegin, bufpos);
            bufcolumn = aint1;
            maxNextCharInd = bufpos = bufpos + (bufsize - tokenBegin);
         } else {
            System.arraycopy(buffer, tokenBegin, achar, 0, bufsize - tokenBegin);
            buffer = achar;
            System.arraycopy(bufline, tokenBegin, aint, 0, bufsize - tokenBegin);
            bufline = aint;
            System.arraycopy(bufcolumn, tokenBegin, aint1, 0, bufsize - tokenBegin);
            bufcolumn = aint1;
            maxNextCharInd = bufpos = bufpos - tokenBegin;
         }
      } catch (Throwable throwable) {
         throw new Error(throwable.getMessage());
      }

      bufsize += 2048;
      available = bufsize;
      tokenBegin = 0;
   }

   private static final void FillBuff() throws IOException {
      if (maxNextCharInd == available) {
         if (available == bufsize) {
            if (tokenBegin > 2048) {
               maxNextCharInd = 0;
               bufpos = 0;
               available = tokenBegin;
            } else if (tokenBegin < 0) {
               maxNextCharInd = 0;
               bufpos = 0;
            } else {
               ExpandBuff(false);
            }
         } else if (available > tokenBegin) {
            available = bufsize;
         } else if (tokenBegin - available < 2048) {
            ExpandBuff(true);
         } else {
            available = tokenBegin;
         }
      }

      try {
         int i;
         if ((i = inputStream.read(buffer, maxNextCharInd, available - maxNextCharInd)) == -1) {
            inputStream.close();
            throw new IOException();
         } else {
            maxNextCharInd += i;
         }
      } catch (IOException ioexception) {
         bufpos--;
         backup(0);
         if (tokenBegin == -1) {
            tokenBegin = bufpos;
         }

         throw ioexception;
      }
   }

   public static final char BeginToken() throws IOException {
      tokenBegin = -1;
      char c0 = readChar();
      tokenBegin = bufpos;
      return c0;
   }

   private static final void UpdateLineColumn(char c0) {
      column++;
      if (prevCharIsLF) {
         prevCharIsLF = false;
         int j = line;
         column = 1;
         line = j + 1;
      } else if (prevCharIsCR) {
         prevCharIsCR = false;
         if (c0 == '\n') {
            prevCharIsLF = true;
         } else {
            int i = line;
            column = 1;
            line = i + 1;
         }
      }

      switch (c0) {
         case '\t':
            column--;
            column = column + (8 - (column & 7));
            break;
         case '\n':
            prevCharIsLF = true;
         case '\u000b':
         case '\f':
         default:
            break;
         case '\r':
            prevCharIsCR = true;
      }

      bufline[bufpos] = line;
      bufcolumn[bufpos] = column;
   }

   public static final char readChar() throws IOException {
      if (inBuf > 0) {
         inBuf--;
         return (char)(255 & buffer[bufpos == bufsize - 1 ? (bufpos = 0) : ++bufpos]);
      } else {
         if (++bufpos >= maxNextCharInd) {
            FillBuff();
         }

         char c0 = (char)(255 & buffer[bufpos]);
         UpdateLineColumn(c0);
         return c0;
      }
   }

   public static final int getColumn() {
      return bufcolumn[bufpos];
   }

   public static final int getLine() {
      return bufline[bufpos];
   }

   public static final int getEndColumn() {
      return bufcolumn[bufpos];
   }

   public static final int getEndLine() {
      return bufline[bufpos];
   }

   public static final int getBeginColumn() {
      return bufcolumn[tokenBegin];
   }

   public static final int getBeginLine() {
      return bufline[tokenBegin];
   }

   public static final void backup(int i) {
      inBuf += i;
      if ((bufpos -= i) < 0) {
         bufpos = bufpos + bufsize;
      }
   }

   public Syntax1CharStream(Reader reader, int i, int j, int k) {
      if (inputStream != null) {
         throw new Error(
            "\n   ERROR: Second call to the constructor of a static ASCII_CharStream.  You must\n       either use ReInit() or set the JavaCC option STATIC to false\n       during the generation of this class."
         );
      } else {
         inputStream = reader;
         line = i;
         column = j - 1;
         bufsize = k;
         available = k;
         buffer = new char[k];
         bufline = new int[k];
         bufcolumn = new int[k];
      }
   }

   public Syntax1CharStream(Reader reader, int i, int j) {
      this(reader, i, j, 4096);
   }

   public static void ReInit(Reader reader, int i, int j, int k) {
      inputStream = reader;
      line = i;
      column = j - 1;
      if (buffer == null || k != buffer.length) {
         bufsize = k;
         available = k;
         buffer = new char[k];
         bufline = new int[k];
         bufcolumn = new int[k];
      }

      prevCharIsCR = false;
      prevCharIsLF = false;
      maxNextCharInd = 0;
      inBuf = 0;
      tokenBegin = 0;
      bufpos = -1;
   }

   public static void ReInit(Reader reader, int i, int j) {
      ReInit(reader, i, j, 4096);
   }

   public Syntax1CharStream(InputStream inputstream, int i, int j, int k) {
      this(new InputStreamReader(inputstream), i, j, 4096);
   }

   public Syntax1CharStream(InputStream inputstream, int i, int j) {
      this(inputstream, i, j, 4096);
   }

   public static void ReInit(InputStream inputstream, int i, int j, int k) {
      ReInit(new InputStreamReader(inputstream), i, j, 4096);
   }

   public static void ReInit(InputStream inputstream, int i, int j) {
      ReInit(inputstream, i, j, 4096);
   }

   public static final String GetImage() {
      return bufpos >= tokenBegin
         ? new String(buffer, tokenBegin, bufpos - tokenBegin + 1)
         : new String(buffer, tokenBegin, bufsize - tokenBegin) + new String(buffer, 0, bufpos + 1);
   }

   public static final char[] GetSuffix(int i) {
      char[] achar = new char[i];
      if (bufpos + 1 >= i) {
         System.arraycopy(buffer, bufpos - i + 1, achar, 0, i);
      } else {
         System.arraycopy(buffer, bufsize - (i - bufpos - 1), achar, 0, i - bufpos - 1);
         System.arraycopy(buffer, 0, achar, i - bufpos - 1, bufpos + 1);
      }

      return achar;
   }

   public static void Done() {
      buffer = null;
      bufline = null;
      bufcolumn = null;
   }

   public static void adjustBeginLineColumn(int i, int j) {
      int k = tokenBegin;
      int l;
      if (bufpos >= tokenBegin) {
         l = bufpos - tokenBegin + inBuf + 1;
      } else {
         l = bufsize - tokenBegin + bufpos + 1 + inBuf;
      }

      int i1 = 0;
      int j1 = 0;
      int k1 = 0;
      int l1 = 0;

      int i2;
      for (i2 = 0; i1 < l; i1++) {
         if (bufline[j1 = k % bufsize] != bufline[k1 = ++k % bufsize]) {
            break;
         }

         bufline[j1] = i;
         l1 = i2 + bufcolumn[k1] - bufcolumn[j1];
         bufcolumn[j1] = j + i2;
         i2 = l1;
      }

      if (i1 < l) {
         bufline[j1] = i++;
         bufcolumn[j1] = j + i2;

         while (i1++ < l) {
            if (bufline[j1 = k % bufsize] != bufline[++k % bufsize]) {
               bufline[j1] = i++;
            } else {
               bufline[j1] = i;
            }
         }
      }

      line = bufline[j1];
      column = bufcolumn[j1];
   }
}
