package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.InputStream;

public class Base64InputStream extends InputStream {
   InputStream in;
   Base64Codec codec;
   byte[] decoded;
   int decodedPos;
   boolean eof;

   public Base64InputStream(InputStream inputstream) {
      this.in = inputstream;
      this.codec = new Base64Codec();
      this.decoded = null;
      this.decodedPos = 0;
      this.eof = false;
   }

   @Override
   public int available() throws IOException {
      this.readAvailableInput();
      return this.bufferedCount();
   }

   private int bufferedCount() {
      return (this.decoded == null ? 0 : this.decoded.length - this.decodedPos) + this.codec.length;
   }

   @Override
   public int read() throws IOException {
      byte[] abyte = new byte[1];
      this.read(abyte);
      return abyte[0] & 0xFF;
   }

   @Override
   public int read(byte[] abyte) throws IOException {
      return this.read(abyte, 0, abyte.length);
   }

   @Override
   public int read(byte[] abyte, int i, int j) throws IOException {
      byte b0 = 0;
      this.readAvailableInput();
      int k = this.copyDecoded(abyte, i, j);
      int j1 = b0 + k;
      int l = i + k;
      int i1 = j - k;

      while (i1 > 0 && !this.eof) {
         k = this.in.read();
         if (k == -1) {
            this.codec.reset();
            this.eof = true;
         } else {
            this.codec.addBase64Char((char)k);
            this.readAvailableInput();
            k = this.copyDecoded(abyte, l, i1);
            j1 += k;
            l += k;
            i1 -= k;
         }
      }

      return j1 == 0 && this.eof ? -1 : j1;
   }

   private int copyDecoded(byte[] abyte, int i, int j) {
      if (j <= 0) {
         return 0;
      } else {
         int k = 0;
         int l = this.decoded == null ? 0 : this.decoded.length - this.decodedPos;
         if (l > 0) {
            if (l > j) {
               l = j;
            }

            System.arraycopy(this.decoded, this.decodedPos, abyte, i, l);
            this.decodedPos += l;
            k += l;
            j -= l;
         }

         if (j == 0) {
            return k;
         } else {
            this.decoded = this.codec.getBytes(true);
            this.decodedPos = 0;
            int i1 = this.decoded.length;
            if (i1 > 0) {
               if (i1 > j) {
                  i1 = j;
               }

               System.arraycopy(this.decoded, this.decodedPos, abyte, i, i1);
               this.decodedPos += i1;
               k += i1;
               j -= i1;
            }

            return k;
         }
      }
   }

   private void readAvailableInput() throws IOException {
      int i = this.eof ? 0 : this.in.available();
      if (i > 0) {
         byte[] abyte = new byte[i];
         this.in.read(abyte);
         this.codec.addBase64String(new String(abyte));
      }
   }
}
