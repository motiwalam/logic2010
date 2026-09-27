package edu.ucla.phil.logic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Md5OutputStream extends OutputStream {
   MessageDigest md5;
   byte[] digestBytes;
   long byteCount;

   public Md5OutputStream() {
      try {
         this.md5 = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
         this.md5 = null;
      }

      this.digestBytes = null;
      this.byteCount = 0L;
   }

   @Override
   public void write(int i) {
      this.write(new byte[]{(byte)i});
   }

   @Override
   public void write(byte[] abyte) {
      if (this.md5 != null) {
         this.md5.update(abyte);
      }

      this.byteCount += abyte.length;
   }

   @Override
   public void write(byte[] abyte, int i, int j) {
      if (this.md5 != null) {
         this.md5.update(abyte, i, j);
      }

      this.byteCount += j;
   }

   public byte[] digest() {
      if (this.digestBytes == null) {
         this.digestBytes = this.md5 == null ? null : this.md5.digest();
      }

      return this.digestBytes;
   }

   public long getByteCount() {
      return this.byteCount;
   }

   public void writeFile(File file1) throws IOException {
      this.writeFile(file1, false);
   }

   public void writeFile(File file1, boolean flag) throws IOException {
      BufferedInputStream bufferedinputstream = new BufferedInputStream(new FileInputStream(file1));
      byte[] abyte = new byte[4096];
      Base64Codec base64codec = flag ? null : new Base64Codec();

      while (true) {
         try {
            int i = bufferedinputstream.read(abyte);
            if (i == -1) {
               break;
            }

            if (flag) {
               base64codec.addBytes(abyte, 0, i);
               this.write(base64codec.encodeChunk(true).getBytes());
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
         this.write(base64codec.encodeChunk(false).getBytes());
      }

      bufferedinputstream.close();
   }
}
