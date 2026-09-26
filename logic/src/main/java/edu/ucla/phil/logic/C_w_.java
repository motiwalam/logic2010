package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.InputStream;

class C_w_ {
   InputStream f1421;
   boolean f1422;
   byte[] f1423;

   C_w_(InputStream inputstream) {
      this.f1421 = inputstream;
      this.f1422 = false;
      this.f1423 = new byte[1024];
   }

   final String m2131() throws IOException {
      int i = 0;

      while (true) {
         int j = this.f1421.read();
         if (j == -1) {
            if (i == 0) {
               return null;
            }
            break;
         }

         if (this.f1422 && j == 10) {
            this.f1422 = false;
         } else {
            if ((this.f1422 = j == 13) || j == 10) {
               break;
            }

            if (i >= this.f1423.length) {
               byte[] abyte = new byte[i + 1024];
               System.arraycopy(this.f1423, 0, abyte, 0, i);
               this.f1423 = abyte;
            }

            this.f1423[i] = (byte)j;
            i++;
         }
      }

      return new String(this.f1423, 0, i);
   }

   void m2132() throws IOException {
      this.f1421.close();
   }
}
