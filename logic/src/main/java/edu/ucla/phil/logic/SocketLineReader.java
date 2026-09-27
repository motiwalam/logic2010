package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.InputStream;

class SocketLineReader {
   InputStream in;
   boolean sawCr;
   byte[] buffer;

   SocketLineReader(InputStream inputstream) {
      this.in = inputstream;
      this.sawCr = false;
      this.buffer = new byte[1024];
   }

   final String readLine() throws IOException {
      int i = 0;

      while (true) {
         int j = this.in.read();
         if (j == -1) {
            if (i == 0) {
               return null;
            }
            break;
         }

         if (this.sawCr && j == 10) {
            this.sawCr = false;
         } else {
            if ((this.sawCr = j == 13) || j == 10) {
               break;
            }

            if (i >= this.buffer.length) {
               byte[] abyte = new byte[i + 1024];
               System.arraycopy(this.buffer, 0, abyte, 0, i);
               this.buffer = abyte;
            }

            this.buffer[i] = (byte)j;
            i++;
         }
      }

      return new String(this.buffer, 0, i);
   }

   void close() throws IOException {
      this.in.close();
   }
}
