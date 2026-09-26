package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.OutputStream;

public class C_WC extends OutputStream {
   OutputStream f855;
   C_o_B f856;
   byte[] f857;
   int f858;
   boolean f859;

   public C_WC(OutputStream outputstream) {
      this.f855 = outputstream;
      this.f856 = new C_o_B();
      this.f857 = null;
      this.f858 = 0;
      this.f859 = false;
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
      if (!this.f859) {
         this.f856.m1999(abyte, i, j);
      }
   }

   @Override
   public void flush() throws IOException {
      this.f855.write(this.f856.m2005(true).getBytes());
   }

   @Override
   public void close() throws IOException {
      this.f855.write(this.f856.m2005(false).getBytes());
      this.f856.m1996();
      this.f859 = true;
   }
}
