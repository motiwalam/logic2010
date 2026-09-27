package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.OutputStream;

public class Base64OutputStream extends OutputStream {
   OutputStream out;
   Base64Codec codec;
   byte[] unusedBuffer;
   int unusedCount;
   boolean closed;

   public Base64OutputStream(OutputStream outputstream) {
      this.out = outputstream;
      this.codec = new Base64Codec();
      this.unusedBuffer = null;
      this.unusedCount = 0;
      this.closed = false;
   }

   @Override
   public void write(int i) throws IOException {
      this.write(new byte[]{(byte)i});
   }

   @Override
   public void write(byte[] abyte) throws IOException {
      this.write(abyte, 0, abyte.length);
   }

   @Override
   public void write(byte[] abyte, int i, int j) throws IOException {
      if (!this.closed) {
         this.codec.addBytes(abyte, i, j);
      }
   }

   @Override
   public void flush() throws IOException {
      this.out.write(this.codec.encodeChunk(true).getBytes());
   }

   @Override
   public void close() throws IOException {
      this.out.write(this.codec.encodeChunk(false).getBytes());
      this.codec.reset();
      this.closed = true;
   }
}
