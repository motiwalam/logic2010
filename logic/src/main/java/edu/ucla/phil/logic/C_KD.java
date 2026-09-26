package edu.ucla.phil.logic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class C_KD extends OutputStream {
   MessageDigest f495;
   byte[] f496;
   long f497;

   public C_KD() {
      try {
         this.f495 = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
         this.f495 = null;
      }

      this.f496 = null;
      this.f497 = 0L;
   }

   @Override
   public void write(int i) {
      this.write(new byte[]{(byte)i});
   }

   @Override
   public void write(byte[] abyte) {
      if (this.f495 != null) {
         this.f495.update(abyte);
      }

      this.f497 += abyte.length;
   }

   @Override
   public void write(byte[] abyte, int i, int j) {
      if (this.f495 != null) {
         this.f495.update(abyte, i, j);
      }

      this.f497 += j;
   }

   public byte[] m923() {
      if (this.f496 == null) {
         this.f496 = this.f495 == null ? null : this.f495.digest();
      }

      return this.f496;
   }

   public long m924() {
      return this.f497;
   }

   public void m925(File file1) throws IOException {
      this.m926(file1, false);
   }

   public void m926(File file1, boolean flag) throws IOException {
      BufferedInputStream bufferedinputstream = new BufferedInputStream(new FileInputStream(file1));
      byte[] abyte = new byte[4096];
      C_o_B c_o_b = flag ? null : new C_o_B();

      while (true) {
         try {
            int i = bufferedinputstream.read(abyte);
            if (i == -1) {
               break;
            }

            if (flag) {
               c_o_b.m1999(abyte, 0, i);
               this.write(c_o_b.m2005(true).getBytes());
            } else {
               this.write(abyte, 0, i);
            }
         } catch (IOException ioexception1) {
            try {
               bufferedinputstream.close();
            } catch (IOException ioexception) {
            }

            throw ioexception1;
         }
      }

      if (flag) {
         this.write(c_o_b.m2005(false).getBytes());
      }

      bufferedinputstream.close();
   }
}
