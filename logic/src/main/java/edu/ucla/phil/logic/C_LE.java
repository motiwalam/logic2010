package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.InputStream;

public class C_LE extends InputStream {
   InputStream f521;
   Base64Codec f522;
   byte[] f523;
   int f524;
   boolean f525;

   public C_LE(InputStream inputstream) {
      this.f521 = inputstream;
      this.f522 = new Base64Codec();
      this.f523 = null;
      this.f524 = 0;
      this.f525 = false;
   }

   @Override
   public int available() throws IOException {
      this.m947();
      return this.m945();
   }

   private int m945() {
      return (this.f523 == null ? 0 : this.f523.length - this.f524) + this.f522.f1319;
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
      int k = 0;
      this.m947();
      int l = this.m946(abyte, i, j);
      k += l;
      i += l;
      j -= l;

      while (j > 0 && !this.f525) {
         l = this.f521.read();
         if (l == -1) {
            this.f522.m1996();
            this.f525 = true;
         } else {
            this.f522.m2002((char)l);
            this.m947();
            l = this.m946(abyte, i, j);
            k += l;
            i += l;
            j -= l;
         }
      }

      return k == 0 && this.f525 ? -1 : k;
   }

   private int m946(byte[] abyte, int i, int j) {
      if (j <= 0) {
         return 0;
      } else {
         int k = 0;
         int l = this.f523 == null ? 0 : this.f523.length - this.f524;
         if (l > 0) {
            if (l > j) {
               l = j;
            }

            System.arraycopy(this.f523, this.f524, abyte, i, l);
            this.f524 += l;
            k += l;
            j -= l;
         }

         if (j == 0) {
            return k;
         } else {
            this.f523 = this.f522.m2001(true);
            this.f524 = 0;
            l = this.f523.length;
            if (l > 0) {
               if (l > j) {
                  l = j;
               }

               System.arraycopy(this.f523, this.f524, abyte, i, l);
               this.f524 += l;
               k += l;
               j -= l;
            }

            return k;
         }
      }
   }

   private void m947() throws IOException {
      int i = this.f525 ? 0 : this.f521.available();
      if (i > 0) {
         byte[] abyte = new byte[i];
         this.f521.read(abyte);
         this.f522.m2003(new String(abyte));
      }
   }
}
